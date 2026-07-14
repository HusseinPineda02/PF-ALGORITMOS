package modelo;

public final class CentroAutorizado {
    private final String codigo, resolucion, institucion, area, subarea, disciplina, investigacion, desarrollo, innovacion;
    public CentroAutorizado(String codigo, String resolucion, String institucion, String area, String subarea, String disciplina, String investigacion, String desarrollo, String innovacion) {
        this.codigo=l(codigo); this.resolucion=l(resolucion); this.institucion=l(institucion); this.area=l(area); this.subarea=l(subarea); this.disciplina=l(disciplina); this.investigacion=l(investigacion); this.desarrollo=l(desarrollo); this.innovacion=l(innovacion);
    }
    public static CentroAutorizado desdeCampos(String[] c) { return new CentroAutorizado(v(c,0),v(c,1),v(c,2),v(c,3),v(c,4),v(c,5),v(c,7),v(c,8),v(c,9)); }
    public String getCodigo(){return codigo;} public String getInstitucion(){return institucion;} public String getArea(){return area;} public String getSubarea(){return subarea;} public String getDisciplina(){return disciplina;}
    public String getInvestigacion(){return investigacion;} public String getDesarrollo(){return desarrollo;} public String getInnovacion(){return innovacion;}
    private static String v(String[] a,int i){return i<a.length?a[i]:"";} private static String l(String v){return v==null?"":v.trim();}
}
