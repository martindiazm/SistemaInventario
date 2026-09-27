
package com.mycompany.sistemainventario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Collections;

/**
 * Representa el inventario general del supermercado.
 *
 * Administra las categorias registradas y centraliza las operaciones de
 * busqueda, modificacion y eliminacion de productos y categorias.
 */
public class Inventario 
{
    private HashMap<String, Categoria> categorias;
    
    // Crea un inventario vacio. 
    public Inventario() 
    {
        categorias = new HashMap<>();
    }
    
    /**
     * Retorna una vista de solo lectura de las categorias.
     * Las modificaciones deben realizarse mediante los metodos de esta clase.
     */
    public Map<String, Categoria> getCategorias() 
    {
        return Collections.unmodifiableMap(categorias);
    }

    /**
     * Reemplaza las categorias mediante una copia defensiva.
     * No se conserva una referencia externa a la coleccion recibida.
     */
    public void setCategorias(Map<String, Categoria> categorias) 
    {
        if (categorias == null) 
        {
            this.categorias = new HashMap<>();
        } 
        else 
        {
            this.categorias = new HashMap<>(categorias);
        }
    }

    // Agrega una categoria si su nombre no esta ocupado. 
    public void agregarCategoria(Categoria categoria) throws CategoriaYaExisteException 
    {
        if (categoria == null || categoria.getNombreCat() == null || categoria.getNombreCat().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("La categoria debe tener un nombre valido.");
        }

        if (categorias.containsKey(categoria.getNombreCat())) 
        {
            throw new CategoriaYaExisteException("La categoria '" + categoria.getNombreCat() + "' ya existe.");
        }

        categorias.put(categoria.getNombreCat(), categoria);
    }

    /**
     * Agrega un producto a una categoria existente.
     * El codigo del producto se valida para evitar identificadores duplicados.
     */
    public void agregarProducto(String nombreCat, Producto producto) throws CategoriaNoEncontradaException 
    {
        Categoria categoria = categorias.get(nombreCat);

        if (categoria == null) 
        {
            throw new CategoriaNoEncontradaException("No se encontro la categoria: " + nombreCat);
        }

        validarProductoNuevo(producto);
        categoria.agregarProducto(producto);
    }

    // Agrega un producto a una categoria ya obtenida del inventario
    public void agregarProducto(Categoria categoria, Producto producto) throws CategoriaNoEncontradaException 
    {
        if (categoria == null || !categorias.containsValue(categoria)) 
        {
            throw new CategoriaNoEncontradaException("La categoria no existe.");
        }

        validarProductoNuevo(producto);
        categoria.agregarProducto(producto);
    }
    
    // Verifica que un producto nuevo tenga un identificador unico
    private void validarProductoNuevo(Producto producto) 
    {
        if (producto == null) 
        {
            throw new IllegalArgumentException("El producto no puede ser nulo.");
        }

        if (producto.getId() == null || producto.getId().trim().isEmpty()) 
        {
            throw new IllegalArgumentException("El producto debe tener un codigo valido.");
        }

        if (existeProducto(producto.getId())) 
        {
            throw new IllegalArgumentException("Ya existe un producto con el codigo: " + producto.getId());
        }
    }
    // Indica si existe un producto con el codigo recibido
    public boolean existeProducto(String codigo) 
    {
        if (codigo == null) 
        {
            return false;
        }

        for (Categoria categoria : categorias.values()) 
        {
            if (categoria.existeProducto(codigo)) 
            {
                return true;
            }
        }
        return false;
    }

    // Busca un producto por su codigo unico
    public Producto buscarProducto(String codigo) throws ProductoNoEncontradoException
    {
        for (Categoria categoria : categorias.values()) 
        {
            for (Producto producto : categoria.getListaProductos()) 
            {
                if (producto.getId().equals(codigo)) 
                {
                    return producto;
                }
            }
        }

        throw new ProductoNoEncontradoException("No se encontró un producto con el código: " + codigo);
    }

    // Busca un producto por nombre dentro de una categoria especifica
    public Producto buscarProducto(String nombre, Categoria categoria) throws ProductoNoEncontradoException
    {
        if (categoria == null) 
        {
            throw new ProductoNoEncontradoException("La categoria no existe.");
        }

        for (Producto producto : categoria.getListaProductos()) 
        {
            if (producto.getNombre().equalsIgnoreCase(nombre)) 
            {   
                return producto;
            }
        }

        throw new ProductoNoEncontradoException("No se encontró el producto: " + nombre);
    }

