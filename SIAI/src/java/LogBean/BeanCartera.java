package LogBean;

import Logic.LogAdministracion;
import Logic.LogCartera;
import Logic.LogActivRecord;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.SelectEvent;

@ManagedBean(name = "MBCartera")
@ViewScoped
@SessionScoped
public class BeanCartera {

    /**
     * Variable tipo BeanTodero para traer los atributos y metodos de el
     * ManagedBean BeanTodero.java
     *
     *
     * @see BeanTodero.java
     */
    @ManagedProperty("#{MBTodero}")
    BeanTodero mBTodero;

    public BeanTodero getmBTodero() {
        return mBTodero;
    }

    public void setmBTodero(BeanTodero mBTodero) {
        this.mBTodero = mBTodero;
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

    @ManagedProperty("#{MBCliente}")
    private BeanCliente mBCliente;

    public BeanCliente getmBCliente() {
        return mBCliente;
    }

    public void setmBCliente(BeanCliente mBCliente) {
        this.mBCliente = mBCliente;
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

    @ManagedProperty("#{MBAdministacion}")
    private BeanAdministracion mBAdministacion;

    public BeanAdministracion getmBAdministacion() {
        return mBAdministacion;
    }

    private boolean OpcAjusteValor;
    private List<LogCartera> ListaImpuestosOrd = new ArrayList<>();
    /*Variable para el metodo mayor Valor a Cancelar*/
    public int mayorValor;
    public int tmpMayormeno;
    public String observMayorValor;
    private LogCartera selectedCar;
    //Cobro
    private String EstadoCobro = "";
    private String Valida = "";
    //Declaracion de Variables.
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
    List<LogCartera> ListTablaNotaCre = new ArrayList<>();
    //Lista para el registro de Nuevos Impuestos 
    List<LogCartera> listRgsImps = new ArrayList<>();
    //Valor de la Factura
    private int SelectFact;
    private String SelectImps;
    private double SelectTasa;
    private double SelectIca;
    private int CambImps;
    private String Cobrar = "Cobrar";
    private int codFactRecord;
    //Nuevos valores para edicion de la informacion
    private int modFact;
    private int modImps;
    private int modTasa;
    String Lbel = "";
    //Variable para cambiar estado de Combobox
    private boolean estdCmb;
    private boolean NameImpsto = false;
    private boolean ReciboConsig = false;
    private LogCartera Carte = new LogCartera();

    private String TipoPorFijo;
    public int Nombre = 0;
    public int valorConsig = 0;
    public int InfoClienteCartera = 0;
    public boolean estadoPago = false;
    private List<LogCartera> listaEstadoCartera = null;

    public int getTmpMayormeno() {
        return tmpMayormeno;
    }

    public void setTmpMayormeno(int tmpMayormeno) {
        this.tmpMayormeno = tmpMayormeno;
    }

    public boolean isOpcAjusteValor() {
        return OpcAjusteValor;
    }

    public void setOpcAjusteValor(boolean OpcAjusteValor) {
        this.OpcAjusteValor = OpcAjusteValor;
    }

    public List<LogCartera> getListaEstadoCartera() {
        return listaEstadoCartera;
    }

    public void setListaEstadoCartera(List<LogCartera> listaEstadoCartera) {
        this.listaEstadoCartera = listaEstadoCartera;
    }
    //esto es para grupos
    private List<LogCartera> listaGruposTotalIca = new ArrayList<>();

    public boolean isEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(boolean estadoPago) {
        this.estadoPago = estadoPago;
    }

    public List<LogCartera> getListaGruposTotalIca() {
        return listaGruposTotalIca;
    }

    public void setListaGruposTotalIca(List<LogCartera> listaGruposTotalIca) {
        this.listaGruposTotalIca = listaGruposTotalIca;
    }

    //Start
    private List<LogCartera> listaSaldoAFavor = null;
    private List<LogCartera> listaPendientesPorCobrar = null;
    private List<LogCartera> listaFactPagos = new ArrayList<>();
    private List<LogCartera> selectSaldoFavor = null;
    private List<LogCartera> selectPendiXCobrar = null;
    private List<LogCartera> TempEstados = new ArrayList<>();
    private List<LogCartera> selectFactPago = null;
    private List<LogCartera> listaImpuestosFacturas = new ArrayList<>();
    private List<LogCartera> listaValoresAnular = new ArrayList<>();
    private List<LogCartera> listaReprtFact = new ArrayList<>();
    private List<LogCartera> listaReprtTemp = new ArrayList<>();
    private List<LogCartera> SelectRegisNotaCre = null;
    private List<LogCartera> listaObservaciones = new ArrayList<>();
    private List<LogCartera> listaObservDetalle = new ArrayList<>();
    private ArrayList<SelectItem> ListImpsFact = new ArrayList<>();
    private ArrayList<LogCartera> ListTemp = new ArrayList<>();
    private List<LogCartera> selectDetPago = null;
    private ArrayList<SelectItem> ListImpsTasa = new ArrayList<>();
    private ArrayList<SelectItem> CargarFacturas = new ArrayList<>();
    private List<LogCartera> selectCJuridico = new ArrayList<>();
    private List<LogCartera> selectCCartera = new ArrayList<>();
    private List<LogCartera> selectObserFact = new ArrayList<>();
    private List<LogCartera> selectReprtPago = null;
    //Cargar la informacion correspondiente a los impuestos Aplicados a las Facturas
    private List<LogCartera> listaAplicaImpst = new ArrayList<>();
    private List<LogCartera> listCamImpst = new ArrayList<>();
    private List<LogCartera> listCamImpstemp = new ArrayList<>();
    private ArrayList<SelectItem> ListImpsIca = new ArrayList<>();

    LogCartera selectNotaCred = new LogCartera();
    private int validar = 0;
    LogCartera Carte1 = new LogCartera();
    LogCartera ModImp = new LogCartera();

    LogCartera Observ = new LogCartera();
    LogCartera DtlleAval = new LogCartera();
    LogCartera AdmRegNota = new LogCartera();
    LogCartera Imps = new LogCartera();
    LogCartera InfoCartera = new LogCartera();
    LogActivRecord ActiviRecor = new LogActivRecord();
    private String nombreCliente = "";
    private String fecha = "";
    private String fechaConsig = "";
    private long valorFactura = 0;
    private int icaAval = 0;
    private int icaAvaltmp = 0;
    private int valorAvaluo = 0;
    private int iva = 0;
    private int ica = 0;
    private int fuente = 0;
    private int reteIva = 0;
    private int abonos = 0;
    private int notasCredito = 0;
    private int saldoAPagar = 0;
    private int Tpagar = 0;
    private int codBanco = 0;
    private Date fechaPagos;
    private int CodRecibo;
    private int CodTipoPago;
    private int Abono;
    private long ValorInicialFcatura = 0;
    private String pasarJuridicoObser = "";
    private String pasarCCastigada = "";
    private int CodNota = 1;
    //Gestion Cartera
    //Insercion Notas Credito 
    //Variables:
    private int ValorNotaCre = 0;
    private int ValorAbono = 0;
    private int ValorRegnotaCre = 0;
    private String estadoPnlCart = "";
    private String ObservNotaCre = "";
    //Lista Seguimiento Cobro 
    private String observCobro = "";

    //Devolver estado de la factura
    private List<LogCartera> selectCmbioEstado = null;
    //Listas parta Gestion de Cartera
    private ArrayList<SelectItem> cargarRecibo = new ArrayList<>();
    private ArrayList<SelectItem> cargarBancos = new ArrayList<>();
    private ArrayList<SelectItem> cargarTipoPago = new ArrayList<>();
    //Lista para mostrar la informacion de las notas credito cargadas para las FACTURAS seleccionadas
    List<LogCartera> listConsNotaCreFac = new ArrayList<>();
    List<LogCartera> listConsNotaTemp = new ArrayList<>();
    List<LogCartera> ListNotaCre = new ArrayList<>();
    List<LogCartera> ListNotaAbonos = new ArrayList<>();
    List<LogCartera> ListImp = new ArrayList<>();
    //Lista para  mostrar el detalle del Pago , dependiendo de la factura seleccionada
    List<LogCartera> listaDetallePago = new ArrayList<>();
    List<LogCartera> listInfoAval = new ArrayList<>();
    private int ValorConsulta = 0;
    private int CountAbono = 0;
    private int VladcConIvaFact;
    private int VladcSinIvaFact;
    private int VladcConIvaAval;
    private int VladcSinIvaAval;
    private int Antcp;
    private int AntcpFact;
    private int Dscuento;
    private int Subtotal;
    private String NombreImpuesto;
    private int TasaImpuesto;
    private int PendtPorPagar;
    private int TotalDescFactAval;
    //Info Cliente 
    private String DocCliente;
    private String NitEntidad;
    private String TelEntidad;
    private String TelCliente;
    private String NombCliente;
    private String NombEnt;
    private String tipoAjuste;
    private String valorAjuste;
    private double ValorElectronico;
    //Variables para mostrar los porcentajes de cada impuesto aplicado
    private String lblliva = "";
    private String lblica = "";
    private String lblfuente = "";
    private String lblreteIva = "";
    private String lblIcaAval = "";
    private String lblIcaAvaltmp = "";
    private boolean radioUno;
    private boolean estTasaBmberos;
    public int CodAvaluo;
    public int CodSeg;
    public int NBien;
    public int SaldoFavor = 0;
    // Facturacion en Grupos 
    private List<LogCartera> listaInfoAvaluos = new ArrayList<>();
    public List<LogCartera> listaCiudad = new ArrayList<>();
    private List<LogCartera> listaCalculoIca = new ArrayList<>();
    private String EstadoCartera = "Ajuste Ica";
    private int Id_Pago;

    public int getTotalDescFactAval() {
        return TotalDescFactAval;
    }
    public void setTotalDescFactAval(int TotalDescFactAval) {
        this.TotalDescFactAval = TotalDescFactAval;
    }
     
    public boolean isEstTasaBmberos() {
        return estTasaBmberos;
    }
    public void setEstTasaBmberos(boolean estTasaBmberos) {
        this.estTasaBmberos = estTasaBmberos;
    }
    public int getSaldoFavor() {
        return SaldoFavor;
    }
    public void setSaldoFavor(int SaldoFavor) {
        this.SaldoFavor = SaldoFavor;
    }

    public int getId_Pago() {
        return Id_Pago;
    }

    public void setId_Pago(int Id_Pago) {
        this.Id_Pago = Id_Pago;
    }

    LogCartera DetalleAval = new LogCartera();

    public boolean EstIcaAval = false;

    private List<LogActivRecord> ListaInfoActivRecord = new ArrayList<>();

    public List<LogActivRecord> getListaInfoActivRecord() {
        return ListaInfoActivRecord;
    }

    public void setListaInfoActivRecord(List<LogActivRecord> ListaInfoActivRecord) {
        this.ListaInfoActivRecord = ListaInfoActivRecord;
    }

    public boolean isEstIcaAval() {
        return EstIcaAval;
    }

    public void setEstIcaAval(boolean EstIcaAval) {
        this.EstIcaAval = EstIcaAval;
    }

    public int getIcaAvaltmp() {
        return icaAvaltmp;
    }

    public void setIcaAvaltmp(int icaAvaltmp) {
        this.icaAvaltmp = icaAvaltmp;
    }

    public String getLblIcaAvaltmp() {
        return lblIcaAvaltmp;
    }

    public void setLblIcaAvaltmp(String lblIcaAvaltmp) {
        this.lblIcaAvaltmp = lblIcaAvaltmp;
    }

    public int getValorAvaluo() {
        return valorAvaluo;
    }

    public void setValorAvaluo(int valorAvaluo) {
        this.valorAvaluo = valorAvaluo;
    }

    public String getEstadoCartera() {
        return EstadoCartera;
    }

    public void setEstadoCartera(String EstadoCartera) {
        this.EstadoCartera = EstadoCartera;
    }

    // Inicio de Getters and Setters
    public String getEstadoCobro() {
        return EstadoCobro;
    }

    public void setEstadoCobro(String EstadoCobro) {
        this.EstadoCobro = EstadoCobro;
    }

    public int getIcaAval() {
        return icaAval;
    }

    public void setIcaAval(int icaAval) {
        this.icaAval = icaAval;
    }

    public String getLblIcaAval() {
        return lblIcaAval;
    }

    public void setLblIcaAval(String lblIcaAval) {
        this.lblIcaAval = lblIcaAval;
    }

    public LogCartera getDtlleAval() {
        return DtlleAval;
    }

    public void setDtlleAval(LogCartera DtlleAval) {
        this.DtlleAval = DtlleAval;
    }

    public String getValida() {
        return Valida;
    }

    public void setValida(String Valida) {
        this.Valida = Valida;
    }

    public ArrayList<LogCartera> getListTemp() {
        return ListTemp;
    }

    public void setListTemp(ArrayList<LogCartera> ListTemp) {
        this.ListTemp = ListTemp;
    }

    public double getSelectIca() {
        return SelectIca;
    }

    public void setSelectIca(double SelectIca) {
        this.SelectIca = SelectIca;
    }

    public ArrayList<SelectItem> getListImpsIca() {
        return ListImpsIca;
    }

    public void setListImpsIca(ArrayList<SelectItem> ListImpsIca) {
        this.ListImpsIca = ListImpsIca;
    }

    public List<LogCartera> getListaCiudad() {
        return listaCiudad;
    }

    public void setListaCiudad(List<LogCartera> listaCiudad) {
        this.listaCiudad = listaCiudad;
    }

    public List<LogCartera> getListaInfoAvaluos() {
        return listaInfoAvaluos;
    }

    public void setListaInfoAvaluos(List<LogCartera> listaInfoAvaluos) {
        this.listaInfoAvaluos = listaInfoAvaluos;
    }

    public List<LogCartera> getListInfoAval() {
        return listInfoAval;
    }

    public void setListInfoAval(List<LogCartera> listInfoAval) {
        this.listInfoAval = listInfoAval;
    }

    public String getPasarCCastigada() {
        return pasarCCastigada;
    }

    public void setPasarCCastigada(String pasarCCastigada) {
        this.pasarCCastigada = pasarCCastigada;
    }

    public List<LogCartera> getSelectCCartera() {
        return selectCCartera;
    }

    public void setSelectCCartera(List<LogCartera> selectCCartera) {
        this.selectCCartera = selectCCartera;
    }

    public List<LogCartera> getTempEstados() {
        return TempEstados;
    }

    public void setTempEstados(List<LogCartera> TempEstados) {
        this.TempEstados = TempEstados;
    }

    public int getCodSeg() {
        return CodSeg;
    }

    public String getCobrar() {
        return Cobrar;
    }

    public void setCobrar(String Cobrar) {
        this.Cobrar = Cobrar;
    }

    public void setCodSeg(int CodSeg) {
        this.CodSeg = CodSeg;
    }

    public int getNBien() {
        return NBien;
    }

    public void setNBien(int NBien) {
        this.NBien = NBien;
    }

    public int getCodAvaluo() {
        return CodAvaluo;
    }

    public void setCodAvaluo(int CodAvaluo) {
        this.CodAvaluo = CodAvaluo;
    }

    public int getInfoClienteCartera() {
        return InfoClienteCartera;
    }

    public void setInfoClienteCartera(int InfoClienteCartera) {
        this.InfoClienteCartera = InfoClienteCartera;
    }

    public String getFechaConsig() {
        return fechaConsig;
    }

    public void setFechaConsig(String fechaConsig) {
        this.fechaConsig = fechaConsig;
    }

    public List<LogCartera> getListaValoresAnular() {
        return listaValoresAnular;
    }

    public void setListaValoresAnular(List<LogCartera> listaValoresAnular) {
        this.listaValoresAnular = listaValoresAnular;
    }

    public List<LogCartera> getSelectDetPago() {
        return selectDetPago;
    }

    public void setSelectDetPago(List<LogCartera> selectDetPago) {
        this.selectDetPago = selectDetPago;
    }

    public int getTpagar() {
        return Tpagar;
    }

    public void setTpagar(int Tpagar) {
        this.Tpagar = Tpagar;
    }

    public List<LogCartera> getListaDetallePago() {
        return listaDetallePago;
    }

    public void setListaDetallePago(List<LogCartera> listaDetallePago) {
        this.listaDetallePago = listaDetallePago;
    }

    public List<LogCartera> getSelectFactPago() {
        return selectFactPago;
    }

    public void setSelectFactPago(List<LogCartera> selectFactPago) {
        this.selectFactPago = selectFactPago;
    }

    public List<LogCartera> getListaFactPagos() {
        return listaFactPagos;
    }

    public void setListaFactPagos(List<LogCartera> listaFactPagos) {
        this.listaFactPagos = listaFactPagos;
    }

    public int getCodFactRecord() {
        return codFactRecord;
    }

    public void setCodFactRecord(int codFactRecord) {
        this.codFactRecord = codFactRecord;
    }

    public LogActivRecord getActiviRecor() {
        return ActiviRecor;
    }

    public void setActiviRecor(LogActivRecord ActiviRecor) {
        this.ActiviRecor = ActiviRecor;
    }

    public int getValorConsig() {
        return valorConsig;
    }

    public void setValorConsig(int valorConsig) {
        this.valorConsig = Abono;
    }

    public int getNombre() {
        return Nombre;
    }

    public void setNombre(int Nombre) {
        this.Nombre = Nombre;
    }

    public boolean isReciboConsig() {
        return ReciboConsig;
    }

    public void setReciboConsig(boolean ReciboConsig) {
        this.ReciboConsig = ReciboConsig;
    }

    public boolean isEstdCmb() {
        return estdCmb;
    }

    public boolean isNameImpsto() {
        return NameImpsto;
    }

    public void setNameImpsto(boolean NameImpsto) {
        this.NameImpsto = NameImpsto;
    }

    public void setEstdCmb(boolean estdCmb) {
        this.estdCmb = estdCmb;
    }

    public String getLbel() {
        return Lbel;
    }

    public void setLbel(String Lbel) {
        this.Lbel = Lbel;
    }

    public String getTipoPorFijo() {
        return TipoPorFijo;
    }

    public void setTipoPorFijo(String TipoPorFijo) {
        this.TipoPorFijo = TipoPorFijo;
    }

    private String ObservCamImp = "";

    public int getModFact() {
        return modFact;
    }

    public void setModFact(int modFact) {
        this.modFact = modFact;
    }

    public int getModImps() {
        return modImps;
    }

    public void setModImps(int modImps) {
        this.modImps = modImps;
    }

    public int getModTasa() {
        return modTasa;
    }

    public void setModTasa(int modTasa) {
        this.modTasa = modTasa;
    }

    public String getObservCamImp() {
        return ObservCamImp;
    }

    public void setObservCamImp(String ObservCamImp) {
        this.ObservCamImp = ObservCamImp;
    }

    public List<LogCartera> getListRgsImps() {
        return listRgsImps;
    }

    public void setListRgsImps(List<LogCartera> listRgsImps) {
        this.listRgsImps = listRgsImps;
    }

    public List<LogCartera> getListTablaNotaCre() {
        return ListTablaNotaCre;
    }

    public void setListTablaNotaCre(List<LogCartera> ListTablaNotaCre) {
        this.ListTablaNotaCre = ListTablaNotaCre;
    }

    public double getSelectTasa() {
        return SelectTasa;
    }

    public void setSelectTasa(double SelectTasa) {
        this.SelectTasa = SelectTasa;
    }

    public int getCambImps() {
        return CambImps;
    }

    public void setCambImps(int CambImps) {
        this.CambImps = CambImps;
    }

    public String getSelectImps() {
        return SelectImps;
    }

    public void setSelectImps(String SelectImps) {
        this.SelectImps = SelectImps;
    }

    public LogCartera getAdmRegNota() {
        return AdmRegNota;
    }

    public void setAdmRegNota(LogCartera AdmRegNota) {
        this.AdmRegNota = AdmRegNota;
    }

    public int getSelectFact() {
        return SelectFact;
    }

    public void setSelectFact(int SelectFact) {
        this.SelectFact = SelectFact;
    }

    public long getValorInicialFcatura() {
        return ValorInicialFcatura;
    }

    public void setValorInicialFcatura(long ValorInicialFcatura) {
        this.ValorInicialFcatura = ValorInicialFcatura;
    }

    public boolean isRadioUno() {
        return radioUno;
    }

    public void setRadioUno(boolean radioUno) {
        this.radioUno = radioUno;
    }

    public String getLblliva() {
        return lblliva;
    }

    public void setLblliva(String lblliva) {
        this.lblliva = lblliva;
    }

    public String getLblica() {
        return lblica;
    }

    public void setLblica(String lblica) {
        this.lblica = lblica;
    }

    public String getLblfuente() {
        return lblfuente;
    }

    public void setLblfuente(String lblfuente) {
        this.lblfuente = lblfuente;
    }

    public String getLblreteIva() {
        return lblreteIva;
    }

    public void setLblreteIva(String lblreteIva) {
        this.lblreteIva = lblreteIva;
    }

    public double getValorElectronico() {
        return ValorElectronico;
    }

    public void setValorElectronico(double ValorElectronico) {
        this.ValorElectronico = ValorElectronico;
    }

    public ArrayList<SelectItem> getCargarTipoPago() {
        return cargarTipoPago;
    }

    public void setCargarTipoPago(ArrayList<SelectItem> cargarTipoPago) {
        this.cargarTipoPago = cargarTipoPago;
    }

    public int getCodTipoPago() {
        return CodTipoPago;
    }

    public void setCodTipoPago(int CodTipoPago) {
        this.CodTipoPago = CodTipoPago;
    }

    public LogCartera getObserv() {
        return Observ;
    }

    public List<LogCartera> getListaObservDetalle() {
        return listaObservDetalle;
    }

    public void setListaObservDetalle(List<LogCartera> listaObservDetalle) {
        this.listaObservDetalle = listaObservDetalle;
    }

    public void setObserv(LogCartera Observ) {
        this.Observ = Observ;
    }

    public List<LogCartera> getListImp() {
        return ListImp;
    }

    public void setListImp(List<LogCartera> ListImp) {
        this.ListImp = ListImp;
    }

    public String getTipoAjuste() {
        return tipoAjuste;
    }

    public void setTipoAjuste(String tipoAjuste) {
        this.tipoAjuste = tipoAjuste;
    }

    public String getValorAjuste() {
        return valorAjuste;
    }

    public void setValorAjuste(String valorAjuste) {
        this.valorAjuste = valorAjuste;
    }

    public List<LogCartera> getListaReprtFact() {
        return listaReprtFact;
    }

    public void setListaReprtFact(List<LogCartera> listaReprtFact) {
        this.listaReprtFact = listaReprtFact;
    }

    public List<LogCartera> getSelectReprtPago() {
        return selectReprtPago;
    }

    public void setSelectReprtPago(List<LogCartera> selectReprtPago) {
        this.selectReprtPago = selectReprtPago;
    }

    public List<LogCartera> getSelectSaldoFavor() {
        return selectSaldoFavor;
    }

    public void setSelectSaldoFavor(List<LogCartera> selectSaldoFavor) {
        this.selectSaldoFavor = selectSaldoFavor;
    }

    public List<LogCartera> getListaSaldoAFavor() {
        return listaSaldoAFavor;
    }

    public void setListaSaldoAFavor(List<LogCartera> listaSaldoAFavor) {
        this.listaSaldoAFavor = listaSaldoAFavor;
    }

    public ArrayList<SelectItem> getListImpsTasa() {
        return ListImpsTasa;
    }

    public void setListImpsTasa(ArrayList<SelectItem> ListImpsTasa) {
        this.ListImpsTasa = ListImpsTasa;
    }

    public int getPendtPorPagar() {
        return PendtPorPagar;
    }

    public void setPendtPorPagar(int PendtPorPagar) {
        this.PendtPorPagar = PendtPorPagar;
    }

    public LogCartera getImps() {
        return Imps;
    }

    public void setImps(LogCartera Imps) {
        this.Imps = Imps;
    }    //Info Cliente Metodos 

    public List<LogCartera> getSelectObserFact() {
        return selectObserFact;
    }

    public void setSelectObserFact(List<LogCartera> selectObserFact) {
        this.selectObserFact = selectObserFact;
    }

    public String getDocCliente() {
        return DocCliente;
    }

    public void setDocCliente(String DocCliente) {
        this.DocCliente = DocCliente;
    }

    public String getNitEntidad() {
        return NitEntidad;
    }

    public void setNitEntidad(String NitEntidad) {
        this.NitEntidad = NitEntidad;
    }

    public String getTelEntidad() {
        return TelEntidad;
    }

    public void setTelEntidad(String TelEntidad) {
        this.TelEntidad = TelEntidad;
    }

    public String getTelCliente() {
        return TelCliente;
    }

    public void setTelCliente(String TelCliente) {
        this.TelCliente = TelCliente;
    }

    public String getNombCliente() {
        return NombCliente;
    }

    public void setNombCliente(String NombCliente) {
        this.NombCliente = NombCliente;
    }

    public String getNombEnt() {
        return NombEnt;
    }

    public void setNombEnt(String NombEnt) {
        this.NombEnt = NombEnt;
    }

    public List<LogCartera> getSelectCJuridico() {
        return selectCJuridico;
    }

    public void setSelectCJuridico(List<LogCartera> selectCJuridico) {
        this.selectCJuridico = selectCJuridico;
    }

    public LogCartera getInfoCartera() {
        return InfoCartera;
    }

    public void setInfoCartera(LogCartera InfoCartera) {
        this.InfoCartera = InfoCartera;
    }

    public ArrayList<SelectItem> getListImpsFact() {
        return ListImpsFact;
    }

    public void setListImpsFact(ArrayList<SelectItem> ListImpsFact) {
        this.ListImpsFact = ListImpsFact;
    }

    public LogCartera getModImp() {
        return ModImp;
    }

    public void setModImp(LogCartera ModImp) {
        this.ModImp = ModImp;
    }

    public List<LogCartera> getListCamImpst() {
        return listCamImpst;
    }

    public void setListCamImpst(List<LogCartera> listCamImpst) {
        this.listCamImpst = listCamImpst;
    }

    public List<LogCartera> getListaAplicaImpst() {
        return listaAplicaImpst;
    }

    public void setListaAplicaImpst(List<LogCartera> listaAplicaImpst) {
        this.listaAplicaImpst = listaAplicaImpst;
    }

    public String getNombreImpuesto() {
        return NombreImpuesto;
    }

    public void setNombreImpuesto(String NombreImpuesto) {
        this.NombreImpuesto = NombreImpuesto;
    }

    public int getTasaImpuesto() {
        return TasaImpuesto;
    }

    public void setTasaImpuesto(int TasaImpuesto) {
        this.TasaImpuesto = TasaImpuesto;
    }

    public int getSubtotal() {
        return Subtotal;
    }

    public void setSubtotal(int Subtotal) {
        this.Subtotal = Subtotal;
    }

    public int getDscuento() {
        return Dscuento;
    }

    public void setDscuento(int Dscuento) {
        this.Dscuento = Dscuento;
    }

    public int getVladcConIvaFact() {
        return VladcConIvaFact;
    }

    public void setVladcConIvaFact(int VladcConIvaFact) {
        this.VladcConIvaFact = VladcConIvaFact;
    }

    public int getVladcSinIvaFact() {
        return VladcSinIvaFact;
    }

    public void setVladcSinIvaFact(int VladcSinIvaFact) {
        this.VladcSinIvaFact = VladcSinIvaFact;
    }

    public int getVladcConIvaAval() {
        return VladcConIvaAval;
    }

    public void setVladcConIvaAval(int VladcConIvaAval) {
        this.VladcConIvaAval = VladcConIvaAval;
    }

    public int getVladcSinIvaAval() {
        return VladcSinIvaAval;
    }

    public void setVladcSinIvaAval(int VladcSinIvaAval) {
        this.VladcSinIvaAval = VladcSinIvaAval;
    }

    public int getAntcp() {
        return Antcp;
    }

    public void setAntcp(int Antcp) {
        this.Antcp = Antcp;
    }

    public int getAntcpFact() {
        return AntcpFact;
    }

    public void setAntcpFact(int AntcpFact) {
        this.AntcpFact = AntcpFact;
    }

    public int getCountAbono() {
        return CountAbono;
    }

    public void setCountAbono(int CountAbono) {
        this.CountAbono = CountAbono;
    }

    public int getValorConsulta() {
        return ValorConsulta;
    }

    public void setValorConsulta(int ValorConsulta) {
        this.ValorConsulta = ValorConsulta;
    }

    public void setmBAdministacion(BeanAdministracion mBAdministacion) {
        this.mBAdministacion = mBAdministacion;
    }

    public ArrayList<SelectItem> getCargarFacturas() {
        return CargarFacturas;
    }

    public void setCargarFacturas(ArrayList<SelectItem> CargarFacturas) {
        this.CargarFacturas = CargarFacturas;
    }

    public List<LogCartera> getSelectCmbioEstado() {
        return selectCmbioEstado;
    }

    public void setSelectCmbioEstado(List<LogCartera> selectCmbioEstado) {
        this.selectCmbioEstado = selectCmbioEstado;
    }

    public int getAbono() {
        return Abono;
    }

    public void setAbono(int Abono) {
        this.Abono = Abono;
    }

    public List<LogCartera> getListNotaAbonos() {
        return ListNotaAbonos;
    }

    public void setListNotaAbonos(List<LogCartera> ListNotaAbonos) {
        this.ListNotaAbonos = ListNotaAbonos;
    }

    public List<LogCartera> getListNotaCre() {
        return ListNotaCre;
    }

    public void setListNotaCre(List<LogCartera> ListNotaCre) {
        this.ListNotaCre = ListNotaCre;
    }

    public int getCodNota() {
        return CodNota;
    }

    public void setCodNota(int CodNota) {
        this.CodNota = CodNota;
    }

    public String getEstadoPnlCart() {
        return estadoPnlCart;
    }

    public void setEstadoPnlCart(String estadoPnlCart) {
        this.estadoPnlCart = estadoPnlCart;
    }

    public List<LogCartera> getSelectRegisNotaCre() {
        return SelectRegisNotaCre;
    }

    public void setSelectRegisNotaCre(List<LogCartera> SelectRegisNotaCre) {
        this.SelectRegisNotaCre = SelectRegisNotaCre;
    }

    public int getValorRegnotaCre() {
        return ValorRegnotaCre;
    }

    public void setValorRegnotaCre(int ValorRegnotaCre) {
        this.ValorRegnotaCre = ValorRegnotaCre;
    }

    public LogCartera getSelectNotaCred() {
        return selectNotaCred;
    }

    public void setSelectNotaCred(LogCartera selectNotaCred) {
        this.selectNotaCred = selectNotaCred;
    }

    public List<LogCartera> getListConsNotaCreFac() {
        return listConsNotaCreFac;
    }

    public void setListConsNotaCreFac(List<LogCartera> listConsNotaCreFac) {
        this.listConsNotaCreFac = listConsNotaCreFac;
    }

    public int getValorAbono() {
        return ValorAbono;
    }

    public void setValorAbono(int ValorAbono) {
        this.ValorAbono = ValorAbono;
    }

    public String getObservCobro() {
        return observCobro;
    }

    public void setObservCobro(String observCobro) {
        this.observCobro = observCobro;
    }

    public String getObservNotaCre() {
        return ObservNotaCre;
    }

    public void setObservNotaCre(String ObservNotaCre) {
        this.ObservNotaCre = ObservNotaCre;
    }

    public int getValorNotaCre() {
        return ValorNotaCre;
    }

    public void setValorNotaCre(int ValorNotaCre) {
        this.ValorNotaCre = ValorNotaCre;
    }

    public int getCodRecibo() {
        return CodRecibo;
    }

    public void setCodRecibo(int CodRecibo) {
        this.CodRecibo = CodRecibo;
    }

    public ArrayList<SelectItem> getCargarRecibo() {
        return cargarRecibo;
    }

    public void setCargarRecibo(ArrayList<SelectItem> cargarRecibo) {
        this.cargarRecibo = cargarRecibo;
    }

    public String getPasarJuridicoObser() {
        return pasarJuridicoObser;
    }

    public void setPasarJuridicoObser(String pasarJuridicoObser) {
        this.pasarJuridicoObser = pasarJuridicoObser;
    }

    public List<LogCartera> getListaObservaciones() {
        return listaObservaciones;
    }

    public void setListaObservaciones(List<LogCartera> listaObservaciones) {
        this.listaObservaciones = listaObservaciones;
    }

    public int getIca() {
        return ica;
    }

    public void setIca(int ica) {
        this.ica = ica;
    }

    public int getFuente() {
        return fuente;
    }

    public void setFuente(int fuente) {
        this.fuente = fuente;
    }

    public int getReteIva() {
        return reteIva;
    }

    public void setReteIva(int reteIva) {
        this.reteIva = reteIva;
    }

    public Date getFechaPagos() {
        return fechaPagos;
    }

    public List<LogCartera> getListaImpuestosFacturas() {
        return listaImpuestosFacturas;
    }

    public void setListaImpuestosFacturas(List<LogCartera> listaImpuestosFacturas) {
        this.listaImpuestosFacturas = listaImpuestosFacturas;
    }

    public void setFechaPagos(Date fechaPagos) {
        this.fechaPagos = fechaPagos;
    }

    public int getCodBanco() {
        return codBanco;
    }

    public void setCodBanco(int codBanco) {
        this.codBanco = codBanco;
    }

    public ArrayList<SelectItem> getCargarBancos() {
        return cargarBancos;
    }

    public void setCargarBancos(ArrayList<SelectItem> cargarBancos) {
        this.cargarBancos = cargarBancos;
    }

    public long getValorFactura() {
        return valorFactura;
    }

    public void setValorFactura(long valorFactura) {
        this.valorFactura = valorFactura;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getAbonos() {
        return abonos;
    }

    public void setAbonos(int abonos) {
        this.abonos = abonos;
    }

    public int getNotasCredito() {
        return notasCredito;
    }

    public void setNotasCredito(int notasCredito) {
        this.notasCredito = notasCredito;
    }

    public int getSaldoAPagar() {
        return saldoAPagar;
    }

    public void setSaldoAPagar(int saldoAPagar) {
        this.saldoAPagar = saldoAPagar;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getValidar() {
        return validar;
    }

    public void setValidar(int validar) {
        this.validar = validar;
    }

    public List<LogCartera> getSelectPendiXCobrar() {
        return selectPendiXCobrar;
    }

    public void setSelectPendiXCobrar(List<LogCartera> selectPendiXCobrar) {
        this.selectPendiXCobrar = selectPendiXCobrar;
    }

    public List<LogCartera> getListaPendientesPorCobrar() {
        return listaPendientesPorCobrar;
    }

    public void setListaPendientesPorCobrar(List<LogCartera> listaPendientesPorCobrar) {
        this.listaPendientesPorCobrar = listaPendientesPorCobrar;
    }

    public LogCartera getCarte() {
        return Carte1;
    }

    public void setCarte(LogCartera Carte1) {
        this.Carte1 = Carte1;
    }

    public List<LogCartera> getListaImpuestosOrd() {
        return ListaImpuestosOrd;
    }

    public void setListaImpuestosOrd(List<LogCartera> ListaImpuestosOrd) {
        this.ListaImpuestosOrd = ListaImpuestosOrd;
    }

    public int getMayorValor() {
        return mayorValor;
    }

    public void setMayorValor(int mayorValor) {
        this.mayorValor = mayorValor;
    }

    public String getObservMayorValor() {
        return observMayorValor;
    }

    public void setObservMayorValor(String observMayorValor) {
        this.observMayorValor = observMayorValor;
    }

    public void ImpsCarga() {
        CargaImp();
    }

    public void cargTasa() {
        CargarTasa();
    }
    /*Metodo para mostrar el detalle de las observaciones de una factura , prueba  */
    private LogCartera selectedFact;

    public LogCartera getSelectedFact() {
        return selectedFact;
    }

    public void setSelectedFact(LogCartera selectedFact) {
        this.selectedFact = selectedFact;
    }

    public LogCartera getSelectedCar() {
        return selectedCar;
    }

    public void setSelectedCar(LogCartera selectedCar) {
        this.selectedCar = selectedCar;
    }

    public void ValidarSeleccion(int Val) {
        ClearVbls();
        int size = getSelectPendiXCobrar().size();
        validar = 1;
        switch (Val) {
            case 1:
                if (getSelectPendiXCobrar() != null) {
                    if (size > 1) {
                        for (int i = 0; i < size; i++) {
                            if (!getSelectPendiXCobrar().get(0).getPersona().equalsIgnoreCase(getSelectPendiXCobrar().get(i).getPersona())) {
                                validar++;
                                mBTodero.setMens("Si desea realizar seguimiento a varias facturas, estas deben ser del mismo cliente");
                                mBTodero.warn();
                                setSelectPendiXCobrar(null);
                            }
                        }
                        if (getListaImpuestosFacturas().size() > 0) {
                            InfoCarte();
                        } //Generar una consulta para informacion de los impuestos            //Lenar una nueva lista con la informacion de lso impuestos
                    } else {
                        InfoCarte();
                    }
                }
                break;
            case 2: {
                CadenaFact = "";
                // Se ejeceuta este metodo cuando se ha realizado un cambio en  los impuestos
                //esto con el fin de volver a recalcular el nuevo valor a cancelar de la factura
                LogCartera Cart = new LogCartera();
                if (getSelectPendiXCobrar().size() == 1) {
                    CadenaFact = String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura());
                } else {
                    for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                        CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
                    }
                    CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                }
                GestionCartera("InfoImpst");
                if (getListaReprtFact().size() > 0) {
                    setValorElectronico(getValorElectronico() / getListaReprtFact().size());
                }
                Cart.setInfoXAval(2);
                setListaImpuestosFacturas(Cart.Impuestos(1, "cambio_impuesto", CadenaFact));
                if (getSelectPendiXCobrar() != null) {
                    if (size > 1) {
                        for (int i = 0; i < size; i++) {
                            if (!getSelectPendiXCobrar().get(0).getPersona().equalsIgnoreCase(getSelectPendiXCobrar().get(i).getPersona())) {
                                validar++;
                                mBTodero.setMens("Si desea realizar seguimiento a varias facturas, estas deben ser del mismo cliente");
                                mBTodero.warn();
                                setSelectPendiXCobrar(null);
                            }
                        }
                        if (getListaImpuestosFacturas().size() > 0) {
                            InfoCarte();
                        } //Generar una consulta para informacion de los impuestos            //Lenar una nueva lista con la informacion de lso impuestos
                    } else {
                        InfoCarte();
                    }
                }
                traerIca();
            }
            break;
            case 3: {
                CadenaFact = "";
                LogCartera Cart = new LogCartera();
                if (getSelectPendiXCobrar().size() == 1) {
                    CadenaFact = String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura());
                } else {
                    for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                        CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
                    }
                    CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                }
                GestionCartera("InfoImpst");
                if (getListaReprtFact().size() > 0) {
                    setValorElectronico(getValorElectronico() / getListaReprtFact().size());
                }
                Cart.setInfoXAval(2);
                setListaImpuestosFacturas(Cart.Impuestos(2, String.valueOf(getValorElectronico()), CadenaFact));
                if (getSelectPendiXCobrar() != null) {
                    if (size > 1) {
                        for (int i = 0; i < size; i++) {
                            if (!getSelectPendiXCobrar().get(0).getPersona().equalsIgnoreCase(getSelectPendiXCobrar().get(i).getPersona())) {
                                validar++;
                                mBTodero.setMens("Si desea realizar seguimiento a varias facturas, estas deben ser del mismo cliente");
                                mBTodero.warn();
                                setSelectPendiXCobrar(null);
                            }
                        }
                        if (getListaImpuestosFacturas().size() > 0) {
                            InfoCarte();
                        } //Generar una consulta para informacion de los impuestos            //Lenar una nueva lista con la informacion de lso impuestos
                    } else {
                        InfoCarte();
                    }
                }
            }
            break;
            default:
                break;
        }
    }

