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
		
		
		for (int i = 0; i < BATH_CAPACITY; i++){
			Person p = new Person("T"+i , Gender.MALE, i*2);
			p.start();
			
		}
		
		
		
	}

}
