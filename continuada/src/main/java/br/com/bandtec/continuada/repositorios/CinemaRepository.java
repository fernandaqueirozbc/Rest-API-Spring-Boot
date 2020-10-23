package br.com.bandtec.continuada.repositorios;

import br.com.bandtec.continuada.dominios.Cinema;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;

public interface CinemaRepository  extends JpaRepository<Cinema, Integer> {

}
