package database;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {

    static String nombreBD = "DataBase.bd";
    static Connection connect;

    public static void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:" + nombreBD);
            if (connect != null) {
                System.out.println("Conectado");
            }
        } catch (SQLException ex) {
            System.err.println("No se ha podido conectar\n" + ex.getMessage());
        }
    }

    public static void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Alumno> mostrarAlumnos() {
        ArrayList<Alumno> listaAlumnos = new ArrayList();
        connect();
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("SELECT * FROM Alumnos");
            result = st.executeQuery();
            while (result.next()) {
                Alumno alu = new Alumno(result.getInt("id"), result.getString("Nombre"),
                        result.getString("Apellidos"), result.getString("FechaNacimiento"), result.getInt("Nota"));
                listaAlumnos.add(alu);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        close();
        return listaAlumnos;
    }

    public static void añadirAlumno(Alumno alumno) {
        connect();
        try {
            PreparedStatement st = connect.prepareStatement("insert into Alumnos (id, Nombre, Apellidos, FechaNacimiento, Nota) values (?,?,?,?,?)");
            st.setString(1, String.valueOf(alumno.getId()));
            st.setString(2, alumno.getNombre());
            st.setString(3, alumno.getApellidos());
            st.setString(4, alumno.getFechaNaciemiento());
            st.setString(5, String.valueOf(alumno.getNota()));
            st.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        close();
    }

    public static void borrarAlumno(int id) {
        connect();
        try {
            PreparedStatement st = connect.prepareStatement("DELETE FROM Alumnos WHERE id=?");
            st.setInt(1, id);
            st.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();
    }

    public static ArrayList<Alumno> search(String busqueda) {
        ArrayList<Alumno> listaAlumnos = new ArrayList();
        connect();
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Alumnos where id like '" + busqueda
                    + "' or Nombre like '" + busqueda + "' or Apellidos like '" + busqueda + "' or Nota like '" + busqueda + ""
                    + "' or FechaNacimiento like '" + busqueda + "'");
            result = st.executeQuery();
            while (result.next()) {
                Alumno alu = new Alumno(result.getInt("id"), result.getString("Nombre"),
                        result.getString("Apellidos"), result.getString("FechaNacimiento"), result.getInt("Nota"));
                listaAlumnos.add(alu);
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        close();
        return listaAlumnos;
    }

    public static void modificarAlumno(Alumno alumno) {
        close();
        connect();
        try {
            PreparedStatement st = connect.prepareStatement("UPDATE Alumnos SET Nombre=? , Apellidos = ? , FechaNacimiento = ? , Nota = ?  where id=?");
            st.setString(1, alumno.getNombre());
            st.setString(2, alumno.getApellidos());
            st.setString(3, alumno.getFechaNaciemiento());
            st.setString(4, String.valueOf(alumno.getNota()));
            st.setString(5, String.valueOf(alumno.getId()));
            st.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();
    }

    public static Alumno buscarAlumno(int id) {
        connect();
        ResultSet result = null;
        try {
            PreparedStatement st = connect.prepareStatement("select * from Alumnos where id like '" + id + "'");
            result = st.executeQuery();
            if (result.next()) {
                Alumno alu = new Alumno(result.getInt("id"), result.getString("Nombre"),
                        result.getString("Apellidos"), result.getString("FechaNacimiento"), result.getInt("Nota"));
                return alu;
            }
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        close();
        return null;
    }
}
