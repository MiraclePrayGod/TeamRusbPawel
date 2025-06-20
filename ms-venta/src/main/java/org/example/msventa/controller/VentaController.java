package org.example.msventa.controller;

import org.example.msventa.entity.Venta;
import org.example.msventa.service.VentaService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import org.example.msventa.entity.Venta;
import org.example.msventa.service.VentaService;
import org.example.msventa.repository.VentaRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ventas")
public class VentaController {

    @Autowired private VentaService ventaService;
    @Autowired private VentaRepository ventaRepository; // ✅ instancia para acceso directo

    @GetMapping
    public ResponseEntity<List<Venta>> listar() {
        return ResponseEntity.ok(ventaService.listar());
    }
    @GetMapping("/pagadas/{clienteId}")
    public ResponseEntity<List<Venta>> pagadas(@PathVariable Integer clienteId) {
        return ResponseEntity.ok(ventaService.pagadas(clienteId));
    }


    @GetMapping("/{id}")
    @CircuitBreaker(name = "ventaCB", fallbackMethod = "fallbackVenta")
    public ResponseEntity<Venta> obtener(@PathVariable Integer id) {
        return ventaService.obtener(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<Venta> fallbackVenta(Integer id, Throwable throwable) {
        Venta fallback = new Venta();
        fallback.setId(0);
        fallback.setObservacion("Venta no disponible temporalmente.");
        return ResponseEntity.ok(fallback);
    }

    @PostMapping
    public ResponseEntity<Venta> registrar(@RequestBody Venta venta) {
        return ResponseEntity.ok(ventaService.registrar(venta));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        ventaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    // ✅ NUEVO ENDPOINT para marcar una venta como FACTURADA
    @PutMapping("/{id}/marcar-facturada")
    public ResponseEntity<Void> facturar(@PathVariable Integer id) {
        ventaRepository.findById(id).ifPresent(v -> {
            v.setEstado("FACTURADA");
            ventaRepository.save(v);
        });
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/{id}/marcar-pagada")
    public ResponseEntity<Void> marcarPagada(@PathVariable Integer id) {
        ventaService.marcarPagada(id);
        return ResponseEntity.noContent().build();
    }

}