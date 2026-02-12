import java.util.*;

public class BibliotecaDAO {
    private static BibliotecaDAO instancia;

    // ========== "TABLAS" DE LA BASE DE DATOS ==========
    private ArrayList<Material> materiales = new ArrayList<>();
    private ArrayList<Socio> socios = new ArrayList<>();
    private ArrayList<Prestamo> prestamos = new ArrayList<>();

    private BibliotecaDAO() {}

    public static BibliotecaDAO getInstance() {
        if (instancia == null) {
            instancia = new BibliotecaDAO();
        }
        return instancia;
    }

    // ========== METODOS PARA GESTIONAR MATERIALES ==========

    public void agregarMaterial(Material m) {
        if (buscarMaterial(m.getCodigo()) != null) {
            throw new IllegalArgumentException("Codigo ya existe");
        }
        materiales.add(m);
    }

    public Material buscarMaterial(String codigo) {
        for (Material material : materiales) {
            if (material.getCodigo().equals(codigo)) {
                return material;
            }
        }
        return null;
    }

    public List<Libro> getLibrosDisponibles() {
        List<Libro> disponibles = new ArrayList<>();
        for (Material material : materiales) {
            if (material instanceof Libro && material.getEstado() == EstadoMaterial.DISPONIBLE) {
                disponibles.add((Libro) material);
            }
        }
        return disponibles;
    }

    // ========== METODOS PARA BAJA Y MODIFICACION ==========

    public void eliminarMaterial(String codigo) {
        Material material = buscarMaterial(codigo);
        if (material != null) {
            materiales.remove(material);
        }
    }

    public void modificarMaterial(Material materialActualizado) {
        Material materialExistente = buscarMaterial(materialActualizado.getCodigo());
        if (materialExistente != null) {
            // Reemplazar el material existente
            materiales.remove(materialExistente);
            materiales.add(materialActualizado);
        }
    }

    // ========== METODOS PARA GESTIONAR SOCIOS ==========

    public void agregarSocio(Socio s) {
        if (buscarSocio(s.getDni()) != null) {
            throw new IllegalArgumentException("DNI ya existe");
        }
        socios.add(s);
    }

    public Socio buscarSocio(String dni) {
        for (Socio socio : socios) {
            if (socio.getDni().equals(dni)) {
                return socio;
            }
        }
        return null;
    }

    // ========== METODOS PARA GESTIONAR PRESTAMOS ==========

    public void agregarPrestamo(Prestamo p) {
        prestamos.add(p);
    }

    public Prestamo buscarPrestamoActivo(String codigoLibro) {
        for (Prestamo prestamo : prestamos) {
            if (prestamo.isActivo() && prestamo.getLibro().getCodigo().equals(codigoLibro)) {
                return prestamo;
            }
        }
        return null;
    }

    // ========== METODOS DE CONSULTA ==========

    public List<Material> obtenerTodosMateriales() {
        return new ArrayList<>(materiales);
    }

    public List<Socio> obtenerTodosSocios() {
        return new ArrayList<>(socios);
    }

    public List<Prestamo> obtenerTodosPrestamos() {
        return new ArrayList<>(prestamos);
    }

    public List<Prestamo> obtenerPrestamosActivos() {
        List<Prestamo> activos = new ArrayList<>();
        for (Prestamo prestamo : prestamos) {
            if (prestamo.isActivo()) {
                activos.add(prestamo);
            }
        }
        return activos;
    }
}