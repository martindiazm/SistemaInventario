
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

        inventario.agregarCategoria(lacteos);
        inventario.agregarCategoria(bebidas);
        inventario.agregarCategoria(aseo);

        
        Producto leche = new Producto(
        "P001",
        "Leche Entera 1L",
        "Colun",
        1200,
        990,
        30
        );
        Producto yogurt = new Producto(
        "P002",
        "Yogur Natural",
        "Soprole",
        800,
        20
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

        inventario.agregarProducto("Lácteos", leche);
        inventario.agregarProducto(lacteos, yogurt);
        inventario.agregarProducto("Bebidas", agua);
        inventario.agregarProducto("Aseo", detergente);

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
