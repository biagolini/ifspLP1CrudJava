package br.edu.ifsp.dao;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CargoDao extends GenericDao {

    private String instrucaoSql;
    private PreparedStatement comando;
    private ResultSet registros;
    private static String excecao = null;


    public String insereCargo(Cargo cargo) {
        instrucaoSql = "INSERT INTO CARGO (Descricao, IdDepto) VALUES (?,?)";
        return insere(instrucaoSql, cargo.getDescricao(), cargo.getDepartamento().getId() );
    }

    public List<Departamento> recuperaDepartamentos() {
        Departamento departamento ;
        List<Departamento> departamentos = new ArrayList<Departamento>();
        instrucaoSql = "SELECT * FROM DEPARTAMENTO";

        try {
            excecao = ConnectionDatabase.conectaBd();
            if (excecao == null) {
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
                registros = comando.executeQuery();
                if (registros.next()) {
                    registros.beforeFirst();
                    while (registros.next()) {
                        departamento = new Departamento();
                        departamento.setId(registros.getInt("Id"));
                        departamento.setNomeDepto(registros.getString("NomeDepto"));
                        departamentos.add(departamento);
                    }
                }
                registros.close();
                comando.close();
                ConnectionDatabase.getConexaoBd().close();
            }
        } catch (Exception e) {
            excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
            departamentos = null;
        }
        return departamentos;
    }
    public String getExcecao() {
        return excecao;
    }

}
