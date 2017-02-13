/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package LogBeanConsultas;

import LogBean.BeanArchivos;
import LogBean.BeanAvaluo;
import LogBean.BeanPerito;
import Logic.LogAdministracion;
import Logic.LogConsulta;
import Logic.LogPerito; //GCH 30DIC2016
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.model.SelectItem;
import LogBean.BeanTodero;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
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
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
/**
 *
 * @author admin
 */
@ManagedBean (name = "MBConsultaRad")
@ViewScoped
@SessionScoped



   

public class BeanConsultaRad {

    public BeanConsultaRad() {
        Parametros();
    }
    
    private int EnvioInformacion = 0; 
    private String opcionRadioEstados;
    private ResultSet DatObser;
    private boolean preRadicacionModificacion;
    private boolean preRadicacionRegistro;
    private ResultSet DatCampoEnviarA;
    private String EnviarALabel;
    private Date FechaRegisIni;
    private Date FechaRegisFin;
    private boolean opcFechaRegistro;
    String fecha_actual;
    private String OpcionConsul1  = "";
   private List<String> Parametros;
    
    private String NombrePerito;
    private int codPerito; //GCH 30DIC2016
    private String cod_Ava_Cons; //GCH 02ENE2017
    private String IdentificacionPerito;
    private String OpcionBusqPerito;
    private boolean Ubicacion = false;
     
     private List SelectListTipoAval = null;//Variable para la muestra de la seleccion de informacion del producto Entidad
    private String NombreCliente;
    private String IdentificacionCliente;
    private String OpcionBusqCliente;
    private String NombreEntidad;
    private String IdentificacionIdentidad;
    private List ListEntidad = null;
   private List ListTipoAvaluo = null;
   
   private boolean  OpcFechaCitas;
    private Date FechaCitaIni;
    private Date FechaCitaFin;
    private String RemitidoX;
    private  String OPciones = "";
    
    //Variables de las Logicas
    LogConsulta Cons = new LogConsulta();
    LogAdministracion Adm = new LogAdministracion();
    LogPerito Per = new LogPerito();//GCH 30DIC2016
    private List<LogConsulta> ListaConsulR2 = new ArrayList<>();//GCH 02ENE2017
    
    ResultSet Dat = null;
    //Variables 
    String Query = "";
    Calendar Calendary = GregorianCalendar.getInstance();
    java.util.Date Fech = Calendary.getTime();

    
    public String getOpcPerito() {
        return OpcPerito;
    }

    public void setOpcPerito(String OpcPerito) {
        this.OpcPerito = OpcPerito;
    }
    private String OpcPerito = "";

    
    public int getCodPerito() {//GCH 30DIC2016
        return codPerito;
    }

    public void setCodPerito(int codPerito) {//GCH 30DIC2016
        this.codPerito = codPerito;
    }

    public LogPerito getPer() {//GCH 30DIC2016
        return Per;
    }

    public void setPer(LogPerito Per) {//GCH 30DIC2016
        this.Per = Per;
    }

    public String getCod_Ava_Cons() {//GCH 02ENE2017
        return cod_Ava_Cons;
    }

    public void setCod_Ava_Cons(String cod_Ava_Cons) {//GCH 02ENE2017
        this.cod_Ava_Cons = cod_Ava_Cons;
    }

    public List<LogConsulta> getListaConsulR2() {//GCH 02ENE2017
        return ListaConsulR2;
    }

    public void setListaConsulR2(List<LogConsulta> ListaConsulR2) {//GCH 02ENE2017
        this.ListaConsulR2 = ListaConsulR2;
    }
      

    public boolean isUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(boolean Ubicacion) {
        this.Ubicacion = Ubicacion;
    }
    
  
    //Variables para PreRadicacion
    public List<LogConsulta> getListaConsulR() {
        return ListaConsulR;
    }

    public void setListaConsulR(List<LogConsulta> ListaConsulR) {
        this.ListaConsulR = ListaConsulR;
    }
    private List<LogConsulta> ListaConsulR = new ArrayList<>();

    public List getListProEntPr() {
        return ListProEntPr;
    }

    public void setListProEntPr(List ListProEntPr) {
        this.ListProEntPr = ListProEntPr;
    }

    private List ListProEntPr = null;//Variable para la seleccion de informacion del producto Entidad

    public List getListTipoAval() {
        return ListTipoAval;
    }

    public void setListTipoAval(List ListTipoAval) {
        this.ListTipoAval = ListTipoAval;
    }
      private List ListTipoAval = null;//Variable para la seleccion de informacion del producto Entidad

    public List getListEnviarA() {
        return ListEnviarA;
    }

    public void setListEnviarA(List ListEnviarA) {
        this.ListEnviarA = ListEnviarA;
    }

    private List ListEnviarA = null;
    
    public List getSelectListProEntPr() {
        return SelectListProEntPr;
    }

