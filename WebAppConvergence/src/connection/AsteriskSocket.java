package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import asterisk.AsteriskConfiguration;

public class AsteriskSocket {

	private String ip;
	private int port;
	
	private Socket socket;
	private BufferedReader input;
	private PrintStream output;
	
	/**
	 * Construtor com os dados padrões para o IP e Porta do servidor asterisk
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public AsteriskSocket() throws UnknownHostException, IOException{
		this(AsteriskConfiguration.ASTERISK_IP, AsteriskConfiguration.ASTERISK_PORT);
	}
	
	/**
	 * Cosntrutor que aceita como entrada o valor do ip e da porta do servidor asterisk
	 * @param ip
	 * @param port
	 * @throws IOException 
	 * @throws UnknownHostException 
	 */
	public AsteriskSocket(String ip, int port) throws UnknownHostException, IOException {
		super();
		this.ip = ip;
		this.port = port;
		
		socket = new Socket(ip, port);
		input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		output = new PrintStream(socket.getOutputStream(), true);
	}
	
	/**
	 * Envia uma mensagem ao servidor
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		output.print(message);
	}
	
	/**
	 * Método para receber mensagens vindas do servidor
	 * 
	 * @return retorna a lista de mensagens vindas do servidor ou nula caso nenhuma mensagem seja recebida
	 * @throws IOException
	 */
	public List<String> receiveMessage() throws IOException{
		List<String> list = new ArrayList<String>();
		
		String message = null;
		while(input.ready()){
			message = input.readLine();
			list.add(message);
		}
		
		return (list.size() > 0) ? list : null;
	}
	
	/**
	 * Método para dar um delay de X milliseconds
	 * @param milliseconds
	 * @throws InterruptedException
	 */
	public void delay(long milliseconds) throws InterruptedException{
		Thread.sleep(milliseconds);
	}
	
	/**
	 * Método para fechar a conexão com o servidor e 
	 * enviar o comando de close para os objetos de input, output
	 * e para o socket
	 * 
	 * @throws IOException
	 */
	public void closeSocket() throws IOException{
		input.close();
		output.close();
		socket.close();
	}
	
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
}
