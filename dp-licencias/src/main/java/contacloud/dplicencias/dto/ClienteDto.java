package contacloud.dplicencias.dto;

import java.time.LocalDateTime;

public class ClienteDto {
    private Long id;      // opcional, para identificar al cliente
    private String nombres;
    private String apellidos;
    private String correo;
    private String numeroDocumento;
    private Boolean estado;
    private Boolean licenciaActiva;
    private LocalDateTime fechaRegistro;

    public ClienteDto() {
    }

    public ClienteDto(Long id, String nombres, String apellidos, String correo, String numeroDocumento, Boolean estado,Boolean licenciaActiva, LocalDateTime fechaRegistro) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.correo = correo;
        this.numeroDocumento = numeroDocumento;
        this.estado = estado;
        this.licenciaActiva = licenciaActiva;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public Boolean getLicenciaActiva() {
        return licenciaActiva;
    }

    public void setLicenciaActiva(Boolean licenciaActiva) {
        this.licenciaActiva = licenciaActiva;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}