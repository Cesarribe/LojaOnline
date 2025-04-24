package com.csribeiro.sistema.repositorios;

import com.csribeiro.sistema.modelos.Estado;
import com.csribeiro.sistema.modelos.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepositorio extends JpaRepository<Produto, Long> {

}
