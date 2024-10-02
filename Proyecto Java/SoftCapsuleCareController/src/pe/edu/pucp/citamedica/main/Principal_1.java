package pe.edu.pucp.citamedica.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;
import pe.edu.pucp.citamedica.dao.AdministradorDAO;
import pe.edu.pucp.citamedica.model.clinica.Administrador;
import pe.edu.pucp.citamedica.model.usuario.Persona;
import pe.edu.pucp.citamedica.model.usuario.Usuario;
import pe.edu.pucp.citamedica.mysql.AdministradorMySQL;


public class Principal_1 {
    public static void main(String[] args) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Administrador admin = new Administrador();
        admin.setDNI("44444");
        admin.setNombre("Conexion2");
        admin.setApellido("probando");
        admin.setCorreoElectronico("prueba2@hotmail.com");
        admin.setNumTelefono(123312);
        admin.setDireccion("Av.Brasil 123");
        admin.setFechaNacimiento(sdf.parse("23-10-2000"));
        admin.setGenero('M');
        
        Usuario usuario = new Usuario();
        usuario.setUsername("44444");
        usuario.setContrasenha("admin123");
        
        
        AdministradorDAO ad = new AdministradorMySQL();
        int resultado = ad.insertar(admin, usuario);
        if(resultado>0)
            System.out.println("Administrador ingreso correctamente");
        else
            System.out.println("Error en la creacion");
//        
//        Scanner in = new Scanner(System.in);
//        String lect = in.next();
//        admin.setNombre("NOMBREMODIFICADO");
//        int valor = ad.modificar(admin);
//        if(valor>0)
//            System.out.println("Administrador modificado correctamente");
//        else
//            System.out.println("Error en la modificacion");
//        ad.eliminar(24);
//        ArrayList<Administrador>lista=ad.listarTodos();
//        for(Administrador p:lista){
//            String fecha = sdf.format(p.getFechaNacimiento());
//            System.out.println("IdPaciente: "+p.getIdAdministrador()+ "  DNI:"+p.getDNI()+"   "
//                    + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                    "   Correo:"+p.getCorreoElectronico()+"   FechaNacimiento:"+fecha);
//            System.out.println("---------------------------------");
//        }
//        Administrador p = ad.obtenerPorId(23);
//            String fecha = sdf.format(p.getFechaNacimiento());
//            System.out.println("IdPaciente: "+p.getIdAdministrador()+ "  DNI:"+p.getDNI()+"   "
//                    + "Nombre:"+p.getNombre()+"   Apellido:"+p.getApellido()+
//                    "   Correo:"+p.getCorreoElectronico()+"   FechaNacimiento:"+fecha);
//            System.out.println("---------------------------------");
    }
}
