package com.csribeiro.sistema.controle;

import com.csribeiro.sistema.modelos.Estado;
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
public class EstadoControle {

    @Autowired
    private EstadoRepositorio estadoRepositorio;

    @GetMapping("/cadastroEstado")
    public ModelAndView cadastrar(Estado estado){
        ModelAndView mv = new ModelAndView("administrativo/estados/cadastro");
        mv.addObject("estado", estado);
        return mv;
    }

    @PostMapping("/salvarEstado")
    public ModelAndView salvar(Estado estado, BindingResult result){
        if(result.hasErrors()){
            return cadastrar(estado); // Se houver erros, retorna ao formulário de cadastro
        }
        estadoRepositorio.saveAndFlush(estado); // Salva ou atualiza o estado
        return new ModelAndView("redirect:/listarEstado"); // Redireciona para a página de listagem
    }

    @GetMapping("/listarEstado")
    public ModelAndView listar(){
        ModelAndView mv = new ModelAndView("/administrativo/estados/lista");
        mv.addObject("listaEstados", estadoRepositorio.findAll());
        return mv;
    }

    @GetMapping("/editarEstado/{id}")
    public ModelAndView editar(@PathVariable("id") Long id){
        Optional<Estado> estado = estadoRepositorio.findById(id);
        if(estado.isPresent()) {
            return cadastrar(estado.get()); // Retorna para o formulário de edição
        }
        return new ModelAndView("redirect:/listarEstado"); // Redireciona se não encontrar o estado
    }
    @GetMapping("/removerEstado/{id}")
    public ModelAndView remover(@PathVariable("id") Long id) {
        Optional<Estado> estado = estadoRepositorio.findById(id);

        if (estado.isPresent()) {
            estadoRepositorio.delete(estado.get());
            return listar(); // Retorna para o formulário de edição
        }

        return new ModelAndView("redirect:/listarEstado"); // Redireciona se não encontrar o estado
    }
}
