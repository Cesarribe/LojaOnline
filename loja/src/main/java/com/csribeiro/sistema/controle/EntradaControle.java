package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Entrada;
import com.csribeiro.sistema.modelos.ItemEntrada;
import com.csribeiro.sistema.modelos.Produto;
import com.csribeiro.sistema.repositorios.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EntradaControle {

    @Autowired
    private EntradaRepositorio entradaRepositorio;
    @Autowired
    private ItemEntradaRepositorio itemEntradaRepositorio;
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private FuncionarioRepositorio funcionarioRepositorio;
    @Autowired
    private FornecedorRepositorio fornecedorRepositorio;

    private List<ItemEntrada> listaItemEntrada = new ArrayList<ItemEntrada>();


    @GetMapping("/cadastroEntrada")
    public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada) {
        ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("itemEntrada", itemEntrada);
        mv.addObject("listaItemEntrada", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    public EntradaRepositorio getEntradaRepositorio() {
        return entradaRepositorio;
    }

    public void setEntradaRepositorio(EntradaRepositorio entradaRepositorio) {
        this.entradaRepositorio = entradaRepositorio;
    }

    public ItemEntradaRepositorio getItemEntradaRepositorio() {
        return itemEntradaRepositorio;
    }

    public void setItemEntradaRepositorio(ItemEntradaRepositorio itemEntradaRepositorio) {
        this.itemEntradaRepositorio = itemEntradaRepositorio;
    }

    public ProdutoRepositorio getProdutoRepositorio() {
        return produtoRepositorio;
    }

    public void setProdutoRepositorio(ProdutoRepositorio produtoRepositorio) {
        this.produtoRepositorio = produtoRepositorio;
    }

    public FuncionarioRepositorio getFuncionarioRepositorio() {
        return funcionarioRepositorio;
    }

    public void setFuncionarioRepositorio(FuncionarioRepositorio funcionarioRepositorio) {
        this.funcionarioRepositorio = funcionarioRepositorio;
    }

    public FornecedorRepositorio getFornecedorRepositorio() {
        return fornecedorRepositorio;
    }

    public void setFornecedorRepositorio(FornecedorRepositorio fornecedorRepositorio) {
        this.fornecedorRepositorio = fornecedorRepositorio;
    }

    @PostMapping("/salvarEntrada")
    public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada, BindingResult result) {
        if (result.hasErrors()) {
            return cadastrar(entrada, itemEntrada); // Se houver erros, retorna ao formulário de cadastro
        }

        if ("itens".equals(acao)) {
            this.listaItemEntrada.add(itemEntrada);
            entrada.setValorTotal(entrada.getValorTotal() + itemEntrada.getValor() * itemEntrada.getQuantidade());
            entrada.setQuantidadeTotal(entrada.getQuantidadeTotal() + itemEntrada.getQuantidade());

        } else if ("salvar".equals(acao)) {
            entradaRepositorio.saveAndFlush(entrada);
            for (ItemEntrada it : listaItemEntrada) {
                it.setEntrada(entrada);
                itemEntradaRepositorio.saveAndFlush(itemEntrada);

                Optional<Produto> prod = produtoRepositorio.findById(it.getProduto().getId());
                Produto produto = prod.get();
                produto.setEstoque(produto.getEstoque() + it.getQuantidade());
                produto.setPrecoVenda(Double.valueOf(it.getValor().toString()));
                produto.setPrecoCusto(Double.valueOf(it.getValorCusto()));
                produtoRepositorio.saveAndFlush(produto);

                this.listaItemEntrada = new ArrayList<>();
            }
            return cadastrar(new Entrada(), new ItemEntrada());
        }
        return cadastrar(entrada, new ItemEntrada());
    }



    @GetMapping("/listarEntrada")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("/administrativo/entradas/lista");
        mv.addObject("listaEntrada", entradaRepositorio.findAll());
        return mv;
    }

//    @GetMapping("/editarEntrada/{id}")
//    public ModelAndView editar(@PathVariable("id") Long id){
//        Optional<Entrada> entrada = entradaRepositorio.findById(id);
//        if(entrada.isPresent()) {
//            return cadastrar(entrada.get()); // Retorna para o formulário de edição
//        }
//        return new ModelAndView("redirect:/listarEntrada"); // Redireciona se não encontrar o estado
//    }

    public List<ItemEntrada> getListaItemEntrada() {
        return listaItemEntrada;
    }

    public void setListaItemEntrada(List<ItemEntrada> listaItemEntrada) {
        this.listaItemEntrada = listaItemEntrada;
    }

//    @GetMapping("/removerEntrada/{id}")
//    public ModelAndView remover(@PathVariable("id") Long id) {
//        Optional<Entrada> entrada = entradaRepositorio.findById(id);
//
//        if (entrada.isPresent()) {
//            entradaRepositorio.delete(entrada.get());
//            return listar(); // Retorna para o formulário de edição
//        }
//
//        return new ModelAndView("redirect:/listarEntrada"); // Redireciona se não encontrar o estado
//    }
}
