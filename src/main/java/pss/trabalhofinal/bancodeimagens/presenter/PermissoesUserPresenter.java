package pss.trabalhofinal.bancodeimagens.presenter;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import pss.trabalhofinal.bancodeimagens.dao.PermissaoDAO;
import pss.trabalhofinal.bancodeimagens.model.Admin;
import pss.trabalhofinal.bancodeimagens.model.Permissao;
import pss.trabalhofinal.bancodeimagens.model.UserModel;
import pss.trabalhofinal.bancodeimagens.model.interfaces.IObserver;
import pss.trabalhofinal.bancodeimagens.utils.RelativePath;
import pss.trabalhofinal.bancodeimagens.view.PermissoesUserView;

public class PermissoesUserPresenter implements IObserver {

    private PermissoesUserView view;
    private UserModel user;
    private Admin admin;

    public PermissoesUserPresenter(UserModel user, Admin admin, JDesktopPane desktop) {
        view = new PermissoesUserView();
        this.user = user;
        this.admin = admin;
        view.setTitle("Permissões " + user.getUsername());

        loadTable();

        view.getBtnFechar().addActionListener(l -> {
            view.dispose();
        });

        view.getBtnAdicionar().addActionListener(l -> {
            addPermissao(desktop);
        });

        desktop.add(view);
        view.setVisible(true);
    }

    private void addPermissao(JDesktopPane desktop) {
        try {
            JFileChooser chooser = new JFileChooser(new File("./images/"));
            chooser.setDialogTitle("Inserir Permissão");
            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            int res = chooser.showOpenDialog(view);
            if (res == JFileChooser.APPROVE_OPTION) {
                File escolhido = chooser.getSelectedFile();
                if (escolhido.isDirectory()) {
                    List<Permissao> temp = PermissaoDAO.getPermissionsByUser(user.getId());
                    for (Permissao p : temp) {
                        if (p.getPath().startsWith(RelativePath.toRelativePath(escolhido))) {
                            PermissaoDAO.removeById(p.getId());
                        }
                    }
                    PermissaoDAO.insert(new Permissao(user.getId(), admin.getId(), "pasta", RelativePath.toRelativePath(escolhido), LocalDate.now()));
                } else {
                    PermissaoDAO.insert(new Permissao(user.getId(), admin.getId(), "arquivo", RelativePath.toRelativePath(escolhido), LocalDate.now()));
                }
            }
            loadTable();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Erro ao abrir arquivo! " + e.getMessage());
        }
    }

    private void loadTable() {

        var tableModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"ID", "IDAdmin", "Tipo", "Path", "Data de Autorização"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };

        tableModel.setNumRows(0);

        try {
            List<Permissao> lista = PermissaoDAO.getPermissionsByUser(user.getId());
            var dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            for (Permissao p : lista) {
                tableModel.addRow(new Object[]{
                    p.getId(),
                    p.getAdminWhoGranted(),
                    p.getTipo(),
                    p.getPath(),
                    p.getDate().format(dataFormat)});
            }

            view.getTblPermissoes().setModel(tableModel);

        } catch (RuntimeException e) {
            JOptionPane.showMessageDialog(view, "Erro ao carregar tabela: " + e.getMessage());
        }

    }

    @Override
    public void update(Object obj) {
        loadTable();
    }

}
