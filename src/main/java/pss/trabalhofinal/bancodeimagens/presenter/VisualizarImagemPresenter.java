package pss.trabalhofinal.bancodeimagens.presenter;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import pss.trabalhofinal.bancodeimagens.dao.HistoricoFiltroDAO;
import pss.trabalhofinal.bancodeimagens.dao.LixeiraDAO;
import pss.trabalhofinal.bancodeimagens.model.HistoricoFiltros;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.Lixeira;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.utils.RelativePath;
import pss.trabalhofinal.bancodeimagens.view.VisualizarImagemView;

public class VisualizarImagemPresenter implements IObservable {

    private VisualizarImagemView view;
    private List<IObserver> observers;
    private Image imagem;
    private UserModel user;

    public VisualizarImagemPresenter(Image imagem, JDesktopPane desktop, UserModel user) {
        view = new VisualizarImagemView();
        observers = new ArrayList<>();
        this.imagem = imagem;
        this.user = user;

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
            view.dispose();
            new AplicarFiltroPresenter(this.imagem, desktop);
        });

        view.getBtnVisualizarHistorico().addActionListener(ae -> {
            historico(desktop);
        });

        view.getBtnExcluir().addActionListener(ae -> {
            excluir();
        });

        view.getBtnExportar().addActionListener(l -> {
            exportar();
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void exportar() {
        try {
            JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Escolha pasta para exportação");
            var res = chooser.showSaveDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File salvar = chooser.getSelectedFile();
                System.out.println(salvar.getPath());
                if (!salvar.getPath().endsWith(".jpg")) {
                    salvar = new File(salvar + ".jpg");
                }
                ImageIO.write(imagem.getImagem(), "jpg", salvar);
                System.out.println("Salvar em" + salvar.getPath());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao exportar imagem: " + e.getMessage());
        }
    }

    private void historico(JDesktopPane desktop) {

        ArrayList<HistoricoFiltros> lista = new ArrayList<>();

        lista = (ArrayList<HistoricoFiltros>) HistoricoFiltroDAO.getHistoricoByImagem(imagem.getPath());

        if (!lista.isEmpty()) {
            new HistoricoFiltroPresenter(lista, desktop);
        } else {
            JOptionPane.showMessageDialog(view, "Imagem não tem histórico de filtros.");
        }

    }

    private void excluir() {
        String[] options = {"Sim", "Não"};

        int resposta = JOptionPane.showOptionDialog(
                view,
                "Tem certeza que deseja apagar a imagem?",
                "Apagar imagem",
                JOptionPane.YES_OPTION,
                JOptionPane.NO_OPTION,
                null,
                options,
                options[1]);

        if (resposta == 0) {
            view.dispose();
            LixeiraDAO.insert(new Lixeira(user.getId(), imagem.getPath(), Paths.get(imagem.getPath()).getFileName().toString(), LocalDate.now()));
            System.out.println("Apaga imagem " + imagem.getPath());

        }
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
