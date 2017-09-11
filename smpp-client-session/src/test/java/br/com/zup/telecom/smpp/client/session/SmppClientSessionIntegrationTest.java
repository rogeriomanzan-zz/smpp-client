package br.com.zup.telecom.smpp.client.session;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.zup.telecom.smpp.client.config.SmppSimulatorIntegrationTest;

@RunWith(SpringRunner.class)
@Import(SmppClientSessionConfig.class)
@TestPropertySource("classpath:application-test.properties")
public class SmppClientSessionIntegrationTest extends SmppSimulatorIntegrationTest {
	
	@Autowired
	private SmppClientSession smppClientSession;

	@Test
	public void testSubmitShortMessage() {
		
		SmppClientSessionResponse result = smppClientSession.submitShortMessage("34912345678", "Short Message Test");
		assertThat(SmppClientSessionCode.MESSAGE_SUBIMITED.code(), equalTo(result.getCode()));
	}

}
