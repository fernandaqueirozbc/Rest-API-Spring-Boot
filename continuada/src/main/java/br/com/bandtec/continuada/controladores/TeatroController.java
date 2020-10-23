package br.com.bandtec.continuada.controladores;

import br.com.bandtec.continuada.dominios.Teatro;
import br.com.bandtec.continuada.repositorios.TeatroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pecas")
public class TeatroController {

    public TeatroController() {

    }

    @Autowired
    private TeatroRepository repository;

    @PostMapping
    public ResponseEntity addPeca(@RequestBody Teatro peca) {
        repository.save(peca);
        return ResponseEntity.created(null).build();
    }

    @GetMapping
    public ResponseEntity exibirPeca() {
        return ResponseEntity.ok(this.repository.findAll());
    }

    @GetMapping(value = "/{id}")
    public  ResponseEntity exibirId(@PathVariable int id) {
        return ResponseEntity.ok(this.repository.findById(id));
    }

    @PutMapping(path = "/{id}")
    ResponseEntity<?> alterar(@RequestBody Teatro teatro, @PathVariable int id){
        return repository.findById(id)
                .map(pecaAchada -> {
                    pecaAchada.setNomePeca(teatro.getNomePeca());
                    pecaAchada.setHorario(teatro.getHorario());
                    pecaAchada.setValorIngresso(teatro.getValorIngresso());
                    Teatro updated = repository.save(pecaAchada);
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
