import java.awt.*;
import java.util.stream.Collectors;
import javax.swing.*;
import java.util.List;

public class AdminsInfoPanel extends JPanel {

    public AdminsInfoPanel() {
        setLayout(new BorderLayout(20, 20));
        setBackground(UtilMaterial.BG); 
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Título ajustado
        JLabel title = UtilMaterial.h1("Informações e Funções de Administrador");
        add(title, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(UtilMaterial.BG);
        content.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // Seção 1: Lista de Administradores
        JLabel lblAdminsTitle = new JLabel("<html><h2 style='color: " + UtilMaterial.PRIMARY_DARK.getRGB() + ";'>Administradores Ativos:</h2></html>");
        lblAdminsTitle.setFont(UtilMaterial.H1);
        lblAdminsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblAdminsTitle);
        content.add(Box.createVerticalStrut(10));

        // Note: Requer RepositorioUsuarios.lista para funcionar
        List<Usuario> admins = RepositorioUsuarios.lista.stream()
                .filter(u -> u.admin && u.ativo)
                .collect(Collectors.toList());

        if (admins.isEmpty()) {
            JLabel noAdmin = new JLabel("Nenhum administrador ativo encontrado na base de dados.");
            noAdmin.setFont(UtilMaterial.PLAIN);
            content.add(noAdmin);
        } else {
            JList<String> adminList = new JList<>(admins.stream()
                    .map(u -> "Nome: " + u.nome + " | Username: " + u.username)
                    .toArray(String[]::new));
            adminList.setFont(UtilMaterial.PLAIN);
            adminList.setFixedCellHeight(25);
            adminList.setBorder(BorderFactory.createLineBorder(UtilMaterial.PRIMARY, 1));
            JScrollPane scroll = new JScrollPane(adminList);
            scroll.setMaximumSize(new Dimension(800, 150));
            scroll.setAlignmentX(Component.LEFT_ALIGNMENT);
            content.add(scroll);
        }
        
        content.add(Box.createVerticalStrut(30));

        // Seção 2: Funções de Administrador
        JLabel lblFuncTitle = new JLabel("<html><h2 style='color: " + UtilMaterial.PRIMARY_DARK.getRGB() + ";'>Funções Exclusivas de Administrador:</h2></html>");
        lblFuncTitle.setFont(UtilMaterial.H1);
        lblFuncTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblFuncTitle);
        content.add(Box.createVerticalStrut(10));

        String funcoes = "<html>" +
                "<p style='margin-bottom: 15px; font-family: Segoe UI; font-size: 15pt; color: " + UtilMaterial.TEXT.getRGB() + ";'>O perfil Administrador possui controle total sobre o sistema, incluindo:</p>" +
                "<ul>" +
                "<li><b>Gestão Completa de Usuários:</b> Cadastro, Edição de Perfil e <u>Controle de Status</u> (Ativar/Inativar contas).</li>" +
                "<li><b>Exclusão de Conteúdo:</b> Acesso à interface de <u>Exclusão de Recursos</u> (Artigos, Vídeos, Cursos, etc.) cadastrados por qualquer usuário.</li>" +
                "<li><b>Configurações:</b> Visualização rápida dos demais administradores ativos.</li>" +
                "</ul>" +
                "</html>";

        JLabel lblFuncoes = new JLabel(funcoes);
        lblFuncoes.setFont(UtilMaterial.PLAIN);
        lblFuncoes.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(lblFuncoes);

        add(new JScrollPane(content), BorderLayout.CENTER);
    }
}