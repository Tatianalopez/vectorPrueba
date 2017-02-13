/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBean;

import Logic.LogActivRecord;
import Logic.LogAdministracion;
import Logic.LogAvaluo;
import Logic.LogCliente;
import Logic.LogEmpleado;
import Logic.LogEntidad;
import Logic.LogPerito;
import Logic.LogSeguimiento;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Desarrollo
 */
@ManagedBean(name = "MBActivRecor")
@ViewScoped

public class BeanActividRecord {

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

    @ManagedProperty("#{MBCorreo}")
    private BeanCorreo mBCorreo;

    public BeanCorreo getmBCorreo() {
        return mBCorreo;
    }

    public void setmBCorreo(BeanCorreo mBCorreo) {
        this.mBCorreo = mBCorreo;
    }

    @ManagedProperty("#{MBRadicacion}")
    private BeanRadicacion mBRadicacion;

    public BeanRadicacion getmBRadicacion() {
        return mBRadicacion;
    }

    public void setmBRadicacion(BeanRadicacion mBRadicacion) {
        this.mBRadicacion = mBRadicacion;
    }

    @ManagedProperty("#{MBCartera}")
    private BeanCartera mbCartera;

    public BeanCartera getMbCartera() {
        return mbCartera;
    }

    public void setMbCartera(BeanCartera mbCartera) {
        this.mbCartera = mbCartera;
    }

    private String opcionTableroControlSeguim;
    private LogActivRecord Actividad;
    /**
     * tablero de control y seguimiento*
     */
    /**
     * Estados de las opciones de los paneles*
     */
    LogCliente CliSeguim = new LogCliente();
    LogEntidad EntiSeguim = new LogEntidad();
    LogPerito PerSeguim = new LogPerito();

    private boolean estadoPanelMisActivid;
    private boolean estadoPanelMisRecordat;
    private boolean estadoPanelActividAsign;
    private boolean estadoPanelRecordatAsign;
    private String estadoTituloDialogActivRecordat;
    private int CodAvaluo;
    private int CodigoSeguimiento;
    private int CodigoBienSeguimiento;

    private ArrayList<SelectItem> CargaActividadesCombo = new ArrayList<>();

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
     * Variables implicitas*
     */
    private String TipoPersona;
    private String TipoPersonaExterno;
    private String PersonaExterno;
    private ArrayList<SelectItem> CargaPersonaExterna = new ArrayList<>();
    private String PersonaInterno;
    private ArrayList<SelectItem> CargaPersonaInterna = new ArrayList<>();

    private List<LogActivRecord> ListAccionesSeg = new ArrayList<>();
    private List<LogActivRecord> ListActiviAsig = new ArrayList<>();
    private List<LogActivRecord> ListActiviAsigExter = new ArrayList<>();
    private List<LogActivRecord> listaActividadesExterRealiz = new ArrayList<>();
    private List<LogActivRecord> listaRecordarAsigExterRealiz = new ArrayList<>();
    LogActivRecord SegActivid = new LogActivRecord();
    LogActivRecord SegActividAsig = new LogActivRecord();
    LogActivRecord SegRecordaAsig = new LogActivRecord();

    public LogCliente getCliSeguim() {
        return CliSeguim;
    }

    public void setCliSeguim(LogCliente CliSeguim) {
        this.CliSeguim = CliSeguim;
    }

    public LogEntidad getEntiSeguim() {
        return EntiSeguim;
    }

    public void setEntiSeguim(LogEntidad EntiSeguim) {
        this.EntiSeguim = EntiSeguim;
    }

    public LogPerito getPerSeguim() {
        return PerSeguim;
    }

    public void setPerSeguim(LogPerito PerSeguim) {
        this.PerSeguim = PerSeguim;
    }

    public LogActivRecord getSegActivid() {
        return SegActivid;
    }

    public void setSegActivid(LogActivRecord SegActivid) {
        this.SegActivid = SegActivid;
    }

    public LogActivRecord getSegActividAsig() {
        return SegActividAsig;
    }

    public void setSegActividAsig(LogActivRecord SegActividAsig) {
        this.SegActividAsig = SegActividAsig;
    }

