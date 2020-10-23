package br.com.bandtec.continuada.repositorios;

import br.com.bandtec.continuada.dominios.Teatro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeatroRepository extends JpaRepository<Teatro, Integer> {
}
