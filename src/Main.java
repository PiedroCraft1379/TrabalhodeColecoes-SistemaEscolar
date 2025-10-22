/**
 * Classe principal do programa.
 * Popula dados de exemplo e gera o arquivo output.txt.
 */
public class Main {
    public static void main(String[] args) {
        SistemaDeGerenciamento sistema = new SistemaDeGerenciamento();

        // ===== CADASTRO DE ESTUDANTES =====
        sistema.adicionarEstudante(new Estudante("2025001", "Ana Silva"));
        sistema.adicionarEstudante(new Estudante("2025002", "Bruno Costa"));
        sistema.adicionarEstudante(new Estudante("2025003", "Carla Souza"));

        // ===== CADASTRO DE DISCIPLINAS =====
        sistema.adicionarDisciplina(new Disciplina("MAT101", "Matemática"));
        sistema.adicionarDisciplina(new Disciplina("POR101", "Português"));
        sistema.adicionarDisciplina(new Disciplina("FIS101", "Física"));

        // ===== MATRÍCULAS =====
        sistema.matricular("2025001", "MAT101");
        sistema.matricular("2025001", "POR101");
        sistema.matricular("2025002", "MAT101");
        sistema.matricular("2025002", "FIS101");
        sistema.matricular("2025003", "POR101");

        // ===== NOTAS =====
        sistema.adicionarNota("2025001", "MAT101", 8.5);
        sistema.adicionarNota("2025001", "MAT101", 7.0);
        sistema.adicionarNota("2025001", "POR101", 9.0);

        sistema.adicionarNota("2025002", "MAT101", 6.0);
        sistema.adicionarNota("2025002", "FIS101", 7.5);

        sistema.adicionarNota("2025003", "POR101", 5.5);
        sistema.adicionarNota("2025003", "POR101", 6.0);

        // ===== RELATÓRIO =====
        sistema.gerarRelatorio("output.txt", 6.0);
    }
}

