public class Libro extends Material implements IPrestable {
    private String autor;
    private String genero;

    public Libro(String codigo, String titulo, int anio, String autor, String genero) {
        super(codigo, titulo, anio);
        this.autor = autor;
        this.genero = genero;
    }

    public String getAutor() { return autor; }
    public String getGenero() { return genero; }

    @Override
    public void prestar() {
        if (this.getEstado() == EstadoMaterial.DISPONIBLE) {
            this.setEstado(EstadoMaterial.PRESTADO);
        } else {
            throw new IllegalStateException("Libro no disponible");
        }
    }

    @Override
    public void devolver() {
        this.setEstado(EstadoMaterial.DISPONIBLE);
    }

    @Override
    public String getTipo() { return "Libro"; }
}