import java.io.*;
import java.util.*;

public class RepositorioUsuarios {

    public static List<Usuario> lista = new ArrayList<>();
    private static final String FILE = "usuarios.txt";

    static {
        carregar();
    }

    // ---------------------------------------------------------
    // ADICIONAR
    // ---------------------------------------------------------
    public static boolean adicionar(Usuario u) {
        // evita duplicação de usernames
        for (Usuario x : lista) {
            if (x.username.equalsIgnoreCase(u.username)) return false;
        }
        lista.add(u);
        salvar();
        return true;
    }

    // ---------------------------------------------------------
    // LOGIN
    // ---------------------------------------------------------
    public static Usuario login(String user, String senha) {
        for (Usuario u : lista) {
            if (u.username.equalsIgnoreCase(user)
                    && u.senha.equals(senha)
                    && u.ativo) { // Só permite login se ativo
                return u;
            }
        }
        return null;
    }

    // ---------------------------------------------------------
    // INATIVAR CONTA
    // ---------------------------------------------------------
    public static void inativar(String username) {
        for (Usuario u : lista) {
            if (u.username.equalsIgnoreCase(username)) {
                u.ativo = false;
                salvar();
                return;
            }
        }
    }
    
    // ---------------------------------------------------------
    // ATIVAR CONTA (Necessário para a Gestão de Usuários)
    // ---------------------------------------------------------
    public static void ativar(String username) {
        for (Usuario u : lista) {
            if (u.username.equalsIgnoreCase(username)) {
                u.ativo = true;
                salvar();
                return;
            }
        }
    }


    // ---------------------------------------------------------
    // CARREGAR USUÁRIOS DO ARQUIVO (Corrigido para 8 campos)
    // ---------------------------------------------------------
    public static void carregar() {
        lista.clear();
        File f = new File(FILE);
        if (!f.exists()) {
            System.out.println("Arquivo usuarios.txt não encontrado. Criando admin padrão...");
            criarAdminPadrao();
            salvar();
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                
                // Trata o 8º campo (interesse2) como opcional e garante pelo menos 7
                if (p.length >= 7) { 
                    
                    String interesse2 = p.length > 7 && !p[7].isEmpty() ? p[7] : null;

                    Usuario u = new Usuario(
                            p[0],                                 // 0: username
                            p[1],                                 // 1: senha
                            p[2],                                 // 2: nome
                            Integer.parseInt(p[3]),               // 3: idade (int)
                            Boolean.parseBoolean(p[4]),           // 4: admin (boolean)
                            Boolean.parseBoolean(p[5]),           // 5: ativo (boolean)
                            p[6],                                 // 6: interesse1
                            interesse2                            // 7: interesse2 (String ou null)
                    );
                    lista.add(u);
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private static void criarAdminPadrao() {
        Usuario admin = new Usuario(
                "admin",
                "123",
                "Administrador Sistema",
                30,
                true,
                true,
                "IA Responsável",
                "Cibersegurança"
        );
        lista.add(admin);
    }

    // ---------------------------------------------------------
    // SALVAR USUÁRIOS NO ARQUIVO
    // ---------------------------------------------------------
    public static void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {
            for (Usuario u : lista) {
                // Certifica que null é salvo como string vazia
                String i2 = u.interesse2 == null ? "" : u.interesse2; 
                
                pw.println(
                        u.username + ";" +
                        u.senha + ";" +
                        u.nome + ";" +
                        u.idade + ";" +
                        u.admin + ";" +
                        u.ativo + ";" +
                        u.interesse1 + ";" +
                        i2
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}