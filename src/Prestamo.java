import java.time.LocalDate;

public class Prestamo {
    private String id;
    private Libro libro;
    private Socio socio;
    private LocalDate fechaPrestamo;
    private LocalDate fechaDevolucion;
    private boolean activo;

    public Prestamo(String id, Libro libro, Socio socio) {
        this.id = id;
        this.libro = libro;
        this.socio = socio;
        this.fechaPrestamo = LocalDate.now();
        this.activo = true;
    }

    public void devolver() {
        this.activo = false;
        this.fechaDevolucion = LocalDate.now();
        libro.devolver();
    }

    public boolean isActivo() { return activo; }
    public Libro getLibro() { return libro; }
    public Socio getSocio() { return socio; }
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
}