package pss.trabalhofinal.bancodeimagens.view;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

public class AplicarFiltroView extends javax.swing.JInternalFrame {

    public AplicarFiltroView() {
        initComponents();
    }

    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblFiltros = new javax.swing.JLabel();
        ckbImgVermelho = new javax.swing.JCheckBox();
        ckbImgVerde = new javax.swing.JCheckBox();
        ckbImgAzul = new javax.swing.JCheckBox();
        ckbImgEspelhada = new javax.swing.JCheckBox();
        ckbImgRotacionar = new javax.swing.JCheckBox();
        ckbImgNegativo = new javax.swing.JCheckBox();
        CkbImgSepia = new javax.swing.JCheckBox();
        ckbImgPixelar = new javax.swing.JCheckBox();
        ckbImgCinza = new javax.swing.JCheckBox();
        ckbImgBrilho = new javax.swing.JCheckBox();
        scpPainel = new javax.swing.JScrollPane();
        lblImagem = new javax.swing.JLabel();
        lblPredefinicoes = new javax.swing.JLabel();
        cmbPredefinicoes = new javax.swing.JComboBox<>();
        btnFechar = new javax.swing.JButton();
        txtRotacao = new javax.swing.JTextField();
        txtPixel = new javax.swing.JTextField();
        txtBrilho = new javax.swing.JTextField();
        btnReverter = new javax.swing.JButton();

        setTitle("Aplicar filtro em uma imagem");

        lblFiltros.setFont(new java.awt.Font("sansserif", 1, 18)); // NOI18N
        lblFiltros.setText("Filtros");

        ckbImgVermelho.setText("Imagem Vermelha");

        ckbImgVerde.setText("Imagem Verde");

        ckbImgAzul.setText("Imagem Azul");

        ckbImgEspelhada.setText("Imagem Espelhada");

        ckbImgRotacionar.setText("Rotacionar Imagem");

        ckbImgNegativo.setText("Negativo da Imagem");

        CkbImgSepia.setText("Sépia");

        ckbImgPixelar.setText("Pixelar Imagem");

        ckbImgCinza.setText("Tons de Cinza");

        ckbImgBrilho.setText("Aplicar Brilho");

        scpPainel.setViewportView(lblImagem);

        lblPredefinicoes.setText("Predefinições");

