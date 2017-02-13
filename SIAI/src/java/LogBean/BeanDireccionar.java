package LogBean;

import LogBeanConsultas.BeanConsultaFactu;
import LogBeanConsultas.BeanConsultaPreRad;
import LogBeanConsultas.BeanConsultaSeg;
import LogBeanConsultas.BeanConsultaRevi;
import LogBeanConsultas.BeanConsultaRad;
import Logic.LogAdministracion;
import Logic.LogAnticipo;
import Logic.LogAvaluo;
import Logic.LogCargueArchivos;
import Logic.LogCartera;
import Logic.LogCliente;
import Logic.LogConsulta;
import Logic.LogEmpleado;
import Logic.LogEntidad;
import Logic.LogEntregaYRevision;
import Logic.LogEnvio;
import Logic.LogFacturacion;
import Logic.LogPerito;
import Logic.LogPermiso;
import Logic.LogPreRadicacion;
import Logic.LogRadicacion;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso y manejo de los direccionamientos que
 * maneja la aplicacion de menus, submenus etc </b></p>
 *
 * @author Jhojan Stiven Rodriguez
 * @author Maira Alejandra Carvajal
 * @version 2.0.0
 * @since 01-12-2014 1.0.0
 *
 *
 */
@ManagedBean
@ViewScoped
public class BeanDireccionar {

    private String TemaSeleccionado;

    /**
     * Variables para el manejo de los titulos, url y visibilidades que se
     * muestran en toda la aplicacion
     *
     */
    private String nombre_pagina;
    private String nombre_pagina_interna;
    private String url_pagina = "fondoSIAI.xhtml";
    private String tituloMenu = "Bienvenidos a SIAI 2.0";
    private String url_pagina_Administracion;
    private String url_pagina_Pre_Radicacion;
    private String url_pagina_Radicacion;
    private String url_pagina_Anticipo;
    public String url_pagina_Seguimiento;
    public String url_pagina_Entrega;
    public String url_pagina_Revision;
    public String url_pagina_Facturacion;
    public String url_pagina_Cartera;
    private boolean opcionPNLValidacion = true;
    private boolean opcionPNLInformacion = false;
    private boolean opcionPNLRevGen = false;
    private boolean opcionPNLRevJur = false;
    private boolean opcionPNLRevTec = false;
    private int po1 = 486;
    private int po2 = 264;
    private int aquita = 0;
    private String PassValida;
    private boolean EstadotabRadicacion;
    private String tamañoPantalla1 = "auto";
    private String tamañoPantalla2 = "1%";
    ResultSet valCom = null;
    LogConsulta Con1 = new LogConsulta();

    /**
     * Formatos de fechas y hora *
     */
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat FormatCompleto = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    SimpleDateFormat FormatHora = new SimpleDateFormat("HH:mm");

    private String seleccionPreradica;

    ResultSet Dat = null;
    private boolean estadoActivacionGestor;
    private boolean ActivacionAprobacionRevision = false;
    private List<LogAdministracion> ListMenu = null;
    private LogAdministracion obcionseleccionada;

    /**
     * Variables que se conectan a las diferentes clases de el proyecto
     * NegociosISA > Logic*
     */
    LogAdministracion Adm = new LogAdministracion();
    List<LogFacturacion> ListPendientXImpri = null;
    LogAvaluo Ava = new LogAvaluo();
    LogEmpleado Emp = new LogEmpleado();
    LogPreRadicacion PreRad = new LogPreRadicacion();
    LogEntregaYRevision EntRev = new LogEntregaYRevision();
    LogPermiso Perm = new LogPermiso();
    LogCartera Cart = new LogCartera();
    LogRadicacion Rad = new LogRadicacion();
    LogFacturacion Fact = new LogFacturacion();
    LogEnvio Envi = new LogEnvio();
    LogFacturacion Fac = new LogFacturacion();
    LogPerito Per = new LogPerito();
    LogCargueArchivos CarArc = new LogCargueArchivos();

    private ResultSet DatInfoGeneralRevision;
    LogAnticipo Anti = new LogAnticipo();

    @PostConstruct
    public void init() {
        this.aquita = 0;
        RolGestor(mBSesion.codigoMiSesion());
    }

    /**
     * Constructor de la clase que carga segun el usuario los menus a los que
     * tiene acceso
     *
     *
     * @throws java.lang.Exception
     */
    public BeanDireccionar() throws Exception {
        try {
            BeanSesion MBSes = new BeanSesion();
            Adm.setCodEmp(Integer.parseInt(MBSes.getCod_empleado()));
            this.ListMenu = Adm.ConsulProcesEmple();
        } catch (IOException | NumberFormatException ex) {
            FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
        }
    }

    /**
     * Variable tipo BeanEmpleado para traer los atributos y metodos de el
     * ManagedBean BeanEmpleado.java
     *
     *
     * @see BeanEmpleado.java
     */
    @ManagedProperty("#{MBEmpleado}")
    private BeanEmpleado mBEmpleado;

    public BeanEmpleado getmBEmpleado() {
        return mBEmpleado;
    }

    public void setmBEmpleado(BeanEmpleado mBEmpleado) {
        this.mBEmpleado = mBEmpleado;
    }

    /**
     * Variable tipo BeanCartera para traer los atributos y metodos de el
     * ManagedBean BeanCartera.java
     *
     *
     * @see BeanFacturacion.java
     */
    @ManagedProperty("#{MBCartera}")
    private BeanCartera mBCartera;

    public BeanCartera getmBCartera() {
        return mBCartera;
    }

    public void setmBCartera(BeanCartera mBCartera) {
        this.mBCartera = mBCartera;
    }

    /**
     * Variable tipo BeanPerito para traer los atributos y metodos de el
     * ManagedBean BeanPerito.java
     *
     *
     * @see BeanPerito.java
     */
    @ManagedProperty("#{MBPerito}")
    private BeanPerito mBPerito;

    public BeanPerito getmBPerito() {
        return mBPerito;
    }

    public void setmBPerito(BeanPerito mBPerito) {
        this.mBPerito = mBPerito;
    }

    /**
     * Variable tipo BeanPreRadicacion para traer los atributos y metodos de el
     * ManagedBean BeanPreRadicacion.java
     *
     *
     * @see BeanPreRadicacion.java
     */
    @ManagedProperty("#{MBPreRadicacion}")
    private BeanPreRadicacion mBPreRadicacion;

    public BeanPreRadicacion getmBPreRadicacion() {
        return mBPreRadicacion;
    }

    public void setmBPreRadicacion(BeanPreRadicacion mBPreRadicacion) {
        this.mBPreRadicacion = mBPreRadicacion;
    }

    /**
     * Variable tipo BeanConsultaPreRad para traer los atributos y metodos de el
     * ManagedBean BeanConsultaPreRad.java
     *
     *
     * @see BeanConsultaPreRad.java
     */
    @ManagedProperty("#{MBConsultaPR}")
    private BeanConsultaPreRad mBConsultaPR;

    public BeanConsultaPreRad getmBConsultaPR() {
        return mBConsultaPR;
    }

    public void setmBConsultaPR(BeanConsultaPreRad mBConsultaPR) {
        this.mBConsultaPR = mBConsultaPR;
    }
    /**
     * Variable tipo BeanConsultaRad para traer los atributos y metodos de el
     * ManagedBean BeanConsultaRad.java
     *
     *
     * @see BeanConsultaPreRad.java
     */
    @ManagedProperty("#{MBConsultaFactu}")
    private BeanConsultaFactu mBConsultaFactu;

    public BeanConsultaFactu getmBConsultaFactu() {
        return mBConsultaFactu;
    }

    public void setmBConsultaFactu(BeanConsultaFactu mBConsultaFactu) {
        this.mBConsultaFactu = mBConsultaFactu;
    }

    /**
     * Variable tipo BeanConsultaRad para traer los atributos y metodos de el
     * ManagedBean BeanConsultaRad.java
     *
     *
     * @see BeanConsultaPreRad.java
     */
    @ManagedProperty("#{MBConsultaRad}")
    private BeanConsultaRad mBConsultaRad;

    public BeanConsultaRad getmBConsultaRad() {
        return mBConsultaRad;
    }

    public void setmBConsultaRad(BeanConsultaRad mBConsultaRad) {
        this.mBConsultaRad = mBConsultaRad;
    }

    /**
     * Variable tipo BeanAdministracion para traer los atributos y metodos de el
     * ManagedBean BeanAdministracion.java
     *
     *
     * @see BeanAdministracion.java
     */
    @ManagedProperty("#{MBAdministacion}")
    private BeanAdministracion mBAdministacion;

    public BeanAdministracion getmBAdministacion() {
        return mBAdministacion;
    }

    public void setmBAdministacion(BeanAdministracion mBAdministacion) {
        this.mBAdministacion = mBAdministacion;
    }

    /**
     * Variable tipo BeanEntidad para traer los atributos y metodos de el
     * ManagedBean BeanEntidad.java
     *
     *
     * @see BeanEntidad.java
     */
    @ManagedProperty("#{MBEntidad}")
    private BeanEntidad mBEntidad;

    public BeanEntidad getmBEntidad() {
        return mBEntidad;
    }

    public void setmBEntidad(BeanEntidad mBEntidad) {
        this.mBEntidad = mBEntidad;
    }

    /**
     * Variable tipo BeanUbicacion para traer los atributos y metodos de el
     * ManagedBean BeanUbicacion.java
     *
     *
     * @see BeanUbicacion.java
     */
    @ManagedProperty("#{MBUbicacion}")
    private BeanUbicacion MBUbicacion;

    public BeanUbicacion getMBUbicacion() {
        return MBUbicacion;
    }

