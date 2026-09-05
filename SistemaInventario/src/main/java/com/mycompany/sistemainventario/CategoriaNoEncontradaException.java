
package com.mycompany.sistemainventario;


public class CategoriaNoEncontradaException extends Exception 
{
    public CategoriaNoEncontradaException(String mensaje) 
    {
        super(mensaje);
    }
}