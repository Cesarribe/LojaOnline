package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Entrada;
import com.csribeiro.sistema.modelos.ItemEntrada;
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
    public ModelAndView cadastrar(Entrada entrada, ItemEntrada itemEntrada){
        ModelAndView mv = new ModelAndView("administrativo/entradas/cadastro");
        mv.addObject("entrada", entrada);
        mv.addObject("itemEntrada",itemEntrada);
        mv.addObject("listaItemEntrada", this.listaItemEntrada);
        mv.addObject("listaFuncionarios", funcionarioRepositorio.findAll());
        mv.addObject("listaFornecedores", fornecedorRepositorio.findAll());
        mv.addObject("listaProdutos", produtoRepositorio.findAll());
        return mv;
    }

    @PostMapping("/salvarEntrada")
    public ModelAndView salvar(String acao, Entrada entrada, ItemEntrada itemEntrada, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(entrada, itemEntrada); // Se houver erros, retorna ao formulário de cadastro
        }

        if(acao.equals("itens")){
        this.listaItemEntrada.add(itemEntrada);
        }
        entradaRepositorio.saveAndFlush(entrada); // Salva ou atualiza o estado
        return cadastrar(new Entrada(), new ItemEntrada());
    }

    @GetMapping("/listarEntrada")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("/administrativo/entrada/lista");
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
