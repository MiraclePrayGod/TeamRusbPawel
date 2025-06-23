package contacloud.dplicencias.dto;

import jakarta.persistence.Transient;

public class VentaDetalleDto {
    private Integer id;
    private Integer productoId;

    public VentaDetalleDto(Integer id, Integer productoId) {
        this.id = id;
        this.productoId = productoId;
    }

    public VentaDetalleDto() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductoId() {
        return productoId;
    }

    public void setProductoId(Integer productoId) {
        this.productoId = productoId;
    }

}
