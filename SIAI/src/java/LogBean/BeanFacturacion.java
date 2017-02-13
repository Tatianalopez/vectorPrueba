package LogBean;

import Logic.LogAnticipo;
import Logic.LogCliente;
import Logic.LogEntidad;
import Logic.LogAdministracion;
import Logic.LogFacturacion;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import Logic.LogCartera;
import Logic.LogEnvio;
import Logic.LogRadicacion;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.Iterator;
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
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
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
 * <p>
 * Bean para la realizacion de la facturacion de los avaluos en cualquier
 * proceso.
 * </p>
 *
 * @author Johan Stiven Rodriguez Doncel
 * @author Maira Alejandra Carvajal Chaparro
 * @version 2.0
 * @since 10-10-2015 1.0.0
 *
 */
@ManagedBean(name = "MBFacturacion")
@ViewScoped
@SessionScoped
public class BeanFacturacion {

    /*Variables para la insersion*/
    boolean descuentoInser = false;
    int DescInser = 0;
    boolean distanciaInser = false;
    String tipoDescInser;
    boolean valorAdiInser = false;
    /**
     * Varibles para realizar proceso de facturación*
     */
    private double total;
    private ResultSet Dat;
    private ResultSet Dat2;
    private int selecAvaLiq;
    private String tipoLiqui = "";
    private double reteiva;
    private String TipoFacturacion;
    private boolean OpcDistancia;
    private boolean OpcDescuentos;
    private boolean OpcDescuentos2;
    private boolean opcIva1;
    private boolean cambiarFecha;
    private boolean opcIva2;
    private boolean opcIva3;
    private boolean opcIva4;
    private boolean opcIva5;
    private boolean OpcValAdc;
    private boolean OpcValAdc2 = false;
    private boolean OpcValAdc3 = false;
    private boolean OpcValAdc4 = false;
    private boolean OpcValAdc5 = false;
    private int valorAnticipos = 0;
    private double valorDescuentos = 0;
    private String tipoDescuento = "";
    private int valorAnti = 0;
    private String NombreTable;
    private int CodTabla;
    double ValorPac = 0;
    double iva = 0;
    double subtotal = 0;
    private boolean cambValorPac = false;
    private boolean camTablaParti = false;
    double cambiarAPac = 0;
    private List listReteIca = null;//Variable para la seleccion de informacion del producto Entidad
    private String detalleFactura = "";
    private boolean opcReteIva = false;
    private boolean opcRetefuente = false;
    private boolean opcDesaprueba = false;
    private boolean opcReteIca = false;
    private double descuento = 0;
    private String valComerBig;

    private int valAdiFacIva = 0;
    private int valAdiFacSinIva = 0;

    private int valorTotalTabla = 0;

    List<LogFacturacion> ListTablaDistancias = new ArrayList<>();
    List<LogFacturacion> ListTablaDescuentos = new ArrayList<>();
    List<LogAnticipo> ListTablaAnticipos = new ArrayList<>();
    List<LogFacturacion> ListTablaValAdici = new ArrayList<>();
    List<LogFacturacion> ListTablaPacValor = new ArrayList<>();
    List<LogFacturacion> ListTablaLiqTabla = new ArrayList<>();
    LogFacturacion selecDist = new LogFacturacion();
    LogFacturacion selecDes = new LogFacturacion();
    LogFacturacion selecVaAdi = new LogFacturacion();
    LogFacturacion selecAvaLiqTabla = new LogFacturacion();
    LogFacturacion selecVerTabla = new LogFacturacion();

    private int FactLiberar = 0;
    List<LogFacturacion> listAvalAscdos = null;
    private List<LogFacturacion> SelectListaAvaltAnt;
    //Cajas de raparticion 
    private int cajaRepar1 = 0;
    private int cajaRepar2 = 0;
    private int cajaRepar3 = 0;
    private int cajaRepar4 = 0;
    private int cajaRepar5 = 0;
    private double ParticionFactura = 0;
    private boolean calculo = false;
    private String simbolo = "";
    private int sumaFacAnt;
    private int selectNAva;
    private int selectNAvaDes;
    private int selectNAvaVaAdi;
    private Date fechaFacturacion;

    //Cajas anticipos
    private int anticipo1 = 0;
    private int anticipo2 = 0;
    private int anticipo3 = 0;
    private int anticipo4 = 0;
    private int anticipo5 = 0;
    //ica
    private double ica1 = 0;

    private String radioUno = "";
    private String visible1 = "N";
    private String visible2 = "N";
    private String visible3 = "N";
    private String visible4 = "N";
    private String visible5 = "N";
    //ReteFuente
    private double fuente1 = 0;

    //clientes
    private int cli1 = 0;
    private int cli2 = 0;
    private int cli3 = 0;
    //Parametros
    private int codParametro = 0;
    private int codParametro2 = 0;
    private int codParametro3 = 0;
    private int codParametro4 = 0;
    private int codParametro5 = 0;

    //Asignacion de Subtotal 
    private double AsignTotal1 = 0;
    private int AsignTotal2 = 0;
    private int AsignTotal3 = 0;
    private int AsignTotal4 = 0;
    private int AsignTotal5 = 0;

    //porcentualizacion prefactura
    private double AsigDescuento1 = 0;
    private int AsigDescuento2 = 0;
    private int AsigDescuento3 = 0;
    private int AsigDescuento4 = 0;
    private int AsigDescuento5 = 0;

    private double AsigValorAdicional1 = 0;
    private int AsigValorAdicional2 = 0;
    private int AsigValorAdicional3 = 0;
    private int AsigValorAdicional4 = 0;
    private int AsigValorAdicional5 = 0;
    private boolean validarPanel = false;

    private String observacionCambio;
    private boolean validacion = false;
    List<LogEntidad> ListaEntidadesR = new ArrayList<>();
    List<LogCliente> ListaClientesR = new ArrayList<>();
    List<LogFacturacion> ListConsulValorAddFactura = new ArrayList<>();
    //Facturacion Anticipo
    private String OpcionFacturarA;
    private int ValorFactura;
    private int IvaAnticp;
    private int TotalIvaAnti = 0;
    private double totalpagarFactAnt = 0;
    private String ObserFactAnti = "";
    private int nfactAnticpo = 0;
    private String opcAplicarIva = "";
    private String estadoPnlIva;
    List<LogCartera> listaObservaciones = new ArrayList<>();

    //Tatiana 28/03/2016
    //Facturacion Concepto
    private int CodTipoConcepto = 0;
    private Date Fecha_Inicio;
    private String fechaConsignacion;

    //Tatiana 01/04/2016
    //Anular Factura
    private String opcReempl = "";

    //Tatiana 07/08/2016
    //Factura Copia
    private int ValorAdicion;
    private int ValorAdicionSinIva;
    private int NavaluoCopia = 0;
    private boolean estadoInfoAval;
    List<LogFacturacion> ListInfoImpri = null;
    private List<LogFacturacion> observaciones = null;
    List<LogFacturacion> listaDetallesFac = new ArrayList<>();
    List<LogFacturacion> listaInfoFactura = new ArrayList<>();
    List<LogFacturacion> listaFacturaAnticipo = new ArrayList<>();
    List<LogFacturacion> SelectlistaFactAnticipos = new ArrayList<>();
    List<LogFacturacion> listaDesc = new ArrayList<>();
    List<LogFacturacion> listaValoAdi = new ArrayList<>();
    private int distancia;
    private ArrayList<SelectItem> cargarNumAvalu = new ArrayList<>();
    private int exento = 0;
    private int TotalValAdic;
    int valorTotal = 0;
    String AplicarIvaValAd;
    private int SelValAdic;
    int Apply_Iva = 0;
    private int ValorAdic;
    private int NumPendiFac;
    private int NumPendiImpr;
    private int NumPendiFacEx;
    private int NumPendiActRec;

    public int getNumPendiFacEx() {
        return NumPendiFacEx;
    }

    public void setNumPendiFacEx(int NumPendiFacEx) {
        this.NumPendiFacEx = NumPendiFacEx;
    }

    public int getNumPendiFac() {
        return NumPendiFac;
    }

    public void setNumPendiFac(int NumPendiFac) {
        this.NumPendiFac = NumPendiFac;
    }

    public int getNumPendiImpr() {
        return NumPendiImpr;
    }

    public void setNumPendiImpr(int NumPendiImpr) {
        this.NumPendiImpr = NumPendiImpr;
    }

    public int getNumPendiActRec() {
        return NumPendiActRec;
    }

    public void setNumPendiActRec(int NumPendiActRec) {
        this.NumPendiActRec = NumPendiActRec;
    }

    public List<LogFacturacion> getListaValoAdi() {
        return listaValoAdi;
    }

    public void setListaValoAdi(List<LogFacturacion> listaValoAdi) {
        this.listaValoAdi = listaValoAdi;
    }

    public int getTotalValAdic() {
        return TotalValAdic;
    }

    public void setTotalValAdic(int TotalValAdic) {
        this.TotalValAdic = TotalValAdic;
    }

    public int getSelValAdic() {
        return SelValAdic;
    }

    public void setSelValAdic(int SelValAdic) {
        this.SelValAdic = SelValAdic;
    }

    public int getValorAdic() {
        return ValorAdic;
    }

