package wc.utils;

public enum Gender {
	MALE("male"), FEMALE("female"), EMPTY("empty");
	
	private final String gender;

	Gender(String gender) {
		this.gender = gender;

	}

	public String getGender() {
		return this.gender;
	}
	
	
}
