package com.sistemacrud.sistemacrud.Controller;

import java.util.List;
import java.util.Optional;

import com.sistemacrud.sistemacrud.InterfaceService.InterfacePostreService;
import com.sistemacrud.sistemacrud.Model.Postre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping; 

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.web.bind.annotation.RequestMethod; 

@Controller
@RequestMapping
public class Controlador {

    @Autowired
    private InterfacePostreService service; 

    //private static String UPLOAD_FOLDER = "F://old_h_xampp//htdocs//xampp//nc//tutoriales//blog//sistemacrud//src//main//resources//static//uploads//";
    
    // Directorio a donde se subirán las imágenes 
    public static String UPLOADS = "src/main/resources/static/uploads/";     

    // Listar Registros  
    @GetMapping("/")
    public String listar(Model model){

        // Seleccionamos el modelo 'Postres' y listamos los registros desde la Base de datos 
        List<Postre> postres = service.listar();
        model.addAttribute("postres", postres); 

        return "index";
        
    }

    // Vista Para Crear un Registro 
    @GetMapping("/crear")
    public String crear(Model model){

        // Cargamos el modelo 'Postre' y mostramos un formulario para crear un nuevo registro 
        model.addAttribute("postres", new Postre());
        return "crear";

    }

    // Proceso para Guardar los datos en la Base de datos y subir la imágen al servidor 
    @PostMapping("/guardar")
    public String guardar(@Validated Postre p, Model model, @RequestParam("img") MultipartFile multipartFile, RedirectAttributes redirAttrs) throws IOException {
         
        // Obtenemos el nombre la imagen
        String nombreImagen = StringUtils.cleanPath(multipartFile.getOriginalFilename());        
        System.out.println(nombreImagen); 
        p.setImagen(nombreImagen); 
                 
        // Subimos la imagen al servidor 
        byte[] bytes = multipartFile.getBytes();
        Path path = Paths.get(UPLOADS + multipartFile.getOriginalFilename());
        Files.write(path, bytes); 

        // Guardamos los datos del formulario en la base de datos 
        this.service.save(p); 

        // Enviamos un mensaje a la vista principal 
        redirAttrs.addFlashAttribute("success", "Creado Correctamente !");

        // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
        return "redirect:/";

    } 

    // Vista para Leer un Registro     
    @GetMapping("/leer/{id}")   
    @RequestMapping(value="/leer/{id}", method = RequestMethod.GET) 
    public String leer(@PathVariable("id") int id, Model model){ 

        // Recibimos el 'id' del registro a leer 
        Optional<Postre> postres = this.service.leer(id); 

        if (postres.isEmpty()) {
            System.out.println("none");
        } else {
            System.out.println(postres.get().getNombre());
        }

        // Seleccionamos los datos de la tabla 'postres' 
        model.addAttribute("postres", postres);  

        // Cargamos la vista para leer un registro 
        return "leer";

    } 

    // Vista para Actualizar un Registro     
    @GetMapping("/actualizar/{id}")   
    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET) 
    public String actualizar(@PathVariable("id") int id, Model model){ 

        // Recibimos el 'id' del registro que se va Actualizar 
        Optional<Postre> postres = this.service.getId(id);

        // Seleccionamos los datos de la tabla 'postres' 
        model.addAttribute("postres", postres); 

        // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
        return "actualizar";

    } 

    // Proceso Para Actualizar un Registro 
    @PostMapping("/update/{id}")
    public String update(@Validated Postre p, @PathVariable("id") int id, Model model, @RequestParam("img") MultipartFile multipartFile, RedirectAttributes redirAttrs) throws IOException, ClassNotFoundException {

        // Obtenemos el nombre la imagen
        String nombreImagen = StringUtils.cleanPath(multipartFile.getOriginalFilename());        
        System.out.println(nombreImagen); 

        // Verificamos si el usuario cargo una imagen en el formulario 
        if(nombreImagen == null || nombreImagen.length() == 0) { 
            // Si el usuario no cargo una imagen en el formulario
            // Realizo una Conexión a la base de datos para obtener la imagen actual del registro
            // Recibimos el 'id' del registro a leer 
            Optional<Postre> postres = this.service.leer(id); 

            if (postres.isEmpty()) {
                System.out.println("none");
            } else {
                System.out.println(postres.get().getImagen());
                
                // Mantenemos el mismo nombre de imagen actual 
                p.setImagen(postres.get().getImagen()); 
            }

            System.out.println("No se cargo una imagen");             

        }
        else {
            // Si el usuario si cargo una imagen en el formulario
            // Seleccionamos el nombre de la imagen, el cual se guardará en la columna 'img' 
            p.setImagen(nombreImagen); 

            // Subimos la imagen al servidor 
            byte[] bytes = multipartFile.getBytes();
            Path path = Paths.get(UPLOADS + multipartFile.getOriginalFilename());
            Files.write(path, bytes);

            System.out.println("Si se cargo una imagen");            
            
        }           

        // Guardamos los datos del formulario en la base de datos 
        this.service.save(p); 

        // Enviamos un mensaje a la vista principal 
        redirAttrs.addFlashAttribute("success", "Actualizado Correctamente !");

        // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
        return "redirect:/";

    }

    // Eliminar un Registro 
    @GetMapping("/eliminar/{id}") 
    public String delete(@PathVariable int id, Model model, RedirectAttributes redirAttrs){

        // Recibimos el 'id' del registro a eliminar  
        this.service.delete(id);

        // Enviamos un mensaje a la vista principal 
        redirAttrs.addFlashAttribute("success", "Eliminado Correctamente !");

        // Luego de realizar las tareas correspondientes, redireccionamos a la vista principal 
        return "redirect:/";

    }
    
}