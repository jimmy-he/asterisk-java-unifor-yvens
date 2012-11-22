package asterisk;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import connection.AsteriskSocket;

/**
 * Classe responsável por recarregar o serviço Asterisk
 * 
 * @author daniel
 * 
 */

public class ReloadHandler {

	// Método para dar reload
	public void reload() {
		AsteriskSocket socket;
		try {
			socket = new AsteriskSocket();
			socket.delay(500);

			socket.sendMessage("Action: login\r\nUsername: root\r\nSecret: root\r\n\r\n");

			socket.delay(500);
			List<String> list = socket.receiveMessage();

			socket.sendMessage("Action: reload\r\n\r\n");

			socket.delay(1000);

			list = socket.receiveMessage();

			socket.sendMessage("Action: Logoff\r\n\r\n");
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
