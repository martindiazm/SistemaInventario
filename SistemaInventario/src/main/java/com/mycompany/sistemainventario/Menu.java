
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
    public Inventario getInventario()
    {
        return inventario;
    }

    public void setInventario(Inventario inventario)
    {
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
                    System.out.println("\n--- AGREGAR CATEGORÍA ---");

                    System.out.print("Ingrese el nombre de la categoría: ");
                    String nombreNuevaCategoria = lector.readLine();

                    Categoria nuevaCategoria = new Categoria(nombreNuevaCategoria);

                    try {
                        inventario.agregarCategoria(nuevaCategoria);

                        System.out.println("\nCategoría agregada correctamente.");
                    }
                    catch (CategoriaYaExisteException e) {
                        System.out.println("\n" + e.getMessage());
                    }


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
                    System.out.println("\n--- MODIFICAR CATEGORÍA ---");

                    System.out.print("Ingrese el nombre actual de la categoría: ");
                    String nombreActual = lector.readLine();

                    System.out.print("Ingrese el nuevo nombre de la categoría: ");
                    String nuevoNombre = lector.readLine();

                    try {
                        inventario.modificarCategoria(nombreActual, nuevoNombre);

                        System.out.println("\nCategoría modificada correctamente.");
                    }
                    catch (CategoriaNoEncontradaException e) {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (CategoriaYaExisteException e) {
                        System.out.println("\n" + e.getMessage());
                    }



                    break;

                case 5:
                    // Eliminar categoria
                    System.out.println("\n--- ELIMINAR CATEGORÍA ---");
                    System.out.print("Ingrese el nombre de la categoría: ");
                    String nombreEliminar = lector.readLine();

                    try {
                        inventario.eliminarCategoria(nombreEliminar);
                        System.out.println("\nCategoría eliminada correctamente.");
                    }
                    catch (CategoriaNoEncontradaException e) {
                        System.out.println("\n" + e.getMessage());
                    }


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
            System.out.println("6. Caluclar precio producto");
            System.out.println("7. Registrar entrada de stock");
            System.out.println("8. Registrar venta / salida de stock");
            System.out.println("9. Volver al menú principal");
            System.out.println("==============================");

            System.out.print("Seleccione una opción: ");
            opcion = Integer.parseInt(lector.readLine());

            switch (opcion) 
            {

                case 1:
                    // Agregar producto
                    System.out.println("\n--- AGREGAR PRODUCTO ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoNuevo = lector.readLine();

                    System.out.print("Ingrese el nombre del producto: ");
                    String nombreNuevo = lector.readLine();

                    System.out.print("Ingrese la marca del producto: ");
                    String marcaNueva = lector.readLine();

                    System.out.print("Ingrese el precio del producto: ");
                    int precioNuevo = Integer.parseInt(lector.readLine());

                    System.out.print("Ingrese el precio de oferta: ");
                    int precioOfertaNuevo = Integer.parseInt(lector.readLine());

                    System.out.print("Ingrese el stock inicial: ");
                    int stockNuevo = Integer.parseInt(lector.readLine());

                    System.out.print("Ingrese la categoría: ");
                    String categoriaNueva = lector.readLine();

                    Producto nuevoProducto = new Producto(codigoNuevo, nombreNuevo, marcaNueva, precioNuevo, precioOfertaNuevo, stockNuevo);

                    try {
                        inventario.agregarProducto(categoriaNueva, nuevoProducto);

                        System.out.println("\nProducto agregado correctamente.");
                    } 
                    catch (CategoriaNoEncontradaException e) {
                        System.out.println("\n" + e.getMessage());
                    }

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

                    if (tipoBusqueda == 1) 
                    {

                        System.out.print("\nIngrese el código del producto: ");
                        String codigo = lector.readLine();
                        
                        try
                        {
                            Producto productoEncontrado = inventario.buscarProducto(codigo);

                            
                            System.out.println("\n--- PRODUCTO ENCONTRADO ---");
                            System.out.println("Código: " + productoEncontrado.getId());
                            System.out.println("Nombre: " + productoEncontrado.getNombre());
                            System.out.println("Marca: " + productoEncontrado.getMarca());
                            System.out.println("Precio: $" + productoEncontrado.getPrecio());
                            System.out.println("Stock: " + productoEncontrado.getStock());
                        } 
                        catch (ProductoNoEncontradoException e)
                        {
                            System.out.println("\n" + e.getMessage());
                        }
                    

                    } 
                    else if (tipoBusqueda == 2) 
                    {

                        System.out.print("\nIngrese el nombre del producto: ");
                        String nombreProducto = lector.readLine();

                        System.out.print("Ingrese la categoría: ");
                        String nombreCategoria = lector.readLine();

                        Categoria categoriaEncontrada = inventario.buscarCategoria(nombreCategoria);

                        if (categoriaEncontrada != null) {

                            try
                            {   
                                Producto productoEncontrado = inventario.buscarProducto(nombreProducto, categoriaEncontrada);
                            
                                System.out.println("\n--- PRODUCTO ENCONTRADO ---");
                                System.out.println("Código: " + productoEncontrado.getId());
                                System.out.println("Nombre: " + productoEncontrado.getNombre());
                                System.out.println("Marca: " + productoEncontrado.getMarca());
                                System.out.println("Precio: $" + productoEncontrado.getPrecio());
                                System.out.println("Stock: " + productoEncontrado.getStock());
                            
                            }
                            catch (ProductoNoEncontradoException e)
                            {
                                System.out.println("\n" + e.getMessage());
                            }

                        } 
                        else 
                        {
                            System.out.println("\nLa categoría no existe.");
                        }

                    } 
                    else {
                        System.out.println("\nOpción inválida.");
                    }


                    break;

                case 4:
                    // Modificar producto
                    System.out.println("\n--- MODIFICAR PRODUCTO ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoModificar = lector.readLine();


                    try 
                    {
                        
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

                    } 
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }


                    break;

                case 5:
                    // Eliminar producto
                    System.out.println("\n--- ELIMINAR PRODUCTO ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoEliminar = lector.readLine();

                    try
                    {
                        inventario.eliminarProducto(codigoEliminar);

                        System.out.println("\nProducto eliminado correctamente.");
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;

                    
                case 6:
                    // Calcular precio de un producto

                    System.out.println("\n--- CALCULAR PRECIO ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoPrecio = lector.readLine();

                    try
                    {
                        Producto productoPrecio = inventario.buscarProducto(codigoPrecio);

                        System.out.println("Precio unitario: $" + productoPrecio.calcularPrecio());

                        System.out.print("Ingrese cantidad de productos: ");

                        int cantidadPrecio = Integer.parseInt(lector.readLine());

                        System.out.println("Precio total: $" + productoPrecio.calcularPrecio(cantidadPrecio));
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;


                case 7:
                    // Registrar entrada de stock

                    System.out.println("\n--- ENTRADA DE STOCK ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoEntrada = lector.readLine();

                    try
                    {
                        Producto productoEntrada = inventario.buscarProducto(codigoEntrada);

                        System.out.print("Ingrese la cantidad a agregar: ");
                        int cantidadEntrada = Integer.parseInt(lector.readLine());

                        productoEntrada.aumentarStock(cantidadEntrada);

                        System.out.println("\nStock actualizado correctamente.");

                        System.out.println("Nuevo stock: " + productoEntrada.getStock());
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;


                case 8:
                    // Registrar venta / salida de stock
                    
                    System.out.println("\n--- REGISTRAR VENTA ---");

                    System.out.print("Ingrese el código del producto: ");
                    String codigoVenta = lector.readLine();

                    try
                    {
                        Producto productoVenta = inventario.buscarProducto(codigoVenta);

                        System.out.print("Ingrese la cantidad vendida: ");
                        int cantidadVenta = Integer.parseInt(lector.readLine());

                        productoVenta.disminuirStock(cantidadVenta);

                        System.out.println("\nVenta registrada correctamente.");

                        System.out.println("Stock actual: " + productoVenta.getStock());
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (StockInsuficienteException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;


                case 9:
                    System.out.println("\nVolviendo al menú principal...");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
                    break;
            }

        } while (opcion != 9);
    }
}
