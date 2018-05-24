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
     * @param g, Gender
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

        System.out.println("[" + this.getName() + "] - May I use the bath for " + timeRequest + " seconds?");
        Bathroom bath = Bathroom.getBath();
        try {
            bath.getSemaphore().acquire();

            bath.addUser(this);
            bath.getOccupancy();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bath.getSemaphore().release();
        }

    }


    private void useToilet() {
        System.out.println("[" + this.getName() + "] - I am peeing. Lets Rock!");
        Bathroom bath = Bathroom.getBath();
        while (!bath.isPersonThere(this)) {
            try {
                sleep(timeRequest);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void exitToilet() {
        System.out.println("[" + this.getName() + "] - Finished.");
        Bathroom bath = Bathroom.getBath();
        try {
            bath.getSemaphore().acquire();
            bath.removePerson(this);
            bath.getOccupancy();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bath.getSemaphore().release();
        }

    }

}
