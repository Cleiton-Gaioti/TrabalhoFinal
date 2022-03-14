package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.IOException;

public abstract class ImagemComponente {

    protected BufferedImage imagem;

    public abstract BufferedImage getImagem() throws IOException;

    public abstract ImagemComponente reverter();

    public final void visualizar() throws InterruptedException, IOException {
        VisualizarDecorator visualizador = new VisualizarDecorator(this);
    }
}