    public LogActivRecord getSegRecordaAsig() {
        return SegRecordaAsig;
    }

    public void setSegRecordaAsig(LogActivRecord SegRecordaAsig) {
        this.SegRecordaAsig = SegRecordaAsig;
    }

    public LogActivRecord getSeg() {
        return Seg;
    }

    public void setSeg(LogActivRecord Seg) {
        this.Seg = Seg;
    }

    public LogEmpleado getEmpSeguim() {
        return EmpSeguim;
    }

    public void setEmpSeguim(LogEmpleado EmpSeguim) {
        this.EmpSeguim = EmpSeguim;
    }

    public LogAvaluo getAval() {
        return Aval;
    }

    public void setAval(LogAvaluo Aval) {
        this.Aval = Aval;
    }
    /**
     * Variables implicitas*
     */
    private List<LogActivRecord> ListaRecordatoriosRealizados;
    private List<LogActivRecord> ListaRecordatorios;
    private List<LogActivRecord> ListRecordatAsig = new ArrayList<>();
    private List<LogActivRecord> ListRecordatAsigExter = new ArrayList<>();

    LogActivRecord Seg = new LogActivRecord();

    /**
     * recordatorios*
     */
    private ArrayList<SelectItem> CargaRecordatoriosCombo = new ArrayList<>();

    /**
     * Actividades *
     */
    private Date fechaActividadComplet;
    private String Fecha_actual;
    private boolean estadoActividadComplet;
    private String observaActividadComplet;
    int ValidarInserionActividad;

    private String nombreResponsableSeguimiento;
    private String correoResponsableSeguimiento;
    private ResultSet DatNombreAccionRecod;
    private String nombreActiviORecorCorreo;
    private String TextoCorreoExternos;
    private int NumeroaAsignadosSeguim;

    private ResultSet DatObser;
    private ResultSet DatPerExterna;

    public String getNombreResponsableSeguimiento() {
        return nombreResponsableSeguimiento;
    }

    public void setNombreResponsableSeguimiento(String nombreResponsableSeguimiento) {
        this.nombreResponsableSeguimiento = nombreResponsableSeguimiento;
    }

    public String getCorreoResponsableSeguimiento() {
        return correoResponsableSeguimiento;
    }

    public void setCorreoResponsableSeguimiento(String correoResponsableSeguimiento) {
        this.correoResponsableSeguimiento = correoResponsableSeguimiento;
    }

    public ResultSet getDatNombreAccionRecod() {
        return DatNombreAccionRecod;
    }

    public void setDatNombreAccionRecod(ResultSet DatNombreAccionRecod) {
        this.DatNombreAccionRecod = DatNombreAccionRecod;
    }

    public String getNombreActiviORecorCorreo() {
        return nombreActiviORecorCorreo;
    }

    public void setNombreActiviORecorCorreo(String nombreActiviORecorCorreo) {
        this.nombreActiviORecorCorreo = nombreActiviORecorCorreo;
    }

    public String getTextoCorreoExternos() {
        return TextoCorreoExternos;
    }

    public void setTextoCorreoExternos(String TextoCorreoExternos) {
        this.TextoCorreoExternos = TextoCorreoExternos;
    }

    public int getNumeroaAsignadosSeguim() {
        return NumeroaAsignadosSeguim;
    }

    public void setNumeroaAsignadosSeguim(int NumeroaAsignadosSeguim) {
        this.NumeroaAsignadosSeguim = NumeroaAsignadosSeguim;
    }

    public ResultSet getDatObser() {
        return DatObser;
    }

    public void setDatObser(ResultSet DatObser) {
        this.DatObser = DatObser;
    }

    public ResultSet getDatPerExterna() {
        return DatPerExterna;
    }

    public void setDatPerExterna(ResultSet DatPerExterna) {
        this.DatPerExterna = DatPerExterna;
    }

    public String getFechaCorreo() {
        return fechaCorreo;
    }

