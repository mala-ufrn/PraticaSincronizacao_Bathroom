package wc.utils;

import wc.Bathroom;

public class Person extends Thread {
	private Gender gender;
	private Integer timeRequest;

	public Person(String name, Gender g, int time) {
		super(name);
		this.gender = g;
		this.timeRequest = time;
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

	private void requiresToilet() {

		Bathroom bath = Bathroom.getInstance();
		try {
			bath.getSemaphore().acquire();
			System.out.println("[" + this.getName() + "] - May I use the bath for " + timeRequest + " seconds?");

			if (!bath.addUser(this)) {
				System.out
						.println(Thread.currentThread().getName() + " I Can not enter in the bath. I am going to the queue.");
				if (this.getGender().equals(Gender.MALE)) {
					bath.getMaleWaitingList().add(this);
				} else {
					bath.getFemaleWaitingList().add(this);
				}
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bath.getSemaphore().release();
		}

	}

	private void useToilet() {

		Bathroom bath = Bathroom.getInstance();
		while (!bath.isPersonThere(this)) {

			try {
				sleep((timeRequest*100));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("[" + this.getName() + "] - Peed.");

	}

	private void exitToilet() {
		
		Bathroom bath = Bathroom.getInstance();
		try {
			bath.getSemaphore().acquire();
			System.out.println("[" + this.getName() + "] - Finished.");
			bath.removePerson(this);
			bath.warnWaitngQueue(this.getGender());
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			bath.getSemaphore().release();
		}

	}

}
