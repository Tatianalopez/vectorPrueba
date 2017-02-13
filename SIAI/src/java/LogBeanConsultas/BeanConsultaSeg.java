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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

/**
 *
 * @author admin
 */
@ManagedBean(name = "MBConsultaSeg")
@ViewScoped
@SessionScoped
public class BeanConsultaSeg {

    private String Val;

    /**
     * Creates a new instance of BeanConsulta
     */
    public BeanConsultaSeg() {
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
    private List<LogConsulta> ListaConsulSeg = new ArrayList<>();

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
    private boolean OpcFechaReg;
    private boolean OpcFechaRad;
    private boolean OpcFechaAsigPerito;
    private boolean OpcFechaAsigCita;
    private boolean OpcFechaVeriCita;
    private boolean OpcNumAva;
    private boolean OpcNumPreRad;

    private Date FechaRadIni;
    private Date FechaRadFin;
    private Date FechaRegIni;
    private Date FechaRegFin;
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
    private int numPreRad;
    private int numAva;

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

    public boolean isOpcNumAva() {
        return OpcNumAva;
    }

    public Date getFechaRegIni() {
        return FechaRegIni;
    }

    public void setFechaRegIni(Date FechaRegIni) {
        this.FechaRegIni = FechaRegIni;
    }

    public Date getFechaRegFin() {
        return FechaRegFin;
    }

    public void setFechaRegFin(Date FechaRegFin) {
        this.FechaRegFin = FechaRegFin;
    }

    public int getNumAva() {
        return numAva;
    }

    public void setNumAva(int numAva) {
        this.numAva = numAva;
    }

    public void setOpcNumAva(boolean OpcNumAva) {
        this.OpcNumAva = OpcNumAva;
    }

    public boolean isOpcNumPreRad() {
        return OpcNumPreRad;
    }

    public boolean isOpcFechaReg() {
        return OpcFechaReg;
    }

    public void setOpcFechaReg(boolean OpcFechaReg) {
        this.OpcFechaReg = OpcFechaReg;
    }

    public int getNumPreRad() {
        return numPreRad;
    }

    public void setNumPreRad(int numPreRad) {
        this.numPreRad = numPreRad;
    }

    //GET Y SET PreRadicacion
    public void setOpcNumPreRad(boolean OpcNumPreRad) {
        this.OpcNumPreRad = OpcNumPreRad;
    }

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

    public List<LogConsulta> getListaConsulSeg() {
        return ListaConsulSeg;
    }

    public void setListaConsulSeg(List<LogConsulta> ListaConsulSeg) {
        this.ListaConsulSeg = ListaConsulSeg;
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
    public void validaInfoSeguimiento() throws ParseException {
        switch (this.OpcionConsul) {
            case "Esp":// Para el caso de que sea una preradicacion especifica
                break;
            case "Gen":// Para el caso de que sea una consulta general
                int Paso = 0;
                String ItemFecReg = "NO",
                 ItemFecRad = "NO",
                 ItemFecAP = "NO",
                 ItemFecAC = "NO",
                 ItemFecVC = "NO",
                 ItemPerito = "NO",
                 ItemEnviar = "NO",
                 ItemProEnt = "NO",
                 ItemEst = "NO",
                 ItemRemX = "NO";
                Query = "";

                if (OpcNumPreRad == true) {

                    if (numPreRad <= 0) {
                        mbTodero.setMens("Falta ingresar el número de pre radicacón");
                        mbTodero.warn();
                        Paso++;
                    } else {
                    }
                }

                if (OpcNumAva == true) {

                    if (numAva <= 0) {
                        mbTodero.setMens("Falta ingresar el número de avalúo");
                        mbTodero.warn();
                        Paso++;
                    } else {
                    }
                }

                if (OpcFechaReg == true) {
                    //Para la validacion de la fecha de Radicacion
                    if (FechaRegIni == null || FechaRegFin == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de registro");
                        mbTodero.warn();
                        Paso++;
                    } else if ((this.FechaRegFin.before(this.FechaRegIni))) {
                        mbTodero.setMens("La fecha inicial es mayor a la fecha final de registro");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemFecReg = "OK";
                    }
                }

                if (OpcFechaRad == true) {
                    //Para la validacion de la fecha de Radicacion
                    if (FechaRadIni == null || FechaRadFin == null) {
                        mbTodero.setMens("Falta llenar información de rangos en fechas de radicación");
                        mbTodero.warn();
                        Paso++;
                    } else if ((this.FechaRadFin.before(this.FechaRadIni))) {
                        mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemFecRad = "OK";
                    }
                }

                if (OpcFechaAsigPerito == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigPeritoIni == null || this.FechaAsigPeritoFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Perito");
                        mbTodero.warn();
                        Paso++;
                    } else if (FechaAsigPeritoFin.before(FechaAsigPeritoIni)) {
                        mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemFecAP = "OK";
                    }
                }

                if (OpcFechaAsigCita == true) {
                    //Para la validacion de los rangos de fecha de Asignacion al perito
                    if (FechaAsigCitaIni == null || this.FechaAsigCitaFin == null) {
                        mbTodero.setMens("Falta fechas en el rango de fecha Asignación Cita");
                        mbTodero.warn();
                        Paso++;
                    } else if (FechaAsigCitaFin.before(FechaAsigCitaIni)) {
                        mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                        mbTodero.warn();
                        Paso++;
                    } else {
                        ItemFecAC = "OK";
                    }
                }
                //Genera la validacion del perito
                if (!"".equals(this.OpcPerito)) {
                    switch (OpcPerito) {
                        case "Nombre":
                            if ("".equals(this.NombrePerito)) {
                                mbTodero.setMens("No ha digitado el Nombre del Perito");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                ItemPerito = "OK";
                            }
                            break;
                        case "Identificacion":
                            if ("".equals(this.IdentificacionPerito)) {
                                mbTodero.setMens("No ha ingresado la identificacion del Perito");
                                mbTodero.warn();
                                Paso++;
                            } else {
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
                                Paso++;
                            } else {
                                ItemRemX = "OK";
                            }
                            break;
                        case "Cliente":
                            if ("".equals(this.NombreCliente)) {
                                mbTodero.setMens("No ha ingresado algo dentro del remitente de cliente");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                ItemRemX = "OK";
                            }
                            break;
                    }
                }

                if (this.ListProEntSeg != null && this.ListProEntSeg.size() > 0) {
                    ItemProEnt = "OK";
                }

                if (this.ListEstado != null && this.ListEstado.size() > 0) {
                    ItemEst = "OK";
                }
                //list tipo avaluo
                //departamenro
                //matricula

                if (Paso == 0) {

                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

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
                    if (!"".equals(RemitidoX)) {

                        switch (RemitidoX) {
                            case "Cliente"://Llenado de informacion para el cliente que remite la solicitud
                                if ("".equals(Query)) {
                                    Query = " Cl.Nombre_Cliente like '%" + this.NombreCliente + "%'  ";
                                } else {
                                    Query = Query + " and  Cl.Nombre_Cliente  like  '%" + this.NombreCliente + "%'  ";
                                }
                                break;
                            case "Entidad":// Llenado de informacion para el cliente que remite la solicitud
                                for (int i = 0; i < ListEntidad.size(); i++) {
                                    if (i == 0) {
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
                    ListaConsulSeg = new ArrayList<>();
                    ListaConsulSeg = Cons.ConsultaSeg(2, Query);
                } // FINALIZACION DEL PASO=1
                else if (Paso == 2) {
                    Cons = new LogConsulta();
                    Query = "";
                    ListaConsulSeg = new ArrayList<>();
                    ListaConsulSeg = Cons.ConsultaSeg(1, Query);
                }

                break;
        }
    }

    //Metodo para realizar el exportado en el area de preradicacion
    //Metodo para realizar la limpieza del bean y recargar nuevamente las  variables
    public void clearBean() {
        this.ListaConsulSeg = new ArrayList<>();
        this.ListProEntSeg = new ArrayList<>();
        this.ListEstado = new ArrayList<>();
        this.ListEntidad = new ArrayList<>();
        this.ListProEntSeg = null;
        this.SelectListProEntSeg = null;
        this.SelectListEnviarA = null;
        this.SelectListEstado = null;
        this.ListEnviarA = null;
        this.OpcionConsul = "";
        this.OpcFechaRad = false;
        this.OpcFechaAsigPerito = false;
        this.OpcFechaAsigCita = false;
        this.OpcFechaVeriCita = false;
        this.FechaAsigPeritoFin = null;
        this.FechaAsigPeritoIni = null;
        this.FechaRadFin = null;
        this.FechaRadIni = null;
        this.FechaAsigCitaIni = null;
        this.FechaAsigCitaFin = null;
        this.FechaVeriCitaIni = null;
        this.FechaVeriCitaFin = null;
        this.OpcPerito = null;
        this.NombrePerito = null;
        this.IdentificacionPerito = null;
        this.RemitidoX = null;
        this.NombreCliente = null;
        this.ListEntidad = null;
        this.ListEstado = null;
        this.SelectListTipoAvaluo = null;

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
