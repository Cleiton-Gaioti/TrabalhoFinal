package pss.trabalhofinal.bancodeimagens.model;

import com.pss.imagem.processamento.decorator.Imagem;
import java.io.IOException;

public class Image extends Imagem {

    private String path;

    public Image(String caminhoArquivo) throws IOException, InterruptedException {
        super(caminhoArquivo);
        this.path = caminhoArquivo;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
