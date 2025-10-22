/**
 * Representa uma disciplina (matéria).
 * Implementa Comparable para permitir ordenação por nome.
 */
public class Disciplina implements Comparable<Disciplina> {
    private final String codigo;
    private final String nome;

    public Disciplina(String codigo, String nome) {
        this.codigo = codigo.trim();
        this.nome = nome.trim();
    }

    public String getCodigo() { return codigo; }
    public String getNome() { return nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Disciplina)) return false;
        Disciplina d = (Disciplina) o;
        return codigo.equalsIgnoreCase(d.codigo);
    }

    @Override
    public int hashCode() {
        return codigo.toLowerCase().hashCode();
    }

    @Override
    public int compareTo(Disciplina outra) {
        return this.nome.compareToIgnoreCase(outra.nome);
    }

    @Override
    public String toString() {
        return codigo + " - " + nome;
    }
}
