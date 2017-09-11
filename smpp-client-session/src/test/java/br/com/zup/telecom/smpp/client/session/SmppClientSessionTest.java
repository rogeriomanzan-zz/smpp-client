package br.com.zup.telecom.smpp.client.session;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class SmppClientSessionTest {
	
	@Mock
	private SmppClientSession session;
	
	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testSubmitShortMessage() {
		when(session.submitShortMessage(anyString(), anyString())).thenReturn(buildResult());
		
		SmppClientSessionResponse result = session.submitShortMessage("34912341234", "test");
		
		assertThat(result).isNotNull();
		assertThat(result).extracting("code").containsExactly(SmppClientSessionCode.MESSAGE_SUBIMITED.code());
		assertThat(result).extracting("messageId").containsExactly("001");
		assertThat(result).extracting("message").containsExactly("test");
		assertThat(result).extracting("info").containsExactly("infoTest");
	}	
	
	public SmppClientSessionResponse buildResult() {
		return new SmppClientSessionResponse(SmppClientSessionCode.MESSAGE_SUBIMITED.code(),
				"001", "test", "infoTest");
	}

}
