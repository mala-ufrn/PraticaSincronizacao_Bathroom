import wc.Bathroom;

public class Main {
	
	
	public final static int BATH_CAPACITY = 10;
	
	
	public static void main(String[] args) {
		
		Bathroom bath = new Bathroom.Builder()
				.setBathCapacity(BATH_CAPACITY)
				.build();

		System.out.println(bath.getToiletList().get(0).whosThere());
		
	}

}
