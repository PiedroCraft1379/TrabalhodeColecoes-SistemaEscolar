🧾 Trabalho de Programação — Coleções Java: List, Set e Map
📚 Instituição

ICEV – Instituto de Ensino Superior

👥 Integrantes do grupo

Mardson Varela Lima

Isac Albuquerque

📅 Ano de entrega

2025

🎯 Descrição do projeto

O projeto Sistema de Gerenciamento Escolar foi desenvolvido para automatizar o controle de estudantes, disciplinas, matrículas e notas em uma escola de ensino médio.

O sistema utiliza coleções da linguagem Java (List, Set e Map) para armazenar e manipular os dados de forma eficiente, permitindo:

Cadastrar, remover, buscar e ordenar estudantes.

Gerenciar disciplinas sem duplicatas.

Registrar matrículas e notas de cada estudante.

Calcular médias individuais e por disciplina.

Listar os alunos aprovados com base em uma média mínima.

Gerar um relatório automático (output.txt) com todas essas informações organizadas.

⚙️ Estrutura das classes
Classe	Função principal
Estudante.java	Representa um estudante com ID e nome.
Disciplina.java	Representa uma disciplina única (sem duplicatas).
Matricula.java	Relaciona estudante, disciplina e suas notas.
SistemaDeGerenciamento.java	Controla todas as operações do sistema, como adicionar alunos, disciplinas, notas, calcular médias e gerar o relatório.
Principal.java	Classe principal que executa o programa, cria os objetos e gera o arquivo de saída.
🧠 Justificativas de uso das coleções

O trabalho exigia o uso de List, Set e Map.
Segue a justificativa de cada escolha:

1️⃣ List — (Implementação: ArrayList)

Usada para armazenar:

A lista de estudantes.

As notas de cada matrícula.

👉 Escolhida porque permite ordenação, buscas por índice e inserções rápidas.
Além disso, preserva a ordem de inserção — essencial para mostrar os alunos na ordem cadastrada e para gerar relatórios ordenados por nome.

2️⃣ Set — (Implementação: HashSet)

Usado para armazenar:

O conjunto de disciplinas.

👉 Escolhido porque o Set não permite duplicatas, o que garante que não existam duas disciplinas com o mesmo código.
O HashSet oferece verificação rápida de existência (O(1)) e é eficiente para garantir unicidade.

3️⃣ Map — (Implementação: HashMap)

Usado para:

Associar cada estudante às suas matrículas (idEstudante → lista de matrículas).

Criar um mapa auxiliar de disciplinas (código → disciplina).

👉 O Map foi escolhido porque permite associar chaves únicas a valores, ideal para representar as relações:

Um estudante → suas matrículas.

Um código de disciplina → seu objeto.

🏁 Conclusão

O projeto cumpre todos os requisitos propostos no enunciado:
✅ Uso das coleções List, Set e Map com justificativa.
✅ Funcionalidades completas (cadastro, busca, notas, médias e relatório).
✅ Código limpo, organizado e comentado.
✅ Geração correta do arquivo output.txt.
