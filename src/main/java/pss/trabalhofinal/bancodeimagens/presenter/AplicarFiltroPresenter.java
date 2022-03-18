package pss.trabalhofinal.bancodeimagens.presenter;

import com.pss.imagem.processamento.decorator.AzulDecorator;
import com.pss.imagem.processamento.decorator.BrilhoDecorator;
import com.pss.imagem.processamento.decorator.EspelhadaDecorator;
import com.pss.imagem.processamento.decorator.ImagemComponente;
import com.pss.imagem.processamento.decorator.NegativaDecorator;
import com.pss.imagem.processamento.decorator.PixeladaDecorator;
import com.pss.imagem.processamento.decorator.RotacionaDecorator;
import com.pss.imagem.processamento.decorator.SepiaDecorator;
import com.pss.imagem.processamento.decorator.TomDeCinzaDecorator;
import com.pss.imagem.processamento.decorator.VerdeDecorator;
import com.pss.imagem.processamento.decorator.VermelhoDecorator;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.AplicarFiltroView;

public class AplicarFiltroPresenter implements IObservable {

    private AplicarFiltroView view;
    private ArrayList<IObserver> observers;
    private ImagemComponente imagem;

    public AplicarFiltroPresenter(ImagemComponente imagem, JDesktopPane desktop) {
        this.imagem = imagem;
        view = new AplicarFiltroView();

        view.getCmbPredefinicoes().setSelectedIndex(-1);
        try {

            view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        view.getCkbImgAzul().addActionListener((ActionEvent ae) -> {
            azul(view.getCkbImgAzul().isSelected());
        });
        view.getCkbImgVerde().addActionListener((ActionEvent ae) -> {
            verde(view.getCkbImgVerde().isSelected());
        });
        view.getCkbImgVermelho().addActionListener((ActionEvent ae) -> {
            vermelho(view.getCkbImgVermelho().isSelected());
        });
        view.getCkbImgEspelhada().addActionListener((ActionEvent ae) -> {
            espelhada();
        });
        view.getCkbImgRotacionar().addActionListener((ActionEvent ae) -> {
            rotacionar();
        });
        view.getCkbImgNegativo().addActionListener((ActionEvent ae) -> {
            negativo(view.getCkbImgNegativo().isSelected());
        });
        view.getCkbImgSepia().addActionListener((ActionEvent ae) -> {
            sepia(view.getCkbImgSepia().isSelected());
        });
        view.getCkbImgPixelar().addActionListener((ActionEvent ae) -> {
            pixelar();
        });
        view.getCkbImgCinza().addActionListener((ActionEvent ae) -> {
            cinza();
        });
        view.getCkbImgBrilho().addActionListener((ActionEvent ae) -> {
            brilho();
        });
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });
        view.getCmbPredefinicoes().addActionListener((ActionEvent ae) -> {
            predefinicoes();
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

    private void predefinicoes() {
        try {
            String res = view.getCmbPredefinicoes().getSelectedItem().toString();
            if (!res.isEmpty()) {
                if (res.startsWith("1.")) {
                    vermelho(true);
                    sepia(true);
                } else if (res.startsWith("2.")) {
                    verde(true);
                    sepia(true);
                } else if (res.startsWith("3.")) {
                    azul(true);
                    sepia(true);
                } else if (res.startsWith("4.")) {
                    vermelho(true);
                    negativo(true);
                } else if (res.startsWith("5.")) {
                    verde(true);
                    negativo(true);
                } else if (res.startsWith("6.")) {
                    azul(true);
                    negativo(true);
                } else if (res.startsWith("7.")) {
                    negativo(true);
                    sepia(true);
                }

            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void brilho() {
        try {
            if (view.getCkbImgBrilho().isSelected()) {

                int res = Integer.parseInt(view.getTxtBrilho().getText());

                imagem = new BrilhoDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void pixelar() {
        try {
            if (view.getCkbImgPixelar().isSelected()) {

                int res = Integer.parseInt(view.getTxtPixel().getText());

                imagem = new PixeladaDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void rotacionar() {
        try {
            if (view.getCkbImgRotacionar().isSelected()) {

                int res = Integer.parseInt(view.getTxtRotacao().getText());

                imagem = new RotacionaDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void cinza() {
        try {
            if (view.getCkbImgCinza().isSelected()) {
                imagem = new TomDeCinzaDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));

            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void negativo(boolean selecionado) {
        try {
            if (selecionado) {
                imagem = new NegativaDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));

            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void espelhada() {
        try {
            if (view.getCkbImgEspelhada().isSelected()) {
                imagem = new EspelhadaDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));

            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void sepia(boolean selecionado) {
        try {
            if (selecionado) {
                imagem = new SepiaDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void azul(boolean selecionado) {
        try {
            if (selecionado) {
                imagem = new AzulDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void vermelho(boolean selecionado) {
        try {
            if (selecionado) {
                imagem = new VermelhoDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void verde(boolean selecionado) {
        try {
            if (selecionado) {
                imagem = new VerdeDecorator(imagem);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

}
