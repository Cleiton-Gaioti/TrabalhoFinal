package pss.trabalhofinal.bancodeimagens.model;

import java.util.Stack;

public class Zelador {

    private final Stack<MementoImagem> elementos;
    private static Zelador instancia;

    private Zelador() {
        this.elementos = new Stack<>();
    }

    public static Zelador getInstancia() {
        if (instancia == null) {
            instancia = new Zelador();
        }
        return instancia;
    }

    public void add(MementoImagem memento) {
        this.elementos.push(memento);
    }

    public MementoImagem getUltimo() {
        if (!elementos.isEmpty()) {
            return elementos.pop();
        }
        return null;
    }

}
