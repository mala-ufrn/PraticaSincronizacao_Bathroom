package wc.utils;

/**
 * Knowed states for the bathroom 
 * @author paulo
 *
 */
public enum BathStatus {
	EMPTY("empty bathroom"), 
	FULL("full bathroom"),
	GENDER("Opposite gender in"),
	TIME("Time Requested"),
	SUCCESS("enters in the bath"),
	NULL("Unknown Status");
	
	private final String bathAnswer;

	BathStatus(String bathAnswer) {
		this.bathAnswer = bathAnswer;

	}

	public String getBathAnswer() {
		return this.bathAnswer;
	}
}
