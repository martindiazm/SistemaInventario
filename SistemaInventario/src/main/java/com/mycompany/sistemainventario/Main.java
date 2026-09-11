
package com.mycompany.sistemainventario;

import java.io.IOException;

public class Main 
{

    public static void main(String[] args) 
    {

        Inventario inventario = new Inventario();

        Categoria lacteos = new Categoria("Lácteos");
        Categoria bebidas = new Categoria("Bebidas");
        Categoria aseo = new Categoria("Aseo");
        Categoria granos = new Categoria("Granos");
        
        try
        {
            inventario.agregarCategoria(lacteos);
            inventario.agregarCategoria(bebidas);
            inventario.agregarCategoria(aseo);
            inventario.agregarCategoria(granos);
        }
        catch (CategoriaYaExisteException e)
        {
            System.out.println(e.getMessage());
        }
        
        Producto leche = new Producto(
        "P001",
        "Leche Entera 1L",
        "Colun",
        1200,
        990,
        30
        );
        Producto yogurt = new ProductoPerecible(
        "P002",
        "Yogur Natural",
        "Soprole",
        800,
        700,
        20,
        10
        );
        ProductoGranel arroz = new ProductoGranel(
        "P005",
        "Arroz a granel",
        "Granel",
        1800,
        0,
        40,
        "kg"
        );
        Producto agua = new Producto(
        "P003",
        "Agua Mineral 1.5L",
        "Cachantun",
        1000,
        800,
        50
        );
        Producto detergente = new Producto(
        "P004",
        "Detergente 1L",
        "Omo",
        2500,
        1990,
        15
        );

        try
        {
            inventario.agregarProducto("Lácteos", leche);
            inventario.agregarProducto(lacteos, yogurt);

            inventario.agregarProducto("Bebidas", agua);
            inventario.agregarProducto("Aseo", detergente);

            inventario.agregarProducto(granos, arroz);
        }
        catch (CategoriaNoEncontradaException e)
        {
            System.out.println("Error al agregar producto: " + e.getMessage());
        }

        Menu menu = new Menu(inventario);

        try 
        {
            menu.iniciar();
        }
        catch (IOException e) 
        {
            System.out.println("Error de lectura: " + e.getMessage());
        }
    }
}
