package wc;

import java.util.ArrayList;
import java.util.List;

import wc.utils.Person;
import wc.utils.Toilet;

/**
 * Office Bathroon Class, detain all toilets
 * 
 * @author paulo
 *
 */
public final class Bathroom {
	int occupancy = 0;
	private List<Toilet> toiletList;

	public Bathroom(int capacity) {
		toiletList = new ArrayList<Toilet>(capacity);
	}

	public List<Toilet> getToiletList() {
		return toiletList;
	}

	public int getOccupancy() {
		occupancy = 0;

		this.getToiletList().stream()
			.filter(a -> a.getUser() != Person.EMPTY)
			.forEach(a -> occupancy++);

		System.out.println("Currently the total occupancy is: " + occupancy);
		
		return occupancy;
	}

	public void atThisMoment() {

		this.getToiletList().forEach(System.out::println);
	}

	/**
	 * Bathroom Builder if the bathroom increases in attributes, just put these
	 * here.
	 * 
	 * @author paulo
	 *
	 */
	public static class Builder {

		private int bathCapacity;

		public Builder setBathCapacity(int capacity) {
			this.bathCapacity = capacity;
			return this;
		}

		/**
		 * starts a Bathroom with empty toilets
		 * 
		 * @return
		 */
		public Bathroom build() {
			Bathroom bath = new Bathroom(this.bathCapacity);

			for (int i = 0; i < bathCapacity; i++)
				bath.getToiletList().add(new Toilet(Person.EMPTY));

			return bath;
		}
	}

}
