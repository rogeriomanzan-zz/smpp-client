package br.com.zup.telecom.smpp.client.ws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

import br.com.zup.telecom.smpp.client.session.SmppClientSessionConfig;

@SpringBootApplication
@Import(value = SmppClientSessionConfig.class)
public class SmppClientApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(SmppClientApplication.class, args);
	}

}