    public void setSelectListProEntPr(List SelectListProEntPr) {
        this.SelectListProEntPr = SelectListProEntPr;
    }
    private List SelectListProEntPr = null;//Variable para la muestra de la seleccion de informacion del producto Entidad

    public List getSelectListTipoAval() {
        return SelectListTipoAval;
    }

    public void setSelectListTipoAval(List SelectListTipoAval) {
        this.SelectListTipoAval = SelectListTipoAval;
    }
     

    public List getSelectTipoAv() {
        return SelectTipoAv;
    }

    public void setSelectTipoAv(List SelectTipoAv) {
        this.SelectTipoAv = SelectTipoAv;
    }

private List SelectTipoAv = null;


   
   public boolean isOpcFechaCita() {
        return OpcFechaCita;
    }

    public void setOpcFechaCita(boolean OpcFechaCita) {
        this.OpcFechaCita = OpcFechaCita;
    }

    public boolean isOpcFechaPerito() {
        return OpcFechaPerito;
    }

    public void setOpcFechaPerito(boolean OpcFechaPerito) {
        this.OpcFechaPerito = OpcFechaPerito;
    }
    private boolean  OpcFechaCita;
    private boolean  OpcFechaPerito;

    public Date getFechaPeritoIni() {
        return FechaPeritoIni;
    }

    public void setFechaPeritoIni(Date FechaPeritoIni) {
        this.FechaPeritoIni = FechaPeritoIni;
    }

    public Date getFechaPeritoFin() {
        return FechaPeritoFin;
    }

    public void setFechaPeritoFin(Date FechaPeritoFin) {
        this.FechaPeritoFin = FechaPeritoFin;
    }
    private Date FechaPeritoIni;
    private Date FechaPeritoFin;
   
    public List getListTipoAvaluo() {
        return ListTipoAvaluo;
    }

    public void setListTipoAvaluo(List ListTipoAvaluo) {
        this.ListTipoAvaluo = ListTipoAvaluo;
    }

    public boolean isOpcFechaCitas() {
        return OpcFechaCitas;
    }

    public void setOpcFechaCitas(boolean OpcFechaCitas) {
        this.OpcFechaCitas = OpcFechaCitas;
    }

    public Date getFechaCitaIni() {
        return FechaCitaIni;
    }

    public void setFechaCitaIni(Date FechaCitaIni) {
        this.FechaCitaIni = FechaCitaIni;
    }

    public Date getFechaCitaFin() {
        return FechaCitaFin;
    }

    public void setFechaCitaFin(Date FechaCitaFin) {
        this.FechaCitaFin = FechaCitaFin;
    }
     
    public List getListEntidad() {
        return ListEntidad;
    }

