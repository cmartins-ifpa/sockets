/*
 * TCPServidorOBJ.java
 * Criado em 08/09/2016
 *
 * Baseado em Servidor ECHO (Coulouris, 2008) 
 * Passar número da porta como argumento (opcional)
 * Ex. java -jar TCPServidorOBJ <porta> ou
 *     java -jar TCPServidorOBJ
 */
package exemplos.tcp.ex1simples;

import java.net.*;
import java.io.*;

public class TCPServidor {
	public static void main(String args[]) {
		// verifica se os argumentos foram passados (configura o servidor)
		if (args == null || args.length > 0) {
			String argsT[] = { "localhost", "6789" };
			args = argsT;
		}

		try {
			int porta = 6789; // porta do serviço
			if (args.length > 0)
				porta = Integer.parseInt(args[0]);
			ServerSocket escuta = new ServerSocket(porta);
			System.out.println("*** Servidor ***");
			System.out.println("*** Porta de escuta (listen): " + porta);
			while (true) {
				// accept bloqueia ateh que chegue um pedido de conexao de um
				// cliente
				Socket cliente = escuta.accept();
				System.out.println("*** conexao aceita de (remoto): " + cliente.getRemoteSocketAddress() );
				// quando chega, cria nova thread para atender em especial o
				// cliente
				Conexao c = new Conexao(cliente);
			}
		} catch (IOException e) {
			System.out.println("Erro na escuta: " + e.getMessage());
		}

	}
}

class Conexao extends Thread {
	DataInputStream ent;
	DataOutputStream sai;
	Socket cliente;

	public Conexao(Socket s) {
		try {
			cliente = s;
			ent = new DataInputStream(cliente.getInputStream());
			sai = new DataOutputStream( cliente.getOutputStream() ); 
			this.start();
		} catch (IOException e) {
			System.out.println("Erro IO Conexao: " + e.getMessage());
		}
	}

	public void run() {
		try {
			String recebido = ent.readUTF();
			sai.writeUTF(recebido.toUpperCase()+" * OK * ");
		} catch (EOFException e) {
			System.out.println("Conexao: EOFException " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Conexao: IOException " + e.getMessage());
		} finally {
			try {  cliente.close();
			} catch (IOException e) {
				System.out.println("Conexao: erro close do socket");
			}
		}
	}

}
