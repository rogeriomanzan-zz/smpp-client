package br.com.zup.telecom.smpp.client.session;

import java.io.IOException;
import java.util.Date;

import org.jsmpp.InvalidResponseException;
import org.jsmpp.PDUException;
import org.jsmpp.bean.Alphabet;
import org.jsmpp.bean.BindType;
import org.jsmpp.bean.ESMClass;
import org.jsmpp.bean.GeneralDataCoding;
import org.jsmpp.bean.MessageClass;
import org.jsmpp.bean.NumberingPlanIndicator;
import org.jsmpp.bean.RegisteredDelivery;
import org.jsmpp.bean.SMSCDeliveryReceipt;
import org.jsmpp.bean.TypeOfNumber;
import org.jsmpp.extra.NegativeResponseException;
import org.jsmpp.extra.ResponseTimeoutException;
import org.jsmpp.session.BindParameter;
import org.jsmpp.session.SMPPSession;
import org.jsmpp.util.AbsoluteTimeFormatter;
import org.jsmpp.util.TimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Component to communicate with SMPP Server or SMSC.
 * 
 * @author Rogério M. Manzan
 */
@Component
public class SmppClientSession {

	private static final Logger LOGGER = LoggerFactory.getLogger(SmppClientSession.class);
	
	private static final TimeFormatter TIME_FORMATTER = new AbsoluteTimeFormatter();
	
	/**
	 * host is the SMSC host address.
	 */
	@Value("${smsc-gw.host}")
	private String host;

	/**
	 * port is the SMSC listen port.
	 */
	@Value("${smsc-gw.port}")
	private String port;
	
	/**
	 * systemName is SMSC system id.
	 */
	@Value("${smsc-gw.systemName}")
	private String systemName;
	
	/**
	 * password is the password.
	 */
	@Value("${smsc-gw.password}")
	private String password;
	
	/**
	 * systemType is the system type.
	 */
	@Value("${smsc-gw.systemType}")
	private String systemType;
	
	/**
	 * sourceAddr is the source_addr.
	 */
	@Value("${smsc-gw.sourceAddr}")
	private String sourceAddr;

	/**
	 * Submit a short message to specified destination address.
	 * 
	 * @param phoneNumber is the destination address.
	 * @param shortMessage is the short message to be sent.
	 * @return SmppClientSessionResponse
	 * @throws SmppClientSessionException if there error.
	 */
	public SmppClientSessionResponse submitShortMessage(String phoneNumber, String shortMessage) {

		String messageId = "";
		SMPPSession session = init();
		
		try {
			messageId = session.submitShortMessage(null, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN,
					sourceAddr, TypeOfNumber.INTERNATIONAL, NumberingPlanIndicator.UNKNOWN, phoneNumber, new ESMClass(),
					(byte) 0, (byte) 1, TIME_FORMATTER.format(new Date()), null,
					new RegisteredDelivery(SMSCDeliveryReceipt.DEFAULT), (byte) 0,
					new GeneralDataCoding(Alphabet.ALPHA_DEFAULT, MessageClass.CLASS1, false), (byte) 0,
					shortMessage.getBytes());

			LOGGER.info("Message submitted, message_id: {}", messageId);

		} catch (PDUException e) {

			String code = SmppClientSessionCode.INVALID_PDU_PARAMETER.code();
			throw new SmppClientSessionException(code, "Invalid PDU parameter", e);

		} catch (ResponseTimeoutException e) {

			String code = SmppClientSessionCode.RESPONSE_TIMEOUT.code();
			throw new SmppClientSessionException(code, "Response timeout", e);

		} catch (InvalidResponseException e) {

			String code = SmppClientSessionCode.RECEIVE_INVALID_RESPONSE.code();
			throw new SmppClientSessionException(code, "Receive invalid response", e);

		} catch (NegativeResponseException e) {

			String code = SmppClientSessionCode.RECEIVE_NEGATIVE_RESPONSE.code();
			throw new SmppClientSessionException(code, "Receive negative response", e);

		} catch (IOException e) {

			String code = SmppClientSessionCode.IO_ERROR_OCCURED.code();
			throw new SmppClientSessionException(code, "IO error occured", e);
		}

		session.unbindAndClose();

		String code = SmppClientSessionCode.MESSAGE_SUBIMITED.code();
		return new SmppClientSessionResponse(code, messageId, shortMessage, "Message submitted");
	}
	
	/**
	 * Open connection and bind immediately.
	 * 
	 * @return open SMPPSession. 
	 * @throws SmppClientSessionException if there error.
	 */
	private SMPPSession init() {
		SMPPSession session;
		try {	
			session = new SMPPSession();
			String systemId = session.connectAndBind(host, Integer.valueOf(port), new BindParameter(BindType.BIND_TX,
					systemName, password, systemType, TypeOfNumber.UNKNOWN, NumberingPlanIndicator.UNKNOWN, null));
			LOGGER.info("Connected with SMSC with system id {}", systemId);
		
		} catch (Exception e) {
			
			String code = SmppClientSessionCode.CONNECTION_REFUSED.code();
			throw new SmppClientSessionException(code,
					String.format("Failed connect and bind to host '%s:%s'", host, port), e);
		
		}
		return session;
	}

}
