package wc.utils;

public class ToiletException extends Exception {
	private String cause;
	
	public ToiletException(String cause){
		this.cause = cause;
	}
	
	public String getMessage(){
		return cause;
	}
}
