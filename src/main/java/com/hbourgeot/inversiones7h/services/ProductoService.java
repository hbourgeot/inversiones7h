package com.hbourgeot.inversiones7h.services;

import com.hbourgeot.inversiones7h.dao.IProductoRepo;
import com.hbourgeot.inversiones7h.entities.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductoService implements IProductoService {

  @Autowired
  private IProductoRepo productoRepo;

  public Producto findByNombre(String nombre) {
      Optional<Producto> productoOptional = productoRepo.findByNombre(nombre);
      return productoOptional.orElse(null); 
  }

  @Autowired
  private IProductoRepo repo;

  @Override
  public void save(Producto producto) {
    repo.save(producto);
  }

  @Override
  public Producto findById(String id) {
    return repo.findById(id).orElse(null);
  }

  @Override
  public List<Producto> findAll() {
    return (List<Producto>) repo.findAll();
  }

  @Override
  public List<Producto> findDisponibles() {
    // TODO Auto-generated method stub
    List<Producto> productos = (List<Producto>) repo.findAll(), products = new ArrayList<>();

    for (int i = 0; i < productos.size(); i++) {
      Producto producto = productos.get(i);
      if (producto.getCantidad() != 0) {
        products.add(i, producto);
      }
    }
    return productos;
  }

  @Override
  public long count() {
    return repo.count();
  }

  @Override
  public void delete(Producto producto) {
    repo.delete(producto);
  }

  @Override
  public void deleteById(String id) {
    repo.deleteById(id);
  }


}
