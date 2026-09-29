
package com.mycompany.sistemainventario;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

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

    // Inicia el menu principal y mantiene la ejecucion hasta elegir salir
    public void iniciar() throws IOException 
    {

        int opcion;

        do {
            mostrarMenuPrincipal();

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) 
            {

                case 1:
                    menuCategorias();
                    break;

                case 2:
                    menuProductos();
                    break;

                case 3:
                    reponerProductosBajoStock();
                    break;

                case 4:
                    System.out.println("\nSaliendo del sistema...");
                    System.out.println("Guardando inventario.csv...");
                    GestorCSV.guardar(inventario);
                    System.out.println("Datos guardados con éxito.");
                    break;

                default:
                    System.out.println("\nOpción inválida.");
                    break;
            }

        } while (opcion != 4); // Se actualizó la condición de salida a 6
    }
    
    // Lee repetidamente un entero hasta recibir una entrada valida
    private int leerEntero(String mensaje) throws IOException {
        while (true) {
            System.out.println(mensaje);
            String entrada = lector.readLine();
            try {
                return Integer.parseInt(entrada.trim());
            } catch (NumberFormatException e) {
                System.out.println("Entrada invalida. Debe ingresar un numero entero.");
            }
        }
    }
    
    // Lee un entero que debe ser mayor que cero
    private int leerEnteroPositivo(String mensaje) throws IOException {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor > 0) return valor;
            System.out.println("El valor debe ser mayor que cero.");
        }
    }
    
    // Lee un entero que puede ser cero, pero no negativo
    private int leerEnteroNoNegativo(String mensaje) throws IOException {
        while (true) {
            int valor = leerEntero(mensaje);
            if (valor >= 0) return valor;
            System.out.println("El valor no puede ser negativo.");
        }
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
    
    private void reponerProductosBajoStock() throws IOException {
        System.out.println("\n--- REPOSICION DE PRODUCTOS ---");
        int stockMinimo = leerEnteroNoNegativo("Ingrese el stock minimo: ");
        ArrayList<Producto> productosBajoStock = inventario.obtenerProductosBajoStock(stockMinimo);

        if (productosBajoStock.isEmpty()) {
            System.out.println("\nNo existen productos con stock menor a " + stockMinimo);
            return;
        }

        System.out.println("\n--- PRODUCTOS CON BAJO STOCK ---");
        for (Producto producto : productosBajoStock) {
            System.out.println(producto.getId() + " - " + producto.getNombre()
                    + " - Stock actual: " + producto.getStock());
        }

        String codigo = leerTexto("Ingrese el codigo del producto que desea reponer: ");
        try {
            Producto producto = inventario.buscarProducto(codigo);
            if (producto.getStock() >= stockMinimo) {
                System.out.println("\nEl producto seleccionado no pertenece al grupo de bajo stock.");
                return;
            }

            int cantidad = leerEnteroPositivo("Ingrese la cantidad a reponer: ");
            producto.aumentarStock(cantidad);
            System.out.println("\nProducto repuesto correctamente.");
            System.out.println("Nuevo stock de " + producto.getNombre() + ": " + producto.getStock());
        } catch (ProductoNoEncontradoException e) {
            System.out.println("\n" + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("\n" + e.getMessage());
        }
    }

    private String leerTexto(String mensaje) throws IOException {
        while (true) {
            System.out.println(mensaje);
            String texto = lector.readLine();
            if (texto != null && !texto.trim().isEmpty()) return texto.trim();
            System.out.println("La entrada no puede estar vacia.");
        }
    }
    
     private void mostrarProducto(Producto p) {
        System.out.println("\n--- PRODUCTO ENCONTRADO ---");
        System.out.println("Codigo: " + p.getId());
        System.out.println("Nombre: " + p.getNombre());
        System.out.println("Marca: " + p.getMarca());
        System.out.println("Precio: $" + p.getPrecio());
        System.out.println("Stock: " + p.getStock());
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

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) 
            {

                case 1:
                    // Agregar categoria
                    System.out.println("\n--- AGREGAR CATEGORÍA ---");

                    String nombreNuevaCategoria = leerTexto("Ingrese el nombre de la categoria: ");

                    Categoria nuevaCategoria = new Categoria(nombreNuevaCategoria);

                    try {
                        inventario.agregarCategoria(nuevaCategoria);

                        System.out.println("\nCategoría agregada correctamente.");
                    }
                    catch (CategoriaYaExisteException e) {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (RuntimeException e) {
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
                    String nombreCategoria = leerTexto("\nIngrese el nombre de la categoria: ");

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

                    String nombreActual = leerTexto("Ingrese el nombre actual de la categoria: ");

                    String nuevoNombre = leerTexto("Ingrese el nuevo nombre de la categoria: ");

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

                    String nombreEliminar = leerTexto("Ingrese el nombre de la categoria: ");

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
            System.out.println("6. Calcular precio producto");
            System.out.println("7. Registrar entrada de stock");
            System.out.println("8. Registrar venta / salida de stock");
            System.out.println("9. Volver al menú principal");
            System.out.println("==============================");

            opcion = leerEntero("Seleccione una opcion: ");

            switch (opcion) 
            {

                case 1:
                    // Agregar producto
                    System.out.println("\n--- AGREGAR PRODUCTO ---");

                    System.out.println("1. Producto normal");
                    System.out.println("2. Producto perecible");
                    System.out.println("3. Producto a granel");

                    int tipoProducto = leerEntero("Seleccione el tipo: ");
                    
                     if (tipoProducto < 1 || tipoProducto > 3) 
                    {
                        System.out.println("\nTipo de producto invalido.");
                        break;
                    }

                    String codigoNuevo = leerTexto("Ingrese el codigo del producto: ");
                    if (inventario.existeProducto(codigoNuevo)) 
                    {
                        System.out.println("\nYa existe un producto con el codigo: " + codigoNuevo);
                        break;
                    }

                    String nombreNuevo = leerTexto("Ingrese el nombre del producto: ");
                    String marcaNueva = leerTexto("Ingrese la marca del producto: ");
                    int precioNuevo = leerEnteroPositivo("Ingrese el precio del producto: ");
                    int precioOfertaNuevo = leerEnteroNoNegativo("Ingrese el precio de oferta (0 si no tiene): ");
                    int stockNuevo = leerEnteroNoNegativo("Ingrese el stock inicial: ");
                    String categoriaNueva = leerTexto("Ingrese la categoria: ");

                    Producto nuevoProducto;

                    if (tipoProducto == 1)
                    {
                        nuevoProducto = new Producto(
                                codigoNuevo,
                                nombreNuevo,
                                marcaNueva,
                                precioNuevo,
                                precioOfertaNuevo,
                                stockNuevo
                        );
                    }
                    else if (tipoProducto == 2)
                    {
                        int diasVencimiento = leerEnteroNoNegativo("Ingrese los dias para el vencimiento: ");

                        nuevoProducto = new ProductoPerecible(
                                codigoNuevo,
                                nombreNuevo,
                                marcaNueva,
                                precioNuevo,
                                precioOfertaNuevo,
                                stockNuevo,
                                diasVencimiento
                        );
                    }
                    else 
                    {
                        String unidadMedida = leerTexto("Ingrese la unidad de medida (kg, litro, etc.): ");

                        nuevoProducto = new ProductoGranel(
                                codigoNuevo,
                                nombreNuevo,
                                marcaNueva,
                                precioNuevo,
                                precioOfertaNuevo,
                                stockNuevo,
                                unidadMedida
                        );
                    }

                    try
                    {
                        inventario.agregarProducto(categoriaNueva, nuevoProducto);

                        System.out.println("\nProducto agregado correctamente.");
                    }
                    catch (CategoriaNoEncontradaException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (RuntimeException e)
                    {
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

                    System.out.println("1. Buscar por codigo");
                    System.out.println("2. Buscar por nombre y categoria");

                    int tipoBusqueda = leerEntero("Seleccione una opcion: ");
                    
                    try 
                    {
                        Producto producto;
                        if (tipoBusqueda  == 1) {
                            producto = inventario.buscarProducto(leerTexto("Ingrese el codigo del producto: "));
                        } 
                        else if (tipoBusqueda  == 2) {
                            String nombre = leerTexto("Ingrese el nombre del producto: ");
                            Categoria categoria = inventario.buscarCategoria(leerTexto("Ingrese la categoria: "));
                            if (categoria == null) {
                                System.out.println("\nLa categoria no existe.");
                                break;
                            }
                            producto = inventario.buscarProducto(nombre, categoria);
                        } 
                        else {
                            System.out.println("\nOpcion invalida.");
                            break;
                        }
                        mostrarProducto(producto);
                    } 
                    catch (ProductoNoEncontradoException e)    
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;

                case 4:
                    // Modificar producto
                    System.out.println("\n--- MODIFICAR PRODUCTO ---");

                    String codigoModificar = leerTexto("Ingrese el codigo del producto: ");

                    try 
                    {
                        inventario.buscarProducto(codigoModificar);
                        String nuevoNombre = leerTexto("Ingrese el nuevo nombre: ");
                        String nuevaMarca = leerTexto("Ingrese la nueva marca: ");
                        int nuevoPrecio = leerEnteroPositivo("Ingrese el nuevo precio: ");
                        int nuevoPrecioOferta = leerEnteroNoNegativo("Ingrese el nuevo precio de oferta: ");
                        int nuevoStock = leerEnteroNoNegativo("Ingrese el nuevo stock: ");

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

                    String codigoEliminar = leerTexto("Ingrese el codigo del producto: ");

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

                    String codigoPrecio = leerTexto("Ingrese el codigo del producto: ");

                    try
                    {
                        Producto productoPrecio = inventario.buscarProducto(codigoPrecio);

                        System.out.println("Precio unitario: $" + productoPrecio.calcularPrecio());

                        int cantidadPrecio = leerEnteroPositivo("Ingrese cantidad de productos: ");

                        System.out.println("Precio total: $" + productoPrecio.calcularPrecio(cantidadPrecio));
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }

                    break;


                case 7:
                    // Registrar entrada de stock
                    System.out.println("\n--- ENTRADA DE STOCK ---");

                    String codigoEntrada = leerTexto("Ingrese el codigo del producto: ");

                    try
                    {
                        Producto productoEntrada = inventario.buscarProducto(codigoEntrada);

                        int cantidadEntrada = leerEnteroPositivo("Ingrese la cantidad a agregar: ");

                        productoEntrada.aumentarStock(cantidadEntrada);

                        System.out.println("\nStock actualizado correctamente.");

                        System.out.println("Nuevo stock: " + productoEntrada.getStock());
                    }
                    catch (ProductoNoEncontradoException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }
                    catch (RuntimeException e)
                    {
                        System.out.println("\n" + e.getMessage());
                    }
                    

                    break;


                case 8:
                    // Registrar venta / salida de stock
                    System.out.println("\n--- REGISTRAR VENTA ---");

                    String codigoVenta = leerTexto("Ingrese el codigo del producto: ");

                    try
                    {
                        Producto productoVenta = inventario.buscarProducto(codigoVenta);

                        int cantidadVenta = leerEnteroPositivo("Ingrese la cantidad vendida: ");

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
