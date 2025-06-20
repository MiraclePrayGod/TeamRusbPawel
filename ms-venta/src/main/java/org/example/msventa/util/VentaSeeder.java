package org.example.msventa.util;

import org.example.msventa.entity.Venta;
import org.example.msventa.entity.VentaDetalle;
import org.example.msventa.repository.VentaRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class VentaSeeder implements CommandLineRunner {
    private final VentaRepository ventaRepository;

    public VentaSeeder(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public void run(String... args) {
        if (ventaRepository.count() == 0) {

            // ========================
            // Venta hecha por TRABAJADOR
            // ========================
            VentaDetalle d1 = new VentaDetalle();
            d1.setProductoId(1);
            d1.setCantidad(2);
            d1.setPrecioUnitario(500.0);
            d1.setSubtotal(1000.0);

            VentaDetalle d2 = new VentaDetalle();
            d2.setProductoId(2);
            d2.setCantidad(1);
            d2.setPrecioUnitario(300.0);
            d2.setSubtotal(300.0);

            Venta venta1 = new Venta();
            venta1.setClienteId(1);
            venta1.setTrabajadorId(1);
            venta1.setFechaVenta(LocalDate.now());
            venta1.setTotal(1300.0);
            venta1.setOrigen("TRABAJADOR");
            venta1.setEstado("SIN_PAGAR");
            venta1.setDetalles(Arrays.asList(d1, d2));

            // ========================
            // Venta hecha por CLIENTE (desde frontend)
            // ========================
            VentaDetalle d3 = new VentaDetalle();
            d3.setProductoId(3);
            d3.setCantidad(1);
            d3.setPrecioUnitario(750.0);
            d3.setSubtotal(750.0);

            Venta venta2 = new Venta();
            venta2.setClienteId(2);
            venta2.setTrabajadorId(null);
            venta2.setFechaVenta(LocalDate.now());
            venta2.setTotal(750.0);
            venta2.setOrigen("CLIENTE");
            venta2.setEstado("SIN_PAGAR");
            venta2.setDetalles(List.of(d3));

            // Guardamos ambas ventas
            ventaRepository.save(venta1);
            ventaRepository.save(venta2);

            System.out.println("✅ Ventas de ejemplo registradas con estado SIN_PAGAR.");
        } else {
            System.out.println("ℹ️ Ya existen ventas registradas, seeder omitido.");
        }
    }
}