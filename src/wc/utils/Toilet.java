package wc.utils;

import java.util.Optional;

public class Toilet {
	
	private Optional<Boolean> whoIs;
	
	private Toilet(Optional<Boolean> user) {
		this.whoIs = user;
	}
	
	/**
	 * We can change this approach to return Boolean or other value
	 * @return
	 */
	public String whosThere(){
		if( whoIs.equals(MALE.whoIs)){
			return "MALE";
		} else if (whoIs.equals(FEMALE.whoIs)){
			return "FEMALE";
		}
		return "EMPTY";
	}
	
	/**
	 * Here we should ship conditional logic to verify who is in
	 * @param person
	 */
	public void enterToilet(Boolean person){
		this.whoIs = Optional.of(person);
	}
	
	public Boolean exitToilet(){
		Boolean aux = this.whoIs.get();
		this.whoIs = EMPTY.whoIs;
		return aux;
	}
	
	public static final Toilet MALE = new Toilet(Optional.of(true));
	public static final Toilet FEMALE = new Toilet(Optional.of(false));
	public static final Toilet EMPTY = new Toilet(Optional.empty());
	
}
