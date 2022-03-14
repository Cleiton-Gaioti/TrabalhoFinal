package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.awt.image.WritableRaster;
import java.io.IOException;

//Adaptado de https://stackoverflow.com/questions/15777821/how-can-i-pixelate-a-jpg-with-java
public class PixeladaDecorator extends ImagemDecorator {

    private BufferedImage img;
    private final int tamanhoPixel;

    public PixeladaDecorator(ImagemComponente elementoDecorado, int tamanhoPixel) throws InterruptedException {
        super(elementoDecorado);
        this.tamanhoPixel = tamanhoPixel;
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        if (img == null) {
            return pixelar();
        }
        return img;
    }

    private BufferedImage pixelar() throws IOException {
        this.imagem = elementoDecorado.getImagem();

        Raster src = imagem.getData();

        // Create an identically-sized output raster
        WritableRaster dest = src.createCompatibleWritableRaster();

        // Loop through every PIX_SIZE pixels, in both x and y directions
        for (int y = 0; y < src.getHeight(); y += tamanhoPixel) {
            for (int x = 0; x < src.getWidth(); x += tamanhoPixel) {

                // Copy the pixel
                double[] pixel = new double[3];
                pixel = src.getPixel(x, y, pixel);

                // "Paste" the pixel onto the surrounding PIX_SIZE by PIX_SIZE neighbors
                // Also make sure that our loop never goes outside the bounds of the image
                for (int yd = y; (yd < y + tamanhoPixel) && (yd < dest.getHeight()); yd++) {
                    for (int xd = x; (xd < x + tamanhoPixel) && (xd < dest.getWidth()); xd++) {
                        dest.setPixel(xd, yd, pixel);
                    }
                }
            }
        }

        // Save the raster back to the Image
        BufferedImage novaImagem = new BufferedImage(imagem.getColorModel(), dest, imagem.getColorModel().isAlphaPremultiplied(), null);
        img = novaImagem;

        return novaImagem;
    }
}