    public void setFechaCorreo(String fechaCorreo) {
        this.fechaCorreo = fechaCorreo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getAnalista() {
        return analista;
    }

    public void setAnalista(String analista) {
        this.analista = analista;
    }

    public ResultSet getDat() {
        return Dat;
    }

    public void setDat(ResultSet Dat) {
        this.Dat = Dat;
    }

    public ArrayList<SelectItem> getCargaRecordatoriosCombo() {
        return CargaRecordatoriosCombo;
    }

    public void setCargaRecordatoriosCombo(ArrayList<SelectItem> CargaRecordatoriosCombo) {
        this.CargaRecordatoriosCombo = CargaRecordatoriosCombo;
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

    public ArrayList<SelectItem> getCargaPersonaExterna() {
        return CargaPersonaExterna;
    }

    public void setCargaPersonaExterna(ArrayList<SelectItem> CargaPersonaExterna) {
        this.CargaPersonaExterna = CargaPersonaExterna;
    }

    public String getPersonaInterno() {
        return PersonaInterno;
    }

    public void setPersonaInterno(String PersonaInterno) {
        this.PersonaInterno = PersonaInterno;
    }

    public ArrayList<SelectItem> getCargaPersonaInterna() {
        return CargaPersonaInterna;
    }

    public void setCargaPersonaInterna(ArrayList<SelectItem> CargaPersonaInterna) {
        this.CargaPersonaInterna = CargaPersonaInterna;
    }

    public List<LogActivRecord> getListAccionesSeg() {
        return ListAccionesSeg;
    }

    public void setListAccionesSeg(List<LogActivRecord> ListAccionesSeg) {
        this.ListAccionesSeg = ListAccionesSeg;
    }

    public List<LogActivRecord> getListActiviAsig() {
        return ListActiviAsig;
    }

    public void setListActiviAsig(List<LogActivRecord> ListActiviAsig) {
        this.ListActiviAsig = ListActiviAsig;
    }

    public List<LogActivRecord> getListActiviAsigExter() {
        return ListActiviAsigExter;
    }

    public void setListActiviAsigExter(List<LogActivRecord> ListActiviAsigExter) {
        this.ListActiviAsigExter = ListActiviAsigExter;
    }

    public List<LogActivRecord> getListaActividadesExterRealiz() {
        return listaActividadesExterRealiz;
    }

    public void setListaActividadesExterRealiz(List<LogActivRecord> listaActividadesExterRealiz) {
        this.listaActividadesExterRealiz = listaActividadesExterRealiz;
    }

    public List<LogActivRecord> getListaRecordarAsigExterRealiz() {
        return listaRecordarAsigExterRealiz;
    }

    public void setListaRecordarAsigExterRealiz(List<LogActivRecord> listaRecordarAsigExterRealiz) {
        this.listaRecordarAsigExterRealiz = listaRecordarAsigExterRealiz;
    }

    public List<LogActivRecord> getListaRecordatoriosRealizados() {
        return ListaRecordatoriosRealizados;
    }

    public void setListaRecordatoriosRealizados(List<LogActivRecord> ListaRecordatoriosRealizados) {
        this.ListaRecordatoriosRealizados = ListaRecordatoriosRealizados;
    }

    public List<LogActivRecord> getListaRecordatorios() {
        return ListaRecordatorios;
    }

    public void setListaRecordatorios(List<LogActivRecord> ListaRecordatorios) {
        this.ListaRecordatorios = ListaRecordatorios;
    }

    public List<LogActivRecord> getListRecordatAsig() {
        return ListRecordatAsig;
    }

    public void setListRecordatAsig(List<LogActivRecord> ListRecordatAsig) {
        this.ListRecordatAsig = ListRecordatAsig;
    }

    public List<LogActivRecord> getListRecordatAsigExter() {
        return ListRecordatAsigExter;
    }

    public void setListRecordatAsigExter(List<LogActivRecord> ListRecordatAsigExter) {
        this.ListRecordatAsigExter = ListRecordatAsigExter;
    }

    public Date getFechaActividadComplet() {
        return fechaActividadComplet;
    }

    public void setFechaActividadComplet(Date fechaActividadComplet) {
        this.fechaActividadComplet = fechaActividadComplet;
    }

    public String getFecha_actual() {
        return Fecha_actual;
    }

    public void setFecha_actual(String Fecha_actual) {
        this.Fecha_actual = Fecha_actual;
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

    public void setObservaActividadComplet(String observaActividadComplet) {
        this.observaActividadComplet = observaActividadComplet;
    }

    public Calendar getFecha() {
        return fecha;
    }

    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

    public Date getFecha1() {
        return fecha1;
    }

    public void setFecha1(Date fecha1) {
        this.fecha1 = fecha1;
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

    public SimpleDateFormat getFormatFecha() {
        return FormatFecha;
    }

    public void setFormatFecha(SimpleDateFormat FormatFecha) {
        this.FormatFecha = FormatFecha;
    }

    public LogActivRecord getActividad() {
        return Actividad;
    }

    public void setActividad(LogActivRecord Actividad) {
        this.Actividad = Actividad;
    }

    public ArrayList<SelectItem> getCargaActividadesCombo() {
        return CargaActividadesCombo;
    }

    public void setCargaActividadesCombo(ArrayList<SelectItem> CargaActividadesCombo) {
        this.CargaActividadesCombo = CargaActividadesCombo;
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

    public int getCodAvaluo() {
        return CodAvaluo;
    }

    public void setCodAvaluo(int CodAvaluo) {
        this.CodAvaluo = CodAvaluo;
    }

    public int getCodigoSeguimiento() {
        return CodigoSeguimiento;
    }

    public void setCodigoSeguimiento(int CodigoSeguimiento) {
        this.CodigoSeguimiento = CodigoSeguimiento;
    }

    public int getCodigoBienSeguimiento() {
        return CodigoBienSeguimiento;
    }

    public void setCodigoBienSeguimiento(int CodigoBienSeguimiento) {
        this.CodigoBienSeguimiento = CodigoBienSeguimiento;
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
     * @param CodAvaluo
     * @param CodigoSeguimiento
     * @param CodigoBienSeguimiento
     * @since 01-05-2015
     */
    public void habilitarInfoTableroControl(int proceso) {
        try {
            estadoPanelMisActivid = false;
            estadoPanelMisRecordat = false;
            estadoPanelActividAsign = false;
            estadoPanelRecordatAsign = false;
 /*           CodAvaluo = mbCartera.getCodAvaluo();
            CodigoSeguimiento = mbCartera.getCodSeg();
            CodigoBienSeguimiento = mbCartera.getNBien();*/

            if (proceso == 1) {
                //Esto es para Seguimiento
                //mbTodero.resetTable("FormRadicacion:AccionesSeguim");
                mbTodero.resetTable("FormCartera:AccionesSeguim");
                ConsultaActividades(CodigoSeguimiento);
                estadoPanelMisActivid = true;
                estadoPanelMisRecordat = false;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;
            }
            if (proceso == 2) {
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;
                CargaActividadesCombo.clear();
                String Prmtro = "TC";
                getConsulActividades(Prmtro);
                estadoTituloDialogActivRecordat = "Crear y asignar actividades";
                NombreActivORecor = "";
                PioridadActivORecor = "";
                TipoPersona = "";
                TipoPersonaExterno = "";
                PersonaExterno = "";
                PersonaInterno = "";
                fechaActividadComplet = null;
                observaActividadComplet = "";
                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");

            }
            if (proceso == 3) {
                mbTodero.resetTable("FormCartera:ListaRecordatorios");
                ConsultaRecordatorios();
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = true;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;

            }
            if (proceso == 4) {
                CargaRecordatoriosCombo.clear();
                getConsulRecordator();
                estadoTituloDialogActivRecordat = "Crear y asignar recordatorios";
                NombreActivORecor = "";
                PioridadActivORecor = "";
                TipoPersona = "";
                TipoPersonaExterno = "";
                PersonaExterno = "";
                PersonaInterno = "";
                fechaActividadComplet = null;
                observaActividadComplet = "";

                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = false;
            }
            //Carga la informacion en el form de radicacion desde radicacion
            if (proceso == 5) {
                switch (opcionTableroControlSeguim) {
                    case "1":
                        mbTodero.resetTable("FormCartera:AccionesSeguim");
                        ConsultaActividades(CodigoSeguimiento);
                        break;
                    case "2":
                        mbTodero.resetTable("FormCartera:ListaRecordatorios");
                        ConsultaRecordatorios();
                        break;
                }
            }
            if (proceso == 6) {
                mbTodero.resetTable("FormCartera:ActividadesAsignadas");
                mbTodero.resetTable("FormCartera:ActividadesAsignadasExter");
                ConsultaActividadesAsignadas();
                estadoPanelActividAsign = true;
                estadoPanelRecordatAsign = false;
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;
            }
            if (proceso == 7) {
                mbTodero.resetTable("FormCartera:RecordatorAsignados");
                mbTodero.resetTable("FormCartera:RecordatorAsignadosExter");
                ConsultaRecordatoriosAsignados();
                estadoPanelActividAsign = false;
                estadoPanelRecordatAsign = true;
                estadoPanelMisActivid = false;
                estadoPanelMisRecordat = false;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".habilitarInfoTableroControl()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    public void ConsultaActividades(int CodSeg) {
        try {
            ListAccionesSeg = new ArrayList<>();
            ListAccionesSeg = Seg.consultaActividadesPorAnalista(mBsesion.codigoMiSesion(), CodSeg);
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsultaActividades()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta las actividades y las carga en un selectedOneMenu
     *
     * @param Activd: pARAMETRO QUE TOMA DEPENDIENDO DE QUE SEA EL RECORATORIO O
     * LA ACTIVIDAD , CARTERA O AVALUO :D
     * @return
     */
    public ArrayList<SelectItem> getConsulActividades(String Activd) {
        try {
            Iterator<LogActivRecord> Ite;
            Ite = Seg.consultaActividades(Activd).iterator();
            while (Ite.hasNext()) {
                LogActivRecord seguimActiv = Ite.next();
                this.CargaActividadesCombo.add(new SelectItem(seguimActiv.getCodAccion(), seguimActiv.getNombreAccion()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulActividades()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaActividadesCombo;
    }

    /**
     * Metodo que consulta los recordatorios que tiene un analista asignadas
     *
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
     * Metodo que consulta los recordatorios y los carga en un selectedOneMenu
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @return ArrayList con los recordatorios en un combo
     * @since 01-05-2015
     */
    public ArrayList<SelectItem> getConsulRecordator() {
        try {
            Iterator<LogActivRecord> Ite;
            Ite = Seg.consultaRecordatorios().iterator();
            while (Ite.hasNext()) {
                LogActivRecord Act = Ite.next();
                this.CargaRecordatoriosCombo.add(new SelectItem(Act.getCodRecordat(), Act.getNombreRecorda()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulRecordator()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaRecordatoriosCombo;
    }

    /**
     * Metodo que consulta todas las actividades tanto para realizar el
     * responsable o asignarla a otra persona, sean internos o externos
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
     * Metodo que carga el dialog con la informacion para editar y registrar la
     * informacion de las actividades
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

                    RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').show()");
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargarDialogActivComple()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite completar un recordatorio y cambiarlo de estado
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
     * Metodo que sirve para abrir el dialogo ara crear actividades y
     * recordatorios
     *
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
     * Metodo que permite determinar cual es el tipo de interno que va a ser
     * asignado una actividad o recordatorio
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
     * Metodo que consulta los inernos, que son las personas que pertenecen a
     * empresa y que se involucran en el proceso de los avaluos, y los agrega en
     * una lista selectedOneMenu
     *
     */
    LogEmpleado EmpSeguim = new LogEmpleado();

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
     * Metodo que modifica una actividad cuando ya fue completada
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

                Seg.ModifcActividadORecordat(1, CodigoSeguimiento, mBsesion.codigoMiSesion());
                mbTodero.setMens("Actividad completada correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:AccionesSeguim");
                ConsultaActividades(CodigoSeguimiento);
                RequestContext.getCurrentInstance().execute("PF('DlgActivRealiada').hide()");

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiActividadAsignadaCompleta()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que inserta una nueva actividad o recordatorio y se la asigna a
     * una persona
     */
    private String fechaCorreo;
    private String direccion;
    private String ubicacion;
    private String analista;
    private ResultSet Dat;
    LogAvaluo Aval = new LogAvaluo();

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
                        TipoActividad = "TC";
                        break;
                    case "Crear y asignar recordatorios":
                        TipoActividad = "RC";
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
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", CodigoSeguimiento, mBsesion.codigoMiSesion());

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
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", CodigoSeguimiento, mBsesion.codigoMiSesion());

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
                                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", CodigoSeguimiento, mBsesion.codigoMiSesion());

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
                        Seg.insertActividadORecordat(TipoActividad, Tipo_Persona, "P", CodigoSeguimiento, mBsesion.codigoMiSesion());

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
     * Metodo que permite registra una actividad en el form de administracion o
     * el form de seguimiento
     */
    String Prmtro = "";
    public void regisActivid() {
        try {
            //    mbTodero.resetTable("FormRadicacion:TipActividadTable");
            if ("".equals(this.mBAdmin.getNombreActividad())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                LogAdministracion AdmRegisActiv = new LogAdministracion();
                AdmRegisActiv.setNombreActividad(mBAdmin.getNombreActividad());
                if (mBAdmin.getTipoAct().equals("Avaluo")) {
                    Prmtro = "T";
                } else if (mBAdmin.getTipoAct().equals("Cartera")) {
                    Prmtro = "TC";
                }
                AdmRegisActiv.InsertarActividad(mBsesion.codigoMiSesion(), Prmtro);
                mBAdmin.setCodActividad(0);
                mBAdmin.setNombreActividad("");
                mbTodero.setMens("Registro creado correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipActividadTable");
                mBAdmin.getListActividGest().clear();
                mBAdmin.setListActividGest(AdmRegisActiv.getActividades(Prmtro));
                //   Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaActividadesCombo.clear();
                getConsulActividades(Prmtro);
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
                if (mBAdmin.getTipoAct().equals("Avaluo")) {
                    Prmtro = "T";
                } else if (mBAdmin.getTipoAct().equals("Cartera")) {
                    Prmtro = "TC";
                }
                mBAdmin.getListActividGest().clear();
                mBAdmin.setListActividGest(AdmModifActiv.getActividades(Prmtro));
                //Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaActividadesCombo.clear();
                getConsulActividades(Prmtro);

                RequestContext.getCurrentInstance().execute("PF('DlgActividad').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiActivida()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que registra un recordatorio en el form de administracion o el
     * form de seguimiento
     *
     */
    public void regisRecordator() {

        try {
            mbTodero.resetTable("FormRadicacion:TipRecordatorTable");
            if ("".equals(this.mBAdmin.getNombreRecordatori())) {
                mbTodero.setMens("Favor ingrese  la informacion correspondiente");
                mbTodero.warn();
            } else {
                if (mBAdmin.getTipoAct().equals("Avaluo")) {
                    Prmtro = "R";
                } else if (mBAdmin.getTipoAct().equals("Cartera")) {
                    Prmtro = "RC";
                }
                LogAdministracion AdmRegistroRecord = new LogAdministracion();
                AdmRegistroRecord.setNombreRecordatorio(mBAdmin.getNombreRecordatori());
                AdmRegistroRecord.InsertarRecordatorio(mBsesion.codigoMiSesion(), "Actividad",Prmtro);
                mBAdmin.setCodRecordato(0);
                mBAdmin.setNombreRecordatori("");
                mbTodero.setMens("Registro creado correctamente");
                mbTodero.info();
                mbTodero.resetTable("FormRadicacion:TipRecordatorTable");

                mBAdmin.getListRecordaGest().clear();
                mBAdmin.setListRecordaGest(AdmRegistroRecord.getRecordatorios(Prmtro));
                //   Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaRecordatoriosCombo.clear();
                getConsulRecordator();
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
                  if (mBAdmin.getTipoAct().equals("Avaluo")) {
                    Prmtro = "R";
                } else if (mBAdmin.getTipoAct().equals("Cartera")) {
                    Prmtro = "RC";
                }
                mBAdmin.setListRecordaGest(AdmModifiRecord.getRecordatorios(Prmtro));
                //Ubi.setIdPiso(Adm.ConsulCodigoPrincipalParametro("Cod_Parametro", "Ubicacion"));
                CargaRecordatoriosCombo.clear();
                getConsulRecordator();
                RequestContext.getCurrentInstance().execute("PF('DlgRecordator').hide()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".modifiRecordator()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

}
