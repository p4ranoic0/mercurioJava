package pe.isil.mercurio.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pe.isil.mercurio.model.Curso;
import pe.isil.mercurio.repository.CursoRepository;
import pe.isil.mercurio.service.FileSystemStorageService;


@Controller
@RequestMapping("/admin/cursos")
public class AdminCursoController {

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private FileSystemStorageService fileSystemStorageService;

    //LISTAR LOS CURSOS
    //http:localhost:8080/cursos

    @GetMapping("")
    String index(Model model,
                 @PageableDefault(size = 5,
                         sort = "titulo") Pageable pageable,
                 @RequestParam(required = false) String titulo) {
        /*List<Curso> cursos = cursoRepository.findAll(); //Obtener todos los cursos de la base de datos
        model.addAttribute("cursos", cursos);
        return "index"; //retorna el atributo cursos a la vista: index*/

        Page<Curso> cursos;

        if (titulo != null && !titulo.trim().isEmpty()) { //que el titulo es diferente de nulo y que no es vacio
            cursos = cursoRepository.findByTituloContaining(titulo, pageable);
        } else {
            cursos = cursoRepository.findAll(pageable);
        }
        model.addAttribute("cursos", cursos);
        return "/admin/index";

    }

    //http:localhost:8080/admin/cursos/nuevo -> nuevo.html => mostrar el formulario
    @GetMapping("/nuevo")
    String nuevo(Model model) {
        model.addAttribute("curso", new Curso());
        return "admin/nuevo";
    }

    //http:localhost:8080/cursos/nuevo => guardar o registrar un nuevo curso en la base de datos
    @PostMapping("/nuevo")
    String crear(@Validated Curso curso, BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        //valida si el curso se carga una imagen, si no hay imagen emite un msje de error
        if (curso.getImagen().isEmpty()) {
            bindingResult.rejectValue("imagen", "MultipartNotEmpty");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "admin/nuevo";
        }

        String rutaImagen = fileSystemStorageService.store(curso.getImagen());
        curso.setRutaImagen(rutaImagen);

        cursoRepository.save(curso);
        redirectAttributes.addFlashAttribute("msgExito", "El curso se registró correctamente");
        return "redirect:/admin/cursos";
    }

    @GetMapping("/{id}/editar")
    String editar(@PathVariable Integer id, Model model) {
        Curso curso = cursoRepository.getById(id);

        model.addAttribute("curso", curso);
        return "admin/editar";
    }

    @PostMapping("/{id}/editar")
    String actualizar(@PathVariable Integer id, Curso curso, Model model, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        Curso cursoFromDB = cursoRepository.getById(id);

        if (bindingResult.hasErrors()) {
            model.addAttribute("curso", curso);
            return "admin/editar";
        }

        if (!curso.getImagen().isEmpty()) {
            String rutaImagen = fileSystemStorageService.store(curso.getImagen());
            cursoFromDB.setRutaImagen(rutaImagen);
        }

        cursoFromDB.setTitulo(curso.getTitulo());
        cursoFromDB.setDescripcion(curso.getDescripcion());
        cursoFromDB.setPrecio(curso.getPrecio());

        cursoRepository.save(cursoFromDB); //save insertar(id=null) , actualizar(id=valor)
        redirectAttributes.addFlashAttribute("msgExito", "El curso fué actualizado correctamente");
        return "redirect:/admin/cursos";
    }

    @PostMapping("/{id}/eliminar")
    String eliminar(@PathVariable Integer id, RedirectAttributes redirectAttributes) {

        //Curso curso = cursoRepository.getById(id);
        //cursoRepository.delete(curso);

        cursoRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("msgExito", "El curso se eleminó correctamente");
        return "redirect:/admin/cursos";
    }










    /*
    //api que agregue nuevos cursos
    @ResponseBody
    @PostMapping("/crear")
    Curso crear(@RequestBody Curso p_curso)
    {
        return cursoRepository.save(p_curso);//el metodo save= inserta en la base de datos
    }

    @ResponseBody
    @PostMapping("/{id}/eliminar")
    void eliminar(@PathVariable Integer id)
    {
        Curso curso = cursoRepository.getById(id);
        cursoRepository.delete(curso);
    }

    @ResponseBody
    @PostMapping("/{id}/actualizar")
    Curso actualizar(@PathVariable Integer id, @RequestBody Curso p_curso)
    {
        Curso cursoFromDB = cursoRepository.getById(id);
        cursoFromDB.setTitulo(p_curso.getTitulo());
        cursoFromDB.setPrecio(p_curso.getPrecio());

        return cursoRepository.save(cursoFromDB);
    }

    */

}