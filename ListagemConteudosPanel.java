import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

public class ListagemConteudosPanel extends JPanel {

    private JTable tabela;
    private DefaultTableModel model;

    public ListagemConteudosPanel() {
        setLayout(new BorderLayout());
        setBackground(UtilMaterial.BG);

        // Título ajustado
        JLabel title = UtilMaterial.h1("Visualização de Recursos Cadastrados");
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        model = new DefaultTableModel(new Object[]{"Título", "Autor", "Tipo", "Categoria", "Cadastrado Por"}, 0);
        tabela = new JTable(model);
        tabela.setFont(UtilMaterial.PLAIN);
        tabela.setRowHeight(25);
        tabela.setAutoCreateRowSorter(true); // Melhoria: Ordenação por coluna

        JScrollPane scroll = new JScrollPane(tabela);
        add(scroll, BorderLayout.CENTER);

        // Adiciona um botão de atualização para garantir que a lista esteja sempre atualizada
        JButton btnRefresh = UtilMaterial.materialButton("Atualizar Lista");
        btnRefresh.addActionListener(e -> atualizarLista());
        
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottom.setBackground(UtilMaterial.BG);
        bottom.add(btnRefresh);
        add(bottom, BorderLayout.SOUTH);

        atualizarLista();
    }

    private void atualizarLista() {
        model.setRowCount(0);
        // Usa RepositorioConteudos.todosOrdenadosPorTitulo()
        List<Recurso> recursos = RepositorioConteudos.todosOrdenadosPorTitulo(); 
        for (Recurso r : recursos) {
            model.addRow(new Object[]{r.titulo, r.autor, r.tipo, r.categoria, r.cadastradoPor});
        }
    }

    public void refreshList() {
        atualizarLista();
    }
}