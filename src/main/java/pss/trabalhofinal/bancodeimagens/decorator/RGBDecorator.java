package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.IOException;

//Adaptado de https://www.geeksforgeeks.org/image-processing-java-set-5-colored-red-green-blue-image-conversion/
public abstract class RGBDecorator extends ImagemDecorator {

    private BufferedImage img;
    protected int rgb;
    protected int a;
    protected int red;
    protected int green;
    protected int blue;

    public RGBDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return colorir();
        }
        return img;
    }

    protected final BufferedImage colorir() throws IOException {

        //https://www.geeksforgeeks.org/image-processing-java-set-8-creating-mirror-image/
        imagem = elementoDecorado.getImagem();

        int altura = imagem.getHeight();
        int largura = imagem.getWidth();

        BufferedImage novaImagem = new BufferedImage(largura, altura,
                BufferedImage.TYPE_INT_ARGB);

        // convert to blue image 
        for (int y = 0; y < altura; y++) {
            for (int x = 0; x < largura; x++) {

                this.rgb = imagem.getRGB(x, y);

                alteraCor();

                novaImagem.setRGB(x, y, this.rgb);
            }
        }

        img = novaImagem;

        return novaImagem;
    }

    public abstract void alteraCor();
}
