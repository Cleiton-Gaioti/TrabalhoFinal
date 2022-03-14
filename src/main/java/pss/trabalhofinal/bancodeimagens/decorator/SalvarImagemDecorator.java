package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SalvarImagemDecorator extends ImagemDecorator {

    private String nome;

    public SalvarImagemDecorator(ImagemComponente elementoDecorado, String nome) throws InterruptedException, IOException {
        super(elementoDecorado);
        this.nome = nome;
        salvarImagem();
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        return salvarImagem();
    }

    private BufferedImage salvarImagem() throws IOException {

        imagem = elementoDecorado.getImagem();

        String caminho = new File("src/main/resources/").getAbsolutePath();
        File arquivo = new File(caminho + "\\" + this.nome);

        ImageIO.write(imagem, "jpg", arquivo);

        return imagem;

    }

    @Override
    public ImagemComponente reverter() {
        throw new IllegalArgumentException("A imagem foi salva, nao e possivel reverter");
    }

}
