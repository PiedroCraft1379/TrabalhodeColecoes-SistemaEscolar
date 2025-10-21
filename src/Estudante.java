import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Classe que representa um estudante.
 * Cada estudante possui um ID e um nome.
 */
class Estudante {
    private int id;
    private String nome;

    // Construtor
    public Estudante(int id, String nome) {
        this.id = id;
        this.nome = nome;
    }

    // Retorna o ID do estudante
    public int getId() {
        return id;
    }

    // Retorna o nome do estudante
    public String getNome() {
        return nome;
    }

    // Retorna uma representação em texto do estudante (usada ao imprimir)
    @Override
    public String toString() {
        return String.format("ID: %d | Nome: %s", id, nome);
    }
}

/**
 * Classe responsável por gerenciar uma lista de estudantes.
 * Utiliza a interface List (implementada com ArrayList) para armazenar os objetos.
 */
class GerenciadorEstudantes {
    private List<Estudante> estudantes;

    // Construtor: cria uma lista vazia de estudantes
    public GerenciadorEstudantes() {
        estudantes = new ArrayList<>();
    }

    /**
     * Adiciona um novo estudante à lista.
     * @param e Estudante a ser adicionado
     */
    public void adicionarEstudante(Estudante e) {
        estudantes.add(e);
    }

    /**
     * Remove um estudante com base em seu ID.
     * @param id ID do estudante a ser removido
     */
    public void removerEstudantePorId(int id) {
        // removeIf é um método de List que remove todos os elementos que satisfazem a condição
        estudantes.removeIf(e -> e.getId() == id);
    }

    /**
     * Retorna o estudante localizado em um índice específico da lista.
     * @param indice Posição do estudante na lista
     * @return Estudante no índice, ou null se o índice for inválido
     */
    public Estudante obterEstudantePorIndice(int indice) {
        if (indice >= 0 && indice < estudantes.size()) {
            return estudantes.get(indice);
        }
        return null; // índice fora dos limites da lista
    }

    /**
     * Busca estudantes cujo nome contém a substring informada (ignorando maiúsculas/minúsculas).
     * @param substring Parte do nome a ser buscada
     * @return Lista com todos os estudantes encontrados
     */
    public List<Estudante> buscarEstudantesPorNome(String substring) {
        List<Estudante> resultado = new ArrayList<>();
        for (Estudante e : estudantes) {
            if (e.getNome().toLowerCase().contains(substring.toLowerCase())) {
                resultado.add(e);
            }
        }
        return resultado;
    }

    /**
     * Ordena a lista de estudantes alfabeticamente pelo nome.
     */
    public void ordenarEstudantesPorNome() {
        // Comparator.comparing cria um comparador baseado no nome
        Collections.sort(estudantes, Comparator.comparing(Estudante::getNome));
    }

    // Retorna a lista completa de estudantes
    public List<Estudante> getEstudantes() {
        return estudantes;
    }
}

/**
 * Classe principal que executa o programa.
 * Lê o arquivo CSV e demonstra o funcionamento dos métodos.
 */
public class Main {
    public static void main(String[] args) {
        GerenciadorEstudantes gerenciador = new GerenciadorEstudantes();

        // Caminho do arquivo CSV — altere se necessário
        String caminhoArquivo = "C:\\temp\\estudantes.csv";

        // Leitura do arquivo CSV linha por linha
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean cabecalho = true; // usado para ignorar a primeira linha (cabeçalho)

            while ((linha = br.readLine()) != null) {
                if (cabecalho) { // ignora a linha "id,nome"
                    cabecalho = false;
                    continue;
                }

                // Divide a linha usando vírgula como separador
                String[] partes = linha.split(",");

                // Converte o ID para inteiro e obtém o nome
                int id = Integer.parseInt(partes[0].trim());
                String nome = partes[1].trim();

                // Cria um novo estudante e adiciona ao gerenciador
                gerenciador.adicionarEstudante(new Estudante(id, nome));
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Exibe a lista original de estudantes
        System.out.println("=== Lista Original de Estudantes ===");
        for (Estudante e : gerenciador.getEstudantes()) {
            System.out.println(e);
        }

        // Ordena a lista por nome
        gerenciador.ordenarEstudantesPorNome();

        // Exibe a lista ordenada
        System.out.println("\n=== Lista Ordenada por Nome ===");
        for (Estudante e : gerenciador.getEstudantes()) {
            System.out.println(e);
        }
    }
}
