package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.IOException;

//Adaptado de https://www.dyclassroom.com/image-processing-project/how-to-convert-a-color-image-into-negative
public class NegativaDecorator extends ImagemDecorator {

    private BufferedImage img;

    public NegativaDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return converterNegativo();
        }
        return img;
    }

    private BufferedImage converterNegativo() throws IOException {
        imagem = elementoDecorado.getImagem();

        int altura = imagem.getHeight();
        int largura = imagem.getWidth();

        BufferedImage novaImagem = new BufferedImage(largura, altura,
                BufferedImage.TYPE_INT_ARGB);

        // Convert to negative 
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {
                int p = imagem.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (p >> 8) & 0xff;
                int b = p & 0xff;

                //subtract RGB from 255 
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                //set new RGB value 
                p = (a << 24) | (r << 16) | (g << 8) | b;
                novaImagem.setRGB(x, y, p);
            }
        }
        img = novaImagem;

        return novaImagem;
    }
}
