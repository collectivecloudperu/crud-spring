package com.sistemacrud.sistemacrud.Repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.sistemacrud.sistemacrud.Model.Postre; 

@Repository
public interface InterfacePostre extends CrudRepository<Postre, Integer> {
    //
}