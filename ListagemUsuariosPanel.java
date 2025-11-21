import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.List;

public class ListagemUsuariosPanel extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;
    private JTextField tfNome, tfIdade;
    private JComboBox<String> cbAdmin, cbInteresse1, cbInteresse2;
    private JButton btnInactivate; // AGORA É MEMBRO DA CLASSE
    private Usuario usuarioSelecionado = null; 

    public ListagemUsuariosPanel() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        // Título ajustado
        JLabel title = UtilMaterial.h1("Gestão e Edição de Usuários");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Username", "Nome", "Idade", "Admin", "Ativo"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); 

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);
        
        // Listener para selecionar e carregar dados para edição
        tabela.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabela.getSelectedRow() != -1) {
                carregarParaEdicao(tabela.getSelectedRow());
            }
        });

        // Painel de Edição
        JPanel editPanel = new JPanel(new BorderLayout());
        editPanel.setBackground(UtilMaterial.PRIMARY.brighter()); 
        editPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Formulário de edição
        JPanel form = new JPanel(new GridLayout(2, 6, 10, 10));
        form.setBackground(UtilMaterial.PRIMARY.brighter());
        
        // Campos de Edição
        tfNome = new JTextField(15); tfNome.setFont(UtilMaterial.PLAIN);
        tfIdade = new JTextField(5); tfIdade.setFont(UtilMaterial.PLAIN);
        cbAdmin = new JComboBox<>(new String[]{"Comum", "Admin"}); cbAdmin.setFont(UtilMaterial.PLAIN);
        cbInteresse1 = new JComboBox<>(new String[]{"IA Responsável", "Cibersegurança", "Ética Digital"}); cbInteresse1.setFont(UtilMaterial.PLAIN);
        cbInteresse2 = new JComboBox<>(new String[]{"", "IA Responsável", "Cibersegurança", "Ética Digital"}); cbInteresse2.setFont(UtilMaterial.PLAIN); 
        
        form.add(new JLabel("Nome:")); form.add(tfNome);
        form.add(new JLabel("Idade:")); form.add(tfIdade);
        form.add(new JLabel("Tipo:")); form.add(cbAdmin);
        form.add(new JLabel("I. 1:")); form.add(cbInteresse1);
        form.add(new JLabel("I. 2:")); form.add(cbInteresse2);
        form.add(new JLabel("")); 
        
        // Painel de botões
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnPanel.setBackground(UtilMaterial.PRIMARY.brighter());
        
        JButton btnEdit = UtilMaterial.materialButton("Salvar Edição");
        btnInactivate = UtilMaterial.materialButton("Inativar/Ativar Conta"); 
        
        btnEdit.addActionListener(e -> editarUsuario());
        btnInactivate.addActionListener(e -> alternarStatusUsuario()); 
        
        btnPanel.add(btnEdit);
        btnPanel.add(btnInactivate);
        
        editPanel.add(form, BorderLayout.CENTER);
        editPanel.add(btnPanel, BorderLayout.SOUTH);

        add(editPanel, BorderLayout.SOUTH);

        atualizarLista();
    }
    
    private void carregarParaEdicao(int viewRowIndex) {
        // Converte o índice da tabela visual (após ordenação) para o índice do modelo
        int modelRowIndex = tabela.convertRowIndexToModel(viewRowIndex);
        String username = model.getValueAt(modelRowIndex, 0).toString();
        
        usuarioSelecionado = RepositorioUsuarios.lista.stream() 
            .filter(u -> u.username.equals(username))
            .findFirst()
            .orElse(null);
            
        if (usuarioSelecionado != null) {
            tfNome.setText(usuarioSelecionado.nome);
            tfIdade.setText(String.valueOf(usuarioSelecionado.idade));
            cbAdmin.setSelectedItem(usuarioSelecionado.admin ? "Admin" : "Comum");
            cbInteresse1.setSelectedItem(usuarioSelecionado.interesse1);
            cbInteresse2.setSelectedItem(usuarioSelecionado.interesse2 != null ? usuarioSelecionado.interesse2 : "");
            
            // ATUALIZA O TEXTO DO BOTÃO (Correção do escopo)
            btnInactivate.setText(usuarioSelecionado.ativo ? "Inativar Conta" : "Reativar Conta");
        }
    }

    private void editarUsuario() {
        if (usuarioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela para editar.");
            return;
        }

        try {
            int novaIdade = Integer.parseInt(tfIdade.getText().trim());
            String novoNome = tfNome.getText().trim();
            boolean novoAdmin = cbAdmin.getSelectedItem().equals("Admin");
            String novoI1 = (String) cbInteresse1.getSelectedItem();
            String novoI2 = (String) cbInteresse2.getSelectedItem();
            
            if (novoI2 != null && novoI2.isEmpty()) novoI2 = null;
            
            usuarioSelecionado.nome = novoNome;
            usuarioSelecionado.idade = novaIdade;
            usuarioSelecionado.admin = novoAdmin;
            usuarioSelecionado.interesse1 = novoI1;
            usuarioSelecionado.interesse2 = novoI2;
            
            RepositorioUsuarios.salvar();
            atualizarLista();
            JOptionPane.showMessageDialog(this, "✅ Usuário '" + usuarioSelecionado.username + "' editado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "❌ Erro: Idade inválida.");
        }
    }

    private void alternarStatusUsuario() {
        if (usuarioSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário na tabela.");
            return;
        }
        
        String acao = usuarioSelecionado.ativo ? "inativar" : "reativar";

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente " + acao + " a conta de: " + usuarioSelecionado.nome + "?",
                "Confirmação de Ação", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            if (usuarioSelecionado.ativo) {
                RepositorioUsuarios.inativar(usuarioSelecionado.username); 
                JOptionPane.showMessageDialog(this, "Conta inativada com sucesso!");
            } else {
                RepositorioUsuarios.ativar(usuarioSelecionado.username); 
                JOptionPane.showMessageDialog(this, "Conta reativada com sucesso!");
            }
            atualizarLista();
            usuarioSelecionado = null; 
        }
    }

    public void atualizarLista() {
        model.setRowCount(0);
        for (Usuario u : RepositorioUsuarios.lista) { 
            model.addRow(new Object[]{u.username, u.nome, u.idade, u.admin ? "Sim" : "Não", u.ativo ? "Sim" : "Não"});
        }
    }
}