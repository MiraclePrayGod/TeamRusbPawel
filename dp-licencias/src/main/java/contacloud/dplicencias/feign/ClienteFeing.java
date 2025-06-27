package contacloud.dplicencias.feign;

import contacloud.dplicencias.dto.ClienteDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "ms-cliente-service", path = "/clientes")
public interface ClienteFeing {
        @GetMapping("/{id}")
        ResponseEntity<ClienteDto> obtenerPorId(@PathVariable Long id);

        @PutMapping("/habilitar/{clienteId}")
        ResponseEntity<Void> actualizarEstado(@PathVariable Long clienteId, @RequestBody Boolean estado);

}
