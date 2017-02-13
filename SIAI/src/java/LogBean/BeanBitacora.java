package LogBean;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import Logic.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.primefaces.context.RequestContext;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso de la bitacora general de los
 * avalúos</b></p>
 *
 * @author Jhojan Stiven Rodriguez
 * @version 2.0.0
 * @since 27-07-2015 1.0.0
 *
 *
 */
@ManagedBean
@ViewScoped
public class BeanBitacora {

    /**
     * Variables tipo ResultSet que cargan la informacion consultada, que sera
     * mostrada en las tablas
     */
    private ResultSet DatAvaluoGeneral;
    private ResultSet DatSolicitanteAvaluo;
    private ResultSet DatAvaluoAnterior;
    private ResultSet DatAvaluador;
    private ResultSet DatObser;
    private ResultSet DatPreRadicionGeneral;
    private ResultSet DatAvaluoTemporal;
    private ResultSet DatAvalPreRadic;
    private ResultSet DatLineaTiempoRegistro;

    /**
     * Instancia de objetos tipo LogBitacora que obtienen los atributos y
     * metodos de la clase LogBitacora.java
     *
     * @see LogBitacora.java
     */
    LogBitacora bita = new LogBitacora();
    LogBitacora bit = new LogBitacora();

    /**
     * Instancia de objetos tipo LogRadicacion que obtienen los atributos y
     * metodos de la clase LogRadicacion.java
     *
     * @see LogRadicacion.java
     */
    LogRadicacion Rad = new LogRadicacion();

    /**
     * Instancia de objetos tipo LogPreRadicacion que obtienen los atributos y
     * metodos de la clase LogPreRadicacion.java
     *
     * @see LogPreRadicacion.java
     */
    LogPreRadicacion PreRad = new LogPreRadicacion();

    /**
     * Instancia de objetos tipo LogAvaluo que obtienen los atributos y metodos
     * de la clase LogBitacora.java
     *
     * @see LogAvaluo.java
     */
    LogAvaluo Ava = new LogAvaluo();

    /**
     * Variable tipo BeanRadicacion para traer los atributos y metodos de el
     * ManagedBean BeanRadicacion.java
     *
     *
     * @see BeanRadicacion.java
     */
    @ManagedProperty("#{MBRadicacion}")
    private BeanRadicacion mBRadicacion;

    /**
     * Variable tipo BeanTodero para traer los atributos y metodos de el
     * ManagedBean BeanTodero.java
     *
     *
     * @see BeanTodero.java
     */
    @ManagedProperty("#{MBTodero}")
    private BeanTodero mbTodero;

    /**
     * Variables de la tabla, formato, que se muestran en la parte superior
     */
    private String tipoProceso;
    private String codigoRegist;
    private String codigoPreRadicac;
    private String codigoavaluo;
    private boolean estadoInformacionAvaluo;
    private int codigoBien;
    private int codigoSeguimiento;

    private String productoEntidad;

    private String tipoAvaluo;

    private String solicitantePreradicacion;

    private String direccionInmbueble;
    private String ubicacionInmbueble;

    private String nombreContacto;
    private String telefonoContacto;
    private String celularContacto;

    private String enviarAvaluoA;

    /**
     * Variables del responsable de cada proceso, fechas tiempos y estados
     */
    private String NombreResponsableRegistro = "";
    private String DiaReg = "";
    private String MesReg = "";
    private String AñoReg = "";
    private String FechaInicioReg = "";
    private String FechaFinReg = "";
    private String NombreResponsableRecibePostReg = "";
    private String TiempoTranscurridoReg = "";
    private String EstadoReg = "";

    private String NombreResponsablePreRadica = "";
    private String DiaPre = "";
    private String MesPre = "";
    private String AñoPre = "";
    private String FechaInicioPre = "";
    private String FechaFinPre = "";
    private String NombreResponsableRecibePostPre = "";
    private String TiempoTranscurridoPreRad = "";
    private String EstadoPreRad = "";

    private String NombreResponsableRadica = "";
    private String DiaRad = "";
    private String MesRad = "";
    private String AñoRad = "";
    private String FechaInicioRad = "";
    private String FechaFinRad = "";
    private String NombreResponsableRecibePostRad = "";
    private String TiempoTranscurridoRad = "";
    private String EstadoRad = "";

    private String TiempoTranscurridoEnCartaAval = "";
    private String FechaCartaAvaluador = "";

    private String TiempoTranscurridoEnFechaCita = "";
    private String FechaCita = "";

    private String TiempoTranscurridoEnConfirmaCita = "";
    private String FechaConfirmCita = "";

    private String NombreResponsableSeguimiento = "";
    private String DiaSegui = "";
    private String MesSegui = "";
    private String AñoSegui = "";
    private String FechaInicioSeg = "";
    private String FechaFinSeg = "";
    private String NombreResponsableRecibePostSeg = "";

    private String TiempoTranscurridoEnEntrega = "";
    private String FechaEntrega = "";

    private String NombreResponsableRevJuridica = "";
    private String DiaRevJur = "";
    private String MesRevJur = "";
    private String AñoRevJur = "";
    private String FechaInicioRevJur = "";
    private String FechaFinRevJur = "";
    private String NombreResponsableRecibePostRevJur = "";
    private String TiempoTranscurridoRevJur = "";
    private String EstadoRevJur = "";

    private String NombreResponsableRevTecnica = "";
    private String DiaRevTecn = "";
    private String MesRevTecn = "";
    private String AñoRevTecn = "";
    private String FechaInicioRevTecn = "";
    private String FechaFinRevTecn = "";
    private String NombreResponsableRecibePostRevTecn = "";
    private String TiempoTranscurridoRevTec = "";
    private String EstadoRevTecn = "";

    private String TiempoTranscurridoImpresion = "";
    private String FechaImpresion = "";

