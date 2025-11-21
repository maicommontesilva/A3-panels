public class Recurso {

    public String titulo;
    public String autor;
    public String categoria;    // IA Responsável, Cibersegurança, Ética Digital
    public String tipo;         // Artigo, Curso, Vídeo, Podcast
    public String cadastradoPor;

    public Recurso(String titulo, String autor, String categoria, String tipo, String cadastradoPor) {
        this.titulo = titulo;
        this.autor = autor;
        this.categoria = categoria;
        this.tipo = tipo;
        this.cadastradoPor = cadastradoPor;
    }
}
