package com.example.msproducto.util;

import com.example.msproducto.entity.Producto;
import com.example.msproducto.repository.ProductoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class ProductoSeeder implements CommandLineRunner {
    private final ProductoRepository productoRepository;

    public ProductoSeeder(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productoRepository.count() == 0) {
            Producto p1 = new Producto();
            p1.setNombre("Laptop Lenovo");
            p1.setDescripcion("Laptop Lenovo IdeaPad 15\" con Ryzen 5, 8GB RAM, SSD 256GB");
            p1.setCategoria("Electrónica");
            p1.setPrecioUnitario(new BigDecimal("3500.00"));
            p1.setStock(10);
            p1.setStockMinimo(2);
            p1.setImagenUrl("https://cdn.tienda.com/laptop-lenovo.jpg");
            p1.setEstado(true);
            p1.setFechaCreacion(LocalDateTime.now());

            Producto p2 = new Producto();
            p2.setNombre("Mouse Logitech");
            p2.setDescripcion("Mouse inalámbrico Logitech M185 con tecnología óptica");
            p2.setCategoria("Accesorios");
            p2.setPrecioUnitario(new BigDecimal("70.00"));
            p2.setStock(25);
            p2.setStockMinimo(5);
            p2.setImagenUrl("https://cdn.tienda.com/mouse-logitech.jpg");
            p2.setEstado(true);
            p2.setFechaCreacion(LocalDateTime.now());

            productoRepository.save(p1);
            productoRepository.save(p2);

            System.out.println("✅ Productos de ejemplo creados correctamente.");
        } else {
            System.out.println("ℹ️ Ya existen productos registrados. Seeder omitido.");
        }
    }
}