    public void setListEntidad(List ListEntidad) {
        this.ListEntidad = ListEntidad;
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
    
   

    public String getFecha_actual() {
        return fecha_actual;
    }

    public void setFecha_actual(String fecha_actual) {
        this.fecha_actual = fecha_actual;
    }
 

 

    public boolean isOpcFechaRegistro() {
        return opcFechaRegistro;
    }

    public void setOpcFechaRegistro(boolean opcFechaRegistro) {
        this.opcFechaRegistro = opcFechaRegistro;
    }

    public int getEnvioInformacion() {
        return EnvioInformacion;
    }

    public void setEnvioInformacion(int EnvioInformacion) {
        this.EnvioInformacion = EnvioInformacion;
    }
    
        /**
     * Creates a new instance of MBConsultaRad
     */

    /**
     * Creates a new instance of BeanConsultaRad
     */
    @ManagedProperty("#{MBTodero}")
    private BeanTodero mbTodero;

    public BeanTodero getMbTodero() {
        return mbTodero;
    }

    public void setMbTodero(BeanTodero mbTodero) {
        this.mbTodero = mbTodero;
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
    

    public String getNombreEntidad() {
        return NombreEntidad;
    }

    public void setNombreEntidad(String NombreEntidad) {
        this.NombreEntidad = NombreEntidad;
    }

    public String getIdentificacionIdentidad() {
        return IdentificacionIdentidad;
    }

    public void setIdentificacionIdentidad(String IdentificacionIdentidad) {
        this.IdentificacionIdentidad = IdentificacionIdentidad;
    }
    
   
    public String getOpcionBusqEntidad() {
        return OpcionBusqEntidad;
    }

    public void setOpcionBusqEntidad(String OpcionBusqEntidad) {
        this.OpcionBusqEntidad = OpcionBusqEntidad;
    }
    private String OpcionBusqEntidad;
    private boolean OpcCliente;

    public String getNombrePerito() {
        return NombrePerito;
    }

    public void setNombrePerito(String NombrePerito) {
        this.NombrePerito = NombrePerito;
    }

    public String getIdentificacionPerito() {
        return IdentificacionPerito;
    }

    public void setIdentificacionPerito(String IdentificacionPerito) {
        this.IdentificacionPerito = IdentificacionPerito;
    }

    public String getOpcionBusqPerito() {
        return OpcionBusqPerito;
    }

    public void setOpcionBusqPerito(String OpcionBusqPerito) {
        this.OpcionBusqPerito = OpcionBusqPerito;
    }

    public String getOpcionConsul1() {
        return OpcionConsul1;
    }

    public void setOpcionConsul1(String OpcionConsul1) {
        this.OpcionConsul1 = OpcionConsul1;
    }
    
 

    // -- Para los rangos de fecha de registro y de preradicacion
   
   

    public List getSelectListOpcion() {
        return SelectListOpcion;
    }

    public void setSelectListOpcion(List SelectListOpcion) {
        this.SelectListOpcion = SelectListOpcion;
    }

    private List SelectListOpcion = null;// Variable para la seleccion de informacion del las opciones de Tipo de busqueda por Cliente

    public String getOpcionBusqCliente() {
        return OpcionBusqCliente;
    }

    public void setOpcionBusqCliente(String OpcionBusqCliente) {
        this.OpcionBusqCliente = OpcionBusqCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getIdentificacionCliente() {
        return IdentificacionCliente;
    }

    public void setIdentificacionCliente(String IdentificacionCliente) {
        this.IdentificacionCliente = IdentificacionCliente;
    }
     
    
    
  
    
  //--Gets y Sets de Consulta Radicacion
    
     public boolean isOpcCliente() {
        return OpcCliente;
    }

    public void setOpcCliente(boolean OpcCliente) {
        this.OpcCliente = OpcCliente;
    }

   
    public String getOPciones() {
        return OPciones;
    }

    public void setOPciones(String OPciones) {
        this.OPciones = OPciones;
    }

    public String getRemitidoX() {
        return RemitidoX;
    }

    public void setRemitidoX(String RemitidoX) {
        this.RemitidoX = RemitidoX;
    }
    
 
    public void onCargarOpcSelecc() {
       
        try {
            SelectListOpcion = new ArrayList<>();
            for (int j = 0; j <= this.SelectListOpcion.size() - 1; j++) {
                String codigo = SelectListOpcion.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "N":
                            OPciones = "NOMBRE";
                            break;
                        case "I":
                            OPciones = "IDENTIFICACION";
                            break;
                      
                    }
                }
                SelectListOpcion.add(new SelectItem(codigo, OPciones));
           }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

    public List<String> getParametros() {
        return Parametros;
    }

    public void setParametros(List<String> Parametros) {
        this.Parametros = Parametros;
    }


  
  
     
    public String[] getSelcPrmtros() {
        return selcPrmtros;
    }

    public void setSelcPrmtros(String[] selcPrmtros) {
        this.selcPrmtros = selcPrmtros;
    }
    
     private String[] selcPrmtros;
    public void Parametros() 
    {
        
        Parametros = new ArrayList<String>();
        Parametros.add("No Avaluo");
        Parametros.add("No Bien");
        Parametros.add("Producto Entidad");
        Parametros.add("Fecha Solicitud");
        Parametros.add("Fecha Registro");
        Parametros.add("Fecha Radicacion");
        Parametros.add("Estado");
        Parametros.add("Proceso Actual");
        Parametros.add("Ubicacion");
        
        Parametros.add("Fecha Cita");
        Parametros.add("Fecha Entrega Informe");
        
    }

    public List getSelectListEnviarA() {
        return SelectListEnviarA;
    }

    public void setSelectListEnviarA(List SelectListEnviarA) {
        this.SelectListEnviarA = SelectListEnviarA;
    }
    
    private List SelectListEnviarA = null;
    
    
    public void onCargarEnvirA() {
        String OPciones = "";
        try {
            SelectListEnviarA = new ArrayList<>();
            for (int j = 0; j <= this.ListEnviarA.size() - 1; j++) {
                String codigo = ListEnviarA.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "BANCO":
                            OPciones = "BANCO";
                            break;
                        case "CLIENTE":
                            OPciones = "CLIENTE";
                            break;
                        case "OFICINA":
                            OPciones = "OFICINA ISA ";
                            break;
                        case "OTRO":
                            OPciones = "OTRO";
                             case "Seleccione":
                            OPciones = "";
                            break;
                 
                    
                    }
                }
               SelectListEnviarA.add(new SelectItem(codigo, OPciones));

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }
    
      public void clearBean() 
      {
             this.ListaConsulR = new ArrayList<>();
        this.ListProEntPr = new ArrayList<>();
    
        this.ListEntidad = new ArrayList<>();
        this.OpcionConsul1 = "";
        this.opcFechaRegistro = false;
        this.FechaRegisFin = null;
        this.FechaRegisIni = null;
        this.RemitidoX = "";
        this.NombreCliente = "";
        
        
        
      }
      
      LogConsulta Consulta = new  LogConsulta();
      private List ListaConsulta; 
      
    public void exportConsulRad(){
            
         //GCH 02ENE2017
        int tipo;

        if ("".equals(Query) || Query == null){
            tipo=1;
        } else {
            tipo=2;
        }         
             
        /*INICIA EL PROCESO DE LA CREACION DEL ARCHIVO DE EXCEL*/
        File Rutas = new File(File.separator + "DBARCHIVOS" + File.separator + "ConsultaRadica.xls");
        int Row = 0;
        //Escribir el tipo de formato de fuente que va a tener el archivo de Excel
        WritableFont ExcFontHeads = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD,false,UnderlineStyle.SINGLE,Colour.SKY_BLUE,ScriptStyle.SUPERSCRIPT);
        WritableCellFormat CellFormatHeads = new WritableCellFormat(ExcFontHeads);
        
        WritableFont ExcFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
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
            WorkBook.createSheet("ConsultaRadica", 0);
            ExcSheet = WorkBook.getSheet(0);
        } catch (IOException ex) {
            System.err.println("Error: " + ex.getClass() + " .exportConsulRad() causado por : " + ex.getMessage());//GCH 02ENE2017
        }

        
        try {
            
            //
            Dat = Cons.ConsultRadicacionGen(tipo,Query);

            //GCH 02ENE2017 
            Label NAvaluo;
            Label FechaSoliRad;
            Label FechaRad;
            Label Estad_Ava;
            Label ProEnt, Clientes, Entidades, RadicadorAsig, SeguimientoAsig, NBien, Resultado_Parametro, Ubica; 
            Label Asignacion_Perito, Perito, ValorAvaluo, Fecha_Registro, TiempoTransRegistro, Fecha_PreRadica;
            Label TiempoTranPreRad, TiempoTransRadica, EnviarA, VistoCita, FechaCita, FechaEntrega, TiempoTransEntrega;
            Label FechaJuridica, TiempoTransJuridica, FechaTecnica, TiempoTransTecnica, FechaImpresion, TiempoTranImpresion;
            
            while (Dat.next()) 
            {               
                try {
                    
                    if (Row == 0) {

                        //GCH 02ENE2017
                        NAvaluo = new Label(0, Row, "N* Avaluo", CellFormatHeads);
                        FechaSoliRad = new Label(1, Row, "Fecha Solicitud", CellFormatHeads);
                        FechaRad = new Label(2, Row, "Fecha Radicacion", CellFormatHeads);
                        ProEnt = new Label(3, Row, "Prod Entidad", CellFormatHeads); 
                        Clientes = new Label(4, Row, "Cliente", CellFormatHeads); 
                        Entidades = new Label(5, Row, "Entidad", CellFormatHeads);
                        RadicadorAsig = new Label(6, Row, "Analista Rad", CellFormatHeads);
                        SeguimientoAsig = new Label(7, Row, "Analista Seg", CellFormatHeads);
                        NBien = new Label(8, Row, "N Bien", CellFormatHeads);
                        Resultado_Parametro = new Label(9, Row, "Solicitado", CellFormatHeads);
                        Ubica = new Label(10, Row, "Ubicacion", CellFormatHeads);
                        Asignacion_Perito = new Label(11, Row, "F Asig Perito", CellFormatHeads);
                        Perito = new Label(12, Row, "Perito", CellFormatHeads);
                        ValorAvaluo = new Label(13, Row, "Vr Avaluo", CellFormatHeads);
                        Estad_Ava = new Label(14, Row, "Estado Avaluo", CellFormatHeads);
                        Fecha_Registro = new Label(15, Row, "Fecha Registro", CellFormatHeads);
                        TiempoTransRegistro = new Label(16, Row, "Tiempo Trans. Registro", CellFormatHeads);
                        Fecha_PreRadica = new Label(17, Row, "Fecha Pre-Rad", CellFormatHeads);
                        TiempoTranPreRad = new Label(18, Row, "Tiempo Trans. Pre-Rad", CellFormatHeads);
                        TiempoTransRadica = new Label(19, Row, "Tiempo Trans. Rad", CellFormatHeads);
                        EnviarA = new Label(20, Row, "Enviar A", CellFormatHeads);
                        VistoCita = new Label(21, Row, "Fecha Conf. Cita", CellFormatHeads);
                        FechaCita = new Label(22, Row, "Fecha Cita", CellFormatHeads);
                        FechaEntrega = new Label(23, Row, "Fecha Entrega", CellFormatHeads);
                        TiempoTransEntrega = new Label(24, Row, "Tiempo Trans. Ent", CellFormatHeads);
                        FechaJuridica = new Label(25, Row, "Fecha Rev. Juridica", CellFormatHeads);
                        TiempoTransJuridica = new Label(26, Row, "Tiempo Trans. RJ", CellFormatHeads);
                        FechaTecnica = new Label(27, Row, "Fecha Rev. Tecnica", CellFormatHeads);
                        TiempoTransTecnica = new Label(28, Row, "Tiempo Trans. RT", CellFormatHeads);
                        FechaImpresion = new Label(29, Row, "Fecha Impresion", CellFormatHeads);
                        TiempoTranImpresion = new Label(30, Row, "Tiempo Trans. Imp", CellFormatHeads);

                        Row++;
                        
                        Dat.beforeFirst();
                    
                    } else {

                        NAvaluo = new Label(0, Row, Dat.getString("Cod_Avaluo"), CellFormat);
                        FechaSoliRad = new Label(1, Row, Dat.getString("Fecha_Solicitud"), CellFormat);
                        FechaRad = new Label(2, Row, Dat.getString("Fecha_Radicado"), CellFormat);                  
                        ProEnt = new Label(3, Row, Dat.getString("ProEnt"), CellFormat); 
                        Clientes = new Label(4, Row, Dat.getString("Clientes"), CellFormat); 
                        Entidades = new Label(5, Row, Dat.getString("Entidades"), CellFormat);
                        RadicadorAsig = new Label(6, Row, Dat.getString("RadicadorAsig"), CellFormat);
                        SeguimientoAsig = new Label(7, Row, Dat.getString("SeguimientoAsig"), CellFormat);
                        NBien = new Label(8, Row, Dat.getString("NBien"), CellFormat);
                        Resultado_Parametro = new Label(9, Row, Dat.getString("Resultado_Parametro"), CellFormat);
                        Ubica = new Label(10, Row, Dat.getString("Ubicacion"), CellFormat);
                        Asignacion_Perito = new Label(11, Row, Dat.getString("Asignacion_Perito"), CellFormat);
                        Perito = new Label(12, Row, Dat.getString("Perito"), CellFormat);
                        ValorAvaluo = new Label(13, Row, Dat.getString("ValorAvaluo"), CellFormat);
                        Estad_Ava = new Label(14, Row, Dat.getString("Estado_Avaluo"), CellFormat);
                        Fecha_Registro = new Label(15, Row, Dat.getString("Fecha_Registro"), CellFormat);
                        TiempoTransRegistro = new Label(16, Row, Dat.getString("TiempoTransRegistro"), CellFormat);
                        Fecha_PreRadica = new Label(17, Row, Dat.getString("Fecha_PreRadica"), CellFormat);
                        TiempoTranPreRad = new Label(18, Row, Dat.getString("TiempoTranPreRad"), CellFormat);
                        TiempoTransRadica = new Label(19, Row, Dat.getString("TiempoTransRadica"), CellFormat);
                        EnviarA = new Label(20, Row, Dat.getString("EnviarA"), CellFormat);
                        VistoCita = new Label(21, Row, Dat.getString("VistoCita"), CellFormat);
                        FechaCita = new Label(22, Row, Dat.getString("FechaCita"), CellFormat);
                        FechaEntrega = new Label(23, Row, Dat.getString("FechaEntrega"), CellFormat);
                        TiempoTransEntrega = new Label(24, Row, Dat.getString("TiempoTransEntrega"), CellFormat);
                        FechaJuridica = new Label(25, Row, Dat.getString("FechaJuridica"), CellFormat);
                        TiempoTransJuridica = new Label(26, Row, Dat.getString("TiempoTransJuridica"), CellFormat);
                        FechaTecnica = new Label(27, Row, Dat.getString("FechaTecnica"), CellFormat);
                        TiempoTransTecnica = new Label(28, Row, Dat.getString("TiempoTransTecnica"), CellFormat);
                        FechaImpresion = new Label(29, Row, Dat.getString("FechaImpresion"), CellFormat);
                        TiempoTranImpresion = new Label(30, Row, Dat.getString("TiempoTranImpresion"), CellFormat);

                        Row++;           
                    }                
                
                    //GCH 02ENE2017 - escribe en archivo el contenido de las celdas por cada fila
                    ExcSheet.addCell(NAvaluo);
                    ExcSheet.addCell(FechaSoliRad);
                    ExcSheet.addCell(FechaRad);
                    ExcSheet.addCell(ProEnt); 
                    ExcSheet.addCell(Clientes); 
                    ExcSheet.addCell(Entidades);
                    ExcSheet.addCell(RadicadorAsig);
                    ExcSheet.addCell(SeguimientoAsig);
                    ExcSheet.addCell(NBien);
                    ExcSheet.addCell(Resultado_Parametro);
                    ExcSheet.addCell(Ubica);
                    ExcSheet.addCell(Asignacion_Perito);
                    ExcSheet.addCell(Perito);
                    ExcSheet.addCell(ValorAvaluo);
                    ExcSheet.addCell(Estad_Ava);
                    ExcSheet.addCell(Fecha_Registro);
                    ExcSheet.addCell(TiempoTransRegistro);
                    ExcSheet.addCell(Fecha_PreRadica);
                    ExcSheet.addCell(TiempoTranPreRad);
                    ExcSheet.addCell(TiempoTransRadica);
                    ExcSheet.addCell(EnviarA);
                    ExcSheet.addCell(VistoCita);
                    ExcSheet.addCell(FechaCita);
                    ExcSheet.addCell(FechaEntrega);
                    ExcSheet.addCell(TiempoTransEntrega);
                    ExcSheet.addCell(FechaJuridica);
                    ExcSheet.addCell(TiempoTransJuridica);
                    ExcSheet.addCell(FechaTecnica);
                    ExcSheet.addCell(TiempoTransTecnica);
                    ExcSheet.addCell(FechaImpresion);
                    ExcSheet.addCell(TiempoTranImpresion);
                    
                  } 
                catch (WriteException ex) {
                    System.err.println("Error: " + ex.getClass() + " .exportConsulRad() causado por : " + ex.getMessage());//GCH 02ENE2017
                }
            }
            Conexion.Conexion.CloseCon();
            //Dat.close();
        } catch (SQLException e) {
            System.err.println("Error: " + e.getClass() + " .exportConsulRad() causado por : " + e.getMessage());//GCH 02ENE2017
        }
        //Escribe el archivo excel en disco
        try {

            WorkBook.write();
            WorkBook.close();
            //Genero la descarga del archivo para el usuario
            mBArchivos.DescarArcIndi(String.valueOf(Rutas), "ConsultaRadica", "xls");
            Rutas.delete();
        } catch (IOException | WriteException ex) {
            System.err.println("Error: " + ex.getClass() + " .exportConsulRad() causado por : " + ex.getMessage());//GCH 02ENE2017
        }
    }

    public boolean isRequiereUbicacion() {
        return RequiereUbicacion;
    }

    public void setRequiereUbicacion(boolean RequiereUbicacion) {
        this.RequiereUbicacion = RequiereUbicacion;
    }

        /**
     * Variable tipo BeanAvaluo para traer los atributos y metodos de el
     * ManagedBean BeanAvaluo.java
     *
     *
     * @see BeanAvaluo.java
     */
    @ManagedProperty("#{MBAvaluo}")
    private BeanAvaluo mBAvaluo;

    public BeanAvaluo getmBAvaluo() {
        return mBAvaluo;
    }

    public void setmBAvaluo(BeanAvaluo mBAvaluo) {
        this.mBAvaluo = mBAvaluo;
    }
      private boolean RequiereUbicacion ;
         
       /**
     * Metodo que verifica que se habiliten los cuando se requiere de ubicacion
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @since 01-11-2014
     */
    public void validarUbicacion()
    {
        try {
            if (this.RequiereUbicacion == true) {
                this.Ubicacion = true;
                mBAvaluo.setCodDeptoSol("0");
                mBAvaluo.setCodCiudadSol("0");
            } else if (this.RequiereUbicacion == false) {
                this.Ubicacion = false;
                mBAvaluo.setCodDeptoSol("0");
                mBAvaluo.setCodCiudadSol("0");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".validarUbicacion()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

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
     
     
      public void postProcessXLS(Object document) {
        HSSFWorkbook wb = (HSSFWorkbook) document;
        HSSFSheet sheet = wb.getSheetAt(0);
        HSSFRow header = sheet.getRow(0);
         
        HSSFCellStyle cellStyle = wb.createCellStyle();  
        cellStyle.setFillForegroundColor(HSSFColor.AQUA.index);
        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
         
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            HSSFCell cell = header.getCell(i);
             
            cell.setCellStyle(cellStyle);
        }
    }
     
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
        Document pdf = (Document) document;
        pdf.open();
        pdf.setPageSize(PageSize.A4);
 
        ServletContext servletContext = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String logo = servletContext.getRealPath("") + File.separator + "resources" + File.separator + "demo" + File.separator + "images" + File.separator + ".png";
         pdf.add(Image.getInstance(logo));
    }
     
     
     
     
     
     
     
     
     
    
   //METODOS
    //METODOS PARA RADICACION
    public void validaInfoRadica() throws ParseException
    {
        switch (getOpcionConsul1())
        {
            case "Esp":// Para el caso de que sea una radicacion especifica
                    
                //GCH 02ENE2017
                if ( "".equals(this.cod_Ava_Cons) | this.cod_Ava_Cons==null ){
                    mbTodero.setMens("Debe ingresar un numero de radicacion válido antes de continuar");
                    mbTodero.error();
                } else {
                    Cons = new LogConsulta();
                    Query = " A.Cod_Avaluo = '" + Integer.parseInt(this.cod_Ava_Cons) + "' ";
                    ListaConsulR2 = new ArrayList<>();
                    ListaConsulR2 = Cons.ConsultaRad(2, Query);
                }            
                break;
            case "Gen":// Para el caso de que sea una consulta general
                int Paso = 2;
                String ItemFecReg = "NO",
                 ItemFecPR = "NO",
                 ItemProEnt = "NO",
                 ItemFechAsigPer= "NO",
                 ItemEst = "NO",
                 ItemPerito = "NO",
                 ItemFCita = "NO",
                        ItemEnviar = "NO",
                 ItemRemX = "NO";
                Query = "";
                if (opcFechaRegistro == true) 
                {
                    //Para la validacion de la fecha de Registro
                    if (FechaRegisIni == null || FechaRegisFin == null)
                    {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de Radicacion");
                        mbTodero.warn();
                        Paso = 0;
                    } 
                    else
                    {
                        if ((this.FechaRegisFin.before(this.FechaRegisIni))) 
                        {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else
                        {
                            Paso = 1;
                            ItemFecReg = "OK";
                        }
                    }
                }
                
                /* GCH 30DIC2016 //Perito Identificacion y/o Nombre
                //Genera la validacion del Perito
                if (!"".equals(this.OpcPerito))
                {
                    switch (OpcPerito) {
                        case "Nombre":
                            if ("".equals(this.NombrePerito))
                            {
                                mbTodero.setMens("No ha digitado el Nombre del Perito");
                                mbTodero.warn();
                                Paso = 0;
                            } 
                            else
                            {
                                 Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;
                        case "Identificacion":
                            if ("".equals(this.IdentificacionPerito))
                            {
                                mbTodero.setMens("No ha ingresado la identificacion del Perito");
                                mbTodero.warn();
                                Paso = 0;
                                
                            } else 
                            {
                                 Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;    
                    }
                    
                }*/
                
                if(this.codPerito == 0 && Paso == 0){
                    Paso = 0;    
                } else if ( this.codPerito != 0 ) {
                    Paso = 1;
                    ItemPerito = "OK";
                }
                
                //Fecha Asignacion Perito
                if (this.OpcFechaPerito == true) 
                {
                    //Para la validacion de la fecha de Registro
                    if (FechaPeritoIni == null || FechaPeritoFin == null)
                    {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de Asignacion Perito");
                        mbTodero.warn();
                        Paso = 0;
                    } 
                    else
                    {
                        if ((this.FechaPeritoFin.before(this.FechaPeritoIni))) 
                        {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else
                        {
                            Paso = 1;
                            ItemFechAsigPer = "OK";
                        }
                    }
                }
                
                
                
                 //Fecha Asignacion Cita
                if (OpcFechaCita == true) 
                {
                    //Para la validacion de la fecha de Registro
                    if (FechaCitaIni == null || FechaCitaFin == null)
                    {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de la cita");
                        mbTodero.warn();
                        Paso = 0;
                    } 
                    else
                    {
                        if ((this.FechaCitaFin.before(this.FechaCitaIni))) 
                        {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else
                        {
                            Paso = 1;
                            ItemFCita = "OK";
                        }
                    }
                }
                

               if (this.ListProEntPr.size() > 0) 
               {
                    Paso = 1;
                    ItemProEnt = "OK";
                }
               
                if (this.ListEntidad.size() > 0) 
               {
                    Paso = 1;
                    ItemRemX = "OK";
                }
                  if (this.ListEnviarA.size() > 0) 
                  {
                    Paso = 1;
                    ItemEnviar = "OK";
                   }
                  
                
                //Estructura la informacion de los filtros que se encontraran dentro de la consulta
                if (Paso == 1) 
                {
                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                    //Fechas de Radicado
                    if ("OK".equals(ItemFecReg)) 
                    {
                         Query = "A.Fecha_Radicado between '" + Format.format(this.FechaRegisIni) + "' and  date_add('" + Format.format(this.FechaRegisFin) + "',interval 1 day)  ";
                    }
                     //Fechas de Asignacion Perito
                     if ("OK".equals(ItemFechAsigPer)) 
                    {
                         Query = "SP.Fecha_asignacion between '" + Format.format(this.FechaPeritoIni) + "' and  date_add('" + Format.format(this.FechaPeritoFin) + "',interval 1 day)  ";
                     }
                     
                     //Fechas de Asignacion Cita
                     if ("OK".equals(ItemFCita)) 
                    {
                        Query = " Ct.Fecha_Cita  between '" + Format.format(this.FechaCitaIni) + "' and  date_add('" + Format.format(this.FechaCitaFin) + "',interval 1 day)  ";
                    }
                     
                    
                    //Producto Entidad
                    String Val = "";
                    if ("OK".equals(ItemProEnt))
                    {
                        for (int i = 0; i < ListProEntPr.size(); i++) 
                        {
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
              
                    //Remitido por (Cliente or Entidad)
                    //Genera la validacion del remitente
                    if (!"".equals(this.RemitidoX))
                    {
                        switch (RemitidoX) {
                            case "Entidad":
                                if (ListEntidad.size() <= 0)
                                {
                                    mbTodero.setMens("No ha seleccionado ninguna entidad");
                                    mbTodero.warn();
                                    Paso = 0;
                                } 
                                else
                                {
                                    Paso = 1;
                                    ItemRemX = "OK";
                                }
                                break;
                            case "Cliente":
                                if ("".equals(this.NombreCliente)) {
                                    mbTodero.setMens("No ha ingresado algo dentro del remitente de cliente");
                                    mbTodero.warn();
                                    Paso = 0;
                                } else 
                                {
                                    Paso = 1;
                                    ItemRemX = "OK";
                                }
                                break;

                        }

                    }
                
                 //Remitido por (Cliente or Entidad)
                    Val = "";
                    if (!"".equals(RemitidoX))
                    {
                       
                        switch (RemitidoX) 
                        {
                            case "Cliente"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query))
                                {
                                    Query = " Cl.Nombre_Cliente like '%" + this.NombreCliente + "%'  ";
                                } else 
                                {
                                    Query = Query + " and  Cl.Nombre_Cliente  like  '%" + this.NombreCliente + "%'  ";
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
                    
                    
                    Val = "";
                    if ("OK".equals(ItemEnviar)) {
                        for (int i = 0; i < ListEnviarA.size(); i++) 
                        {
                            if (i == 0) {
                                Val = " '" + ListEnviarA.get(i).toString() + "'";
                            } 
                            else
                            {
                                Val = Val + "," + " '" + ListEnviarA.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query))
                        {
                            Query = " Resultado_Parametro in (" + Val + ")";
                        }
                        else
                        {
                            Query = Query + " and Resultado_Parametro in (" + Val + ")";
                        }
                    }
                   
                    
                    /*GCH 30DIC2016 //Perito (identificacion / Nombre)
                    Val = "";
                    if (!"".equals(OpcPerito))
                    {
                       
                        switch (OpcPerito) 
                        {
                            case "Identificacion"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query))
                                {
                                    Query = " P.NumDoc_Perito  like '%" + this.IdentificacionPerito + "%'  ";
                                } else 
                                {
                                    Query = Query + " and  P.NumDoc_Perito  like  '%" + this.IdentificacionPerito + "%'  ";
                                }
                                break;
                            case "Nombre":// Llenado de informacion para el cliente que remite la solicitud
                             if ("".equals(Query))
                                {
                                    Query = " P.Nombre_Perito  like '%" + this.NombrePerito + "%'  ";
                                } else 
                                {
                                    Query = Query + " and  P.Nombre_Perito   like  '%" + this.NombrePerito + "%'  ";
                                }
                                break;
                        }
                    }*/
                    
                    //GCH 30DIC2016
                    
                    if(this.codPerito!=0){
                        
                        if ("".equals(Query))
                                {
                                    Query = " P.Cod_Perito = " + this.codPerito + " ";
                                } else 
                                {
                                    Query = Query + " and  P.Cod_Perito = " + this.codPerito + " " ;
                                }
                    }
                    
                    Cons = new LogConsulta();
                    ListaConsulR = new ArrayList<>();
                    ListaConsulR = Cons.ConsultaRad(2, Query);
                    
                }// FINALIZACION DEL PASO=1
                else if (Paso == 2) 
                {
                    Cons = new LogConsulta();
                    Query = "";
                    ListaConsulR = new ArrayList<>();
                    ListaConsulR = Cons.ConsultaRad(1, Query);
                }

                break;
        }
}

/**
 * Metodo que valida perito desde el proceso del modulo de Consultas
 *
 * @author GCH
 * @since 01-01-2017
 */

/**
     * Variable tipo BeanPerito para traer los atributos y metodos de el
     * ManagedBean BeanPerito.java
     *
     *
     * @see BeanPerito.java
     */    
    @ManagedProperty("#{MBPerito}")
    private BeanPerito mbPerito;

    public BeanPerito getMbPerito() {
        return mbPerito;
    }

    public void setMbPerito(BeanPerito mbPerito) {
        this.mbPerito = mbPerito;
    }    
    
    
public void validaTablaPeritos(int op){
    
   if ( op == 1 ){

        if( this.Per == null ) {
           mbTodero.setMens("No selecciono ningun perito, este criterio NO sera incluido en la busqueda");
           mbTodero.warn();
           NombrePerito="";
           codPerito = 0;
        } else {
                 codPerito = this.Per.getCodPerito();
                 NombrePerito = this.Per.getNombrePerito().toUpperCase() + " " +  this.Per.getApellidoPerito().toUpperCase() ; 
        }   
        
   }  else {
                  
           NombrePerito="";
           codPerito = 0;
   }
   
} 


}
    
