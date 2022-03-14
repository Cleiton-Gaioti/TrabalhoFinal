package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;

//Adaptado de https://pt.stackoverflow.com/questions/3777/rota%C3%A7%C3%A3o-de-imagem-em-java?rq=1
public class RotacionaDecorator extends ImagemDecorator {

    private int angulo;

    public RotacionaDecorator(ImagemComponente elementoDecorado, int angulo) throws InterruptedException {
        super(elementoDecorado);
        this.angulo = angulo;

    }

    @Override
    public BufferedImage getImagem() throws IOException {
        return inverte();
    }

    private BufferedImage inverte() throws IOException {

        BufferedImage novaImagem = elementoDecorado.getImagem();
        int altura = novaImagem.getHeight();
        int largura = novaImagem.getWidth();

        angulo %= 360;
        if (angulo < 0) {
            angulo += 360;
        }

        AffineTransform tx = new AffineTransform();
        tx.rotate(Math.toRadians(angulo), largura / 2.0, altura / 2.0);

        double ytrans = 0;
        double xtrans = 0;
        if (angulo <= 90) {
            xtrans = tx.transform(new Point2D.Double(0, altura), null).getX();
            ytrans = tx.transform(new Point2D.Double(0.0, 0.0), null).getY();
        } else if (angulo <= 180) {
            xtrans = tx.transform(new Point2D.Double(largura, altura), null).getX();
            ytrans = tx.transform(new Point2D.Double(0, altura), null).getY();
        } else if (angulo <= 270) {
            xtrans = tx.transform(new Point2D.Double(largura, 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(largura, altura), null).getY();
        } else {
            xtrans = tx.transform(new Point2D.Double(0, 0), null).getX();
            ytrans = tx.transform(new Point2D.Double(largura, 0), null).getY();
        }

        AffineTransform translationTransform = new AffineTransform();
        translationTransform.translate(-xtrans, -ytrans);
        tx.preConcatenate(translationTransform);

        return new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR).filter(novaImagem, null);
    }

}
