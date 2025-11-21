public class Usuario {

    public String username;
    public String senha;
    public String nome;
    public int idade;
    public boolean admin;
    public boolean ativo;
    public String interesse1;
    public String interesse2;

    public Usuario(
            String username,
            String senha,
            String nome,
            int idade,
            boolean admin,
            boolean ativo,
            String interesse1,
            String interesse2
    ) {
        this.username = username;
        this.senha = senha;
        this.nome = nome;
        this.idade = idade;
        this.admin = admin;
        this.ativo = ativo;
        this.interesse1 = interesse1;
        this.interesse2 = interesse2;
    }
}
