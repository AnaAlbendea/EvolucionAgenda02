package agenda.excecepciones;

public class ContactoPersistenceException extends RuntimeException {

	public ContactoPersistenceException() {
		
	}

	public ContactoPersistenceException(String message) {
		super(message);
	
	}

	public ContactoPersistenceException(Throwable cause) {
		super(cause);

	}

	public ContactoPersistenceException(String message, Throwable cause) {
		super(message, cause);
		
	}

	public ContactoPersistenceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		
	}

}
