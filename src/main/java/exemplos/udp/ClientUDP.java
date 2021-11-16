package exemplos.udp;

import java.io.IOException;
import java.net.*;

public class ClientUDP {
	public static void main(String[] args) throws IOException {
		// configura o nome do servidor e o conteúdo da mensagem 
		String servidorIP = "localhost";
		String msg = "HELLO, TUDO BEM!";
		DatagramSocket aSocket = null;
	 
		// cria um socket para o processo cliente ligando-o a uma porta disponível
		aSocket = new DatagramSocket();
		byte [] m = msg.getBytes();
		
		// Conversão do nome DNS para um endereço Internet 
		InetAddress aHost = InetAddress.getByName(servidorIP);
		int serverPort = 6789;
		
		// criar o datagrama para envio
		DatagramPacket request = new DatagramPacket(m, 
				msg.length(), 
				aHost, serverPort);
		// envia a mensagem
		aSocket.send(request);	
	 	
		// prepara o cliente para receber resposta do servidor
		byte[] buffer = new byte[1000];
		DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
		// recebe resposta
		aSocket.receive(reply);
		System.out.println("Reply: " + new String( reply.getData()));
		aSocket.close();  // fecha a conexao 
	}
}
