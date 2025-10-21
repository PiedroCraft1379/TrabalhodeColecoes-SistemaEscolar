import java.util.*;
import java.util.stream.Collectors;

// Parte C: Gerenciamento de matrículas, notas e médias
public class ParteC {

    // Classe interna que representa uma matrícula
    public static class Matricula {
        private int idEstudante;           // ID do estudante
        private String codigoDisciplina;   // Código da disciplina
        private double nota;               // Nota obtida

        public Matricula(int idEstudante, String codigoDisciplina, double nota) {
            this.idEstudante = idEstudante;
            this.codigoDisciplina = codigoDisciplina;
            this.nota = nota;
        }

        public int getIdEstudante() {
            return idEstudante;
        }

        public String getCodigoDisciplina() {
            return codigoDisciplina;
        }

        public double getNota() {
            return nota;
        }
    }

    // Classe que gerencia todas as matrículas
    public static class RegistroAcademico {
        private Map<Integer, List<Matricula>> matriculasMap; // ID do estudante -> lista de matrículas

        public RegistroAcademico() {
            this.matriculasMap = new HashMap<>();
        }

        // Adiciona matrícula de um estudante
        public void adicionarMatricula(int idEstudante, String codigoDisciplina, double nota) {
            Matricula m = new Matricula(idEstudante, codigoDisciplina, nota);
            matriculasMap.computeIfAbsent(idEstudante, k -> new ArrayList<>()).add(m);
        }

        // Retorna todas as matrículas de um estudante
        public List<Matricula> obterMatriculas(int idEstudante) {
            return matriculasMap.getOrDefault(idEstudante, new ArrayList<>());
        }

        // Retorna a nota de um estudante em uma disciplina específica
        public Optional<Double> obterNota(int idEstudante, String codigoDisciplina) {
            List<Matricula> lista = matriculasMap.get(idEstudante);
            if (lista != null) {
                return lista.stream()
                        .filter(m -> m.getCodigoDisciplina().equals(codigoDisciplina))
                        .map(Matricula::getNota)
                        .findFirst();
            }
            return Optional.empty();
        }

        // Remove a matrícula de um estudante em uma disciplina
        public boolean removerMatricula(int idEstudante, String codigoDisciplina) {
            List<Matricula> lista = matriculasMap.get(idEstudante);
            if (lista != null) {
                boolean removed = lista.removeIf(m -> m.getCodigoDisciplina().equals(codigoDisciplina));
                if (lista.isEmpty()) {
                    matriculasMap.remove(idEstudante);
                }
                return removed;
            }
            return false;
        }

        // Calcula a média das notas de um estudante
        public double mediaDoEstudante(int idEstudante) {
            List<Matricula> lista = matriculasMap.get(idEstudante);
            if (lista == null || lista.isEmpty()) return 0.0;
            return lista.stream().mapToDouble(Matricula::getNota).average().orElse(0.0);
        }

        // Calcula a média de uma disciplina considerando todos os estudantes
        public double mediaDaDisciplina(String codigoDisciplina) {
            List<Double> notas = new ArrayList<>();
            for (List<Matricula> lista : matriculasMap.values()) {
                lista.stream()
                        .filter(m -> m.getCodigoDisciplina().equals(codigoDisciplina))
                        .map(Matricula::getNota)
                        .forEach(notas::add);
            }
            return notas.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        }

        // Retorna os N estudantes com maior média
        public List<Integer> topNEstudantesPorMedia(int N) {
            return matriculasMap.keySet().stream()
                    .sorted((id1, id2) -> Double.compare(mediaDoEstudante(id2), mediaDoEstudante(id1)))
                    .limit(N)
                    .collect(Collectors.toList());
        }
    }

    // Método main para testar todos os métodos da Parte C
    public static void main(String[] args) {
        RegistroAcademico registro = new RegistroAcademico();

        // Adiciona matrículas
        registro.adicionarMatricula(1, "MAT101", 8.5);
        registro.adicionarMatricula(1, "FIS101", 7.0);
        registro.adicionarMatricula(2, "MAT101", 9.0);
        registro.adicionarMatricula(2, "FIS101", 6.5);
        registro.adicionarMatricula(3, "MAT101", 10.0);

        // Imprime matrículas do estudante 1
        System.out.println("Matrículas do estudante 1:");
        for (Matricula m : registro.obterMatriculas(1)) {
            System.out.println(m.getCodigoDisciplina() + " - " + m.getNota());
        }

        // Obtém nota específica
        System.out.println("Nota do estudante 2 em MAT101: " +
                registro.obterNota(2, "MAT101").orElse(0.0));

        // Remove matrícula
        boolean removido = registro.removerMatricula(1, "FIS101");
        System.out.println("Matrícula FIS101 removida do estudante 1? " + removido);

        // Calcula médias
        System.out.println("Média do estudante 1: " + registro.mediaDoEstudante(1));
        System.out.println("Média da disciplina MAT101: " + registro.mediaDaDisciplina("MAT101"));

        // Top 2 estudantes por média
        System.out.println("Top 2 estudantes por média: " + registro.topNEstudantesPorMedia(2));
    }
}
