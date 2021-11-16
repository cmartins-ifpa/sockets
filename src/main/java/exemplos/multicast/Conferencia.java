package exemplos.multicast;

import java.net.*;
import java.io.*;

/**
 * Conferencia de grupo, varias pessoas unem-se ao grupo para trocar mensagens
 * args[1] endereço de multicast.  Ex: 224.225.226.227
 * args[2] porta                   Ex: 6868
 * args[3] identificador da pessoa Ex: Oliva
 **/

public class Conferencia {
    int porta = 6789;
    InetAddress ipGrupo;
    String nome;
    MulticastSocket s=null;
    
    /*
     * Método Construtor 
     */
    public Conferencia(String args[]) {
        try {
            porta = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("!!! Numero de porta invalido. Assume porta padrao: " + porta);
        }
        // junta-se a um grupo de Multicast e envia saudacoes
        nome = new String(args[2]);
        
        try {
            ipGrupo = InetAddress.getByName(args[0]);
            s = new MulticastSocket(porta);
            s.joinGroup(ipGrupo);
        } catch (SocketException e) {
            System.out.println("Falha! " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Falha! " + e.getMessage());
        }
    }
        
    private void deixarGrupo() {
        try {
            s.leaveGroup(ipGrupo);
            if (s!=null) s.close();
        } catch (IOException e) {
            System.out.println("!!!deixarGrupo: " + e.getMessage());
        }
    }
    
    public static void main(String args[]) {
        if (args == null || args.length < 3) {
            System.out.println("!!!Fornecendo argumentos: <IP grupo multicast> <porta> <seu nome>");
           // System.exit(1);
           	String  argsT[] = {"231.232.233.234", "6789", "USER_anonimo"};
        	args = argsT;
        }
        System.out.println("Args: " + args[0] + " " + args[1] + " " + args[2]);
        Conferencia c = new Conferencia(args);
        Thread envia = new Thread(new Envia(c));
        Thread recebe = new Thread(new Recebe(c));
        envia.start();
        recebe.start();
        try {
            // O método join() aguarda a finalização da thread (tarefa)
            envia.join();
            recebe.join();
        } catch (InterruptedException e) {
            System.out.println("!Falha no Join: " + e.getMessage());
        } finally {
            c.deixarGrupo();
        }
    }
}