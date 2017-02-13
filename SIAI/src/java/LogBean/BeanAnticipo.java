/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBean;

import Logic.LogAnticipo;
import Logic.LogAvaluo;
import Logic.LogCliente;
import Logic.LogEntidad;
import Logic.LogCargueArchivos;
import Logic.LogFacturacion;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 ** <p>
 * <b>Managed bean / Bean que utiliza los metodos para la creacion de los
 * anticipos, en cualquier proceso, sea de uno a uno, o anticipos especiales,
 * esto dependera del registro que desee ser aplicado</b></p>
 *
 * @author Jhohan Stiven Rodriguez
 * @author Maira Alejandra Chaparro
 * @version 2.0.0
 * @since 01-09-2015 1.0.0
 */
@ManagedBean(name = "MBAnticipo")
@ViewScoped
@SessionScoped
public class BeanAnticipo {

    /**
     * Creates a new instance of BeanAnticipo
     */
    public BeanAnticipo() {
    }

    /**
     * Variables de las Logicas
     */
    private String ValoTarifaAval;

    LogAnticipo Ant = new LogAnticipo();
    LogAnticipo Anti = new LogAnticipo(); // Interna dentro del manejo del bean
    LogAnticipo AntiCliente = new LogAnticipo(); // Interna dentro del manejo del bean
    LogCliente Client = new LogCliente(); // Interna dentro del manejo del bean
    LogEntidad Ent = new LogEntidad();

    private List<LogAnticipo> listConsuEntidad = null;
    private List<LogAnticipo> listObservaciones = null;

    private List<LogAnticipo> ListObserAnti;

    public LogAnticipo getAntiAval() {
        return AntiAval;
    }

    public void setAntiAval(LogAnticipo AntiAval) {
        this.AntiAval = AntiAval;
    }
    LogAnticipo AntiAval = new LogAnticipo(); // Interna dentro del manejo del bean
    private LogAnticipo laura = new LogAnticipo();
    LogCliente Cli = new LogCliente();
    LogAvaluo Ava = new LogAvaluo();

    /**
     * Variables necesarias para cargar y agregar los anticipos *
     */
    /**
     * Variables para carpturar anticipos*
     */
    private String TipoAntIng;
    private boolean AprobPerFactGen;
    private boolean AprobAvalCliFac;
    private ResultSet Tab = null;
    private boolean NAvaluoAsociado = false;//Para el caso de que el avaluo no tenga numero de avaluo asociado
    private int CodAnt;
    private String ValorAnt;
    private String FechaConsignacion;
    private Date FechaConsigna;
    private String FechaAprobacion;
    private String BancoAnt;
    private String EstadoAnt;
    private String FechaObser;
    private String FechaArreglo;
    private String ObservacionAnt;
    private String ObservacionAntDev = "";
    private String ObservacionDevMasi = "";
    private String ObservacionAntTrans = "";
    private String valorReparticion;
    double ValorGenralAnti = 0;

    public boolean ArchivoCreado = false;
    public int Paso = 0;
    public boolean Result = false;
    String fecha_actual;
    String[] Fecha;
    int Suma = 0;

    //Varbles Necesarias para el modulo de Consulta anticipos
    private int CodAnticipo;
    private String ValorAnticipo;
    private String FechaAnticipoConsignacion;
    private Date FechaAntConsig;
    private String FechaAnticipoAprobacion;
    private String BancoAnticipo;
    private String EstadoAnticipo;
    private String FechaObservacion;
    private String ObservacionAnticipo;
    private String CodSegAnt;
    private List<LogAnticipo> ListConslGen = null;
    private List<LogAnticipo> SelectListConslGen = null;
    private int filtrarAvaluo;

    private int valorTex1;
    private int valorTex2;
    private int valorTex3;
    private int valorTex4;
    private int valorTex5;

    private List<LogAnticipo> ListPopUp = null;
    private List<LogAnticipo> lauralist = null;

    public List<LogAnticipo> getLauralist() {
        return lauralist;
    }

    public void setLauralist(List<LogAnticipo> lauralist) {
        this.lauralist = lauralist;
    }

    private Date fechaConsigIni;
    private Date fechaConsigFin;
    private boolean OpcFechaConsig;
    LogAnticipo Cons = new LogAnticipo();

    private List ListEstado = null;
    private List SelectListEstado = null;

    public int multiple, codCuenta;
    private List<LogAnticipo> listMontosPopUp = null;

    private List<LogAnticipo> selectCodCliente = null;
    private List<LogAnticipo> selectAnticipo = null;
    private List<LogAnticipo> selectAntTrans = null;
    private List<LogAnticipo> selectAprobados = null;

    /**
     * Varialbes para las listas de anticipos *
     */
    private List<LogAnticipo> ListAntGeneral = null;
    private List<LogAnticipo> ListSelectAvaluo = null;
    private List<LogCliente> ListClienPerFac = null;

    private List<LogAnticipo> Listslcantcp = null;
    private List<LogAvaluo> Listslctavl = null;
    private ArrayList<SelectItem> CargaAnticipoCli = null;
    private List<LogAnticipo> listaAnticipoDet = null;
    private List<LogAnticipo> listSelecTrans = null;
    private List<LogCliente> Lstcles = null;
    private List<LogAvaluo> ListAvalClieFac = null;

    private List<LogAnticipo> listaConsulClientesAva = null;
    private List<LogAnticipo> listaTransEsTrue = null;
    private List<LogAnticipo> ListPersonasAFacturarAntic = null;

    String sumPath = "";
    String split = "";

    public LogAnticipo getLaura() {
        return laura;
    }

    public void setLaura(LogAnticipo laura) {
        this.laura = laura;
    }

    public List<LogAnticipo> getListaDetCliente() {
        return ListaDetCliente;
    }

    public void setListaDetCliente(List<LogAnticipo> ListaDetCliente) {
        this.ListaDetCliente = ListaDetCliente;
    }
    private List<LogAnticipo> ListaDetCliente;

    public List<LogAnticipo> getListObservaciones() {
        return listObservaciones;
    }

    public void setListObservaciones(List<LogAnticipo> listObservaciones) {
        this.listObservaciones = listObservaciones;
    }

    public List<LogAnticipo> getListConsuEntidad() {
        return listConsuEntidad;
    }

    public void setListConsuEntidad(List<LogAnticipo> listConsuEntidad) {
        this.listConsuEntidad = listConsuEntidad;
    }

    public String getValoTarifaAval() {
        return ValoTarifaAval;
    }

    public void setValoTarifaAval(String ValoTarifaAval) {
        this.ValoTarifaAval = ValoTarifaAval;
    }

    public List<LogAnticipo> getListObserAnti() {
        return ListObserAnti;
    }

    public void setListObserAnti(List<LogAnticipo> ListObserAnti) {
        this.ListObserAnti = ListObserAnti;
    }

    public int getValorTex1() {
        return valorTex1;
    }

    public void setValorTex1(int valorTex1) {
        this.valorTex1 = valorTex1;
    }

    public int getValorTex2() {
        return valorTex2;
    }

    public void setValorTex2(int valorTex2) {
        this.valorTex2 = valorTex2;
    }

    public int getValorTex3() {
        return valorTex3;
    }

    public void setValorTex3(int valorTex3) {
        this.valorTex3 = valorTex3;
    }

    public int getValorTex4() {
        return valorTex4;
    }

    public void setValorTex4(int valorTex4) {
        this.valorTex4 = valorTex4;
    }

    public int getValorTex5() {
        return valorTex5;
    }

    public void setValorTex5(int valorTex5) {
        this.valorTex5 = valorTex5;
    }

    public String getValorReparticion() {
        return valorReparticion;
    }

    public void setValorReparticion(String valorReparticion) {
        this.valorReparticion = valorReparticion;
    }

    public String getObservacionAntDev() {
        return ObservacionAntDev;
    }

    public void setObservacionAntDev(String ObservacionAntDev) {
        this.ObservacionAntDev = ObservacionAntDev;
    }

    public String getObservacionAntTrans() {
        return ObservacionAntTrans;
    }

    public void setObservacionAntTrans(String ObservacionAntTrans) {
        this.ObservacionAntTrans = ObservacionAntTrans;
    }

    public String getObservacionDevMasi() {
        return ObservacionDevMasi;
    }

    public void setObservacionDevMasi(String ObservacionDevMasi) {
        this.ObservacionDevMasi = ObservacionDevMasi;
    }

    public List<LogAnticipo> getListPopUp() {
        return ListPopUp;
    }

    public void setListPopUp(List<LogAnticipo> ListPopUp) {
        this.ListPopUp = ListPopUp;
    }

    public LogCliente getClient() {
        return Client;
    }

    public void setClient(LogCliente Client) {
        this.Client = Client;
    }

    public int getCodAnticipo() {
        return CodAnticipo;
    }

    public void setCodAnticipo(int CodAnticipo) {
        this.CodAnticipo = CodAnticipo;
    }

    public String getValorAnticipo() {
        return ValorAnticipo;
    }

    public void setValorAnticipo(String ValorAnticipo) {
        this.ValorAnticipo = ValorAnticipo;
    }

    public String getFechaAnticipoConsignacion() {
        return FechaAnticipoConsignacion;
    }

    public void setFechaAnticipoConsignacion(String FechaAnticipoConsignacion) {
        this.FechaAnticipoConsignacion = FechaAnticipoConsignacion;
    }

    public Date getFechaAntConsig() {
        return FechaAntConsig;
    }

    public void setFechaAntConsig(Date FechaAntConsig) {
        this.FechaAntConsig = FechaAntConsig;
    }

    public String getFechaAnticipoAprobacion() {
        return FechaAnticipoAprobacion;
    }

    public void setFechaAnticipoAprobacion(String FechaAnticipoAprobacion) {
        this.FechaAnticipoAprobacion = FechaAnticipoAprobacion;
    }

    public String getBancoAnticipo() {
        return BancoAnticipo;
    }

    public void setBancoAnticipo(String BancoAnticipo) {
        this.BancoAnticipo = BancoAnticipo;
    }

    public String getEstadoAnticipo() {
        return EstadoAnticipo;
    }

    public void setEstadoAnticipo(String EstadoAnticipo) {
        this.EstadoAnticipo = EstadoAnticipo;
    }

    public String getFechaObservacion() {
        return FechaObservacion;
    }

    public void setFechaObservacion(String FechaObservacion) {
        this.FechaObservacion = FechaObservacion;
    }

    public String getObservacionAnticipo() {
        return ObservacionAnticipo;
    }

    public void setObservacionAnticipo(String ObservacionAnticipo) {
        this.ObservacionAnticipo = ObservacionAnticipo;
    }

    public String getCodSegAnt() {
        return CodSegAnt;
    }

    public void setCodSegAnt(String CodSegAnt) {
        this.CodSegAnt = CodSegAnt;
    }

    public int getFiltrarAvaluo() {
        return filtrarAvaluo;
    }

    public void setFiltrarAvaluo(int filtrarAvaluo) {
        this.filtrarAvaluo = filtrarAvaluo;
    }

