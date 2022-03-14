package pss.trabalhofinal.bancodeimagens.decorator;

public abstract class ImagemDecorator extends ImagemComponente {

    protected ImagemComponente elementoDecorado;

    public ImagemDecorator(ImagemComponente elementoDecorado) throws InterruptedException {
        Thread.sleep(100);
        this.elementoDecorado = elementoDecorado;
    }

    @Override
    public ImagemComponente reverter() {
        return elementoDecorado;
    }
}
