public abstract class Material {
    protected String codigo;
    protected String titulo;
    protected int anio;
    protected EstadoMaterial estado;

    public Material(String codigo, String titulo, int anio) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.anio = anio;
        this.estado = EstadoMaterial.DISPONIBLE;
    }

    public String getCodigo() { return codigo; }
    public String getTitulo() { return titulo; }
    public int getAnio() { return anio; }
    public EstadoMaterial getEstado() { return estado; }
    public void setEstado(EstadoMaterial estado) { this.estado = estado; }

    public abstract String getTipo();
}