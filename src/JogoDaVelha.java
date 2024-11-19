import java.util.Scanner;

public class JogoDaVelha {

    // Começo declarando algumas variaveis, decidi deixar o tamanho do tabuleiro em
    // uma variavel pois fica mais facil alterar o tamanho do tabuleiro caso assim
    // desejar
    private static final int TAMANHO = 3;

    // construo o tabuleiro utilizano uma matriz bidimencional 3x3
    private static char[][] tabuleiro = new char[TAMANHO][TAMANHO];

    // variavel que criei para saber qual jogador será o atual, como regra o X
    // sempre inicia a partida
    private static char jogadorAtual = 'X';

    // Nomes dados aos jogadores, no qual podem ser alterados
    private static String jogadorX = "Jogador X";
    private static String jogadorO = "Jogador O";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            limparTela();

            // Fiz um menu para o jogo ficar mais intuitivo e treinar escolhas de usuário no
            // terminal
            System.out.println("===== JOGO DA VELHA =====");
            System.out.println("1. Alterar nomes dos jogadores");
            System.out.println("2. Jogar");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção para continuar: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    inserirNomesDosJogadores(scanner);
                    break;
                case 2:
                    jogar(scanner);
                    break;
                case 3:
                    executando = false;
                    System.out.println("Fechando o jogo!");
                    break;
                default:
                    System.out.println("Opção inválida! Tente novamente.");
            }
        }

        scanner.close();
    }

    // Essa função é responsável por toda a lógica de alteração de nome dos
    // usuários
    private static void inserirNomesDosJogadores(Scanner scanner) {
        System.out.print("Digite o nome do jogador X: ");
        jogadorX = scanner.nextLine();
        System.out.print("Digite o nome do jogador O: ");
        jogadorO = scanner.nextLine();
        System.out.println("Nomes dos jogadores atualizados com sucesso!");
        System.out.println(jogadorX + " será o jogador X.");
        System.out.println(jogadorO + " será o jogador O.");
        System.out.println("Pressione Enter para continuar...");
        scanner.nextLine();
    }

    // Função responsável por iniciar o jogo
    private static void jogar(Scanner scanner) {
        boolean jogoEmAndamento = true; // Como dou opção ao usuário de escolher se quer continuar a jogar ao terminal,
                                        // preciso de um booleano para isso

        inicializarTabuleiro(); // Inicio o tabuleiro (explicação do método no local do mesmo)

        while (jogoEmAndamento) {
            limparTela(); // explicação no método
            exibirTabuleiro(); // explicação no método
            jogada(scanner); // explicação no método

            // aguarda verificação de vitoria ou empate
            jogoEmAndamento = !verificarVitoria() && !verificarEmpate();
            if (jogoEmAndamento) { // enquanto não houver vitoria ou empate, segue o jogo
                trocarJogador();
            }
        }

        limparTela(); // limpa a tela pela última vez
        exibirTabuleiro(); // exibe o último tabuleiro da partida com o resultado

        if (verificarEmpate()) {
            System.out.println("Empate! Não há vencedores.");
        } else {
            String vencedor = (jogadorAtual == 'X') ? jogadorX : jogadorO;
            System.out.println("Parabéns, " + vencedor + "! Você venceu!");
        }

        // Pergunta para o jogador qual o próximo passo
        System.out.println("\n1. Jogar novamente");
        System.out.println("2. Voltar ao menu");
        System.out.println("3. Sair");
        System.out.print("Escolha uma opção: ");
        int escolha = scanner.nextInt();

        switch (escolha) {
            case 1:
                jogar(scanner);
                break;
            case 2:
                break;
            case 3:
                System.out.println("Saindo do jogo...");
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida! Voltando ao menu principal.");
        }
    }

    // esse método percorre todas as linhas do tabuleiro e coloca o valor como -
    // para simbolizar que está vazio e seta o jogador atual como X
    private static void inicializarTabuleiro() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                tabuleiro[i][j] = '-';
            }
        }
        jogadorAtual = 'X';
    }

    // função responsável por exibir o tabuleiro, todo o tabuleiro é construído por
    // esta função.
    private static void exibirTabuleiro() {
        System.out.println("   1   2   3");
        System.out.println("  +---+---+---+");

        for (int i = 0; i < TAMANHO; i++) {
            System.out.print((char) ('A' + i) + " | ");
            for (int j = 0; j < TAMANHO; j++) {
                System.out.print(tabuleiro[i][j] + " | ");
            }
            System.out.println();
            System.out.println("  +---+---+---+");
        }
    }

    // Este método é responsável por escanear a jogada do usuário, enquanto a jogada
    // não for válida ele permanecerá em um loop. Ao escanear a entrada é validado
    // se
    // está no formato correto, ela deve ter o tamanho de 2, pois a entrada é uma
    // letra e um número. Em seguida é verificado se a letra e o número estão dentro
    // do intervalo de jogo, caso estejam dentro são convertidos para os indexes da
    // coluna e da linha. Após isso é validado se a célula na qual foi tentado
    // inserir algo, está vazia. Se tiver inserimos nela se não pedimos para inserir
    // em uma célula diferente
    private static void jogada(Scanner scanner) {
        int linha = -1, coluna = -1;
        boolean jogadaValida = false;

        while (!jogadaValida) {
            String jogadorAtualNome = (jogadorAtual == 'X') ? jogadorX : jogadorO;
            System.out.println(jogadorAtualNome + " (" + jogadorAtual + "), insira sua jogada (ex: A1, B2): ");
            String entrada = scanner.next().toUpperCase();

            if (entrada.length() == 2) {
                char letra = entrada.charAt(0);
                char numero = entrada.charAt(1);

                if (letra >= 'A' && letra < 'A' + TAMANHO && numero >= '1' && numero < '1' + TAMANHO) {
                    linha = letra - 'A';
                    coluna = numero - '1';

                    if (tabuleiro[linha][coluna] == '-') {
                        tabuleiro[linha][coluna] = jogadorAtual;
                        jogadaValida = true;
                    } else {
                        System.out.println("Célula já ocupada! Tente novamente.");
                    }
                } else {
                    System.out.println("Entrada fora dos limites! Use A1, B2, etc.");
                }
            } else {
                System.out.println("Formato inválido! Use algo como A1, B2, etc.");
            }
        }
    }

    // troca o jogador atual
    private static void trocarJogador() {
        jogadorAtual = (jogadorAtual == 'X') ? 'O' : 'X';
    }

    // esse método verifica se houve vitória, primeiramente é verificado se a linha
    // inteira é igual ao jogador atual seja ele X ou O, caso seja ele venceu. Porém
    // essa não é a única forma de vitoria então caso essa verificação não seja
    // satisfeita na verificação, seguimos para a próxima, verificando se todos os
    // itens de uma coluna são iguais ao jogador atual e posteriormente verifico as
    // diagonais, caso nenhuma das condições seja satisfeita, não houve vitória
    // nessa rodada.
    private static boolean verificarVitoria() {
        for (int i = 0; i < TAMANHO; i++) {
            if (tabuleiro[i][0] == jogadorAtual && tabuleiro[i][1] == jogadorAtual && tabuleiro[i][2] == jogadorAtual)
                return true;
            if (tabuleiro[0][i] == jogadorAtual && tabuleiro[1][i] == jogadorAtual && tabuleiro[2][i] == jogadorAtual)
                return true;
        }
        if (tabuleiro[0][0] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][2] == jogadorAtual)
            return true;
        if (tabuleiro[0][2] == jogadorAtual && tabuleiro[1][1] == jogadorAtual && tabuleiro[2][0] == jogadorAtual)
            return true;
        return false;
    }

    // Verificação de empate verificando se há células vazias, caso haja ainda pode
    // ser realizado uma jogada então não houve empate
    private static boolean verificarEmpate() {
        for (int i = 0; i < TAMANHO; i++) {
            for (int j = 0; j < TAMANHO; j++) {
                if (tabuleiro[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    // sempre que uma opção é inserida limpo a tela para que não vire uma bagunça a
    // visualização do jogo
    private static void limparTela() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
