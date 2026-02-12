public class Revista extends Material {
    private String nombre;
    private int numero;

    public Revista(String codigo, String titulo, int anio, String nombre, int numero) {
        super(codigo, titulo, anio);
        this.nombre = nombre;
        this.numero = numero;
    }

    public String getNombre() { return nombre; }
    public int getNumero() { return numero; }

    @Override
    public String getTipo() { return "Revista"; }
}