package com.sistemacrud.sistemacrud.Service;

import java.util.List;
import java.util.Optional;

import com.sistemacrud.sistemacrud.InterfaceService.InterfacePostreService;
import com.sistemacrud.sistemacrud.Repositories.InterfacePostre;
import com.sistemacrud.sistemacrud.Model.Postre;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostreService implements InterfacePostreService {

    // Instanciamos nuestro repositorio 
    @Autowired(required = false)
    private InterfacePostre data; 

    // Método para listar todos los registros en la vista 'index.html' 
    @Override
    public List<Postre> listar() {
        return (List<Postre>)data.findAll();
    }

    // Método para listar los datos de un registro en la vista 'leer.html' 
    @Override
    public Optional<Postre> leer(int id) {
        return this.data.findById(id);
    }

    // Método para mostrar los datos de un registro en la vista 'actualizar.html'
    @Override
    public Optional<Postre> getId(int id) {
        return this.data.findById(id);
    }

    // Método para guardar un registro mediante la vista 'crear.html' 
    @Override
    public int save(Postre p) {
        int response = 0;
        Postre Postre = data.save(p);

        if(!Postre.equals(null)){
            response = 1;
        }

        return response;
    }

    // Método para eliminar un registro desde la vista 'index.html' 
    @Override
    public void delete(int id) {
        this.data.deleteById(id);
    }
    
}