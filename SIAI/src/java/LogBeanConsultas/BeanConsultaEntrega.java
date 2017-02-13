package LogBeanConsultas;

import Conexion.Conexion;
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
@ManagedBean(name = "MBConsultaEntrega")
@ViewScoped
@SessionScoped
public class BeanConsultaEntrega {

    /**
     * Creates a new instance of BeanConsulta
     */
    public BeanConsultaEntrega() {
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
    private List<LogConsulta> ListaConsulEntrega = new ArrayList<>();

    private List ListProEntSeg = null;//Variable para la seleccion de informacion del producto Entidad
    private List SelectListProEntSeg = null;//Variable para la muestra de la seleccion de informacion del producto Entidad
    private List ListEstado = null;// Variable para la seleccion de informacion del estado del avaluo
    private List SelectListEstado = null;// Variable para la seleccion de informacion del estado del avaluo
    private List ListEntidad = null;
    private List SelectListEnviarA = null;
    private List ListEnviarA = null;

    private List ListProEntPr = null;

    private List SelectListTipoAvaluo = null;
    private List ListTipoAvaluo = null;

    // -- Para los rangos de fecha de registro y de preradicacion
    private String OpcionConsul = "";
    private boolean OpcFechaRad;
    private boolean OpcFechaAsigPerito;
    private boolean OpcFechaAsigCita;
    private boolean OpcFechaVeriCita;

    private Date FechaRadIni;
    private Date FechaRadFin;
    private Date FechaAsigPeritoIni;
    private Date FechaAsigPeritoFin;
    private Date FechaAsigCitaIni;
    private Date FechaAsigCitaFin;
    private Date FechaVeriCitaIni;
    private Date FechaVeriCitaFin;

    private String RemitidoX;
    private String OpcPerito;
    private String NombreCliente;
    private String NombrePerito;
    private String IdentificacionPerito;
    


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


  

    public List getListProEntSeg() {
        return ListProEntSeg;
    }

    public void setListProEntSeg(List ListProEntSeg) {
        this.ListProEntSeg = ListProEntSeg;
    }

    public List getSelectListProEntSeg() {
        return SelectListProEntSeg;
    }

    public void setSelectListProEntSeg(List SelectListProEntSeg) {
        this.SelectListProEntSeg = SelectListProEntSeg;
    }

    public List getListProEntPr() {
        return ListProEntPr;
    }

    public void setListProEntPr(List ListProEntPr) {
        this.ListProEntPr = ListProEntPr;
    }

    public String getOpcPerito() {
        return OpcPerito;
    }

    public void setOpcPerito(String OpcPerito) {
        this.OpcPerito = OpcPerito;
    }

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

    public List getListEstado() {
        return ListEstado;
    }

    public void setListEstado(List ListEstado) {
        this.ListEstado = ListEstado;
    }

    public List getSelectListEstado() {
        return SelectListEstado;
    }

    public void setSelectListEstado(List SelectListEstado) {
        this.SelectListEstado = SelectListEstado;
    }

    public List getSelectListEnviarA() {
        return SelectListEnviarA;
    }

    public void setSelectListEnviarA(List ListEnviarA) {
        this.SelectListEnviarA = ListEnviarA;
    }

    public List getSelectListTipoAvaluo() {
        return SelectListTipoAvaluo;
    }

    public void setSelectListTipoAvaluo(List SelectListTipoAvaluo) {
        this.SelectListTipoAvaluo = SelectListTipoAvaluo;
    }

    public List getListTipoAvaluo() {
        return ListTipoAvaluo;
    }

    public void setListTipoAvaluo(List ListTipoAvaluo) {
        this.ListTipoAvaluo = ListTipoAvaluo;
    }

    public List getListEnviarA() {
        return ListEnviarA;
    }

    public void setListEnviarA(List ListEnviarA) {
        this.ListEnviarA = ListEnviarA;
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

    public boolean isOpcFechaRad() {
        return OpcFechaRad;
    }

    public void setOpcFechaRad(boolean OpcFechaRad) {
        this.OpcFechaRad = OpcFechaRad;
    }

    public boolean isOpcFechaAsigPerito() {
        return OpcFechaAsigPerito;
    }

    public void setOpcFechaAsigPerito(boolean OpcFechaAsigPerito) {
        this.OpcFechaAsigPerito = OpcFechaAsigPerito;
    }

    public Date getFechaRadIni() {
        return FechaRadIni;
    }

    public void setFechaRadIni(Date FechaRegisIni) {
        this.FechaRadIni = FechaRegisIni;
    }

    public Date getFechaRadFin() {
        return FechaRadFin;
    }

    public void setFechaRadFin(Date FechaRegisFin) {
        this.FechaRadFin = FechaRegisFin;
    }

    public Date getFechaAsigPeritoIni() {
        return FechaAsigPeritoIni;
    }

    public void setFechaAsigPeritoIni(Date FechaAsigPeritoIni) {
        this.FechaAsigPeritoIni = FechaAsigPeritoIni;
    }

    public Date getFechaAsigPeritoFin() {
        return FechaAsigPeritoFin;
    }

    public void setFechaAsigPeritoFin(Date FechaAsigPeritoFin) {
        this.FechaAsigPeritoFin = FechaAsigPeritoFin;
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

    public List<LogConsulta> getListaConsulEntrega() {
        return ListaConsulEntrega;
    }

    public void setListaConsulEntrega(List<LogConsulta> ListaConsulEntrega) {
        this.ListaConsulEntrega = ListaConsulEntrega;
    }

    public boolean isOpcFechaAsigCita() {
        return OpcFechaAsigCita;
    }

    public void setOpcFechaAsigCita(boolean OpcFechaAsigCita) {
        this.OpcFechaAsigCita = OpcFechaAsigCita;
    }

    public Date getFechaAsigCitaIni() {
        return FechaAsigCitaIni;
    }

    public void setFechaAsigCitaIni(Date FechaAsigCitaIni) {
        this.FechaAsigCitaIni = FechaAsigCitaIni;
    }

    public Date getFechaAsigCitaFin() {
        return FechaAsigCitaFin;
    }

    public void setFechaAsigCitaFin(Date FechaAsigCitaFin) {
        this.FechaAsigCitaFin = FechaAsigCitaFin;
    }

    public boolean isOpcFechaVeriCita() {
        return OpcFechaVeriCita;
    }

    public void setOpcFechaVeriCita(boolean OpcFechaVeriCita) {
        this.OpcFechaVeriCita = OpcFechaVeriCita;
    }

    public Date getFechaVeriCitaIni() {
        return FechaVeriCitaIni;
    }

    public void setFechaVeriCitaIni(Date FechaVeriCitaIni) {
        this.FechaVeriCitaIni = FechaVeriCitaIni;
    }

    public Date getFechaVeriCitaFin() {
        return FechaVeriCitaFin;
    }

    public void setFechaVeriCitaFin(Date FechaVeriCitaFin) {
        this.FechaVeriCitaFin = FechaVeriCitaFin;
    }

    public boolean isRequiereUbicacion() {
        return RequiereUbicacion;
    }

    public void setRequiereUbicacion(boolean RequiereUbicacion) {
        this.RequiereUbicacion = RequiereUbicacion;
    }

    public boolean isUbicacion() {
        return Ubicacion;
    }

    public void setUbicacion(boolean Ubicacion) {
        this.Ubicacion = Ubicacion;
    }


                
       private boolean RequiereUbicacion = false;
    private boolean Ubicacion = false;
    
    
   
    
    

    //METODOS
    //METODOS PARA PRERADICACION
    public void validaInfoEntrega() throws ParseException {
        switch (this.OpcionConsul) {
            case "Esp":// Para el caso de que sea una preradicacion especifica
                break;
            case "Gen":// Para el caso de que sea una consulta general
                int Paso = 2;
                String validar = "";
                String ItemFecRad = "NO",
                 ItemFecAP = "NO",
                 ItemFecAC = "NO",
                 ItemFecVC = "NO",
                 ItemPerito = "NO",
                 ItemEnviar = "NO",
                 ItemProEnt = "NO",
                 ItemEst = "NO",
                 ItemRemX = "NO";
                Query = "";

                if (OpcFechaRad == true) {
                    //Para la validacion de la fecha de Radicacion
                    if (FechaRadIni == null || FechaRadFin == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de radicación");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if ((this.FechaRadFin.before(this.FechaRadIni))) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecRad = "OK";
                        }
                    }
                }

                if (OpcFechaAsigPerito == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigPeritoIni == null || this.FechaAsigPeritoFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Perito");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaAsigPeritoFin.before(FechaAsigPeritoIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecAP = "OK";
                        }
                    }
                }

                if (OpcFechaAsigCita == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigCitaIni == null || this.FechaAsigCitaFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Cita");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaAsigCitaFin.before(FechaAsigCitaIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecAC = "OK";
                        }
                    }
                }

