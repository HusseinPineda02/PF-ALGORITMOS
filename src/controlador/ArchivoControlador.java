package controlador;

import java.io.File;
import java.io.IOException;
import modelo.CentroAutorizado;
import modelo.Investigador;
import tads.TablaHashEncadenada;

public final class ArchivoControlador {
    private final GestorArchivos gestor = new GestorArchivos();
    public int importarInvestigadores(File archivo, TablaHashEncadenada<String, Investigador> tabla) throws IOException { return gestor.cargarInvestigadores(archivo, tabla); }
    public int importarCentros(File archivo, TablaHashEncadenada<String, CentroAutorizado> tabla) throws IOException { return gestor.cargarCentros(archivo, tabla); }
    public void persistirInvestigadores(File archivo, TablaHashEncadenada<String, Investigador> tabla) throws IOException { gestor.guardarInvestigadores(archivo, tabla); }
}