    public void traerIca() {
        LogCartera Cartera1 = new LogCartera();
        setListaInfoAvaluos(Cartera1.ImpsDtAval("CodAval = cambio_impuesto.Fk_Cod_Avaluo", Cadena()));
        int Total = 0;
        EstIcaAval = true;
        for (int i = 0; i < getListaInfoAvaluos().size(); i++) {
            Total = getListaInfoAvaluos().get(i).getRteIca() + Total;
            setIcaAvaltmp(Total);
        }
        setLblIcaAvaltmp("RETE ICA %");
    }

    public void InfoCarte() {
        setDocCliente(getSelectPendiXCobrar().get(0).getDocCliente());
        setNitEntidad(getSelectPendiXCobrar().get(0).getNitEntidad());
        setTelCliente(getSelectPendiXCobrar().get(0).getTelCliente());
        setTelEntidad(getSelectPendiXCobrar().get(0).getTelEntidad());
        setNombreCliente(getSelectPendiXCobrar().get(0).getNombrecliente());
        setNombEnt(getSelectPendiXCobrar().get(0).getNombreEntidad());
        setFecha(selectPendiXCobrar.get(0).getFechaFacturacion().substring(0, 11));
        //Informacion que se debe sumar por las Facturas seleccionadas //
        for (int i = 0; i < getListaImpuestosFacturas().size(); i++) {
            setNotasCredito(getListaImpuestosFacturas().get(i).getFijonotacre() + getListaImpuestosFacturas().get(i).getPorcnotacre() + getNotasCredito());
            setAbonos(getListaImpuestosFacturas().get(i).getAbonosAvaluo() + getAbonos());
            setValorNotaCre(getListaImpuestosFacturas().get(i).getNotasCredito() + getListaImpuestosFacturas().get(i).getPorcnotacre() + getNotasCredito());
            setValorAbono(getListaImpuestosFacturas().get(i).getAbonosAvaluo() + getValorAbono());
            setSubtotal(getListaImpuestosFacturas().get(i).getSubtotal() + getSubtotal());
            setValorFactura(getListaImpuestosFacturas().get(i).getValorFactura() + getValorFactura());
            setVladcConIvaAval(getListaImpuestosFacturas().get(i).getVladcConIvaAval() + getVladcConIvaAval());
            setVladcConIvaFact(getListaImpuestosFacturas().get(i).getVladcConIvaFact() + getVladcConIvaFact());
            setTotalDescFactAval(getListaImpuestosFacturas().get(i).getTotalDescFactyAva());
            setVladcSinIvaAval(getListaImpuestosFacturas().get(i).getVladcSinIvaAval() + getVladcSinIvaAval());
            setVladcSinIvaFact(getListaImpuestosFacturas().get(i).getVladcSinIvaFact() + getVladcSinIvaFact());
            setAntcp(getListaImpuestosFacturas().get(i).getAntcp() + getAntcp());
            setAntcpFact(getListaImpuestosFacturas().get(i).getAntcpFact() + getAntcpFact());
            setDscuento(getListaImpuestosFacturas().get(i).getDscnto() + getDscuento());
            setTpagar(getListaImpuestosFacturas().get(i).getTotalPagar() + getTpagar());
            setIva(getListaImpuestosFacturas().get(i).getIva() + getIva());
            setFuente((int) (getListaImpuestosFacturas().get(i).getRteFuente() + getFuente()));
            setIca(getListaImpuestosFacturas().get(i).getRteIca() + getIca());
            setReteIva(getListaImpuestosFacturas().get(i).getRteIva() + getReteIva());
            setPendtPorPagar(getListaImpuestosFacturas().get(i).getPndPorCobrar() + getPendtPorPagar());

            //Traer el tipo de pago , con el que se calcularon los valores 
            // setCodTipoPago(getListaImpuestosFacturas().get(i).getTipoPago());
        }
        setListaReprtFact(getListaImpuestosFacturas());
    }

