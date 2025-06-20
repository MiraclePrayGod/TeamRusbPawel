package contacloud.dplicencias.dto;

public class ProductoDto {
    private  Integer id;
    private String nombre;

    public ProductoDto() {
    }

    public ProductoDto(Integer id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public ProductoDto(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
