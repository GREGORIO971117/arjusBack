package com.arjusven.model;
import jakarta.persistence.*;

@Entity
@Table(name = "Usuarios")
public class Usuarios {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuarios")
    private Long id;

    @Column(name = "Nombre", nullable = false)
    private String nombreCompleto;
    
    @Column(name = "Correo")
    private String correo;
    
    @Column(name = "Estado_de_residencia")
    private String estadoDeResidencia;
    
    @Column(name = "Edad")
    private Integer edad;
    
    @Column(name = "Rol_de_empleado", nullable = false)
    private String rol;

    // Constructor por defecto
    public Usuarios() {}

    // Getters
    public Long getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getCorreo() { return correo; }
    public String getEstadoDeResidencia() { return estadoDeResidencia; }
    public Integer getEdad() { return edad; }
    public String getRol() { return rol; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setEstadoDeResidencia(String estadoDeResidencia) { this.estadoDeResidencia = estadoDeResidencia; }
    public void setEdad(Integer edad) { this.edad = edad; }
    public void setRol(String rol) { this.rol = rol; }

	@Override
	public String toString() {
		return "Usuarios [id=" + id + ", nombreCompleto=" + nombreCompleto + ", correo=" + correo
				+ ", estadoDeResidencia=" + estadoDeResidencia + ", edad=" + edad + ", rol=" + rol + "]";
	}
    
    
    
}