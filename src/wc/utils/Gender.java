package wc.utils;

/**
 * Defines the gender of people
 * @author paulo
 *
 */
public enum Gender {
	MALE("male"), FEMALE("female");
	
	private final String gender;

	Gender(String gender) {
		this.gender = gender;

	}

	public String getGender() {
		return this.gender;
	}
	
	
}
