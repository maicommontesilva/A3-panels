import java.io.*;
import java.util.*;

public class RepositorioConteudos {

    public static List<Recurso> lista = new ArrayList<>();
    private static final String ARQ = "recursos.txt";

    // ---------------------------------------------------------
    // CARREGA RECURSOS DO ARQUIVO
    // ---------------------------------------------------------
    public static void carregar() {
        lista.clear();
        File f = new File(ARQ);
        if (!f.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] p = linha.split(";");
                if (p.length == 5) {
                    lista.add(new Recurso(p[0], p[1], p[2], p[3], p[4]));
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao carregar recursos: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // SALVA RECURSOS NO ARQUIVO
    // ---------------------------------------------------------
    public static void salvar() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(ARQ))) {
            for (Recurso r : lista) {
                pw.println(r.titulo + ";" + r.autor + ";" + r.categoria + ";" + r.tipo + ";" + r.cadastradoPor);
            }
        } catch (Exception e) {
            System.out.println("Erro ao salvar recursos: " + e.getMessage());
        }
    }

    // ---------------------------------------------------------
    // ADICIONAR — evita duplicados (título + autor + tipo)
    // ---------------------------------------------------------
    public static boolean adicionar(Recurso r) {

        for (Recurso x : lista) {
            if (x.titulo.equalsIgnoreCase(r.titulo) &&
                x.autor.equalsIgnoreCase(r.autor) &&
                x.tipo.equalsIgnoreCase(r.tipo)) {

                return false; // recurso duplicado
            }
        }

        lista.add(r);
        salvar();
        return true;
    }

    // ---------------------------------------------------------
    // REMOVER
    // ---------------------------------------------------------
    public static boolean remover(Recurso r) {
        boolean ok = lista.remove(r);
        if (ok) salvar();
        return ok;
    }

    // ---------------------------------------------------------
    // LISTAGEM ORDENADA POR TÍTULO
    // ---------------------------------------------------------
    public static List<Recurso> todosOrdenadosPorTitulo() {
        List<Recurso> ordenada = new ArrayList<>(lista);
        ordenada.sort(Comparator.comparing(a -> a.titulo.toLowerCase()));
        return ordenada;
    }
}
