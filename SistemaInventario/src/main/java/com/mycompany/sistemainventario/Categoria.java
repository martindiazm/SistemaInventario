
package com.mycompany.sistemainventario;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
* Representa una categoria del supermercado y administra los productos que
* pertenecen a ella.
*/
public class Categoria 
{
    private String nombreCat;
    private ArrayList<Producto> listaProductos;
    
    // Crea una categoria sin productos
    public Categoria(String nombreCat) 
    {
        this.nombreCat = nombreCat;
        this.listaProductos = new ArrayList<>();
    }

    public String getNombreCat() 
    {
        return nombreCat;
    }

    public void setNombreCat(String nombreCat) 
    {
        this.nombreCat = nombreCat;
    }

    /**
     * Retorna una vista de solo lectura de los productos.
     * Las modificaciones deben realizarse mediante los metodos de Categoria.
     */
    public List<Producto> getListaProductos() 
    {
        return Collections.unmodifiableList(listaProductos);
    }

    // Reemplaza la lista mediante una copia defensiva
    public void setListaProductos(List<Producto> listaProductos) 
    {
        this.listaProductos = listaProductos == null
                ? new ArrayList<Producto>()
                : new ArrayList<>(listaProductos);
    }

    // Agrega un producto a la categoria
    public void agregarProducto(Producto producto) 
    {
        if (producto == null) 
        {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }
        listaProductos.add(producto);
    }

    // Indica si la categoria contiene un producto con el codigo indicado
    public boolean existeProducto(String codigo) 
    {
        for (Producto producto : listaProductos) 
        {
            if (producto.getId().equals(codigo)) 
            {
                return true;
            }
        }
        return false;
    }
    
    // Elimina el producto con el codigo indicado y retorna si se encontro
    public boolean eliminarProducto(String codigo) 
    {
        for (int i = 0; i < listaProductos.size(); i++) 
        {
            if (listaProductos.get(i).getId().equals(codigo)) 
            {
                listaProductos.remove(i);
                return true;
            }
        }
        return false;
    }

}
