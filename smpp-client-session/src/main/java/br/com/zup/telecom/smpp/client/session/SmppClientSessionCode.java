package br.com.zup.telecom.smpp.client.session;

/**
 * Code to identify return {@link SmppClientSession}.
 * 
 * @author Rogério M. Manzan
 */
public enum SmppClientSessionCode {

	CONNECTION_REFUSED("smpp.connection.refused"),
	IO_ERROR_OCCURED("smpp.io.error.occured"),
	INVALID_PDU_PARAMETER("smpp.invalid.pdu.parameter"),
	MESSAGE_SUBIMITED("smpp.message.submitted"),
	RECEIVE_INVALID_RESPONSE("smpp.receive.invalid.response"),
	RECEIVE_NEGATIVE_RESPONSE("smpp.receive.negative.response"),
	RESPONSE_TIMEOUT("smpp.response.timeout"),
	;
	
	private String code;
	
	private SmppClientSessionCode(String code) {
		this.code = code;
	}
	
	/**
	 * code to identify the execution result in {@link SmppClientSession#submitShortMessage}.
	 * 
	 * @return
	 */
	public String code() {
		return this.code;
	}
}
