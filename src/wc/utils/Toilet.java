package wc.utils;

import java.util.Optional;

public class Toilet {

	private Person user;

	public Toilet(Person user) {
		this.user = user;
	}

	/**
	 * We can change this approach to return Boolean or other value
	 * 
	 * @return
	 */
	public String whosThere() {

		return user.toString();
	}

	/**
	 * Here we should ship conditional logic to verify who is in
	 * 
	 * @param person
	 */
	public void enterToilet(Person person) {
		this.user = person;
	}

	public Person exitToilet() throws ToiletException {

		if (user.equals(Person.EMPTY)) {
			throw new ToiletException(
					"The bathroom is empty, so it is not possible to be emptier than it is already =( kill me please..");
		}

		Person aux = this.user;
		this.user = Person.EMPTY;

		return aux;
	}

	public Person getUser() {
		return user;
	}
	
	
	public String toString(){
		return user.toString();
	}


	

}
