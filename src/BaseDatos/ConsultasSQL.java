package BaseDatos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Modelo.Mensaje;
import Modelo.Usuario;

public class ConsultasSQL {

    // Metodo que permite sacar la lista de usuarios de la base de datos
    public ArrayList<Usuario> listaUsuarios(Connection conn){
        ArrayList<Usuario> usuarios= new ArrayList<>();
        String sql= "SELECT id, nombre, pass FROM usuarios";

        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            ResultSet resultado= statement.executeQuery();

            while (resultado.next()) {
                Integer id= resultado.getInt("id");
                String nombre= resultado.getString("nombre");
                String password= resultado.getString("pass");

                Usuario usuario= new Usuario(id,nombre, password);
                usuarios.add(usuario);
                
            }
        } catch (SQLException e) {
            System.out.println("Se ha producido un error al consultar los usuarios en la base de datos");
        }

    return usuarios;
    }

    public void insertarUsuario(Connection conn, Usuario user){
        String sql= "insert into usuarios(nombre,pass) values (?,?)";
        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, user.getNombre());
            statement.setString(2, user.getPassword());

            int filasInsetadas= statement.executeUpdate();
            if(filasInsetadas>0){
                System.out.println("El usuario: "+ user.getNombre()+ " ha sido dado de alta con éxito");
            }
        } catch (SQLException e) {
            System.out.println("Se ha producido un error al insertar al usuario en la base de datos");
        }
    }

    public Integer obtenerIdUsuario(Connection conn, String nombreUsuario){
        Integer id= -1;
        String sql= "SELECT id FROM usuarios where nombre=?";

        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            ResultSet resultado= statement.executeQuery();

            while (resultado.next()) {
                id= resultado.getInt("id");
                return id;
                
            }
        } catch (SQLException e) {
            System.out.println("Se ha producido un error al consultar el id del usuario en la base de datos");
        }

    return id;
    }
    

    public ArrayList<String> verAmigos(Connection conn, Integer id){
        ArrayList<String> amigos= new ArrayList<>();
        String sql= "Select nombre from amigos where idUsuario=?";
        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setInt(1, id);
            ResultSet resultado= statement.executeQuery();

            while(resultado.next()){
                String nombre= resultado.getString("nombre");
                amigos.add(nombre);
            }
            return amigos;
        } catch (Exception e) {
            System.out.println("Se ha producido un error al consultar los amigos en la base de datos");
        }
        return amigos;
    }

    public void insertarAmigo(Connection conn, Integer idUsuario, String nombre){
        String sql= "INSERT INTO amigos(nombre, idUsuario) VALUES (?,?)";
        try {PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, nombre );
            statement.setInt(2,idUsuario);

            int filasInsetadas= statement.executeUpdate();
            if(filasInsetadas>0){
                System.out.println("Amigo añadido correctamente");
            }
            
        } catch (Exception e) {
            System.out.println("Se ha producido un error al insertar al amigo en la base de datos");
        }
    }

    public void eliminarAmigo(Connection conn, Integer idUsuario, String nombre){
        String sql= "DELETE FROM amigos WHERE nombre= ? and idUsuario= ?";
        try {PreparedStatement statement= conn.prepareStatement(sql);
            statement.setString(1, nombre );
            statement.setInt(2,idUsuario);

            int filasInsetadas= statement.executeUpdate();
            if(filasInsetadas>0){
                System.out.println("Amigo eliminado correctamente");
            }
            
        } catch (Exception e) {
            System.out.println("Se ha producido un error al elimnar al amigo en la base de datos");
        }
    }

    public ArrayList<Mensaje> consultarMensajes(Connection conn, Integer idUsuario){
        String sql= "SELECT id, emisario, destinatario, texto FROM mensajes WHERE destinatario= ?";
        ArrayList<Mensaje> mensajes= new ArrayList<>();

        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            ResultSet resultado= statement.executeQuery();

            while (resultado.next()) {
                Integer id= resultado.getInt("Id");
                Integer emisario= resultado.getInt("emisario");
                String texto= resultado.getString("texto");

                Mensaje mensaje= new Mensaje(id,texto, emisario, emisario);
                mensajes.add(mensaje);   
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error al consultar los mensajes en la base de datos");
        }

        return mensajes;
    }

    public Usuario sacarUsuario(Connection conn, Integer idUsuario){
        String sql= "SELECT id, nombre FROM usuarios where id= ?";
        Usuario user= null;
        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            ResultSet resultado= statement.executeQuery();

            while (resultado.next()) {
                Integer id= resultado.getInt("id");
                String nombre= resultado.getString("nombre");
                
                user= new Usuario(id, nombre);
                return user;
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error al consultar al usuario en la base de datos");
        }
        return user;
    }

    public void insertarMensaje(Connection conn, Integer idEmisor, Integer idDestinatario, String texto){
        String sql= "INSERT INTO mensajes(emisario,destinatario,texto) VALUES (?,?,?)";

        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setInt(1, idEmisor);
            statement.setInt(2, idDestinatario);
            statement.setString(3, texto);

            int filasInsetadas= statement.executeUpdate();
            if(filasInsetadas>0){
                System.out.println("Mensaje mandado con éxito");
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error al insertar el mensaje en la base de datos");
        }
    }

    public void eliminarMensaje(Connection conn, Integer idMensaje){
        String sql= "DELETE FROM mensajes WHERE Id= ?";

        try {
            PreparedStatement statement= conn.prepareStatement(sql);
            statement.setInt(1, idMensaje);

            int filasInsetadas= statement.executeUpdate();
            if(filasInsetadas>0){
                System.out.println("Mensaje eliminado con éxito");
            }
        } catch (Exception e) {
            System.out.println("Se ha producido un error al eliminar el mensaje en la base de datos");
        }
    }

}
