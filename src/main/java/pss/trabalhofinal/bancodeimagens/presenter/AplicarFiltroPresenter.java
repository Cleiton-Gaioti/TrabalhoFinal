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
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.view.AplicarFiltroView;

public class AplicarFiltroPresenter implements IObservable {

    private AplicarFiltroView view;
    private ArrayList<IObserver> observers;
    private ImagemComponente imagem;
    private String caminho;

    public AplicarFiltroPresenter(Image imagem, JDesktopPane desktop) {
        this.imagem = imagem;
        caminho = imagem.getPath();
        view = new AplicarFiltroView();

        view.getBtnReverter().setVisible(false);
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
        view.getBtnReverter().addActionListener((ActionEvent ae) -> {
            reverterPredefinicoes();
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

    private void reverterPredefinicoes() {
        try {
            imagem = imagem.reverter();
            imagem = imagem.reverter();
            HistoricoFiltroDAO.insertHistorico(caminho, "Efeito anterior revertido");
            HistoricoFiltroDAO.insertHistorico(caminho, "Efeito anterior revertido");
            view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
            view.getBtnReverter().setVisible(false);
            save();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
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
                view.getBtnReverter().setVisible(true);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void brilho() {
        try {
            if (view.getCkbImgBrilho().isSelected()) {

                int res = Integer.parseInt(view.getTxtBrilho().getText());

                if (res >= 0) {
                    imagem = new BrilhoDecorator(imagem, res);
                    view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                    save();
                    HistoricoFiltroDAO.insertHistorico(caminho, "Brilho aumentado em " + res);
                } else {
                    JOptionPane.showMessageDialog(view, "Valor de brilho deve ser maior ou igual a 0!");
                    view.getCkbImgBrilho().setSelected(false);
                    view.getTxtBrilho().setText("0");
                }

            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Brilho revertido");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void pixelar() {
        try {
            if (view.getCkbImgPixelar().isSelected()) {

                int res = Integer.parseInt(view.getTxtPixel().getText());
                if (res > 0) {
                    imagem = new PixeladaDecorator(imagem, res);
                    view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                    save();
                    HistoricoFiltroDAO.insertHistorico(caminho, "Pixelado com tamanho " + res);
                } else {
                    JOptionPane.showMessageDialog(view, "Valor de píxel deve ser maior do que 1!");
                    view.getCkbImgPixelar().setSelected(false);
                    view.getTxtPixel().setText("1");
                }
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Pixelação revertida");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

    private void rotacionar() {
        try {
            if (view.getCkbImgRotacionar().isSelected()) {

                int res = Integer.parseInt(view.getTxtRotacao().getText());

                if (res >= 0) {
                    imagem = new RotacionaDecorator(imagem, res);
                    view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                    save();
                    HistoricoFiltroDAO.insertHistorico(caminho, "Rotacionado em " + res + "graus");
                } else {
                    JOptionPane.showMessageDialog(view, "Valor de rotação deve ser maior ou igual a 0!");
                    view.getCkbImgRotacionar().setSelected(false);
                    view.getTxtRotacao().setText("0");
                }
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Rotação revertida");
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Tons de Cinza Aplicado");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Tons de Cinza revertido");
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Negativo");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Negativo revertido");
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Espelhada");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                HistoricoFiltroDAO.insertHistorico(caminho, "Espelhada Revertida");
                save();
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Sépia");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Sépia revertido");
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Azul");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Azul revertido");
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
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Vermelho");
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                save();
                HistoricoFiltroDAO.insertHistorico(caminho, "Vermelho revertido");
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
                HistoricoFiltroDAO.insertHistorico(caminho, "Verde");
                save();
            } else {
                imagem = imagem.reverter();
                view.getLblImagem().setIcon(new ImageIcon(imagem.getImagem()));
                HistoricoFiltroDAO.insertHistorico(caminho, "Verde revertido");
                save();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao aplicar filtro! " + e.getMessage());
        }
    }

}
