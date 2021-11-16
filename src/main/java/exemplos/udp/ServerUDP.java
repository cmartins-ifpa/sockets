package exemplos.udp;

import java.io.IOException;
import java.io.PrintStream;
import java.net.*;

import javax.swing.JOptionPane;

public class ServerUDP {
	public static void main(String[] args) throws IOException {
		DatagramSocket aSocket = null;
		// cria um objeto do tipo socket e liga-o na porta 6789
		aSocket = new DatagramSocket(6789);
		System.out.println("Servidor UDP iniciado");
		// buffer de recepção vazio
		byte[] buffer = new byte[1000];
		while (true) {
			// instancia o objecto onde vai receber a msg
			DatagramPacket request = new DatagramPacket(buffer, buffer.length);
			// bloqueia até receber a mensagem
			aSocket.receive(request);
			System.out.println( "RECEIVE=" + new String (request.getData()) );
			String msgString = new String("Msg recebida=") + 
					   new String (request.getData()); 		
			byte[] msg = msgString.getBytes();  // converte String para array de bytes
			// instancia o objeto para enviar a mensagem
			DatagramPacket reply = new DatagramPacket(msg, 
					msgString.length(), request.getAddress(),
					request.getPort());
			// envia a resposta ao cliente
			aSocket.send(reply);
		} // fim do while
	}
}
