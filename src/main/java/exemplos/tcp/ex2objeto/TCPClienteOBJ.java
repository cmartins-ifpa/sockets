/*
 * TCPClienteOBJ.java
 * Criado em 08-09-2016
 * Estabelece conexão com o servidor e envia um objeto da classe documento.
 *  
 * Argumentos: java TCPClienteOBJ <IP servidor> <porta>
 */
package exemplos.tcp.ex2objeto;
import java.net.*;
import java.io.*;

public class TCPClienteOBJ {
    public static void main(String args[]) {
    	// verifica se os argumentos foram passados (configura o servidor)
    	if (args==null || args.length==0){
    		System.out.println("Configurando o cliente.");
    		String  argsT[] = {"localhost", "6789"};
    		args = argsT;
    	}
        Socket s = null;
        try {
            s = new Socket("localhost", 6789); // conecta o socket aa porta remota
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());   
            Documento d1 = new Documento("Divina Comedia", "Dante");
            oos.writeObject(d1); 
            Documento d2 = new Documento("Dom Casmurro", "M. de Assis");   
            oos.writeObject(d2);  
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
