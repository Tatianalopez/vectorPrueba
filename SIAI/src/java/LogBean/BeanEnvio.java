/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBean;

import Logic.LogEnvio;
import Logic.LogFacturacion;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Laura Guerrero Morales
 */
@ManagedBean(name = "MBEnvio")
@ViewScoped
@SessionScoped

public class BeanEnvio {

    
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
    LogEnvio Envio = new LogEnvio();
    LogEnvio Envio2 = new LogEnvio();
    private String observacion = "";
    List<LogEnvio> ListPendientXEnvi = null;
    List<LogEnvio> ListObservaciones = null;
    List<LogEnvio> selectListPendientXEnvi = null;
    List<LogEnvio> ListConsultaEnvio = null;
    private String OpcionConsul = "";
    private boolean OpcFechaEnvi;
    private Date FechaEnvio;
    private boolean OpcNumAso;
    private int numAso = 0;
    private String Tipo;
    private String Estado;

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public int getNumAso() {
        return numAso;
    }

    public void setNumAso(int numAso) {
        this.numAso = numAso;
    }

    public boolean isOpcNumAso() {
        return OpcNumAso;
    }

    public void setOpcNumAso(boolean OpcNumAso) {
        this.OpcNumAso = OpcNumAso;
    }

    public Date getFechaEnvio() {
        return FechaEnvio;
    }

    public void setFechaEnvio(Date FechaEnvio) {
        this.FechaEnvio = FechaEnvio;
    }

    public LogEnvio getEnvio() {
        return Envio;
    }

    public void setEnvio(LogEnvio Envio) {
        this.Envio = Envio;
    }

    public boolean isOpcFechaEnvi() {
        return OpcFechaEnvi;
    }

    public void setOpcFechaEnvi(boolean OpcFechaEnvi) {
        this.OpcFechaEnvi = OpcFechaEnvi;
    }

    public String getOpcionConsul() {
        return OpcionConsul;
    }

    public void setOpcionConsul(String OpcionConsul) {
        this.OpcionConsul = OpcionConsul;
    }

    public List<LogEnvio> getListConsultaEnvio() {
        return ListConsultaEnvio;
    }

    public void setListConsultaEnvio(List<LogEnvio> ListConsultaEnvio) {
        this.ListConsultaEnvio = ListConsultaEnvio;
    }

    public List<LogEnvio> getListObservaciones() {
        return ListObservaciones;
    }

    public void setListObservaciones(List<LogEnvio> ListObservaciones) {
        this.ListObservaciones = ListObservaciones;
    }

    public List<LogEnvio> getListPendientXEnvi() {
        return ListPendientXEnvi;
    }

    public void setListPendientXEnvi(List<LogEnvio> ListPendientXEnvi) {
        this.ListPendientXEnvi = ListPendientXEnvi;
    }

    public List<LogEnvio> getSelectListPendientXEnvi() {
        return selectListPendientXEnvi;
    }

