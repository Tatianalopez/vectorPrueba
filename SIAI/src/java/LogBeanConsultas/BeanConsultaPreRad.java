/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBeanConsultas;

import LogBean.BeanArchivos;
import LogBean.BeanSesion;
import LogBean.BeanTodero;
import Logic.LogAdministracion;
import Logic.LogConsulta;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.format.ScriptStyle;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author admin
 */
@ManagedBean(name = "MBConsultaPR")
@ViewScoped
@SessionScoped
public class BeanConsultaPreRad 
{

    /**
     * Creates a new instance of BeanConsulta
     */
    public BeanConsultaPreRad() {
    }

    //Variables de las Logicas
    LogConsulta Cons = new LogConsulta();
    LogAdministracion Adm = new LogAdministracion();
    ResultSet Dat = null;
    //Variables 
    String Query = "";
    Calendar Calendary = GregorianCalendar.getInstance();
    java.util.Date Fech = Calendary.getTime();
    //Variables para PreRadicacion
    private List<LogConsulta> ListaConsulPR = new ArrayList<>();
 
    
    public List<LogConsulta> getListaConsulRad() {
        return ListaConsulRad;
    }

    public void setListaConsulRad(List<LogConsulta> ListaConsulRad) {
        this.ListaConsulRad = ListaConsulRad;
    }
     private List<LogConsulta> ListaConsulRad = new ArrayList<>();
    private List ListProEntPr = null;//Variable para la seleccion de informacion del producto Entidad
    private List SelectListProEntPr = null;//Variable para la muestra de la seleccion de informacion del producto Entidad
    private List ListEstadoPr = null;// Variable para la seleccion de informacion del estado del avaluo
    private List SelectListEstadoPr = null;// Variable para la seleccion de informacion del estado del avaluo
    private List ListEntidad = null;
    // -- Para los rangos de fecha de registro y de preradicacion
    private String OpcionConsul = "";
    private boolean OpcFechaRegistro;

    
    
    
    
    private Date FechaRegisIni;
    private Date FechaRegisFin;
    private Date FechaPreRadIni;
    private Date FechaPreRadFin;
    private String RemitidoX;
    private String NombreCliente;
    private String Cod_Pr;

    public boolean isOpcFechaPreRad() {
        return OpcFechaPreRad;
    }

    public void setOpcFechaPreRad(boolean OpcFechaPreRad) {
        this.OpcFechaPreRad = OpcFechaPreRad;
    }
    private boolean  OpcFechaPreRad;
    

    //GET Y SETS
    //VARIABLES DE LOS BEANS
    //VARIABLES DE BEAN DE SESION
    @ManagedProperty("#{MBSesion}")
    private BeanSesion mBSesion;

    public BeanSesion getmBSesion() {
        return mBSesion;
    }

    public void setmBSesion(BeanSesion mBSesion) {
        this.mBSesion = mBSesion;
    }

    //VARIABLES DE BEAN DE ARCHIVOS
    @ManagedProperty("#{MBArchivos}")
    private BeanArchivos mBArchivos;

    public BeanArchivos getmBArchivos() {
        return mBArchivos;
    }

    public void setmBArchivos(BeanArchivos mBArchivos) {
        this.mBArchivos = mBArchivos;
    }

    /**
     * Variable tipo BeanTodero para traer los atributos y metodos de el
     * ManagedBean BeanTodero.java
     *
     *
     * @see BeanTodero.java
     */
    @ManagedProperty("#{MBTodero}")
    private BeanTodero mbTodero;

    public BeanTodero getMbTodero() {
        return mbTodero;
    }

    public void setMbTodero(BeanTodero mbTodero) {
        this.mbTodero = mbTodero;
    }

    //GET Y SET PreRadicacion
    public LogConsulta getCons() {
        return Cons;
    }

    public void setCons(LogConsulta Cons) {
        this.Cons = Cons;
    }

    public List<LogConsulta> getListaConsulPR() {
        return ListaConsulPR;
    }

    public void setListaConsulPR(List<LogConsulta> ListaConsulPR) {
        this.ListaConsulPR = ListaConsulPR;
    }

    public List getListProEntPr() {
        return ListProEntPr;
    }

    public void setListProEntPr(List ListProEntPr) {
        this.ListProEntPr = ListProEntPr;
    }

    public List getSelectListProEntPr() {
        return SelectListProEntPr;
    }

    public void setSelectListProEntPr(List SelectListProEntPr) {
        this.SelectListProEntPr = SelectListProEntPr;
    }

    public List getListEstadoPr() {
        return ListEstadoPr;
    }

