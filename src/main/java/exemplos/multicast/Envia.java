package exemplos.multicast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;

class Envia implements Runnable {
    private Conferencia c;
    
    Envia(Conferencia conf) {
        c = conf;
    }
    private String obterMensagem() {
        BufferedReader rdr = new BufferedReader(new InputStreamReader(System.in));
        String s="";
        //System.out.println("Tecle mensagem ou enter=fim? ");
        try {
            System.out.print(">");
            s = new String(rdr.readLine());
        } catch (IOException e) {
            System.out.println(c.nome + ": estou com problemas :-/");
        }
        if (s.length()>0)
            return(c.nome + ": " + s);
        else
            return s;
    }
    
    public void run() {
        // envia saudação
        String msg = new String(c.nome + ": Ola a todos, cheguei!!!");
        DatagramPacket dtgrm = new DatagramPacket(msg.getBytes(), msg.length(), c.ipGrupo, c.porta);
        try {
            c.s.send(dtgrm);
        } catch (IOException e) {
            System.out.println("!!!Envia: " + e.getMessage());
        }
        // envia mensagem lida do teclado
        msg = new String(obterMensagem());
        while (msg.length() > 0) {
            dtgrm = new DatagramPacket(msg.getBytes(), msg.length(), c.ipGrupo, c.porta);
            try {
                c.s.send(dtgrm);
            } catch (IOException e) {
                System.out.println("!!!Envia: " + e.getMessage());
            }
            msg = obterMensagem();
        }
    }
}
