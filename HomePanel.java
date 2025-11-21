import java.awt.*;
import javax.swing.*;

public class HomePanel extends JPanel {

    public HomePanel() {

        // Puxa as cores de UtilMaterial
        Color BG = UtilMaterial.BG;
        Color PRIMARY_DARK = UtilMaterial.PRIMARY_DARK;

        setLayout(new BorderLayout(0, 10)); 
        setBackground(BG);

        // Título estilizado (AGORA USA UtilMaterial.h1())
        JLabel title = UtilMaterial.h1("Página Inicial — Sistema de Curadoria"); 
        title.setBorder(BorderFactory.createEmptyBorder(25, 0, 15, 0));
        add(title, BorderLayout.NORTH);

        // Texto informativo
        JTextArea info = new JTextArea(
                "O Sistema de Curadoria de Recursos Educacionais permite cadastrar, pesquisar e visualizar " +
                "conteúdos focados em temas críticos da Tecnologia.\n\n" +
                "Funcionalidades disponíveis:\n" +
                "• Cadastrar novos Recursos (Artigos, Cursos, Vídeos ou Podcasts)\n" +
                "• Visualizar e pesquisar conteúdos cadastrados\n" +
                "• Foco em: IA Responsável, Cibersegurança e Ética Digital\n" +
                "• Administradores: Acesso à Gestão de Usuários e Exclusão de Recursos\n\n" +
                "Use o menu lateral esquerdo para navegar e interagir com o sistema."
        );
        info.setEditable(false);
        info.setBackground(BG);
        info.setFont(UtilMaterial.PLAIN); 
        info.setForeground(PRIMARY_DARK);
        info.setLineWrap(true);
        info.setWrapStyleWord(true);
        
        info.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Centraliza o JTextArea
        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        center.setBackground(BG);
        center.add(info);
        
        add(center, BorderLayout.CENTER);

        // Rodapé
        JPanel footer = new JPanel();
        footer.setBackground(BG);
        JLabel version = new JLabel("© 2025 Sistema de Curadoria e Compartilhamento");
        version.setFont(new Font("Arial", Font.ITALIC, 12));
        footer.add(version);
        add(footer, BorderLayout.SOUTH);
    }
}