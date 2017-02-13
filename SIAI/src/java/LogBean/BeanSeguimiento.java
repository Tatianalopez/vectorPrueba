/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBean;

import Logic.LogAdministracion;
import Logic.LogAvaluo;
import Logic.LogCliente;
import Logic.LogEmpleado;
import Logic.LogEntidad;
import Logic.LogPerito;
import Logic.LogRadicacion;
import Logic.LogSeguimiento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso y manejo del seguimiento y control que se
 * tiene sobre cada avaluo radicado </b></p>
 *
 * @author Jhojan Stiven Rodriguez
 * @author Maira Alejandra Carvajal
 * @version 2.0.0
 * @since 01-12-2014 1.0.0
 *
 *
 */
@ManagedBean(name = "MBSeguimiento")
@ViewScoped
public class BeanSeguimiento {

    /**
     * Variables utilizadas para el manejo de consultas, correos, visita etc *
     */
    private ResultSet DatObser;
    private ResultSet DatPerExterna;
    private String fechaCorreo;
    private String direccion;
    private String ubicacion;
    private String analista;
    private ResultSet Dat;
    private String nombreResponsableSeguimiento;
    private String correoResponsableSeguimiento;
    private ResultSet DatNombreAccionRecod;
    private String nombreActiviORecorCorreo;
    private String TextoCorreoExternos;
    private int NumeroaAsignadosSeguim;
    private int NumerosPendiSeg;
    private Date fechaNueva;
    private String opcionCitaAvaluo;
    private boolean visitaRealizada;
    private String observacionReasignaCita;

    public BeanSeguimiento() {
    }

    /**
     * Variables de listas que cargan la informacion consultada*
     */
    private List<LogSeguimiento> ListSeguimiento = null;
    private List<LogRadicacion> ListSeguimientoRadicacionesConCita = null;
    private List<LogRadicacion> ListSeguimientoAsignados = null;
    private List<LogRadicacion> ListSeguimSeleccAsignados = new ArrayList<>();
    private List<LogSeguimiento> ListEmpAsignaciones = new ArrayList<>();

    /**
     * tablero de control y seguimiento*
     */
    /**
     * Estados de las opciones de los paneles*
     */
    private String opcionTableroControlSeguim;
    private boolean estadoPanelMisActivid;
    private boolean estadoPanelMisRecordat;
    private boolean estadoPanelActividAsign;
    private boolean estadoPanelRecordatAsign;
    private String estadoTituloDialogActivRecordat;
    private int CodAvaluo;
    private int CodigoSeguimiento;
    private int CodigoBienSeguimiento;

    /**
     * Crear y asignar actividades y recordatorios
     * -----------------------------------------------------------------
     * actividades
     *
     */
    private ArrayList<SelectItem> CargaActividadesCombo = new ArrayList<>();
    /**
     * recordatorios*
     */
    private ArrayList<SelectItem> CargaRecordatoriosCombo = new ArrayList<>();

    /**
     * variables que comparten las dos*
     */
    Calendar fecha = new GregorianCalendar();
    Date fecha1 = new Date();
    private int CodigoActivORecor;
    private String NombreActivORecor;
    private String DescripcionActivORecor;
    private String PioridadActivORecor;
    SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    /**
     * Variables que se conectan a las diferentes clases de el proyecto
     * NegociosISA > Logic*
     */
    LogAdministracion adm = new LogAdministracion();
    LogSeguimiento Seg = new LogSeguimiento();
    LogRadicacion Rad = new LogRadicacion();
    LogRadicacion Radi = new LogRadicacion();
    LogCliente CliSeguim = new LogCliente();
    LogEntidad EntiSeguim = new LogEntidad();
    LogPerito PerSeguim = new LogPerito();
    LogEmpleado EmpSeguim = new LogEmpleado();
    LogAvaluo Aval = new LogAvaluo();

    /**
     * Variables implicitas*
     */
    private String TipoPersona;
    private String TipoPersonaExterno;
    private String PersonaExterno;
    private ArrayList<SelectItem> CargaPersonaExterna = new ArrayList<>();
    private String PersonaInterno;
    private ArrayList<SelectItem> CargaPersonaInterna = new ArrayList<>();

    private List<LogSeguimiento> ListAccionesSeg = new ArrayList<>();
    private List<LogSeguimiento> ListActiviAsig = new ArrayList<>();
    private List<LogSeguimiento> ListActiviAsigExter = new ArrayList<>();
    private List<LogSeguimiento> listaActividadesExterRealiz = new ArrayList<>();
    private List<LogSeguimiento> listaRecordarAsigExterRealiz = new ArrayList<>();
    LogSeguimiento SegActivid = new LogSeguimiento();
    LogSeguimiento SegActividAsig = new LogSeguimiento();
    LogSeguimiento SegRecordaAsig = new LogSeguimiento();

    private List<LogSeguimiento> ListaRecordatoriosRealizados;
    private List<LogSeguimiento> ListaRecordatorios;
    private List<LogSeguimiento> ListRecordatAsig = new ArrayList<>();
    private List<LogSeguimiento> ListRecordatAsigExter = new ArrayList<>();

    /**
     * Actividades *
     */
    private Date fechaActividadComplet;
    private String Fecha_actual;
    private boolean estadoActividadComplet;
    private String observaActividadComplet;
    int ValidarInserionActividad;

    private ArrayList<SelectItem> CargArchivos = new ArrayList<>();

    private String opcionMostrarAnalistaSeguim;

    //Para la Reasignacion de Persona Encargada del Seguimiento
    private String CodProEnt;
    private boolean EstadoOpcionAsignado = false;

    /**
     * Variable tipo BeanActividadyrecordatorios para traer los atributos y
     * metodos de el ManagedBean BeanActividRecord.java
     *
     *
     * @see BeanActividRecord.java
     */
    /*
    @ManagedProperty("#{MBActivRecor}")
    private BeanActividRecord BeanActivd;

    public BeanActividRecord getBeanActivd() {
        return BeanActivd;
    }

    public void setBeanActivd(BeanActividRecord BeanActivd) {
        this.BeanActivd = BeanActivd;
    }
    
    /**
     * Variable tipo BeanSesion para traer los atributos y metodos de el
     * ManagedBean BeanSesion.java
     *
     *
     * @see BeanSesion.java
     */
    @ManagedProperty("#{MBSesion}")
    private BeanSesion mBsesion;

    public BeanSesion getmBsesion() {
        return mBsesion;
    }

    public void setmBsesion(BeanSesion mBsesion) {
        this.mBsesion = mBsesion;
    }

    /**
     * Variable tipo BeanAdministracion para traer los atributos y metodos de el
     * ManagedBean BeanAdministracion.java
     *
     *
     * @see BeanAdministracion.java
     */
    @ManagedProperty("#{MBAdministacion}")
    private BeanAdministracion mBAdmin;

    public BeanAdministracion getmBAdmin() {
        return mBAdmin;
    }

