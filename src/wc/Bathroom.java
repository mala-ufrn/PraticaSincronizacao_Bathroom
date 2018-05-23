package wc;

import java.util.ArrayList;
import java.util.List;

import wc.utils.Gender;
import wc.utils.Person;
import wc.utils.Toilet;

/**
 * Office Bathroon Class, detain all biombos.
 * 
 * @author paulo
 *
 */
public class Bathroom {
	
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
		long aux = this.getUsersList().stream()
			.filter(a -> a.getGender() != Gender.EMPTY).count();
			
		
		System.out.println("Currently the total occupancy is: " + aux + "/" +this.getUsersList().size());
		return aux;
	}
	
	
	public void addUser(Person p){
		usersList.add(p);
	}
	
	public Boolean isPersonThere(Person p){
		return usersList.contains(p);
	}
	

	public void removePerson(Person person) {
		usersList.remove(person);
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
	
	public static Bathroom getInstance(){
		if (bath == null){
			bath = Builder.build();
		}
		return bath;
	}
	
	

	/**
	 * Bathroom Builder if the bathroom increases in attributes, just put these
	 * here.
	 * 
	 * @author paulo
	 *
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
