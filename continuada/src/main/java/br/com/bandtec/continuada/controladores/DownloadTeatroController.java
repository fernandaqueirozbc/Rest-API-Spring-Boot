package br.com.bandtec.continuada.controladores;


import br.com.bandtec.continuada.dominios.Teatro;
import br.com.bandtec.continuada.repositorios.TeatroRepository;
import br.com.bandtec.continuada.util.GravaTeatro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/pecas")
public class DownloadTeatroController {


    @Autowired
    private TeatroRepository repository;

    @GetMapping(value = {"/downloadCsv"}, produces = {"*/*"})
    @ResponseBody
    public ResponseEntity download() {

        List<Teatro> teatro = this.repository.findAll();
        GravaTeatro novoArq = new GravaTeatro();

        String arquivo = "teatro.csv";
        HttpHeaders cabecalho = new HttpHeaders();

        GravaTeatro.gravaRegistroList(teatro, arquivo, false);
        cabecalho.add("Content-Disposition", "attachment: filename='teatro.csv'");
        cabecalho.add("Content-Type", "txt/csv");
        return new ResponseEntity(new FileSystemResource("src\\main\\resources\\static\\" + arquivo), cabecalho, HttpStatus.OK);

    }


    @GetMapping( value = {"/downloadTxt"}, produces = {"*/*"})
    @ResponseBody
    public ResponseEntity downloadTxt() {

        List<Teatro> teatro = this.repository.findAll();
        String dirPath = "src\\main\\resources\\static\\";
        String nomeArquivo = "teatro.txt";

        HttpHeaders headers = new HttpHeaders();

        headers.add("Content-Disposition", "attachment; filename="+nomeArquivo);
        headers.add("Content-Type", "text/txt");

        String header = "";
        String corpo = "";
        String trailer = "";
        int contRegDados = 0;

        Date dataDeHoje = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        header += "00 Teatro ";
        header += formatter.format(dataDeHoje);
        header += "01";

        gravaRegistro(nomeArquivo, header);

        for (Teatro t : teatro){
            corpo = "";
            corpo += "99";
            corpo += String.format("%-5d", t.getCodPeca());
            corpo += String.format("%-15s", t.getNomePeca());
            corpo += String.format("%-15s", t.getHorario());
            corpo += String.format("%-14.2f", t.getValorIngresso());
            contRegDados++;
            gravaRegistro(nomeArquivo,corpo);
        }

        trailer += "01";
        trailer += String.format("%010d", contRegDados);
        gravaRegistro(nomeArquivo,trailer);

        return new ResponseEntity(new FileSystemResource(dirPath+nomeArquivo),headers, HttpStatus.OK );
    }

    public static void gravaRegistro (String nomeArquivo, String registro) {
        BufferedWriter saida = null;
        try {
            saida = new BufferedWriter(new FileWriter("src\\main\\resources\\static\\" + nomeArquivo, true));
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
}
