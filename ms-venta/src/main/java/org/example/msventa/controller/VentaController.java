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
import java.util.Optional;

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
    public ResponseEntity<?> obtener(
            @PathVariable Integer id, // Obtener la venta por ID de la venta
            @RequestParam(required = false) Integer idCliente) { // Parámetro opcional para el idCliente
        if (idCliente != null) {
            // Buscar ventas por idCliente
            List<Venta> ventas = ventaService.obtenerPorCliente(idCliente);
            if (!ventas.isEmpty()) {
                return ResponseEntity.ok(ventas);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("No se encontraron ventas para el cliente con ID: " + idCliente);
            }
        } else {
            // Buscar por id de venta
            Optional<Venta> venta = ventaService.obtener(id);
            return venta.map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        }
    }

    // Método de fallback
    public ResponseEntity<?> fallbackVenta(Integer id, Throwable throwable) {
        // Aquí puedes retornar una respuesta predeterminada en caso de fallo
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Servicio no disponible, intente nuevamente más tarde.");
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