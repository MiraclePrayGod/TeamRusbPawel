package org.example.msventa.feign;

import org.example.msventa.dato.Cliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ms-cliente-service", path = "/clientes")
public interface ClienteFeign {
    @GetMapping("/{id}")
    ResponseEntity<Cliente> obtenerPorId(@PathVariable Integer id);
}