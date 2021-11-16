package exemplos.socket.fileTransfer;
/*
 * TCPServidorFTP.java
 *
 * Servidor de arquivo texto. Envia um arquivo texto (teste.txt) para os clientes que
 * se conectam. Os clientes devem passar um ID que será utilizado nas mensagens
 * impressas pelo servidor na sua console.
 */
import java.net.*;
import java.io.*;
public class TCPServidorFTP {
    public static void main(String args[]) {
        try {
            int porta = 6789; // porta do serviço
            if (args.length > 0) porta = Integer.parseInt(args[0]);
            ServerSocket escuta = new ServerSocket(porta);
            System.out.println("*** Servidor ***");
            System.out.println("*** Inicio - porta de escuta (listen): " + porta);
            while (true) {
                // accept bloqueia ateh que chegue um pedido de conexao de um cliente
                // qdo chega, cria um novo socket = cliente
                Socket cliente = escuta.accept();
                System.out.println("*** Socket de escuta (listen): " + escuta.getLocalSocketAddress().toString());
                System.out.println("*** Conexao aceita de (remoto): " + cliente.getRemoteSocketAddress().toString());
                // quando chega, cria nova thread para atender em especial o cliente
                Conexao c = new Conexao(cliente);
            }
        } catch (IOException e) {
            System.out.println("Erro na escuta: " + e.getMessage());
        }
    }
    
}

class Conexao extends Thread {
    private static final String FILENAME ="c:/tmp/arqtesteUTF.txt";
    		// "c:/tmp/arqteste.txt";
    
	protected BufferedReader arq = null;
    DataInputStream ent;
    DataOutputStream sai;
    Socket cliente;
    String idCliente = null;
    
    public Conexao(Socket s) {
        try {
            cliente = s;
            ent = new DataInputStream(cliente.getInputStream());
            sai = new DataOutputStream(cliente.getOutputStream());
            idCliente = ent.readUTF();
            this.start();
        } catch (IOException e) {
            System.out.println("Erro IO Conexao: " + e.getMessage());
        }
    }
    
    public void enviarMsg(String msg) {
        try {
            sai.writeUTF(msg);
        } catch (IOException e1) {
            System.out.println("Erro de escrita no buffer da conexao ("+idCliente+")");
        }
    }
    public void run() {
        try {
            arq = new BufferedReader(new FileReader(FILENAME));
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo nao econtrado: " + FILENAME);
            enviarMsg("!!! Erro ao tentar abrir arquivo teste.txt"+e.getMessage());
        }
        
        if (arq != null) {
            try {
                String l = arq.readLine();
                while (l != null) {
                    enviarMsg(l);
                    l = arq.readLine();
                }
            } catch (IOException e) {
                System.err.println("Erro ao ler linha do arquivo "
                	+ FILENAME	+ " ("+idCliente+")");
                enviarMsg("!!! Erro ao ler arquivo "
                		+ FILENAME +" - "+ e.getMessage());
            }
            try {
                arq.close();
            } catch (IOException e) {
                System.out.println("Erro fechamento do arquivo "
                		+ FILENAME	+ " ("+idCliente+")");
            }
            try {
                cliente.close();
            } catch (IOException e) {
                System.out.println("Erro fechamento do socket cliente ("+idCliente+")");
            }
            System.out.println("*** Conexao encerrada com "+idCliente + "\n");
        }
    }
}