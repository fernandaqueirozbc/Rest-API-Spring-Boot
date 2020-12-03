package br.com.bandtec.ac3fernanda.controller;

import br.com.bandtec.ac3fernanda.dominios.Cinema;
import br.com.bandtec.ac3fernanda.repositorios.CinemaRepository;
import br.com.bandtec.ac3fernanda.util.FilaObj;
import br.com.bandtec.ac3fernanda.util.PilhaObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

@RestController
@RequestMapping("/filmes")
public class CinemaController {

    PilhaObj<Cinema> pilhaCinema = new PilhaObj<>(500);
    FilaObj<Cinema> filaObj = new FilaObj<>(500);
    private Integer finalFilaObj = 0;

    public CinemaController() {
    }

    @Autowired
    private CinemaRepository repository;

    @Scheduled(fixedRate = 10000)
    public void agendador() {
        if (filaObj.isEmpty()) {
            finalFilaObj = 0;
        } else {
            filaObj.poll();
        }

    }

    @PostMapping
    public ResponseEntity addFilme(@RequestBody Cinema filme) {
        try {
            filaObj.insert(filme);
            pilhaCinema.push(filme);
        } catch (Exception e) {
            e.printStackTrace();
        }
        repository.save(filme);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity exibirFilme() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @PostMapping("/desfazer")
    public ResponseEntity desfazer() {
        //Metodos ".exibe" para consultar no console a execução e validar o funcionamento
        pilhaCinema.exibe();
        try {
            pilhaCinema.pop();
            repository.deleteById(finalFilaObj);
            pilhaCinema.exibe();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/exibir/{id}")
    public ResponseEntity exibirUm(@PathVariable Cinema id) {
        filaObj.insert(id);
        return ResponseEntity.accepted().build();
    }

    @GetMapping("/exibir/agendamento")
    public ResponseEntity agenda() {

        if (repository.existsById(finalFilaObj))
        {
            return ResponseEntity.ok(repository.findById(finalFilaObj));
        }
        else
        {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping(value = "/upload")
    public ResponseEntity importar(@RequestBody byte[] arquivo) throws IOException {

        String registro;
        Double valor;
        String nome, horario;
        String tipoRegistro;
        String nomeArquivo = "Cinema.txt";
        Integer cont = 0;

        BufferedReader entrada = null;
        Path path = Paths.get(nomeArquivo);
        Files.write(path, arquivo);
        List<Cinema> listaFilme = new ArrayList<>();
        Cinema novoFilme = new Cinema();
        novoFilme.setArquivo(arquivo);

        try {
            entrada = new BufferedReader(new FileReader(nomeArquivo));
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
        try {
            registro = entrada.readLine();

            while (registro != null) {
                Cinema cinema = new Cinema();
                tipoRegistro = registro.substring(0, 2);

                if (tipoRegistro.equals("00")) {
                    System.out.println("Header");
                    System.out.println("Tipo de arquivo: " + registro.substring(2, 6));
                    System.out.println("Data/hora de geração do arquivo: " + registro.substring(7, 30));
                    System.out.println("Versão do layout: " + registro.substring(30, 32));
                } else if (tipoRegistro.equals("01")) {
                    System.out.println("\nTrailer");
                    int qtdRegistro = Integer.parseInt(registro.substring(2, 12));
                    if (qtdRegistro == cont) {
                        System.out.println("Quantidade de registros gravados compatível com quantidade lida");
                    } else {
                        System.out.println("Quantidade de registros gravados não confere com quantidade lida");
                    }
                } else if (tipoRegistro.equals("02")) {
                    if (cont == 0) {
                        System.out.println();
                        System.out.printf("%-5s %-8s %6s\n", "Nome Filme", "Horario", "Valor ingresso");

                    }

                    nome = registro.substring(2, 10);
                    horario = registro.substring(10, 25);
                    valor = Double.parseDouble(registro.substring(25, 30));
                    System.out.println(nome + " " + " " + horario);

                    cinema.setNomeFilme(nome);
                    cinema.setHorario(horario);
                    cinema.setValorIngresso(valor);

                    listaFilme.add(cinema);

                    cont++;


                } else {
                    System.out.println("Tipo de registro inválido");
                }

                registro = entrada.readLine();
            }
            for (Cinema c : listaFilme) {
                repository.save(c);
            }

            entrada.close();
        }
         catch (IOException e)
            {
                System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
            }
            return ResponseEntity.created(null).build();
        }
    }

