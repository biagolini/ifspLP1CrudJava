package br.edu.ifsp.view.departamento;

import br.edu.ifsp.controller.DepartamentoController;
import br.edu.ifsp.controller.FuncionarioController;
import br.edu.ifsp.model.funcionario.Funcionario;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoCadastro extends JDialog {

    private JLabel lbTitulo, lbNome, lbGerente;
    private JTextField tfNome;
    private JComboBox<Funcionario> cbGerente;
    private JButton btCadastrar;
    private Container cp;

    public DepartamentoCadastro() {
        setTitle("Cadastro de Departamentos");
        setSize(500, 335);
        setLocationRelativeTo(null);
        setModal(true);

        lbTitulo = new JLabel("Cadastro de Departamentos");
        lbTitulo.setFont(new Font("Arial", Font.BOLD, 19));
        lbNome = new JLabel("Departamento");
        lbGerente = new JLabel("Gerente");
        tfNome = new JTextField();

        cbGerente = new JComboBox<>();

        java.util.List<Funcionario> funcionarios = new ArrayList<Funcionario>();

        funcionarios = new DepartamentoController().recuperaNaoGerentes();

        if (funcionarios != null)
            for (Funcionario f : funcionarios)
                cbGerente.addItem(f);

        String erro = new FuncionarioController().getExcecao();

        if (erro != null)
            JOptionPane.showMessageDialog(null, "Não foi possível recuperar os dados dos cargos:\n" + erro,
                    "Erro", JOptionPane.ERROR_MESSAGE);

        btCadastrar = new JButton("Cadastrar");

        cp = getContentPane();
        cp.setLayout(null);
        cp.setBackground(new Color(180, 205, 205));


        lbTitulo.setBounds(125, 10, 300, 25);
        lbNome.setBounds(20, 50, 100, 25);
        tfNome.setBounds(100, 50, 360, 25);
        lbGerente.setBounds(20, 90, 100, 25);
        cbGerente.setBounds(100, 90, 220, 25);
        btCadastrar.setBounds(200, 250, 100, 25);

        // Adição dos componentes de interface ao container.
        cp.add(lbTitulo);
        cp.add(lbNome);
        cp.add(tfNome);
        cp.add(lbGerente);
        cp.add(cbGerente);
        cp.add(btCadastrar);


        btCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)  {
                btCadastrarAction();
            }
        });
    } // Final do construtor.

    private void btCadastrarAction() { // Método acionado pelo clique no botão Cadastrar.
        List<String> erros = new ArrayList<String>();
        erros = new DepartamentoController().insereDepartamento(
                tfNome.getText(),
                (Funcionario) cbGerente.getSelectedItem());
        if (erros.get(0) == null) { // Se o primeiro elemento do ArrayList for null.
            JOptionPane.showMessageDialog(this, "Departamento cadastrado com sucesso.",
                    "Informação", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(false); // Fecha a janela.
        } else { // Se o primeiro elemento do ArrayList não for null.
            String mensagem = "Não foi possível cadastrar o departamento:\n";
            for (String e : erros) // Cria uma mensagem contendo todos os erros armazenados no ArrayList.
                mensagem = mensagem + e + "\n";
            JOptionPane.showMessageDialog(this, mensagem, "Erros", JOptionPane.ERROR_MESSAGE);
        }
    }
}
