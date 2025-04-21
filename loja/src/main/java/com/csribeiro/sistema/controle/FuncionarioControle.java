package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Funcionario;
import com.csribeiro.sistema.repositorios.CidadeRepositorio;
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
public class FuncionarioControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;

    @GetMapping("/cadastroFuncionario")
    public ModelAndView cadastrar(Funcionario funcionario){
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/cadastro");
        mv.addObject("funcionario", funcionario);
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarFuncionario")
    public ModelAndView salvar(Funcionario funcionario, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(funcionario); // Se houver erros, retorna ao formulário de cadastro
        }
        funcionarioRepositorio.saveAndFlush(funcionario); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarFuncionario"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarFuncionario")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("administrativo/funcionarios/lista");
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarFuncionario/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);
        if(funcionario.isPresent()) {
            return cadastrar(funcionario.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarFuncionario"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerFuncionario/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Funcionario> funcionario = funcionarioRepositorio.findById(id);

        if (funcionario.isPresent()) {
            funcionarioRepositorio.delete(funcionario.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarFuncionario"); // Redireciona se não encontrar o estado
    }
}
