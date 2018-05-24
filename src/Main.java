import wc.Bathroom;
import wc.utils.Gender;
import wc.utils.Person;

public class Main {


    public final static int BATH_CAPACITY = 10;


    public static void main(String[] args) {


        Bathroom bath = new Bathroom.Builder()
                .setBathCapacity(BATH_CAPACITY)
                .setQueueCapacity(BATH_CAPACITY)
                .build();

        Person p1 = new Person("Joao", Gender.MALE, 2 * 2);
        Person p2 = new Person("Tiago", Gender.MALE, 2 * 3);
        Person p3 = new Person("Geovanio", Gender.MALE, 2 * 30);
        Person p4 = new Person("Lourdes", Gender.MALE, 2 * 30);
        
        p1.start();
        p2.start();
        p3.start();
        p4.start();

        try {
            p1.join(5000);
            p2.join(5000);
            p3.join(5000);
            p4.join(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        bath.getOccupancy();
    }

}
