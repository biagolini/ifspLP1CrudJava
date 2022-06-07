package br.edu.ifsp.controller;

import br.edu.ifsp.dao.CargoDao;
import br.edu.ifsp.dao.DepartamentoDao;
import br.edu.ifsp.model.cargo.Cargo;
import br.edu.ifsp.model.cargo.CargoValidacao;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.departamento.DepartamentoValidacao;
import br.edu.ifsp.model.funcionario.Funcionario;

import java.util.ArrayList;
import java.util.List;

public class CargoController {

    private Cargo cargo;
    private List<String> erros;


    public List<String> insereCargo(String descricao, Departamento depto) {
        recebeDadosCargo(null, descricao, depto);
        if (erros.size() == 0)
            erros.add(new CargoDao().insereCargo(cargo));
        return erros;
    }

    public void recebeDadosCargo(Integer id, String descricao, Departamento depto) {
        cargo = new Cargo();
        erros = new ArrayList<String>();
        cargo.setId(id);
        cargo.setDescricao(descricao);
        cargo.setDepartamento(depto);
        erros = CargoValidacao.validaCargo(cargo);
    }

    public List<Departamento>  recuperaDepartamentos() {
        return new CargoDao().recuperaDepartamentos();
    }

    public String getExcecao() {
        return new CargoDao().getExcecao();
    }

}

