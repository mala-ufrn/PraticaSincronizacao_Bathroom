import wc.Bathroom;
import wc.utils.Person;

public class Main {
	
	
	public final static int BATH_CAPACITY = 10;
	
	
	public static void main(String[] args) {
		
		System.out.println(args[0]);
		
		Bathroom bath = new Bathroom.Builder()
				.setBathCapacity(BATH_CAPACITY)
				.build();
		
		bath.getToiletList().get(5).enterToilet(Person.MALE);
		bath.getOccupancy();
		bath.atThisMoment();
		
	}

}
