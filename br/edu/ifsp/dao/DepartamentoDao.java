package br.edu.ifsp.dao;

import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDao extends GenericDao {

    private String instrucaoSql;
    private PreparedStatement comando;
    private ResultSet registros;
    private static String excecao = null;

    public String insereDepartamento(Departamento departamento) {
        instrucaoSql = "INSERT INTO DEPARTAMENTO (NomeDepto, IdFuncGerente) VALUES (?,?)";
        return insere(instrucaoSql, departamento.getNomeDepto(), departamento.getGerente().getId());
    }
    public List<Funcionario> recuperaGerentes() {
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        instrucaoSql = "SELECT * FROM FUNCIONARIO WHERE Id IN(SELECT IdFuncGerente FROM DEPARTAMENTO)";
        try {
            excecao = ConnectionDatabase.conectaBd();
            if (excecao == null) {
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
                registros = comando.executeQuery();
                if (registros.next()) {
                    registros.beforeFirst();
                    while (registros.next()) {
                        funcionario = new Funcionario();
                        funcionario.setId(registros.getInt("Id"));
                        funcionario.setNome(registros.getString("Nome"));
                        funcionario.setSexo(registros.getString("Sexo").charAt(0));
                        funcionario.setSalario(registros.getBigDecimal("Salario"));
                        funcionario.setPlanoSaude(registros.getBoolean("PlanoSaude"));
                        funcionarios.add(funcionario);
                    }
                }
                registros.close();
                comando.close();
                ConnectionDatabase.getConexaoBd().close();
            }
        } catch (Exception e) {
            excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
            funcionarios = null;
        }
        return funcionarios;
    }

    public List<Funcionario> recuperaNaoGerentes() {
        Funcionario funcionario;
        List<Funcionario> funcionarios = new ArrayList<Funcionario>();
        instrucaoSql = "SELECT * FROM FUNCIONARIO WHERE Id NOT IN(SELECT IdFuncGerente FROM DEPARTAMENTO)";
        try {
            excecao = ConnectionDatabase.conectaBd();
            if (excecao == null) {
                comando = ConnectionDatabase.getConexaoBd().prepareStatement(instrucaoSql);
                registros = comando.executeQuery();
                if (registros.next()) {
                    registros.beforeFirst();
                    while (registros.next()) {
                        funcionario = new Funcionario();
                        funcionario.setId(registros.getInt("Id"));
                        funcionario.setNome(registros.getString("Nome"));
                        funcionario.setSexo(registros.getString("Sexo").charAt(0));
                        funcionario.setSalario(registros.getBigDecimal("Salario"));
                        funcionario.setPlanoSaude(registros.getBoolean("PlanoSaude"));
                        funcionarios.add(funcionario);
                    }
                }
                registros.close();
                comando.close();
                ConnectionDatabase.getConexaoBd().close();
            }
        } catch (Exception e) {
            excecao = "Tipo de Exceção: " + e.getClass().getSimpleName() + "\nMensagem: " + e.getMessage();
            funcionarios = null;
        }
        return funcionarios;
    }
    public List<Departamento> consultaDepartamentos() {
        Departamento departamento;
        Funcionario funcionario;

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
                        funcionario = new Funcionario();
                        funcionario.setId(registros.getInt("idFuncGerente"));
                        departamento.setGerente(funcionario);
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
