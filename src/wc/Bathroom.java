package wc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Semaphore;
import java.util.stream.Collectors;

import wc.utils.Gender;
import wc.utils.Person;

/**
 * Office Bathroon Class, detain all biombos.
 *
 * @author paulo
 */
public class Bathroom {
    private Semaphore semaphore = new Semaphore(1);

    private final int bathCapacity;
    private List<Person> usersList;
    private List<Person> waitingList;

    private static Bathroom bath;

    private Bathroom(int capacity, int waitingListCapacity) {
        this.bathCapacity = capacity;
        this.waitingList = new ArrayList<>(waitingListCapacity);
        this.usersList = new ArrayList<>(capacity);
    }

    public long getOccupancy() {
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
        if(bathCapacity > usersList.size()){
            usersList.add(p);
            System.out.println(p.getName() + " enters in the bath.");
        }else {
            System.out.println(p.getName() + " The bath is totally occuped.");
        }


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

    public static Bathroom getBath() {
        return bath;
    }

    public int getBathCapacity() {
        return bathCapacity;
    }

    public static Bathroom getInstance() {
        if (bath == null) {
            bath = Builder.build();
        }
        return bath;
    }


    /**
     * Bathroom Builder if the bathroom increases in attributes, just put these
     * here.
     *
     * @author paulo
     */
    public static class Builder {

        private static int bathCapacity;
        private static int queueCapacity;

        public Builder setBathCapacity(int capacity) {
            this.bathCapacity = capacity;
            return this;
        }

        public Builder setQueueCapacity(int qCapacity) {
            this.queueCapacity = qCapacity;
            return this;
        }

        /**
         * starts a Bathroom with empty toilets
         *
         * @return
         */
        public static Bathroom build() {
            if (bath == null) {
                synchronized (Bathroom.class) {
                    if (bath == null) {
                        bath = new Bathroom(bathCapacity, queueCapacity);
                    }
                }
            }
            return bath;
        }
    }


}
