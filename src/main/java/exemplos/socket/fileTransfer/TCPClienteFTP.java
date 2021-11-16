package exemplos.socket.fileTransfer;
/*
* Ex. java TCPClienteFTP 127.0.0.1 6789 NOMECLIENTE
* Recebe o arquivo teste.txt. Para pedi-lo executar o cliente:
* java TCPClienteFTP <IP servidor> <porta> <id cliente>
*/

import java.net.*;
import java.io.*;
public class TCPClienteFTP {
   public static void main(String args[]) {
       Socket s = null;
       String server = "localhost" ;   // args[0]
       String porta = "6789"; // args[1]
       String nomeClient = "MANUEL"; // args[2]
       try {
           s = new Socket(server, Integer.parseInt(porta)); // conecta o socket aa porta remota
           DataInputStream  ent = new DataInputStream(s.getInputStream());
           DataOutputStream sai = new DataOutputStream(s.getOutputStream());
           sai.writeUTF(nomeClient);
           String recebido = ent.readUTF();
           while (recebido != null) {
               System.out.println(recebido);
               recebido = ent.readUTF();
           }
       } catch (UnknownHostException e) {
           System.out.println("Servidor desconhecido: " + e.getMessage());
       } catch (EOFException e) {
           System.out.println("--- FIM DA TRANSFERENCIA ---");
       } catch (IOException e) {
           System.out.println("E/S: " + e.getMessage());
       } finally {
           if (s!=null)
               try {
                   s.close();
               } catch (IOException e){
                   System.out.println("Encerramento do socket falhou: " + e.getMessage());
               }
       }
   }
   
}
