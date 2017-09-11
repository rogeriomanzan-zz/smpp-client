package br.com.zup.telecom.smpp.client.ws.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.telecom.smpp.client.session.SmppClientSessionResponse;
import br.com.zup.telecom.smpp.client.ws.controller.configIntegrationTest.SmppSimulatorIntegrationTest;

/**
 * <p>Integrated test uses an SMSC simulator and SMPP protocol.</p>
 * <p>Success scenario in sending message.</p>
 * 
 * @author Rogério M. Manzan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SmppClientControllerIntegrationTest extends SmppSimulatorIntegrationTest {
	
	private MockMvc mockMvc;
	
    @Autowired
    private WebApplicationContext context;
	
	@Before
	public void setUp() throws Exception {		
		mockMvc = MockMvcBuilders
				.webAppContextSetup(this.context)
				.build();
	}

	@Test
	public void testSendSms() throws Exception {
		
		MvcResult result = mockMvc.perform(
				post("/smpp/sms")
				.content(getSmsRequest())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect( status().isOk() )
				.andReturn();
		
		String jsonResponse = result.getResponse().getContentAsString();
		SmppClientSessionResponse response = new ObjectMapper().readValue(jsonResponse, SmppClientSessionResponse.class);
		
		assertThat("smpp.message.submitted", equalTo(response.getCode()));
		assertThat("Message submitted", equalTo(response.getInfo()));
		assertThat("Message Test", equalTo(response.getMessage()));
		
		assertTrue(existsMessageSubmitted(response.getMessageId()));
	}
	
	public String getSmsRequest() {
		return "{\"phoneNumber\" : \"12345678\",\"message\" : \"Message Test\"}";
	}

}