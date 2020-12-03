package br.com.bandtec.ac3fernanda.repositorios;

import br.com.bandtec.ac3fernanda.dominios.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CinemaRepository extends JpaRepository<Cinema, Integer> {

    @Query("select c from Cinema c")
    List<Cinema> findAll();

    Cinema findById(int id);
}
