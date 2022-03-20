package pss.trabalhofinal.bancodeimagens.factory;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class ImageConverter {

    public static void convertImg(String inputPath, String outputPath) throws IOException {

        File input = new File(inputPath);
        File output = new File(outputPath);

        BufferedImage image = ImageIO.read(input);
        BufferedImage result = new BufferedImage(
                image.getWidth(),
                image.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        result.createGraphics().drawImage(image, 0, 0, Color.white, null);
        ImageIO.write(result, "jpg", output);

    }
}