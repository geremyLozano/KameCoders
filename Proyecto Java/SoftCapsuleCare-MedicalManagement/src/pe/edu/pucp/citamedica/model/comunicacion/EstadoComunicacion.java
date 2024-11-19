
package pe.edu.pucp.citamedica.model.comunicacion;

/**
 *
 * @author berna
 */
public enum EstadoComunicacion {
    RECIBIDA,      // Comunicación recién recibida
    PENDIENTE,     // Comunicación en espera de ser gestionada
    RESPONDIDA,    // Comunicación a la que se ha respondido
    FINALIZADA     // Comunicación que ha sido completamente atendida
}
