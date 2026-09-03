
package com.mycompany.sistemainventario;

import java.util.ArrayList;
import java.util.HashMap;

public class Inventario 
{
    private HashMap<String, Categoria> categorias;

    public Inventario() 
    {
        categorias = new HashMap<>();
    }

    public HashMap<String, Categoria> getCategorias() 
    {
        return categorias;
    }

    public void setCategorias(HashMap<String, Categoria> categorias) 
    {
        this.categorias = categorias;
    }

    // Agrega una categoría al inventario
    public void agregarCategoria(Categoria categoria) 
    {
        if (categorias.containsKey(categoria.getNombreCat()) == false) {
            categorias.put(categoria.getNombreCat(), categoria);
        }
    }

    // Agrega un producto dentro de una categoría
    public void agregarProducto(String nombreCat, Producto producto) 
    {
        Categoria categoria = categorias.get(nombreCat);

        if (categoria != null) {
            categoria.agregarProducto(producto);
        } else {
            System.out.println("La categoría no existe.");
        }
    }
    public Producto buscarProducto(String codigo)
    {
        // buscar por código
        for (Categoria categoria : categorias.values()) {
            for (Producto producto : categoria.getListaProductos()) {
                if (producto.getId().equals(codigo)) {
                    return producto;
                }
            }
        }

        return null;
    }
    public Producto buscarProducto(String nombre, Categoria categoria) {
        // buscar por nombre y categoria
        for (Producto producto : categoria.getListaProductos()) {
            if (producto.getNombre().equalsIgnoreCase(nombre)) {   
                return producto;
            }
        }

        return null;
    }
    public Categoria buscarCategoria(String nombre)
    {
        // buscar por nombre
        for (Categoria categoria : categorias.values()) {
            if (categoria.getNombreCat().equalsIgnoreCase(nombre)) {
                return categoria;
            }
        }

        return null;
    }
    public void mostrarCategorias() 
    {

        if (categorias.isEmpty()) 
        {
            System.out.println("No existen categorías.");
            return;
        }
        
        // Este for recorre todas las categorias de una coleccion sin usar indices
        // .values() retorna una coleccion con todas las categorias(valores) del mapa
        for (Categoria categoria : categorias.values()) 
        {
            System.out.println(categoria.getNombreCat());
        }
    }
    public void mostrarProductos() 
    {

    for (Categoria categoria : categorias.values()) {

        System.out.println("\nCategoría: " + categoria.getNombreCat());
        
        // Este for recorre todos los productos de la lista sin usar indices
        for (Producto producto : categoria.getListaProductos()) {

            System.out.println(
                    producto.getId()
                    + " - "
                    + producto.getNombre()
                    + " - $"
                    + producto.getPrecio()
                    + " - Stock: "
                    + producto.getStock()
            );
        }
    }
}
}
