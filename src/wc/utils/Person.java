package wc.utils;

public enum Person {
	MALE(1, 1.0), FEMALE(2, 1.0), EMPTY(0, 0.0);
	
	private final int gender;
	private final double timeNeeded;

	Person(Integer i, double j) {
		this.gender = i;
		this.timeNeeded = j;
	}

	public int getGender() {
		return this.gender;
	}

	public double getTimeNeeded() {
		return timeNeeded;
	}
	
	
}
