import java.util.*;

public class BibliotecaController {
    private BibliotecaDAO dao = BibliotecaDAO.getInstance();
    private int contadorPrestamos = 1;

    // ========== METODOS PARA DAR DE ALTA MATERIALES ==========

    public void altaLibro(String codigo, String titulo, int anio, String autor, String genero) {
        Libro libro = new Libro(codigo, titulo, anio, autor, genero);
        dao.agregarMaterial(libro);
    }

    public void altaRevista(String codigo, String titulo, int anio, String nombre, int numero) {
        Revista revista = new Revista(codigo, titulo, anio, nombre, numero);
        dao.agregarMaterial(revista);
    }

    public void altaSocio(String dni, String nombre, String apellido) {
        Socio socio = new Socio(dni, nombre, apellido);
        dao.agregarSocio(socio);
    }

    // ==========  METODOS PARA BAJA Y MODIFICACION ==========

    public void bajaMaterial(String codigo) {
        dao.eliminarMaterial(codigo);
    }

    public void modificarLibro(String codigo, String titulo, int anio, String autor, String genero) {
        Material materialExistente = dao.buscarMaterial(codigo);
        if (materialExistente instanceof Libro) {
            Libro libroActualizado = new Libro(codigo, titulo, anio, autor, genero);
            dao.modificarMaterial(libroActualizado);
        }
    }

    public void modificarRevista(String codigo, String titulo, int anio, String nombre, int numero) {
        Material materialExistente = dao.buscarMaterial(codigo);
        if (materialExistente instanceof Revista) {
            Revista revistaActualizada = new Revista(codigo, titulo, anio, nombre, numero);
            dao.modificarMaterial(revistaActualizada);
        }
    }

    // ========== METODOS PARA GESTION DE PRESTAMOS ==========

    public void prestarLibro(String codigoLibro, String dniSocio) {
        Material material = dao.buscarMaterial(codigoLibro);
        Socio socio = dao.buscarSocio(dniSocio);

        if (!(material instanceof Libro))
            throw new IllegalArgumentException("Solo libros se pueden prestar");

        if (material.getEstado() != EstadoMaterial.DISPONIBLE)
            throw new IllegalStateException("Libro no disponible");

        if (socio == null)
            throw new IllegalArgumentException("Socio no encontrado");

        Libro libro = (Libro) material;
        libro.prestar();

        String idPrestamo = "P" + contadorPrestamos++;
        Prestamo prestamo = new Prestamo(idPrestamo, libro, socio);
        dao.agregarPrestamo(prestamo);
    }

    public void devolverLibro(String codigoLibro) {
        Prestamo prestamo = dao.buscarPrestamoActivo(codigoLibro);
        if (prestamo == null)
            throw new IllegalArgumentException("Prestamo activo no encontrado");
        prestamo.devolver();
    }

    // ========== METODOS DE CONSULTA (para mostrar datos) ==========

    public List<Material> getMateriales() {
        return dao.obtenerTodosMateriales();
    }

    public List<Socio> getSocios() {
        return dao.obtenerTodosSocios();
    }

    public List<Prestamo> getPrestamosActivos() {
        return dao.obtenerPrestamosActivos();
    }
}