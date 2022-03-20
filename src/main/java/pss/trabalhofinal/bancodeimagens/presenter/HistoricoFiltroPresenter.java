package pss.trabalhofinal.bancodeimagens.presenter;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JDesktopPane;
import javax.swing.table.DefaultTableModel;
import pss.trabalhofinal.bancodeimagens.model.HistoricoFiltros;
import pss.trabalhofinal.bancodeimagens.view.HistoricoFiltroView;

public class HistoricoFiltroPresenter {
    
    private HistoricoFiltroView view;
    private ArrayList<HistoricoFiltros> historico;
    
    public HistoricoFiltroPresenter(ArrayList<HistoricoFiltros> historico, JDesktopPane desktop) {
        this.historico = historico;
        view = new HistoricoFiltroView();
        
        var tableModel = new DefaultTableModel(
                new Object[][]{}, new String[]{"Id", "Imagem", "Filtro", "Data"}) {
            @Override
            public boolean isCellEditable(final int row, final int column) {
                return false;
            }
        };
        
        var dataFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        for (HistoricoFiltros hf : this.historico) {
            tableModel.addRow(new Object[]{
                hf.getId(),
                hf.getPath(),
                hf.getFilter(),
                hf.getDate().format(dataFormat)
            });
        }
        
        view.getTblHistorico().setModel(tableModel);
        
        view.getBtnFechar().addActionListener(ae -> {
            view.dispose();
        });
        
        desktop.add(view);
        view.setVisible(true);
    }
    
}