    public void setListEstadoPr(List ListEstadoPr) {
        this.ListEstadoPr = ListEstadoPr;
    }

    public List getSelectListEstadoPr() {
        return SelectListEstadoPr;
    }

    public void setSelectListEstadoPr(List SelectListEstadoPr) {
        this.SelectListEstadoPr = SelectListEstadoPr;
    }

    public List getListEntidad() {
        return ListEntidad;
    }

    public void setListEntidad(List ListEntidad) {
        this.ListEntidad = ListEntidad;
    }

    public String getOpcionConsul() {
        return OpcionConsul;
    }

    public void setOpcionConsul(String OpcionConsul) {
        this.OpcionConsul = OpcionConsul;
    }

    public boolean isOpcFechaRegistro() {
        return OpcFechaRegistro;
    }

    public void setOpcFechaRegistro(boolean OpcFechaRegistro) {
        this.OpcFechaRegistro = OpcFechaRegistro;
    }

  

    public Date getFechaRegisIni() {
        return FechaRegisIni;
    }

    public void setFechaRegisIni(Date FechaRegisIni) {
        this.FechaRegisIni = FechaRegisIni;
    }

    public Date getFechaRegisFin() {
        return FechaRegisFin;
    }

    public void setFechaRegisFin(Date FechaRegisFin) {
        this.FechaRegisFin = FechaRegisFin;
    }

    public Date getFechaPreRadIni() {
        return FechaPreRadIni;
    }

    public void setFechaPreRadIni(Date FechaPreRadIni) {
        this.FechaPreRadIni = FechaPreRadIni;
    }

    public Date getFechaPreRadFin() {
        return FechaPreRadFin;
    }

    public void setFechaPreRadFin(Date FechaPreRadFin) {
        this.FechaPreRadFin = FechaPreRadFin;
    }

    public String getRemitidoX() {
        return RemitidoX;
    }

