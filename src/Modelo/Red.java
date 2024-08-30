package Modelo;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import BaseDatos.ConsultasSQL;
import Utilidades.Utilidades;

public class Red {
    Connection conexion;
    ConsultasSQL consultas;
    ArrayList<Usuario> usuarios;

    public Red(Connection conexion){
        this.conexion=conexion;
        consultas= new ConsultasSQL();
        usuarios= new ArrayList<>();

    }

    public void verUsuarios(){
        usuarios= consultas.listaUsuarios(conexion);

        if(usuarios.isEmpty()){
            System.out.println("No hay usuarios registrados");
        }else{
            for(Usuario user:usuarios){
                System.out.println("Id: "+ user.getId()+" Nombre: "+ user.getNombre()+ " Password: "+ user.getPassword());
            }
        }
    }


    public void cerrarConexion(){
        if(conexion != null){
            try {
                conexion.close();
                System.out.println("Conexion cerrada con éxito");
            } catch (SQLException e) {
                System.out.println("Se ha producido un error al cerrar la conexión con la base de datos");
            }
        }else{
            System.out.println("No se puede cerrar la conexión a la base de datos porque no estaba conectada");
        }
    }

    private Usuario crearUsuario(){
        String nombre= Utilidades.leerString("Introduce el nombre del usuario: ");
        String pass= Utilidades.leerString("Introduce la contraseña del usuario: ");

        Usuario user= new Usuario(nombre, pass);
        return user;
    }

    public void altaUsuario(){
        Usuario usuario= crearUsuario();
        boolean existe= false;
        usuarios= consultas.listaUsuarios(conexion);
         if(usuarios.isEmpty()){
            usuarios.add(usuario);
         }else{
            for(Usuario user:usuarios){
                if(user.getNombre().toUpperCase().equals(usuario.getNombre().toUpperCase())){
                    existe=true;
                }
            }
         }

         if(existe){
            System.out.println("Lo siento, ese usuario ya esta registrado");
         }else{
            consultas.insertarUsuario(conexion, usuario);
         }
    }

    public void menuUsuario(){
        Boolean salir= false;
        Boolean existe= false;
        Usuario user= crearUsuario();
        usuarios= consultas.listaUsuarios(conexion);
        
        for(Usuario usuario: usuarios){
            if(usuario.getNombre().toUpperCase().equals(user.getNombre().toUpperCase()) && usuario.getPassword().toUpperCase().equals(user.getPassword().toUpperCase())){
                existe=true;
                user.setId(usuario.getId());
            }
        }

        if(existe){
            while (!salir) {
                System.out.println("BIENVENIDO AL MENU DE: "+ user.getNombre().toUpperCase());
                salir=menu(user);
            }
        }else{
            System.out.println("Error al acceder a la cuenta, nombre o contraseña erronea");
        }
    }

    private Boolean menu(Usuario user){
        Boolean salir= false;
        System.out.println("1- Agregar Amigos");
        System.out.println("2- Eliminar Amigos");
        System.out.println("3- Ver lista de Amigos");
        System.out.println("4- Leer Mensajes");
        System.out.println("5- Eviar Mensaje");
        System.out.println("6- Borrar Mensaje");
        System.out.println("9- Salir");

        String opcion= Utilidades.leerString("Escoge una opción: ");
        switch (opcion) {
            case "1":
                System.out.println("Agregar Amigos");
                user.altaAmigo(conexion, consultas);
                break;
            case "2":
                System.out.println("Elimnar Amigos");
                user.bajaAmigo(conexion, consultas);
                break;
            case "3":
                System.out.println("Ver Amigos");
                user.verAmigos(conexion, consultas);
                break;
            case "4":
                System.out.println("Leer Mensajes");
                user.verMensajes(conexion, consultas);
                break;
            case "5":
                System.out.println("Enviar Mensajes");
                user.mandarMensaje(conexion, consultas);
                break;
            case "6":
                System.out.println("Borrar Mensajes");
                user.eliminarMensaje(conexion, consultas);
                break;
            case "9":
                System.out.println("Saliendo");
                salir=true;
                break;
            default:
                System.out.println("Escoge una opción válida");
                break;
        }
        return salir;


    }

    
}


    

