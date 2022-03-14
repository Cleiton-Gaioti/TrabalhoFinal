package pss.trabalhofinal.bancodeimagens.decorator;

//Adaptado de https://www.geeksforgeeks.org/image-processing-java-set-5-colored-red-green-blue-image-conversion/
public class VermelhoDecorator extends RGBDecorator {

    public VermelhoDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public void alteraCor() {
        a = (rgb >> 24) & 0xff;
        blue = rgb & 0xff;
        red = (rgb >> 16) & 0xff;

        this.rgb = (a << 24) | (red << 16);
    }
}
