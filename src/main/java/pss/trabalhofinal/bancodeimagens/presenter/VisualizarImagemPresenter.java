package pss.trabalhofinal.bancodeimagens.presenter;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.VisualizarImagemView;

public class VisualizarImagemPresenter implements IObservable {

    private VisualizarImagemView view;
    private List<IObserver> observers;
    private Image imagem;

    public VisualizarImagemPresenter(Image imagem, JDesktopPane desktop) {
        view = new VisualizarImagemView();
        observers = new ArrayList<>();
        this.imagem = imagem;

        try {
            view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir imagem! " + e.getMessage());
        }

        view.getBtnFechar().addActionListener(ae -> {
            view.dispose();
        });

        view.getBtnCompartilhar().addActionListener(ae -> {

        });

        view.getBtnEditar().addActionListener(ae -> {
            new AplicarFiltroPresenter(this.imagem, desktop);
        });

        view.getBtnVisualizarHistorico().addActionListener(ae -> {

        });

        view.getBtnFechar().addActionListener(ae -> {

        });

        desktop.add(view);
        view.setVisible(true);
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
