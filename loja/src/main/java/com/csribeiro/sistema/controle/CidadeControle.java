package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Cidade;
import com.csribeiro.sistema.modelos.Estado;
import com.csribeiro.sistema.repositorios.CidadeRepositorio;
import com.csribeiro.sistema.repositorios.EstadoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CidadeControle {

    @Autowired
    private CidadeRepositorio cidadeRepositorio;
    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @GetMapping("/cadastroCidade")
    public ModelAndView cadastrar(Cidade cidade){
        ModelAndView mv = new ModelAndView("administrativo/cidades/cadastro");
        mv.addObject("cidade", cidade);
        mv.addObject("listaEstados", estadoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarCidade")
    public ModelAndView salvar(Cidade cidade, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(cidade); // Se houver erros, retorna ao formulário de cadastro
        }
        cidadeRepositorio.saveAndFlush(cidade); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarCidade"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarCidade")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("/administrativo/cidades/lista");
        mv.addObject("listaCidades", cidadeRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarCidade/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Cidade> cidade = cidadeRepositorio.findById(id);
        if(cidade.isPresent()) {
            return cadastrar(cidade.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarCidade"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerCidade/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Cidade> cidade = cidadeRepositorio.findById(id);

        if (cidade.isPresent()) {
            cidadeRepositorio.delete(cidade.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarCidade"); // Redireciona se não encontrar o estado
    }
}
