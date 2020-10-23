package br.com.bandtec.continuada.util;

import br.com.bandtec.continuada.dominios.Cinema;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Formatter;
import java.util.List;

public class GravaFilme {

    public GravaFilme() {
    }

    public static void gravaRegistro(String nomeArq, String registro) {
        BufferedWriter saida = null;
        try {
            saida = new BufferedWriter(new FileWriter("src\\main\\resources\\static\\"
                    + nomeArq, true));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }

        try {
            saida.append(registro + "\n");
            saida.close();

        } catch (IOException e) {
            System.err.printf("Erro ao gravar arquivo: %s.\n", e.getMessage());
        }
    }

    public static void gravaRegistroList(List<Cinema> cinema, String nomeArq, Boolean txt) {
        String header = "";
        String delimitador = "";
        String trailer = "";
        Integer cont = 0;

        if (txt) {
            delimitador = " ";
        } else {
            delimitador = ";";
        }

        if (txt) {
            Date dataAtual = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            header += String.format("%02d%s", 01, delimitador);
            header += String.format("%-20s%s ", "Cinema", delimitador);
            header += String.format("%10s%-10s%5s \n", formatter.format(dataAtual), delimitador
                    , "1.0 VERSION", delimitador);
            header += String.format("%02d%s ", 01, delimitador);
            header += String.format("%-5s%s%30s%s%20s%20s\n", "CODIGO FILME", delimitador, "NOME FILME", delimitador, "HORARIO", delimitador, "VALOR INGRESSO");

            gravaRegistro(nomeArq, header);
        }


        FileWriter arq = null;
        Formatter saida = null;
        try {

            arq = new FileWriter("src\\main\\resources\\static\\" + nomeArq, true);
            saida = new Formatter(arq);

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }


        for (Cinema c : cinema) {
            saida.format("%5d%s%20s%s%20s%s$14.2f\n",
                    c.getCodFilme(), delimitador, c.getNomeFilme(), delimitador, c.getHorario(), c.getValorIngresso());
            cont++;
        }

        saida.close();

        if (txt) {
            trailer += String.format("%20d DADOS", cont);

            gravaRegistro(nomeArq, trailer);
        }

    }
}
