package com.csribeiro.sistema.repositorios;

import com.csribeiro.sistema.modelos.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepositorio extends JpaRepository<Cidade, Long> {

}
