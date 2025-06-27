package com.example.msreporte.feign;

import com.example.msreporte.model.VentaDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "ms-venta-service", path = "/ventas")
public interface VentaFeign {

    @GetMapping
    ResponseEntity<List<VentaDTO>> listarVentas();

    @GetMapping("/{id}")
    ResponseEntity<VentaDTO> obtener(@PathVariable Long id);
}
