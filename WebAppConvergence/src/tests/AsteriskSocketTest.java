package tests;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import connection.AsteriskSocket;

public class AsteriskSocketTest {

	@Test
	public void initialAsteriskSocketTest() {
		try {
			AsteriskSocket socket = new AsteriskSocket();
			
			List<String> list = socket.receiveMessage();
			for(String message : list){
				System.out.println(message);
			}
			
			socket.delay(500);
			
			socket.sendMessage("Action: login\r\nUsername: root\r\nSecret: root\r\n\r\n");
			
			socket.delay(500);
			
			list = socket.receiveMessage();
			for(String message : list){
				System.out.println(message);
			}
			
			socket.sendMessage("Action: ping\r\n\r\n");
			
			socket.delay(500);
			
			list = socket.receiveMessage();
			for(String message : list){
				System.out.println(message);
			}
			
			socket.sendMessage("Action: ListCommands\r\n\r\n");
			
			socket.delay(500);
			
			list = socket.receiveMessage();
			for(String message : list){
				System.out.println(message);
			}
			
			socket.sendMessage("Action: Logoff\r\n\r\n");
			
			socket.delay(500);
			
			list = socket.receiveMessage();
			for(String message : list){
				System.out.println(message);
			}
				
			Assert.assertEquals(true, list != null && !list.isEmpty());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