    // Busca una categoria por nombre sin lanzar excepcion si no existe
    public Categoria buscarCategoria(String nombre)
    {
        for (Categoria categoria : categorias.values()) 
        {
            if (categoria.getNombreCat().equalsIgnoreCase(nombre)) 
            {
                return categoria;
            }
        }
        return null;
    }
    
    // Modifica los datos principales de un producto existente
    public void modificarProducto(String codigo, String nuevoNombre, String nuevaMarca, int nuevoPrecio, int nuevoPrecioOferta, int nuevoStock) throws ProductoNoEncontradoException
    {
        Producto producto = buscarProducto(codigo);

        producto.setNombre(nuevoNombre);
        producto.setMarca(nuevaMarca);
        producto.setPrecio(nuevoPrecio);
        producto.setPrecioOferta(nuevoPrecioOferta);
        producto.setStock(nuevoStock);
        
    }
    
    // Cambia el nombre de una categoria, evitando nombres duplicados
    public void modificarCategoria(String nombreActual, String nuevoNombre) throws CategoriaNoEncontradaException, CategoriaYaExisteException {
         String claveActual = null;

        for (String nombre : categorias.keySet()) 
        {
            if (nombre.equalsIgnoreCase(nombreActual)) 
            {
                claveActual = nombre;
                break;
            }
        }

        if (claveActual == null) {
            throw new CategoriaNoEncontradaException("No se encontró la categoría: " + nombreActual);
        }

        for (String nombre : categorias.keySet()) {
            if (nombre.equalsIgnoreCase(nuevoNombre) && !nombre.equalsIgnoreCase(claveActual)) {
                throw new CategoriaYaExisteException("La categoría '" + nuevoNombre + "' ya existe.");
            }
        }

        Categoria categoria = categorias.get(claveActual);

        categorias.remove(claveActual);

        categoria.setNombreCat(nuevoNombre);

        categorias.put(nuevoNombre, categoria);
    }

    // Muestra los nombres de todas las categorias
    public void mostrarCategorias() 
    {

        if (categorias.isEmpty()) 
        {
            System.out.println("No existen categorías.");
            return;
        }
        
        for (Categoria categoria : categorias.values()) 
        {
            System.out.println(categoria.getNombreCat());
        }
    }

    // Muestra todos los productos agrupados por categoria
    public void mostrarProductos() 
    {
        for (Categoria categoria : categorias.values()) 
        {
            System.out.println("\nCategoría: " + categoria.getNombreCat());

            for (Producto producto : categoria.getListaProductos())
            {
                producto.mostrarInformacion();
            }
        }
    }

    // Elimina un producto identificado por su codigo
    public void eliminarProducto(String codigo) throws ProductoNoEncontradoException
    {
        for (Categoria categoria : categorias.values()) 
        {
            if (categoria.eliminarProducto(codigo)) 
            {
                return;
            }
        }

        throw new ProductoNoEncontradoException("No se encontro un producto con el codigo: " + codigo);

    }

    // Elimina una categoria identificada por su nombre
    public void eliminarCategoria(String nombre) throws CategoriaNoEncontradaException {
        String claveActual = null;

        for (String nombreCategoria : categorias.keySet()) {
            if (nombreCategoria.equalsIgnoreCase(nombre)) {
                claveActual = nombreCategoria;
                break;
            }
        }

        if (claveActual == null) {
            throw new CategoriaNoEncontradaException("No se encontró la categoría: " + nombre);
        }

        categorias.remove(claveActual);
    }

    /**
     * Obtiene una nueva lista con los productos cuyo stock esta bajo el limite.
     * La lista devuelta no es la lista interna de ninguna categoria.
     */
    public ArrayList<Producto> obtenerProductosBajoStock(int limite)
    {
        ArrayList<Producto> productosBajoStock = new ArrayList<>();

        for (Categoria categoria : categorias.values())
        {
            for (Producto producto : categoria.getListaProductos())
            {
                if (producto.getStock() < limite)
                {
                    productosBajoStock.add(producto);
                }
            }
        }

        return productosBajoStock;
    }
}