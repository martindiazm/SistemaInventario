

package com.mycompany.sistemainventario;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class GestorCSV {
    private static final String SEPARADOR = ",";
    
    public static void guardarInventarioCSV(Inventario inventario, String archivo) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(archivo))) {
            
            // escribe el encabezado del CSV
            writer.println("categoria,tipo,id,nombre,marca,precio,precioOferta,stock,atributoEspecial");
            
            for (Categoria categoria : inventario.getCategorias().values()) {
                String nombreCat = categoria.getNombreCat();
                
                for (Producto p : categoria.getListaProductos()) {
                    String tipo = "NORMAL";
                    String atributoEspecial = "N/A";
                    
                    if (p instanceof ProductoPerecible) {
                        tipo = "PERECIBLE";
                        atributoEspecial = String.valueOf(((ProductoPerecible) p).getDiasVencimiento());
                    } else if (p instanceof ProductoGranel) {
                        tipo = "GRANEL";
                        atributoEspecial = ((ProductoGranel) p).getUnidadMedida();
                    }
                    
                    writer.println(
                        nombreCat + SEPARADOR +
                        tipo + SEPARADOR +
                        p.getId() + SEPARADOR +
                        p.getNombre() + SEPARADOR +
                        p.getMarca() + SEPARADOR +
                        p.getPrecio() + SEPARADOR +
                        p.getPrecioOferta() + SEPARADOR +
                        p.getStock() + SEPARADOR +
                        atributoEspecial
                    );
                }
            }
        }
    }

    public static void cargarInventarioCSV(Inventario inventario, String archivo) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = reader.readLine()) != null) {
                // omite encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] datos = linea.split(SEPARADOR);
                if (datos.length < 9) continue; // solo lineas completas(formato correcto)

                String nombreCat = datos[0].trim();
                String tipo = datos[1].trim();
                String id = datos[2].trim();
                String nombre = datos[3].trim();
                String marca = datos[4].trim();
                
                
                int precio = Integer.parseInt(datos[5].trim());
                int precioOferta = Integer.parseInt(datos[6].trim());
                int stock = Integer.parseInt(datos[7].trim());
                String atributoEspecial = datos[8].trim();

                
                Categoria cat = inventario.buscarCategoria(nombreCat);
                if (cat == null) {
                    cat = new Categoria(nombreCat);
                    try {
                        inventario.agregarCategoria(cat);
                    } catch (Exception e) {
                        cat = inventario.buscarCategoria(nombreCat);
                    }
                }

                // reconstruir objetos
                Producto producto;
                if ("PERECIBLE".equalsIgnoreCase(tipo)) {
                    int diasVencimiento = Integer.parseInt(atributoEspecial);
                    producto = new ProductoPerecible(id, nombre, marca, precio, precioOferta, stock, diasVencimiento);
                } else if ("GRANEL".equalsIgnoreCase(tipo)) {
                    producto = new ProductoGranel(id, nombre, marca, precio, precioOferta, stock, atributoEspecial);
                } else {
                    producto = new Producto(id, nombre, marca, precio, precioOferta, stock);
                }

                // insertar producto
                try {
                    inventario.agregarProducto(nombreCat, producto);
                } catch (Exception e) {
                    // prevenir posibles fallos de categoría no encontrada
                }
            }
        }
    }
}