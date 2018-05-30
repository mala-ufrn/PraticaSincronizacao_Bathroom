package wc;

import java.util.AbstractQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import javax.swing.plaf.synth.SynthSeparatorUI;

import wc.utils.BathStatus;
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
	private Integer fairness = Integer.MAX_VALUE;

	private List<Person> usersList;
	private Queue<Person> maleWaitingList = new ConcurrentLinkedQueue();
	private Queue<Person> femaleWaitingList = new ConcurrentLinkedQueue();

	private static Bathroom bath;

	private Bathroom(int capacity) {
		this.bathCapacity = capacity;
		this.usersList = new ArrayList<>(capacity);
	}
	/**
	 * Returns the current bathroom's occupancy.
	 * This method must be synchronized as a guarantee of correctness 
	 * @return
	 */
	public synchronized long getOccupancy() {
		System.out.println("[" + Thread.currentThread().getName() + "] Currently the occupancy is: " + usersList.size()
				+ "/" + bathCapacity);
		return usersList.size();
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

	/**
	 * this method inserts blocks of rules for a person to enter the bathroom.
	 * Basically opposite genders cannot stay inside at the same time, ,
	 * moreover people can not in if bathroom is full or time request is greater than maximun permitted. 

	 * @param p
	 */
	public BathStatus addUser(Person p) {

		if ((p.getGender().equals(accessGenderProvider()) && usersList.size() < bathCapacity
				&& p.getTimeRequest() <= fairness) || usersList.size() == 0) {
			fairness = p.getTimeRequest();
			
			usersList.add(p);
			System.out.println("[BATH] - "+p.getName()+ " Gettin." );
			getOccupancy();
			return BathStatus.SUCCESS;
		}
		
		if (bathCapacity <= usersList.size())
			return BathStatus.FULL;
		if (!p.getGender().equals(accessGenderProvider()))
			return BathStatus.GENDER;
		if (!(p.getTimeRequest() <= fairness)){
			System.out.println("[BATH] - Release access for time requests less than: " +fairness);
			return BathStatus.TIME;
		}
		
		return BathStatus.NULL;
	}

	/**
	 * Informs the current gender inside the bathroom
	 * @return
	 */
	private Gender accessGenderProvider() {
		return (usersList.stream().filter(a -> a.getGender() == Gender.MALE).count() > 0) ? Gender.MALE : Gender.FEMALE;
	}

	/**
	 * Ask if a person is inside the bath.
	 * @param p
	 * @return
	 */
	public Boolean isPersonThere(Person p) {

		try {
			- I can not enter in the bath. I am going to the queue because: Opposite gender in
			[Lourdes] - May I use the bath for 23 seconds?
			[BATH] - Release access for time requests less than: 9
			[Lourdes] - I can not enter in the bath. I am going to the queue because: Time Requested
			[Joao] - May I use the bath for 7 seconds?
			[Joao] - I can not enter in the bath. I am going to the queue because: Opposite gender in
			[Tiago] - May I use the bath for 15 seconds?
			[Tiago] - I can not enter in the bath. I am going to the queue because: Opposite gender in
			[Geovanio] - May I use the bath for 1 seconds?
			[Geovanio] - I can not enter in the bath. I am going to the queue because: Opposite gender in
			[Hugo] - May I use the bath for 22 seconds?
			[Hugo] - I can not enter in the bath. I am going to the queue because: Opposite gender in
			[Maria] - Finished.
			[BATH] - Maria goes out
			[Maria] Currently the occupancy is: 0/2
			[BATH] - Cicero Gettin.
			[Maria] Currently the occupancy is: 1/2
			[BATH] - Joao Gettin.
			[Maria] Currently the occupancy is: 2/2
			[Joao] - Peed.
			[Joao] - Finished.
			[BATH] - Joao goes out
			[Joao] Currently the occupancy is: 1/2
			[Cicero] - Peed.
			[Cicero] - Finished.
			[BATH] - Cicero goes out
			[Cicero] Currently the occupancy is: 0/2			semaphore.acquire();
			return usersList.contains(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

		return false;

	}
	
	/**
	 * Removes a Person from bath.
	 * @param person
	 */
	public void removePerson(Person person) {
		usersList.remove(person);
		System.out.println("[BATH] - "+ person.getName() + " goes out");
		getOccupancy();
	}

	/**
	 * Notify the waiting people on queues 
	 * @param gender
	 */
	public void warnWaitngQueue(Gender gender) {
		if (usersList.isEmpty()) {
			if (gender.equals(Gender.MALE)) {
				if (!femaleWaitingList.isEmpty()){
					while (femaleWaitingList.size() > 0 && addUser(femaleWaitingList.poll()).equals(BathStatus.SUCCESS)) {

					}
				} else {
					while (maleWaitingList.size() > 0 && addUser(maleWaitingList.poll()).equals(BathStatus.SUCCESS)) {

					}
				}
			} else {
				if (!maleWaitingList.isEmpty()){
					while (maleWaitingList.size() > 0 && addUser(maleWaitingList.poll()).equals(BathStatus.SUCCESS)) {

					}
				} else {
					while (femaleWaitingList.size() > 0 && addUser(femaleWaitingList.poll()).equals(BathStatus.SUCCESS)) {

					}
				}
			}
		}
	}

	// Setters and Getters 
	
	public List<Person> getUsersList() {
		return usersList;
	}

	public int getBathCapacity() {
		return bathCapacity;
	}

	public Queue<Person> getMaleWaitingList() {
		return maleWaitingList;
	}

	public Queue<Person> getFemaleWaitingList() {
		return femaleWaitingList;
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
		
		public Builder setBathCapacity(int capacity) {
			this.capacity = capacity;
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
						bath = new Bathroom(capacity);
					}
				}
			}
			return bath;
		}
	}

}
