package br.com.zup.telecom.smpp.client.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smpp.SmppObject;
import org.smpp.debug.Debug;
import org.smpp.debug.Event;
import org.smpp.debug.FileDebug;
import org.smpp.debug.FileEvent;
import org.smpp.smscsim.DeliveryInfoSender;
import org.smpp.smscsim.PDUProcessorGroup;
import org.smpp.smscsim.SMSCListener;
import org.smpp.smscsim.SMSCListenerImpl;
import org.smpp.smscsim.SMSCSession;
import org.smpp.smscsim.ShortMessageStore;
import org.smpp.smscsim.SimulatorPDUProcessor;
import org.smpp.smscsim.SimulatorPDUProcessorFactory;
import org.smpp.smscsim.util.Table;

/**
 * Class <code>SmppSimulatorConfig</code> is an integrated test application configuration
 * that simulates an a Short Message Service Centre (SMSC).
 * 
 * @see org.smpp.smscsim.Simulator
 * 
 * @author Rogério M. Manzan
 *
 */
public class SmppSimulatorConfig {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmppSimulatorConfig.class);
	
	/**
	 * Port to list on. 
	 * OBS: must have the same value as the property 'smsc-gw.port' in application.properties.
	 */
	static int port = 19510;

	/**
	 * Directory for creating of debug and event files.
	 */
	static final String simulatorDir = "src/test/resources/smpp/";
	
	/**
	 * Name of file with user (client) authentication information.
	 */
	static String credentialsFileName = simulatorDir + "credentials.properties";

	/**
	 * The debug object.
	 */
	static Debug debug = new FileDebug(simulatorDir, "sim.dbg");

	/**
	 * The event object.
	 */
	static Event event = new FileEvent(simulatorDir, "sim.evt");
	
	boolean keepRunning = true;
	private SMSCListener smscListener = null;
	private SimulatorPDUProcessorFactory factory = null;
	private PDUProcessorGroup processors = null;
	private ShortMessageStore messageStore = null;
	private DeliveryInfoSender deliveryInfoSender = null;
	private Table users = null;
	private boolean displayInfo = true;

	public static final int DSIM = 16;
	public static final int DSIMD = 17;
	public static final int DSIMD2 = 18;
	
	/**
	 * @see org.smpp.smscsim.Simulator#start()
	 * 
	 * @param port
	 * @throws IOException
	 */
	protected void start() throws Exception {
		
		SmppObject.setDebug(debug);
		SmppObject.setEvent(event);
		debug.activate();
		event.activate();
		debug.deactivate(SmppObject.DRXTXD2);
		debug.deactivate(SmppObject.DPDUD);
		debug.deactivate(SmppObject.DCOMD);
		debug.deactivate(DSIMD2);
		
		debug.write("simulator started");
		
		if (smscListener == null) {
			
			LOGGER.debug("SMPP Simulator starting listener... ");
			
			smscListener = new SMSCListenerImpl(port, true);
			processors = new PDUProcessorGroup();
			messageStore = new ShortMessageStore();
			deliveryInfoSender = new DeliveryInfoSender();
			deliveryInfoSender.start();
			users = new Table(credentialsFileName);
			factory = new SimulatorPDUProcessorFactory(processors, messageStore, deliveryInfoSender, users);
			factory.setDisplayInfo(displayInfo);
			smscListener.setPDUProcessorFactory(factory);
			smscListener.start();

			LOGGER.debug(String.format("SMPP Simulator started port:%s", port));
		
		} else {
			LOGGER.debug("SMPP Simulator Listener is already running.");
		}
	}
	
	/**
	 * @see org.smpp.smscsim.Simulator#stop()
	 */
	protected void stop() throws IOException {
		
		if (smscListener != null) {
			LOGGER.debug("Stopping listener...");
			
			synchronized (processors) {
				int procCount = processors.count();
				SimulatorPDUProcessor proc;
				SMSCSession session;
				for (int i = 0; i < procCount; i++) {
					proc = (SimulatorPDUProcessor) processors.get(i);
					session = proc.getSession();
					LOGGER.debug("SMPP Simulator Stopping session " + i + ": " + proc.getSystemId() + " ...");
					session.stop();
					LOGGER.debug("SMPP Simulator stopped.");
				}
			}
			smscListener.stop();
			smscListener = null;
			if (deliveryInfoSender != null) {
				deliveryInfoSender.stop();
			}
			LOGGER.debug("Stopped.");
		}
	}
	
	/**
	 * Get a message by messageId.
	 * 
	 * @param messageId
	 * @return
	 */
	protected boolean existsMessageSubmitted(String messageId) {
		
		if (smscListener != null) {
			try {
				return  messageStore.getMessage(messageId) != null;
			} catch (Exception e) {
				LOGGER.debug("Failed get message", e);
			}
		}
		
		return false;
	}

}
