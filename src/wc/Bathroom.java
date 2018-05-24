package wc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import wc.utils.Gender;
import wc.utils.Person;

/**
 * Office Bathroon Class, detain all biombos.
 *
 * @author paulo
 */
public class Bathroom {
    private Semaphore semaphore = new Semaphore(1);
    private final Lock lock = new ReentrantLock();
    final Condition notMale = lock.newCondition();
    final Condition notFemale = lock.newCondition();
    

    private final int bathCapacity;
    private List<Person> usersList;
    private List<Person> waitingList;

    private static Bathroom bath;

    private Bathroom(int capacity, int waitingListCapacity) {
        this.bathCapacity = capacity;
        this.waitingList = new ArrayList<>(waitingListCapacity);
        this.usersList = new ArrayList<>(capacity);
    }

    public synchronized long getOccupancy() {
        System.out.println("[" + Thread.currentThread().getName() + "] Currently the occupancy is: " + usersList.size() + "/" + bathCapacity);
        return usersList.size();
    }

    public Semaphore getSemaphore() {
        return semaphore;
    }

    /**
     * Here we must insert smart blocks to see if one person could enter
     *
     * @param p
     */
    public void addUser(Person p) {
    	lock.lock();
    	try {
    		while (bathCapacity > usersList.size() && accessProvider().equals(p.getGender()) || usersList.isEmpty()){
    			if (p.getGender().equals(Gender.MALE)){
    				notFemale.await();
    				waitingList.add(p);
    			} else {
    				notMale.await();
    				waitingList.add(p);
    			}
    		}
    		notMale.signal();
    		notFemale.signal();
    	} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
    		lock.unlock();
    	}
    	 
    	
    	if(bathCapacity > usersList.size()){
            usersList.add(p);
            System.out.println(Thread.currentThread().getName() + " enters in the bath.");
        }else {
            System.out.println(Thread.currentThread().getName() + " The bath is totally occuped.");
        }
    }
    
    private Gender accessProvider(){
    	return (usersList.stream().filter(a -> a.getGender() == Gender.MALE).count() > 0) ? Gender.MALE : Gender.FEMALE;
    }


    public Boolean isPersonThere(Person p) {

        return usersList.contains(p);

    }


    public void removePerson(Person person) {
        usersList.remove(person);
        System.out.println(person.getName() + " goes out");

    }


    public List<Person> getUsersList() {
        return usersList;
    }

    public List<Person> getWaitingList() {
        return waitingList;
    }

    public int getBathCapacity() {
        return bathCapacity;
    }

    public static Bathroom getInstance() {
        if (bath == null) {
            bath = new Bathroom.Builder().build();
        }
        return bath;
    }


    /**
     * Bathroom Builder if the bathroom increases in attributes, just put these
     * here.
     *
     * @author paulo
     */
    @SuppressWarnings("static-access")
    public static class Builder {

        private static int capacity;
        private static int qCapacity;
        
        public Builder setBathCapacity(int capacity) {
            this.capacity = capacity;
            return this;
        }
        
        
		public Builder setQueueCapacity(int qCapacity) {
            this.qCapacity = qCapacity;
            return this;
        }

        /**
         * starts a Bathroom with empty toilets
         *
         * @return
         */
        public Bathroom build() {
            if (bath == null) {
                synchronized (Bathroom.class) {
                    if (bath == null) {
                        bath = new Bathroom(capacity, qCapacity);
                    }
                }
            }
            return bath;
        }
    }


}
