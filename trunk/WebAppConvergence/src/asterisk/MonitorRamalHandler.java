package asterisk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import connection.AsteriskSocket;

import model.MonitorRamalIAX;
import model.MonitorRamalSip;

/**
 * Classe responsável por listar os ramais para monitoramento, tanto IAX quanto
 * sip
 * 
 * @author daniel
 * 
 */

public class MonitorRamalHandler {

	/**
	 * Método para buscar a lista de todos os ramais SIP do comando SIPpeers
	 * 
	 * 
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public List<MonitorRamalSip> listRamalSip() throws IOException,
			InterruptedException {
		AsteriskSocket socket = new AsteriskSocket();
		socket.delay(500);

		socket.sendMessage("Action: login\r\nUsername: root\r\nSecret: root\r\n\r\n");

		socket.delay(500);
		List<String> list = socket.receiveMessage();

		socket.sendMessage("Action: reload\r\n\r\n");
		
		//o serviço demora mais de 500ms para dar reload

		socket.delay(1000);

		list = socket.receiveMessage();

		socket.sendMessage("Action: SIPpeers\r\n\r\n");

		socket.delay(500);

		list = socket.receiveMessage();

		ArrayList<MonitorRamalSip> ramaisSip = new ArrayList<MonitorRamalSip>();

		String name = "";
		String host = "";
		String dyn = "";
		String forceport = "";
		String acl = "";
		int port = 0;
		String status = "";
		for (String message : list) {
			String[] messages = message.split(": ");
			if (messages[0].contains("ObjectName")) {
				name = messages[1];
			} else if (messages[0].contains("IPaddress")) {
				host = messages[1];
			} else if (messages[0].contains("IPport")) {
				port = Integer.valueOf(messages[1]);
			} else if (messages[0].contains("Dynamic")) {
				dyn = messages[1];
			} else if (messages[0].contains("Forcerport")) {
				forceport = messages[1];
			} else if (messages[0].contains("ACL")) {
				acl = messages[1];
			} else if (messages[0].contains("Status")) {
				status = messages[1];

			} else if (messages[0].contains("RealtimeDevice")) {
				MonitorRamalSip mr = new MonitorRamalSip(name, host, dyn,
						forceport, acl, port, status);
				ramaisSip.add(mr);
			}
		}

		socket.sendMessage("Action: Logoff\r\n\r\n");
		return ramaisSip;
	}

	/**
	 * Método para buscar a lista de todos os ramais IAX do comando IAXpeers
	 * 
	 * 
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */

	public List<MonitorRamalIAX> listRamalIAX() throws IOException,
			InterruptedException {
		AsteriskSocket socket = new AsteriskSocket();
		socket.delay(500);

		socket.sendMessage("Action: login\r\nUsername: root\r\nSecret: root\r\n\r\n");

		socket.delay(500);
		List<String> list = socket.receiveMessage();

		socket.sendMessage("Action: reload\r\n\r\n");

		socket.delay(1000);

		list = socket.receiveMessage();

		socket.sendMessage("Action: IAXpeers\r\n\r\n");

		socket.delay(500);

		list = socket.receiveMessage();

		ArrayList<MonitorRamalIAX> ramaisIAX = new ArrayList<MonitorRamalIAX>();

		String name = "";
		String host = "";
		String dyn = "";
		String mask = "255.255.255.255";
		int port = 0;
		String status = "";
		for (String message : list) {
			String[] messages = message.split(": ");
			if (messages[0].contains("ObjectName")) {
				name = messages[1];
			} else if (messages[0].contains("IPaddress")) {
				host = messages[1];
			} else if (messages[0].contains("IPport")) {
				port = Integer.valueOf(messages[1]);
			} else if (messages[0].contains("Dynamic")) {
				dyn = messages[1];
			} else if (messages[0].contains("Mask")) {
				mask = messages[1];
			} else if (messages[0].contains("Status")) {
				status = messages[1];
				MonitorRamalIAX mr = new MonitorRamalIAX(name, host, dyn, mask,
						port, status);
				ramaisIAX.add(mr);
			} else if (messages[0].contains("RealtimeDevice")) {

			}
		}

		socket.sendMessage("Action: Logoff\r\n\r\n");
		return ramaisIAX;
	}

}
