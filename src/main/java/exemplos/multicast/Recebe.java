package exemplos.multicast;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketException;
import java.net.SocketTimeoutException;


class Recebe implements Runnable {
    private Conferencia c;
    Recebe(Conferencia conf) {
        c = conf;
    }
    public void run() {
        // Recebe mensagens
        while (true) {
            byte[] buf = new byte[256];
            DatagramPacket recebido = new DatagramPacket(buf, buf.length);
            try {
                c.s.setSoTimeout(60000); // se não receber nada em 60s, encerra o programa
            } catch (SocketException e) {
                System.out.println("!!!Recebe: " + e.getMessage());
            }
            try {
                c.s.receive(recebido);
            } catch (SocketTimeoutException e) {
                System.out.println("\n!!!INATIVIDADE... encerrado por timeout");
                System.exit(0);                // break;
            } catch (IOException e) {
                System.out.println("!!!Recebe: " + e.getMessage());
            } 
            String str = new String(recebido.getData());
            System.out.println("(" + recebido.getAddress().getHostAddress() +
                    ":" + recebido.getPort() + ") << " + str.trim());     
        }
    }
}
