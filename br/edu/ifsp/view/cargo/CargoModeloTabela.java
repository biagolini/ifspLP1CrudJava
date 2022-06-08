package br.edu.ifsp.view.cargo;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class CargoModeloTabela extends AbstractTableModel {
    private String[] colunas = {"Código", "Descrição", "Nome do departamento"};
    private List<Cargo> cargos;
    private List<Departamento> departamentos;

    public CargoModeloTabela() {
    }

    public CargoModeloTabela(List<Cargo> cargos, List<Departamento> departamentos) {
        this.cargos = cargos;
        this.departamentos = departamentos;
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
        if (cargos != null)
            return cargos.size();
        return 0;
    }

    @Override
    public Object getValueAt(int linha, int coluna) {
        Cargo cargo = cargos.get(linha);
        Object valor = null;
        switch (coluna) {
            case 0:
                valor = cargo.getId();
                break;
            case 1: // Coluna descrição
                valor = cargo.getDescricao();
                break;
            case 2: // Coluna Nome do departamento
                if (departamentos != null) //
                    for (Departamento d : departamentos)
                        if (d.getId() == cargo.getDepartamento().getId())
                            valor = d;
                break;
        }
        return valor;
    }

    @Override
    public boolean isCellEditable(int linha, int coluna) {
        return false;
    }
}