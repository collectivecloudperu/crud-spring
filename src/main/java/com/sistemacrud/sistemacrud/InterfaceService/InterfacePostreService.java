package com.sistemacrud.sistemacrud.InterfaceService;
import java.util.List;
import java.util.Optional;

import com.sistemacrud.sistemacrud.Model.Postre;

public interface InterfacePostreService {
    
    // Métodos 
    public List<Postre> listar();
    public Optional<Postre> leer(int id);
    public Optional<Postre> getId(int id);
    public int save(Postre p);
    public void delete(int id);   

}