/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auxiliares;

import java.util.Objects;

/**
 *
 * @author aescribano
 */
public class Producto {
    
    private String nombreProducto;
    private int cantidad;

    public Producto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
        this.cantidad = 1;
    }

    public Producto() {
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    public void sumaCantidad(){
        this.cantidad++;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.nombreProducto);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (!Objects.equals(this.nombreProducto, other.nombreProducto)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto{" + "nombreProducto=" + nombreProducto + ", cantidad=" + cantidad + '}';
    }
    
    
    
}
