package com.sistemacrud.sistemacrud.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

// Modelo

@Entity
@Table( name = "postres" )
public class Postre {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
    public String nombre;
    public String precio;
    public String stock;
    public String img;


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getImagen() {
		return img;
	}

	public void setImagen(String img) {
		this.img = img;
	}

} 