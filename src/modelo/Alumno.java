package modelo;

public class Alumno {

    private int id;
    private String nombre;
    private String apellidos;
    private String fechaNaciemiento;
    private int nota;

    public Alumno() {
    }

    public Alumno(int id, String nombre, String apellidos, String fechaNaciemiento, int nota) {
        this.id = id;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.fechaNaciemiento = fechaNaciemiento;
        this.nota = nota;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public String getFechaNaciemiento() {
        return fechaNaciemiento;
    }

    public int getNota() {
        return nota;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public void setFechaNaciemiento(String fechaNaciemiento) {
        this.fechaNaciemiento = fechaNaciemiento;
    }

    public void setNota(int nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return "id: " + id + " -- nombre: " + nombre + " -- apellidos:" + apellidos
                + " --  fechaNaciemiento: " + fechaNaciemiento + " -- nota:" + nota;
    }
}
