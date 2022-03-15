package pss.trabalhofinal.bancodeimagens.presenter;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import pss.trabalhofinal.bancodeimagens.decorator.ImagemComponente;
import pss.trabalhofinal.bancodeimagens.decorator.ImagemDecorator;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.AplicarFiltroView;

public class AplicarFiltroPresenter extends ImagemDecorator implements IObservable {

    private AplicarFiltroView view;
    private ArrayList<IObserver> observers;

    public AplicarFiltroPresenter(ImagemComponente imagemComponente, JDesktopPane desktop) throws InterruptedException, IOException {
        super(imagemComponente);
        //this.imagem = imagemComponente.getImagem();
        view = new AplicarFiltroView();
        //view.getLblImagem().setIcon(new ImageIcon(imagem));

        view.getCkbImgAzul().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgVerde().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgVermelho().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgEspelhada().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgRotacionar().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgNegativo().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgSepia().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgPixelar().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgCinza().addActionListener((ActionEvent ae) -> {

        });
        view.getCkbImgBrilho().addActionListener((ActionEvent ae) -> {

        });
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    @Override
    public BufferedImage getImagem() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ImagemComponente reverter() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void registerObserver(IObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Object obj) {
        observers.forEach(o -> {
            o.update(obj);
        });
    }

}
