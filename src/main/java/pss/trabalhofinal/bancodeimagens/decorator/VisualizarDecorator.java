package pss.trabalhofinal.bancodeimagens.decorator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.ImageIcon;

public class VisualizarDecorator extends ImagemDecorator {

    private VisualizarImagemView view;

    public VisualizarDecorator(ImagemComponente imagemComponente) throws InterruptedException, IOException {
        super(imagemComponente);
        imagem = imagemComponente.getImagem();

        view = new VisualizarImagemView();

        view.getLblImagem().setIcon(new ImageIcon(imagem));
        view.getBtnFechar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.dispose();
            }
        });
        view.pack();
        view.repaint();
        view.setVisible(true);
    }

    @Override
    public BufferedImage getImagem() {
        return imagem;
    }

    @Override
    public ImagemComponente reverter() {
        throw new IllegalArgumentException("A imagem foi exibida, nao e possivel reverter");
    }

}
