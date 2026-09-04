
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
                    System.out.print("\nIngrese el nombre de la categoría: ");
                    String nombreCategoria = lector.readLine();

                    Categoria categoriaEncontrada = inventario.buscarCategoria(nombreCategoria);

                    if (categoriaEncontrada != null) {
                        System.out.println("\nCategoría encontrada: " + categoriaEncontrada.getNombreCat());
                    } else {
                        System.out.println("\nLa categoría no existe.");
                    }



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
                    System.out.println("\n--- BUSCAR PRODUCTO ---");
                    System.out.println("1. Buscar por código");
                    System.out.println("2. Buscar por nombre y categoría");
    
                    System.out.print("Seleccione una opción: ");
                    int tipoBusqueda = Integer.parseInt(lector.readLine());

                    if (tipoBusqueda == 1) {

                        System.out.print("\nIngrese el código del producto: ");
                        String codigo = lector.readLine();

                        Producto productoEncontrado = inventario.buscarProducto(codigo);

                        if (productoEncontrado != null) {
                            System.out.println("\n--- PRODUCTO ENCONTRADO ---");
                            System.out.println("Código: " + productoEncontrado.getId());
                            System.out.println("Nombre: " + productoEncontrado.getNombre());
                            System.out.println("Marca: " + productoEncontrado.getMarca());
                            System.out.println("Precio: $" + productoEncontrado.getPrecio());
                            System.out.println("Stock: " + productoEncontrado.getStock());
                        } else {
                            System.out.println("\nEl producto no existe.");
                        }

                    } else if (tipoBusqueda == 2) {

                        System.out.print("\nIngrese el nombre del producto: ");
                        String nombreProducto = lector.readLine();

                        System.out.print("Ingrese la categoría: ");
                        String nombreCategoria = lector.readLine();

                        Categoria categoriaEncontrada = inventario.buscarCategoria(nombreCategoria);

                        if (categoriaEncontrada != null) {

                            Producto productoEncontrado =
                                    inventario.buscarProducto(nombreProducto, categoriaEncontrada);

                            if (productoEncontrado != null) {
                                System.out.println("\n--- PRODUCTO ENCONTRADO ---");
                                System.out.println("Código: " + productoEncontrado.getId());
                                System.out.println("Nombre: " + productoEncontrado.getNombre());
                                System.out.println("Marca: " + productoEncontrado.getMarca());
                                System.out.println("Precio: $" + productoEncontrado.getPrecio());
                                System.out.println("Stock: " + productoEncontrado.getStock());
                            } else {
                                System.out.println("\nEl producto no existe en esa categoría.");
                            }

                        } else {
                            System.out.println("\nLa categoría no existe.");
                        }

                    } else {
                        System.out.println("\nOpción inválida.");
                    }


                    break;

                case 4:
                    // Modificar producto
                    System.out.println("\n--- MODIFICAR PRODUCTO ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoModificar = lector.readLine();

                    Producto productoModificar = inventario.buscarProducto(codigoModificar);

                    if (productoModificar != null) {

                        System.out.print("Ingrese el nuevo nombre: ");
                        String nuevoNombre = lector.readLine();

                        System.out.print("Ingrese la nueva marca: ");
                        String nuevaMarca = lector.readLine();

                        System.out.print("Ingrese el nuevo precio: ");
                        int nuevoPrecio = Integer.parseInt(lector.readLine());

                        System.out.print("Ingrese el nuevo precio de oferta: ");
                        int nuevoPrecioOferta = Integer.parseInt(lector.readLine());

                        System.out.print("Ingrese el nuevo stock: ");
                        int nuevoStock = Integer.parseInt(lector.readLine());

                        inventario.modificarProducto(
                                codigoModificar,
                                nuevoNombre,
                                nuevaMarca,
                                nuevoPrecio,
                                nuevoPrecioOferta,
                                nuevoStock
                        );

                        System.out.println("\nProducto modificado correctamente.");

                    } else {
                        System.out.println("\nEl producto no existe.");
                    }



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