    public void setRemitidoX(String RemitidoX) {
        this.RemitidoX = RemitidoX;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    //GCH - AGO2016
    public String getCod_Pr() {
        return Cod_Pr;
    }

    public void setCod_Pr(String Cod_Pr) {
        this.Cod_Pr = Cod_Pr;
    }
    
    
    //METODOS
    //METODOS PARA PRERADICACION
    public void validaInfoPreRadica() throws ParseException {
        switch (this.OpcionConsul) {
            case "Esp":// Para el caso de que sea una preradicacion especifica
                    
                    //GCH - AGO2016
                
                    Cons = new LogConsulta();
                    Query = " PR.Cod_PreRadica = '" + Integer.parseInt(this.Cod_Pr) + "' ";
                    ListaConsulPR = new ArrayList<>();
                    ListaConsulPR = Cons.ConsultaPreRad(2, Query);
                
                break;
            case "Gen":// Para el caso de que sea una consulta general
                int Paso = 2;
                String ItemFecReg = "NO",
                 ItemFecPR = "NO",
                 ItemProEnt = "NO",
                 ItemEst = "NO",
                 ItemRemX = "NO";
                Query = "";
                if (OpcFechaRegistro == true) {
                    //Para la validacion de la fecha de Registro
                    if (FechaRegisIni == null || FechaRegisFin == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de registro");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if ((this.FechaRegisFin.before(this.FechaRegisIni))) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecReg = "OK";
                        }
                    }
                }

                if (OpcFechaPreRad == true) {
                    //Para la validacion de los rangos de fecha de preradicacion
                    if (FechaPreRadIni == null || this.FechaPreRadFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha PreRadica");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaPreRadFin.before(FechaPreRadIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecPR = "OK";
                        }
                    }
                }

                if (this.ListProEntPr.size() > 0) {
                    Paso = 1;
                    ItemProEnt = "OK";
                }

                if (this.ListEstadoPr.size() > 0) {
                    Paso = 1;
                    ItemEst = "OK";
                }
                //Genera la validacion del remitente
                if (!"".equals(this.RemitidoX)) 
                {
                    switch (RemitidoX) {
                        case "Entidad":
                            if (ListEntidad.size() <= 0) {
                                mbTodero.setMens("No ha seleccionado ninguna entidad");
                                mbTodero.warn();
                                Paso = 0;
                            } else {
                                Paso = 1;
                                ItemRemX = "OK";
                            }
                            break;
                        case "Cliente":
                            if ("".equals(this.NombreCliente)) {
                                mbTodero.setMens("No ha ingresado algo dentro del remitente de cliente");
                                mbTodero.warn();
                                Paso = 0;
                            } else {
                                Paso = 1;
                                ItemRemX = "OK";
                            }
                            break;
                    }
                }

                //Estructura la informacion de los filtros que se encontraran dentro de la consulta
                if (Paso == 1) {
                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                    //Fechas de Registro
                    if ("OK".equals(ItemFecReg)) {
                        Query = "PR.Fecha_Registro between '" + Format.format(this.FechaRegisIni) + "' and  date_add('" + Format.format(this.FechaRegisFin) + "',interval 1 day)  ";
                    }
                    //Fechas de PreRadica
                    if ("OK".equals(ItemFecPR)) {
                        if ("".equals(Query)) {
                            Query = " PR.Fecha_PreRadica between '" + Format.format(this.FechaPreRadIni) + "'  and date_add('" + Format.format(this.FechaPreRadFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and PR.Fecha_PreRadica between '" + Format.format(this.FechaPreRadIni) + "'  and date_add('" + Format.format(this.FechaPreRadFin) + "',interval 1 day)";
                        }
                    }
                    //Producto Entidad
                    String Val = "";
                    if ("OK".equals(ItemProEnt)) {
                        for (int i = 0; i < ListProEntPr.size(); i++) {
                            if (i == 0) {
                                Val = ListProEntPr.get(i).toString();
                            } else {
                                Val = Val + "," + ListProEntPr.get(i).toString();
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " PE.Cod_ProEnt in (" + Val + ")";
                        } else {
                            Query = Query + " and PE.Cod_ProEnt in (" + Val + ")";
                        }
                    }
                    //Estado PreRadicacion
                    Val = "";
                    if ("OK".equals(ItemEst)) {
                        for (int i = 0; i < ListEstadoPr.size(); i++) {
                            if (i == 0) {
                                Val = " '" + ListEstadoPr.get(i).toString() + "'";
                            } else {
                                Val = Val + "," + " '" + ListEstadoPr.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " PR.Estado_PreRadica in (" + Val + ")";
                        } else {
                            Query = Query + " and PR.Estado_PreRadica in (" + Val + ")";
                        }
                    }

                    //Remitido por (Cliente or Entidad)
                    Val = "";
                    if (!"".equals(RemitidoX))
                    {
                        if ("".equals(Query)) {
                            Query = " PR.Solicitante_PreRadica = '" + this.RemitidoX + "' ";
                        } else {
                            Query = Query + " and  PR.Solicitante_PreRadica = '" + this.RemitidoX + "' ";
                        }
                        switch (RemitidoX) {
                            case "Cliente"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " C.Nombre_Cliente like '%" + this.NombreCliente + "%'  ";
                                } else {
                                    Query = Query + " and  C.Nombre_Cliente like '%" + this.NombreCliente + "%'  ";
                                }
                                break;
                            case "Entidad":// Llenado de informacion para el cliente que remite la solicitud
                                for (int i = 0; i < ListEntidad.size(); i++) {
                                    if (i == 0) 
                                    {
                                        Val = " '" + ListEntidad.get(i).toString() + "'";
                                    } else {
                                        Val = Val + "," + " '" + ListEntidad.get(i).toString() + "' ";
                                    }
                                }
                                if ("".equals(Query)) {
                                    Query = " E.Cod_Entidad in (" + Val + ")";
                                } else {
                                    Query = Query + " and E.Cod_Entidad in (" + Val + ")";
                                }
                                break;
                        }
                    }
                    Cons = new LogConsulta();
                    ListaConsulPR = new ArrayList<>();
                    ListaConsulPR = Cons.ConsultaPreRad(2, Query);
                }// FINALIZACION DEL PASO=1
                else if (Paso == 2) {
                    Cons = new LogConsulta();
                    Query = "";
                    ListaConsulPR = new ArrayList<>();
                    ListaConsulPR = Cons.ConsultaPreRad(1, Query);
                }

                break;
        }
    }

//Metodo para realizar el exportado en el area de preradicacion
    public void exportConsul() {
         
        //GCH 02ENE2017
        int tipo;
        
        if ("".equals(Query) || Query == null){
            tipo =1;
        } else {
            tipo = 2;
        }     

        /*INICIA EL PROCESO DE LA CREACION DEL ARCHIVO DE EXCEL*/
        File Rutas = new File(File.separator + "DBARCHIVOS" + File.separator + "ConsultaPreRadica.xls");
        int Row = 0;
        //Escribir el tipo de formato de fuente que va a tener el archivo de Excel
        WritableFont ExcFontHeads = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD,false,UnderlineStyle.SINGLE,Colour.SKY_BLUE,ScriptStyle.SUPERSCRIPT);
        WritableCellFormat CellFormatHeads = new WritableCellFormat(ExcFontHeads);
        
        WritableFont ExcFont = new WritableFont(WritableFont.ARIAL, 12, WritableFont.NO_BOLD);
        WritableCellFormat CellFormat = new WritableCellFormat(ExcFont);
        //Presentacion o forma grafica de la hoja de calculo
        WritableSheet ExcSheet = null;
        WritableWorkbook WorkBook = null;
        //Informacion regional de la hoja de calculo que se va a crear
        WorkbookSettings WbSettings = new WorkbookSettings();
        WbSettings.setLocale(new Locale("es", "CO"));

        if (Rutas.exists()) {//Valida si el directorio del archivo existe, lo borra y lo crea nuevamente
            Rutas.delete();
            try {
                Rutas.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(BeanArchivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        try {
            WorkBook = Workbook.createWorkbook(Rutas, WbSettings);
            //Hoja con el nombre del Cual se le da a asignar
            WorkBook.createSheet("ConsultaPreRadica", 0);
            ExcSheet = WorkBook.getSheet(0);
        } catch (IOException ex) {
            System.err.println("Error : " + ex.getClass() + " .exportConsul() causado por : " + ex.getMessage());//GCH 02ENE2017
        }

        try {
            Dat = Cons.ConsultPreRadicacionGen(tipo,Query);

            //GCH 02ENE2017
            Label CodPreRad;
            Label TipoAva;
            Label FechaReg;
            Label FechaPreRadic;
            Label FechaSol;
            Label SolicitantPR;
            Label NumH;
            Label EnviarA;
            Label Estado;
            Label EstudioPR;
            Label Cotizacion;
            Label ProEnt;
            Label TipProEnt;
            Label Ubica;
            Label Solic;
            Label Entid;
            
            while (Dat.next()) {

                try {//GCH 02ENE2017
                    
                    if (Row == 0) {
                        CodPreRad = new Label(0, Row, "N* PreRadicacion", CellFormatHeads);
                        TipoAva = new Label(1, Row, "Tipo Avaluo", CellFormatHeads);
                        FechaReg = new Label(2, Row, "Fecha Registro", CellFormatHeads);
                        FechaPreRadic = new Label(3, Row, "Fecha PreRadicado", CellFormatHeads);
                        FechaSol = new Label(4, Row, "Fecha Solicitud", CellFormatHeads);
                        SolicitantPR = new Label(5, Row, "Solicitante", CellFormatHeads);
                        NumH = new Label(6, Row, "N* Hojas", CellFormatHeads);
                        EnviarA = new Label(7, Row, "EnviarA", CellFormatHeads);
                        Estado = new Label(8, Row, "Estado", CellFormatHeads);
                        EstudioPR = new Label(9, Row, "Estudio Pre Radicación", CellFormatHeads);
                        Cotizacion = new Label(10, Row, "Cotizacion", CellFormatHeads);
                        ProEnt = new Label(11, Row, "Producto Entidad", CellFormatHeads);
                        TipProEnt = new Label(12, Row, "Tipo Producto Entidad", CellFormatHeads);
                        Ubica = new Label(13, Row, "Departamento-Ciudad", CellFormatHeads);
                        Solic = new Label(14, Row, "Solicitante", CellFormatHeads);
                        Entid = new Label(15, Row, "Entidad", CellFormatHeads);
                        
                        Row++;
                        
                        Dat.beforeFirst();

                    }  else { //GCH 02ENE2017
                        
                        CodPreRad = new Label(0, Row, Dat.getString("Cod_PreRadica"), CellFormat);
                        TipoAva = new Label(1, Row, Dat.getString("TipoAvaluo"), CellFormat);
                        FechaReg = new Label(2, Row, Dat.getString("Fecha_Registro"), CellFormat);
                        FechaPreRadic = new Label(3, Row, Dat.getString("FechaPreRadica"), CellFormat);
                        FechaSol = new Label(4, Row, Dat.getString("FechaSolicitud"), CellFormat);
                        SolicitantPR = new Label(5, Row, Dat.getString("Solicitante_PreRadica"), CellFormat);
                        NumH = new Label(6, Row, Dat.getString("NumHojas"), CellFormat);
                        EnviarA = new Label(7, Row, Dat.getString("EnviarA"), CellFormat);
                        Estado = new Label(8, Row, Dat.getString("EstadoPreRadica"), CellFormat);
                        EstudioPR = new Label(9, Row, Dat.getString("EstudioPreRadica"), CellFormat);
                        Cotizacion = new Label(10, Row, Dat.getString("CotizacionPreRadica"), CellFormat);
                        ProEnt = new Label(11, Row, Dat.getString("Nombre_ProEnt"), CellFormat);
                        TipProEnt = new Label(12, Row, Dat.getString("Nombre_TipProEnt"), CellFormat);
                        Ubica = new Label(13, Row, Dat.getString("Ubicacion"), CellFormat);
                        Solic = new Label(14, Row, Dat.getString("Solicitante"), CellFormat);
                        Entid = new Label(15, Row, Dat.getString("Entidad"), CellFormat);

                        Row++;
                    }
                    
                    //Escribe en archivo el contenido de cada celda para la fila
                    ExcSheet.addCell(CodPreRad);
                    ExcSheet.addCell(TipoAva);
                    ExcSheet.addCell(FechaReg);
                    ExcSheet.addCell(FechaPreRadic);
                    ExcSheet.addCell(FechaSol);
                    ExcSheet.addCell(SolicitantPR);
                    ExcSheet.addCell(NumH);
                    ExcSheet.addCell(EnviarA);
                    ExcSheet.addCell(Estado);
                    ExcSheet.addCell(EstudioPR);
                    ExcSheet.addCell(Cotizacion);
                    ExcSheet.addCell(ProEnt);
                    ExcSheet.addCell(TipProEnt);
                    ExcSheet.addCell(Ubica);
                    ExcSheet.addCell(Solic);
                    ExcSheet.addCell(Entid);
                    
                } catch (WriteException ex) {
                    System.err.println("Error : " + ex.getClass() + " .exportConsul() causado por : " + ex.getMessage());//GCH 02ENE2017
                }
            }
           // Dat.close();
            Conexion.Conexion.CloseCon();
        } catch (SQLException e ) {
            System.err.println("Error : " + e.getClass() + " .exportConsul() causado por : " + e.getMessage());//GCH 02ENE2017
        }
        //Escribe el archivo excel en disco
        try {

            WorkBook.write();
            WorkBook.close();
            //Genero la descarga del archivo para el usuario
            mBArchivos.DescarArcIndi(String.valueOf(Rutas), "ConsultaPreRadica", "xls");
            Rutas.delete();
        } catch (IOException | WriteException ex) {
            System.err.println("Error : " + ex.getClass() + " .exportConsul() causado por : " + ex.getMessage());//GCH 02ENE2017
        }
    }


    //Metodo para realizar la limpieza del bean y recargar nuevamente las  variables
    public void clearBean() {
        this.ListaConsulPR = new ArrayList<>();
        this.ListProEntPr = new ArrayList<>();
        this.ListEstadoPr = new ArrayList<>();
        this.ListEntidad = new ArrayList<>();
        this.OpcionConsul = "";
        this.OpcFechaRegistro = false;
        this.OpcFechaPreRad = false;
        this.FechaPreRadFin = null;
        this.FechaPreRadIni = null;
        this.FechaRegisFin = null;
        this.FechaRegisIni = null;
        this.RemitidoX = "";
        this.NombreCliente = "";
    }

    /**
     * Metodo tipo ajax que permite cargar los tipos de entidades que fueron
     * seleccionados en el formulario de consultas de pre-radicacion
     *
     * @author Jhojan Stiven Rodriguez
     * @since 13-11-15
     *
     *
     */
    public void onCargarProdEntiSelecc() {
        try {
            SelectListProEntPr = new ArrayList<>();
            for (int j = 0; j <= this.ListProEntPr.size() - 1; j++) {
                String codigo = ListProEntPr.get(j).toString();
                Adm.setCodProEnt(Integer.parseInt(codigo));
                String OPciones = Adm.ConsulNombreProdEnti();
                SelectListProEntPr.add(new SelectItem(codigo, OPciones));
            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

    /**
     * Metodo tipo ajax que permite cargar los estados que fueron seleccionados
     * en el formulario de consultas de pre-radicacion
     *
     * @author Jhojan Stiven Rodriguez
     * @since 13-11-15
     *
     *
     */
    public void onCargarEstadosSelecc()
    {
        String OPciones = "";
        try {
            SelectListEstadoPr = new ArrayList<>();
            for (int j = 0; j <= this.ListEstadoPr.size() - 1; j++) {
                String codigo = ListEstadoPr.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "P":
                            OPciones = "PENDIENTE POR RADICAR";
                            break;
                        case "A":
                            OPciones = "ASIGNADO";
                            break;
                        case "I":
                            OPciones = "IMPEDIDO";
                            break;
                        case "N":
                            OPciones = "ANULADO";
                            break;
                        case "C":
                            OPciones = "RADICADO";
                            break;
                    }
                }
                SelectListEstadoPr.add(new SelectItem(codigo, OPciones));

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

}
