import wc.Bathroom;
import wc.utils.Gender;
import wc.utils.Person;

public class Main {


    public final static int BATH_CAPACITY = 3;


    public static void main(String[] args) {


        Bathroom bath = new Bathroom.Builder()
                .setBathCapacity(BATH_CAPACITY)
                .setQueueCapacity(BATH_CAPACITY)
                .build();

        Person p0 = new Person("Cicero", Gender.MALE, 2 * 2);
        Person p1 = new Person("Joao", Gender.MALE, 2 * 2);
        Person p2 = new Person("Tiago", Gender.MALE, 2 * 3);
        Person p3 = new Person("Geovanio", Gender.MALE, 2 * 3);
        Person p4 = new Person("Lourdes", Gender.FEMALE, 2 * 3);
        Person p5 = new Person("Hugo", Gender.MALE, 2 * 3);
        
        
        p0.start();
        p4.start();
        p1.start();
        p2.start();
        p3.start();
        p5.start();
        

        try {
        	p0.join(1000);
        	p1.join(1000);
            p2.join(1000);
            p3.join(1000);
            p4.join(1000);
            p5.join(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bath.getOccupancy();
    }

}
