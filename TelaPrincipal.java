import java.awt.*;
import javax.swing.*;

public class TelaPrincipal extends JFrame {

    private CardLayout cardLayout;
    private JPanel cards;
    private Usuario usuario;

    // Declaração de todos os painéis para escopo
    private HomePanel home;
    private CadastroUsuarioPanel cadUserPanel;
    private CadastroRecursoPanel cadRec;
    private ListagemConteudosPanel painelListRecursos;
    private ListagemUsuariosPanel listUsers;
    private PainelExcluirRecurso painelExcluir;
    private PerfilPanel perfil;
    private AdminsInfoPanel adminsInfo;

    public TelaPrincipal(Usuario usuario) {

        this.usuario = usuario;

        setTitle("Sistema de Curadoria — " + usuario.nome + (usuario.admin ? " (Admin)" : ""));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ---------- SIDEBAR (MENU) ----------
        JPanel side = new JPanel();
        side.setBackground(UtilMaterial.PRIMARY_DARK); 
        side.setPreferredSize(new Dimension(240, 700));
        side.setLayout(new GridLayout(0, 1, 10, 10));
        side.setBorder(BorderFactory.createEmptyBorder(16, 12, 16, 12));

        // Info do Usuário no Sidebar
        JLabel lblUser = new JLabel(
            "<html><b style='color:white;'>Perfil: " + (usuario.admin ? "Admin" : "Comum") +
            "</b><br/><span style='color:"+ UtilMaterial.BG_HEX +"; font-size: 10pt;'>" + 
            usuario.nome + "</span></html>"
        );
        lblUser.setFont(UtilMaterial.PLAIN);
        lblUser.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        side.add(lblUser);

        // ---------- BOTÕES COMUNS (Todos os usuários) ----------
        
        JButton bHome = UtilMaterial.materialButton("Página Inicial");
        JButton bPerfil = UtilMaterial.materialButton("Meu Perfil");
        JButton bCadRec = UtilMaterial.materialButton("Cadastrar Recurso");
        // Ajuste de texto: "Listar Conteúdos" -> "Visualizar Recursos"
        JButton bListRec = UtilMaterial.materialButton("Visualizar Recursos");
        
        side.add(bHome);
        side.add(bPerfil);
        side.add(bCadRec);
        side.add(bListRec);

        // Variáveis para botões exclusivos de Admin (só instanciadas se admin)
        JButton bCadUser = null;
        JButton bListUsers = null;
        JButton bExcluir = null;
        JButton bAdmins = null;

        // ---------- OPÇÕES EXCLUSIVAS DO ADMINISTRADOR ----------
        if (usuario.admin) {
            
            bCadUser = UtilMaterial.materialButton("Cadastrar Usuário");
            // Ajuste de texto: "Gerenciar Usuários" -> "Gestão de Usuários"
            bListUsers = UtilMaterial.materialButton("Gestão de Usuários");
            bExcluir = UtilMaterial.materialButton("Excluir Recurso");
            // Ajuste de texto: "Info Administradores" -> "Informações Admin"
            bAdmins = UtilMaterial.materialButton("Informações Admin");

            side.add(bCadUser);
            side.add(bListUsers);
            side.add(bExcluir);
            side.add(bAdmins);
        } 
        
        // Espaço para alinhar Sair ao final
        side.add(new JLabel("")); 
        
        JButton bSair = UtilMaterial.materialButton("Sair");
        side.add(bSair);


        add(side, BorderLayout.WEST);

        // ---------- CARDS DE CONTEÚDO ----------
        cardLayout = new CardLayout();
        cards = new JPanel(cardLayout);

        // Instanciação dos Painéis
        home = new HomePanel();
        // A lambda () -> {} é para atualizar a lista de usuários após o cadastro
        cadUserPanel = new CadastroUsuarioPanel(() -> { 
            if (usuario.admin && listUsers != null) {
                listUsers.atualizarLista();
            }
        }); 
        cadRec = new CadastroRecursoPanel();
        painelListRecursos = new ListagemConteudosPanel();
        listUsers = new ListagemUsuariosPanel();
        
        // Novos Painéis
        perfil = new PerfilPanel(usuario);
        adminsInfo = new AdminsInfoPanel();
        painelExcluir = new PainelExcluirRecurso(); 

        // Adiciona todos os cards 
        cards.add(home, "home");
        cards.add(cadUserPanel, "cadUser"); 
        cards.add(listUsers, "listUsers");
        cards.add(painelExcluir, "excluir");
        cards.add(cadRec, "cadRec");
        cards.add(painelListRecursos, "listRec");
        cards.add(perfil, "perfil");
        cards.add(adminsInfo, "adminsInfo"); 

        add(cards, BorderLayout.CENTER);

        // ---------- AÇÕES PARA TODOS OS USUÁRIOS ----------
        bHome.addActionListener(e -> cardLayout.show(cards, "home"));
        bPerfil.addActionListener(e -> cardLayout.show(cards, "perfil"));
        bCadRec.addActionListener(e -> cardLayout.show(cards, "cadRec"));
        bListRec.addActionListener(e -> { painelListRecursos.refreshList(); cardLayout.show(cards, "listRec"); });

        bSair.addActionListener(e -> { new TelaLogin().setVisible(true); dispose(); });
        
        // ---------- AÇÕES EXCLUSIVAS DO ADMIN ----------
        if (usuario.admin) {
            bCadUser.addActionListener(e -> cardLayout.show(cards, "cadUser"));
            bListUsers.addActionListener(e -> { listUsers.atualizarLista(); cardLayout.show(cards, "listUsers"); }); 
            bExcluir.addActionListener(e -> { painelExcluir.refreshList(); cardLayout.show(cards, "excluir"); });
            bAdmins.addActionListener(e -> cardLayout.show(cards, "adminsInfo"));
        }

        cardLayout.show(cards, "home");
    }
}