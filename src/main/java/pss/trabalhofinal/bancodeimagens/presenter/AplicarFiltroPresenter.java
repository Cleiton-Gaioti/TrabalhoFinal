package pss.trabalhofinal.bancodeimagens.presenter;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JOptionPane;

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

import pss.trabalhofinal.bancodeimagens.dao.HistoricoFiltroDAO;
import pss.trabalhofinal.bancodeimagens.factory.ImageConverter;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.MementoImagem;
import pss.trabalhofinal.bancodeimagens.model.Zelador;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.AplicarFiltroView;

public class AplicarFiltroPresenter implements IObservable {

    private final HistoricoFiltroDAO historicoFiltroDAO;
    private ArrayList<IObserver> observers;
    private AplicarFiltroView view;
    private ImagemComponente imagem;
    private String caminho;

    public AplicarFiltroPresenter(Image imagem, JDesktopPane desktop) {
        historicoFiltroDAO = new HistoricoFiltroDAO();
        view = new AplicarFiltroView();
        caminho = imagem.getPath();
        this.imagem = imagem;

        view.getCmbPredefinicoes().setSelectedIndex(-1);
        try {
            view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar imagem: " + e.getMessage());
        }

        view.getBtnImgAzul().addActionListener((ActionEvent ae) -> {
            azul();
        });
        view.getBtnImgVerde().addActionListener((ActionEvent ae) -> {
            verde();
        });
        view.getBtnImgVermelha().addActionListener((ActionEvent ae) -> {
            vermelho();
        });
        view.getBtnEspelhar().addActionListener((ActionEvent ae) -> {
            espelhada();
        });
        view.getBtnRotacionar().addActionListener((ActionEvent ae) -> {
            rotacionar();
        });
        view.getBtnNegativo().addActionListener((ActionEvent ae) -> {
            negativo();
        });
        view.getBtnSepia().addActionListener((ActionEvent ae) -> {
            sepia();
        });
        view.getBtnPixelar().addActionListener((ActionEvent ae) -> {
            pixelar();
        });
        view.getBtnCinza().addActionListener((ActionEvent ae) -> {
            cinza();
        });
        view.getBtnBrilho().addActionListener((ActionEvent ae) -> {
            brilho();
        });
        view.getBtnFechar().addActionListener((ActionEvent ae) -> {
            view.dispose();
        });
        view.getCmbPredefinicoes().addActionListener((ActionEvent ae) -> {
            predefinicoes();
        });

        view.getBtnRestaurar().addActionListener(l -> {
            restaurar();
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

    private void restaurar() {
        try {
            MementoImagem m = Zelador.getInstancia().getUltimo();
            if (m != null) {
                imagem = m.getImagem();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                historicoFiltroDAO.insertHistorico(caminho, "Filtro revertido");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao restaurar imagem: " + e.getMessage());
        }
    }

    private void save() {

        try {

            var caminhoPNG = caminho.replace(".jpg", ".png");
            var imagePNG = new File(caminhoPNG);

            ImageIO.write(imagem.getImagem(), "png", imagePNG);
            ImageConverter.convertImg(caminhoPNG, caminho);
            imagePNG.delete();

        } catch (IOException | RuntimeException e) {
            JOptionPane.showMessageDialog(view, "Erro ao salvar imagem: " + e.getMessage());
        }

    }

    private void predefinicoes() {
        try {
            String res = view.getCmbPredefinicoes().getSelectedItem().toString();
            if (!res.isEmpty()) {
                if (res.startsWith("1.")) {
                    vermelho();
                    sepia();
                } else if (res.startsWith("2.")) {
                    verde();
                    sepia();
                } else if (res.startsWith("3.")) {
                    azul();
                    sepia();
                } else if (res.startsWith("4.")) {
                    vermelho();
                    negativo();
                } else if (res.startsWith("5.")) {
                    verde();
                    negativo();
                } else if (res.startsWith("6.")) {
                    azul();
                    negativo();
                } else if (res.startsWith("7.")) {
                    negativo();
                    sepia();
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void brilho() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));

            int res = Integer.parseInt(view.getTxtBrilho().getText());

            if (res >= 0) {
                imagem = new BrilhoDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                historicoFiltroDAO.insertHistorico(caminho, "Brilho aumentado em " + res);
            } else {
                JOptionPane.showMessageDialog(view, "Valor de brilho deve ser maior ou igual a 0!");
            }
            view.getTxtBrilho().setText("0");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void pixelar() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));

            int res = Integer.parseInt(view.getTxtPixel().getText());
            if (res > 0) {
                imagem = new PixeladaDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                historicoFiltroDAO.insertHistorico(caminho, "Pixelado com tamanho " + res);
            } else {
                JOptionPane.showMessageDialog(view, "Valor de píxel deve ser maior do que 1!");
            }
            view.getTxtPixel().setText("1");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void rotacionar() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));

            int res = Integer.parseInt(view.getTxtRotacao().getText());

            if (res >= 0) {
                imagem = new RotacionaDecorator(imagem, res);
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                historicoFiltroDAO.insertHistorico(caminho, "Rotacionado em " + res + "graus");
            } else {
                JOptionPane.showMessageDialog(view, "Valor de rotação deve ser maior ou igual a 0!");
            }
            view.getTxtRotacao().setText("0");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void cinza() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new TomDeCinzaDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Tons de Cinza Aplicado");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void negativo() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new NegativaDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Negativo");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void espelhada() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new EspelhadaDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Espelhada");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void sepia() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new SepiaDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Sépia");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void azul() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new AzulDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Azul");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void vermelho() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new VermelhoDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            save();
            historicoFiltroDAO.insertHistorico(caminho, "Vermelho");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void verde() {
        try {
            Zelador.getInstancia().add(new MementoImagem(imagem));
            imagem = new VerdeDecorator(imagem);
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            historicoFiltroDAO.insertHistorico(caminho, "Verde");
            save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

}
