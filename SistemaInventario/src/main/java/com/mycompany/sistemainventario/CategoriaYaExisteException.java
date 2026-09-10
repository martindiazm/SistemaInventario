
package com.mycompany.sistemainventario;

public class CategoriaYaExisteException extends Exception 
{
    public CategoriaYaExisteException(String mensaje) 
    {
        super(mensaje);
    }
}