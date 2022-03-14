package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.IOException;

//Adaptado de //https://www.geeksforgeeks.org/image-processing-java-set-8-creating-mirror-image/
public class EspelhadaDecorator extends ImagemDecorator {

    private BufferedImage img;

    public EspelhadaDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return espelhar();
        }
        return img;
    }

    private BufferedImage espelhar() throws IOException {
        int altura;
        int largura;

        imagem = elementoDecorado.getImagem();

        altura = imagem.getHeight();
        largura = imagem.getWidth();

        BufferedImage novaImagem = new BufferedImage(largura, altura,
                BufferedImage.TYPE_INT_ARGB);

        // Create mirror image pixel by pixel 
        for (int y = 0; y < altura; y++) {
            for (int lx = 0, rx = largura - 1; lx < largura; lx++, rx--) {
                // lx starts from the left side of the image 
                // rx starts from the right side of the image 
                // lx is used since we are getting pixel from left side 
                // rx is used to set from right side 
                // get source pixel value 
                int p = imagem.getRGB(lx, y);

                // set mirror image pixel value 
                novaImagem.setRGB(rx, y, p);
            }
        }
        img = novaImagem;

        return novaImagem;
    }
}
