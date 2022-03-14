package pss.trabalhofinal.bancodeimagens.decorator;

//Adaptado de https://www.geeksforgeeks.org/image-processing-java-set-5-colored-red-green-blue-image-conversion/
public class VerdeDecorator extends RGBDecorator {

    public VerdeDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        super(elementoDecorado);
    }

    @Override
    public void alteraCor() {
        a = (rgb >> 24) & 0xff;
        green = (rgb >> 8) & 0xff;

        rgb = (a << 24) | (green << 8);
    }
}
