package wc.utils;

import wc.Bathroom;

public class Person extends Thread {
	private Gender gender;
	private String name;
	private Integer timeRequest;

	public Person(String name, Gender g, int time) {
		this.gender = g;
		this.timeRequest = time;
		this.name = name;
	}

	/**
	 * Points out the gender of the person
	 * 
	 * @param g,
	 *            Gender
	 */
	public void setGender(Gender g) {
		this.gender = g;
	}

	/**
	 * Set the time the person needs
	 * 
	 * @param time
	 */
	public void setTimeRequest(Integer time) {
		timeRequest = time;
	}

	public Gender getGender() {
		return gender;
	}

	public Integer getTimeRequest() {
		return timeRequest;
	}

	@Override
	public void run() {
		requiresToilet();
		useToilet();
		exitToilet();
	}

	private void useToilet() {
		System.out.println("["+ this.name+ "] - I am peeing. Lets Rock!");
		Bathroom bath = Bathroom.getBath();
		while (!bath.isPersonThere(this)) {
			try {
				sleep(timeRequest);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void requiresToilet() {
		System.out.println("["+ this.name+ "] - May I user the bath for " +timeRequest+ " seconds?");
		Bathroom bath = Bathroom.getBath();
		bath.addUser(this);

	}

	private void exitToilet() {
		System.out.println("["+ this.name+ "] - I am out.");
		Bathroom bath = Bathroom.getBath();
		bath.removePerson(this);
	}

}