    public Date getFechaConsigIni() {
        return fechaConsigIni;
    }

    public void setFechaConsigIni(Date fechaConsigIni) {
        this.fechaConsigIni = fechaConsigIni;
    }

    public Date getFechaConsigFin() {
        return fechaConsigFin;
    }

    public void setFechaConsigFin(Date fechaConsigFin) {
        this.fechaConsigFin = fechaConsigFin;
    }

    public boolean isOpcFechaConsig() {
        return OpcFechaConsig;
    }

    public void setOpcFechaConsig(boolean OpcFechaConsig) {
        this.OpcFechaConsig = OpcFechaConsig;
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

    public List<LogAnticipo> getClienteSegAnt() {
        return ClienteSegAnt;
    }

    public void setClienteSegAnt(List<LogAnticipo> ClienteSegAnt) {
        this.ClienteSegAnt = ClienteSegAnt;
    }

    public String getCodAnti() {
        return CodAnti;
    }

    public void setCodAnti(String CodAnti) {
        this.CodAnti = CodAnti;
    }

    public String getCodSeg() {
        return CodSeg;
    }

    public void setCodSeg(String CodSeg) {
        this.CodSeg = CodSeg;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String Estado) {
        this.Estado = Estado;
    }

    public List<LogAnticipo> getListMontosPopUp() {
        return listMontosPopUp;
    }

    public void setListMontosPopUp(List<LogAnticipo> listMontosPopUp) {
        this.listMontosPopUp = listMontosPopUp;
    }

    public List<LogAnticipo> getListaTransEsTrue() {
        return listaTransEsTrue;
    }

    public void setListaTransEsTrue(List<LogAnticipo> listaTransEsTrue) {
        this.listaTransEsTrue = listaTransEsTrue;
    }

    public List<LogAnticipo> getSelectAprobados() {
        return selectAprobados;
    }

    public void setSelectAprobados(List<LogAnticipo> selectAprobados) {
        this.selectAprobados = selectAprobados;
    }

    public List<LogAnticipo> getSelectCodCliente() {
        return selectCodCliente;
    }

    public void setSelectCodCliente(List<LogAnticipo> selectCodCliente) {
        this.selectCodCliente = selectCodCliente;
    }

    public List<LogAnticipo> getListaConsulClientesAva() {
        return listaConsulClientesAva;
    }

    public void setListaConsulClientesAva(List<LogAnticipo> listaConsulClientesAva) {
        this.listaConsulClientesAva = listaConsulClientesAva;
    }

    public List<LogAnticipo> getListSelecTrans() {
        return listSelecTrans;
    }

    public void setListSelecTrans(List<LogAnticipo> listSelecTrans) {
        this.listSelecTrans = listSelecTrans;
    }

    public List<LogAnticipo> getSelectAntTrans() {
        return selectAntTrans;
    }

    public void setSelectAntTrans(List<LogAnticipo> selectAntTrans) {
        this.selectAntTrans = selectAntTrans;
    }

    public List<LogAnticipo> getListConslGen() {
        return ListConslGen;
    }

    public void setListConslGen(List<LogAnticipo> ListConslGen) {
        this.ListConslGen = ListConslGen;
    }

    public List<LogAnticipo> getSelectAnticipo() {
        return selectAnticipo;
    }

    public void setSelectAnticipo(List<LogAnticipo> selectAnticipo) {
        this.selectAnticipo = selectAnticipo;
    }

    public List<LogAnticipo> getListaAnticipoDet() {
        return listaAnticipoDet;
    }

    public void setListaAnticipoDet(List<LogAnticipo> listaAnticipoDet) {
        this.listaAnticipoDet = listaAnticipoDet;
    }

    public List<LogAnticipo> getListslcantcp() {
        return Listslcantcp;
    }

    public void setListslcantcp(List<LogAnticipo> Listslcantcp) {
        this.Listslcantcp = Listslcantcp;
    }

    public List<LogCliente> getLstcles() {
        return Lstcles;
    }

    public void setLstcles(List<LogCliente> Lstcles) {
        this.Lstcles = Lstcles;
    }

    public List<LogAvaluo> getListslctavl() {
        return Listslctavl;
    }

    public void setListslctavl(List<LogAvaluo> Listslctavl) {
        this.Listslctavl = Listslctavl;
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

    /**
     * Variable tipo BeanSesion para traer los atributos y metodos de el
     * ManagedBean BeanSesion.java
     *
     *
     * @see BeanSesion.java
     */
    @ManagedProperty("#{MBSesion}")
    private BeanSesion mBSesion;

    public BeanSesion getmBSesion() {
        return mBSesion;
    }

    public void setmBSesion(BeanSesion mBSesion) {
        this.mBSesion = mBSesion;
    }

    /**
     * Variable tipo BeanArchivos para traer los atributos y metodos de el
     * ManagedBean BeanArchivos.java
     *
     *
     * @see BeanArchivos.java
     */
    @ManagedProperty("#{MBArchivos}")
    private BeanArchivos mBArchivos;

    public BeanArchivos getmBArchivos() {
        return mBArchivos;
    }

    public void setmBArchivos(BeanArchivos mBArchivos) {
        this.mBArchivos = mBArchivos;
    }

    @ManagedProperty("#{MBadministracion}")
    private BeanAdministracion mbAdministracion;

    public BeanAdministracion getMbAdministracion() {
        return mbAdministracion;
    }

    public void setMbAdministracion(BeanAdministracion mbAdministracion) {
        this.mbAdministracion = mbAdministracion;
    }

    /**
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @return LogAnticipo Ant
     */
    public String getCodseg() {
        return Codseg;
    }

    public void setCodseg(String Codseg) {
        this.Codseg = Codseg;
    }

    private String Codseg;

    public LogAnticipo getAnt() {
        return Ant;
    }

    public void setAnt(LogAnticipo Ant) {
        this.Ant = Ant;
    }

    public LogAnticipo getAnti() {
        return Anti;
    }

    public void setAnti(LogAnticipo Anti) {
        this.Anti = Anti;
    }

    public LogAnticipo getAntiCliente() {
        return AntiCliente;
    }

    public void setAntiCliente(LogAnticipo AntiCliente) {
        this.AntiCliente = AntiCliente;
    }

    public LogCliente getCli() {
        return Cli;
    }

    public void setCli(LogCliente Cli) {
        this.Cli = Cli;
    }

    public List<LogAnticipo> getListAntGeneral() {
        return ListAntGeneral;
    }

    public void setListAntGeneral(List<LogAnticipo> ListAntGeneral) {
        this.ListAntGeneral = ListAntGeneral;
    }

    public List<LogAnticipo> getListSelectAvaluo() {
        return ListSelectAvaluo;
    }

    public void setListSelectAvaluo(List<LogAnticipo> ListSelectAvaluo) {
        this.ListSelectAvaluo = ListSelectAvaluo;
    }

    public List<LogCliente> getListClienPerFac() {
        return ListClienPerFac;
    }

    public void setListClienPerFac(List<LogCliente> ListClienPerFac) {
        this.ListClienPerFac = ListClienPerFac;
    }

    public List<LogAvaluo> getListAvalClieFac() {
        return ListAvalClieFac;
    }

    public void setListAvalClieFac(List<LogAvaluo> ListAvalClieFac) {
        this.ListAvalClieFac = ListAvalClieFac;
    }

    public List<LogAnticipo> getListPersonasAFacturarAntic() {
        return ListPersonasAFacturarAntic;
    }

    public void setListPersonasAFacturarAntic(List<LogAnticipo> ListPersonasAFacturarAntic) {
        this.ListPersonasAFacturarAntic = ListPersonasAFacturarAntic;
    }

    public ArrayList<SelectItem> getCargaAnticipoCli() {
        return CargaAnticipoCli;
    }

    public void setCargaAnticipoCli(ArrayList<SelectItem> CargaAnticipoCli) {
        this.CargaAnticipoCli = CargaAnticipoCli;
    }

    public boolean isNAvaluoAsociado() {
        return NAvaluoAsociado;
    }

    public void setNAvaluoAsociado(boolean NAvaluoAsociado) {
        this.NAvaluoAsociado = NAvaluoAsociado;
    }

    public int getCodAnt() {
        return CodAnt;
    }

    public void setCodAnt(int CodAnt) {
        this.CodAnt = CodAnt;
    }

    public String getValorAnt() {
        return ValorAnt;
    }

    public void setValorAnt(String ValorAnt) {
        this.ValorAnt = ValorAnt;
    }

    public String getFechaConsignacion() {
        return FechaConsignacion;
    }

    public void setFechaConsignacion(String FechaConsignacion) {
        this.FechaConsignacion = FechaConsignacion;
    }

    public Date getFechaConsigna() {
        return FechaConsigna;
    }

    public void setFechaConsigna(Date FechaConsigna) {
        this.FechaConsigna = FechaConsigna;
    }

    public String getFechaAprobacion() {
        return FechaAprobacion;
    }

    public void setFechaAprobacion(String FechaAprobacion) {
        this.FechaAprobacion = FechaAprobacion;
    }

    public String getBancoAnt() {
        return BancoAnt;
    }

    public void setBancoAnt(String BancoAnt) {
        this.BancoAnt = BancoAnt;
    }

    public String getEstadoAnt() {
        return EstadoAnt;
    }

    public void setEstadoAnt(String EstadoAnt) {
        this.EstadoAnt = EstadoAnt;
    }

    public String getFechaObser() {
        return FechaObser;
    }

    public void setFechaObser(String FechaObser) {
        this.FechaObser = FechaObser;
    }

    public String getFechaArreglo() {
        return FechaArreglo;
    }

    public void setFechaArreglo(String FechaArreglo) {
        this.FechaArreglo = FechaArreglo;
    }

    public String getObservacionAnt() {
        return ObservacionAnt;
    }

    public void setObservacionAnt(String ObservacionAnt) {
        this.ObservacionAnt = ObservacionAnt;
    }

    public String getTipoAntIng() {
        return TipoAntIng;
    }

    public void setTipoAntIng(String TipoAntIng) {
        this.TipoAntIng = TipoAntIng;
    }

    public boolean isAprobPerFactGen() {
        return AprobPerFactGen;
    }

    public void setAprobPerFactGen(boolean AprobPerFactGen) {
        this.AprobPerFactGen = AprobPerFactGen;
    }

    public boolean isAprobAvalCliFac() {
        return AprobAvalCliFac;
    }

    public void setAprobAvalCliFac(boolean AprobAvalCliFac) {
        this.AprobAvalCliFac = AprobAvalCliFac;
    }

    public List getSelnaval() {
        return selnaval;
    }

    public void setSelnaval(List selnaval) {
        this.selnaval = selnaval;
    }

    private List selnaval = null;

    /**
     * Crear Metodo para cargar la lista de los avaluos seleccionados se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     * FIN Metodos get y set de todas las variables / atributos, listas, etc que
     * se utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     * Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     * Metodo para para consulta informacion de anticipo general y especial
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tipo String que trae informacion sobre que tipo de anticipo se va
     * a consultar
     * @param NAvaluo String que contiene el numero de avaluo
     * @param NBien String que contiene el numero de bien
     * @param codAnticipo int que contiene el numero de anticipo
     * @since 01-10-2014
     */
    public void verInformacion(String Tipo, String NAvaluo, String NBien, int codAnticipo) {
        switch (Tipo) {
            case "General":
                Anti.setCodAvaluo(NAvaluo);
                Anti.setNBien(NBien);
                Tab = Anti.ConsultAntGen();
                try {
                    while (Tab.next()) {
                        this.CodAnt = Tab.getInt("Cod_Anticipo");
                        this.BancoAnt = Tab.getString("Banco_Anticipo");
                        this.FechaConsigna = Tab.getDate("Fecha_Anticipo_Consignacion");
                        this.ValorAnt = Tab.getString("ValorAnticipo");
                        this.EstadoAnt = Tab.getString("EstadoAnt");
                        this.FechaObser = Tab.getString("Fecha_Observacion");
                        this.ObservacionAnt = Tab.getString("Observacion_AntObs");
                    }
                    Conexion.Conexion.CloseCon();
                } catch (SQLException e) {
                    mbTodero.setMens("Error: " + e.getMessage());
                    mbTodero.fatal();
                }
                break;
            case "Especial":
                Anti.setCodAnticipo(codAnticipo);
                Tab = Anti.ConsultAntEspecial();
                try {
                    while (Tab.next()) {
                        AntiCliente = new LogAnticipo();
                        this.CodAnt = Tab.getInt("Cod_Anticipo");
                        this.BancoAnt = Tab.getString("Banco_Anticipo");
                        this.FechaConsigna = Tab.getDate("Fecha_Anticipo_Consignacion");
                        this.ValorAnt = Tab.getString("ValorAnticipo");
                        this.EstadoAnt = Tab.getString("EstadoAnt");
                        this.FechaObser = Tab.getString("Fecha_Observacion");
                        this.ObservacionAnt = Tab.getString("Observacion_AntObs");
                        AntiCliente.getLogCliente().setCodCliente(Tab.getInt("Cod_Cliente"));
                        AntiCliente.getLogCliente().setNumeroDoccliente(Tab.getString("Numero_DocCliente"));
                        AntiCliente.getLogCliente().setNombreCliente(Tab.getString("Nombre_Cliente"));
                        AntiCliente.getLogCliente().setMailCliente(Tab.getString("Mail_Cliente"));
                        AntiCliente.getLogCliente().setDireccionCliente(Tab.getString("Direccion_Cliente"));
                        AntiCliente.getLogCliente().setTelefonoCliente(Tab.getString("Telefono_Cliente"));
                    }
                    Conexion.Conexion.CloseCon();
                } catch (SQLException e) {
                    mbTodero.setMens("Error: " + e.getMessage());
                    mbTodero.fatal();
                }
                break;
        }
    }

    /**
     * Metodo para cargar informacion segun la opcion que se seleccione a la
     * hora de agregar anticipos
     *
     * @author Maira Alejandra Carvajal
     * @since 01-03-2015
     */
    public void seleccOpcAnt() {
        switch (this.TipoAntIng) {
            case "AntiGen": // Carga informacion para un anticipo general
                this.ListSelectAvaluo = new ArrayList<>();
                this.ListSelectAvaluo = Ant.CargInfAvaluoGen(mBSesion.codigoMiSesion());
                Anti = new LogAnticipo();
                break;
            case "Cliente": // Carga informacion de varios anticipos que se encuentren asociados dentro cliente
                this.ListClienPerFac = new ArrayList<>();
                this.ListClienPerFac = Client.ConsultaClienPerFact(2, 0);
                Cli = new LogCliente();
                break;
            case "AntiEspecial": //Carga especificaciones para cada uno de los avaluos que se van a facturar y son varios o tambien varios clientes

                this.Lstcles = new ArrayList<>();
                this.Lstcles = Client.ConsultaClienPerFact(2, 0);
                Cli = new LogCliente();
                break;
        }
    }

    /**
     * Metodo para realizar la validacion de tabs segun la opcion que se
     * encuentre dentro del anticipo
     *
     * @author Maira Alejandra Carvajal
     * @param ValorTab String que contiene el valor del tab al que se quiera
     * acceder
     * @since 01-03-2015
     */
    public void seleccionTab(String ValorTab) {
        try {
            switch (ValorTab) {
                case "NextTab1":
                    if (Anti == null) {
                        mbTodero.setMens("No ha seleccionado ningun numero de avaluo");
                        mbTodero.warn();
                    } else {
                        int CantiClient = Client.ConsultCantiClienteRad(Integer.parseInt(Anti.getCodAvaluo()));
                        if (CantiClient > 1) {
                            mbTodero.setMens("El numero de avaluo escogido, tiene más de una persona a facturar, por favor ingrese por la opción de varios anticipos para varios clientes y/o avaluos");
                            mbTodero.warn();
                        } else {
                            //Carga la informacion de los clientes que son personas a facturar
                            ListClienPerFac = new ArrayList<>();
                            ListClienPerFac = Client.ConsultaClienPerFact(1, Integer.parseInt(Anti.getCodAvaluo()));
                            //Habilita y deshabilita los tabs del proceso
                            RequestContext.getCurrentInstance().execute("PF('TabGeneral').disable(0)");
                            RequestContext.getCurrentInstance().execute("PF('TabGeneral').select(1)");
                            RequestContext.getCurrentInstance().execute("PF('TabGeneral').enable(1)");
                        }
                    }
                    break;
                case "BackTab1":
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').disable(1)");
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').select(0)");
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').enable(0)");
                    Anti = null;
                    break;
                case "NextTab2":
                    if (this.AprobPerFactGen == false) {
                        mbTodero.setMens("No ha dado el visto bueno de que la información vista este correcta, para seguir con el siguiente proceso");
                        mbTodero.warn();
                    } else {
                        //Cargar Informacion para los archivos
                        mBArchivos.setOpcionPanel("SelAnticipo");
                        mBArchivos.setNAvaluo(Integer.parseInt(Anti.getCodAvaluo()));
                        mBArchivos.setNBien(Integer.parseInt(Anti.getNBien()));
                        mBArchivos.setTipoAvaluo(Anti.getTipoAvaluo());
                        mBArchivos.seleccionPanel(1);

                        //Cargar ka informacion de los anticipos asociados al avaluo 
                        Codseg = "=" + String.valueOf(Anti.getCodAvaluo());
                        Listslcantcp = null;
                        this.Listslcantcp = new ArrayList<>();
//este tampoco                          this.Listslcantcp = Ant.CargarAntcpavl(mBSesion.codigoMiSesion(),getCodseg());
                        Anti = new LogAnticipo();

                        //Habilita y deshabilita los tabs del proceso
                        RequestContext.getCurrentInstance().execute("PF('TabGeneral').disable(1)");
                        RequestContext.getCurrentInstance().execute("PF('TabGeneral').select(2)");
                        RequestContext.getCurrentInstance().execute("PF('TabGeneral').enable(2)");
                    }
                    break;
                case "BackTab2":
                    //Habilita y deshabilita los tabs del proceso
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').disable(2)");
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').select(1)");
                    RequestContext.getCurrentInstance().execute("PF('TabGeneral').enable(1)");
                    this.AprobPerFactGen = false;
                    break;
                /*AQUI EMPIEZAN LOS CASE DEL CARGUE DE ANTICIPOS PARA EL SEGUNDO CASO (CLIENTE)*/
                case "NextCTab1":
                    if (Cli == null) {
                        mbTodero.setMens("No ha seleccionado ningún Cliente");
                        mbTodero.warn();
                    } else {
                        //Consulta la informacion de los avaluos que tiene la persona su nombre
                        this.ListAvalClieFac = new ArrayList<>();
                        this.ListAvalClieFac = Ava.ConsulSoliciAvaClientFac(Cli.getCodCliente());
                        if (this.ListAvalClieFac.size() <= 1) {
                            mbTodero.setMens("El cliente seleccionado no tiene mas de dos solicitudes, por favor ingrese el anticipo desde la opción Asociar anticipo a un avalúo");
                            mbTodero.warn();
                        } else {
                            //Habilita y deshabilita los tab
                            RequestContext.getCurrentInstance().execute("PF('TabClient').disable(0)");
                            RequestContext.getCurrentInstance().execute("PF('TabClient').select(1)");
                            RequestContext.getCurrentInstance().execute("PF('TabClient').enable(1)");
                        }
                    }
                    break;
                case "BackCTab1":
                    Cli = null;
                    RequestContext.getCurrentInstance().execute("PF('TabClient').disable(1)");
                    RequestContext.getCurrentInstance().execute("PF('TabClient').select(0)");
                    RequestContext.getCurrentInstance().execute("PF('TabClient').enable(0)");
                    break;
                case "NextCTab2":
                    if (this.AprobAvalCliFac == false) {
                        mbTodero.setMens("No ha seleccionado la opcion de aprobación de solicitudes");
                        mbTodero.warn();
                    } else {
                        //Cargar Informacion para los archivos
                        ResultSet Dato = Ava.ConsulTotAvaCliente(Cli.getCodCliente());
                        int Predio = 0, Maq = 0, Mueble = 0;
                        while (Dato.next()) {

                            if (Dato.getInt("TipAva") == 1) {
                                Predio = Dato.getInt("Tot");
                            } else if (Dato.getInt("TipAva") == 2) {
                                Maq = Dato.getInt("Tot");
                            } else if (Dato.getInt("TipAva") == 3) {
                                Mueble = Dato.getInt("Tot");
                            }

                        }
                        Conexion.Conexion.CloseCon();
                        this.CargaAnticipoCli = new ArrayList<>();
                        if (Predio > Maq && Predio > Mueble) {
                            mBArchivos.setTipoAvaluo("Predio");
                            this.CargaAnticipoCli = mBArchivos.getConsulArchivos(11);
                        } else if (Maq > Predio && Maq > Mueble) {
                            mBArchivos.setTipoAvaluo("Maquinaria");
                            this.CargaAnticipoCli = mBArchivos.getConsulArchivos(12);
                        } else if (Mueble > Maq && Mueble > Predio) {
                            mBArchivos.setTipoAvaluo("Mueble");
                            this.CargaAnticipoCli = mBArchivos.getConsulArchivos(13);
                        }
                        //mBArchivos.setOpcionPanel("SelAnticipo");
                        //mBArchivos.seleccionPanel(1);// El uno que tiene aqui el seleccion panel no tiene interferencia pues igual es un numero que solo sirve para el numero de avaluo, que en este caso no afecta.
                        Ant = new LogAnticipo();

                        RequestContext.getCurrentInstance().execute("PF('TabClient').disable(1)");
                        RequestContext.getCurrentInstance().execute("PF('TabClient').select(2)");
                        RequestContext.getCurrentInstance().execute("PF('TabClient').enable(2)");
                    }
                    break;
                case "BackCTab2":
                    RequestContext.getCurrentInstance().execute("PF('TabClient').disable(2)");
                    RequestContext.getCurrentInstance().execute("PF('TabClient').select(1)");
                    RequestContext.getCurrentInstance().execute("PF('TabClient').enable(1)");
                    break;

                case "NextCliAv":
                    if (Cli == null) {
                        mbTodero.setMens("No ha seleccionado ningún Cliente");
                        mbTodero.warn();
                    } else {
                        //Consulta la informacion de los avaluos que tiene la persona su nombre
                        this.ListAvalClieFac = new ArrayList<>();
                        this.ListAvalClieFac = Ava.ConsulSoliciAvaClientFac(Cli.getCodCliente());
                        if (this.ListAvalClieFac.size() <= 1) {
                            mbTodero.setMens("El cliente seleccionado no tiene mas de dos solicitudes, por favor ingrese el anticipo desde la opción Asociar anticipo a un avalúo");
                            mbTodero.warn();
                        } else {
                            //Habilita y deshabilita los tab
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').disable(0)");
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').select(1)");
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').enable(1)");
                        }
                    }
                    break;

                case "NextPorc":

                    if (this.Listslctavl.isEmpty()) {
                        mbTodero.setMens("Debe seleccionar un Anticipo");
                        mbTodero.warn();
                        RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').select(1)");
                        //   RequestContext.getCurrentInstance().execute("PF('DlgConsultaAvaluoPredio').show()");
                    } else {

                    }

                    if (Cli == null) {
                        mbTodero.setMens("No ha seleccionado ningún Cliente");
                        mbTodero.warn();
                    } else {
                        //Consulta la informacion de los avaluos que tiene la persona su nombre
                        this.ListAvalClieFac = new ArrayList<>();
                        this.ListAvalClieFac = Ava.ConsulSoliciAvaClientFac(Cli.getCodCliente());
                        if (this.ListAvalClieFac.size() <= 1) {
                            mbTodero.setMens("El cliente seleccionado no tiene mas de dos solicitudes, por favor ingrese el anticipo desde la opción Asociar anticipo a un avalúo");
                            mbTodero.warn();
                        } else {
                            //Habilita y deshabilita los tab
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').disable(0)");
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').select(1)");
                            RequestContext.getCurrentInstance().execute("PF('Tabcliaval1').enable(1)");
                        }
                    }
                    break;

            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error: " + e.getMessage());
            mbTodero.fatal();
        }
    }

    /**
     * Metodo para cargar la informacio de los archivos segun la opcion que se
     * seleccione.
     *
     * @author Maira Alejandra Carvajal
     * @param Tipo = Sera el tipo de archivo a cargar, Normal,Clientes, o
     * anticipos especiales
     */
    public void cargarInforArc(String Tipo) {
        switch (Tipo) {
            case "AntiGen":
                //Cargar Informacion para los archivos
                mBArchivos.setOpcionPanel("SelAnticipo");
                mBArchivos.setNAvaluo(Integer.parseInt(Anti.getCodAvaluo()));
                mBArchivos.setNBien(Integer.parseInt(Anti.getNBien()));
                mBArchivos.setTipoAvaluo(Anti.getTipoAvaluo());
                try {
                    mBArchivos.consultaArchivo(5);
                } catch (Exception e) {
                    mbTodero.setMens("Error: " + e.getMessage());
                    mbTodero.fatal();
                }
                break;
            case "Cliente":
                if (mBArchivos.getCodTipArchivo() <= 0) {
                    mbTodero.setMens("No ha seleccionado ningun tipo de archivo");
                    mbTodero.warn();
                } else if ("".equals(Ant.getValorAnticipo())) {
                    mbTodero.setMens("No ha ingresado un valor de anticipo");
                    mbTodero.warn();
                } else if (null == Ant.getFechaAntConsig()) {
                    mbTodero.setMens("No ha ingresado la fecha de consignacion del anticipo");
                    mbTodero.warn();
                } else if ("".equals(Ant.getBancoAnticipo())) {
                    mbTodero.setMens("No ha ingresado el nombre del banco donde se realizo la consignación");
                    mbTodero.warn();
                } else if (mBArchivos.getNombreArchivo() == null) {
                    mbTodero.setMens("No ha seleccionado ningun archivo a subir");
                    mbTodero.warn();
                } else if (mBArchivos.getNombreArchivo().getSize() > 2000000) {
                    mbTodero.setMens("El archivo a subir es mas grande de lo permitido (20 MB)");
                    mbTodero.warn();
                } else {
                    /**
                     * Si pasa las anteriores condiciones se puede realizar el
                     * cargue de la informacion de anticipo asociado para el
                     * cliente.
                     */
                    Anti = new LogAnticipo();
                    if (".".contains(Ant.getValorAnticipo())) {
                        Anti.setValorAnticipo(Ant.getValorAnticipo().replace(".", ""));
                    } else {
                        Anti.setValorAnticipo(Ant.getValorAnticipo().replace(".", ""));
                    }
                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                    String Fech = Format.format(Ant.getFechaAntConsig());
                    Anti.setFechaAnticipoConsignacion(Fech);
                    Anti.setBancoAnticipo(Ant.getBancoAnticipo());
                    /**
                     * Inserta la información del anticipo que se encuentra
                     * asociado al cliente, y sobre el resultado trae el path, y
                     * el numero del anticipo.
                     */
                    //       Tab = Anti.InsertAnticipo(1, mBArchivos.getCodTipArchivo(), Cli.getCodCliente(), mBArchivos.getNombreArchivo().getFileName(), mBSesion.codigoMiSesion());
                    try {
                        String PathAnt;
                        boolean ArcSubido;

                        if (Tab.next()) {
                            PathAnt = Tab.getString("Path");
                            /**
                             * Valida si el archivo o la direccion del archivo
                             * se encuentra ya registrado, en el caso de que no
                             * se encuentre registrado puede realizar el cargue
                             * del archivo.
                             */
                            mBArchivos.setArchivoCreado(mBArchivos.getArch().PathExistente(PathAnt + mBArchivos.getNombreArchivo()));
                            if (mBArchivos.isArchivoCreado() == true) {
                                mbTodero.setMens("El archivo ya se encuentra creado");
                                mbTodero.warn();
                            } else {
                                /**
                                 * Se realizar el cargue del archivo que va a
                                 * subirse como anticipo, y se crea el
                                 * directorio.
                                 */
                                mBArchivos.Nuevopath(PathAnt);
                                mBArchivos.setPath(PathAnt + mBArchivos.getNombreArchivo().getFileName());
                                ArcSubido = mBArchivos.CargaPath();
                                if (ArcSubido == true) {
                                    mBArchivos.setNumCliente(Cli.getCodCliente());
                                    mBArchivos.setListaArchivosAntClient(new ArrayList<BeanArchivos>());
                                    mBArchivos.setListaArchivosAntClient(mBArchivos.MostrarArchivos(8, File.separator + "DBARCHIVOS" + File.separator + "DBAnticipos" + File.separator));
                                    //mBArchivos.
                                } else {
                                    mbTodero.setMens("Error en la subida del archivo");
                                    mbTodero.error();
                                }
                            }
                        }
                        Conexion.Conexion.CloseCon();
                    } catch (SQLException e) {
                        mbTodero.setMens("Error: " + e.getMessage());
                        mbTodero.fatal();
                    }
                }
                break;
            case "AntEspecial":
                break;
        }
    }

    /**
     * Metodo para eliminar un anticipo seleccione.
     *
     * @author Maira Alejandra Carvajal
     * @param ind
     */
    public void eliminacionAnt(int ind) {
        //    RequestContext.getCurrentInstance().execute("PF('DlgConf').show()");
    }

    /**
     * Nuevos metodos para gestoin de Anticipos Ayda y LAURA
     *
     */
    public String ArrayObserv = "";
    public String ArrayAnti = "";
    public String ArrayEstados = "";
    public String arrayEstadosGestion = "";

    public int valorAnt;
    public String fechaconsignacion = "";
    int codSegAnt = 0;
    int antigioCodAnt = 0;
    public String banco = "";
    public String nBien = "";
    public String tipoBien = "";
    public String codClient = "";
    public String codClient2 = "";
    public String telefono = "";
    public String direccion = "";
    public String mail = "";
    public int capturaCod = 0;
    public String capturaEntidad = "";
    public String capturaSubString = "";

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    private String ExtendNombre(String Tipo) {
        String Extension;
        //Extension = FilenameUtils.getExtension(Tipo);
        Extension = Tipo.substring(Tipo.lastIndexOf("."));
        Extension = Extension.replace(".", "");
        return Extension;
    }

    String path = "";

    public String getCodClienteAnti() {
        return CodClienteAnti;
    }

    public void setCodClienteAnti(String CodClienteAnti) {
        this.CodClienteAnti = CodClienteAnti;
    }

    public String getValorClienteAnti() {
        return ValorClienteAnti;
    }

    public void setValorClienteAnti(String ValorClienteAnti) {
        this.ValorClienteAnti = ValorClienteAnti;
    }

    public String CodClienteAnti = "";
    public String ValorClienteAnti = "";
    double ValorParticion = 0;

    String Query;
    String consul;

    public void Limpiar() {
        this.fechaConsigIni = null;
        this.fechaConsigFin = null;
        this.ListEstado = null;
        this.OpcFechaConsig = false;
        this.SelectListEstado = null;
        this.ObservacionAnt = null;
        this.ObservacionAntDev = null;
        this.listMontosPopUp = null;
        this.ListPopUp = null;
        this.listaConsulClientesAva = null;
        selectAprobados = null;
    }

    public void validaInfoConsAnti() {

        Paso = 0;
        String ItemFecConsig = "NO", ItemEst = "NO", itemAval = "NO";
        Query = "";

        if (OpcFechaConsig == true) {
            //Para la validacion de la fecha de Radicacion
            if (fechaConsigIni == null || fechaConsigFin == null) {
                mbTodero.setMens("Falta llenar información de rangos en fechas de consignación anticipo");
                mbTodero.warn();
                Paso++;
            } else if ((this.fechaConsigFin.before(this.fechaConsigIni))) {
                mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                mbTodero.warn();
                Paso++;
            } else {
                ItemFecConsig = "OK";
            }
        }

        if (this.ListEstado.size() > 0) {
            ItemEst = "OK";

        }
        if (cajaRepar1 > 0) {
            itemAval = "OK";
        }

        //Estructura la informacion de los filtros que se encontraran dentro de la consulta
        if (Paso == 0) {

            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
            //Fechas de Radicacion

            String Val;

            if ("OK".equals(ItemFecConsig)) {
                Query = "An.Fecha_Anticipo_Consignacion between '" + Format.format(this.fechaConsigIni) + "' and date_add( '" + Format.format(this.fechaConsigFin) + "',interval 1 day) ";
            }

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
                    Query = "An.Estado_Anticipo in (" + Val + ")";
                } else {
                    Query = Query + "and An.Estado_Anticipo in (" + Val + ")";
                }
            }

            if ("OK".equals(itemAval)) {

                if ("".equals(Query)) {
                    Query = "Seg.FK_Cod_Avaluo in (" + cajaRepar1 + ")";
                } else {
                    Query = Query + " and Seg.FK_Cod_Avaluo in (" + cajaRepar1 + ")";
                }
            }

            String Query1;
            Cons = new LogAnticipo();
            ListConslGen = new ArrayList<>();
            Query1 = " where  " + Query + ";";
            ListConslGen = Cons.consulGnral("", Query1);
            listMontosPopUp = new ArrayList<>();
            listMontosPopUp = Cons.MontosPorEstadoPopUp(Query);

        }

    }

    public void cambarEstadoTablas(String tipo_tabla) throws SQLException, FileNotFoundException, IOException {

        int codEmpleado = mBSesion.codigoMiSesion();

        switch (tipo_tabla) {

            case "cancelar":

                mBArchivos.setEstadoModifica(false);
                Clear();
                mBArchivos.setSelectAnticipoRad(null);
                mBArchivos.MostrarValorAntip();

                break;

            case "descargar":

                if (selectAprobados.size() > 1 || selectAprobados.isEmpty()) {
                    mbTodero.setMens("Seleccione unicamente un aniticipo para descargar");
                    mbTodero.warn();
                    selectAprobados = null;
                } else {

                    File dir = new File(selectAprobados.get(0).getPath());
                    if (!dir.exists()) {
                        mbTodero.setMens("El archivo especificado no existe");
                        mbTodero.warn();
                    } else {
                        split = selectAprobados.get(0).getPath();
                        String[] division = split.split("/");
                        sumPath = "/" + division[0] + division[1] + "/" + division[2] + "/" + division[3] + "/" + division[4];
                        mBArchivos.setListaArchivosAnt(mBArchivos.ConstruirDescargue(sumPath, selectAprobados.get(0).getCodAvaluo(), selectAprobados.get(0).getCodAnticipo()));
                        try {
                            mBArchivos.descargarArchAva(0, 5);
                        } catch (JRException ex) {
                            Logger.getLogger(BeanAnticipo.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }
                }
                break;

            case "descargar2":

                try {

                    if (mBArchivos.getSelectAnticipoRad().size() == 1) {
                        String sumPath2;
                        split = mBArchivos.getSelectAnticipoRad().get(0).getPath();
                        File fichero = new File(split);

                        if (fichero.exists()) {
                            String[] division2 = split.split("/");
                            sumPath2 = "/" + division2[0] + division2[1] + "/" + division2[2] + "/" + division2[3] + "/" + division2[4];

                            mBArchivos.setListaArchivosAnt(mBArchivos.ConstruirDescargue(sumPath2, mBArchivos.getSelectAnticipoRad().get(0).getCodAvaluo(), mBArchivos.getSelectAnticipoRad().get(0).getCodAnticipo()));

                            for (int k = 0; k < mBArchivos.getListaArchivosAnt().size(); k++) {

                                if (division2[6] == null ? mBArchivos.getListaArchivosAnt().get(k).getName() == null : division2[6].equals(mBArchivos.getListaArchivosAnt().get(k).getName())) {
                                    mBArchivos.descargarArchAva(k, 5);
                                }
                            }
                        } else {
                            mbTodero.setMens("El archivo especificado no existe");
                            mbTodero.warn();
                        }

                    } else {
                        mbTodero.setMens("Debe seleccionar solo un archivo para descargar");
                        mbTodero.info();
                        mBArchivos.setSelectAnticipoRad(null);

                    }
                } catch (JRException ex) {
                    mbTodero.setMens("Error al descargar el archivo" + ex);
                    mbTodero.warn();

                }
                break;

            case "descargarFormAnt":

                if (selectAnticipo.size() > 1 || selectAnticipo.isEmpty()) {

                    mbTodero.setMens("Seleccione unicamente un aniticipo para descargar");
                    mbTodero.warn();
                    selectAnticipo = null;
                } else {
                    String sumPathForm = "";
                    String splitForm = "";
                    splitForm = selectAnticipo.get(0).getPath();
                    String[] divisionForm = splitForm.split("/");
                    sumPathForm = "/" + divisionForm[0] + divisionForm[1] + "/" + divisionForm[2] + "/" + divisionForm[3] + "/" + divisionForm[4];

                    mBArchivos.setListaArchivosAnt(mBArchivos.ConstruirDescargue(sumPathForm, selectAnticipo.get(0).getCodAvaluo(), selectAnticipo.get(0).getCodAnticipo()));
                    try {
                        mBArchivos.descargarArchAva(0, 5);
                    } catch (JRException ex) {
                        Logger.getLogger(BeanAnticipo.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;

            case "infoInter":
                if (selectAnticipo.size() > 1 || selectAnticipo.isEmpty()) {

                    mbTodero.setMens("Seleccione unicamente un aniticipo para visualizar la información");
                    mbTodero.warn();
                    selectAnticipo = null;
                } else {
                    String entidad = selectAnticipo.get(0).getNombreAsesor();
                    boolean contFac = entidad.contains("Facturar");
                    if (contFac == true) {
                        String sp[] = entidad.split(" ");
                        int cant = sp.length - 5;
                        String queryEnti = sp[0];
                        String ciudad = sp[cant];

                        for (int i = 1; i <= cant; i++) {
                            queryEnti = queryEnti + " " + sp[i];
                        }
//                        listConsuEntidad = Anti.ConsulInfoEntidad(queryEnti, ciudad);
                        RequestContext.getCurrentInstance().execute("PF('infoInt').show()");
                    } else {

                        listConsuEntidad = null;
                        RequestContext.getCurrentInstance().execute("PF('infoClienteInt').show()");
                    }
                }
                break;

            case "info":

                if (selectAprobados != null && (selectAprobados.size() > 1 || selectAprobados.isEmpty())) {
                    mbTodero.setMens("Seleccione unicamente un aniticipo para visualizar la información del solicitante");
                    mbTodero.warn();
                    selectAprobados = null;
                } else {
                    RequestContext.getCurrentInstance().execute("PF('infoCliente').show()");
                }
                break;

            case "ObservacionInt":
                if (selectAnticipo.size() > 1 || selectAnticipo.isEmpty()) {
                    mbTodero.setMens("Seleccione unicamente un aniticipo para descargar");
                    mbTodero.warn();
                    selectAprobados = null;
                } else {
                    listObservaciones = Anti.ConsulObserAnti(selectAnticipo.get(0).getCodAnticipo());
                    RequestContext.getCurrentInstance().execute("PF('observInt').show()");
                }
                break;

            case "Observacion":

                if (selectAprobados.size() > 1 || selectAprobados.isEmpty()) {
                    mbTodero.setMens("Seleccione unicamente un aniticipo para visualizar las observaciones");
                    mbTodero.warn();
                    selectAprobados = null;
                } else {
                    listObservaciones = Anti.ConsulObserAnti(selectAprobados.get(0).getCodAnticipo());
                    RequestContext.getCurrentInstance().execute("PF('observ').show()");
                }
                break;

            case "DevPop":
                if (selectAprobados.size() > 1 || selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo");
                    mbTodero.warn();
                    selectAprobados = null;
                } else {
                    int validar = 0;

                    for (int j = 0; j < selectAprobados.size(); j++) {

                        if (selectAprobados.get(i).getEstadoAnticipo().toLowerCase().equals("anulado")
                                || selectAprobados.get(i).getEstadoAnticipo().toLowerCase().equals("trasladado")) {
                            validar++;
                        }

                    }
                    if (validar == 0) {
                        RequestContext.getCurrentInstance().execute("PF('devAntiMasi').show()");

                    } else {
                        mbTodero.setMens("No se puede Aprobar un anticipo anulado o trasladado");
                        mbTodero.warn();

                    }

                }
                break;

            case "DevolverMasivo":

                if (selectAprobados.size() > 1 || this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo");
                    mbTodero.warn();
                    selectAprobados = null;

                } else {
                    Iterator<LogAnticipo> it = selectAprobados.iterator();
                    while (it.hasNext()) {
                        LogAnticipo SelectApro = it.next();
                        ArrayAnti += String.valueOf(SelectApro.getCodAnticipo()) + ",";
                        ArrayEstados += String.valueOf(SelectApro.getEstadoAnticipo() + ",");

                    }

                    if (ArrayEstados.contains("ANULADO")) {

                        mbTodero.setMens("No se puede Devolver un anticipo anulado");
                        mbTodero.warn();
                        selectAprobados = null;
                        ArrayEstados = "";
                        ArrayAnti = "";

                    } else {
                        int Result = 0;
                        ArrayAnti = ArrayAnti.substring(0, ArrayAnti.length() - 1);
                        ArrayAnti = "'" + ArrayAnti + "'";
                        ArrayEstados = ArrayEstados.substring(0, ArrayEstados.length() - 1);
                        ArrayEstados = "'" + ArrayEstados + "'";
                        Result = Ant.updateAnti(3, ArrayAnti, codEmpleado, ArrayEstados);
                        Ant.agregarObservacionAnticipo(getObservacionDevMasi(), String.valueOf(selectAprobados.get(0).getCodAnticipo()), codEmpleado);
                        ArrayAnti = "";
                        ArrayEstados = "";
                        mbTodero.setMens("Devolución Satisfactoria");
                        mbTodero.info();
                        selectAprobados = null;
                        setObservacionDevMasi("");
                    }
                }

                break;

            case "AprobacionMasiva":
                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo");
                    mbTodero.warn();

                } else {
                    Iterator<LogAnticipo> it = selectAprobados.iterator();
                    while (it.hasNext()) {
                        LogAnticipo SelectApro = it.next();
                        ArrayAnti += String.valueOf(SelectApro.getCodAnticipo()) + ",";
                        ArrayEstados += String.valueOf(SelectApro.getEstadoAnticipo() + ",").toLowerCase();

                    }

                    if (ArrayEstados.toLowerCase().contains("anulado") || ArrayEstados.toLowerCase().contains("trasladado")) {

                        mbTodero.setMens("No se puede Aprobar un anticipo anulado o trasladado");
                        mbTodero.warn();
                        selectAprobados = null;
                        ArrayEstados = "";
                        ArrayAnti = "";

                    } else {

                        ArrayAnti = ArrayAnti.substring(0, ArrayAnti.length() - 1);
                        ArrayAnti = "'" + ArrayAnti + "'";
                        ArrayEstados = ArrayEstados.substring(0, ArrayEstados.length() - 1);
                        ArrayEstados = "'" + ArrayEstados + "'";
                        Ant.updateAnti(1, ArrayAnti, codEmpleado, ArrayEstados);
                        ArrayAnti = "";
                        ArrayEstados = "";
                        mbTodero.setMens("Aprobacion Satisfactoria");
                        mbTodero.info();
                        selectAprobados = null;
                    }
                }
                break;

            case "Aprobado":

                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo");
                    mbTodero.warn();

                    //    RequestContext.getCurrentInstance().execute("PF('DlgConsultaAnti').show()");
                } else if (selectAprobados.get(0).getEstadoAnticipo().equalsIgnoreCase("Trasladado")) {
                    mbTodero.setMens("El anticipo fue trasladadoa otro cliente. No puede modificar el estado.");
                    mbTodero.warn();
                } else {

                    Iterator<LogAnticipo> it = selectAprobados.iterator();
                    while (it.hasNext()) {
                        LogAnticipo SelectAnti = it.next();
                        ArrayAnti += String.valueOf(SelectAnti.getCodAnticipo()) + ",";
                        ArrayEstados += String.valueOf(SelectAnti.getEstadoAnticipo() + ",");
                    }
                    int Result = 0;

                    ArrayAnti = ArrayAnti.substring(0, ArrayAnti.length() - 1);
                    ArrayAnti = "'" + ArrayAnti + "'";
                    ArrayEstados = ArrayEstados.substring(0, ArrayEstados.length() - 1);
                    ArrayEstados = "'" + ArrayEstados + "'";
                    Result = Ant.updateAnti(1, ArrayAnti, codEmpleado, ArrayEstados);
                    ArrayAnti = "";
                    ArrayEstados = "";
                    Query = "where Seg.FK_Cod_Avaluo = " + selectAprobados.get(0).getCodAvaluo();
                    setListaAnticipoDet(new ArrayList<>());
                    setListaAnticipoDet(Anti.consulGnral("", Query));
                    mbTodero.setMens("Aprobacion Satisfactoria, Verifique en la Opcion Consulta Anticipos");
                    mbTodero.info();

                }

                break;

            case "Transladar":
                listaTransEsTrue = null;
                ListPopUp = null;
                listaConsulClientesAva = null;
                filtrarAvaluo = 0;
                ObservacionAntTrans = "";
                CodClienteAnti = "";
                ValorClienteAnti = "";
                valorTex1 = 0;
                valorTex2 = 0;
                valorTex3 = 0;
                valorTex4 = 0;
                valorTex5 = 0;

                if (this.selectAprobados.size() > 1 || this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Seleccione unicamente un aniticipo");
                    mbTodero.warn();

                } else if (selectAprobados.get(0).getEstadoAnticipo().equals("Aprobado") || selectAprobados.get(0).getEstadoAnticipo().equals("Trasladado")
                        || selectAprobados.get(0).getEstadoAnticipo().equals("Anulado")) {
                    mbTodero.setMens("No se puede trasladar un anticipo ya aprobado, trasladado o anulado");
                    mbTodero.warn();
                    selectAprobados = null;
                    ArrayEstados = "";
                } else {
                    this.ListPopUp = new ArrayList<>();
                    this.ListPopUp = Ant.TransAnti(selectAprobados.get(0).getCodigoCliente(), selectAprobados.get(0).getCodAsesor(), selectAprobados.get(0).getCodAvaluo());
                    RequestContext.getCurrentInstance().execute("PF('TransAnti').show()");
                }
                break;

            //Boton aceptar translado
            case "AcepTrans":

                int validar = 0;
                if (Integer.valueOf(CodAnti) <= 0 || ObservacionAntTrans.isEmpty()) {
                    mbTodero.setMens("Ingrese el número de avalúo y observación.");
                    mbTodero.warn();
                } else {
                    for (int j = 0; j < ListPopUp.size(); j++) {
                        if (ListPopUp.get(j).getCodAvaluo().equals(CodAnti)) {

                            Ant.InsertAnticipo(3, selectAprobados.get(0).getCodAnticipo(), "", 0, "", ObservacionAntTrans, ListPopUp.get(j).getCodSeguimiento(), codEmpleado, 0, 0);
                            mbTodero.setMens("Traslado exitoso");
                            mbTodero.info();
                            Query = "where Seg.FK_Cod_Avaluo = " + selectAprobados.get(0).getCodAvaluo();
                            setListaAnticipoDet(new ArrayList<>());
                            setListaAnticipoDet(Anti.consulGnral("", Query));
                            RequestContext.getCurrentInstance().execute("PF('TransAnti').hide()");
                            validar++;
                            break;
                        }
                    }
                    if (validar == 0) {
                        mbTodero.setMens("El número de avaluo debe estar en la lista de avalúos asociados al cliente.");
                        mbTodero.warn();
                    }

                }

                break;

            case "Anular":
                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un anticipo");
                    mbTodero.warn();
                } else if (selectAprobados.get(0).getEstadoAnticipo().equalsIgnoreCase("Trasladado")) {
                    mbTodero.setMens("El anticipo fue trasladadoa otro cliente. No puede modificar el estado.");
                    mbTodero.warn();
                } else {
                    RequestContext.getCurrentInstance().execute("PF('AnuAnti').show()");

                }
                break;

            //Boton aceptar devolución
            case "AcepAnu":

                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo para Anular");
                    mbTodero.warn();

                } else if (this.ObservacionAnt.isEmpty()) {
                    mbTodero.setMens("El campo observación es obligatorio");
                    mbTodero.warn();

                } else {

                    Iterator<LogAnticipo> it = selectAprobados.iterator();
                    while (it.hasNext()) {
                        LogAnticipo SelectAnti = it.next();
                        ArrayAnti += String.valueOf(SelectAnti.getCodAnticipo()) + ",";
                        ArrayEstados += String.valueOf(SelectAnti.getEstadoAnticipo() + ",");
                        ArrayObserv = String.valueOf(SelectAnti.getCodAnticipo());
                        Ant.agregarObservacionAnticipo(getObservacionAnt(), ArrayObserv, codEmpleado);
                    }

                    ObservacionAnt = null;

                    int Result = 0;
                    ArrayAnti = ArrayAnti.substring(0, ArrayAnti.length() - 1);
                    ArrayAnti = "'" + ArrayAnti + "'";
                    ArrayEstados = ArrayEstados.substring(0, ArrayEstados.length() - 1);
                    ArrayEstados = "'" + ArrayEstados + "'";
                    Result = Ant.updateAnti(4, ArrayAnti, codEmpleado, ArrayEstados);

                    ArrayAnti = "";
                    ArrayEstados = "";
                    Query = "where Seg.FK_Cod_Avaluo = " + selectAprobados.get(0).getCodAvaluo();
                    setListaAnticipoDet(new ArrayList<>());
                    setListaAnticipoDet(Anti.consulGnral("", Query));
                    selectAprobados.clear();
                    mbTodero.setMens("Anulacion Satisfactoria, Verifique en la Opcion Consulta Anticipos");
                    mbTodero.info();
                }
                break;

            case "Devolver":
                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un anticipo para realizar la devolución");
                    mbTodero.warn();
                } else if (selectAprobados.get(0).getEstadoAnticipo().equalsIgnoreCase("Trasladado")) {
                    mbTodero.setMens("El anticipo fue trasladadoa otro cliente. No puede modificar el estado.");
                    mbTodero.warn();
                } else {
                    RequestContext.getCurrentInstance().execute("PF('DevAnti').show()");
                }
                break;

            //Boton Aceptar devolución
            case "AcepDev":
                if (this.selectAprobados.isEmpty()) {
                    mbTodero.setMens("Debe seleccionar un Anticipo para Devolver");
                    mbTodero.warn();

                } else if (ObservacionAntDev.isEmpty()) {
                    mbTodero.setMens("El campo observación es obligatorio");
                    mbTodero.warn();
                } else {

                    Iterator<LogAnticipo> it = selectAprobados.iterator();
                    while (it.hasNext()) {
                        LogAnticipo SelectAnti = it.next();
                        ArrayAnti += String.valueOf(SelectAnti.getCodAnticipo()) + ",";
                        ArrayObserv = String.valueOf(SelectAnti.getCodAnticipo());
                        ArrayEstados += String.valueOf(SelectAnti.getEstadoAnticipo() + ",");
                        Ant.agregarObservacionAnticipo(getObservacionAntDev(), ArrayObserv, codEmpleado);
                    }

                    ObservacionAntDev = "";
                    ArrayAnti = ArrayAnti.substring(0, ArrayAnti.length() - 1);
                    ArrayAnti = "'" + ArrayAnti + "'";
                    ArrayEstados = ArrayEstados.substring(0, ArrayEstados.length() - 1);
                    ArrayEstados = "'" + ArrayEstados + "'";
                    Ant.updateAnti(3, ArrayAnti, codEmpleado, ArrayEstados);
                    ArrayAnti = "";
                    ArrayEstados = "";
                    Query = "where Seg.FK_Cod_Avaluo = " + selectAprobados.get(0).getCodAvaluo();
                    setListaAnticipoDet(new ArrayList<>());
                    setListaAnticipoDet(Anti.consulGnral("", Query));
                    mbTodero.setMens("Devolución Satisfactoria, Verifique en la Opcion Consulta Anticipos");
                    mbTodero.info();
                    selectAprobados.clear();
                }
                break;

        }

    }

    public void LimpiarCajas() {
        mBArchivos.Arch.setValorParaPerFact1(0);
        mBArchivos.Arch.setValorParaPerFact2(0);
        mBArchivos.Arch.setValorParaPerFact3(0);
        mBArchivos.Arch.setValorParaPerFact4(0);
        mBArchivos.Arch.setValorParaPerFact5(0);
    }

    public void Clear() {
        try {
            this.CodAnti = "";
            mBArchivos.Arch.setFechaSol(null);
            mBArchivos.Arch.setFk_CuentaBanco("");
            mBArchivos.Arch.setValorAnt(0);
            mBArchivos.setValorAct(0);
            mBArchivos.setValorAvaluo(0);
            mBArchivos.Arch.setValorParaPerFact1(0);
            mBArchivos.Arch.setValorParaPerFact2(0);
            mBArchivos.Arch.setValorParaPerFact3(0);
            mBArchivos.Arch.setValorParaPerFact4(0);
            mBArchivos.Arch.setValorParaPerFact5(0);
            mBArchivos.CodClienteAnti = "";
            mBArchivos.ValorClienteAnti = "";
            mBArchivos.Arch.setValorAval(0);
            mBArchivos.Arch.setValorAnt(0);
            mBArchivos.Arch.setValorTotalAnti(0);
            mBArchivos.setOpcValorantiavalu("0");
            mBArchivos.setNameArch("");
            mBArchivos.Arch.setNameArch("");
        } catch (Exception e) {

        }

    }

    /**
     * Metodo para abrir el dialog para agregar una observacion para un
     * pre-radicacion que se encuentre en estado asignado
     *
     * @author Ayda Tatiana Lopez Moreno
     *
     */
    public void agregarTarifasAvaluo() {

        if ("".equals(this.ValoTarifaAval) || ValoTarifaAval == null) {
            mbTodero.setMens("Debe llenar el campo Tipo de tarifa");
            mbTodero.warn();
        }
        RequestContext.getCurrentInstance().execute("PF('DlgSlctAntiAvaluio').Show()");

    }

    public void addObserRadAnti() {
        try {

            Ant.agregarObservacionAnticipo(getObservacionAnticipo(), String.valueOf(mBArchivos.getSelectAnticipoRad().get(0).getCodAnticipo()), mBSesion.codigoMiSesion());
            this.setObservacionAnticipo("");

            RequestContext.getCurrentInstance().execute("PF('DlgAddObserRadAnti').hide()");
            mbTodero.setMens("Observación ingresada Satisfactoriamente");
            mbTodero.info();
        } catch (Exception e) {
            mbTodero.setMens("Error agregar observacion" + e);
            mbTodero.info();
        }
    }

    public void abrirPopUpRpart() {
        CodAnti = "";
        CodSeg = "";
        if (mBArchivos.getValorAct() == 1) {

            RequestContext.getCurrentInstance().execute("PF('DialogRepartAntic').show()");

        } else if ((mBArchivos.getArch().getValorAnt()) == 0) {
            mbTodero.setMens("Debe ingresar el valor del anticipo");
            mbTodero.warn();
        } else if (mBArchivos.getArch().getValorParaPerFact1() != 0 || mBArchivos.getArch().getValorParaPerFact2() != 0 || mBArchivos.getArch().getValorParaPerFact3() != 0 || mBArchivos.getArch().getValorParaPerFact4() != 0 || mBArchivos.getArch().getValorParaPerFact5() != 0) {
            mBArchivos.getArch().getValorParaPerFact1();
            mBArchivos.getArch().getValorParaPerFact2();
            mBArchivos.getArch().getValorParaPerFact3();
            mBArchivos.getArch().getValorParaPerFact4();
            mBArchivos.getArch().getValorParaPerFact5();

            RequestContext.getCurrentInstance().execute("PF('DialogRepartAntic').show()");

        } else {
            mBArchivos.getArch().setValorParaPerFact1(0);
            mBArchivos.getArch().setValorParaPerFact2(0);
            mBArchivos.getArch().setValorParaPerFact3(0);
            mBArchivos.getArch().setValorParaPerFact4(0);
            mBArchivos.getArch().setValorParaPerFact5(0);

            RequestContext.getCurrentInstance().execute("PF('DialogRepartAntic').show()");

        }
    }

    public void abrirDialogParticioinanticipo() {
        try {
            if ("1".equals(mBArchivos.getOpcValorantiavalu())) {
                if (mBArchivos.Arch.getValorAval() != 0 && mBArchivos.Arch.getValorAnt() != 0) {
                    if (mBArchivos.Arch.getValorAval() < mBArchivos.Arch.getValorAnt()) {
                        mBArchivos.setSegmto(1);
                        abrirPopUpRpart();
                    } else {
                        mbTodero.setMens("El valor no puede ser igual o superior al valor general de la consignación");
                        mbTodero.warn();
                    }

                }

            } else {
                abrirPopUpRpart();
            }

        } catch (NumberFormatException e) {
            mbTodero.setMens("Error abrirDialogParticioinanticipo" + e);
            mbTodero.warn();
        }
    }

    public void ValorClienteAnti() {
        if (mBArchivos.getListClientesPersonasAFacturarAntic().size() > 0) {
            /*      ListaDetCliente =  mBArchivos.Arch.ValorAntClientes(get)*/

        }

    }

    public void ValidarParticion() {
        int Suma = mBArchivos.Arch.getValorParaPerFact1() + mBArchivos.Arch.getValorParaPerFact2() + mBArchivos.Arch.getValorParaPerFact3() + mBArchivos.Arch.getValorParaPerFact4() + mBArchivos.Arch.getValorParaPerFact5();

        if (mBArchivos.isValorGenrl() == true && mBArchivos.isValorAnticp() == false) {
            ValorParticion = mBArchivos.Arch.getValorAnt();
        } else if (mBArchivos.isValorAnticp() == true && mBArchivos.isValorGenrl() == true) {
            ValorParticion = mBArchivos.Arch.getValorAval();
        }

        if (Suma > ValorParticion) {
            mbTodero.setMens("El valor supera al monto del anticipo");
            mbTodero.warn();
        } else if (Suma < ValorParticion) {
            mbTodero.setMens("El valor es inferior  al monto del anticipo");
            mbTodero.warn();
        } else {
            int cont = 1;
            String Res = "";

            int ValorAnti = 0;
            ValorClienteAnti = "";
            CodClienteAnti = "";

            if (mBArchivos.getListClientesPersonasAFacturarAntic().size() > 0) {
                Iterator<LogCliente> it = mBArchivos.getListClientesPersonasAFacturarAntic().iterator();
                while (it.hasNext()) {
                    LogCliente PersFac = it.next();
                    switch (cont) {
                        case 1:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact1();
                            break;
                        case 2:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact2();
                            break;
                        case 3:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact3();
                            break;
                        case 4:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact4();
                            break;
                        case 5:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact5();
                            break;

                    }
                    cont = cont + 1;
                    if (ValorAnti != 0) {
                        /*Concatenar Valores para emvio de parametros en de mi procedimiento almacenado  */
                        this.CodClienteAnti += String.valueOf(PersFac.getCodCliente()) + ",";
                        this.ValorClienteAnti += String.valueOf(ValorAnti) + ",";

                    }
                }
                this.CodClienteAnti = this.CodClienteAnti.substring(0, this.CodClienteAnti.length() - 1);
                this.ValorClienteAnti = this.ValorClienteAnti.substring(0, this.ValorClienteAnti.length() - 1);
                mBArchivos.setCodClienteAnti(CodClienteAnti);
                mBArchivos.setValorClienteAnti(ValorClienteAnti);

                RequestContext.getCurrentInstance().execute("PF('DialogRepartAntic').hide()");

            } else {

                //Para entidades 
                Iterator<LogEntidad> it1 = mBArchivos.getListEntidadesPersonasAFacturarAntic().iterator();
                while (it1.hasNext()) {
                    LogEntidad EntFac = it1.next();
                    switch (cont) {
                        case 1:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact1();
                            break;
                        case 2:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact2();
                            break;
                        case 3:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact3();
                            break;
                        case 4:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact4();
                            break;
                        case 5:
                            ValorAnti = mBArchivos.Arch.getValorParaPerFact5();
                            break;

                    }
                    cont = cont + 1;
                    if (ValorAnti != 0) {

                        /*Concatenar Valores para emvio de parametros en de mi procedimiento almacenado  */
                        this.CodClienteAnti += String.valueOf(EntFac.getFk_EntidadAsesor()) + ",";
                        this.ValorClienteAnti += String.valueOf(ValorAnti) + ",";

                    }
                }

                this.CodClienteAnti = this.CodClienteAnti.substring(0, this.CodClienteAnti.length() - 1);
                this.ValorClienteAnti = this.ValorClienteAnti.substring(0, this.ValorClienteAnti.length() - 1);
                mBArchivos.setCodEntidAnti(CodClienteAnti);
                mBArchivos.setValorEntdAnti(ValorClienteAnti);

                RequestContext.getCurrentInstance().execute("PF('DialogRepartAntic').hide()");
                LimpiarCajas();

            }
        }

    }

    public void onCargarEstadosSelecc(String tipo) {
        String OPciones = "";
        try {
            switch (tipo) {

                case "":
                    SelectListEstado = new ArrayList<>();
                    for (int j = 0; j <= this.ListEstado.size() - 1; j++) {
                        String codigo = ListEstado.get(j).toString();
                        if (null != codigo) {
                            switch (codigo) {
                                case "P":
                                    OPciones = "PENDIENTE";
                                    break;
                                case "D":
                                    OPciones = "DEVUELTO";
                                    break;

                                case "A":
                                    OPciones = "APROBADO";
                                    break;
                                case "Anu":
                                    OPciones = "ANULADO";
                                    break;
                                case "T":
                                    OPciones = "TRASLADADO";
                                    break;

                            }
                        }
                        SelectListEstado.add(new SelectItem(codigo, OPciones));
                    }
                    break;
                case "inicializar":
                    SelectListEstado = new ArrayList<>();
                    OPciones = "PENDIENTE";
                    String codigo = "P";
                    SelectListEstado.add(new SelectItem(codigo, OPciones));
                    break;
            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }

    private List<LogAnticipo> ClienteSegAnt;

    private String CodAnti = "";
    private String CodSeg = "";
    private String Estado = "";

    public void EliminarModAnti(int Oper) {

        int Res2 = 0;
        //Oper = a uno elimina
        //Oper =  2 actualiza
        CodAnti = "";
        CodSeg = "";
        if (mBArchivos.getSelectAnticipoRad().size() > 0) {

            Iterator<LogAnticipo> it = mBArchivos.getSelectAnticipoRad().iterator();
            while (it.hasNext()) {
                LogAnticipo Anticipo = it.next();
                Estado = Anticipo.getEstadoAnticipo();
                if ("Pendiente".equals(Estado) || "Devuelto".equals(Estado)) {
                    this.CodAnti += String.valueOf(Anticipo.getCodAnticipo()) + ",";
                    this.CodSeg = String.valueOf(Anticipo.getCodSeguimiento());
                } else if (Oper == 2 && mBArchivos.getSelectAnticipoRad().size() > 1) {
                    mbTodero.setMens("Seleccione solo un registro para su modificación.");
                    mbTodero.info();
                } else {
                    mbTodero.setMens("Verifique que el estado del anticipo no este Aprobado, anulado o trasladado.");
                    mbTodero.info();
                    Oper = 0;
                    mBArchivos.setValorAct(0);
                }
            }

        } else {

            mbTodero.setMens("Debe seleccionar al menos un anticipo");
            mbTodero.warn();
            Clear();
            Oper = 0;
        }

        if (Oper == 1) {

            Anti = new LogAnticipo();
            Anti.EliminarModfAntp(mBArchivos.getSelectAnticipoRad().get(0).getCodAnticipo(), mBArchivos.getSelectAnticipoRad().get(0).getPath());
            mbTodero.setMens("Eliminacion de Anticipo satisfactoria");
            mbTodero.warn();
            Clear();
            mBArchivos.setEstadoModifica(false);
            mBArchivos.setSelectAnticipoRad(null);
            Oper = 0;

        } else if (Oper == 2) {
            mBArchivos.setEstadoModifica(true);

            try {
                //CargarAnticipo();

                SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                String strFecha = mBArchivos.getSelectAnticipoRad().get(0).getFechaAnticipoConsignacion();
                Date fecha = null;
                fecha = Format.parse(strFecha);
                mBArchivos.setOpcValorantiavalu((mBArchivos.getSelectAnticipoRad().get(0).getMultipleAva().equals("No")) ? "0" : "1");
                mBArchivos.Arch.setValorAnt(Integer.valueOf(mBArchivos.getSelectAnticipoRad().get(0).getValorAnticipo().replace(",", "")));
                mBArchivos.setValorAnticp((mBArchivos.getOpcValorantiavalu().equals("1")));
                mBArchivos.Arch.setValorAval((mBArchivos.getSelectAnticipoRad().get(0).getMultipleAva().equals("No")) ? 0 : Double.valueOf(mBArchivos.getSelectAnticipoRad().get(0).getValorParcial().replace(",", "")));
                mBArchivos.Arch.setFechaSol(fecha);
                mBArchivos.Arch.setFk_CuentaBanco(String.valueOf(mBArchivos.getSelectAnticipoRad().get(0).getCodCuenta()));
                mBArchivos.Arch.setValorParaPerFact1(Integer.valueOf(mBArchivos.getSelectAnticipoRad().get(0).getValorCorrespondiente().replace(",", "")));
                mBArchivos.Arch.setCodAnt(mBArchivos.getSelectAnticipoRad().get(0).getCodAnticipo());

            } catch (ParseException ex) {
                Logger.getLogger(BeanAnticipo.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    /**
     * Metodo para abrir el dialog para agregar una observacion para un
     * pre-radicacion que se encuentre en estado asignado
     *
     * @author Ayda Tatiana Lopez Moreno
     *
     */
    public void onAbrirDialogAgregarObserv() {
        try {
            this.setObservacionAnticipo("");
            if (mBArchivos.getSelectAnticipoRad().size() == 1) {

                ListObserAnti = Anti.ConsulObserAnti(mBArchivos.getSelectAnticipoRad().get(0).getCodAnticipo());
                RequestContext.getCurrentInstance().execute("PF('DlgAddObserRadAnti').show()");

            } else {
                mbTodero.setMens("Debe seleccionar un solo registro");
                mbTodero.warn();
            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".onAbrirDialogAgregarObserv()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    int i = 0;
    //Cargar la informacion de Anticipos para Facturacion 

    private List<LogEntidad> Lista = new ArrayList<>();

    public List<LogEntidad> getLista() {
        return Lista;
    }

    public void setLista(List<LogEntidad> Lista) {
        this.Lista = Lista;
    }

//Tatiana 
    //Metodo para Traaer las facturas asociadas al cliente pendientes por imprimir en Facturacion
    //Tatiana 
    //Mod 17/03/2016
    LogFacturacion Fact = new LogFacturacion();

    //Tatiana
    public List<LogFacturacion> getListaFactAnt() {
        return ListaFactAnt;
    }

    public void setListaFactAnt(List<LogFacturacion> ListaFactAnt) {
        this.ListaFactAnt = ListaFactAnt;
    }
    private List<LogFacturacion> ListaFactAnt = new ArrayList<>();

    private List<LogFacturacion> selectListFactAscd;

    public List<LogFacturacion> getSelectListFactAscd() {
        return selectListFactAscd;
    }

    public void setSelectListFactAscd(List<LogFacturacion> selectListFactAscd) {
        this.selectListFactAscd = selectListFactAscd;
    }

    public void CargarNFactXCliente(int Tipo, int CodSeg) {
        try {

            Anti.setTipoSelPers(Tipo);
            int CodPersn = 0;
            if (mBArchivos.getListEntidadesPersonasAFacturarAntic().size() > 0) {
                CodPersn = mBArchivos.getListEntidadesPersonasAFacturarAntic().get(Tipo).getCodEntidad();

                this.setListaFactAnt(Fact.NFactAsocPorClient(CodSeg, CodPersn, "Entidad"));

            } else if (mBArchivos.getListClientesPersonasAFacturarAntic().size() > 0) {
                CodPersn = mBArchivos.getListClientesPersonasAFacturarAntic().get(Tipo).getCodCliente();

                this.setListaFactAnt(Fact.NFactAsocPorClient(CodSeg, CodPersn, "Cliente"));

            }

            RequestContext.getCurrentInstance().execute("PF('FactAsocd').show()");

        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();

        }

    }

    //Tatiana
    //Cajas de raparticion
    public int getCajaRepar1() {
        return cajaRepar1;
    }

    public void setCajaRepar1(int cajaRepar1) {
        this.cajaRepar1 = cajaRepar1;
    }

    public int getCajaRepar2() {
        return cajaRepar2;
    }

    public void setCajaRepar2(int cajaRepar2) {
        this.cajaRepar2 = cajaRepar2;
    }

    public int getCajaRepar3() {
        return cajaRepar3;
    }

    public void setCajaRepar3(int cajaRepar3) {
        this.cajaRepar3 = cajaRepar3;
    }

    public int getCajaRepar4() {
        return cajaRepar4;
    }

    public void setCajaRepar4(int cajaRepar4) {
        this.cajaRepar4 = cajaRepar4;
    }

    public int getCajaRepar5() {
        return cajaRepar5;
    }

    public void setCajaRepar5(int cajaRepar5) {
        this.cajaRepar5 = cajaRepar5;
    }

    public int getParticionFactura() {
        return ParticionFactura;
    }

    public void setParticionFactura(int ParticionFactura) {
        this.ParticionFactura = ParticionFactura;
    }
    private int cajaRepar1 = 0;
    private int cajaRepar2 = 0;
    private int cajaRepar3 = 0;
    private int cajaRepar4 = 0;
    private int cajaRepar5 = 0;
    private int ParticionFactura = 0;

    public void SeleccFac() {
        if (this.getSelectListFactAscd().size() > 0) {
            int NFactSelct;
            switch (Ant.getTipoSelPers()) {
                case 0:
                    NFactSelct = this.getSelectListFactAscd().get(0).getCodFactura();
                    this.setCajaRepar1(NFactSelct);
                    break;
                case 1:
                    NFactSelct = this.getSelectListFactAscd().get(0).getCodFactura();
                    this.setCajaRepar2(NFactSelct);
                    break;
                case 2:
                    NFactSelct = this.getSelectListFactAscd().get(0).getCodFactura();
                    this.setCajaRepar3(NFactSelct);
                    break;
                case 3:
                    NFactSelct = this.getSelectListFactAscd().get(0).getCodFactura();
                    this.setCajaRepar4(NFactSelct);
                    break;
                case 4:
                    NFactSelct = this.getSelectListFactAscd().get(0).getCodFactura();
                    this.setCajaRepar5(NFactSelct);
                    break;
            }
        } else {
            mbTodero.setMens("Debe seleccionar una factura");
            mbTodero.info();
        }
        RequestContext.getCurrentInstance().execute("PF('FactAsocd').hide()");

    }
    //Tatiana
    int MaxFact = 0;

    public void CargarNFactura() {
        MaxFact = Fact.MaxNoFact();
        this.setCajaRepar1(MaxFact = MaxFact + 1);
        this.setCajaRepar2(MaxFact = MaxFact + 1);
        this.setCajaRepar3(MaxFact = MaxFact + 1);
        this.setCajaRepar4(MaxFact = MaxFact + 1);
        this.setCajaRepar5(MaxFact = MaxFact + 1);
    }

    public void ValidarCajasFactu() {

        int sum = 0;
        LogAnticipo Antcp = new LogAnticipo();
        sum = this.getCajaRepar1() + this.getCajaRepar2() + this.getCajaRepar3() + this.getCajaRepar4() + this.getCajaRepar5();
        int e = (int) Fact.getValorNetoFacturado();

        if (sum == e) {
            this.setParticionFactura(sum);
        } else {
            mbTodero.setMens("Los valores a facturar no concuerdan con el neto ");
            mbTodero.warn();

        }

    }

    int Valor = 0;

    public void CargarAnticpFactura(int CodAvaluo) {
        //Tatiana
        //Carge la lista con la informacion de Reparticion de Anticiopos
        //setLista(Ent.ConsultaReparticion("Cliente_SegAnt", "FK_Cod_Cliente",  String.valueOf(CodAvaluo)));  
        //setLista(Ent.ConsultaReparticion("Asesor_SegAnt", "FK_Cod_Asesor",  String.valueOf(CodAvaluo)));
        //Realiza la consulta y verifica tipo de persona realzo el avaluo Cliente o Entidad.
        if (mBArchivos.getListClientesPersonasAFacturarAntic().size() > 0) {
            for (int j = 0; j < mBArchivos.getListClientesPersonasAFacturarAntic().size(); j++) {
                //Tatiana
                //Carga nombre del cliente o la entidad //
                //Carga el valor total por anticipo para el cliente o la entidad 
                ClienteSegAnt = Anti.ConsultaReparticion("Cliente_SegAnt", "FK_Cod_Cliente", String.valueOf(CodAvaluo));
                for (int i = 0; i < ClienteSegAnt.size(); i++) {
                    if (ClienteSegAnt.get(i).getCodCliente() == mBArchivos.getListClientesPersonasAFacturarAntic().get(i).getCodCliente()) {
                        if (Integer.valueOf((ClienteSegAnt.get(i).getValorAnticipo())) != null) {
                            Valor = Integer.valueOf((ClienteSegAnt.get(i).getValorAnticipo()));

                        } else {
                            Valor = 0;

                        }

                        //    MaxFact = ClienteSegAnt.get(i).getMaxCodFact();
                        switch (i + 1) {
                            case 1:
                                mBArchivos.getArch().setValorParaPerFact1(Valor);

                                break;
                            case 2:
                                mBArchivos.getArch().setValorParaPerFact2(Valor);
                                //           Anti.setCajaRepar2(MaxFact = MaxFact + 1);
                                break;
                            case 3:
                                mBArchivos.getArch().setValorParaPerFact3(Valor);
                                //         Anti.setCajaRepar3(MaxFact = MaxFact + 2);
                                break;
                            case 4:
                                mBArchivos.getArch().setValorParaPerFact4(Valor);
                                //       Anti.setCajaRepar4(MaxFact = MaxFact + 3);
                                break;
                            case 5:
                                mBArchivos.getArch().setValorParaPerFact5(Valor);
                                //    Anti.setCajaRepar5( MaxFact = MaxFact + 4);
                                break;
                        }
                    }
                }

            }

        } else if (mBArchivos.getListEntidadesPersonasAFacturarAntic().size() > 0) {
            ClienteSegAnt = Anti.ConsultaReparticion("Asesor_SegAnt", "FK_Cod_Asesor", String.valueOf(CodAvaluo));
            for (int i = 0; i < ClienteSegAnt.size(); i++) {
                if (ClienteSegAnt.get(i).getCodCliente() == mBArchivos.getListEntidadesPersonasAFacturarAntic().get(i).getCodEntidad()) {
                    if (Integer.valueOf((ClienteSegAnt.get(i).getValorAnticipo())) != null) {
                        Valor = Integer.valueOf((ClienteSegAnt.get(i).getValorAnticipo()));
                    } else {
                        Valor = 0;
                    }
                    switch (i + 1) {
                        case 1:
                            mBArchivos.getArch().setValorParaPerFact1(Valor);
                            break;
                        case 2:
                            mBArchivos.getArch().setValorParaPerFact2(Valor);

                            break;
                        case 3:
                            mBArchivos.getArch().setValorParaPerFact3(Valor);

                            break;
                        case 4:
                            mBArchivos.getArch().setValorParaPerFact4(Valor);

                            break;
                        case 5:
                            mBArchivos.getArch().setValorParaPerFact5(Valor);
                            break;
                    }
                }
            }
        }

    }

}

/**
 * FIN Metodos de funcionalidad de la clase
 * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
 */
