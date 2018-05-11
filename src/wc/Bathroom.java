package wc;

import java.util.ArrayList;
import java.util.List;

import wc.utils.Toilet;

/**
 * Office Bathroon Class, detain all toilets
 * @author paulo
 *
 */
public final class Bathroom {

	private List<Toilet> bathList;

	public Bathroom(int capacity) {
		bathList = new ArrayList<Toilet>(capacity);
	}

	public List<Toilet> getBathList() {
		return bathList;
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
		 * @return
		 */
		public Bathroom build() {
			Bathroom bath = new Bathroom(this.bathCapacity);
			
			for (int i = 0; i < bathCapacity; i++)
				bath.getBathList().add(Toilet.FEMALE);
			
			return bath;
		}
	}

}
