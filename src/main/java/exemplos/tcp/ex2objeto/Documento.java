package exemplos.tcp.ex2objeto;
import java.io.*;
public class Documento implements Serializable  {
    private String autor;
    private String tit;
    private int numAcessos =1;
    
    public Documento(String tit, String autor) {
        this.autor = autor;
        this.tit = tit;
        this.numAcessos = this.numAcessos + 1;
    }
    public int incrNumAcessos() {
        return (numAcessos++);
    }
    public String toString() {
        return tit+" de "+autor+", acessado "+numAcessos+" vezes.";
    }
}
