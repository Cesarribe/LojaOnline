package com.csribeiro.sistema.repositorios;

import com.csribeiro.sistema.modelos.Cliente;
import com.csribeiro.sistema.modelos.Fornecedor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FornecedorRepositorio extends JpaRepository<Fornecedor, Long> {

}
