package LogBean;

import Logic.LogAdministracion;
import Logic.LogAvaluo;
import Logic.LogCargueArchivos;
import Logic.LogCliente;
import Logic.LogEntidad;
import Logic.LogEntregaYRevision;
import Logic.LogEnvio;
import Logic.LogPerito;
import Logic.LogPermiso;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import net.sf.jasperreports.engine.JRException;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.UploadedFile;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso y manejo de los modulos de entrega y
 * revision que se manejan en la apliacion etc </b></p>
 *
 * @author Jhojan Stiven Rodriguez
 * @author Maira Alejandra Carvajal
 * @version 2.0.0
 * @since 01-06-2015 1.0.0
 *
 *
 */
@ManagedBean(name = "MBEntregaYRevision")
@ViewScoped
@SessionScoped
public class BeanEntregaYRevision {

    /**
     * Variables tipo ResultSet para cargar informacion desde la base de datos*
     */
    private ResultSet DatAvalSeguim;
    private ResultSet DatInfoDevAvaluo;
    private ResultSet DatModificaciones;
    private ResultSet DatInfoGeneralEntrega;
    private ResultSet DatInfoGeneralRevision;
    private ResultSet DatAvalRevision;

    /**
     * Variables tipo List para cargar informacion en tablas, listas,
     * selectedonemenu etc*
     */
    private List<LogEntregaYRevision> ListEntreg = null;
    private List<LogEntregaYRevision> ListEntregDevoluc = new ArrayList<>();
    private List<LogEntregaYRevision> ListMisEntregados = null;
    private List<LogEntregaYRevision> ListAsignadoRevGeneral = null;
    private List<LogEntregaYRevision> ListDevolucRevGeneral = null;
    private List<LogEntregaYRevision> ListMisDevolucRevGeneral = null;
    private List<LogEntregaYRevision> ListMisPendientAprobacion = null;
    private List<LogEntregaYRevision> ListAprobados = null;

    private List<LogEntregaYRevision> ListSeleccionPerito = null;

    LogEnvio Envio = new LogEnvio();

    private List<LogEntregaYRevision> ListSelectEmpRevJuri = null;
    private List<LogEntregaYRevision> ListAsignadoRevJuri = null;
    private List<LogEntregaYRevision> ListDevolucRevJuri = new ArrayList<>();

    private List<LogEntregaYRevision> ListSelectEmpRevTec = null;
    private List<LogEntregaYRevision> LisAsignadotRevTec = null;
    private List<LogEntregaYRevision> ListDevolucRevTec = new ArrayList<>();

    private List<LogEntregaYRevision> LisAsignadoPenPrint = null;
    private List<LogAdministracion> LisArcRegla = null;

    /**
     * Variables para las listas de asignacion de analistas*
     */
    private int CodProEnt;
    private List<LogEntregaYRevision> ListAsigEntrega = null;
    private List<LogEntregaYRevision> ListSeleccAsigEntrega;
    private List<LogEntregaYRevision> ListAnalistEntrega = null;
    private List<LogEntregaYRevision> ListAsigRevisionJur = null;
    private List<LogEntregaYRevision> ListSeleccAsigRevisionJur;
    private List<LogEntregaYRevision> ListAsigRevisionTec = null;
    private List<LogEntregaYRevision> ListSeleccAsigRevisionTec;
    private List<LogEntregaYRevision> ListAnalistRevJur = null;
    private List<LogEntregaYRevision> ListAnalistRevTec = null;

    private List<LogEntregaYRevision> ListActividadesYRecord = null;

    /**
     * Variables de cheque para entrega y revision (Juridica y Tecnica)*
     */
    private ArrayList<SelectItem> CargaCheckEntrega = null;
    private ArrayList<SelectItem> CargaCheckRevJur = null;
    private ArrayList<SelectItem> CargaCheckRevTec = null;
    private ArrayList<SelectItem> CargaCheckImp = null;
    private List CargaSelecCheckEnt = null;
    private List CargaSelecCheckRevJur = null;
    private List CargaSelecCheckRevTec = null;
    private List CargaSelecCheckImp = null;
    private int CodAvaAnterior;

    /**
     * Listas para la validacion de dodumentacion segun el modulo*
     */
    private List<LogCargueArchivos> ListCargueArchEntreg = null;//Esta es para archivos subidos del avaluo en entrega
    private ArrayList<SelectItem> CargaArchivosEnt = null;
    private boolean PanelMuesArcEnt = false;
    private UploadedFile NombreArchivo;
    private int CodArchivo;

    /**
     * Variables para la seleccion del analista en el modulo de entrega de
     * informe*
     */
    private boolean VistoBuenoRevision;
    private String ValorFinal = "";
    private String ObservaAva = "";
    private String AnalistaJurid = "";
    private String AnalistaTecnic = "";
    private int CodEmpJurid;
    private int CodEmpTecnic;

    private String fechaEntregaRegistrada = "";
    private String fechaConvertida;

    /**
     * Variables que se conectan a las diferentes clases de el proyecto
     * NegociosISA > Logic*
     */
    LogEntregaYRevision EnRevAnaliJ = new LogEntregaYRevision();//Variable para seleccionar juridico
    LogEntregaYRevision EnRevAnaliT = new LogEntregaYRevision();//Variable para seleccionar Tecnico
    LogEntregaYRevision EntRev = new LogEntregaYRevision();
    LogEntregaYRevision EntRevInfomPendi = new LogEntregaYRevision();
    LogEntregaYRevision EntrRevAvaluosPeritos = new LogEntregaYRevision();

    LogCargueArchivos CarArc = new LogCargueArchivos();
    LogAvaluo Ava = new LogAvaluo();
    LogPerito PerEntreyRev = new LogPerito();
    LogAdministracion Admin = new LogAdministracion();
    LogPermiso Perm = new LogPermiso();
    LogCliente Client = new LogCliente();
    LogCliente Cliente = new LogCliente();

    /**
     * Muestra de informacion segun el Tipo de Avaluo*
     */
    private boolean PanelPredio = false;
    private boolean PanelMaquinaria = false;
    private boolean PanelEnser = false;
    ResultSet Dat = null, Tab = null;
    ResultSet DatAvaAnt = null;

    /**
     * Para la confirmacion de seleccion de revisores*
     */
    private boolean OpcConfirmRevJuri = false;
    private boolean OpcConfirmRevTecn = false;

    /**
     * Variables para aprobacion de la revision en el modulo de revision*
     */
    private boolean OpcConfirmAvaluo;
    private boolean OpcExistEmail;

    /**
     * Variables para enviar por correo la informacion al avaluador*
     */
    //Variables para saber si es un proceso de Registro o de actualizacion (Tanto para revision como para entrega)
    private String TipoProcesoER;

    private String FechaJuridica;
    private String FechaTecnica;

    /**
     * Variables para devolucion*
     */
    private boolean OpcConfirmDevolu;//Opcion para la confirmacion de la devolucion del avaluo
    private boolean OpcConfirmPeritEmail;
    private ArrayList<SelectItem> CargaPerito = new ArrayList<>();
    private String tipoProceso;
    private String tipoProcesoInt;
    private String codAvaluador;
    private ArrayList<SelectItem> CargaDevolucionCombo = new ArrayList<>();
    private String codTipoDevolucion;
    private String nombreTipoDevolucion;
    private String ObserDevolucion;
    private Date fechaEsperadaCorreccion;
    private String estadoTituloDialogDevoluc;
    private String Fecha_actual;
    private String TipoDevolucion;

    private String NombreTipoDevoluciones;
    private String FechaCreacionDevolucion;
    private String DescripcionDevolucion;
    private boolean DevolucionCompletada;
    private boolean opcionEnvioArchivoDevol;

    private int NumeroAvalDevueltosEntrega;
    private int NumeroActividades;
    private int NumeroMisEntregados;
    private int NumeroPendientesImprimir;
    private int NumeroPendientesImprimirAprobado;
    private int NumeroAprobados;

    private int NumeroMisAsignadosRevGeneral;
    private int NumeroMisAsignadosRevJurid;
    private int NumeroMisAsignadosRevTecnic;
    private int NumeroDevolucionesReviGeneral;
    private int NumeroDevolucionesReviJurid;
    private int NumeroDevolucionesReviTecnic;
    private int NumeroPendientesAprobar;

    private boolean estadoOpcionrRegistrar;
    private boolean estadoOpcionModificar;
    private boolean estadoOpcionActividades;

    /**
     * Variables para contruccion de zonas para el tipo de avaluo Predio*
     */
    private ArrayList<SelectItem> ListConstruccion = new ArrayList<>();
    private ArrayList<SelectItem> ListMedicion = new ArrayList<>();
    private ArrayList<SelectItem> ListMatricula = new ArrayList<>();
    private ArrayList<SelectItem> ListTipoMaquin = new ArrayList<>();
    private List<LogEntregaYRevision> ListZonasConst = new ArrayList<>();
    private int NumConstrucc = 0;
    private String NombreConstucc;
    private String ValorConstr;
    private String MetroCuadrado;
    private String Matricula;
    private String TipoMedida;
    private int TipMaquin = 0;
    private double SumTotal;

    /**
     * Variables para enviar notificacion al avaluador*
     */
    private boolean OpcConfirmModificAvaluador;
    private List codModificSelecc;
    private List<SelectItem> CargaModificacionesLista = new ArrayList<>();
    private ArrayList<SelectItem> CargaPeritoModific = new ArrayList<>();
    private String codAvaluadorModif;
    private String observacionesModificacionesInfo;
    private String estadoTituloDialogModificac;
    private String nombreModificaciones = "";
    private List<SelectItem> CargaModifSeleccionados = new ArrayList<>();

    /**
     * Variables para realizar el envio de archivo tanto para modificacion como
     * para devolucion del avaluo*
     */
    private boolean OpcionArchivo;
    private String[] ListArcInformModificacion;
    private List ListArcInformDevolucion;

    private boolean esperarAprobación;

    /**
     * Variables para el propietario*
     */
    private String opcionRadioPropietario;
    private ArrayList<LogCliente> CargClientesSeleccPropiet = new ArrayList<>();
    private ArrayList<LogEntidad> CargEntidSeleccPropiet = new ArrayList<>();
    private boolean estadoLabelsClienteEnti;
    private int CodPropietario;
    private String NumDocPropietario;
    private String NombrePropietario;
    private boolean PanelPropietario;
    LogCliente ClieConta = new LogCliente();
    LogEntidad EntiConta = new LogEntidad();
    LogEntidad Enti = new LogEntidad();

    /**
     * Variables para correos*
     */
    Calendar fecha = new GregorianCalendar();
    private String fechaCorreo;
    private String direccion;
    private String ubicacion;
    private String analista;

    private String nombreResponsableSeguimiento;
    private String correoResponsableSeguimiento;

    private String nombreResponsableRevTecnica;
    private String correoResponsableRevTecnica;

    private String TextoCorreo;

    ResultSet DatTipoDevolucion;
    ResultSet DatAvaluadorDevoluc;

    SimpleDateFormat FormatFecha = new SimpleDateFormat("yyyy-MM-dd HH:mm");

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
     * Variable tipo BeanSeguimiento para traer los atributos y metodos de el
     * ManagedBean BeanSeguimiento.java
     *
     *
     * @see BeanSeguimiento.java
     */
    @ManagedProperty("#{MBSeguimiento}")
    private BeanSeguimiento mBSeguimiento;

    public BeanSeguimiento getmBSeguimiento() {
        return mBSeguimiento;
    }

