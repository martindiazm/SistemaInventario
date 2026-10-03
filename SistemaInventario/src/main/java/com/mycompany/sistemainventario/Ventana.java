package com.mycompany.sistemainventario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

public class Ventana extends JFrame
{
    private final Inventario inventario;

    private DefaultTableModel modeloCategorias;
    private JTable tablaCategorias;

    private DefaultTableModel modeloProductos;
    private JTable tablaProductos;

    public Ventana(Inventario inventario)
    {
        super("Sistema de Inventario - Modo Ventana");
        this.inventario = inventario;

        construirInterfaz();
        refrescarCategorias();
        refrescarProductos();

        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                salir();
            }
        });

        setSize(950, 550);
        setLocationRelativeTo(null);
    }

    private void construirInterfaz()
    {
        JTabbedPane tabs = new JTabbedPane();

        tabs.addTab("Categorías", construirPanelCategorias());
        tabs.addTab("Productos", construirPanelProductos());
        tabs.addTab("Inventario", construirPanelInventario());

        setLayout(new BorderLayout());
        add(tabs, BorderLayout.CENTER);
    }

    private JPanel construirPanelCategorias()
    {
        modeloCategorias = new DefaultTableModel(new Object[]{"Categoría", "N° Productos"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaCategorias = new JTable(modeloCategorias);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tablaCategorias), BorderLayout.CENTER);

        JPanel botones = new JPanel();

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnRefrescar = new JButton("Refrescar");

        btnAgregar.addActionListener(e -> agregarCategoria());
        btnModificar.addActionListener(e -> modificarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnBuscar.addActionListener(e -> buscarCategoria());
        btnRefrescar.addActionListener(e -> refrescarCategorias());

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        botones.add(btnRefrescar);

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void refrescarCategorias()
    {
        modeloCategorias.setRowCount(0);

        for (Categoria categoria : inventario.getCategorias().values())
        {
            modeloCategorias.addRow(new Object[]{
                categoria.getNombreCat(),
                categoria.getListaProductos().size()
            });
        }
    }

    private String obtenerCategoriaSeleccionada()
    {
        int fila = tablaCategorias.getSelectedRow();

        if (fila == -1)
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar una categoría de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return (String) modeloCategorias.getValueAt(fila, 0);
    }

    private void agregarCategoria()
    {
        String[] valores = solicitarDatos("Agregar categoría", new String[]{"Nombre:"}, null);
        if (valores == null) return;

        if (valores[0].isEmpty())
        {
            JOptionPane.showMessageDialog(this, "El nombre no puede estar vacío.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try
        {
            inventario.agregarCategoria(new Categoria(valores[0]));
            GestorCSV.guardar(inventario); // Auto-guardado
            JOptionPane.showMessageDialog(this, "Categoría agregada correctamente.");
            refrescarCategorias();
        }
        catch (CategoriaYaExisteException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modificarCategoria()
    {
        String actual = obtenerCategoriaSeleccionada();
        if (actual == null) return;

        String[] valores = solicitarDatos("Modificar categoría", new String[]{"Nuevo nombre:"}, new String[]{actual});
        if (valores == null) return;

        try
        {
            inventario.modificarCategoria(actual, valores[0]);
            GestorCSV.guardar(inventario); // Auto-guardado
            JOptionPane.showMessageDialog(this, "Categoría modificada correctamente.");
            refrescarCategorias();
            refrescarProductos();
        }
        catch (CategoriaNoEncontradaException | CategoriaYaExisteException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
         catch (IllegalArgumentException e) 
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria()
    {
        String nombre = obtenerCategoriaSeleccionada();
        if (nombre == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar la categoría '" + nombre + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        try
        {
            inventario.eliminarCategoria(nombre);
            GestorCSV.guardar(inventario); // Auto-guardado
            JOptionPane.showMessageDialog(this, "Categoría eliminada correctamente.");
            refrescarCategorias();
            refrescarProductos();
        }
        catch (CategoriaNoEncontradaException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarCategoria()
    {
        String[] valores = solicitarDatos("Buscar categoría", new String[]{"Nombre:"}, null);
        if (valores == null) return;

        Categoria categoria = inventario.buscarCategoria(valores[0]);

        if (categoria != null)
        {
            JOptionPane.showMessageDialog(this, "Categoría encontrada: " + categoria.getNombreCat()
                + "\nProductos: " + categoria.getListaProductos().size());
        }
        else
        {
            JOptionPane.showMessageDialog(this, "La categoría no existe.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private JPanel construirPanelProductos()
    {
        modeloProductos = new DefaultTableModel(new Object[]{"Código", "Nombre", "Marca", "Categoría", "Precio", "Stock", "Tipo"}, 0)
        {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tablaProductos = new JTable(modeloProductos);

        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JScrollPane(tablaProductos), BorderLayout.CENTER);

        JPanel botones = new JPanel(new GridLayout(2, 4, 5, 5));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnPrecio = new JButton("Calcular precio");
        JButton btnEntrada = new JButton("Entrada stock");
        JButton btnSalida = new JButton("Salida stock");
        JButton btnRefrescar = new JButton("Refrescar");

        btnAgregar.addActionListener(e -> agregarProducto());
        btnModificar.addActionListener(e -> modificarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnBuscar.addActionListener(e -> buscarProducto());
        btnPrecio.addActionListener(e -> calcularPrecioProducto());
        btnEntrada.addActionListener(e -> entradaStock());
        btnSalida.addActionListener(e -> salidaStock());
        btnRefrescar.addActionListener(e -> refrescarProductos());

        botones.add(btnAgregar);
        botones.add(btnModificar);
        botones.add(btnEliminar);
        botones.add(btnBuscar);
        botones.add(btnPrecio);
        botones.add(btnEntrada);
        botones.add(btnSalida);
        botones.add(btnRefrescar);

        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void refrescarProductos()
    {
        modeloProductos.setRowCount(0);

        for (Categoria categoria : inventario.getCategorias().values())
        {
            for (Producto producto : categoria.getListaProductos())
            {
                String tipo = "NORMAL";
                if (producto instanceof ProductoPerecible) tipo = "PERECIBLE";
                else if (producto instanceof ProductoGranel) tipo = "GRANEL";

                modeloProductos.addRow(new Object[]{
                    producto.getId(),
                    producto.getNombre(),
                    producto.getMarca(),
                    categoria.getNombreCat(),
                    producto.calcularPrecio(),
                    producto.getStock(),
                    tipo
                });
            }
        }
    }

    private String obtenerCodigoSeleccionado()
    {
        int fila = tablaProductos.getSelectedRow();

        if (fila == -1)
        {
            JOptionPane.showMessageDialog(this, "Debe seleccionar un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        return (String) modeloProductos.getValueAt(fila, 0);
    }

    private void agregarProducto()
    {
        String[] valores = solicitarDatos(
            "Agregar producto",
            new String[]{
                "Código:",
                "Nombre:",
                "Marca:",
                "Precio:",
                "Precio oferta:",
                "Stock inicial:",
                "Categoría:"
            },
            null
        );

        if (valores == null)
        {
            return;
        }

        try
        {
            // Datos básicos
            String codigo = valores[0].trim();
            String nombre = valores[1].trim();
            String marca = valores[2].trim();
            String categoria = valores[6].trim();

            // Validar campos de texto
            if (codigo.isEmpty()
                    || nombre.isEmpty()
                    || marca.isEmpty()
                    || categoria.isEmpty())
            {
                JOptionPane.showMessageDialog(
                    this,
                    "Código, nombre, marca y categoría son obligatorios.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Datos numéricos
            int precio = Integer.parseInt(valores[3].trim());
            int precioOferta = Integer.parseInt(valores[4].trim());
            int stock = Integer.parseInt(valores[5].trim());

            // Validar valores numéricos
            if (precio < 0)
            {
                JOptionPane.showMessageDialog(
                    this,
                    "El precio no puede ser negativo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (precioOferta < 0)
            {
                JOptionPane.showMessageDialog(
                    this,
                    "El precio de oferta no puede ser negativo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (stock < 0)
            {
                JOptionPane.showMessageDialog(
                    this,
                    "El stock inicial no puede ser negativo.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Seleccionar tipo de producto
            String[] tipos = {
                "Producto normal",
                "Producto perecible",
                "Producto a granel"
            };

            int tipoProducto = JOptionPane.showOptionDialog(
                this,
                "Seleccione el tipo de producto:",
                "Tipo de producto",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                tipos,
                tipos[0]
            );

            // Cancelar
            if (tipoProducto == -1)
            {
                return;
            }

            Producto producto;

            // Producto normal
            if (tipoProducto == 0)
            {
                producto = new Producto(
                    codigo,
                    nombre,
                    marca,
                    precio,
                    precioOferta,
                    stock
                );
            }

            // Producto perecible
            else if (tipoProducto == 1)
            {
                String diasTexto = JOptionPane.showInputDialog(
                    this,
                    "Ingrese los días de vencimiento:"
                );

                if (diasTexto == null)
                {
                    return;
                }

                int diasVencimiento = Integer.parseInt(diasTexto.trim());

                if (diasVencimiento < 0)
                {
                    JOptionPane.showMessageDialog(
                        this,
                        "Los días de vencimiento no pueden ser negativos.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                producto = new ProductoPerecible(
                    codigo,
                    nombre,
                    marca,
                    precio,
                    precioOferta,
                    stock,
                    diasVencimiento
                );
            }

            // Producto a granel
            else
            {
                String unidadMedida = JOptionPane.showInputDialog(
                    this,
                    "Ingrese la unidad de medida:"
                );

                if (unidadMedida == null)
                {
                    return;
                }

                unidadMedida = unidadMedida.trim();

                if (unidadMedida.isEmpty())
                {
                    JOptionPane.showMessageDialog(
                        this,
                        "La unidad de medida no puede estar vacía.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                producto = new ProductoGranel(
                    codigo,
                    nombre,
                    marca,
                    precio,
                    precioOferta,
                    stock,
                    unidadMedida
                );
            }

            // Agregar al inventario
            inventario.agregarProducto(categoria, producto);

            // Auto-guardado
            GestorCSV.guardar(inventario);

            JOptionPane.showMessageDialog(
                this,
                "Producto agregado correctamente."
            );

            // Actualizar tablas
            refrescarProductos();
            refrescarCategorias();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(
                this,
                "Precio, precio de oferta, stock y datos numéricos adicionales deben ser números válidos.",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        catch (CategoriaNoEncontradaException e)
        {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
        catch (IllegalArgumentException e)
        {
            JOptionPane.showMessageDialog(
                this,
                e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void modificarProducto()
    {
        String codigo = obtenerCodigoSeleccionado();
        if (codigo == null) return;

        try
        {
            Producto actual = inventario.buscarProducto(codigo);

            String[] valores = solicitarDatos(
                "Modificar producto " + codigo,
                new String[]{"Nombre:", "Marca:", "Precio:", "Precio oferta:", "Stock:"},
                new String[]{
                    actual.getNombre(),
                    actual.getMarca(),
                    String.valueOf(actual.getPrecio()),
                    String.valueOf(actual.getPrecioOferta()),
                    String.valueOf(actual.getStock())
                }
            );
            if (valores == null) return;

            int precio = Integer.parseInt(valores[2]);
            int precioOferta = Integer.parseInt(valores[3]);
            int stock = Integer.parseInt(valores[4]);

            inventario.modificarProducto(codigo, valores[0], valores[1], precio, precioOferta, stock);
            GestorCSV.guardar(inventario); // Auto-guardado

            JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");
            refrescarProductos();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Precio, precio de oferta y stock deben ser números.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto()
    {
        String codigo = obtenerCodigoSeleccionado();
        if (codigo == null) return;

        int confirmacion = JOptionPane.showConfirmDialog(this, "¿Eliminar el producto '" + codigo + "'?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirmacion != JOptionPane.YES_OPTION) return;

        try
        {
            inventario.eliminarProducto(codigo);
            GestorCSV.guardar(inventario); // Auto-guardado
            JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.");
            refrescarProductos();
            refrescarCategorias();
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto()
    {
        String[] valores = solicitarDatos("Buscar producto por código", new String[]{"Código:"}, null);
        if (valores == null) return;

        try
        {
            Producto producto = inventario.buscarProducto(valores[0]);

            JOptionPane.showMessageDialog(this,
                "Código: " + producto.getId()
                + "\nNombre: " + producto.getNombre()
                + "\nMarca: " + producto.getMarca()
                + "\nPrecio: $" + producto.calcularPrecio()
                + "\nStock: " + producto.getStock()
            );
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void calcularPrecioProducto()
    {
        String codigo = obtenerCodigoSeleccionado();
        if (codigo == null) return;

        String[] valores = solicitarDatos("Calcular precio", new String[]{"Cantidad:"}, new String[]{"1"});
        if (valores == null) return;

        try
        {
            int cantidad = Integer.parseInt(valores[0]);
            if (cantidad <= 0)
            {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = inventario.buscarProducto(codigo);

            JOptionPane.showMessageDialog(this,
                "Precio unitario: $" + producto.calcularPrecio()
                + "\nPrecio total (" + cantidad + " unid.): $" + producto.calcularPrecio(cantidad)
            );
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void entradaStock()
    {
        String codigo = obtenerCodigoSeleccionado();
        if (codigo == null) return;

        String[] valores = solicitarDatos("Registrar entrada de stock", new String[]{"Cantidad a agregar:"}, null);
        if (valores == null) return;

        try
        {
            int cantidad = Integer.parseInt(valores[0]);
            if (cantidad <= 0)
            {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = inventario.buscarProducto(codigo);
            producto.aumentarStock(cantidad);
            GestorCSV.guardar(inventario); // Auto-guardado

            JOptionPane.showMessageDialog(this, "Stock actualizado. Nuevo stock: " + producto.getStock());
            refrescarProductos();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salidaStock()
    {
        String codigo = obtenerCodigoSeleccionado();
        if (codigo == null) return;

        String[] valores = solicitarDatos("Registrar venta / salida de stock", new String[]{"Cantidad vendida:"}, null);
        if (valores == null) return;

        try
        {
            int cantidad = Integer.parseInt(valores[0]);
            if (cantidad <= 0)
            {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Producto producto = inventario.buscarProducto(codigo);
            producto.disminuirStock(cantidad);
            GestorCSV.guardar(inventario); // Auto-guardado

            JOptionPane.showMessageDialog(this, "Venta registrada. Stock actual: " + producto.getStock());
            refrescarProductos();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "La cantidad debe ser un número.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ProductoNoEncontradoException | StockInsuficienteException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private JPanel construirPanelInventario()
    {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnReposicion = new JButton("Reponer productos con bajo stock");
        JButton btnSalir = new JButton("Guardar y salir");

        btnReposicion.addActionListener(e -> reponerBajoStock());
        btnSalir.addActionListener(e -> salir());

        panel.add(btnReposicion);
        panel.add(btnSalir);

        return panel;
    }

    private void reponerBajoStock()
    {
        String[] valores = solicitarDatos("Reponer productos con bajo stock", new String[]{"Stock mínimo:"}, null);
        if (valores == null) return;

        try
        {
            int limite = Integer.parseInt(valores[0]);
            List<Producto> productosBajoStock = inventario.obtenerProductosBajoStock(limite);

            if (productosBajoStock.isEmpty())
            {
                JOptionPane.showMessageDialog(this, "No existen productos con stock menor a " + limite);
                return;
            }

            StringBuilder listado = new StringBuilder("Productos con bajo stock:\n\n");
            for (Producto producto : productosBajoStock)
            {
                listado.append(producto.getId()).append(" - ").append(producto.getNombre())
                    .append(" (stock: ").append(producto.getStock()).append(")\n");
            }
            listado.append("\nIngrese el código del producto a reponer:");

            String codigo = JOptionPane.showInputDialog(this, listado.toString());
            if (codigo == null || codigo.trim().isEmpty()) return;

            Producto producto = inventario.buscarProducto(codigo.trim());

            if (producto.getStock() >= limite)
            {
                JOptionPane.showMessageDialog(this, "El producto seleccionado no pertenece al grupo de bajo stock.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String cantidadStr = JOptionPane.showInputDialog(this, "Cantidad a reponer:");
            if (cantidadStr == null) return;

            int cantidad = Integer.parseInt(cantidadStr.trim());
            if (cantidad <= 0)
            {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            producto.aumentarStock(cantidad);
            GestorCSV.guardar(inventario); // Auto-guardado

            JOptionPane.showMessageDialog(this, "Producto repuesto. Nuevo stock: " + producto.getStock());
            refrescarProductos();
        }
        catch (NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Debe ingresar valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        catch (ProductoNoEncontradoException e)
        {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salir()
    {
        GestorCSV.guardar(inventario);
        JOptionPane.showMessageDialog(this, "Datos guardados correctamente en inventario.csv.", "Salida", JOptionPane.INFORMATION_MESSAGE);
        dispose();
        System.exit(0);
    }

    private String[] solicitarDatos(String titulo, String[] etiquetas, String[] valoresIniciales)
    {
        JPanel panel = new JPanel(new GridLayout(etiquetas.length, 2, 5, 5));
        JTextField[] campos = new JTextField[etiquetas.length];

        for (int i = 0; i < etiquetas.length; i++)
        {
            panel.add(new JLabel(etiquetas[i]));
            campos[i] = new JTextField(valoresIniciales != null && valoresIniciales[i] != null ? valoresIniciales[i] : "");
            panel.add(campos[i]);
        }

        int resultado = JOptionPane.showConfirmDialog(this, panel, titulo, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (resultado != JOptionPane.OK_OPTION) return null;

        String[] valores = new String[etiquetas.length];
        for (int i = 0; i < etiquetas.length; i++)
        {
            valores[i] = campos[i].getText().trim();
        }

        return valores;
    }
}