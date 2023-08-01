package pe.isil.mercurio.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.mercurio.model.Usuario;
import pe.isil.mercurio.repository.UsuarioRepository;

@Controller
public class RegistroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/registro")
    public String index(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String crear(Model model, @Validated Usuario usuario, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "registro";
        }
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            model.addAttribute("msgExito", "El correo ya se encuentra registrado");
            return "registro";
        }
        if (usuario.getPassword1().equals(usuario.getPassword2())) {
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword1()));
        } else {
            model.addAttribute("msgExito", "Las contraseñas ingresadas son diferentes");
            return "registro";
        }

        usuario.setRol(Usuario.Rol.valueOf("ESTUDIANTE"));
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("msgExito", "El Usuario fue registrado");
        return "redirect:/login";
    }

}
