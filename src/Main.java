import java.util.Random;

import wc.Bathroom;
import wc.utils.Gender;
import wc.utils.Person;

public class Main {


    public final static int BATH_CAPACITY = 2;
    
    

    public static void main(String[] args) {
    	Random generator = new Random();
    	
        Bathroom bath = new Bathroom.Builder()
                .setBathCapacity(BATH_CAPACITY)
                .build();
        
        
        System.out.println("The Bath capacity is "+BATH_CAPACITY);
        Person p0 = new Person("Cicero", Gender.MALE, generator.nextInt(25));
        Person p6 = new Person("Maria", Gender.FEMALE, generator.nextInt(25));
        Person p1 = new Person("Joao", Gender.MALE, generator.nextInt(25));
        Person p2 = new Person("Tiago", Gender.MALE, generator.nextInt(25));
        Person p3 = new Person("Geovanio", Gender.MALE, generator.nextInt(25));
        Person p4 = new Person("Lourdes", Gender.FEMALE, generator.nextInt(25));
        Person p5 = new Person("Hugo", Gender.MALE, generator.nextInt(25));
        
        p6.start();
        p0.start();
        p4.start();
        p1.start();
        p2.start();
        p3.start();
        p5.start();
        

        try {
        	p6.join(1000);
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
        System.out.println("The day is over.");
        System.exit(-1);
    }

}