    public void setmBAdmin(BeanAdministracion mBAdmin) {
        this.mBAdmin = mBAdmin;
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

    /**
     * Variable tipo BeanCorreo para traer los atributos y metodos de el
     * ManagedBean BeanCorreo.java
     *
     *
     * @see BeanCorreo.java
     */
    @ManagedProperty("#{MBCorreo}")
    private BeanCorreo mBCorreo;

    public BeanCorreo getmBCorreo() {
        return mBCorreo;
    }

    public void setmBCorreo(BeanCorreo mBCorreo) {
        this.mBCorreo = mBCorreo;
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

    public int getNumerosPendiSeg() {
        return NumerosPendiSeg;
    }

    /**
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     *
     */
    /**
     *
     * @return
     */
    public void setNumerosPendiSeg(int NumerosPendiSeg) {
        this.NumerosPendiSeg = NumerosPendiSeg;
    }

    public List<LogSeguimiento> getListSeguimiento() {
        return ListSeguimiento;
    }

    public void setListSeguimiento(List<LogSeguimiento> ListSeguimiento) {
        this.ListSeguimiento = ListSeguimiento;
    }

    public List<LogRadicacion> getListSeguimientoRadicacionesConCita() {
        return ListSeguimientoRadicacionesConCita;
    }

    public void setListSeguimientoRadicacionesConCita(List<LogRadicacion> ListSeguimientoRadicacionesConCita) {
        this.ListSeguimientoRadicacionesConCita = ListSeguimientoRadicacionesConCita;
    }

    public List<LogRadicacion> getListSeguimientoAsignados() {
        return ListSeguimientoAsignados;
    }

    public void setListSeguimientoAsignados(List<LogRadicacion> ListSeguimientoAsignados) {
        this.ListSeguimientoAsignados = ListSeguimientoAsignados;
    }

    public List<LogRadicacion> getListSeguimSeleccAsignados() {
        return ListSeguimSeleccAsignados;
    }

    public void setListSeguimSeleccAsignados(List<LogRadicacion> ListSeguimSeleccAsignados) {
        this.ListSeguimSeleccAsignados = ListSeguimSeleccAsignados;
    }

    public List<LogSeguimiento> getListEmpAsignaciones() {
        return ListEmpAsignaciones;
    }

    public void setListEmpAsignaciones(List<LogSeguimiento> ListEmpAsignaciones) {
        this.ListEmpAsignaciones = ListEmpAsignaciones;
    }

    public Date getFechaNueva() {
        return fechaNueva;
    }

    public void setFechaNueva(Date fechaNueva) {
        this.fechaNueva = fechaNueva;
    }

    public String getOpcionCitaAvaluo() {
        return opcionCitaAvaluo;
    }

    public void setOpcionCitaAvaluo(String opcionCitaAvaluo) {
        this.opcionCitaAvaluo = opcionCitaAvaluo;
    }

    public boolean getVisitaRealizada() {
        return visitaRealizada;
    }

    public void setVisitaRealizada(boolean visitaRealizada) {
        this.visitaRealizada = visitaRealizada;
    }

    public String getObservacionReasignaCita() {
        return observacionReasignaCita;
    }

    public void setObservacionReasignaCita(String observacionReasignaCita) {
        this.observacionReasignaCita = observacionReasignaCita;
    }

    public String getOpcionTableroControlSeguim() {
        return opcionTableroControlSeguim;
    }

    public void setOpcionTableroControlSeguim(String opcionTableroControlSeguim) {
        this.opcionTableroControlSeguim = opcionTableroControlSeguim;
    }

    public boolean isEstadoPanelMisActivid() {
        return estadoPanelMisActivid;
    }

    public void setEstadoPanelMisActivid(boolean estadoPanelMisActivid) {
        this.estadoPanelMisActivid = estadoPanelMisActivid;
    }

    public boolean isEstadoPanelMisRecordat() {
        return estadoPanelMisRecordat;
    }

    public void setEstadoPanelMisRecordat(boolean estadoPanelMisRecordat) {
        this.estadoPanelMisRecordat = estadoPanelMisRecordat;
    }

    public boolean isEstadoPanelActividAsign() {
        return estadoPanelActividAsign;
    }

    public void setEstadoPanelActividAsign(boolean estadoPanelActividAsign) {
        this.estadoPanelActividAsign = estadoPanelActividAsign;
    }

    public boolean isEstadoPanelRecordatAsign() {
        return estadoPanelRecordatAsign;
    }

    public void setEstadoPanelRecordatAsign(boolean estadoPanelRecordatAsign) {
        this.estadoPanelRecordatAsign = estadoPanelRecordatAsign;
    }

    public String getEstadoTituloDialogActivRecordat() {
        return estadoTituloDialogActivRecordat;
    }

    public void setEstadoTituloDialogActivRecordat(String estadoTituloDialogActivRecordat) {
        this.estadoTituloDialogActivRecordat = estadoTituloDialogActivRecordat;
    }

    public List<LogSeguimiento> getListAccionesSeg() {
        return ListAccionesSeg;
    }

    public void setListAccionesSeg(List<LogSeguimiento> ListAccionesSeg) {
        this.ListAccionesSeg = ListAccionesSeg;
    }

    public LogSeguimiento getSegActivid() {
        return SegActivid;
    }

    public void setSegActivid(LogSeguimiento SegActivid) {
        this.SegActivid = SegActivid;
    }

    public String getFecha_actual() {
        return Fecha_actual;
    }

    public void setFecha_actual(String Fecha_actual) {
        this.Fecha_actual = Fecha_actual;
    }

    public Date getFechaActividadComplet() {
        return fechaActividadComplet;
    }

    public void setFechaActividadComplet(Date fechaActividadComplet) {
        this.fechaActividadComplet = fechaActividadComplet;
    }

    public boolean isEstadoActividadComplet() {
        return estadoActividadComplet;
    }

    public void setEstadoActividadComplet(boolean estadoActividadComplet) {
        this.estadoActividadComplet = estadoActividadComplet;
    }

    public String getObservaActividadComplet() {
        return observaActividadComplet;
    }

    public int getValidarInserionActividad() {
        return ValidarInserionActividad;
    }

    public void setValidarInserionActividad(int ValidarInserionActividad) {
        this.ValidarInserionActividad = ValidarInserionActividad;
    }

    public void setObservaActividadComplet(String observaActividadComplet) {
        this.observaActividadComplet = observaActividadComplet;
    }

    public LogSeguimiento getSegActividAsig() {
        return SegActividAsig;
    }

    public void setSegActividAsig(LogSeguimiento SegActividAsig) {
        this.SegActividAsig = SegActividAsig;
    }

    public LogSeguimiento getSegRecordaAsig() {
        return SegRecordaAsig;
    }

    public void setSegRecordaAsig(LogSeguimiento SegRecordaAsig) {
        this.SegRecordaAsig = SegRecordaAsig;
    }

    public ArrayList<SelectItem> getCargaActividadesCombo() {
        return CargaActividadesCombo;
    }

    public void setCargaActividadesCombo(ArrayList<SelectItem> CargaActividadesCombo) {
        this.CargaActividadesCombo = CargaActividadesCombo;
    }

    public ArrayList<SelectItem> getCargaRecordatoriosCombo() {
        return CargaRecordatoriosCombo;
    }

    public void setCargaRecordatoriosCombo(ArrayList<SelectItem> CargaRecordatoriosCombo) {
        this.CargaRecordatoriosCombo = CargaRecordatoriosCombo;
    }

    public ArrayList<SelectItem> getCargaPersonaExterna() {
        return CargaPersonaExterna;
    }

    public void setCargaPersonaExterna(ArrayList<SelectItem> CargaPersonaExterna) {
        this.CargaPersonaExterna = CargaPersonaExterna;
    }

    public ArrayList<SelectItem> getCargaPersonaInterna() {
        return CargaPersonaInterna;
    }

    public void setCargaPersonaInterna(ArrayList<SelectItem> CargaPersonaInterna) {
        this.CargaPersonaInterna = CargaPersonaInterna;
    }

    public int getCodigoActivORecor() {
        return CodigoActivORecor;
    }

    public void setCodigoActivORecor(int CodigoActivORecor) {
        this.CodigoActivORecor = CodigoActivORecor;
    }

    public String getNombreActivORecor() {
        return NombreActivORecor;
    }

    public void setNombreActivORecor(String NombreActivORecor) {
        this.NombreActivORecor = NombreActivORecor;
    }

    public String getDescripcionActivORecor() {
        return DescripcionActivORecor;
    }

    public void setDescripcionActivORecor(String DescripcionActivORecor) {
        this.DescripcionActivORecor = DescripcionActivORecor;
    }

    public String getPioridadActivORecor() {
        return PioridadActivORecor;
    }

    public void setPioridadActivORecor(String PioridadActivORecor) {
        this.PioridadActivORecor = PioridadActivORecor;
    }

    public String getTipoPersona() {
        return TipoPersona;
    }

    public void setTipoPersona(String TipoPersona) {
        this.TipoPersona = TipoPersona;
    }

    public String getTipoPersonaExterno() {
        return TipoPersonaExterno;
    }

    public void setTipoPersonaExterno(String TipoPersonaExterno) {
        this.TipoPersonaExterno = TipoPersonaExterno;
    }

    public String getPersonaExterno() {
        return PersonaExterno;
    }

    public void setPersonaExterno(String PersonaExterno) {
        this.PersonaExterno = PersonaExterno;
    }

    public String getPersonaInterno() {
        return PersonaInterno;
    }

    public void setPersonaInterno(String PersonaInterno) {
        this.PersonaInterno = PersonaInterno;
    }

    public List<LogSeguimiento> getListaRecordatoriosRealizados() {
        return ListaRecordatoriosRealizados;
    }

    public void setListaRecordatoriosRealizados(List<LogSeguimiento> ListaRecordatoriosRealizados) {
        this.ListaRecordatoriosRealizados = ListaRecordatoriosRealizados;
    }

    public List<LogSeguimiento> getListaRecordatorios() {
        return ListaRecordatorios;
    }

    public void setListaRecordatorios(List<LogSeguimiento> ListaRecordatorios) {
        this.ListaRecordatorios = ListaRecordatorios;
    }

    public List<LogSeguimiento> getListActiviAsig() {
        return ListActiviAsig;
    }

    public void setListActiviAsig(List<LogSeguimiento> ListActiviAsig) {
        this.ListActiviAsig = ListActiviAsig;
    }

    public List<LogSeguimiento> getListActiviAsigExter() {
        return ListActiviAsigExter;
    }

    public void setListActiviAsigExter(List<LogSeguimiento> ListActiviAsigExter) {
        this.ListActiviAsigExter = ListActiviAsigExter;
    }

    public List<LogSeguimiento> getListaActividadesExterRealiz() {
        return listaActividadesExterRealiz;
    }

    public void setListaActividadesExterRealiz(List<LogSeguimiento> listaActividadesExterRealiz) {
        this.listaActividadesExterRealiz = listaActividadesExterRealiz;
    }

    public List<LogSeguimiento> getListRecordatAsig() {
        return ListRecordatAsig;
    }

    public void setListRecordatAsig(List<LogSeguimiento> ListRecordatAsig) {
        this.ListRecordatAsig = ListRecordatAsig;
    }

    public List<LogSeguimiento> getListRecordatAsigExter() {
        return ListRecordatAsigExter;
    }

    public void setListRecordatAsigExter(List<LogSeguimiento> ListRecordatAsigExter) {
        this.ListRecordatAsigExter = ListRecordatAsigExter;
    }

    public List<LogSeguimiento> getListaRecordarAsigExterRealiz() {
        return listaRecordarAsigExterRealiz;
    }

    public void setListaRecordarAsigExterRealiz(List<LogSeguimiento> listaRecordarAsigExterRealiz) {
        this.listaRecordarAsigExterRealiz = listaRecordarAsigExterRealiz;
    }

    public String getOpcionMostrarAnalistaSeguim() {
        return opcionMostrarAnalistaSeguim;
    }

    public void setOpcionMostrarAnalistaSeguim(String opcionMostrarAnalistaSeguim) {
        this.opcionMostrarAnalistaSeguim = opcionMostrarAnalistaSeguim;
    }

    public LogSeguimiento getSeg() {
        return Seg;
    }

    public void setSeg(LogSeguimiento Seg) {
        this.Seg = Seg;
    }

    public ArrayList<SelectItem> getCargArchivos() {
        return CargArchivos;
    }

    public void setCargArchivos(ArrayList<SelectItem> CargArchivos) {
        this.CargArchivos = CargArchivos;
    }

    public LogRadicacion getRadi() {
        return Radi;
    }

    public void setRadi(LogRadicacion Radi) {
        this.Radi = Radi;
    }

    public String getCodProEnt() {
        return CodProEnt;
    }

    public void setCodProEnt(String CodProEnt) {
        this.CodProEnt = CodProEnt;
    }

    public boolean isEstadoOpcionAsignado() {
        return EstadoOpcionAsignado;
    }

    public void setEstadoOpcionAsignado(boolean EstadoOpcionAsignado) {
        this.EstadoOpcionAsignado = EstadoOpcionAsignado;
    }

    public int getNumeroaAsignadosSeguim() {
        return NumeroaAsignadosSeguim;
    }

    public void setNumeroaAsignadosSeguim(int NumeroaAsignadosSeguim) {
        this.NumeroaAsignadosSeguim = NumeroaAsignadosSeguim;
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
     * Metodo que Captura numero del avaluo y el tipo de bien
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Op int que contiene el numero del avaluo
     * @since 01-05-2015
     */
    public void capturarNumAvaluo(int Op) {
        try {
            if (Seg.getCodAvaluo() == 0) {
                mbTodero.setMens("Seleccione un numero de Radicacion");
                mbTodero.error();
            } else if (Op == 1) {
                RequestContext.getCurrentInstance().execute("PF('DlgInfSeguimiento').show()");
            } else if (Op == 2) {
                mBArchivos.setNAvaluo(Seg.getCodAvaluo());
                mBArchivos.setNBien(Seg.getNumeroBienSeguimiento());
                switch (Seg.getTipAvaluo()) {
                    case "Predio":
                        mBArchivos.setTipoAvaluo("Predio");
                        mBArchivos.Limp(1);
                        break;
                    case "Maquinaria":
                        mBArchivos.setTipoAvaluo("Maquinaria");
                        mBArchivos.Limp(1);
                        break;
                    default:
                        mBArchivos.setTipoAvaluo("Enser");
                        mBArchivos.Limp(1);
                        break;
                }
                mBArchivos.setEstadoPanelArchivoAva(false);
                mBArchivos.setEstadoPanelArchivoCli(false);
                mBArchivos.Limp(4);
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".capturarNumAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que verifica el cambio de estado, si es impedimento o anulacion
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param op int que define si se abre el dialgo para anular o impedir
     * @since 01-05-2015
     */
    public void verificaCambioEstado(int op) {
        try {
            if (Seg.getCodAvaluo() == 0) {
                mbTodero.setMens("Seleccione un numero de Radicacion");
                mbTodero.warn();
            } else {
                mBRadicacion.Radi.setCodAvaluo(Seg.getCodAvaluo());
                if (op == 1) {
                    RequestContext.getCurrentInstance().execute("PF('DlgEstAvaluo').show()");
                } else if (op == 2) {
                    RequestContext.getCurrentInstance().execute("PF('DLGAnuAvaluo').show()");
                }

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".verificaCambioEstado()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para realizar el cambio de estado de la PreRadicacion
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    //Metodo 
    public void cambioEstadAvaluo() {
        try {
            if ("".equals(mBRadicacion.Radi.getObservacionAvaluo()) || "".equals(mBRadicacion.Radi.getEstadoAvaluo())) {
                mbTodero.setMens("Falta informacion por Llenar");
                mbTodero.warn();
            } else {
                mBRadicacion.Radi.CambioEstRad(mBsesion.codigoMiSesion());
                mbTodero.setMens("La informacion ha sido guardada correctamente");
                mbTodero.info();
                mbTodero.resetTable("FRMSeguimiento:SeguimientoTable");
                RequestContext.getCurrentInstance().execute("PF('DlgEstAvaluo').hide()");
                ListSeguimiento = Seg.ConsulSeguimAvaluos(mBsesion.codigoMiSesion());//VARIABLES DE SESION
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cambioEstadAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que permite realizar la anulacion de la radicacion
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void anulaAvaluo() {
        try {
            if ("".equals(mBRadicacion.Radi.getObservacionAnulaAvaluo())) {
                mbTodero.setMens("Falta informacion por Llenar");
                mbTodero.warn();
            } else {
                int CodRad = mBRadicacion.Radi.getCodAvaluo();
                mBRadicacion.Radi.AnulaRadicacion(mBsesion.codigoMiSesion());
                mbTodero.setMens("El Avaluo N*: " + CodRad + " ha sido anulada");
                mbTodero.info();
                mbTodero.resetTable("FRMSeguimiento:SeguimientoTable");
                mbTodero.resetTable("FormMisAsignados:RadicadosSegTable");
                RequestContext.getCurrentInstance().execute("PF('DLGAnuAvaluo').hide()");
                ListSeguimiento = Seg.ConsulSeguimAvaluos(mBsesion.codigoMiSesion());//VARIABLES DE SESION
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".anulaAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta las radicaciones/avaluos que estan en el proceso de
     * seguimiento
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso
     * @since 01-05-2015
     */
    public void consultaRadicacionesSegumiento(int proceso) {
        try {
            ListSeguimientoRadicacionesConCita = Rad.ConsultasRadicacionYSeguimiento(proceso, mBsesion.codigoMiSesion());
            Conexion.Conexion.CloseCon();
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaRadicacionesSegumiento()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que captura numero del pre-radicacion y consulta las observaciones
     * sobre este
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void capturarNumPreRadica() {
        try {
            if (mBRadicacion.getRadi() == null) {
                mbTodero.setMens("Debe seleccionar un registro de la tabla");
                mbTodero.warn();
            } else {
                DatObser = Rad.consultaObservaciones("obRad", String.valueOf(mBRadicacion.getRadi().getCodAvaluo()), mBRadicacion.getRadi().getCodSeguimiento());
                mBRadicacion.setListObserRadicados(new ArrayList<LogRadicacion>());

                while (DatObser.next()) {
                    LogRadicacion RadObs = new LogRadicacion();
                    RadObs.setObservacionRadic(DatObser.getString("Obser"));
                    RadObs.setFechaObservacionRadic(DatObser.getString("Fecha"));
                    RadObs.setAnalistaObservacionRadic(DatObser.getString("empleado"));
                    mBRadicacion.getListObserRadicados().add(RadObs);
                }
                Conexion.Conexion.CloseCon();
                opcionCitaAvaluo = "";
                visitaRealizada = false;
                fechaNueva = null;
                observacionReasignaCita = "";
                RequestContext.getCurrentInstance().execute("PF('DlgInfRadicacion').show()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".capturarNumPreRadica()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite limpiar los campos utilizados en las citas en el form
     * de seguimierto
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void limpiarCamposCita() {
        try {
            visitaRealizada = false;
            fechaNueva = null;
            observacionReasignaCita = "";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".limpiarCamposCita()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que limoia los campos utilizados en las actividades y
     * recordatorios
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void limpiarCamposActividadesYRecord() {
        try {
            estadoTituloDialogActivRecordat = "";
            opcionTableroControlSeguim = "";
            estadoPanelMisActivid = false;
            estadoPanelMisRecordat = false;
            estadoPanelActividAsign = false;
            estadoPanelRecordatAsign = false;
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".limpiarCamposCita()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que consulta las actividades y las carga en un selectedOneMenu
     *
     * @author Modificado por laura :) 18/01/17
     * @param parametro
     * @return ArrayList con las actividades en un combo
     * @since 01-05-2015
     */
    public ArrayList<SelectItem> getConsulActividades(String parametro) {
        try {
            Iterator<LogAdministracion> Ite;
            Ite = adm.getActividades(parametro).iterator();
            while (Ite.hasNext()) {
                LogAdministracion adm1 = Ite.next();
                this.CargaActividadesCombo.add(new SelectItem(adm1.getCodActividad(), adm1.getNombreActividad()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulActividades()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaActividadesCombo;
    }

    /**
     * Metodo que consulta los recordatorios y los carga en un selectedOneMenu
     * mdificado por laura
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param parm
     * @return ArrayList con los recordatorios en un combo
     * @since 01-05-2015
     */
    public ArrayList<SelectItem> getConsulRecordator(String parm) {
        try {
            Iterator<LogSeguimiento> Ite;
            Ite = Seg.consultaRecordatorios(parm).iterator();
            while (Ite.hasNext()) {
                LogSeguimiento seguimRecorda = Ite.next();
                this.CargaRecordatoriosCombo.add(new SelectItem(seguimRecorda.getCodRecordat(), seguimRecorda.getNombreRecorda()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulRecordator()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaRecordatoriosCombo;
    }

    /**
     * Metodo que consulta los externos, que son las personas ajenas a la
     * empresa pero que se involucran en el proceso de los avaluos, y los agrega
     * en una lista selectedOneMenu
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param externo String que contiene el parametro para identificar un
     * externo asi: Cliente, Entidad, Avaluador
     * @return ArrayList con la informacion de los externos
     * @since 01-05-2015
     */
    public ArrayList<SelectItem> getConsulExternos(String externo) {
        try {
            switch (externo) {
                case "Cliente":
                    Iterator<LogCliente> IteCliente;
                    IteCliente = CliSeguim.consulCliAvaluoCombo(mBRadicacion.getCod_avaluo()).iterator();
                    while (IteCliente.hasNext()) {
                        LogCliente seguimExternoCliente = IteCliente.next();
                        this.CargaPersonaExterna.add(new SelectItem(seguimExternoCliente.getCodCliente(), seguimExternoCliente.getNombreCliente()));
                    }
                    break;
                case "Entidad":
                    Iterator<LogEntidad> IteEntidad;
                    IteEntidad = EntiSeguim.consultaEntidadAvalCombo(mBRadicacion.getCod_avaluo()).iterator();
                    while (IteEntidad.hasNext()) {
                        LogEntidad seguimExternoEntidad = IteEntidad.next();
                        this.CargaPersonaExterna.add(new SelectItem(seguimExternoEntidad.getCodAsesor(), seguimExternoEntidad.getNombreEntidad() + " - " + seguimExternoEntidad.getNombreOfic()));
                    }
                    break;
                case "Avaluador":
                    Iterator<LogPerito> ItePerito;
                    ItePerito = PerSeguim.ConsulPeritoCombo(mBRadicacion.getCod_avaluo()).iterator();
                    while (ItePerito.hasNext()) {
                        LogPerito seguimExternoPerito = ItePerito.next();
                        this.CargaPersonaExterna.add(new SelectItem(seguimExternoPerito.getCodPerito(), seguimExternoPerito.getNombrePerito()));
                    }
                    break;
            }

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulExternos()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaRecordatoriosCombo;
    }

    /**
     * Metodo que consulta los inernos, que son las personas que pertenecen a
     * empresa y que se involucran en el proceso de los avaluos, y los agrega en
     * una lista selectedOneMenu
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @return ArrayList con la informacion de los inernos
     * @since 01-05-2015
     */
    public ArrayList<SelectItem> getConsulInternos() {
        try {
            Iterator<LogEmpleado> Ite;
            Ite = EmpSeguim.EmpleadosTodosCombo().iterator();
            while (Ite.hasNext()) {
                LogEmpleado seguimEmpleadoInternos = Ite.next();
                this.CargaPersonaInterna.add(new SelectItem(seguimEmpleadoInternos.getCodEmp(), seguimEmpleadoInternos.getNombreEmp()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulInternos()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaPersonaInterna;
    }

    /**
     * Metodo que consulta las actividades que tiene un analista asignadas
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     *
     * @since 01-05-2015
     */
    public void ConsultaActividades() {
        try {
            ListAccionesSeg = new ArrayList<>();
            ListAccionesSeg = Seg.consultaActividadesPorAnalista(mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsultaActividades()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que consulta los recordatorios que tiene un analista asignadas
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void ConsultaRecordatorios() {
        try {
            ListaRecordatorios = new ArrayList<>();
            ListaRecordatorios = Seg.consultaRecordatoriosPorAnalista(mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsultaRecordatorios()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que consulta todas las actividades tanto para realizar el
     * responsable o asignarla a otra persona, sean internos o externos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void ConsultaActividadesAsignadas() {
        try {
            ListActiviAsig = new ArrayList<>();
            ListActiviAsig = Seg.consultaActividadesAsignadasAnalista(1, mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
            ListActiviAsigExter = new ArrayList<>();
            ListActiviAsigExter = Seg.consultaActividadesAsignadasAnalista(2, mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsultaActividadesAsignadas()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que consulta los recordatorios que tiene un analista asignadas
     * sean internos o externos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void ConsultaRecordatoriosAsignados() {
        try {
            ListRecordatAsig = new ArrayList<>();
            ListRecordatAsig = Seg.consultaRecordatoriosAsignadosAnalista(1, mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
            ListRecordatAsigExter = new ArrayList<>();
            ListRecordatAsigExter = Seg.consultaRecordatoriosAsignadosAnalista(2, mBsesion.codigoMiSesion(), mBRadicacion.getCod_seguimiento());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsultaRecordatoriosAsignados()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que sirve para habilitar los paneles del tablero de control
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso int que contiene el parametro de esta forma= 1 - Habilita
     * opcion para ver las actividades a realizar 2 - Habilita dialog para crea
     * y asignar actividades a otras personas 3 - Habilita opcion para ver los
     * recordatorios que tiene asignado el analista 4 - Habilita dialog para
     * crear y asignar recordatorios a otras personas 5 - Habilita Atividades o
     * recordatorios que tenga asignado un analista en radicacion 6 - Habilita
     * consulta para ver las actividades que ha asignado el analista de
     * seguimiento a otras personas 7 - Habilita consulta para ver los
     * recordatorios que ha asignado el analista de seguimiento a otras personas
     * @since 01-05-2015
     */
    public void habilitarInfoTableroControl(int proceso) {
        try {
            estadoPanelMisActivid = false;
            estadoPanelMisRecordat = false;
            estadoPanelActividAsign = false;
            estadoPanelRecordatAsign = false;
            CodAvaluo = mBRadicacion.getCod_avaluo();
            CodigoSeguimiento = mBRadicacion.getCod_seguimiento();
            CodigoBienSeguimiento = mBRadicacion.getCod_bien_seguimiento();
            if (proceso == 0) {
                CargaActividadesCombo.clear();
                estadoTituloDialogActivRecordat = "Crear y asignar actividades";
                NombreActivORecor = "";
                PioridadActivORecor = "";
                TipoPersona = "";
                TipoPersonaExterno = "";
                PersonaExterno = "";
                PersonaInterno = "";
                fechaActividadComplet = null;
                observaActividadComplet = "";
            }

            if (proceso == 1) {
                mbTodero.resetTable("FormRadicacion:AccionesSeguim");
                ConsultaActividades();
                estadoPanelMisActivid = true;
                estadoPanelMisRecordat = false;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;
            }
            if (proceso == 2) {
                habilitarInfoTableroControl(0);
                getConsulActividades("T");
                RequestContext.getCurrentInstance()
                        .execute("PF('DlgActivRealiada').show()");

            }
            if (proceso == 3) {
                mbTodero.resetTable("FormRadicacion:ListaRecordatorios");
                ConsultaRecordatorios();
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = true;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;

            }
            if (proceso == 4) {
                CargaRecordatoriosCombo.clear();
                getConsulRecordator("R");
                habilitarInfoTableroControl(0);
                estadoTituloDialogActivRecordat = "Crear y asignar recordatorios";
                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");
            }
            //Carga la informacion en el form de radicacion desde radicacion
            if (proceso == 5) {
                switch (opcionTableroControlSeguim) {
                    case "1":
                        mbTodero.resetTable("FormRadicacion:AccionesSeguim");
                        ConsultaActividades();
                        break;
                    case "2":
                        mbTodero.resetTable("FormRadicacion:ListaRecordatorios");
                        ConsultaRecordatorios();
                        break;
                }
            }
            if (proceso == 6) {
                mbTodero.resetTable("FormRadicacion:ActividadesAsignadas");
                mbTodero.resetTable("FormRadicacion:ActividadesAsignadasExter");
                ConsultaActividadesAsignadas();
                estadoPanelActividAsign = true;
                estadoPanelRecordatAsign = false;
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;

            }
            if (proceso == 7) {
                mbTodero.resetTable("FormRadicacion:RecordatorAsignados");
                mbTodero.resetTable("FormRadicacion:RecordatorAsignadosExter");
                ConsultaRecordatoriosAsignados();
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = true;
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;

            }

            if (proceso == 8) {
                mBAdmin.setTipoAct("Fac");
            }

            if (proceso == 9) {
                //Proceso apra cargar aprametros de antividades facturación
                habilitarInfoTableroControl(0);
                getConsulActividades("F");
                RequestContext.getCurrentInstance()
                        .execute("PF('DlgActivRealiada').show()");

            }

            if (proceso == 10) {
                //Carga recordatorios de facturación
                CargaRecordatoriosCombo.clear();
                getConsulRecordator("F");
                habilitarInfoTableroControl(0);
                estadoTituloDialogActivRecordat = "Crear y asignar recordatorios";
                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");
            }

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".habilitarInfoTableroControl()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que sirve para abrir el dialogo ara crear actividades y
     * recordatorios
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso int que contiene el parametro para = 1 - Habilita dialog
     * para actividades 2 - Habilita dialog para recordatorios
     * @since 01-05-2015
     */
    public void abrirDialogCreaActividORecord(int proceso) {
        try {
            if (proceso == 1) {
                mBAdmin.setEstadoRadioSeleccionActiviYRecord("GesAcccion");
                mBAdmin.visiblePanelActividRecordat();
                RequestContext.getCurrentInstance().execute("PF('DialogCrearActividadesORecordat').show()");
            } else if (proceso == 2) {
                mBAdmin.setEstadoRadioSeleccionActiviYRecord("GesRecordat");
                mBAdmin.visiblePanelActividRecordat();
                RequestContext.getCurrentInstance().execute("PF('DialogCrearActividadesORecordat').show()");

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirDialogCreaActividORecord()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite registra una actividad en el form de administracion o
     * el form de seguimiento Modificado LauraG 18-01-17
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param parArgu
     * @since 01-05-2015
     */
    public void regisActivid(String parArgu) {
        try {
            mbTodero.resetTable("FormRadicacion:TipActividadTable");
            if ("".equals(this.mBAdmin.getNombreActividad())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                LogAdministracion AdmRegisActiv = new LogAdministracion();
                AdmRegisActiv.setNombreActividad(mBAdmin.getNombreActividad());
                AdmRegisActiv.InsertarActividad(mBsesion.codigoMiSesion(), parArgu);
                mBAdmin.setCodActividad(0);
                mBAdmin.setNombreActividad("");
                mbTodero.setMens("Registro creado correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipActividadTable");

                mBAdmin.getListActividGest().clear();
                mBAdmin.setListActividGest(AdmRegisActiv.getActividades(parArgu));

                CargaActividadesCombo.clear();
                getConsulActividades(parArgu);
                RequestContext.getCurrentInstance().execute("PF('DlgActividad').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".regisActivid()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que permite modifica una actividad en el form de administracion o
     * el form de seguimiento
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void modifiActivida() { //ok
        try {
            mbTodero.resetTable("FormRadicacion:TipActividadTable");
            if ("".equals(this.mBAdmin.getNombreActividad())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                LogAdministracion AdmModifActiv = new LogAdministracion();
                AdmModifActiv.setCodActividad(mBAdmin.getCodActividad());
                AdmModifActiv.setNombreActividad(mBAdmin.getNombreActividad());
                AdmModifActiv.ActualizaActividad(mBsesion.codigoMiSesion());
                mBAdmin.setCodActividad(0);
                mBAdmin.setNombreActividad("");
                mbTodero.setMens("Informacion actualizada correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipActividadTable");

                mBAdmin.getListActividGest().clear();
                mBAdmin.setListActividGest(AdmModifActiv.getActividades("T"));
                //Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaActividadesCombo.clear();
                getConsulActividades("T");
                RequestContext.getCurrentInstance().execute("PF('DlgActividad').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiActivida()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que registra un recordatorio en el form de administracion o el
     * form de seguimiento modi LG
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param tipProce
     * @param parArgu
     * @since 01-05-2015
     */
    public void regisRecordator(String tipProce, String parArgu) {

        try {
            mbTodero.resetTable("FormRadicacion:TipRecordatorTable");
            if ("".equals(this.mBAdmin.getNombreRecordatori())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                LogAdministracion AdmRegistroRecord = new LogAdministracion();
                AdmRegistroRecord.setNombreRecordatorio(mBAdmin.getNombreRecordatori());
                AdmRegistroRecord.InsertarRecordatorio(mBsesion.codigoMiSesion(), tipProce, parArgu);
                mBAdmin.setCodRecordato(0);
                mBAdmin.setNombreRecordatori("");
                mbTodero.setMens("Registro creado correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipRecordatorTable");

                mBAdmin.getListRecordaGest().clear();
                mBAdmin.setListRecordaGest(AdmRegistroRecord.getRecordatorios(parArgu));
                //   Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaRecordatoriosCombo.clear();
                getConsulRecordator(parArgu);
                RequestContext.getCurrentInstance().execute("PF('DlgRecordator').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".regisRecordator()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que modifica un recordatorio en el form de administracion o el
     * form de seguimiento
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void modifiRecordator() { //ok

        try {
            mbTodero.resetTable("FormRadicacion:TipRecordatorTable");
            if ("".equals(this.mBAdmin.getNombreRecordatori())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                LogAdministracion AdmModifiRecord = new LogAdministracion();
                AdmModifiRecord.setCodRecordatorio(mBAdmin.getCodRecordato());
                AdmModifiRecord.setNombreRecordatorio(mBAdmin.getNombreRecordatori());
                AdmModifiRecord.ActualizaRecordatorio(mBsesion.codigoMiSesion());
                mBAdmin.setCodRecordato(0);
                mBAdmin.setNombreRecordatori("");
                mbTodero.setMens("Informacion actualizada correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipRecordatorTable");
                mBAdmin.getListRecordaGest().clear();
                mBAdmin.setListRecordaGest(AdmModifiRecord.getRecordatorios("R"));
                //Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaRecordatoriosCombo.clear();
                getConsulRecordator("R");
                RequestContext.getCurrentInstance().execute("PF('DlgRecordator').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiRecordator()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite determinar cual es el tipo de externo que va a ser
     * asignado una actividad o recordatorio
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void selectTipoPersonaExterna() {
        try {
            CargaPersonaExterna.clear();
            switch (TipoPersonaExterno) {
                case "Cliente":
                    getConsulExternos("Cliente");
                    break;
                case "Entidad":
                    getConsulExternos("Entidad");
                    break;
                case "Avaluador":
                    getConsulExternos("Avaluador");
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".selectTipoPersonaExterna()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite determinar cual es el tipo de interno que va a ser
     * asignado una actividad o recordatorio
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void selectTipoPersonaInterna() {
        try {
            CargaPersonaInterna.clear();
            if ("Interna".equals(TipoPersona)) {
                getConsulInternos();
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".selectTipoPersonaInterna()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que carga el dialog con la informacion para editar y registrar la
     * informacion de las actividades
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso int que se utiliza de esta forma: (1) actividades propias.
     * (2) asignar actividades
     * @since 01-05-2015
     */
    public void cargarDialogActivComple(int proceso) {
        try {
            if (proceso == 1) {
                if (SegActivid == null) {
                    mbTodero.setMens("Debe seleccionar una actividad de la tabla");
                    mbTodero.warn();
                } else {
                    estadoTituloDialogActivRecordat = "Verificar y completar actividad";
                    CodigoActivORecor = SegActivid.getCodAccion();
                    NombreActivORecor = SegActivid.getNombreAccion();
                    DescripcionActivORecor = SegActivid.getDescripcionAccion();
                    PioridadActivORecor = SegActivid.getPioridadAccion();
                    estadoActividadComplet = false;
                    fechaActividadComplet = null;
                    observaActividadComplet = "";
                    RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");
                }
            } else if (proceso == 2) {
                if (SegActividAsig == null) {
                    mbTodero.setMens("Debe seleccionar una actividad de la tabla");
                    mbTodero.warn();
                } else {
                    CodigoActivORecor = SegActividAsig.getCodAccion();
                    NombreActivORecor = SegActividAsig.getNombreAccion();
                    DescripcionActivORecor = SegActividAsig.getDescripcionAccion();
                    PioridadActivORecor = SegActividAsig.getPioridadAccion();
                    TipoPersona = "";
                    TipoPersonaExterno = "";
                    PersonaExterno = "";
                    PersonaInterno = "";
                    fechaActividadComplet = null;
                    observaActividadComplet = "";

                    RequestContext.getCurrentInstance()
                            .execute("PF('DlgActivRealiada').show()");
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargarDialogActivComple()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que inserta una nueva actividad o recordatorio y se la asigna a
     * una persona
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void insertActividadAsignada() {
        try {
            if ("".equals(NombreActivORecor) && "Crear y asignar actividades".equals(estadoTituloDialogActivRecordat)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Nombre de acción'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("".equals(NombreActivORecor) && "Crear y asignar recordatorios".equals(estadoTituloDialogActivRecordat)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Nombre de recordatorio'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("".equals(PioridadActivORecor)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Pioridad'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("".equals(TipoPersona)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Tipo de persona' ");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("Externa".equals(TipoPersona) && "".equals(TipoPersonaExterno)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Tipo de externo'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("Externa".equals(TipoPersona) && "".equals(PersonaExterno)) {
                mbTodero.setMens("Debe seleccionar información del campo '" + TipoPersonaExterno + "'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("Interna".equals(TipoPersona) && "".equals(PersonaInterno)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Empleado'");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if ("".equals(observaActividadComplet)) {
                mbTodero.setMens("Debe llenar el campo 'Descripción' ");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else if (fechaActividadComplet == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Fecha de ejecución maxima' ");
                mbTodero.warn();
                ValidarInserionActividad = 0;
            } else {

                String TipoActividad = "";
                String Tipo_Persona = "";
                ValidarInserionActividad = 1;
                switch (estadoTituloDialogActivRecordat) {
                    case "Crear y asignar actividades":
                        TipoActividad = "T";
                        break;
                    case "Crear y asignar recordatorios":
                        TipoActividad = "R";
                        break;
                }
                Seg.setNombreAccion(NombreActivORecor);
                Seg.setPioridadAccion(PioridadActivORecor);
                Seg.setTipoPersona(TipoPersona);
                String fechaConvertida = FormatFecha.format(fechaActividadComplet);

                switch (TipoPersona) {
                    case "Externa":
                        Tipo_Persona = TipoPersonaExterno;

                        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciemrbre"};

                        int año = fecha.get(Calendar.YEAR);
                        int mes = fecha.get(Calendar.MONTH) + 1;
                        int dia = fecha.get(Calendar.DAY_OF_MONTH);

                        String mesLetra = meses[mes - 1];

                        fechaCorreo = "Bogota D.C.  " + dia + " de " + mesLetra + " de " + año;
                        direccion = "0";
                        ubicacion = "0";
                        analista = "";

                        Dat = Aval.getConsultarAvaluoSeguim(CodAvaluo);
                        if (Dat.next()) {
                            direccion = Dat.getString("Direccion");
                            ubicacion = Dat.getString("Ubicacion");
                            analista = Dat.getString("Analista");
                            nombreResponsableSeguimiento = Dat.getString("Respon_Segumie");
                            if (Dat.getString("Correo_Corporativo_Empleados") != null || "".equals(Dat.getString("Correo_Corporativo_Empleados"))) {
                                correoResponsableSeguimiento = Dat.getString("Correo_Personal_Empleados");
                            } else {
                                correoResponsableSeguimiento = Dat.getString("Correo_Corporativo_Empleados");
                            }
                        }
                        Conexion.Conexion.CloseCon();
                        switch (estadoTituloDialogActivRecordat) {
                            case "Crear y asignar actividades":
                                DatNombreAccionRecod = Seg.consultaActividadesCod(Integer.parseInt(NombreActivORecor));

                                break;
                            case "Crear y asignar recordatorios":
                                DatNombreAccionRecod = Seg.consultaRecordatoriosCod(Integer.parseInt(NombreActivORecor));
                                break;
                        }

                        if (DatNombreAccionRecod.next()) {
                            nombreActiviORecorCorreo = DatNombreAccionRecod.getString("Resultado_Parametro");
                        }
                        Conexion.Conexion.CloseCon();
                        //                                    Emp.setCodEmp(mBsesion.codigoMiSesion());
//                                    DatAnalista = Emp.ConsulEmp();
//                                    if (DatAnalista.next()) {
//                                        extensionAnalista = DatAnalista.getString("Extension_Empleados");
//                                        if ("".equals(DatAnalista.getString("Correo_Corporativo_Empleados")) || DatAnalista.getString("Correo_Corporativo_Empleados") == null) {
//                                            correoAnalista = DatAnalista.getString("Correo_Personal_Empleados");
//                                        } else {
//                                            correoAnalista = DatAnalista.getString("Correo_Corporativo_Empleados");
//                                        }
//
//                                    }
                        TextoCorreoExternos = "<b>Fecha:</b> " + fechaCorreo + "<br/>"
                                + "<br/>"
                                + "<br/>"
                                + "<br/>"
                                + "Buen dia, me permito informarle que debe realizar la actividad descrita a continuación con referente a:<br/>"
                                + "<br/>"
                                + "<b>Numero de avaluo:</b> " + CodAvaluo + ".<br/>"
                                + "<b>Dirección:</b> " + direccion + "<br/>"
                                + "<b>Ubicación:</b> " + ubicacion + "<br/>"
                                + "<br/>"
                                + "<b>Actividad:</b> " + nombreActiviORecorCorreo + ".<br/>"
                                + "<b>Descripción:</b> " + observaActividadComplet + "<br/>"
                                + "<b>Fecha maxima de ejecución:</b> " + fechaConvertida + "<br/>"
                                + "<br/>"
                                + "Cualquier información adicional acerca del avalúo podrá comunicarse con nosotros al PBX 3230450 Opción 1, mencionando el número de avalúo señalado arriba.<br/>"
                                + "<br/>"
                                + "<br/>"
                                + "<br/>"
                                + " El seguimiento esta a cargo de <br/><br/>"
                                + " <b>NOMBRE: </b> " + nombreResponsableSeguimiento.toUpperCase() + ",<br/>"
                                + " <b>CORREO: </b> " + correoResponsableSeguimiento.toUpperCase() + ",<br/><br/>"
                                + " para mayor información puede comunicarse con el/ella a través de su cuenta de correo."
                                + "<br/>"
                                + "<br/>"
                                + "<br/>"
                                + "Este correo es únicamente informativo y es de uso exclusivo del destinatario(a), puede contener información privilegiada y/o confidencial. Si no es usted el destinatario(a) deberá borrarlo inmediatamente.";

                        Seg.setPersonaAsignarInternOExt(PersonaExterno);

                        mBCorreo.setAsunto("Notificación de actividad por realizar");
                        mBCorreo.setMensaje(TextoCorreoExternos);
                        mBCorreo.setMailToReply(correoResponsableSeguimiento);
                        switch (Tipo_Persona) {
                            case "Cliente":
                                DatPerExterna = CliSeguim.ConsulCodCli(PersonaExterno);
                                if (DatPerExterna.next()) {
                                    if (DatPerExterna.getString("Mail_Cliente") != null || !"".equals(DatPerExterna.getString("Mail_Cliente"))) {
                                        mBCorreo.setMailDestino(DatPerExterna.getString("Mail_Cliente"));
                                        mBCorreo.enviarCorreo(2);
                                        Seg.setDescripcionAccion(observaActividadComplet);
                                        Seg.setFecha_Max_ejecuc(fechaConvertida);
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                                        switch (estadoTituloDialogActivRecordat) {
                                            case "Crear y asignar actividades":
                                                mbTodero.setMens("Actividad registrada y notificada correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                            case "Crear y asignar recordatorios":
                                                mbTodero.setMens("Recordatorio registrado y notificado correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                        }
                                    } else {
                                        mbTodero.setMens("El cliente que selecciono no tiene correo registrado, no le podra ser notificada la actividad");
                                        mbTodero.warn();
                                    }

                                }
                                Conexion.Conexion.CloseCon();
                                break;
                            case "Entidad":
                                DatPerExterna = EntiSeguim.consultaEntidadPorCodEntiYAval(CodAvaluo, Integer.parseInt(PersonaExterno));
                                if (DatPerExterna.next()) {

                                    if (DatPerExterna.getString("Mail_Asesor") != null || !"".equals(DatPerExterna.getString("Mail_Asesor"))) {
                                        mBCorreo.setMailDestino(DatPerExterna.getString("Mail_Asesor"));
                                        mBCorreo.enviarCorreo(2);
                                        Seg.setDescripcionAccion(observaActividadComplet);
                                        Seg.setFecha_Max_ejecuc(fechaConvertida);
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                                        switch (estadoTituloDialogActivRecordat) {
                                            case "Crear y asignar actividades":
                                                mbTodero.setMens("Actividad registrada y notificada correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                            case "Crear y asignar recordatorios":
                                                mbTodero.setMens("Recordatorio registrado y notificado correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                        }
                                    } else {
                                        mbTodero.setMens("El asesor de la entidad que selecciono no tiene correo registrado, no le podra ser notificada la actividad");
                                        mbTodero.warn();
                                    }

                                }
                                Conexion.Conexion.CloseCon();
                                break;
                            case "Avaluador":
                                DatPerExterna = PerSeguim.ConsultaCodPerito(1, PersonaExterno);
                                if (DatPerExterna.next()) {

                                    if (DatPerExterna.getString("Email_perito") != null || !"".equals(DatPerExterna.getString("Email_perito"))) {
                                        mBCorreo.setMailDestino(DatPerExterna.getString("Email_perito"));
                                        mBCorreo.enviarCorreo(2);
                                        Seg.setDescripcionAccion(observaActividadComplet);
                                        Seg.setFecha_Max_ejecuc(fechaConvertida);
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                                        switch (estadoTituloDialogActivRecordat) {
                                            case "Crear y asignar actividades":
                                                mbTodero.setMens("Actividad registrada y notificada correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                            case "Crear y asignar recordatorios":
                                                mbTodero.setMens("Recordatorio registrado y notificado correctamente");
                                                mbTodero.info();
                                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                                break;
                                        }
                                    } else {
                                        mbTodero.setMens("El avaluador que selecciono no tiene correo registrado, no le podra ser notificada la actividad");
                                        mbTodero.warn();
                                    }
                                    //DatPerExterna.getString("Email_perito")

                                }
                                Conexion.Conexion.CloseCon();
                                break;
                        }

                        break;
                    case "Interna":
                        Tipo_Persona = "Empleado";
                        Seg.setPersonaAsignarInternOExt(PersonaInterno);
                        Seg.setDescripcionAccion(observaActividadComplet);
                        Seg.setFecha_Max_ejecuc(fechaConvertida);
                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                        switch (estadoTituloDialogActivRecordat) {
                            case "Crear y asignar actividades":
                                mbTodero.setMens("Actividad registrada y asignada correctamente");
                                mbTodero.info();
                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                break;
                            case "Crear y asignar recordatorios":
                                mbTodero.setMens("Recordatorio registrado y asignada correctamente");
                                mbTodero.info();
                                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");
                                break;
                        }
                        break;
                }

            }
        } catch (SQLException | NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".insertActividadAsignada()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que modifica una actividad cuando ya fue completada
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void modifiActividadAsignadaCompleta() {
        try {
            if (estadoActividadComplet == false && "Verificar y completar actividad".equals(estadoTituloDialogActivRecordat)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Actividad completada'");
                mbTodero.warn();
            } else if ("".equals(observaActividadComplet) && "Verificar y completar actividad".equals(estadoTituloDialogActivRecordat)) {
                mbTodero.setMens("Debe llenar el campo 'Descripción' ");
                mbTodero.warn();
            } else {
                Seg.setCodAccion(CodigoActivORecor);
                Seg.setDescripcionAccion(observaActividadComplet);

                Seg.ModifcActividadORecordat(1, mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());
                mbTodero.setMens("Actividad completada correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:AccionesSeguim");
                ConsultaActividades();
                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiActividadAsignadaCompleta()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que permite completar un recordatorio y cambiarlo de estado
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void completarRecordat() {
        try {
            mbTodero.resetTable("FormRadicacion:ListaRecordatorios");
            if (ListaRecordatoriosRealizados.isEmpty()) {
                mbTodero.setMens("Debe seleccionar por lo menos un recordario a completar");
                mbTodero.warn();
            } else {
                for (int i = 0; i <= ListaRecordatoriosRealizados.size() - 1; i++) {
                    Seg.setCodRecordat(ListaRecordatoriosRealizados.get(i).getCodRecordat());
                    Seg.setDescripcionAccion("Recordatorio completado");
                    Seg.ModifcActividadORecordat(2, mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                }
                mbTodero.setMens("Recordatorio(s) completado(s) correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:ListaRecordatorios");
                ConsultaRecordatorios();

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".completarRecordat()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que modifica una activiad externa cuando ya fue completado
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void completarActiviAsigExternas() {
        try {
            mbTodero.resetTable("FormRadicacion:ActividadesAsignadas");
            mbTodero.resetTable("FormRadicacion:ActividadesAsignadasExter");
            if (listaActividadesExterRealiz.isEmpty()) {
                mbTodero.setMens("Debe seleccionar por lo menos una actividad externa a completar");
                mbTodero.warn();
            } else {
                for (int i = 0; i <= listaActividadesExterRealiz.size() - 1; i++) {
                    Seg.setCodAccion(listaActividadesExterRealiz.get(i).getCodAccion());
                    Seg.setDescripcionAccion("Actividad asignada a un(a) " + listaActividadesExterRealiz.get(i).getTipoPersona() + " completada");
                    Seg.ModifcActividadORecordat(1, mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                }
                mbTodero.setMens("Actividad(es) completada(s) correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:ActividadesAsignadas");
                mbTodero.resetTable("FormRadicacion:ActividadesAsignadasExter");
                ConsultaActividadesAsignadas();

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".completarActiviAsigExternas()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que modifica un recordatorio cuando ya fue completado
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void completarRecordatAsigExternos() {
        try {
            mbTodero.resetTable("FormRadicacion:RecordatorAsignados");
            mbTodero.resetTable("FormRadicacion:RecordatorAsignadosExter");
            if (listaRecordarAsigExterRealiz.isEmpty()) {
                mbTodero.setMens("Debe seleccionar por lo menos un recordario externo a completar");
                mbTodero.warn();
            } else {
                for (int i = 0; i <= listaRecordarAsigExterRealiz.size() - 1; i++) {
                    Seg.setCodRecordat(listaRecordarAsigExterRealiz.get(i).getCodRecordat());
                    Seg.setDescripcionAccion("Recordatorio asignado a un(a) " + listaRecordarAsigExterRealiz.get(i).getTipoPersona() + " completado");
                    Seg.ModifcActividadORecordat(2, mBRadicacion.getCod_seguimiento(), mBsesion.codigoMiSesion());

                }
                mbTodero.setMens("Recordatorio(s) completado(s) correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:RecordatorAsignados");
                mbTodero.resetTable("FormRadicacion:RecordatorAsignadosExter");
                ConsultaRecordatoriosAsignados();

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".completarRecordatAsigExternos()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para consultar todos los avaluos que corresponden al Producto
     * Entidad Seleccionado (REASIGNACION AVALUO EN RADICACION)
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void consultaReasignaAvaluo() {
        try {
            if (!"".equals(CodProEnt)) {
                this.ListSeguimientoAsignados = new ArrayList<>();
                this.EstadoOpcionAsignado = true;
                ListSeguimientoAsignados = Rad.ConsultaAvaluoReAsigna(Integer.parseInt(CodProEnt));
            } else {
                this.EstadoOpcionAsignado = false;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaReasignaAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para realizar la seleccion de las radicaciones que se encuentran
     * activas para reasignar al personal ISA
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void seleccionListaReAsignacion() {
        try {
            if (this.ListSeguimSeleccAsignados.isEmpty()) {
                mbTodero.setMens("Debe seleccionar por lo menos un avaluo para realizar la reasignacion de responsable");
                mbTodero.warn();
            } else {
                Seg = new LogSeguimiento();
                mbTodero.resetTable("FRMAsigSegui:EmpAsignadosTable");
                this.ListEmpAsignaciones = new ArrayList<>();
                this.ListEmpAsignaciones = Seg.ConsultaTotalEmp(Integer.parseInt(CodProEnt));
                Seg.setCodEmp(ListEmpAsignaciones.get(0).getCodEmp());
                RequestContext.getCurrentInstance().execute("PF('DlgReAsigSeguim').show()");
            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionListaReAsignacion()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para realizar las reasignaciones de los avaluos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void generarReAsignacion() {
        try {
            if (Seg.getCodEmp() <= 0) {
                mbTodero.setMens("No ha seleccionado un analista para generar la Asignacion de Registros");
                mbTodero.warn();
            } else {
                for (int i = 0; i < ListSeguimSeleccAsignados.size(); i++) {
                    Seg.setCodSeguimiento(ListSeguimSeleccAsignados.get(i).getCodSeguimiento());
                    Seg.ModifiAsignacionAnalista(3, Seg.getCodEmp(), mBsesion.codigoMiSesion());
                }
                mbTodero.setMens("Se ha generado la Re-Asignacion Correctamente de los Avaluos");
                mbTodero.info();
                RequestContext.getCurrentInstance().execute("PF('DlgReAsigSeguim').hide()");
                consultaReasignaAvaluo();
                this.ListEmpAsignaciones = null;
                this.EstadoOpcionAsignado = false;

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".generarReAsignacion()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta el numero de registros totales de las diferentes
     * consultas que se retornan en los submenus de seguimiento, esto se muestra
     * numericamente al lado de cada nombre de los submenus
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void consultaTotalRegistrosTablas() {
        try {
            NumerosPendiSeg = Rad.ConsultasRadicacionYSeguimiento(3, mBsesion.codigoMiSesion()).size();
            NumeroaAsignadosSeguim = Rad.ConsultasRadicacionYSeguimiento(2, mBsesion.codigoMiSesion()).size();

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaTotalRegistrosTablas()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    public void onRowSelect(SelectEvent event) {
        mbTodero.setMens("Ok");
        mbTodero.info();
    }

    /**
     * FIN Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
}
