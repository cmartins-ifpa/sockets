/*
 * TCPClienteOBJ.java
 * Criado em 08-09-2016
 * Estabelece conexão com o servidor TCP e envia mensagem.
 * O servidor devolve a mensagem enviada e o cliente a imprime.
 * Argumentos: java TCPClienteOBJ <IP servidor> <porta> "msg a ser enviada"
 */
package exemplos.tcp.ex1simples;
import java.net.*;
import java.io.*;

public class TCPCliente {
    public static void main(String args[]) {
    	// verifica se os argumentos foram passados (configura o servidor)
    	if (args==null || args.length==0){
    		System.out.println("Configurando o cliente.");
    		String  argsT[] = {"localhost", "6789", "Hello, Server!"};
    		args = argsT;
    	}
    		
        Socket s = null;
        try {
            s = new Socket(args[0], Integer.parseInt(args[1])); // conecta o socket aa porta remota
            DataInputStream ent = new DataInputStream(s.getInputStream());
            DataOutputStream sai = new DataOutputStream(s.getOutputStream());
            sai.writeUTF(args[2]);
            // le buffer de entrada
            String recebido = ent.readUTF();
            System.out.println("*** Recebido do servidor: " + recebido);
         } catch (UnknownHostException e) {
             System.out.println("!!! Servidor desconhecido: " + e.getMessage());
         } catch (EOFException e) {
             System.out.println("!!! Nao ha mais dados de entrada: " + e.getMessage());
         } catch (IOException e) {
             System.out.println("!!! E/S: " + e.getMessage());
         } finally {
             if (s!=null)
                 try {
                     s.close();
                 } catch (IOException e){
                    System.out.println("!!! Encerramento do socket falhou: " + e.getMessage());
                 }
         }
    }   

}