    public void ClearVbls() {
        DocCliente = "";
        TelCliente = "";
        TelEntidad = "";
        nombreCliente = "";
        NombEnt = "";
        fecha = "";
        notasCredito = 0;
        abonos = 0;
        ValorNotaCre = 0;
        ValorAbono = 0;
        Subtotal = 0;
        valorFactura = 0;
        VladcConIvaAval = 0;
        VladcConIvaFact = 0;
        VladcSinIvaAval = 0;
        VladcSinIvaFact = 0;
        Antcp = 0;
        AntcpFact = 0;
        AntcpFact = 0;
        Dscuento = 0;
        Tpagar = 0;
        iva = 0;
        ica = 0;
        fuente = 0;
        reteIva = 0;
        PendtPorPagar = 0;
        saldoAPagar = 0;
        fechaConsig = "";
        Nombre = 0;
    }

    public ArrayList<SelectItem> OnBancos() {
        try {
            Iterator<LogCartera> Ite;
            LogCartera Carte = new LogCartera();
            Ite = Carte.CargarBancos().iterator();
            while (Ite.hasNext()) {
                Carte = Ite.next();
                this.cargarBancos.add(new SelectItem(Carte.getCodBanco(), String.valueOf(Carte.getBancos())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + " causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.cargarBancos;
    }

    public void Limpiar(int opc) {
        if (opc == 1) {
            listaImpuestosFacturas.clear();
            setIca(0);
            setIva(0);
            setFuente(0);
            setReteIva(0);
        }
        if (opc == 2) {
            setCambImps(0);
            setSelectImps("");
            setSelectTasa(0);
            ObservCamImp = "";
        }
    }

    //Metodo que se utiliza para la limpieza del pago de la factura
    public void LimCajasPago() {
        try {
            setNombre(0);
            setAbono(0);
            setCodBanco(0);
            setCodRecibo(0);
            fechaPagos = null;
            setTipoAjuste("");
            setValorAjuste("");
        } catch (Exception e) {
            mBTodero.setMens("Error " + e.getMessage());
            mBTodero.info();
        }
    }

    //Cargar la informacion en la lista de Cargar cartera 
    public ArrayList<SelectItem> OnRecibos() {
        try {
            Iterator<LogCartera> Ite;
            LogCartera Carte = new LogCartera();
            Ite = Carte.CargarRecibo().iterator();
            while (Ite.hasNext()) {
                Carte = Ite.next();
                this.cargarRecibo.add(new SelectItem(Carte.getCodRecibo(), String.valueOf(Carte.getRecibo())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + " causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.cargarRecibo;
    }
    int Cond = 0;
    String confirmar = "";
    private String CambIm = "";
    int valdelete = 1;
    int S = 1;
    int Count = 0;

    /// variables Acept Pago
    int Total = 0;
    int size = 0;

    public void LlenarDistribPago() {
        CadenaFact = "";
//Crear un metodo para realizar este procedimiento
        //Crear la tabla con el promedio de el valor dividido en el numero de avaluos seleccionados
        for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
            CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
        }
        CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
        LogCartera CamImps = new LogCartera();
        setListaReprtFact(CamImps.Impuestos2(1, "cambio_impuesto", CadenaFact));
        size = listaReprtFact.size();
        if (size != 0) {
            listaReprtTemp.clear();
            for (int i = 0; i < size; i++) {
                LogCartera Ajuste = new LogCartera();
                //Ingresar el rsto de la informacion  hace falta en la lista para mostrar la info
                int Cod = getListaReprtFact().get(i).getCodFactura();
                Total = getListaReprtFact().get(i).getTotalPagar();
                Ajuste.setCodFactura(Cod);
                Ajuste.setCodAval(getListaReprtFact().get(i).getCodAval());
                Ajuste.setTotalPagar(Total);
                String Tipo = "Porcentaje";
                int TotalAbono = 0;
                Ajuste.setValorAjuste(Tipo);
                Ajuste.setTotalAjuste(TotalAbono);
                listaReprtTemp.add(i, Ajuste);
            }
            setMayorValor(getAbono() - getPendtPorPagar());
            listaReprtFact.clear();
            setListaReprtFact(listaReprtTemp);
            setValorConsig(getAbono());
        }
    }
    int ValorAjuste = 0;

    public void NotaCreditoGes(String Opc) {
        switch (Opc) {
            case "InfoNotaCred":
                try {
                    Carte = new LogCartera();
                    setListConsNotaCreFac(Carte.ConsFactNota(String.valueOf(SelectFact)));
                } catch (Exception e) {
                }
                break;
        }
    }

    public void GestionCartera(String Opc) {
        try {
            switch (Opc) {
                case "ListarObserv":
                    LogCartera Car = new LogCartera();
                    setListaObservDetalle(Car.ListaObservaciones(String.valueOf(Observ.getCodFactura()), 2));
                    RequestContext.getCurrentInstance().execute("PF('ObservFact').show()");
                    break;
                //Se uitiliza para realizar por fin el pago una vez validada la forma de la reparticion 
                case "AceptarInsertPago":
                    ValorAjuste = 0;
                    getFechaPagos();
                    try {
                        //Validar el tema de Fechas 
                        Carte = new LogCartera();
                        if (S == 1) {

                            if (getTipoAjuste().equals("1") || getTipoAjuste().equals("2")) {
                                for (int i = 0; i < listaReprtFact.size(); i++) {
                                    //Tipo de Ajuste 
                                    // 1: Para porcentaje.
                                    // 2: Para Valor Fijo
                                    if (getTipoAjuste().equals("2")) {
                                        ValorAjuste = listaReprtFact.get(i).getTotalAjuste() + ValorAjuste;
                                    } else if (getTipoAjuste().equals("1")) {
                                        if (listaReprtFact.get(i).getTotalAjuste() > 100 || listaReprtFact.get(i).getTotalAjuste() < 0) {
                                            mBTodero.setMens("No puede exceder el 100%");
                                            mBTodero.warn();
                                            break;
                                        } else {
                                            ValorAjuste = (getValorConsig() * listaReprtFact.get(i).getTotalAjuste()) / 100 + ValorAjuste;
                                        }
                                    }
                                }
                            } else {
                                mBTodero.setMens("Por favor seleccione alguna opcion de Tipo Ajuste");
                                mBTodero.warn();
                            }
                            if (ValorAjuste == getAbono()) {
                                confirmar = "Aceptar";
                                Cond = 1;
                                GestionCartera("FinalizarPago");
                            } else {
                                mBTodero.setMens("Debe validar el valor de la distribucion, ya que que este no es igual al consignado");
                                mBTodero.info();
                            }
                        }
                    } catch (Exception e) {
                        mBTodero.setMens("Error en el metodo de AceptarInsertPago, Valide con el administrador. " + e.getMessage());
                        mBTodero.error();
                    }
                    break;
                case "AceptPago":
                    try {
                        setValorConsig(getAbono() + val);
                        if (CambIm.equals("Aceptar")) {
                            for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                                CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
                            }
                            CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                            LogCartera CamImps = new LogCartera();
                            setListaReprtFact(CamImps.Impuestos2(1, "cambio_impuesto", CadenaFact));
                        }
                        if (getListaReprtFact().size() >= 1) {
                            Cond = 2;
                            confirmar = "Aceptar";
                            GestionCartera("FinalizarPago");
                        }
                    } catch (Exception e) {
                        mBTodero.setMens("Error en aceptar el pago" + e.getMessage());
                        mBTodero.info();
                    }
                    break;
                case "ReptPago":
                    // Aqui debe hacer el registro para varias facturas llamando el metodo de registro de pago
                    GestionCartera("RegistroPago");
                    break;
                case "CobroJur":
                    RequestContext.getCurrentInstance().execute("PF('juridico').show()");
                    break;
                case "OpcCobro":
                    //RequestContext.getCurrentInstance().execute("PF('CastigaCartera').show()");
                    cambiarEstados(1, selectPendiXCobrar.size());
                    RequestContext.getCurrentInstance().execute("PF('juridico').hide()");
                    //Crear un mensaje de Alerta para aparecer en pendientes por Pagar
                    RequestContext.getCurrentInstance().execute("PF('PopObservaciones').show()");
                    break;
                case "RemovNotaCre":
                    try {
                        for (int i = 0; i < listConsNotaCreFac.size(); i++) {
                            //Tener en cuenta que este numero no lo cargo en la lista 
                            if (getListConsNotaCreFac().get(i).getCodNota() == Carte1.getCodNota()) {
                                Carte1.GestionCartera("DeleteNotaCre", listConsNotaCreFac.get(i).getIdNotaCre(), 0, mBsesion.codigoMiSesion());
                                listConsNotaCreFac.remove(i);
                                Valor = 0;
                            }
                        }
                        for (int i = 0; i < listConsNotaTemp.size(); i++) {
                            if (!listConsNotaTemp.isEmpty()) {
                                if (listConsNotaTemp.get(i).getCodNota() == Carte1.getCodNota()) {
                                    listConsNotaTemp.remove(i);
                                }
                            }
                        }
                    } catch (Exception e) {
                        mBTodero.setMens("Error en el metodo '" + this.getClass() + ".removerZon()' causado por: " + e.getMessage());
                        mBTodero.error();
                    }
                    break;
                case "NotaCreInsert":
                    //Metodo que permite hacer el registro de las Notas Credito asociadas a la Factura
                    mBTodero.setMens("Proceso realizado correctamente");
                    mBTodero.info();
                    RequestContext.getCurrentInstance().execute("PF('notaCredito').hide()");
                    //Limpiar las Cajas 
                    Cajas();
                    ListTablaNotaCre = new ArrayList<>();
                    listConsNotaTemp.clear();
                    listConsNotaCreFac.clear();
                    break;
                case "FinalizarPago":
                    if ("Aceptar".equals(confirmar) || Cond == 1) {
                        try {
                            LogCartera Inser = new LogCartera();
                            if (getCodBanco() != 0 && getCodRecibo() != 0 && getAbono() != 0) {
                                Inser.setCodNota(getCodTipoPago());
                                Inser.setFk_CodCuenta(getCodBanco());
                                Inser.setFk_Id_Recibo(getCodRecibo());
                                Inser.setFechaConsig(Format.format(getFechaPagos()));
                                Inser.GestionCartera("RegistroPago", getNombre(), 0, mBsesion.codigoMiSesion());
                                setId_Pago(Inser.Id_Pago);
                                if (RegPago == 0) {
                                    /*Insertar el ajuste que se realizo al pago  Ajustes (Mayor/Menor) Valor */
                                    Carte1 = new LogCartera();
                                    Carte1.setIdPago(Id_Pago);
                                    Carte1.setObservacion(getObservMayorValor());
                                    if (getMayorValor() == 0) {
                                        setMayorValor(val);
                                    }
                                    Carte1.GestionCartera("MayorValor", getMayorValor(), 0, mBsesion.codigoMiSesion());
                                }
                            } else {
                                mBTodero.setMens("Debe diligenciar los campos obligatorios");
                                mBTodero.info();
                            }
                            ValorAjuste = 0;
                            for (int i = 0; i < getListaReprtFact().size(); i++) {
                                LogCartera Cart = new LogCartera();
                                if ("Aceptar".equals(confirmar) || Cond == 1) {
                                    if (getCodBanco() != 0 && getCodRecibo() != 0 && getAbono() != 0 && getCodTipoPago() != 0) {
                                        Cart.setValorFactPago(getTpagar());
                                        Cart.setCodImp(getListaReprtFact().get(i).getCodAval());
                                        if (Cond == 2) {
                                            Cart.GestionCartera("DtallePago", getListaReprtFact().get(0).getCodFactura(), (getAbono()), mBsesion.codigoMiSesion());
                                        } else if (getListaReprtFact().get(i).getTotalAjuste() != 0) {

                                            //Cuando el ajuste se realiza por porcentaje
                                            if (getTipoAjuste().equals("1")) {
                                                if (listaReprtFact.get(i).getTotalAjuste() > 100 || listaReprtFact.get(i).getTotalAjuste() < 0) {
                                                    mBTodero.setMens("No puede exceder el 100%");
                                                    mBTodero.warn();
                                                    break;
                                                } else {
                                                    ValorAjuste = (getValorConsig() * listaReprtFact.get(i).getTotalAjuste()) / 100;
                                                }
                                                Cart.GestionCartera("DtallePago", getListaReprtFact().get(i).getCodFactura(), ValorAjuste, mBsesion.codigoMiSesion());
                                            } else if (getTipoAjuste().equals("2")) {
                                                Cart.GestionCartera("DtallePago", getListaReprtFact().get(i).getCodFactura(), getListaReprtFact().get(i).getTotalAjuste(), mBsesion.codigoMiSesion());
                                            }
                                        }
                                        Cond = 1;
                                    }
                                } else {
                                    mBTodero.setMens("Por favor diligencie los campos obligatorios para realizar el registro");
                                    mBTodero.info();
                                    break;
                                }
                            }
                            if (Cond == 1) {
                                if (RegPago == 0) {
                                    PendtPorPagar = 0;
                                } else {
                                    PendtPorPagar = getTpagar() - getAbono();
                                }
                                RequestContext.getCurrentInstance().execute("PF('PopPago').show()");
                                mBTodero.setMens("Pago Registrado satisfactoriamente");
                                mBTodero.info();
                                EstadoFact();
                                LimCajasPago();
                                confirmar = "";
                                Cobrar = "Cobrar";
                            }
                        } catch (Exception e) {
                            mBTodero.setMens("Error" + e.getMessage());
                            mBTodero.info();
                        }
                    }
                    break;

                case "ObservCobro":
                    try {
                        CadenaFact = "";
                        int Insert = 0;
                        if (!getSelectObserFact().isEmpty()) {
                            if (!getObservCobro().isEmpty()) {
                                for (int i = 0; getSelectObserFact().size() > i; i++) {
                                    Carte = new LogCartera();
                                    Carte.setObservCobro(getObservCobro());
                                    Carte.GestionCartera("ObservCobro", getSelectObserFact().get(i).getCodFactura(), 0, mBsesion.codigoMiSesion());
                                    Insert = 1;
                                }
                            } else {
                                mBTodero.setMens("Debe ingresar una observación");
                                mBTodero.info();
                            }
                            if (getSelectPendiXCobrar().size() == 1) {
                                CadenaFact = String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura());
                            } else {
                                for (int j = 0; getListaImpuestosFacturas().size() > j; j++) {
                                    CadenaFact = String.valueOf(getListaImpuestosFacturas().get(j).getCodFactura()) + "," + CadenaFact;
                                }
                                CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                            }
                            setListaObservaciones(Carte.ListaObservaciones(CadenaFact, 1));
                            setObservCobro("");
                        } else {
                            mBTodero.setMens("Seleccione la factura a la cual desea realizar el seguimiento");
                            mBTodero.info();
                        }
                        if (Insert == 1) {
                            mBTodero.setMens("Registro realizado correctamente");
                            mBTodero.info();
                        }
                    } catch (Exception e) {
                        mBTodero.setMens("Error en el metodo de ObservCobro, verifique ");
                        mBTodero.error();
                    }
                    break;
                case "InfoNotacre":
                    try {
                        //Cargar el combobox de las Facturas seleccionadas en Pendientes por Cobrar
                        getCargarFacturas().clear();
                        listConsNotaTemp.clear();
                        setCargarFacturas(OnFacturas());
                        //Traer la informacion de Nota credito de las facturas seleccionadas 
                        Carte = new LogCartera();
                        setListConsNotaCreFac(Carte.notacreditoFact(getSelectPendiXCobrar().get(0).getCodFactura()));
                        RequestContext.getCurrentInstance().execute("PF('notaCredito').show()");
                    } catch (Exception e) {
                        mBTodero.setMens("Error al cargar la informacion de Nota Credito" + e.getMessage());
                        mBTodero.warn();
                    }
                    break;
                case "CastigarCarte":
                    RequestContext.getCurrentInstance().execute("PF('CastigaCartera').show()");
                    break;
                case "OperCast":
                    try {
                        TempEstados.clear();
                        LogCartera ObserCobro = new LogCartera();
                        ObserCobro.setObservCobro("Castigo Cartera --" + getPasarCCastigada());
                        ObserCobro.GestionCartera("ObservCobro", getSelectCCartera().get(0).getCodFactura(), 0, mBsesion.codigoMiSesion());
                        TempEstados.addAll(selectCCartera);
                        //Esta opcion permite cambiar el estado de la tabla Factura a Cartera Castigada
                        cambiarEstados(2, selectCCartera.size());
                        RequestContext.getCurrentInstance().execute("PF('CastigaCartera').hide()");
                        //Crear un mensaje de Alerta para aparecer en pendientes por Pagar
                        RequestContext.getCurrentInstance().execute("PF('PopObservaciones').show()");
                    } catch (Exception e) {
                        mBTodero.setMens("Error al ejecutar el metodo de Castigar aCartera" + e.getMessage());
                        mBTodero.warn();
                    }
                    break;
                case "OperCobroJurd":
                    try {
                        //Ejecuta el metodo para cambiar el Estado de las Facturas seleccionadas a Cobro Juridico
                        LogCartera ObserCobro = new LogCartera();
                        TempEstados.addAll(selectCJuridico);
                        ObserCobro.setObservCobro("Cobro Juridico --" + getPasarJuridicoObser());
                        ObserCobro.GestionCartera("ObservCobro", getSelectCJuridico().get(0).getCodFactura(), 0, mBsesion.codigoMiSesion());
                        cambiarEstados(1, selectCJuridico.size());
                        RequestContext.getCurrentInstance().execute("PF('juridico').hide()");
                        //Crear un mensaje de Alerta para aparecer en pendientes por Pagar
                        RequestContext.getCurrentInstance().execute("PF('PopObservaciones').show()");
                    } catch (Exception e) {
                        mBTodero.setMens("Error al ejecutar el metodo de Cobro Juridico" + e.getMessage());
                        mBTodero.warn();
                    }
                    break;

                case "FactNotaCre":
                    try {
                        CadenaFact = "";
                        //Cargar la informacion correspondiente a las notas credito asociadas a la factura seleccionada
                        if (ValorConsulta == 1) {
                            Carte = new LogCartera();
                            CadenaFact = Cadena();
                            setListNotaCre(Carte.ConsFactNota(CadenaFact));
                            ValorConsulta = 2;
                        } else if (ValorConsulta == 2) {
                            RequestContext.getCurrentInstance().execute("PF('NotaCreFact').show()");
                        }
                    } catch (Exception e) {
                        mBTodero.setMens("Error en el metodo FactNotaCre");
                        mBTodero.error();
                    }
                    break;
                case "FactAbono":
                    try {

                        CadenaFact = "";

                        if (CountAbono == 1 || CountAbono == 3) {
                            //Cargar la informacion correspondiente a los abonos asociados a la factura
                            Carte = new LogCartera();
                            if (getSelectPendiXCobrar().size() == 1) {
                                CadenaFact = String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura());
                            } else {
                                for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                                    CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
                                }
                                CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                            }
                            setListNotaAbonos(Carte.ConsFactAbono(CadenaFact));
                        } else if (CountAbono == 2) {
                            RequestContext.getCurrentInstance().execute("PF('AbonoFact').show()");
                        }

                    } catch (Exception e) {
                        mBTodero.setMens(" Error en el metodo FactAbono");
                        mBTodero.error();
                    }
                    break;
                case "Abonos":
                    RequestContext.getCurrentInstance().execute("PF('AbonoFact').show()");
                    break;

                case "CambioImpuestos":
                    try {
                        Lbel = "Agregar";
                        /*Aqui debe ejecutarse los nuevos valores para el camnio de retenciones que se le apliqien al cliente 
                     consultar la infromacion de los impuestos aplicados para X Factura , si es solo una cargar la respectiva informacion , 
                     permitir agregar un nuevo impuesto
                         */
                        getListCamImpst().clear();
                        getCargarFacturas().clear();
                        setCargarFacturas(OnFacturas());
                        //Cargar la informacion de los impuestos y la tasa de cada impuesto seleccionada
                        getListImpsFact().clear();
                        ImpsCarga();
                        Count = 0;
                        RequestContext.getCurrentInstance().execute("PF('CambioImps').show()");
                    } catch (Exception e) {
                        mBTodero.setMens(" Error en el metodo CambioImpuestos");
                        mBTodero.error();
                    }
                    break;
                case "InfoImpst":
                    try {
                        //Mostrar observaciones 
                        //Cargar la informacion de los impuestos asociados a la Factura
                        Carte = new LogCartera();
                        if (getCodTipoPago() != 0) {
                            if (getCargarTipoPago().size() > 0) {
                                for (int u = 0; u < getCargarTipoPago().size(); u++) {
                                    if (getCargarTipoPago().get(u).getValue().equals(CodTipoPago)) {
                                        SelectImps = String.valueOf(getCargarTipoPago().get(u).getLabel());
                                        if (SelectImps.equalsIgnoreCase("debito") || SelectImps.equalsIgnoreCase("credito")) {
                                            Carte.setObservacion("impuesto_tmp");
                                            break;
                                        } else {
                                            Carte.setObservacion("cambio_impuesto");
                                            break;
                                        }
                                    }
                                }
                            } else {
                                Carte.setObservacion("cambio_impuesto");
                            }
                        }
                        if (getCambImps() != 0) {
                            setListCamImpst(Carte.Impuestos(0, String.valueOf(getCambImps())));
                            Carte.InfoImpuesto(String.valueOf(getCambImps()));
                        } else {
                            String Fact = Cadena();
                            setListCamImpst(Carte.Impuestos(0, Fact));
                            Carte.InfoImpuesto(Fact);
                        }

                        //Llenar las Variables con el Nombre y la Tasa del Impuesto Aplicada
                        ViewImpsApli();
                    } catch (Exception e) {
                        mBTodero.setMens(" Error en el metodo InfoImpst");
                        mBTodero.error();
                    }
                    break;

                case "MostrarFact":
                    Car = new LogCartera();
                    for (int u = 0; u < getCargarTipoPago().size(); u++) {
                        if (getCargarTipoPago().get(u).getValue().equals(CodTipoPago)) {
                            SelectImps = String.valueOf(getCargarTipoPago().get(u).getLabel());
                            if (SelectImps.equalsIgnoreCase("debito") || SelectImps.equalsIgnoreCase("credito")) {
                                Car.setObservacion("Impuesto_tmp");
                                break;
                            } else {
                                Car.setObservacion("Cambio_impuesto");
                                break;
                            }
                        }
                    }
                    setListCamImpst(Carte.Impuestos(0, String.valueOf(getCambImps())));
                    Carte.InfoImpuesto(String.valueOf(getCambImps()));
                    ViewImpsApli();
                    break;
                case "ImpsInfoXfact":
                    //Aqui se debe traer la informacion de los impuestos aplicados para la factura seleccionada
                    Carte = new LogCartera();
                    setListaAplicaImpst(Carte.ImpsFact(getSelectFact()));
                    break;
                case "Reintegro":
                    LogCartera ObserReinte = new LogCartera();
                    ObserReinte.setObservCobro("REINTEGRO --" + getObservCobro());
                    ObserReinte.GestionCartera("ObservCobro", getSelectSaldoFavor().get(0).getCodFactura(), 0, mBsesion.codigoMiSesion());
                    ObserReinte.ProcesoAnular("UpdateEstado", getSelectSaldoFavor().get(0).getCodFactura(), mBsesion.codigoMiSesion(), "P");
                    setListaSaldoAFavor(new ArrayList<>());
                    setListaSaldoAFavor(ObserReinte.SaldoaFavor());
                    mBTodero.setMens("Reintegro realizado correctamente");
                    mBTodero.info();
                    RequestContext.getCurrentInstance().execute("PF('Reintegro').hide()");
                    setObservCobro("");
                    break;
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo de Gestion Cartera" + e.getMessage());
            mBTodero.warn();
        }
    }
    //Agregar nuevos valores al impuesto

    public void AddImpuesto() {
        int Val = 0;
        Val = (int) getSelectTasa();
        for (int j = 0; j < getListImpsFact().size(); j++) {
            if (Val == Integer.parseInt(getListImpsFact().get(j).getValue().toString())) {
                if (Integer.valueOf(getSelectImps()) == Val) {
                    setNombreImpuesto(getListImpsFact().get(j).getLabel());
                }
                break;
            }
        }

        if (S == 0) {
            if (Valor >= 1) {
                if (getCambImps() == 0) {
                    mBTodero.setMens("Debe seleccionar  la Factura");
                    mBTodero.warn();
                } else if ("".equals(getObservCamImp())) {
                    mBTodero.setMens("Debe ingresar informcion en el campo Observaciones'");
                    mBTodero.warn();
                } else if (Integer.valueOf(getSelectImps()) == 0) {
                    mBTodero.setMens("Debe seleccionar el Impuesto'");
                    mBTodero.warn();
                } else if (getSelectTasa() == 0) {
                    mBTodero.setMens("Debe seleccionar la Tasa'");
                    mBTodero.warn();
                } else {
                    if ("Modificar".equals(ModificarImps)) {
                        getListCamImpst().remove(ModImp.getCodNota() - 1);
                        CodNota = 1;
                    }
                    for (int i = 0; i < getListCamImpst().size(); i++) {
                        if (getListCamImpst().get(i).getNombreImpuesto().equals(getNombreImpuesto())) {
                            mBTodero.setMens("El impuesto ya se encuentra registrado. Dirijase a la pestana Impuestos a la factura");
                            mBTodero.info();
                            S = 1;
                            Limpiar(2);
                            break;
                        }
                    }

                    if (S != 1) {
                        //debe almacenar la informacion que ya existe del datagrid
                        LogCartera Imp = new LogCartera();
                        for (int i = 0; i < getListCamImpst().size(); i++) {
                            CodNota = 1;
                            Imp.setCodNota(CodNota);
                            Imp.setCodImp(getListCamImpst().get(i).getCodImp());
                            Imp.setTasaImpuesto(getListCamImpst().get(i).getTasaImpuesto());
                            Imp.setNombreImpuesto(getListCamImpst().get(i).getNombreImpuesto());
                            Imp.setCodFactura(getCambImps());
                            Imp.setObservCobro(getListCamImpst().get(i).getObservCobro());
                            CodNota = CodNota + 1;
                        }
                        //Insercion para el tema de un nuevo impuesto aplicar
                        Imp.setCodNota(getListCamImpst().size() + 1);
                        Imp.setCodImp(Integer.valueOf(getSelectImps()));
                        Imp.setTasaImpuesto((int) getSelectTasa());
                        Imp.setNombreImpuesto(getNombreImpuesto());
                        Imp.setCodFactura(getCambImps());
                        Imp.setObservCobro(getObservCamImp());
                        if ("Modificar".equals(ModificarImps)) {
                            listCamImpst.add(CodNota - 1, Imp);
                            //Realizar la actualizacion en Base de datos para el impuesto 
                            LogCartera Carte = new LogCartera();
                            //getCambImps() = Este toma el valor de la factura
                            Carte.setCodRecibo((int) SelectTasa);
                            Carte.setCodImp(ModImp.getCodImp());
                            Carte.GestionCartera("UpdateCmbImps", getCambImps(), 0, mBsesion.codigoMiSesion());
                        } else {
                            listCamImpst.add(Imp);
                        }
                    }
                }
            }
        }
    }

    int count = 1;
    int Valor = 1;

    public void agregarValAdicional() {

        setSelectRegisNotaCre(null);
        if (Valor == 0) {
            CodNota = CodNota + 1;
            Valor = 1;
        } else {
            CodNota = getListConsNotaCreFac().size() + 1;
        }
        try {
            //Validar que no se repita el tipo de valor adicional
            //Validar con la lista de los ya registrados anteriormente.
            if (Valor >= 1) {
                if (getValorRegnotaCre() == 0) {
                    mBTodero.setMens("Debe colocar informacion en el campo de Valor");
                    mBTodero.warn();
                } else if ("".equals(getObservNotaCre())) {
                    mBTodero.setMens("Debe ingresar informcion en el campo Observaciones'");
                    mBTodero.warn();
                } else {
                    LogCartera Cartera = new LogCartera();
                    Cartera.setCodNota(CodNota);
                    Cartera.setValNotaCre(getValorRegnotaCre());
                    Cartera.setObservacion(getObservNotaCre());
                    Cartera.setCodFactura(getSelectFact());
                    if ("P".equals(getTipoPorFijo())) {
                        setTipoPorFijo("Porcentaje");
                    } else {
                        setTipoPorFijo("Fijo");
                    }
                    Cartera.setTipoFactura(getTipoPorFijo());

                    if (getValorRegnotaCre() > 100 && getTipoPorFijo().equals("P")) {
                        mBTodero.setMens("Valide que el Valor no exceda el 100%");
                        mBTodero.info();
                    } else {
                        listConsNotaCreFac.add(Cartera);
                        listConsNotaTemp.add(Cartera);
                        CodNota = CodNota + 1;

                        //Insertar Notas Credito 
                        LogCartera Cart1 = new LogCartera();
                        for (int i = 0; i < listConsNotaTemp.size(); i++) {
                            if (listConsNotaTemp.get(i).getTipoFactura().equals("Fijo") || listConsNotaTemp.get(i).getTipoFactura().equals("F")) {
                                setTipoPorFijo("F");
                            } else {
                                setTipoPorFijo("P");
                            }
                            Cart1.setTipoFactura(getTipoPorFijo());
                            Cart1.GestionCartera("GrabarNotaCre", listConsNotaTemp.get(i).getCodFactura(), listConsNotaTemp.get(i).getValNotaCre(), mBsesion.codigoMiSesion());
                        }
                        Cajas();
                        mBTodero.setMens("Registro satisfactorio de la nota credito.");
                        mBTodero.info();
                    }
                    //Arreglar el codigo para colocar metodos por cada PopUp y no hacer solo un chorrero para gestion cartera
                }
            }
        } catch (NumberFormatException e) {
            mBTodero.setMens("error" + e);
            mBTodero.warn();
        }
    }
    String NombreImp = "";
    int CodImps = 0;
    int CodTasa = 0;
    String ModificarImps;

    public void ModImpuesto(String Opc) {
        try {
            if ("Aceptar".equals(Opc)) {
                ModificarImps = "Modificar";
                if (NombreImpuesto.equals("IVA")) {
                    mBTodero.setMens("Tenga en cuenta que bajo ningun motivo puede modificar o eliminar este impuesto.");
                    mBTodero.warn();
                } else if (ListImpsTasa.size() > 1) {
                    AddImpuesto();
                } else {
                    mBTodero.setMens("Debe seleccionar un valor diferente, para realizar la modificacion");
                    mBTodero.warn();
                }
            }
        } catch (Exception e) {
            if (ListImpsFact.size() >= 1) {
                AddImpuesto();
            }
        }
    }

    public void eventmodImps(String Opc) {
        int Val = 0;
        if ("Aceptar".equals(Opc)) {
            for (int j = 0; j < getListImpsFact().size(); j++) {
                Val = (int) getListImpsFact().get(j).getValue();
                //Aqui toca traer el valor del impuesto del que se va ha modificar
                for (int u = 0; u < getListImpsFact().size(); u++) {
                    String Val2 = "";
                    Val2 = getListImpsFact().get(u).getLabel();
                    if (getNombreImpuesto().equals(Val2)) {
                        SelectImps = String.valueOf(getListImpsFact().get(u).getValue());
                        break;
                    }
                    break;
                }
            }
            if (ModImp.getTasaImpuesto() == SelectTasa) {
                mBTodero.setMens("el valor a modificar debe ser diferente");
                mBTodero.info();
            } else if (ModImp.getTasaImpuesto() != getTasaImpuesto()) {
                for (int i = 0; i < listCamImpst.size(); i++) {
                    Carte = new LogCartera();
                    if (ModImp.getCodNota() == listCamImpst.get(i).getCodNota()) {
                        Carte.setCodFactura(getCambImps());
                        if (ModImp.getCodFactura() != 0) {
                            CodImps = ModImp.getCodImp();
                            //Traer por el codigo seleccionado el nombre del impuesto
                            for (int j = 0; j < getListImpsFact().size(); j++) {
                                Val = (int) getListImpsFact().get(j).getValue();
                                if (Integer.valueOf(getSelectImps()) == Val) {
                                    NombreImp = getListImpsFact().get(j).getLabel();
                                    break;
                                }
                            }
                            CargarTasa();
                            CodTasa = (int) getSelectTasa();
                        } else {
                            mBTodero.setMens("CodFactura seleccionada " + ModImp.getCodFactura());
                            mBTodero.info();
                        }
                    } else {
                        NombreImp = listCamImpst.get(i).getNombreImpuesto();
                        CodImps = listCamImpst.get(i).getCodImp();
                        CodTasa = (int) listCamImpst.get(i).getTasaImpuesto();
                    }
                    Carte.setNombreImpuesto(NombreImp);
                    Carte.setCodImp(CodImps);
                    Carte.setTasaImpuesto(CodTasa);
                    Carte.setCodNota(CodNota);
                    CodNota = CodNota + 1;
                    listCamImpstemp.add(Carte);
                }
                listCamImpst.clear();
                listCamImpst = listCamImpstemp;
            } else if (ModImp.getCodFactura() != 0) {
                CambImps = ModImp.getCodFactura();
                CargaImp();
                SelectImps = String.valueOf(ModImp.getCodImp());
                //Traer por el codigo seleccionado el nombre del impuesto
                for (int j = 0; j < getListImpsFact().size(); j++) {
                    Val = (int) getListImpsFact().get(j).getValue();
                    if (Integer.valueOf(getSelectImps()) == Val) {
                        setNombreImpuesto(getListImpsFact().get(j).getLabel());
                        break;
                    }
                }
            }
            ListImpsTasa.clear();
            CargarTasa();
            SelectTasa = ModImp.getTasaImpuesto();
        }
    }

    public void Cajas() {
        setObservNotaCre("");
        setValorRegnotaCre(0);
        setSelectFact(0);
    }

    //Cargar combobox de Facturas seleccionadas
    public ArrayList<SelectItem> OnFacturas() {
        try {
            CargarFacturas.clear();
            Iterator<LogCartera> Ite;
            Ite = getSelectPendiXCobrar().iterator();
            while (Ite.hasNext()) {
                LogCartera MBAdm = Ite.next();
                this.CargarFacturas.add(new SelectItem(MBAdm.getCodFactura(), String.valueOf(MBAdm.getCodFactura())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulTipProEnt()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.CargarFacturas;

    }

    public ArrayList<SelectItem> CargaImp() {
        try {
            Iterator<LogCartera> Ite;
            LogCartera Carte = new LogCartera();
            Ite = Carte.Impuestos(1, "0").iterator();
            while (Ite.hasNext()) {
                Carte = Ite.next();
                this.ListImpsFact.add(new SelectItem(Carte.getCodImp(), String.valueOf(Carte.getNombreImpuesto())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".CargaImp()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.ListImpsFact;
    }

//Verificar eso
    public ArrayList<SelectItem> CargarTasa() {
        try {
            ListImpsTasa.clear();
            Iterator<LogCartera> Ite;
            LogCartera Cart = new LogCartera();
            Ite = Cart.Impuestos(2, getSelectImps()).iterator();
            while (Ite.hasNext()) {
                Cart = Ite.next();
                this.ListImpsTasa.add(new SelectItem(Cart.getCodImp(), String.valueOf(Cart.getTasaImpuesto())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".CargarImpuestos()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.ListImpsTasa;
    }

    public void reset() {
        this.observMayorValor = null;
        this.mayorValor = 0;
        this.radioUno = false;
    }

    public int RegPago = 1;
    int val;

    public void RegstVariasFcat(String Prmt) {
        RegPago = 0;

// Registrar la Informacion de Mayor Menor valor a cancelar.
        /* AlertPago */
        val = getMayorValor();
        setAbono(getAbono() + val);
        setMayorValor(0);
        //Limpiar las Variables del Pop uP de Alerta realizar Pago
        RequestContext.getCurrentInstance().execute("PF('AlertPago').hide()");

//    mayorCancelar("2");
        /*         
        //Si registro Pago, se realza para varias facturas, colocar como visible el panel de la reparticion //
        //Validar que el abono realizado no supere el valor del Avaluo PENDIENTE!!
        //Condicionar si el pago el mayor o menor en la aplicacion
        if ("Aceptar".equals(Prmt)) {
            if (radioUno == true) {
                confirmar = "Aceptar";
                Cond = 1;
                GestionCartera("AceptPago");
                //Actualizar el estado del pago 
                LogCartera EstadoFact = new LogCartera();
                EstadoFact.CambiarEstados(4, String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura()));
            } else if (radioUno == false) {
                if (mayorValor < 0)
                {
                    setAbono(Abono - mayorValor);
                }
                GestionCartera("AceptPago");
                // Cargar el valor de los abonos y cargar 
                CountAbono = 3;
                GestionCartera("FactAbono");
                for (int i = 0; i < ListNotaAbonos.size(); i++) {
                    setAbonos((int) ListNotaAbonos.get(i).getValorFactura() + getAbonos());
                }
                setPendtPorPagar(getTpagar() - getAbonos());
            }
        }
         */
    }

    public void onRowSelect(SelectEvent event) {
        try {
            //Cargar la informacion  en los combobox
            if (ModImp.getCodFactura() != 0) {
                CambImps = ModImp.getCodFactura();
                SelectImps = String.valueOf(ModImp.getCodImp());
                //Traer por el codigo seleccionado el nombre del impuesto
                for (int j = 0; j < getListImpsFact().size(); j++) {
                    int Val = 0;
                    Val = (int) getListImpsFact().get(j).getValue();
                    if (Integer.valueOf(getSelectImps()) == Val) {
                        setNombreImpuesto(getListImpsFact().get(j).getLabel());
                        break;
                    }
                }
                CargarTasa();
                SelectTasa = (int) ModImp.getTasaImpuesto();
                estdCmb = true;
                NameImpsto = true;
                Lbel = "Modificar";
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo de onRowSelect ");
            mBTodero.warn();
        }
    }
    private String CadenaFact = "";
    String Var;

    public int validacion = 0;

    public void validarPago() {
        S = 1;
        Cobrar = "Proceso";
        try {
            LogCartera Cartera = new LogCartera();
            Cartera.RecbCons();
            if (getCodBanco() != 0 && getCodRecibo() != 0 && getAbono() != 0) {
                if (name.equals("Avaluo")) {
                    setNombre(Cartera.getAvaluo());
                    this.ReciboConsig = true;
                    Date fechaDate1 = Format.parse(Cartera.getFechaAva());
                    Date myDate = new Date();
                    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
                    String mdy = mdyFormat.format(myDate);
                    if (fechaPagos.after(fechaDate1) && fechaPagos.before(myDate) || fechaPagos.equals(fechaDate1) || fechaPagos.equals(myDate)) {
                    } else {
                        mBTodero.setMens("Por Favor , cambie la fecha de consignacion , ya que esta es menor  o  mayor a la del consecutivo anterior");
                        mBTodero.warn();
                        S = 0;
                        validacion++;
                    }
                } else if (name.equals("Caja")) {
                    setNombre(Cartera.getCaja());
                    this.ReciboConsig = true;
                    Date fechaDate1 = Format.parse(Cartera.getFechaCaja());
                    Date myDate = new Date();
                    SimpleDateFormat mdyFormat = new SimpleDateFormat("MM-dd-yyyy");
                    // Format the date to Strings
                    String mdy = mdyFormat.format(myDate);

                    if (fechaPagos.after(fechaDate1) && fechaPagos.before(myDate) || fechaPagos.equals(fechaDate1) || fechaPagos.equals(myDate)) {
                        validacion = 0;
                    } else {
                        mBTodero.setMens("Por Favor , cambie la fecha de consignacion , ya que esta es menor a la del consecutivo anterior");
                        mBTodero.warn();
                        validacion++;
                        S = 0;
                    }
                }
                if (S == 1) {
                    if (getListaImpuestosFacturas().size() > 1) {
                        RequestContext.getCurrentInstance().execute("PF('Pnl').show()");
                    } else {
                        GestionCartera("AceptPago");
                        CambIm = "";
                    }
                }
            } else {
                mBTodero.setMens("debe diligenciar la informacion obligatoria");
                mBTodero.warn();
            }
        } catch (Exception ex) {
            Logger.getLogger(BeanCartera.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ajusteValor() {

//Verificar aqui q hace , debe mostrar Mayor valor a cancelar o Saldo a Favor 
        RequestContext.getCurrentInstance().execute("PF('AlertPago').show()");

    }

    public void EstadoFact() {
        try {
            String EstadoFact = "";
            LogCartera Cart = new LogCartera();
            if (RegPago == 0) {
                Total = 0;
            } else {
                Total = getTpagar() - getAbono();
            }
            for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                EstadoFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + EstadoFact;
            }
            EstadoFact = EstadoFact.substring(0, EstadoFact.length() - 1);
            if (Total < 0) {
                if (radioUno == true) {
                    Cart.UpdateStatusFac(1, EstadoFact, "SP");
                } else if (radioUno == false) {
                    Cart.UpdateStatusFac(1, EstadoFact, "P");
                    estadoPago = false;
                }
            } else if (Total > 0) {
                Cart.UpdateStatusFac(1, EstadoFact, "PC");
            } else if (Total == 0) {
                if (radioUno == true) {
                    Cart.UpdateStatusFac(1, EstadoFact, "SP");
                } else {
                    Cart.UpdateStatusFac(1, EstadoFact, "P");
                    estadoPago = false;
                }

            }
        } catch (Exception e) {
            mBTodero.setMens("Error al actualizar el estado de la factura");
            mBTodero.warn();
        }
    }

    public void abrir() {
        RequestContext.getCurrentInstance().execute("PF('carDialog').show()");
    }

    //Cargar la informacion en la lista de Cargar cartera 
    public ArrayList<SelectItem> OnMediosPago() {
        try {
            Iterator<LogCartera> Ite;
            Ite = Carte.CargarMediosPago().iterator();
            while (Ite.hasNext()) {
                Carte = Ite.next();
                if (Carte.getRecibo().equalsIgnoreCase("Efectivo")) {
                    setCodTipoPago(Carte.getCodRecibo());
                }
                this.cargarTipoPago.add(new SelectItem(Carte.getCodRecibo(), String.valueOf(Carte.getRecibo())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + " causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.cargarTipoPago;
    }
    int cont = 0;

    public void Calcular() {
        //Aplico los impuestos que estan asociados a los diferentes tipos de pago, entonces si el tipo de pago es electronico,
        //por defecto se debe cargar los impuestos del 
        //Retefuente 1,5%, ReteIca 4,14% , ReteIva: 15 %
        for (int i = 0; i < listaImpuestosFacturas.size(); i++) {
            //Insercion por cada factura seleccionada la tasa de impuesto
            Carte.CambioImpuesto("AddImpsElectro", getListaImpuestosFacturas().get(i).getCodFactura(), 0, mBsesion.codigoMiSesion());
        }
        //Se realiza de nuevo el calculo para cartera con la nueva aplicacion de Impuestos
        //en el valor base de % que es el valor de la factura dividido en 1,16
        setValorElectronico(Math.round(getValorInicialFcatura() / 1.16));
        ValidarSeleccion(3);
    }

    public void CambioImpuesto(String Prmtro) {
        switch (Prmtro) {
            case "DebitoCredito":
                //Eliminar los impuesto aplicados en Cambio_impuesto, ya que este trae los mismo de Factura_Impuesto
                Carte = new LogCartera();
                for (int u = 0; u < getCargarTipoPago().size(); u++) {
                    if (getCargarTipoPago().get(u).getValue().equals(CodTipoPago)) {
                        SelectImps = String.valueOf(getCargarTipoPago().get(u).getLabel());
                        if (SelectImps.equalsIgnoreCase("debito") || SelectImps.equalsIgnoreCase("credito")) {
                            Calcular();
                            break;
                        } else {
                            cont = 0;
                            //El tipo de pago no es credito ni debito , asi que se realiza el proceso normal.
                            ValidarSeleccion(2);
                        }
                    }
                }
                break;
        }
    }
    String name = "";

    public void consRecibo() {
        // LlenarDistribPago(getListaReprtFact().size());
        //Traer la informacion de la ultima fecha de Consignacion 
        setAbono(getAbono() - getTmpMayormeno());
        Carte.FechaConsigna();
        try {
            LogCartera Cartera = new LogCartera();
            Cartera.RecbCons();
            for (int i = 0; i < getCargarRecibo().size(); i++) {
                if ((int) getCargarRecibo().get(i).getValue() == getCodRecibo()) {
                    name = getCargarRecibo().get(i).getLabel();
                    break;
                }
            }
            if (name.equals("Avaluo")) {
                setNombre(Cartera.getAvaluo());
                //  Date fechaDate1 = formateador.parse(Carte.getFechaAva());
                setFechaConsig(Carte.getFechaAva());
                this.ReciboConsig = true;
            } else if (name.equals("Caja")) {
                setNombre(Cartera.getCaja());
                setFechaConsig(Carte.getFechaCaja());
                this.ReciboConsig = true;
            }
        } catch (Exception e) {
            mBTodero.setMens(e.getMessage());
            mBTodero.info();
        }
    }

    public void reabrir() {
        setSaldoFavor(getMayorValor());
        //     RequestContext.getCurrentInstance().execute("PF('AlertPago').show()");
    }

    public void mayorCancelar(String Pmtro) {
        setValorConsig(getAbono());
        setMayorValor(getAbono() - getPendtPorPagar());
        //Prmtro : 1 cuando se necesita concelar  un valor mayor, esto hace abrir el pop up
        switch (Pmtro) {
            case "1":
                if (S == 1) {
                    RequestContext.getCurrentInstance().execute("PF('MayorCancel').show()");
                    RequestContext.getCurrentInstance().execute("PF('AlertPago').hide()");
                    // GestionCartera("AceptPago");
                }
                break;
            case "2":
                if (getListaReprtFact().size() > 1) {
                    setValorConsig(getAbono());
                    RequestContext.getCurrentInstance().execute("PF('Pnl').show()");
                    RequestContext.getCurrentInstance().execute("PF('MayorCancel').hide()");
                }   //Realizar el registro de pago , normalmente
                // GestionCartera("AceptPago");
                //Insertar la informacion del mayor valor a cancelar en Base de datos
                //Llamar la logica

                RequestContext.getCurrentInstance().execute("PF('MayorCancel').hide()");
                break;
            case "3":
                setMayorValor(0);
                observMayorValor = "";
                if (getAbono() > PendtPorPagar) {
                    RequestContext.getCurrentInstance().execute("PF('MayorCancel').hide()");
                    RequestContext.getCurrentInstance().execute("PF('AlertPago').show()");
                }
                break;
            case "4":
                setMayorValor(0);
                OpcAjusteValor = false;
                radioUno = false;
                setObservMayorValor("");
                RequestContext.getCurrentInstance().execute("PF('AlertPago').hide()");
                break;
            default:
                break;
        }
    }

    public void cargarInfoAvaluo() {
        LogActivRecord Act = new LogActivRecord();
        setListaInfoActivRecord(Act.InfoCartera(codFactRecord));
        CodAvaluo = getListaInfoActivRecord().get(0).getCodAvaluo();
        CodSeg = getListaInfoActivRecord().get(0).getCodSeg();
        NBien = getListaInfoActivRecord().get(0).getNbien();
    }

    public void ViewImpsApli() {
        setLblliva(Carte.getLblIva());
        setLblica(Carte.getLblReteIca());
        setLblreteIva(Carte.getLblReteIva());
        setLblfuente(Carte.getLblRtFuente());
    }
    LogCartera FactPago = new LogCartera();

    public LogCartera getFactPago() {
        return FactPago;
    }

    public void setFactPago(LogCartera FactPago) {
        this.FactPago = FactPago;
    }

    //Inicio de Metodos y Funcionalidades para el proceso de anulacion de Pago de Cartera 
    public void anularPago() throws SQLException {
        ClearVbls();
        //Cargar la Informacion inicial del los valores de la factura
        FactPago = new LogCartera();
        getListaDetallePago().clear();
        setListaDetallePago(FactPago.DetallePago(getSelectFactPago().get(0).getCodFactura()));
        //Cargar la Informacion de los Impuestos
        FactPago.setObservacion("cambio_impuesto");
        FactPago.setInfoXAval(2);
        setListCamImpst(FactPago.Impuestos(0, String.valueOf(getSelectFactPago().get(0).getCodFactura())));
        FactPago.InfoImpuesto(String.valueOf(getSelectFactPago().get(0).getCodFactura()));
        //Cargar la informacion de los clientes.
        InfoCliente();
        RequestContext.getCurrentInstance().execute("PF('AnulaPagos').show()");
    }

    public void InfoCliente() {
        //Traer la Informacion del Cliente (Persona a Facturar).
        setDocCliente(getSelectFactPago().get(0).getDocCliente());
        setNitEntidad(getSelectFactPago().get(0).getNitEntidad());
        setTelCliente(getSelectFactPago().get(0).getTelCliente());
        setTelEntidad(getSelectFactPago().get(0).getTelEntidad());
        setNombreCliente(getSelectFactPago().get(0).getNombrecliente());
        setNombEnt(getSelectFactPago().get(0).getNombreEntidad());
        Carte.setInfoXAval(2);
        if (getSelectFactPago().get(0).getTipoFactura().equals("Normal")) {
            setListaValoresAnular(Carte.Impuestos(1, "cambio_impuesto", String.valueOf(getSelectFactPago().get(0).getCodFactura())));
        } else if (getSelectPendiXCobrar().get(0).getTipoFactura().equals("Copia") || getSelectFactPago().get(0).getTipoFactura().equals("Concepto") || getSelectFactPago().get(0).getTipoFactura().equals("Excedente")) {
            setListaValoresAnular(Carte.ImpuestosinAval(5, "cambio_impuesto", String.valueOf(getSelectFactPago().get(0).getCodFactura())));
        }
        for (int i = 0; i < getListaValoresAnular().size(); i++) {
            //Valor de los Impuestos
            setIva(getListaValoresAnular().get(i).getIva());
            setFuente((int) (getListaValoresAnular().get(i).getRteFuente()));
            setIca(getListaValoresAnular().get(i).getRteIca());
            setReteIva(getListaValoresAnular().get(i).getRteIva());
            setValorFactura(getListaValoresAnular().get(i).getValorFactura() + getValorFactura());
            setPendtPorPagar(getListaValoresAnular().get(i).getPndPorCobrar() + getPendtPorPagar());
            setTpagar(getListaValoresAnular().get(i).getTotalPagar() + getTpagar());
            setSubtotal(getListaValoresAnular().get(i).getSubtotal() + getSubtotal());
            setAbonos(getListaValoresAnular().get(i).getAbonosAvaluo() + getAbonos());
        }
    }
    int Rslt = 0;
    int S1 = 0;

    public void quitarPago() throws SQLException {
        for (int i = 0; i < listaDetallePago.size(); i++) {
            //Tener en cuenta que este numero no lo cargo en la lista 
            if (getListaDetallePago().get(i).getCodNota() == FactPago.getCodNota()) {
                int Total = getPendtPorPagar() + getListaDetallePago().get(i).getValorFactPago();
                FactPago.ProcesoAnular("AnularPago", getListaDetallePago().get(i).getNPago(), mBsesion.codigoMiSesion(), "");
                setPendtPorPagar(Total);
                listaDetallePago.remove(i);
                S1 = 1;
                break;
            }
        }
        //Cargar la Informacion inicial del los valores de la factura
        anularPago();
        RequestContext.getCurrentInstance().execute("PF('AnulaPagos').show()");
        mBTodero.setMens("Anulacion del pago Satisfactoria");
        mBTodero.info();
    }

    public void aceptCambio() {
        FactPago = new LogCartera();
        if (getAbonos() == 0) {
            //Actualizar el estado a la factura, pendeinte por Pagar 
            FactPago.ProcesoAnular("UpdateEstado", getSelectFactPago().get(0).getCodFactura(), mBsesion.codigoMiSesion(), "PP");
        } else if (getPendtPorPagar() > getTpagar()) {
            FactPago.ProcesoAnular("UpdateEstado", getSelectFactPago().get(0).getCodFactura(), mBsesion.codigoMiSesion(), "PC");
        } else if (getPendtPorPagar() == getTpagar()) {
            FactPago.ProcesoAnular("UpdateEstado", getSelectFactPago().get(0).getCodFactura(), mBsesion.codigoMiSesion(), "P");
        }
        //Consultar de Nuevo la informacion de las Fcaturas pagadas
        getListaFactPagos().clear();
        setListaFactPagos(FactPago.ConsultaPagosFactura());
        //Aceptar la informacion de la anulacion del Pago.
        RequestContext.getCurrentInstance().execute("PF('AnulaPagos').hide()");
    }

    //Metodos Funciones para la operacion de Saldos  a Favor para el cliente.
    public void saldoFavor(String Prmtro) {
        switch (Prmtro) {
            case "Reintegro":
                RequestContext.getCurrentInstance().execute("PF('Reintegro').show()");
                break;
        }
    }

    /* Mostrar la informacion por Avaluo */
    LogCartera CarteraGrupos = new LogCartera();

    public void FactGrupoInfo(String Prmtro) throws SQLException {
        switch (Prmtro) {
            case "InfoAvaluo":
                Carte = new LogCartera();
                setListInfoAval(Carte.AvaluosGrupoInfo(getSelectObserFact().get(0).getCodFactura()));
                RequestContext.getCurrentInstance().execute("PF('InfoAval').show()");
                break;
            case "CambioIca":
                //Cambiar la lista Ciudad 
                RequestContext.getCurrentInstance().execute("PF('IcaCiudad').show()");
                break;
            case "CalcularIca":
                double band = 0;
                for (int i = 0; i < listaCiudad.size(); i++) {
                    if (!listaCiudad.get(i).getIcaAjusteMuncipio().equals(0)) {
                        Double.parseDouble(listaCiudad.get(i).getIcaAjusteMuncipio());
                        band = (Double.parseDouble(listaCiudad.get(i).getIcaAjusteMuncipio())) + band;
                    }
                }
                if (band == 0) {
                    mBTodero.setMens("Por Favor verifique la informacion del ajuste");
                    mBTodero.warn();
                } else {
                    //La factura , presenta retenciones , desea eliminar el reteica de la factura y aplicarlo al avaluo.
                    RequestContext.getCurrentInstance().execute("PF('ConfirmIca').show()");
                }
                break;
        }
    }

    public void calcularIcaCiudad() {
        double ValorAjuste = 0;
        try {
            //Validar el tema de Fechas 
            LogCartera CarteraIca = new LogCartera();
            if (S == 1) {
                //Eliminar el Ica de la Factura.
                String CodImpuesto = Carte.CambioIca("ConsImpsIca");
                CodImpuesto = CodImpuesto.substring(0, CodImpuesto.length() - 1);
                // Se elimina el impuesto del Ica asociado a la factura.
                CarteraIca.setCodBanco(Cadena());
                CarteraIca.setCuentaBanc(CodImpuesto);
                CarteraIca.CambioIca("deleteIca");
                //Traer el codigo del impuesto aplicado, por el valor de la tasa 
                for (int i = 0; i < listaInfoAvaluos.size(); i++) {
                    for (int j = 0; j < listaCiudad.size(); j++) {
                        if (listaInfoAvaluos.get(i).getCiudadAval().equals(listaCiudad.get(j).getCiudadAval())) {
                            int Rst = CarteraIca.impsporCiudad(getListaInfoAvaluos().get(i).getCodAval());
                            if (!"0".equals(listaCiudad.get(j).getIcaAjusteMuncipio())) {
                                String CodImps = CarteraIca.codImpuesto(listaCiudad.get(j).getIcaAjusteMuncipio());
                                if (!"".equals(listaCiudad.get(j).getIcaAjusteMuncipio()) || listaCiudad.get(j).getIcaAjusteMuncipio().equals(0) || listaCiudad.get(j).getIcaAjusteMuncipio().equals(null)) {
                                    ValorAjuste = Double.parseDouble(listaCiudad.get(j).getIcaAjusteMuncipio());
                                    //Conslta por codigo de avaluo si retorna 
                                    if (Rst == (getListaInfoAvaluos().get(i).getCodAval()) && CarteraIca.getCodImp() != Integer.valueOf(CodImps)) {
                                        // Haga una actualizacion del  codigo del impuesto 
                                        CarteraIca.setCodFactura(getListaInfoAvaluos().get(i).getCodFactura());
                                        CarteraIca.updateCodImps(CarteraIca.getCodImp(), getListaInfoAvaluos().get(i).getCodAval());
                                    } else if (CarteraIca.getCodImp() != Integer.valueOf(CodImps) && Rst != (getListaInfoAvaluos().get(i).getCodAval())) {
                                        CarteraIca.ImpuestoAval(getListaInfoAvaluos().get(i).getCodFactura(), getListaInfoAvaluos().get(i).getCodAval(), CodImps);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            /*
            MostrarInfo();
            setEstIcaAval(true);
            int Total = 0;
            //Sumar los retenciones  por avaluo//
            for (int i = 0; i < listaCiudad.size(); i++) {
                Total = Integer.parseInt(listaCiudad.get(i).getValorAjuste()) + Total;
                setIcaAvaltmp(Total);
            }
            setLblIcaAvaltmp("RETE ICA %");
             */
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo CalcularIca " + e.getMessage());
            mBTodero.error();
        }
    }

    public void MostrarInfo() {
        ListTemp.clear();
        CadenaFact = "";
        if (getListaCiudad().size() == 1) {
            CadenaFact = String.valueOf(getListaInfoAvaluos().get(0).getCodFactura());
        } else {
            for (int i = 0; i < getListaInfoAvaluos().size(); i++) {
                CadenaFact = String.valueOf(getListaInfoAvaluos().get(i).getCodFactura()) + "," + CadenaFact;
            }
            CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
        }
        listaCalculoIca = (ArrayList<LogCartera>) Carte.CalculoIca(CadenaFact);
        for (int i = 0; i < getListaCiudad().size(); i++) {
            Carte = new LogCartera();
            Carte.setCiudadAval(listaCalculoIca.get(i).getCiudadAval());
            Carte.setIcaAjusteMuncipio(getListaCiudad().get(i).getIcaAjusteMuncipio());
            Carte.setValorAjuste(listaCalculoIca.get(i).getValorAjuste());
            ListTemp.add(i, Carte);
        }
        listaCiudad.clear();
        listaCiudad = ListTemp;
        //Traer totales 
        setListaGruposTotalIca(Carte.InfoImpsXAval(CadenaFact));
    }

    public ArrayList<SelectItem> CargarTasaIca() {
        try {
            ListImpsIca.clear();
            Iterator<LogCartera> Ite;
            LogCartera Cart = new LogCartera();
            // 100 es el CodImpuesto para el Ica
            Ite = Cart.icaCod().iterator();
            while (Ite.hasNext()) {
                Cart = Ite.next();
                this.ListImpsIca.add(new SelectItem(Cart.getTasaImpuesto(), String.valueOf(Cart.getTasaImpuesto())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".CargarImpuestos()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.ListImpsIca;
    }

    public void impuestoporAval() {
        try {
            //Mostrar los valores adicionales y anticipos cargados por cada avaluo
            DetalleAval.ImpsXaval(String.valueOf(DtlleAval.getCodAval()));
            //Cargar impuestos por avaluo 
            setValorAvaluo(DetalleAval.getValorAvaluo());
            setLblIcaAval(DetalleAval.getLblRteIcaAval());
            setIcaAval(DetalleAval.getRteIcaAval());
            RequestContext.getCurrentInstance().execute("PF('DlgAval').show()");
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo ImpuestoporAval " + e.getMessage());
            mBTodero.info();
        }
    }

    public void onRowSelect2(SelectEvent event) {
        impuestoporAval();
    }

    // Reingenieria de Metodos :C
    //FORMULARIO CAMBIO DE IMPUESTO Y SUS METODOS
    public void CambioImps(String Prmtro) {
        switch (Prmtro) {
            case "addImps":
                try {
                    if (Lbel.equals("Modificar")) {
                        ModImpuesto("Aceptar");
                    } else {
                        S = 0;
                        AddImpuesto();
                        LogCartera Cartera = new LogCartera();
                        if (!"".equals(getSelectTasa())) {
                            Cartera.setCodImp((int) getSelectTasa());
                            Cartera.GestionCartera("InsertImpsCam", getCambImps(), 0, mBsesion.codigoMiSesion());
                        }
                        //GestionCartera("InfoImpst");
                        Limpiar(2);
                    }
                } catch (NumberFormatException e) {
                    mBTodero.setMens("Error en el metodo CambioImpuesto Opc addImps" + e.getMessage());
                    mBTodero.warn();
                }
                break;
            case "eliminar":
                try {
                    CadenaFact = "";
                    for (int i = 0; i < listCamImpst.size(); i++) {
                        if (!ModImp.getNombreImpuesto().equalsIgnoreCase("Iva")) {
                            if (getListCamImpst().get(i).getCodNota() == ModImp.getCodNota()) {
                                Var = String.valueOf(getListCamImpst().get(i).getCodImp());
                                setCambImps(getListCamImpst().get(i).getCodFactura());
                                CadenaFact = String.valueOf(getListCamImpst().get(i).getCodImp()) + "," + CadenaFact;
                                listCamImpst.remove(i);
                                break;
                            }
                        } else {
                            mBTodero.setMens("No puede remover el impuesto del Iva");
                            mBTodero.error();
                            break;
                        }
                    }
                    if (!"".equals(CadenaFact)) {
                        CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
                        LogCartera Cart = new LogCartera();
                        setObservCobro(CadenaFact);
                        ///Conocer el tipo de Pago ya sea Efectivo Debito y Credito
                        for (int u = 0; u < getCargarTipoPago().size(); u++) {
                            if (getCargarTipoPago().get(u).getValue().equals(CodTipoPago)) {
                                SelectImps = String.valueOf(getCargarTipoPago().get(u).getLabel());
                                if (SelectImps.equalsIgnoreCase("debito") || SelectImps.equalsIgnoreCase("credito")) {
                                    Cart.setObservacion(CadenaFact);
                                    Cart.GestionCartera("DleteImpsTmp", getCambImps(), 0, mBsesion.codigoMiSesion());
                                    break;
                                } else {
                                    Cart.GestionCartera("DleteImps", Integer.parseInt(getObservCobro()), CambImps, mBsesion.codigoMiSesion());
                                }
                            }
                        }

                        mBTodero.setMens("Eliminacion realizada con exito");
                        CodNota = CodNota - 1;
                        mBTodero.info();
                        Limpiar(2);
                        setNameImpsto(false);
                        estdCmb = false;
                    }
                } catch (Exception e) {
                    mBTodero.setMens("Error en el metodo '" + this.getClass() + ".remover()' causado por: " + e.getMessage());
                    mBTodero.error();
                }
                break;
            case "AplicarImps":
                try {
                    ValidarSeleccion(2);
                    RequestContext.getCurrentInstance().execute("PF('CambioImps').hide()");
                } catch (Exception e) {
                    mBTodero.setMens("Error en el metodo de Gestion Cartera abrirpop" + e.getMessage());
                    mBTodero.warn();
                }
                break;
            default:
                break;
        }
    }

    public void cambiarEstados(int Tipo, int Cant) {
        for (int i = 0; i < Cant; i++) {
            //Se cincatena la informacion para almacenarla en una unica variable
            if (Tipo == 1 || Tipo == 2) {
                CadenaFact = String.valueOf(getTempEstados().get(i).getCodFactura());
            } else if (Tipo >= 3) {
                CadenaFact = String.valueOf(getListaEstadoCartera().get(i).getCodFactura());
            }
            CadenaFact = "'" + CadenaFact + "'" + ",";
        }
        CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
        LogCartera Carte = new LogCartera();
        if (Tipo == 1) {
            //Cobro Juridico
            Carte.CambiarEstados(1, CadenaFact);
        }
        //Castigar Cartera
        if (Tipo == 2) {
            Carte.CambiarEstados(2, CadenaFact);
        }
        if (Tipo == 3) {
            Carte.CambiarEstados(3, CadenaFact);
        }
        mBTodero.setMens("Actualizacion realizada correctamente");
        mBTodero.info();
    }

    public void cancelMod(String Val) {
        estdCmb = false;
        Limpiar(2);
        Lbel = "Agregar";
        setNameImpsto(false);
        if ("Cerrar".equals(Val)) {
            RequestContext.getCurrentInstance().execute("PF('CambioImps').hide()");
        }
    }

    public String Cadena() {
        String CadenaFact = "";
        if (getSelectPendiXCobrar().size() == 1) {
            CadenaFact = String.valueOf(getSelectPendiXCobrar().get(0).getCodFactura());
        } else {
            for (int i = 0; i < getSelectPendiXCobrar().size(); i++) {
                CadenaFact = String.valueOf(getSelectPendiXCobrar().get(i).getCodFactura()) + "," + CadenaFact;
            }
            CadenaFact = CadenaFact.substring(0, CadenaFact.length() - 1);
        }
        return CadenaFact;
    }

    public void cambioTipo() {
        setValorAjuste(getTipoAjuste());
    }

    public String getCambIm() {
        return CambIm;
    }

    public void setCambIm(String CambIm) {
        this.CambIm = CambIm;
    }

}
