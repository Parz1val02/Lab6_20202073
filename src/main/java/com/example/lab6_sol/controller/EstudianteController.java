package com.example.lab6_sol.controller;

import com.example.lab6_sol.entity.Usuario;
import com.example.lab6_sol.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/estudiante")
public class EstudianteController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/lista")
    public String listaUsuarios(Model model){
        List<Usuario> estudiantes = usuarioRepository.findByRolid(5);
        model.addAttribute("estudiantes", estudiantes);
        return "lista_usuarios";
    }

    @GetMapping("/nuevo")
    public String nuevoUsuario(@ModelAttribute("usuario") Usuario usuario, Model model) {
        return "usuarioForm";
    }
    @GetMapping("/edit")
    public String nuevo(@ModelAttribute("usuario")Usuario usurio, Model model, @RequestParam("id") Integer id){
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);

        if (optionalUsuario.isPresent()) {
            Usuario usuario = optionalUsuario.get();
            model.addAttribute("usuario", usuario);
            return "usuarioForm";
        } else {
            return "redirect:/estudiant/lista";
        }
    }
    @PostMapping("/guardar")
    public String guardarUsuario(@ModelAttribute("usuario") @Valid Usuario usuario, BindingResult bindingResult, RedirectAttributes attr){

        if(bindingResult.hasErrors()){
            return "usuarioForm";
        }else{
            if(usuario.getId()==0){
                attr.addFlashAttribute("msg", "Estudiante creado exitosamente");
            }else{
                attr.addFlashAttribute("msg", "EStudiante actualizado exitosamente");
            }
            usuario.setRolid(5);
            usuario.setActivo(true);
            //usuario.setPassword(new BCryptPasswordEncoder().encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
            return "redirect:/estudiante/lista";
        }
    }
}
