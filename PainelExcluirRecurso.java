import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PainelExcluirRecurso extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;

    public PainelExcluirRecurso() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        // Título ajustado
        JLabel titulo = UtilMaterial.h1("Exclusão de Recursos (Acesso Admin)");
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(titulo, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Título", "Autor", "Tipo", "Categoria", "Cadastrado Por"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); 

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Botão Excluir
        JButton btnExcluir = UtilMaterial.materialButton("Excluir Recurso Selecionado");
        btnExcluir.addActionListener(e -> excluirRecurso());

        JPanel bottom = new JPanel();
        bottom.setBackground(UtilMaterial.BG);
        bottom.add(btnExcluir);

        add(bottom, BorderLayout.SOUTH);

        atualizarLista();
    }

    private void atualizarLista() {
        model.setRowCount(0);
        for (Recurso r : RepositorioConteudos.lista) { 
            model.addRow(new Object[]{r.titulo, r.autor, r.tipo, r.categoria, r.cadastradoPor});
        }
    }

    public void refreshList() {
        atualizarLista();
    }

    private void excluirRecurso() {
        int row = tabela.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um recurso na tabela para excluir.");
            return;
        }
        
        // Mapeia a linha selecionada da visualização para a linha do modelo, se houver ordenação
        int modelRow = tabela.convertRowIndexToModel(row); 

        String titulo = model.getValueAt(modelRow, 0).toString();
        String autor = model.getValueAt(modelRow, 1).toString();
        String tipo = model.getValueAt(modelRow, 2).toString();

        int confirm = JOptionPane.showConfirmDialog(this,
                "Deseja realmente excluir o recurso: " + titulo + " (" + tipo + ")?",
                "Confirmação de Exclusão", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            Recurso toRemove = null;
            // Busca o recurso na lista global (RepositorioConteudos.lista)
            for (Recurso r : RepositorioConteudos.lista) {
                if (r.titulo.equals(titulo) && r.autor.equals(autor) && r.tipo.equalsIgnoreCase(tipo)) {
                    toRemove = r;
                    break;
                }
            }
            if (toRemove != null) {
                RepositorioConteudos.lista.remove(toRemove);
                RepositorioConteudos.salvar(); 
                atualizarLista();
                JOptionPane.showMessageDialog(this, "✅ Recurso excluído com sucesso!");
            } else {
                JOptionPane.showMessageDialog(this, "❌ Erro: Recurso não encontrado na base de dados.");
            }
        }
    }
}