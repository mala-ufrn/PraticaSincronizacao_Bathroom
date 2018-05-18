package wc.utils;

public enum Person {
	MALE(1), FEMALE(2), EMPTY(0);

	private final int gender;

	Person(Integer i) {
		this.gender = i;
	}

	public int getGender() {
		return this.gender;
	}
}
