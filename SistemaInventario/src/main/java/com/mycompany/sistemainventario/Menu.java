
package com.mycompany.sistemainventario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Menu 
    {

    private BufferedReader lector;
    private Inventario inventario;

    public Menu(Inventario inventario) 
    {
        lector = new BufferedReader( new InputStreamReader(System.in));
        this.inventario = inventario;
    }

    public void iniciar() throws IOException 
    {

        int opcion;

        do {
            mostrarMenuPrincipal();

            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(lector.readLine());

            switch (opcion) 
            {

                case 1:
                    menuCategorias();
                    break;

                case 2:
                    menuProductos();
                    break;

                case 3:
                    System.out.println("\n--- REPOSICIÓN DE PRODUCTOS ---");
                    // Aqui va a ir la función(método) de reposición



                    break;

                case 4:
                    System.out.println("\nSaliendo del sistema...");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
                    break;
            }

        } while (opcion != 4);
    }

    private void mostrarMenuPrincipal() 
    {

        System.out.println("\n==============================");
        System.out.println("     SISTEMA DE INVENTARIO");
        System.out.println("==============================");
        System.out.println("1. Gestionar categorías");
        System.out.println("2. Gestionar productos");
        System.out.println("3. Reponer productos con bajo stock");
        System.out.println("4. Salir");
        System.out.println("==============================");
    }

    private void menuCategorias() throws IOException 
    {

        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("       MENÚ CATEGORÍAS");
            System.out.println("==============================");
            System.out.println("1. Agregar categoría");
            System.out.println("2. Mostrar categorías");
            System.out.println("3. Buscar categoría");
            System.out.println("4. Modificar categoría");
            System.out.println("5. Eliminar categoría");
            System.out.println("6. Volver al menú principal");
            System.out.println("==============================");

            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(lector.readLine());

            switch (opcion) 
            {

                case 1:
                    // Agregar categoria
                    


                    break;

                case 2:
                    // Mostrar categorias

                    System.out.println("\n--- CATEGORÍAS ---");
                    inventario.mostrarCategorias();

                    break;

                case 3:
                    // Buscar categoria



                    break;

                case 4:
                    // Modificar categoria



                    break;

                case 5:
                    // Eliminar categoria



                    break;

                case 6:
                    System.out.println("\nVolviendo al menú principal...");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
                    break;
            }

        } while (opcion != 6);
    }

    private void menuProductos() throws IOException 
    {

        int opcion;

        do {
            System.out.println("\n==============================");
            System.out.println("        MENÚ PRODUCTOS");
            System.out.println("==============================");
            System.out.println("1. Agregar producto");
            System.out.println("2. Mostrar productos");
            System.out.println("3. Buscar producto");
            System.out.println("4. Modificar producto");
            System.out.println("5. Eliminar producto");
            System.out.println("6. Registrar entrada de stock");
            System.out.println("7. Registrar venta / salida de stock");
            System.out.println("8. Volver al menú principal");
            System.out.println("==============================");

            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(lector.readLine());

            switch (opcion) 
            {

                case 1:
                    // Agregar producto



                    break;

                case 2:
                    // Mostrar productos

                    System.out.println("\n--- PRODUCTOS ---");
                    inventario.mostrarProductos();

                    break;

                case 3:
                    // Buscar producto



                    break;

                case 4:
                    // Modificar producto



                    break;

                case 5:
                    // Eliminar producto



                    break;

                case 6:
                    // Registrar entrada de stock



                    break;

                case 7:
                    // Registrar venta / salida de stock



                    break;

                case 8:
                    System.out.println("\nVolviendo al menú principal...");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
                    break;
            }

        } while (opcion != 8);
    }
}
