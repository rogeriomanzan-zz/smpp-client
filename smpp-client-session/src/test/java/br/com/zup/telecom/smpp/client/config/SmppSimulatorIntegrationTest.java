package br.com.zup.telecom.smpp.client.config;

import java.io.IOException;

import org.junit.BeforeClass;

/**
 * Starts the simulator with the settings in <code>SmppSimulatorConfig<code> 
 * for running the integrated tests with SMSC.
 * 
 * @author Rogério M. Manzan
 *
 */
public class SmppSimulatorIntegrationTest {
	
	private static SmppSimulatorConfig simulator;
	
	@BeforeClass
	public static void init() throws Exception {
		if(simulator == null) {
			simulator = new SmppSimulatorConfig();
			simulator.start();
		}
	}
	
	public static void stopSmppSimulator() throws IOException {
		if(simulator != null) {
			simulator.stop();
		}
	}
	
	public static boolean existsMessageSubmitted(String messageId) {
		return simulator.existsMessageSubmitted(messageId);
	}

}
