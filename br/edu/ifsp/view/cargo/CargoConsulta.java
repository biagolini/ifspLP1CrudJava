package br.edu.ifsp.view.cargo;

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class CargoConsulta extends JDialog {
    private JLabel lbTitulo;
    private JTable jTable;
    private CargoModeloTabela mtTabela;
    private JScrollPane spTabela;
    private Container cp;

    public CargoConsulta() {
        setTitle("Consulta de Cargos");
        setSize(700, 320);
        setLocationRelativeTo(null);
        setModal(true);

        String excecaoCargos = null;
        String excecaoDepartamentos = null;

        java.util.List<Cargo> cargos = new CargoController().recuperaCargos();
        excecaoCargos = new CargoController().getExcecao();

        List<Departamento> departamentos = new CargoController().recuperaDepartamentos();
        excecaoDepartamentos = new CargoController().getExcecao();

        if (excecaoCargos != null) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos funcionários:\n" + excecaoCargos,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            mtTabela = new CargoModeloTabela();
        } else if (excecaoDepartamentos != null) {
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos cargos dos funcionários:\n" + excecaoDepartamentos,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            mtTabela = new CargoModeloTabela();
        } else
            mtTabela = new CargoModeloTabela(cargos,departamentos);

        lbTitulo = new JLabel("Consulta de Cargos");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.


        jTable = new JTable(mtTabela);
        spTabela = new JScrollPane(jTable);


        jTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // Configura a largura de cada coluna do JTable (em pixels).
        jTable.getColumnModel().getColumn(0).setPreferredWidth(102);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(270);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(270);


        // Configura a fonte do cabeçalho do JTable.
        jTable.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

        DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
        dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
        jTable.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);


        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(new Color(180, 205, 205));

        lbTitulo.setBounds(215, 10, 300, 25);
        spTabela.setBounds(20, 40, 645, 182);

        cp.add(lbTitulo);
        cp.add(spTabela);
    }
}
