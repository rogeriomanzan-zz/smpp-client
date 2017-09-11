package br.com.zup.telecom.smpp.client.session;

import static org.junit.Assert.*;

import org.junit.Test;

public class SmppClientSessionCodeTest {

	@Test
	public void testCode() {
		assertEquals("smpp.connection.refused", SmppClientSessionCode.CONNECTION_REFUSED.code());
		assertEquals("smpp.io.error.occured", SmppClientSessionCode.IO_ERROR_OCCURED.code());
		assertEquals("smpp.invalid.pdu.parameter", SmppClientSessionCode.INVALID_PDU_PARAMETER.code());
		assertEquals("smpp.message.submitted", SmppClientSessionCode.MESSAGE_SUBIMITED.code());
		assertEquals("smpp.receive.invalid.response", SmppClientSessionCode.RECEIVE_INVALID_RESPONSE.code());
		assertEquals("smpp.receive.negative.response", SmppClientSessionCode.RECEIVE_NEGATIVE_RESPONSE.code());
		assertEquals("smpp.response.timeout",SmppClientSessionCode.RESPONSE_TIMEOUT.code());
	}

}
