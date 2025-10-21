import java.util.*;
import java.io.*;
import java.util.stream.Collectors;

public class MainInterativo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // =========================
        // Gerenciadores
        // =========================
        GerenciadorEstudantes gerEst = new GerenciadorEstudantes();
        GerenciadorDisciplinas gerDisc = new GerenciadorDisciplinas();
        ParteC.RegistroAcademico registro = new ParteC.RegistroAcademico();

        // =========================
        // Lê estudantes do CSV
        // =========================
        String caminhoEstudantes = "C:\\temp\\estudantes.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoEstudantes))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (cabecalho) { cabecalho = false; continue; }
                String[] partes = linha.split(",");
                int id = Integer.parseInt(partes[0].trim());
                String nome = partes[1].trim();
                gerEst.adicionarEstudante(new Estudante(id, nome));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler estudantes: " + e.getMessage());
        }

        // =========================
        // Lê disciplinas do CSV
        // =========================
        String caminhoDisciplinas = "C:\\temp\\disciplinas.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoDisciplinas))) {
            String linha;
            boolean cabecalho = true;
            while ((linha = br.readLine()) != null) {
                if (cabecalho) { cabecalho = false; continue; }
                String[] partes = linha.split(",");
                String codigo = partes[0].trim();
                String nome = partes[1].trim();
                gerDisc.adicionarDisciplina(new Disciplina(codigo, nome));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler disciplinas: " + e.getMessage());
        }

        // =========================
        // Menu interativo
        // =========================
        while (true) {
            System.out.println("\n=== MENU ===");
            System.out.println("1 - Adicionar matrícula");
            System.out.println("2 - Remover matrícula");
            System.out.println("3 - Consultar nota");
            System.out.println("4 - Ver matrículas de um estudante");
            System.out.println("5 - Média do estudante");
            System.out.println("6 - Média de uma disciplina");
            System.out.println("7 - Top N estudantes por média");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = sc.nextLine();

            switch (opcao) {
                case "1":
                    System.out.print("ID do estudante: ");
                    int idAdd = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codAdd = sc.nextLine();
                    System.out.print("Nota: ");
                    double notaAdd = Double.parseDouble(sc.nextLine());

                    if (gerEst.getEstudantes().stream().anyMatch(e -> e.getId() == idAdd)
                            && gerDisc.verificarDisciplina(codAdd)) {
                        registro.adicionarMatricula(idAdd, codAdd, notaAdd);
                        System.out.println("Matrícula adicionada com sucesso!");
                    } else {
                        System.out.println("Estudante ou disciplina inválidos!");
                    }
                    break;

                case "2":
                    System.out.print("ID do estudante: ");
                    int idRem = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codRem = sc.nextLine();
                    boolean removed = registro.removerMatricula(idRem, codRem);
                    System.out.println(removed ? "Matrícula removida!" : "Matrícula não encontrada.");
                    break;

                case "3":
                    System.out.print("ID do estudante: ");
                    int idCon = Integer.parseInt(sc.nextLine());
                    System.out.print("Código da disciplina: ");
                    String codCon = sc.nextLine();
                    Optional<Double> notaCon = registro.obterNota(idCon, codCon);
                    if (notaCon.isPresent()) {
                        System.out.println("Nota: " + notaCon.get());
                    } else {
                        System.out.println("Matrícula não encontrada.");
                    }
                    break;

                case "4":
                    System.out.print("ID do estudante: ");
                    int idMat = Integer.parseInt(sc.nextLine());
                    List<ParteC.Matricula> mats = registro.obterMatriculas(idMat);
                    if (mats.isEmpty()) {
                        System.out.println("Sem matrículas.");
                    } else {
                        for (ParteC.Matricula m : mats) {
                            System.out.println(m.getCodigoDisciplina() + " - " + m.getNota());
                        }
                    }
                    break;

                case "5":
                    System.out.print("ID do estudante: ");
                    int idMed = Integer.parseInt(sc.nextLine());
                    double mediaEst = registro.mediaDoEstudante(idMed);
                    System.out.println("Média do estudante: " + mediaEst);
                    break;

                case "6":
                    System.out.print("Código da disciplina: ");
                    String codMed = sc.nextLine();
                    double mediaDisc = registro.mediaDaDisciplina(codMed);
                    System.out.println("Média da disciplina: " + mediaDisc);
                    break;

                case "7":
                    System.out.print("Quantos estudantes exibir (N): ");
                    int topN = Integer.parseInt(sc.nextLine());
                    List<Integer> topEst = registro.topNEstudantesPorMedia(topN);
                    for (int id : topEst) {
                        Estudante e = gerEst.getEstudantes().stream().filter(s -> s.getId() == id).findFirst().orElse(null);
                        if (e != null) {
                            System.out.println(e.getNome() + " (ID " + e.getId() + ") | Média: " + registro.mediaDoEstudante(id));
                        }
                    }
                    break;

                case "0":
                    System.out.println("Saindo...");
                    sc.close();
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }
}
