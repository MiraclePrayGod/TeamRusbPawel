package org.example.msventa.dato;

public class Cliente {
    private Integer id;
    private String nombres;
    private String apellidos;
    private String tipoDocumento;
    private String numeroDocumento;
    private String correo;
    private Boolean estado;
    private Boolean licenciaActiva;

    public Cliente() {
    }

    public Cliente(Integer id,
                   String nombres,
                   String apellidos,
                   String tipoDocumento,
                   String numeroDocumento,
                   String correo,
                   Boolean estado,
                   Boolean licenciaActiva) {
        this.id = id;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.correo = correo;
        this.estado = estado;
        this.licenciaActiva = licenciaActiva;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getTipoDocumento() {
        return tipoDocumento;
    }

    public void setTipoDocumento(String tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
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
}