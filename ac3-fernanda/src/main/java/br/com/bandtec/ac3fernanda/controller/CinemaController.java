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
import java.util.*;

@RestController
@RequestMapping("/filmes")
public class CinemaController {

    PilhaObj<Cinema> pilhaCinema = new PilhaObj<>(500);
    FilaObj<Cinema> filaObj = new FilaObj<>(500);
    FilaObj<Integer> valorFila = new FilaObj<>(500);
    private Map<UUID, Integer> result = new HashMap<>();
    private Integer finalFilaObj = 1;
    private Integer finalPilhaObj = 0;

    public CinemaController() {
    }

    @Autowired
    private CinemaRepository repository;

    @PostMapping
    public ResponseEntity addFilme(@RequestBody Cinema filme) {
        try {
            pilhaCinema.push(filme);
            finalPilhaObj++;
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
            if (repository.existsById(finalPilhaObj)) {

                pilhaCinema.pop();

                repository.deleteById(finalPilhaObj);

                pilhaCinema.exibe();

                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }

    }

    @GetMapping("/exibir/{id}")
    public ResponseEntity exibirUm(@PathVariable int id) {
        return ResponseEntity.ok(this.repository.findById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity deletarRequisição(@PathVariable int id) {

        if (repository.existsById(id)) {
            repository.deleteById(id);
            filaObj.poll();
            UUID identificador = UUID.randomUUID();


            return ResponseEntity.ok("Identificador da requisição :" + (identificador));

        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Scheduled(fixedRate = 20000)
    public void valida() {
        if (!filaObj.isEmpty()) {
            System.out.println("Não há requisições para processar");
        } else {
            finalFilaObj = valorFila.poll();
        }

    }

    @PostMapping(value = "/upload")
    public ResponseEntity importar(@RequestBody byte[] arquivo) throws IOException {

        String nomeArquivo = "cinema.txt";
        Path path = Paths.get(nomeArquivo);
        BufferedReader entrada = null;
        Files.write(path, arquivo);

        String registro;
        Double valor;
        Integer cod;
        String nome, horario;
        String tipoRegistro;
        Integer cont = 0;

        List<Cinema> listaFilme = new ArrayList<>();
        Cinema novoCinema = new Cinema();
        novoCinema.setArquivo(arquivo);


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

                if (tipoRegistro.equals("01")) {
                    if (cont == 0) {
                        System.out.println();

                    }

                    nome = registro.substring(2, 22);
                    horario = registro.substring(23, 33);
                    valor = Double.parseDouble(registro.substring(33, 40));
                    System.out.println(nome + " " + " " + horario + " " + " " + valor);


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
        } catch (IOException e) {
            System.err.printf("Erro ao ler arquivo: %s.\n", e.getMessage());
        }
        return ResponseEntity.created(null).build();
    }
}

