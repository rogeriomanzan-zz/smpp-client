package br.com.zup.telecom.smpp.client.session;

public class SmppClientSessionException extends RuntimeException {

	private static final long serialVersionUID = 1040972200198422599L;

	private String code;
	
	public SmppClientSessionException(String message) {
		super( message );
	}

	public SmppClientSessionException() {
		super();
	}

	public SmppClientSessionException(String message, Throwable cause) {
		super( message, cause );
	}
	
	public SmppClientSessionException(String code, String message, Throwable cause) {
		super( message, cause );
		this.code = code;
	}

	public SmppClientSessionException(Throwable cause) {
		super( cause );
	}
	
	public String getCode() {
		return code != null ? code : "";
	}

}
