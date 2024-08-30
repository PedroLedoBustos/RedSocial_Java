import BaseDatos.Conexion;
import Modelo.Red;
import Utilidades.Utilidades;

public class App {
    static Conexion conexion= new Conexion();
    static Red red= new Red(conexion.getConexion());

    public static void main(String[] args) throws Exception {
        Boolean salir= false;
        while (!salir) {
            salir=menu();
        }
    }

    private static Boolean menu(){
        Boolean salir= false;
        System.out.println("#####################################");
        System.out.println("############ MENU PRINCIPAL #########");
        System.out.println("#####################################");
        System.out.println("1 Alta Usuario");
        System.out.println("2 Acceder");
        System.out.println("9 Salir");

        String opcion= Utilidades.leerString("Escoge una opción: ");

        switch (opcion) {
            case "1":
                System.out.println("Ver usuarios");
                red.altaUsuario();
                break;
            case "2":
                System.out.println("Acceder");
                red.menuUsuario();
            break;
            case "4":
                System.out.println("Ver Usuarios"); // Escondido al menu solo para pruebas borrar al terminar
                red.verUsuarios();
            break;
            case "9":
                System.out.println("Saliendo...");
                red.cerrarConexion();
                salir=true;
                break;
        
            default:
            System.out.println("Escoge una opción válida");
                break;
        }
        return salir;
        
    }


}