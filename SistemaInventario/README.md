# Sistema de Inventario



El sistema administra el inventario de un supermercado. Los datos principales corresponden a categorías y productos. Cada producto posee código, nombre, marca, precio, precio de oferta y stock. Los productos se organizan dentro de categorías. El sistema permitirá agregar, buscar, modificar y eliminar categorías y productos, además de registrar movimientos de stock e identificar productos que requieren reposición.







### Instrucciones de instalación y ejecución del proyecto



##### Requisitos previos



Para ejecutar el proyecto se recomienda contar con:



Java Development Kit (JDK) 8 o superior.

Apache NetBeans IDE 12.0 o una versión compatible con el proyecto.

Sistema operativo Windows, Linux o macOS.



##### Instalación



1. Descomprimir el archivo .zip entregado.

2\. Dentro de la carpeta descomprimida se encontrarán:

* La carpeta correspondiente al proyecto de NetBeans.
* El informe final del proyecto.
* Este archivo README.

3\. Abrir Apache NetBeans.



4\. Seleccionar:



File → Open Project



5\. Buscar la carpeta del proyecto descomprimido y seleccionarla.

6\. Presionar Open Project.

7\. Verificar que NetBeans reconozca correctamente el JDK configurado para el proyecto.



##### Ejecución del proyecto



Una vez abierto el proyecto en NetBeans:



1. Ubicar la clase principal:



Main.java



2\. Ejecutar el proyecto utilizando:



Run → Run Project



o presionando la tecla:



F6



3\. El sistema se iniciará y mostrará el menú principal de la aplicación.



Desde este menú se podrán utilizar las distintas funcionalidades implementadas para la gestión del inventario, categorías, productos y stock.



##### Estructura principal del proyecto



El proyecto se encuentra organizado principalmente en las siguientes clases:



* Main.java: inicia la aplicación y carga los datos iniciales.
* Menu.java: administra la interacción del usuario con el sistema.
* Inventario.java: administra las categorías y productos registrados.
* Categoria.java: representa una categoría y almacena sus productos.
* Producto.java: representa la información y operaciones asociadas a un producto.



Además, el proyecto puede contener otras clases relacionadas con funcionalidades adicionales, excepciones, persistencia o especializaciones de productos.



