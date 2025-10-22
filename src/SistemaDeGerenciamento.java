import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Classe que gerencia estudantes, disciplinas e matrículas.
 * Usa coleções Java: List, Set e Map.
 */
public class SistemaDeGerenciamento {
    // Lista de estudantes
    private final List<Estudante> estudantes = new ArrayList<>();

    // Conjunto de disciplinas (sem duplicatas)
    private final Set<Disciplina> disciplinas = new HashSet<>();

    // Mapa: idEstudante -> lista de matrículas
    private final Map<String, List<Matricula>> matriculasPorEstudante = new HashMap<>();

    // Mapa auxiliar: código -> disciplina
    private final Map<String, Disciplina> mapaDisciplinas = new HashMap<>();

    // ======== ESTUDANTES ========

    public void adicionarEstudante(Estudante e) {
        if (buscarEstudantePorId(e.getId()) != null) {
            System.out.println("Estudante já cadastrado: " + e.getId());
            return;
        }
        estudantes.add(e);
    }

    public boolean removerEstudantePorId(String id) {
        Estudante e = buscarEstudantePorId(id);
        if (e == null) return false;
        estudantes.remove(e);
        matriculasPorEstudante.remove(id);
        return true;
    }

    public Estudante buscarEstudantePorId(String id) {
        for (Estudante e : estudantes) {
            if (e.getId().equals(id)) return e;
        }
        return null;
    }

    public void ordenarEstudantesPorNome() {
        Collections.sort(estudantes);
    }

    public List<Estudante> getEstudantes() {
        return estudantes;
    }

    // ======== DISCIPLINAS ========
    public boolean adicionarDisciplina(Disciplina d) {
        if (disciplinas.add(d)) {
            mapaDisciplinas.put(d.getCodigo().toLowerCase(), d);
            return true;
        }
        return false;
    }

    public Disciplina buscarDisciplinaPorCodigo(String codigo) {
        return mapaDisciplinas.get(codigo.toLowerCase());
    }

    // ======== MATRÍCULAS ========

    public boolean matricular(String idEstudante, String codigoDisciplina) {
        Estudante e = buscarEstudantePorId(idEstudante);
        Disciplina d = buscarDisciplinaPorCodigo(codigoDisciplina);
        if (e == null || d == null) return false;

        List<Matricula> lista = matriculasPorEstudante.computeIfAbsent(idEstudante, k -> new ArrayList<>());

        for (Matricula m : lista) {
            if (m.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina)) return false;
        }

        lista.add(new Matricula(idEstudante, codigoDisciplina));
        return true;
    }

    public boolean adicionarNota(String idEstudante, String codigoDisciplina, double nota) {
        List<Matricula> lista = matriculasPorEstudante.get(idEstudante);
        if (lista == null) return false;
        for (Matricula m : lista) {
            if (m.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina)) {
                m.adicionarNota(nota);
                return true;
            }
        }
        return false;
    }

    // ======== CÁLCULOS ========

    public double getMediaEstudante(String idEstudante) {
        List<Matricula> lista = matriculasPorEstudante.get(idEstudante);
        if (lista == null || lista.isEmpty()) return 0.0;
        double soma = 0.0;
        int cont = 0;
        for (Matricula m : lista) {
            if (!m.getNotas().isEmpty()) {
                soma += m.getMedia();
                cont++;
            }
        }
        return (cont == 0) ? 0.0 : soma / cont;
    }

    public double getMediaDisciplina(String codigoDisciplina) {
        double soma = 0.0;
        int cont = 0;
        for (List<Matricula> lista : matriculasPorEstudante.values()) {
            for (Matricula m : lista) {
                if (m.getCodigoDisciplina().equalsIgnoreCase(codigoDisciplina) && !m.getNotas().isEmpty()) {
                    soma += m.getMedia();
                    cont++;
                }
            }
        }
        return (cont == 0) ? 0.0 : soma / cont;
    }

    public List<Estudante> getEstudantesAprovados(double mediaMinima) {
        List<Estudante> aprovados = new ArrayList<>();
        for (Estudante e : estudantes) {
            if (getMediaEstudante(e.getId()) >= mediaMinima) {
                aprovados.add(e);
            }
        }
        return aprovados;
    }

    // ======== RELATÓRIO ========

    public void gerarRelatorio(String caminhoArquivo, double mediaAprovacao) {
        ordenarEstudantesPorNome();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo))) {
            bw.write("RELATÓRIO ESCOLAR\n");
            bw.write("=================\n\n");

            bw.write("Estudantes (ordenados):\n");
            for (Estudante e : estudantes) {
                bw.write(e.toString() + "\n");
                List<Matricula> lista = matriculasPorEstudante.getOrDefault(e.getId(), Collections.emptyList());
                for (Matricula m : lista) {
                    bw.write("  " + m.toString() + "\n");
                }
                bw.write(String.format("  Média geral: %.2f\n\n", getMediaEstudante(e.getId())));
            }

            bw.write("Médias por disciplina:\n");
            List<Disciplina> ordenadas = disciplinas.stream().sorted().collect(Collectors.toList());
            for (Disciplina d : ordenadas) {
                bw.write(String.format("%s -> Média: %.2f\n", d.toString(), getMediaDisciplina(d.getCodigo())));
            }
            bw.write("\n");

            bw.write(String.format("Estudantes aprovados (média >= %.2f):\n", mediaAprovacao));
            for (Estudante e : getEstudantesAprovados(mediaAprovacao)) {
                bw.write(String.format("%s (média %.2f)\n", e.toString(), getMediaEstudante(e.getId())));
            }

            bw.flush();
            System.out.println("Arquivo gerado com sucesso: " + caminhoArquivo);
        } catch (IOException ex) {
            System.err.println("Erro ao gerar relatório: " + ex.getMessage());
        }
    }
}