    public void setMBUbicacion(BeanUbicacion MBUbicacion) {
        this.MBUbicacion = MBUbicacion;
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
     * Variable tipo BeanRadicacion para traer los atributos y metodos de el
     * ManagedBean BeanRadicacion.java
     *
     *
     * @see BeanRadicacion.java
     */
    @ManagedProperty("#{MBRadicacion}")
    private BeanRadicacion mBRadicacion;

    public BeanRadicacion getmBRadicacion() {
        return mBRadicacion;
    }

    public void setmBRadicacion(BeanRadicacion mBRadicacion) {
        this.mBRadicacion = mBRadicacion;
    }

    /**
     * Variable tipo BeanEntregaYRevision para traer los atributos y metodos de
     * el ManagedBean BeanEntregaYRevision.java
     *
     *
     * @see BeanEntregaYRevision.java
     */
    @ManagedProperty("#{MBEntregaYRevision}")
    private BeanEntregaYRevision mBEntregaYRevision;

    public BeanEntregaYRevision getmBEntregaYRevision() {
        return mBEntregaYRevision;
    }

    public void setmBEntregaYRevision(BeanEntregaYRevision mBEntregaYRevision) {
        this.mBEntregaYRevision = mBEntregaYRevision;
    }

    @ManagedProperty("#{MBConsulEntregaYRevision}")
    private BeanConsultaRevi mbConsulEntregaYRevision;

    public BeanConsultaRevi getMbConsulEntregaYRevision() {
        return mbConsulEntregaYRevision;
    }

    public void setMbConsulEntregaYRevision(BeanConsultaRevi mbConsulEntregaYRevision) {
        this.mbConsulEntregaYRevision = mbConsulEntregaYRevision;
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
     * Variable tipo BeanAvaluo para traer los atributos y metodos de el
     * ManagedBean BeanAvaluo.java
     *
     *
     * @see BeanAvaluo.java
     */
    @ManagedProperty("#{MBAvaluo}")
    private BeanAvaluo mbAvaluo;

    public BeanAvaluo getMbAvaluo() {
        return mbAvaluo;
    }

    public void setMbAvaluo(BeanAvaluo mbAvaluo) {
        this.mbAvaluo = mbAvaluo;
    }

    /**
     * Variable tipo BeanPermiso para traer los atributos y metodos de el
     * ManagedBean BeanPermiso.java
     *
     *
     * @see BeanPermiso.java
     */
    @ManagedProperty("#{MBPermiso}")
    private BeanPermiso mbPermiso;

    public BeanPermiso getMbPermiso() {
        return mbPermiso;
    }

    public void setMbPermiso(BeanPermiso mbPermiso) {
        this.mbPermiso = mbPermiso;
    }

    /**
     * Variable tipo BeanArchivos para traer los atributos y metodos de el
     * ManagedBean BeanArchivos.java
     *
     *
     * @see BeanArchivos.java
     */
    @ManagedProperty("#{MBArchivos}")
    private BeanArchivos mBArchivo;

    public BeanArchivos getmBArchivo() {
        return mBArchivo;
    }

    public void setmBArchivo(BeanArchivos mBArchivo) {
        this.mBArchivo = mBArchivo;
    }

    /**
     * Variable tipo BeanRegimen para traer los atributos y metodos de el
     * ManagedBean BeanRegimen.java
     *
     *
     * @see BeanRegimen.java
     */
    @ManagedProperty("#{MBRegimen}")
    private BeanRegimen mbRegimen;

    public BeanRegimen getMbRegimen() {
        return mbRegimen;
    }

    public void setMbRegimen(BeanRegimen mbRegimen) {
        this.mbRegimen = mbRegimen;
    }

    /**
     * Variable tipo BeanSeguimiento para traer los atributos y metodos de el
     * ManagedBean BeanSeguimiento.java
     *
     *
     * @see BeanSeguimiento.java
     */
    @ManagedProperty("#{MBSeguimiento}")
    private BeanSeguimiento mbSeguimiento;

    public BeanSeguimiento getMbSeguimiento() {
        return mbSeguimiento;
    }

    public void setMbSeguimiento(BeanSeguimiento mbSeguimiento) {
        this.mbSeguimiento = mbSeguimiento;
    }

    @ManagedProperty("#{MBConsultaSeg}")
    private BeanConsultaSeg mbConsSeguimiento;

    public BeanConsultaSeg getMbConsSeguimiento() {
        return mbConsSeguimiento;
    }

    public void setMbConsSeguimiento(BeanConsultaSeg mbConsSeguimiento) {
        this.mbConsSeguimiento = mbConsSeguimiento;
    }

    /**
     * Variable tipo BeanCliente para traer los atributos y metodos de el
     * ManagedBean BeanCliente.java
     *
     *
     * @see BeanCliente.java
     */
    @ManagedProperty("#{MBCliente}")
    private BeanCliente mBCliente;

    public BeanCliente getmBCliente() {
        return mBCliente;
    }

    public void setmBCliente(BeanCliente mBCliente) {
        this.mBCliente = mBCliente;
    }

    /**
     * Variable tipo BeanBitacora para traer los atributos y metodos de el
     * ManagedBean BeanBitacora.java
     *
     *
     * @see BeanBitacora.java
     */
    @ManagedProperty("#{beanBitacora}")
    private BeanBitacora mbBitacora;

    public BeanBitacora getMbBitacora() {
        return mbBitacora;
    }

    public void setMbBitacora(BeanBitacora mbBitacora) {
        this.mbBitacora = mbBitacora;
    }

    /**
     * Variable tipo BeanAnticipo para traer los atributos y metodos de el
     * ManagedBean BeanAnticipo.java
     *
     *
     * @see BeanAnticipo.java
     */
    @ManagedProperty("#{MBAnticipo}")
    private BeanAnticipo mBAnticipo;

    public BeanAnticipo getmBAnticipo() {
        return mBAnticipo;
    }

    public void setmBAnticipo(BeanAnticipo mBAnticipo) {
        this.mBAnticipo = mBAnticipo;
    }
    /**
     * Variable tipo BeanAnticipo para traer los atributos y metodos de el
     * ManagedBean BeanAnticipo.java
     *
     *
     * @see BeanAnticipo.java
     */
    @ManagedProperty("#{MBEnvio}")
    private BeanEnvio mBEnvio;

    public BeanEnvio getmBEnvio() {
        return mBEnvio;
    }

    public void setmBEnvio(BeanEnvio mBEnvio) {
        this.mBEnvio = mBEnvio;
    }

    /**
     * Variable tipo BeanFacturacion para traer los atributos y metodos de el
     * ManagedBean BeanFacturacion.java
     *
     *
     * @see BeanFacturacion.java
     */
    @ManagedProperty("#{MBFacturacion}")
    private BeanFacturacion mBFacturacion;

    public BeanFacturacion getmBFacturacion() {
        return mBFacturacion;
    }

    public void setmBFacturacion(BeanFacturacion mBFacturacion) {
        this.mBFacturacion = mBFacturacion;
    }

    public List<LogFacturacion> getListPendientXImpri() {
        return ListPendientXImpri;
    }

    public void setListPendientXImpri(List<LogFacturacion> ListPendientXImpri) {
        this.ListPendientXImpri = ListPendientXImpri;
    }

    /**
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @return
     */
    public String getUrl_pagina_Cartera() {
        return url_pagina_Cartera;
    }

    public void setUrl_pagina_Cartera(String url_pagina_Cartera) {
        this.url_pagina_Cartera = url_pagina_Cartera;
    }

    public LogAdministracion getAdm() {
        return Adm;
    }

    public void setAdm(LogAdministracion Adm) {
        this.Adm = Adm;
    }

    public String getTemaSeleccionado() {
        return TemaSeleccionado;
    }

    public void setTemaSeleccionado(String TemaSeleccionado) {
        this.TemaSeleccionado = TemaSeleccionado;
    }

    public String getNombre_pagina() {
        return nombre_pagina;
    }

    public void setNombre_pagina(String nombre_pagina) {
        this.nombre_pagina = nombre_pagina;
    }

    public String getNombre_pagina_interna() {
        return nombre_pagina_interna;
    }

    public void setNombre_pagina_interna(String nombre_pagina_interna) {
        this.nombre_pagina_interna = nombre_pagina_interna;
    }

    public String getUrl_pagina() {
        return url_pagina;
    }

    public void setUrl_pagina(String url_pagina) {
        this.url_pagina = url_pagina;
    }

    public String getTituloMenu() {
        return tituloMenu;
    }

    public void setTituloMenu(String tituloMenu) {
        this.tituloMenu = tituloMenu;
    }

    public String getUrl_pagina_Administracion() {
        return url_pagina_Administracion;
    }

    public void setUrl_pagina_Administracion(String url_pagina_Administracion) {
        this.url_pagina_Administracion = url_pagina_Administracion;
    }

    public String getUrl_pagina_Pre_Radicacion() {
        return url_pagina_Pre_Radicacion;
    }

    public void setUrl_pagina_Pre_Radicacion(String url_pagina_Pre_Radicacion) {
        this.url_pagina_Pre_Radicacion = url_pagina_Pre_Radicacion;
    }

    public String getUrl_pagina_Radicacion() {
        return url_pagina_Radicacion;
    }

    public void setUrl_pagina_Radicacion(String url_pagina_Radicacion) {
        this.url_pagina_Radicacion = url_pagina_Radicacion;
    }

    public String getUrl_pagina_Anticipo() {
        return url_pagina_Anticipo;
    }

    public void setUrl_pagina_Anticipo(String url_pagina_Anticipo) {
        this.url_pagina_Anticipo = url_pagina_Anticipo;
    }

    public String getUrl_pagina_Entrega() {
        return url_pagina_Entrega;
    }

    public void setUrl_pagina_Entrega(String url_pagina_Entrega) {
        this.url_pagina_Entrega = url_pagina_Entrega;
    }

    public String getUrl_pagina_Revision() {
        return url_pagina_Revision;
    }

    public void setUrl_pagina_Revision(String url_pagina_Revision) {
        this.url_pagina_Revision = url_pagina_Revision;
    }

    public String getUrl_pagina_Seguimiento() {
        return url_pagina_Seguimiento;
    }

    public void setUrl_pagina_Seguimiento(String url_pagina_Seguimiento) {
        this.url_pagina_Seguimiento = url_pagina_Seguimiento;
    }

    public String getUrl_pagina_Facturacion() {
        return url_pagina_Facturacion;
    }

    public void setUrl_pagina_Facturacion(String url_pagina_Facturacion) {
        this.url_pagina_Facturacion = url_pagina_Facturacion;
    }

    public boolean isEstadoActivacionGestor() {
        return estadoActivacionGestor;
    }

    public void setEstadoActivacionGestor(boolean estadoActivacionGestor) {
        this.estadoActivacionGestor = estadoActivacionGestor;
    }

    public boolean isActivacionAprobacionRevision() {
        return ActivacionAprobacionRevision;
    }

    public void setActivacionAprobacionRevision(boolean ActivacionAprobacionRevision) {
        this.ActivacionAprobacionRevision = ActivacionAprobacionRevision;
    }

    public void asignar_url(String url_inicial) {
        this.nombre_pagina = url_inicial;
    }

    public List<LogAdministracion> getListMenu() {
        return ListMenu;
    }

    public void setListMenu(List<LogAdministracion> ListMenu) {
        this.ListMenu = ListMenu;
    }

    public LogAdministracion getObcionseleccionada() {
        return obcionseleccionada;
    }

    public void setObcionseleccionada(LogAdministracion obcionseleccionada) {
        this.obcionseleccionada = obcionseleccionada;
    }

    public boolean isOpcionPNLValidacion() {
        return opcionPNLValidacion;
    }

    public void setOpcionPNLValidacion(boolean opcionPNLValidacion) {
        this.opcionPNLValidacion = opcionPNLValidacion;
    }

    public boolean isOpcionPNLInformacion() {
        return opcionPNLInformacion;
    }

    public void setOpcionPNLInformacion(boolean opcionPNLInformacion) {
        this.opcionPNLInformacion = opcionPNLInformacion;
    }

    public boolean isOpcionPNLRevGen() {
        return opcionPNLRevGen;
    }

    public void setOpcionPNLRevGen(boolean opcionPNLRevGen) {
        this.opcionPNLRevGen = opcionPNLRevGen;
    }

    public boolean isOpcionPNLRevJur() {
        return opcionPNLRevJur;
    }

    public void setOpcionPNLRevJur(boolean opcionPNLRevJur) {
        this.opcionPNLRevJur = opcionPNLRevJur;
    }

    public boolean isOpcionPNLRevTec() {
        return opcionPNLRevTec;
    }

    public void setOpcionPNLRevTec(boolean opcionPNLRevTec) {
        this.opcionPNLRevTec = opcionPNLRevTec;
    }

    public int getPo1() {
        return po1;
    }

    public void setPo1(int po1) {
        this.po1 = po1;
    }

    public int getPo2() {
        return po2;
    }

    public void setPo2(int po2) {
        this.po2 = po2;
    }

    public int getAquita() {
        return aquita;
    }

    public void setAquita(int aquita) {
        this.aquita = aquita;
    }

    public String getPassValida() {
        return PassValida;
    }

    public void setPassValida(String PassValida) {
        this.PassValida = PassValida;
    }

    public boolean isEstadotabRadicacion() {
        return EstadotabRadicacion;
    }

    public void setEstadotabRadicacion(boolean EstadotabRadicacion) {
        this.EstadotabRadicacion = EstadotabRadicacion;
    }

    public String getTamañoPantalla1() {
        return tamañoPantalla1;
    }

    public void setTamañoPantalla1(String tamañoPantalla1) {
        this.tamañoPantalla1 = tamañoPantalla1;
    }

    public String getTamañoPantalla2() {
        return tamañoPantalla2;
    }

    public void setTamañoPantalla2(String tamañoPantalla2) {
        this.tamañoPantalla2 = tamañoPantalla2;
    }

    public String getSeleccionPreradica() {
        return seleccionPreradica;
    }

    public void setSeleccionPreradica(String seleccionPreradica) {
        this.seleccionPreradica = seleccionPreradica;
    }

    /**
     * FIN Metodos get y set de todas las variables / atributos, listas, etc que
     * se utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     * Metodos de funcionalidad de la clase /** FIN Metodos get y set de todas
     * las variables / atributos, listas, etc que se utilizaran para enviar y
     * traer datos a las respectivas variables ---funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     * Metodo que se utiliza para la verificacion del rol de gestor del formato
     * de control y seguimiento
     *
     * @author Maira Alejandra Carvajal
     * @param Id int que contiene el codigo del empleado
     * @since 01-05-2015
     */
    private void RolGestor(int Id) {
        try {
            Logic.LogPermiso Permi = new LogPermiso();
            Dat = Permi.consulRolEmpleado(Id, "Gestor");
            if (Dat.next()) {
                this.estadoActivacionGestor = true;
            } else if (!Dat.next()) {
                this.estadoActivacionGestor = false;
            }
            Conexion.Conexion.CloseCon();
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".RolGestor()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para validar informacion de si el parametro de aprobacion se
     * encuentra activo o se encuentra false
     *
     * @author Maira Alejandra Carvajal
     * @since 01-05-2015
     */
    private void AprobacionRevision() {
        try {
            LogAdministracion Admn = new LogAdministracion();
            Dat = Admn.ConsulAprobRevi();
            if (Dat.next()) {
                this.ActivacionAprobacionRevision = "true".equals(Dat.getString("Resultado_Parametro"));
            }
            Conexion.Conexion.CloseCon();
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".AprobacionRevision()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que le asigna valor false a al variable estadoActivacionGestor
     *
     * @author Maira Alejandra Carvajal
     * @since 01-05-2015
     */
    private void lim() {
        try {
            this.estadoActivacionGestor = false;
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que direcciona a las diferentes ventanas de la aplicacion pro
     * modulos
     *
     * @author Maira Alejandra Carvajal
     * @param Modulo String que contiene el modilo al cual se direccionara
     * @param url_interna String que contiene la url del modulo
     * @since 01-05-2015
     */
    public void direccionarpormodulo(String Modulo, String url_interna) {
        try {
            Logic.LogPermiso Permis = new LogPermiso();
            Dat = Permis.consulRolEmpleadoEspe(mBSesion.codigoMiSesion(), "Radicador");
            if (Dat.next()) {
                seleccionar_url_menuprincipal(Modulo);
                this.url_Menu_Radica(url_interna);
            } else {
                this.seleccionar_url_menuprincipal("Inicio");
            }
            Conexion.Conexion.CloseCon();
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".direccionarpormodulo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que selecciona una url y realiza su respectiva redireccion a la
     * misma
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_inicial String que recibe como parametro wl nombre del modulo
     * al cual redireccionara
     * @since 01-05-2015
     */
    public void seleccionar_url_menuprincipal(String url_inicial) {
        try {
            this.nombre_pagina = "";
            this.nombre_pagina = url_inicial;
            switch (this.nombre_pagina) {
                case "Administracion":
                    this.tituloMenu = "Administracion";
                    this.url_pagina = "../Menus/Administracion/Menu_Administrativo.xhtml";
                    break;
                case "Pre-Radicacion":
                    mBPreRadicacion.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Pre-Radicacion";
                    this.url_pagina = "../Menus/Radicacion/Menu_Pre_Radicacion.xhtml";
                    break;
                case "Radicacion":
                    mBRadicacion.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Radicacion";
                    this.url_pagina = "../Menus/Radicacion/Menu_Radicacion.xhtml";
                    break;
                case "Archivos":
                    this.tituloMenu = "Archivos";
                    this.url_pagina = "../Menus/Archivos/Menu_Archivos.xhtml";
                    break;
                case "Anticipo":
                    this.tituloMenu = "Anticipo";
                    this.url_pagina = "../Menus/Facturacion/Menu_Anticipo.xhtml";
                    break;
                case "Seguimiento":
                    mbSeguimiento.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Seguimiento";
                    this.url_pagina = "../Menus/Seguimiento/Menu_Seguimiento.xhtml";
                    break;
                case "Inicio":
                    this.tituloMenu = "";
                    this.url_pagina = "fondoSIAI.xhtml";
                    break;
                case "Entrega":
                    AprobacionRevision();
                    mBEntregaYRevision.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Entrega";
                    this.url_pagina = "../Menus/Entrega/Menu_Entrega.xhtml";
                    break;
                case "Revision":
                    AprobacionRevision();
                    mBEntregaYRevision.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Revision";
                    boolean InfPermJ = Perm.ConsultaTipRevision(mBSesion.codigoMiSesion(), 1);
                    boolean InfPermT = Perm.ConsultaTipRevision(mBSesion.codigoMiSesion(), 2);
                    if (InfPermJ == true && InfPermT == true) {
                        opcionPNLRevGen = true;
                        opcionPNLRevJur = false;
                        opcionPNLRevTec = false;
                    } else if (InfPermJ == true) {
                        opcionPNLRevGen = false;
                        opcionPNLRevJur = true;
                        opcionPNLRevTec = false;
                    } else if (InfPermT == true) {
                        opcionPNLRevGen = false;
                        opcionPNLRevJur = false;
                        opcionPNLRevTec = true;
                    }
                    this.url_pagina = "../Menus/Entrega/Menu_Revision.xhtml";
                    break;
                case "Cartera":
                    this.tituloMenu = "Cartera";
                    this.url_pagina = "../Menus/Cartera/Menu_Cartera.xhtml";
                    break;
                case "Facturacion":
                    mBFacturacion.consultaTotalRegistrosTablas();
                    this.tituloMenu = "Facturacion";
                    this.url_pagina = "../Menus/Facturacion/Menu_Facturacion.xhtml";
                    break;
                case "Envio":
                    this.tituloMenu = "Envio";
                    this.url_pagina = "../Menus/Envio/Menu_Envio.xhtml";
                    break;
                case "Liquidacion":
                    this.tituloMenu = "Facturacion";
                    this.url_pagina = "../Menus/Facturacion/Menu_Facturacion.xhtml";
                    break;

                case "PQRS":
                    this.tituloMenu = "PQRS";
                    this.url_pagina = "../Menus/Pqrs/Menu_Pqrs.xhtml";
                    break;
                default:

                    this.tituloMenu = "";
                    this.url_pagina = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";

            ListMenu.clear();
            ListMenu = Adm.ConsulProcesEmple();
            this.tamañoPantalla1 = "auto";
            this.tamañoPantalla2 = "auto";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionar_url_menuprincipal()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que redirecciona a las url internas de el menu de administración
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna que contiene a que tipo de formulario se direccionara
     * @since 01-05-2015
     */
    public void url_MenusInternos(String url_interna) {
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna;
            switch (this.nombre_pagina_interna) {

                case "Mensaje_Corp":
                    mBSesion.setNewMensaje("");
                    RequestContext.getCurrentInstance().execute("PF('dlgCambioMensaje').show()");
                    break;

                case "Gestion_Empleado":
                    mBEmpleado.clearResetComponets();

                    mBEmpleado.getTipDocEmp().clear();
                    mBEmpleado.setTipDocEmp(mBEmpleado.getConsulTipDocEmp());

                    mBEmpleado.getCargaDep().clear();
                    mBEmpleado.cargDepto();

                    mBEmpleado.getCargEmp().clear();
                    mBEmpleado.setCargEmp(mBEmpleado.getConsulCargEmp());

                    mBEmpleado.getCargpisos().clear();
                    mBEmpleado.setCargpisos(mBEmpleado.getConsulPisos());

                    mBEmpleado.getEstEmp().clear();
                    mBEmpleado.setEstEmp(mBEmpleado.getConsulEstEmp());

                    mBEmpleado.getTipDocEmp().clear();
                    mBEmpleado.setTipDocEmp(mBEmpleado.getConsulTipDocEmp());

                    mBEmpleado.getCargJefes().clear();
                    mBEmpleado.setCargJefes(mBEmpleado.getCargJefeEmp());

                    mBEmpleado.setListEmp(new ArrayList<>());
                    mBEmpleado.setListEmp(mBEmpleado.Emp.ConsulAllEmpleados());

                    mBEmpleado.setCargEmpSeguimiento(mBEmpleado.getCargEmplSeguimiento());

                    mBEmpleado.getcargaRol();

                    MBUbicacion.setListDepto(MBUbicacion.Ubi.ConsulDepto());

                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.cargDepto();

                    mBEmpleado.getCargaCiu().clear();

                    mbPermiso.getcargaProEnt().clear();
                    mbPermiso.getcargaProEnt();

                    this.url_pagina_Administracion = "../Persona/GestionEmpleados.xhtml";
                    break;

                case "Gestion_Peritos":
                    mBPerito.clearComponents();
                    mBPerito.setListPerit(Per.ConsulAllPerito(0));

                    mBPerito.getCargTipDocPer().clear();
                    mBPerito.setCargTipDocPer(mBPerito.getConsulTipDocPer());

                    mBPerito.getCargEstPer().clear();
                    mBPerito.setCargEstPer(mBPerito.getConsulEstPer());

                    mBPerito.getCargRegPer().clear();
                    mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());

                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.setListDepto(MBUbicacion.Ubi.ConsulDepto());

                    mBPerito.getCargaCiu().clear();

                    MBUbicacion.cargDepto();
                    this.url_pagina_Administracion = "../Persona/GestionAvaluadores.xhtml";
                    break;

                case "Gestion_Estados":
                    mBAdministacion.limpiarAdministracion();
                    mbTodero.resetTable("formEst:EstTable");
                    mBAdministacion.setListEstado(mBAdministacion.Adm.ConsulEstad());
                    Conexion.Conexion.CloseCon();
                    mBAdministacion.getListEstado().clear();
                    mBAdministacion.setListEstado(mBAdministacion.Adm.ConsulEstad());
                    Conexion.Conexion.CloseCon();
                    if (mBAdministacion.getListEstado().size() > 0) {
                        mBAdministacion.AdmEstados.setCodEstado(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_Estado", "Estado"));
                    }
                    this.url_pagina_Administracion = "../Administracion/GestionEstados.xhtml";
                    break;

                case "GCargos":
                    mBAdministacion.limpiarAdministracion();
                    mbTodero.resetTable("FRMGesCargo:CargoTable");
                    mBAdministacion.setListCargos(mBAdministacion.Adm.ConsulCargo());
                    mBAdministacion.getListCargos().clear();
                    mBAdministacion.setListCargos(mBAdministacion.Adm.ConsulCargo());
                    if (mBAdministacion.getListCargos().size() > 0) {
                        mBAdministacion.AdmCargos.setCodCargo(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_Cargo", "Cargo"));
                    }
                    this.url_pagina_Administracion = "../Administracion/GestionCargos.xhtml";
                    break;

                case "ConsultaProductoEntidad":
                    mBAdministacion.limpiarAdministracion();
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    this.url_pagina_Administracion = "../Administracion/GestionProductoEntidad.xhtml";
                    break;

                case "ConsultaUbicacion":
                    mBAdministacion.limpiarAdministracion();
                    MBUbicacion.setListPisos(MBUbicacion.Ubi.ConsulPisos());
                    MBUbicacion.setListDepto(MBUbicacion.Ubi.ConsulDepto());
                    MBUbicacion.setListCiudad(MBUbicacion.Ubi.ConsulCiudad());
                    MBUbicacion.setEstadoRadioSeleccion("");
                    MBUbicacion.setEstDepto(false);
                    MBUbicacion.setEstCiu(false);
                    MBUbicacion.setEstPisos(false);

                    MBUbicacion.cargDepto();
                    this.url_pagina_Administracion = "../Administracion/GestionUbicaciones.xhtml";
                    break;

                case "ConsultaRoles":
                    mBAdministacion.limpiarAdministracion();
                    mbTodero.resetTable("FrmRol:RolTable");
                    mBAdministacion.setListRoles(mBAdministacion.Adm.ConsulRoles());
                    mBAdministacion.getListRoles().clear();
                    mBAdministacion.setListRoles(mBAdministacion.Adm.ConsulRoles());
                    if (mBAdministacion.getListRoles().size() > 0) {
                        mBAdministacion.AdmRoles.setCodRol(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_Roles", "Roles"));
                    }
                    mbPermiso.setCargProcesos(mbPermiso.cargaProcesos());
                    this.url_pagina_Administracion = "../Administracion/GestionRoles.xhtml";
                    break;

                case "ConsultaRegimen":
                    mBAdministacion.limpiarAdministracion();
                    mbRegimen.setCargaReg(mbRegimen.cargaTipRegimen());
                    this.url_pagina_Administracion = "../Administracion/GestionRegimen.xhtml";
                    break;

                case "ConsultaTipoDocumento":
                    mBAdministacion.limpiarAdministracion();
                    mbTodero.resetTable("formTipDoc:TipDocTable");
                    mBAdministacion.setListTipDocumento(mBAdministacion.Adm.ConsulTipDoc());
                    mBAdministacion.getListTipDocumento().clear();
                    mBAdministacion.setListTipDocumento(mBAdministacion.Adm.ConsulTipDoc());
                    if (mBAdministacion.getListTipDocumento().size() > 0) {
                        mBAdministacion.AdmTipDoc.setCodTipDocumento(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_TipDocumento", "Tipo_Documento"));
                    }

                    this.url_pagina_Administracion = "../Administracion/GestionTipoDocumento.xhtml";
                    break;

                case "GestionEntidades":
                    mBAdministacion.limpiarAdministracion();
                    mBEntidad.limpiar();
                    mBEntidad.limpiarCajasAsesor();
                    mBEntidad.limpiarCajasEntidad();
                    mBEntidad.limpiarCajasSucursales();

                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.cargDepto();

                    mBPerito.getCargRegPer().clear();
                    mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());

                    this.url_pagina_Administracion = "../Administracion/GestionEntidades.xhtml";
                    break;

                case "GestionEnvio":
                    mbTodero.resetTable("formTipDoc:TipDocTable");
                    mBAdministacion.setListEnvio(mBAdministacion.Adm.ConsulEnvio());
                    mBAdministacion.getListEnvio().clear();
                    mBAdministacion.setListEnvio(mBAdministacion.Adm.ConsulEnvio());
                    mBAdministacion.Ad.setCodigoParametro(mBAdministacion.Ad.ConsulCodigoPrincipalParametro("Cod_Parametro", "Enviar a"));
                    this.url_pagina_Administracion = "../Administracion/GestionEnviarA.xhtml";
                    break;

                case "GArchivos":
                    mBAdministacion.limpiarAdministracion();
                    mbTodero.resetTable("FRMGestArc:ArchivoTable");
                    mBAdministacion.setListArchivos(mBAdministacion.Adm.ConsulArchivos());
                    mBAdministacion.getListArchivos().clear();
                    mBAdministacion.setListArchivos(mBAdministacion.Adm.ConsulArchivos());
                    mBAdministacion.Adm.setCodParametro(mBAdministacion.Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Sol_Pre','Inf_Pre','Sol_Maq','Inf_Maq','Sol_Ens','Inf_Ens','Sol_Cli','Inf_Cli"));
                    this.url_pagina_Administracion = "../Administracion/GestionArchivos.xhtml";
                    break;

                case "ReglaArchivoAvaluo":

                    mBAdministacion.limpiarAdministracion();

                    //tipo de avaluo
                    mbAvaluo.getCargaTipAvaluo().clear();
                    mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());

                    //producto entidad
                    mBAdministacion.getProEnt().clear();
                    mBAdministacion.setProEnt(mBAdministacion.getConsulProEnt());

                    //tipo producto entidad
                    mBAdministacion.getTipProEnt().clear();
                    mBAdministacion.setTipProEnt(mBAdministacion.getConsulTipProEnt(1));

                    //tipo de archivo de cliente
                    mBAdministacion.getCargTipArchCli().clear();
                    mBAdministacion.setCargTipArchCli(mBAdministacion.getConsulArcCli());

                    //tipo de archivo de cliente
                    mBAdministacion.getCargTipArchAva().clear();

                    //regimen
                    mBPerito.getCargRegPer().clear();
                    mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());

                    //Tablas
                    mBAdministacion.getCargTabla().clear();
                    mBAdministacion.setCargTabla(mBAdministacion.getConsulTabla());

                    mBAdministacion.setCargModulo(mBAdministacion.getConsulModulo()); //modulos del sistema
                    //mBAdministacion.setCargTipArchAva(mBAdministacion.getConsulArcAva()); //archivos registrados de avaluos               

                    //limpiar componentes
                    mBAdministacion.setOpcionCondicion(false);
                    mBAdministacion.setOpcionArchivoAvaluo(false);
                    mBAdministacion.setOpcionListaChequeo(false);
                    mBAdministacion.setOpcionArchivoCliente(false);
                    mBAdministacion.setOpcionRegla("");

                    mBAdministacion.setAdm(new LogAdministracion());

                    //archivos de avaluos
                    mBAdministacion.setListArcAva(mBAdministacion.Adm.ConsulRegArcAva());
                    if (mBAdministacion.getListArcAva().size() > 0) {
                        mBAdministacion.AdmArchivAval.setCodArcAva(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_ArcAva", "Regla_ArcAvaluo"));
                        //  mBAdministacion.Adm.setCodArcAva(mBAdministacion.getListArcAva().get(0).getCodArcAva());
                    }

                    //archivos de clientes
                    mBAdministacion.setListArcCli(mBAdministacion.Adm.ConsulRegArcCli());
                    if (mBAdministacion.getListArcCli().size() > 0) {
                        mBAdministacion.AdmArchvClie.setCodArcCli(mBAdministacion.Adm.ConsulCodigoPrincipal("Cod_ArcCli", "Regla_ArcCliente"));
                        //    mBAdministacion.Adm.setCodArcCli(mBAdministacion.getListArcCli().get(0).getCodArcCli());
                    }

                    mBAdministacion.limpiarAdministracion();
                    mbPermiso.cargaProcesos();
                    mbPermiso.cargaProcesosExis();
                    this.url_pagina_Administracion = "../Administracion/GestionReglaArchivoAvaluo.xhtml";
                    break;

                case "GestionPlantillas":
                    mBAdministacion.setCargModulo(mBAdministacion.getConsulModulo());
                    this.url_pagina_Administracion = "../Administracion/GestionPlantillas.xhtml";
                    break;

                case "GestionTipoBienes":
                    mbAvaluo.setEstadoRadioSeleccionTipBien("");
                    this.url_pagina_Administracion = "../Administracion/GestionTiposBienes.xhtml";
                    break;

                case "GestionActividYRecordat":
                    mBAdministacion.setEstadoRadioSeleccionActiviYRecord("");
                    this.url_pagina_Administracion = "../Administracion/GestionAccionesYRecordat.xhtml";
                    break;

                case "GestionAnulaciones":
                    mBAdministacion.setListAnulaciones(new ArrayList<>());
                    mBAdministacion.setListAnulaciones(Adm.getMotivosAnulaciones());
                    this.url_pagina_Administracion = "../Administracion/GestionAnulaciones.xhtml";
                    break;
                case "GestionListaChequeo":
                    mBAdministacion.setListChequeoItems(new ArrayList<>());
                    mBAdministacion.setListChequeoItems(Adm.getItemsListaChequeo());
                    this.url_pagina_Administracion = "../Administracion/GestionListaChequeo.xhtml";
                    break;

                case "Devoluciones":
                    mBAdministacion.setListDevoluc(new ArrayList<>());
                    mBAdministacion.setListDevoluc(Adm.getMotivosDevoluciones(1, ""));
                    this.url_pagina_Administracion = "../Administracion/GestionDevoluciones.xhtml";
                    break;

                case "TiposContrucciones":
                    mBAdministacion.setListTipoConstrucc(new ArrayList<>());
                    mBAdministacion.setListTipoConstrucc(Adm.getItemsTipoConstruccion());
                    this.url_pagina_Administracion = "../Administracion/GestionTiposConstrucciones.xhtml";
                    break;

                case "ModificacionesInfome":
                    mBAdministacion.setListModificacionInf(new ArrayList<>());
                    mBAdministacion.setListModificacionInf(Adm.getItemsModificacion());
                    this.url_pagina_Administracion = "../Administracion/GestionModificacionesInforme.xhtml";
                    break;

                case "Medidas":
                    mBAdministacion.setListMedidas(new ArrayList<>());
                    mBAdministacion.setListMedidas(Adm.getItemsMedida());
                    this.url_pagina_Administracion = "../Administracion/GestionMedidas.xhtml";
                    break;

                case "TiemposAvaluos":
                    //producto entidad
                    mBAdministacion.getProEnt().clear();
                    mBAdministacion.setProEnt(mBAdministacion.getConsulProEnt());
                    mBAdministacion.setListTiemposAval(new ArrayList<>());
                    mBAdministacion.setListTiemposAval(Adm.getItemsTiempoAvaluo());
                    this.url_pagina_Administracion = "../Administracion/GestionTiemposAvaluos.xhtml";
                    break;

                case "BitacoraGeneral":
                    mbBitacora.setTipoProceso("");
                    mbBitacora.setCodigoavaluo("");
                    mbBitacora.setCodigoPreRadicac("");
                    mbBitacora.setCodigoRegist("");
                    mbBitacora.LimpiarBitacora();
                    this.url_pagina_Administracion = "../Administracion/Bitacora.xhtml";
                    break;

                case "BitacoraPreRadicacion":
                    mbBitacora.setTipoProceso("Pre");
                    mbBitacora.setCodigoavaluo("");
                    mbBitacora.setCodigoPreRadicac("");
                    mbBitacora.setCodigoRegist("");
                    mbBitacora.LimpiarBitacora();
                    this.url_pagina_Administracion = "../Administracion/Bitacora.xhtml";
                    break;

                case "BitacoraRadicacion":
                    mbBitacora.setTipoProceso("RadAval");
                    mbBitacora.setCodigoavaluo("");
                    mbBitacora.setCodigoPreRadicac("");
                    mbBitacora.setCodigoRegist("");
                    mbBitacora.LimpiarBitacora();
                    this.url_pagina_Administracion = "../Administracion/Bitacora.xhtml";
                    break;
                case "AprobImpresion":
                    Adm = new LogAdministracion();
                    Dat = Adm.ConsulAprobRevi();
                    try {
                        if (Dat.next()) {
                            mBAdministacion.setAprobacionRevision(Dat.getBoolean("Resultado_Parametro"));
                        }
                        Conexion.Conexion.CloseCon();
                        this.url_pagina_Administracion = "../Administracion/GestionAprobImpresion.xhtml";
                    } catch (SQLException e) {
                        mbTodero.setMens("Error: " + e.getSQLState());
                        mbTodero.fatal();
                    }
                    break;
                case "GestionTablasFacturacion":

                    mBFacturacion.limpiarCamposTablaFactur();

                    mBFacturacion.setListTablasFacturac(new ArrayList<>());
                    mBFacturacion.setListTablasFacturac(Fact.ConsultarTodasTablasFacturacionAdmin());

                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());

                    mBAdministacion.getProEnt().clear();
                    mBAdministacion.setProEnt(mBAdministacion.getConsulProEnt());

                    mBAdministacion.getTipInm().clear();
                    mBAdministacion.setTipInm(mBAdministacion.getTipoInmueble());
                    this.url_pagina_Administracion = "../Administracion/GestionTablasFacturacion.xhtml";
                    break;
                case "GestionImpuestos":

                    mBAdministacion.setListImpuestos(new ArrayList<>());
                    mBAdministacion.setListImpuestos(Adm.getItemsImpuesto());
                    this.url_pagina_Administracion = "../Administracion/GestionImpuestos.xhtml";
                    break;
                case "GestionTablasImpuesto":
                    Calendar fecha = new GregorianCalendar();
                    Date fecha1 = new Date();
                    fecha1 = fecha.getTime();
                    mBAdministacion.setFecha_actual(FormatCompleto.format(fecha1));
                    mBFacturacion.setListTiposImpuestos(new ArrayList<>());
                    mBFacturacion.setListTiposImpuestos(mBFacturacion.getConsulTiposImpuestos());

                    mBFacturacion.setAdmTablasImpues(null);

                    mBFacturacion.setListTablasImpuestos(new ArrayList<>());
                    mBFacturacion.setListTablasImpuestos(Fact.ConsultarTodasTablasImpuestoAdmin());

                    this.url_pagina_Administracion = "../Administracion/GestionTablasImpuesto.xhtml";
                    break;
                case "GestionBancos":

                    mBAdministacion.setListGestBancos(new ArrayList<>());
                    mBAdministacion.setListGestBancos(Adm.CargarBancos());
                    this.url_pagina_Administracion = "../Administracion/GestionBancos.xhtml";
                    break;
                //Tatiana 28/03/2016
                case "GestionConcepto":
                    //Carga la lista principal en el formulario 
                    mBAdministacion.AdmTipoFactConcep = new LogAdministracion();
                    mBAdministacion.setListTipoConcepto(mBAdministacion.AdmTipoFactConcep.CargarConcepFac("Fact_Concepto"));
                    this.url_pagina_Administracion = "../Administracion/GestionTipoConcepto.xhtml";
                    break;

                case "GestionTipoValorAdicional":
                    //Carga la lista principal en el formulario 
                    mBAdministacion.AdmTipoFactConcep = new LogAdministracion();
                    mBAdministacion.setListTipoConcepto(mBAdministacion.AdmTipoFactConcep.CargarConcepFac("Fact_ValorAd' , 'Avaluo_ValAd"));
                    this.url_pagina_Administracion = "../Administracion/GestionValoresAdicionales.xhtml";
                    break;

                case "ConsultaMedioPago":
                    // Reutilizar la lista de Bancos para cargar los tipos de Pago  
                    mBAdministacion.setListGestBancos(new ArrayList<>());
                    mBAdministacion.setListGestBancos(Adm.CargarMediosPago());
                    this.url_pagina_Administracion = "../Administracion/GestionMediosPago.xhtml";
                    break;

                case "ConsecutivoCartera":
                    mBAdministacion.setListAdmCartera(new ArrayList<>());
                    mBAdministacion.setListAdmCartera(Adm.CargarAdmCartera());
                    mBAdministacion.setConsAval(mBAdministacion.getListAdmCartera().get(0).getConsAval());
                    mBAdministacion.setConsCaja(mBAdministacion.getListAdmCartera().get(0).getConsCaja());
                    mBAdministacion.setFechaAval(mBAdministacion.getListAdmCartera().get(0).getFechaAval());
                    mBAdministacion.setFechaCaja(mBAdministacion.getListAdmCartera().get(0).getFechaCaja());
                    this.url_pagina_Administracion = "../Administracion/C-GestionConsecutivo.xhtml";
                    break;
                default:
                    this.url_pagina_Administracion = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_MenusInternos()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    Date Fechas;

    /**
     * Metodo que muestra un mensaje tipo growl
     *
     * @author Maira Alejandra Carvajal
     * @param summary String que contiene el mensaje que se mostrara
     * @since 01-05-2015
     */
    public void addMessage(String summary) {
        try {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
            FacesContext.getCurrentInstance().addMessage(null, message);
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionar_url_menuprincipal()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Pre-Radicacion
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_preradica String que contiene el tipo de submenu al
     * que redireccionara
     * @since 01-05-2015
     */
    public void url_Menu_prRadica(String url_interna_preradica) {
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_preradica;
            Calendar fecha = new GregorianCalendar();
            mbSeguimiento.setEstadoTituloDialogActivRecordat("PreRadicacion");
            Date fecha1 = new Date();
            mBPreRadicacion.consultaTotalRegistrosTablas();
            switch (this.nombre_pagina_interna) {
                case "Registro":
                    mBPreRadicacion.cleear();
                    fecha1 = fecha.getTime();
                    mBPreRadicacion.setFecha_actual(Format.format(fecha1));
                    mbAvaluo.setFechaActual(fecha1.toString());
                    //mBPreRadicacion.setListPreRadicados(mBPreRadicacion.PreRad.ConsulPendientesRadicar(mBSesion.codigoMiSesion()));

                    MBUbicacion.cargDepto();
                    mBPreRadicacion.setListAllMisAsignados(mBPreRadicacion.PreRad.getConsultarAllAnalistas(mBAdministacion.Adm.getCodProEnt(), 1));

                    //Cargar lo de Entidades
                    LogEntidad Ent = new LogEntidad();
                    mBEntidad.setCargaEntTodo(new ArrayList<>());
                    mBEntidad.setCargaEntTodo(Ent.getEntidad());
                    LogEntidad EntEnt = new LogEntidad();
                    EntEnt.setCodEntidad(Adm.ConsulCodigoPrincipal("Cod_Entidad", "Entidad"));

                    mBEntidad.setCodEntidad1("0");
                    mBEntidad.setCodEntidad2("0");
                    mBEntidad.setCodEntidad3("0");
                    mBEntidad.setCodEntidad4("0");
                    mBEntidad.setCodEntidad5("0");
                    mBEntidad.cargEntRadic(1);

                    //Cargar sucursales 
                    mBEntidad.setCargaSucurTodo(new ArrayList<>());
                    mBEntidad.setCargaSucurTodo(Ent.getSucursalAll());
                    LogEntidad Entsucu = new LogEntidad();
                    Entsucu.setCodSucursal(Adm.ConsulCodigoPrincipal("Cod_Sucursal", "Sucursal"));

                    //Cargar Asesores
                    mBEntidad.setCargaAsesTodo(new ArrayList<>());
                    mBEntidad.setCargaAsesTodo(Ent.getAsesorAll());
                    LogEntidad EntAse = new LogEntidad();
                    EntAse.setCodAsesor(Adm.ConsulCodigoPrincipal("Cod_Asesor", "Asesor"));

                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-Registro.xhtml";
                    break;

                case "MisAsignados":
                    mbTodero.resetTable("FormMisAsignados:DtgMisAsignados");
                    mBPreRadicacion.setListPreRadicados(PreRad.ConsulPendientesRadicar(mBSesion.codigoMiSesion()));

                    mBPreRadicacion.setListMisAginadosPreRad(new ArrayList<>());
                    mBPreRadicacion.setListMisAginadosPreRad(PreRad.ConsulMisRegistrosAsignados(mBSesion.codigoMiSesion()));
                    if (mBPreRadicacion.getListMisAginadosPreRad().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("90px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    mbTodero.resetTable("FormMisAsignados:DtgMisAsignados");
                    mBPreRadicacion.cleear();

                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-MisAsignadosRegistro.xhtml";
                    break;

                case "MisPreRadicados":

                    mbTodero.resetTable("FormMisPreRadicaciones:MisPreRadicadosTable");
                    mBPreRadicacion.setListMisPreRadicaciones(new ArrayList<>());
                    mBPreRadicacion.setListMisPreRadicaciones(PreRad.ConsulMisPreRadicaAsignados(mBSesion.codigoMiSesion(), 3));
                    if (mBPreRadicacion.getListMisPreRadicaciones().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("90px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    mbTodero.resetTable("FormMisPreRadicaciones:MisPreRadicadosTable");
                    mBPreRadicacion.cleear();

                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-MisPreRadicaciones.xhtml";
                    break;

                case "PreRadica":
                    mBPreRadicacion.Limpiar();
                    if (!"".equals(mBPreRadicacion.preRadRegiMisAsig.getCodPreRadica())) {
                        this.seleccionPreradica = "PreRadica";
                        mBPreRadicacion.setEstadotabsPreRadica(true);
                        mBPreRadicacion.setPreRadicacionModificacion(false);
                        mBPreRadicacion.setPreRadicacionRegistro(true);
                        mBCliente.getTipDocEmp().clear();
                        mBCliente.setTipDocEmp(mBCliente.getConsulTipDocEmp());
                        mBPerito.getCargRegPer().clear();
                        mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());
                        mBAdministacion.getCargaTipoPredios().clear();
                        mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());
                        mBAdministacion.getCargaTipoMaquinaria().clear();
                        mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());
                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());
                        MBUbicacion.cargDepto();
                        mbAvaluo.getCargaTipAvaluo().clear();
                        mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());
                        mBPreRadicacion.getCargEnviarA().clear();
                        mBPreRadicacion.getConsulEnviarA();
                        mbAvaluo.setListPredio(mbAvaluo.Ava.getConsultarAllPredios());
                        mbAvaluo.setListMaquinaria(mbAvaluo.Ava.getConsultarAllMaquinaria());
                        mbAvaluo.setListEnser(mbAvaluo.Ava.getConsultarAllEnser());

                        mBPreRadicacion.setCargaAnali(mBPreRadicacion.cargAnalista());

                        mBPreRadicacion.setListAllMisAsignados(mBPreRadicacion.PreRadi.getConsultarAllAnalistas(1, 1));
                        mBPreRadicacion.setListAllMisAsignadosRadicacion(mBPreRadicacion.PreRadi.getConsultarAllAnalistas(1, 2));
                        mBPreRadicacion.rolGestor(mBSesion.codigoMiSesion());
                        fecha1 = fecha.getTime();
                        mBPreRadicacion.setFecha_actual(Format.format(fecha1));
                        mbAvaluo.setFechaActual(fecha1.toString());
                        mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                        this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-PreRadicacion.xhtml";
                    }

                    break;

                case "ModificarPreRadicacion":

                    if (mBPreRadicacion.PreRad != null) {
                        mBCliente.getTipDocEmp().clear();
                        mBCliente.setTipDocEmp(mBCliente.getConsulTipDocEmp());

                        mBPerito.getCargRegPer().clear();
                        mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());

                        mBAdministacion.getCargaTipoPredios().clear();
                        mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());

                        mBAdministacion.getCargaTipoMaquinaria().clear();
                        mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

                        MBUbicacion.getCargaDep().clear();
                        MBUbicacion.cargDepto();

                        mbAvaluo.getCargaTipAvaluo().clear();
                        mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());

                        mbAvaluo.setListPredio(mbAvaluo.Ava.getConsultarAllPredios());
                        mbAvaluo.setListMaquinaria(mbAvaluo.Ava.getConsultarAllMaquinaria());
                        mbAvaluo.setListEnser(mbAvaluo.Ava.getConsultarAllEnser());

                        mBPreRadicacion.getCargEnviarA().clear();
                        mBPreRadicacion.getConsulEnviarA();

                        mBPreRadicacion.rolGestor(mBSesion.codigoMiSesion());
                        fecha1 = fecha.getTime();
                        mBPreRadicacion.setFecha_actual(Format.format(fecha1));
                        mbAvaluo.setFechaActual(fecha1.toString());
                        mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                        mBPreRadicacion.setEstadotabsPreRadica(false);
                        mBPreRadicacion.setPreRadicacionModificacion(true);
                        mBPreRadicacion.setPreRadicacionRegistro(false);
                        this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-PreRadicacion.xhtml";
                    } else {
                        mBPreRadicacion.PreRad = new LogPreRadicacion();
                    }

                    break;

                case "pendientes_Radicar":
                    mBPreRadicacion.tablas();
                    this.url_pagina_Pre_Radicacion = "../Radicacion/Pre_Radicados.xhtml";
                    break;
                case "Reasignar":
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBAdministacion.getAdm().setCodProEnt(0);
                    mBEmpleado.setEmpleado("0");
                    mBEmpleado.getPersonaCargo();
                    mBPreRadicacion.setEstadoOpcionAsignado(false);
                    RolGestor(mBSesion.codigoMiSesion());
                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-CambioAnalistas.xhtml";
                    break;
                case "TotalPreRadicaciones":
                    mbTodero.resetTable("FRMPreRadicados:PreRadicadosTable");
                    lim();
                    RolGestor(mBSesion.codigoMiSesion());
                    mBPreRadicacion.setListMisAginadosPreRadica(mBPreRadicacion.PreRad.ConsulMisPreRadicaAsignados(mBSesion.codigoMiSesion(), 2));
                    if (mBPreRadicacion.getListMisAginadosPreRadica().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("90px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }

                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-TotalPreRadicaciones.xhtml";
                    break;
                case "ConsultaPreRad":
                    /*Carga toda la informacion necesaria para realizar el cargue de las consulta necesarias*/
                    mBConsultaPR.clearBean();
                    fecha1 = fecha.getTime();
                    mBPreRadicacion.setFecha_actual(Format.format(fecha1));
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                    this.url_pagina_Pre_Radicacion = "../Radicacion/FormPR-ConsultaPreRadica.xhtml";
                    break;
                default:
                    this.url_pagina_Pre_Radicacion = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_prRadica()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Radicacion
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_radica String que contiene el tipo de submenu al que
     * redireccionara
     * @since 01-05-2015
     */
    public void url_Menu_Radica(String url_interna_radica) {
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_radica;
            mBRadicacion.consultaTotalRegistrosTablas();
            Calendar fecha = new GregorianCalendar();
            Date fecha12 = new Date();
            switch (this.nombre_pagina_interna) {
                case "Radicacion":
                    //Variables de cliente
                    if (mBRadicacion.PreRad != null) {
                        this.EstadotabRadicacion = true;
                        mBCliente.getTipDocEmp().clear();
                        mBCliente.setTipDocEmp(mBCliente.getConsulTipDocEmp());
                        mBPerito.getCargRegPer().clear();
                        mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());

                        mbAvaluo.getCargaTipAvaluo().clear();
                        RequestContext.getCurrentInstance().execute("PF('tabRadicacion').select(0)");
                        RolGestor(mBSesion.codigoMiSesion());

                        mBRadicacion.setCargEmpSeguimiento(mBRadicacion.Radi.getConsultarAllAnalistas(1));

                        mBAdministacion.getCargaTipoPredios().clear();
                        mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());

                        mBAdministacion.getCargaTipoMaquinaria().clear();
                        mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

//                    mBRadicacion.setCod_avaluo(14);
//                    mBArchivo.setNAvaluo(mBRadicacion.getCod_avaluo());
//                    mBArchivo.setTipoAvaluo("Predio");
//                    mBArchivo.setNBien(1);
//                    mBArchivo.NumerAvaluo("Predio");
//                    mBArchivo.Limp(1);
                        mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                        MBUbicacion.cargDepto();
                        this.url_pagina_Radicacion = "../Radicacion/FormR-Radicacion.xhtml";
                    }

                    break;
                case "MisAsignados":
                    mbTodero.resetTable("FormMisAsignados:DtgMisAsignados");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.setEstadoTituloDialogActivRecordat("MisAsigRad");
                    mBPreRadicacion.setListMisAginadosPreRadica(new ArrayList<>());
                    mBPreRadicacion.setListMisAginadosPreRadica(mBPreRadicacion.PreRad.ConsulMisPreRadicaAsignados(mBSesion.codigoMiSesion(), 1));
                    if (mBPreRadicacion.getListMisAginadosPreRadica().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    mbTodero.resetTable("FormMisAsignados:DtgMisAsignados");
                    this.url_pagina_Radicacion = "../Radicacion/FormR-MisAsignadosPreRadica.xhtml";
                    break;
                case "Radicacion1":

                    //Variables de cliente
                    if (mBRadicacion.PreRad != null) {
                        this.EstadotabRadicacion = true;
                        mBCliente.getTipDocEmp().clear();
                        mBCliente.setTipDocEmp(mBCliente.getConsulTipDocEmp());
                        mBPerito.getCargRegPer().clear();
                        mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());
                        MBUbicacion.cargDepto();
                        mbAvaluo.getCargaTipAvaluo().clear();
                        mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());
                        RequestContext.getCurrentInstance().execute("PF('tabRadicacion').select(0)");
                        RolGestor(mBSesion.codigoMiSesion());

                        mBRadicacion.setCargEmpSeguimiento(new ArrayList<>());
                        mBRadicacion.setCargEmpSeguimiento(Rad.getConsultarAllAnalistas(1));

                        mBAdministacion.getCargaTipoPredios().clear();
                        mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());

                        mBAdministacion.getCargaTipoMaquinaria().clear();
                        mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

                        mBAdministacion.getCargarBancos().clear();
                        mBAdministacion.setCargarBancos(mBAdministacion.OnBancos());

                        mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                        MBUbicacion.cargDepto();
                        this.url_pagina_Radicacion = "../Radicacion/FormR-Radicacion.xhtml";
                    }

                    break;
                case "RadicacionEditar":

                    if (mBRadicacion.Radi != null) {

                        MBUbicacion.getCargaDep().clear();
                        MBUbicacion.cargDepto();

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

                        mBRadicacion.setEstadoTituloRadicacion(true);
                        mBRadicacion.setEstadoLabelsClienteEnti(true);
                        mBRadicacion.setRadicacionModificacion(true);
                        mBRadicacion.setRadicacionRegistro(false);

                        this.EstadotabRadicacion = false;
                        this.url_pagina_Radicacion = "../Radicacion/FormR-Radicacion.xhtml";
                    }
                    break;

                case "Radicados":
                    mbTodero.resetTable("FRMRadicados:RadicadosTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBArchivo.Clear();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mbSeguimiento.setEstadoTituloDialogActivRecordat("Radicacion");
                    mbSeguimiento.setOpcionTableroControlSeguim("");
                    mBRadicacion.consultasRadicacion(1);

                    mBAdministacion.getCargarBancos().clear();
                    mBAdministacion.setCargarBancos(mBAdministacion.OnBancos());

                    if (mBRadicacion.getListRadicados().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }

                    this.url_pagina_Radicacion = "../Radicacion/FormR-MisRadicaciones.xhtml";
                    break;

                case "MisActividades":

                    mbTodero.resetTable("FRMActividadesYRecord:RadicadosTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.cargDepto();
                    mbSeguimiento.setOpcionTableroControlSeguim("");
                    mBRadicacion.setListRadicados(new ArrayList<>());
                    mBRadicacion.consultaAvaluosConActiviYRecor();
                    if (mBRadicacion.getListRadicados().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Radicacion = "../Radicacion/FormR-ActividadesYRecordatori.xhtml";
                    mbTodero.resetTable("FRMActividadesYRecord:RadicadosTable");
                    break;

                case "TotalRadicacion":
                    mbTodero.resetTable("FRMRadicadosGes:RadicadosTable");
                    lim();
                    RolGestor(mBSesion.codigoMiSesion());
                    mBRadicacion.consultasRadicacion(2);
                    if (mBRadicacion.getListRadicadosGestor().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Radicacion = "../Radicacion/FormR-TotalRadicaciones.xhtml";
                    mbTodero.resetTable("FRMRadicadosGes:RadicadosTable");
                    break;
                case "ReAsignacion":

                    this.url_pagina_Radicacion = "../Radicacion/FormR-CambioAnalistas.xhtml";
                    mBAdministacion.limpiarAdministracion();
                    mBRadicacion.setOpcionesCambioAnalistaRadica("");
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBRadicacion.setCodProEnt("");
                    mBRadicacion.setEstadoOpcionAsignado(false);

                    mBEmpleado.setEmpleado("0");
                    mBRadicacion.setListRadicaAsignados(new ArrayList<>());
                    break;
                case "AvaluosPaquetes":

                    this.EstadotabRadicacion = true;
                    mBCliente.getTipDocEmp().clear();
                    mBCliente.setTipDocEmp(mBCliente.getConsulTipDocEmp());
                    mBPerito.getCargRegPer().clear();
                    mBPerito.setCargRegPer(mBPerito.getConsulRegimPer());
                    MBUbicacion.cargDepto();
                    mbAvaluo.getCargaTipAvaluo().clear();
                    mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());
                    RolGestor(mBSesion.codigoMiSesion());

                    mBRadicacion.setCargEmpSeguimiento(new ArrayList<>());
                    mBRadicacion.setCargEmpSeguimiento(mBRadicacion.Radi.getConsultarAllAnalistas(1));

                    mBAdministacion.getCargaTipoPredios().clear();
                    mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());

                    mBAdministacion.getCargaTipoMaquinaria().clear();
                    mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());
                    this.url_pagina_Radicacion = "../Radicacion/FormR-Paquetes.xhtml";
                    break;

                case "ConsultaRad":
                    /*Carga toda la informacion necesaria para realizar el cargue de las consulta necesarias*/
                    mBConsultaRad.clearBean();
                    fecha12 = fecha.getTime();
                    mBConsultaRad.setFecha_actual(Format.format(fecha12));

                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());

                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());

                    mbAvaluo.getCargaTipAvaluo().clear();
                    mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());

                    mBPreRadicacion.getCargEnviarA().clear();
                    mBPreRadicacion.getConsulEnviarA();
                    //GCH 30DIC2016
                    mBPerito.clearComponents();
                    mBPerito.setListPerit(Per.ConsulAllPerito(0)); //Carga Lista de Peritos
                    mBConsultaRad.setNombrePerito("");
                    mBConsultaRad.setCodPerito(0);
                    mBConsultaRad.setCod_Ava_Cons("");
                    mBConsultaRad.setListaConsulR(new ArrayList<>());
                    mBConsultaRad.setListaConsulR2(new ArrayList<>());

                    this.url_pagina_Radicacion = "../Radicacion/FormR-ConsultaRadicacion.xhtml";
                    break;

                default:
                    this.url_pagina_Radicacion = "../NoAlcansada.xhtml";
                    break;

            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Radica()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Anticipo
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_Anticip String que contiene el tipo de submenu al que
     * redireccionara
     * @since 01-05-2015
     */
    LogAnticipo Ant = new LogAnticipo();

    public String Query = "";

    public void url_Menu_Anticipo(String url_interna_Anticip) {
        this.nombre_pagina_interna = "";
        this.nombre_pagina_interna = url_interna_Anticip;
        String Query1;
        switch (this.nombre_pagina_interna) {

            case "ConsultAntGeneral":
                Query1 = "  where An.Estado_Anticipo='P' group by Seg.FK_Cod_Avaluo";
                mBAnticipo.setAnt(new LogAnticipo());
                mBAnticipo.setListAntGeneral(new ArrayList<>());
                mBAnticipo.setListAntGeneral(Anti.consulGnral("avaluo", Query1));
                Conexion.Conexion.CloseCon();
                this.url_pagina_Anticipo = "../Anticipo/ConsultAntIndividual.xhtml";
                break;

            case "ConsulAnt":

                mBAnticipo.setListConslGen(new ArrayList<>());
                Query1 = " where An.Estado_Anticipo in ('P')";
                mBAnticipo.setListConslGen(Anti.consulGnral("", Query1));
                mBAnticipo.Limpiar();
                mBAnticipo.onCargarEstadosSelecc("inicializar");
                mBAnticipo.setListMontosPopUp(new ArrayList<>());
                mBAnticipo.setListMontosPopUp(Anti.MontosPorEstadoPopUp("Estado_Anticipo='P'"));
                this.url_pagina_Anticipo = "../Anticipo/ConsultaAntGen.xhtml";
                break;

            case "AnticipoGeneral":

                if (mBAnticipo.Ant == null) {
                    mbTodero.setMens("Debe seleccionar un registro");
                    mbTodero.warn();
                } else {
                    //Si tiene numero de avaluo asociado cargo la informacion necesaria y se la muestro en el formAnticipoGen
                    try {
                        Query = "where Seg.FK_Cod_Avaluo = " + mBAnticipo.Ant.getCodAvaluo();
                        mBAnticipo.setListaAnticipoDet(new ArrayList<>());
                        mBAnticipo.setListaAnticipoDet(Anti.consulGnral("", Query));
                        Conexion.Conexion.CloseCon();
                        mBAnticipo.setNAvaluoAsociado(true);
                        mBRadicacion.limpiarRadicacionGeneral();
                        mBRadicacion.verInformacion(2, Integer.parseInt(mBAnticipo.Ant.getCodAvaluo()), mBAnticipo.Ant.getCodSeguimiento());

                    } catch (NumberFormatException | ParseException e) {
                        mbTodero.setMens("Error: " + e.getMessage());
                        mbTodero.fatal();
                    }
                    mBAnticipo.verInformacion("General", mBAnticipo.Ant.getCodAvaluo(), mBAnticipo.Ant.getNBien(), 0);

                    this.url_pagina_Anticipo = "../Anticipo/FormAnticipoGen.xhtml";
                }
                break;

            default:
                this.url_pagina_Anticipo = "../NoAlcansada.xhtml";
                break;
        }
        this.url_pagina_Administracion = "../Blanco.xhtml";
        this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
        this.url_pagina_Seguimiento = "../Blanco.xhtml";
        this.url_pagina_Entrega = "../Blanco.xhtml";
        this.url_pagina_Cartera = "../Blanco.xhtml";
        this.url_pagina_Revision = "../Blanco.xhtml";
        this.url_pagina_Facturacion = "../Blanco.xhtml";

    }

    /**
     * Metodo que verifica el password de un empleado, consultando toda la
     * informacion que este tenga registrada
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @since 01-05-2015
     */
    public void verificaPassword() {
        try {
            if ("".equals(this.PassValida)) {
                mbTodero.setMens("Ingrese la constraseña de su cuenta");
                mbTodero.warn();
                RequestContext.getCurrentInstance().execute("PF('DlgPassword').show()");
            } else {
                Emp.setCodEmp(mBSesion.codigoMiSesion());
                Emp.setPassEmp(PassValida);
                boolean Result = Emp.ConsulPass();
                if (Result == true) {
                    RequestContext.getCurrentInstance().execute("PF('DlgPassword').hide()");
                    opcionPNLValidacion = false;
                    po1 = 290;
                    po2 = 145;
                    RequestContext.getCurrentInstance().execute("PF('DlgPassword').show()");
                    opcionPNLInformacion = true;
                    Emp.setCodEmp(mBSesion.codigoMiSesion());
                    try {
                        Dat = Emp.ConsulEmp();
                        if (Dat.next()) {
                            mBEmpleado.getTipDocEmp().clear();
                            mBEmpleado.getConsulTipDocEmp();
                            MBUbicacion.getCargaDep().clear();
                            MBUbicacion.cargDepto();
                            mBEmpleado.setNombreEmp(Dat.getString("Nombre_Empleados"));
                            mBEmpleado.setApellidoEmp(Dat.getString("Apellido_Empleados"));
                            mBEmpleado.setTipDoc(Dat.getString("FK_Cod_TipDocumento"));
                            mBEmpleado.setDocEmp(Dat.getInt("Documento_Empleados"));
                            mBEmpleado.setTelPerEmp(Dat.getString("Telefo_Personal_Empleados"));
                            mBEmpleado.setCelPerEmp(Dat.getString("Celular_Personal_Empleados"));
                            mBEmpleado.setMailPerEmp(Dat.getString("Correo_Personal_Empleados"));
                            mBEmpleado.setNombreDepto(Dat.getString("Cod_Departamento"));
                            mBEmpleado.cargCiudad();
                            mBEmpleado.setCuidad(Dat.getString("E.FK_Cod_Ciudad"));
                            mBEmpleado.setNumContacEmp(Dat.getString("Numero_Contacto_Empleados"));
                            mBEmpleado.setNomContacEmp(Dat.getString("Nombre_Contacto_Empleados"));

                            mBEmpleado.setEstado(Dat.getString("Nombre_Estado"));
                            mBEmpleado.setCargo(Dat.getString("Nombre_Cargo"));
                            mBEmpleado.setExtEmp(Dat.getString("Extension_Empleados"));
                            mBEmpleado.setCelEmp(Dat.getString("Celular_Empresa_Empleados"));
                            mBEmpleado.setUbicaEmp(Dat.getString("Resultado_Parametro"));
                            if ("".equals(Dat.getString("Correo_Corporativo_Empleados")) || Dat.getString("Correo_Corporativo_Empleados") == null) {
                                mBEmpleado.setMailEmp("No registra");
                            } else {
                                mBEmpleado.setMailEmp(Dat.getString("Correo_Corporativo_Empleados"));
                            }
                            mBEmpleado.setEmpleado(Dat.getString("Jefe"));
                            mBEmpleado.setUsuarEmp(Dat.getString("Username_Empleados"));

                            mbPermiso.setListPermisosAsig(Perm.consultaRolesAsignados(mBSesion.codigoMiSesion()));

                            mbPermiso.setListProdEntiAsig(Perm.getProEntAsignadoooo(mBSesion.codigoMiSesion()));

                        }
                        Conexion.Conexion.CloseCon();
                    } catch (SQLException e) {
                        mbTodero.setMens("Error: " + e.getMessage());
                        mbTodero.fatal();
                    }
                } else {
                    this.PassValida = "";
                    mbTodero.setMens("La contraseña no es la correcta , favor ingresela nuevamente");
                    mbTodero.warn();
                    RequestContext.getCurrentInstance().execute("PF('DlgPassword').show()");
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".verificaPassword()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que confrma el password y habilita los paneles de informacion y
     * abre el dialog con la informacion de el empleado
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @since 01-05-2015
     */
    public void confirmapass() {
        try {
            this.opcionPNLValidacion = true;
            this.opcionPNLInformacion = false;
            this.PassValida = "";
            po1 = 486;
            po2 = 264;
            RequestContext.getCurrentInstance().execute("PF('DlgPassword').show()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".confirmapass()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que actualiza la informacion del empleado
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @since 01-05-2015
     */
    public void llamarBeanEmp() {
        try {
            this.PassValida = "";
            mBEmpleado.actualizacionEmp();
            this.opcionPNLInformacion = false;
            this.opcionPNLValidacion = true;
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".llamarBeanEmp()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    //Metodo que permitira cargar la informacion de la factura copia dependdeindo del avaluo que ecoja 
    /**
     * Metodo que tendra la direccion y cargue de metodos para el modulo de
     * facturación.
     *
     * @param url_interna_Factura trae el nombre del parametro que redirecciona
     * la url
     * @throws java.text.ParseException
     */
    /**
     * Metodo que tendra la direccion y cargue de metodos para el modulo de
     * facturación.
     *
     * @param url_interna_Factura trae el nombre del parametro que redirecciona
     * la url
     * @throws java.text.ParseException
     */
    public void url_Menu_Facturacion(String url_interna_Factura) throws ParseException {

        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_Factura;
            mBFacturacion.consultaTotalRegistrosTablas();
            Calendar fecha = new GregorianCalendar();
            Date fecha1 = new Date();
            switch (nombre_pagina_interna) {

                case "MisActividades":

                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mbSeguimiento.setOpcionTableroControlSeguim("");
                    mBRadicacion.setListRadicados(new ArrayList<>());
                    mBRadicacion.consultaAvaluosConActiviYRecor();
                    this.url_pagina_Facturacion = "../Facturacion/FormF-ActYRecPendientes.xhtml";
                    // mbTodero.resetTable("FRMActividadesYRecord:RadicadosTable");
                    break;

                case "GestActYRecr":

                    if (mBRadicacion.Radi != null) {

                        MBUbicacion.getCargaDep().clear();
                        MBUbicacion.cargDepto();

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

                        mBRadicacion.setEstadoTituloRadicacion(true);
                        mBRadicacion.setEstadoLabelsClienteEnti(true);
                        mBRadicacion.setRadicacionModificacion(true);
                        mBRadicacion.setRadicacionRegistro(false);

                        this.EstadotabRadicacion = false;
                        this.url_pagina_Facturacion = "../Facturacion/FormF-GestionActYRec.xhtml";
                    }
                    break;

                case "Remisiones":
                    mBConsultaFactu.limpiar();
                    this.url_pagina_Facturacion = "../Facturacion/FormF-Remisiones.xhtml";
                    break;

                case "PendiExcede":
                    mBFacturacion.Limpiar(4);
                    mBFacturacion.setListPendientXFact(new ArrayList<>());
                    mBFacturacion.setFac(null);
                    mBFacturacion.setListPendientXFact(Fact.ConsultPendientExce());
                    this.url_pagina_Facturacion = "../Facturacion/FormF-PendExcedente.xhtml";
                    break;

                case "PendienteXFact":
                    mBFacturacion.Limpiar(3);
                    mBFacturacion.Limpiar(2);
                    mBCliente.limpiarCajasRegistroCli();
                    mBFacturacion.setListPendientXFact(new ArrayList<>());
                    mBFacturacion.setFac(null);
                    mBFacturacion.setListPendientXFact(Fact.ConsultPendientXFac());
                    this.url_pagina_Facturacion = "../Facturacion/FormF-AvaPendientXFact.xhtml";
                    break;

                case "PendienteImpresion":
                    mBFacturacion.Limpiar(2);
                    mBAdministacion.getCargarTipi().clear();
                    mBAdministacion.setCargarTipi(mBAdministacion.OnTipificacion("Fact_ValorAd"));
                    mBCliente.limpiarCajasRegistroCli();
                    mBFacturacion.setFac(null);
                    mBFacturacion.setListPendientXImpri(new ArrayList<>());
                    mBFacturacion.setListPendientXImpri(Fact.ConsultPendientXImpri("general"));
                    this.url_pagina_Facturacion = "../Facturacion/FormF-AvaPendtImpresion.xhtml";
                    break;

                case "Imprimir":

                    if (mBFacturacion.Fac == null) {
                        mbTodero.setMens("No ha seleccionado ningún registro");
                        mbTodero.warn();
                    } else {
                        mBFacturacion.Limpiar(8);
                        if (!mBFacturacion.Fac.getCliente().isEmpty()) {
                            mBFacturacion.setAnticipo5(Ant.AnticiposFacturaPersona(mBFacturacion.Fac.getCodSeg(), mBFacturacion.Fac.getCodClie(), "cliente_segant.FK_Cod_Cliente"));
                            if (mBFacturacion.getAnticipo5() > 0) {
                                mBFacturacion.setFechaConsignacion(Ant.fecha(mBFacturacion.Fac.getCodSeg(), mBFacturacion.Fac.getCodClie(), "cliente_segant.FK_Cod_Cliente").substring(0, 11));
                            }
                            mBFacturacion.setListaFacturaAnticipo(Fac.AsociarFacAnticipo(23, "FK_Cod_Cliente=" + mBFacturacion.Fac.getCodClie()));

                        }
                        if (!mBFacturacion.Fac.getNombre_Entidad().isEmpty()) {
                            mBFacturacion.setAnticipo5(Ant.AnticiposFacturaPersona(mBFacturacion.Fac.getCodSeg(), mBFacturacion.Fac.getCodClie(), "ASE.FK_Cod_Asesor"));
                            if (mBFacturacion.getAnticipo5() > 0) {
                                mBFacturacion.setFechaConsignacion(Ant.fecha(mBFacturacion.Fac.getCodSeg(), mBFacturacion.Fac.getCodClie(), "ASE.FK_Cod_Asesor").substring(0, 11));
                            }
                            mBFacturacion.setListaFacturaAnticipo(Fac.AsociarFacAnticipo(23, "FK_Cod_Entidad=" + mBFacturacion.Fac.getCodClie()));
                        }

                        mBFacturacion.setIvaBD((Fac.impuestoFactAntcpo().size() > 0) ? (int) Fac.impuestoFactAntcpo().get(0).getTasaIva() : 0);
                        mBAdministacion.getCargarFuente().clear();
                        mBAdministacion.setCargarFuente(mBAdministacion.OnRetefuente("0"));
                        mBAdministacion.getCargarICA().clear();
                        mBAdministacion.setCargarICA(mBAdministacion.OnReteica("0,1"));
                        mBAdministacion.getCargarReteIva().clear();
                        mBAdministacion.setCargarReteIva(mBAdministacion.OnReteiva("0"));
                        mBFacturacion.setObservaciones(Fac.Observaciones(mBFacturacion.Fac.getCodFactu()));
                        mBFacturacion.getObservaciones();

                        if (mBFacturacion.Fac.getTipoFactura().contains("NORMAL")) {
                            mBFacturacion.setListInfoImpri(Fac.ConsultFormImpre(mBFacturacion.Fac.getCodFactu()));
                            mBFacturacion.Porcentualizar("NORMAL");
                            Conexion.Conexion.CloseCon();
                            this.url_pagina_Facturacion = "../Facturacion/FormF-FacturaImpresion.xhtml";

                        } else if (mBFacturacion.Fac.getTipoFactura().contains("ANTICIPO")) {
                            mBFacturacion.setListInfoImpri(Fac.ConculImpreAnti(mBFacturacion.Fac.getCodFactu()));
                            mBFacturacion.Porcentualizar("OTROS");
                            Conexion.Conexion.CloseCon();
                            this.url_pagina_Facturacion = "../Facturacion/FormF-ImpresionAnt.xhtml";
                        } else if (mBFacturacion.Fac.getTipoFactura().contains("COPIA")) {
                            mBFacturacion.setListInfoImpri(Fac.ConsulImpreCopia(mBFacturacion.Fac.getCodFactu()));
                            mBFacturacion.Porcentualizar("OTROS");
                            Conexion.Conexion.CloseCon();
                            this.url_pagina_Facturacion = "../Facturacion/FormF-ImpresionCopia.xhtml";
                        } else if (mBFacturacion.Fac.getTipoFactura().contains("CONCEPTO")) {

                            mBFacturacion.setListInfoImpri(Fac.ConsulImpreConcepto(mBFacturacion.Fac.getCodFactu()));
                            mBFacturacion.setObservacionCambio(mBFacturacion.getListInfoImpri().get(0).getConcepto());
                            mBFacturacion.Porcentualizar("OTROS");
                            Conexion.Conexion.CloseCon();
                            this.url_pagina_Facturacion = "../Facturacion/FormF-ImpresionConcepto.xhtml";
                        } else if (mBFacturacion.Fac.getTipoFactura().contains("EXCEDENTE")) {
                            mBFacturacion.setListInfoImpri(Fac.ConsulImpreCopia(mBFacturacion.Fac.getCodFactu()));
                            mBFacturacion.Porcentualizar("EXCE");
                            Conexion.Conexion.CloseCon();
                            for (int i = 0; i < mBFacturacion.listaImpuesto.size(); i++) {
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().equalsIgnoreCase("iva")) {
                                    mBFacturacion.setOpcIva1(true);
                                    mBFacturacion.setIvaBD((int) mBFacturacion.listaImpuesto.get(i).getTasaImpuesto());
                                    mBFacturacion.setIva(Math.round(mBFacturacion.getSubtotal() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100);
                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("RETEIVA")) {
                                    mBFacturacion.setOpcReteIva(true);
                                    mBFacturacion.setValorReIva((int) (Math.round(mBFacturacion.getIva() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100));

                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("FUENTE")) {
                                    mBFacturacion.setOpcRetefuente(true);
                                    mBFacturacion.setValorFuente((int) (Math.round(mBFacturacion.getSubtotal() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100));

                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("ICA")) {
                                    mBFacturacion.setOpcReteIca(true);
                                    mBFacturacion.setValorIca((int) (Math.round(mBFacturacion.getSubtotal() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100));
                                }
                                mBFacturacion.Porcentualizar("EXCE");
                                Conexion.Conexion.CloseCon();
                            }

                            this.url_pagina_Facturacion = "../Facturacion/FormF-ImprExcedente.xhtml";

                        }
                    }
                    break;

                case "Factura_Anticipo":
                    mBEntidad.cargEntRadic(1);
                    mBFacturacion.setFormName("FactuAntiCon");
                    mBFacturacion.ConsultarPersona(10);
                    mBFacturacion.Clear();
                    mBFacturacion.setNfactAnticpo(Fac.MaxNoFact() + 1);
                    //Cargar lo Necesario para la facturacion de Anticipo
                    //Cargar fuente
                    //mBAdministacion.getCargarFuente().clear();
                    //mBAdministacion.setCargarFuente(mBAdministacion.OnRetefuente());
                    mBFacturacion.setListaImpuesto(new ArrayList<>());
                    mBFacturacion.setListaImpuesto(Fac.impuestoFactAntcpo());
                    mBFacturacion.setIvaAnticp((mBFacturacion.getListaImpuesto().size() > 0) ? (int) mBFacturacion.getListaImpuesto().get(0).getTasaIva() : 0);
                    //Traer el numero de Factura 

                    this.url_pagina_Facturacion = "../Facturacion/FormF-FacturaAnticipo.xhtml";
                    break;
                case "Factura_Copia":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBArchivo.CleanRepartValor();
                    mBFacturacion.ConsultarPersona(10);
                    mBEntidad.cargEntRadic(1);
                    mBFacturacion.setFormName("FactuCopia");
                    mBFacturacion.Clear();
                    mBFacturacion.setNfactAnticpo(0);
                    mBFacturacion.setNfactAnticpo(Fac.MaxNoFact() + 1);
                    mBFacturacion.setListaImpuesto(new ArrayList<>());
                    mBFacturacion.setListaImpuesto(Fac.impuestoFactAntcpo());
                    mBFacturacion.setIvaAnticp((mBFacturacion.getListaImpuesto().size() > 0) ? (int) mBFacturacion.getListaImpuesto().get(0).getTasaIva() : 0);
                    //Traer el numero de Factura 
                    this.url_pagina_Facturacion = "../Facturacion/FormF-FacturaCopia.xhtml";
                    break;

                case "Factura_Concepto":

                    mBFacturacion.ConsultarPersona(7);
                    mBEntidad.cargEntRadic(1);
                    mBFacturacion.setFormName("FactuConcepto");
                    mBFacturacion.Clear();
                    //Cargar Combobox de Conceptos
                    //Carga la informacion del combobox con los conceptos Registrados en la Base de Datos
                    mBAdministacion.getCargarfactConcepto().clear();
                    mBAdministacion.setCargarfactConcepto(mBAdministacion.OnFactConcepto());
                    //      mBFacturacion.setNfactAnticpo(Fac.MaxNoFact() + 1);
                    this.url_pagina_Facturacion = "../Facturacion/FormF-FacturaConcepto.xhtml";
                    break;

                case "LiberarAvaluo":
                    int codSeg;
                    int CodFact;
                    int codSegFac;
                    if (mBFacturacion.getFactLiberar() != 0) {
                        //Mostrar un mensaje el Numero de avaluo que se va ha liberar
                        if (mBFacturacion.getSelectListaAvaltAnt().size() > 0) {

                            for (int i = 0; i < mBFacturacion.getSelectListaAvaltAnt().size(); i++) {
                                //Traer  el numero de avaluo 
                                codSeg = mBFacturacion.getSelectListaAvaltAnt().get(i).getCodSeg();
                                CodFact = mBFacturacion.getFactLiberar();
                                codSegFac = mBFacturacion.getSelectListaAvaltAnt().get(i).getCodSegFac();
                                int bandera = Fact.LiberarNumFacAso(codSeg);

                                if (bandera == 0) {
                                    //llamar al procedimiento almacendo de sp_liberarAvaluo
                                    Fac.LiberarAvaluo(codSeg, CodFact, codSegFac, mBSesion.codigoMiSesion());

                                    RequestContext.getCurrentInstance().execute("PF('MsgAlerta').hide()");
                                    setListPendientXImpri(new ArrayList<>());
                                    setListPendientXImpri(Fac.ConsultPendientXImpri("general"));
                                    mbTodero.setMens("Avaluo Liberado Correctamente, Verifique en el Modulo pendientes por Facturar");
                                    mbTodero.info();
                                    url_Menu_Facturacion("PendienteImpresion");
                                } else {
                                    mbTodero.setMens("No se puede liberar el avaluo ya que parte de el ya ha sido facturado");
                                    mbTodero.info();
                                    RequestContext.getCurrentInstance().execute("PF('MsgAlerta').hide()");
                                }
                            }

                        }
                    }
                    break;

                case "CargueInfoFactCopia":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBFacturacion.ConsultarPersona(10);
                    int Valor = Fac.AvaluoYaFacturado(mBFacturacion.getNavaluoCopia());
                    if (Valor != 0) {
                        if (mBFacturacion.getNavaluoCopia() != 0) {
                            mBFacturacion.setEstadoInfoAval(true);
                            mBFacturacion.setValidarPanel(true);

                            mBRadicacion.verInformacion(3, mBFacturacion.getNavaluoCopia(), Valor);
                        }
                    } else {
                        mbTodero.setMens("No de Avaluo Ingresado NO EXISTE, o no se encuentra en estado Facturado.");
                        mbTodero.info();
                        mBFacturacion.setNavaluoCopia(0);
                        mBFacturacion.setEstadoInfoAval(false);
                        mBFacturacion.setValidarPanel(false);
                    }
                    mBFacturacion.getNavaluoCopia();

                    break;

                case "PreFactura":

                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBAnticipo.setListaFactAnt(null);
                    mBAdministacion.getCargarTipi().clear();
                    mBAdministacion.setCargarTipi(mBAdministacion.OnTipificacion("Avaluo_ValAd"));
                    mBArchivo.setListEntidadesPersonasAFacturarAntic(null);
                    mBArchivo.setListClientesPersonasAFacturarAntic(null);
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBArchivo.CleanRepartValor();

                    //Cargar fuente
                    mBAdministacion.getCargarFuente().clear();
                    mBAdministacion.setCargarFuente(mBAdministacion.OnRetefuente("0"));

                    if (mBFacturacion.Fac == null) {
                        mbTodero.setMens("No ha seleccionado ningún registro");
                        mbTodero.warn();
                    } else {
                        mBFacturacion.Limpiar(1);
                        mBFacturacion.setRadioUno("P");
                        mBFacturacion.setListaAnticipos(mBFacturacion.Factu.ConsulAnticipoPoD(mBFacturacion.Fac.getCodSeg()));
                        mBFacturacion.setListValAdiAva(mBFacturacion.Factu.ValoresAdiAva(mBFacturacion.Fac.getCodSeg()));
                        mBFacturacion.setListDistancia(mBFacturacion.Factu.Distancia(mBFacturacion.Fac.getCodSeg()));
                        if (mBFacturacion.ListaAnticipos.size() > 0) {
                            mbTodero.setMens("El avaluo tiene anticipos asociados que aún no han sido aprobados y se encuentran devueltos, realice este proceso antes de facturar");
                            mbTodero.warn();
                        } else {

                            mBFacturacion.setTipoFacturacion("Normal");
                            try {

                                mBRadicacion.verInformacion(3, mBFacturacion.Fac.getCodAva(), mBFacturacion.Fac.getCodSeg());
                                LogCliente ClientFact = new LogCliente();
                                mBArchivo.setListClientesPersonasAFacturarAntic(ClientFact.ConsultaClienPerFact(1, mBFacturacion.Fac.getCodAva()));
                                LogEntidad EntidFact = new LogEntidad();
                                mBArchivo.setListEntidadesPersonasAFacturarAntic(EntidFact.consultaEntidadPorEntiFacturar(mBFacturacion.Fac.getCodAva()));
                                mBAnticipo.CargarAnticpFactura(mBFacturacion.Fac.getCodSeg());
                                mBArchivo.Arch.getValorParaPerFact1();
                                //Consulta para traerme la maxima Factura
                                mBFacturacion.CargarNFactura();
                                mBFacturacion.SumarParticion(mBArchivo.Arch.getValorParaPerFact1(), mBArchivo.Arch.getValorParaPerFact2(),
                                        mBArchivo.Arch.getValorParaPerFact3(), mBArchivo.Arch.getValorParaPerFact4(), mBArchivo.Arch.getValorParaPerFact5());
                                int valorAnticiposDi = Ant.SumaAnticipos(mBFacturacion.Fac.getCodSeg());
                                mBFacturacion.setValorAnti(valorAnticiposDi);
                                mBFacturacion.setListaAnticiposPop(Ant.ConsulReparAnti(mBFacturacion.Fac.getCodSeg()));
                                mBFacturacion.Reparticion(mBFacturacion.Fac.getCodAva());
                                String pacClie = (mBCliente.getValorTarifaCli1());
                                String pacEnti = (mBEntidad.getValorTarifaEnti1());

                                if (pacClie != null || pacEnti != null) {
                                    mBFacturacion.validacionTab("validacionOk", mBFacturacion.Fac.getCodSeg());
                                }

                                this.url_pagina_Facturacion = "../Facturacion/FormF-PreFactura.xhtml";
                            } catch (ParseException e) {
                                mbTodero.setMens("Error: " + e.getMessage());
                                mbTodero.error();
                            }
                        }
                    }

                    break;

                case "FacExcedente":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBAnticipo.setListaFactAnt(null);
                    mBAdministacion.getCargarTipi().clear();
                    mBAdministacion.setCargarTipi(mBAdministacion.OnTipificacion("Avaluo_ValAd"));
                    mBArchivo.setListEntidadesPersonasAFacturarAntic(null);
                    mBArchivo.setListClientesPersonasAFacturarAntic(null);
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBArchivo.CleanRepartValor();

                    if (mBFacturacion.Fac == null) {
                        mbTodero.setMens("No ha seleccionado ningún registro");
                        mbTodero.warn();
                    } else {
                        mBFacturacion.Limpiar(1);
                        try {
                            mBRadicacion.verInformacion(3, mBFacturacion.Fac.getCodAva(), mBFacturacion.Fac.getCodSeg());

                            LogCliente ClientFact = new LogCliente();
                            mBArchivo.setListClientesPersonasAFacturarAntic(ClientFact.ConsultaClienPerFact(1, mBFacturacion.Fac.getCodAva()));
                            LogEntidad EntidFact = new LogEntidad();
                            mBArchivo.setListEntidadesPersonasAFacturarAntic(EntidFact.consultaEntidadPorEntiFacturar(mBFacturacion.Fac.getCodAva()));
                            //Consulta para traerme la maxima Factura
                            mBFacturacion.CargarNFactura();
                            valCom = Fac.GetInfoFacExc(mBFacturacion.Fac.getCodSeg(), "distancia", "");
                            if (valCom.next()) {
                                mBFacturacion.setDistancia(valCom.getInt("Distancia"));
                            }
                            Conexion.Conexion.CloseCon();
                            long Dato;
                            Dato = Fac.ConsulValLiquid(mBFacturacion.Fac.getCodSeg(), mBFacturacion.getDistancia());
                            int cantCli = mBArchivo.getListClientesPersonasAFacturarAntic().size() + mBArchivo.getListEntidadesPersonasAFacturarAntic().size();
                            if (cantCli == 1) {
                                mBFacturacion.setAsignTotal1((int) Dato);
                            }
                            valCom = Fac.UltimoValComercial(mBFacturacion.Fac.getCodAva(), "consul");
                            if (valCom.next()) {
                                mBFacturacion.setValComerBig(valCom.getString("valorComercial"));
                            }
                            Conexion.Conexion.CloseCon();
                            valCom = Fac.GetInfoFacExc(mBFacturacion.Fac.getCodSeg(), "infoAnter", "");
                            if (valCom.next()) {
                                mBFacturacion.setTipoFacturacion(valCom.getString("tab"));
                                mBFacturacion.setValorTotalTabla(valCom.getInt("valAvaAnt"));
                            }
                            Conexion.Conexion.CloseCon();

                            /*   valCom = Fac.GetInfoFacExc(mBFacturacion.Fac.getCodSeg(), "saldoFac");
                             if (valCom.next()) {
                             mBFacturacion.setValorFactura(valCom.getInt("SaldoAPagar"));
                             }*/
                            mBFacturacion.Fac.setValorFacturado(Dato);
                            double oper = (int) (mBFacturacion.Fac.getValorFacturado() - mBFacturacion.getValorTotalTabla());

                            valCom = Fac.GetInfoFacExc(mBFacturacion.Fac.getCodSeg(), "descuento", "");
                            if (valCom.next()) {
                                mBFacturacion.setSimbolo((valCom.getString("Tipo_Descuento").equalsIgnoreCase("F")) ? "$" : "%");
                                mBFacturacion.setValorDescuentos(valCom.getInt("Descuento"));

                                if (mBFacturacion.getSimbolo().equalsIgnoreCase("%")) {
                                    int aux = (int) ((oper * mBFacturacion.getValorDescuentos()) / 100);
                                    mBFacturacion.setDescuento(aux);

                                }
                            }
                            Conexion.Conexion.CloseCon();
                            mBFacturacion.Fac.setValorNetoFacturado((long) (oper - mBFacturacion.getDescuento()));

                            mBFacturacion.setCodTabla(Fac.getCodTabla());
                            mBFacturacion.setNombreTable(Fac.getNombreTabla());
                            mBFacturacion.ListTablaAplicada = new ArrayList<>();
                            mBFacturacion.ListTablaAplicada = Fac.TablaFacturacion("Exedente", mBFacturacion.getCodTabla());
                            mBFacturacion.ListaDistanciaTablas = new ArrayList<>();
                            mBFacturacion.ListaDistanciaTablas = Fac.TablaDistanciaFac("Exedente", mBFacturacion.getCodTabla());
                            mBFacturacion.listaImpuesto = Fac.ImpuAplyAva(mBFacturacion.Fac.getCodSeg());
                            for (int i = 0; i < mBFacturacion.listaImpuesto.size(); i++) {
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().equalsIgnoreCase("iva")) {
                                    mBFacturacion.setOpcIva1(true);
                                    mBFacturacion.setIvaBD((int) mBFacturacion.listaImpuesto.get(i).getTasaImpuesto());
                                    mBFacturacion.setIva(Math.round(mBFacturacion.Fac.getValorNetoFacturado() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100);
                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("RETEIVA")) {
                                    mBFacturacion.setOpcReteIva(true);
                                    mBFacturacion.setReteiva((int) (Math.round(mBFacturacion.getIva() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100));

                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("FUENTE")) {
                                    mBFacturacion.setOpcRetefuente(true);
                                    mBFacturacion.setFuente1(Math.round(mBFacturacion.Fac.getValorNetoFacturado() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100);

                                }
                                if (mBFacturacion.listaImpuesto.get(i).getNombreImpuesto().contains("ICA")) {
                                    mBFacturacion.setOpcReteIca(true);
                                    mBFacturacion.setIca1(Math.round(mBFacturacion.Fac.getValorNetoFacturado() * mBFacturacion.listaImpuesto.get(i).getTasaImpuesto()) / 100);

                                }
                            }

                            mBFacturacion.setTotalpagarFactAnt((long) (mBFacturacion.Fac.getValorNetoFacturado() + mBFacturacion.getIva()
                                    - mBFacturacion.getReteiva() - mBFacturacion.getFuente1() - mBFacturacion.getIca1()));
                            this.url_pagina_Facturacion = "../Facturacion/FormF-FacExcedente.xhtml";
                        } catch (ParseException e) {
                            mbTodero.setMens("Error: " + e.getMessage());
                            mbTodero.error();
                        }
                    }

                    break;

                case "ConsulFact":

                    mBFacturacion.Fac = new LogFacturacion();
                    fecha1 = fecha.getTime();
                    mbSeguimiento.setFecha_actual(FormatCompleto.format(fecha1));
                    mBConsultaFactu.limpiar();
                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.cargDepto();
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                    mbAvaluo.setCargaTipAvaluo(new ArrayList<>());
                    mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());
                    Con1 = new LogConsulta();
                    Con1.SP_CalculosFac();

                    this.url_pagina_Facturacion = "../Facturacion/ConsultFac.xhtml";
                    break;

                case "PendiXGrupos":
                    this.url_pagina_Facturacion = "../Facturacion/PendientGrupos.xhtml";
                    break;

                case "FacGrupo":

                    mBFacturacion.Fac = new LogFacturacion();
                    int validar = 0;
                    int validarAnt = 0;
                    int valorAnticiposDi = 0;
                    LogEntidad EntidFact = new LogEntidad();
                    LogCliente ClientFact = new LogCliente();
                    mBAdministacion.getCargarTipi().clear();
                    if (mBFacturacion.getSelectListPendientXFact() != null && mBFacturacion.getSelectListPendientXFact().size() > 1) {

                        for (int i = 0; i < mBFacturacion.getSelectListPendientXFact().size(); i++) {

                            if (mBFacturacion.getSelectListPendientXFact().get(i).getGrupos().equalsIgnoreCase(mBFacturacion.getSelectListPendientXFact().get(0).getGrupos())) {
                            } else {
                                validar++;
                            }

                            mBFacturacion.setListaAnticipos(mBFacturacion.Factu.ConsulAnticipoPoD(mBFacturacion.getSelectListPendientXFact().get(i).getCodSeg()));
                            if (mBFacturacion.ListaAnticipos.size() > 0) {
                                validarAnt++;
                            }
                        }

                        if (validar == 0 && validarAnt == 0) {

                            mBAdministacion.setCargarTipi(mBAdministacion.OnTipificacion("Avaluo_ValAd"));
                            mBFacturacion.setCargarNumAvalu(mBFacturacion.CargarListaAvaluos());
                            mBFacturacion.CargarNFactura();
                            mBFacturacion.setIvaBD((int) Fac.impuestoFactAntcpo().get(0).getTasaIva());

                            for (int i = 0; i < mBFacturacion.getSelectListPendientXFact().size(); i++) {

                                mBArchivo.setListClientesPersonasAFacturarAntic(ClientFact.ConsultaClienPerFact(1, mBFacturacion.getSelectListPendientXFact().get(i).getCodAva()));
                                mBArchivo.setListEntidadesPersonasAFacturarAntic(EntidFact.consultaEntidadPorEntiFacturar(mBFacturacion.getSelectListPendientXFact().get(i).getCodAva()));

                                if (mBArchivo.getListClientesPersonasAFacturarAntic().size() > 0 && !mBArchivo.getListClientesPersonasAFacturarAntic().isEmpty()) {

                                    int tamano = mBArchivo.getListClientesPersonasAFacturarAntic().size();
                                    for (int j = 0; j < tamano; j++) {
                                        mBFacturacion.setCli1(mBArchivo.getListClientesPersonasAFacturarAntic().get(0).getCodCliente());
                                        mBFacturacion.setCli2((tamano > 1) ? mBArchivo.getListClientesPersonasAFacturarAntic().get(1).getCodCliente() : 0);
                                        mBFacturacion.setCli3((tamano > 2) ? mBArchivo.getListClientesPersonasAFacturarAntic().get(2).getCodCliente() : 0);
                                        mBFacturacion.setOpcionFacturarA("Cliente");
                                    }

                                }
                                if (mBArchivo.getListEntidadesPersonasAFacturarAntic().size() > 0 && !mBArchivo.getListEntidadesPersonasAFacturarAntic().isEmpty()) {
                                    int tamano = mBArchivo.getListEntidadesPersonasAFacturarAntic().size();
                                    for (int j = 0; j < mBArchivo.getListEntidadesPersonasAFacturarAntic().size(); j++) {
                                        mBFacturacion.setCli1(mBArchivo.getListEntidadesPersonasAFacturarAntic().get(0).getCodEntidad());
                                        mBFacturacion.setCli2((tamano > 1) ? mBArchivo.getListEntidadesPersonasAFacturarAntic().get(1).getCodEntidad() : 0);
                                        mBFacturacion.setCli3((tamano > 2) ? mBArchivo.getListEntidadesPersonasAFacturarAntic().get(2).getCodEntidad() : 0);
                                        mBFacturacion.setOpcionFacturarA("Entidad");
                                    }

                                }

                                this.url_pagina_Facturacion = "../Facturacion/FormF-facGrupos.xhtml";

                                valorAnticiposDi = valorAnticiposDi + Ant.SumaAnticipos(mBFacturacion.getSelectListPendientXFact().get(i).getCodSeg());
                                mBFacturacion.setValorAnti(valorAnticiposDi);

                                mBFacturacion.setListaAnticiposPop(Ant.ConsulReparAnti(mBFacturacion.getSelectListPendientXFact().get(i).getCodSeg()));

                                for (int j = 0; j < mBFacturacion.getListaAnticiposPop().size(); j++) {
                                    LogAnticipo Antici = new LogAnticipo();
                                    Antici.setCodAnticipo(mBFacturacion.getListaAnticiposPop().get(j).getCodAnticipo());
                                    Antici.setCodAvaluo(mBFacturacion.getListaAnticiposPop().get(j).getCodAvaluo());
                                    Antici.setMultiple(mBFacturacion.getListaAnticiposPop().get(j).getMultiple());
                                    Antici.setValorReparClie(mBFacturacion.getListaAnticiposPop().get(j).getValorReparClie());
                                    Antici.setNombreCliente(mBFacturacion.getListaAnticiposPop().get(j).getNombreCliente());
                                    Antici.setFechaAnticipoAprobacion(mBFacturacion.getListaAnticiposPop().get(j).getFechaAnticipoAprobacion());
                                    Antici.setAprobadoPor(mBFacturacion.getListaAnticiposPop().get(j).getAprobadoPor());
                                    mBFacturacion.getListTablaAnticipos().add(Antici);
                                }

                            }
                        } else {
                            mbTodero.setMens(((validar > 0) ? "Debe seleccionar avaluos de un mismo cliente para poder realizar facturacion en grupos.\n " : "")
                                    + ((validarAnt > 0) ? "El avaluo tiene anticipos asociados que aún no han sido aprobados y se encuentran devueltos, realice este proceso antes de facturar" : ""));
                            mbTodero.info();
                            mBFacturacion.getCargarNumAvalu().clear();
                        }

                    } else {
                        mbTodero.setMens("Selecciones mas de avalúo del mismo cliente");
                        mbTodero.info();
                    }

                    mBFacturacion.getListTablaLiqTabla().size();
                    break;

                default:
                    this.url_pagina_Facturacion = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Facturacion()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    public void url_Menu_Envio(String url_interna_Envio) throws ParseException {

        this.nombre_pagina_interna = "";
        this.nombre_pagina_interna = url_interna_Envio;

        switch (nombre_pagina_interna) {

            case "pendiEnvio":
                mBEnvio.setListPendientXEnvi(new ArrayList<>());
                mBEnvio.setListPendientXEnvi(Envi.PendientXEnvio());
                this.url_pagina_Facturacion = "../Envio/FormE-PendEnvio.xhtml";
                break;

            case "consultaEnvio":
                mBEnvio.setListConsultaEnvio(new ArrayList<>());
                this.url_pagina_Facturacion = "../Envio/FormE-ConsultaE.xhtml";

                break;

        }
    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Revision
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_Revision String que contiene el tipo de submenu al que
     * redireccionara
     * @throws java.text.ParseException
     * @since 01-05-2015
     */
    public void url_Menu_Revision(String url_interna_Revision) throws ParseException {
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_Revision;
            Calendar fecha = new GregorianCalendar();
            Date fecha1 = new Date();
            mBEntregaYRevision.consultaTotalRegistrosTablas();
            switch (this.nombre_pagina_interna) {

                case "Reasignar":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBRadicacion.setReasignar(0);
                    mBRadicacion.setFiltroNumSeg(0);
                    mBRadicacion.setObsReasig("");
                    mBRadicacion.setNewValReasig(0);
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-Reasignar.xhtml";
                    break;
                // Permite mostrar la informacion de los avaluos devueltos por el Revisor Tecnico y Juridico

                case "MisDevoluciones":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.consultaMisDevolu(1);
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-MisAvaluosDevueltos.xhtml";
                    break;

                case "ReasignarForm":
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBRadicacion.setReasignar(0);
                    mBRadicacion.setCont(0);
                    mBFacturacion.Fac = new LogFacturacion();
                    if (mBConsultaFactu.getFiltrarX().equalsIgnoreCase("Factura")) {
                        valCom = Fac.numFacAsoAva(mBConsultaFactu.getNumFac());
                        if (valCom.next()) {
                            if (valCom.getInt("F") != 0) {
                                if (valCom.getInt("N") == 1) {
                                    mBRadicacion.setFiltroNumSeg(valCom.getInt("seg"));
                                    mBRadicacion.setFiltroNumAva(valCom.getInt("ava"));
                                } else if (mBConsultaFactu.getNumFac() == 0) {
                                    mbTodero.setMens("Ingrese un número de factura");
                                    mbTodero.info();
                                    mBRadicacion.setReasignar(0);
                                    mBRadicacion.setCont(1);
                                }
                            } else {
                                mbTodero.setMens("El número de factura no existe");
                                mbTodero.info();
                                mBRadicacion.setCont(1);
                            }
                        }

                    }

                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                    if (mBConsultaFactu.getFiltrarX().equalsIgnoreCase("Avaluo") && mBRadicacion.getFiltroNumAva() <= 0) {
                        mBRadicacion.setCont(1);
                        mbTodero.setMens("Ingrese en número del avalúo");
                        mbTodero.info();
                    } else if (Fac.CantFacturaExc(mBFacturacion.Fac.validarNAval(mBRadicacion.getFiltroNumAva())) == 0) {
                        Fac = new LogFacturacion();
                        mBRadicacion.setFiltroNumSeg(mBFacturacion.Fac.validarNAval(mBRadicacion.getFiltroNumAva()));
                        mBRadicacion.verInformacion(2, mBRadicacion.getFiltroNumAva(), mBRadicacion.getFiltroNumSeg());
                        LogFacturacion LogFac1 = new LogFacturacion();

                        mBRadicacion.setUltValComAva(LogFac1.NombrePeritoImpreFac("ultiValAva", mBRadicacion.getFiltroNumAva()));

                        // mBArchivo.getListaArchivosAll.add(mBArchivo.getListaArchivosAvaEnt());
                        mBArchivo.getListaArchivosAll().addAll(mBArchivo.getListaArchivosAvaEnt());
                        mBArchivo.getListaArchivosAll().addAll(mBArchivo.getListaArchivosAva());
                        mBArchivo.getListaArchivosAll().addAll(mBArchivo.getListaArchivosFact());

                        mBRadicacion.getUltValComAva();
                        if (mBRadicacion.getReasignar() == 0) {
                            mbTodero.setMens("No se ha encontrado el número del avalúo asociado");
                            mbTodero.info();
                        } else if (mBRadicacion.getInfoFactu() == 0) {
                            mbTodero.setMens("El avalúo no se ha facturado");
                            mbTodero.info();
                        }
                    } else {
                        mbTodero.setMens("El avalúo tiene factura(s) excedentes pendientes de imprimir. Por favor realice este proceso.");
                        mbTodero.info();
                    }
                    mBArchivo.getNAvaluo();
                    break;
                case "MisAsignadosGen":
                    mbTodero.resetTable("FRMRevGen:RevGenTable");
                    mBEntregaYRevision.setEstadoOpcionActividades(false);
                    mBEntregaYRevision.setEstadoOpcionrRegistrar(true);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setListAsignadoRevGeneral(new ArrayList<>());
                    mBEntregaYRevision.setListAsignadoRevGeneral(EntRev.ConsultRevisionGene(mBSesion.codigoMiSesion()));
                    if (mBEntregaYRevision.getListAsignadoRevGeneral().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-PendientRevisGeneral.xhtml";
                    mbTodero.resetTable("FRMRevGen:RevGenTable");
                    break;
                case "MisAsignadosJur":
                    mbTodero.resetTable("FRMRevEsp:RevJurTable");
                    mBEntregaYRevision.setEstadoOpcionActividades(false);
                    mBEntregaYRevision.setEstadoOpcionrRegistrar(true);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setListAsignadoRevJuri(new ArrayList<>());
                    mBEntregaYRevision.setLisAsignadotRevTec(new ArrayList<>());
                    mBEntregaYRevision.setListAsignadoRevJuri(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 2));
                    if (mBEntregaYRevision.getListAsignadoRevJuri().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-PendientRevisEspecific.xhtml";
                    mbTodero.resetTable("FRMRevEsp:RevJurTable");
                    break;
                case "MisAsignadosTec":
                    mbTodero.resetTable("FRMRevEsp:RevTecTable");
                    mBEntregaYRevision.setEstadoOpcionActividades(false);
                    mBEntregaYRevision.setEstadoOpcionrRegistrar(true);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setLisAsignadotRevTec(new ArrayList<>());
                    mBEntregaYRevision.setListAsignadoRevJuri(new ArrayList<>());
                    mBEntregaYRevision.setLisAsignadotRevTec(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 3));
                    if (mBEntregaYRevision.getLisAsignadotRevTec().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-PendientRevisEspecific.xhtml";
                    mbTodero.resetTable("FRMRevEsp:RevTecTable");
                    break;
                case "RevisionGeneral":
                    try {

                        if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                            int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                            int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());
                            switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getObservacion()) {
                                case "Falta Revision Juridica y Tecnica":
                                    fecha1 = fecha.getTime();
                                    mbSeguimiento.setFecha_actual(FormatCompleto.format(fecha1));
                                    mBRadicacion.setEstadoTituloRadicacion(true);
                                    mBRadicacion.setEstadoLabelsClienteEnti(true);
                                    mBRadicacion.setRadicacionModificacion(true);
                                    mBRadicacion.setRadicacionRegistro(false);
                                    this.EstadotabRadicacion = false;

                                    //Consulta la lista de checkeo con la informacion que tiene cargada de rev juridica
                                    mBEntregaYRevision.setCargaCheckRevJur(new ArrayList<>());
                                    mBEntregaYRevision.setCargaCheckRevJur(mBEntregaYRevision.ConsListasCheck("RJ", 'J'));
                                    mBEntregaYRevision.setCargaSelecCheckRevJur(new ArrayList<>());
                                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'J');
                                    while (Dat.next()) {
                                        if (Dat.getInt("CodChequeo") != 0) {
                                            mBEntregaYRevision.getCargaSelecCheckRevJur().add(Dat.getInt("CodChequeo"));
                                        }

                                    }

                                    //Consulta la lista de checkeo con la informacion que tiene cargada de rev tecnica                         
                                    mBEntregaYRevision.setCargaCheckRevTec(new ArrayList<>());
                                    mBEntregaYRevision.setCargaCheckRevTec(mBEntregaYRevision.ConsListasCheck("RT", 'T'));
                                    mBEntregaYRevision.setCargaSelecCheckRevTec(new ArrayList<>());
                                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'T');
                                    while (Dat.next()) {
                                        mBEntregaYRevision.getCargaSelecCheckRevTec().add(Dat.getInt("CodChequeo"));
                                    }

                                    //Consulta la informacion de las modificaciones que se le realizaron
                                    mBEntregaYRevision.setCodModificSelecc(new ArrayList<>());
                                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'M');
                                    while (Dat.next()) {
                                        mBEntregaYRevision.setOpcConfirmModificAvaluador(true);
                                        mBEntregaYRevision.getCodModificSelecc().add(Dat.getInt("CodChequeo"));
                                        mBEntregaYRevision.getcargaModificSelecc();
                                    }

                                    mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                                    switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                        case "Predio":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                            break;
                                        case "Maquinaria":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                            break;
                                        case "Mueble":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                            break;
                                    }
                                    mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                                    mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(1, ProEnt, NTipAva, "Revision"));

                                    if (EntRev.ConsultaPermRevision(1, mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), mBSesion.codigoMiSesion()) == true && EntRev.ConsultaPermRevision(2, mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), mBSesion.codigoMiSesion()) == true) {
                                        mBEntregaYRevision.setTipoProcesoER("InserRevisionGeneral");

                                        //Consulta la informacion de la revision tecnica general
                                        DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN GENERAL");
                                        while (DatInfoGeneralRevision.next()) {
                                            mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(DatInfoGeneralRevision.getString("ValorAvaluo"));
                                            mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                            mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                            mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                                        }
                                        this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionGeneral.xhtml";
                                    } else if (EntRev.ConsultaPermRevision(1, mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), mBSesion.codigoMiSesion()) == true && EntRev.ConsultaPermRevision(2, mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), mBSesion.codigoMiSesion()) == false) {
                                        mBEntregaYRevision.setTipoProcesoER("InserRevisionJur");

                                        //Consulta la informacion de la revision tecnica general
                                        DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN JURIDICA");
                                        while (DatInfoGeneralRevision.next()) {
                                            mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(DatInfoGeneralRevision.getString("ValorAvaluo"));
                                            mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                            mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                            mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                                        }
                                        this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionJuridica.xhtml";
                                    }
                                    break;
                                case "Falta Revision Tecnica":
                                    fecha1 = fecha.getTime();
                                    mbSeguimiento.setFecha_actual(FormatCompleto.format(fecha1));
                                    mBRadicacion.setEstadoTituloRadicacion(true);
                                    mBRadicacion.setEstadoLabelsClienteEnti(true);
                                    mBRadicacion.setRadicacionModificacion(true);
                                    mBRadicacion.setRadicacionRegistro(false);
                                    //Consulta la informacion de la revision tecnica general
                                    DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN TECNICA");
                                    while (DatInfoGeneralRevision.next()) {
                                        mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(DatInfoGeneralRevision.getString("ValorAvaluo"));
                                        mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                        mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                        mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                                    }
                                    //Consulta la lista de checkeo con la informacion que tiene cargada de rev tecnica                         
                                    mBEntregaYRevision.setCargaCheckRevTec(new ArrayList<>());
                                    mBEntregaYRevision.setCargaCheckRevTec(mBEntregaYRevision.ConsListasCheck("RT", 'T'));
                                    mBEntregaYRevision.setCargaSelecCheckRevTec(new ArrayList<>());
                                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'T');
                                    while (Dat.next()) {
                                        mBEntregaYRevision.getCargaSelecCheckRevTec().add(Dat.getInt("CodChequeo"));
                                    }
                                    //Consulta la informacion de las modificaciones que se le realizaron
                                    mBEntregaYRevision.setCodModificSelecc(new ArrayList<>());
                                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'M');
                                    while (Dat.next()) {
                                        mBEntregaYRevision.setOpcConfirmModificAvaluador(true);
                                        mBEntregaYRevision.getCodModificSelecc().add(Dat.getInt("CodChequeo"));
                                        mBEntregaYRevision.getcargaModificSelecc();
                                    }
                                    this.EstadotabRadicacion = false;
                                    mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                                    switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                        case "Predio":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                            break;
                                        case "Maquinaria":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                            break;
                                        case "Mueble":
                                            mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                            break;
                                    }
                                    mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                                    mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(1, ProEnt, NTipAva, "Revision"));
                                    mBEntregaYRevision.setTipoProcesoER("InserRevisionTec");
                                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionTecnica.xhtml";
                                    break;
                            }
                        }

                    } catch (Exception e) {
                        mbTodero.setMens("Error en el metodo entrega y Revision" + e.getMessage());
                        mbTodero.error();
                    }
                    break;
                case "RevisionEspecifica":
                    //     mBEntregaYRevision.clearBean();
                    if (mBEntregaYRevision.getListAsignadoRevJuri().isEmpty()) {
                        if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                            int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                            int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());

                            //Consulta la lista de checkeo con la informacion que tiene cargada de rev tecnica                         
                            mBEntregaYRevision.setCargaCheckRevTec(new ArrayList<>());
                            mBEntregaYRevision.setCargaCheckRevTec(mBEntregaYRevision.ConsListasCheck("RT", 'T'));
                            mBEntregaYRevision.setCargaSelecCheckRevTec(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'T');
                            while (Dat.next()) {
                                mBEntregaYRevision.getCargaSelecCheckRevTec().add(Dat.getInt("CodChequeo"));
                            }

                            //Consulta la informacion de la revision tecnica general
                            DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN TECNICA");
                            while (DatInfoGeneralRevision.next()) {
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(DatInfoGeneralRevision.getString("ValorAvaluo"));
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                            }

                            //Consulta la informacion de las modificaciones que se le realizaron
                            mBEntregaYRevision.setCodModificSelecc(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'M');
                            while (Dat.next()) {
                                mBEntregaYRevision.setOpcConfirmModificAvaluador(true);
                                mBEntregaYRevision.getCodModificSelecc().add(Dat.getInt("CodChequeo"));
                                mBEntregaYRevision.getcargaModificSelecc();
                            }

                            mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                            switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                case "Predio":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                    break;
                                case "Maquinaria":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                    break;
                                case "Mueble":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                    break;
                            }
                            mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                            mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(2, ProEnt, NTipAva, "Revision"));
                            mBEntregaYRevision.setTipoProcesoER("InserRevisionTec");
                            this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionTecnica.xhtml";
                        } else {
                            url_Menu_Revision("MisAsignadosTec");

                        }
                    } else if (mBEntregaYRevision.getLisAsignadotRevTec().isEmpty()) {
                        if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                            int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                            int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());

                            mBEntregaYRevision.setCargaCheckRevJur(new ArrayList<>());
                            mBEntregaYRevision.setCargaCheckRevJur(mBEntregaYRevision.ConsListasCheck("RJ", 'J'));
                            mBEntregaYRevision.setCargaSelecCheckRevJur(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'J');
                            while (Dat.next()) {
                                mBEntregaYRevision.getCargaSelecCheckRevJur().add(Dat.getInt("CodChequeo"));
                            }
                            //Consulta la informacion de la revision tecnica general
                            DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN JURIDICA");
                            while (DatInfoGeneralRevision.next()) {
                                //mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(Dat.getString("ValorAvaluo"));
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                            }
                            mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                            switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                case "Predio":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                    break;
                                case "Maquinaria":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                    break;
                                case "Mueble":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                    break;
                            }
                            mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                            mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(2, ProEnt, NTipAva, "Revision"));
                            mBEntregaYRevision.setTipoProcesoER("InserRevisionJur");
                            this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionJuridica.xhtml";
                        } else {
                            url_Menu_Revision("MisAsignadosJur");
                        }
                    }

                    break;

                case "RevisionEspecificaDevoluc":

                    if (mBEntregaYRevision.getListDevolucRevJuri().isEmpty()) {
                        if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                            int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                            int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());

                            //Consulta la lista de checkeo con la informacion que tiene cargada de rev tecnica                         
                            mBEntregaYRevision.setCargaCheckRevTec(new ArrayList<>());
                            mBEntregaYRevision.setCargaCheckRevTec(mBEntregaYRevision.ConsListasCheck("RT", 'T'));
                            mBEntregaYRevision.setCargaSelecCheckRevTec(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'T');
                            while (Dat.next()) {
                                mBEntregaYRevision.getCargaSelecCheckRevTec().add(Dat.getInt("CodChequeo"));
                            }

                            //Consulta la informacion de la revision tecnica general
                            DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN TECNICA");
                            while (DatInfoGeneralRevision.next()) {
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(DatInfoGeneralRevision.getString("ValorAvaluo"));
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                            }

                            //Consulta la informacion de las modificaciones que se le realizaron
                            mBEntregaYRevision.setCodModificSelecc(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'M');
                            while (Dat.next()) {
                                mBEntregaYRevision.setOpcConfirmModificAvaluador(true);
                                mBEntregaYRevision.getCodModificSelecc().add(Dat.getInt("CodChequeo"));
                                mBEntregaYRevision.getcargaModificSelecc();
                            }

                            mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                            switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                case "Predio":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                    break;
                                case "Maquinaria":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                    break;
                                case "Mueble":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                    break;
                            }
                            mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                            mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(2, ProEnt, NTipAva, "Revision"));
                            mBEntregaYRevision.setTipoProcesoER("InserRevisionTec");
                            this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionTecnica.xhtml";
                        } else {
                            url_Menu_Revision("MisAsignadosTec");

                        }
                    } else if (mBEntregaYRevision.getListDevolucRevTec().isEmpty()) {
                        if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                            int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                            int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());

                            mBEntregaYRevision.setCargaCheckRevJur(new ArrayList<>());
                            mBEntregaYRevision.setCargaCheckRevJur(mBEntregaYRevision.ConsListasCheck("RJ", 'J'));
                            mBEntregaYRevision.setCargaSelecCheckRevJur(new ArrayList<>());
                            Dat = EntRev.ConsultaListCheckSeleccionadaAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien(), 'J');
                            while (Dat.next()) {
                                mBEntregaYRevision.getCargaSelecCheckRevJur().add(Dat.getInt("CodChequeo"));
                            }
                            Conexion.Conexion.CloseCon();
                            //Consulta la informacion de la revision tecnica general
                            DatInfoGeneralRevision = EntRev.consultaInformacionRevisionRealizada(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodSeguimiento(), "REVISIÓN JURIDICA");
                            while (DatInfoGeneralRevision.next()) {
                                //mBEntregaYRevision.getEntrRevAvaluosPeritos().setValorComercAvaluo(Dat.getString("ValorAvaluo"));
                                mBEntregaYRevision.getEntrRevAvaluosPeritos().setObservaRevision(DatInfoGeneralRevision.getString("Observacion"));
                                mBEntregaYRevision.setFechaJuridica(DatInfoGeneralRevision.getString("Fecha_Juridica"));
                                mBEntregaYRevision.setFechaTecnica(DatInfoGeneralRevision.getString("Fecha_Tecnica"));
                            }
                            Conexion.Conexion.CloseCon();
                            mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                            switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                                case "Predio":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                    break;
                                case "Maquinaria":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                    break;
                                case "Mueble":
                                    mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                    break;
                            }
                            mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                            mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(2, ProEnt, NTipAva, "Revision"));
                            mBEntregaYRevision.setTipoProcesoER("InserRevisionJur");
                            this.url_pagina_Revision = "../EntregaYRevision/FormRev-RevisionJuridica.xhtml";
                        } else {
                            url_Menu_Revision("MisAsignadosJur");
                        }
                    }

