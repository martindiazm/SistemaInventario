
package com.mycompany.sistemainventario;

// Producto comercializado utilizando una unidad de medida
public class ProductoGranel extends Producto
{
    private String unidadMedida;

    // Crea un producto a granel con su unidad de medida
    public ProductoGranel(
            String id,
            String nombre,
            String marca,
            int precio,
            int precioOferta,
            int stock,
            String unidadMedida)
    {
        super(id, nombre, marca, precio, precioOferta, stock);

        this.unidadMedida = unidadMedida;
    }

    public String getUnidadMedida()
    {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida)
    {
        this.unidadMedida = unidadMedida;
    }

    // Sobrescribe la visualizacion para incluir la unidad de medida
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
                + " por "
                + unidadMedida
                + " - Stock: "
                + getStock()
        );
    }
}
