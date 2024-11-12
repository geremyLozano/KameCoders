/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.edu.pucp.citamedica.model.dto;

import java.util.Date;

/**
 *
 * @author Usuario
 */
public class HistorialMedicoDto {
    
    
    
    

    private int idHistorialMedico;
    private Date fechaCreacion;
    private int idPaciente;
    private String dniPaciente;
    private String nombrePaciente;
    private String apellidoPaciente;
    private boolean activo;
    
    
    public HistorialMedicoDto() {
    }

    // Constructor con parámetros
    public HistorialMedicoDto(int idHistorialMedico, Date fechaCreacion,
            int idPaciente, String dniPaciente, String nombrePaciente,
            String apellidoPaciente) {
        this.idHistorialMedico = idHistorialMedico;
        this.fechaCreacion = fechaCreacion;
        this.idPaciente = idPaciente;
        this.dniPaciente = dniPaciente;
        this.nombrePaciente = nombrePaciente;
        this.apellidoPaciente = apellidoPaciente;
    }
    
    
    public boolean getActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    
    

    public int getIdHistorialMedico() {
        return idHistorialMedico;
    }

    public void setIdHistorialMedico(int idHistorialMedico) {
        this.idHistorialMedico = idHistorialMedico;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdPaciente() {
        return idPaciente;
    }

    public void setIdPaciente(int idPaciente) {
        this.idPaciente = idPaciente;
    }

    public String getDniPaciente() {
        return dniPaciente;
    }

    public void setDniPaciente(String dniPaciente) {
        this.dniPaciente = dniPaciente;
    }

    public String getNombrePaciente() {
        return nombrePaciente;
    }

    public void setNombrePaciente(String nombrePaciente) {
        this.nombrePaciente = nombrePaciente;
    }

    public String getApellidoPaciente() {
        return apellidoPaciente;
    }

    public void setApellidoPaciente(String apellidoPaciente) {
        this.apellidoPaciente = apellidoPaciente;
    }
    
    
    
    
    
    
    
    
}
