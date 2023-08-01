package pe.isil.mercurio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.mercurio.model.Usuario;
import pe.isil.mercurio.repository.UsuarioRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/usuarios")
public class AdminUsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    //http:localhost:8080/usuarios/nuevo --Cargar vista NUEVO CURSO
    @GetMapping("")
    String listaUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "admin/usuarios";
    }

    //http:localhost:8080/usuarios/nuevo --Cargar vista NUEVO USUARIO
    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "admin/nuevoUsuario";
    }

    //http:localhost:8080/usuarios/nuevo --Guarda USUARIO
    @PostMapping("/nuevo")
    String crear(Model model, @Validated Usuario usuario, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "admin/nuevoUsuario";
        }
        usuarioRepository.save(usuario);
        redirectAttributes.addFlashAttribute("msgExito", "El usuario fue registrado");
        return "redirect:/admin/usuarios";

    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model) {
        Usuario usuario = usuarioRepository.getById(id);
        model.addAttribute("usuario", usuario);
        return "admin/editarUsuario";
    }

    @PostMapping("/{id}/editar")
    String actualizar(@PathVariable Integer id, @Validated Usuario usuario, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        Usuario usuariodb = usuarioRepository.getById(id);
        if (bindingResult.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "admin/editarUsuario";
        }

        usuariodb.setNombres(usuario.getNombres());
        usuariodb.setApellidos(usuario.getApellidos());
        usuariodb.setEmail(usuario.getEmail());

        usuariodb.setPassword(usuario.getPassword());
        usuariodb.setRol(usuario.getRol());

        usuarioRepository.save(usuariodb);
        redirectAttributes.addFlashAttribute("msgExito", "Los datos del usuario fueron actualizados");

        return "redirect:/admin/usuarios";
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        usuarioRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgExito", "El usuario fue eliminado");
        return "redirect:/admin/usuarios";
    }

}
