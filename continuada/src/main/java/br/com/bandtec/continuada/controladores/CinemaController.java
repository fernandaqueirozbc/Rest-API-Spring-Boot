package br.com.bandtec.continuada.controladores;


import br.com.bandtec.continuada.dominios.Cinema;
import br.com.bandtec.continuada.repositorios.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/filmes")
public class CinemaController {

    public CinemaController() {

    }

    @Autowired
    private CinemaRepository repository;

    @PostMapping
    public ResponseEntity addFilme(@RequestBody Cinema filme) {
        repository.save(filme);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity exibirFilme() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity exibirId(@PathVariable int id) {
        return ResponseEntity.ok(this.repository.findById(id));
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<?> alterar(@RequestBody Cinema cinema, @PathVariable int id){
            return repository.findById(id)
                    .map(cinemaAchado -> {
                        cinemaAchado.setNomeFilme(cinema.getNomeFilme());
                        cinemaAchado.setHorario(cinema.getHorario());
                        cinemaAchado.setValorIngresso(cinema.getValorIngresso());
                       Cinema updated = repository.save(cinemaAchado);
                        return ResponseEntity.ok().body(updated);
                    }).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity <?> deletar(@PathVariable int id) {
        return repository.findById(id)
                .map(record -> {
                    repository.deleteById(id);
                    return ResponseEntity.ok().build();
                }).orElse(ResponseEntity.notFound().build());
    }

}
