import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class aspirador {

    public static int aresta, matriz[][], posicaoX, posicaoY, direcao, posicaoXInicial, posicaoYInicial;
    public static String msg;
    public static long bgn, end, time;

    //Criando codes de cor
    public static String y = "\u001B[33m", w = "\u001B[0m", c = "\u001B[36m", p = "\u001B[35m";

    public static void main(String[] args) throws IOException, InterruptedException {
        Scanner input = new Scanner(System.in);
        Random aleatorio = new Random();

        System.out.print("Entre com a largura da sala: ");
        while (!input.hasNextInt()) {
            System.out.println("ERRO: Digite um valor inteiro ");
            input.next();
        }
        aresta = input.nextInt();
        matriz = new int[aresta][aresta];
        System.out.print(y + "\n[ 1 ] <- piso sujo\n[ 0 ] <- piso limpo\n" + w);

        //preenchendo a área com pisos sujos e limpos
        for (int i = 0; i < aresta; i++) {
            for (int j = 0; j < aresta; j++) {
                matriz[i][j] = aleatorio.nextInt(2);
            }
        }

        //posicionando o aspirador
        posicaoX = aleatorio.nextInt(aresta);
        posicaoXInicial = posicaoX;
        posicaoY = aleatorio.nextInt(aresta);
        posicaoYInicial = posicaoY;

        //Iniciação da limpeza
        desenhandoMatriz();
        System.out.println("Start");

        posicionaAspirador();

        boolean sujo = true;
        while (sujo){
            if((posicaoX == aresta-1) && (posicaoY == 0)){
               sujo = false;
            } else {
                if((posicaoY == 0) && (direcao == 0) || (posicaoY == aresta-1) && (direcao == 1)){
                    //aspirador desce 1 posição
                    posicaoX++;
                    desenhandoMatriz();
                    limpaPiso(posicaoX, posicaoY);
                    //inverte direção
                    if(direcao == 1){
                        direcao = 0;
                    } else {
                        direcao = 1;
                    }
                } else {
                    if(direcao == 1){
                        posicaoY++;
                    } else {
                        posicaoY--;
                    }
                    desenhandoMatriz();
                    limpaPiso(posicaoX, posicaoY);
                }
            }
        }

        retornaOrigem(posicaoX, posicaoY);
    }

    //Desenhando a sala
    public static void desenhandoMatriz() throws IOException, InterruptedException {
        System.out.println("\t");
        for (int i = 0; i < aresta; i++){
            for (int j = 0; j < aresta; j++) {
                if((posicaoX == i) && (posicaoY == j)){
                    System.out.print(p + "[:" + matriz[i][j] + ":]" + w);
                } else if(matriz[i][j] == 0){
                    System.out.print("[ " + c + matriz[i][j] + w + " ]");
                } else {
                    System.out.print("[ " + y + matriz[i][j] + w + " ]");
                }
            }
            System.out.println();
        }
    }

    //Posiciona o aspirador em uma das pontas
    public static void posicionaAspirador() throws IOException, InterruptedException {
        if (matriz[posicaoX][posicaoY] == 1) {
            limpaPiso(posicaoX, posicaoY);
        }
        boolean naoChegou = true;
        while (naoChegou){
            //direita será a direção inicial
            if ((posicaoX == 0) && (posicaoY == aresta-1)) {
                naoChegou = false;
            } else if (posicaoY == aresta-1) {
                //movimenta para cima
                posicaoX--;
                desenhandoMatriz();
            } else {
                //movimenta para direita
                posicaoY++;
                desenhandoMatriz();
            }

            if (matriz[posicaoX][posicaoY] == 1) {
                limpaPiso(posicaoX, posicaoY);
            }
        }
        direcao = 0;
    }

    public static void limpaPiso(int x, int y) throws IOException, InterruptedException {
        if(matriz[x][y] == 1){
            matriz[x][y] = 0;
            desenhandoMatriz();
            System.out.println("Limpo");
        }
    }

    //Retorna para origem
    public static void retornaOrigem(int x, int y) throws IOException, InterruptedException {
        boolean chegou = false;
        while (!chegou){
            if(posicaoY == posicaoYInicial && posicaoX == posicaoXInicial){
                chegou = true;
            } else {
                if(posicaoY == posicaoYInicial){
                    //movimenta para cima
                    posicaoX--;
                } else {
                    //movimenta para direita
                    posicaoY++;
                }
                desenhandoMatriz();
            }
        }
    }
}