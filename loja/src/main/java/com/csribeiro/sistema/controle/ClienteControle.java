package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Cliente;
import com.csribeiro.sistema.modelos.Funcionario;
import com.csribeiro.sistema.repositorios.CidadeRepositorio;
import com.csribeiro.sistema.repositorios.ClienteRepositorio;
import com.csribeiro.sistema.repositorios.FuncionarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ClienteControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @GetMapping("/cadastroCliente")
    public ModelAndView cadastrar(Cliente cliente){
        ModelAndView mv = new ModelAndView("administrativo/cliente/cadastro");
        mv.addObject("cliente", cliente);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarCliente")
    public ModelAndView salvar(Cliente cliente, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(cliente); // Se houver erros, retorna ao formulário de cadastro
        }
        clienteRepositorio.saveAndFlush(cliente); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarCliente"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarCliente")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/cliente/lista");
        mv.addObject("listaCliente", clienteRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarCliente/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cliente> cliente = clienteRepositorio.findById(id);
        if(cliente.isPresent()) {
            return cadastrar(cliente.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarCliente"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerCliente/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cliente> cliente = clienteRepositorio.findById(id);

        if (cliente.isPresent()) {
            clienteRepositorio.delete(cliente.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarCliente"); // Redireciona se não encontrar o estado
    }
}
