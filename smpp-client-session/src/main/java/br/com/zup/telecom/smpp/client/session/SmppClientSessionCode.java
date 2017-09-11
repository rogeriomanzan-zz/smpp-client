package br.com.zup.telecom.smpp.client.session;

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
	
	public String code() {
		return this.code;
	}
}