    /**
     * Variables de cliente
     */
    private String DocumentoCliente;
    private String NombreCliente;
    private String MailCliente;
    private String TelefonoCliente;
    private String Tipo;
    private String Funcionario;
    private String Correo;
    private String Direccion;

    /**
     * Variables de la entidad
     */
    private String DocumentoEntidad;
    private String NombreEntidad;

    /**
     * Variables de la sucursal
     */
    private String CodigoOficina;
    private String NombreSucursal;
    private String TelefonoSucursal;

    /**
     * Variables del asesor
     */
    private String NombreAsesor;
    private String CargoAsesor;
    private String MailAsesor;
    private String TelefonoAsesor;
    private String DireccionAsesor;

    /**
     * Variables del avaluo anterior
     */
    private boolean estadoAvalAnterior = false;
    private int CodAvalAnterior;
    private String ValorAvalAnterior;
    private String FechaAvalAnterior;
    private String AvaluadorAvalAnterior;

    /**
     * Variables del avaluador seleccionado
     */
    private String nombreAvaluador;
    private String fechaAsignacionAvaluador;

    /**
     * variables tipo List
     *
     */
    /**
     * Lista para el responsable de Registro y PreRadica
     */
    private List<LogBitacora> ListaResponsables = new ArrayList<>();

    /**
     * Lista de clientes temporales
     */
    private List<LogBitacora> ListaClientesTemporales = new ArrayList<>();

    /**
     * Lista de Observaciones
     */
    private List<LogPreRadicacion> listObserBita = new ArrayList<>();

    /**
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @return
     */
    public BeanTodero getMbTodero() {
        return mbTodero;
    }

    public void setMbTodero(BeanTodero mbTodero) {
        this.mbTodero = mbTodero;
    }

    public BeanRadicacion getmBRadicacion() {
        return mBRadicacion;
    }

    public void setmBRadicacion(BeanRadicacion mBRadicacion) {
        this.mBRadicacion = mBRadicacion;
    }

    public String getTipoProceso() {
        return tipoProceso;
    }

    public void setTipoProceso(String tipoProceso) {
        this.tipoProceso = tipoProceso;
    }

    public String getCodigoRegist() {
        return codigoRegist;
    }

    public void setCodigoRegist(String codigoRegist) {
        this.codigoRegist = codigoRegist;
    }

    public String getCodigoPreRadicac() {
        return codigoPreRadicac;
    }

    public void setCodigoPreRadicac(String codigoPreRadicac) {
        this.codigoPreRadicac = codigoPreRadicac;
    }

    public String getCodigoavaluo() {
        return codigoavaluo;
    }

    public void setCodigoavaluo(String codigoavaluo) {
        this.codigoavaluo = codigoavaluo;
    }

    public boolean isEstadoInformacionAvaluo() {
        return estadoInformacionAvaluo;
    }

    public void setEstadoInformacionAvaluo(boolean estadoInformacionAvaluo) {
        this.estadoInformacionAvaluo = estadoInformacionAvaluo;
    }

    public int getCodigoBien() {
        return codigoBien;
    }

    public void setCodigoBien(int codigoBien) {
        this.codigoBien = codigoBien;
    }

    public int getCodigoSeguimiento() {
        return codigoSeguimiento;
    }

    public void setCodigoSeguimiento(int codigoSeguimiento) {
        this.codigoSeguimiento = codigoSeguimiento;
    }

    public String getProductoEntidad() {
        return productoEntidad;
    }

    public void setProductoEntidad(String productoEntidad) {
        this.productoEntidad = productoEntidad;
    }

    public String getTipoAvaluo() {
        return tipoAvaluo;
    }

    public void setTipoAvaluo(String tipoAvaluo) {
        this.tipoAvaluo = tipoAvaluo;
    }

    public String getSolicitantePreradicacion() {
        return solicitantePreradicacion;
    }

    public void setSolicitantePreradicacion(String solicitantePreradicacion) {
        this.solicitantePreradicacion = solicitantePreradicacion;
    }

    public String getDireccionInmbueble() {
        return direccionInmbueble;
    }

    public void setDireccionInmbueble(String direccionInmbueble) {
        this.direccionInmbueble = direccionInmbueble;
    }

    public String getUbicacionInmbueble() {
        return ubicacionInmbueble;
    }

