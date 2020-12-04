package br.com.bandtec.ac3fernanda.controller;

import br.com.bandtec.ac3fernanda.controller.CinemaController;
import br.com.bandtec.ac3fernanda.dominios.Cinema;
import br.com.bandtec.ac3fernanda.repositorios.CinemaRepository;
import br.com.bandtec.ac3fernanda.util.FilaObj;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = CinemaController.class)
class  CinemaControllerTests {

    @Autowired
    CinemaController controller;

    @MockBean
    CinemaRepository repository;

    @Test
    @DisplayName("adcionar filme e reotrna 200")
    void setFilme() {
        Cinema cinema = Mockito.mock(Cinema.class);

        cinema.setNomeFilme("Vingadores");
        cinema.setValorIngresso(20.00);
        cinema.setHorario("20:00");

        ResponseEntity resposta = controller.addFilme(cinema);

        assertEquals(201, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("deleteUma() com identificador")
    void delete() {
        Integer id = 15;
        Mockito.when(repository.existsById(id)).thenReturn(false);
        ResponseEntity resposta = controller.deletarRequisição(id);
        assertEquals(404, resposta.getStatusCodeValue());
        assertEquals(null, resposta.getBody());
    }

    @Test
    @DisplayName("delete() status 200")
    void deleteSemCorpo() {
        Integer id = 12;
        Mockito.when(repository.existsById(id)).thenReturn(true);

        ResponseEntity resposta = controller.deletarRequisição(id);

        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("exibe caso nao exista")
    void getExibirUm200() {
        Integer id = 10;
        Cinema cinema = Mockito.mock(Cinema.class);

        Mockito.when(repository.findById(id))
                .thenReturn(Optional.of(cinema));

        ResponseEntity resposta = controller.exibirUm(id);

        assertEquals(200, resposta.getStatusCodeValue());
    }

    @Test
    @DisplayName("Trazer tudo ok")
    void getExibirFilme() {
        List<Cinema> cinema = Arrays.asList(Mockito.mock(Cinema.class));
        Mockito.when(repository.findAll()).thenReturn(cinema);

        ResponseEntity resposta = controller.exibirFilme();

        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(cinema, resposta.getBody());
    }

    @Test
    @DisplayName("getExibirfilme sem corpo 204")
    void getExibirFilmeSemCorpo() {
        Integer id = 02;
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());

        ResponseEntity resposta = controller.exibirUm(id);

        assertEquals(404, resposta.getStatusCodeValue());
        assertEquals(null, resposta.getBody());
    }


    @Test
    @DisplayName("Exibe um sem o corpo")
    void getExibirUmSemCorpo() {
        Integer id = 02;
        Mockito.when(repository.findAll()).thenReturn(new ArrayList<>());
        ResponseEntity resposta = controller.exibirUm(id);
        assertEquals(204, resposta.getStatusCodeValue());
        assertEquals(null, resposta.getBody());
    }

    @Test
    @DisplayName("Exibe um")
    void getExibirUm() {
        Integer id =01;
        Mockito.when(repository.findById(id))
                .thenReturn(Optional.empty());
        ResponseEntity resposta = controller.exibirUm(id);
        assertEquals(200, resposta.getStatusCodeValue());
        assertEquals(null, resposta.getBody());
    }


    @Test
    @DisplayName("valida vazio")
    void validaVazio() {
        FilaObj fila = new FilaObj(20);
        Mockito.mock(FilaObj.class);
        controller.valida();
    }

//
//    @Test
//    @DisplayName("upload")
//    void importar() {
//        String arquivo = "cinema.txt";
//        ResponseEntity resposta = controller.importar(arquivo);
//    }

}
