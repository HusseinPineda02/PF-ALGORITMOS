package controlador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import modelo.CentroAutorizado;
import modelo.Investigador;
import tads.TablaHashEncadenada;
import tads.Visitante;

public final class GestorArchivos {
    public static final String CABECERA_INVESTIGADORES = "codigo_renacyt;investigador;genero;URL_ficha_renacyt;reglamento;condicion_actividad;emision_de_constancia_reglamento_2021;nivel_reglamento_2021;grupo_reglamento_2018;nivel_reglamento_2018;fecha_inicio_vigencia_reglamento_2018;fecha_fin_vigencia_reglamento_2018;rango_edad;pais_residencia;departamento;provincia;distrito;CENTRO_AUTORIZADO";
    public int cargarInvestigadores(File archivo, TablaHashEncadenada<String, Investigador> tabla) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo)); String linea; int leidos=0; boolean cabecera=true;
        while((linea=lector.readLine())!=null) { if(cabecera){cabecera=false; continue;} if(linea.trim().length()==0) continue; String[] campos=linea.split(";",-1); Investigador i=Investigador.desdeCampos(campos); if(i.getCodigo().length()>0){tabla.poner(i.getCodigo(),i);leidos++;} }
        lector.close(); return leidos;
    }
    public int cargarCentros(File archivo, TablaHashEncadenada<String, CentroAutorizado> tabla) throws IOException {
        BufferedReader lector = new BufferedReader(new FileReader(archivo)); String linea; int leidos=0; boolean cabecera=true;
        while((linea=lector.readLine())!=null) { if(cabecera){cabecera=false; continue;} if(linea.trim().length()==0) continue; CentroAutorizado c=CentroAutorizado.desdeCampos(linea.split(";",-1)); if(c.getCodigo().length()>0){tabla.poner(c.getCodigo(),c);leidos++;} }
        lector.close(); return leidos;
    }
    public void guardarInvestigadores(File archivo, final TablaHashEncadenada<String, Investigador> tabla) throws IOException {
        File padre=archivo.getParentFile(); if(padre!=null) padre.mkdirs(); final BufferedWriter escritor=new BufferedWriter(new FileWriter(archivo)); escritor.write(CABECERA_INVESTIGADORES); escritor.newLine();
        tabla.recorrer(new Visitante<String,Investigador>() { public void visitar(String clave, Investigador i) { try { escritor.write(i.aCsv()); escritor.newLine(); } catch(IOException ex){throw new ErrorEscritura(ex);} } });
        escritor.close();
    }
    public void exportarTexto(File archivo, String cabecera, final String[] filas, int cantidad) throws IOException {
        BufferedWriter escritor=new BufferedWriter(new FileWriter(archivo)); escritor.write(cabecera); escritor.newLine(); int i=0; while(i<cantidad){escritor.write(filas[i]); escritor.newLine();i++;} escritor.close();
    }
   
    public void exportarPdf(File archivo, String titulo, String[] lineas, int cantidad) throws IOException {
        String contenido="BT /F1 11 Tf 50 790 Td ("+pdf(titulo)+") Tj 0 -20 Td "; int i=0; while(i<cantidad && i<34){contenido += "("+pdf(lineas[i])+") Tj 0 -18 Td ";i++;} contenido+="ET";
        String[] objetos=new String[5]; objetos[0]="<< /Type /Catalog /Pages 2 0 R >>"; objetos[1]="<< /Type /Pages /Kids [3 0 R] /Count 1 >>"; objetos[2]="<< /Type /Page /Parent 2 0 R /MediaBox [0 0 595 842] /Resources << /Font << /F1 4 0 R >> >> /Contents 5 0 R >>"; objetos[3]="<< /Type /Font /Subtype /Type1 /BaseFont /Helvetica >>"; objetos[4]="<< /Length "+contenido.length()+" >>\nstream\n"+contenido+"\nendstream";
        BufferedWriter w=new BufferedWriter(new FileWriter(archivo)); w.write("%PDF-1.4\n"); int[] pos=new int[5]; int cursor=9; i=0; while(i<5){pos[i]=cursor;String o=(i+1)+" 0 obj\n"+objetos[i]+"\nendobj\n";w.write(o);cursor+=o.length();i++;} int xref=cursor;w.write("xref\n0 6\n0000000000 65535 f \n");i=0;while(i<5){w.write(String.format("%010d 00000 n \n",pos[i]));i++;}w.write("trailer\n<< /Size 6 /Root 1 0 R >>\nstartxref\n"+xref+"\n%%EOF");w.close();
    }
    private String pdf(String s){return s.replace("\\","\\\\").replace("(","\\(").replace(")","\\)").replace("á","a").replace("é","e").replace("í","i").replace("ó","o").replace("ú","u").replace("ñ","n").replace("Á","A").replace("É","E").replace("Í","I").replace("Ó","O").replace("Ú","U").replace("Ñ","N");}
    private static final class ErrorEscritura extends RuntimeException { ErrorEscritura(IOException causa){super(causa);} }
}
