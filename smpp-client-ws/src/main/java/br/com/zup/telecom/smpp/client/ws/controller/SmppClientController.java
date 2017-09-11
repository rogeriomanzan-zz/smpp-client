package br.com.zup.telecom.smpp.client.ws.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.zup.telecom.smpp.client.session.SmppClientSession;
import br.com.zup.telecom.smpp.client.session.SmppClientSessionResponse;
import br.com.zup.telecom.smpp.client.ws.request.SmsRequest;
import br.com.zup.telecom.smpp.client.ws.request.SmsRequestValidator;

@Controller
@RequestMapping("/smpp")
public class SmppClientController {
	
	@Autowired
	private SmppClientSession smppClientSession;

	@RequestMapping("/sms")
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SmppClientSessionResponse> sendSms(@RequestBody SmsRequest request) {
		SmsRequestValidator.check(request);
		return new ResponseEntity<>(
				smppClientSession.submitShortMessage(request.getPhoneNumber(), request.getMessage()), HttpStatus.OK);
	}

}
