package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AuxiliarDAO;
import pe.edu.pucp.citamedica.model.clinica.Auxiliar;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AuxiliarMySQL;

public class Principal_3 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Auxiliar aux = new Auxiliar();
        aux.setDNI("16:07");
        aux.setNombre("Conexion2");
        aux.setApellido("probando");
        aux.setCorreoElectronico("prueba2@hotmail.com");
        aux.setNumTelefono(123312);
        aux.setDireccion("Av.Brasil 123");
        aux.setFechaNacimiento(sdf.parse("23-10-2000"));
        aux.setGenero('M');
        
        Usuario usuario = new Usuario();
        usuario.setUsername("16:07");
        usuario.setContrasenha("admin123");
        
        AuxiliarDAO ad = new AuxiliarMySQL();
        int resultado = ad.insertar(aux, usuario);
        if(resultado>0)
            System.out.println("Auxiliar ingreso correctamente");
        else
            System.out.println("Error en la creacion");

//        Scanner in = new Scanner(System.in);
//        String dato = in.next();
//        aux.setNombre("AUXILIARMODIFICADO");
//        int valor = ad.modificar(aux);
//        if(valor>0)
//            System.out.println("Modificado correctamente");
//        else
//            System.out.println("ERROR al modificar");
//        AuxiliarDAO ad = new AuxiliarMySQL();
//        int valor = ad.eliminar(26);
//        if(valor>0)
//            System.out.println("Eliminado correctamente");
//        else
//            System.out.println("Error al eliminar");
//        ArrayList<Auxiliar>aux=ad.listarTodos();
//        for(Auxiliar p:aux){
//            String fecha = sdf.format(p.getFechaNacimiento());
//            System.out.println("IdPaciente: "+p.getIdAuxiliar()+ "  DNI:"+p.getDNI()+"   "
//                    + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                    "   Especialidad:"+p.getEspecialidad().getNombre()+"   FechaNacimiento:"+fecha);
//            System.out.println("---------------------------------");
//        }
//        Auxiliar p=ad.obtenerPorId(25);
//        String fecha = sdf.format(p.getFechaNacimiento());
//        System.out.println("IdPaciente: "+p.getIdAuxiliar()+ "  DNI:"+p.getDNI()+"   "
//                + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                "   Especialidad:"+p.getEspecialidad().getNombre()+"   FechaNacimiento:"+fecha);
//        System.out.println("---------------------------------");
        
    }
}
