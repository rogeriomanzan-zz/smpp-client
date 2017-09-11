package br.com.zup.telecom.smpp.client.session;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Response to use in return {@link SmppClientSession}.
 * 
 * @author Rogério M. Manzan
 *
 */
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class SmppClientSessionResponse {

	/**
	 * code is any value of {@link SmppClientSessionCode}.
	 */
	private String code;
	
	/**
	 * messageId is the message_id to identified the submitted short message in SMPP Server or SMSC.
	 */
	private String messageId;

	/**
	 * message is the submitted short message.
	 */
	private String message;

	/**
	 * info is some information, may be exception messages.
	 */
	private String info;
	
	/**
	 * Default constructor.
	 */
	public SmppClientSessionResponse() {
	}
	
	public SmppClientSessionResponse(String code, String info) {
		this.code = code;
		this.info = info;
	}
	
	public SmppClientSessionResponse(String code, String messageId, String message, String info) {
		this.code = code;
		this.messageId = messageId;
		this.message = message;
		this.info = info;
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
