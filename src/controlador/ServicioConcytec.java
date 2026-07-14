package controlador;

import java.io.File;
import java.io.IOException;
import modelo.CentroAutorizado;
import modelo.EstadisticaRegistro;
import modelo.Investigador;
import tads.ListaEnlazada;
import tads.TablaHashEncadenada;
import tads.Visitante;

public final class ServicioConcytec {
    private final TablaHashEncadenada<String, Investigador> investigadores = new TablaHashEncadenada<String, Investigador>(20011);
    private final TablaHashEncadenada<String, CentroAutorizado> centros = new TablaHashEncadenada<String, CentroAutorizado>(101);
    private final GestorArchivos archivos = new GestorArchivos();
    private File archivoInvestigadores;

    public ServicioConcytec(File directorioDatos) throws IOException {
        archivoInvestigadores = new File(directorioDatos, "investigadores_concytec.csv");
        File archivoCentros = new File(directorioDatos, "centros_autorizados_2023.csv");
        if (archivoInvestigadores.exists()) archivos.cargarInvestigadores(archivoInvestigadores, investigadores);
        if (archivoCentros.exists()) archivos.cargarCentros(archivoCentros, centros);
    }
    public int importarInvestigadores(File origen) throws IOException { investigadoresVacias(); int n=archivos.cargarInvestigadores(origen, investigadores); archivoInvestigadores=new File("data", "investigadores_concytec.csv"); archivos.guardarInvestigadores(archivoInvestigadores, investigadores); return n; }
    public int importarCentros(File origen) throws IOException { centrosVacios(); return archivos.cargarCentros(origen, centros); }
    public void guardar() throws IOException { archivos.guardarInvestigadores(archivoInvestigadores, investigadores); }
    public Investigador obtenerInvestigador(String codigo) { return investigadores.obtener(limpio(codigo)); }
    public void registrar(Investigador investigador) throws IOException {
        if (investigador == null || investigador.getCodigo().length()==0) throw new IllegalArgumentException("El código RENACYT es obligatorio.");
        if (investigadores.contiene(investigador.getCodigo())) throw new IllegalArgumentException("El código RENACYT ya existe; use Actualizar.");
        investigadores.poner(investigador.getCodigo(), investigador); guardar();
    }
    public void actualizar(Investigador investigador) throws IOException {
        if (investigador == null || !investigadores.contiene(investigador.getCodigo())) throw new IllegalArgumentException("No existe el código RENACYT indicado.");
        investigadores.poner(investigador.getCodigo(), investigador); guardar();
    }
    public void eliminar(String codigo) throws IOException {
        if (investigadores.eliminar(limpio(codigo)) == null) throw new IllegalArgumentException("No existe el código RENACYT indicado.");
        guardar();
    }
    public ListaEnlazada<String, Investigador> buscarInvestigadores(final String campo, final String texto, final boolean filtrarCentro, final String centro, final boolean filtrarProvincia, final String provincia, final boolean filtrarDistrito, final String distrito) {
        final ListaEnlazada<String, Investigador> salida = new ListaEnlazada<String, Investigador>(); final String criterio=limpio(texto);
        investigadores.recorrer(new Visitante<String, Investigador>() { public void visitar(String clave, Investigador i) {
            boolean coincide;
            if ("Código RENACYT".equals(campo)) coincide=contiene(i.getCodigo(),criterio);
            else if ("Fecha constancia".equals(campo)) coincide=contiene(i.getFechaConstancia(),criterio);
            else coincide=contiene(i.getNombre(),criterio);
            if (coincide && (!filtrarCentro || contiene(i.getCentroId(),centro)) && (!filtrarProvincia || contiene(i.getProvincia(),provincia)) && (!filtrarDistrito || contiene(i.getDistrito(),distrito))) salida.insertarOReemplazar(i.getCodigo(),i);
        }}); return salida;
    }
    public ListaEnlazada<String, CentroAutorizado> buscarCentros(final String campo, final String texto) {
        final ListaEnlazada<String, CentroAutorizado> salida=new ListaEnlazada<String, CentroAutorizado>(); final String criterio=limpio(texto);
        centros.recorrer(new Visitante<String,CentroAutorizado>() { public void visitar(String clave, CentroAutorizado c) {
            String valor="Nombre institución".equals(campo)?c.getInstitucion():"Área científica".equals(campo)?c.getArea():"Subárea científica".equals(campo)?c.getSubarea():c.getDisciplina();
            if(contiene(valor,criterio)) salida.insertarOReemplazar(c.getCodigo(),c);
        }}); return salida;
    }
   
    public ListaEnlazada<String, EstadisticaRegistro> calcularEstadistica(final String tipo) {
        final ListaEnlazada<String, EstadisticaRegistro> acumulados=new ListaEnlazada<String, EstadisticaRegistro>();
        investigadores.recorrer(new Visitante<String,Investigador>() { public void visitar(String clave, Investigador i) { CentroAutorizado c=centros.obtener(i.getCentroId()); String categoria;
            if("Área científica".equals(tipo)) categoria=c==null?"Centro no identificado":c.getArea();
            else if("Subárea científica".equals(tipo)) categoria=c==null?"Centro no identificado":c.getSubarea();
            else if("Disciplina científica".equals(tipo)) categoria=c==null?"Centro no identificado":c.getDisciplina();
            else if("Provincia".equals(tipo)) categoria=i.getProvincia(); else if("Distrito".equals(tipo)) categoria=i.getDistrito();
            else if("Institución".equals(tipo)) categoria=c==null?"Centro no identificado":c.getInstitucion();
            else if("Investigación científica".equals(tipo)) { if(c==null || !"X".equalsIgnoreCase(c.getInvestigacion())) return; categoria=c.getInstitucion(); }
            else if("Desarrollo tecnológico".equals(tipo)) { if(c==null || !"X".equalsIgnoreCase(c.getDesarrollo())) return; categoria=c.getInstitucion(); }
            else if("Innovación tecnológica".equals(tipo)) { if(c==null || !"X".equalsIgnoreCase(c.getInnovacion())) return; categoria=c.getInstitucion(); }
            else categoria=i.getDepartamento();
            EstadisticaRegistro r=acumulados.obtener(categoria); if(r==null){r=new EstadisticaRegistro(categoria); acumulados.insertarOReemplazar(r.getCategoria(),r);} r.incrementar();
        }}); return acumulados;
    }
    public int totalInvestigadores(){return investigadores.tamano();} public int totalCentros(){return centros.tamano();} public int colisiones(){return investigadores.colisiones();} public double carga(){return investigadores.factorCarga();}
    public CentroAutorizado obtenerCentro(String codigo){return centros.obtener(limpio(codigo));}
    public GestorArchivos getArchivos(){return archivos;}
    private void investigadoresVacias(){ investigadores.limpiar(); }
    private void centrosVacios(){ centros.limpiar(); }
    private static String limpio(String s){return s==null?"":s.trim();} private static boolean contiene(String valor,String criterio){return criterio.length()==0 || (valor!=null && valor.toUpperCase().contains(criterio.toUpperCase()));}
}
