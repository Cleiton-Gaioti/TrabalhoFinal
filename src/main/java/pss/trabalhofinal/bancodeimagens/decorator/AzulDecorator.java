package pss.trabalhofinal.bancodeimagens.decorator;

//Adaptado de https://www.geeksforgeeks.org/image-processing-java-set-5-colored-red-green-blue-image-conversion/
public class AzulDecorator extends RGBDecorator {

    public AzulDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public void alteraCor() {
        a = (rgb >> 24) & 0xff;
        blue = rgb & 0xff;

        // set new RGB 
        // keeping the b value same as in original 
        // image and setting r and g as 0. 
        rgb = (a << 24) | blue;
    }
}
