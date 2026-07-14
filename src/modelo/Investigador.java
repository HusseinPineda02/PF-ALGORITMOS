package modelo;

public final class Investigador {
    private String codigo, nombre, genero, url, reglamento, condicion, fechaConstancia, nivel2021;
    private String grupo2018, nivel2018, inicio2018, fin2018, rangoEdad, pais, departamento, provincia, distrito, centroId;

    public Investigador(String codigo, String nombre, String genero, String url, String reglamento, String condicion,
            String fechaConstancia, String nivel2021, String grupo2018, String nivel2018, String inicio2018, String fin2018,
            String rangoEdad, String pais, String departamento, String provincia, String distrito, String centroId) {
        this.codigo = limpio(codigo); this.nombre = limpio(nombre); this.genero = limpio(genero); this.url = limpio(url);
        this.reglamento = limpio(reglamento); this.condicion = limpio(condicion); this.fechaConstancia = limpio(fechaConstancia); this.nivel2021 = limpio(nivel2021);
        this.grupo2018 = limpio(grupo2018); this.nivel2018 = limpio(nivel2018); this.inicio2018 = limpio(inicio2018); this.fin2018 = limpio(fin2018);
        this.rangoEdad = limpio(rangoEdad); this.pais = limpio(pais); this.departamento = limpio(departamento); this.provincia = limpio(provincia); this.distrito = limpio(distrito); this.centroId = limpio(centroId);
    }
    public static Investigador desdeCampos(String[] c) {
        return new Investigador(valor(c,0),valor(c,1),valor(c,2),valor(c,3),valor(c,4),valor(c,5),valor(c,6),valor(c,7),valor(c,8),valor(c,9),valor(c,10),valor(c,11),valor(c,12),valor(c,13),valor(c,14),valor(c,15),valor(c,16),valor(c,17));
    }
    public String aCsv() { return unir(codigo,nombre,genero,url,reglamento,condicion,fechaConstancia,nivel2021,grupo2018,nivel2018,inicio2018,fin2018,rangoEdad,pais,departamento,provincia,distrito,centroId); }
    public String getCodigo() { return codigo; } public String getNombre() { return nombre; } public String getGenero() { return genero; }
    public String getCondicion() { return condicion; } public String getFechaConstancia() { return fechaConstancia; } public String getNivel2021() { return nivel2021; }
    public String getDepartamento() { return departamento; } public String getProvincia() { return provincia; } public String getDistrito() { return distrito; } public String getCentroId() { return centroId; }
    public void actualizar(String nombre, String genero, String condicion, String fechaConstancia, String nivel2021, String departamento, String provincia, String distrito, String centroId) {
        this.nombre=limpio(nombre); this.genero=limpio(genero); this.condicion=limpio(condicion); this.fechaConstancia=limpio(fechaConstancia); this.nivel2021=limpio(nivel2021); this.departamento=limpio(departamento); this.provincia=limpio(provincia); this.distrito=limpio(distrito); this.centroId=limpio(centroId);
    }
    private static String valor(String[] a, int i) { return i < a.length ? a[i] : ""; }
    private static String limpio(String valor) { return valor == null ? "" : valor.trim(); }
    private static String unir(String... valores) { String r=""; int i=0; while(i<valores.length){ if(i>0) r += ";"; r += valores[i].replace(";", ",").replace("\n", " "); i++; } return r; }
}
