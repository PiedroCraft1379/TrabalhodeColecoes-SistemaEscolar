import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Representa a matrícula de um estudante em uma disciplina.
 * Armazena as notas e calcula a média.
 */
public class Matricula {
    private final String idEstudante;
    private final String codigoDisciplina;
    private final List<Double> notas = new ArrayList<>();

    public Matricula(String idEstudante, String codigoDisciplina) {
        this.idEstudante = idEstudante;
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getIdEstudante() { return idEstudante; }
    public String getCodigoDisciplina() { return codigoDisciplina; }
    public List<Double> getNotas() { return notas; }

    /**
     * Adiciona uma nota (de 0 a 10). Caso contrário, lança exceção.
     */
    public void adicionarNota(double nota) {
        if (nota >= 0.0 && nota <= 10.0) {
            notas.add(nota);
        } else {
            throw new IllegalArgumentException("Nota inválida: " + nota);
        }
    }

    /**
     * Calcula a média aritmética simples das notas.
     */
    public double getMedia() {
        if (notas.isEmpty()) return 0.0;
        OptionalDouble media = notas.stream().mapToDouble(Double::doubleValue).average();
        return media.isPresent() ? media.getAsDouble() : 0.0;
    }

    @Override
    public String toString() {
        return "Disciplina: " + codigoDisciplina +
                ", Notas: " + notas +
                ", Média: " + String.format("%.2f", getMedia());
    }
}
