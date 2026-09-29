
package com.mycompany.sistemainventario;

// Producto que posee informacion adicional sobre su vencimiento.
public class ProductoPerecible extends Producto
{
    private int diasVencimiento;
    
    // Crea un producto perecible con sus dias restantes de vencimiento
    public ProductoPerecible(
            String id,
            String nombre,
            String marca,
            int precio,
            int precioOferta,
            int stock,
            int diasVencimiento)
    {
        super(id, nombre, marca, precio, precioOferta, stock);

        this.diasVencimiento = diasVencimiento;
    }

    public int getDiasVencimiento()
    {
        return diasVencimiento;
    }

    public void setDiasVencimiento(int diasVencimiento)
    {
        this.diasVencimiento = diasVencimiento;
    }
    
    // Sobrescribe la visualizacion para incluir el vencimiento
    @Override
    public void mostrarInformacion()
    {
        System.out.println(
                getId()
                + " - "
                + getNombre()
                + " - "
                + getMarca()
                + " - $"
                + calcularPrecio()
                + " - Stock: "
                + getStock()
                + " - Vence en: "
                + diasVencimiento
                + " días"
        );
    }
}
