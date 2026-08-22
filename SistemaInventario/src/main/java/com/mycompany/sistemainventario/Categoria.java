
package com.mycompany.sistemainventario;

import java.util.ArrayList;

public class Categoria 
{
    private String nombreCat;

    private ArrayList<Producto> listaProductos;

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

    public ArrayList<Producto> getListaProductos() 
    {
        return listaProductos;
    }

    public void setListaProductos(ArrayList<Producto> listaProductos)
    {
        this.listaProductos = listaProductos;
    }
    public void agregarProducto(Producto producto) 
    {
        listaProductos.add(producto);
    }
}
