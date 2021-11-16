/*
 * TCPServidorOBJ.java
 * Criado em 08/09/2016
 * Recebe um objeto da classe documento.
 * Ex. java TCPServidorOBJ <porta> ou
 *     java TCPServidorOBJ
 */
package exemplos.tcp.ex2objeto;
import java.net.*;
import java.io.*;
public class TCPServidorOBJ {
	public static void main(String args[]) throws IOException, ClassNotFoundException {
		// verifica se os argumentos foram passados (configura o servidor)
		if (args == null || args.length > 0) {
			String argsT[] = { "localhost", "6789" };
			args = argsT;
		}
		
		int porta = 6789; // porta do serviço
        if (args.length > 0) {
            porta = Integer.parseInt(args[0]);
        }
        ServerSocket escuta = new ServerSocket(porta);
        System.out.println("*** Servidor ***");
        System.out.println("*** Porta de escuta (listen): " + porta);
        while (true) {
            // accept bloqueia ateh que chegue um pedido de conexao de um cliente
            Socket cliente = escuta.accept();
            System.out.println("*** conexao aceita de (remoto): " + cliente.getRemoteSocketAddress());
            // quando chega, cria nova thread para atender em especial o cliente
            ObjectInputStream ois = new ObjectInputStream(cliente.getInputStream());
            while (true) {
                try {
                    Documento doc = (Documento) ois.readObject();
                    System.out.println(doc.toString());
                } catch (IOException e) {
                    break;
                }
            }
        }

 	}
}
