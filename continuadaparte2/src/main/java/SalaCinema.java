import java.util.Scanner;

public class SalaCinema {

    public static void main(String[] args) {

        //Tema: cinema
        //A idéia é que o administrador do cinema possa informar os assentos dentro da sala

        Scanner leitor = new Scanner(System.in);

        int maxFileira = 0;
        int numeroSala;
        String[] fileira;
        int[][]  assento;
        int[] soma = new int[5];
        boolean verificacao = false;

        //Solicita o numero da sala do cinema
        System.out.println("Qual o numero da sala:");
        numeroSala = leitor.nextInt();


        //Valida se o numero digitado corresponde ao valor maximo considerando a pandemia o contexto de negocio
        //capacidade maxima de 10 fileiras
        while (!verificacao) {
            try {
                System.out.println("Digite a quantidade de fileiras na sala:");
                maxFileira = leitor.nextInt();
                if (maxFileira <= 10) {
                    verificacao = true;
                } else {
                    throw new Exception("Valor de até 10 fileiras" +
                            " Digite novamente.");
                }
            } catch (Exception erro) {
                System.out.println(erro);
            }

        }


        fileira = new String[maxFileira];
        assento = new int[maxFileira][5];

        for (int i = 0; i < fileira.length; i++) {
            System.out.println("Digite a letra correspondente a fileira:");
            fileira[i] = leitor.next();
        }

        for (int linha = 0; linha < assento.length; linha++) {
            for (int coluna = 0; coluna < assento[0].length; coluna++) {
                System.out.println("Digite o numero do assento na coluna" + (coluna + 1) +
                        " da fileira " + fileira[linha]);
                assento[linha][coluna] = leitor.nextInt();
            }
        }

        //Faz a exibição da sala e seus assentos
        System.out.println("****Sala de Cinema****");
        System.out.println("****numero da sala: " + numeroSala+ "****");
        System.out.println();
        System.out.printf("%-15s%10s%10s%10s%10s%10s\n", "Fileiras",
                "Coluna 1", "Coluna 2", "Coluna 3", "Coluna 4", "Coluna 5");
        for (int linha = 0; linha < assento.length; linha++) {
            System.out.printf("%-15s", fileira[linha]);
            for (int coluna = 0; coluna < assento[0].length; coluna++) {
                System.out.printf("%10s", assento[linha][coluna]);
            }
            System.out.println();
        }

        for (int coluna=0; coluna < assento[0].length; coluna++) {
            int somaTemp = 0;
            for (int linha=0; linha < assento.length; linha++) {
                somaTemp += assento[linha][coluna];
            }
            soma[coluna] = somaTemp;
        }

        System.out.println();
        System.out.printf("%-15s", "Total de assentos");
        for (int i=0; i < soma.length; i++) {
            System.out.printf("%10s",soma[i]);
        }
    }
}

