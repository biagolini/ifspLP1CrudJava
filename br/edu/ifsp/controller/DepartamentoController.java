package br.edu.ifsp.controller;

import br.edu.ifsp.dao.DepartamentoDao;
import br.edu.ifsp.dao.FuncionarioDao;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.departamento.DepartamentoValidacao;
import br.edu.ifsp.model.funcionario.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class DepartamentoController {

    private Departamento departamento;
    private List<String> erros;

    public List<String> insereDepartamento(String nomeDepto, Funcionario gerente) {
        recebeDadosDepartamento(null, nomeDepto, gerente);
        if (erros.size() == 0)
            erros.add(new DepartamentoDao().insereDepartamento(departamento));
        return erros;
    }

    public void recebeDadosDepartamento(Integer id, String nomeDepto, Funcionario gerente) {
        departamento = new Departamento();
        erros = new ArrayList<String>();
        departamento.setId(id);
        departamento.setNomeDepto(nomeDepto);
        departamento.setGerente(gerente);
        erros = DepartamentoValidacao.validaFuncionario(departamento);
    }

    public List<Funcionario>  recuperaNaoGerentes() {
        return new DepartamentoDao().recuperaNaoGerentes();
    }

    public List<Funcionario>  recuperaGerentes() {
        return new DepartamentoDao().recuperaGerentes();
    }

    public List<Departamento> consultaDepartamentos() {
        return new DepartamentoDao().consultaDepartamentos();
    }
    public String getExcecao() {
        return new DepartamentoDao().getExcecao();
    }

}

