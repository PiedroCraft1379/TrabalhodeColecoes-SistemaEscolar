import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * Classe que representa uma disciplina de um curso.
 * Cada disciplina possui um código (ex: MAT101) e um nome (ex: Matemática).
 */
class Disciplina {
    private String codigo; // código da disciplina (chave única)
    private String nome;   // nome da disciplina

    // Construtor que inicializa os atributos
    public Disciplina(String codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    // Retorna o código da disciplina
    public String getCodigo() {
        return codigo;
    }

    // Retorna o nome da disciplina
    public String getNome() {
        return nome;
    }

    /**
     * Define que duas disciplinas são consideradas iguais
     * se tiverem o mesmo código (ignorando maiúsculas/minúsculas).
     * Isso é essencial para evitar duplicatas no Set.
     */
    @Override
    public boolean equals(Object obj) {
        // Se o objeto for o mesmo na memória, retorna verdadeiro
        if (this == obj) return true;

        // Verifica se o objeto recebido é do tipo Disciplina
        if (!(obj instanceof Disciplina)) return false;

        // Faz o cast e compara os códigos (ignorando letras maiúsculas/minúsculas)
        Disciplina outra = (Disciplina) obj;
        return this.codigo.equalsIgnoreCase(outra.codigo);
    }

    /**
     * hashCode precisa ser compatível com equals.
     * Aqui usamos o código da disciplina como base, em minúsculas.
     */
    @Override
    public int hashCode() {
        return codigo.toLowerCase().hashCode();
    }

    // Retorna uma representação textual da disciplina (usada ao imprimir)
    @Override
    public String toString() {
        return String.format("%s - %s", codigo, nome);
    }
}

/**
 * Classe responsável por gerenciar o conjunto de disciplinas.
 * Utiliza um Set (LinkedHashSet) para garantir que não haja duplicação.
 */
class GerenciadorDisciplinas {
    private Set<Disciplina> disciplinas; // conjunto de disciplinas

    /**
     * Construtor: cria o conjunto de disciplinas.
     * LinkedHashSet mantém a ordem de inserção dos elementos,
     * diferente de HashSet, que não garante ordem.
     */
    public GerenciadorDisciplinas() {
        disciplinas = new LinkedHashSet<>();
    }

    /**
     * Adiciona uma disciplina ao conjunto.
     * O Set automaticamente ignora duplicatas com base em equals() e hashCode().
     * @param d Disciplina a ser adicionada
     * @return true se foi adicionada, false se já existia
     */
    public boolean adicionarDisciplina(Disciplina d) {
        // add() retorna false se o objeto já existir (duplicado)
        return disciplinas.add(d);
    }

    /**
     * Verifica se já existe uma disciplina com o código informado.
     * @param codigo Código a ser buscado
     * @return true se encontrar, false caso contrário
     */
    public boolean verificarDisciplina(String codigo) {
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Remove uma disciplina com o código informado.
     * Caso o código não exista, nada acontece.
     * @param codigo Código da disciplina a ser removida
     */
    public void removerDisciplina(String codigo) {
        disciplinas.removeIf(d -> d.getCodigo().equalsIgnoreCase(codigo));
    }

    /**
     * Retorna todas as disciplinas em ordem de inserção.
     * @return Conjunto de disciplinas (LinkedHashSet)
     */
    public Set<Disciplina> obterTodasDisciplinas() {
        return disciplinas;
    }
}

/**
 * Classe principal: responsável por ler o arquivo CSV,
 * importar as disciplinas e demonstrar o funcionamento dos métodos.
 */
public class MainDisciplinas {
    public static void main(String[] args) {
        // Cria o gerenciador de disciplinas
        GerenciadorDisciplinas gerenciador = new GerenciadorDisciplinas();

        // Caminho do arquivo CSV (ajuste conforme a localização real)
        String caminhoArquivo = "C:\\temp\\disciplinas.csv";

        /**
         * Lê o arquivo CSV linha por linha.
         * Ignora a primeira linha (cabeçalho) e converte as demais em objetos Disciplina.
         */
        try (BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo))) {
            String linha;
            boolean cabecalho = true; // usado para pular a linha de títulos

            // Lê o arquivo até o final
            while ((linha = br.readLine()) != null) {
                if (cabecalho) {
                    cabecalho = false; // primeira linha é o cabeçalho, então pula
                    continue;
                }

                // Divide a linha em duas partes: código e nome (separados por vírgula)
                String[] partes = linha.split(",");

                // Remove espaços em branco e armazena os valores
                String codigo = partes[0].trim();
                String nome = partes[1].trim();

                // Cria o objeto Disciplina
                Disciplina nova = new Disciplina(codigo, nome);

                // Tenta adicionar ao conjunto
                boolean adicionada = gerenciador.adicionarDisciplina(nova);

                // Se não foi adicionada, é porque já existia
                if (!adicionada) {
                    System.out.println("⚠️  Duplicada ignorada: " + codigo + " - " + nome);
                }
            }

        } catch (IOException e) {
            // Caso o arquivo não seja encontrado ou haja erro de leitura
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Exibe todas as disciplinas importadas (sem duplicadas)
        System.out.println("\n=== Disciplinas Cadastradas ===");
        for (Disciplina d : gerenciador.obterTodasDisciplinas()) {
            System.out.println(d);
        }

        // Demonstração de uso do método verificarDisciplina()
        System.out.println("\nExiste PRG201? " + gerenciador.verificarDisciplina("PRG201"));

        // Exemplo de remoção
        gerenciador.removerDisciplina("EDF110");
        System.out.println("\nApós remover EDF110:");
        for (Disciplina d : gerenciador.obterTodasDisciplinas()) {
            System.out.println(d);
        }
    }
}
