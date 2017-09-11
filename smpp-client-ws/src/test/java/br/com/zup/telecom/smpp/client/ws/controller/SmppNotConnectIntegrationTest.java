package br.com.zup.telecom.smpp.client.ws.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

/**
 * <p>Integrated test uses an SMSC simulator and SMPP protocol.</p>
 * <p>Fail scenario in sending message.</p>
 * 
 * @author Rogério M. Manzan
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
public class SmppNotConnectIntegrationTest {
	
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
	public void testNotSendSms() throws Exception {
		
		MvcResult result = mockMvc.perform(
				post("/smpp/sms")
				.content(getSmsRequest())
				.contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect( status().is5xxServerError() )
				.andReturn();
		
		assertTrue(result.getResponse().getContentAsString().contains("Failed connect"));
	}
	
	public String getSmsRequest() {
		return "{\"phoneNumber\" : \"34912345678\",\"message\" : \"Message Test\"}";
	}

}