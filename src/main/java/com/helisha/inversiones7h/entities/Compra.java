package com.helisha.inversiones7h.entities;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "compras")
public class Compra {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Producto producto;

  @ManyToOne
  private Proveedor proveedor;

  @Column(nullable = false)
  private Long cantidad;

  @Column(nullable = false)
  private BigDecimal montoTotal;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getProducto() {
    return "#" + producto.getCodigo() + " - " + producto.getNombre();
  }

  public void setProducto(Producto producto) {
    this.producto = producto;
  }

  public String getProveedor() {
    return "Cédula: " + proveedor.getCedulaIndentidad() + " - " + "Nombre: " + proveedor.getNombre() + " "
      + proveedor.getApellido();
  }

  public void setProveedor(Proveedor proveedor) {
    this.proveedor = proveedor;
  }

  public Long getCantidad() {
    return cantidad;
  }

  public void setCantidad(Long cantidad2) {
    this.cantidad = cantidad2;
  }

  public BigDecimal getMontoTotal() {
    return montoTotal;
  }

  public void setMontoTotal(BigDecimal montoTotal) {
    this.montoTotal = montoTotal;
  }
}
