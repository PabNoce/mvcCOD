package control;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Alumno;

public class Control {

    static Connection connect;

    public static void connect() {
        try {
            connect = DriverManager.getConnection("jdbc:sqlite:DataBase.bd");
            if (connect != null) {
            }
        } catch (SQLException ex) {
            System.err.println("Error\n" + ex.getMessage());
        }
    }

    public static void close() {
        try {
            connect.close();
        } catch (SQLException ex) {
            Logger.getLogger(Control.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static ArrayList<Alumno> mostrarAlumnos() {
        ArrayList<Alumno> listaAlumnos = new ArrayList();
        connect();
        ResultSet select = null;
        try {
            PreparedStatement statement = connect.prepareStatement("SELECT * FROM Alumnos");
            select = statement.executeQuery();
            while (select.next()) {
                Alumno alu = new Alumno(select.getInt("id"), select.getString("Nombre"),
                        select.getString("Apellidos"), select.getString("FechaNacimiento"), select.getInt("Nota"));
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
            PreparedStatement statement = connect.prepareStatement("insert into Alumnos (id, Nombre, Apellidos, FechaNacimiento, Nota) values (?,?,?,?,?)");
            statement.setString(1, String.valueOf(alumno.getId()));
            statement.setString(2, alumno.getNombre());
            statement.setString(3, alumno.getApellidos());
            statement.setString(4, alumno.getFechaNaciemiento());
            statement.setString(5, String.valueOf(alumno.getNota()));
            statement.execute();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        close();
    }

    public static void borrarAlumno(int id) {
        connect();
        try {
            PreparedStatement statement = connect.prepareStatement("DELETE FROM Alumnos WHERE id=?");
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();
    }

    public static ArrayList<Alumno> search(String busqueda) {
        ArrayList<Alumno> listaAlumnos = new ArrayList();
        connect();
        ResultSet select = null;
        try {
            PreparedStatement statement = connect.prepareStatement("select * from Alumnos where id like '" + busqueda
                    + "' or Nombre like '" + busqueda + "' or Apellidos like '" + busqueda + "' or Nota like '" + busqueda + ""
                    + "' or FechaNacimiento like '" + busqueda + "'");
            select = statement.executeQuery();
            while (select.next()) {
                Alumno alu = new Alumno(select.getInt("id"), select.getString("Nombre"),
                        select.getString("Apellidos"), select.getString("FechaNacimiento"), select.getInt("Nota"));
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
            PreparedStatement statement = connect.prepareStatement("UPDATE Alumnos SET Nombre=? , Apellidos = ? , FechaNacimiento = ? , Nota = ?  where id=?");
            statement.setString(1, alumno.getNombre());
            statement.setString(2, alumno.getApellidos());
            statement.setString(3, alumno.getFechaNaciemiento());
            statement.setString(4, String.valueOf(alumno.getNota()));
            statement.setString(5, String.valueOf(alumno.getId()));
            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        close();
    }

    public static Alumno buscarAlumno(int id) {
        connect();
        ResultSet result = null;
        try {
            PreparedStatement statement = connect.prepareStatement("select * from Alumnos where id like '" + id + "'");
            result = statement.executeQuery();
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
