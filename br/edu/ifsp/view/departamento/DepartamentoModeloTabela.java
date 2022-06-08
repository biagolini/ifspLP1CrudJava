package br.edu.ifsp.view.departamento;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

import javax.swing.table.AbstractTableModel;
import java.text.DecimalFormat;
import java.util.List;

public class DepartamentoModeloTabela extends AbstractTableModel {
    private String[] colunas = { "Código", "Nome do departamento", "Nome do gerente" };
    private List<Departamento> departamentos;
    private List<Funcionario> gerentes;

    public DepartamentoModeloTabela() { } 

    public DepartamentoModeloTabela(List<Departamento> departamentos, List<Funcionario> gerentes) {
        this.departamentos = departamentos;
        this.gerentes = gerentes;
    }

    @Override
    public int getColumnCount() {
        return colunas.length;
    }

    @Override
    public String getColumnName(int coluna) {
        return colunas[coluna];
    }

    @Override
    public int getRowCount() {
        if (departamentos != null)
            return departamentos.size();
        return 0;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Departamento departamento = departamentos.get(linha);
        Object valor = null;
        switch (coluna) {
            case 0:
                valor = departamento.getId();
                break;
            case 1: // Coluna Nome do departamento
                valor = departamento.getNomeDepto();
                break;
            case 2: // Coluna Nome do gerente
                if (gerentes != null) //
                    for (Funcionario f : gerentes)
                        if (f.getId() == departamento.getGerente().getId())
                            valor = f;
                            break;
        }
        return valor;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}