package Modelo;

import BaseDatos.ConsultasSQL;
import Utilidades.Utilidades;

import java.sql.Connection;
import java.util.ArrayList;

public class Usuario {
    private int id;
    private String nombre;
    private String password;
    private ArrayList<String> amigos;
    private ArrayList<Mensaje> mensajes;

    public Usuario(String nombre, String password){
        this.nombre= nombre;
        this.password= password;
        amigos= new ArrayList<>();
        mensajes= new ArrayList<>();
    }

    public Usuario(int id, String nombre, String password){
        this.id=id;
        this.nombre= nombre;
        this.password= password;
        amigos= new ArrayList<>();
        mensajes= new ArrayList<>();
    }

    public Usuario(int id, String nombre){
        this.id=id;
        this.nombre= nombre;
        amigos= new ArrayList<>();
        mensajes= new ArrayList<>();
    }

    public Integer getId(){
        return this.id;
    }

    public void setId(Integer idNuevo){
        id= idNuevo;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getPassword(){
        return this.password;
    }

    public void verAmigos(Connection conn, ConsultasSQL consultas){
        amigos= consultas.verAmigos(conn, this.id);

        if(amigos.isEmpty()){
            System.out.println("El usuario: "+ this.nombre+ " no tiene agregado ningún amigo.");
        }else{
            int contador=0;
            System.out.println("######## LISTA DE AMIGOS ##########");
            for(String amigo: amigos){
                contador++;
                System.out.println(contador+" - "+ amigo.toUpperCase() );
            }
        }
    }

    public void altaAmigo(Connection conn, ConsultasSQL consultas){
        ArrayList<Usuario> usuarios= consultas.listaUsuarios(conn);
        amigos= consultas.verAmigos(conn, this.id);

        String nombreAmigo= Utilidades.leerString("Introduce el nombre del usuario que quieres que sea tu amigo: ");
        Boolean existeUsuario= false;
        Boolean existeAmigo= false;
        Boolean soyYo= false;

        if(nombreAmigo.toUpperCase().equals(this.nombre.toUpperCase())){
            soyYo=true;
        }

        for(Usuario user: usuarios){
            if(user.getNombre().toUpperCase().equals(nombreAmigo.toUpperCase())){
                existeUsuario= true;
                break;
            }
        }
        for(String nombre: amigos){
            if(nombre.toUpperCase().equals(nombreAmigo.toUpperCase())){
                existeAmigo=true;
                break;
            }
        }

        if(existeUsuario && !existeAmigo && !soyYo){
            consultas.insertarAmigo(conn, getId(), nombreAmigo);
        }else{
            System.out.println("El nombre no esta registrado como usuario o ya es tu amigo");
        }
    }

    public void bajaAmigo(Connection conn, ConsultasSQL consultas){
        amigos= consultas.verAmigos(conn, this.id);
        String nombreAmigo= Utilidades.leerString("Introduce el nombre del amigo que quieres elimnar: ");
        Boolean existeAmigo= false;
    
        for(String nombre: amigos){
            if(nombre.toUpperCase().equals(nombreAmigo.toUpperCase())){
                existeAmigo=true;
            }
        }

        if(existeAmigo){
            consultas.eliminarAmigo(conn, getId(), nombreAmigo);
        }else{
            System.out.println("Lo siento, ese usuario no esta registrado como tu amigo");
        }
    }

    public void verMensajes(Connection conn, ConsultasSQL consultas){
        mensajes= consultas.consultarMensajes(conn, getId());

        if(mensajes.isEmpty()){
            System.out.println("Este usuario no tiene mensajes recibidos");
        }else{
            int contador=0;
            for(Mensaje mensaje:mensajes){
                contador++;
                Usuario emisario= consultas.sacarUsuario(conn, mensaje.getEmisario());
                System.out.println(contador+"º MANDADO POR: "+ emisario.getNombre()+ " TEXTO: "+ mensaje.getTexto()+ " ID= "+ mensaje.getId());
            }
        }
    }

    public void mandarMensaje(Connection conn, ConsultasSQL consultas){
        Integer idEmisor= getId();
        amigos= consultas.verAmigos(conn, this.id);

        String nombreAmigo= Utilidades.leerString("Introduce el nombre del usuario al que quieres mandarle el mensaje: ");
        Boolean amigoExiste= false;

        for(String nombre:amigos){
            if(nombre.toUpperCase().equals(nombreAmigo.toUpperCase())){
                amigoExiste=true;
                break;
            }
        }

        if(!amigoExiste){
            System.out.println("Lo siento, ese usuario no es tu amigo y no le puedes mandar mensajes");
        }else{
            Integer idDestinatario= consultas.obtenerIdUsuario(conn, nombreAmigo);
            String texto= Utilidades.leerString("Escribir mensaje: ");
            consultas.insertarMensaje(conn, idEmisor, idDestinatario, texto);
        }

    }

    public void eliminarMensaje(Connection conn, ConsultasSQL consultas){
        mensajes= consultas.consultarMensajes(conn, getId());
        Integer idEliminar= Utilidades.leerInteger("Introduce el ID del mensaje que quieres eliminar: ");
        Boolean existe= false;

        for(Mensaje mensaje:mensajes){
            if(mensaje.getId().equals(idEliminar)){
                existe=true;
                break;
            }
        }

        if(existe){
            consultas.eliminarMensaje(conn, idEliminar);
        }else{
            System.out.println("Lo siento, no tienen ningun mensaje con ese ID.");
        }
    }


}
