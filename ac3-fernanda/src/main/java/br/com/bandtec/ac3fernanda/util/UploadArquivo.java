package br.com.bandtec.ac3fernanda.util;


import java.io.FileReader;
import java.io.IOException;

public class UploadArquivo {

    public static String upload(String nomeArquivo) throws IOException
    {
        if (!nomeArquivo.endsWith(".txt"))
        {
            nomeArquivo += ".txt";
        }

        FileReader arquivo = new FileReader(nomeArquivo);

        String conteudo = "";

        int i;
        while ((i = arquivo.read())!=-1)
        {

            conteudo += (char)(i);
        }

        arquivo.close();
        return conteudo;
    }
}
