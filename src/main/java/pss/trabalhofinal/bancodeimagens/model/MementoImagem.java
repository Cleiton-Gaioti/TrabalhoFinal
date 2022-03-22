package pss.trabalhofinal.bancodeimagens.model;

import com.pss.imagem.processamento.decorator.ImagemComponente;

public class MementoImagem {

    private ImagemComponente imagem;

    public MementoImagem(ImagemComponente imagem) {
        setImagem(imagem);
    }

    public ImagemComponente getImagem() {
        return imagem;
    }

    public void setImagem(ImagemComponente imagem) {
        this.imagem = imagem;
    }

}
