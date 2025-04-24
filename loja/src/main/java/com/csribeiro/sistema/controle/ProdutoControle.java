package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Produto;

import com.csribeiro.sistema.repositorios.ProdutoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class ProdutoControle {

    @Autowired
    private ProdutoRepositorio produtoRepositorio;

    @GetMapping("/cadastroProduto")
    public ModelAndView cadastrar(Produto produto){
        ModelAndView mv = new ModelAndView("administrativo/produto/cadastro");
        mv.addObject("produto", produto);
        return mv;
    }

    @PostMapping("/salvarProduto")
    public ModelAndView salvar(Produto produto, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(produto); // Se houver erros, retorna ao formulário de cadastro
        }
        produtoRepositorio.saveAndFlush(produto); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarProduto"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarProduto")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("/administrativo/produto/lista");
        mv.addObject("listaProduto", produtoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarProduto/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Produto> produto = produtoRepositorio.findById(id);
        if(produto.isPresent()) {
            return cadastrar(produto.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarProduto"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerProduto/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Produto> produto = produtoRepositorio.findById(id);

        if (produto.isPresent()) {
            produtoRepositorio.delete(produto.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarProduto"); // Redireciona se não encontrar o estado
    }
}
