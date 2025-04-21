package com.csribeiro.sistema.repositorios;

import com.csribeiro.sistema.modelos.Cliente;
import com.csribeiro.sistema.modelos.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

}