    public void setSelectListPendientXEnvi(List<LogEnvio> selectListPendientXEnvi) {
        this.selectListPendientXEnvi = selectListPendientXEnvi;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public void OpcionesEnvio(String opci) throws SQLException, WriteException {

        switch (opci) {
            case "observacion":
                observacion = "";
                if (getSelectListPendientXEnvi() != null && getSelectListPendientXEnvi().size() > 0) {
                    setListObservaciones(new ArrayList<>());
                    String cadena = "";
                    for (int i = 0; i < getSelectListPendientXEnvi().size(); i++) {
                        cadena = String.valueOf(getSelectListPendientXEnvi().get(i).getCodEnvi()) + "," + cadena;
                    }
                    setListObservaciones(Envio.Observaciones(cadena.substring(0, cadena.length() - 1)));
                    RequestContext.getCurrentInstance().execute("PF('enviPop').show()");
                } else {
                    mbTodero.setMens("Seleccione registros de la tabla");
                    mbTodero.warn();
                }

                break;

            case "confirmar":
                if (getSelectListPendientXEnvi() != null && getSelectListPendientXEnvi().size() > 0) {
                    RequestContext.getCurrentInstance().execute("PF('confirPop').show()");
                } else {
                    mbTodero.setMens("Seleccione registros de la tabla");
                    mbTodero.warn();
                }
                break;

            case "InserConfir":
                for (int i = 0; i < selectListPendientXEnvi.size(); i++) {
                    Envio.GestioEnvio(1, selectListPendientXEnvi.get(i).getCodEnvi(), observacion);
                }
                RequestContext.getCurrentInstance().execute("PF('confirPop').hide()");
                setListPendientXEnvi(Envio.PendientXEnvio());
                selectListPendientXEnvi = null;
                break;

            case "InserObser":
                if (!observacion.isEmpty()) {
                    for (int i = 0; i < selectListPendientXEnvi.size(); i++) {
                        Envio.GestioEnvio(2, selectListPendientXEnvi.get(i).getCodEnvi(), observacion);
                    }
                    RequestContext.getCurrentInstance().execute("PF('enviPop').hide()");
                    mbTodero.setMens("Observacion ingresada satisfactoriamente");
                    mbTodero.warn();
                    selectListPendientXEnvi = null;
                } else {
                    mbTodero.setMens("Ingrese una observación");
                    mbTodero.warn();
                }

                break;

            case "consulta":

                Envio2 = new LogEnvio();
                String Query = "";
                int Paso = 0;
                String ItemFec = "";
                String ItemTipo = "";
                String ItemAsoci = "";
                String ItemEstado = "";
                if (OpcFechaEnvi == true) {
                    //Para la validacion de la fecha de Radicacion
                    if (FechaEnvio == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fecha de envio");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemFec = "OK";
                    }
                }

                if (!Tipo.isEmpty()) {
                    ItemTipo = "OK";
                }

                if (OpcNumAso) {
                    if (numAso <= 0) {
                        mbTodero.setMens("Ingrese un numero asociado al tipo de envio valido");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemAsoci = "OK";
                    }
                }
                if (!Estado.isEmpty()) {
                    ItemEstado = "OK";
                }

                if (Paso == 0) {
                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

                    if ("OK".equals(ItemFec)) {
                        Query = "where Fecha_Entrega like  '%" + Format.format(this.FechaEnvio) + "%' ";
                    }

                    if ("OK".equals(ItemTipo)) {
                        if ("".equals(Query)) {
                            Query = "where Tipo=" + Tipo + " ";
                        } else {
                            Query = Query + " and Tipo=" + Tipo + " ";
                        }
                    }

                    if ("OK".equals(ItemAsoci)) {
                        if ("".equals(Query)) {
                            Query = "where Asociado=" + numAso + " ";
                        } else {
                            Query = Query + " and Asociado=" + numAso + " ";
                        }
                    }

                    if ("OK".equals(ItemEstado)) {
                        if ("".equals(Query)) {
                            
                            Query = "where Fecha_Entrega is "+((Estado.equalsIgnoreCase("Enviado"))?"not null":"null")+" ";
                        } else {
                            Query = Query + " and Fecha_Entrega is "+((Estado.equalsIgnoreCase("Enviado"))?"not null":"null")+" ";
                        }
                    }

                    setListConsultaEnvio(Envio2.ConsultaEnvio(Query));

                }

                break;
                
                 case "Expor":

                /*INICIA EL PROCESO DE LA CREACION DEL ARCHIVO DE EXCEL*/
                File Rutas = new File(File.separator + "DBARCHIVOS" + File.separator + "ConsultaEnvio.xls");
                int Row = 0;
                //Escribir el tipo de formato de fuente que va a tener el archivo de Excel
                WritableFont ExcFontHeads = new WritableFont(WritableFont.ARIAL, 16, WritableFont.NO_BOLD, false, UnderlineStyle.SINGLE, Colour.SKY_BLUE, ScriptStyle.SUPERSCRIPT);
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
                    WorkBook.createSheet("ConsultaEnvio", 0);
                    ExcSheet = WorkBook.getSheet(0);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

                //Variables sera la cantidad de columnas que se traigan despues de iniciar la consulta
                for (int i = 0; i < ListConsultaEnvio.size(); i++) {
                    if (Row == 0) {
                        //  NAvaluo = new Label(0, Row, "N Avaluo", CellFormatHeads);
                        ExcSheet.addCell(new Label(0, Row, "N Envio", CellFormatHeads));
                        ExcSheet.addCell(new Label(1, Row, "Tipo Envio", CellFormatHeads));
                        ExcSheet.addCell(new Label(2, Row, "N asociado", CellFormatHeads));
                        ExcSheet.addCell(new Label(3, Row, "Estado", CellFormatHeads));
                        ExcSheet.addCell(new Label(4, Row, "Observacion", CellFormatHeads));
                     
                        Row++;
                    }

                    ExcSheet.addCell(new Label(0, Row, String.valueOf(ListConsultaEnvio.get(i).getCodEnvi()), CellFormat));
                    ExcSheet.addCell(new Label(1, Row, String.valueOf(ListConsultaEnvio.get(i).getTipEnv()), CellFormat));
                    ExcSheet.addCell(new Label(2, Row, String.valueOf(ListConsultaEnvio.get(i).getAsociado()), CellFormat));
                    ExcSheet.addCell(new Label(3, Row, String.valueOf(ListConsultaEnvio.get(i).getEstado()), CellFormat));
                    ExcSheet.addCell(new Label(4, Row, String.valueOf(ListConsultaEnvio.get(i).getEstado()), CellFormat));
                
                    Row++;
                }

                //Escribe el archivo excel en disco
                try {
                    WorkBook.write();
                    WorkBook.close();
                    //Genero la descarga del archivo para el usuario
                    mBArchivos.DescarArcIndi(String.valueOf(Rutas), "ConsultaEnvio", "xls");
                    Rutas.delete();
                } catch (IOException | WriteException ex) {
                    System.err.println(ex.getMessage());
                }

                break;

        }

    }

}
