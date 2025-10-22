/**
 * Representa um estudante no sistema.
 * Implementa Comparable para permitir ordenação por nome.
 */
public class Estudante implements Comparable<Estudante> {
    // identificador único do estudante (ex: matrícula)
    private final String id;
    // nome completo do estudante
    private String nome;

    /**
     * Construtor da classe Estudante.
     * @param id identificador único (não null)
     * @param nome nome do estudante
     */
    public Estudante(String id, String nome) {
        this.id = id.trim();
        this.nome = nome.trim();
    }

    // ===== Getters e Setter =====
    public String getId() { return id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    /**
     * Permite ordenar estudantes alfabeticamente pelo nome.
     * Ignora diferenças entre maiúsculas e minúsculas.
     */
    @Override
    public int compareTo(Estudante outro) {
        return this.nome.compareToIgnoreCase(outro.nome);
    }

    /**
     * Dois estudantes são considerados iguais se tiverem o mesmo ID.
     * Isso permite uso correto em coleções (como HashSet e HashMap).
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;                 // mesmo objeto
        if (!(o instanceof Estudante)) return false; // tipo diferente
        Estudante e = (Estudante) o;
        return id.equals(e.id);                     // igualdade por ID
    }

    /**
     * hashCode consistente com equals (baseado em id).
     * Importante para uso em HashSet/HashMap.
     */
    @Override
    public int hashCode() {
        return id.hashCode();
    }

    /**
     * Retorna uma representação textual do estudante.
     * Útil para relatórios ou debug.
     */
    @Override
    public String toString() {
        return id + " - " + nome;
    }
}
