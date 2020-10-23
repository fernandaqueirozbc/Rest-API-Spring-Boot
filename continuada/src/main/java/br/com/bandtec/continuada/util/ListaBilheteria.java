package br.com.bandtec.continuada.util;

import br.com.bandtec.continuada.dominios.Cinema;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ListaBilheteria<T> {
    private T[] vetor;
    private int nroElem;

    public ListaBilheteria(int tam) {
        vetor = (T[]) new Object[tam];
        nroElem = 0;
    }

    public boolean adiciona(T valor) {
        if (nroElem >= vetor.length) {
            System.out.println("Lista está cheia");
            return false;
        } else {
            vetor[nroElem++] = valor;
            return true;
        }
    }

    public void exibe() {
        System.out.println("\nExibindo elementos da lista:");
        for (int i = 0; i < nroElem; i++) {
            System.out.println(vetor[i]);
        }
        System.out.println();
    }

    public int busca(T valor) {
        for (int i = 0; i < nroElem; i++) {
            if (vetor[i].equals(valor)) {
                return i;
            }
        }
        return -1;
    }


    public boolean removePeloIndice(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return false;
        } else {

            for (int i = indice; i < nroElem - 1; i++) {
                vetor[i] = vetor[i + 1];
                }

            nroElem--;
            return true;
        }
    }

    public boolean removeElemento(T valor) {
        return removePeloIndice(busca(valor));
    }

    public int getTamanho() {
        return nroElem;
    }

    public T getElemento(int indice) {
        if (indice < 0 || indice >= nroElem) {
            return null;
        } else {
            return vetor[indice];
        }
    }

    public void limpa() {
        nroElem = 0;
    }

    public void gravaLista(ListaBilheteria<Cinema> listaBilheteria, String nomeArquivo) {

        FileWriter arq = null;
        Formatter saida = null;
        boolean erro = false;

        try {
            arq = new FileWriter(nomeArquivo, true);
            saida = new Formatter(arq);
        } catch (IOException e) {
            System.err.println("Erro ao abrir arquivo");
            System.exit(1);
        }

        try {
            for (int i = 0; i < listaBilheteria.getTamanho(); i++) {
                Cinema f = listaBilheteria.getElemento(i);

                saida.format("%d;%s;%.2f;%s;%d;",
                        f.getCodFilme(), f.getNomeFilme(), f.getHorario(), f.getValorIngresso());
            }
        } catch (FormatterClosedException e) {
            System.err.println("Erro ao gravar no arquivo");
            erro = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar o arquivo");
                erro = true;
            }
            if (erro) {
                System.exit(1);
            }
        }
    }

    public void gravaListaNome(ListaBilheteria<Cinema> listaBilheteria, String nomeArquivo, String nomeFilme) {
        FileWriter arq = null;
        Formatter saida = null;
        boolean erro = false;

        try {
            arq = new FileWriter(nomeArquivo, true);
            saida = new Formatter(arq);
        } catch (IOException e) {
            System.err.println("Erro ao abrir o arquivo");
            System.exit(1);
        }

        try {
            for (int i = 0; i < listaBilheteria.getTamanho(); i++) {
                Cinema f = listaBilheteria.getElemento(i);

                int qtdeFilmeDiferente = 0;

                if (f.getNomeFilme().equals(nomeFilme)) {
                    saida.format("%d;%s;%.2f;%s;%d;",
                            f.getCodFilme(), f.getNomeFilme(), f.getHorario(), f.getValorIngresso());
                } else {
                    qtdeFilmeDiferente++;
                }

                if (qtdeFilmeDiferente == listaBilheteria.getTamanho()) {
                    System.out.println("Não há filmes com este nome na lista");
                }
            }
        } catch (FormatterClosedException e) {
            System.err.println("Erro ao gravar no arquivo");
            erro = true;
        } finally {
            saida.close();
            try {
                arq.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar o arquivo");
                erro = true;
            }
            if (erro) {
                System.exit(1);
            }
        }

    }

    public void lerExibeArquivo(String nomeArquivo) {
        FileReader arq = null;
        Scanner entrada = null;
        boolean erro = false;



        try {
            arq = new FileReader(nomeArquivo);
            entrada = new Scanner(arq).useDelimiter(";|\\r\\n");
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo não encontrado");
            System.exit(1);
        }


        try {

            System.out.printf("%-6s%-10s%-14s%-16s%\n", "Codigo", "NOME", "HORARIO", "VALOR");

            while (entrada.hasNext()) {
                int codigo = entrada.nextInt();
                String nomeFilme = entrada.next();
                double horario = entrada.nextDouble();
                String valor = entrada.next();
                System.out.printf("%-6d%-10s%-10s%-14.2f%\n", codigo, nomeFilme, horario, valor);
            }
        } catch (NoSuchElementException e) {
            System.err.println("Arquivo com problema");
            erro = true;
        } catch (IllegalStateException e) {
            System.err.println("Erro na leitura do arquivo");
            erro = true;
        } finally {
            entrada.close();
            try {
                arq.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar arquivo");
                erro = true;

                if (erro) {
                    System.exit(1);
                }
            }
        }

    }
}
