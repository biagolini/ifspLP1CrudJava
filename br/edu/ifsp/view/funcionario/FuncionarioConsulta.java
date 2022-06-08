package br.edu.ifsp.view.funcionario;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.funcionario.Funcionario;

@SuppressWarnings("serial")
public class FuncionarioConsulta extends JDialog {
    private JLabel lbTitulo;
    private JTable tbFuncionario; // Tabela que exibirá os funcionários cadastrados no banco de dados.
    private FuncionarioModeloTabela mtTabela; // Modelo que definirá a estrutura da tabela e permitirá o carregamento dos dados nela.
    private JScrollPane spTabela; // Painel de rolagem ao qual será vinculado o JTable.
    private Container cp; // Container para organizar os componentes na janela.

    public FuncionarioConsulta() { // Construtor.
        // Instanciação e configuração dos componentes de interface.
        setTitle("Consulta de Funcionários"); // Título da janela.
        setSize(700, 320); // Tamanho da janela em pixels.
        setLocationRelativeTo(null); // Centraliza a janela na tela.
        setModal(true); // Torna a janela "modal" (janela que não permite acesso a outras janelas abertas).

        String excecaoFuncionarios = null;
        String excecaoCargos = null;

        List<Funcionario> funcionarios = new FuncionarioController().consultaFuncionarios();
        excecaoFuncionarios = new FuncionarioController().getExcecao();

        List<Cargo> cargos = new FuncionarioController().recuperaCargos();
        excecaoCargos = new FuncionarioController().getExcecao();

        if (excecaoFuncionarios != null) { // Caso ocorra qualquer tipo de exceção.
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos funcionários:\n" + excecaoFuncionarios,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            mtTabela = new FuncionarioModeloTabela(); // Chama o construtor da classe FuncionarioModeloTabela, que apenas define a estrutura do JTable.
        } else if (excecaoCargos != null) { // Caso ocorra qualquer tipo de exceção.
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos cargos dos funcionários:\n" + excecaoCargos,
                    "Erro", JOptionPane.ERROR_MESSAGE);
            mtTabela = new FuncionarioModeloTabela(); // Chama o construtor da classe FuncionarioModeloTabela, que apenas define a estrutura do JTable.
        } else
            // Chama o construtor da classe FuncionarioModeloTabela, que define a estrutura do JTable e carrega os dados nele.
            mtTabela = new FuncionarioModeloTabela(funcionarios, cargos);

        lbTitulo = new JLabel("Consulta de Funcionários");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 19)); // Ajusta a fonte do JLabel.

        // Inclui o modelo da tabela no JTable. Nesse momento, são chamados os métodos da classe FuncionarioModeloTabela.
        tbFuncionario = new JTable(mtTabela);
        spTabela = new JScrollPane(tbFuncionario); // Vincula o JTable ao painel de rolagem.

        tbFuncionario.setAutoResizeMode(JTable.AUTO_RESIZE_OFF); // Desabilita o dimensionamento automático das colunas.
        // Configura a largura de cada coluna do JTable (em pixels).
        tbFuncionario.getColumnModel().getColumn(0).setPreferredWidth(50);
        tbFuncionario.getColumnModel().getColumn(1).setPreferredWidth(160);
        tbFuncionario.getColumnModel().getColumn(2).setPreferredWidth(70);
        tbFuncionario.getColumnModel().getColumn(3).setPreferredWidth(80);
        tbFuncionario.getColumnModel().getColumn(4).setPreferredWidth(105);
        tbFuncionario.getColumnModel().getColumn(5).setPreferredWidth(177);

        // Configura a fonte do cabeçalho do JTable.
        tbFuncionario.getTableHeader().setFont(new Font(null, Font.BOLD, 12));

        // Centraliza o conteúdo da coluna referente ao Id do funcionário (índice 0).
        DefaultTableCellRenderer dtcrCentro = new DefaultTableCellRenderer();
        dtcrCentro.setHorizontalAlignment(SwingConstants.CENTER);
        tbFuncionario.getColumnModel().getColumn(0).setCellRenderer(dtcrCentro);

        // Alinha à direira o conteúdo da coluna referente ao Salário do funcionário (índice 3).
        DefaultTableCellRenderer dtcrDireita = new DefaultTableCellRenderer();
        dtcrDireita.setHorizontalAlignment(SwingConstants.RIGHT);
        tbFuncionario.getColumnModel().getColumn(3).setCellRenderer(dtcrDireita);

        tbFuncionario.getTableHeader().setReorderingAllowed(false); // Desabilita a reordenação das colunas do JTable.
        // Habilita o modo de seleção simples, onde é possível selecionar apenas uma linha de cada vez no JTable.
        tbFuncionario.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        cp = getContentPane(); // Instancia o container da janela.
        cp.setLayout(null); // Configura o layout do container como nulo.
        cp.setBackground(new Color(180, 205, 205)); // Configura a cor de fundo do container.

        // Posicionamento dos componentes de interface na janela.
        lbTitulo.setBounds(215, 10, 300, 25); // x, y, largura, altura.
        spTabela.setBounds(20, 40, 645, 182);

        // Adição dos componentes de interface ao container.
        cp.add(lbTitulo);
        cp.add(spTabela);
    } // Final do construtor.
}