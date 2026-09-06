
package com.mycompany.sistemainventario;


public class ProductoGranel extends Producto
{
    private String unidadMedida;

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
