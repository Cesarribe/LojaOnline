package com.csribeiro.sistema.repositorios;

import com.csribeiro.sistema.modelos.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FuncionarioRepositorio extends JpaRepository<Funcionario, Long> {

}
