package wc;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
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
	
	private final int bathCapacity;
	private List<Person> usersList;
	private Queue<Person> maleWaitingList = new PriorityQueue<>();
	private Queue<Person> femaleWaitingList = new PriorityQueue<>();

	private static Bathroom bath;

	private Bathroom(int capacity, int waitingListCapacity) {
		this.bathCapacity = capacity;
		this.usersList = new ArrayList<>(capacity);
	}

	public synchronized long getOccupancy() {
		System.out.println("[" + Thread.currentThread().getName() + "] Currently the occupancy is: " + usersList.size()
				+ "/" + bathCapacity);
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
	public Boolean addUser(Person p) {

		if ( (p.getGender().equals(accessGenderProvider()) && usersList.size() < bathCapacity ) || usersList.size() == 0 ) {
			usersList.add(p);
			System.out.println(p.getName() + " enters in the bath.");
			return true;
		}
		return false;
	}

	private Gender accessGenderProvider() {
		return (usersList.stream().filter(a -> a.getGender() == Gender.MALE).count() > 0) ? Gender.MALE : Gender.FEMALE;
	}

	public Boolean isPersonThere(Person p) {

		try {
			semaphore.acquire();
			return usersList.contains(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}

		return false;

	}

	public void removePerson(Person person) {
		usersList.remove(person);
		System.out.println(person.getName() + " goes out");
	}

	public void warnWaitngQueue(Gender gender) {
		if (usersList.isEmpty()) {
			if (gender.equals(Gender.MALE)) {
				while (femaleWaitingList.size() > 0 && addUser(femaleWaitingList.poll())) {

				}
			} else {

				while (maleWaitingList.size() > 0 && addUser(maleWaitingList.remove())) {

				}
			}
		}

	}

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