        cmbPredefinicoes.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] {"1. Vermelho = Sépia", "2. Verde + Sépia", "3. Azul + Sépia", "4. Vermelho + Negativo", "5. Verde + Negativo", "6. Azul + Negativo", "7. Sépia + Negativo"}));

        btnFechar.setText("Fechar");

        txtRotacao.setText("0");

        txtPixel.setText("0");

        txtBrilho.setText("0");

        btnReverter.setText("Reverter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ckbImgNegativo)
                            .addComponent(CkbImgSepia)
                            .addComponent(ckbImgCinza)
                            .addComponent(ckbImgVermelho)
                            .addComponent(ckbImgVerde)
                            .addComponent(ckbImgAzul)
                            .addComponent(ckbImgEspelhada)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ckbImgRotacionar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(ckbImgPixelar)
                                    .addComponent(ckbImgBrilho))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtBrilho, javax.swing.GroupLayout.DEFAULT_SIZE, 76, Short.MAX_VALUE)
                                    .addComponent(txtPixel))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scpPainel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblFiltros)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 338, Short.MAX_VALUE)
                        .addComponent(btnReverter)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblPredefinicoes)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbPredefinicoes, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnFechar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFiltros)
                    .addComponent(lblPredefinicoes)
                    .addComponent(cmbPredefinicoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnReverter))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(ckbImgVermelho)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbImgVerde)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbImgAzul)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbImgEspelhada)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ckbImgRotacionar)
                            .addComponent(txtRotacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbImgNegativo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(CkbImgSepia)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckbImgPixelar)
                            .addComponent(txtPixel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ckbImgCinza)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(ckbImgBrilho)
                            .addComponent(txtBrilho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(scpPainel, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                .addComponent(btnFechar)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public JCheckBox getCkbImgSepia() {
        return CkbImgSepia;
    }

    public void setCkbImgSepia(JCheckBox CkbImgSepia) {
        this.CkbImgSepia = CkbImgSepia;
    }

    public JButton getBtnFechar() {
        return btnFechar;
    }

    public void setBtnFechar(JButton btnFechar) {
        this.btnFechar = btnFechar;
    }

    public JCheckBox getCkbImgAzul() {
        return ckbImgAzul;
    }

    public void setCkbImgAzul(JCheckBox ckbImgAzul) {
        this.ckbImgAzul = ckbImgAzul;
    }

    public JCheckBox getCkbImgBrilho() {
        return ckbImgBrilho;
    }

    public void setCkbImgBrilho(JCheckBox ckbImgBrilho) {
        this.ckbImgBrilho = ckbImgBrilho;
    }

    public JCheckBox getCkbImgCinza() {
        return ckbImgCinza;
    }

    public JTextField getTxtBrilho() {
        return txtBrilho;
    }

    public void setTxtBrilho(JTextField txtBrilho) {
        this.txtBrilho = txtBrilho;
    }

    public JTextField getTxtPixel() {
        return txtPixel;
    }

    public void setTxtPixel(JTextField txtPixel) {
        this.txtPixel = txtPixel;
    }

    public JTextField getTxtRotacao() {
        return txtRotacao;
    }

    public void setTxtRotacao(JTextField txtRotacao) {
        this.txtRotacao = txtRotacao;
    }

    public void setCkbImgCinza(JCheckBox ckbImgCinza) {
        this.ckbImgCinza = ckbImgCinza;
    }

    public JButton getBtnReverter() {
        return btnReverter;
    }

    public void setBtnReverter(JButton btnReverter) {
        this.btnReverter = btnReverter;
    }

    public JCheckBox getCkbImgEspelhada() {
        return ckbImgEspelhada;
    }

    public void setCkbImgEspelhada(JCheckBox ckbImgEspelhada) {
        this.ckbImgEspelhada = ckbImgEspelhada;
    }

    public JCheckBox getCkbImgNegativo() {
        return ckbImgNegativo;
    }

    public void setCkbImgNegativo(JCheckBox ckbImgNegativo) {
        this.ckbImgNegativo = ckbImgNegativo;
    }

    public JCheckBox getCkbImgPixelar() {
        return ckbImgPixelar;
    }

    public void setCkbImgPixelar(JCheckBox ckbImgPixelar) {
        this.ckbImgPixelar = ckbImgPixelar;
    }

    public JCheckBox getCkbImgRotacionar() {
        return ckbImgRotacionar;
    }

    public void setCkbImgRotacionar(JCheckBox ckbImgRotacionar) {
        this.ckbImgRotacionar = ckbImgRotacionar;
    }

    public JCheckBox getCkbImgVerde() {
        return ckbImgVerde;
    }

    public void setCkbImgVerde(JCheckBox ckbImgVerde) {
        this.ckbImgVerde = ckbImgVerde;
    }

    public JCheckBox getCkbImgVermelho() {
        return ckbImgVermelho;
    }

    public void setCkbImgVermelho(JCheckBox ckbImgVermelho) {
        this.ckbImgVermelho = ckbImgVermelho;
    }

    public JComboBox<String> getCmbPredefinicoes() {
        return cmbPredefinicoes;
    }

    public void setCmbPredefinicoes(JComboBox<String> cmbPredefinicoes) {
        this.cmbPredefinicoes = cmbPredefinicoes;
    }

    public JLabel getLblFiltros() {
        return lblFiltros;
    }

    public void setLblFiltros(JLabel lblFiltros) {
        this.lblFiltros = lblFiltros;
    }

    public JLabel getLblImagem() {
        return lblImagem;
    }

    public void setLblImagem(JLabel lblImagem) {
        this.lblImagem = lblImagem;
    }

    public JLabel getLblPredefinicoes() {
        return lblPredefinicoes;
    }

    public void setLblPredefinicoes(JLabel lblPredefinicoes) {
        this.lblPredefinicoes = lblPredefinicoes;
    }

    public JScrollPane getScpPainel() {
        return scpPainel;
    }

    public void setScpPainel(JScrollPane scpPainel) {
        this.scpPainel = scpPainel;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox CkbImgSepia;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnReverter;
    private javax.swing.JCheckBox ckbImgAzul;
    private javax.swing.JCheckBox ckbImgBrilho;
    private javax.swing.JCheckBox ckbImgCinza;
    private javax.swing.JCheckBox ckbImgEspelhada;
    private javax.swing.JCheckBox ckbImgNegativo;
    private javax.swing.JCheckBox ckbImgPixelar;
    private javax.swing.JCheckBox ckbImgRotacionar;
    private javax.swing.JCheckBox ckbImgVerde;
    private javax.swing.JCheckBox ckbImgVermelho;
    private javax.swing.JComboBox<String> cmbPredefinicoes;
    private javax.swing.JLabel lblFiltros;
    private javax.swing.JLabel lblImagem;
    private javax.swing.JLabel lblPredefinicoes;
    private javax.swing.JScrollPane scpPainel;
    private javax.swing.JTextField txtBrilho;
    private javax.swing.JTextField txtPixel;
    private javax.swing.JTextField txtRotacao;
    // End of variables declaration//GEN-END:variables
}
