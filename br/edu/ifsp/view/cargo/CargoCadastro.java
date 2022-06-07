package br.edu.ifsp.view.cargo;

import br.edu.ifsp.controller.CargoController;
import br.edu.ifsp.controller.DepartamentoController;
import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.departamento.Departamento;
import br.edu.ifsp.model.funcionario.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CargoCadastro extends JDialog {

    private JLabel lbTitulo, lbNome, lbDepartamento;
    private JTextField tfNome;
    private JComboBox<Departamento> cbDepartamento;
    private JButton btCadastrar;
    private Container cp;

    public CargoCadastro() {
        setTitle("Cadastro de Cargo");
        setSize(500, 335);
        setLocationRelativeTo(null);
        setModal(true);

        lbTitulo = new JLabel("Cadastro de Cargo");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
        lbNome = new JLabel("Descrição");
        lbDepartamento = new JLabel("Departamento");
        tfNome = new JTextField();

        cbDepartamento = new JComboBox<>();

        java.util.List<Departamento> departamentos = new ArrayList<Departamento>();

        departamentos = new CargoController().recuperaDepartamentos();

        if (departamentos != null)
            for (Departamento d : departamentos)
                cbDepartamento.addItem(d);

        String erro = new FuncionarioController().getExcecao();

        if (erro != null)
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos departamentos:\n" + erro,
                    "Erro", JOptionPane.ERROR_MESSAGE);

        btCadastrar = new JButton("Cadastrar");

        cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(new Color(180, 205, 205));


        lbTitulo.setBounds(125, 10, 300, 25);
        lbNome.setBounds(20, 50, 100, 25);
        tfNome.setBounds(100, 50, 360, 25);
        lbDepartamento.setBounds(20, 90, 100, 25);
        cbDepartamento.setBounds(100, 90, 220, 25);
        btCadastrar.setBounds(200, 250, 100, 25);

        // Adição dos componentes de interface ao container.
        cp.add(lbTitulo);
        cp.add(lbNome);
        cp.add(tfNome);
        cp.add(lbDepartamento);
        cp.add(cbDepartamento);
        cp.add(btCadastrar);


        btCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)  {
                btCadastrarAction();
            }
        });
    } // Final do construtor.

    private void btCadastrarAction() {
        List<String> erros = new ArrayList<String>();

        erros = new CargoController().insereCargo(
                tfNome.getText(),
                (Departamento) cbDepartamento.getSelectedItem());

        if (erros.get(0) == null) {
            JOptionPane.showMessageDialog(this, "Cargo cadastrado com sucesso.",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false);
        } else {
            String mensagem = "Não foi possível cadastrar o cargo:\n";
            for (String e : erros)
                mensagem = mensagem + e + "\n";
            JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
        }
    }
}
