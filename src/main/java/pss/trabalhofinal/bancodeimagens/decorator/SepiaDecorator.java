package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.IOException;

//Adaptado de https://www.dyclassroom.com/image-processing-project/how-to-convert-a-color-image-into-sepia-image
public class SepiaDecorator extends ImagemDecorator {

    private BufferedImage img;

    public SepiaDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return converterSepia();
        }
        return img;
    }

    private BufferedImage converterSepia() throws IOException {
        imagem = elementoDecorado.getImagem();

        int altura = imagem.getHeight();
        int largura = imagem.getWidth();

        BufferedImage novaImagem = new BufferedImage(largura, altura,
                BufferedImage.TYPE_INT_ARGB);

        //convert to sepia
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int p = imagem.getRGB(x, y);

                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //calculate tr, tg, tb
                int tr = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int tg = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int tb = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                //check condition
                if (tr > 255) {
                    r = 255;
                } else {
                    r = tr;
                }

                if (tg > 255) {
                    g = 255;
                } else {
                    g = tg;
                }

                if (tb > 255) {
                    b = 255;
                } else {
                    b = tb;
                }

                //set new RGB value
                p = (a << 24) | (r << 16) | (g << 8) | b;

                novaImagem.setRGB(x, y, p);
            }
        }
        img = novaImagem;

        return novaImagem;
    }
}