    public void setmBSeguimiento(BeanSeguimiento mBSeguimiento) {
        this.mBSeguimiento = mBSeguimiento;
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

    public void setmBCliente(BeanCliente mBEntidad) {
        this.mBCliente = mBEntidad;
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

    public List<LogEntregaYRevision> getListMisDevolucRevGeneral() {
        return ListMisDevolucRevGeneral;
    }

    public void setListMisDevolucRevGeneral(List<LogEntregaYRevision> ListMisDevolucRevGeneral) {
        this.ListMisDevolucRevGeneral = ListMisDevolucRevGeneral;
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
    public List<LogEntregaYRevision> getListEntreg() {
        return ListEntreg;
    }

    public void setListEntreg(List<LogEntregaYRevision> ListEntreg) {
        this.ListEntreg = ListEntreg;
    }

    public List<LogEntregaYRevision> getListEntregDevoluc() {
        return ListEntregDevoluc;
    }

    public void setListEntregDevoluc(List<LogEntregaYRevision> ListEntregDevoluc) {
        this.ListEntregDevoluc = ListEntregDevoluc;
    }

    public List<LogEntregaYRevision> getListMisEntregados() {
        return ListMisEntregados;
    }

    public void setListMisEntregados(List<LogEntregaYRevision> ListMisEntregados) {
        this.ListMisEntregados = ListMisEntregados;
    }

    public List<LogEntregaYRevision> getListSeleccionPerito() {
        return ListSeleccionPerito;
    }

    public void setListSeleccionPerito(List<LogEntregaYRevision> ListSeleccionPerito) {
        this.ListSeleccionPerito = ListSeleccionPerito;
    }

    public List<LogEntregaYRevision> getListSelectEmpRevJuri() {
        return ListSelectEmpRevJuri;
    }

    public void setListSelectEmpRevJuri(List<LogEntregaYRevision> ListSelectEmpRevJuri) {
        this.ListSelectEmpRevJuri = ListSelectEmpRevJuri;
    }

    public List<LogEntregaYRevision> getListSelectEmpRevTec() {
        return ListSelectEmpRevTec;
    }

    public void setListSelectEmpRevTec(List<LogEntregaYRevision> ListSelectEmpRevTec) {
        this.ListSelectEmpRevTec = ListSelectEmpRevTec;
    }

    public List<LogEntregaYRevision> getListAsignadoRevGeneral() {
        return ListAsignadoRevGeneral;
    }

    public void setListAsignadoRevGeneral(List<LogEntregaYRevision> ListAsignadoRevGeneral) {
        this.ListAsignadoRevGeneral = ListAsignadoRevGeneral;
    }

    public List<LogEntregaYRevision> getListDevolucRevGeneral() {
        return ListDevolucRevGeneral;
    }

    public void setListDevolucRevGeneral(List<LogEntregaYRevision> ListDevolucRevGeneral) {
        this.ListDevolucRevGeneral = ListDevolucRevGeneral;
    }

    public List<LogEntregaYRevision> getListMisPendientAprobacion() {
        return ListMisPendientAprobacion;
    }

    public void setListMisPendientAprobacion(List<LogEntregaYRevision> ListMisPendientAprobacion) {
        this.ListMisPendientAprobacion = ListMisPendientAprobacion;
    }

    public List<LogEntregaYRevision> getListAprobados() {
        return ListAprobados;
    }

    public void setListAprobados(List<LogEntregaYRevision> ListAprobados) {
        this.ListAprobados = ListAprobados;
    }

    public List<LogEntregaYRevision> getListDevolucRevJuri() {
        return ListDevolucRevJuri;
    }

    public void setListDevolucRevJuri(List<LogEntregaYRevision> ListDevolucRevJuri) {
        this.ListDevolucRevJuri = ListDevolucRevJuri;
    }

    public List<LogEntregaYRevision> getListDevolucRevTec() {
        return ListDevolucRevTec;
    }

    public void setListDevolucRevTec(List<LogEntregaYRevision> ListDevolucRevTec) {
        this.ListDevolucRevTec = ListDevolucRevTec;
    }

    public List<LogEntregaYRevision> getListAsignadoRevJuri() {
        return ListAsignadoRevJuri;
    }

    public void setListAsignadoRevJuri(List<LogEntregaYRevision> ListAsignadoRevJuri) {
        this.ListAsignadoRevJuri = ListAsignadoRevJuri;
    }

    public List<LogEntregaYRevision> getLisAsignadotRevTec() {
        return LisAsignadotRevTec;
    }

    public void setLisAsignadotRevTec(List<LogEntregaYRevision> LisAsignadotRevTec) {
        this.LisAsignadotRevTec = LisAsignadotRevTec;
    }

    public List<LogEntregaYRevision> getLisAsignadoPenPrint() {
        return LisAsignadoPenPrint;
    }

    public void setLisAsignadoPenPrint(List<LogEntregaYRevision> LisAsignadoPenPrint) {
        this.LisAsignadoPenPrint = LisAsignadoPenPrint;
    }

    public List<LogAdministracion> getLisArcRegla() {
        return LisArcRegla;
    }

    public void setLisArcRegla(List<LogAdministracion> LisArcRegla) {
        this.LisArcRegla = LisArcRegla;
    }

    public int getCodProEnt() {
        return CodProEnt;
    }

    public void setCodProEnt(int CodProEnt) {
        this.CodProEnt = CodProEnt;
    }

    public List<LogEntregaYRevision> getListAsigEntrega() {
        return ListAsigEntrega;
    }

    public void setListAsigEntrega(List<LogEntregaYRevision> ListAsigEntrega) {
        this.ListAsigEntrega = ListAsigEntrega;
    }

    public List<LogEntregaYRevision> getListSeleccAsigEntrega() {
        return ListSeleccAsigEntrega;
    }

    public void setListSeleccAsigEntrega(List<LogEntregaYRevision> ListSeleccAsigEntrega) {
        this.ListSeleccAsigEntrega = ListSeleccAsigEntrega;
    }

    public List<LogEntregaYRevision> getListAnalistEntrega() {
        return ListAnalistEntrega;
    }

    public void setListAnalistEntrega(List<LogEntregaYRevision> ListAnalistEntrega) {
        this.ListAnalistEntrega = ListAnalistEntrega;
    }

    public List<LogEntregaYRevision> getListAsigRevisionJur() {
        return ListAsigRevisionJur;
    }

    public void setListAsigRevisionJur(List<LogEntregaYRevision> ListAsigRevisionJur) {
        this.ListAsigRevisionJur = ListAsigRevisionJur;
    }

    public List<LogEntregaYRevision> getListSeleccAsigRevisionJur() {
        return ListSeleccAsigRevisionJur;
    }

    public void setListSeleccAsigRevisionJur(List<LogEntregaYRevision> ListSeleccAsigRevisionJur) {
        this.ListSeleccAsigRevisionJur = ListSeleccAsigRevisionJur;
    }

    public List<LogEntregaYRevision> getListAsigRevisionTec() {
        return ListAsigRevisionTec;
    }

    public void setListAsigRevisionTec(List<LogEntregaYRevision> ListAsigRevisionTec) {
        this.ListAsigRevisionTec = ListAsigRevisionTec;
    }

    public List<LogEntregaYRevision> getListSeleccAsigRevisionTec() {
        return ListSeleccAsigRevisionTec;
    }

    public void setListSeleccAsigRevisionTec(List<LogEntregaYRevision> ListSeleccAsigRevisionTec) {
        this.ListSeleccAsigRevisionTec = ListSeleccAsigRevisionTec;
    }

    public List<LogEntregaYRevision> getListAnalistRevJur() {
        return ListAnalistRevJur;
    }

    public void setListAnalistRevJur(List<LogEntregaYRevision> ListAnalistRevJur) {
        this.ListAnalistRevJur = ListAnalistRevJur;
    }

    public List<LogEntregaYRevision> getListAnalistRevTec() {
        return ListAnalistRevTec;
    }

    public void setListAnalistRevTec(List<LogEntregaYRevision> ListAnalistRevTec) {
        this.ListAnalistRevTec = ListAnalistRevTec;
    }

    public List<LogEntregaYRevision> getListActividadesYRecord() {
        return ListActividadesYRecord;
    }

    public void setListActividadesYRecord(List<LogEntregaYRevision> ListActividadesYRecord) {
        this.ListActividadesYRecord = ListActividadesYRecord;
    }

    public ArrayList<SelectItem> getCargaCheckEntrega() {
        return CargaCheckEntrega;
    }

    public void setCargaCheckEntrega(ArrayList<SelectItem> CargaCheckEntrega) {
        this.CargaCheckEntrega = CargaCheckEntrega;
    }

    public ArrayList<SelectItem> getCargaCheckRevJur() {
        return CargaCheckRevJur;
    }

    public void setCargaCheckRevJur(ArrayList<SelectItem> CargaCheckRevJur) {
        this.CargaCheckRevJur = CargaCheckRevJur;
    }

    public ArrayList<SelectItem> getCargaCheckRevTec() {
        return CargaCheckRevTec;
    }

    public void setCargaCheckRevTec(ArrayList<SelectItem> CargaCheckRevTec) {
        this.CargaCheckRevTec = CargaCheckRevTec;
    }

    public ArrayList<SelectItem> getCargaCheckImp() {
        return CargaCheckImp;
    }

    public void setCargaCheckImp(ArrayList<SelectItem> CargaCheckImp) {
        this.CargaCheckImp = CargaCheckImp;
    }

    public List<Integer> getCargaSelecCheckEnt() {
        return CargaSelecCheckEnt;
    }

    public void setCargaSelecCheckEnt(List<Integer> CargaSelecCheckEnt) {
        this.CargaSelecCheckEnt = CargaSelecCheckEnt;
    }

    public List<Integer> getCargaSelecCheckRevJur() {
        return CargaSelecCheckRevJur;
    }

    public void setCargaSelecCheckRevJur(List<Integer> CargaSelecCheckRevJur) {
        this.CargaSelecCheckRevJur = CargaSelecCheckRevJur;
    }

    public List<Integer> getCargaSelecCheckRevTec() {
        return CargaSelecCheckRevTec;
    }

    public void setCargaSelecCheckRevTec(List<Integer> CargaSelecCheckRevTec) {
        this.CargaSelecCheckRevTec = CargaSelecCheckRevTec;
    }

    public List getCargaSelecCheckImp() {
        return CargaSelecCheckImp;
    }

    public void setCargaSelecCheckImp(List CargaSelecCheckImp) {
        this.CargaSelecCheckImp = CargaSelecCheckImp;
    }

    public int getCodAvaAnterior() {
        return CodAvaAnterior;
    }

    public void setCodAvaAnterior(int CodAvaAnterior) {
        this.CodAvaAnterior = CodAvaAnterior;
    }

    public List<LogCargueArchivos> getListCargueArchEntreg() {
        return ListCargueArchEntreg;
    }

    public void setListCargueArchEntreg(List<LogCargueArchivos> ListCargueArchEntreg) {
        this.ListCargueArchEntreg = ListCargueArchEntreg;
    }

    public ArrayList<SelectItem> getCargaArchivosEnt() {
        return CargaArchivosEnt;
    }

    public void setCargaArchivosEnt(ArrayList<SelectItem> CargaArchivosEnt) {
        this.CargaArchivosEnt = CargaArchivosEnt;
    }

    public boolean isPanelMuesArcEnt() {
        return PanelMuesArcEnt;
    }

    public void setPanelMuesArcEnt(boolean PanelMuesArcEnt) {
        this.PanelMuesArcEnt = PanelMuesArcEnt;
    }

    public UploadedFile getNombreArchivo() {
        return NombreArchivo;
    }

    public void setNombreArchivo(UploadedFile NombreArchivo) {
        this.NombreArchivo = NombreArchivo;
    }

    public int getCodArchivo() {
        return CodArchivo;
    }

    public void setCodArchivo(int CodArchivo) {
        this.CodArchivo = CodArchivo;
    }

    public boolean isVistoBuenoRevision() {
        return VistoBuenoRevision;
    }

    public void setVistoBuenoRevision(boolean VistoBuenoRevision) {
        this.VistoBuenoRevision = VistoBuenoRevision;
    }

    public String getValorFinal() {
        return ValorFinal;
    }

    public void setValorFinal(String ValorFinal) {
        this.ValorFinal = ValorFinal;
    }

    public String getObservaAva() {
        return ObservaAva;
    }

    public void setObservaAva(String ObservaAva) {
        this.ObservaAva = ObservaAva;
    }

    public String getAnalistaJurid() {
        return AnalistaJurid;
    }

    public void setAnalistaJurid(String AnalistaJurid) {
        this.AnalistaJurid = AnalistaJurid;
    }

    public String getAnalistaTecnic() {
        return AnalistaTecnic;
    }

    public void setAnalistaTecnic(String AnalistaTecnic) {
        this.AnalistaTecnic = AnalistaTecnic;
    }

    public int getCodEmpJurid() {
        return CodEmpJurid;
    }

    public void setCodEmpJurid(int CodEmpJurid) {
        this.CodEmpJurid = CodEmpJurid;
    }

    public int getCodEmpTecnic() {
        return CodEmpTecnic;
    }

    public void setCodEmpTecnic(int CodEmpTecnic) {
        this.CodEmpTecnic = CodEmpTecnic;
    }

    public String getFechaEntregaRegistrada() {
        return fechaEntregaRegistrada;
    }

    public void setFechaEntregaRegistrada(String fechaEntregaRegistrada) {
        this.fechaEntregaRegistrada = fechaEntregaRegistrada;
    }

    public LogEntregaYRevision getEnRevAnaliJ() {
        return EnRevAnaliJ;
    }

    public void setEnRevAnaliJ(LogEntregaYRevision EnRevAnaliJ) {
        this.EnRevAnaliJ = EnRevAnaliJ;
    }

    public LogEntregaYRevision getEnRevAnaliT() {
        return EnRevAnaliT;
    }

    public void setEnRevAnaliT(LogEntregaYRevision EnRevAnaliT) {
        this.EnRevAnaliT = EnRevAnaliT;
    }

    public LogEntregaYRevision getEntRev() {
        return EntRev;
    }

    public void setEntRev(LogEntregaYRevision EntRev) {
        this.EntRev = EntRev;
    }

    public LogEntregaYRevision getEntRevInfomPendi() {
        return EntRevInfomPendi;
    }

    public void setEntRevInfomPendi(LogEntregaYRevision EntRevInfomPendi) {
        this.EntRevInfomPendi = EntRevInfomPendi;
    }

    public LogEntregaYRevision getEntrRevAvaluosPeritos() {
        return EntrRevAvaluosPeritos;
    }

    public void setEntrRevAvaluosPeritos(LogEntregaYRevision EntrRevAvaluosPeritos) {
        this.EntrRevAvaluosPeritos = EntrRevAvaluosPeritos;
    }

    public LogCargueArchivos getCarArc() {
        return CarArc;
    }

    public void setCarArc(LogCargueArchivos CarArc) {
        this.CarArc = CarArc;
    }

    public LogAvaluo getAva() {
        return Ava;
    }

    public void setAva(LogAvaluo Ava) {
        this.Ava = Ava;
    }

    public LogCliente getClient() {
        return Client;
    }

    public void setClient(LogCliente Client) {
        this.Client = Client;
    }

    public LogCliente getCliente() {
        return Cliente;
    }

    public void setCliente(LogCliente Cliente) {
        this.Cliente = Cliente;
    }

    public boolean isPanelPredio() {
        return PanelPredio;
    }

    public void setPanelPredio(boolean PanelPredio) {
        this.PanelPredio = PanelPredio;
    }

    public boolean isPanelMaquinaria() {
        return PanelMaquinaria;
    }

    public void setPanelMaquinaria(boolean PanelMaquinaria) {
        this.PanelMaquinaria = PanelMaquinaria;
    }

    public boolean isPanelEnser() {
        return PanelEnser;
    }

    public void setPanelEnser(boolean PanelEnser) {
        this.PanelEnser = PanelEnser;
    }

    public boolean isOpcConfirmRevJuri() {
        return OpcConfirmRevJuri;
    }

    public void setOpcConfirmRevJuri(boolean OpcConfirmRevJuri) {
        this.OpcConfirmRevJuri = OpcConfirmRevJuri;
    }

    public boolean isOpcConfirmRevTecn() {
        return OpcConfirmRevTecn;
    }

    public void setOpcConfirmRevTecn(boolean OpcConfirmRevTecn) {
        this.OpcConfirmRevTecn = OpcConfirmRevTecn;
    }

    public boolean isOpcConfirmAvaluo() {
        return OpcConfirmAvaluo;
    }

    public void setOpcConfirmAvaluo(boolean OpcConfirmAvaluo) {
        this.OpcConfirmAvaluo = OpcConfirmAvaluo;
    }

    public boolean isOpcExistEmail() {
        return OpcExistEmail;
    }

    public void setOpcExistEmail(boolean OpcExistEmail) {
        this.OpcExistEmail = OpcExistEmail;
    }

    public ArrayList<SelectItem> getCargaPerito() {
        return CargaPerito;
    }

    public void setCargaPerito(ArrayList<SelectItem> CargaPerito) {
        this.CargaPerito = CargaPerito;
    }

    public String getTipoProcesoER() {
        return TipoProcesoER;
    }

    public String getFechaJuridica() {
        return FechaJuridica;
    }

    public void setFechaJuridica(String FechaJuridica) {
        this.FechaJuridica = FechaJuridica;
    }

    public String getFechaTecnica() {
        return FechaTecnica;
    }

    public void setFechaTecnica(String FechaTecnica) {
        this.FechaTecnica = FechaTecnica;
    }

    public void setTipoProcesoER(String TipoProcesoER) {
        this.TipoProcesoER = TipoProcesoER;
    }

    //devolucion el avaluo
    public boolean isOpcConfirmDevolu() {
        return OpcConfirmDevolu;
    }

    public void setOpcConfirmDevolu(boolean OpcConfirmDevolu) {
        this.OpcConfirmDevolu = OpcConfirmDevolu;
    }

    public boolean isOpcConfirmPeritEmail() {
        return OpcConfirmPeritEmail;
    }

    public void setOpcConfirmPeritEmail(boolean OpcConfirmPeritEmail) {
        this.OpcConfirmPeritEmail = OpcConfirmPeritEmail;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public String getTipoProcesoInt() {
        return tipoProcesoInt;
    }

    public void setTipoProcesoInt(String tipoProcesoInt) {
        this.tipoProcesoInt = tipoProcesoInt;
    }

    public String getCodAvaluador() {
        return codAvaluador;
    }

    public void setCodAvaluador(String codAvaluador) {
        this.codAvaluador = codAvaluador;
    }

    public ArrayList<SelectItem> getCargaDevolucionCombo() {
        return CargaDevolucionCombo;
    }

    public void setCargaDevolucionCombo(ArrayList<SelectItem> CargaDevolucionCombo) {
        this.CargaDevolucionCombo = CargaDevolucionCombo;
    }

    public String getObserDevolucion() {
        return ObserDevolucion;
    }

    public void setObserDevolucion(String ObserDevolucion) {
        this.ObserDevolucion = ObserDevolucion;
    }

    public String getCodTipoDevolucion() {
        return codTipoDevolucion;
    }

    public void setCodTipoDevolucion(String codTipoDevolucion) {
        this.codTipoDevolucion = codTipoDevolucion;
    }

    public Date getFechaEsperadaCorreccion() {
        return fechaEsperadaCorreccion;
    }

    public void setFechaEsperadaCorreccion(Date fechaEsperadaCorreccion) {
        this.fechaEsperadaCorreccion = fechaEsperadaCorreccion;
    }

    public String getEstadoTituloDialogDevoluc() {
        return estadoTituloDialogDevoluc;
    }

    public void setEstadoTituloDialogDevoluc(String estadoTituloDialogDevoluc) {
        this.estadoTituloDialogDevoluc = estadoTituloDialogDevoluc;
    }

    public String getFecha_actual() {
        return Fecha_actual;
    }

    public void setFecha_actual(String Fecha_actual) {
        this.Fecha_actual = Fecha_actual;
    }

    public String getTipoDevolucion() {
        return TipoDevolucion;
    }

    public void setTipoDevolucion(String TipoDevolucion) {
        this.TipoDevolucion = TipoDevolucion;
    }

    public String getNombreTipoDevoluciones() {
        return NombreTipoDevoluciones;
    }

    public void setNombreTipoDevoluciones(String NombreTipoDevoluciones) {
        this.NombreTipoDevoluciones = NombreTipoDevoluciones;
    }

    public String getFechaCreacionDevolucion() {
        return FechaCreacionDevolucion;
    }

    public void setFechaCreacionDevolucion(String FechaCreacionDevolucion) {
        this.FechaCreacionDevolucion = FechaCreacionDevolucion;
    }

    public String getDescripcionDevolucion() {
        return DescripcionDevolucion;
    }

    public void setDescripcionDevolucion(String DescripcionDevolucion) {
        this.DescripcionDevolucion = DescripcionDevolucion;
    }

    public boolean isDevolucionCompletada() {
        return DevolucionCompletada;
    }

    public boolean isOpcionEnvioArchivoDevol() {
        return opcionEnvioArchivoDevol;
    }

    public void setOpcionEnvioArchivoDevol(boolean opcionEnvioArchivoDevol) {
        this.opcionEnvioArchivoDevol = opcionEnvioArchivoDevol;
    }

    public void setDevolucionCompletada(boolean DevolucionCompletada) {
        this.DevolucionCompletada = DevolucionCompletada;
    }

    public int getNumeroAvalDevueltosEntrega() {
        return NumeroAvalDevueltosEntrega;
    }

    public void setNumeroAvalDevueltosEntrega(int NumeroAvalDevueltosEntrega) {
        this.NumeroAvalDevueltosEntrega = NumeroAvalDevueltosEntrega;
    }

    public int getNumeroMisEntregados() {
        return NumeroMisEntregados;
    }

    public void setNumeroMisEntregados(int NumeroMisEntregados) {
        this.NumeroMisEntregados = NumeroMisEntregados;
    }

    public int getNumeroPendientesImprimir() {
        return NumeroPendientesImprimir;
    }

    public void setNumeroPendientesImprimir(int NumeroPendientesImprimir) {
        this.NumeroPendientesImprimir = NumeroPendientesImprimir;
    }

    public int getNumeroPendientesImprimirAprobado() {
        return NumeroPendientesImprimirAprobado;
    }

    public void setNumeroPendientesImprimirAprobado(int NumeroPendientesImprimirAprobado) {
        this.NumeroPendientesImprimirAprobado = NumeroPendientesImprimirAprobado;
    }

    public int getNumeroAprobados() {
        return NumeroAprobados;
    }

    public void setNumeroAprobados(int NumeroAprobados) {
        this.NumeroAprobados = NumeroAprobados;
    }

    public int getNumeroMisAsignadosRevGeneral() {
        return NumeroMisAsignadosRevGeneral;
    }

    public void setNumeroMisAsignadosRevGeneral(int NumeroMisAsignadosRevGeneral) {
        this.NumeroMisAsignadosRevGeneral = NumeroMisAsignadosRevGeneral;
    }

    public int getNumeroMisAsignadosRevJurid() {
        return NumeroMisAsignadosRevJurid;
    }

    public void setNumeroMisAsignadosRevJurid(int NumeroMisAsignadosRevJurid) {
        this.NumeroMisAsignadosRevJurid = NumeroMisAsignadosRevJurid;
    }

    public int getNumeroMisAsignadosRevTecnic() {
        return NumeroMisAsignadosRevTecnic;
    }

    public void setNumeroMisAsignadosRevTecnic(int NumeroMisAsignadosRevTecnic) {
        this.NumeroMisAsignadosRevTecnic = NumeroMisAsignadosRevTecnic;
    }

    public int getNumeroDevolucionesReviGeneral() {
        return NumeroDevolucionesReviGeneral;
    }

    public void setNumeroDevolucionesReviGeneral(int NumeroDevolucionesReviGeneral) {
        this.NumeroDevolucionesReviGeneral = NumeroDevolucionesReviGeneral;
    }

    public int getNumeroDevolucionesReviJurid() {
        return NumeroDevolucionesReviJurid;
    }

    public void setNumeroDevolucionesReviJurid(int NumeroDevolucionesReviJurid) {
        this.NumeroDevolucionesReviJurid = NumeroDevolucionesReviJurid;
    }

    public int getNumeroDevolucionesReviTecnic() {
        return NumeroDevolucionesReviTecnic;
    }

    public void setNumeroDevolucionesReviTecnic(int NumeroDevolucionesReviTecnic) {
        this.NumeroDevolucionesReviTecnic = NumeroDevolucionesReviTecnic;
    }

    public int getNumeroPendientesAprobar() {
        return NumeroPendientesAprobar;
    }

    public void setNumeroPendientesAprobar(int NumeroPendientesAprobar) {
        this.NumeroPendientesAprobar = NumeroPendientesAprobar;
    }

    public int getNumeroActividades() {
        return NumeroActividades;
    }

    public void setNumeroActividades(int NumeroActividades) {
        this.NumeroActividades = NumeroActividades;
    }

    public boolean isEstadoOpcionActividades() {
        return estadoOpcionActividades;
    }

    public void setEstadoOpcionActividades(boolean estadoOpcionActividades) {
        this.estadoOpcionActividades = estadoOpcionActividades;
    }

    public boolean isEstadoOpcionrRegistrar() {
        return estadoOpcionrRegistrar;
    }

    public void setEstadoOpcionrRegistrar(boolean estadoOpcionrRegistrar) {
        this.estadoOpcionrRegistrar = estadoOpcionrRegistrar;
    }

    public boolean isEstadoOpcionModificar() {
        return estadoOpcionModificar;
    }

    public void setEstadoOpcionModificar(boolean estadoOpcionModificar) {
        this.estadoOpcionModificar = estadoOpcionModificar;
    }

    //Enviar modificaciones al avaluador
    public boolean isOpcConfirmModificAvaluador() {
        return OpcConfirmModificAvaluador;
    }

    public void setOpcConfirmModificAvaluador(boolean OpcConfirmModificAvaluador) {
        this.OpcConfirmModificAvaluador = OpcConfirmModificAvaluador;
    }

    public List<SelectItem> getCargaModificacionesLista() {
        return CargaModificacionesLista;
    }

    public void setCargaModificacionesLista(List<SelectItem> CargaModificacionesLista) {
        this.CargaModificacionesLista = CargaModificacionesLista;
    }

    public List<Integer> getCodModificSelecc() {
        return codModificSelecc;
    }

    public void setCodModificSelecc(List<Integer> codModificSelecc) {
        this.codModificSelecc = codModificSelecc;
    }

    public ArrayList<SelectItem> getCargaPeritoModific() {
        return CargaPeritoModific;
    }

    public void setCargaPeritoModific(ArrayList<SelectItem> CargaPeritoModific) {
        this.CargaPeritoModific = CargaPeritoModific;
    }

    public String getCodAvaluadorModif() {
        return codAvaluadorModif;
    }

    public void setCodAvaluadorModif(String codAvaluadorModif) {
        this.codAvaluadorModif = codAvaluadorModif;
    }

    public String getObservacionesModificacionesInfo() {
        return observacionesModificacionesInfo;
    }

    public void setObservacionesModificacionesInfo(String observacionesModificacionesInfo) {
        this.observacionesModificacionesInfo = observacionesModificacionesInfo;
    }

    public String getEstadoTituloDialogModificac() {
        return estadoTituloDialogModificac;
    }

    public void setEstadoTituloDialogModificac(String estadoTituloDialogModificac) {
        this.estadoTituloDialogModificac = estadoTituloDialogModificac;
    }

    public String getNombreModificaciones() {
        return nombreModificaciones;
    }

    public void setNombreModificaciones(String nombreModificaciones) {
        this.nombreModificaciones = nombreModificaciones;
    }

    public List<SelectItem> getCargaModifSeleccionados() {
        return CargaModifSeleccionados;
    }

    public void setCargaModifSeleccionados(List<SelectItem> CargaModifSeleccionados) {
        this.CargaModifSeleccionados = CargaModifSeleccionados;
    }

    public boolean isOpcionArchivo() {
        return OpcionArchivo;
    }

    public void setOpcionArchivo(boolean OpcionArchivo) {
        this.OpcionArchivo = OpcionArchivo;
    }

    public String[] getListArcInformModificacion() {
        return ListArcInformModificacion;
    }

    public void setListArcInformModificacion(String[] ListArcInformModificacion) {
        this.ListArcInformModificacion = ListArcInformModificacion;
    }

    public List getListArcInformDevolucion() {
        return ListArcInformDevolucion;
    }

    public void setListArcInformDevolucion(List ListArcInformDevolucion) {
        this.ListArcInformDevolucion = ListArcInformDevolucion;
    }

    public boolean isEsperarAprobación() {
        return esperarAprobación;
    }

    public void setEsperarAprobación(boolean esperarAprobación) {
        this.esperarAprobación = esperarAprobación;
    }

    public String getOpcionRadioPropietario() {
        return opcionRadioPropietario;
    }

    public void setOpcionRadioPropietario(String opcionRadioPropietario) {
        this.opcionRadioPropietario = opcionRadioPropietario;
    }

    public ArrayList<LogCliente> getCargClientesSeleccPropiet() {
        return CargClientesSeleccPropiet;
    }

    public void setCargClientesSeleccPropiet(ArrayList<LogCliente> CargClientesSeleccPropiet) {
        this.CargClientesSeleccPropiet = CargClientesSeleccPropiet;
    }

    public ArrayList<LogEntidad> getCargEntidSeleccPropiet() {
        return CargEntidSeleccPropiet;
    }

    public void setCargEntidSeleccPropiet(ArrayList<LogEntidad> CargEntidSeleccPropiet) {
        this.CargEntidSeleccPropiet = CargEntidSeleccPropiet;
    }

    public boolean isEstadoLabelsClienteEnti() {
        return estadoLabelsClienteEnti;
    }

    public void setEstadoLabelsClienteEnti(boolean estadoLabelsClienteEnti) {
        this.estadoLabelsClienteEnti = estadoLabelsClienteEnti;
    }

    public int getCodPropietario() {
        return CodPropietario;
    }

    public void setCodPropietario(int CodPropietario) {
        this.CodPropietario = CodPropietario;
    }

    public String getNumDocPropietario() {
        return NumDocPropietario;
    }

    public void setNumDocPropietario(String NumDocPropietario) {
        this.NumDocPropietario = NumDocPropietario;
    }

    public String getNombrePropietario() {
        return NombrePropietario;
    }

    public void setNombrePropietario(String NombrePropietario) {
        this.NombrePropietario = NombrePropietario;
    }

    public boolean isPanelPropietario() {
        return PanelPropietario;
    }

    public void setPanelPropietario(boolean PanelPropietario) {
        this.PanelPropietario = PanelPropietario;
    }

    public LogCliente getClieConta() {
        return ClieConta;
    }

    public void setClieConta(LogCliente ClieConta) {
        this.ClieConta = ClieConta;
    }

    public LogEntidad getEntiConta() {
        return EntiConta;
    }

    public void setEntiConta(LogEntidad EntiConta) {
        this.EntiConta = EntiConta;
    }

    public ArrayList<SelectItem> getListConstruccion() {
        return ListConstruccion;
    }

    public void setListConstruccion(ArrayList<SelectItem> ListConstruccion) {
        this.ListConstruccion = ListConstruccion;
    }

    public ArrayList<SelectItem> getListMedicion() {
        return ListMedicion;
    }

    public void setListMedicion(ArrayList<SelectItem> ListMedicion) {
        this.ListMedicion = ListMedicion;
    }

    public ArrayList<SelectItem> getListMatricula() {
        return ListMatricula;
    }

    public void setListMatricula(ArrayList<SelectItem> ListMatricula) {
        this.ListMatricula = ListMatricula;
    }

    public ArrayList<SelectItem> getListTipoMaquin() {
        return ListTipoMaquin;
    }

    public void setListTipoMaquin(ArrayList<SelectItem> ListTipoMaquin) {
        this.ListTipoMaquin = ListTipoMaquin;
    }

    public List<LogEntregaYRevision> getListZonasConst() {
        return ListZonasConst;
    }

    public void setListZonasConst(List<LogEntregaYRevision> ListZonasConst) {
        this.ListZonasConst = ListZonasConst;
    }

    public int getNumConstrucc() {
        return NumConstrucc;
    }

    public void setNumConstrucc(int NumConstrucc) {
        this.NumConstrucc = NumConstrucc;
    }

    public String getNombreConstucc() {
        return NombreConstucc;
    }

    public void setNombreConstucc(String NombreConstucc) {
        this.NombreConstucc = NombreConstucc;
    }

    public String getValorConstr() {
        return ValorConstr;
    }

    public void setValorConstr(String ValorConstr) {
        this.ValorConstr = ValorConstr;
    }

    public String getMetroCuadrado() {
        return MetroCuadrado;
    }

    public void setMetroCuadrado(String MetroCuadrado) {
        this.MetroCuadrado = MetroCuadrado;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String Matricula) {
        this.Matricula = Matricula;
    }

    public String getTipoMedida() {
        return TipoMedida;
    }

    public void setTipoMedida(String TipoMedida) {
        this.TipoMedida = TipoMedida;
    }

    public int getTipMaquin() {
        return TipMaquin;
    }

    public void setTipMaquin(int TipMaquin) {
        this.TipMaquin = TipMaquin;
    }

    public double getSumTotal() {
        return SumTotal;
    }

    public void setSumTotal(double SumTotal) {
        this.SumTotal = SumTotal;
    }

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
     * Metodo para identificar que proceso se desea realizar en la entrega del
     * informe del perito, Proceso de ENTREGA
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip
     * @throws java.text.ParseException
     * @since 01-06-2015
     */
    public void verificacion(int Tip) throws ParseException {
        try {
            switch (Tip) {
                case 1://Este primer case es para cuando se va a seleccionar los informes que tiene pendiente el perito por entregar
                    if (EntRevInfomPendi == null) {
                        mbTodero.setMens("Seleccione un avaluador para ver Informacion");
                        mbTodero.warn();
                    } //QUITE ESTO TATIANA LOPEZ , POR QUE CUANDO EL PERITO TIENE N AVALUOS ASIGNADOS , PERO ALGUNO DE ELLOS ESTA DEVUELTO NO ME DEJA HACER PROCESO DE SUBIR INFORME
                    /*//
                    } else //GCH - NOV2016 SE INCLUYE LLAMADO NUEVO DIALOGO PARA DEVOLUCIONES
                     if ((EntRevInfomPendi.getTotalAvaluosPenImpe() == 0) && (EntRevInfomPendi.getTotalAvaluosPenImpe() == 0) && (EntRevInfomPendi.getToalAvaluXPaquet() == 0) && (EntRevInfomPendi.getTotalAvaluosDev() > 0)) {

                            Llamar(1, "PF('DlgPeritoDev').show()");

                        }
                     
                     */ else {

                        Llamar(1, "PF('DlgAvaluosPerito').show()");
                        EntRev.setCodPerito(EntRevInfomPendi.getCodPerito());
                        this.ListSeleccionPerito = new ArrayList<>();
                        this.ListSeleccionPerito = EntRevInfomPendi.ConsultAvaluosPerito();
                        if (ListSeleccionPerito.isEmpty()) {
                            mBRadicacion.getmBPreRadicacion().setTamañoTablas("70px");
                        } else {
                            mBRadicacion.getmBPreRadicacion().setTamañoTablas("50px");
                        }

                        EntRevInfomPendi.setCodSeguimiento(this.ListSeleccionPerito.get(0).getCodSeguimiento());
                        Llamar(2, ":contenedorGeneral:Contenedor:FRMEntregInf:InfmePeritoTable");
                        EntrRevAvaluosPeritos = new LogEntregaYRevision();
                    }
                    break;
                case 2:
                    //Este segundo case es la que va a ver los avaluos que faltan por entregar los archivos de informes, por avaluador
                    //Este segundo case es la que va a ver los avaluos DEVUELTOS que faltan por entregar los archivos de informes
                    if (EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Seleccione un numero de avalúo para ver información");
                        mbTodero.warn();
                    } else {
                        mBRadicacion.verInformacion(2, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodSeguimiento());
                        Ava.setAreaTerreno("");
                        Ava.setValorMetroTerreno(null);
                        Ava.setCodAvaluo(EntrRevAvaluosPeritos.getCodAvaluo());
                        Ava.setNumeroBienSeguimiento(String.valueOf(EntrRevAvaluosPeritos.getCodNBien()));
                        Ava.setNomTipAvaluo(EntrRevAvaluosPeritos.getTipoAva());
                        try {
                            switch (EntrRevAvaluosPeritos.getTipoAva()) {
                                case "Predio":
                                    this.PanelPredio = true;
                                    this.PanelEnser = false;
                                    this.PanelMaquinaria = false;
                                    Dat = Ava.ConsullInfAvaluoEntrega(1);
                                    if (Dat.next()) {
                                        Ava.setMatricula(Dat.getString("Matricula"));
                                        Ava.setNombreTipPredio(Dat.getString("TipPredio"));
                                        Ava.setBarrio(Dat.getString("Barrio"));
                                    }
                                    Conexion.Conexion.CloseCon();
                                    break;
                                case "Maquinaria":
                                    this.PanelMaquinaria = true;
                                    this.PanelEnser = false;
                                    this.PanelPredio = false;
                                    Dat = Ava.ConsullInfAvaluoEntrega(2);
                                    if (Dat.next()) {
                                        Ava.setMarcaMaquinaria(Dat.getString("Marca_Maquinaria"));
                                        Ava.setModeloMaquinaria(Dat.getString("Modelo_Maquinaria"));
                                        Ava.setSerieMaquinaria(Dat.getString("Serie_Maquinaria"));
                                        Ava.setNombreTipMaquinaria(Dat.getString("TipMaq"));
                                    }
                                    Conexion.Conexion.CloseCon();
                                    break;
                                default:
                                    this.PanelEnser = true;
                                    this.PanelMaquinaria = false;
                                    this.PanelPredio = false;
                                    Dat = Ava.ConsullInfAvaluoEntrega(3);
                                    if (Dat.next()) {
                                        Ava.setObservacionMueble(Dat.getString("Observacion_Mueble"));
                                    }
                                    Conexion.Conexion.CloseCon();
                                    break;
                            }

                            //Consulta esta informacion solo si el avaluo esta en estado DEVUELTO
                            if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                                DatInfoDevAvaluo = EntRev.consultaInformacionDevoluc(1, EntrRevAvaluosPeritos.getCodSeguimiento(), "E");

                                if (DatInfoDevAvaluo.next()) {
                                    NombreTipoDevoluciones = DatInfoDevAvaluo.getString("Resultado_Parametro");
                                    FechaCreacionDevolucion = DatInfoDevAvaluo.getString("Fecha_Creacion");
                                    DescripcionDevolucion = DatInfoDevAvaluo.getString("Observa_Revision");
                                }
                                Conexion.Conexion.CloseCon();
                            }

                        } catch (SQLException e) {
                            mbTodero.setMens("Error: " + e.getMessage());
                            mbTodero.fatal();
                        }
                    }
                    break;
                case 3: //Metodo validador de la informacion de entrega
                    int Paso = 0;
                    if ("".equals(EntrRevAvaluosPeritos.getValorPerito())) {
                        mbTodero.setMens("Debe llenar el campo 'Valor comercial del avalúo");
                        mbTodero.warn();
                    } else if ((OpcConfirmAvaluo == false) && (CargaCheckEntrega.size() != CargaSelecCheckEnt.size())) {
                        mbTodero.setMens("Debe validar la pregunta '¿Desea subir el informe sin cumplir todo el listado de chequeo?'");
                        mbTodero.warn();
                    } else if (mBArchivos.getListaArchivosAvaEnt().size() <= 0) {
                        mbTodero.setMens("No se puede guardar informacion del avalúo; faltan archivos para subir");
                        mbTodero.warn();
                    } else if ("".equals(ObservaAva) && ("".equals(fechaEntregaRegistrada) || fechaEntregaRegistrada == null)) {
                        mbTodero.setMens("Falta agregar las observaciones");
                        mbTodero.warn();
                    } else {
                        //Esta lista tendra todos los archivos que se encuentren subidos para ese numero de avaluo
                        if (LisArcRegla.size() > 0) {
                            int Cantidad = 0;
                            ListCargueArchEntreg = new ArrayList<>();
                            ListCargueArchEntreg = CarArc.ConsultaArchivAvaluoRadica(1, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien());
                            for (int i = 0; i < ListCargueArchEntreg.size(); i++) {
                                for (int j = 0; j < LisArcRegla.size(); j++) {
                                    if (ListCargueArchEntreg.get(i).getCodTipArchivo() == LisArcRegla.get(j).getCodigoParametro()) {
                                        Cantidad += 1;
                                    }
                                }
                            }
                            if (Cantidad < LisArcRegla.size()) {
                                mbTodero.setMens("Hay documentos obligatorios que aun falta por subir,por favor verifique");
                                mbTodero.warn();
                            } else {
                                Paso = 1;
                            }
                        } else {
                            Paso = 1;
                        }
                        Paso = 1;
                    }
                    if (Paso == 1) {
                        if ("".equals(AnalistaJurid)) {
                            mbTodero.setMens("Falta la asignacion del revisor juridico");
                            mbTodero.warn();
                            Paso = 0;
                        } else if ("".equals(AnalistaTecnic)) {
                            mbTodero.setMens("Falta la asignacion del revisor tecnico");
                            mbTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (Paso == 1) {
                        EntRev = new LogEntregaYRevision();
                        EntRev.setCodAvaluo(EntrRevAvaluosPeritos.getCodAvaluo());
                        EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                        EntRev.setCodNBien(EntrRevAvaluosPeritos.getCodNBien());
                        if (EntrRevAvaluosPeritos.getValorPerito().contains(",")) {
                            EntRev.setValorPerito(EntrRevAvaluosPeritos.getValorPerito().replace(",", ""));
                        }
                        EntRev.setRevisorJuridico(String.valueOf(CodEmpJurid));
                        EntRev.setRevisorTecnico(String.valueOf(CodEmpTecnic));
                        EntRev.setObservaRevision(ObservaAva + " - ENTREGA DEL AVALÚO");

                        //Verifica que la fecha de entrega este llena
                        // si esta llena solo modifica la informacin, si no, la ingresa normalmente
                        if ("".equals(fechaEntregaRegistrada) || fechaEntregaRegistrada == null) {

                            //Esto para el caso de check list Entrega
                            EntRev.DeleteCheList("E");
                            for (int i = 0; i < CargaSelecCheckEnt.size(); i++) {
                                EntRev.InserCheList(1, Integer.parseInt(CargaSelecCheckEnt.get(i).toString()), mBSesion.codigoMiSesion());
                            }

                            if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                                if (DevolucionCompletada) {
                                    EntRev.InsertInfEntreg(1, mBSesion.codigoMiSesion());
                                    EntRev.InserDevolucion(5, "", 0, "", mBSesion.codigoMiSesion());
                                    mbTodero.setMens("La Información se ha ingresado correctamente para el avalúo :" + EntrRevAvaluosPeritos.getCodAvaluo() + " - " + EntrRevAvaluosPeritos.getCodNBien());
                                    mbTodero.info();

                                    RequestContext.getCurrentInstance().execute("PF('DialogOkEntrega').show()");
                                } else {
                                    mbTodero.setMens("Para guardar la información debe verificar que el motivo de la devolución se haya solucionado");
                                    mbTodero.warn();
                                }

                            } else {

                                //TATIANA , ACA EJECUTA LA RESPONSABILIDAD DE ENTREGA
                                EntRev.InsertInfEntreg(1, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La Información se ha ingresado correctamente para el avalúo :" + EntrRevAvaluosPeritos.getCodAvaluo() + " - " + EntrRevAvaluosPeritos.getCodNBien());
                                mbTodero.info();

                                RequestContext.getCurrentInstance().execute("PF('DialogOkEntrega').show()");
                            }

                        } else {

                            if (EntrRevAvaluosPeritos.getValorPerito().contains(",")) {
                                EntRev.setValorPerito(EntrRevAvaluosPeritos.getValorPerito().replace(",", ""));
                            }
                            if (!"".equals(ObservaAva) || ObservaAva != null) {
                                EntRev.setObservaRevision(ObservaAva + " - ENTREGA DEL AVALÚO");
                                EntRev.InsertInfEntregModific(2, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                            } else {
                                EntRev.InsertInfEntregModific(1, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                            }
                            mbTodero.setMens("La Información se ha modificada correctamente para el avalúo :" + EntrRevAvaluosPeritos.getCodAvaluo() + " - " + EntrRevAvaluosPeritos.getCodNBien());
                            mbTodero.info();
                            RequestContext.getCurrentInstance().execute("PF('DialogOkEntrega').show()");

                        }

                    }
                    break;

            }
        } catch (ParseException | NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".verificacion()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que carga la informacion de la entrega en el respectivo formulario
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void consultaInformacionEntrega() {
        try {
            if (EntrRevAvaluosPeritos == null) {
                mbTodero.setMens("Seleccione un numero de avalúo para ver información");
                mbTodero.warn();
            } else {
                mBRadicacion.verInformacion(2, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodSeguimiento());

                //Consulta la inormacion de la entrega
                DatInfoGeneralEntrega = EntRev.consultaInformacionEntregaRealizada(EntrRevAvaluosPeritos.getCodSeguimiento(), "ENTREGA DEL AVALÚO");
                while (DatInfoGeneralEntrega.next()) {

                    if ("J".equals(DatInfoGeneralEntrega.getString("Tipo_Revision"))) {
                        CodEmpJurid = DatInfoGeneralEntrega.getInt("Revisor_Asignado");
                        AnalistaJurid = DatInfoGeneralEntrega.getString("Revisor");

                    }
                    if ("T".equals(DatInfoGeneralEntrega.getString("Tipo_Revision"))) {
                        CodEmpTecnic = DatInfoGeneralEntrega.getInt("Revisor_Asignado");
                        AnalistaTecnic = DatInfoGeneralEntrega.getString("Revisor");
                    }

                    // ObservaAva = DatInfoGeneralEntrega.getString("Observacion_Seg");
                    fechaEntregaRegistrada = DatInfoGeneralEntrega.getString("Fecha_Entrega");
                    //Carga valor final

                }
                Conexion.Conexion.CloseCon();
                ValorFinal = EntrRevAvaluosPeritos.getValorComercAvaluo();

                Ava.setCodAvaluo(EntrRevAvaluosPeritos.getCodAvaluo());
                Ava.setNumeroBienSeguimiento(String.valueOf(EntrRevAvaluosPeritos.getCodNBien()));
                Ava.setNomTipAvaluo(EntrRevAvaluosPeritos.getTipoAva());
                try {
                    switch (EntrRevAvaluosPeritos.getTipoAva()) {
                        case "Predio":
                            this.PanelPredio = true;
                            this.PanelEnser = false;
                            this.PanelMaquinaria = false;
                            Dat = Ava.ConsullInfAvaluoEntrega(1);
                            if (Dat.next()) {
                                Ava.setMatricula(Dat.getString("Matricula"));
                                Ava.setNombreTipPredio(Dat.getString("TipPredio"));
                                Ava.setBarrio(Dat.getString("Barrio"));
                            }
                            ListZonasConst = new ArrayList<>();
                            Tab = EntRev.ConsultZonasPredio(Ava.getCodAvaluo(), Integer.parseInt(Ava.getNumeroBienSeguimiento()));
                            while (Tab.next()) {
                                if (Tab.getString("TipConst") != null) {
                                    LogEntregaYRevision ZonEYR = new LogEntregaYRevision();
                                    ZonEYR.setMatricula(Tab.getString("Matricula"));
                                    ZonEYR.setCodZona(Tab.getInt("TipoConstruccion"));
                                    ZonEYR.setNomZona(Tab.getString("TipConst"));
                                    ZonEYR.setAreaConst(Tab.getString("AreaTerreno"));
                                    ZonEYR.setValorConst(Tab.getString("ValMetTerren"));
                                    ZonEYR.setNomMedic(Tab.getString("TipArea"));
                                    ZonEYR.setCodMedic(Tab.getInt("TipoArea"));
                                    ZonEYR.setSubTotArea(Tab.getString("SubTot"));
                                    ListZonasConst.add(ZonEYR);
                                }
                            }
                            break;
                        case "Maquinaria":
                            this.PanelMaquinaria = true;
                            this.PanelEnser = false;
                            this.PanelPredio = false;
                            Dat = Ava.ConsullInfAvaluoEntrega(2);
                            if (Dat.next()) {
                                Ava.setMarcaMaquinaria(Dat.getString("Marca_Maquinaria"));
                                Ava.setModeloMaquinaria(Dat.getString("Modelo_Maquinaria"));
                                Ava.setSerieMaquinaria(Dat.getString("Serie_Maquinaria"));
                                Ava.setNombreTipMaquinaria(Dat.getString("TipMaq"));
                            }
                            break;
                        default:
                            this.PanelEnser = true;
                            this.PanelMaquinaria = false;
                            this.PanelPredio = false;
                            Dat = Ava.ConsullInfAvaluoEntrega(3);
                            if (Dat.next()) {
                                Ava.setObservacionMueble(Dat.getString("Observacion_Mueble"));
                            }
                            Conexion.Conexion.CloseCon();
                            break;
                    }
                    //Consulta la lista de checkeo con la informacion que tiene cargada
                    CargaCheckEntrega = new ArrayList<>();
                    CargaCheckEntrega = ConsListasCheck("EA", 'E');
                    CargaSelecCheckEnt = new ArrayList<>();
                    Dat = EntRev.ConsultaListCheckSeleccionadaAva(EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien(), 'E');
                    while (Dat.next()) {
                        CargaSelecCheckEnt.add(Dat.getInt("CodChequeo"));
                    }
                    //Carga la infomacion de documentos de entrega
                    int NTipAva = 0;
                    NTipAva = CarArc.ConsultaTipAval(EntrRevAvaluosPeritos.getTipoAva());//Variable me trae el registro del tipo de avaluo que hay
                    //Construir el path segun el numer de avaluo que se contiene
                    String Archivo = CarArc.ConsulDirectorioAvaluo(NTipAva, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien(), File.separator + "DBInforme" + File.separator);
                    Archivo = File.separator + "DBARCHIVOS" + Archivo;
                    mBArchivos.setOpcionPanel("SelEntrega");
                    mBArchivos.setPath(Archivo);
                    mBArchivos.seleccionPanel(mBSesion.codigoMiSesion());
                    //Carga los tipos de archivos segun el tipo de bien
                    setCargaArchivosEnt(new ArrayList<SelectItem>());
                    switch (EntrRevAvaluosPeritos.getTipoAva()) {
                        case "Predio":
                            setCargaArchivosEnt(mBArchivos.getConsulArchivos(8));
                            break;
                        case "Maquinaria":
                            setCargaArchivosEnt(mBArchivos.getConsulArchivos(9));
                            break;
                        case "Mueble":
                            setCargaArchivosEnt(mBArchivos.getConsulArchivos(10));
                            break;
                    }
                    //Consulta esta informacion solo si el avaluo esta en estado DEVUELTO
                    if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                        DatInfoDevAvaluo = EntRev.consultaInformacionDevoluc(1, EntrRevAvaluosPeritos.getCodSeguimiento(), "E");

                        if (DatInfoDevAvaluo.next()) {
                            NombreTipoDevoluciones = DatInfoDevAvaluo.getString("Resultado_Parametro");
                            FechaCreacionDevolucion = DatInfoDevAvaluo.getString("Fecha_Creacion");
                            DescripcionDevolucion = DatInfoDevAvaluo.getString("Observa_Revision");
                        }
                    }

                } catch (SQLException e) {
                    mbTodero.setMens("Error: " + e.getMessage());
                    mbTodero.fatal();
                }
            }
        } catch (ParseException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaInformacionEntrega()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para realizar la verificacion de la subida del informe enviado por
     * el perito
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Validador int que contiene el numero de tab y la respectiva
     * validacion que va a realizar
     * @since 01-06-2015
     */
    public void cargarInforEntreg(int Validador) {
        try {
            CargaArchivosEnt = new ArrayList<>();
            switch (Validador) {
                case 1://Este case esta validando el primer tab de la informacion del cargue de los datos de la entrega del informe
                    if (this.PanelPredio == true && this.PanelMaquinaria == false && this.PanelEnser == false) {// Condicion en el caso de que se este llenando la informacion de Predio
                        if ("".equals(EntrRevAvaluosPeritos.getValorPerito())) {
                            mbTodero.setMens("Debe llenar el campo 'Valor comercial del avalúo");
                            mbTodero.warn();
                        } else {
                            //mBArchivos = new BeanArchivos();
                            CargaArchivosEnt = mBArchivos.getConsulArchivos(8);
                            Llamar(1, "PF('TabEntrega').disable(7)");
                            Llamar(1, "PF('TabEntrega').enable(6)");
                            Llamar(1, "PF('TabEntrega').select(6)");
                        }
                    } else if (this.PanelPredio == false && this.PanelMaquinaria == true && this.PanelEnser == false) {// Condicion en el caso de que se este llenando la informacion de Maquinaria
                        if ("".equals(EntrRevAvaluosPeritos.getValorPerito())) {
                            mbTodero.setMens("Debe llenar el campo 'Valor comercial del avalúo");
                            mbTodero.warn();
                        } else {
                            CargaArchivosEnt = mBArchivos.getConsulArchivos(9);
                            Llamar(1, "PF('TabEntrega').disable(7)");
                            Llamar(1, "PF('TabEntrega').enable(6)");
                            Llamar(1, "PF('TabEntrega').select(6)");
                        }
                    } else if (this.PanelPredio == false && this.PanelMaquinaria == false && this.PanelEnser == true) {// Condicion en el caso de que se este llenando la informacion de Enser
                        if ("".equals(EntrRevAvaluosPeritos.getValorPerito())) {
                            mbTodero.setMens("Debe llenar el campo 'Valor comercial del avalúo");
                            mbTodero.warn();
                        } else {
                            CargaArchivosEnt = mBArchivos.getConsulArchivos(10);
                            Llamar(1, "PF('TabEntrega').disable(7)");
                            Llamar(1, "PF('TabEntrega').enable(6)");
                            Llamar(1, "PF('TabEntrega').select(6)");
                        }
                    }
                    CargaCheckEntrega = new ArrayList<>();
                    CargaCheckEntrega = ConsListasCheck("EA", 'E');
                    CargaSelecCheckEnt = new ArrayList<>();
                    break;
                case 2:// Este es la devuelta al prime tab
                    Llamar(1, "PF('TabEntrega').disable(6)");
                    Llamar(1, "PF('TabEntrega').enable(5)");
                    Llamar(1, "PF('TabEntrega').select(5)");
                    break;
                case 3:// Se envia al tercer tab
                    int Paso = 0;
                    if (CargaCheckEntrega.size() == CargaSelecCheckEnt.size()) {
                        Paso = 1;
                    } else if (OpcConfirmAvaluo == false) {
                        mbTodero.setMens("Debe validar la pregunta '¿Desea subir el informe sin cumplir todo el listado de chequeo?'");
                        mbTodero.warn();
                    } else {
                        Paso = 1;
                    }
                    //Para realizar el paso al siguiente tab
                    if (Paso == 1) {
                        int NTipAva = 0;
                        try {
                            NTipAva = CarArc.ConsultaTipAval(EntrRevAvaluosPeritos.getTipoAva());//Variable me trae el registro del tipo de avaluo que hay
                        } catch (SQLException e) {
                            mbTodero.setMens("Error: " + e.getMessage());
                        }
                        //Construir el path segun el numer de avaluo que se contiene
                        String Archivo = CarArc.ConsulDirectorioAvaluo(NTipAva, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien(), File.separator + "DBInforme" + File.separator);
                        Archivo = File.separator + "DBARCHIVOS" + Archivo;
                        mBArchivos.setOpcionPanel("SelEntrega");
                        mBArchivos.setPath(Archivo);
                        mBArchivos.seleccionPanel(mBSesion.codigoMiSesion());
                        this.CargaArchivosEnt = new ArrayList<>();
                        if (this.PanelPredio == true) {
                            this.CargaArchivosEnt = mBArchivos.getConsulArchivos(8);
                        } else if (this.PanelMaquinaria == true) {
                            this.CargaArchivosEnt = mBArchivos.getConsulArchivos(9);
                        } else if (this.PanelEnser == true) {
                            this.CargaArchivosEnt = mBArchivos.getConsulArchivos(10);
                        }
                        int ProEnt = Ava.ConsulTipEntAva(EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien());
                        LisArcRegla = new ArrayList<>();
                        LisArcRegla = Admin.ConsulListaReglasProceso(1, ProEnt, NTipAva, "Entrega");
                        Llamar(1, "PF('TabEntrega').enable(7)");
                        Llamar(1, "PF('TabEntrega').select(7)");
                        Llamar(1, "PF('TabEntrega').disable(6)");
                        Llamar(1, "PF('TabEntrega').disable(5)");
                    }
                    break;
                case 4:// Este es la devuelta al segundo tab
                    Llamar(1, "PF('TabEntrega').enable(6)");
                    Llamar(1, "PF('TabEntrega').select(6)");
                    Llamar(1, "PF('TabEntrega').disable(7)");
                    Llamar(1, "PF('TabEntrega').disable(5)");
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargarInforEntreg()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para realizar la verificacion de los archivos que se estan
     * subiendo en el modulo de entrega
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void verArchivos() {
        try {
            if (CodArchivo <= 0 || this.NombreArchivo == null) {
                mbTodero.setMens("Falta informacion para el cargue de archivos");
                mbTodero.warn();
            } else if (NombreArchivo.getSize() > 167772160) {
                mbTodero.setMens("El tamaño del archivo es mayor a lo establecido (20 MB)");
                mbTodero.warn();
            } else {//Se le envian las variables con las cuales se realizara el cargue de los archivos, tener en cuenta que no se debe instancial la clase de BeanArchivos
                mBArchivos.setNombreArchivo(NombreArchivo);
                mBArchivos.setNAvaluo(getEntrRevAvaluosPeritos().getCodAvaluo());
                mBArchivos.setNBien(getEntrRevAvaluosPeritos().getCodNBien());
                mBArchivos.setTipoAvaluo(getEntrRevAvaluosPeritos().getTipoAva());
                mBArchivos.setCodTipArchivo(CodArchivo);
                if ("InserRevisionGeneral".equals(TipoProcesoER) || "InserRevisionTec".equals(TipoProcesoER) || "InserEntrega".equals(TipoProcesoER)) {
                    if ("".equals(EntrRevAvaluosPeritos.getValorComercAvaluo()) && (("InserRevisionGeneral".equals(TipoProcesoER) || "InserRevisionTec".equals(TipoProcesoER)))) {
                        mbTodero.setMens("No ha ingresado un valor comercial del avaluo");
                        mbTodero.warn();
                    }
                    if ("".equals(EntrRevAvaluosPeritos.getValorPerito()) && ("InserEntrega".equals(TipoProcesoER))) {
                        mbTodero.setMens("No ha ingresado el valor dado por el avaluador");
                        mbTodero.warn();
                    } else {
                        if (EntrRevAvaluosPeritos.getValorPerito().contains(",")) {
                            mBArchivos.setValorAvaluo(Double.parseDouble(EntrRevAvaluosPeritos.getValorPerito().replace(",", "")));
                        }
                        mBArchivos.consultaArchivo(2);
                    }
                } else {
                    mBArchivos.consultaArchivo(2);
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".verArchivos()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta el analista que se desea asignar en el form entrega
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip int que contiene un numero para diferenciar el analista que se
     * agregará (1: Revi juridica, 2: Revi Tecnica)
     * @since 01-06-2015
     */
    public void consultaAnalistaRev(int Tip) {
        try {
            switch (Tip) {
                case 1://Para Consulta el de Revision Juridica
                    mbTodero.resetTable("FRMCapturaInf:AnalistaJurTable");
                    EntRev = new LogEntregaYRevision();
                    ListSelectEmpRevJuri = new ArrayList<>();
                    //ListSelectEmpRevJuri = EntRev.ConsulAnalistas(1, EntrRevAvaluosPeritos.getCodAvaluo());  //GCH - NOV2016
                    ListSelectEmpRevJuri = EntRev.ConsulAnalistas(1, Integer.parseInt(mBRadicacion.getCodProEnt())); //GCH - NOV2016
                    Llamar(1, "PF('DlgAnalistaJ').show()");
                    break;
                case 2://Para Consulta el de Revision Tecnica
                    mbTodero.resetTable("FRMCapturaInf:AnalistaTecTable");
                    EntRev = new LogEntregaYRevision();
                    ListSelectEmpRevTec = new ArrayList<>();
                    //ListSelectEmpRevTec = EntRev.ConsulAnalistas(2, EntrRevAvaluosPeritos.getCodAvaluo()); //GCH - NOV2016
                    ListSelectEmpRevTec = EntRev.ConsulAnalistas(2, Integer.parseInt(mBRadicacion.getCodProEnt())); //GCH - NOV2016
                    Llamar(1, "PF('DlgAnalistaT').show()");
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaAnalistaRev()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo usado para la selección y agregar el analista que realizara algun
     * tipo de revision (Tecnica o Juridica)
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip int que contiene un numero para diferenciar el analista que se
     * agregará (1: Revi juridica, 2: Revi Tecnica)
     * @since 01-06-2015
     */
    public void seleccionarAnalista(int Tip) {
        try {
            switch (Tip) {
                case 1://Para Consulta el de Revision Juridica
                    if (EnRevAnaliJ == null) {
                        mbTodero.setMens("Debe seleccionar un analista jurídico");
                        mbTodero.warn();
                    } else {
                        boolean PermJur = Perm.ConsultaPermisoUsuario(1, EnRevAnaliJ.getCodEmpl());
                        if (PermJur == true) {
                            OpcConfirmRevJuri = true;
                            OpcConfirmRevTecn = false;
                            Llamar(1, "PF('DlgConfirma').show()");
                        } else {
                            OpcConfirmRevJuri = false;
                            OpcConfirmRevTecn = false;
                        }
                        CodEmpJurid = EnRevAnaliJ.getCodEmpl();
                        AnalistaJurid = EnRevAnaliJ.getNomEmp();
                        Llamar(1, "PF('DlgAnalistaJ').hide()");
                    }
                    break;
                case 2://Para Consulta el de Revision Tecnica
                    if (null == EnRevAnaliT) {
                        mbTodero.setMens("Debe seleccionar un analista técnico");
                        mbTodero.warn();
                    } else {
                        boolean PermTec = Perm.ConsultaPermisoUsuario(2, EnRevAnaliT.getCodEmpl());
                        if (PermTec == true) {
                            OpcConfirmRevTecn = true;
                            OpcConfirmRevJuri = false;
                            Llamar(1, "PF('DlgConfirma').show()");
                        } else {
                            OpcConfirmRevTecn = false;
                            OpcConfirmRevJuri = false;
                        }
                        CodEmpTecnic = EnRevAnaliT.getCodEmpl();
                        AnalistaTecnic = EnRevAnaliT.getNomEmp();
                        Llamar(1, "PF('DlgAnalistaT').hide()");
                    }
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionarAnalista()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para validar la seleccion del analista y agregar la informacion a
     * el formulario de entrega
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void selecAnalista() {
        try {
            if (this.OpcConfirmRevJuri == true && this.OpcConfirmRevTecn == false) {
                CodEmpTecnic = EnRevAnaliJ.getCodEmpl();
                AnalistaTecnic = EnRevAnaliJ.getNomEmp();
                CodEmpJurid = EnRevAnaliJ.getCodEmpl();
                AnalistaJurid = EnRevAnaliJ.getNomEmp();
            }
            if (this.OpcConfirmRevJuri == false && this.OpcConfirmRevTecn == true) {
                CodEmpJurid = EnRevAnaliT.getCodEmpl();
                AnalistaJurid = EnRevAnaliT.getNomEmp();
                CodEmpTecnic = EnRevAnaliT.getCodEmpl();
                AnalistaTecnic = EnRevAnaliT.getNomEmp();
            }
            Llamar(1, "PF('DlgConfirma').hide()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".selecAnalista()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que que llama la informacion para que elimine o descargue los
     * archivos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip int que identifica que accion se tomara para los archivos (1:
     * eliminar, 2: descargar)
     * @param Op int que contiene el numero de opcion correspontiene a un tipo
     * de archivo especifico por cada proceso
     * @param In int que contiene el numero indicador del archivo por posicion
     * en la lista
     * @since 01-06-2015
     */
    public void tipoAccionArch(int Tip, int Op, int In) {
        try {
            mBArchivos.setNAvaluo(getEntrRevAvaluosPeritos().getCodAvaluo());
            mBArchivos.setNBien(getEntrRevAvaluosPeritos().getCodNBien());
            mBArchivos.setTipoAvaluo(getEntrRevAvaluosPeritos().getTipoAva());
            switch (Tip) {
                case 1://Informacion para eliminar
                    mBArchivos.eliminarAch(Op, In);
                    break;
                case 2://Informacion para descargar
                    mBArchivos.descargarArchAva(In, Op);
                    break;
            }
        } catch (IOException | JRException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".tipoAccionArch()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que va a cargar toda la informacion dentro de los formularios de
     * la revision
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void cargarInformaAvaluo() {
        //int Paso = 0;
        try {
            if (EntrRevAvaluosPeritos == null) {
                mbTodero.setMens("Debe seleccionar un avalúo");
                mbTodero.warn();
            } else {

                mBRadicacion.verInformacion(2, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodSeguimiento());
                //mBRadicacion.verInformacion(2);
                mBArchivos.setNAvaluo(EntrRevAvaluosPeritos.getCodAvaluo());
                mBArchivos.setNBien(EntrRevAvaluosPeritos.getCodNBien());
                mBArchivos.setTipoAvaluo(EntrRevAvaluosPeritos.getTipoAva());
                //mBArchivos.setListaArchivosAvaRevision(new ArrayList<BeanArchivos>());
                mBArchivos.setListaArchivosAva(new ArrayList<BeanArchivos>());
                mBArchivos.setListaArchivosAvaEnt(new ArrayList<BeanArchivos>());
                int NumTipAva = 0;
                NumTipAva = mBArchivos.NumerAvaluo(EntrRevAvaluosPeritos.getTipoAva());
                // Consulta Informacion del Avaluo en archivos de Informe
                String ArchInf = CarArc.ConsulDirectorioAvaluo(NumTipAva, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien(), "DBInforme" + File.separator);
                // Consulta Informacion del Avaluo en archivos de Solicitud
                String ArchSol = CarArc.ConsulDirectorioAvaluo(NumTipAva, EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien(), "DBSolicitud" + File.separator);

                mBArchivos.setListaArchivosAvaAnteriorSol(new ArrayList<BeanArchivos>());
                mBArchivos.setListaArchivosAvaAnteriorInf(new ArrayList<BeanArchivos>());

                ValorFinal = EntrRevAvaluosPeritos.getValorComercAvaluo();
                // Consulta Informacion del Avaluo en archivos de Solicitud del avaluo anterior
                DatAvaAnt = CarArc.ConsultAvaAnterior(EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien());
                try {
                    if (DatAvaAnt.next()) {
                        String ArchSolAvaAntSol = CarArc.ConsulDirectorioAvaluo(NumTipAva, DatAvaAnt.getInt("CodAvaluo"), DatAvaAnt.getInt("NBien"), "DBSolicitud" + File.separator);
                        String ArchSolAvaAntInf = CarArc.ConsulDirectorioAvaluo(NumTipAva, DatAvaAnt.getInt("CodAvaluo"), DatAvaAnt.getInt("NBien"), "DBInforme" + File.separator);
                        CodAvaAnterior = DatAvaAnt.getInt("CodAvaluo");
                        // Carga los archivos se se tienen del avaluo Anterior
                        mBArchivos.setListaArchivosAvaAnteriorSol(mBArchivos.MostrarArchivos(1, File.separator + "DBARCHIVOS" + File.separator + ArchSolAvaAntSol));
                        mBArchivos.setListaArchivosAvaAnteriorInf(mBArchivos.MostrarArchivos(3, File.separator + "DBARCHIVOS" + File.separator + ArchSolAvaAntInf));
                    }
                    Conexion.Conexion.CloseCon();
                } catch (SQLException e) {
                    mbTodero.setMens("Error: " + e.getMessage());
                    mbTodero.error();
                }
                // Carga los archivos de Entrega
                mBArchivos.setListaArchivosAva(mBArchivos.MostrarArchivos(1, File.separator + "DBARCHIVOS" + File.separator + ArchSol));
                // Carga los archivos de Informe
                mBArchivos.setListaArchivosAvaEnt(mBArchivos.MostrarArchivos(3, File.separator + "DBARCHIVOS" + File.separator + ArchInf));

                //En este metodo carga la informacion de las matriculas que 
                if ("Predio".equals(EntrRevAvaluosPeritos.getTipoAva())) {
                    this.ListMatricula = new ArrayList<>();
                    this.ListMatricula = cargListMatricula();
                    this.ListZonasConst = new ArrayList<>();
                    Tab = EntRev.ConsultZonasPredio(EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien());
                    while (Tab.next()) {
                        if (Tab.getString("TipConst") != null) {
                            LogEntregaYRevision ZonEYR = new LogEntregaYRevision();
                            ZonEYR.setMatricula(Tab.getString("Matricula"));
                            ZonEYR.setCodZona(Tab.getInt("TipoConstruccion"));
                            ZonEYR.setNomZona(Tab.getString("TipConst"));
                            ZonEYR.setAreaConst(Tab.getString("AreaTerreno"));
                            ZonEYR.setValorConst(Tab.getString("ValMetTerren"));
                            ZonEYR.setNomMedic(Tab.getString("TipArea"));
                            ZonEYR.setCodMedic(Tab.getInt("TipoArea"));
                            ZonEYR.setSubTotArea(Tab.getString("SubTot"));
                            ListZonasConst.add(ZonEYR);
                        }
                    }
                    Conexion.Conexion.CloseCon();
                }
                PanelPropietario = false;
                if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                    DatInfoDevAvaluo = EntRev.consultaInformacionDevoluc(2, EntrRevAvaluosPeritos.getCodSeguimiento(), "");

                    if (DatInfoDevAvaluo.next()) {
                        NombreTipoDevoluciones = DatInfoDevAvaluo.getString("Resultado_Parametro");
                        FechaCreacionDevolucion = DatInfoDevAvaluo.getString("Fecha_Creacion");
                        DescripcionDevolucion = DatInfoDevAvaluo.getString("Observa_Revision");
                    }
                    Conexion.Conexion.CloseCon();
                }

            }

        } catch (ParseException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargarInformaAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        } catch (SQLException e) {
            Logger.getLogger(BeanEntregaYRevision.class.getName()).log(Level.SEVERE, null, e);
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargarInformaAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo de prueba que remplaza un numero por su respectiva separacion por
     * puntos y comas
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void pruebaDecimal() {
        try {
            String valorConvertido = "";
            if (EntrRevAvaluosPeritos.getValorPerito().contains(",")) {
                valorConvertido = EntrRevAvaluosPeritos.getValorPerito().replace(",", "");
            }
            mbTodero.setMens(EntrRevAvaluosPeritos.getValorPerito());
            mbTodero.info();

            mbTodero.setMens(valorConvertido);
            mbTodero.info();
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".pruebaDecimal()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para cargar los tipos de devolucion en un combo o selectedOneMenu
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso int que carga las devoluciones segun el siguiente
     * indicativo= 1: entrega 2: rev juridica 3: rev tecnica 4: rev juridica y
     * rev tecnica 5: Impresion
     * @return
     * @since 01-06-2015
     */
    public ArrayList<SelectItem> getConsulDevoluciones(int proceso) {
        try {
            Iterator<LogAdministracion> Ite = null;
            switch (proceso) {
                case 1:
                    Ite = Admin.getMotivosDevoluciones(2, "E").iterator();
                    break;
                case 2:
                    Ite = Admin.getMotivosDevoluciones(2, "J").iterator();
                    break;
                case 3:
                    Ite = Admin.getMotivosDevoluciones(2, "T").iterator();
                    break;
                case 4:
                    Ite = Admin.getMotivosDevoluciones(3, "'J','T'").iterator();
                    break;
                case 5:
                    Ite = Admin.getMotivosDevoluciones(2, "I").iterator();
                    break;
                default:
                    break;
            }

            while (Ite.hasNext()) {
                LogAdministracion AdminDevoluciones = Ite.next();
                this.CargaDevolucionCombo.add(new SelectItem(AdminDevoluciones.getCodDevoluc(), AdminDevoluciones.getDesDevoluc()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulDevoluciones()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaDevolucionCombo;
    }

    /**
     * Metodo para cargar las modificaciones de los informes
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @return List con los tipos de modificaciones de informe
     * @since 01-06-2015
     */
    public List<SelectItem> getConsulModificInforme() {
        try {
            Iterator<LogAdministracion> Ite = null;
            Ite = EntRev.getItemsModificacion().iterator();

            while (Ite.hasNext()) {
                LogAdministracion AdminModificaciones = Ite.next();
                this.CargaModificacionesLista.add(new SelectItem(AdminModificaciones.getCodModifica(), AdminModificaciones.getNombreModifica()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulModificInforme()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return this.CargaModificacionesLista;
    }

    /**
     * Metodo para cargar el perito en el array list para enviar el correo
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip
     * @since 01-06-2015
     */
    public void consulPerit(int Tip) {
        try {
            switch (Tip) {
                case 1:
                    //Para cargar envio de correo, en entrega de informe para el caso de generar una devolucion
                    if (OpcConfirmDevolu == true) {
                        CargaPerito = new ArrayList<>();
                        CargaPerito = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());
                        CargaDevolucionCombo = new ArrayList<>();
                        getConsulDevoluciones(1);
                        TipoDevolucion = "E";
                        tipoProceso = "Externo";
                        tipoProcesoInt = "";
                        codAvaluador = "";
                        codTipoDevolucion = "";
                        ObserDevolucion = "";
                        fechaEsperadaCorreccion = null;

                        OpcExistEmail = false;

                        estadoTituloDialogDevoluc = "Devolución de avalúo - Entrega";
                        Llamar(1, "PF('DlgDevolucionEntrega').show()");
                    }
                    break;

                case 3://Cerrar Dialogo Devoluciones en Entrega
                    OpcConfirmDevolu = false;

                    TipoDevolucion = "";
                    tipoProceso = "";
                    tipoProcesoInt = "";
                    codAvaluador = "";
                    codTipoDevolucion = "";
                    ObserDevolucion = "";
                    fechaEsperadaCorreccion = null;

                    Llamar(1, "PF('DlgDevolucionEntrega').hide()");
                    break;
                case 4: //Para generar la devolucion del avaluo desde la revision (Juridica o tecnica)
                    if (OpcConfirmDevolu == true) {
                        CargaPerito = new ArrayList<>();
                        CargaPerito = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());

                        CargaDevolucionCombo = new ArrayList<>();
                        getConsulDevoluciones(4);

                        TipoDevolucion = "RG";
                        tipoProceso = "";
                        tipoProcesoInt = "";
                        codAvaluador = "";
                        codTipoDevolucion = "";
                        ObserDevolucion = "";
                        fechaEsperadaCorreccion = null;
                        this.OpcionArchivo = false;
                        this.ListArcInformDevolucion = new ArrayList<>();

                        OpcExistEmail = false;
                        estadoTituloDialogDevoluc = "Devolución de avalúo - Revisión";
                        Llamar(1, "PF('DlgDevAvalTecYJur').show()");

                    }
                    break;
                case 5: //Cerrar Dialogo de devoluciones de Revision General

                    OpcConfirmDevolu = false;
                    Llamar(1, "PF('DlgDevAvalTecYJur').hide()");
                    break;
                case 6:
                    //Para generar la devolucion del avaluo desde la revision (Juridica )
                    if (OpcConfirmDevolu == true) {

                        CargaPerito = new ArrayList<>();
                        CargaPerito = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());

                        CargaDevolucionCombo = new ArrayList<>();
                        getConsulDevoluciones(2);

                        TipoDevolucion = "RJ";
                        tipoProceso = "";
                        tipoProcesoInt = "";
                        codAvaluador = "";
                        codTipoDevolucion = "";
                        ObserDevolucion = "";
                        fechaEsperadaCorreccion = null;
                        this.OpcionArchivo = false;
                        ListArcInformDevolucion = new ArrayList<>();

                        OpcExistEmail = false;
                        estadoTituloDialogDevoluc = "Devolución de avalúo - R. Juridica";

                        Llamar(1, "PF('DlgDevAvalJ').show()");
                    }
                    break;
                case 7:
                    OpcConfirmDevolu = false;
                    Llamar(1, "PF('DlgDevAvalJ').hide()");
                    break;

                case 8:
                    //Para generar la devolucion del avaluo desde la revision (Juridica )
                    if (OpcConfirmDevolu == true) {

                        CargaPerito = new ArrayList<>();
                        CargaPerito = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());

                        CargaDevolucionCombo = new ArrayList<>();
                        getConsulDevoluciones(3);

                        TipoDevolucion = "RT";
                        tipoProceso = "";
                        tipoProcesoInt = "";
                        codAvaluador = "";
                        codTipoDevolucion = "";
                        ObserDevolucion = "";
                        fechaEsperadaCorreccion = null;
                        this.OpcionArchivo = false;
                        this.ListArcInformDevolucion = new ArrayList<>();

                        OpcExistEmail = false;
                        estadoTituloDialogDevoluc = "Devolución de avalúo - R. Tecnica";

                        Llamar(1, "PF('DlgDevAvalTec').show()");
                    }
                    break;

                case 9:
                    OpcConfirmDevolu = false;
                    Llamar(1, "PF('DlgDevAvalTec').hide()");
                    break;

                case 10:
                    //Para generar la devolucion del avaluo desde la impresion
                    if (OpcConfirmDevolu == true) {
                        CargaPerito = new ArrayList<>();
                        CargaPerito = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());
                        CargaDevolucionCombo = new ArrayList<>();
                        getConsulDevoluciones(5);
                        TipoDevolucion = "I";
                        tipoProceso = "";
                        tipoProcesoInt = "";
                        codAvaluador = "";
                        codTipoDevolucion = "";
                        ObserDevolucion = "";
                        fechaEsperadaCorreccion = null;
                        this.OpcionArchivo = false;
                        this.ListArcInformDevolucion = new ArrayList<>();
                        OpcExistEmail = false;
                        estadoTituloDialogDevoluc = "Devolución de avalúo - Impresión";

                        Llamar(1, "PF('DlgDevAvalImpresion').show()");
                    }
                    break;

                case 11:
                    OpcConfirmDevolu = false;
                    Llamar(1, "PF('DlgDevAvalImpresion').hide()");
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consulPerit()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que sirve para abrir el dialogo para crear devoluciones
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void abrirDialogCreaDevoluciones() {
        try {
            OpcConfirmDevolu = false;
            mBAdmin.setListDevoluc(new ArrayList<LogAdministracion>());
            mBAdmin.setListDevoluc(Admin.getMotivosDevoluciones(1, ""));
            RequestContext.getCurrentInstance().execute("PF('DlgGestionDevoluc').show()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirDialogCreaDevoluciones()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que abre el formulario tipo dialog con la infomacion para agregar
     * una modificacion que se le haya hecho al informe en revision general o
     * revision tecnica
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void abrirDialogModificacionesInforme() {
        try {
            if (OpcConfirmModificAvaluador) {
                CargaPeritoModific = new ArrayList<>();
                CargaPeritoModific = mBPerito.getConsultPerito(mBRadicacion.getCod_avaluo());

                CargaModificacionesLista = new ArrayList<>();
                getConsulModificInforme();

                estadoTituloDialogModificac = "Notificar modificación del informe - Revisión";
                //codModificSelecc = new ArrayList<>();
                codAvaluadorModif = "";
                observacionesModificacionesInfo = "";
                esperarAprobación = false;

                if (ListArcInformModificacion != null) {
                    for (int i = 0; i <= ListArcInformModificacion.length - 1; i++) {
                        ListArcInformModificacion[i] = null;
                    }
                }

                this.OpcionArchivo = false;
                Llamar(1, "PF('DlgModifInforme').show()");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirDialogModificacionesInforme()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que cierra el dialog de las modificaciones del informe en revision
     * tecnica o revision general
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void cerrarDialogModificacionesInforme() {
        try {
            OpcConfirmModificAvaluador = false;
            CargaModifSeleccionados = new ArrayList<>();
            Llamar(1, "PF('DlgModifInforme').hide()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cerrarDialogModificacionesInforme()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que abre el dialog para crear tipos de modificaciones en revision
     * tecnica o revision general
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void abrirDialogCreaModificaciones() {
        try {
            OpcConfirmModificAvaluador = false;
            mBAdmin.setListModificacionInf(new ArrayList<LogAdministracion>());
            mBAdmin.setListModificacionInf(Admin.getItemsModificacion());
            RequestContext.getCurrentInstance().execute("PF('DlgGestionModific').show()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirDialogCreaModificaciones()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que carga modificacion de informe que halla sido seleccionada
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void getcargaModificSelecc() {
        try {
            CargaModifSeleccionados = new ArrayList<>();
            for (int j = 0; j <= this.codModificSelecc.size() - 1; j++) { //carga los producto entidad que fueron seleccionados para el empleado
                //prod_entidad_asignado = NombreProEnt.get(j);
                String codigo = codModificSelecc.get(j).toString();
                String nombre = EntRev.consultaModificacionesPorCod(Integer.parseInt(codigo));
                CargaModifSeleccionados.add(new SelectItem(codigo, nombre));
            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".getcargaModificSelecc()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que convierte la primera letra de una palabra en mayuscula
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param original String que contiene la palabra a convertir
     * @return
     * @since 01-06-2015
     */
    public String capitalizeFirstLetter(String original) {
        try {
            if (original.length() == 0) {
                return original;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".capitalizeFirstLetter()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return original.substring(0, 1).toUpperCase() + original.substring(1).toLowerCase();

    }

    /**
     * Metodo que valida que los campos de el dialog de devolucion se ingresen
     * correctamente
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void validarCamposDevolucion() {
        try {
            if ("".equals(tipoProceso) || tipoProceso == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Tipo de proceso'");
                mbTodero.warn();
            } else if (("".equals(tipoProcesoInt) || tipoProcesoInt == null) && "Interno".equals(tipoProceso)) {
                mbTodero.setMens("Debe seleccionar información del campo 'A que proceso'");
                mbTodero.warn();
            } else if (("".equals(codAvaluador) || codAvaluador == null) && "Externo".equals(tipoProceso)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Avaluador'");
                mbTodero.warn();
            } else if ("".equals(codTipoDevolucion) || codTipoDevolucion == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Tipo de devolución'");
                mbTodero.warn();
            } else if ("".equals(ObserDevolucion) || ObserDevolucion == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Observaciones'");
                mbTodero.warn();
            } else if (fechaEsperadaCorreccion == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Fecha esperada de correccion(es)'");
                mbTodero.warn();
            } else if ((!"".equals(codAvaluador) || codAvaluador != null) && "Externo".equals(tipoProceso)) {
                DatAvaluadorDevoluc = PerEntreyRev.ConsultaCodPerito(1, codAvaluador);
                if (DatAvaluadorDevoluc.next()) {
                    if (DatAvaluadorDevoluc.getString("Email_perito") == null || "".equals(DatAvaluadorDevoluc.getString("Email_perito"))) {
                        mbTodero.setMens("El avaluador que selecciono no tiene correo registrado, no le podra ser notificada la devolución");
                        mbTodero.warn();
                    } else {
                        RequestContext.getCurrentInstance().execute("PF('confirmarcion_devolucion').show()");
                    }
                }
            } else {
                RequestContext.getCurrentInstance().execute("PF('confirmarcion_devolucion').show()");
            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que valida que los campos de el dialog de modificacion de informes
     * se ingresen correctamente
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void validarCamposModificacion() {
        try {
            if (codModificSelecc.isEmpty() || codModificSelecc == null) {
                mbTodero.setMens("Debe seleccionar información del de la lista 'Tipo de modificación'");
                mbTodero.warn();
            } else if (("".equals(codAvaluadorModif) || codAvaluadorModif == null)) {
                mbTodero.setMens("Debe seleccionar información del campo 'Avaluador'");
                mbTodero.warn();
            } else if ("".equals(observacionesModificacionesInfo) || observacionesModificacionesInfo == null) {
                mbTodero.setMens("Debe seleccionar información del campo 'Observaciones'");
                mbTodero.warn();
            } else if ((!"".equals(codAvaluadorModif) || codAvaluadorModif != null) && "Externo".equals(tipoProceso)) {
                DatAvaluadorDevoluc = PerEntreyRev.ConsultaCodPerito(1, codAvaluadorModif);
                if (DatAvaluadorDevoluc.next()) {
                    if (DatAvaluadorDevoluc.getString("Email_perito") == null || "".equals(DatAvaluadorDevoluc.getString("Email_perito"))) {
                        mbTodero.setMens("El avaluador que selecciono no tiene correo registrado, no le podra ser notificada las modificaciones del informe");
                        mbTodero.warn();
                    } else if (esperarAprobación) {

                    } else {
                        RequestContext.getCurrentInstance().execute("PF('confirmarcionEnvioModific').show()");
                    }
                }

            } else if (esperarAprobación) {
                abrirActividadParaAprobarAvaluoModific();
                enviarCorreoModificacionAvaluo();
            } else {
                RequestContext.getCurrentInstance().execute("PF('confirmarcionEnvioModific').show()");
            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que crea la estructura e informacion que ira contenida en el
     * cuerpo que se envia en todos los correos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void informacionCorreos() {
        try {
            String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciemrbre"};

            int año = fecha.get(Calendar.YEAR);
            int mes = fecha.get(Calendar.MONTH) + 1;
            int dia = fecha.get(Calendar.DAY_OF_MONTH);

            String mesLetra = meses[mes - 1];

            fechaCorreo = "Bogota D.C.  " + dia + " de " + mesLetra + " de " + año;
            direccion = "0";
            ubicacion = "0";
            analista = "";

            DatAvalSeguim = Ava.getConsultarAvaluoSeguim(mBRadicacion.getCod_avaluo());
            if (DatAvalSeguim.next()) {
                direccion = DatAvalSeguim.getString("Direccion");
                ubicacion = DatAvalSeguim.getString("Ubicacion");
                analista = DatAvalSeguim.getString("Analista");
                nombreResponsableSeguimiento = DatAvalSeguim.getString("Respon_Segumie");
                if (DatAvalSeguim.getString("Correo_Corporativo_Empleados") != null || "".equals(DatAvalSeguim.getString("Correo_Corporativo_Empleados"))) {
                    correoResponsableSeguimiento = DatAvalSeguim.getString("Correo_Personal_Empleados");
                } else {
                    correoResponsableSeguimiento = DatAvalSeguim.getString("Correo_Corporativo_Empleados");
                }
            }
            Conexion.Conexion.CloseCon();
            DatAvalRevision = EntRev.getConsultarAvaluoRevision(mBRadicacion.getCod_avaluo());
            if (DatAvalRevision.next()) {
                nombreResponsableRevTecnica = DatAvalRevision.getString("Revisor");
                if (DatAvalRevision.getString("Correo_Corporativo_Empleados") != null || "".equals(DatAvalRevision.getString("Correo_Corporativo_Empleados"))) {
                    correoResponsableRevTecnica = DatAvalRevision.getString("Correo_Personal_Empleados");
                } else {
                    correoResponsableRevTecnica = DatAvalRevision.getString("Correo_Corporativo_Empleados");
                }
            }
            Conexion.Conexion.CloseCon();
        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".informacionCorreos()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que realiza las respectivas validaciones, carga la informacion,
     * arma el cuerpo y envia el correo a los avaluadores informando que se le
     * ha devuelto el informe de avaluo
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void enviarCorreoDevolucionAvaluo() {
        try {

            if ("Externo".equals(tipoProceso)) {

                informacionCorreos();

                //Consulta motivos de devoluciones en entrega
                DatTipoDevolucion = EntRev.consultaTipoDevolucionesPorCod(Integer.parseInt(codTipoDevolucion));
                // Conexion.Conexion.CloseCon();
                if (DatTipoDevolucion.next()) {
                    nombreTipoDevolucion = DatTipoDevolucion.getString("Resultado_Parametro");
                }
                //Convirte la fecha date a string
                fechaConvertida = FormatFecha.format(fechaEsperadaCorreccion);

                TextoCorreo = "<b>Fecha:</b> " + fechaCorreo + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Buen dia, me permito informarle que le ha sido devuelto el avalúo con referente a:<br/>"
                        + "<br/>"
                        + "<b>Numero de avaluo:</b> " + mBRadicacion.getCod_avaluo() + ".<br/>"
                        + "<b>Dirección:</b> " + direccion + "<br/>"
                        + "<b>Ubicación:</b> " + ubicacion + "<br/>"
                        + "<br/>"
                        + "<b>Motivo de devolución:</b> " + capitalizeFirstLetter(nombreTipoDevolucion) + ".<br/>"
                        + "<b>Descripción:</b> " + ObserDevolucion + "<br/>"
                        + "<b>Fecha esperada de correcciones:</b> " + fechaConvertida + "<br/>"
                        + "<br/>"
                        + "Cualquier información adicional acerca del avalúo podrá comunicarse con nosotros al PBX 3230450 Opción 1, mencionando el número de avalúo señalado arriba.<br/>"
                        + "<br/>"
                        + " El seguimiento esta a cargo de <br/><br/>"
                        + " <b>NOMBRE: </b> " + nombreResponsableSeguimiento.toUpperCase() + ",<br/>"
                        + " <b>CORREO: </b> " + correoResponsableSeguimiento.toUpperCase() + ",<br/><br/>"
                        + " para mayor información puede comunicarse con el/ella a través de su cuenta de correo."
                        + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Este correo es únicamente informativo y es de uso exclusivo del destinatario(a), puede contener información privilegiada y/o confidencial. Si no es usted el destinatario(a) deberá borrarlo inmediatamente.";

                mBCorreo.setAsunto("Notificación - Devolución de avalúo");
                mBCorreo.setMensaje(TextoCorreo);
                mBCorreo.setMailToReply(correoResponsableSeguimiento);

                DatAvaluadorDevoluc = PerEntreyRev.ConsultaCodPerito(1, codAvaluador);
                if (DatAvaluadorDevoluc.next()) {
                    mBCorreo.setMailDestino(DatAvaluadorDevoluc.getString("Email_perito"));

                    if (opcionEnvioArchivoDevol) {
                        List<String> ListaArchivosTodosArchivosSelect = new ArrayList<>();
                        //rodriguezdjohan@misena.edu.co
                        //brayan1509_94@hotmail.com            

                        for (int k = 0; k <= ListArcInformModificacion.length - 1; k++) {
                            ListaArchivosTodosArchivosSelect.add(ListArcInformModificacion[k]);
                        }

                        mBCorreo.enviarCorreoVariosAdjuntos("", ListaArchivosTodosArchivosSelect);

                    } else {
                        mBCorreo.enviarCorreo(2);
                    }

                }

            }
            EntRev = new LogEntregaYRevision();
            EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
            EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());

            switch (estadoTituloDialogDevoluc) {

                case "Devolución de avalúo - Entrega":

                    EntRev.InserDevolucion(1, "E", Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    mbTodero.setMens("Devolución realizada");
                    mbTodero.info();

                    break;
                case "Devolución de avalúo - Revisión":

                    if ("Externo".equals(tipoProceso)) {
                        EntRev.InserDevolucion(3, "E", Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    } else {
                        EntRev.InserDevolucion(3, tipoProcesoInt, Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    }

                    mbTodero.setMens("Devolución realizada");
                    mbTodero.info();

                    break;
                case "Devolución de avalúo - R. Juridica":

                    if ("Externo".equals(tipoProceso)) {
                        EntRev.InserDevolucion(2, "E", Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    } else {
                        EntRev.InserDevolucion(2, tipoProcesoInt, Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    }
                    mbTodero.setMens("Devolución realizada");
                    mbTodero.info();

                    break;
                case "Devolución de avalúo - R. Tecnica":

                    if ("Externo".equals(tipoProceso)) {
                        EntRev.InserDevolucion(3, "E", Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    } else {
                        EntRev.InserDevolucion(3, tipoProcesoInt, Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    }
                    mbTodero.setMens("Devolución realizada");
                    mbTodero.info();
                    break;

                case "Devolución de avalúo - Impresión":

                    if ("Externo".equals(tipoProceso)) {
                        EntRev.InserDevolucion(4, "E", Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    } else {
                        EntRev.InserDevolucion(3, tipoProcesoInt, Integer.parseInt(codTipoDevolucion), ObserDevolucion, mBSesion.codigoMiSesion());
                    }
                    mbTodero.setMens("Devolución realizada");
                    mbTodero.info();
                    break;
            }

        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".enviarCorreoDevolucionAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que realiza las respectivas validaciones, carga la informacion,
     * arma el cuerpo y envia el correo a los avaluadores informando las
     * modificaciones de el informe de avaluo
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void enviarCorreoModificacionAvaluo() {
        try {

            informacionCorreos();

            //Consulta las modificaciones en entrega
            for (int i = 0; i <= codModificSelecc.size() - 1; i++) {
                nombreModificaciones = nombreModificaciones + "- " + capitalizeFirstLetter(EntRev.consultaModificacionesPorCod(Integer.parseInt(codModificSelecc.get(i).toString()))) + ".<br/>";
            }

            if (esperarAprobación) {
                TextoCorreo = "<b>Fecha:</b> " + fechaCorreo + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Buen dia, me permito informarle que le han realizado las siguientes modificaciones al informe del avalúo con referente a:<br/>"
                        + "<br/>"
                        + "<b>Numero de avaluo:</b> " + mBRadicacion.getCod_avaluo() + ".<br/>"
                        + "<b>Dirección:</b> " + direccion + "<br/>"
                        + "<b>Ubicación:</b> " + ubicacion + "<br/>"
                        + "<br/>"
                        + "<b>Modificaciones:</b><br/>"
                        + nombreModificaciones
                        + "<br/>"
                        + "<b>Observaciones:</b> " + observacionesModificacionesInfo + "<br/>"
                        + "<br/>"
                        + "<b>Quedamos atentos a su respectiva aprobación conforme a los cambios realizados</b>"
                        + "<br/>"
                        + "<br/>"
                        + "Cualquier información adicional acerca del avalúo podrá comunicarse con nosotros al PBX 3230450 Opción 1, mencionando el número de avalúo señalado arriba.<br/>"
                        + "<br/>"
                        + " La revisión esta a cargo de <br/><br/>"
                        + " <b>NOMBRE: </b> " + nombreResponsableRevTecnica.toUpperCase() + ",<br/>"
                        + " <b>CORREO: </b> " + correoResponsableRevTecnica + ",<br/><br/>"
                        + " para mayor información puede comunicarse con el/ella a través de su cuenta de correo."
                        + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Este correo es únicamente informativo y es de uso exclusivo del destinatario(a), puede contener información privilegiada y/o confidencial. Si no es usted el destinatario(a) deberá borrarlo inmediatamente.";

            } else {
                TextoCorreo = "<b>Fecha:</b> " + fechaCorreo + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Buen dia, me permito informarle que le han realizado las siguientes modificaciones al informe del avalúo con referente a:<br/>"
                        + "<br/>"
                        + "<b>Numero de avaluo:</b> " + mBRadicacion.getCod_avaluo() + ".<br/>"
                        + "<b>Dirección:</b> " + direccion + "<br/>"
                        + "<b>Ubicación:</b> " + ubicacion + "<br/>"
                        + "<br/>"
                        + "<b>Modificaciones:</b><br/>"
                        + nombreModificaciones
                        + "<br/>"
                        + "<b>Observaciones:</b> " + observacionesModificacionesInfo + "<br/>"
                        + "<br/>"
                        + "Cualquier información adicional acerca del avalúo podrá comunicarse con nosotros al PBX 3230450 Opción 1, mencionando el número de avalúo señalado arriba.<br/>"
                        + "<br/>"
                        + " La revisión esta a cargo de <br/><br/>"
                        + " <b>NOMBRE: </b> " + nombreResponsableRevTecnica.toUpperCase() + ",<br/>"
                        + " <b>CORREO: </b> " + correoResponsableRevTecnica + ",<br/><br/>"
                        + " para mayor información puede comunicarse con el/ella a través de su cuenta de correo."
                        + "<br/>"
                        + "<br/>"
                        + "<br/>"
                        + "Este correo es únicamente informativo y es de uso exclusivo del destinatario(a), puede contener información privilegiada y/o confidencial. Si no es usted el destinatario(a) deberá borrarlo inmediatamente.";

            }

            mBCorreo.setAsunto("Notificación - Modificaciones sobre el informe de avalúo");
            mBCorreo.setMensaje(TextoCorreo);
            mBCorreo.setMailToReply(correoResponsableRevTecnica);
            DatAvaluadorDevoluc = PerEntreyRev.ConsultaCodPerito(1, codAvaluadorModif);
            if (DatAvaluadorDevoluc.next()) {
                mBCorreo.setMailDestino(DatAvaluadorDevoluc.getString("Email_perito"));

                if (OpcionArchivo) {
                    List<String> ListaArchivosTodosArchivosSelect = new ArrayList<>();
                    //rodriguezdjohan@misena.edu.co
                    //brayan1509_94@hotmail.com            

                    for (int k = 0; k <= ListArcInformModificacion.length - 1; k++) {
                        ListaArchivosTodosArchivosSelect.add(ListArcInformModificacion[k]);
                    }

                    mBCorreo.enviarCorreoVariosAdjuntos("", ListaArchivosTodosArchivosSelect);
                    RequestContext.getCurrentInstance().execute("PF('DlgModifInforme').hide()");

                } else {
                    mBCorreo.enviarCorreo(2);
                }

            }
            EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
            EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
            EntRev.setCodSeguimiento(mBRadicacion.getCod_seguimiento());
            EntRev.DeleteCheList("M");
            //Esto para el caso de check list de modificaciones
            for (int i = 0; i <= codModificSelecc.size() - 1; i++) {
                EntRev.InserCheList(4, Integer.parseInt(codModificSelecc.get(i).toString()), mBSesion.codigoMiSesion());
            }
            if (esperarAprobación) {
                mbTodero.setMens("Modificaciones enviadas correctamente, se debe crear una actividad para esperar la aprobación del avaluador");
                mbTodero.info();
            } else {
                mbTodero.setMens("Modificaciones enviadas correctamente:");
                mbTodero.info();
            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que abre el formulario de actividades para crear actividad al
     * revisor de que espera que el avaluador apruebe las modificaciones
     * realizadas al informe
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void abrirActividadParaAprobarAvaluoModific() {
        try {
            //Abre la informacion pára crear una actividad propia para esperar la aprobacion del avaluador
            mBSeguimiento.habilitarInfoTableroControl(2);
            mBSeguimiento.setObservaActividadComplet("ESPERAR APROBACIÓN POR PARTE DEL AVALUADOR");
            mBSeguimiento.setTipoPersona("Interna");
            mBSeguimiento.selectTipoPersonaInterna();
            mBSeguimiento.setPersonaInterno(String.valueOf(mBSesion.codigoMiSesion()));
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirActividadParaAprobarAvaluoModific()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que inserta la actividad al revisor sobre la aprobacion por parte
     * del avaluador de las modificaciones de informe y luego llama al metodo
     * validacionRevision() para guardar los cambios realizados hasta el momento
     * en el formulario en el que se este ejecutando la accion.
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void guardarCambiosAprobacionAvaluadorRevision() {
        try {
            mBSeguimiento.insertActividadAsignada();
            if (mBSeguimiento.getValidarInserionActividad() != 0) {
                validacionRevision("TempRevisionTecnica");
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".guardarCambiosAprobacionAvaluadorRevision()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que limpia las listas de tipos de devolucion o modificacion
     * dependiendo del parametro que se le envie
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param tipoLista int que contiene un numero para limpiar las listas para=
     * 1 : devolucion 2 : modificacion
     * @since 01-06-2015
     */
    public void onLimpiarListaArchivosAEnviar(int tipoLista) {
        try {
            if (tipoLista == 1) {
                ListArcInformDevolucion = new ArrayList<>();
            } else if (tipoLista == 2) {
                if (ListArcInformModificacion != null) {
                    for (int i = 0; i <= ListArcInformModificacion.length - 1; i++) {
                        ListArcInformModificacion[i] = null;
                    }
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".onLimpiarListaArchivosAEnviar()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para realizar pasos de la revision y las respectivas validaciones
     * de los campos de informacion
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param TipRev
     * @since 01-06-2015
     */
    public void validacionRevision(String TipRev) {
        try {
            int Paso = 0;
            mBSeguimiento.ConsultaActividadesAsignadas();
            int paso1 = 0, paso2 = 0, paso3 = 0, paso4 = 0;
            switch (TipRev) {
                case "RevisionGeneral"://Para el caso de que se envie la informacion cuando el usuario tiene los dos permisos y esta generando la revision

                    if ((!mBSeguimiento.getListActiviAsig().isEmpty() || mBSeguimiento.getListActiviAsig() != null)
                            || (!mBSeguimiento.getListActiviAsigExter().isEmpty() || mBSeguimiento.getListActiviAsigExter() != null)
                            || (!mBSeguimiento.getListRecordatAsig().isEmpty() || mBSeguimiento.getListRecordatAsig() != null)
                            || (!mBSeguimiento.getListRecordatAsigExter().isEmpty() || mBSeguimiento.getListRecordatAsigExter() != null)) {

                        //Actividades
                        for (int i = 0; i <= mBSeguimiento.getListActiviAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsig().get(i).getEstadoAccion())) {
                                paso1 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListActiviAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoAccion())) {
                                paso2 = 1;
                            }
                        }

                        //Recordatorios
                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListRecordatAsig().get(i).getEstadoRecord())) {
                                paso3 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoRecord())) {
                                paso4 = 1;
                            }
                        }
                    }

                    if (paso1 == 1 || paso2 == 1 || paso3 == 1 || paso4 == 1) {
                        mbTodero.setMens("Debe verificar que se hayan completado todas las actividades o recordatorios");
                        mbTodero.warn();
                        Paso = 0;
                    } else if (CargaSelecCheckRevJur.size() <= 0) {
                        mbTodero.setMens("No se ha realizado el check list para la revisión juridica");
                        mbTodero.warn();
                    } else if (CargaSelecCheckRevTec.size() <= 0) {
                        mbTodero.setMens("No se ha realizado el check list para la revisión tecnica");
                        mbTodero.warn();
                    } else if ("".equals(EntrRevAvaluosPeritos.getValorComercAvaluo())) {
                        mbTodero.setMens("No ha ingresado un valor comercial del avaluo");
                        mbTodero.warn();
                    } else if ("".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        mbTodero.setMens("Debe Agregar una observacion al avaluo");
                        mbTodero.warn();
                    } else {
                        Paso = 1;
                    }
                    //Para el caso de que haya reglas de proceso obligatorias
                    if (LisArcRegla.size() > 0) {
                        List<LogCargueArchivos> ListaRevision = new ArrayList<>();
                        ListaRevision = CarArc.ConsultaArchivAvaluoRadica(2, EntrRevAvaluosPeritos.getCodAvaluo(), mBRadicacion.getCod_bien_seguimiento());
                        int Cantidad = 0;
                        for (int i = 0; i < LisArcRegla.size(); i++) {
                            for (int j = 0; j < ListaRevision.size(); j++) {
                                if (LisArcRegla.get(i).getCodigoParametro() == ListaRevision.get(j).getCodTipArchivo()) {
                                    Cantidad += 1;
                                }
                            }
                        }
                        if (Cantidad == LisArcRegla.size()) {
                            Paso = 1;
                        } else {
                            mbTodero.setMens("No se ha subido todos los archivos obligatorios por favor validar y cargue los documentos necesarios");
                            mbTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (Paso == 1) {
                        if ("InserRevisionGeneral".equals(TipoProcesoER)) {
                            EntRev = new LogEntregaYRevision();
                            EntRev.setObservaRevision(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN GENERAL");
                            if (EntrRevAvaluosPeritos.getValorComercAvaluo().contains(",")) {
                                EntRev.setValorPerito(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(",", ""));
                            }
                            //System.out.println("Informacion :D :D " + EntRev.getValorPerito());
                            EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                            EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                            //Ingreso de la informacion para registro de la revision juridica y tecnica al tiempo (Revision general)                        

                            EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                            //Esto para el caso de check list juridico

                            EntRev.DeleteCheList("J");
                            for (int i = 0; i < CargaSelecCheckRevJur.size(); i++) {
                                EntRev.InserCheList(2, Integer.parseInt(CargaSelecCheckRevJur.get(i).toString()), mBSesion.codigoMiSesion());
                            }
                            //Esto para el caso de check list tecnico
                            EntRev.DeleteCheList("T");
                            for (int i = 0; i < CargaSelecCheckRevTec.size(); i++) {
                                EntRev.InserCheList(3, Integer.parseInt(CargaSelecCheckRevTec.get(i).toString()), mBSesion.codigoMiSesion());
                            }

                            if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                                if (DevolucionCompletada) {
                                    EntRev.InsertInfEntreg(2, mBSesion.codigoMiSesion());
                                    EntRev.InserDevolucion(7, "", 0, "", mBSesion.codigoMiSesion());
                                    mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                    mbTodero.info();

                                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                                } else {
                                    mbTodero.setMens("Para guardar la información debe verificar que el motivo de la devolución se haya solucionado");
                                    mbTodero.warn();
                                }

                            } else {

                                EntRev.InsertInfEntreg(2, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                mbTodero.info();

                                RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                            }

                        }
                    }
                    break;

                case "RevisionJuridica"://Cuando se esta generando solo la revision juridica del avaluo.

                    if ((!mBSeguimiento.getListActiviAsig().isEmpty() || mBSeguimiento.getListActiviAsig() != null)
                            || (!mBSeguimiento.getListActiviAsigExter().isEmpty() || mBSeguimiento.getListActiviAsigExter() != null)
                            || (!mBSeguimiento.getListRecordatAsig().isEmpty() || mBSeguimiento.getListRecordatAsig() != null)
                            || (!mBSeguimiento.getListRecordatAsigExter().isEmpty() || mBSeguimiento.getListRecordatAsigExter() != null)) {

                        //Actividades
                        for (int i = 0; i <= mBSeguimiento.getListActiviAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsig().get(i).getEstadoAccion())) {
                                paso1 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListActiviAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoAccion())) {
                                paso2 = 1;
                            }
                        }

                        //Recordatorios
                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListRecordatAsig().get(i).getEstadoRecord())) {
                                paso3 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoRecord())) {
                                paso4 = 1;
                            }
                        }
                    }

                    if (paso1 == 1 || paso2 == 1 || paso3 == 1 || paso4 == 1) {
                        mbTodero.setMens("Debe verificar que se hayan completado todas las actividades o recordatorios");
                        mbTodero.warn();
                        Paso = 0;
                    } else if (CargaSelecCheckRevJur.size() <= 0) {
                        mbTodero.setMens("No se ha realizado el check list para la revisión juridica");
                        mbTodero.warn();
                    } else if ("".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        mbTodero.setMens("Debe agregar una observación al avalúo");
                        mbTodero.warn();
                    } else {
                        Paso = 1;
                    }
                    //Para el caso de que haya reglas de proceso obligatorias
                    if (LisArcRegla.size() > 0) {
                        List<LogCargueArchivos> ListaRevision = new ArrayList<>();
                        ListaRevision = CarArc.ConsultaArchivAvaluoRadica(2, EntrRevAvaluosPeritos.getCodAvaluo(), mBRadicacion.getCod_bien_seguimiento());
                        int Cantidad = 0;
                        for (int i = 0; i < LisArcRegla.size(); i++) {
                            for (int j = 0; j < ListaRevision.size(); j++) {
                                if (LisArcRegla.get(i).getCodigoParametro() == ListaRevision.get(j).getCodTipArchivo()) {
                                    Cantidad += 1;
                                }
                            }
                        }
                        if (Cantidad == LisArcRegla.size()) {
                            Paso = 1;
                        } else {
                            mbTodero.setMens("No se ha subido todos los archivos obligatorios por favor validar y cargue los documentos necesarios");
                            mbTodero.warn();
                            Paso = 0;
                        }
                    }

                    if (Paso == 1) {
                        if ("InserRevisionJur".equals(TipoProcesoER)) {
                            EntRev = new LogEntregaYRevision();
                            EntRev.setObservaRevision(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN JURIDICA");
                            EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                            EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                            //Ingreso de la informacion para registro de la revision juridica
                            EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                            //Esto para el caso de check list juridico
                            EntRev.DeleteCheList("J");
                            for (int i = 0; i < CargaSelecCheckRevJur.size(); i++) {
                                EntRev.InserCheList(2, Integer.parseInt(CargaSelecCheckRevJur.get(i).toString()), mBSesion.codigoMiSesion());
                            }

                            if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                                if (DevolucionCompletada) {
                                    EntRev.InsertInfEntreg(3, mBSesion.codigoMiSesion());
                                    EntRev.InserDevolucion(6, "", 0, "", mBSesion.codigoMiSesion());
                                    mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                    mbTodero.info();

                                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                                } else {
                                    mbTodero.setMens("Para guardar la información debe verificar que el motivo de la devolución se haya solucionado");
                                    mbTodero.warn();
                                }

                            } else {

                                EntRev.InsertInfEntreg(3, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                mbTodero.info();

                                RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                            }

                        }
                    }

                    break;
                case "RevisionTecnica"://Cuando se esta generando solo la revision tecnica del avaluo.

                    if ((!mBSeguimiento.getListActiviAsig().isEmpty() || mBSeguimiento.getListActiviAsig() != null)
                            || (!mBSeguimiento.getListActiviAsigExter().isEmpty() || mBSeguimiento.getListActiviAsigExter() != null)
                            || (!mBSeguimiento.getListRecordatAsig().isEmpty() || mBSeguimiento.getListRecordatAsig() != null)
                            || (!mBSeguimiento.getListRecordatAsigExter().isEmpty() || mBSeguimiento.getListRecordatAsigExter() != null)) {

                        //Actividades
                        for (int i = 0; i <= mBSeguimiento.getListActiviAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsig().get(i).getEstadoAccion())) {
                                paso1 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListActiviAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoAccion())) {
                                paso2 = 1;
                            }
                        }

                        //Recordatorios
                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsig().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListRecordatAsig().get(i).getEstadoRecord())) {
                                paso3 = 1;
                            }
                        }

                        for (int i = 0; i <= mBSeguimiento.getListRecordatAsigExter().size() - 1; i++) {
                            if ("Pendiente por realizar".equals(mBSeguimiento.getListActiviAsigExter().get(i).getEstadoRecord())) {
                                paso4 = 1;
                            }
                        }
                    }

                    if (paso1 == 1 || paso2 == 1 || paso3 == 1 || paso4 == 1) {
                        mbTodero.setMens("Debe verificar que se hayan completado todas las actividades o recordatorios");
                        mbTodero.warn();
                        Paso = 0;
                    } else if (CargaSelecCheckRevTec.size() <= 0) {
                        mbTodero.setMens("No se ha realizado el check list para la revisión tecnica");
                        mbTodero.warn();
                    } else if ("".equals(EntrRevAvaluosPeritos.getValorComercAvaluo())) {
                        mbTodero.setMens("No ha ingresado un valor comercial del avaluo");
                        mbTodero.warn();
                    } else if ("".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        mbTodero.setMens("Debe Agregar una observacion al avaluo");
                        mbTodero.warn();
                    } else {
                        Paso = 1;
                    }
                    //Para el caso de que haya reglas de proceso obligatorias
                    if (LisArcRegla.size() > 0) {
                        List<LogCargueArchivos> ListaRevision = new ArrayList<>();
                        ListaRevision = CarArc.ConsultaArchivAvaluoRadica(2, EntrRevAvaluosPeritos.getCodAvaluo(), mBRadicacion.getCod_bien_seguimiento());
                        int Cantidad = 0;
                        for (int i = 0; i < LisArcRegla.size(); i++) {
                            for (int j = 0; j < ListaRevision.size(); j++) {
                                if (LisArcRegla.get(i).getCodigoParametro() == ListaRevision.get(j).getCodTipArchivo()) {
                                    Cantidad += 1;
                                }
                            }
                        }
                        if (Cantidad == LisArcRegla.size()) {
                            Paso = 1;
                        } else {
                            mbTodero.setMens("No se ha subido todos los archivos obligatorios por favor validar y cargue los documentos necesarios");
                            mbTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (Paso == 1) {
                        if ("InserRevisionTec".equals(TipoProcesoER)) {
                            EntRev = new LogEntregaYRevision();
                            EntRev.setObservaRevision(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN TECNICA");
                            if (EntrRevAvaluosPeritos.getValorComercAvaluo().contains(".")) {
                                EntRev.setValorPerito(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(".", ""));
                            } else {
                                EntRev.setValorPerito(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(",", ""));
                            }
                            EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                            EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                            //Ingreso de la informacion para registro de la revision tecnica                       
                            EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                            //Esto para el caso de check list tecnico
                            EntRev.DeleteCheList("T");
                            for (int i = 0; i < CargaSelecCheckRevTec.size(); i++) {
                                EntRev.InserCheList(3, Integer.parseInt(CargaSelecCheckRevTec.get(i).toString()), mBSesion.codigoMiSesion());
                            }

                            if ("DEVUELTO".equals(mBRadicacion.getEstadoRad())) {
                                if (DevolucionCompletada) {
                                    EntRev.InsertInfEntreg(4, mBSesion.codigoMiSesion());
                                    EntRev.InserDevolucion(7, "", 0, "", mBSesion.codigoMiSesion());
                                    mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                    mbTodero.info();

                                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                                } else {
                                    mbTodero.setMens("Para guardar la información debe verificar que el motivo de la devolución se haya solucionado");
                                    mbTodero.warn();
                                }

                            } else {

                                EntRev.InsertInfEntreg(4, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La información de la revisión se ha ingresado satisfactoriamente");
                                mbTodero.info();

                                RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                            }

                        }
                    }
                    break;
                case "TempRevisionGeneral":
                    /*SE VA A GUARDAR INFORMACION TEMPORAL DE LAS DOS REVISIONES*/
                    EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                    // Informacion para guardar lo que se haya registrado de la revision Juridica    

                    EntRev.DeleteCheList("J");

                    if (CargaSelecCheckRevJur.size() > 0) {
                        for (int i = 0; i < CargaSelecCheckRevJur.size(); i++) {
                            EntRev.InserCheList(2, Integer.parseInt(CargaSelecCheckRevJur.get(i).toString()), mBSesion.codigoMiSesion());
                        }
                    }
                    // Informacion para guardar lo que se haya registrado de la revision Tecnica
                    EntRev.DeleteCheList("T");

                    if (CargaSelecCheckRevTec.size() > 0) {
                        for (int i = 0; i < CargaSelecCheckRevTec.size(); i++) {
                            EntRev.InserCheList(3, Integer.parseInt(CargaSelecCheckRevTec.get(i).toString()), mBSesion.codigoMiSesion());
                        }
                    }
                    // Informacion para ingresar la actualizacion de valor comercial del avaluo
                    if (!"".equals(EntrRevAvaluosPeritos.getValorComercAvaluo())) {
                        if (EntrRevAvaluosPeritos.getValorComercAvaluo().contains(".")) {
                            EntRev.setValorComercAvaluo(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(".", ""));
                        } else {
                            EntRev.setValorComercAvaluo(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(",", ""));
                        }
                        EntRev.InsertInfEntregTemp(2, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                    }
                    // Informacion pra ingresar la actualizacion de la observacion de revision general
                    if (!"".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        EntRev.setObservacion(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN GENERAL");
                        EntRev.InsertInfEntregTemp(1, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                    }

                    mbTodero.setMens("Los datos se han guardado temporalmente");
                    mbTodero.info();

                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                    break;
                case "TempRevisionJuridica":
                    /*GUARDAR INFORMACION TEMPORAL EN EL AREA JURIDICA*/
                    EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                    // Informacion para guardar lo que se haya registrado de la revision Juridica   

                    EntRev.DeleteCheList("J");

                    if (CargaSelecCheckRevJur.size() > 0) {
                        for (int i = 0; i < CargaSelecCheckRevJur.size(); i++) {
                            EntRev.InserCheList(2, Integer.parseInt(CargaSelecCheckRevJur.get(i).toString()), mBSesion.codigoMiSesion());
                        }
                    }
                    // Informacion pra ingresar la actualizacion de la observacion de revision general
                    if (!"".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        EntRev.setObservacion(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN JURIDICA");
                        EntRev.InsertInfEntregTemp(1, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                    }

                    mbTodero.setMens("Los datos se han guardado temporalmente");
                    mbTodero.info();

                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                    break;
                case "TempRevisionTecnica":
                    /*GUARDAR INFORMACION TEMPORAL EN EL AREA TECNICA*/
                    EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                    EntRev.DeleteCheList("T");
                    // Informacion para guardar lo que se haya registrado de la revision Tecnica
                    if (CargaSelecCheckRevTec.size() > 0) {
                        for (int i = 0; i < CargaSelecCheckRevTec.size(); i++) {
                            EntRev.InserCheList(3, Integer.parseInt(CargaSelecCheckRevTec.get(i).toString()), mBSesion.codigoMiSesion());
                        }
                    }
                    // Informacion pra ingresar la actualizacion de la observacion de revision general
                    if (!"".equals(EntrRevAvaluosPeritos.getObservaRevision())) {
                        EntRev.setObservacion(EntrRevAvaluosPeritos.getObservaRevision() + " - REVISIÓN TECNICA");
                        EntRev.InsertInfEntregTemp(1, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                    }
                    // Informacion para ingresar la actualizacion de valor comercial del avaluo
                    if (!"".equals(EntrRevAvaluosPeritos.getValorComercAvaluo())) {
                        if (EntrRevAvaluosPeritos.getValorComercAvaluo().contains(".")) {
                            EntRev.setValorComercAvaluo(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(".", ""));
                        } else {
                            EntRev.setValorComercAvaluo(EntrRevAvaluosPeritos.getValorComercAvaluo().replace(",", ""));
                        }
                        EntRev.InsertInfEntregTemp(2, EntRev.getCodSeguimiento(), mBSesion.codigoMiSesion());
                    }
                    mbTodero.setMens("Los datos se han guardado temporalmente");
                    mbTodero.info();

                    RequestContext.getCurrentInstance().execute("PF('DialogOkRevisiones').show()");
                    break;
                case "ImpresionAva":
                    if (CargaSelecCheckImp.size() <= 0) {
                        mbTodero.setMens("Seleccione por lo menos un item de la lista de chequeo");
                        mbTodero.warn();
                    } else if ("".equals(ObservaAva)) {
                        mbTodero.setMens("La observación del avaluo es obligatoria");
                        mbTodero.warn();
                    } else if (this.PanelPropietario == false) {
                        mbTodero.setMens("Falta ingresar información de propietario");
                        mbTodero.warn();
                    } else {
                        EntRev = new LogEntregaYRevision();
                        EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                        EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                        EntRev.setObservaRevision(ObservaAva);
                        if (ValorFinal.contains(",")) {
                            EntRev.setValorPerito(ValorFinal.replace(",", ""));
                        }
                        if ("Predio".equals(mBAvaluo.getNombreTipAvaluoRadic())) {
                            if (ListZonasConst.size() <= 0) {
                                mbTodero.setMens("No ha ingresado una zona de construcción");
                                mbTodero.warn();
                            } else {
                                double ResultTot = 0;
                                for (int i = 0; i < ListZonasConst.size(); i++) {
                                    String total = ListZonasConst.get(i).getSubTotArea().replace(".", "");
                                    double result = Double.parseDouble(total.replace(",", "."));
                                    ResultTot = ResultTot + result;
                                }

                                double value = ResultTot;
                                DecimalFormat df = new DecimalFormat("#.##");    //This should print a number which is rounded to 2 decimal places.
                                String str = df.format(value);
                                str = str.replace(",", ".");

                                ResultTot = Double.parseDouble(str);
                                //DecimalFormat formatea = new DecimalFormat("###,###.##");

                                double Tot = 0;

                                if (ValorFinal.contains(",")) {
                                    Tot = Double.parseDouble(ValorFinal.replace(",", ""));
                                }

//                            3.5000000059E8
                                if (ResultTot != Tot) {
                                    mbTodero.setMens("El valor comercial final no concuerda con la suma total de las zonas ingresadas");
                                    mbTodero.warn();
                                } else {
                                    //Inserta la informacion de entrega para el proceso de impresion  
                                    if ("".equals(this.NumDocPropietario) || NumDocPropietario == null) {
                                        mbTodero.setMens("Falta ingresar informacion del numero del documentos del propietario");
                                        mbTodero.warn();
                                    } else if ("".equals(this.NombrePropietario) || NombrePropietario == null) {
                                        mbTodero.setMens("Falta el nombre del Propietario por Ingresar");
                                        mbTodero.warn();
                                    } else {
                                        EntRev.InsertPropietario(NumDocPropietario, NombrePropietario);
                                        EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                                        EntRev.InsertInfEntreg(5, mBSesion.codigoMiSesion());
                                    }

                                    EntRev.DeleteCheList("I");
                                    // Informacion para guardar lo que se haya registrado de la impresion
                                    if (CargaSelecCheckImp.size() > 0) {
                                        for (int i = 0; i < CargaSelecCheckImp.size(); i++) {
                                            EntRev.InserCheList(5, Integer.parseInt(CargaSelecCheckImp.get(i).toString()), mBSesion.codigoMiSesion());
                                        }
                                    }

                                    //Ciclo para agregar las zonas de construccion
                                    for (int i = 0; i < ListZonasConst.size(); i++) {
                                        EntRev = new LogEntregaYRevision();
                                        EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                                        EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                                        EntRev.setCodZona(ListZonasConst.get(i).getCodZona());
                                        EntRev.setAreaConst(ListZonasConst.get(i).getAreaConst());
                                        EntRev.setValorConst(ListZonasConst.get(i).getValorConst().replace(",", ""));
                                        EntRev.setCodArea(ListZonasConst.get(i).getCodMedic());
                                        EntRev.setMatricula(ListZonasConst.get(i).getMatricula());
                                        EntRev.InserZonCons(1, mBSesion.codigoMiSesion());

                                    }
                                    mbTodero.setMens("La información ha sido guardada correctamente");
                                    mbTodero.info();
                                    Envio.GestioEnvio(3, mBRadicacion.getCod_avaluo(), "");
                                    RequestContext.getCurrentInstance().execute("PF('DialogOkImpresion').show()");
                                }

                            }
                        } else //Insert ala informacion de entrega para el proceso de impresion
                        {
                            if ("".equals(this.NumDocPropietario) || NumDocPropietario == null) {
                                mbTodero.setMens("Falta ingresar informacion del numero del documentos del propietario");
                                mbTodero.warn();
                            } else if ("".equals(this.NombrePropietario) || NombrePropietario == null) {
                                mbTodero.setMens("Falta el nombre del Propietario por Ingresar");
                                mbTodero.warn();
                            } else {
                                EntRev.InsertPropietario(NumDocPropietario, NombrePropietario);
                                EntRev.InsertInfEntreg(5, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La información ha sido guardada correctamente");
                                mbTodero.info();
                                Envio.GestioEnvio(3, mBRadicacion.getCod_avaluo(), "");
                                RequestContext.getCurrentInstance().execute("PF('DialogOkImpresion').show()");
                            }
                        }
                    }

                    break;
                case "PasoAprobacion":
                    if (CargaSelecCheckImp.size() <= 0) {
                        mbTodero.setMens("Seleccione por lo menos un item de la lista de chequeo");
                        mbTodero.warn();
                    } else if ("".equals(ObservaAva)) {
                        mbTodero.setMens("La observación del avaluo es obligatoria");
                        mbTodero.warn();
                    } else if (this.PanelPropietario == false) {
                        mbTodero.setMens("No ha ingresado información del propietario");
                        mbTodero.warn();
                    } else {
                        EntRev = new LogEntregaYRevision();
                        EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                        EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                        EntRev.setObservaRevision(ObservaAva + " - IMPRESIÓN");
                        if (ValorFinal.contains(",")) {
                            EntRev.setValorPerito(ValorFinal.replace(",", ""));
                        }
                        if ("Predio".equals(mBAvaluo.getNombreTipAvaluoRadic())) {
                            if (ListZonasConst.size() <= 0) {
                                mbTodero.setMens("No ha ingresado una zona de construcción");
                                mbTodero.warn();
                            } else {
                                double ResultTot = 0;
                                for (int i = 0; i < ListZonasConst.size(); i++) {
                                    String total = ListZonasConst.get(i).getSubTotArea().replace(".", "");
                                    ResultTot = ResultTot + Double.parseDouble(total.replace(",", "."));
                                }
                                double Tot = 0;

                                if (ValorFinal.contains(",")) {
                                    Tot = Double.parseDouble(ValorFinal.replace(",", ""));
                                }
                                if (ResultTot != Tot) {
                                    mbTodero.setMens("El valor comercial final no concuerda con la suma total de las zonas ingresadas");
                                    mbTodero.warn();
                                } else {
                                    //Insert ala informacion de entrega para el proceso de impresion
                                    if ("".equals(this.NumDocPropietario) || NumDocPropietario == null) {
                                        mbTodero.setMens("Falta ingresar informacion del numero del documentos del propietario");
                                        mbTodero.warn();
                                    } else if ("".equals(this.NombrePropietario) || NombrePropietario == null) {
                                        mbTodero.setMens("Falta el nombre del Propietario por Ingresar");
                                        mbTodero.warn();
                                    } else {
                                        EntRev.InsertPropietario(NumDocPropietario, NombrePropietario);
                                        EntRev.setCodSeguimiento(EntrRevAvaluosPeritos.getCodSeguimiento());
                                        EntRev.InsertInfEntreg(6, mBSesion.codigoMiSesion());
                                    }

                                    EntRev.DeleteCheList("I");
                                    // Informacion para guardar lo que se haya registrado de la impresion
                                    if (CargaSelecCheckImp.size() > 0) {
                                        for (int i = 0; i < CargaSelecCheckImp.size(); i++) {
                                            EntRev.InserCheList(5, Integer.parseInt(CargaSelecCheckImp.get(i).toString()), mBSesion.codigoMiSesion());
                                        }
                                    }
                                    //Ciclo para agregar las zonas de construccion
                                    for (int i = 0; i < ListZonasConst.size(); i++) {
                                        EntRev = new LogEntregaYRevision();
                                        EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                                        EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                                        EntRev.setCodZona(ListZonasConst.get(i).getCodZona());
                                        EntRev.setAreaConst(ListZonasConst.get(i).getAreaConst());
                                        EntRev.setValorConst(ListZonasConst.get(i).getValorConst().replace(",", ""));
                                        EntRev.setCodArea(ListZonasConst.get(i).getCodMedic());
                                        EntRev.setMatricula(ListZonasConst.get(i).getMatricula());
                                        EntRev.InserZonCons(1, mBSesion.codigoMiSesion());

                                    }
                                    mbTodero.setMens("La información ha sido guardada correctamente");
                                    mbTodero.info();

                                    RequestContext.getCurrentInstance().execute("PF('DialogOkAprobacRev').show()");
                                }

                            }
                        } else {
                            //Insert ala informacion de entrega para el proceso de impresion
                            if ("".equals(this.NumDocPropietario) || NumDocPropietario == null) {
                                mbTodero.setMens("Falta ingresar informacion del numero del documentos del propietario");
                                mbTodero.warn();
                            } else if ("".equals(this.NombrePropietario) || NombrePropietario == null) {
                                mbTodero.setMens("Falta el nombre del Propietario por Ingresar");
                                mbTodero.warn();
                            } else {
                                EntRev.InsertPropietario(NumDocPropietario, NombrePropietario);
                                EntRev.InsertInfEntreg(6, mBSesion.codigoMiSesion());
                                mbTodero.setMens("La informacion ha sido guardada correctamente");
                                mbTodero.info();
                            }

                            RequestContext.getCurrentInstance().execute("PF('DialogOkAprobacRev').show()");
                        }
                    }
                    break;
                case "AprobacionRev":
                    //Aqui va la informacion de aprobacion en el area de revision
                    if (VistoBuenoRevision == false) {
                        mbTodero.setMens("No se ha realizado la aprobación del avalúo");
                        mbTodero.warn();
                    } else if ("".equals(ObservaAva)) {
                        mbTodero.setMens("Debe llenar el campo de observaciones");
                        mbTodero.warn();
                    } else {
                        EntRev = new LogEntregaYRevision();
                        EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                        EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                        EntRev.setObservaRevision(ObservaAva + " - Aprobación");
                        EntRev.InsertInfEntreg(7, mBSesion.codigoMiSesion());
                        mbTodero.setMens("Se ha realizado el ingreso de información correctamente");
                        mbTodero.info();
                        RequestContext.getCurrentInstance().execute("PF('DialogOkAprobadoRevis').show()");

                    }
                    break;
                case "AprobImprimir":
                    EntRev = new LogEntregaYRevision();
                    EntRev.setCodAvaluo(mBRadicacion.getCod_avaluo());
                    EntRev.setCodNBien(mBRadicacion.getCod_bien_seguimiento());
                    EntRev.setObservaRevision(ObservaAva);
                    EntRev.InsertInfEntreg(8, mBSesion.codigoMiSesion());
                    mbTodero.setMens("Se ha realizado el ingreso de información correctamente");
                    mbTodero.info();
                    RequestContext.getCurrentInstance().execute("PF('DialogOkImpresion').show()");
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que para la carga de lista de chequeo
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip String que carga el tipo de lista de chequeo que se va a
     * cargar
     * @param Check char que carga el tipo de letra con la que se identifica
     * cada tipo de lista en la tabla Parametro
     * @return ArrayList con la lista de chequeo
     * @since 01-06-2015
     */
    public ArrayList<SelectItem> ConsListasCheck(String Tip, char Check) {
        ArrayList<SelectItem> ListaPrinc = new ArrayList<>();
        try {
            Iterator<LogEntregaYRevision> Ite;
            Ite = EntrRevAvaluosPeritos.ConsultaListCheck(Tip, Check).iterator();
            while (Ite.hasNext()) {
                LogEntregaYRevision MBEntRev = Ite.next();
                ListaPrinc.add(new SelectItem(MBEntRev.getNumCheck(), MBEntRev.getPreguntaCheck(), String.valueOf(MBEntRev.getCheck())));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".ConsListasCheck()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        return ListaPrinc;
    }

    public void consultaAsigEntrega(int Tip) {
        try {
            switch (Tip) {
                case 1:// Para consulta los avaluos de cambio de analistas en el area de entrega e impresion
                    this.ListAsigEntrega = new ArrayList<>();
                    EntRev.Query = " and  producto_entidad.Cod_ProEnt = " + CodProEnt ; //+ " and revision.Estado_Asignado=1 ";//GCH 11ENE2017
                    this.ListAsigEntrega = EntRev.ConsulAsigInformeEntregPerito(mBSesion.codigoMiSesion());

                    break;

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultTipoCambio()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para realizar los cambios de analistas en el area de entrega,
     * revision juridica y revision tecnica
     *
     * @param Tip int que contiene el numero para cambiar analistas asi= 1: area
     * de entrega e impresion 2: area de revision juridica 3: area de revision
     * tecnica
     */
    public void consultTipoCambio(int Tip) {
        try {
            switch (Tip) {
                case 1:// Para consulta los avaluos de cambio de analistas en el area de entrega e impresion
                    this.ListAsigEntrega = new ArrayList<>();
                    this.ListAsigEntrega = EntRev.ConsultAllAvaluos(1, CodProEnt);
                    break;
                case 2:// Para consultar los avaluos de cambio de analistas en el area de revision juridica 
                    this.ListAsigRevisionJur = new ArrayList<>();
                    this.ListAsigRevisionJur = EntRev.ConsultAllAvaluos(2, CodProEnt);
                    break;
                case 3:// Para consultar los avaluos de cambio de analistas en el area de revision  tecnica
                    this.ListAsigRevisionTec = new ArrayList<>();
                    this.ListAsigRevisionTec = EntRev.ConsultAllAvaluos(3, CodProEnt);
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultTipoCambio()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que carga la consulta de los analistas que tengan el permiso de
     * revisores, area de entrega e imopresion
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip int que contiene el valor de parametro y se utiliza= 1:
     * disponibles en el area de entrega 2: disponibles en el area juridica 3:
     * disponibles en el area tecnica
     * @since 01-06-2015
     */
    public void consultCambioAnalista(int Tip) {
        try {
            switch (Tip) {
                case 1: // Para consulta los analistas que se encuentran disponibles en el area de entrega

                    if (this.ListSeleccAsigEntrega.size() <= 0) {
                        mbTodero.setMens("Por favor seleccionar minimo un numero de avaluo para realizar el cambio de analista");
                        mbTodero.warn();
                    } else {
                        this.ListAnalistEntrega = new ArrayList<>();
                        this.ListAnalistEntrega = EntRev.ConsulAnalistas(3, CodProEnt);
                        Llamar(1, "PF('DlgAnalistaE').show()");
                    }
                    break;
                case 2:// Para consultar los analistas que se encuentran disponibles en el area juridica
                    if (this.ListSeleccAsigRevisionJur.size() <= 0) {
                        mbTodero.setMens("Por favor seleccionar minimo un numero de avaluo para realizar el cambio de analista");
                        mbTodero.warn();
                    } else {
                        this.ListAnalistRevJur = new ArrayList<>();
                        this.ListAnalistRevJur = EntRev.ConsulAnalistas(1, CodProEnt);
                        Llamar(1, "PF('DlgAnalistaE').show()");
                    }
                    break;
                case 3:// Para consultar los analistas que se encuentran disponibles en el area tecnica
                    if (this.ListSeleccAsigRevisionTec.size() <= 0) {
                        mbTodero.setMens("Por favor seleccionar minimo un numero de avaluo para realizar el cambio de analista");
                        mbTodero.warn();
                    } else {
                        EntRev = new LogEntregaYRevision();
                        ListAnalistRevTec = new ArrayList<>();
                        ListAnalistRevTec = EntRev.ConsulAnalistas(2, CodProEnt);
                        Llamar(1, "PF('DlgAnalistaTecnico').show()");
                    }
                    break;

            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultCambioAnalista()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     *
     */
    public void AsignarAnalEntre(int Tip) {
        switch (Tip) {
            case 1:// Para realizar la asignacion del cambio de analistas para el area de entrega
                if (EntrRevAvaluosPeritos == null) {
                    mbTodero.setMens("Seleccione un usuario para realizar el cambio de avaluos");
                    mbTodero.warn();
                } else {
                    EntRev.setCodEmpl(EntrRevAvaluosPeritos.getCodEmpl());

                    for (int i = 0; i < ListSeleccAsigEntrega.size(); i++) {
                        EntRev.setCodAvaluo(ListSeleccAsigEntrega.get(i).getCodSeguimiento());
                        EntRev.setCodNBien(ListSeleccAsigEntrega.get(i).getCodNBien());
                        EntRev.setCodEmpl(EntrRevAvaluosPeritos.getCodEmpl());
                        EntRev.InserCambioAnalist(4, mBSesion.codigoMiSesion());
                    }
                    this.ListAnalistEntrega = new ArrayList<>();
                    consultaAsigEntrega(1);
                    mbTodero.setMens("Se ha realizado la asignación del analista exitosamente");
                    mbTodero.info();
                }
                break;
        }
    }

    /**
     * Metodo para realizar el cambio de analistas y asignarle nuevo avaluos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tip int que contiene el valor de parametro y se utiliza=
     * 1:disponibles en el area de entrega 2: disponibles en el area juridica 3:
     * disponibles en el area tecnica
     * @since 01-06-2015
     *
     */
    public void cambioAnalista(int Tip) {
        try {
            switch (Tip) {
                case 1:// Para realizar la asignacion del cambio de analistas para el area de entrega
                    if (EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Seleccione un usuario para realizar el cambio de avaluos");
                        mbTodero.warn();
                    } else {
                        EntRev.setCodEmpl(EntrRevAvaluosPeritos.getCodEmpl());
                        for (int i = 0; i < ListSeleccAsigEntrega.size(); i++) {
                            EntRev.setCodAvaluo(ListSeleccAsigEntrega.get(i).getCodAvaluo());
                            EntRev.InserCambioAnalist(1, mBSesion.codigoMiSesion());
                        }
                        this.ListAnalistEntrega = new ArrayList<>();
                        this.ListAnalistEntrega = EntRev.ConsulAnalistas(3, CodProEnt);
                        Llamar(1, "PF('DlgAnalistaE').hide()");
                        mbTodero.setMens("Se ha realizado la asignacion del analista correctamente");
                        mbTodero.info();
                        this.ListAsigEntrega = EntRev.ConsultAllAvaluos(1, CodProEnt);
                    }
                    break;
                case 2://Pára realizar la asignacion del cambio de analistas para el area juridica
                    if (EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Seleccione un usuario para realizar el cambio de avaluos");
                        mbTodero.warn();
                    } else {
                        EntRev.setCodEmpl(EntrRevAvaluosPeritos.getCodEmpl());
                        for (int i = 0; i < ListSeleccAsigRevisionJur.size(); i++) {
                            EntRev.setCodAvaluo(ListSeleccAsigRevisionJur.get(i).getCodAvaluo());
                            EntRev.InserCambioAnalist(2, mBSesion.codigoMiSesion());
                        }
                        this.ListAsigRevisionJur = new ArrayList<>();
                        this.ListAsigRevisionJur = EntRev.ConsultAllAvaluos(2, CodProEnt);
                        Llamar(1, "PF('DlgAnalistaE').hide()");
                        mbTodero.setMens("Se ha realizado la asignacion del analista correctamente");
                        mbTodero.info();
                    }
                    break;
                case 3://Para realizar la asignacion del cambio de analistas para el area tecnica
                    if (EntrRevAvaluosPeritos == null) {
                        mbTodero.setMens("Seleccione un usuario para realizar el cambio de avaluos");
                        mbTodero.warn();
                    } else {
                        EntRev.setCodEmpl(EnRevAnaliT.getCodEmpl());
                        for (int i = 0; i < ListSeleccAsigRevisionTec.size(); i++) {
                            EntRev.setCodAvaluo(ListSeleccAsigRevisionTec.get(i).getCodAvaluo());
                            EntRev.setCodEmpl(EnRevAnaliT.getCodEmpl());
                            EntRev.InserCambioAnalist(3, mBSesion.codigoMiSesion());
                        }
                        this.ListAnalistRevTec = new ArrayList<>();
                        this.ListAnalistRevTec = EntRev.ConsultAllAvaluos(3, CodProEnt);
                        this.CodProEnt = 0;
                        Llamar(1, "PF('DlgAnalistaTecnico').hide()");
                        mbTodero.setMens("Se ha realizado la asignacion del analista correctamente");
                        mbTodero.info();
                    }
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cambioAnalista()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para consulta las matriculas asociadas al avaluo
     *
     * @author Jhojan Stiven Rodriguez
     * @return ArrayList Con las matriculas que trae de consulta de la base de
     * datos
     */
    public ArrayList<SelectItem> cargListMatricula() {
        try {
            Iterator<LogAvaluo> Ite;
            ListMatricula = new ArrayList<>();
            Ite = Ava.AvaluoMatriculas(EntrRevAvaluosPeritos.getCodAvaluo(), EntrRevAvaluosPeritos.getCodNBien()).iterator();
            while (Ite.hasNext()) {
                LogAvaluo MBAvaMat = Ite.next();
                this.ListMatricula.add(new SelectItem(MBAvaMat.getMatricula(), MBAvaMat.getMatricula()));
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".cargListMatricula()' causado por: " + e.getMessage());
            mbTodero.error();
        }
        this.ListZonasConst = new ArrayList<>();
        NumConstrucc = 0;
        MetroCuadrado = "";
        MetroCuadrado = "";
        TipoMedida = "";
        ValorConstr = "";
        return ListMatricula;

    }

    /**
     * Metodo para limpieza de variables
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void clearBean() {
        try {
            ListSeleccAsigEntrega = null;
            ListEntreg = new ArrayList<>();
            ListSeleccionPerito = new ArrayList<>();
            ListSelectEmpRevJuri = new ArrayList<>();
            ListSelectEmpRevTec = new ArrayList<>();
            ListAsignadoRevGeneral = new ArrayList<>();
            ListAsignadoRevJuri = new ArrayList<>();
            LisAsignadotRevTec = new ArrayList<>();
            LisArcRegla = new ArrayList<>();
            //Listas para realizar los check list
            CargaCheckEntrega = new ArrayList<>();
            CargaCheckRevJur = new ArrayList<>();
            CargaCheckRevTec = new ArrayList<>();
            CargaSelecCheckEnt = new ArrayList<>();
            CargaSelecCheckRevJur = new ArrayList<>();
            CargaSelecCheckRevTec = new ArrayList<>();

            DevolucionCompletada = false;

            //Listas para la validacion de dodumentacion segun el modulo
            ListCargueArchEntreg = new ArrayList<>();//Esta es para archivos subidos del avaluo en entrega
            CargaArchivosEnt = new ArrayList<>();
            PanelMuesArcEnt = false;
            NombreArchivo = null;
            CodArchivo = 0;
            //Variables para la seleccion del analista en el modulo de entrega de informe
            AnalistaJurid = "";
            AnalistaTecnic = "";
            CodEmpJurid = 0;
            CodEmpTecnic = 0;
            ObservaAva = "";
            //Muestra de informacion segun el Tipo de Avaluo
            PanelPredio = false;
            PanelMaquinaria = false;
            PanelEnser = false;
            Dat = null;
            //Para la confirmacion de seleccion de revisores
            OpcConfirmRevJuri = false;
            OpcConfirmRevTecn = false;
            //Para la confirmacion de seleccion de revisores
            OpcConfirmRevJuri = false;
            OpcConfirmRevTecn = false;
            OpcConfirmAvaluo = false;
            OpcConfirmDevolu = false;
            OpcConfirmPeritEmail = false;
            OpcExistEmail = false;
            VistoBuenoRevision = false;

            //Variables para enviar por correo la informacion al avaluador
            CargaPerito = new ArrayList<>();
            ObserDevolucion = "";
            codAvaluador = "";
            codTipoDevolucion = "";
            fechaEsperadaCorreccion = null;
            TipoProcesoER = "";

            this.OpcionArchivo = false;
            esperarAprobación = false;
            OpcConfirmModificAvaluador = false;
            codAvaluadorModif = "";
            observacionesModificacionesInfo = "";
            CargaModifSeleccionados = new ArrayList<>();

            if (ListArcInformModificacion != null) {
                for (int i = 0; i <= ListArcInformModificacion.length - 1; i++) {
                    ListArcInformModificacion[i] = null;
                }
                this.OpcionArchivo = false;
            }

            opcionRadioPropietario = "";
            limpiarPropietario();
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".clearBean()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que permite ejecutar sentencia javascript o resetear una tabla
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Op int que diferencia que proceso se hara : 1= ejecutar
     * javascript, 2= reseteo de tablas
     * @param Cadena Strig que contiene la instruccion que se quiere ejecutar
     * @since 01-06-2015
     */
    public void Llamar(int Op, String Cadena) {
        try {
            if (Op == 1) {
                RequestContext.getCurrentInstance().execute(Cadena);
            } else if (Op == 2) {
                DataTable DatTabla = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent(Cadena);
                DatTabla.reset();
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".Llamar()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta los avaluos devueltos
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param proceso int utilizado para cargar la devolucion asi: 1: entrega 2:
     * rev juridica 3: rev tecnica 4: rev tec y jurid (general)
     * @since 01-06-2015
     */
    public void consultaAvaluosDevoluc(int proceso) {
        try {
            if (proceso == 1) {
                ListEntregDevoluc = new ArrayList<>();
                ListEntregDevoluc = EntRev.ConsultAvaluosDevuel(1, mBSesion.codigoMiSesion());
            }
            if (proceso == 2) {
                ListDevolucRevJuri = new ArrayList<>();
                ListDevolucRevJuri = EntRev.ConsultAvaluosDevuel(2, mBSesion.codigoMiSesion());
            }
            if (proceso == 3) {
                ListDevolucRevTec = new ArrayList<>();
                ListDevolucRevTec = EntRev.ConsultAvaluosDevuel(3, mBSesion.codigoMiSesion());
            }
            if (proceso == 4) {
                ListDevolucRevGeneral = new ArrayList<>();
                ListDevolucRevGeneral = EntRev.ConsultAvaluosDevuel(4, mBSesion.codigoMiSesion());
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaAvaluosDevoluc()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que consulta la cantidad de registros que se tengan demepdiendo de
     * cada modulo, rol y empleado, esto se muestra como numeros al lado de los
     * titulos de cada submodulo de entrega y revision
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void consultaTotalRegistrosTablas() {
        try {

            //Numeros para entrega
            EntRev = new LogEntregaYRevision();
            NumeroMisEntregados = EntRev.consultaNumerosEntrega(1, mBSesion.codigoMiSesion());
            NumeroActividades = EntRev.consultaNumerosEntrega(2, mBSesion.codigoMiSesion());
            NumeroPendientesImprimir = EntRev.consultaNumerosEntrega(3, mBSesion.codigoMiSesion());
            NumeroPendientesImprimirAprobado = EntRev.consultaNumerosEntrega(6, mBSesion.codigoMiSesion());
            NumeroAprobados = EntRev.consultaNumerosEntrega(4, mBSesion.codigoMiSesion());
            NumeroAvalDevueltosEntrega = EntRev.consultaNumerosEntrega(5, mBSesion.codigoMiSesion());

            //Numeros para revision
            NumeroMisAsignadosRevGeneral = EntRev.consultaNumerosRevision(1, mBSesion.codigoMiSesion());
            NumeroMisAsignadosRevJurid = EntRev.consultaNumerosRevision(2, mBSesion.codigoMiSesion());
            NumeroMisAsignadosRevTecnic = EntRev.consultaNumerosRevision(3, mBSesion.codigoMiSesion());
            NumeroDevolucionesReviGeneral = EntRev.consultaNumerosRevision(4, mBSesion.codigoMiSesion());
            NumeroDevolucionesReviJurid = EntRev.consultaNumerosRevision(5, mBSesion.codigoMiSesion());
            NumeroDevolucionesReviTecnic = EntRev.consultaNumerosRevision(6, mBSesion.codigoMiSesion());
            NumeroPendientesAprobar = EntRev.consultaNumerosRevision(7, mBSesion.codigoMiSesion());

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaTotalRegistrosTablas()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que valida y agrega la información ingresada de las zonas de
     * construcción cuando el tipo del un avaluo es un predio
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void validarZonas() {
        try {
            if ("".equals(this.Matricula)) {
                mbTodero.setMens("No ha elegido matrícula ");
                mbTodero.warn();
            } else if (NumConstrucc == 0) {
                mbTodero.setMens("No ha seleccionado tipo de zona construida");
                mbTodero.warn();
            } else if ("".equals(MetroCuadrado)) {
                mbTodero.setMens("No se ha ingresado el área");
                mbTodero.warn();
            } else if ("".equals(ValorConstr)) {
                mbTodero.setMens("No se ha ingresado el valor");
                mbTodero.warn();
            } else if ("".equals(TipoMedida)) {
                mbTodero.setMens("No se ha seleccionado una medida");
                mbTodero.warn();
            } else {
                DecimalFormat formatea = new DecimalFormat("###,###.##");
                DecimalFormat formateador = new DecimalFormat("###,###.##");
                // Cargar las areas construidas dentro de la lista 
                EntRev = new LogEntregaYRevision();
                EntRev.setMatricula(Matricula);
                //Ingresar el nombre de la construccion que se selecciono
                EntRev.setCodZona(NumConstrucc);
                for (int i = 0; i < ListConstruccion.size(); i++) {
                    if (NumConstrucc == (int) ListConstruccion.get(i).getValue()) {
                        EntRev.setNomZona(ListConstruccion.get(i).getLabel());
                    }
                }
                //Ingresar el nombre del tipo de medida que se selecciono
                EntRev.setCodMedic(Integer.parseInt(TipoMedida));
                for (int i = 0; i < ListMedicion.size(); i++) {
                    if (Integer.parseInt(TipoMedida) == (int) ListMedicion.get(i).getValue()) {
                        EntRev.setNomMedic(ListMedicion.get(i).getLabel());
                    }
                }
                EntRev.setAreaConst(MetroCuadrado);
                EntRev.setValorConst(ValorConstr);
                EntRev.setNumberZona(ListZonasConst.size() + 1);
                double R = (Double.parseDouble(MetroCuadrado) * Double.parseDouble(ValorConstr.replace(",", "")));
                //EntRev.setSubTotArea(formatea.format(R));
                EntRev.setSubTotArea(formatea.format(R));
                ListZonasConst.add(EntRev);

                String cosa = "";
                double valor = 0;

                //For que me llena la informacion de total en suma
                for (int i = 0; i < ListZonasConst.size(); i++) {
                    System.out.println("LISTA: " + ListZonasConst.get(i).getSubTotArea());
                    cosa = ListZonasConst.get(i).getSubTotArea().replace(".", "");
                    cosa = cosa.replace(",", ".");
                    System.out.println("LISTA1: " + cosa);
                    valor = valor + Double.parseDouble(cosa);
                    System.out.println("LISTA2: " + valor);
                    SumTotal += valor;
                }

                //Limpieza de las variables
                Matricula = "";
                NumConstrucc = 0;
                MetroCuadrado = "";
                ValorConstr = "";
                TipoMedida = "";
            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".validarZonas()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo para remover item de listas de zonas de construcción, cuando el
     * avalúo es un predio
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-10-2015
     */
    public void removerZon() {

        try {
            for (int i = 0; i < ListZonasConst.size(); i++) {
                if (EntRev.getNumberZona() == ListZonasConst.get(i).getNumberZona()) {
                    ListZonasConst.remove(i);
                }
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".removerZon()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que carga los avaluos que presenten una actividad o recordatorio
     * pendiente por completar
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-10-2015
     */
    public void consultaAvaluosConActiviYRecor() {
        try {
            mbTodero.resetTable("FRMActividadesYRecordEntrega:ActiviRecordTable");
            ListActividadesYRecord = new ArrayList<>();
            EntRev = new LogEntregaYRevision();
            this.ListActividadesYRecord = EntRev.ConsultasActiviYRecordat(mBSesion.codigoMiSesion());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaAvaluosConActiviYRecor()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que limpia la opcion del propietario
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 15-10-2015
     */
    public void limpiarPropietario() {
        try {
            NombrePropietario = "";
            NumDocPropietario = "";
            ClieConta = new LogCliente();
            EntiConta = new LogEntidad();
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".limpiarPropietario()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que habilita muestra los paneles de propietario si es el
     * propietario como cliente, como entidad u otro
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-06-2015
     */
    public void mostrarInfoPropietario() {
        try {
            estadoLabelsClienteEnti = false;
            switch (opcionRadioPropietario) {
                case "Cliente":
                    limpiarPropietario();
                    String telefono = "";
                    int codCiudad = 0;
                    String lbciudad = "";
                    String lbdepto = "";
                    if ("".equals(mBCliente.getNombreCliente1()) && "".equals(mBCliente.getNumeroDoccliente1())) {
                        mbTodero.setMens("Debe llenar la información del cliente");
                        mbTodero.warn();
                    } else {
                        CargClientesSeleccPropiet.clear();
                        LogCliente Clie = new LogCliente();
                        Dat = Clie.ConsulCodCli(mBCliente.getNumeroDoccliente1());
                        if (Dat.next()) {
                            telefono = Dat.getString("telefono_cliente");
                            codCiudad = Dat.getInt("Fk_Cod_Ciudad");
                            lbciudad = Dat.getString("Nombre_Ciudad");
                            lbdepto = Dat.getString("Nombre_Departamento");
                        }
                        Conexion.Conexion.CloseCon();

                        Clie.setNumeroDoccliente(mBCliente.getNumeroDoccliente1());
                        Clie.setNombreCliente(mBCliente.getNombreCliente1());
                        Clie.setTelefonoCliente(telefono);
                        Clie.setFK_CodCiudad(codCiudad);
                        Clie.setCiudadCliente(lbciudad);
                        Clie.setDeptoCliente(lbdepto);
                        CargClientesSeleccPropiet.add(Clie);

                        if (mBCliente.isEstadocajasAgregarF1()) {
                            Clie = new LogCliente();
                            Dat = Clie.ConsulCodCli(mBCliente.getNumeroDoccliente2());
                            if (Dat.next()) {
                                telefono = Dat.getString("telefono_cliente");
                                codCiudad = Dat.getInt("Fk_Cod_Ciudad");
                                lbciudad = Dat.getString("Nombre_Ciudad");
                                lbdepto = Dat.getString("Nombre_Departamento");
                            }
                            Clie.setNumeroDoccliente(mBCliente.getNumeroDoccliente2());
                            Clie.setNombreCliente(mBCliente.getNombreCliente2());
                            Clie.setTelefonoCliente(telefono);
                            Clie.setFK_CodCiudad(codCiudad);
                            Clie.setCiudadCliente(lbciudad);
                            Clie.setDeptoCliente(lbdepto);
                            CargClientesSeleccPropiet.add(Clie);
                        }
                        if (mBCliente.isEstadocajasAgregarF2()) {
                            Clie = new LogCliente();
                            Dat = Clie.ConsulCodCli(mBCliente.getNumeroDoccliente3());
                            if (Dat.next()) {
                                telefono = Dat.getString("telefono_cliente");
                                codCiudad = Dat.getInt("Fk_Cod_Ciudad");
                                lbciudad = Dat.getString("Nombre_Ciudad");
                                lbdepto = Dat.getString("Nombre_Departamento");
                            }
                            Conexion.Conexion.CloseCon();
                            Clie.setNumeroDoccliente(mBCliente.getNumeroDoccliente3());
                            Clie.setNombreCliente(mBCliente.getNombreCliente3());
                            Clie.setTelefonoCliente(telefono);
                            Clie.setFK_CodCiudad(codCiudad);
                            Clie.setCiudadCliente(lbciudad);
                            Clie.setDeptoCliente(lbdepto);
                            CargClientesSeleccPropiet.add(Clie);
                        }
                        if (mBCliente.isEstadocajasAgregarF3()) {
                            Clie = new LogCliente();
                            Dat = Clie.ConsulCodCli(mBCliente.getNumeroDoccliente4());
                            if (Dat.next()) {
                                telefono = Dat.getString("telefono_cliente");
                                codCiudad = Dat.getInt("Fk_Cod_Ciudad");
                                lbciudad = Dat.getString("Nombre_Ciudad");
                                lbdepto = Dat.getString("Nombre_Departamento");
                            }
                            Conexion.Conexion.CloseCon();
                            Clie.setNumeroDoccliente(mBCliente.getNumeroDoccliente4());
                            Clie.setNombreCliente(mBCliente.getNombreCliente4());
                            Clie.setTelefonoCliente(telefono);

                            Clie.setFK_CodCiudad(codCiudad);
                            Clie.setCiudadCliente(lbciudad);
                            Clie.setDeptoCliente(lbdepto);
                            CargClientesSeleccPropiet.add(Clie);
                        }
                        if (mBCliente.isEstadocajasAgregarF4()) {
                            Clie = new LogCliente();
                            Dat = Clie.ConsulCodCli(mBCliente.getNumeroDoccliente5());
                            if (Dat.next()) {
                                telefono = Dat.getString("telefono_cliente");
                                codCiudad = Dat.getInt("Fk_Cod_Ciudad");
                                lbciudad = Dat.getString("Nombre_Ciudad");
                                lbdepto = Dat.getString("Nombre_Departamento");
                            }
                            Conexion.Conexion.CloseCon();
                            Clie.setNumeroDoccliente(mBCliente.getNumeroDoccliente5());
                            Clie.setNombreCliente(mBCliente.getNombreCliente5());
                            Clie.setTelefonoCliente(telefono);

                            Clie.setFK_CodCiudad(codCiudad);
                            Clie.setCiudadCliente(lbciudad);
                            Clie.setDeptoCliente(lbdepto);
                            CargClientesSeleccPropiet.add(Clie);
                        }
                        RequestContext.getCurrentInstance().execute("PF('DlgSelectClieContacto').show()");
                    }
                    break;
                case "Entidad":
                    limpiarPropietario();
                    if ("".equals(mBEntidad.getCodEntidad1()) && "".equals(mBEntidad.getCodSucursal1()) && "".equals(mBEntidad.getCodAsesor1())) {
                        mbTodero.setMens("Debe llenar la información de la entidad");
                        mbTodero.warn();
                    } else {
                        CargEntidSeleccPropiet.clear();
                        EntiConta = null;
                        LogEntidad Entid = new LogEntidad();
                        Enti.setCodEntidad(Integer.parseInt(mBEntidad.getCodEntidad1()));
                        Enti.setCodSucursal(Integer.parseInt(mBEntidad.getCodSucursal1()));
                        Enti.setCodAsesor(Integer.parseInt(mBEntidad.getCodAsesor1()));
                        Dat = Enti.ConsulInfEntAsesor();
                        if (Dat.next()) {
                            Entid.setDocumentoEntidad(Dat.getString("Documento_entidad"));
                            Entid.setNombreEntidad(Dat.getString("nombre_entidad"));
                            Entid.setNombreOfic(Dat.getString("nombre_oficina"));
                            Entid.setTelefonoOfic(Dat.getString("telefono_oficina"));
                            Entid.setNombreAsesor(Dat.getString("nombre_asesor"));
                            Entid.setTelefonoAsesor(Dat.getString("telAse"));
                            Entid.setCodDeptoSucursal(Dat.getString("Fk_Cod_Ciudad"));
                            Entid.setNombreCiudadSucursal(Dat.getString("Nombre_Ciudad"));
                            Entid.setNombreDeptoSucursal(Dat.getString("Nombre_Departamento"));
                        }

                        CargEntidSeleccPropiet.add(Entid);
                        Conexion.Conexion.CloseCon();
                        if (mBEntidad.isEstadoAgregarFilasEntidad2()) {
                            Entid = new LogEntidad();
                            Enti.setCodEntidad(Integer.parseInt(mBEntidad.getCodEntidad2()));
                            Enti.setCodSucursal(Integer.parseInt(mBEntidad.getCodSucursal2()));
                            Enti.setCodAsesor(Integer.parseInt(mBEntidad.getCodAsesor2()));
                            Dat = Enti.ConsulInfEntAsesor();
                            if (Dat.next()) {
                                Entid.setDocumentoEntidad(Dat.getString("Documento_entidad"));
                                Entid.setNombreEntidad(Dat.getString("nombre_entidad"));
                                Entid.setNombreOfic(Dat.getString("nombre_oficina"));
                                Entid.setTelefonoOfic(Dat.getString("telefono_oficina"));
                                Entid.setNombreAsesor(Dat.getString("nombre_asesor"));
                                Entid.setTelefonoAsesor(Dat.getString("telAse"));
                                Entid.setCodDeptoSucursal(Dat.getString("Fk_Cod_Ciudad"));
                                Entid.setNombreCiudadSucursal(Dat.getString("Nombre_Ciudad"));
                                Entid.setNombreDeptoSucursal(Dat.getString("Nombre_Departamento"));
                            }
                            CargEntidSeleccPropiet.add(Entid);
                            Conexion.Conexion.CloseCon();
                        }
                        if (mBEntidad.isEstadoAgregarFilasEntidad3()) {
                            Entid = new LogEntidad();
                            Enti.setCodEntidad(Integer.parseInt(mBEntidad.getCodEntidad3()));
                            Enti.setCodSucursal(Integer.parseInt(mBEntidad.getCodSucursal3()));
                            Enti.setCodAsesor(Integer.parseInt(mBEntidad.getCodAsesor3()));
                            Dat = Enti.ConsulInfEntAsesor();
                            if (Dat.next()) {
                                Entid.setDocumentoEntidad(Dat.getString("Documento_entidad"));
                                Entid.setNombreEntidad(Dat.getString("nombre_entidad"));
                                Entid.setNombreOfic(Dat.getString("nombre_oficina"));
                                Entid.setTelefonoOfic(Dat.getString("telefono_oficina"));
                                Entid.setNombreAsesor(Dat.getString("nombre_asesor"));
                                Entid.setTelefonoAsesor(Dat.getString("telefono_asesor"));

                                Entid.setCodDeptoSucursal(Dat.getString("Fk_Cod_Ciudad"));
                                Entid.setNombreCiudadSucursal(Dat.getString("Nombre_Ciudad"));
                                Entid.setNombreDeptoSucursal(Dat.getString("Nombre_Departamento"));
                            }
                            CargEntidSeleccPropiet.add(Entid);
                            Conexion.Conexion.CloseCon();
                        }
                        if (mBEntidad.isEstadoAgregarFilasEntidad4()) {
                            Entid = new LogEntidad();
                            Enti.setCodEntidad(Integer.parseInt(mBEntidad.getCodEntidad4()));
                            Enti.setCodSucursal(Integer.parseInt(mBEntidad.getCodSucursal4()));
                            Enti.setCodAsesor(Integer.parseInt(mBEntidad.getCodAsesor4()));
                            Dat = Enti.ConsulInfEntAsesor();
                            if (Dat.next()) {
                                Entid.setDocumentoEntidad(Dat.getString("Documento_entidad"));
                                Entid.setNombreEntidad(Dat.getString("nombre_entidad"));
                                Entid.setNombreOfic(Dat.getString("nombre_oficina"));
                                Entid.setTelefonoOfic(Dat.getString("telefono_oficina"));
                                Entid.setNombreAsesor(Dat.getString("nombre_asesor"));
                                Entid.setTelefonoAsesor(Dat.getString("telefono_asesor"));

                                Entid.setCodDeptoSucursal(Dat.getString("Fk_Cod_Ciudad"));
                                Entid.setNombreCiudadSucursal(Dat.getString("Nombre_Ciudad"));
                                Entid.setNombreDeptoSucursal(Dat.getString("Nombre_Departamento"));
                            }
                            CargEntidSeleccPropiet.add(Entid);
                            Conexion.Conexion.CloseCon();
                        }
                        if (mBEntidad.isEstadoAgregarFilasEntidad5()) {
                            Entid = new LogEntidad();
                            Enti.setCodEntidad(Integer.parseInt(mBEntidad.getCodEntidad5()));
                            Enti.setCodSucursal(Integer.parseInt(mBEntidad.getCodSucursal5()));
                            Enti.setCodAsesor(Integer.parseInt(mBEntidad.getCodAsesor5()));
                            Dat = Enti.ConsulInfEntAsesor();
                            if (Dat.next()) {
                                Entid.setDocumentoEntidad(Dat.getString("Documento_entidad"));
                                Entid.setNombreEntidad(Dat.getString("nombre_entidad"));
                                Entid.setNombreOfic(Dat.getString("nombre_oficina"));
                                Entid.setTelefonoOfic(Dat.getString("telefono_oficina"));
                                Entid.setNombreAsesor(Dat.getString("nombre_asesor"));
                                Entid.setTelefonoAsesor(Dat.getString("telefono_asesor"));

                                Entid.setCodDeptoSucursal(Dat.getString("Fk_Cod_Ciudad"));
                                Entid.setNombreCiudadSucursal(Dat.getString("Nombre_Ciudad"));
                                Entid.setNombreDeptoSucursal(Dat.getString("Nombre_Departamento"));
                            }
                            CargEntidSeleccPropiet.add(Entid);
                            Conexion.Conexion.CloseCon();
                        }
                        RequestContext.getCurrentInstance().execute("PF('DlgSelectEntiContacto').show()");

                    }
                    break;
                case "Otro":
                    limpiarPropietario();
                    break;
            }
        } catch (NumberFormatException | SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".mostrarInfoPropietario()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que permite agregar un cliente perteneciente al avalúo como
     * propietario
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 15-10-2015
     */
    public void agregarPropietClie() {
        try {
            if (ClieConta == null) {
                mbTodero.setMens("Debe seleccionar un cliente");
                mbTodero.warn();
            } else {
                this.PanelPropietario = true;
                NumDocPropietario = ClieConta.getNumeroDoccliente();
                NombrePropietario = ClieConta.getNombreCliente();
                RequestContext.getCurrentInstance().execute("PF('DlgSelectClieContacto').hide()");
                estadoLabelsClienteEnti = true;
            }

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".agregarPropietClie()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que permite agregar una entidad perteneciente al avalúo como
     * propietario
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 15-10-2015
     */
    public void agregarPropietEnti() {
        try {
            if (EntiConta == null) {
                mbTodero.setMens("Debe seleccionar una entidad");
                mbTodero.warn();
            } else {
                this.PanelPropietario = true;
                NumDocPropietario = EntiConta.getDocumentoEntidad();
                NombrePropietario = EntiConta.getNombreEntidad();
                RequestContext.getCurrentInstance().execute("PF('DlgSelectEntiContacto').hide()");
                estadoLabelsClienteEnti = true;
            }

        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".agregarPropietEnti()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo para seleccionar el propietario
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @param Tipo int que contiene un valor para realizar diferentes procesos
     * sobre la opcion de propietario
     * @since 01-06-2015
     */
    public void selecPropietario(int Tipo) {
        try {
            switch (Tipo) {
                case 1://Para seleccionar informacion del propietario
                    if (Client == null) {
                        mbTodero.setMens("No ha seleccionado ningun registro de propietario");
                        mbTodero.warn();
                    } else {
                        this.PanelPropietario = true;
                        NumDocPropietario = Client.getNumDocPropietario();
                        NombrePropietario = Client.getNombrePropietario();
                        Llamar(1, "PF('DlgPropiet').hide()");
                    }
                    break;
                case 2://Para abrir el dialogo y crear la informacion del propietario
                    Llamar(1, "PF('DlgCrearProp').show()");
                    Llamar(1, "PF('DlgPropiet').hide()");
                    this.CodPropietario = 0;
                    this.NumDocPropietario = "";
                    this.NombrePropietario = "";
                    break;
                case 3://Para registrar la informacion del propietario
                    if ("".equals(this.NumDocPropietario)) {
                        mbTodero.setMens("Falta ingresar informacion del numero del documentos del propietario");
                        mbTodero.warn();
                    } else if ("".equals(this.NombrePropietario)) {
                        mbTodero.setMens("Falta el nombre del Propietario por Ingresar");
                        mbTodero.warn();
                    } else {
                        Client = new LogCliente();
                        Client.setNumDocPropietario(this.NumDocPropietario);
                        Client.setNombrePropietario(this.NombrePropietario);
                        Client.InsertPropietario(mBSesion.codigoMiSesion());
                        mbTodero.setMens("El Propietario ha sido registrado con exito");
                        mbTodero.info();
                        Cliente.setNumDocPropietario(this.NumDocPropietario);
                        Cliente.setNombrePropietario(this.NombrePropietario);
                        Llamar(1, "PF('DlgCrearProp').hide()");
                        PanelPropietario = true;
                    }
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".lim()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * FIN Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /*Nuevos Metodos para la Gestion de mis Devoluciones , realizadas por Revisor Tecnico y Juridico */
    public void consultaMisDevolu(int proceso) {
        try {
            // Lista para los avaluos de Revision Tecnica
            LogEntregaYRevision MisDevol = new LogEntregaYRevision();
            ListMisDevolucRevGeneral = MisDevol.ConsultaMisDevol(mBSesion.codigoMiSesion());
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaAvaluosDevoluc()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }
}