                    break;
                case "CambioAnalistaJ":
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntregaYRevision.setCodProEnt(0);

                    //mBEntregaYRevision.setListAnalistRevJur(new ArrayList<>()); //GCH - NOV2016
                    mBEntregaYRevision.setListAsigRevisionJur(new ArrayList<>()); //GCH - NOV2016
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-ReasigAnalistJuridico.xhtml";
                    break;
                case "CambioAnalistaT":
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntregaYRevision.setCodProEnt(0);
                    mBEntregaYRevision.setListAnalistRevTec(new ArrayList<>());
                    mBEntregaYRevision.setListAsigRevisionTec(new ArrayList<>()); //GCH - NOV2016
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-ReasigAnalistTecnico.xhtml";
                    break;

                case "MisActividades":
                    mbTodero.resetTable("FRMActividadesYRecordEntrega:ActiviRecordTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setEstadoOpcionActividades(true);
                    mBEntregaYRevision.setEstadoOpcionrRegistrar(false);
                    mbSeguimiento.setOpcionTableroControlSeguim("");
                    mBEntregaYRevision.setListActividadesYRecord(new ArrayList<>());
                    mBEntregaYRevision.consultaAvaluosConActiviYRecor();
                    if (mBEntregaYRevision.getListActividadesYRecord().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-ActividadesYRecordatori.xhtml";
                    mbTodero.resetTable("FRMActividadesYRecordEntrega:ActiviRecordTable");
                    break;

                case "DevolucionRevision":
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisGener");
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisJurid");
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisTecnica");
                    mBEntregaYRevision.setEstadoOpcionActividades(false);
                    mBEntregaYRevision.setEstadoOpcionrRegistrar(true);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();

                    if (opcionPNLRevGen == true) {
                        mBEntregaYRevision.consultaAvaluosDevoluc(4);
                        if (mBEntregaYRevision.getListDevolucRevGeneral().isEmpty()) {
                            mBPreRadicacion.setTamañoTablas("70px");
                        } else {
                            mBPreRadicacion.setTamañoTablas("50px");
                        }

                    } else if (opcionPNLRevJur == true) {
                        mBEntregaYRevision.consultaAvaluosDevoluc(2);
                        if (mBEntregaYRevision.getListDevolucRevJuri().isEmpty()) {
                            mBPreRadicacion.setTamañoTablas("70px");
                        } else {
                            mBPreRadicacion.setTamañoTablas("50px");
                        }

                    } else if (opcionPNLRevTec == true) {
                        mBEntregaYRevision.consultaAvaluosDevoluc(3);
                        if (mBEntregaYRevision.getListDevolucRevTec().isEmpty()) {
                            mBPreRadicacion.setTamañoTablas("70px");
                        } else {
                            mBPreRadicacion.setTamañoTablas("50px");
                        }
                    }
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-AvaluosDevueltosRevision.xhtml";
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisGener");
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisJurid");
                    mbTodero.resetTable("FormAvaluosDevueltosRevision:TableDevolucRevisTecnica");
                    break;
                case "PendientAprobar":
                    mbTodero.resetTable("FRMMisPend:MisPendATable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setListMisPendientAprobacion(new ArrayList<>());
                    mBEntregaYRevision.setListMisPendientAprobacion(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 6));
                    this.url_pagina_Revision = "../EntregaYRevision/FormRev-MisPendientAprobacion.xhtml";
                    mbTodero.resetTable("FRMMisPend:MisPendATable");
                    break;
                case "SelecPendientAprobacion":
                    if (mBEntregaYRevision.EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("No ha seleccionado un número de avalúo");
                        mbTodero.warn();
                        mBEntregaYRevision.clearBean();
                    } else {
                        mBEntregaYRevision.consultaInformacionEntrega();
                        this.url_pagina_Revision = "../EntregaYRevision/FormRev-PendientAprobacion.xhtml";
                    }
                    break;
                /*
               case "ConsultaRevi":
                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    this.url_pagina_Revision = "../EntregaYRevision/FormER-ConsultaRevi.xhtml";
                    break;
                 */
            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Revision()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Entrega
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_Entrega String que contiene el tipo de submenu al que
     * redireccionara
     * @since 01-05-2015
     */
    public void url_Menu_Entrega(String url_interna_Entrega) {
        this.nombre_pagina_interna = "";
        this.nombre_pagina_interna = url_interna_Entrega;
        Calendar fecha = new GregorianCalendar();
        Date fecha1 = new Date();
        try {
            mBEntregaYRevision.consultaTotalRegistrosTablas();
            switch (this.nombre_pagina_interna) {
                case "PendienteInforme":
                    mbTodero.resetTable("FRMEntregInf:PendientInforTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBEntregaYRevision.setListEntreg(new ArrayList<>());
                    mBEntregaYRevision.setListEntreg(EntRev.ConsulPendientesInformeEntregPerito(mBSesion.codigoMiSesion()));
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-PendiInfPerit.xhtml";
                    mbTodero.resetTable("FRMEntregInf:PendientInforTable");
                    break;
                case "InformeAvaluo":
                    if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                        mBEntregaYRevision.setTipoProcesoER("InserEntrega");
                        fecha1 = fecha.getTime();
                        mBEntregaYRevision.setFecha_actual(FormatCompleto.format(fecha1));
                        MBUbicacion.getCargaDep().clear();
                        MBUbicacion.cargDepto();
                        mBRadicacion.setEstadoTituloRadicacion(true);
                        mBRadicacion.setEstadoLabelsClienteEnti(true);
                        mBRadicacion.setRadicacionModificacion(true);
                        mBRadicacion.setRadicacionRegistro(false);
                        this.EstadotabRadicacion = false;
                        this.url_pagina_Entrega = "../EntregaYRevision/FormE-EntregaAvaluo.xhtml";

                    }
                    break;
                case "PendientePrint":
                    mbTodero.resetTable("FRMPendPrint:RevGenTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setLisAsignadoPenPrint(new ArrayList<>());
                    mBEntregaYRevision.setLisAsignadoPenPrint(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 4));
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-PendienteImpresion.xhtml";
                    mbTodero.resetTable("FRMPendPrint:RevGenTable");
                    break;
                case "CargArchiv":
                    this.url_pagina_Entrega = "../EntregaYRevision/EntregaInforme.xhtml";
                    break;
                case "CambioAnalista":
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntregaYRevision.setCodProEnt(0);
                    mBEntregaYRevision.setListAsigEntrega(new ArrayList<>());
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-ReasigAnalistEntrega.xhtml";
                    break;
                case "ImpresionAvaluo":
                    mBEntregaYRevision.clearBean();
                    if (mBEntregaYRevision.EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Por favor seleccion un numero de avaluo para realizar la impresion");
                        mbTodero.warn();
                    } else {
                        //Metodo carga la informacion de los avaluos
                        //mBEntregaYRevision.cargarInformaAvaluo();
                        if ("Predio".equals(mBEntregaYRevision.EntrRevAvaluosPeritos.getTipoAva())) {
                            mBEntregaYRevision.setListConstruccion(new ArrayList<>());
                            mBEntregaYRevision.setListConstruccion(mBAdministacion.consultParametro("Zonas", "Construcción"));
                            mBEntregaYRevision.setListMedicion(new ArrayList<>());
                            mBEntregaYRevision.setListMedicion(mBAdministacion.consultParametro("Predio", "Medición"));
                        }

                        mBEntregaYRevision.setCargaArchivosEnt(new ArrayList<>());
                        switch (mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva()) {
                            case "Predio":
                                mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(8));
                                break;
                            case "Maquinaria":
                                mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(9));
                                break;
                            case "Mueble":
                                mBEntregaYRevision.setCargaArchivosEnt(mBArchivo.getConsulArchivos(10));
                                break;
                        }

                        int ProEnt = Ava.ConsulTipEntAva(mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodAvaluo(), mBEntregaYRevision.getEntrRevAvaluosPeritos().getCodNBien());
                        int NTipAva = CarArc.ConsultaTipAval(mBEntregaYRevision.getEntrRevAvaluosPeritos().getTipoAva());

                        MBUbicacion.cargDepto();

                        mBAdministacion.getCargaTipoPredios().clear();
                        mBAdministacion.setCargaTipoPredios(mBAdministacion.getConsTipoPredios());

                        mBAdministacion.getCargaTipoMaquinaria().clear();
                        mBAdministacion.setCargaTipoMaquinaria(mBAdministacion.getConsTipoMaquinaria());

                        mBEntregaYRevision.setLisArcRegla(new ArrayList<>());
                        mBEntregaYRevision.setLisArcRegla(Adm.ConsulListaReglasProceso(1, ProEnt, NTipAva, "Revision"));

                        mBEntregaYRevision.setCargaCheckImp(new ArrayList<>());
                        mBEntregaYRevision.setCargaCheckImp(mBEntregaYRevision.ConsListasCheck("IA", 'I'));
                        this.url_pagina_Entrega = "../EntregaYRevision/FormE-ImpresionAvaluo.xhtml";
                    }
                    break;
                case "DevolAvalEntrega":
                    mbTodero.resetTable("FormAvaluosDevueltosEntrega:TableDevolucEntrega");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.consultaAvaluosDevoluc(1);
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-AvaluosDevueltosEntrega.xhtml";
                    mbTodero.resetTable("FormAvaluosDevueltosEntrega:TableDevolucEntrega");
                    break;
                case "MisEntregados":
                    mbTodero.resetTable("FRMMisEnt:MisEntTable");
                    mBEntregaYRevision.clearBean();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.setListMisEntregados(new ArrayList<>());
                    mBEntregaYRevision.setListMisEntregados(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 5));
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-MisEntregados.xhtml";
                    mbTodero.resetTable("FRMMisEnt:MisEntTable");
                    break;

                case "MisActividades":
                    mbTodero.resetTable("FRMActividadesYRecordEntrega:ActiviRecordTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mBEntregaYRevision.clearBean();
                    mbSeguimiento.setOpcionTableroControlSeguim("");
                    mBEntregaYRevision.setListActividadesYRecord(new ArrayList<>());
                    mBEntregaYRevision.consultaAvaluosConActiviYRecor();
                    if (mBEntregaYRevision.getListActividadesYRecord().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-ActividadesYRecordatori.xhtml";
                    mbTodero.resetTable("FRMActividadesYRecordEntrega:ActiviRecordTable");
                    break;

                case "CargaInforEntrega":
                    if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                        mBEntregaYRevision.setEstadoOpcionModificar(true);
                        mBEntregaYRevision.setEstadoOpcionActividades(false);
                        this.url_pagina_Entrega = "../EntregaYRevision/FormE-FMisEntregados.xhtml";
                    }

                    break;

                case "FormEntregaActividades":
                    if (mBEntregaYRevision.getEntrRevAvaluosPeritos() != null) {
                        mBEntregaYRevision.setEstadoOpcionModificar(false);
                        mBEntregaYRevision.setEstadoOpcionActividades(true);
                        this.url_pagina_Entrega = "../EntregaYRevision/FormE-FMisEntregados.xhtml";
                    }

                    break;
                case "RevisionEntrega":
                    mbTodero.resetTable("FRMPendPrint:RevGenTable");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setLisAsignadoPenPrint(new ArrayList<>());
                    mBEntregaYRevision.setLisAsignadoPenPrint(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 7));
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-PendienteImpresion.xhtml";
                    mbTodero.resetTable("FRMPendPrint:RevGenTable");
                    break;
                case "AprobadosImpresion":
                    mbTodero.resetTable("FRMAprobadosImpres:TableAprobadosImpres");
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mBEntregaYRevision.setListAprobados(new ArrayList<>());
                    mBEntregaYRevision.setListAprobados(EntRev.ConsulPendientesRevision(mBSesion.codigoMiSesion(), 8));
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-AprobadosImpresion.xhtml";
                    mbTodero.resetTable("FRMAprobadosImpres:TableAprobadosImpres");
                    break;

                case "ConsultaEntrega":
                    /*
                    this.url_pagina_Entrega = "../EntregaYRevision/FormE-ConsultaEntrega.xhtml";
                     */
                    break;

                case "AprobadosParaImpresion":
                    mBEntregaYRevision.clearBean();
                    if (mBEntregaYRevision.EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Por favor seleccion un numero de avaluo para realizar la impresión");
                        mbTodero.warn();
                    } else {
                        //Metodo carga la informacion de los avaluos
                        mBEntregaYRevision.cargarInformaAvaluo();
                        /*      if ("Predio".equals(mBEntregaYRevision.EntrRevAvaluosPeritos.getTipoAva())) {
                         mBEntregaYRevision.setListConstruccion(new ArrayList<SelectItem>());
                         mBEntregaYRevision.setListConstruccion(mBAdministacion.consultParametro("Zonas", "Construcción"));
                         mBEntregaYRevision.setListMedicion(new ArrayList<SelectItem>());
                         mBEntregaYRevision.setListMedicion(mBAdministacion.consultParametro("Predio", "Medición"));
                         }*/

                        mBEntregaYRevision.setCargaCheckImp(new ArrayList<>());
                        mBEntregaYRevision.setCargaCheckImp(mBEntregaYRevision.ConsListasCheck("IA", 'I'));
                        this.url_pagina_Entrega = "../EntregaYRevision/FormE-Aprobados.xhtml";
                    }
                    break;
                default:
                    this.url_pagina_Entrega = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Entrega()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que redirecciona a los difererentes submenus de el modulo de
     * Seguimiento
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @param url_interna_Seguim String que contiene el tipo de submenu al que
     * redireccionara
     * @since 01-05-2015
     */
    public void url_Menu_Seguimiento(String url_interna_Seguim) {
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_Seguim;
            Calendar fecha = new GregorianCalendar();
            Date fecha1 = new Date();
            mbSeguimiento.consultaTotalRegistrosTablas();
            switch (this.nombre_pagina_interna) {
                case "PendienteAsigInforme":
                    mBAdministacion.getProEntAll().clear();
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntregaYRevision.setCodProEnt(0);
                    mBEntregaYRevision.setListAsigEntrega(new ArrayList<>());
                    mBRadicacion.limpiarRadicacionGeneral();
                    mBEntregaYRevision.clearBean();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    this.url_pagina_Seguimiento = "../Seguimiento/FormS-PendiInfPerit.xhtml";
                    break;
                case "AsigAnalistaEntrega":
                    mBEntregaYRevision.consultCambioAnalista(1);
                    break;

                case "SeguimientoMisAsignados":

                    mBArchivo.setSegmto(2);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mbSeguimiento.setEstadoTituloDialogActivRecordat("Seguimiento");
                    //   mbTodero.resetTable("FormMisAsignados:RadicadosSegTable");
                    mbSeguimiento.consultaRadicacionesSegumiento(2);
                    mBAdministacion.getCargarBancos().clear();
                    mBAdministacion.setCargarBancos(mBAdministacion.OnBancos());
                    // mbSeguimiento.setListSeguimiento(mbSeguimiento.Seg.ConsulSeguimAvaluos(mBSesion.codigoMiSesion()));
                    this.url_pagina_Seguimiento = "../Seguimiento/FormS-MisAsignadosSeguimiento.xhtml";
                    break;

                case "PendientesSeguimiento":
                    mBArchivo.setSegmto(2);
                    mBRadicacion.limpiarRadicacionGeneral();
                    mbSeguimiento.limpiarCamposActividadesYRecord();
                    mbSeguimiento.setEstadoTituloDialogActivRecordat("Seguimiento");
                    mbTodero.resetTable("FormMisAsignados:RadicadosSegTable");
                    mbSeguimiento.consultaRadicacionesSegumiento(3);
                    if (mbSeguimiento.getListSeguimientoRadicacionesConCita().isEmpty()) {
                        mBPreRadicacion.setTamañoTablas("70px");
                    } else {
                        mBPreRadicacion.setTamañoTablas("50px");
                    }
                    mBAdministacion.getCargarBancos().clear();
                    mBAdministacion.setCargarBancos(mBAdministacion.OnBancos());
                    // mbSeguimiento.setListSeguimiento(mbSeguimiento.Seg.ConsulSeguimAvaluos(mBSesion.codigoMiSesion()));
                    this.url_pagina_Seguimiento = "../Seguimiento/FormS-PendientesSeguimiento.xhtml";
                    break;
                case "ControlSeguimiento":
                    this.url_pagina_Seguimiento = "../Seguimiento/Seguimiento_avaluo.xhtml";
                    break;
                case "RadicacionFormSeguimiento":
                    // mBRadicacion.limpiarRadicacionGeneral();
                    if (mBRadicacion.getRadi() != null) {
                        fecha1 = fecha.getTime();
                        mbSeguimiento.setFecha_actual(FormatCompleto.format(fecha1));

                        MBUbicacion.getCargaDep().clear();
                        MBUbicacion.cargDepto();

                        mBAdministacion.getCargaMotivoAnulaciones().clear();
                        mBAdministacion.setCargaMotivoAnulaciones(mBAdministacion.getMotivoAnulacion());

                        mbSeguimiento.setOpcionTableroControlSeguim("");
                        mBRadicacion.setEstadoTituloRadicacion(true);
                        mBRadicacion.setEstadoLabelsClienteEnti(true);
                        mBRadicacion.setRadicacionModificacion(true);
                        mBRadicacion.setRadicacionRegistro(false);
                        mbSeguimiento.setEstadoPanelMisActivid(false);
                        mbSeguimiento.setEstadoPanelMisRecordat(false);
                        mbSeguimiento.setEstadoPanelActividAsign(false);
                        mbSeguimiento.setEstadoPanelRecordatAsign(false);
                        mbSeguimiento.setOpcionCitaAvaluo(""); //GCH NOV2016
                        this.EstadotabRadicacion = false;

                        if (!"Ninguna".equals(mBRadicacion.getRadi().getObservacionDocument())) {
                            mbTodero.setMens("Observaciones a tener en cuenta: \n" + mBRadicacion.getRadi().getObservacionDocument());
                            mbTodero.warn();
                        }

                        this.url_pagina_Seguimiento = "../Seguimiento/FormS-RadicacionSeguim.xhtml";
                    }

                    break;

                case "ReAsignacion":
                    mbSeguimiento.setEstadoOpcionAsignado(false);
                    mBAdministacion.limpiarAdministracion();
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mbSeguimiento.setCodProEnt("");
                    mbSeguimiento.setListSeguimientoAsignados(new ArrayList<>());

                    this.url_pagina_Seguimiento = "../Seguimiento/FormS-ReasignSeguimiento.xhtml";
                    break;

                case "ConsultaSeg":

                    mbConsSeguimiento.clearBean();
                    fecha1 = fecha.getTime();
                    mbSeguimiento.setFecha_actual(FormatCompleto.format(fecha1));

                    MBUbicacion.getCargaDep().clear();
                    MBUbicacion.cargDepto();
                    mBAdministacion.setProEntAll(new ArrayList<>());
                    mBAdministacion.setProEntAll(mBAdministacion.getConsulProEntAll());
                    mBEntidad.setCargaEnt(new ArrayList<>());
                    mBEntidad.setCargaEnt(mBEntidad.cargEntidad());

                    mbAvaluo.setCargaTipAvaluo(new ArrayList<>());
                    mbAvaluo.setCargaTipAvaluo(mbAvaluo.cargTipAvaluo());

                    this.url_pagina_Seguimiento = "../Seguimiento/FormS-ConsultaSeg.xhtml";
                    break;

                default:
                    this.url_pagina_Seguimiento = "../NoAlcansada.xhtml";
                    break;
            }

            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Revision = "../Blanco.xhtml";
            this.url_pagina_Facturacion = "../Blanco.xhtml";
            this.url_pagina_Cartera = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Seguimiento()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    String CodAval = "";

    public String Cadena() {
        String CadenaFact = "";
        if (mBCartera.getSelectPendiXCobrar().size() == 1) {
            CadenaFact = String.valueOf(mBCartera.getSelectPendiXCobrar().get(0).getCodFactura());
        } else {
            for (int i = 0; i < mBCartera.getSelectPendiXCobrar().size(); i++) {
                CadenaFact = String.valueOf(mBCartera.getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
            }
            CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
        }
        return CadenaFact;
    }

    public void SeguimientoCartera() {
        try {
            //mBCartera.setListaPendientesPorCobrar(Cart.ConsultaPndientesCobro());
            //Cargar la informacion de los Impuestos
            String Cadena = mBCartera.Cadena();
            if (mBCartera.getSelectPendiXCobrar().get(0).getTipoFactura().equals("Normal")) {
                //Cargar la Informacion Inicial del Ica
                url_Menu_Cartera("CargaIcaAvaluo");
                //Mostrar el total de Ica 
                Cart.setInfoXAval(2);
                mBCartera.setListaImpuestosFacturas(Cart.Impuestos(1, "cambio_impuesto", Cadena));
            } else if (mBCartera.getSelectPendiXCobrar().get(0).getTipoFactura().equals("Copia") || mBCartera.getSelectPendiXCobrar().get(0).getTipoFactura().equals("Concepto") || mBCartera.getSelectPendiXCobrar().get(0).getTipoFactura().equals("Excedente")) {
                mBCartera.setListaImpuestosFacturas(Cart.ImpuestosinAval(5, "cambio_impuesto", Cadena));
            }
            //Cargar la informacion de Cambio de Impuesto las tasas aplicadas por avaluo.
            mBCartera.CargarTasaIca();
            //Cargar la informacion de las tasas aplicadas a las Facturas
            mBCartera.GestionCartera("InfoImpst");
            // se envia el tipo de parametro uno , esto con el fin de consultar la informacion inicial de la factura  y sus impuestos iniciales
            mBCartera.ValidarSeleccion(1);
            mBCartera.setValorInicialFcatura(mBCartera.getValorFactura());
            mBCartera.setListaObservaciones(Cart.ListaObservaciones(Cadena, 1));
            //Cargar la Informacion correspondiente a los abonos y Notas Credito asociadas
            //NOtas Credito
            mBCartera.setValorConsulta(1);
            mBCartera.GestionCartera("FactNotaCre");
            //Abonos
            mBCartera.setCountAbono(1);
            mBCartera.GestionCartera("FactAbono");
            //Carga la informacion de los Bancos
            mBCartera.getCargarBancos().clear();
            mBCartera.setCargarBancos(mBCartera.OnBancos());
            //Carga la informacion de la tablaTipo Recibo (Avaluo o Caja)
            mBCartera.getCargarRecibo().clear();
            mBCartera.setCargarRecibo(mBCartera.OnRecibos());
            //Cargar tipos de Pago
            mBCartera.getCargarTipoPago().clear();
            mBCartera.setCargarTipoPago(mBCartera.OnMediosPago());
            //Mostrar EFECTIVO PAGO POR DEFECTO
            mBCartera.setCargarFacturas(mBCartera.OnFacturas());
            //Traer la ultima fecha de consignacion
        } catch (Exception e) {

        }
    }

    public void url_Menu_Cartera(String url_interna_Cartera) {
        // FormF-AvaPendientXFact
        try {
            this.nombre_pagina_interna = "";
            this.nombre_pagina_interna = url_interna_Cartera;
            mBCartera.InfoClienteCartera = 1;
            switch (nombre_pagina_interna) {
                case "PendientesxCobrar":
                    mBCartera.Limpiar(1);
                    mBCartera.setSelectPendiXCobrar(null);
                    mBCartera.setListaPendientesPorCobrar(new ArrayList<>());
                    mBCartera.setListaPendientesPorCobrar(Cart.ConsultaPndientesCobro());
                    this.url_pagina_Cartera = "../Cartera/FormC-FacturasPendientes.xhtml";
                    break;

                case "PndXAvaluo":
                    mBCartera.Limpiar(1);
                    mBCartera.setSelectPendiXCobrar(null);
                    mBCartera.setListaPendientesPorCobrar(new ArrayList<>());
                    mBCartera.setListaPendientesPorCobrar(Cart.ConsultaPndientesCobro());
                    this.url_pagina_Cartera = "../Cartera/FormC-FacturaPndXCobrar.xhtml";
                    break;

                case "FacturasPagos":
                    //Coonsulta con estado de Pc, pp, sp , respectivamente son  (Pago parcial, Pagada, Saldo Afavor)
                    mBCartera.getListaFactPagos().clear();
                    mBCartera.setListaFactPagos(Cart.ConsultaPagosFactura());
                    this.url_pagina_Cartera = "../Cartera/FormC-FacturasPago.xhtml";
                    break;
                case "SeguimientoFactura":
                    try {
                        //Colocar una condicion para no ejecutar este metodo una vez se este en la reparticion del Pago 
                        mBCartera.setObservCobro("");
                        if (mBCartera.getListaImpuestosFacturas().size() == 1) {
                            SeguimientoCartera();
                        }
                        if (mBCartera.getCobrar().equals("Cobrar")) {
                            SeguimientoCartera();
                            mBCartera.setAbono(mBCartera.getTpagar());
                        } else {
                            mBCartera.setValorConsig(mBCartera.getAbono());
                            mBCartera.setMayorValor(mBCartera.getAbono() - mBCartera.getPendtPorPagar());
                            mBCartera.setValorConsig(mBCartera.getAbono());
                            //Facturacion Normal                          
                            //  this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                            this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                        }
                        if (mBCartera.getValidar() == 1) {
                            // this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                            this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                        } else {
                            mBCartera.setSelectPendiXCobrar(null);
                        }
                    } catch (Exception e) {
                        mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Cartera()' causado por: " + e.getMessage());
                        mbTodero.info();
                    }
                    break;
                case "MayorMenorValor":
                    try {
                        if (mBCartera.validacion <= 0) {
                            if (mBCartera.getAbono() == mBCartera.getPendtPorPagar() || mBCartera.getPendtPorPagar() > mBCartera.getAbono()) {
                                mBCartera.getAbono();
                                /*Pago */
                                mBCartera.getAbonos();
                                /*Respectivo valos a cancelar */
                                mBCartera.getPendtPorPagar();
                                /*Saldo a Pagar*/
                                // Cargar Pagos Y notas Credito 
                                //NOtas Credito
                                mBCartera.setValorConsulta(1);
                                mBCartera.GestionCartera("FactNotaCre");
                                //Abonos
                                //CountAbono = 3 es para cargar los abonos a la factura , pero que no muestre el cuadro de consulta.
                                mBCartera.setCountAbono(3);
                                mBCartera.GestionCartera("FactAbono");
                                // Suma de Abonos a la Factura 
                                mBCartera.setAbonos(0);
                                for (int i = 0; i < mBCartera.ListNotaAbonos.size(); i++) {
                                    mBCartera.setAbonos((int) mBCartera.ListNotaAbonos.get(i).getValorFactura() + mBCartera.getAbonos());
                                }
                                mBCartera.setPendtPorPagar(mBCartera.getTpagar() - mBCartera.getAbonos());
                            } else {
                                mBCartera.setMayorValor(0);
                                mBCartera.setValorConsig(mBCartera.getAbono());
                                mBCartera.setMayorValor(mBCartera.getPendtPorPagar() - mBCartera.getAbono());
                                RequestContext.getCurrentInstance().execute("PF('AlertPago').show()");
                            }
                        }
                    } catch (Exception e) {
                        mbTodero.setMens("Error en el metodo MayorMenorValor  " + e.getMessage());
                        mbTodero.info();
                    }

                    break;
                case "CargarDistribucion":
                    String Cadena = mBCartera.Cadena();
                    mBCartera.LlenarDistribPago();
                    break;
                case "ConsulCartera":
                    this.url_pagina_Cartera = "../Cartera/FormC-ConsultaCartera.xhtml";
                    break;
                case "Confirmacion":
                    mBCartera.setValida("Acepto");
                    mBCartera.calcularIcaCiudad();
                    this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                    break;
                case "ConsulSadosAfavor":
                    mBCartera.setListaSaldoAFavor(null);
                    mBCartera.setListaSaldoAFavor(new ArrayList<>());
                    mBCartera.setListaSaldoAFavor(Cart.SaldoaFavor());
                    this.url_pagina_Cartera = "../Cartera/FormC-SaldosAFavor.xhtml";
                    break;
                case "ModificacionIca":
                    mBCartera.MostrarInfo();
                    this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                    break;
                case "CargaIcaAvaluo":
                    Cart.setInfoXAval(1);
                    mBCartera.setListaInfoAvaluos(Cart.ImpsDtAval("CodAval = cambio_impuesto.Fk_Cod_Avaluo", Cadena()));
                    int Total = 0;
                    mBCartera.EstIcaAval = true;
                    for (int i = 0; i < mBCartera.listaCiudad.size(); i++) {
                        Total = Integer.parseInt(mBCartera.listaCiudad.get(i).getValorAjuste()) + Total;
                        mBCartera.setIcaAvaltmp(Total);
                    }
                    for (int i = 0; i < mBCartera.getListaInfoAvaluos().size(); i++) {
                        Total = mBCartera.getListaInfoAvaluos().get(i).getRteIca() + Total;
                        mBCartera.setIcaAvaltmp(Total);
                    }
                    mBCartera.setLblIcaAvaltmp("RETE ICA %");
                    CodAval = "";
                    if (mBCartera.getListaInfoAvaluos().size() == 1) {
                        CodAval = String.valueOf(mBCartera.getListaInfoAvaluos().get(0).getCodAval());
                    } else {
                        for (int i = 0; i < mBCartera.getListaInfoAvaluos().size(); i++) {
                            CodAval = String.valueOf(mBCartera.getListaInfoAvaluos().get(i).getCodAval()) + "," + CodAval;
                        }
                        if (!"".equals(CodAval)) {
                            CodAval = CodAval.substring(0, CodAval.length() - 1);
                        }
                    }
                    // Cargar la informacion de las ciudades de los avaluos
                    if (!"".equals(CodAval)) {
                        mBCartera.setListaCiudad(Cart.AvalCiudad(CodAval));
                    }
                    this.url_pagina_Cartera = "../Cartera/FormC-SegFac.xhtml";
                    break;

                default:
                    this.url_pagina_Cartera = "../NoAlcansada.xhtml";
                    break;
            }
            this.url_pagina_Administracion = "../Blanco.xhtml";
            this.url_pagina_Pre_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Radicacion = "../Blanco.xhtml";
            this.url_pagina_Seguimiento = "../Blanco.xhtml";
            this.url_pagina_Entrega = "../Blanco.xhtml";
            this.url_pagina_Anticipo = "../Blanco.xhtml";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".url_Menu_Cartera()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    public void ConsulCartera(String prmtro, String interno) {
        switch (prmtro) {
            case "Xpagar":
                switch (interno) {
                    //Facturas Pendientes por Pagar 
                    // por persona Cliente/Entidad
                    case "PorPagarPers":
                        this.url_pagina_Cartera = "../Cartera/Consulta/FormC-FactXPagar.xhtml";
                        break;

                    case "Porestracto":

                        break;
                }
                break;
        }
    }
}
