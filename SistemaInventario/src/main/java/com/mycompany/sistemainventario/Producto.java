
package com.mycompany.sistemainventario;

/**
 * Representa un producto generico del supermercado.
 * Contiene los datos comunes y las operaciones de precio y stock.
 */
public class Producto 
{
    private String id;
    private String nombre;
    private String marca;
    private int precio;
    private int precioOferta;
    private int stock;

    // Crea un producto sin precio de oferta
    public Producto(String id, String nombre, String marca, int precio, int stock) 
    {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.stock = stock;
    }

    // Crea un producto con precio normal, oferta y stock inicial
    public Producto(String id, String nombre, String marca, int precio, int precioOferta, int stock) 
    {
        this.id = id;
        this.nombre = nombre;
        this.marca = marca;
        this.precio = precio;
        this.precioOferta = precioOferta;
        this.stock = stock;
    }

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getNombre() 
    {
        return nombre;
    }

    public void setNombre(String nombre) 
    {
        this.nombre = nombre;
    }
    
    public String getMarca() 
    {
        return marca;
    }

    public void setMarca(String marca) 
    {
        this.marca = marca;
    }

    public int getPrecio() 
    {
        return precio;
    }

    public void setPrecio(int precio) 
    {
        this.precio = precio;
    }

    public int getPrecioOferta() 
    {
        return precioOferta;
    }

    public void setPrecioOferta(int precioOferta) 
    {
        this.precioOferta = precioOferta;
    }

    public int getStock() 
    {
        return stock;
    }

    public void setStock(int stock) 
    {
        this.stock = stock;
    }
    // Aumenta el stock con una cantidad positiva
    public void aumentarStock(int cantidad) 
    {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor que cero.");
        }
        stock += cantidad;
    }

    // Disminuye el stock sin permitir cantidades invalidas o superiores al stock
    public void disminuirStock(int cantidad) throws StockInsuficienteException {
        if (cantidad <= 0) {
            throw new StockInsuficienteException("La cantidad debe ser mayor que cero.");
        }
        if (cantidad > stock) {
            throw new StockInsuficienteException("No existe stock suficiente.");
        }
        stock -= cantidad;
    }
    
    // Muestra la informacion general del producto
    public void mostrarInformacion()
    {
        System.out.println(
                id
                + " - "
                + nombre
                + " - "
                + marca
                + " - $"
                + calcularPrecio()
                + " - Stock: "
                + stock
        );  
    }

    // Calcula el precio unitario, usando oferta cuando corresponde
    public int calcularPrecio() 
    {
        if (precioOferta > 0)  
        {
            return precioOferta;
        }
        else
        {
            return precio;
        }

    }

    // Calcula el precio total para una cantidad determinada
    public int calcularPrecio(int cantidad) {
        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad no puede ser negativa.");
        }
        return calcularPrecio() * cantidad;
    }
}
