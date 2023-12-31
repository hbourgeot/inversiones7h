package com.helisha.inversiones7h.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto implements Serializable {

  @Id
  @Column(nullable = false, length = 10)
  private String codigo;

  @Column(nullable = false, length = 100)
  private  String nombre;

  @Column(nullable = false)
  private Long cantidad;

  @Column(nullable = false, precision = 9, scale = 2)
  private BigDecimal precio;

  @ManyToOne
  public Proveedor proveedor;

  @Transient
  private Long cantidadVendida;

  public String getProveedor() {
    if (proveedor != null) {
      return proveedor.getNombre() + " " + proveedor.getApellido();
    } else {
      return "";
    }
  }

  public Proveedor getProveedorObj(){ return  proveedor; }

  public void setProveedor(Proveedor proveedor) {
    this.proveedor = proveedor;
  }

  public Long getCantidadVendida() {
    return cantidadVendida;
  }

  public void setCantidadVendida(Long cantidadVendida) {
    this.cantidadVendida = cantidadVendida;
  }

  public String getCodigo() {
    return codigo;
  }

  public void setCodigo(String codigo) {
    this.codigo = codigo;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad) {
    this.cantidad = cantidad;
  }

  public BigDecimal getPrecio() {
    return precio;
  }

  public String getPrecioString() {
    return precio.toString() + "$";
  }

  public void setPrecio(BigDecimal precio) {
    this.precio = precio;
  }

}