    public void setUbicacionInmbueble(String ubicacionInmbueble) {
        this.ubicacionInmbueble = ubicacionInmbueble;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getTelefonoContacto() {
        return telefonoContacto;
    }

    public void setTelefonoContacto(String telefonoContacto) {
        this.telefonoContacto = telefonoContacto;
    }

    public String getCelularContacto() {
        return celularContacto;
    }

    public void setCelularContacto(String celularContacto) {
        this.celularContacto = celularContacto;
    }

    public String getEnviarAvaluoA() {
        return enviarAvaluoA;
    }

    public void setEnviarAvaluoA(String enviarAvaluoA) {
        this.enviarAvaluoA = enviarAvaluoA;
    }

    public String getNombreResponsableRegistro() {
        return NombreResponsableRegistro;
    }

    public void setNombreResponsableRegistro(String NombreResponsableRegistro) {
        this.NombreResponsableRegistro = NombreResponsableRegistro;
    }

    public String getDiaReg() {
        return DiaReg;
    }

    public void setDiaReg(String DiaReg) {
        this.DiaReg = DiaReg;
    }

    public String getMesReg() {
        return MesReg;
    }

    public void setMesReg(String MesReg) {
        this.MesReg = MesReg;
    }

    public String getAñoReg() {
        return AñoReg;
    }

    public void setAñoReg(String AñoReg) {
        this.AñoReg = AñoReg;
    }

    public String getFechaInicioReg() {
        return FechaInicioReg;
    }

    public void setFechaInicioReg(String FechaInicioReg) {
        this.FechaInicioReg = FechaInicioReg;
    }

    public String getFechaFinReg() {
        return FechaFinReg;
    }

    public void setFechaFinReg(String FechaFinReg) {
        this.FechaFinReg = FechaFinReg;
    }

    public String getNombreResponsableRecibePostReg() {
        return NombreResponsableRecibePostReg;
    }

    public void setNombreResponsableRecibePostReg(String NombreResponsableRecibePostReg) {
        this.NombreResponsableRecibePostReg = NombreResponsableRecibePostReg;
    }

    public String getTiempoTranscurridoReg() {
        return TiempoTranscurridoReg;
    }

    public void setTiempoTranscurridoReg(String TiempoTranscurridoReg) {
        this.TiempoTranscurridoReg = TiempoTranscurridoReg;
    }

    public String getEstadoReg() {
        return EstadoReg;
    }

    public void setEstadoReg(String EstadoReg) {
        this.EstadoReg = EstadoReg;
    }

    public String getNombreResponsablePreRadica() {
        return NombreResponsablePreRadica;
    }

    public void setNombreResponsablePreRadica(String NombreResponsablePreRadica) {
        this.NombreResponsablePreRadica = NombreResponsablePreRadica;
    }

    public String getDiaPre() {
        return DiaPre;
    }

    public void setDiaPre(String DiaPre) {
        this.DiaPre = DiaPre;
    }

    public String getMesPre() {
        return MesPre;
    }

    public void setMesPre(String MesPre) {
        this.MesPre = MesPre;
    }

    public String getAñoPre() {
        return AñoPre;
    }

    public void setAñoPre(String AñoPre) {
        this.AñoPre = AñoPre;
    }

    public String getFechaInicioPre() {
        return FechaInicioPre;
    }

    public void setFechaInicioPre(String FechaInicioPre) {
        this.FechaInicioPre = FechaInicioPre;
    }

    public String getFechaFinPre() {
        return FechaFinPre;
    }

    public void setFechaFinPre(String FechaFinPre) {
        this.FechaFinPre = FechaFinPre;
    }

    public String getNombreResponsableRecibePostPre() {
        return NombreResponsableRecibePostPre;
    }

    public void setNombreResponsableRecibePostPre(String NombreResponsableRecibePostPre) {
        this.NombreResponsableRecibePostPre = NombreResponsableRecibePostPre;
    }

    public String getTiempoTranscurridoPreRad() {
        return TiempoTranscurridoPreRad;
    }

    public void setTiempoTranscurridoPreRad(String TiempoTranscurridoPreRad) {
        this.TiempoTranscurridoPreRad = TiempoTranscurridoPreRad;
    }

    public String getEstadoPreRad() {
        return EstadoPreRad;
    }

    public void setEstadoPreRad(String EstadoPreRad) {
        this.EstadoPreRad = EstadoPreRad;
    }

    public String getNombreResponsableRadica() {
        return NombreResponsableRadica;
    }

    public void setNombreResponsableRadica(String NombreResponsableRadica) {
        this.NombreResponsableRadica = NombreResponsableRadica;
    }

    public String getDiaRad() {
        return DiaRad;
    }

    public void setDiaRad(String DiaRad) {
        this.DiaRad = DiaRad;
    }

    public String getMesRad() {
        return MesRad;
    }

    public void setMesRad(String MesRad) {
        this.MesRad = MesRad;
    }

    public String getAñoRad() {
        return AñoRad;
    }

    public void setAñoRad(String AñoRad) {
        this.AñoRad = AñoRad;
    }

    public String getFechaInicioRad() {
        return FechaInicioRad;
    }

    public void setFechaInicioRad(String FechaInicioRad) {
        this.FechaInicioRad = FechaInicioRad;
    }

    public String getFechaFinRad() {
        return FechaFinRad;
    }

    public void setFechaFinRad(String FechaFinRad) {
        this.FechaFinRad = FechaFinRad;
    }

    public String getNombreResponsableRecibePostRad() {
        return NombreResponsableRecibePostRad;
    }

    public void setNombreResponsableRecibePostRad(String NombreResponsableRecibePostRad) {
        this.NombreResponsableRecibePostRad = NombreResponsableRecibePostRad;
    }

    public String getTiempoTranscurridoRad() {
        return TiempoTranscurridoRad;
    }

    public void setTiempoTranscurridoRad(String TiempoTranscurridoRad) {
        this.TiempoTranscurridoRad = TiempoTranscurridoRad;
    }

    public String getEstadoRad() {
        return EstadoRad;
    }

    public void setEstadoRad(String EstadoRad) {
        this.EstadoRad = EstadoRad;
    }

    public String getTiempoTranscurridoEnCartaAval() {
        return TiempoTranscurridoEnCartaAval;
    }

    public void setTiempoTranscurridoEnCartaAval(String TiempoTranscurridoEnCartaAval) {
        this.TiempoTranscurridoEnCartaAval = TiempoTranscurridoEnCartaAval;
    }

    public String getFechaCartaAvaluador() {
        return FechaCartaAvaluador;
    }

    public void setFechaCartaAvaluador(String FechaCartaAvaluador) {
        this.FechaCartaAvaluador = FechaCartaAvaluador;
    }

    public String getTiempoTranscurridoEnFechaCita() {
        return TiempoTranscurridoEnFechaCita;
    }

    public void setTiempoTranscurridoEnFechaCita(String TiempoTranscurridoEnFechaCita) {
        this.TiempoTranscurridoEnFechaCita = TiempoTranscurridoEnFechaCita;
    }

    public String getFechaCita() {
        return FechaCita;
    }

    public void setFechaCita(String FechaCita) {
        this.FechaCita = FechaCita;
    }

    public String getTiempoTranscurridoEnConfirmaCita() {
        return TiempoTranscurridoEnConfirmaCita;
    }

    public void setTiempoTranscurridoEnConfirmaCita(String TiempoTranscurridoEnConfirmaCita) {
        this.TiempoTranscurridoEnConfirmaCita = TiempoTranscurridoEnConfirmaCita;
    }

    public String getFechaConfirmCita() {
        return FechaConfirmCita;
    }

    public void setFechaConfirmCita(String FechaConfirmCita) {
        this.FechaConfirmCita = FechaConfirmCita;
    }

    public String getNombreResponsableSeguimiento() {
        return NombreResponsableSeguimiento;
    }

    public void setNombreResponsableSeguimiento(String NombreResponsableSeguimiento) {
        this.NombreResponsableSeguimiento = NombreResponsableSeguimiento;
    }

    public String getDiaSegui() {
        return DiaSegui;
    }

    public void setDiaSegui(String DiaSegui) {
        this.DiaSegui = DiaSegui;
    }

    public String getMesSegui() {
        return MesSegui;
    }

    public void setMesSegui(String MesSegui) {
        this.MesSegui = MesSegui;
    }

    public String getAñoSegui() {
        return AñoSegui;
    }

    public void setAñoSegui(String AñoSegui) {
        this.AñoSegui = AñoSegui;
    }

    public String getFechaInicioSeg() {
        return FechaInicioSeg;
    }

    public void setFechaInicioSeg(String FechaInicioSeg) {
        this.FechaInicioSeg = FechaInicioSeg;
    }

    public String getFechaFinSeg() {
        return FechaFinSeg;
    }

    public void setFechaFinSeg(String FechaFinSeg) {
        this.FechaFinSeg = FechaFinSeg;
    }

    public String getNombreResponsableRecibePostSeg() {
        return NombreResponsableRecibePostSeg;
    }

    public void setNombreResponsableRecibePostSeg(String NombreResponsableRecibePostSeg) {
        this.NombreResponsableRecibePostSeg = NombreResponsableRecibePostSeg;
    }

    public String getTiempoTranscurridoEnEntrega() {
        return TiempoTranscurridoEnEntrega;
    }

    public void setTiempoTranscurridoEnEntrega(String TiempoTranscurridoEnEntrega) {
        this.TiempoTranscurridoEnEntrega = TiempoTranscurridoEnEntrega;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String FechaEntrega) {
        this.FechaEntrega = FechaEntrega;
    }

    public String getNombreResponsableRevJuridica() {
        return NombreResponsableRevJuridica;
    }

    public void setNombreResponsableRevJuridica(String NombreResponsableRevJuridica) {
        this.NombreResponsableRevJuridica = NombreResponsableRevJuridica;
    }

    public String getDiaRevJur() {
        return DiaRevJur;
    }

    public void setDiaRevJur(String DiaRevJur) {
        this.DiaRevJur = DiaRevJur;
    }

    public String getMesRevJur() {
        return MesRevJur;
    }

    public void setMesRevJur(String MesRevJur) {
        this.MesRevJur = MesRevJur;
    }

    public String getAñoRevJur() {
        return AñoRevJur;
    }

    public void setAñoRevJur(String AñoRevJur) {
        this.AñoRevJur = AñoRevJur;
    }

    public String getFechaInicioRevJur() {
        return FechaInicioRevJur;
    }

    public void setFechaInicioRevJur(String FechaInicioRevJur) {
        this.FechaInicioRevJur = FechaInicioRevJur;
    }

    public String getFechaFinRevJur() {
        return FechaFinRevJur;
    }

    public void setFechaFinRevJur(String FechaFinRevJur) {
        this.FechaFinRevJur = FechaFinRevJur;
    }

    public String getNombreResponsableRecibePostRevJur() {
        return NombreResponsableRecibePostRevJur;
    }

    public void setNombreResponsableRecibePostRevJur(String NombreResponsableRecibePostRevJur) {
        this.NombreResponsableRecibePostRevJur = NombreResponsableRecibePostRevJur;
    }

    public String getTiempoTranscurridoRevJur() {
        return TiempoTranscurridoRevJur;
    }

    public void setTiempoTranscurridoRevJur(String TiempoTranscurridoRevJur) {
        this.TiempoTranscurridoRevJur = TiempoTranscurridoRevJur;
    }

    public String getEstadoRevJur() {
        return EstadoRevJur;
    }

    public void setEstadoRevJur(String EstadoRevJur) {
        this.EstadoRevJur = EstadoRevJur;
    }

    public String getNombreResponsableRevTecnica() {
        return NombreResponsableRevTecnica;
    }

    public void setNombreResponsableRevTecnica(String NombreResponsableRevTecnica) {
        this.NombreResponsableRevTecnica = NombreResponsableRevTecnica;
    }

    public String getDiaRevTecn() {
        return DiaRevTecn;
    }

    public void setDiaRevTecn(String DiaRevTecn) {
        this.DiaRevTecn = DiaRevTecn;
    }

    public String getMesRevTecn() {
        return MesRevTecn;
    }

    public void setMesRevTecn(String MesRevTecn) {
        this.MesRevTecn = MesRevTecn;
    }

    public String getAñoRevTecn() {
        return AñoRevTecn;
    }

    public void setAñoRevTecn(String AñoRevTecn) {
        this.AñoRevTecn = AñoRevTecn;
    }

    public String getFechaInicioRevTecn() {
        return FechaInicioRevTecn;
    }

    public void setFechaInicioRevTecn(String FechaInicioRevTecn) {
        this.FechaInicioRevTecn = FechaInicioRevTecn;
    }

    public String getFechaFinRevTecn() {
        return FechaFinRevTecn;
    }

    public void setFechaFinRevTecn(String FechaFinRevTecn) {
        this.FechaFinRevTecn = FechaFinRevTecn;
    }

    public String getNombreResponsableRecibePostRevTecn() {
        return NombreResponsableRecibePostRevTecn;
    }

    public void setNombreResponsableRecibePostRevTecn(String NombreResponsableRecibePostRevTecn) {
        this.NombreResponsableRecibePostRevTecn = NombreResponsableRecibePostRevTecn;
    }

    public String getTiempoTranscurridoRevTec() {
        return TiempoTranscurridoRevTec;
    }

    public void setTiempoTranscurridoRevTec(String TiempoTranscurridoRevTec) {
        this.TiempoTranscurridoRevTec = TiempoTranscurridoRevTec;
    }

    public String getEstadoRevTecn() {
        return EstadoRevTecn;
    }

    public void setEstadoRevTecn(String EstadoRevTecn) {
        this.EstadoRevTecn = EstadoRevTecn;
    }

    public String getTiempoTranscurridoImpresion() {
        return TiempoTranscurridoImpresion;
    }

    public void setTiempoTranscurridoImpresion(String TiempoTranscurridoImpresion) {
        this.TiempoTranscurridoImpresion = TiempoTranscurridoImpresion;
    }

    public String getFechaImpresion() {
        return FechaImpresion;
    }

    public void setFechaImpresion(String FechaImpresion) {
        this.FechaImpresion = FechaImpresion;
    }

    public String getDocumentoCliente() {
        return DocumentoCliente;
    }

    public void setDocumentoCliente(String DocumentoCliente) {
        this.DocumentoCliente = DocumentoCliente;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public String getMailCliente() {
        return MailCliente;
    }

    public void setMailCliente(String MailCliente) {
        this.MailCliente = MailCliente;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public String getTelefonoCliente() {
        return TelefonoCliente;
    }

    public void setTelefonoCliente(String TelefonoCliente) {
        this.TelefonoCliente = TelefonoCliente;
    }

    public String getFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(String Funcionario) {
        this.Funcionario = Funcionario;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getDireccion() {
        return Direccion;
    }

    public void setDireccion(String Direccion) {
        this.Direccion = Direccion;
    }

    public String getDocumentoEntidad() {
        return DocumentoEntidad;
    }

    public void setDocumentoEntidad(String DocumentoEntidad) {
        this.DocumentoEntidad = DocumentoEntidad;
    }

    public String getNombreEntidad() {
        return NombreEntidad;
    }

    public void setNombreEntidad(String NombreEntidad) {
        this.NombreEntidad = NombreEntidad;
    }

    public String getCodigoOficina() {
        return CodigoOficina;
    }

    public void setCodigoOficina(String CodigoOficina) {
        this.CodigoOficina = CodigoOficina;
    }

    public String getNombreSucursal() {
        return NombreSucursal;
    }

    public void setNombreSucursal(String NombreSucursal) {
        this.NombreSucursal = NombreSucursal;
    }

    public String getTelefonoSucursal() {
        return TelefonoSucursal;
    }

    public void setTelefonoSucursal(String TelefonoSucursal) {
        this.TelefonoSucursal = TelefonoSucursal;
    }

    public String getNombreAsesor() {
        return NombreAsesor;
    }

    public void setNombreAsesor(String NombreAsesor) {
        this.NombreAsesor = NombreAsesor;
    }

    public String getCargoAsesor() {
        return CargoAsesor;
    }

    public void setCargoAsesor(String CargoAsesor) {
        this.CargoAsesor = CargoAsesor;
    }

    public String getMailAsesor() {
        return MailAsesor;
    }

    public void setMailAsesor(String MailAsesor) {
        this.MailAsesor = MailAsesor;
    }

    public String getTelefonoAsesor() {
        return TelefonoAsesor;
    }

    public void setTelefonoAsesor(String TelefonoAsesor) {
        this.TelefonoAsesor = TelefonoAsesor;
    }

    public String getDireccionAsesor() {
        return DireccionAsesor;
    }

    public void setDireccionAsesor(String DireccionAsesor) {
        this.DireccionAsesor = DireccionAsesor;
    }

    public ResultSet getDatAvaluoAnterior() {
        return DatAvaluoAnterior;
    }

    public void setDatAvaluoAnterior(ResultSet DatAvaluoAnterior) {
        this.DatAvaluoAnterior = DatAvaluoAnterior;
    }

    public boolean isEstadoAvalAnterior() {
        return estadoAvalAnterior;
    }

    public void setEstadoAvalAnterior(boolean estadoAvalAnterior) {
        this.estadoAvalAnterior = estadoAvalAnterior;
    }

    public int getCodAvalAnterior() {
        return CodAvalAnterior;
    }

    public void setCodAvalAnterior(int CodAvalAnterior) {
        this.CodAvalAnterior = CodAvalAnterior;
    }

    public String getValorAvalAnterior() {
        return ValorAvalAnterior;
    }

    public void setValorAvalAnterior(String ValorAvalAnterior) {
        this.ValorAvalAnterior = ValorAvalAnterior;
    }

    public String getFechaAvalAnterior() {
        return FechaAvalAnterior;
    }

    public void setFechaAvalAnterior(String FechaAvalAnterior) {
        this.FechaAvalAnterior = FechaAvalAnterior;
    }

    public String getAvaluadorAvalAnterior() {
        return AvaluadorAvalAnterior;
    }

    public void setAvaluadorAvalAnterior(String AvaluadorAvalAnterior) {
        this.AvaluadorAvalAnterior = AvaluadorAvalAnterior;
    }

    public String getNombreAvaluador() {
        return nombreAvaluador;
    }

    public void setNombreAvaluador(String nombreAvaluador) {
        this.nombreAvaluador = nombreAvaluador;
    }

    public String getFechaAsignacionAvaluador() {
        return fechaAsignacionAvaluador;
    }

    public void setFechaAsignacionAvaluador(String fechaAsignacionAvaluador) {
        this.fechaAsignacionAvaluador = fechaAsignacionAvaluador;
    }

    public List<LogBitacora> getListaClientesTemporales() {
        return ListaClientesTemporales;
    }

    public void setListaClientesTemporales(List<LogBitacora> ListaClientesTemporales) {
        this.ListaClientesTemporales = ListaClientesTemporales;
    }

    public List<LogPreRadicacion> getListObserBita() {
        return listObserBita;
    }

    public void setListObserBita(List<LogPreRadicacion> listObserBita) {
        this.listObserBita = listObserBita;
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
     * Metodo que realiza toda la consulta del avaluo, para cargarlo en la tabla
     * del formato de control y seguimiento
     *
     * @author Jhojan Stiven Rodriguez
     * @param proceso int que recibe un valor para saber si buscar por registro,
     * pre-radicacion o radicacion o numero de avaluo
     * @since 01-05-2015
     */
    public void consultaAvaluo(int proceso) {
        /*
         proceso:
         1 = Registro
         2 = Pre-radicacion
         3 = Avaluo
         */
        try {
            LimpiarBitacora();
            if (("".equals(codigoRegist) || codigoRegist == null) && proceso == 1) {
                mbTodero.setMens("Debe llenar el campo 'Numero del solicitud'");
                mbTodero.warn();
            } else if (("".equals(codigoPreRadicac) || codigoPreRadicac == null) && proceso == 2) {
                mbTodero.setMens("Debe llenar el campo 'Numero del pre-radicación'");
                mbTodero.warn();

            } else if (("".equals(codigoavaluo) || codigoavaluo == null) && proceso == 3) {
                mbTodero.setMens("Debe llenar el campo 'Numero del avaluo'");
                mbTodero.warn();
            } else {

                DatAvaluoGeneral = bita.consultaAvaluoGeneral(proceso, (proceso == 2) ? codigoPreRadicac : codigoavaluo);
                if (DatAvaluoGeneral.next()) {
                    estadoInformacionAvaluo = true;
                    codigoavaluo = DatAvaluoGeneral.getString("Cod_Avaluo");
                    codigoBien = DatAvaluoGeneral.getInt("Numero_Bien_Seguimiento");
                    codigoSeguimiento = DatAvaluoGeneral.getInt("Cod_Seguimiento");

                    productoEntidad = DatAvaluoGeneral.getString("ProductoEntidad");

                    tipoAvaluo = DatAvaluoGeneral.getString("Tipo_Avaluo");

                    solicitantePreradicacion = DatAvaluoGeneral.getString("Solicitante_PreRadica");

                    direccionInmbueble = DatAvaluoGeneral.getString("Direccion_Imbueble");
                    ubicacionInmbueble = DatAvaluoGeneral.getString("Ubicacion_Inmueble");

                    nombreContacto = DatAvaluoGeneral.getString("Nombre_Contacto");
                    telefonoContacto = DatAvaluoGeneral.getString("Telefono_Contacto");
                    celularContacto = DatAvaluoGeneral.getString("Celular_Contacto");

                    enviarAvaluoA = DatAvaluoGeneral.getString("Resultado_Parametro");

                    switch (solicitantePreradicacion) {
                        case "Cliente":
                            DatSolicitanteAvaluo = bita.consultaSolicitanteAvaluo(codigoavaluo, 1);
                            if (DatSolicitanteAvaluo.next()) {
                                DocumentoCliente = DatSolicitanteAvaluo.getString("Numero_DocCliente");
                                NombreCliente = DatSolicitanteAvaluo.getString("Nombre_Cliente");
                                MailCliente = DatSolicitanteAvaluo.getString("Mail_Cliente");
                                TelefonoCliente = DatSolicitanteAvaluo.getString("Telefono_Cliente");
                            }
                            Conexion.Conexion.CloseCon();
                            break;
                        case "Entidad":
                            DatSolicitanteAvaluo = bita.consultaSolicitanteAvaluo(codigoavaluo, 2);
                            if (DatSolicitanteAvaluo.next()) {
                                DocumentoEntidad = DatSolicitanteAvaluo.getString("Documento_Entidad");
                                NombreEntidad = DatSolicitanteAvaluo.getString("Entidad");
                                MailAsesor = DatSolicitanteAvaluo.getString("Mail_Asesor");
                                TelefonoAsesor = DatSolicitanteAvaluo.getString("Telefono_Asesor");
                            }
                            Conexion.Conexion.CloseCon();
                            break;
                    }

                    //Responsables de cada proceso
                    this.ListaResponsables = bita.consultaProcesos(codigoavaluo, 1);
                    if (!ListaResponsables.isEmpty()) {
                        NombreResponsableRegistro = ListaResponsables.get(0).getNombreResponsable();
                        DiaReg = ListaResponsables.get(0).getDia();
                        MesReg = ListaResponsables.get(0).getMes();
                        AñoReg = ListaResponsables.get(0).getAño();
                        FechaInicioReg = ListaResponsables.get(0).getFecha_Inicio();
                        FechaFinReg = ListaResponsables.get(0).getFecha_FIn();
                        NombreResponsableRecibePostReg = ListaResponsables.get(0).getNombreRecibe();

                        NombreResponsablePreRadica = ListaResponsables.get(1).getNombreResponsable();
                        DiaPre = ListaResponsables.get(1).getDia();
                        MesPre = ListaResponsables.get(1).getMes();
                        AñoPre = ListaResponsables.get(1).getAño();
                        FechaInicioPre = ListaResponsables.get(1).getFecha_Inicio();
                        FechaFinPre = ListaResponsables.get(1).getFecha_FIn();
                        NombreResponsableRecibePostPre = ListaResponsables.get(1).getNombreRecibe();

                        NombreResponsableRadica = ListaResponsables.get(2).getNombreResponsable();
                        DiaRad = ListaResponsables.get(2).getDia();
                        MesRad = ListaResponsables.get(2).getMes();
                        AñoRad = ListaResponsables.get(2).getAño();
                        FechaInicioRad = ListaResponsables.get(2).getFecha_Inicio();
                        FechaFinRad = ListaResponsables.get(2).getFecha_FIn();
                        NombreResponsableRecibePostRad = ListaResponsables.get(2).getNombreRecibe();

                        NombreResponsableSeguimiento = ListaResponsables.get(3).getNombreResponsable();
                        DiaSegui = ListaResponsables.get(3).getDia();
                        MesSegui = ListaResponsables.get(3).getMes();
                        AñoSegui = ListaResponsables.get(3).getAño();
                        FechaInicioSeg = ListaResponsables.get(3).getFecha_Inicio();
                        FechaFinSeg = ListaResponsables.get(3).getFecha_FIn();
                        NombreResponsableRecibePostSeg = ListaResponsables.get(3).getNombreRecibe();

                        if (ListaResponsables.size() > 4) {
                            NombreResponsableRevJuridica = ListaResponsables.get(4).getNombreResponsable();
                            DiaRevJur = ListaResponsables.get(4).getDia();
                            MesRevJur = ListaResponsables.get(4).getMes();
                            AñoRevJur = ListaResponsables.get(4).getAño();
                            FechaInicioRevJur = ListaResponsables.get(4).getFecha_Inicio();
                            FechaFinRevJur = ListaResponsables.get(4).getFecha_FIn();
                            NombreResponsableRecibePostRevJur = ListaResponsables.get(4).getNombreRecibe();
                        }
                        if (ListaResponsables.size() > 5) {
                            NombreResponsableRevTecnica = ListaResponsables.get(5).getNombreResponsable();
                            DiaRevTecn = ListaResponsables.get(5).getDia();
                            MesRevTecn = ListaResponsables.get(5).getMes();
                            AñoRevTecn = ListaResponsables.get(5).getAño();
                            FechaInicioRevTecn = ListaResponsables.get(5).getFecha_Inicio();
                            FechaFinRevTecn = ListaResponsables.get(5).getFecha_FIn();
                            NombreResponsableRecibePostRevTecn = ListaResponsables.get(5).getNombreRecibe();
                        }

                    }

                    //Avaluo anterior (si existe o no)
                    DatAvaluoAnterior = bita.consultaAvaluoAnterior(codigoavaluo);

                    if (DatAvaluoAnterior.next()) {
                        estadoAvalAnterior = true;
                        CodAvalAnterior = DatAvaluoAnterior.getInt("CodAvalAnter");
                        FechaAvalAnterior = DatAvaluoAnterior.getString("Fecha_Radicado");
                        ValorAvalAnterior = DatAvaluoAnterior.getString("ValorAvaluo");
                        AvaluadorAvalAnterior = DatAvaluoAnterior.getString("Avaluador_AvalAnter");
                    } else {
                        estadoAvalAnterior = false;
                    }
                    Conexion.Conexion.CloseCon();
                    //Avaluuador seleccionado para el avaluo
                    DatAvaluador = bita.consultaAvaluadorAvaluo(codigoavaluo);

                    if (DatAvaluador.next()) {

                        nombreAvaluador = DatAvaluador.getString("Avaluador_Aval");
                        fechaAsignacionAvaluador = DatAvaluador.getString("Fecha_Asignacion");

                    }
                    Conexion.Conexion.CloseCon();
                    DatObser = Rad.consultaObservaciones("obRad",codigoavaluo, codigoSeguimiento);

                    while (DatObser.next()) {
                        LogPreRadicacion PreRadObs = new LogPreRadicacion();
                        PreRadObs.setObservacionPreRadica(DatObser.getString("Obser"));
                        PreRadObs.setFechaObsPreRadica(DatObser.getString("Fecha"));
                        PreRadObs.setEmpleObservacionPreRadica(DatObser.getString("empleado"));
                        listObserBita.add(PreRadObs);
                    }
                    Conexion.Conexion.CloseCon();
                    //Linea de tiempo
                    DatLineaTiempoRegistro = bita.consultaRegistro_PreRadicacion_Radicacion(codigoavaluo, codigoBien, 3);

                    if (DatLineaTiempoRegistro.next()) {
                        FechaInicioReg = DatLineaTiempoRegistro.getString("Fecha_Registro");
                        EstadoReg = DatLineaTiempoRegistro.getString("EstadoPR");
                        //TiempoTranscurridoReg = DatLineaTiempoRegistro.getString("TiempoDesdeRegistro");

                        FechaInicioPre = DatLineaTiempoRegistro.getString("Fecha_PreRadica");
                        TiempoTranscurridoPreRad = DatLineaTiempoRegistro.getString("TiempoPreRadicacion");
                        EstadoPreRad = DatLineaTiempoRegistro.getString("EstadoPR");

                        FechaInicioRad = DatLineaTiempoRegistro.getString("Fecha_Radicado");
                        TiempoTranscurridoRad = DatLineaTiempoRegistro.getString("TiempoEnRadicacion");
                        EstadoRad = DatLineaTiempoRegistro.getString("EstadoRad");

                        FechaCartaAvaluador = DatLineaTiempoRegistro.getString("Fecha_Carta");
                        TiempoTranscurridoEnCartaAval = DatLineaTiempoRegistro.getString("TiempoEnCartaAvaluador");

                        FechaCita = DatLineaTiempoRegistro.getString("Fecha_Cita");
                        TiempoTranscurridoEnFechaCita = DatLineaTiempoRegistro.getString("TiempoDeCita");

                        FechaConfirmCita = DatLineaTiempoRegistro.getString("Visto_Cita");
                        TiempoTranscurridoEnConfirmaCita = DatLineaTiempoRegistro.getString("TiempoDeConfirmarCita");

                        FechaEntrega = DatLineaTiempoRegistro.getString("Fecha_Entrega");
                        TiempoTranscurridoEnEntrega = DatLineaTiempoRegistro.getString("TiempoEnEntrega");

                        FechaFinRevJur = DatLineaTiempoRegistro.getString("Fecha_Juridica");
                        TiempoTranscurridoRevJur = DatLineaTiempoRegistro.getString("TiempoEnRevJurid");

                        FechaFinRevTecn = DatLineaTiempoRegistro.getString("Fecha_Tecnica");
                        TiempoTranscurridoRevTec = DatLineaTiempoRegistro.getString("TiempoEnRevTec");

                        FechaImpresion = DatLineaTiempoRegistro.getString("Fecha_Impresion");
                        TiempoTranscurridoImpresion = DatLineaTiempoRegistro.getString("TiempoEnImpresion");

                    }
                    Conexion.Conexion.CloseCon();
                } else {
                    mbTodero.setMens("No se encontro información para el numero de avalúo ingresado");
                    mbTodero.warn();
                    estadoInformacionAvaluo = false;
                }

                //this.ListaClientesTemporales = bit.consultaNombreClienteTemporal(codigoavaluo);
            }

        } catch (SQLException e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".consultaAvaluo()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    /**
     * Metodo que limpia toda la informacion y las variables de la bitacora de
     * avalúos
     *
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void LimpiarBitacora() {

        try {
            //Codigos y responsables
            estadoInformacionAvaluo = false;
            codigoSeguimiento = 0;
            codigoBien = 0;
            this.NombreResponsableRegistro = "";
            this.NombreResponsablePreRadica = "";
            this.NombreResponsableRadica = "";
            this.NombreResponsableSeguimiento = "";

            //prod_entidad y tip_avaluo
            productoEntidad = "";
            tipoAvaluo = "";

            //Cliente / entidad solicitante, inmueble, contacto
            //Cliente
            NombreCliente = "";
            TelefonoCliente = "";
            MailCliente = "";

            //Entidad
            NombreEntidad = "";
            TelefonoAsesor = "";
            MailAsesor = "";

            //inmueble
            direccionInmbueble = "";
            ubicacionInmbueble = "";

            //Contacto
            nombreContacto = "";
            telefonoContacto = "";
            celularContacto = "";

            /*nombre del responsable, fechas, quien recibe, de cada proceso*/
            NombreResponsableRegistro = "";
            DiaReg = "";
            MesReg = "";
            AñoReg = "";
            FechaInicioReg = "";
            FechaFinReg = "";
            NombreResponsableRecibePostReg = "";

            NombreResponsablePreRadica = "";
            DiaPre = "";
            MesPre = "";
            AñoPre = "";
            FechaInicioPre = "";
            FechaFinPre = "";
            NombreResponsableRecibePostPre = "";

            NombreResponsableRadica = "";
            DiaRad = "";
            MesRad = "";
            AñoRad = "";
            FechaInicioRad = "";
            FechaFinRad = "";
            NombreResponsableRecibePostRad = "";

            NombreResponsableSeguimiento = "";
            DiaSegui = "";
            MesSegui = "";
            AñoSegui = "";
            FechaInicioSeg = "";
            FechaFinSeg = "";
            NombreResponsableRecibePostSeg = "";

            FechaCartaAvaluador = "";
            TiempoTranscurridoEnCartaAval = "";

            FechaCita = "";
            TiempoTranscurridoEnFechaCita = "";

            FechaConfirmCita = "";
            TiempoTranscurridoEnConfirmaCita = "";

            FechaEntrega = "";
            TiempoTranscurridoEnEntrega = "";

            NombreResponsableRevJuridica = "";
            DiaRevJur = "";
            MesRevJur = "";
            AñoRevJur = "";
            FechaInicioRevJur = "";
            FechaFinRevJur = "";
            NombreResponsableRecibePostRevJur = "";

            NombreResponsableRevTecnica = "";
            DiaRevTecn = "";
            MesRevTecn = "";
            AñoRevTecn = "";
            FechaInicioRevTecn = "";
            FechaFinRevTecn = "";
            NombreResponsableRecibePostRevTecn = "";

            FechaImpresion = "";
            TiempoTranscurridoImpresion = "";

            //Envio del avaluo
            enviarAvaluoA = "";

            //Avaluo Anterior y avaluador seleccionado
            CodAvalAnterior = 0;
            FechaAvalAnterior = "";
            estadoAvalAnterior = false;
            ValorAvalAnterior = "";
            AvaluadorAvalAnterior = "";

            nombreAvaluador = "";
            fechaAsignacionAvaluador = "";

            listObserBita = new ArrayList<>();
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".LimpiarBitacora()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que limpia las variables de busqueda usadas en la bitacora de
     * avalúos
     *
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void limpiarFormato() {
        try {
            estadoInformacionAvaluo = false;
            codigoavaluo = "";
            codigoPreRadicac = "";
            codigoRegist = "";
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".LimpiarBitacora()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * Metodo que abre un dialogo en cada uno de los formularios que muestren la
     * informacion de los avaluos
     *
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void abrirdialogBitacora() {
        try {
            setTipoProceso("RadAval");
            setCodigoavaluo(String.valueOf(mBRadicacion.getCod_avaluo()));
            consultaAvaluo(3);
            setCodigoPreRadicac("");
            setCodigoRegist("");
            RequestContext.getCurrentInstance().execute("PF('DialogBitacoraAvaluo').show()");
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".abrirdialogBitacora()' causado por: " + e.getMessage());
            mbTodero.error();
        }

    }

    /**
     * FIN Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
}
