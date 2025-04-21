package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Fornecedor;
import com.csribeiro.sistema.repositorios.CidadeRepositorio;
import com.csribeiro.sistema.repositorios.FornecedorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class FornecedorControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;

    @GetMapping("/cadastroFornecedor")
    public ModelAndView cadastrar(Fornecedor fornecedor){
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/cadastro");
        mv.addObject("fornecedor", fornecedor);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarFornecedor")
    public ModelAndView salvar(Fornecedor fornecedor, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(fornecedor); // Se houver erros, retorna ao formulário de cadastro
        }
        fornecedorRepositorio.saveAndFlush(fornecedor); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarFornecedor"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarFornecedor")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/fornecedor/lista");
        mv.addObject("listaFornecedor", fornecedorRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarFornecedor/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);
        if(fornecedor.isPresent()) {
            return cadastrar(fornecedor.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarFornecedor"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerFornecedor/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Fornecedor> fornecedor = fornecedorRepositorio.findById(id);

        if (fornecedor.isPresent()) {
            fornecedorRepositorio.delete(fornecedor.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarFornecedor"); // Redireciona se não encontrar o estado
    }
}
