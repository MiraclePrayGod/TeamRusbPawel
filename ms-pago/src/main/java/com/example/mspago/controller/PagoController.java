package com.example.mspago.controller;

import com.example.mspago.dto.PagoRequest;
import com.example.mspago.entity.Pago;
import com.example.mspago.service.PagoService;
import com.example.mspago.dto.VentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    @Autowired private PagoService pagoService;

    /** Lista ventas SIN_PAGAR de un cliente */
    @GetMapping("/pendientes/{clienteId}")
    public ResponseEntity<List<VentaDTO>> pendientes(@PathVariable Long clienteId) {
        return ResponseEntity.ok(pagoService.ventasPendientes(clienteId));
    }

    /** Registra un pago (multipart para comprobante opcional) */
    @PostMapping(consumes = {"multipart/form-data"})
    public ResponseEntity<Pago> pagar(
            @RequestPart("data") PagoRequest request,
            @RequestPart(value = "archivo", required = false) MultipartFile archivo) {

        return ResponseEntity.ok(pagoService.registrar(request, archivo));
    }

    /** Detalle de pago */
    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(pagoService.obtener(id));
    }
}