    public void setValorAdic(int ValorAdic) {
        this.ValorAdic = ValorAdic;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public int getExento() {
        return exento;
    }

    public void setExento(int exento) {
        this.exento = exento;
    }

    public boolean isValidarPanel() {
        return validarPanel;
    }

    public void setValidarPanel(boolean validarPanel) {
        this.validarPanel = validarPanel;
    }

    public List<LogFacturacion> getListTablaLiqTabla() {
        return ListTablaLiqTabla;
    }

    public void setListTablaLiqTabla(List<LogFacturacion> ListTablaLiqTabla) {
        this.ListTablaLiqTabla = ListTablaLiqTabla;
    }

    public int getDistancia() {
        return distancia;
    }

    public LogFacturacion getSelecDes() {
        return selecDes;
    }

    public void setSelecDes(LogFacturacion selecDes) {
        this.selecDes = selecDes;
    }

    public List<LogAnticipo> getListTablaAnticipos() {
        return ListTablaAnticipos;
    }

    public String getValComerBig() {
        return valComerBig;
    }

    public void setValComerBig(String valComerBig) {
        this.valComerBig = valComerBig;
    }

    public void setListTablaAnticipos(List<LogAnticipo> ListTablaAnticipos) {
        this.ListTablaAnticipos = ListTablaAnticipos;
    }

    public double getReteiva() {
        return reteiva;
    }

    public void setReteiva(double reteiva) {
        this.reteiva = reteiva;
    }

    public List<LogFacturacion> getListTablaPacValor() {
        return ListTablaPacValor;
    }

    public void setListTablaPacValor(List<LogFacturacion> ListTablaPacValor) {
        this.ListTablaPacValor = ListTablaPacValor;
    }

    public int getCli1() {
        return cli1;
    }

    public void setCli1(int cli1) {
        this.cli1 = cli1;
    }

    public int getCli2() {
        return cli2;
    }

    public void setCli2(int cli2) {
        this.cli2 = cli2;
    }

    public int getCli3() {
        return cli3;
    }

    public void setCli3(int cli3) {
        this.cli3 = cli3;
    }

    public LogFacturacion getSelecVaAdi() {
        return selecVaAdi;
    }

    public void setSelecVaAdi(LogFacturacion selecVaAdi) {
        this.selecVaAdi = selecVaAdi;
    }

    public int getSelectNAvaDes() {
        return selectNAvaDes;
    }

    public void setSelectNAvaDes(int selectNAvaDes) {
        this.selectNAvaDes = selectNAvaDes;
    }

    public int getSelectNAvaVaAdi() {
        return selectNAvaVaAdi;
    }

    public void setSelectNAvaVaAdi(int selectNAvaVaAdi) {
        this.selectNAvaVaAdi = selectNAvaVaAdi;
    }

    public List<LogFacturacion> getListTablaDescuentos() {
        return ListTablaDescuentos;
    }

    public void setListTablaDescuentos(List<LogFacturacion> ListTablaDescuentos) {
        this.ListTablaDescuentos = ListTablaDescuentos;
    }

    public List<LogFacturacion> getListTablaValAdici() {
        return ListTablaValAdici;
    }

    public void setListTablaValAdici(List<LogFacturacion> ListTablaValAdici) {
        this.ListTablaValAdici = ListTablaValAdici;
    }

    public void setDistancia(int distancia) {
        this.distancia = distancia;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public ArrayList<SelectItem> getCargarNumAvalu() {
        return cargarNumAvalu;
    }

    public int getSelectNAva() {
        return selectNAva;
    }

    public List<LogFacturacion> getListTablaDistancias() {
        return ListTablaDistancias;
    }

    public void setListTablaDistancias(List<LogFacturacion> ListTablaDistancias) {
        this.ListTablaDistancias = ListTablaDistancias;
    }

    public void setSelectNAva(int selectNAva) {
        this.selectNAva = selectNAva;
    }

    public void setCargarNumAvalu(ArrayList<SelectItem> cargarNumAvalu) {
        this.cargarNumAvalu = cargarNumAvalu;
    }

    public List<LogFacturacion> getSelectlistaFactAnticipos() {
        return SelectlistaFactAnticipos;
    }

    public void setSelectlistaFactAnticipos(List<LogFacturacion> SelectlistaFactAnticipos) {
        this.SelectlistaFactAnticipos = SelectlistaFactAnticipos;
    }

    public List<LogFacturacion> getListaDetallesFac() {
        return listaDetallesFac;
    }

    public String getFechaConsignacion() {
        return fechaConsignacion;
    }

    public int getValAdiFacIva() {
        return valAdiFacIva;
    }

    public void setValAdiFacIva(int valAdiFacIva) {
        this.valAdiFacIva = valAdiFacIva;
    }

    public int getValAdiFacSinIva() {
        return valAdiFacSinIva;
    }

    public void setValAdiFacSinIva(int valAdiFacSinIva) {
        this.valAdiFacSinIva = valAdiFacSinIva;
    }

    public int getSumaFacAnt() {
        return sumaFacAnt;
    }

    public void setSumaFacAnt(int sumaFacAnt) {
        this.sumaFacAnt = sumaFacAnt;
    }

    public void setFechaConsignacion(String fechaConsignacion) {
        this.fechaConsignacion = fechaConsignacion;
    }

    public List<LogFacturacion> getListaDesc() {
        return listaDesc;
    }

    public List<LogFacturacion> getListaInfoFactura() {
        return listaInfoFactura;
    }

    public List<LogFacturacion> getListaFacturaAnticipo() {
        return listaFacturaAnticipo;
    }

    public void setListaFacturaAnticipo(List<LogFacturacion> listaFacturaAnticipo) {
        this.listaFacturaAnticipo = listaFacturaAnticipo;
    }

    public String getTipoDescuento() {
        return tipoDescuento;
    }

    public void setTipoDescuento(String tipoDescuento) {
        this.tipoDescuento = tipoDescuento;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getTipoLiqui() {
        return tipoLiqui;
    }

    public void setTipoLiqui(String tipoLiqui) {
        this.tipoLiqui = tipoLiqui;
    }

    public void setListaInfoFactura(List<LogFacturacion> listaInfoFactura) {
        this.listaInfoFactura = listaInfoFactura;
    }

    public void setListaDesc(List<LogFacturacion> listaDesc) {
        this.listaDesc = listaDesc;
    }

    public void setListaDetallesFac(List<LogFacturacion> listaDetallesFac) {
        this.listaDetallesFac = listaDetallesFac;
    }

    public double getAsigDescuento1() {
        return AsigDescuento1;
    }

    public void setAsigDescuento1(double AsigDescuento1) {
        this.AsigDescuento1 = AsigDescuento1;
    }

    public int getAsigDescuento2() {
        return AsigDescuento2;
    }

    public Date getFechaFacturacion() {
        return fechaFacturacion;
    }

    public void setFechaFacturacion(Date fechaFacturacion) {
        this.fechaFacturacion = fechaFacturacion;
    }

    public List<LogFacturacion> getListConsulValorAddFactura() {
        return ListConsulValorAddFactura;
    }

    public void setListConsulValorAddFactura(List<LogFacturacion> ListConsulValorAddFactura) {
        this.ListConsulValorAddFactura = ListConsulValorAddFactura;
    }

    public void setAsigDescuento2(int AsigDescuento2) {
        this.AsigDescuento2 = AsigDescuento2;
    }

    public int getAsigDescuento3() {
        return AsigDescuento3;
    }

    public boolean isCambiarFecha() {
        return cambiarFecha;
    }

    public void setCambiarFecha(boolean cambiarFecha) {
        this.cambiarFecha = cambiarFecha;
    }

    public void setAsigDescuento3(int AsigDescuento3) {
        this.AsigDescuento3 = AsigDescuento3;
    }

    public int getFactLiberar() {
        return FactLiberar;
    }

    public void setFactLiberar(int FactLiberar) {
        this.FactLiberar = FactLiberar;
    }

    public List<LogFacturacion> getSelectListaAvaltAnt() {
        return SelectListaAvaltAnt;
    }

    public void setSelectListaAvaltAnt(List<LogFacturacion> SelectListaAvaltAnt) {
        this.SelectListaAvaltAnt = SelectListaAvaltAnt;
    }

    public int getAsigDescuento4() {
        return AsigDescuento4;
    }

    public LogFacturacion getSelecVerTabla() {
        return selecVerTabla;
    }

    public void setSelecVerTabla(LogFacturacion selecVerTabla) {
        this.selecVerTabla = selecVerTabla;
    }

    public void setAsigDescuento4(int AsigDescuento4) {
        this.AsigDescuento4 = AsigDescuento4;
    }

    public int getAsigDescuento5() {
        return AsigDescuento5;
    }

    public void setAsigDescuento5(int AsigDescuento5) {
        this.AsigDescuento5 = AsigDescuento5;
    }

    public double getAsigValorAdicional1() {
        return AsigValorAdicional1;
    }

    public double getAsigValorAdicional2() {
        return AsigValorAdicional2;
    }

    public void setAsigValorAdicional2(int AsigValorAdicional2) {
        this.AsigValorAdicional2 = AsigValorAdicional2;
    }

    public void setAsigValorAdicional1(double AsigValorAdicional1) {
        this.AsigValorAdicional1 = AsigValorAdicional1;
    }

    public int getAsigValorAdicional3() {
        return AsigValorAdicional3;
    }

    public void setAsigValorAdicional3(int AsigValorAdicional3) {
        this.AsigValorAdicional3 = AsigValorAdicional3;
    }

    public int getSelecAvaLiq() {
        return selecAvaLiq;
    }

    public void setSelecAvaLiq(int selecAvaLiq) {
        this.selecAvaLiq = selecAvaLiq;
    }

    public int getAsigValorAdicional4() {
        return AsigValorAdicional4;
    }

    public void setAsigValorAdicional4(int AsigValorAdicional4) {
        this.AsigValorAdicional4 = AsigValorAdicional4;
    }

    public int getAsigValorAdicional5() {
        return AsigValorAdicional5;
    }

    public LogFacturacion getSelecAvaLiqTabla() {
        return selecAvaLiqTabla;
    }

    public void setSelecAvaLiqTabla(LogFacturacion selecAvaLiqTabla) {
        this.selecAvaLiqTabla = selecAvaLiqTabla;
    }

    public void setAsigValorAdicional5(int AsigValorAdicional5) {
        this.AsigValorAdicional5 = AsigValorAdicional5;
    }

    public List<LogFacturacion> getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(List<LogFacturacion> observaciones) {
        this.observaciones = observaciones;
    }

    public boolean isOpcReteIva() {
        return opcReteIva;
    }

    public int getValorAdicionSinIva() {
        return ValorAdicionSinIva;
    }

    public String getDetalleFactura() {
        return detalleFactura;
    }

    public void setDetalleFactura(String detalleFactura) {
        this.detalleFactura = detalleFactura;
    }

    public void setValorAdicionSinIva(int ValorAdicionSinIva) {
        this.ValorAdicionSinIva = ValorAdicionSinIva;
    }

    public void setOpcReteIva(boolean opcReteIva) {
        this.opcReteIva = opcReteIva;
    }

    public boolean isOpcRetefuente() {
        return opcRetefuente;
    }

    public void setOpcRetefuente(boolean opcRetefuente) {
        this.opcRetefuente = opcRetefuente;
    }

    public boolean isOpcReteIca() {
        return opcReteIca;
    }

    public void setOpcReteIca(boolean opcReteIca) {
        this.opcReteIca = opcReteIca;
    }

    public boolean isOpcIva1() {
        return opcIva1;
    }

    public void setOpcIva1(boolean opcIva1) {
        this.opcIva1 = opcIva1;
    }

    public boolean isOpcIva2() {
        return opcIva2;
    }

    public void setOpcIva2(boolean opcIva2) {
        this.opcIva2 = opcIva2;
    }

    public boolean isOpcIva3() {
        return opcIva3;
    }

    public void setOpcIva3(boolean opcIva3) {
        this.opcIva3 = opcIva3;
    }

    public boolean isOpcIva4() {
        return opcIva4;
    }

    public void setOpcIva4(boolean opcIva4) {
        this.opcIva4 = opcIva4;
    }

    public boolean isOpcIva5() {
        return opcIva5;
    }

    public void setOpcIva5(boolean opcIva5) {
        this.opcIva5 = opcIva5;
    }

    public List<LogFacturacion> getListInfoImpri() {
        return ListInfoImpri;
    }

    public void setListInfoImpri(List<LogFacturacion> ListInfoImpri) {
        this.ListInfoImpri = ListInfoImpri;
    }
    List<LogFacturacion> ListPendientXImpri = null;

    public List<LogFacturacion> getListPendientXImpri() {
        return ListPendientXImpri;
    }

    public void setListPendientXImpri(List<LogFacturacion> ListPendientXImpri) {
        this.ListPendientXImpri = ListPendientXImpri;
    }

    public boolean isEstadoInfoAval() {
        return estadoInfoAval;
    }

    public void setEstadoInfoAval(boolean estadoInfoAval) {
        this.estadoInfoAval = estadoInfoAval;
    }

    public int getValorAdicion() {
        return ValorAdicion;
    }

    public void setValorAdicion(int ValorAdicion) {
        this.ValorAdicion = ValorAdicion;
    }

    public String getRadioUno() {
        return radioUno;
    }

    public void setRadioUno(String radioUno) {
        this.radioUno = radioUno;
    }

    public String getVisible1() {
        return visible1;
    }

    public void setVisible1(String visible1) {
        this.visible1 = visible1;
    }

    public String getVisible2() {
        return visible2;
    }

    public void setVisible2(String visible2) {
        this.visible2 = visible2;
    }

    public String getVisible3() {
        return visible3;
    }

    public void setVisible3(String visible3) {
        this.visible3 = visible3;
    }

    public String getVisible4() {
        return visible4;
    }

    public void setVisible4(String visible4) {
        this.visible4 = visible4;
    }

    public String getVisible5() {
        return visible5;
    }

    public void setVisible5(String visible5) {
        this.visible5 = visible5;
    }

    public int getCodParametro5() {
        return codParametro5;
    }

    public void setCodParametro5(int codParametro5) {
        this.codParametro5 = codParametro5;
    }

    public int getNavaluoCopia() {
        return NavaluoCopia;
    }

    public void setNavaluoCopia(int NavaluoCopia) {
        this.NavaluoCopia = NavaluoCopia;
    }

    /**
     * Listas*
     */
    List<LogFacturacion> ListPendientXFact = null;
    List<LogFacturacion> ListValAdiAva = null;
    List<LogFacturacion> selecListValAdiAva = null;
    List<LogFacturacion> ListDistancia = null;
    List<LogFacturacion> selecListDistancia = null;
    List<LogFacturacion> selectListPendientXFact = null;
    List<LogFacturacion> ListTablaAplicada = null;
    List<LogFacturacion> ListLiquidacion = null;
    List<LogFacturacion> ListaDistanciaTablas = null;
    List<LogAnticipo> ListaAnticiposFac = null;
    List<LogFacturacion> ListTablaParti = null;
    List<LogFacturacion> ListTablaDistCambio = null;
    LogCartera Cart = new LogCartera();
    List<LogFacturacion> ListTablasFacturac = null;
    List<LogFacturacion> ListTablasFacturacTarifas = new ArrayList<>();
    List<LogFacturacion> ListTablasImpuestos = null;
    List<LogFacturacion> ListaAnticipos = null;
    List<LogAnticipo> ListaAnticiposPop = new ArrayList<>();
    List<LogFacturacion> ListaValorAdicional = new ArrayList<>();
    ArrayList<SelectItem> ListTiposImpuestos = new ArrayList<>();
    private ArrayList<SelectItem> CargaRegimen = new ArrayList<>();

    public List<LogFacturacion> getListLiquidacion() {
        return ListLiquidacion;
    }

    public void setListLiquidacion(List<LogFacturacion> ListLiquidacion) {
        this.ListLiquidacion = ListLiquidacion;
    }

    public List<LogFacturacion> getListTablaDistCambio() {
        return ListTablaDistCambio;
    }

    public void setListTablaDistCambio(List<LogFacturacion> ListTablaDistCambio) {
        this.ListTablaDistCambio = ListTablaDistCambio;
    }

    public List<LogFacturacion> getListDistancia() {
        return ListDistancia;
    }

    public void setListDistancia(List<LogFacturacion> ListDistancia) {
        this.ListDistancia = ListDistancia;
    }

    public List<LogFacturacion> getSelecListDistancia() {
        return selecListDistancia;
    }

    public void setSelecListDistancia(List<LogFacturacion> selecListDistancia) {
        this.selecListDistancia = selecListDistancia;
    }

    public List<LogFacturacion> getSelecListValAdiAva() {
        return selecListValAdiAva;
    }

    public void setSelecListValAdiAva(List<LogFacturacion> selecListValAdiAva) {
        this.selecListValAdiAva = selecListValAdiAva;
    }

    public List<LogFacturacion> getListValAdiAva() {
        return ListValAdiAva;
    }

    public void setListValAdiAva(List<LogFacturacion> ListValAdiAva) {
        this.ListValAdiAva = ListValAdiAva;
    }

    public String getOpcReempl() {
        return opcReempl;
    }

    public void setOpcReempl(String opcReempl) {
        this.opcReempl = opcReempl;
    }

    public List<LogFacturacion> getSelectListPendientXFact() {
        return selectListPendientXFact;
    }

    public void setSelectListPendientXFact(List<LogFacturacion> selectListPendientXFact) {
        this.selectListPendientXFact = selectListPendientXFact;
    }

    public String getEstadoPnlIva() {
        return estadoPnlIva;
    }

    public void setEstadoPnlIva(String estadoPnlIva) {
        this.estadoPnlIva = estadoPnlIva;
    }

    public String getObserFactAnti() {
        return ObserFactAnti;
    }

    public void setObserFactAnti(String ObserFactAnti) {
        this.ObserFactAnti = ObserFactAnti;
    }

    public String getOpcAplicarIva() {
        return opcAplicarIva;
    }

    public int getCodParametro() {
        return codParametro;
    }

    public void setCodParametro(int codParametro) {
        this.codParametro = codParametro;
    }

    public int getCodParametro2() {
        return codParametro2;
    }

    public void setCodParametro2(int codParametro2) {
        this.codParametro2 = codParametro2;
    }

    public int getCodParametro3() {
        return codParametro3;
    }

    public void setCodParametro3(int codParametro3) {
        this.codParametro3 = codParametro3;
    }

    public int getCodParametro4() {
        return codParametro4;
    }

    public void setCodParametro4(int codParametro4) {
        this.codParametro4 = codParametro4;
    }

    public void setOpcAplicarIva(String opcAplicarIva) {
        this.opcAplicarIva = opcAplicarIva;
    }

    //Tatiana 31/03/2016
    public int getTotalIvaAnti() {
        return TotalIvaAnti;
    }

    public void setTotalIvaAnti(int TotalIvaAnti) {
        this.TotalIvaAnti = TotalIvaAnti;
    }

    //Tatiana 23/03/16
    List<LogFacturacion> listaImpuesto = null;
    List<LogCliente> listPersFacAnti = new ArrayList<>();

    public List<LogFacturacion> getListaImpuesto() {
        return listaImpuesto;
    }

    public void setListaImpuesto(List<LogFacturacion> listaImpuesto) {
        this.listaImpuesto = listaImpuesto;
    }

    public List<LogCliente> getListPersFacAnti() {
        return listPersFacAnti;
    }

    public void setListPersFacAnti(List<LogCliente> listPersFacAnti) {
        this.listPersFacAnti = listPersFacAnti;
    }

    /**
     * Variables que se conectan a las diferentes clases de el proyecto
     * NegociosISA > Logic*
     */
    LogFacturacion Fac = new LogFacturacion();
    LogEnvio Envio = new LogEnvio();
    LogFacturacion Factu = new LogFacturacion();
    LogFacturacion FacTablas = new LogFacturacion();
    LogFacturacion FacTablasTarifas = new LogFacturacion();
    LogAnticipo Ant = new LogAnticipo();
    LogFacturacion AdmTablasFact = new LogFacturacion();
    LogFacturacion AdmTablasImpues = new LogFacturacion();
    LogFacturacion AdmTablasFactTarifas = new LogFacturacion();
    LogAdministracion Adm = new LogAdministracion();

    LogCliente ClientesAFact = new LogCliente();
    LogEntidad EntidadesAFact = new LogEntidad();
    private List<LogFacturacion> selectListFactAscd;
    int MaxFact = 0;
    private int TipoSelPers = 0;
    List<LogFacturacion> listFactAscdas = null;
    private List<LogFacturacion> SelectListaFactAnt;

    double valorparticion = 0;
    double valorparticion2 = 0;
    double valorparticion3 = 0;
    double valorparticion4 = 0;
    double valorparticion5 = 0;

    public LogFacturacion getFacTablasTarifas() {
        return FacTablasTarifas;
    }

    public void setFacTablasTarifas(LogFacturacion FacTablasTarifas) {
        this.FacTablasTarifas = FacTablasTarifas;
    }

    public int getTipoSelPers() {
        return TipoSelPers;
    }

    public void setTipoSelPers(int TipoSelPers) {
        this.TipoSelPers = TipoSelPers;
    }

    public List<LogFacturacion> getListFactAscdas() {
        return listFactAscdas;
    }

    public void setListFactAscdas(List<LogFacturacion> listFactAscdas) {
        this.listFactAscdas = listFactAscdas;
    }

    public List<LogFacturacion> getSelectListaFactAnt() {
        return SelectListaFactAnt;
    }

    public void setSelectListaFactAnt(List<LogFacturacion> SelectListaFactAnt) {
        this.SelectListaFactAnt = SelectListaFactAnt;
    }

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

    @ManagedProperty("#{MBArchivos}")
    private BeanArchivos mBArchivos;

    public BeanArchivos getmBArchivos() {
        return mBArchivos;
    }

    public void setmBArchivos(BeanArchivos mBArchivos) {
        this.mBArchivos = mBArchivos;
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
    public int getNfactAnticpo() {
        return nfactAnticpo;
    }

    public int getAnticipo1() {
        return anticipo1;
    }

    public void setAnticipo1(int anticipo1) {
        this.anticipo1 = anticipo1;
    }

    public int getAnticipo2() {
        return anticipo2;
    }

    public void setAnticipo2(int anticipo2) {
        this.anticipo2 = anticipo2;
    }

    public int getAnticipo3() {
        return anticipo3;
    }

    public void setAnticipo3(int anticipo3) {
        this.anticipo3 = anticipo3;
    }

    public int getAnticipo4() {
        return anticipo4;
    }

    public int getValorTotalTabla() {
        return valorTotalTabla;
    }

    public void setValorTotalTabla(int valorTotalTabla) {
        this.valorTotalTabla = valorTotalTabla;
    }

    public void setAnticipo4(int anticipo4) {
        this.anticipo4 = anticipo4;
    }

    public int getAnticipo5() {
        return anticipo5;
    }

    public void setAnticipo5(int anticipo5) {
        this.anticipo5 = anticipo5;
    }

    public List<LogFacturacion> getSelectListFactAscd() {
        return selectListFactAscd;
    }

    public void setSelectListFactAscd(List<LogFacturacion> selectListFactAscd) {
        this.selectListFactAscd = selectListFactAscd;
    }

    public void setNfactAnticpo(int nfactAnticpo) {
        this.nfactAnticpo = nfactAnticpo;
    }

    //Tatiana 28/03/2016
    public boolean isCalculo() {
        return calculo;
    }

    public void setCalculo(boolean calculo) {
        this.calculo = calculo;
    }

    public double getTotalpagarFactAnt() {
        return totalpagarFactAnt;
    }

    public void setTotalpagarFactAnt(double totalpagarFactAnt) {
        this.totalpagarFactAnt = totalpagarFactAnt;
    }

    public int getCodTipoConcepto() {
        return CodTipoConcepto;
    }

    public void setCodTipoConcepto(int CodTipoConcepto) {
        this.CodTipoConcepto = CodTipoConcepto;
    }

    //Tatiana 23/03/16
    public int getValorFactura() {
        return ValorFactura;
    }

    public void setValorFactura(int ValorFactura) {
        this.ValorFactura = ValorFactura;
    }

    public int getIvaAnticp() {
        return IvaAnticp;
    }

    public List<LogCartera> getListaObservaciones() {
        return listaObservaciones;
    }

    public void setListaObservaciones(List<LogCartera> listaObservaciones) {
        this.listaObservaciones = listaObservaciones;
    }

    public void setIvaAnticp(int IvaAnticp) {
        this.IvaAnticp = IvaAnticp;
    }

//Tatiana
    public String getOpcionFacturarA() {
        return OpcionFacturarA;
    }

    public void setOpcionFacturarA(String OpcionFacturarA) {
        this.OpcionFacturarA = OpcionFacturarA;
    }

    public double getAsignTotal1() {
        return AsignTotal1;
    }

    public void setAsignTotal1(double AsignTotal1) {
        this.AsignTotal1 = AsignTotal1;
    }

    public int getAsignTotal2() {
        return AsignTotal2;
    }

    public void setAsignTotal2(int AsignTotal2) {
        this.AsignTotal2 = AsignTotal2;
    }

    public int getAsignTotal3() {
        return AsignTotal3;
    }

    public void setAsignTotal3(int AsignTotal3) {
        this.AsignTotal3 = AsignTotal3;
    }

    public int getAsignTotal4() {
        return AsignTotal4;
    }

    public void setAsignTotal4(int AsignTotal4) {
        this.AsignTotal4 = AsignTotal4;
    }

    public int getAsignTotal5() {
        return AsignTotal5;
    }

    public void setAsignTotal5(int AsignTotal5) {
        this.AsignTotal5 = AsignTotal5;
    }

    public double getFuente1() {
        return fuente1;
    }

    public void setFuente1(double fuente1) {
        this.fuente1 = fuente1;
    }

    public double getIca1() {
        return ica1;
    }

    public void setIca1(double ica1) {
        this.ica1 = ica1;
    }

    public List getListReteIca() {
        return listReteIca;
    }

    public void setListReteIca(List listReteIca) {
        this.listReteIca = listReteIca;
    }

    public List<LogFacturacion> getListTablaParti() {
        return ListTablaParti;
    }

    public void setListTablaParti(List<LogFacturacion> ListTablaParti) {
        this.ListTablaParti = ListTablaParti;
    }

    public String getObservacionCambio() {
        return observacionCambio;
    }

    public void setObservacionCambio(String observacionCambio) {
        this.observacionCambio = observacionCambio;
    }

    public double getCambiarAPac() {
        return cambiarAPac;
    }

    public void setCambiarAPac(double cambiarAPac) {
        this.cambiarAPac = cambiarAPac;
    }

    public boolean isCambValorPac() {
        return cambValorPac;
    }

    public void setCambValorPac(boolean cambValorPac) {
        this.cambValorPac = cambValorPac;
    }

    public double getParticionFactura() {
        return ParticionFactura;
    }

    public void setParticionFactura(double ParticionFactura) {
        this.ParticionFactura = ParticionFactura;
    }

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

    public boolean isCamTablaParti() {
        return camTablaParti;
    }

    public void setCamTablaParti(boolean camTablaParti) {
        this.camTablaParti = camTablaParti;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getIva() {
        return iva;
    }

    public void setIva(double iva) {
        this.iva = iva;
    }

    public double getValorPac() {
        return ValorPac;
    }

    public void setValorPac(double ValorPac) {
        this.ValorPac = ValorPac;
    }

    public double getValorDescuentos() {
        return valorDescuentos;
    }

    public void setValorDescuentos(double valorDescuentos) {
        this.valorDescuentos = valorDescuentos;
    }

    public boolean isOpcDescuentos() {
        return OpcDescuentos;
    }

    public void setOpcDescuentos(boolean OpcDescuentos) {
        this.OpcDescuentos = OpcDescuentos;
    }

    public List<LogAnticipo> getListaAnticiposPop() {
        return ListaAnticiposPop;
    }

    public void setListaAnticiposPop(List<LogAnticipo> ListaAnticiposPop) {
        this.ListaAnticiposPop = ListaAnticiposPop;
    }

    public int getValorAnti() {
        return valorAnti;
    }

    public void setValorAnti(int valorAnti) {
        this.valorAnti = valorAnti;
    }

    public int getValorAnticipos() {
        return valorAnticipos;
    }

    public void setValorAnticipos(int valorAnticipos) {
        this.valorAnticipos = valorAnticipos;
    }

    public List<LogAnticipo> getListaAnticiposFac() {
        return ListaAnticiposFac;
    }

    public void setListaAnticiposFac(List<LogAnticipo> ListaAnticiposFac) {
        this.ListaAnticiposFac = ListaAnticiposFac;
    }

    public Date getFecha_Inicio() {
        return Fecha_Inicio;
    }

    public void setFecha_Inicio(Date Fecha_Inicio) {
        this.Fecha_Inicio = Fecha_Inicio;
    }

    public boolean isOpcValAdc5() {
        return OpcValAdc5;
    }

    public void setOpcValAdc5(boolean OpcValAdc5) {
        this.OpcValAdc5 = OpcValAdc5;
    }

    public boolean isOpcValAdc2() {
        return OpcValAdc2;
    }

    public void setOpcValAdc2(boolean OpcValAdc2) {
        this.OpcValAdc2 = OpcValAdc2;
    }

    public boolean isOpcValAdc3() {
        return OpcValAdc3;
    }

    public void setOpcValAdc3(boolean OpcValAdc3) {
        this.OpcValAdc3 = OpcValAdc3;
    }

    public boolean isOpcValAdc4() {
        return OpcValAdc4;
    }

    public void setOpcValAdc4(boolean OpcValAdc4) {
        this.OpcValAdc4 = OpcValAdc4;
    }

    public List<LogFacturacion> getListaValorAdicional() {
        return ListaValorAdicional;
    }

    public void setListaValorAdicional(List<LogFacturacion> ListaValorAdicional) {
        this.ListaValorAdicional = ListaValorAdicional;
    }

    public LogFacturacion getFactu() {
        return Factu;
    }

    public void setFactu(LogFacturacion Factu) {
        this.Factu = Factu;
    }

    public List<LogFacturacion> getListaAnticipos() {
        return ListaAnticipos;
    }

    public void setListaAnticipos(List<LogFacturacion> ListaAnticipos) {
        this.ListaAnticipos = ListaAnticipos;
    }

    public LogFacturacion getFac() {
        return Fac;
    }

    public void setFac(LogFacturacion Fac) {
        this.Fac = Fac;
    }

    public LogFacturacion getFacTablas() {
        return FacTablas;
    }

    public void setFacTablas(LogFacturacion FacTablas) {
        this.FacTablas = FacTablas;
    }

    public LogFacturacion getAdmTablasFact() {
        return AdmTablasFact;
    }

    public void setAdmTablasFact(LogFacturacion AdmTablasFact) {
        this.AdmTablasFact = AdmTablasFact;
    }

    public LogFacturacion getAdmTablasImpues() {
        return AdmTablasImpues;
    }

    public boolean isOpcDescuentos2() {
        return OpcDescuentos2;
    }

    public void setOpcDescuentos2(boolean OpcDescuentos2) {
        this.OpcDescuentos2 = OpcDescuentos2;
    }

    public void setAdmTablasImpues(LogFacturacion AdmTablasImpues) {
        this.AdmTablasImpues = AdmTablasImpues;
    }

    public LogFacturacion getAdmTablasFactTarifas() {
        return AdmTablasFactTarifas;
    }

    public void setAdmTablasFactTarifas(LogFacturacion AdmTablasFactTarifas) {
        this.AdmTablasFactTarifas = AdmTablasFactTarifas;
    }

    public LogCliente getClientesAFact() {
        return ClientesAFact;
    }

    public void setClientesAFact(LogCliente ClientesAFact) {
        this.ClientesAFact = ClientesAFact;
    }

    public LogFacturacion getSelecDist() {
        return selecDist;
    }

    public void setSelecDist(LogFacturacion selecDist) {
        this.selecDist = selecDist;
    }

    public LogEntidad getEntidadesAFact() {
        return EntidadesAFact;
    }

    public void setEntidadesAFact(LogEntidad EntidadesAFact) {
        this.EntidadesAFact = EntidadesAFact;
    }

    public String getTipoFacturacion() {
        return TipoFacturacion;
    }

    public void setTipoFacturacion(String TipoFacturacion) {
        this.TipoFacturacion = TipoFacturacion;
    }

    public boolean isOpcDistancia() {
        return OpcDistancia;
    }

    public void setOpcDistancia(boolean OpcDistancia) {
        this.OpcDistancia = OpcDistancia;
    }

    public boolean isOpcValAdc() {
        return OpcValAdc;
    }

    public void setOpcValAdc(boolean OpcValAdc) {
        this.OpcValAdc = OpcValAdc;
    }

    public String getNombreTable() {
        return NombreTable;
    }

    public void setNombreTable(String NombreTable) {
        this.NombreTable = NombreTable;
    }

    public int getCodTabla() {
        return CodTabla;
    }

    public void setCodTabla(int CodTabla) {
        this.CodTabla = CodTabla;
    }

    public List<LogFacturacion> getListPendientXFact() {
        return ListPendientXFact;
    }

    public void setListPendientXFact(List<LogFacturacion> ListPendientXFact) {
        this.ListPendientXFact = ListPendientXFact;
    }

    public List<LogFacturacion> getListaDistanciaTablas() {
        return ListaDistanciaTablas;
    }

    public void setListaDistanciaTablas(List<LogFacturacion> ListaDistanciaTablas) {
        this.ListaDistanciaTablas = ListaDistanciaTablas;
    }

    public List<LogFacturacion> getListTablaAplicada() {
        return ListTablaAplicada;
    }

    public void setListTablaAplicada(List<LogFacturacion> ListTablaAplicada) {
        this.ListTablaAplicada = ListTablaAplicada;
    }

    public List<LogFacturacion> getListTablasFacturac() {
        return ListTablasFacturac;
    }

    public void setListTablasFacturac(List<LogFacturacion> ListTablasFacturac) {
        this.ListTablasFacturac = ListTablasFacturac;
    }

    public List<LogFacturacion> getListTablasFacturacTarifas() {
        return ListTablasFacturacTarifas;
    }

    public void setListTablasFacturacTarifas(List<LogFacturacion> ListTablasFacturacTarifas) {
        this.ListTablasFacturacTarifas = ListTablasFacturacTarifas;
    }

    public List<LogFacturacion> getListTablasImpuestos() {
        return ListTablasImpuestos;
    }

    public void setListTablasImpuestos(List<LogFacturacion> ListTablasImpuestos) {
        this.ListTablasImpuestos = ListTablasImpuestos;
    }

    public ArrayList<SelectItem> getListTiposImpuestos() {
        return ListTiposImpuestos;
    }

    public void setListTiposImpuestos(ArrayList<SelectItem> ListTiposImpuestos) {
        this.ListTiposImpuestos = ListTiposImpuestos;
    }

    public ArrayList<SelectItem> getCargaRegimen() {
        return CargaRegimen;
    }

    public void setCargaRegimen(ArrayList<SelectItem> CargaRegimen) {
        this.CargaRegimen = CargaRegimen;
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
     * Metodo para seleccionar el avaluo y posterior a ello seleccionar el tipo
     * de facturacion.
     *
     * @param Tip Tipo de seleccion
     */
    public void seleccionAvaluo(int Tip) {

        int numero = Fac.getAva().getCodAvaluo();
        setListaAnticipos(Factu.ConsulAnticipoPoD(Fac.getAva().getCodAvaluo()));
        try {
            switch (Tip) {
                case 1:
                    if (Fac == null) {
                        mBTodero.setMens("No se ha seleccionado ningún número de avalúo");
                        mBTodero.warn();
                    } else {

                        ListaAnticipos = Fac.ConsulAnticipoPoD(numero);
                        if (ListaAnticipos.size() > 0) {
                            RequestContext.getCurrentInstance().execute("PF('DlgAnticiposPendientes').show()");
                        } else {
                            RequestContext.getCurrentInstance().execute("PF('DlgTipFact').show()");
                        }
                    }
                    break;
                case 2:
                    break;
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".seleccionAvaluo()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    /**
     * Metodo para realizar la validacion de los tabs, y dependiento de cada
     * opcion realizar metodos internos en cada uno de ellos.
     *
     * @param Tab Valor del nombre que tiene cada una de los link de tabs
     * @param CodSeg Codigo se seguimiento asociado al numero del avaluo
     */
    int Paso = 1;

    public void validacionTab(String Tab, int CodSeg) {
        try {

            switch (Tab) {
                case "NextTab1":
                    Paso = 1;
                    if (Fac.getCodFactura() <= 0) {
                        mBTodero.setMens("No ha ingresado un numero de factura valido");
                        mBTodero.warn();
                        Paso = 0;
                    } else if (Fac.ConsultNumeroFactura(2) >= 1) {
                        mBTodero.setMens("El número de factura ingresado ya esta en uso");
                        mBTodero.warn();
                    }
                    if (this.OpcDistancia == true) {
                        if (Fac.getDistancia() <= 0) {
                            mBTodero.setMens("Falta ingresar valor de distancia");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (this.OpcDescuentos == true) {
                        if (Fac.getValorDescuento() <= 0) {
                            mBTodero.setMens("El descuento no puede ser un valor menor a 1");
                            mBTodero.warn();
                            Paso = 0;
                        } else if ("".equals(Fac.getObserDescuento())) {
                            mBTodero.setMens("No ha ingresado la observación del primer descuento");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (getRadioUno().isEmpty()) {
                            mBTodero.setMens("Debe selecciona el tipo de descuento (Fijo o Porcentaje)");
                            mBTodero.warn();
                            Paso = 0;
                        } else if ("P".equals(getRadioUno())) {
                            if (Fac.getValorDescuento() > 100) {
                                mBTodero.setMens("El descuento no puede ser mayor al 100% del valor del avaluo");
                                mBTodero.warn();
                                Paso = 0;
                            }
                        }
                    } else {
                        Fac.setValorDescuento(0);
                        setOpcDescuentos(false);
                    }

                    if (this.OpcValAdc == true) {
                        if (Fac.getValorAdicional() <= 0) {
                            mBTodero.setMens("No ha ingresado el valor adicional");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (codParametro == 0) {
                            mBTodero.setMens("No ha seleccionado ningun item de la tipificación n° 1");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    } else {
                        Fac.setValorAdicional(0);
                        Fac.setValorAdicional2(0);
                        Fac.setValorAdicional3(0);
                        Fac.setValorAdicional4(0);
                        Fac.setValorAdicional5(0);
                        setOpcValAdc(false);
                        setOpcValAdc2(false);
                        setOpcValAdc3(false);
                        setOpcValAdc4(false);
                        setOpcValAdc5(false);

                    }
                    if (this.OpcValAdc2 == true) {
                        if (Fac.getValorAdicional2() <= 0) {
                            mBTodero.setMens("No ha ingresado el valor adicional de la segunda caja");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (codParametro2 == 0) {
                            mBTodero.setMens("No ha seleccionado ningun item de la tipificación n°2");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (this.OpcValAdc3 == true) {
                        if (Fac.getValorAdicional3() <= 0) {
                            mBTodero.setMens("No ha ingresado el valor adicional de la tercera caja");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (codParametro3 == 0) {
                            mBTodero.setMens("No ha seleccionado ningun item de la tipificación n°3");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (this.OpcValAdc4 == true) {
                        if (Fac.getValorAdicional4() <= 0) {
                            mBTodero.setMens("No ha ingresado el valor adicional de la cuarta caja");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (codParametro4 == 0) {
                            mBTodero.setMens("No ha seleccionado ningun item de la tipificación n°4");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    }
                    if (this.OpcValAdc5 == true) {
                        if (Fac.getValorAdicional5() <= 0) {
                            mBTodero.setMens("No ha ingresado el valor adicional de la quinta caja");
                            mBTodero.warn();
                            Paso = 0;
                        } else if (codParametro5 == 0) {
                            mBTodero.setMens("No ha seleccionado ningun item de la tipificación n°5");
                            mBTodero.warn();
                            Paso = 0;
                        }
                    }

                    break;

                case "validacionOk":

                    validacionTab("NextTab1", CodSeg);

                    /**
                     * Confirma y valida si cumple cada una de las condiciones
                     * para poder pasar al siguiente proceso; cuando la
                     * variables Paso esta en uno puede realizar el resto del
                     * proceso.
                     */
                    if (Paso == 1) {

                        Fac.getValorNetoFacturado();
                        //Valida si el o los anticipos del avaluo se encuentran aprobados
                        int AntiAprobado;
                        AntiAprobado = Ant.ConsultaAntiAprobado(CodSeg);
                        if (AntiAprobado > 0) {
                            mBTodero.setMens("El avaluo tiene anticipos asociados que aún no han sido aprobados y se encuentran devueltos, realice este proceso antes de facturar");
                            mBTodero.warn();
                        } else {
                            //Consulta el valor total de lo cual va a salir el avaluo
                            setListaAnticiposFac(Ant.ConsulAntiFactu(CodSeg));
                            valorAnticipos = Ant.SumaAnticipos(CodSeg);
                            double Dato;
                            setCalculo(true);
                            if (Fac.ValorPactadoCliente(Fac.validarNAval(CodSeg)) == 0 && Fac.ValorPactadoEntidad(Fac.validarNAval(CodSeg)) == 0) {
                                if (!OpcDistancia) {
                                    Fac.setDistancia(0);
                                }

                                Dato = Fac.ConsulValLiquid(CodSeg, Fac.getDistancia());
                                Fac.setValorFacturado(Dato);
                            } else {
                                ValorPac = Fac.ValorPactadoCliente(Fac.validarNAval(CodSeg)) + Fac.ValorPactadoEntidad(Fac.validarNAval(CodSeg));
                                Fac.setValorFacturado(ValorPac);
                            }

                            if (Fac.getValorAdicional() > 0 && isOpcIva1()) {
                                Fac.setValorTotalAdicional(Fac.getValorAdicional());
                            }
                            if (Fac.getValorAdicional2() > 0 && isOpcIva2()) {
                                Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional2());
                            }
                            if (Fac.getValorAdicional3() > 0 && isOpcIva3()) {
                                Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional3());
                            }
                            if (Fac.getValorAdicional4() > 0 && isOpcIva4()) {
                                Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional4());
                            }
                            if (Fac.getValorAdicional5() > 0 && isOpcIva5()) {
                                Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional5());
                            }
                            Fac.setTotalValorAdicionalSinIva(0);

                            if (Fac.getValorAdicional() > 0 && !isOpcIva1()) {
                                Fac.setTotalValorAdicionalSinIva(Fac.getValorAdicional());
                            }
                            if (Fac.getValorAdicional2() > 0 && !isOpcIva2()) {
                                Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional2());
                            }
                            if (Fac.getValorAdicional3() > 0 && !isOpcIva3()) {
                                Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional3());
                            }
                            if (Fac.getValorAdicional4() > 0 && !isOpcIva4()) {
                                Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional4());
                            }
                            if (Fac.getValorAdicional5() > 0 && !isOpcIva5()) {
                                Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional5());
                            }

                            Fac.getValorNetoFacturado();

                            setValorDescuentos(Fac.getValorDescuento());
                            if ("P".equals(getRadioUno())) {
                                if (getValorDescuentos() <= 100) {
                                    Fac.setValorNetoFacturado((Fac.getValorFacturado() - (Fac.getValorFacturado() * getValorDescuentos() / 100)) + Fac.getValorTotalAdicional());
                                    setSimbolo(getValorDescuentos() + " %");
                                    setValorDescuentos((Fac.getValorFacturado() * getValorDescuentos() / 100));
                                } else {
                                    mBTodero.setMens("El descuento no puede ser mayor al 100% del valor del avaluo");
                                    mBTodero.warn();
                                }
                            } else {
                                Fac.setValorNetoFacturado(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos());
                            }

                            setIvaBD(Fac.impuestoFactAntcpo().get(0).getTasaIva());
                            iva = Math.round(Fac.getValorNetoFacturado() * getIvaBD() / 100);
                            subtotal = Math.round(iva + Fac.getValorNetoFacturado() + Fac.getTotalValorAdicionalSinIva());

                            if (valorAnticipos > 0) {
                                double validar = subtotal - valorAnticipos;
                                if (validar >= -30000 && validar <= 30000) {
                                    mBTodero.setMens("El valor liquidado a sido ajustado por anticipo. Valor liquidado orginal: " + Math.round(Fac.getValorFacturado()));
                                    mBTodero.info();
                                    String vl = "1." + getIvaBD();
                                    double valint = Double.valueOf(vl);
                                    Fac.setValorFacturado(Math.round(valorAnticipos / valint));
                                    Fac.setValorNetoFacturado(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos());;
                                    iva = Math.round(Fac.getValorNetoFacturado() * getIvaBD() / 100);
                                    subtotal = Math.round(iva + Fac.getValorNetoFacturado() + Fac.getTotalValorAdicionalSinIva());
                                }
                            }

                            mBTodero.resetTable("FRMFactu:FacturaTable");
                            CodTabla = Fac.getCodTabla();
                            NombreTable = Fac.getNombreTabla();
                            ListTablaAplicada = new ArrayList<>();
                            ListTablaAplicada = Fac.TablaFacturacion("Exedente", CodTabla);
                            ListaDistanciaTablas = new ArrayList<>();
                            ListaDistanciaTablas = Fac.TablaDistanciaFac("Exedente", CodTabla);

                            if (isOpcDistancia() || isOpcDescuentos() || isOpcValAdc()) {
                                mBTodero.setMens("Calculos realizado ");
                                mBTodero.info();
                            }
                        }
                    }

                    if (!getListaEntidadesR().isEmpty() && getListaEntidadesR().get(0).getValor_tarifa().contains("-1") && getListaEntidadesR().size() == 1) {
                        setAsignTotal1(Fac.getValorFacturado());
                        setAsigDescuento1(getValorDescuentos());
                        setAsigValorAdicional1((Fac.getValorTotalAdicional() + Fac.getTotalValorAdicionalSinIva()));

                    } else if (!getListaClientesR().isEmpty() && getListaClientesR().get(0).getValorTatifa_cliePreRadic().contains("-1") && getListaClientesR().size() == 1) {
                        setAsignTotal1(Fac.getValorFacturado());
                        setAsigDescuento1(getValorDescuentos());
                        setAsigValorAdicional1(Fac.getValorTotalAdicional() + Fac.getTotalValorAdicionalSinIva());
                    }

                    break;

                case "cancelar":
                    RequestContext.getCurrentInstance().execute("PF('asignar').hide()");
                    setTipoFacturacion("");
                    setTipoLiqui("");
                    ListTablaPacValor.clear();
                    ListTablaLiqTabla.clear();

                    setValorFactura(0);
                    break;

                case "verTabla":
                    //CodSeg == Cod tabla (reutilizando)
                    ListTablaAplicada = Fac.TablaFacturacion("Exedente", selecVerTabla.getCodTabla());
                    ListaDistanciaTablas = new ArrayList<>();
                    ListaDistanciaTablas = Fac.TablaDistanciaFac("", selecVerTabla.getCodTabla());
                    RequestContext.getCurrentInstance().execute("PF('tablaAplicada').show()");
                    break;
                case "Guardar":
                    Fac.setValorNetoFacturado(Math.round(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos()));
                    iva = Math.round(Fac.getValorNetoFacturado() * getIvaBD() / 100);
                    subtotal = Math.round(iva + Fac.getValorNetoFacturado());
                    if (!getListaEntidadesR().isEmpty() && getListaEntidadesR().get(0).getValor_tarifa().contains("-1") && getListaEntidadesR().size() == 1) {
                        setAsignTotal1((int) Fac.getValorFacturado());
                    } else if (!getListaClientesR().isEmpty() && getListaClientesR().get(0).getValorTatifa_cliePreRadic().contains("-1") && getListaClientesR().size() == 1) {
                        setAsignTotal1((int) Fac.getValorFacturado());
                    }
                    break;

                case "BackTab1":
                    RequestContext.getCurrentInstance().execute("PF('TabFacturacion').disable(4)");
                    RequestContext.getCurrentInstance().execute("PF('TabFacturacion').enable(3)");
                    RequestContext.getCurrentInstance().execute("PF('TabFacturacion').select(3)");
                    break;
            }
        } catch (SQLException | NumberFormatException e) {
            mBTodero.setMens("Error BeanFacturacion metodo validarTab()");
            mBTodero.warn();
        }

    }

    /**
     * Metodo utilizado para abrir el dialog que permite registrar una nueva
     * tabla de facturacion
     *
     * @author Jhojan Stiven Rodriguez
     */
    public void abrirDialogRegistroTablaFac() {
        limpiarCamposTablaFactur();
        RequestContext.getCurrentInstance().execute("PF('DialogCrearTablasFactur').show()");
    }

    /**
     * Metodo utilizado para abrir el dialog que permite registrar una nueva
     * tabla de impuestos
     *
     * @author Jhojan Stiven Rodriguez
     */
    public void abrirDialogRegistroTablaImpuestos() {

        FacTablas.setFk_Cod_Impuesto(0);
        Fecha_Inicio = null;
        FacTablas.setTasaImpuesto(0);

        RequestContext.getCurrentInstance().execute("PF('DialogCrearTablasImpuesto').show()");
    }

    /**
     * Metodo utilizado inactivar un impuesto
     *
     * @author Laura Guerrero
     * @param tipo
     * @throws java.sql.SQLException
     */
    public void inhabilitar(String tipo) throws SQLException {
        switch (tipo) {
            case "AbrirPop":
                if (AdmTablasImpues == null) {
                    mBTodero.setMens("Debe seleccionar un registro de la tabla");
                    mBTodero.warn();
                } else if (!AdmTablasImpues.getFechaFinTablaImpues().equalsIgnoreCase("activo")) {
                    mBTodero.setMens("El impuesta ya se encuentra inhabilitado");
                    mBTodero.warn();
                } else {
                    int numImp = Fac.NumImpuActivos(AdmTablasImpues.getCodImpuesto());
                    if (numImp <= 1) {
                        mBTodero.setMens("El impuesto que va a inhabilitar es el unico de su tipo que se encuentra activo. Antes de inhabitarlo cree uno nuevo.");
                        mBTodero.warn();
                    } else {
                        RequestContext.getCurrentInstance().execute("PF('inhabilitar').show()");
                    }
                }
                break;
            case "inhabilitar":
                if (AdmTablasImpues == null) {
                    mBTodero.setMens("Debe seleccionar un registro de la tabla");
                    mBTodero.warn();
                } else if (!AdmTablasImpues.getFechaFinTablaImpues().equalsIgnoreCase("activo")) {
                    mBTodero.setMens("El impuesta ya se encuentra inhabilitado");
                    mBTodero.warn();
                } else {
                    try {
                        Fac.Inhabilitar(AdmTablasImpues.getCod_TablaImpuesto());
                        setListTablasImpuestos(Fac.ConsultarTodasTablasImpuestoAdmin());
                        AdmTablasImpues = null;

                    } catch (SQLException ex) {
                        Logger.getLogger(BeanFacturacion.class.getName()).log(Level.SEVERE, null, ex);
                    }

                }
                break;
        }

    }

    /**
     * Metodo tipo ajax que carga la sucursal cuando se va agregar un asesor
     *
     * @author Maira Alejandra Carvajal
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void onSucursalRegis() {
        try {
            if (FacTablas.getFk_Cod_Entid_Fac() != 0) {
                mBEntidad.getCargaSucur().clear();
                mBEntidad.getEnt().setCodEntidad(FacTablas.getFk_Cod_Entid_Fac());
                mBEntidad.cargSucursal();
            } else {
                mBTodero.setMens("Error");
                mBTodero.error();
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".onSucursalRegis()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    /**
     * Metodo que funciona como evento ajax, carga el tipo producto entidad
     * dependiendo del cod del producto entidad, proceso del modulo de
     * administracion
     *
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void onTipProEnt() {
        try {
            if (FacTablas.getFk_Cod_Prod_Fac() != 0) {
                mBAdministacion.getAdm().setCodProEnt(FacTablas.getFk_Cod_Prod_Fac());
                mBAdministacion.getTipProEnt().clear();
                mBAdministacion.getConsulTipProEnt(1);
            } else {
                System.out.println("No funciona");
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".onTipProEnt()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    /**
     * Metodo que limpia la imformacion para la creación de una tabla de
     * facturación
     *
     * @author Maira Alejandra Carvajal-
     * @author Jhojan Stiven Rodriguez
     * @since 01-05-2015
     */
    public void limpiarCamposTablaFactur() {
        FacTablas = new LogFacturacion();
        FacTablas.setFk_Cod_TipAval_Fac(0);
        FacTablas.setFk_Cod_Entid_Fac(0);
        FacTablas.setFk_Cod_Sucur_Fac(0);
        FacTablas.setFk_Cod_Prod_Fac(0);
        FacTablas.setFk_Cod_TipProd_Fac(0);
        FacTablas.setNombreTablaFact("");
        FacTablas.setNombreTablaFact("");

        FacTablas.setTipo_Monto_Fact("");
        FacTablas.setValor_MontoFact(0);
        FacTablas.setValor_Fact(0);
        FacTablas.setTipoValor_Fact("");
        count = 0;

        ListTablasFacturacTarifas = new ArrayList<>();
        ListDistancia = new ArrayList<>();
        estadoPanelCliente = false;

        FacTablas.setDistanciaFact1("");
        FacTablas.setDistanciaFact2("");
        FacTablas.setValorDistanFact1(0);
        FacTablas.setTipoValorDistanFact1("");

    }

    public void LimpiarImpuestos() {
        FacTablas.setFk_Cod_Impuesto(0);
        FacTablas.setFk_Cod_Regimen_Imp(0);
        FacTablas.setFk_Cod_TipRegimen_Imp(0);
        FacTablas.setTasaTablaImpuesto(0);
        this.Fecha_Inicio = null;

    }
    int count = 0;

    /**
     * Metodo que permite validar y agregar la informacion ingresada para las
     * tarifas aplicadas a las tablas de distancias
     *
     * @author Laura Camila Guerrero
     * @since 31-08-15
     *
     */
    public void agregarValoresDistancias() {

        if (FacTablas.getDistancia() == 0) {
            mBTodero.setMens("Debe seleccionar información en el campo distancia'");
            mBTodero.warn();
        } else if (FacTablas.getValorDistanFact1() == 0) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Valor'");
            mBTodero.warn();
        } else if ("".equals(FacTablas.getTipoValorDistanFact1())) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Tipo de valor'");
            mBTodero.warn();
        } else if ("P".equals(FacTablas.getTipoValorDistanFact1()) && FacTablas.getValorDistanFact1() > 100) {
            mBTodero.setMens("El tipo de distancia 'Porcentaje' no puede ser mayor a 100");
            mBTodero.warn();
        } else {

            FacTablasTarifas = new LogFacturacion();
            FacTablasTarifas.setContador(count);
            FacTablasTarifas.setDistancia(FacTablas.getDistancia());
            FacTablasTarifas.setTipoValorDistanFact1(FacTablas.getTipoValorDistanFact1());
            FacTablasTarifas.setValorDistanFact1(FacTablas.getValorDistanFact1());

            ListDistancia.add(FacTablasTarifas);
            count++;

        }
    }

    /**
     * Metodo que permite validar y agregar la informacion ingresada para las
     * tarifas aplicadas a las tablas de facturacion
     *
     * @author Jhojan Stiven Rodriguez
     * @since 19-11-15
     *
     */
    public void agregarValoresTarifas() {

        if (FacTablas.getValor_MontoFact() == 0) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Monto'");
            mBTodero.warn();
        } else if ("".equals(FacTablas.getTipo_Monto_Fact()) || FacTablas.getTipo_Monto_Fact() == null) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Tipo de monto'");
            mBTodero.warn();
        } else if (FacTablas.getValor_Fact() == 0) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Valor'");
            mBTodero.warn();
        } else if ("".equals(FacTablas.getTipoValor_Fact()) || FacTablas.getTipoValor_Fact() == null) {
            mBTodero.setMens("Debe seleccionar información en el campo 'Tipo de valor'");
            mBTodero.warn();
        } else if (("P".equals(FacTablas.getTipo_Monto_Fact())) && FacTablas.getValor_MontoFact() > 100) {
            mBTodero.setMens("El monto no puede ser mayor al 100 %");
            mBTodero.warn();
        } else if (("P".equals(FacTablas.getTipoValor_Fact())) && FacTablas.getValor_Fact() > 100) {
            mBTodero.setMens("El valor no puede ser mayor al 100 %");
            mBTodero.warn();
        } else {

            FacTablasTarifas = new LogFacturacion();
            FacTablasTarifas.setContador(count);
            FacTablasTarifas.setTipo_Monto_Fact(FacTablas.getTipo_Monto_Fact());
            FacTablasTarifas.setTipoValor_Fact(FacTablas.getTipoValor_Fact());
            FacTablasTarifas.setValor_Fact(FacTablas.getValor_Fact());
            FacTablasTarifas.setValor_MontoFact(FacTablas.getValor_MontoFact());
            ListTablasFacturacTarifas.add(FacTablasTarifas);
            count++;

        }
    }

    public void removeTabFactu(int tip, String idCli, String nitEnt, String avaluo) throws SQLException {

        switch (tip) {
            case 1:
                for (int i = 0; i < ListTablasFacturacTarifas.size(); i++) {
                    if (AdmTablasFactTarifas.getContador() == ListTablasFacturacTarifas.get(i).getContador()) {
                        ListTablasFacturacTarifas.remove(i);
                        AdmTablasFact = null;
                    }
                }
                break;
            case 2:
                for (int i = 0; i < ListDistancia.size(); i++) {
                    if (AdmTablasFactTarifas.getContador() == ListDistancia.get(i).getContador()) {
                        ListDistancia.remove(i);
                    }
                }
                break;

            case 3:
                for (int i = 0; i < ListTablasFacturacTarifas.size(); i++) {
                    if (AdmTablasImpues.getContador() == ListTablasFacturacTarifas.get(i).getContador()) {
                        ListTablasFacturacTarifas.remove(i);
                    }
                }
                break;

            case 4:

                LogCliente Cli = new LogCliente();
                Dat = Cli.getConsultarAllClientesMostrarinfo(idCli);
                if (Dat.next()) {
                    mBCliente.setTipoDocclienteInfo(Dat.getString("Nombre_Documento"));
                    mBCliente.setNumeroDocclienteInfo(Dat.getString("Numero_DocCliente"));
                    mBCliente.setNombreClienteInfo(Dat.getString("Nombre_Cliente"));
                    mBCliente.setCorreoClienteInfo(Dat.getString("Mail_Cliente"));
                    mBCliente.setDireccionClienteInfo(Dat.getString("Direccion_Cliente"));
                    mBCliente.setTelefonoClienteInfo(Dat.getString("Telefono_Cliente"));
                    mBCliente.setUbicacionClienteInfo(Dat.getString("Ubicacion"));
                    mBCliente.setCodRegimenCli(Dat.getString("regimen"));
                    RequestContext.getCurrentInstance().execute("PF('InfoCliente').show()");
                } else {
                    LogEntidad Ent = new LogEntidad();
                    Dat2 = Ent.InfoEntidad(Fac.getCodSeg(), nitEnt);
                    if (Dat2.next()) {
                        Ent.setCodEntidad(Dat2.getInt("Cod_Entidad"));
                        Ent.setCodSucursal(Dat2.getInt("Cod_Sucursal"));
                        Ent.setCodAsesor(Dat2.getInt("Cod_Asesor"));
                        Dat = Ent.ConsulInfEntAsesor();
                        if (Dat.next()) {

                            mBEntidad.setNumEntidad(Dat.getString("Documento_Entidad"));
                            mBEntidad.setNombreEntidad(Dat.getString("Nombre_Entidad"));
                            mBEntidad.setCodOficina(Dat.getString("Cod_Oficina"));
                            mBEntidad.setCodRegimen(Dat.getString("regimen"));
                            mBEntidad.setNombreAsesor(Dat.getString("Nombre_Asesor"));
                            mBEntidad.setMailAsesor(Dat.getString("Mail_Asesor"));
                            mBEntidad.setTelefonoAsesor(Dat.getString("telAse"));
                            mBEntidad.setCargoAsesor(Dat.getString("Cargo_Asesor"));
                            mBEntidad.setTelefonoSucursal(Dat.getString("Telefono_Oficina"));
                            mBEntidad.setDireccionSucursal(Dat.getString("Direccion_Oficina"));
                            mBEntidad.setNombreSucursal(Dat.getString("Nombre_Oficina"));

                            RequestContext.getCurrentInstance().execute("PF('InfoAsesor').show()");
                        }
                    } else {

                        Dat = Ent.ConsulInfEntAsesorFactSinAva(Fac.getCodFactu());
                        if (Dat.next()) {

                            mBEntidad.setNumEntidad(Dat.getString("Documento_Entidad"));
                            mBEntidad.setNombreEntidad(Dat.getString("Nombre_Entidad"));
                            mBEntidad.setCodOficina(Dat.getString("Cod_Oficina"));
                            mBEntidad.setCodRegimen(Dat.getString("regimen"));
                            mBEntidad.setNombreAsesor(Dat.getString("Nombre_Asesor"));
                            mBEntidad.setMailAsesor(Dat.getString("Mail_Asesor"));
                            mBEntidad.setTelefonoAsesor(Dat.getString("Telefono_Asesor"));
                            mBEntidad.setCargoAsesor(Dat.getString("Cargo_Asesor"));
                            mBEntidad.setTelefonoSucursal(Dat.getString("Telefono_Oficina"));
                            mBEntidad.setDireccionSucursal(Dat.getString("Direccion_Oficina"));
                            mBEntidad.setNombreSucursal(Dat.getString("Nombre_Oficina"));

                            RequestContext.getCurrentInstance().execute("PF('InfoAsesor').show()");
                        } else {
                            mBTodero.setMens("El cliente no se encuentra debidamente registrado en el sistema");
                            mBTodero.info();
                        }

                    }
                }
                Conexion.Conexion.CloseCon();

                break;

        }

    }

    public void verTablasFact() {
        ListTablasFacturacTarifas = new ArrayList<>();
        ListTablasFacturacTarifas = Fac.TablaFacturacion("Exedente", AdmTablasFact.getCodTablaFactur());
        RequestContext.getCurrentInstance().execute("PF('DialogVerTablasFactur').show()");

    }

    public ArrayList<SelectItem> getConsulTiposImpuestos() {
        try {
            Iterator<LogAdministracion> Ite;
            Ite = Adm.getItemsImpuesto().iterator();
            while (Ite.hasNext()) {
                LogAdministracion MBAdm = Ite.next();
                this.ListTiposImpuestos.add(new SelectItem(MBAdm.getCodImpuesto(), MBAdm.getNombreImpuesto()));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulTiposImpuestos()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.ListTiposImpuestos;
    }

    public ArrayList<SelectItem> getConsulCalifiRegEntid() {
        try {

            Iterator<LogAdministracion> Ite;
            Ite = Adm.getCalificacion((this.FacTablas.getFk_Cod_Regimen_Imp())).iterator();
            while (Ite.hasNext()) {
                LogAdministracion MBAdm = Ite.next();
                this.CargaRegimen.add(new SelectItem(MBAdm.getCodCalificacion(), MBAdm.getNombreCalificacion()));
            }
        } catch (NumberFormatException e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".getConsulCalifiRegEntid()' causado por: " + e.getMessage());
            mBTodero.error();
        }
        return CargaRegimen;

    }

    public void onCalifiReg() {
        try {
            if (FacTablas.getFk_Cod_Regimen_Imp() != 0) {
                getCargaRegimen().clear();
                getConsulCalifiRegEntid();
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".onCalifiReg()' causado por: " + e.getMessage());
            mBTodero.error();
        }
    }

    long suma = 0;

    /**
     * Metodo que permite validar la informacion ingresada para una tabla de
     * impuestos y registrarla en la base de datos
     *
     * @author Jhojan Stiven Rodriguez
     * @since 19-11-15
     *
     */
    public void registrarTablaFacturac() {

        try {
            if (FacTablas.getFk_Cod_TipAval_Fac() == 0) {
                mBTodero.setMens("Debe seleccionar información en el campo 'Tipo de avalúo'");
                mBTodero.warn();

            } else if ("".equals(FacTablas.getNombreTablaFact()) || FacTablas.getNombreTablaFact() == null) {
                mBTodero.setMens("Debe llenar información en el campo 'Nombre de tabla'");
                mBTodero.warn();

            } else if (ListTablasFacturacTarifas.isEmpty()) {
                mBTodero.setMens("Debe llenar información en la tabla de tarifas");
                mBTodero.warn();

            } else if (ListDistancia == null || ListDistancia.isEmpty() && estadoPanelCliente) {
                mBTodero.setMens("Debe ingresar informacion en las tablas de distancia");
                mBTodero.warn();

            } else if (FacTablas.getTipoTablaFact().equals("0")) {
                mBTodero.setMens("Debe ingresar informacion 'Tipo Tabla'");
                mBTodero.warn();
            } else {

                FacTablas.InsertTablaImpsto(2, FacTablas.getFk_Cod_Entid_Fac(), 0, FacTablas.getNombreTablaFact(),
                        (FacTablas.getTipoTablaFact().equalsIgnoreCase("0")) ? "null" : "'" + FacTablas.getTipoTablaFact() + "'",
                        FacTablas.getFk_Cod_Sucur_Fac(), mBsesion.codigoMiSesion(), "Facturacion", "", 0, "", 0, FacTablas.getFk_Tip_Inmueble());

                //Registro de la tabla de Facturacion//
                for (int i = 0; i < this.ListTablasFacturacTarifas.size(); i++) {
                    FacTablas.InsertTablaImpsto(3, 0, 0, "", "", 0, mBsesion.codigoMiSesion(), "TablaFact",
                            ListTablasFacturacTarifas.get(i).getTipo_Monto_Fact(), ListTablasFacturacTarifas.get(i).getValor_MontoFact(),
                            ListTablasFacturacTarifas.get(i).getTipoValor_Fact(), ListTablasFacturacTarifas.get(i).getValor_Fact(), FacTablas.getFk_Tip_Inmueble());
                }

                if (estadoPanelCliente) {
                    //Registro de la tabla de distancia//

                    for (int i = 0; i < this.ListDistancia.size(); i++) {
                        FacTablas.InsertTablaImpsto(4, 0, 0, "", "", 0, mBsesion.codigoMiSesion(), "TablaDist", ListDistancia.get(i).getTipoValorDistanFact1(), ListDistancia.get(i).getValorDistanFact1(), "F", ListDistancia.get(i).getDistancia(), FacTablas.getFk_Tip_Inmueble());
                    }
                }
                mBTodero.setMens("Registro almacenado satisfactoriamente");
                mBTodero.info();
//                this.setListTablasFacturac(new ArrayList<LogFacturacion>());
                this.setListTablasFacturac(new ArrayList<>());
                this.setListTablasFacturac(FacTablas.ConsultarTodasTablasFacturacionAdmin());
                RequestContext.getCurrentInstance().execute("PF('DialogCrearTablasFactur').hide()");
            }

        } catch (SQLException e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".registrarTablaFacturac()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    /**
     * Metodo que permite validar la informacion ingresada para una tabla de
     * impuestos y registrarla en la base de datos
     *
     * @author Jhojan Stiven Rodriguez
     * @since 19-11-15
     *
     */
    public void registrarTablaImpuestos() {
        try {
            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");

            if (FacTablas.getFk_Cod_Impuesto() == 0) {
                mBTodero.setMens("Debe seleccionar información en el campo 'Nombre de impuesto'");
                mBTodero.warn();

            } else if (mBAdministacion.getFecha_actual() == null) {
                mBTodero.setMens("Debe ingresar una fecha de inicio");
                mBTodero.warn();
            } else if (FacTablas.getTasaTablaImpuesto() == 0) {
                mBTodero.setMens("Debe llenar información en el campo 'Tasa'");
                mBTodero.warn();
            } else {

                //Realizar la Insercion de la tabla de Impuestos
                FacTablas.InsertTablaImpsto(1, FacTablas.getFk_Cod_Impuesto(), FacTablas.getTasaTablaImpuesto(), "2016-10-3", FacTablas.getTipoAplicadoTablaImp(),
                        FacTablas.getFk_Cod_TipRegimen_Imp(), mBsesion.codigoMiSesion(), "Impuesto", "", 0, "", 0, FacTablas.getFk_Tip_Inmueble());
                mBTodero.setMens("Registro realizado Satisfactoriamente");
                mBTodero.info();
                LimpiarImpuestos();
                setListTablasImpuestos(Fac.ConsultarTodasTablasImpuestoAdmin());
                RequestContext.getCurrentInstance().execute("PF('DialogCrearTablasImpuesto').hide()");
                //mBAdministacion.setListImpuestos(new ArrayList<LogAdministracion>());
                mBAdministacion.setListImpuestos(new ArrayList<>());
                mBAdministacion.setListImpuestos(Adm.getItemsImpuesto());
                AdmTablasImpues = null;
            }
        } catch (SQLException e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".registrarTablaImpuestos()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    int tipoAva = 0;
    double valorComercial = 0;

    //Tatiana
    //Cliente
    private boolean estadoPanelCliente;
    private String OpcCliente = "";
    private String OpcEntidad = "";
    private String NomPers = "";
    private int IdentfPersona = 0;

    public String getNomPers() {
        return NomPers;
    }

    public void setNomPers(String NomPers) {
        this.NomPers = NomPers;
    }

    public int getIdentfPersona() {
        return IdentfPersona;
    }

    public void setIdentfPersona(int IdentfPersona) {
        this.IdentfPersona = IdentfPersona;
    }

    public String getOpcCliente() {
        return OpcCliente;
    }

    public void setOpcCliente(String OpcCliente) {
        this.OpcCliente = OpcCliente;
    }

    public String getOpcEntidad() {
        return OpcEntidad;
    }

    public void setOpcEntidad(String OpcEntidad) {
        this.OpcEntidad = OpcEntidad;
    }

    public boolean isEstadoPanelCliente() {
        return estadoPanelCliente;
    }

    public void setEstadoPanelCliente(boolean estadoPanelCliente) {
        this.estadoPanelCliente = estadoPanelCliente;
    }

    public void cargarPaneles(int Opc) {
        switch (Opc) {
            case 1:
                this.setEstadoPanelCliente(true);

                break;
        }

    }

    //Tatiana
    private List<LogCliente> selectPersFactAnti = new ArrayList<>();

    ;

    public List<LogCliente> getSelectPersFactAnti() {
        return selectPersFactAnti;
    }

    public void setSelectPersFactAnti(List<LogCliente> selectPersFactAnti) {
        this.selectPersFactAnti = selectPersFactAnti;
    }
    int CodFactura = 0;
    int valorFactAnti = 0;

    boolean EstadoFact;

    public boolean isEstadoFact() {
        return EstadoFact;
    }

    public void setEstadoFact(boolean EstadoFact) {
        this.EstadoFact = EstadoFact;
    }

    //Tatiana Facturacion Anticipo
    //23/03/2016
    int Cons = 100;
    int ValorFactura1;
    int ValorIva;
    int Rst = 0;
    double valorfact1 = 0;
    int Rsltad = 0;

    //Tatiana 
    //28//03/2016
    public void Clear() {
        totalpagarFactAnt = 0;
        OpcionFacturarA = null;
        this.OpcCliente = null;
        this.OpcEntidad = "";
        this.NomPers = "";
        this.IdentfPersona = 0;
        selectPersFactAnti = null;
        this.ValorFactura = 0;
        this.nfactAnticpo = 0;
        EstadoFact = false;
        ObserFactAnti = "";
        estadoPnlIva = "";
        opcAplicarIva = "";
        CodTipoConcepto = 0;
        IvaAnticp = 0;
        TotalIvaAnti = 0;
        setNomPers("");
        setIdentfPersona(0);
        setNavaluoCopia(0);
        setEstadoInfoAval(false);

    }

    //Tatiana 29/03/2016
    public void verifiFec() {
        try {
            mBTodero.setMens("La fecha inicial no debe ser mayor a la fecha final");
            mBTodero.warn();

        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".verifiFec()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    public List<LogCliente> getListaClientesR() {
        return ListaClientesR;
    }

    public void setListaClientesR(List<LogCliente> ListaClientesR) {
        this.ListaClientesR = ListaClientesR;
    }

    public List<LogEntidad> getListaEntidadesR() {
        return ListaEntidadesR;
    }

    public void setListaEntidadesR(List<LogEntidad> ListaEntidadesR) {
        this.ListaEntidadesR = ListaEntidadesR;
    }

    public boolean isValidacion() {
        return validacion;
    }

    public void setValidacion(boolean validacion) {
        this.validacion = validacion;
    }

    private int ivaBD;

    public int getIvaBD() {
        return ivaBD;
    }

    public void setIvaBD(int ivaBD) {
        this.ivaBD = ivaBD;
    }

    public void agregarValorAdional(int opc) {

        switch (opc) {
            case 1:
                if (Fac.getValorAdicional() == 0) {
                    mBTodero.setMens("Ingrese el valor de la primera caja de texto ");
                    mBTodero.warn();
                } else {
                    setOpcValAdc2(true);
                }
                break;
            case 2:
                if (Fac.getValorAdicional2() == 0) {
                    mBTodero.setMens("Ingrese  el valor  de la segunda caja de texto ");
                    mBTodero.warn();
                } else {
                    setOpcValAdc3(true);
                }
                break;
            case 3:
                if (Fac.getValorAdicional3() == 0) {
                    mBTodero.setMens("Ingrese  el valor de la tercera caja de texto ");
                    mBTodero.warn();
                } else {
                    setOpcValAdc4(true);
                }
                break;
            case 4:
                if (Fac.getValorAdicional4() == 0) {
                    mBTodero.setMens("Ingrese  el valor de la cuarta caja de texto ");
                    mBTodero.warn();
                } else {
                    setOpcValAdc5(true);
                }
            case 5:
                if (Fac.getValorDescuento() == 0 || "".equals(Fac.getObserDescuento())) {
                    mBTodero.setMens("Ingrese  el valor y la observación del descuento que desea realizar ");
                    mBTodero.warn();
                }

        }
    }

    public void QuitarValores(int opc) {

        if (OpcValAdc == true) {

            switch (opc) {
                case 1:
                    setOpcValAdc(false);
                    Fac.setValorAdicional(0);
                    break;
                case 2:
                    setOpcValAdc2(false);
                    Fac.setValorAdicional2(0);
                    break;
                case 3:
                    setOpcValAdc3(false);
                    Fac.setValorAdicional3(0);
                    break;
                case 4:
                    setOpcValAdc4(false);
                    Fac.setValorAdicional4(0);
                    break;
                case 5:
                    setOpcValAdc5(false);
                    Fac.setValorAdicional5(0);
                    break;

            }
        } else {
            setOpcValAdc(false);
            setOpcValAdc2(false);
            setOpcValAdc3(false);
            setOpcValAdc4(false);
            setOpcValAdc5(false);
        }

    }

    public void Reparticion(int cod) {

        int cont = 1;
        setListaEntidadesR(EntidadesAFact.consultaEntidadPorEntiFacturar(cod));

        if (!getListaEntidadesR().isEmpty() && !getListaEntidadesR().get(0).getValor_tarifa().contains("-1")) {
            int cant = getListaEntidadesR().size();

            if (cont <= cant) {
                setAsignTotal1(Integer.valueOf(getListaEntidadesR().get(0).getValor_tarifa()));
                cont++;
            }
            if (cont <= cant) {
                setAsignTotal2(Integer.valueOf(getListaEntidadesR().get(1).getValor_tarifa()));
                cont++;
            }
            if (cont <= cant) {
                setAsignTotal3(Integer.valueOf(getListaEntidadesR().get(2).getValor_tarifa()));
                cont++;
            }
            if (cont <= cant) {
                setAsignTotal4(Integer.valueOf(getListaEntidadesR().get(3).getValor_tarifa()));
                cont++;
            }
            if (cont <= cant) {
                setAsignTotal5(Integer.valueOf(getListaEntidadesR().get(4).getValor_tarifa()));
                cont++;
            }
        }

        setListaClientesR(ClientesAFact.ConsultaClienPerFact(1, cod));

        if (!getListaClientesR().isEmpty() && !getListaClientesR().get(0).getValorTatifa_cliePreRadic().contains("-1")) {

            int cantC = getListaClientesR().size();
            if (cont <= cantC) {
                setAsignTotal1(Integer.valueOf(getListaClientesR().get(0).getValorTatifa_cliePreRadic()));
                cont++;
            }
            if (cont <= cantC) {
                setAsignTotal2(Integer.valueOf(getListaClientesR().get(1).getValorTatifa_cliePreRadic()));
                cont++;
            }
            if (cont <= cantC) {
                setAsignTotal3(Integer.valueOf(getListaClientesR().get(2).getValorTatifa_cliePreRadic()));
                cont++;
            }
            if (cont <= cantC) {
                setAsignTotal4(Integer.valueOf(getListaClientesR().get(3).getValorTatifa_cliePreRadic()));
                cont++;
            }
            if (cont <= cantC) {
                setAsignTotal5(Integer.valueOf(getListaClientesR().get(4).getValorTatifa_cliePreRadic()));
                cont++;
            }

        }
    }
    private int numCajaCambiar = 0;

    public int getNumCajaCambiar() {
        return numCajaCambiar;
    }

    public void setNumCajaCambiar(int numCajaCambiar) {
        this.numCajaCambiar = numCajaCambiar;
    }

    private String ob;

    public String getOb() {
        return ob;
    }

    public void setOb(String ob) {
        this.ob = ob;
    }

    public void ModificarPreFactura(String tipo, int CodSeg) throws SQLException, IOException {

        switch (tipo) {

            case "CambiarTabla":
                setCambiarAPac(0);
                setCamTablaParti(false);
                setCambValorPac(false);
                setObservacionCambio("");
                if (isCalculo() == true) {
                    tipoAva = Fac.CambiarTabla(CodSeg);
                    setListTablaParti(Fac.TablaCambParti(tipoAva));
                    setListTablaDistCambio(Fac.TablaDistanciaFac("Exedente", ListTablaParti.get(0).getCodTabla()));
                    RequestContext.getCurrentInstance().execute("PF('modificarTabla').show()");
                } else {
                    mBTodero.setMens("Debe realizar el calculo de la pre factura");
                    mBTodero.warn();
                }
                break;

            case "GuardaCambios":
                opcDesaprueba = true;
                validacionTab("NextTab1", 0);
                if ((isCamTablaParti() == true && isCambValorPac() == true) || (isCamTablaParti() == false && isCambValorPac() == false) || (getObservacionCambio().isEmpty())) {
                    mBTodero.setMens("Debe seleccionar unicamente una opcion e indicar el motivo del cambio");
                    mBTodero.warn();
                    RequestContext.getCurrentInstance().execute("PF('modificarTabla').show()");
                } else if (Paso == 0) {

                } else {

                    if (isCambValorPac() == true && isCamTablaParti() == false) {
                        double valorNewPac = getCambiarAPac();
                        setValorPac(valorNewPac);
                        Fac.setValorFacturado(valorNewPac);
                        OpcDistancia = false;
                    } else {
                        valorComercial = Fac.ValorComercial(CodSeg);
                        tipoAva = Fac.CambiarTabla(CodSeg);
                        double valorNewTabla = Fac.CambiarAParticulares(CodSeg, tipoAva, Fac.getDistancia(), valorComercial);
                        Fac.setValorFacturado((long) valorNewTabla);
                        setNombreTable(Fac.getNombreTabla());
                        setCodTabla(Fac.getCodTabla());
                        setListTablaAplicada(Fac.TablaCambParti(tipoAva));
                    }
                    if (Fac.getValorAdicional() > 0 && isOpcIva1()) {
                        Fac.setValorTotalAdicional(Fac.getValorAdicional());
                    }
                    if (Fac.getValorAdicional2() > 0 && isOpcIva2()) {
                        Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional2());
                    }
                    if (Fac.getValorAdicional3() > 0 && isOpcIva3()) {
                        Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional3());
                    }
                    if (Fac.getValorAdicional4() > 0 && isOpcIva4()) {
                        Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional4());
                    }
                    if (Fac.getValorAdicional5() > 0 && isOpcIva5()) {
                        Fac.setValorTotalAdicional(Fac.getValorTotalAdicional() + Fac.getValorAdicional5());
                    }
                    Fac.setTotalValorAdicionalSinIva(0);

                    if (Fac.getValorAdicional() > 0 && !isOpcIva1()) {
                        Fac.setTotalValorAdicionalSinIva(Fac.getValorAdicional());
                    }
                    if (Fac.getValorAdicional2() > 0 && !isOpcIva2()) {
                        Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional2());
                    }
                    if (Fac.getValorAdicional3() > 0 && !isOpcIva3()) {
                        Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional3());
                    }
                    if (Fac.getValorAdicional4() > 0 && !isOpcIva4()) {
                        Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional4());
                    }
                    if (Fac.getValorAdicional5() > 0 && !isOpcIva5()) {
                        Fac.setTotalValorAdicionalSinIva(Fac.getTotalValorAdicionalSinIva() + Fac.getValorAdicional5());
                    }

                    setValorDescuentos(Fac.getValorDescuento());
                    if ("P".equals(getRadioUno())) {
                        if (getValorDescuentos() <= 100) {
                            Fac.setValorNetoFacturado((Fac.getValorFacturado() - (Fac.getValorFacturado() * getValorDescuentos() / 100)) + Fac.getValorTotalAdicional());
                            setSimbolo(getValorDescuentos() + " %");
                            setValorDescuentos((Fac.getValorFacturado() * getValorDescuentos() / 100));
                        } else {
                            mBTodero.setMens("El descuento no puede ser mayor al 100% del valor del avaluo");
                            mBTodero.warn();
                        }
                    } else {
                        Fac.setValorNetoFacturado(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos());
                    }

                    Fac.setValorNetoFacturado(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos());

                    setIva(Math.round(Fac.getValorNetoFacturado() * Fac.impuestoFactAntcpo().get(0).getTasaIva() / 100));
                    setSubtotal(Math.round(getIva() + Fac.getValorNetoFacturado() + Fac.getTotalValorAdicionalSinIva()));
                    mBTodero.setMens("Modificación realizada");
                    mBTodero.info();
                }
                AsignTotal1 = Fac.getValorFacturado();
                break;

            case "GuardarCambios":

                Fac.setValorFacturado(Fac.getValorFacturado());
                mBTodero.setMens("Valor facturado del avaluo guardado");
                Fac.setValorNetoFacturado(Fac.getValorFacturado() + Fac.getValorTotalAdicional() - getValorDescuentos() - getValorAnti());
                setIva(Fac.getValorNetoFacturado() * 0.16);
                setSubtotal(getIva() + Fac.getValorNetoFacturado());
                mBTodero.warn();
                break;

            case "AbrirPopDev":
                RequestContext.getCurrentInstance().execute("PF('devolver').show()");
                break;

            case "AcepDev":
                Fac.DevovlerRevi(CodSeg);
                mBTodero.setMens("El avaluo " + CodSeg + " ha sido devuelto a Revision");
                mBTodero.warn();

                break;

            case "ReutilizarFac":
                int numCajFac;
                int nuevoCodFac;
                if (listFactAscdas.size() > 0) {
                    if (SelectListaFactAnt.size() == 1) {
                        numCajFac = getNumCajaCambiar();
                        nuevoCodFac = SelectListaFactAnt.get(0).getCodFactura();
                        switch (numCajFac) {
                            case 0:
                                setCajaRepar1(nuevoCodFac);
                                break;
                            case 1:
                                setCajaRepar2(nuevoCodFac);
                                break;
                            case 2:
                                setCajaRepar3(nuevoCodFac);
                                break;
                            case 3:
                                setCajaRepar4(nuevoCodFac);
                                break;
                            case 4:
                                setCajaRepar5(nuevoCodFac);
                                break;
                        }
                        SelectListaFactAnt = null;

                    } else {
                        mBTodero.setMens("Seleccione un número de factura");
                        mBTodero.warn();
                        RequestContext.getCurrentInstance().execute("PF('FactAsocd').show()");
                    }
                } else {
                    mBTodero.setMens("El cliente no tiene facturas asociadas");
                    mBTodero.warn();
                }

                break;

        }
    }

    public void CargarNFactura() {
        MaxFact = Fac.MaxNoFact();
        this.setCajaRepar1(MaxFact = MaxFact + 1);
        this.setCajaRepar2(MaxFact = MaxFact + 1);
        this.setCajaRepar3(MaxFact = MaxFact + 1);
        this.setCajaRepar4(MaxFact = MaxFact + 1);
        this.setCajaRepar5(MaxFact = MaxFact + 1);
    }

    public void FacturarExcedente(String tipo) throws SQLException {
        switch (tipo) {

            case "validar":
                if (getAsignTotal1() + getAsignTotal2() + getAsignTotal3() == Fac.getValorFacturado()) {
                    valorparticion = ((double) getAsignTotal1() * 100) / (double) Fac.getValorFacturado();
                    valorparticion2 = ((double) getAsignTotal2() * 100) / (double) Fac.getValorFacturado();
                    valorparticion3 = (getAsignTotal3() * 100) / Fac.getValorFacturado();
                    valorparticion4 = (getAsignTotal4() * 100) / Fac.getValorFacturado();
                    valorparticion5 = (getAsignTotal5() * 100) / Fac.getValorFacturado();
                    setAsigDescuento1((int) Math.round((getDescuento() * valorparticion) / 100));
                    setAsigDescuento2((int) Math.round((getDescuento() * valorparticion2) / 100));
                    setAsigDescuento3((int) Math.round((getDescuento() * valorparticion3) / 100));
                    setAsigDescuento4((int) Math.round((getDescuento() * valorparticion4) / 100));
                    setAsigDescuento5((int) Math.round((getDescuento() * valorparticion5) / 100));
                    RequestContext.getCurrentInstance().execute("PF('ConfirFactu').show()");

                } else {
                    mBTodero.setMens("El valor asignado al/los cliente no concuerda con el valor facturado");
                    mBTodero.warn();
                }
                break;

            case "imprimir":
                int resp;

                int numCli = mBArchivos.getListClientesPersonasAFacturarAntic().size() + mBArchivos.getListEntidadesPersonasAFacturarAntic().size();
                resp = Fac.FacturaExiste((numCli == 1) ? getCajaRepar1() : (numCli == 2) ? getCajaRepar1() + getCajaRepar2() : (numCli == 3) ? getCajaRepar1() + getCajaRepar2() + getCajaRepar3() : getCajaRepar1() + getCajaRepar2() + getCajaRepar3() + getCajaRepar4());
                if (resp == 0) {
                    Fac.InsertFactu(17, Fac.validarNAval(Fac.getCodAva()), Fac.getValorFacturado(), String.valueOf(getCodTabla()), getCajaRepar1(), "N", 0, 0,
                            "5", "null", 10, "null", 0, 0, 1, "", "", 1);

                    for (int i = 0; i < numCli; i++) {
                        Fac.InsertFactu(18, Fac.getCodAva(), 0, "0", (i + 1 == 1) ? getCajaRepar1() : (i + 1 == 2) ? getCajaRepar2() : (i + 1 == 3) ? getCajaRepar3() : getCajaRepar4(),
                                "N", 0, (i + 1 == 1) ? getAsignTotal1() : (i + 1 == 2) ? getAsignTotal2() : (i + 1 == 3) ? getAsignTotal3() : getAsignTotal4(),
                                (mBArchivos.getListClientesPersonasAFacturarAntic().size() > 0) ? String.valueOf(mBArchivos.getListClientesPersonasAFacturarAntic().get(i).getCodCliente()) : "null",
                                (mBArchivos.getListEntidadesPersonasAFacturarAntic().size() > 0) ? String.valueOf(mBArchivos.getListEntidadesPersonasAFacturarAntic().get(i).getCodEntidad()) : "null", 10, "null", 0, 0, 1, "", "", 1);
                    }
                    RequestContext.getCurrentInstance().execute("PF('Exce').show()");
                } else {
                    mBTodero.setMens("El número de factura ingresado ya se encuentra creado");
                    mBTodero.warn();
                }

                break;
        }

    }
    int codFacOb = 0;

    public void PasarImpresion(int codSeg, String tipo) throws SQLException, IOException {
        int cont = 1;
        valorparticion = 0;
        valorparticion2 = 0;
        valorparticion3 = 0;
        valorparticion4 = 0;
        valorparticion5 = 0;
        int numCajas;
        switch (tipo) {

            case "addObsConsul":
                detalleFactura = "";
                FacTablas = new LogFacturacion();
                codFacOb = codSeg;
                listaDetallesFac = FacTablas.Observaciones(codSeg);
                RequestContext.getCurrentInstance().execute("PF('ObFac').show()");
                break;
            case "AbririPop":
                if (!opcDesaprueba) {
                    validacionTab("validacionOk", codSeg);
                }

                if (Paso == 1) {
                    ob = "";
                    valorparticion = ((double) getAsignTotal1() * 100) / (double) Fac.getValorFacturado();
                    valorparticion2 = ((double) getAsignTotal2() * 100) / (double) Fac.getValorFacturado();
                    valorparticion3 = (getAsignTotal3() * 100) / Fac.getValorFacturado();
                    valorparticion4 = (getAsignTotal4() * 100) / Fac.getValorFacturado();
                    valorparticion5 = (getAsignTotal5() * 100) / Fac.getValorFacturado();
                    setAsigDescuento1((int) Math.round((getValorDescuentos() * valorparticion) / 100));
                    setAsigDescuento2((int) Math.round((getValorDescuentos() * valorparticion2) / 100));
                    setAsigDescuento3((int) Math.round((getValorDescuentos() * valorparticion3) / 100));
                    setAsigDescuento4((int) Math.round((getValorDescuentos() * valorparticion4) / 100));
                    setAsigDescuento5((int) Math.round((getValorDescuentos() * valorparticion5) / 100));

                    setAsigValorAdicional1((int) Math.round(((Fac.getTotalValorAdicionalSinIva() + Fac.getValorTotalAdicional()) * valorparticion) / 100));
                    setAsigValorAdicional2((int) Math.round(((Fac.getTotalValorAdicionalSinIva() + Fac.getValorTotalAdicional()) * valorparticion2) / 100));
                    setAsigValorAdicional3((int) Math.round(((Fac.getTotalValorAdicionalSinIva() + Fac.getValorTotalAdicional()) * valorparticion3) / 100));
                    setAsigValorAdicional4((int) Math.round(((Fac.getTotalValorAdicionalSinIva() + Fac.getValorTotalAdicional()) * valorparticion4) / 100));
                    setAsigValorAdicional4((int) Math.round(((Fac.getTotalValorAdicionalSinIva() + Fac.getValorTotalAdicional()) * valorparticion5) / 100));

                    numCajas = getListaClientesR().size() + getListaEntidadesR().size();
                    validar = 0;
                    for (int i = 0; i < numCajas; i++) {
                        int cajanumFac;
                        cajanumFac = ((i == 0) ? cajaRepar1 : (i == 1) ? cajaRepar2 : (i == 2) ? cajaRepar3 : (i == 3) ? cajaRepar4 : cajaRepar5);
                        if (Fac.FacturaExiste(cajanumFac) == 1) {

                            if (Fac.FacturaReuMismoCli((getListaClientesR().size() > 0) ? "C" : "E", cajanumFac, (getListaClientesR().size() > 0) ? getListaClientesR().get(i).getCodCliente() : getListaEntidadesR().get(i).getCodEntidad()) == 0) {
                                validar++;
                                ob = (ob.isEmpty()) ? "el numero de factura " + cajanumFac : ob + ", " + cajanumFac;
                            }
                            if (validar != 0) {
                                mBTodero.setMens(ob + " se encuentran ocupado por otros clientes");
                                mBTodero.warn();
                            }

                        }

                        if (getAsignTotal1() + getAsignTotal2() + getAsignTotal3() + getAsignTotal4() + getAsignTotal5() != (int) Fac.getValorFacturado()) {
                            validar++;
                            mBTodero.setMens("Los valores de la caja no cocuerdan con el valor a facturar");
                            mBTodero.warn();
                        }
                    }
                    if (validar == 0) {
                        if (isCalculo()) {
                            RequestContext.getCurrentInstance().execute("PF('ConfirFactu').show()");
                        } else {
                            mBTodero.setMens("Realice el calculo de la prefactura");
                            mBTodero.warn();
                        }

                    }
                }
                break;

            case "AbririPopGrupos":

                if (getListTablaPacValor().size() > 0 || getListTablaLiqTabla().size() > 0) {
                    if (AsignTotal1 + AsignTotal2 + AsignTotal3 == getValorTotalTabla()) {

                        valorparticion = ((double) getAsignTotal1() * 100) / (double) getValorTotalTabla();
                        valorparticion2 = ((double) getAsignTotal2() * 100) / (double) getValorTotalTabla();
                        valorparticion3 = (getAsignTotal3() * 100) / getValorTotalTabla();
                        valorparticion4 = (getAsignTotal4() * 100) / getValorTotalTabla();
                        valorparticion5 = (getAsignTotal5() * 100) / getValorTotalTabla();
                        setAsigDescuento1((int) Math.round((getValorDescuentos() * valorparticion) / 100));
                        setAsigDescuento2((int) Math.round((getValorDescuentos() * valorparticion2) / 100));
                        setAsigDescuento3((int) Math.round((getValorDescuentos() * valorparticion3) / 100));
                        setAsigDescuento4((int) Math.round((getValorDescuentos() * valorparticion4) / 100));
                        setAsigDescuento5((int) Math.round((getValorDescuentos() * valorparticion5) / 100));

                        setAsigValorAdicional1((int) Math.round(((getValorAdicionSinIva() + getValorAdicion()) * valorparticion) / 100));
                        setAsigValorAdicional2((int) Math.round(((getValorAdicionSinIva() + getValorAdicion()) * valorparticion2) / 100));
                        setAsigValorAdicional3((int) Math.round(((getValorAdicionSinIva() + getValorAdicion()) * valorparticion3) / 100));
                        setAsigValorAdicional4((int) Math.round(((getValorAdicionSinIva() + getValorAdicion()) * valorparticion4) / 100));
                        setAsigValorAdicional4((int) Math.round(((getValorAdicionSinIva() + getValorAdicion()) * valorparticion5) / 100));
                        RequestContext.getCurrentInstance().execute("PF('ConfirFactu').show()");

                    } else {
                        mBTodero.setMens("El valor total asignado a los clientes no coincide con el valor facturado del avalúo");
                        mBTodero.warn();
                    }

                } else {
                    mBTodero.setMens("No ha asignado la tarifa a los avalúos");
                    mBTodero.warn();
                }

                break;

            case "PasarImpresion":

                if (!opcDesaprueba) {
                    validacionTab("validacionOk", codSeg);
                }
                if (Paso == 1) {
                    validar = 0;
                    AsigDescuento5 = 0;
                    numCajas = getListaClientesR().size() + getListaEntidadesR().size();
                    validar = 0;
                    for (int i = 0; i < numCajas; i++) {
                        int cajanumFac;
                        cajanumFac = ((i == 0) ? cajaRepar1 : (i == 1) ? cajaRepar2 : (i == 2) ? cajaRepar3 : (i == 3) ? cajaRepar4 : cajaRepar5);
                        if (Fac.FacturaExiste(cajanumFac) == 1) {

                            if (Fac.FacturaReuMismoCli((getListaClientesR().size() > 0) ? "C" : "E", cajanumFac, (getListaClientesR().size() > 0) ? getListaClientesR().get(i).getCodCliente() : getListaEntidadesR().get(i).getCodEntidad()) == 0) {
                                validar++;
                                ob = (ob.isEmpty()) ? "el numero de factura " + cajanumFac : ob + ", " + cajanumFac;
                            }

                        }
                        if (numCajas > 1) {
                            if (2 <= numCajas) {
                                if (cajaRepar1 == cajaRepar2) {
                                    AsigDescuento5++;
                                }
                            }
                            if (3 <= numCajas) {
                                if (cajaRepar2 == cajaRepar3 || cajaRepar3 == cajaRepar1) {
                                    AsigDescuento5++;
                                }
                            }
                            if (4 <= numCajas) {
                                if (cajaRepar1 == cajaRepar4 || cajaRepar2 == cajaRepar4 || cajaRepar3 == cajaRepar4) {
                                    AsigDescuento5++;
                                }
                            }
                            if (5 <= numCajas) {
                                if (cajaRepar5 == cajaRepar1 || cajaRepar5 == cajaRepar2 || cajaRepar5 == cajaRepar3 || cajaRepar5 == cajaRepar4) {
                                    AsigDescuento5++;
                                }
                            }
                        }
                    }
                    if (isCalculo() == true) {
                        if (validar == 0) {
                            if (AsigDescuento5 == 0) {

                                if (getAsignTotal1() + getAsignTotal2() + getAsignTotal3() + getAsignTotal4() + getAsignTotal5() == (int) Fac.getValorFacturado()) {
                                    setValidacion(true);
                                    setParticionFactura(getAsignTotal1() + getAsignTotal2() + getAsignTotal3() + getAsignTotal4() + getAsignTotal5());
                                    cont = 1;
                                    int codTabla;
                                    codTabla = getCodTabla();
                                    int cajanumFac;
                                    double asignacion;
                                    int pasaron = 0;

                                    if (getListaClientesR().size() > 0) {

                                        for (int i = 0; i < getListaClientesR().size(); i++) {

                                            cajanumFac = ((i == 0) ? cajaRepar1 : (i == 1) ? cajaRepar2 : (i == 2) ? cajaRepar3 : (i == 3) ? cajaRepar4 : cajaRepar5);
                                            asignacion = ((i == 0) ? getAsignTotal1() : (i == 1) ? getAsignTotal2() : (i == 2) ? getAsignTotal3() : (i == 3) ? getAsignTotal4() : getAsignTotal5());
                                            int resp = Fac.FacturaExiste(cajanumFac);
                                            if (resp == 1) {
                                                int pasar = Fac.FacturaReuMismoCli((getListaClientesR().size() > 0) ? "C" : "E", cajanumFac, getListaClientesR().get(i).getCodCliente());
                                                if (pasar > 0) {
                                                    Fac.InsertFactu(resp, codSeg, (codTabla == 0) ? getValorTotalTabla() : Fac.getValorFacturado(), (codTabla == 0) ? "null" : String.valueOf(codTabla), cajanumFac, "NORMAL", 0, asignacion, String.valueOf(getListaClientesR().get(i).getCodCliente()), "null", 10, "null", 0, 0, 1, "", "", (i == 0) ? 1 : 0);
                                                } else {
                                                    mBTodero.setMens("El número de factura se encuentra ocupado por otro cliente");
                                                    mBTodero.warn();
                                                    pasaron++;

                                                }
                                            } else {
                                                Fac.InsertFactu(resp, codSeg, Fac.getValorFacturado(), (codTabla == 0) ? "null" : String.valueOf(codTabla), cajanumFac, "NORMAL", 0, asignacion, String.valueOf(getListaClientesR().get(i).getCodCliente()), "null", 10, "null", 0, 0, 1, "", "", (i == 0) ? 1 : 0);
                                            }

                                            if (!observacionCambio.isEmpty()) {
                                                Fac.InsertFactu(7, 1, 1, "'1'", cajanumFac, "", 1, 0, null, "0", 1, "null", 0, 0, 1, "", observacionCambio, 0);
                                            }

                                        }

                                    }
                                    //Asignacion de numero de factura Entidades

                                    if (getListaEntidadesR().size() > 0) {

                                        for (int i = 0; i < getListaEntidadesR().size(); i++) {

                                            cajanumFac = ((i == 0) ? cajaRepar1 : (i == 1) ? cajaRepar2 : (i == 2) ? cajaRepar3 : (i == 3) ? cajaRepar4 : cajaRepar5);
                                            asignacion = ((i == 0) ? getAsignTotal1() : (i == 1) ? getAsignTotal2() : (i == 2) ? getAsignTotal3() : (i == 3) ? getAsignTotal4() : getAsignTotal5());

                                            int resp = Fac.FacturaExiste(cajanumFac);
                                            if (resp == 1) {
                                                int pasar = Fac.FacturaReuMismoEnti(cajanumFac, getListaEntidadesR().get(i).getCodEntidad());
                                                if (pasar > 0) {
                                                    Fac.InsertFactu(resp, codSeg, Fac.getValorFacturado(), (codTabla == 0) ? "null" : String.valueOf(codTabla), cajanumFac, "NORMAL", 0, asignacion, "null", String.valueOf(getListaEntidadesR().get(i).getCodEntidad()), 10, "null", 0, 0, 1, "", "", (i == 0) ? 1 : 0);
                                                } else {
                                                    mBTodero.setMens("El número de factura se encuentra ocupado por otro cliente");
                                                    mBTodero.warn();
                                                    pasaron++;
                                                }
                                            } else {
                                                Fac.InsertFactu(resp, codSeg, Fac.getValorFacturado(), (codTabla == 0) ? "null" : String.valueOf(codTabla), cajanumFac, "NORMAL", 0, asignacion, "null", String.valueOf(getListaEntidadesR().get(i).getCodEntidad()), 10, "null", 0, 0, 1, "", "", (i == 0) ? 1 : 0);

                                            }

                                            if (!observacionCambio.isEmpty()) {
                                                Fac.InsertFactu(7, 1, 1, "'1'", cajanumFac, "", 1, 0, null, "0", 1, "null", 0, 0, 1, "", observacionCambio, 0);
                                            }
                                        }
                                    }

                                    if (isOpcDistancia()) {
                                        Fac.InsertFactu(4, codSeg, 1, "null", getCajaRepar1(), "NORMAL", Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), 0, 1, "", "", 1);
                                    }

                                    if (isOpcDescuentos()) {
                                        Fac.InsertFactu(6, codSeg, 1, "null", getCajaRepar1(), "NORMAL", Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, getRadioUno(), (int) Fac.getValorDescuento(), 0, 1, "", "", 1);
                                    }

                                    if (isOpcValAdc()) {

                                        Fac.InsertFactu(11, codSeg, 1, "null", getCajaRepar1(), String.valueOf(isOpcIva1()), Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), (int) Fac.getValorAdicional(), getCodParametro(), getVisible1(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                                        if (isOpcValAdc2()) {
                                            Fac.InsertFactu(11, codSeg, 1, "null", getCajaRepar1(), String.valueOf(isOpcIva2()), Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), (int) Fac.getValorAdicional2(), getCodParametro2(), getVisible2(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                                        }
                                        if (isOpcValAdc3()) {
                                            Fac.InsertFactu(11, codSeg, 1, "null", getCajaRepar1(), String.valueOf(isOpcIva3()), Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), (int) Fac.getValorAdicional3(), getCodParametro3(), getVisible3(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                                        }
                                        if (isOpcValAdc4()) {
                                            Fac.InsertFactu(11, codSeg, 1, "null", getCajaRepar1(), String.valueOf(isOpcIva4()), Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), (int) Fac.getValorAdicional4(), getCodParametro4(), getVisible4(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                                        }
                                        if (isOpcValAdc5()) {
                                            Fac.InsertFactu(11, codSeg, 1, "null", getCajaRepar1(), String.valueOf(isOpcIva5()), Fac.getDistancia(), getAsignTotal1(), (getListaClientesR().size() > 0) ? String.valueOf(getListaClientesR().get(0).getCodCliente()) : "null", (getListaEntidadesR().size() > 0) ? String.valueOf(getListaEntidadesR().get(0).getCodEntidad()) : "null", 10, "null", (int) getValorDescuentos(), (int) Fac.getValorAdicional5(), getCodParametro5(), getVisible5(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                                        }
                                    }

                                    if (pasaron == 0) {
                                        RequestContext.getCurrentInstance().execute("PF('Redireccionar').show()");
                                        mBTodero.setMens("Creación de  Exitosa. Dirigase a Impresion de Facturas Para completar el proceso");
                                        mBTodero.info();
                                    } else {
                                        RequestContext.getCurrentInstance().execute("PF('Redireccionar').show()");
                                        mBTodero.setMens("Se presento un error con una factura. Porfavor diragase a prefactura para reiniciar el proceso");
                                        mBTodero.info();
                                    }

                                } else {
                                    mBTodero.setMens("Los valores a facturar no concuerdan con el neto ");
                                    mBTodero.warn();
                                }

                            } else {
                                mBTodero.setMens("Los números de factura deben ser distintos");
                                mBTodero.warn();
                            }
                        } else {
                            mBTodero.setMens(ob + " se encuentra ocupado por otro cliente ");
                            mBTodero.warn();
                        }
                    } else {
                        mBTodero.setMens(" Debe realizar el calculo de la pre factura ");
                        mBTodero.warn();
                    }
                }

                break;

            case "PasarimpresionGrupos":
                int size;

                int porcentaje = 0;
                boolean bandera;
                if (getCajaRepar1() != getCajaRepar2() && getCajaRepar2() != getCajaRepar3() && getCajaRepar3() != getCajaRepar4() && cajaRepar4 != getCajaRepar5()) {

                    if (getAsignTotal1() + getAsignTotal2() + getAsignTotal3() + getAsignTotal4() + getAsignTotal5() == (int) getValorTotalTabla()) {

                        if (!mBArchivos.getListClientesPersonasAFacturarAntic().isEmpty() && mBArchivos.getListClientesPersonasAFacturarAntic() != null) {
                            size = mBArchivos.getListClientesPersonasAFacturarAntic().size();
                            bandera = true;
                        } else {
                            size = mBArchivos.getListEntidadesPersonasAFacturarAntic().size();
                            bandera = false;
                        }
                        for (int j = 0; j < getListTablaPacValor().size(); j++) {
                            Fac.InsertFactu(19, getListTablaPacValor().get(j).getCodSegFac(), getListTablaPacValor().get(j).getValorFacturado(), "null",
                                    0, "NORMAL", 0, 0, "null", "null", 10, "null", 0, 0, 1, "", "", 1);
                        }

                        for (int j = 0; j < getListTablaLiqTabla().size(); j++) {
                            Fac.InsertFactu(19, getListTablaLiqTabla().get(j).getCodSegFac(), getListTablaLiqTabla().get(j).getValorFacturado(), String.valueOf(getListTablaLiqTabla().get(j).getCodTabla()),
                                    0, "NORMAL", 0, 0, "null", "null", 10, "null", 0, 0, 1, "", "", 1);
                        }

                        for (int i = 0; i < size; i++) {
                            Fac.InsertFactu(20, 0, 0, "0", (i + 1 == 1) ? getCajaRepar1() : (i + 1 == 2) ? getCajaRepar2() : (i + 1 == 3) ? getCajaRepar3() : 0, "", 0, 0, "'0'", "null", 10, "null", 0, 0, 1, "", "", 1);

                            /*Ciclo para insertar lista con valores fijos(No tabla en Persona factura)*/
                            for (int j = 0; j < getListTablaPacValor().size(); j++) {
                                if (i + 1 == 1) {
                                    porcentaje = Math.round(Math.round(getListTablaPacValor().get(j).getValorFacturado()) * (float) getAsignTotal1() * 100 / (float) getValorTotalTabla() / 100);
                                }
                                if (i + 1 == 2) {
                                    porcentaje = Math.round(Math.round(getListTablaPacValor().get(j).getValorFacturado()) * ((float) getAsignTotal2() * 100 / (float) getValorTotalTabla()) / 100);
                                }
                                if (i + 1 == 3) {
                                    porcentaje = Math.round(Math.round(getListTablaPacValor().get(j).getValorFacturado()) * ((float) getAsignTotal3() * 100 / (float) getValorTotalTabla()) / 100);
                                }
                                Fac.InsertFactu(21, getListTablaPacValor().get(j).getCodSegFac(), 0, "0",
                                        (i + 1 == 1) ? getCajaRepar1() : (i + 1 == 2) ? getCajaRepar2() : (i + 1 == 3) ? getCajaRepar3() : 0, "", 0,
                                        porcentaje,
                                        (bandera) ? String.valueOf(mBArchivos.getListClientesPersonasAFacturarAntic().get(0).getCodCliente()) : "null",
                                        (!bandera) ? String.valueOf(mBArchivos.getListEntidadesPersonasAFacturarAntic().get(0).getCodEntidad()) : "null", 10, "null", 0, 0, 1, "", "", 1);
                            }

                            /*Ciclo para insertar lista con valores calculador por tabla( en Persona factura)*/
                            for (int j = 0; j < getListTablaLiqTabla().size(); j++) {
                                if (i + 1 == 1) {
                                    porcentaje = Math.round(Math.round(getListTablaLiqTabla().get(j).getValorFacturado()) * (float) getAsignTotal1() * 100 / (float) getValorTotalTabla() / 100);
                                } else if (i + 1 == 2) {
                                    porcentaje = Math.round(Math.round(getListTablaLiqTabla().get(j).getValorFacturado()) * ((float) getAsignTotal2() * 100 / (float) getValorTotalTabla()) / 100);
                                } else if (i + 1 == 3) {
                                    porcentaje = Math.round(Math.round(getListTablaLiqTabla().get(j).getValorFacturado()) * ((float) getAsignTotal3() * 100 / (float) getValorTotalTabla()) / 100);
                                }
                                Fac.InsertFactu(21, getListTablaLiqTabla().get(j).getCodSegFac(), 0, "0",
                                        (i + 1 == 1) ? getCajaRepar1() : (i + 1 == 2) ? getCajaRepar2() : (i + 1 == 3) ? getCajaRepar3() : 0, "", 0,
                                        porcentaje,
                                        (bandera) ? String.valueOf(mBArchivos.getListClientesPersonasAFacturarAntic().get(i).getCodCliente()) : "null",
                                        (!bandera) ? String.valueOf(mBArchivos.getListEntidadesPersonasAFacturarAntic().get(i).getCodEntidad()) : "null", 10, "null", 0, 0, 1, "", "", 1);
                            }

                        }

                        for (int j = 0; j < getListTablaDistancias().size(); j++) {
                            Fac.InsertFactu(4, getListTablaDistancias().get(j).getCodSegFac(), 1, "null", 0, "null", getListTablaDistancias().get(j).getDistancia(), 0, "null", "null", 0, "null", 0, 0, 1, "", "", 1);
                        }

                        for (int k = 0; k < getListTablaDescuentos().size(); k++) {
                            Fac.InsertFactu(6, getListTablaDescuentos().get(k).getCodSegFac(), 1, "null", 0, "null", 0, 0, "null", "null", 10, (getListTablaDescuentos().get(k).getTipoValor().equalsIgnoreCase("%")) ? "P" : "F", (int) getListTablaDescuentos().get(k).getDescuentos(), 0, 1, "", "", 1);
                        }

                        for (int l = 0; l < ListTablaValAdici.size(); l++) {
                            Fac.InsertFactu(11, getListTablaValAdici().get(l).getCodSegFac(), 1, "null", 0, (getListTablaValAdici().get(l).getApplyIva().contains("No")) ? "false" : "true",
                                    0, 0, "null", "null", 10, "null", 0, (int) getListTablaValAdici().get(l).getValorAdic(),
                                    getListTablaValAdici().get(l).getCodTabla(), getVisible1(), "", Fac.impuestoFactAntcpo().get(0).getCodImpuesto());
                        }

                        RequestContext.getCurrentInstance().execute("PF('ConfirFactu').hide()");
                        RequestContext.getCurrentInstance().execute("PF('Redireccionar').show()");
                        mBTodero.setMens("Creación de  Exitosa. Dirigase a Impresion de Facturas Para completar el proceso");
                        mBTodero.info();
                    } else {
                        mBTodero.setMens("Los números de factura deben ser distintos");
                        mBTodero.warn();
                    }

                    break;
                }

        }

    }

    public void SumarParticion(int anti1, int anti2, int anti3, int anti4, int anti5) {
        setAnticipo1(anti1);
        setAnticipo2(anti2);
        setAnticipo3(anti3);
        setAnticipo4(anti4);
        setAnticipo5(anti5);
    }

    public void CargarNFactXCliente(int Tipo, int CodSeg) {

        setNumCajaCambiar(Tipo);
        setTipoSelPers(Tipo);
        int CodPersn;
        if (getListaEntidadesR().size() > 0) {
            CodPersn = getListaEntidadesR().get(Tipo).getCodEntidad();
            setListFactAscdas(Fac.NFactAsocPorClient(CodSeg, CodPersn, "Entidad"));

        } else if (getListaClientesR().size() > 0) {
            CodPersn = getListaClientesR().get(Tipo).getCodCliente();
            setListFactAscdas(Fac.NFactAsocPorClient(CodSeg, CodPersn, "Cliente"));
        }
        RequestContext.getCurrentInstance().execute("PF('FactAsocd').show()");

    }

    public void CargarFactuGruposxClie(String Tipo, int codPer) {

        setListFactAscdas(Fac.NFactAsocPorClient(0, codPer, Tipo));
        RequestContext.getCurrentInstance().execute("PF('FactAsocd').show()");

    }

    String Nombre;
    private boolean ImpuReIca = false;

    public boolean isImpuReIca() {
        return ImpuReIca;
    }

    public void setImpuReIca(boolean ImpuReIca) {
        this.ImpuReIca = ImpuReIca;
    }

    private int valorIca = 0;
    private int valorFuente = 0;
    private int valorReIva = 0;

    public int getValorIca() {
        return valorIca;
    }

    public void setValorIca(int valorIca) {
        this.valorIca = valorIca;
    }

    public int getValorFuente() {
        return valorFuente;
    }

    public void setValorFuente(int valorFuente) {
        this.valorFuente = valorFuente;
    }

    public int getValorReIva() {
        return valorReIva;
    }

    public void setValorReIva(int valorReIva) {
        this.valorReIva = valorReIva;
    }
    int validar = 0;

    public void AplicarImpuestos(String condicion) throws SQLException {
        validar = 1;
        switch (condicion) {
            case "Aplicar":

                if ((exento == 0 && ListInfoImpri.size() == 1) || (ListInfoImpri.size() > exento)) {
                    if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {
                        if (getFuente1() == 0) {
                            mBTodero.setMens("Debe seleccionar un valor para el Retefuente");
                            mBTodero.warn();
                            validar++;
                        }

                        if (!Fac.getCalificacion().equalsIgnoreCase("REGIMEN COMUN")) {
                            if (getReteiva() == 0) {
                                mBTodero.setMens("Debe seleccionar un valor para el Reteiva");
                                mBTodero.warn();
                                validar++;
                            }
                        }
                    }

                    if (getIca1() == 0 && opcReteIca) {
                        mBTodero.setMens("Debe seleccionar un valor para el Reteica");
                        mBTodero.warn();
                        setValorIca(0);
                        validar++;
                    }
                }
                if (validar == 1) {

                    setSubtotal(Math.round(Fac.getValor() + getValorAdicion() + getValAdiFacIva() - getValorDescuentos() - getSumaFacAnt()));

                    if (subtotal > 0) {
                        if ((exento == 0 && ListInfoImpri.size() == 1) || (ListInfoImpri.size() > exento)) {
                            setIva(getSubtotal() * getIvaBD() / 100);
                        }

                        if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {
                            setValorFuente((int) Math.round((getSubtotal() * getFuente1() / 100)));

                            if (!Fac.getCalificacion().equalsIgnoreCase("REGIMEN COMUN")) {
                                setValorReIva((int) Math.round((getIva() * getReteiva() / 100)));
                            }
                        }

                        if (opcReteIca) {
                            setValorIca((int) Math.round((getSubtotal() * getIca1() / 100)));
                        } else {
                            setValorIca(0);
                        }
                    } else {

                        mBTodero.setMens("No se pueden calcular impuestos sobre un subtotal negativo o igual a cero");
                        mBTodero.warn();
                        iva = 0;
                        valorFuente = 0;
                        valorReIva = 0;
                        valorIca = 0;

                    }

                    total = Math.round(subtotal + iva - valorIca - valorFuente - valorReIva);

                    setTotalpagarFactAnt(Math.round(getSubtotal() + getValorAdicionSinIva() + getValAdiFacSinIva() - getValorIca() - getValorFuente() - getValorReIva() - getAnticipo5() + getIva()));

                }
                break;

            case "Modificar":

                if (!opcIva1 && !opcRetefuente && !opcIva2 && !opcReteIva) {
                    mBTodero.setMens("No ha seleccionado ningun item para remover");
                    mBTodero.warn();
                } else {
                    ObserFactAnti = "";
                    if (opcIva1) {
                        ObserFactAnti = "Iva";
                    }
                    if (opcRetefuente) {
                        ObserFactAnti = ObserFactAnti + ((ObserFactAnti.isEmpty()) ? "" : ", ") + "Retefuente ";
                    }
                    if (opcIva2) {
                        ObserFactAnti = ObserFactAnti + ((ObserFactAnti.isEmpty()) ? "" : ", ") + "Reteica ";
                    }
                    if (opcReteIva) {
                        ObserFactAnti = ObserFactAnti + ((ObserFactAnti.isEmpty()) ? "" : ", ") + "Reteiva ";
                    }

                    RequestContext.getCurrentInstance().execute("PF('removerOk').show()");
                }
                break;

            case "AcepModifi":
                if (opcIva1) {
                    setIva(0);
                    if (valorReIva > 0) {
                        setValorReIva(0);
                    }
                    opcIva1 = false;
                }
                if (opcRetefuente) {
                    setValorFuente(0);
                    opcRetefuente = false;
                }
                if (opcIva2) {
                    setValorIca(0);
                    opcIva2 = false;
                }
                if (opcReteIva) {
                    setValorReIva(0);
                    opcReteIva = false;
                }

                total = Math.round(subtotal + iva - valorIca - valorFuente - valorReIva);
                setTotalpagarFactAnt((long) (getSubtotal() + getValorAdicionSinIva() + getValAdiFacSinIva() - getValorIca() - getValorFuente() - getValorReIva() - getAnticipo5() + getIva()));

                RequestContext.getCurrentInstance().execute("PF('removerOk').hide()");
                RequestContext.getCurrentInstance().execute("PF('impuApli').hide()");
                break;

            case "actuConcep":
                setListInfoImpri(Fac.ConsulImpreConcepto(Fac.getCodFactu()));
                setObservacionCambio(getListInfoImpri().get(0).getConcepto());
                RequestContext.getCurrentInstance().execute("PF('viewConcep').show()");
                break;

            case "confirConcep":
                FacTablas = new LogFacturacion();
                FacTablas.insertarDetalle("upDetlConce", getObservacionCambio(), Fac.getCodFactu(), "", "", 1);
                RequestContext.getCurrentInstance().execute("PF('viewConcep').hide()");
                break;
        }

    }

    public void Facturar(String tipo) throws SQLException, IOException {

        getFuente1();
        getReteiva();
        switch (tipo) {
            case "PopUp":
                validar = 1;

                if ((exento == 0)) {
                    if (subtotal > 0) {
                        if (Fac.getTipoFactura().equalsIgnoreCase("Normal")) {
                            if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {
                                if (getFuente1() == 0 || getValorFuente() <= 0) {
                                    mBTodero.setMens("Debe aplicar un valor para el Retefuente");
                                    mBTodero.warn();
                                    validar++;
                                }

                                if (!Fac.getCalificacion().equalsIgnoreCase("REGIMEN COMUN")) {
                                    if (getReteiva() == 0 || getValorReIva() <= 0) {
                                        mBTodero.setMens("Debe seleccionar un valor para el Reteiva");
                                        mBTodero.warn();
                                        validar++;
                                    }
                                }
                            }
                        }
                        if (!Fac.getTipoFactura().equalsIgnoreCase("Excedente") && opcReteIca && (getValorIca() <= 0 || getIca1() == 0)) {
                            mBTodero.setMens("Debe aplicar un valor para el Reteica o retirar la opción");
                            mBTodero.warn();
                            validar++;
                        }
                    } else {
                        mBTodero.setMens("La factura pasara a saldos a favor del cliente. Dirigase al modulo Cartera para el manejo de este.");
                        mBTodero.warn();

                    }
                }
                if (validar == 1) {
                    RequestContext.getCurrentInstance().execute("PF('confirmar').show()");
                }
                break;

            case "Normal":

                if (listaDetallesFac != null) {
                    for (int i = 0; i < listaDetallesFac.size(); i++) {
                        Fac.insertarDetalle("", listaDetallesFac.get(i).getDetalle(), Fac.getCodFactu(), String.valueOf(mBsesion.codigoMiSesion()), "", 1);
                        getListaDetallesFac();
                    }

                }

                if (isCambiarFecha()) {
                    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                    Fac.InsertFactu(9, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) getTotalpagarFactAnt(), null, "0", 1, "null", 0, 0, 1, "", Format.format(this.fechaFacturacion), 0);
                } else {
                    Fac.InsertFactu(9, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) getTotalpagarFactAnt(), null, "0", 1, "null", 0, 0, 1, "", "actual", 0);
                }

                if (Fac.getTipoFactura().contains("ANTICIPO")) {
                    Fac.InsertFactu(12, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) getTotalpagarFactAnt(), null, "0", 1, "null", 0, 0, 1, "", "actual", 0);
                }

                //Impuestos
                if ((exento == 0 && ListInfoImpri.size() == 1) || (ListInfoImpri.size() > 1 && ListInfoImpri.size() > exento)) {
                    if ((Fac.getTipoFactura().equalsIgnoreCase("Normal") && getIvaBD() > 0) || (!Fac.getTipoFactura().equalsIgnoreCase("Normal") && getIvaBD() > 0 && getIva() > 0)) {
                        Fac.InsertFactu(10, 1, 1, "'1'", Fac.getCodFactu(), "", 1, 0, null, "0", getIvaBD(), "null", 0, 0, 1, "", "iva", 0);
                    }
                }

                if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {
                    if (getFuente1() > 0 && getValorFuente() > 0) {
                        Fac.InsertFactu(10, 1, 1, "'1'", Fac.getCodFactu(), "", 1, 0, null, "0", getFuente1(), "null", 0, 0, 1, "", "fuen", 0);
                    }

                    if (Fac.getCalificacion().equalsIgnoreCase("GRANDES CONTRIBUYENTES")) {
                        if (getValorReIva() > 0 && getReteiva() > 0) {
                            Fac.InsertFactu(10, 1, 1, "'1'", Fac.getCodFactu(), "", 1, 0, null, "0", getReteiva(), "null", 0, 0, 1, "", "teiva", 0);
                        }
                    }

                }

                if (opcReteIca && getIca1() > 0 && valorIca > 0) {
                    Fac.InsertFactu(10, 1, 1, "'1'", Fac.getCodFactu(), "", 1, 0, null, "0", getIca1(), "null", 0, 0, 1, "", "ica", 0);
                }

                if (getSelectlistaFactAnticipos() != null && getSelectlistaFactAnticipos().size() > 0) {
                    for (int i = 0; i < getSelectlistaFactAnticipos().size(); i++) {
                        Fac.CambiarEstadoFactuAnti(Fac.getCodFactu(), getSelectlistaFactAnticipos().get(i).getCodFactuAnti());
                    }
                }

                if (getTotalpagarFactAnt() == 0) {
                    Fac.InsertFactu(12, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) getTotalpagarFactAnt(), null, "0", 1, "null", 0, 0, 1, "", "actual", 0);
                } else if (getTotalpagarFactAnt() < 0) {
                    Fac.InsertFactu(13, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) getTotalpagarFactAnt(), null, "0", 1, "null", 0, 0, 1, "", "actual", 0);
                }

                Envio.GestioEnvio(4, Fac.getCodFactu(), "");

                mBTodero.setMens("Facturacion exitosa N° Fatura: " + Fac.getCodFactu());
                mBTodero.info();
                RequestContext.getCurrentInstance().execute("PF('confirmar').hide()");
                RequestContext.getCurrentInstance().execute("PF('RedireccionarImpre').show()");
                pdfImpresionFactura();
                break;

            case "Excedente":

                if (listaDetallesFac != null) {
                    for (int i = 0; i < listaDetallesFac.size(); i++) {
                        Fac.insertarDetalle("", listaDetallesFac.get(i).getDetalle(), Fac.getCodFactu(), String.valueOf(mBsesion.codigoMiSesion()), "", 1);
                        getListaDetallesFac();
                    }

                }
                Fac.InsertFactu(15, 1, 1, "'1'", Fac.getCodFactu(), "", 1, (int) (Fac.getValor() - valorTotalTabla), null, "0", 1, "null",
                        (getSimbolo().equals("%") && getValorDescuentos() > 0) ? getValorDescuentos() : 0, 0, 1, "", "", 0);

                for (int i = 0; i < listaImpuesto.size(); i++) {
                    Fac.InsertFactu(16, 1, 1, "'1'", Fac.getCodFactu(), "", 1, 0, null, "0", getListaImpuesto().get(i).getCodImpuesto(), "null", 0, 0, 1, "", "", 0);
                }

                mBTodero.setMens("Facturacion exitosa N° Fatura: " + Fac.getCodFactu());
                mBTodero.warn();
                RequestContext.getCurrentInstance().execute("PF('confirmar').hide()");
                RequestContext.getCurrentInstance().execute("PF('RedireccionarImpre').show()");
                pdfImpresionFactura();
                break;
        }

    }

    public void Limpiar(int tip) {

        switch (tip) {

            case 1:
                opcDesaprueba = false;
                observacionCambio = "";
                setValAdiFacIva(0);
                setValAdiFacSinIva(0);
                setListaDistanciaTablas(null);
                setListaEntidadesR(null);
                setListaClientesR(null);
                setValorAnti(0);
                Fac.setDistancia(0);
                setOpcDistancia(false);
                setVisible1("");
                setOpcDescuentos(false);
                setOpcValAdc(false);
                setOpcValAdc2(false);
                setOpcValAdc3(false);
                setOpcValAdc4(false);
                setOpcValAdc5(false);
                setValorPac(0);
                setValorAnticipos(0);
                Fac.setValorTotalAdicional(0);
                Fac.setTotalValorAdicionalSinIva(0);
                Fac.setValorFacturado(0);
                setCalculo(false);
                setValidacion(false);
                setNombreTable("");
                setValorDescuentos(0);
                Fac.setValorFacturado(0);
                Fac.setValorNetoFacturado(0);
                setIva(0);
                setSubtotal(0);
                setAsignTotal1(0);
                setAsignTotal2(0);
                setAsignTotal3(0);
                setAsignTotal4(0);
                setAsignTotal5(0);
                setCajaRepar1(0);
                setCajaRepar2(0);
                setCajaRepar3(0);
                setCajaRepar4(0);
                setCajaRepar5(0);
                setListInfoImpri(null);
                Fac.setTelefono("");
                Fac.setCliente("");
                setListPendientXImpri(null);
                setAsigDescuento1(0);
                setAsigDescuento2(0);
                setAsigDescuento3(0);
                setAsigDescuento4(0);
                setAsigDescuento5(0);
                setAsigValorAdicional1(0);
                setAsigValorAdicional2(0);
                setAsigValorAdicional3(0);
                setAsigValorAdicional4(0);
                setAsigValorAdicional5(0);
                Fac.setValorDescuento(0);
                radioUno = "";
                //  setValorAdicion(0);
                break;
            case 2:
                observacionCambio = "";
                setVisible1("");
                setValAdiFacIva(0);
                setValAdiFacSinIva(0);
                mBAdministacion.setListaImpuestos(null);
                mBAdministacion.setSelectImpuestos(null);
                setListInfoImpri(null);
                setTotalpagarFactAnt(0);
                setIva(0);
                setIca1(0);
                setFuente1(0);
                setValorReIva(0);
                setValorIca(0);
                setValorFuente(0);
                setOpcReteIca(false);
                setOpcRetefuente(false);
                descuento = 0;
                setOpcReteIva(false);
                setValorAdicion(0);
                setValorAdicionSinIva(0);
                setValorDescuentos(0);
                setTotalpagarFactAnt(0);
                setSubtotal(0);
                setValorReIva(0);
                setValorFuente(0);
                setValorIca(0);
                setListaFacturaAnticipo(null);
                setSumaFacAnt(0);
                setSelectlistaFactAnticipos(null);
                setFechaFacturacion(null);
                setCambiarFecha(false);
                listaDetallesFac.clear();
                detalleFactura = "";
                setObservaciones(null);
                break;

            case 3:
                observacionCambio = "";
                setTipoFacturacion("");
                setTipoLiqui("");
                ListTablaDescuentos.clear();
                ListTablaDistancias.clear();
                ListTablaValAdici.clear();
                ListTablaPacValor.clear();
                ListTablaAnticipos.clear();
                setRadioUno("");
                setSelectListPendientXFact(null);
                cargarNumAvalu.clear();
                setValorTotalTabla(0);
                setValorAdicion(0);
                setAsignTotal1(0);
                setAsignTotal2(0);
                setAsignTotal3(0);
                setDistancia(0);
                setDescuento(0);
                setValorAdic(0);
                setCodParametro(0);
                setOpcIva1(false);
                break;

            case 4:
                setReteiva(0);
                setFuente1(0);
                setIca1(0);
                break;

            case 5:
                Fac.setDistancia(0);
                break;
            case 6:
                Fac.setValorDescuento(0);
                Fac.setObserDescuento("");
                break;
            case 7:
                Fac.setValorAdicional(0);
                Fac.setValorAdicional2(0);
                Fac.setValorAdicional3(0);
                Fac.setValorAdicional4(0);
                Fac.setValorAdicional5(0);
                codParametro = 0;
                opcIva1 = false;
                visible1 = "";

                codParametro2 = 0;
                opcIva2 = false;
                visible2 = "";

                codParametro3 = 0;
                opcIva3 = false;
                visible3 = "";

                codParametro4 = 0;
                opcIva4 = false;
                visible4 = "";

                codParametro5 = 0;
                opcIva5 = false;
                visible5 = "";
                break;

            case 8:
                listaDetallesFac = new ArrayList<>();
                detalleFactura = "";
                break;

        }
    }

    public void OnDlgAnularFact() {
        try {
            RequestContext.getCurrentInstance().execute("PF('DlgAnularFact').show()");
        } catch (NumberFormatException e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".DlgAddObserRadAnti()' causado por: " + e.getMessage());
            mBTodero.error();
        }
    }

    //Tatiana 07/04/2016
    //Cargar la informacion general del bien , e incluirla en los Tabs de FACTURACION COPIA
    public void CargeInfoBien() throws SQLException {
        //Verificar si el No de Avaluo digitado existe 
        setNavaluoCopia(NavaluoCopia);
        //Colocar un render en TRUE o FALSE para poder determinar si existe o no ese Avaluo.

    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    int ValorAd;
    int TipoValAdc;

    /**
     * Metodo tipo ajax que carga el metodo cargAnalista() para agregar a los
     * analistas Tatiana //18/03/16
     */
    public void onAnalista() {
        try {

            RequestContext.getCurrentInstance().execute("PF('DlgAnalista').show()");
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".onAnalista()' causado por: " + e.getMessage());
            mBTodero.error();
        }
    }

    private String FormName = "";

    public String getFormName() {
        return FormName;
    }

    public void setFormName(String FormName) {
        this.FormName = FormName;
    }
    int Count = 0;

    public void ConsultarPersona(int opc) {
        Fac = new LogFacturacion();
        LogFacturacion Fact1 = new LogFacturacion();
        try {
            switch (opc) {
                case 1:
                    getSelectPersFactAnti().clear();
                    setEstadoFact(false);
                    validacion = false;
                    switch (getOpcionFacturarA()) {
                        case "Entidad":
                            if (!mBEntidad.getCodEntidad1().equals("0") && !mBEntidad.getCodSucursal1().equals("0") && !mBEntidad.getCodAsesor1().equals("0")) {
                                validacion = true;
                                validarPanel = true;
                            } else {
                                mBTodero.setMens("Falta ingresar la información de " + ((mBEntidad.getCodEntidad1().equals("0")) ? "Entidad" : (mBEntidad.getCodSucursal1().equals("0")) ? "Sucursal" : "Asesor"));
                                mBTodero.info();
                            }

                            break;
                        case "Cliente":
                            switch (getOpcCliente()) {
                                case "Nombre":
                                    setListPersFacAnti(ClientesAFact.ConsultarPersona("Nombre_Cliente", "Cliente", getNomPers()));
                                    break;
                                case "Identificacion":
                                    setListPersFacAnti(ClientesAFact.ConsultarPersona("Numero_DocCliente", "Cliente", String.valueOf(getIdentfPersona())));
                                    break;
                            }
                            RequestContext.getCurrentInstance().execute("PF('DlgPers').show()");

                            break;
                    }
                    break;
                case 2:
                    RequestContext.getCurrentInstance().execute("PF('DlgPers').hide()");
                    break;
                //FACTURAR INSERCION ANTICIPO
                case 3:
                    if (getNfactAnticpo() != 0 && !"".equals(getObserFactAnti())) {
                        //Inserta la informacion correspondiente a las tablas para Factura Anticipos
                        CodFactura = getNfactAnticpo();
                        //Validar que el numero de Factura seleccionado este sin utilizar.
                        Rsltad = Fact1.ValidarNFact(CodFactura);
                        if (Rsltad == 0) {
                            switch (OpcionFacturarA) {
                                case "Entidad":
                                    Rst = Fact1.InsertFactuAnticipo(2, 0, 0, "0", CodFactura, "", 0, getTotalpagarFactAnt(), null, mBEntidad.getCodEntidad1(), 0, "", 0, 0, 0, "", getObserFactAnti());
                                    Fac.insertarDetalle("detFacCopi", "", CodFactura, mBEntidad.getCodSucursal1(), mBEntidad.getCodAsesor1(), 1);

                                    break;
                                case "Cliente":
                                    Rst = Fact1.InsertFactuAnticipo(2, 0, 0, "0", CodFactura, "", 0, getTotalpagarFactAnt(), String.valueOf(getSelectPersFactAnti().get(0).getCodCliente()), null, 0, "", 0, 0, 0, "", getObserFactAnti());
                                    break;
                            }
                            if (Rst == 1) {
                                mBTodero.setMens("Registro Satisfactorio");
                                mBTodero.info();
                                Clear();
                                RequestContext.getCurrentInstance().execute("PF('FactuAntiCon').hide()");
                                RequestContext.getCurrentInstance().execute("PF('RedFact').show()");

                            }

                        } else {
                            mBTodero.setMens("Este Numero de factura esta siendo utilzado, por favor seleccione  uno nuevo");
                            mBTodero.info();
                            setNfactAnticpo(Fact1.MaxNoFact());
                        }
                    } else {
                        mBTodero.setMens("Diligencia la informacion de los campos obligatorios");
                        mBTodero.warn();
                    }
                    break;

                case 5:
                    if ("FactuAntiCon".equals(FormName)) {
                        if (getNfactAnticpo() != 0) {
                            if (!"".equals(getObserFactAnti())) {
                                if (totalpagarFactAnt > 0) {
                                    RequestContext.getCurrentInstance().execute("PF('" + FormName + "').show()");
                                } else {
                                    mBTodero.setMens("El valor a facturar debe ser mayor a cero");
                                    mBTodero.info();
                                }
                            } else {
                                mBTodero.setMens("Diligencie los campos Obligatorios");
                                mBTodero.info();
                            }
                        } else {
                            mBTodero.setMens("Diligencie los campos Obligatorios");
                            mBTodero.info();
                            nfactAnticpo = Fact1.MaxNoFact() + 1;
                        }
                    }

                    if ("FactuConcepto".equals(FormName)) {

                        if ((getOpcionFacturarA().equalsIgnoreCase("Entidad") && !mBEntidad.getCodEntidad1().equals("0") && !mBEntidad.getCodSucursal1().equals("0") && !mBEntidad.getCodAsesor1().equals("0"))
                                || (getOpcionFacturarA().equalsIgnoreCase("Cliente") && selectPersFactAnti.size() > 0)) {
                            if (getNfactAnticpo() != 0) {
                                if (!"".equals(getCodTipoConcepto())) {
                                    if (totalpagarFactAnt > 0) {
                                        RequestContext.getCurrentInstance().execute("PF('" + FormName + "').show()");
                                    } else {
                                        mBTodero.setMens("El valor a facturar debe ser mayor a cero");
                                        mBTodero.info();
                                    }
                                } else {
                                    mBTodero.setMens("Diligencie los campos Obligatorios");
                                    mBTodero.info();
                                }
                            } else {
                                mBTodero.setMens("Diligencie los campos Obligatorios");
                                mBTodero.info();
                            }
                        } else {
                            mBTodero.setMens("Falta ingresar la información de " + ((mBEntidad.getCodEntidad1().equals("0")) ? "Entidad"
                                    : (mBEntidad.getCodSucursal1().equals("0")) ? "Sucursal" : (mBEntidad.getCodAsesor1().equals("0")) ? "Asesor" : "Cliente"));
                            mBTodero.info();
                        }

                    }
                    if ("FactuCopia".equals(FormName)) {

                        if ((getOpcionFacturarA().equalsIgnoreCase("Entidad") && !mBEntidad.getCodEntidad1().equals("0") && !mBEntidad.getCodSucursal1().equals("0") && !mBEntidad.getCodAsesor1().equals("0"))
                                || (getOpcionFacturarA().equalsIgnoreCase("Cliente") && selectPersFactAnti.size() > 0)) {
                            if (getNfactAnticpo() != 0) {

                                if (totalpagarFactAnt > 0) {
                                    if (!ObserFactAnti.isEmpty()) {
                                        RequestContext.getCurrentInstance().execute("PF('" + FormName + "').show()");
                                    } else {
                                        mBTodero.setMens("Debe ingresar una observación");
                                        mBTodero.info();
                                    }
                                } else {
                                    mBTodero.setMens("El valor a facturar debe ser mayor a cero");
                                    mBTodero.info();
                                }

                            } else {
                                mBTodero.setMens("Diligencie los campos Obligatorios");
                                mBTodero.info();
                            }
                        } else {
                            mBTodero.setMens("Falta ingresar la información de " + ((mBEntidad.getCodEntidad1().equals("0")) ? "Entidad"
                                    : (mBEntidad.getCodSucursal1().equals("0")) ? "Sucursal" : (mBEntidad.getCodAsesor1().equals("0")) ? "Asesor" : "Cliente"));
                            mBTodero.info();
                        }
                    }
                    break;

                case 4:

                    ValorFactura1 = getValorFactura();
                    ValorIva = getIvaAnticp();
                    //CALCULAR EL valor total a facturar cone l iva
                    nfactAnticpo = Fact1.MaxNoFact() + 1;
                    TotalIvaAnti = (int) (long) Double.parseDouble(String.valueOf(valorfact1));
                    if (getCodTipoConcepto() != 0) {
                        Nombre = FacTablas.NameValoradc(String.valueOf(getCodTipoConcepto()), "Fact_Concepto");
                    }
                    break;
                case 6:
                    // 30/03/2016
                    //Inserta la informacion correspondiente a las tablas para Factura Concepto
                    CodFactura = getNfactAnticpo();
                    if (this.CodTipoConcepto != 0 && getCodTipoConcepto() != 0) {
                        //Validar que el numero de Factura seleccionado este sin utilizar.
                        Rsltad = Fact1.ValidarNFact(CodFactura);
                        Nombre = FacTablas.NameValoradc(String.valueOf(getCodTipoConcepto()), "Fact_Concepto");
                        if (Rsltad == 0) {

                            Rst = Fact1.InserFacConcepto(3, CodFactura, getTotalpagarFactAnt(), mBEntidad.getCodEntidad1().equals("0") ? String.valueOf(getSelectPersFactAnti().get(0).getCodCliente()) : "null",
                                    (!mBEntidad.getCodEntidad1().equals("0")) ? mBEntidad.getCodEntidad1() : "null", mBEntidad.getCodSucursal1(), mBEntidad.getCodAsesor1(), getCodTipoConcepto());

                            if (Rst == 1) {
                                mBTodero.setMens("Registro Satisfactorio");
                                mBTodero.info();
                                Clear();
                                RequestContext.getCurrentInstance().execute("PF('FactuConcepto').hide()");
                                RequestContext.getCurrentInstance().execute("PF('RedFact').show()");
                            }
                        } else {
                            mBTodero.setMens("Este Numero de factura esta siendo utilzado, por favor seleccione  uno nuevo");
                            mBTodero.info();
                            setNfactAnticpo(Fact1.MaxNoFact());
                        }
                    } else {
                        mBTodero.setMens("Falta seleccionar el concepto");
                        mBTodero.info();
                    }
                    break;
                case 7:
                    //Limpiar Variables
                    this.opcAplicarIva = "";
                    this.OpcCliente = null;
                    this.OpcEntidad = "";
                    this.NomPers = "";
                    this.IdentfPersona = 0;
                    selectPersFactAnti = null;
                    totalpagarFactAnt = 0;
                    validacion = false;
                    this.ValorFactura = 0;
                    this.nfactAnticpo = 0;
                    EstadoFact = false;
                    ObserFactAnti = "";
                    estadoPnlIva = "";
                    TotalIvaAnti = 0;
                    mBEntidad.setCodEntidad1("0");
                    mBEntidad.setCodSucursal1("0");
                    mBEntidad.setCodAsesor1("0");

                    //RequestContext.getCurrentInstance().execute("PF('RedFact').hide()");
                    break;

                case 10:
                    OpcionFacturarA = "";
                    ConsultarPersona(7);
                    setOpcionFacturarA("");
                    validacion = false;
                    validarPanel = false;
                    mBCliente.Limpiar();
                    mBEntidad.LimpiarEntiFac();
                    mBArchivos.CleanRepartValor();

                    break;
                case 8:
                    switch (opcAplicarIva) {
                        case "Si_opcIva":
                            estadoPnlIva = "tat1";
                            setTotalIvaAnti(0);
                            setValorFactura(0);
                            setTotalpagarFactAnt(0);
                            setObserFactAnti("");
                            break;
                        case "No_opcIva":
                            estadoPnlIva = "tat";
                            setTotalIvaAnti(0);
                            setValorFactura(0);
                            setTotalpagarFactAnt(0);
                            setObserFactAnti("");
                            setEstadoFact(true);
                            break;
                    }
                    break;
                case 9:

                    //Mostrar FACTURA COPIA
                    if (getNfactAnticpo() != 0) {
                        //Inserta la informacion correspondiente a las tablas para Factura Anticipos
                        CodFactura = getNfactAnticpo();
                        //Validar que el numero de Factura seleccionado este sin utilizar.
                        Rsltad = Fac.ValidarNFact(CodFactura);
                        if (Rsltad == 0) {
                            switch (OpcionFacturarA) {
                                case "Entidad":
                                    Rst = Fac.InsertFactuAnticipo(8, Fact1.validarNAval(getNavaluoCopia()), 0, "0", CodFactura, "", 0, getTotalpagarFactAnt(), null, mBEntidad.getCodEntidad1(), 0, "", 0, 0, 0, "", getObserFactAnti());
                                    Fac.insertarDetalle("detFacCopi", "", CodFactura, mBEntidad.getCodSucursal1(), mBEntidad.getCodAsesor1(), 1);
                                    break;
                                case "Cliente":
                                    Rst = Fac.InsertFactuAnticipo(8, Fact1.validarNAval(getNavaluoCopia()), 0, "0", CodFactura, "", 0, getTotalpagarFactAnt(), String.valueOf(getSelectPersFactAnti().get(0).getCodCliente()), null, 0, "", 0, 0, 0, "", getObserFactAnti());
                                    break;
                            }

                            if (Rst == 1) {
                                mBTodero.setMens("Registro Satisfactorio");
                                mBTodero.info();
                                Clear();
                                RequestContext.getCurrentInstance().execute("PF('FactuCopia').hide()");
                                RequestContext.getCurrentInstance().execute("PF('RedFact').show()");
                            }

                        } else {
                            mBTodero.setMens("Este Numero de factura esta siendo utilzado, por favor seleccione  uno nuevo");
                            mBTodero.info();
                            setNfactAnticpo(Fac.MaxNoFact());
                        }
                    } else {
                        mBTodero.setMens("Diligencia la informacion de los campos obligatorios");
                        mBTodero.warn();
                    }
                    break;
                default:
                    break;
            }

        } catch (SQLException e) {
            mBTodero.setMens("Reventado");
            mBTodero.fatal();
        }

    }

    public void Porcentualizar(String tipo) throws SQLException {
        int valorPorcentaje;
        double valorDesFactu = 0;
        int cantidadLista = getListInfoImpri().size();
        exento = 0;
        switch (tipo) {

            case "NORMAL":

                FacTablas = new LogFacturacion();

                setValAdiFacIva(Fac.ValoresAdicionalesAvaluo("sinAvaluoIva", Fac.getCodFactu()));
                setValAdiFacSinIva(Fac.ValoresAdicionalesAvaluo("sinAvaluoNoIva", Fac.getCodFactu()));
                //Conocer si la ciudad esta exenta de impuestos
                for (int i = 0; i < cantidadLista; i++) {
                    if (!getListInfoImpri().get(i).getCiudadText().equals("NO")) {
                        exento++;
                    }

                }

                for (int i = 0; i < cantidadLista; i++) {

                    //descuentos
                    if (getListInfoImpri().get(i).getDescuentos() != 0) {

                        if (getListInfoImpri().get(i).getTipoDescuento().equals("$")) {
                            setValorDescuentos((int) Math.round(getValorDescuentos() + ((getListInfoImpri().get(i).getDescuentos() * getListInfoImpri().get(i).getPorcentajeParti()) / 100)));
                            setTipoDescuento(" $");
                        }
                        if (getListInfoImpri().get(i).getTipoDescuento().equals("%")) {
                            valorPorcentaje = (int) ((getListInfoImpri().get(i).getValorTotalAvaluo() * getListInfoImpri().get(i).getDescuentos()) / 100);
                            setValorDescuentos((int) Math.round(getValorDescuentos() + ((valorPorcentaje * getListInfoImpri().get(i).getPorcentajeParti()) / 100)));
                            setTipoDescuento(" %");
                        }

                    }

                    if ("P".equals(getListInfoImpri().get(i).getTipoDescuentoFactu()) && i == 0) {
                        setValorDescuentos(getValorDescuentos() + ((Fac.getValor() * getListInfoImpri().get(i).getValDesFac()) / 100));
                    } else if ("F".equals(getListInfoImpri().get(i).getTipoDescuentoFactu()) && i == 0) {
                        setValorDescuentos(getValorDescuentos() + getListInfoImpri().get(i).getValDesFac());
                    }
                    //valores adiconales con iva   
                    if (Fac.ValoresAdicionalesAvaluo("iva", getListInfoImpri().get(i).getCodSeg()) > 0) {
                        setValorAdicion(getValorAdicion() + (int) Math.round((Fac.ValoresAdicionalesAvaluo("iva", getListInfoImpri().get(i).getCodSeg()) * getListInfoImpri().get(i).getPorcentajeParti()) / 100));
                    }
                    setSubtotal(Math.round(Fac.getValor() + getValorAdicion() + getValAdiFacIva() - getValorDescuentos() - getSumaFacAnt()));
                    if ((exento == 0 && cantidadLista == 1) || (cantidadLista > 1 && cantidadLista > exento)) {

                        if (exento > 0 && i == 0) {
                            mBTodero.setMens("Hay avalúos exenos de iva, para no aplicar los impuestos no facture el avalúo en grupo");
                            mBTodero.info();
                        }
                        setIva(getSubtotal() * getIvaBD() / 100);

                        if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {

                            if (mBAdministacion.getCargarFuente().size() == 1) {
                                ResultSet imp = FacTablas.consulImpu("RETEFUENTE");
                                if (imp.next()) {
                                    setFuente1(imp.getDouble("Tasa_Impuesto"));
                                }
                                setValorFuente((int) Math.round((getSubtotal() * getFuente1() / 100)));
                            }

                            if (Fac.getCalificacion().equalsIgnoreCase("GRANDES CONTRIBUYENTES")) {
                                if (mBAdministacion.getCargarReteIva().size() == 1) {
                                    ResultSet imp = FacTablas.consulImpu("RETEIVA");
                                    if (imp.next()) {
                                        setReteiva(imp.getDouble("Tasa_Impuesto"));
                                    }
                                    setValorReIva((int) Math.round((getIva() * getReteiva() / 100)));
                                }
                            }

                        }

                        if (mBAdministacion.getCargarICA().size() == 1) {
                            ResultSet imp = FacTablas.consulImpu("RETEICA");
                            if (imp.next()) {
                                setIca1(imp.getDouble("Tasa_Impuesto"));
                            }

                        }
                    } else if (i == 0) {
                        mBTodero.setMens("Factura exenta de iva");
                        mBTodero.info();
                    }

                    total = Math.round(subtotal + iva - valorIca - valorFuente - valorReIva);

                    if (Fac.ValoresAdicionalesAvaluo("sinIva", getListInfoImpri().get(i).getCodSeg()) > 0) {
                        setValorAdicionSinIva(getValorAdicionSinIva() + ((int) ((Fac.ValoresAdicionalesAvaluo("sinIva", getListInfoImpri().get(i).getCodSeg()) * getListInfoImpri().get(i).getPorcentajeParti()) / 100)));
                    }
                    setTotalpagarFactAnt(Math.round(getSubtotal() + getValorAdicionSinIva() + getValAdiFacSinIva() - getAnticipo5()
                            + getIva() - getValorIca() - getValorReIva() - getValorFuente()));
                }
                break;

            case "OTROS":

                for (int i = 0; i < cantidadLista; i++) {
                    if (getListInfoImpri().get(i).getCiudadText() != null && !getListInfoImpri().get(i).getCiudadText().equals("NO")) {
                        exento++;
                    }
                }

                FacTablas = new LogFacturacion();
                setValorAdicion(Fac.ValoresAdicionalesAvaluo("sinAvaluoIva", Fac.getCodFactu()));

                if ("P".equals(getListInfoImpri().get(0).getTipoDescuentoFactu())) {
                    setValorDescuentos((Fac.getValor() * getListInfoImpri().get(0).getValDesFac()) / 100);
                } else if ("F".equals(getListInfoImpri().get(0).getTipoDescuentoFactu())) {
                    setValorDescuentos(getListInfoImpri().get(0).getValDesFac());
                }

                setSubtotal(Fac.getValor() + getValorAdicion() - getValorDescuentos());
                setValorAdicionSinIva(Fac.ValoresAdicionalesAvaluo("sinAvaluoNoIva", Fac.getCodFactu()));
                if ((exento == 0 && cantidadLista == 1) || (cantidadLista > 1 && cantidadLista > exento)) {
                    setIva(getSubtotal() * getIvaBD() / 100);
                    if (Fac.getRegimen().equalsIgnoreCase("Persona Juridica")) {

                        if (mBAdministacion.getCargarFuente().size() == 1) {
                            ResultSet imp = FacTablas.consulImpu("RETEFUENTE");
                            if (imp.next()) {
                                setFuente1(imp.getDouble("Tasa_Impuesto"));
                            }
                            setValorFuente((int) Math.round((getSubtotal() * getFuente1() / 100)));
                        }

                        if (Fac.getCalificacion().equalsIgnoreCase("GRANDES CONTRIBUYENTES")) {
                            if (mBAdministacion.getCargarReteIva().size() == 1) {
                                ResultSet imp = FacTablas.consulImpu("RETEIVA");
                                if (imp.next()) {
                                    setReteiva(imp.getDouble("Tasa_Impuesto"));
                                }
                                setValorReIva((int) Math.round((getIva() * getReteiva() / 100)));
                            }
                        }

                    }

                    if (mBAdministacion.getCargarICA().size() == 1) {
                        ResultSet imp = FacTablas.consulImpu("RETEICA");
                        if (imp.next()) {
                            setIca1(imp.getDouble("Tasa_Impuesto"));
                        }

                    }
                } else if (exento > 0) {
                    mBTodero.setMens("Factura exenta de iva");
                    mBTodero.info();
                }
                total = Math.round(subtotal + iva - valorIca - valorFuente - valorReIva);
                setTotalpagarFactAnt((long) (getSubtotal() + getValorAdicionSinIva() + getValAdiFacSinIva() - getAnticipo5()
                        + getIva() - getValorIca() - getValorReIva() - getValorFuente()));
                break;

            case "EXCE":
                ValorPac = 0;
                setDescuento(0);
                FacTablas = new LogFacturacion();
                setValAdiFacIva(Fac.ValoresAdicionalesAvaluo("sinAvaluoIva", Fac.getCodFactu()));
                setValAdiFacSinIva(Fac.ValoresAdicionalesAvaluo("sinAvaluoNoIva", Fac.getCodFactu()));

                ResultSet valCom;
                valCom = Fac.GetInfoFacExc(Fac.getCodSeg(), "infoAnter", " and F.Cod_Factura<>" + Fac.getCodFactu() + " ");
                if (valCom.next()) {
                    setTipoFacturacion(valCom.getString("tab"));
                    setValorTotalTabla(valCom.getInt("valAvaAnt"));
                }

                valCom = Fac.GetInfoFacExc(Fac.getCodSeg(), "distancia", "");
                if (valCom.next()) {
                    setDistancia(valCom.getInt("Distancia"));
                }
                ValorPac = (int) (Fac.getValor() - getValorTotalTabla());

                valCom = Fac.GetInfoFacExc(Fac.getCodSeg(), "descuento", "");
                if (valCom.next()) {
                    setSimbolo((valCom.getString("Tipo_Descuento").equalsIgnoreCase("F")) ? "$" : "%");
                    setValorDescuentos(valCom.getInt("Descuento"));
                    if (getSimbolo().equalsIgnoreCase("%")) {
                        int aux = (int) ((ValorPac * getValorDescuentos()) / 100);
                        setDescuento(aux);
                    }
                }

                if ("P".equals(getListInfoImpri().get(0).getTipoDescuentoFactu())) {
                    setDescuento(getDescuento() + (ValorPac * getListInfoImpri().get(0).getValDesFac()) / 100);
                } else if ("F".equals(getListInfoImpri().get(0).getTipoDescuentoFactu())) {
                    setDescuento(getListInfoImpri().get(0).getValDesFac());
                }
                setFormName(getListInfoImpri().get(0).getDescuentoFactura());

                setSubtotal(ValorPac - descuento + valAdiFacIva);
                total = Math.round(subtotal + iva - valorIca - valorFuente - valorReIva);
                listaImpuesto = Fac.ImpuAplyAva(Fac.getCodSeg());

                setTotalpagarFactAnt((long) (getSubtotal() + getIva() - getValorReIva() - getValorIca() - getValorFuente() + valAdiFacSinIva));

                break;

        }
    }

    public List<LogFacturacion> getListAvalAscdos() {
        return listAvalAscdos;
    }

    public void setListAvalAscdos(List<LogFacturacion> listAvalAscdos) {
        this.listAvalAscdos = listAvalAscdos;
    }

    //Metodo que muestra los avaluos asociadps a la factura
    public void AvaluosAsoc() {
        try {
            if (getListPendientXImpri().size() > 0 && Fac != null) {
                if (getListPendientXImpri().get(0).getCodFactu() != 0) {
                    setFactLiberar(Fac.getCodFactu());
                    if (getFactLiberar() != 0) {
                        //Consulta los valores asociados  de la factura seleccionada.
                        setListAvalAscdos(Fac.FactAsociadas(getFactLiberar()));
                        if (getListAvalAscdos().size() > 0) {

                            if (getListAvalAscdos().get(0).getNumAvaluo() != 0) {
                                RequestContext.getCurrentInstance().execute("PF('LiberarAval').show()");
                            } else {
                                mBTodero.setMens("No tiene avaluos Asociados, Por favor seleccione otra Factura");
                                mBTodero.info();
                            }
                        }
                    } else {
                        mBTodero.setMens("Debe seleccionar el Numero de factura");
                        mBTodero.info();
                    }

                }
            } else {
                mBTodero.setMens("Seleccione un registro");
                mBTodero.info();
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en Liberar Avaluo" + e);
            mBTodero.info();
        }

    }

    public void ProcesoLiberarAvaluo(int opc) {
        try {

            switch (opc) {
                // Crear un mensaje en Cuerpo SIAI, para validar la eliminacion
                case 1:
                    if (getSelectListaAvaltAnt() != null && getSelectListaAvaltAnt().size() > 0) {
                        RequestContext.getCurrentInstance().execute("PF('LiberarAval').hide()");
                        RequestContext.getCurrentInstance().execute("PF('MsgAlerta').show()");
                    } else {
                        mBTodero.setMens("Seleccione un avalúo a liberar");
                        mBTodero.info();
                    }

                    break;

                case 2:
                    //Mostrar un mensaje el Numero de avaluo que se va ha liberar
                    if (getSelectListaAvaltAnt().size() > 0) {
                        for (int i = 0; i < getSelectListaAvaltAnt().size(); i++) {

                            //llamar al procedimiento almacendo de sp_liberarAvaluo
                            Fac.LiberarAvaluo(getSelectListaAvaltAnt().get(i).getCodSeg(), getListPendientXImpri().get(i).getCodFactu(),
                                    SelectListaAvaltAnt.get(0).getCodSegFac(), mBsesion.codigoMiSesion());
                            mBTodero.setMens("Avaluo Liberado Correcramente, Verifique en el Modulo pendientes por Facturar");
                            mBTodero.info();
                            RequestContext.getCurrentInstance().execute("PF('MsgAlerta').hide()");
                            setListPendientXImpri(new ArrayList<>());
                            setListPendientXImpri(Fac.ConsultPendientXImpri("general"));
                        }

                    }

                    break;

                case 3:
                    RequestContext.getCurrentInstance().execute("PF('MsgAlerta').hide()");

                    break;
                case 4:
                    detalleFactura = "";
                    codFacOb = Fac.getCodFactu();
                    listaDetallesFac = Fac.Observaciones(Fac.getCodFactu());
                    RequestContext.getCurrentInstance().execute("PF('ObFac').show()");
                    break;
            }
        } catch (SQLException e) {
            mBTodero.setMens("Error en la liberacion del Avaluo, Metodo ProcesoLiberarAvaluo");
            mBTodero.info();
        }
    }

    public void onAbrirDialogAgregarObserv(String tipo) throws SQLException {
        try {

            ClearValAdicional();
            //Validar que haya seleccionado el Numero de Factura 
            if (getListPendientXImpri().size() > 0 && Fac != null) {
                if (Fac.getCodFactu() != 0) {
                    switch (tipo) {
                        case "valAdic":

                            //Consulta los valores asociados  de la factura seleccionada.
                            setListConsulValorAddFactura(Fac.ConslValFact("valAdic", Fac.getCodFactu()));
                            AdmTablasFactTarifas = new LogFacturacion();
                            RequestContext.getCurrentInstance().execute("PF('DlgAddObserRadAnti').show()");
                            break;

                        case "Descuento":
                            setListConsulValorAddFactura(Fac.ConslValFact("descuento", Fac.getCodFactu()));
                            RequestContext.getCurrentInstance().execute("PF('DlgDescuento').show()");
                            break;
                    }
                } else {
                    mBTodero.setMens("Debe seleccionar el Numero de factura");
                    mBTodero.info();
                }
            } else {
                mBTodero.setMens("Debe seleccionar el Numero de factura");
                mBTodero.info();
            }
        } catch (NumberFormatException e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".DlgAddObserRadAnti()' causado por: " + e.getMessage());
            mBTodero.error();
        }

    }

    public void InsertarValorAd(String tipo) throws SQLException {
        LogFacturacion fac1 = new LogFacturacion();
        switch (tipo) {
            case "valAdic":
                Rst = 0;
                Apply_Iva = Fac.impuestoFactAntcpo().get(0).getCodImpuesto();
                for (int i = 0; i < ListTablasFacturacTarifas.size(); i++) {

                    Rst = fac1.InsertValorAdicion(5, Fac.getCodFactu(), (ListTablasFacturacTarifas.get(i).getEstadoAnti().equals("Si")) ? "true" : "false",
                            ListTablasFacturacTarifas.get(i).getValorAdic(), ListTablasFacturacTarifas.get(i).getCodValAdi(), ListTablasFacturacTarifas.get(i).getVisible(), Apply_Iva);
                }

                if (Rst != 0) {
                    mBTodero.setMens("Registro Satisfactorio");
                    mBTodero.info();
                } else {
                    mBTodero.setMens("Problema al insertar valor adicional");
                    mBTodero.info();
                }

                ClearValAdicional();
                RequestContext.getCurrentInstance().execute("PF('DlgAddObserRadAnti').hide()");

                break;

            case "descuento":

                double porcentDes;

                if ((descuento <= 0 || descuento > 100) && radioUno.equals("P")) {
                    mBTodero.setMens("El descuento debe ser mayor a cero % y menor al 100%");
                    mBTodero.info();
                } else if (ob.isEmpty()) {
                    mBTodero.setMens("Ingrese la observación del descuento");
                    mBTodero.info();
                } else if (radioUno.isEmpty()) {
                    mBTodero.setMens("Seleccione el tipo de descuento");
                    mBTodero.info();
                } else {

                    if (radioUno.equals("F")) {
                        porcentDes = descuento * 100 / Fac.getValor();

                    } else {
                        porcentDes = descuento;
                    }

                    int validarPorce = 0;
                    if (Fac.getTipoFactura().equals("NORMAL") && Fac.getNumFactura() == 1) {
                        LogFacturacion fac2 = new LogFacturacion();

                        for (int i = 0; i < fac2.Descuentos(Fac.getCodFactu()).size(); i++) {
                            validarPorce = validarPorce + fac2.Descuentos(Fac.getCodFactu()).get(i).getValorAnticipoEnt();
                        }

                    } else if (Fac.getTipoFactura().equals("EXCEDENTE") && Fac.getNumFactura() == 1) {

                        LogFacturacion fac2 = new LogFacturacion();

                        ResultSet valCom, numSeg;

                        numSeg = Fac.GetInfoFacExc(Fac.getCodFactu(), "numSeg", "");
                        if (numSeg.next()) {
                            valCom = Fac.GetInfoFacExc(numSeg.getInt("FK_Cod_Seguimiento"), "descuento", "");
                            if (valCom.next()) {
                                if (valCom.getString("Tipo_Descuento").equalsIgnoreCase("P")) {
                                    validarPorce = valCom.getInt("Descuento");
                                }
                            }

                        }
                    }

                    if ((validarPorce + porcentDes) > 100) {
                        mBTodero.setMens("Actualmente los avalúos cuentan con descuento de " + Math.round(validarPorce) + "%."
                                + " El descuento que esta tratando de ingresar equivale al " + porcentDes + " %. Recuerde que el descuento no puede ser mayor al 100%");
                        mBTodero.info();
                    } else {
                        fac1.insertarDescuento(radioUno, descuento, Fac.getCodFactu());
                        RequestContext.getCurrentInstance().execute("PF('DlgDescuento').hide()");
                        descuento = 0;
                        ob = "";
                        radioUno = "";
                    }
                }

                break;

            case "Deletedescuento":

                fac1.EliminarValAdic("", ListConsulValorAddFactura.get(0).getCodFactuAnti(), 3);
                RequestContext.getCurrentInstance().execute("PF('DlgDescuento').hide()");
                mBTodero.setMens("Descuento eliminado satisfactoriamente");
                mBTodero.info();
                break;
        }
    }

    String xml = "";

    /**/
    /**
     *
     * @throws java.sql.SQLException
     * @throws java.io.IOException
     */
    public void pdfImpresionFactura() throws SQLException, IOException {

        xml = "";
        String telefono;
        String ciudad;
        String direccion;
        setListaInfoFactura(Factu.ConsultPendientXImpri("  F.Cod_Factura=" + Fac.getCodFactu() + "  group by s.Cod_Seguimiento"));

        if ((Fac.getTipoFactura().equalsIgnoreCase("NORMAL") || Fac.getTipoFactura().equalsIgnoreCase("EXCEDENTE"))) {
            telefono = Factu.VistasInformacionFactu((Fac.getNit().isEmpty()) ? "infoclientefac" : "infoentfac", "Telefono", "cod_per=" + Fac.getCodClie(), listaInfoFactura.get(0).getCodAva());
            ciudad = Factu.VistasInformacionFactu((Fac.getNit().isEmpty()) ? "infoclientefac" : "infoentfac", "ciudad", "cod_per=" + Fac.getCodClie(), listaInfoFactura.get(0).getCodAva());
            direccion = Factu.VistasInformacionFactu((Fac.getNit().isEmpty()) ? "infoclientefac" : "infoentfac", "direccion", "cod_per=" + Fac.getCodClie(), listaInfoFactura.get(0).getCodAva());
        } else {
            telefono = Factu.VistasInformacionFactu("infFacturas", "Telefono", "", Fac.getCodFactu());
            ciudad = Factu.VistasInformacionFactu("infFacturas", "ciuXML", "", Fac.getCodFactu());
            direccion = Factu.VistasInformacionFactu("infFacturas", "direccion", "", Fac.getCodFactu());
        }

        double valor = 0.0;

        int vrAdi = 0;
        double descuento1 = 0.0;
        double subtotal_factura = 0.0;
        int anticipos = 0;
        int total1 = (int) (getSubtotal() + Math.round(getIva()) - getValorFuente() - Math.round(getValorReIva()) - getValorIca());

        xml = xml + "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>" + "\n";
        xml = xml + "<!--Sample XML file generated by XMLSpy v2010 (http://www.altova.com)-->" + "\n";
        xml = xml + "<Factura_Servicio xsi:noNamespaceSchemaLocation=\"C:\\Elemental\\ISAInmobiliaria\\xml\\Schema\\Factura_Servicio.xsd\"  xmlns:pd=\"http://skylab/paradocs/sys/paradocs.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n";
        xml = xml + "	<pd:DocInfo>" + "\n";
        xml = xml + "		<pd:DOCID></pd:DOCID>" + "\n";
        //   if (!Fac.getTipoFactura().contains("NORMAL")) {
        xml = xml + "		<pd:DocSysState>IsaAvaluo.fo.xslt</pd:DocSysState>" + "\n";
        xml = xml + "		<pd:DocLastView>IsaAvaluo.fo.xslt</pd:DocLastView>" + "\n";
        /*     } else {
            xml = xml + "		<pd:DocFamily>Avaluo Nuevo</pd:DocFamily>" + "\n";
            xml = xml + "             <pd:DocSysState>NORMAL</pd:DocSysState>" + "\n";
            xml = xml + "             <pd:DocPrintingView>IsaAvaluoNuevo.fo.xslt</pd:DocPrintingView>" + "\n";
            xml = xml + "             <pd:DocLastView>IsaAvaluoNuevo.fo.xslt</pd:DocLastView>" + "\n";
        }*/

        xml = xml + "	</pd:DocInfo>" + "\n";
        xml = xml + "	<Encabezado>" + "\n";
        xml = xml + "		<Emite>" + "\n";
        xml = xml + "			<Codigo_Tercero>0</Codigo_Tercero>" + "\n";
        xml = xml + "			<Nombre_Tercero/>" + "\n";
        xml = xml + "			<Telefono/>" + "\n";
        xml = xml + "			<Fax/>" + "\n";
        xml = xml + "			<email/>" + "\n";
        xml = xml + "			<Direccion/>" + "\n";
        xml = xml + "			<Ciudad/>" + "\n";
        xml = xml + "			<Pais/>" + "\n";
        xml = xml + "			<Identificacion/>" + "\n";
        xml = xml + "			<gln/>" + "\n";
        xml = xml + "		</Emite>" + "\n";
        xml = xml + "		<Recibe>" + "\n";
        xml = xml + "			<Codigo_Tercero>" + (Fac.getDocClie() + Fac.getNit()) + "</Codigo_Tercero>" + "\n";
        xml = xml + "			<Nombre_Tercero>" + (Fac.getCliente() + Fac.getNombre_Entidad()).replace("&", "?") + "</Nombre_Tercero>" + "\n";
        xml = xml + "			<Telefono>" + telefono + "</Telefono>" + "\n";
        xml = xml + "			<Fax/>" + "\n";
        xml = xml + "			<email/>" + "\n";
        xml = xml + "			<Direccion>" + direccion + "</Direccion>" + "\n";
        xml = xml + "			<Ciudad>" + ciudad + "</Ciudad>" + "\n";
        xml = xml + "			<Pais/>" + "\n";
        xml = xml + "			<Identificacion/>" + "\n";
        xml = xml + "		</Recibe>" + "\n";
        xml = xml + "		<Fecha>" + getListaInfoFactura().get(0).getFechaFactura().substring(0, 10) + "</Fecha>" + "\n";
        xml = xml + "		<Numero>" + Fac.getCodFactu() + "</Numero>" + "\n";
        xml = xml + "		<Vencimiento>" + getListaInfoFactura().get(0).getFechaVencimiento().substring(0, 10) + "</Vencimiento>" + "\n";
        xml = xml + "		<Comentario/>" + "\n";
        xml = xml + "		<Prefijo>AV</Prefijo>" + "\n";
        xml = xml + "		<Logo>logo2.jpg</Logo>" + "\n";
        xml = xml + "<DetalleObservacion>RECUERDE QUE TAMBIEN PUEDE PAGAR SU FACTURA CON TARJETA DEBITO O CREDITO EN NUESTRA OFICINA EN BOGOTA O EN EL PORTAL DE PAGOS ELECTRONICOS DEL BANCO DE BOGOTA.</DetalleObservacion>" + "\n";
        xml = xml + "	</Encabezado>" + "\n";
        xml = xml + "	<Detalles>" + "\n";

        if (Fac.getTipoFactura().equals("NORMAL") || Fac.getTipoFactura().equals("EXCEDENTE") || Fac.getTipoFactura().equals("COPIA")) {

            if (Fac.getNumFactura() <= 5) {
                for (int i = 0; i < getListaInfoFactura().size(); i++) {

                    xml = xml + "		<Detalle>" + "\n";
                    xml = xml + "			<Servicio>" + "\n";
                    xml = xml + "				<Codigo_Servicio/>" + "\n";
                    xml = xml + "				<Nombre_Servicio/>" + "\n";
                    xml = xml + "			</Servicio>" + "\n";
                    xml = xml + "			<Descripcion> " + getListaInfoFactura().get(i).getTextSolicitante() + "</Descripcion>" + "\n";
                    xml = xml + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                    xml = xml + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                    if (!Fac.getTipoFactura().equals("EXEDENTE")) {
                        if (Fac.getTipoFactura().equals("COPIA")) {
                            xml = xml + "			<Valor>0</Valor>" + "\n";
                        } else {
                            xml = xml + "			<Valor>" + (int) getListaInfoFactura().get(i).getValor() + "</Valor>" + "\n";
                            valor = valor + vrAdi;
                        }
                    }
                    xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                    xml = xml + "			<IVA>0</IVA>" + "\n";
                    xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                    xml = xml + "		</Detalle>" + "\n";
                }
                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>(" + Factu.NombrePeritoImpreFac("", Fac.getCodFactu()) + ")</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>0</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";

                if (!getListaInfoFactura().get(0).getSolicitante().isEmpty()) {
                    xml = xml + "		<Detalle>" + "\n";
                    xml = xml + "			<Servicio>" + "\n";
                    xml = xml + "				<Codigo_Servicio/>" + "\n";
                    xml = xml + "				<Nombre_Servicio/>" + "\n";
                    xml = xml + "			</Servicio>" + "\n";
                    xml = xml + "			<Descripcion> " + getListaInfoFactura().get(0).getSolicitante() + " </Descripcion>" + "\n";
                    xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                    xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                    xml = xml + "			<Valor>0</Valor>" + "\n";
                    xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                    xml = xml + "			<IVA>0</IVA>" + "\n";
                    xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                    xml = xml + "		</Detalle>" + "\n";
                }

            } else {

                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>" + "HONORARIOS POR LA REALIZACION DE " + Fac.getNumAvaluo() + " AVALUOS" + "</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>" + valor + "</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }
            setListaDesc(Fac.Descuentos(Fac.getCodFactu()));
            for (int i = 0; i < getListaDesc().size(); i++) {
                if (getListaDesc() != null && !getListaDesc().isEmpty()) {
                    xml = xml + "		<Detalle>" + "\n";
                    xml = xml + "			<Servicio>" + "\n";
                    xml = xml + "				<Codigo_Servicio/>" + "\n";
                    xml = xml + "				<Nombre_Servicio/>" + "\n";
                    xml = xml + "			</Servicio>" + "\n";
                    xml = xml + "			<Descripcion>Descuento especial " + getListaDesc().get(i).getSimbolo() + "</Descripcion>" + "\n";
                    xml = xml + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                    xml = xml + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                    if (Fac.getNumAvaluo() < 5) {
                        xml = xml + "			<Valor>" + getListaDesc().get(i).getValorRealDescuento() + "</Valor>" + "\n";
                    }
                    xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                    xml = xml + "			<IVA>0</IVA>" + "\n";
                    xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                    xml = xml + "		</Detalle>" + "\n";
                }

            }

            setListaValoAdi(Fac.ValoresAdicionales("factura", String.valueOf(Fac.getCodFactu())));
            listaValoAdi.addAll(Fac.ValoresAdicionales("avaluo", String.valueOf(Fac.getCodFactu())));
            for (int i = 0; i < getListaValoAdi().size(); i++) {

                if (getListaValoAdi() != null && !getListaValoAdi().isEmpty()) {
                    xml = xml + "		<Detalle>" + "\n";
                    xml = xml + "			<Servicio>" + "\n";
                    xml = xml + "				<Codigo_Servicio/>" + "\n";
                    xml = xml + "				<Nombre_Servicio/>" + "\n";
                    xml = xml + "			</Servicio>" + "\n";
                    xml = xml + "			<Descripcion>" + getListaValoAdi().get(i).getObserDescuento5() + "</Descripcion>" + "\n";
                    xml = xml + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                    xml = xml + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                    if (getListaValoAdi().get(i).getVisible().equals("DV")) {
                        xml = xml + "			<Valor>" + getListaValoAdi().get(i).getValorAdic() + "</Valor>" + "\n";
                    }
                    xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                    xml = xml + "			<IVA>0</IVA>" + "\n";
                    xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                    xml = xml + "		</Detalle>" + "\n";
                }
            }
            //Anticipos
            if (getAnticipo5() > 0 && !Fac.getTipoFactura().equals("EXCEDENTE")) {
                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>( Fecha Anticipo: " + getFechaConsignacion() + " )</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>0</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }

            if (Fac.getTipoFactura().equals("EXCEDENTE")) {

                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>" + "EXCEDENTE" + "</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>" + (int) subtotal_factura + "</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }

            if (Fac.getTipoFactura().equals("COPIA")) {

                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>(COPIA)</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>" + (int) (Fac.getValor()) + "</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }

            if ((total1 - getAnticipo5()) < 0) {
                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>Mayor valor cancelado de " + (total1 - getAnticipo5()) + "</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>0</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }

        }

        if (Fac.getTipoFactura().equals("ANTICIPO")) {

            xml = xml + "		<Detalle>" + "\n";
            xml = xml + "			<Servicio>" + "\n";
            xml = xml + "				<Codigo_Servicio/>" + "\n";
            xml = xml + "				<Nombre_Servicio/>" + "\n";
            xml = xml + "			</Servicio>" + "\n";
            xml = xml + "			<Descripcion>Factura Anticipo</Descripcion>" + "\n";
            xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
            xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
            xml = xml + "			<Valor>" + (int) (Fac.getValor()) + "</Valor>" + "\n";
            xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
            xml = xml + "			<IVA>0</IVA>" + "\n";
            xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
            xml = xml + "		</Detalle>" + "\n";
        }
        if (Fac.getTipoFactura().equals("CONCEPTO")) {

            xml = xml + "		<Detalle>" + "\n";
            xml = xml + "			<Servicio>" + "\n";
            xml = xml + "				<Codigo_Servicio/>" + "\n";
            xml = xml + "				<Nombre_Servicio/>" + "\n";
            xml = xml + "			</Servicio>" + "\n";
            xml = xml + "			<Descripcion>" + getObservacionCambio() + "</Descripcion>" + "\n";
            xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
            xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
            xml = xml + "			<Valor>" + (int) (getSubtotal()) + "</Valor>" + "\n";
            xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
            xml = xml + "			<IVA>0</IVA>" + "\n";
            xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
            xml = xml + "		</Detalle>" + "\n";
        }
        if (getListaDetallesFac() != null) {
            for (int i = 0; i < getListaDetallesFac().size(); i++) {
                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>" + getListaDetallesFac().get(i).getDetalle() + "</Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }
        }
        if (getSelectlistaFactAnticipos() != null && !getSelectlistaFactAnticipos().isEmpty()) {
            for (int i = 0; i < getSelectlistaFactAnticipos().size(); i++) {
                anticipos = anticipos + getSelectlistaFactAnticipos().get(i).getValorFacturaAnticipo();
                xml = xml + "		<Detalle>" + "\n";
                xml = xml + "			<Servicio>" + "\n";
                xml = xml + "				<Codigo_Servicio/>" + "\n";
                xml = xml + "				<Nombre_Servicio/>" + "\n";
                xml = xml + "			</Servicio>" + "\n";
                xml = xml + "			<Descripcion>Factura anticipo asociada número " + getSelectlistaFactAnticipos().get(i).getCodFactuAnti() + " </Descripcion>" + "\n";
                xml = xml + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                xml = xml + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                xml = xml + "			<Valor>" + getSelectlistaFactAnticipos().get(i).getValorFacturaAnticipo() + "</Valor>" + "\n";
                xml = xml + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                xml = xml + "			<IVA>0</IVA>" + "\n";
                xml = xml + "			<Valor_Total>0</Valor_Total>" + "\n";
                xml = xml + "		</Detalle>" + "\n";
            }
        }
        xml = xml + "	</Detalles>	" + "\n";

        xml = xml + "	<Mensajes>" + "\n";
        xml = xml + "		<Resolucion>" + Factu.VistasInformacionFactu("Resolucion", "Resultado_Parametro", "", 0) + "</Resolucion>" + "\n";
        xml = xml + "		<Mensaje1>" + Factu.VistasInformacionFactu("Mensaje", "Resultado_Parametro", "Mensaje1", 0) + "</Mensaje1>" + "\n";
        xml = xml + "		<Mensaje2>" + Factu.VistasInformacionFactu("Mensaje", "Resultado_Parametro", "Mensaje2", 0) + "</Mensaje2>" + "\n";
        xml = xml + "		<Mensaje3>" + Factu.VistasInformacionFactu("Mensaje", "Resultado_Parametro", "Mensaje3", 0) + "</Mensaje3>" + "\n";
        xml = xml + "		<Mensaje4>" + Factu.VistasInformacionFactu("Mensaje", "Resultado_Parametro", "Mensaje4", 0) + "</Mensaje4>" + "\n";
        xml = xml + "	</Mensajes>" + "\n";

        xml = xml + "	<Bancos>" + "\n";
        xml = xml + "		<Banco>" + "\n";
        xml = xml + "			<Nombre>BANCO DE BOGOTA BANCO POPULAR BANCO OCCIDENTE </Nombre>" + "\n";
        xml = xml + "			<cuenta>Cta.Cte.Nro.040-21363-9  Cta.Cte.Nro.062-17319-0 Cta.Cte.Nro.230-05957-8</cuenta>" + "\n";
        xml = xml + "		</Banco>" + "\n";

        xml = xml + "	</Bancos>" + "\n";
        NumerosALetras numero;

        numero = new NumerosALetras((int) getTotalpagarFactAnt());
        String letras = numero.convertirLetras((int) getTotalpagarFactAnt());

        xml = xml + "	<Totales>" + "\n";
        xml = xml + "		<Subtotal>" + (int) (getSubtotal()) + "</Subtotal>" + "\n";
        xml = xml + "		<Total_IVA>" + Math.round(getIva()) + "</Total_IVA>" + "\n";
        xml = xml + "		<Total_Neto>" + (int) getSubtotal() + "</Total_Neto>" + "\n";
        xml = xml + "		<Retencion_Renta>" + getValorFuente() + "</Retencion_Renta>" + "\n";
        xml = xml + "		<Retencion_IVA>" + Math.round(getValorReIva()) + "</Retencion_IVA>" + "\n";
        xml = xml + "		<Retencion_ICA>" + getValorIca() + "</Retencion_ICA>" + "\n";
        xml = xml + "		<Total_Pagar>" + total1 + "</Total_Pagar>" + "\n";
        xml = xml + "		<Total_Letras>" + letras.trim().toUpperCase() + " PESOS M/CTE.</Total_Letras>" + "\n";
        xml = xml + "		<Descuentos>" + (int) descuento1 + "</Descuentos>" + "\n";
        xml = xml + "		<Anticipo>" + ((!Fac.getTipoFactura().equals("EXCEDENTE")) ? (getAnticipo5()) : 0) + "</Anticipo>" + "\n";
        xml = xml + "		<Saldo>" + (int) getTotalpagarFactAnt() + "</Saldo>" + "\n";
        xml = xml + "	</Totales>" + "\n";
        xml = xml + "</Factura_Servicio>" + "\n";

        /* File f;
        String ruta = File.separator + mBArchivos.getBaseArc() + File.separator + "XML" + File.separator + Fac.getCodFactu() + File.separator;
        mBArchivos.Nuevopath(ruta);

        f = new File(ruta + Fac.getCodFactu() + ".xml");
        try {
            FileWriter w = new FileWriter(f);
            try (BufferedWriter bw = new BufferedWriter(w); PrintWriter wr = new PrintWriter(bw)) {
                wr.write(xml);
            }
            mBArchivos.DescarArcIndi(ruta, String.valueOf(Fac.getCodFactu()), "xml");
        } catch (IOException e) {
            System.err.println("ERROR :: METODO " + e.getClass() + " :: " + e.getMessage());
        }*/
        String ruta = File.separator + mBArchivos.getBaseArc() + File.separator + "XML" + File.separator + Fac.getCodFactu() + ".xml" + File.separator;
        //String ruta = "/home/mario/archivo.txt";
        File archivo = new File(ruta);
        BufferedWriter bw;
        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(xml);
        bw.close();

    }

    public void asociarFactuAnti() throws SQLException {

        if (getSelectlistaFactAnticipos() != null && getSelectlistaFactAnticipos().size() > 0) {
            setSumaFacAnt(0);
            for (int i = 0; i < getSelectlistaFactAnticipos().size(); i++) {
                setSumaFacAnt(getSumaFacAnt() + getSelectlistaFactAnticipos().get(i).getValorFacturaAnticipo());
            }
            getSumaFacAnt();
            AplicarImpuestos("Aplicar");

            RequestContext.getCurrentInstance().execute("PF('anti').hide()");
            //Fac.CambiarEstadoFactuAnti(1, 4);
        } else {
            mBTodero.setMens("No ha seleccionado ningun anticipo a asociar");
            mBTodero.warn();

        }
    }

    //Cargar combobox de Facturas seleccionadas
    public ArrayList<SelectItem> CargarListaAvaluos() {
        try {
            Iterator<LogFacturacion> Ite;
            Ite = getSelectListPendientXFact().iterator();
            while (Ite.hasNext()) {
                LogFacturacion Iterador = Ite.next();
                this.cargarNumAvalu.add(new SelectItem(Iterador.getCodAva(), String.valueOf(Iterador.getCodAva())));
            }
        } catch (Exception e) {
            mBTodero.setMens("Error en el metodo '" + this.getClass() + ".CargarListaAvaluos causado por: " + e.getMessage());
            mBTodero.error();
        }
        return this.cargarNumAvalu;

    }

    public void agregarGrupos(String tipo) throws SQLException {

        validar = 0;

        switch (tipo) {

            case "detalle":

                if (getListaDetallesFac().size() >= 3) {
                    mBTodero.setMens("No se peuden ingresar más de tres detalles a la factura");
                    mBTodero.warn();
                } else if (getDetalleFactura().isEmpty()) {
                    mBTodero.setMens("Ingrese una observación");
                    mBTodero.warn();
                } else {

                    for (int i = 0; i < listaDetallesFac.size(); i++) {
                        if (listaDetallesFac.get(i).getDetalle().equalsIgnoreCase(getDetalleFactura())) {
                            validar++;
                        }
                    }

                    if (validar == 0) {
                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setDetalle(getDetalleFactura());
                        Factura.setContador(listaDetallesFac.size() + 1);
                        listaDetallesFac.add(Factura);
                    } else {
                        mBTodero.setMens("Ya ingreso la misma observación con anterioridad");
                        mBTodero.warn();
                    }
                }

                break;

            case "observaciones":

                if (getDetalleFactura().isEmpty()) {
                    mBTodero.setMens("Ingrese una observación");
                    mBTodero.warn();
                } else {
                    for (int i = 0; i < listaDetallesFac.size(); i++) {
                        if (listaDetallesFac.get(i).getObserDescuento().equalsIgnoreCase(getDetalleFactura())) {
                            validar++;
                        }
                    }

                    if (validar == 0) {
                        Fac.insertarDetalle("", getDetalleFactura(), codFacOb, String.valueOf(mBsesion.codigoMiSesion()), "", 0);
                        listaDetallesFac = Fac.Observaciones(codFacOb);
                    } else {
                        mBTodero.setMens("Ya ingreso la misma observación con anterioridad");
                        mBTodero.warn();
                    }
                }

                break;

            case "distancia":

                if (getDistancia() == 0) {
                    mBTodero.setMens("Debe ingresar una distancia mayor a 0 ");
                    mBTodero.warn();
                } else if (getSelectNAva() == 0) {
                    mBTodero.setMens("Debe seleccionar un numero de avaluo");
                    mBTodero.warn();
                } else {

                    for (int i = 0; i < ListTablaDistancias.size(); i++) {
                        if (getListTablaDistancias().get(i).getNumAvaluo() == getSelectNAva()) {
                            validar++;
                        }
                    }

                    if (validar == 0) {

                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablaDistancias.size() + 1);
                        Factura.setCodSegFac(Fac.validarNAval(getSelectNAva()));
                        Factura.setNumAvaluo(getSelectNAva());
                        Factura.setDistancia(getDistancia());
                        ListTablaDistancias.add(Factura);

                    } else {
                        mBTodero.setMens("Solo peude agregar un valor de distancia por avaluo");
                        mBTodero.warn();
                    }
                }

                break;

            case "valorAdicFac":

                if (SelValAdic == 0 || ValorAdicion == 0) {
                    mBTodero.setMens("Ingrese los campos obligatorios");
                    mBTodero.warn();
                } else {
                    FacTablas = new LogFacturacion();
                    Nombre = FacTablas.NameValoradc(String.valueOf(getSelValAdic()), "Fact_ValorAd");

                    for (int i = 0; i < ListTablasFacturacTarifas.size(); i++) {
                        if (Nombre.equals(ListTablasFacturacTarifas.get(i).getNombreTabla())) {
                            validar++;
                        }
                    }

                    for (int i = 0; i < getListConsulValorAddFactura().size(); i++) {
                        if (Nombre.equals(getListConsulValorAddFactura().get(i).getNombreTabla())) {
                            validar++;
                        }
                    }

                    if (validar == 0) {
                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablasFacturacTarifas.size() + 1);
                        Factura.setNombreTabla(Nombre);
                        Factura.setCodImpuesto((int) getSelValAdic());
                        Factura.setValorAdic(getValorAdicion());
                        Factura.setEstadoAnti((OpcValAdc) ? "Si" : "No");
                        Factura.setVisible(getVisible1());
                        Factura.setCodValAdi(getSelValAdic());
                        Factura.setCodSegFac(Fac.validarNAval(getSelValAdic()));
                        ListTablasFacturacTarifas.add(Factura);
                    } else {
                        mBTodero.setMens("Ya ingreso un valor adicional del mismo tipo");
                        mBTodero.warn();
                    }
                }

                break;

            case "descuentos":

                if (getDescuento() == 0) {
                    mBTodero.setMens("Debe ingresar una descuento mayor a 0 ");
                    mBTodero.warn();
                } else if (getSelectNAvaDes() == 0) {
                    mBTodero.setMens("Debe seleccionar un numero de avalúo");
                    mBTodero.warn();
                } else if (getRadioUno().isEmpty() || getRadioUno().equalsIgnoreCase("")) {
                    mBTodero.setMens("Seleccione el tipo de descuento: Fijo o Porcentaje");
                    mBTodero.warn();
                } else if (getDescuento() > 100 && getRadioUno().equalsIgnoreCase("P")) {
                    mBTodero.setMens("El descuento por porcentaje no peude ser mayor al 100%");
                    mBTodero.warn();
                } else {

                    for (int i = 0; i < ListTablaDescuentos.size(); i++) {
                        if (getListTablaDescuentos().get(i).getNumAvaluo() == getSelectNAvaDes()) {
                            validar++;
                        }
                    }

                    if (validar == 0) {
                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablaDescuentos.size() + 1);
                        Factura.setNumAvaluo(getSelectNAvaDes());
                        Factura.setCodSegFac(Fac.validarNAval(getSelectNAvaDes()));
                        Factura.setDescuentos(getDescuento());
                        Factura.setTipoValor((getRadioUno().equalsIgnoreCase("F")) ? "FIJO" : "%");
                        ListTablaDescuentos.add(Factura);

                    } else {
                        mBTodero.setMens("Solo puede agregar un valor de descuento por avalúo");
                        mBTodero.warn();
                    }

                }
                break;

            case "ValAdi":

                if (getValorAdic() == 0) {
                    mBTodero.setMens("Debe ingresar una valor mayor a 0 ");
                    mBTodero.warn();
                } else if (getSelectNAvaVaAdi() == 0) {
                    mBTodero.setMens("Debe seleccionar un numero de avalúo");
                    mBTodero.warn();
                } else if (getCodParametro() == 0) {
                    mBTodero.setMens("Debe seleccionar una tipificación de avalúo");
                    mBTodero.warn();
                } else {

                    for (int i = 0; i < ListTablaValAdici.size(); i++) {
                        if (getListTablaValAdici().get(i).getNumAvaluo() == getSelectNAvaVaAdi() && getListTablaValAdici().get(i).getCodTabla() == getCodParametro()) {
                            mBTodero.setMens("Solo puede agregar un valor adicional con el mismo tipo de concepto");
                            mBTodero.warn();
                            validar++;
                        }
                    }

                    if (validar == 0) {
                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablaValAdici.size() + 1);
                        Factura.setNumAvaluo(getSelectNAvaVaAdi());
                        Factura.setValorAdic(getValorAdic());
                        Factura.setCodSegFac(Fac.validarNAval(getSelectNAvaVaAdi()));
                        Factura.setApplyIva((isOpcIva1()) ? "Si" : "No");
                        Factura.setCodTabla(getCodParametro());
                        ListTablaValAdici.add(Factura);
                    }

                }

                break;

            case "addValUnoAUno":

                if (getSelecAvaLiq() == 0) {
                    mBTodero.setMens("Debe seleccionar un numero de avalúo");
                    mBTodero.warn();
                } else {

                    for (int i = 0; i < ListTablaPacValor.size(); i++) {
                        if (getListTablaPacValor().get(i).getNumAvaluo() == getSelecAvaLiq()) {
                            validar++;
                        }
                    }

                    if (validar == 0) {

                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablaPacValor.size() + 1);
                        Factura.setNumAvaluo(getSelecAvaLiq());
                        Factura.setCodSegFac(Fac.validarNAval(getSelecAvaLiq()));
                        Factura.setValorFacturado(getValorFactura());

                        ListTablaPacValor.add(Factura);

                    } else {
                        mBTodero.setMens("Solo puede agregar un valor  por avalúo");
                        mBTodero.warn();
                    }

                }
                break;

            case "addValAll":

                if (getValorFactura() == 0) {
                    mBTodero.setMens("Debe ingresar una valor mayor a 0 ");
                    mBTodero.warn();
                } else {
                    ListTablaPacValor.clear();
                    for (int i = 0; i < selectListPendientXFact.size(); i++) {

                        LogFacturacion Factura = new LogFacturacion();
                        Factura.setContador(ListTablaPacValor.size() + 1);
                        Factura.setNumAvaluo(selectListPendientXFact.get(i).getCodAva());
                        Factura.setCodSegFac(Fac.validarNAval(selectListPendientXFact.get(i).getCodAva()));
                        Factura.setValorFacturado(getValorFactura());
                        ListTablaPacValor.add(Factura);

                    }

                }
                break;

        }
    }

    public void remover(String tipo) {

        switch (tipo) {

            case "distancia":

                for (int i = 0; i < ListTablaDistancias.size(); i++) {
                    //Tener en cuenta que este numero no lo cargo en la lista 
                    if (getListTablaDistancias().get(i).getNumAvaluo() == selecDist.getNumAvaluo()) {
                        ListTablaDistancias.remove(i);
                    }
                }
                selecDist = null;

                break;

            case "descuento":
                for (int i = 0; i < ListTablaDescuentos.size(); i++) {
                    //Tener en cuenta que este numero no lo cargo en la lista 
                    if (getListTablaDescuentos().get(i).getNumAvaluo() == selecDes.getNumAvaluo()) {
                        ListTablaDescuentos.remove(i);
                    }
                }
                selecDes = null;
                break;
            case "valAdi":
                for (int i = 0; i < ListTablaValAdici.size(); i++) {
                    //Tener en cuenta que este numero no lo cargo en la lista 
                    if (getListTablaValAdici().get(i).getNumAvaluo() == selecVaAdi.getNumAvaluo()
                            && getListTablaValAdici().get(i).getValorAdic() == selecVaAdi.getValorAdic()
                            && getListTablaValAdici().get(i).getContador() == selecVaAdi.getContador()) {
                        ListTablaValAdici.remove(i);
                    }
                }
                selecVaAdi = null;
                break;

            case "asig":
                for (int i = 0; i < ListTablaPacValor.size(); i++) {
                    //Tener en cuenta que este numero no lo cargo en la lista 
                    if (getListTablaPacValor().get(i).getNumAvaluo() == selecAvaLiqTabla.getNumAvaluo()) {
                        ListTablaPacValor.remove(i);
                    }
                }
                selecAvaLiqTabla = null;
                break;

            case "detalle":
                for (int i = 0; i < listaDetallesFac.size(); i++) {
                    //Tener en cuenta que este numero no lo cargo en la lista 
                    if (listaDetallesFac.get(i).getContador() == selecDist.getContador()) {
                        listaDetallesFac.remove(i);
                    }
                }
                selecDist = null;
                break;

        }

    }

    public void calcularGrupos(String Tab) throws SQLException {

        switch (Tab) {

            case "guardar":

                if (getTipoFacturacion().equalsIgnoreCase("Tabla")) {
                    setValorFactura(0);
                    ListTablaPacValor.clear();
                    RequestContext.getCurrentInstance().execute("PF('asignar').hide()");

                } else if (getTipoFacturacion().equalsIgnoreCase("Pactado")) {
                    ListTablaLiqTabla.clear();
                    RequestContext.getCurrentInstance().execute("PF('asignar').hide()");
                }

                break;

            case "liqTabla":
                ListTablaLiqTabla.clear();

                for (int i = 0; i < selectListPendientXFact.size(); i++) {
                    LogFacturacion Factura = new LogFacturacion();
                    Fac = new LogFacturacion();
                    long Dato = 0;
                    if (getListTablaDistancias() != null && !getListTablaDistancias().isEmpty()) {

                        for (int j = 0; j < getListTablaDistancias().size(); j++) {

                            if (i < getListTablaDistancias().size() && getListTablaDistancias().get(i).getNumAvaluo() == selectListPendientXFact.get(i).getCodAva()) {
                                Dato = Fac.ConsulValLiquid(selectListPendientXFact.get(i).getCodSeg(), getListTablaDistancias().get(i).getDistancia());
                                Factura.setDistanciaFact1(getListTablaDistancias().get(i).getDistancia() + " Km");
                            } else {
                                Dato = Fac.ConsulValLiquid(selectListPendientXFact.get(i).getCodSeg(), 0);
                                Factura.setDistanciaFact1("NO");
                            }

                        }

                    } else {
                        Dato = Fac.ConsulValLiquid(selectListPendientXFact.get(i).getCodSeg(), 0);
                        Factura.setDistanciaFact1("NO");
                    }
                    Factura.setContador(ListTablaLiqTabla.size() + 1);
                    Factura.setNumAvaluo(selectListPendientXFact.get(i).getCodAva());
                    Factura.setCodSegFac(selectListPendientXFact.get(i).getCodSeg());
                    Factura.setValorFacturado(Dato);
                    Factura.setNombreTabla(Fac.getNombreTabla());
                    Factura.setCodTabla(Fac.getCodTabla());
                    ListTablaLiqTabla.add(Factura);
                }

                //RequestContext.getCurrentInstance().execute("PF('asignar').show()");
                break;

            case "calcular":
                valorTotalTabla = 0;
                ValorAdicion = 0;
                ValorAdicionSinIva = 0;
                valorDescuentos = 0;

                if (getTipoFacturacion().equalsIgnoreCase("Tabla")) {
                    calcularGrupos("liqTabla");
                    for (int i = 0; i < ListTablaLiqTabla.size(); i++) {
                        valorTotalTabla = (int) (valorTotalTabla + ListTablaLiqTabla.get(i).getValorFacturado());
                    }
                } else if (getTipoFacturacion().equalsIgnoreCase("Pactado")) {
                    for (int i = 0; i < ListTablaPacValor.size(); i++) {
                        valorTotalTabla = (int) (valorTotalTabla + ListTablaPacValor.get(i).getValorFacturado());
                    }

                } else {
                    mBTodero.setMens("No ha seleccionado el modo de liquidación. Porfavor dirigase a la opcion asgnar tarifa");
                    mBTodero.warn();
                }

                if (getTipoFacturacion().equalsIgnoreCase("Tabla") || getTipoFacturacion().equalsIgnoreCase("Pactado")) {

                    if (!ListTablaValAdici.isEmpty() && ListTablaValAdici != null) {

                        for (int i = 0; i < ListTablaValAdici.size(); i++) {
                            if (ListTablaValAdici.get(i).getApplyIva().equalsIgnoreCase("Si")) {
                                ValorAdicion = ValorAdicion + ListTablaValAdici.get(i).getValorAdic();
                            } else if (ListTablaValAdici.get(i).getApplyIva().equalsIgnoreCase("No")) {
                                ValorAdicionSinIva = ValorAdicionSinIva + ListTablaValAdici.get(i).getValorAdic();
                            }
                        }
                    }

                    if (!ListTablaDescuentos.isEmpty() && ListTablaDescuentos != null) {

                        for (int i = 0; i < ListTablaDescuentos.size(); i++) {
                            if (ListTablaDescuentos.get(i).getTipoValor().equalsIgnoreCase("FIJO")) {
                                valorDescuentos = (int) (valorDescuentos + ListTablaDescuentos.get(i).getDescuentos());
                            }

                            if (ListTablaDescuentos.get(i).getTipoValor().equalsIgnoreCase("%")) {

                                if (getTipoFacturacion().equalsIgnoreCase("Tabla")) {

                                    if (getListTablaLiqTabla().get(i).getNumAvaluo() == getListTablaDescuentos().get(i).getNumAvaluo()) {
                                        valorDescuentos = (int) (valorDescuentos + (getListTablaLiqTabla().get(i).getValorFacturado() * getListTablaDescuentos().get(i).getDescuentos() / 100));
                                    }
                                } else if (getTipoFacturacion().equalsIgnoreCase("Pactado")) {

                                    if (getListTablaPacValor().get(i).getNumAvaluo() == getListTablaDescuentos().get(i).getNumAvaluo()) {
                                        valorDescuentos = (int) (valorDescuentos + (getListTablaPacValor().get(i).getValorFacturado() * getListTablaDescuentos().get(i).getDescuentos() / 100));
                                    }

                                }

                            }
                        }

                    }
                    setSubtotal(getValorTotalTabla() + getValorAdicion() - getValorDescuentos());
                    setIva(getSubtotal() * getIvaBD() / 100);
                    setTotalpagarFactAnt((long) (getSubtotal() + getValorAdicionSinIva() + getIva()));

                    /*Panel reparticion por personas*/
                    if ((!mBArchivos.getListClientesPersonasAFacturarAntic().isEmpty() && mBArchivos.getListClientesPersonasAFacturarAntic()
                            != null && mBArchivos.getListClientesPersonasAFacturarAntic().size() == 1)
                            || (!mBArchivos.getListEntidadesPersonasAFacturarAntic().isEmpty() && mBArchivos.getListEntidadesPersonasAFacturarAntic()
                            != null && mBArchivos.getListEntidadesPersonasAFacturarAntic().size() == 1)) {
                        setAsignTotal1(getValorTotalTabla());
                        setAsigDescuento1(getValorDescuentos());
                        setAsigValorAdicional1(getValorAdicion());
                        mBArchivos.Arch.setValorParaPerFact1(getValorAnti());
                    }
                }
                break;
        }
    }

    public void eliminarValAdic(int caso) {

        switch (caso) {
            case 1:
                if (FacTablas != null) {
                    Factu.EliminarValAdic(FacTablas.getValorAdicion().replace(",", ""), Fac.getCodSeg(), 1);
                    mBTodero.setMens("Valor adiconal eliminado exitosamente");
                    mBTodero.info();
                    RequestContext.getCurrentInstance().execute("PF('vlaAdic').hide()");
                } else {
                    mBTodero.setMens("Seleccione un registro");
                    mBTodero.warn();
                }
                break;

            case 2:

                if (FacTablasTarifas != null) {
                    Factu.EliminarValAdic(String.valueOf(FacTablasTarifas.getDistanciaFact2()), Fac.getCodSeg(), 2);
                    mBTodero.setMens("Distancia eliminada exitosamente");
                    mBTodero.info();
                    RequestContext.getCurrentInstance().execute("PF('dis').hide()");

                } else {
                    mBTodero.setMens("Seleccione un registro");
                    mBTodero.warn();
                }
                break;

            case 3:
                //VAdic
                RequestContext.getCurrentInstance().execute("PF('vlaAdic').show()");
                setListValAdiAva(Factu.ValoresAdiAva(Fac.getCodSeg()));
                break;

            case 4:
                FacTablasTarifas = new LogFacturacion();
                RequestContext.getCurrentInstance().execute("PF('dis').show()");
                setListDistancia(Factu.Distancia(Fac.getCodSeg()));
                break;
        }
    }

    public void ClearValAdicional() {

        setOpcValAdc(false);
        ValorAdicion = 0;
        TotalValAdic = 0;
        ValorAd = 0;
        TipoValAdc = 0;
        ListTablasFacturacTarifas.clear();

    }

    /**
     * Metodo que consulta el numero de registros totales de facturacion
     *
     * @author Laura Guerrero
     * @since 19-05-2015
     */
    public void consultaTotalRegistrosTablas() {

        LogFacturacion fct = new LogFacturacion();
        LogRadicacion rad = new LogRadicacion();
        NumPendiFac = fct.ConsultPendientXFac().size();
        NumPendiImpr = fct.ConsultPendientXImpri("general").size();
        NumPendiFacEx = fct.ConsultPendientExce().size();
        NumPendiActRec = rad.ConsultasRadicacionYSeguimiento(6, mBsesion.codigoMiSesion()).size();
        //NumPendiActRec= Rad.ConsultasRadicacionYSeguimiento(2, mBsesion.codigoMiSesion()).size();
    }

    public void exportTablaLiq() throws WriteException {

        /*INICIA EL PROCESO DE LA CREACION DEL ARCHIVO DE EXCEL*/
        File Rutas = new File(File.separator + "DBARCHIVOS" + File.separator + "LiquidaciónTabla.xls");
        int Row = 7;
        //Escribir el tipo de formato de fuente que va a tener el archivo de Excel
        WritableFont ExcFontHeads = new WritableFont(WritableFont.ARIAL, 15, WritableFont.BOLD, false, UnderlineStyle.NO_UNDERLINE, Colour.DARK_BLUE, ScriptStyle.SUPERSCRIPT);
        WritableCellFormat CellFormatHeads = new WritableCellFormat(ExcFontHeads);
        WritableCellFormat titulo1 = new WritableCellFormat(ExcFontHeads);
        WritableCellFormat titulo2 = new WritableCellFormat(ExcFontHeads);
        CellFormatHeads.setBorder(Border.ALL, BorderLineStyle.THIN);
        CellFormatHeads.setAlignment(Alignment.CENTRE);
        titulo2.setAlignment(Alignment.RIGHT);

        WritableFont ExcFont = new WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD);
        WritableCellFormat CellFormat = new WritableCellFormat(ExcFont);
        // CellFormat.setBorder(Border.ALL, BorderLineStyle.THIN);
        CellFormat.setAlignment(Alignment.RIGHT);
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
            WorkBook.createSheet("ConsultaFactura", 0);
            ExcSheet = WorkBook.getSheet(0);

            ExcSheet.setColumnView(2, 18);
            ExcSheet.setColumnView(3, 18);
            ExcSheet.setColumnView(4, 15);
            ExcSheet.setColumnView(5, 20);
            ExcSheet.setColumnView(6, 20);

        } catch (IOException ex) {
            System.err.println("Error: " + ex.getClass() + " .exportConsulRad() causado por : " + ex.getMessage());//GCH 02ENE2017
        }

        Label TipMonto, titulo, aval,
                valMon,
                valor,
                tipTabla,
                valLiq,
                distancia,
                tipValDis,
                valorDis;
        try {

            titulo = new Label(4, 3, "CALCULO DE HONORARIOS", titulo1);
            aval = new Label(2, Row, "" + Fac.getCodAva(), titulo2);
            valor = new Label(4, Row, "POR MIL", CellFormatHeads);
            valLiq = new Label(5, Row, "VR. HONORAIOS", CellFormatHeads);
            tipTabla = new Label(6, Row, "DATOS * INGRESAR", CellFormatHeads);

            Row++;
            ExcSheet.addCell(aval);
            ExcSheet.addCell(titulo);
            ExcSheet.addCell(valor);
            ExcSheet.addCell(tipTabla);
            ExcSheet.addCell(valLiq);
            DecimalFormat formatea = new DecimalFormat("###,###.##");
            String subt = "";
            for (int i = 0; i < ListTablaAplicada.size(); i++) {

                TipMonto = new Label(2, Row, (i == 0) ? "Por los primeros  " : "Por los siguientes", CellFormat);
                valMon = new Label(3, Row, ListTablaAplicada.get(i).getTipoValorAdd(), CellFormat);
                valor = new Label(4, Row, ListTablaAplicada.get(i).getConcepto(), CellFormat);
                valLiq = new Label(5, Row, String.valueOf(formatea.format(ListTablaAplicada.get(i).getValorLiquidado())), CellFormat);
                tipTabla = new Label(6, Row, String.valueOf(formatea.format(ListTablaAplicada.get(i).getDatoIngresado())), CellFormat);

                Row++;

                ExcSheet.addCell(TipMonto);
                ExcSheet.addCell(valMon);
                ExcSheet.addCell(valor);
                ExcSheet.addCell(tipTabla);
                ExcSheet.addCell(valLiq);
                subt = String.valueOf(ListTablaAplicada.get(i).getTipoTabla());
            }
            Row++;
            titulo = new Label(4, Row, "SUBTOTAL $", titulo1);
            ExcSheet.addCell(titulo);
            titulo = new Label(5, Row++, String.valueOf(formatea.format(Fac.getValorFacturado())), titulo2);
            ExcSheet.addCell(titulo);
            titulo = new Label(4, Row, "IVA " + ivaBD + " % $", titulo1);
            ExcSheet.addCell(titulo);
            titulo = new Label(5, Row++, String.valueOf(formatea.format(Math.round(Fac.getValorFacturado() * ivaBD / 100))), titulo2);
            ExcSheet.addCell(titulo);
            titulo = new Label(4, Row, "TOTAL $", titulo1);
            ExcSheet.addCell(titulo);
            titulo = new Label(5, Row++, String.valueOf(formatea.format(Math.round(Fac.getValorFacturado() + (Fac.getValorFacturado() * ivaBD / 100)))), titulo2);
            ExcSheet.addCell(titulo);
            if (ListaDistanciaTablas != null && !ListaDistanciaTablas.isEmpty() && Fac.getDistancia() != 0) {

                Row += 2;
                distancia = new Label(4, Row, "<= Km", CellFormatHeads);
                tipValDis = new Label(5, Row, "Tipo Tabla", CellFormatHeads);
                valorDis = new Label(6, Row, "Valor", CellFormatHeads);
                Row++;

                ExcSheet.addCell(distancia);
                ExcSheet.addCell(tipValDis);
                ExcSheet.addCell(valorDis);
                for (int i = 0; i < ListaDistanciaTablas.size(); i++) {

                    distancia = new Label(4, Row, String.valueOf(ListaDistanciaTablas.get(i).getDistanciaTable()), CellFormat);
                    tipValDis = new Label(5, Row, String.valueOf(ListaDistanciaTablas.get(i).getTipoValorTable()), CellFormat);
                    valorDis = new Label(6, Row, ListaDistanciaTablas.get(i).getDistanciaFact1(), CellFormat);
                    Row++;

                    ExcSheet.addCell(distancia);
                    ExcSheet.addCell(tipValDis);
                    ExcSheet.addCell(valorDis);
                }

            }

        } catch (WriteException ex) {
            System.err.println("Error: " + ex.getClass() + " .exportTablaLiquidada() causado por : " + ex.getMessage());//GCH 02ENE2017
        }
        Conexion.Conexion.CloseCon();
        //Escribe el archivo excel en disco
        try {

            WorkBook.write();
            WorkBook.close();
            //Genero la descarga del archivo para el usuario
            mBArchivos.DescarArcIndi(String.valueOf(Rutas), "LiquidacionTabla", "xls");
            Rutas.delete();
        } catch (IOException | WriteException ex) {
            System.err.println("Error: " + ex.getClass() + " .exportTablaLiquidada() causado por : " + ex.getMessage());//GCH 02ENE2017
        }
    }
}
