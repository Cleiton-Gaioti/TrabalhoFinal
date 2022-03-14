package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.awt.Color;
import java.io.IOException;

public class BrilhoDecorator extends ImagemDecorator {

    private BufferedImage img;
    private final int escala;

    public BrilhoDecorator(ImagemComponente elementoDecorado, int escala) throws InterruptedException {
        super(elementoDecorado);
        this.escala = escala;
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return aplicarBrilho();
        }
        return img;
    }

    private BufferedImage aplicarBrilho() throws IOException {
        int altura;
        int largura;
        int i;
        int j;
        this.imagem = elementoDecorado.getImagem();

        altura = imagem.getHeight();
        largura = imagem.getWidth();

        BufferedImage novaImagem = new BufferedImage(largura, altura,
                BufferedImage.TYPE_INT_ARGB);

        for (i = 0; i < altura; i++) {
            for (j = 0; j < largura; j++) {
                Color c = new Color(this.imagem.getRGB(j, i));
                int r = c.getRed();
                int b = c.getBlue();
                int g = c.getGreen();

                int nr = (int) (r + 15.5 * escala);
                int nb = (int) (b + 15.5 * escala);
                int ng = (int) (g + 15.5 * escala);

                nr = Math.min(nr, 255);
                ng = Math.min(ng, 255);
                nb = Math.min(nb, 255);

                Color nc = new Color(nr, ng, nb);
                novaImagem.setRGB(j, i, nc.getRGB());
            }
        }
        img = novaImagem;

        return novaImagem;
    }
}
