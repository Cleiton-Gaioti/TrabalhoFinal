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
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.HistoricoFiltros;
import pss.trabalhofinal.bancodeimagens.model.Image;
import pss.trabalhofinal.bancodeimagens.model.Lixeira;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObservable;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.utils.ThumbnailFileChooser;
import pss.trabalhofinal.bancodeimagens.view.VisualizarImagemView;

public class VisualizarImagemPresenter implements IObservable {

    private final HistoricoFiltroDAO historicoFiltroDAO;
    private final LixeiraDAO lixeiraDAO;
    private final PermissaoDAO permissaoDAO;
    private VisualizarImagemView view;
    private List<IObserver> observers;
    private UserModel user;
    private Image imagem;
    private JDesktopPane desktop;

    public VisualizarImagemPresenter(Image imagem, JDesktopPane desktop, UserModel user) {
        historicoFiltroDAO = new HistoricoFiltroDAO();
        view = new VisualizarImagemView();
        observers = new ArrayList<>();
        lixeiraDAO = new LixeiraDAO();
        permissaoDAO = new PermissaoDAO();
        this.imagem = imagem;
        this.user = user;
        this.desktop = desktop;

        try {
            view.setTitle(imagem.getPath());
            view.getLblImagem().setIcon(new ImageIcon(this.imagem.getImagem()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir imagem! " + e.getMessage());
        }

        view.getBtnFechar().addActionListener(ae -> {
            view.dispose();
        });

        view.getBtnCompartilhar().addActionListener(ae -> {
            if (Admin.class.isInstance(user) || permissaoDAO.canCompartilhar(user.getId(), imagem.getPath())) {
                new CompartilharPresenter(user, imagem, desktop);
            } else {
                new NaoAutorizadoPresenter(user, desktop, imagem, "compartilhar");
            }
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

        this.desktop.add(view);
        view.setVisible(true);
    }

    private void exportar() {

        try {
            ThumbnailFileChooser chooser = new ThumbnailFileChooser(new File("./images/"));
            chooser.setDialogTitle("Escolha pasta para exporta????o");
            var res = chooser.showSaveDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File salvar = chooser.getSelectedFile();
                if (!salvar.getPath().endsWith(".jpg")) {
                    salvar = new File(salvar + ".jpg");
                }
                ImageIO.write(imagem.getImagem(), "jpg", salvar);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao exportar imagem: " + e.getMessage());
        }

    }

    private void historico(JDesktopPane desktop) {

        ArrayList<HistoricoFiltros> lista = new ArrayList<>();

        lista = (ArrayList<HistoricoFiltros>) historicoFiltroDAO.getHistoricoByImagem(imagem.getPath());

        if (!lista.isEmpty()) {
            new HistoricoFiltroPresenter(lista, desktop);
        } else {
            JOptionPane.showMessageDialog(view, "Imagem n??o tem hist??rico de filtros.");
        }

    }

    private void excluir() {

        if (Admin.class.isInstance(this) || permissaoDAO.canExcluir(user.getId(), imagem.getPath())) {

            String[] options = {"Sim", "N??o"};

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
                try {
                    view.dispose();
                    File temp = new File(imagem.getPath());
                    String fileName = temp.getName();
                    temp.renameTo(new File("./images/.lixeira/" + fileName));
                    lixeiraDAO.insert(new Lixeira(user.getId(), imagem.getPath(),
                            Paths.get(imagem.getPath()).getFileName().toString(), LocalDate.now()));
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(view, "Erro ao excluir imagem: " + e.getMessage());
                }

            }
        } else {
            new NaoAutorizadoPresenter(user, desktop, imagem, "excluir");
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