                if (OpcFechaVeriCita == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaVeriCitaIni == null || this.FechaVeriCitaFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Verificación Cita");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaVeriCitaFin.before(FechaVeriCitaIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecVC = "OK";
                        }
                    }
                }

                if (this.ListProEntSeg.size() > 0) {
                    Paso = 1;
                    ItemProEnt = "OK";
                }

                if (this.ListEstado.size() > 0) {
                    Paso = 1;
                    ItemEst = "OK";
                }

                if (this.ListEnviarA.size() > 0) {
                    Paso = 1;
                    ItemEnviar = "OK";

                }

                  //Genera la validacion del perito
                if (!"".equals(this.OpcPerito)) {
                    switch (OpcPerito) {
                        case "Nombre":
                            if ("".equals(this.NombrePerito)) {
                                mbTodero.setMens("No ha digitado el Nombre del Perito");
                                mbTodero.warn();
                                Paso = 0;
                            } else {

                                Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;
                        case "Identificacion":
                            if ("".equals(this.IdentificacionPerito)) {
                                mbTodero.setMens("No ha ingresado la identificacion del Perito");
                                mbTodero.warn();
                                Paso = 0;
                            } else {

                                Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;

                    }

                }

                //Genera la validacion del remitente
                if (!"".equals(this.RemitidoX)) {
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
                    //Fechas de Radicacion
                
                      if ("OK".equals(ItemFecRad)) {
                        Query = "A.Fecha_Radicado between '" + Format.format(this.FechaRadIni) + "' and  date_add('" + Format.format(this.FechaRadFin) + "',interval 1 day)  ";
                    }
                    //Fecha asignacion perito
                    if ("OK".equals(ItemFecAP)) {
                        if ("".equals(Query)) {
                            Query = " SP.Fecha_Asignacion between '" + Format.format(this.FechaAsigPeritoIni) + "'  and date_add('" + Format.format(this.FechaAsigPeritoFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and SP.Fecha_Asignacion between '" + Format.format(this.FechaAsigPeritoIni) + "'  and date_add('" + Format.format(this.FechaAsigPeritoFin) + "',interval 1 day)";
                        }
                    }
                    
                    

                    if ("OK".equals(ItemFecAC)) {
                        if ("".equals(Query)) {
                            Query = " Ct.Fecha_Cita between '" + Format.format(this.FechaAsigCitaIni) + "'  and date_add('" + Format.format(this.FechaAsigCitaFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and Ct.Fecha_Cita between '" + Format.format(this.FechaAsigCitaIni) + "'  and date_add('" + Format.format(this.FechaAsigCitaFin) + "',interval 1 day)";
                        }
                    }

                    if ("OK".equals(ItemFecVC)) {
                        if ("".equals(Query)) {
                            Query = " Ct.Visto_Cita between '" + Format.format(this.FechaVeriCitaIni) + "'  and date_add('" + Format.format(this.FechaVeriCitaFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and Ct.Visto_Cita between '" + Format.format(this.FechaVeriCitaIni) + "'  and date_add('" + Format.format(this.FechaVeriCitaFin) + "',interval 1 day)";
                        }
                    }

                    //Peritos
                    String Val = "";
                    if (!"".equals(OpcPerito)) {

                        switch (OpcPerito) {
                            case "Identificacion"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " P.NumDoc_Perito  like '%" + this.IdentificacionPerito + "%'  ";
                                } else {
                                    Query = Query + " and  P.NumDoc_Perito   like  '%" + this.IdentificacionPerito + "%'  ";
                                }
                                break;
                            case "Nombre":// Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " P.Nombre_Perito  like '%" + this.NombrePerito + "%'  ";
                                } else {
                                    Query = Query + " and  P.Nombre_Perito   like  '%" + this.NombrePerito + "%'  ";
                                }
                                break;
                        }
                    }

                    //Producto Entidad
                    Val = "";
                    if ("OK".equals(ItemProEnt)) {
                        for (int i = 0; i < ListProEntSeg.size(); i++) {
                            if (i == 0) {
                                Val = ListProEntSeg.get(i).toString();
                            } else {
                                Val = Val + "," + ListProEntSeg.get(i).toString();
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " PE.Cod_ProEnt in (" + Val + ")";
                        } else {
                            Query = Query + " and PE.Cod_ProEnt in (" + Val + ")";
                        }
                    }

                    //Enviar A:
                    Val = "";
                    if ("OK".equals(ItemEnviar)) {
                        for (int i = 0; i < ListEnviarA.size(); i++) {
                            if (i == 0) {
                                Val = " '" + ListEnviarA.get(i).toString() + "'";
                            } else {
                                Val = Val + "," + " '" + ListEnviarA.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " Resultado_Parametro in (" + Val + ")";
                        } else {
                            Query = Query + " and Resultado_Parametro in (" + Val + ")";
                        }
                    }

                    //Estado AVALUO
                    Val = "";
                    if ("OK".equals(ItemEst)) {
                        for (int i = 0; i < ListEstado.size(); i++) {
                            if (i == 0) {
                                Val = " '" + ListEstado.get(i).toString() + "'";
                            } else {
                                Val = Val + "," + " '" + ListEstado.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " A.Estado_Avaluo in (" + Val + ")";
                        } else {
                            Query = Query + " and A.Estado_Avaluo in (" + Val + ")";
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

                    
                    
                    
                    Cons = new LogConsulta();
                    ListaConsulEntrega = new ArrayList<>();
                    ListaConsulEntrega = Cons.ConsultaRad(2, Query);
                } // FINALIZACION DEL PASO=1
                else if (Paso == 2) {
                    Cons = new LogConsulta();
                    Query = "";
                    ListaConsulEntrega = new ArrayList<>();
                    ListaConsulEntrega = Cons.ConsultaRad(1, Query);
                }

                break;
        }
    }

    //Metodo para realizar el exportado en el area de preradicacion
   
    //Metodo para realizar la limpieza del bean y recargar nuevamente las  variables
    public void clearBean() {
        this.ListaConsulEntrega = new ArrayList<>();
        this.ListProEntSeg = new ArrayList<>();
        this.ListEstado = new ArrayList<>();
        this.ListEntidad = new ArrayList<>();
        this.ListProEntSeg=null;
        this.SelectListProEntSeg=null;
        this.SelectListEnviarA=null;
        this.SelectListEstado=null;
        this.ListEnviarA=null;
        this.OpcionConsul = "";
        this.OpcFechaRad = false;
        this.OpcFechaAsigPerito = false;
        this.OpcFechaAsigCita=false;
        this.OpcFechaVeriCita=false;
        this.FechaAsigPeritoFin = null;
        this.FechaAsigPeritoIni = null;
        this.FechaRadFin = null;
        this.FechaRadIni = null;
        this.FechaAsigCitaIni=null;
        this.FechaAsigCitaFin=null;
        this.FechaVeriCitaIni=null;
        this.FechaVeriCitaFin=null;
        this.OpcPerito=null;
        this.NombrePerito=null;
        this.IdentificacionPerito=null;
        this.RemitidoX=null;
        this.NombreCliente=null;
        this.ListEntidad=null;
        this.ListEstado=null;
        this.SelectListTipoAvaluo = null;
        


    }
    
     public void exportConsul() {
        int Paso = 2;
                String validar = "";
                String ItemFecRad = "NO",
                 ItemFecAP = "NO",
                 ItemFecAC = "NO",
                 ItemFecVC = "NO",
                 ItemPerito = "NO",
                 ItemEnviar = "NO",
                 ItemProEnt = "NO",
                 ItemEst = "NO",
                 ItemRemX = "NO";
        int tipo=1;
         Query = "";
        if (OpcFechaRad == true) {
                    //Para la validacion de la fecha de Radicacion
                    if (FechaRadIni == null || FechaRadFin == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de radicación");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if ((this.FechaRadFin.before(this.FechaRadIni))) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecRad = "OK";
                        }
                    }
                }

                if (OpcFechaAsigPerito == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigPeritoIni == null || this.FechaAsigPeritoFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Perito");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaAsigPeritoFin.before(FechaAsigPeritoIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecAP = "OK";
                        }
                    }
                }

                if (OpcFechaAsigCita == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigCitaIni == null || this.FechaAsigCitaFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Cita");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaAsigCitaFin.before(FechaAsigCitaIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecAC = "OK";
                        }
                    }
                }

                if (OpcFechaVeriCita == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaVeriCitaIni == null || this.FechaVeriCitaFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Verificación Cita");
                        mbTodero.warn();
                        Paso = 0;
                    } else {
                        if (FechaVeriCitaFin.before(FechaVeriCitaIni)) {
                            mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                            mbTodero.warn();
                            Paso = 0;
                        } else {
                            Paso = 1;
                            ItemFecVC = "OK";
                        }
                    }
                }

                if (this.ListProEntSeg.size() > 0) {
                    Paso = 1;
                    ItemProEnt = "OK";
                }

                if (this.ListEstado.size() > 0) {
                    Paso = 1;
                    ItemEst = "OK";
                }

                if (this.ListEnviarA.size() > 0) {
                    Paso = 1;
                    ItemEnviar = "OK";

                }

                  //Genera la validacion del perito
                if (!"".equals(this.OpcPerito)) {
                    switch (OpcPerito) {
                        case "Nombre":
                            if ("".equals(this.NombrePerito)) {
                                mbTodero.setMens("No ha digitado el Nombre del Perito");
                                mbTodero.warn();
                                Paso = 0;
                            } else {

                                Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;
                        case "Identificacion":
                            if ("".equals(this.IdentificacionPerito)) {
                                mbTodero.setMens("No ha ingresado la identificacion del Perito");
                                mbTodero.warn();
                                Paso = 0;
                            } else {

                                Paso = 1;
                                ItemPerito = "OK";
                            }
                            break;

                    }

                }
                
                    //Genera la validacion del remitente
                if (!"".equals(this.RemitidoX)) {
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
            tipo = 2;
            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
           
            
             //Fechas de Radicacion
                
                      if ("OK".equals(ItemFecRad)) {
                        Query = "A.Fecha_Radicado between '" + Format.format(this.FechaRadIni) + "' and  date_add('" + Format.format(this.FechaRadFin) + "',interval 1 day)  ";
                    }
                    //Fecha asignacion perito
                    if ("OK".equals(ItemFecAP)) {
                        if ("".equals(Query)) {
                            Query = " SP.Fecha_Asignacion between '" + Format.format(this.FechaAsigPeritoIni) + "'  and date_add('" + Format.format(this.FechaAsigPeritoFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and SP.Fecha_Asignacion between '" + Format.format(this.FechaAsigPeritoIni) + "'  and date_add('" + Format.format(this.FechaAsigPeritoFin) + "',interval 1 day)";
                        }
                    }
                    
                    

                    if ("OK".equals(ItemFecAC)) {
                        if ("".equals(Query)) {
                            Query = " Ct.Fecha_Cita between '" + Format.format(this.FechaAsigCitaIni) + "'  and date_add('" + Format.format(this.FechaAsigCitaFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and Ct.Fecha_Cita between '" + Format.format(this.FechaAsigCitaIni) + "'  and date_add('" + Format.format(this.FechaAsigCitaFin) + "',interval 1 day)";
                        }
                    }

                    if ("OK".equals(ItemFecVC)) {
                        if ("".equals(Query)) {
                            Query = " Ct.Visto_Cita between '" + Format.format(this.FechaVeriCitaIni) + "'  and date_add('" + Format.format(this.FechaVeriCitaFin) + "',interval 1 day)";
                        } else {
                            Query = Query + " and Ct.Visto_Cita between '" + Format.format(this.FechaVeriCitaIni) + "'  and date_add('" + Format.format(this.FechaVeriCitaFin) + "',interval 1 day)";
                        }
                    }

                    //Peritos
                    String Val = "";
                    if (!"".equals(OpcPerito)) {

                        switch (OpcPerito) {
                            case "Identificacion"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " P.NumDoc_Perito  like '%" + this.IdentificacionPerito + "%'  ";
                                } else {
                                    Query = Query + " and  P.NumDoc_Perito   like  '%" + this.IdentificacionPerito + "%'  ";
                                }
                                break;
                            case "Nombre":// Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " P.Nombre_Perito  like '%" + this.NombrePerito + "%'  ";
                                } else {
                                    Query = Query + " and  P.Nombre_Perito   like  '%" + this.NombrePerito + "%'  ";
                                }
                                break;
                        }
                    }

                    //Producto Entidad
                    Val = "";
                    if ("OK".equals(ItemProEnt)) {
                        for (int i = 0; i < ListProEntSeg.size(); i++) {
                            if (i == 0) {
                                Val = ListProEntSeg.get(i).toString();
                            } else {
                                Val = Val + "," + ListProEntSeg.get(i).toString();
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " PE.Cod_ProEnt in (" + Val + ")";
                        } else {
                            Query = Query + " and PE.Cod_ProEnt in (" + Val + ")";
                        }
                    }

                    //Enviar A:
                    Val = "";
                    if ("OK".equals(ItemEnviar)) {
                        for (int i = 0; i < ListEnviarA.size(); i++) {
                            if (i == 0) {
                                Val = " '" + ListEnviarA.get(i).toString() + "'";
                            } else {
                                Val = Val + "," + " '" + ListEnviarA.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " Resultado_Parametro in (" + Val + ")";
                        } else {
                            Query = Query + " and Resultado_Parametro in (" + Val + ")";
                        }
                    }

                    //Estado AVALUO
                    Val = "";
                    if ("OK".equals(ItemEst)) {
                        for (int i = 0; i < ListEstado.size(); i++) {
                            if (i == 0) {
                                Val = " '" + ListEstado.get(i).toString() + "'";
                            } else {
                                Val = Val + "," + " '" + ListEstado.get(i).toString() + "' ";
                            }
                        }
                        if ("".equals(Query)) {
                            Query = " A.Estado_Avaluo in (" + Val + ")";
                        } else {
                            Query = Query + " and A.Estado_Avaluo in (" + Val + ")";
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

                   } 
        /*INICIA EL PROCESO DE LA CREACION DEL ARCHIVO DE EXCEL*/
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
            System.err.println(ex.getMessage());
        }
        
        
        
        //Variables sera la cantidad de columnas que se traigan despues de iniciar la consulta
        int TotalCol = 0;
        try {
            
            //
           Dat = Cons.ConsultRadicacionGen(tipo,Query);
            ResultSetMetaData DatMetDat = Dat.getMetaData();
            TotalCol = DatMetDat.getColumnCount();
            while (Dat.next()) 
            {
                Label NAvaluo;
                Label FechaSoliRad;
                Label FechaRad;
                Label Estad_Ava;
                
                if (Row == 0) {
                    NAvaluo = new Label(0, Row, "N* Avaluo", CellFormatHeads);
                    FechaSoliRad = new Label(1, Row, "Fecha Solicitud", CellFormatHeads);
                    FechaRad = new Label(2, Row, "Fecha Radicacion", CellFormatHeads);
                    Estad_Ava = new Label(3, Row, "Estado Avaluo", CellFormatHeads);
                    
                    Row++;
                } else {
                    NAvaluo = new Label(0, Row, Dat.getString("Cod_Avaluo"), CellFormat);
                    FechaSoliRad = new Label(1, Row, Dat.getString("Fecha_Solicitud"), CellFormat);
                    FechaRad = new Label(2, Row, Dat.getString("Fecha_Radicado"), CellFormat);
                    Estad_Ava = new Label(3, Row, Dat.getString("Estado_Avaluo"), CellFormat);
                   
                    Row++;
                }
                try {
                    ExcSheet.addCell(NAvaluo);
                    ExcSheet.addCell(FechaSoliRad);
                    ExcSheet.addCell(FechaRad);
                    ExcSheet.addCell(Estad_Ava);
                  } 
                catch (WriteException ex) {
                    System.err.println(ex.getMessage());
                }
            }
            Conexion.CloseCon();
          //  Dat.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        //Escribe el archivo excel en disco
        try {
            for (int i = 0; i < TotalCol; i++) {

            }
            WorkBook.write();
            WorkBook.close();
            //Genero la descarga del archivo para el usuario
            mBArchivos.DescarArcIndi(String.valueOf(Rutas), "ConsultaRadica", "xls");
            Rutas.delete();
        } catch (IOException | WriteException ex) {
            System.err.println(ex.getMessage());
        }
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
            SelectListProEntSeg = new ArrayList<>();
            for (int j = 0; j <= this.ListProEntSeg.size() - 1; j++) {
                String codigo = ListProEntSeg.get(j).toString();
                Adm.setCodProEnt(Integer.parseInt(codigo));
                String OPciones = Adm.ConsulNombreProdEnti();
                SelectListProEntSeg.add(new SelectItem(codigo, OPciones));
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
    public void onCargarEstadosSelecc() {
        String OPciones = "";
        try {
            SelectListEstado = new ArrayList<>();
            for (int j = 0; j <= this.ListEstado.size() - 1; j++) {
                String codigo = ListEstado.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "P":
                            OPciones = "EN PROCESO";
                            break;
                        case "D":
                            OPciones = "DEVUELTO";
                            break;

                    }
                }
                SelectListEstado.add(new SelectItem(codigo, OPciones));

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

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

    public void onCargarTipoAvaluo() {
        String OPciones = "";
        try {
            SelectListTipoAvaluo = new ArrayList<>();
            for (int j = 0; j <= this.ListTipoAvaluo.size() - 1; j++) {
                String codigo = ListTipoAvaluo.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "E":
                            OPciones = "ENSER";
                            break;
                        case "M":
                            OPciones = "MAQUINARIA";
                            break;
                        case "P":
                            OPciones = "PREDIO ";
                            break;

                    }
                }
                SelectListTipoAvaluo.add(new SelectItem(codigo, OPciones));

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

}