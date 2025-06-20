package contacloud.dplicencias.dto;

public class ClienteDto {
    private Long id;      // opcional, para identificar al cliente
    private String nombre;
    private String email;
    private String rucDni;
    private String estado;

    public ClienteDto() {}

    public ClienteDto(Long id, String nombre,
                      String direccion, String rucDni,String estado) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.rucDni = rucDni;
        this.estado = estado;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getRucDni() { return rucDni; }
    public void setRucDni(String rucDni) { this.rucDni = rucDni; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
}