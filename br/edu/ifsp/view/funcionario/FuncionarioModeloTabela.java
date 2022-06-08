package br.edu.ifsp.view.funcionario;

import java.text.DecimalFormat;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.funcionario.Funcionario;

@SuppressWarnings("serial")
public class FuncionarioModeloTabela extends AbstractTableModel { // A classe abstrata AbstractTableModel implementa a interface TableModel.
    // Array de nomes a serem exibidos no cabeçalho do JTable.
    private String[] colunas = { "Código", "Nome", "Sexo", "Salário (R$)", "Plano de Saúde", "Cargo" };
    private List<Funcionario> funcionarios; // Lista que conterá os dados a serem exibidos no corpo do JTable.
    private List<Cargo> cargos; // Lista que conterá os dados dos cargos associados a cada funcionário.

    public FuncionarioModeloTabela() { } // Construtor vazio.

    public FuncionarioModeloTabela(List<Funcionario> funcionarios, List<Cargo> cargos) { // Construtor.
        // Obtém um ArrayList de objetos Funcionario, contendo os dados dos funcionários cadastrados.
        this.funcionarios = funcionarios;
        this.cargos = cargos;
    }

    // Método da interface TableModel (implementação obrigatória).
    // Retorna a quantidade de colunas do modelo da tabela.
    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    // Método da classe abstrata AbstractTableModel (implementação opcional).
    // Retorna o nome da coluna recebida como argumento.
    // Sem este método, os nomes das colunas são exibidos no JTable como: A, B, C, D etc.
    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    // Método da interface TableModel (implementação obrigatória).
    // Retorna a quantidade de linhas do modelo da tabela.
    @Override
    public int getRowCount() {
        if (funcionarios != null) // Se existir pelo menos um funcionário cadastrado.
            return funcionarios.size();
        return 0;
    }

    // Método da classe abstrata AbstractTableModel (implementação opcional).
    // Retorna a classe da coluna recebida como argumento.
    // Sem este método, a coluna Plano de Saúde do JTable exibe valores true ou false, em vez de checkboxes.
    @Override
    public Class<?> getColumnClass(int coluna) {
        return getValueAt(0, coluna).getClass(); // A linha é zero, mas poderia ser qualquer número de linha,
        // pois o que importa é a classe da coluna.
    }

    // Método da interface TableModel (implementação obrigatória).
    // Cada vez que é chamado, este método recupera o valor de um dos atributos de um funcionário do ArrayList.
    // Cada valor recuperado é então retornado para popular uma célula correspondente no JTable.
    @Override
    public Object getValueAt(int linha, int coluna) {
        Funcionario funcionario = funcionarios.get(linha); // Recupera o objeto Funcionario presente na posição "linha" do ArrayList.
        Object valor = null;
        // Formata os centavos do Salário. #: dígitos opcionais; 0: dígitos obrigatórios.
        DecimalFormat df = new DecimalFormat("##,##0.00");

        switch (coluna) { // Verifica qual atributo do funcionário será recuperado, com base na coluna recebida.
            case 0: // Coluna IdFuncionario
                valor = funcionario.getId();
                break;
            case 1: // Coluna Nome
                valor = funcionario.getNome();
                break;
            case 2: // Coluna Sexo
                if (funcionario.getSexo() == 'M')
                    valor = "Masculino";
                else
                    valor = "Feminino";
                break;
            case 3: // Coluna Salario
                valor = df.format(funcionario.getSalario());
                break;
            case 4: // Coluna PlanoSaude
                valor = funcionario.isPlanoSaude();
                break;
            case 5: // Coluna Cargo
                if (cargos != null) // Se existir pelo menos um cargo cadastrado.
                    for (Cargo c : cargos)
                        if (c.getId() == funcionario.getCargo().getId())
                            // Ao ser carregado, o JTable chama automaticamente o método toString dos objetos Cargo para convertê-los
                            // para String, pois o dado a ser exibido nele deve ser deste tipo. Como o método toString foi sobrescrito
                            // na classe Cargo, de modo a retornar a descrição do cargo, é este o dado que será exibido no JTable.
                            valor = c;
                break;
        }
        return valor;
    }

    // Método da classe abstrata AbstractTableModel (implementação opcional).
    // Bloqueia para edição a célula recebida como argumento.
    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}