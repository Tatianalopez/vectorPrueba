/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBeanConsultas;

import LogBean.BeanArchivos;
import LogBean.BeanAvaluo;
import LogBean.BeanRadicacion;
import LogBean.BeanTodero;
import Logic.LogAdministracion;
import Logic.LogCargueArchivos;
import Logic.LogConsulta;
import Logic.LogFacturacion;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;
import jxl.write.WriteException;
import org.primefaces.context.RequestContext;

/**
 *
 * @author admin
 */
@ManagedBean(name = "MBConsultaFactu")
@ViewScoped
@SessionScoped
public class BeanConsultaFactu {

    /**
     * Creates a new instance of BeanConsulta
     */
    public BeanConsultaFactu() {
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
     * ManagedBean BeanAvaluo.java
     *
     *
     * @see BeanTodero.java
     */
    @ManagedProperty("#{MBAvaluo}")
    private BeanAvaluo mbAvaluo;

    public BeanAvaluo getMbAvaluo() {
        return mbAvaluo;
    }

    public void setMbAvaluo(BeanAvaluo mbAvaluo) {
        this.mbAvaluo = mbAvaluo;
    }

    LogAdministracion Adm = new LogAdministracion();
    private String OpcionConsul = "";
    private boolean OpcFechaFac;
    private Date FechaFacIni;
    private Date FechaFacFin;
    private boolean OpcNumAva;
    private boolean OpcNumFac;
    private boolean OpcDirec;
    private String RemitidoX;
    LogConsulta ConFac = new LogConsulta();
    LogConsulta Fac = new LogConsulta();
    private String FiltrarX;
    private String FiltrarXCarta;
    private String OpcPerito;
    private List ListEntidad = null;
    List<LogFacturacion> ListTablaAplicada = null;
    List<LogFacturacion> ListaDistanciaTablas = null;
    List<LogConsulta> ListClientes = null;
    List<LogConsulta> SelecListClientes = null;
    List<LogConsulta> MostrarRemision = null;
    private String NombreCliente;
    private String EntSoli;
    private List SelectListProEntSeg = null;//Variable para la muestra de la seleccion de informacion del producto Entidad
    private List ListProEntSeg = null;//Variable para la seleccion de informacion del producto Entidad
    private List ListTipoAvaluo = null;
    private List ListEstados = null;
    private List SelectListTipoAvaluo = null;
    private List SelListEstad = null;
    private boolean OpcMatricula;
    private boolean Funcionario;
    String Query = "";
    private String Val;
    LogConsulta Cons = new LogConsulta();
    LogFacturacion Factu = new LogFacturacion();
    boolean viewPan = false;
    private List<LogConsulta> ListaConsulFac = new ArrayList<>();
    private List<LogConsulta> SelectListaConsulFac = new ArrayList<>();
    //private List<LogAnticipo> selectAprobados = null;
    private int numAva = 0;
    private int numFac = 0;
    private String direccion = "";
    private String matricula = "";
    private String codDeptoSol;
    private String codCiudadSol;
    private String IdentificacionPerito;
    private String NombrePerito;
    private String IdCli;
    private String Nit;
    private String nameEnt;
    private boolean genCart;
    private boolean panelOtro = false;
    private int numRemision;
    private double ValorFacturar;
    private double ValorAdicion;
    private double ValorAdicionSinIva;
    private double valAdiFacIva;
    private double valAdiFacSinIva;
    private double valorDescuentos;
    private double sumaFacAnt;
    private double subtotal;
    private double iva;
    private double valorIca;
    private double valorFuente;
    private double valorReIva;
    //private double total;
    private double saldoPagar;
    private double anticipoFac;
    private boolean existeFactura;

    @ManagedProperty("#{MBRadicacion}")
    private BeanRadicacion mBRadicacion;

    public BeanRadicacion getmBRadicacion() {
        return mBRadicacion;
    }

    public void setmBRadicacion(BeanRadicacion mBRadicacion) {
        this.mBRadicacion = mBRadicacion;
    }

    public boolean isExisteFactura() {
        return existeFactura;
    }

    public void setExisteFactura(boolean existeFactura) {
        this.existeFactura = existeFactura;
    }

    public List getSelListEstad() {
        return SelListEstad;
    }

    public void setSelListEstad(List SelListEstad) {
        this.SelListEstad = SelListEstad;
    }

    public List getListEstados() {
        return ListEstados;
    }

    public void setListEstados(List ListEstados) {
        this.ListEstados = ListEstados;
    }

    public double getValorFacturar() {
        return ValorFacturar;
    }

    public void setValorFacturar(double ValorFacturar) {
        this.ValorFacturar = ValorFacturar;
    }

    public boolean isPanelOtro() {
        return panelOtro;
    }

    public List<LogConsulta> getMostrarRemision() {
        return MostrarRemision;
    }

    public void setMostrarRemision(List<LogConsulta> MostrarRemision) {
        this.MostrarRemision = MostrarRemision;
    }

    public void setPanelOtro(boolean panelOtro) {
        this.panelOtro = panelOtro;
    }

    public int getNumRemision() {
        return numRemision;
    }

    public void setNumRemision(int numRemision) {
        this.numRemision = numRemision;
    }

    public boolean isGenCart() {
        return genCart;
    }

    public String getEntSoli() {
        return EntSoli;
    }

    public void setEntSoli(String EntSoli) {
        this.EntSoli = EntSoli;
    }

    public List<LogConsulta> getSelecListClientes() {
        return SelecListClientes;
    }

    public void setSelecListClientes(List<LogConsulta> SelecListClientes) {
        this.SelecListClientes = SelecListClientes;
    }

    public void setGenCart(boolean genCart) {
        this.genCart = genCart;
    }

    public String getNameEnt() {
        return nameEnt;
    }

    public String getFiltrarXCarta() {
        return FiltrarXCarta;
    }

    public void setFiltrarXCarta(String FiltrarXCarta) {
        this.FiltrarXCarta = FiltrarXCarta;
    }

    public LogConsulta getConFac() {
        return ConFac;
    }

    public void setConFac(LogConsulta ConFac) {
        this.ConFac = ConFac;
    }

    public List<LogConsulta> getListClientes() {
        return ListClientes;
    }

    public void setListClientes(List<LogConsulta> ListClientes) {
        this.ListClientes = ListClientes;
    }

    public void setNameEnt(String nameEnt) {
        this.nameEnt = nameEnt;
    }

    public String getIdCli() {
        return IdCli;
    }

    public void setIdCli(String IdCli) {
        this.IdCli = IdCli;
    }

    public String getNit() {
        return Nit;
    }

    public void setNit(String Nit) {
        this.Nit = Nit;
    }

    public LogConsulta getFac() {
        return Fac;
    }

    public void setFac(LogConsulta Fac) {
        this.Fac = Fac;
    }

    public List<LogFacturacion> getListaDistanciaTablas() {
        return ListaDistanciaTablas;
    }

    public void setListaDistanciaTablas(List<LogFacturacion> ListaDistanciaTablas) {
        this.ListaDistanciaTablas = ListaDistanciaTablas;
    }

    public List<LogConsulta> getSelectListaConsulFac() {
        return SelectListaConsulFac;
    }

    public void setSelectListaConsulFac(List<LogConsulta> SelectListaConsulFac) {
        this.SelectListaConsulFac = SelectListaConsulFac;
    }

    public boolean isViewPan() {
        return viewPan;
    }

    public void setViewPan(boolean viewPan) {
        this.viewPan = viewPan;
    }

    public LogConsulta getCons() {
        return Cons;
    }

    public List<LogFacturacion> getListTablaAplicada() {
        return ListTablaAplicada;
    }

    public void setListTablaAplicada(List<LogFacturacion> ListTablaAplicada) {
        this.ListTablaAplicada = ListTablaAplicada;
    }

    public void setCons(LogConsulta Cons) {
        this.Cons = Cons;
    }

    public String getFiltrarX() {
        return FiltrarX;
    }

    public void setFiltrarX(String FiltrarX) {
        this.FiltrarX = FiltrarX;
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

    public String getCodDeptoSol() {
        return codDeptoSol;
    }

    public void setCodDeptoSol(String codDeptoSol) {
        this.codDeptoSol = codDeptoSol;
    }

    public String getCodCiudadSol() {
        return codCiudadSol;
    }

    public void setCodCiudadSol(String codCiudadSol) {
        this.codCiudadSol = codCiudadSol;
    }

    public String getOpcPerito() {
        return OpcPerito;
    }

    public void setOpcPerito(String OpcPerito) {
        this.OpcPerito = OpcPerito;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public int getNumAva() {
        return numAva;
    }

    public void setNumAva(int numAva) {
        this.numAva = numAva;
    }

    public int getNumFac() {
        return numFac;
    }

    public void setNumFac(int numFac) {
        this.numFac = numFac;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getVal() {
        return Val;
    }

    public void setVal(String Val) {
        this.Val = Val;
    }

    public List<LogConsulta> getListaConsulFac() {
        return ListaConsulFac;
    }

    public void setListaConsulFac(List<LogConsulta> ListaConsulFac) {
        this.ListaConsulFac = ListaConsulFac;
    }

    public boolean isOpcMatricula() {
        return OpcMatricula;
    }

    public void setOpcMatricula(boolean OpcMatricula) {
        this.OpcMatricula = OpcMatricula;
    }

    public boolean isFuncionario() {
        return Funcionario;
    }

    public void setFuncionario(boolean Funcionario) {
        this.Funcionario = Funcionario;
    }

    public List getListTipoAvaluo() {
        return ListTipoAvaluo;
    }

    public void setListTipoAvaluo(List ListTipoAvaluo) {
        this.ListTipoAvaluo = ListTipoAvaluo;
    }

    public List getSelectListTipoAvaluo() {
        return SelectListTipoAvaluo;
    }

    public void setSelectListTipoAvaluo(List SelectListTipoAvaluo) {
        this.SelectListTipoAvaluo = SelectListTipoAvaluo;
    }

    public List getSelectListProEntSeg() {
        return SelectListProEntSeg;
    }

    public void setSelectListProEntSeg(List SelectListProEntSeg) {
        this.SelectListProEntSeg = SelectListProEntSeg;
    }

    public List getListProEntSeg() {
        return ListProEntSeg;
    }

    public void setListProEntSeg(List ListProEntSeg) {
        this.ListProEntSeg = ListProEntSeg;
    }

    public String getNombreCliente() {
        return NombreCliente;
    }

    public void setNombreCliente(String NombreCliente) {
        this.NombreCliente = NombreCliente;
    }

    public List getListEntidad() {
        return ListEntidad;
    }

    public void setListEntidad(List ListEntidad) {
        this.ListEntidad = ListEntidad;
    }

    public boolean isOpcNumAva() {
        return OpcNumAva;
    }

    public void setOpcNumAva(boolean OpcNumAva) {
        this.OpcNumAva = OpcNumAva;
    }

    public boolean isOpcNumFac() {
        return OpcNumFac;
    }

    public void setOpcNumFac(boolean OpcNumFac) {
        this.OpcNumFac = OpcNumFac;
    }

    public boolean isOpcDirec() {
        return OpcDirec;
    }

    public void setOpcDirec(boolean OpcDirec) {
        this.OpcDirec = OpcDirec;
    }

    public String getRemitidoX() {
        return RemitidoX;
    }

    public void setRemitidoX(String RemitidoX) {
        this.RemitidoX = RemitidoX;
    }

    public Date getFechaFacIni() {
        return FechaFacIni;
    }

    public void setFechaFacIni(Date FechaFacIni) {
        this.FechaFacIni = FechaFacIni;
    }

    public Date getFechaFacFin() {
        return FechaFacFin;
    }

    public void setFechaFacFin(Date FechaFacFin) {
        this.FechaFacFin = FechaFacFin;
    }

    public boolean isOpcFechaFac() {
        return OpcFechaFac;
    }

    public void setOpcFechaFac(boolean OpcFechaFac) {
        this.OpcFechaFac = OpcFechaFac;
    }

    public String getOpcionConsul() {
        return OpcionConsul;
    }

    public void setOpcionConsul(String OpcionConsul) {
        this.OpcionConsul = OpcionConsul;
    }

    public double getValorAdicion() {
        return ValorAdicion;
    }

    public void setValorAdicion(double ValorAdicion) {
        this.ValorAdicion = ValorAdicion;
    }

    public double getValorAdicionSinIva() {
        return ValorAdicionSinIva;
    }

    public void setValorAdicionSinIva(double ValorAdicionSinIva) {
        this.ValorAdicionSinIva = ValorAdicionSinIva;
    }

    public double getValAdiFacIva() {
        return valAdiFacIva;
    }

    public void setValAdiFacIva(double valAdiFacIva) {
        this.valAdiFacIva = valAdiFacIva;
    }

    public double getValAdiFacSinIva() {
        return valAdiFacSinIva;
    }

    public void setValAdiFacSinIva(double valAdiFacSinIva) {
        this.valAdiFacSinIva = valAdiFacSinIva;
    }

    public double getValorDescuentos() {
        return valorDescuentos;
    }

    public void setValorDescuentos(double valorDescuentos) {
        this.valorDescuentos = valorDescuentos;
    }

    public double getSumaFacAnt() {
        return sumaFacAnt;
    }

    public void setSumaFacAnt(double sumaFacAnt) {
        this.sumaFacAnt = sumaFacAnt;
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

    public double getValorIca() {
        return valorIca;
    }

    public void setValorIca(double valorIca) {
        this.valorIca = valorIca;
    }

    public double getValorFuente() {
        return valorFuente;
    }

    public void setValorFuente(double valorFuente) {
        this.valorFuente = valorFuente;
    }

    public double getValorReIva() {
        return valorReIva;
    }

    public void setValorReIva(double valorReIva) {
        this.valorReIva = valorReIva;
    }

    public double getSaldoPagar() {
        return saldoPagar;
    }

    public void setSaldoPagar(double saldoPagar) {
        this.saldoPagar = saldoPagar;
    }

    public double getAnticipoFac() {
        return anticipoFac;
    }

    public void setAnticipoFac(double anticipoFac) {
        this.anticipoFac = anticipoFac;
    }

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

    public void onCargarEstados() {
        String OPciones = "";
        try {
            SelListEstad = new ArrayList<>();
            for (int j = 0; j <= this.ListEstados.size() - 1; j++) {
                String codigo = ListEstados.get(j).toString();
                if (null != codigo) {
                    switch (codigo) {
                        case "F":
                            OPciones = "EN FACTURACIÓN";
                            break;
                        case "PP":
                            OPciones = "CARTERA";
                            break;
                        case "SP":
                            OPciones = "SALDO A FAVOR ";
                            break;
                        case "P":
                            OPciones = "PAGADA";
                            break;
                        case "PC":
                            OPciones = "PAGO PARCIAL";
                            break;
                        case "CJ":
                            OPciones = "COBRO JURIDICO ";
                            break;
                        case "C":
                            OPciones = "CASTIGADA";
                            break;
                        case "R":
                            OPciones = "RECUPERADA";
                            break;
                        case "N":
                            OPciones = "ANULADA";
                            break;

                    }
                }
                SelListEstad.add(new SelectItem(codigo, OPciones));

            }
        } catch (NumberFormatException e) {
            mbTodero.setMens("Error " + e.getClass());
            mbTodero.error();
        }
    }
    ResultSet Dat = null;
    String QueSinAval = "";

    public void validaInfoFactu(String Tipo) throws ParseException, IOException, WriteException, SQLException {

        switch (Tipo) {

            case "verTabla":
                //CodSeg == Cod tabla (reutilizando)
                LogFacturacion Factu1 = new LogFacturacion();
                ListTablaAplicada = Factu1.TablaFacturacion("Exedente", Integer.valueOf(Cons.getTabla()));
                ListaDistanciaTablas = new ArrayList<>();
                ListaDistanciaTablas = Factu1.TablaDistanciaFac("", Integer.valueOf(Cons.getTabla()));
                RequestContext.getCurrentInstance().execute("PF('tablaAplicada').show()");
                break;
            case "Anular":
                Factu = new LogFacturacion();
                Factu.AnularFac(Integer.valueOf(Fac.getCodFac()));
                ListaConsulFac = new ArrayList<>();
                ListaConsulFac = Cons.ConsultaFac("Gen", Query, QueSinAval);
                Fac = new LogConsulta();

                break;

            case "OpenPopAnu":

                if (Fac != null) {
                    if (Fac.getCodFac().isEmpty()) {
                        mbTodero.setMens("el/los avalúos no se ha facturado");
                        mbTodero.warn();
                    } else if (!(Fac.getEstadoAvaluo().equalsIgnoreCase("En facturacion") || Fac.getEstadoAvaluo().equalsIgnoreCase("Cobro"))) {
                        mbTodero.setMens("La factura no se puede anular ya que su estado actual no lo permite");
                        mbTodero.warn();
                    } else {
                        RequestContext.getCurrentInstance().execute("PF('AnuFac').show()");

                    }

                } else {
                    mbTodero.setMens("Seleccione una factura a anular");
                    mbTodero.warn();
                }

                break;

            case "OpenPopTable":

                if (Fac != null) {
                    if (Fac.getCodFac() == null || Fac.getCodFac().isEmpty()) {
                        mbTodero.setMens("el/los avalúos no se ha facturado");
                        mbTodero.info();
                    } else if (Fac.getTabla() != null && !Fac.getTabla().equalsIgnoreCase("Pactado")) {
                        ListTablaAplicada = Factu.TablaFacturacion("Exedente", Integer.valueOf(Fac.getTabla()));
                        ListaDistanciaTablas = Factu.TablaDistanciaFac("Exedente", Integer.valueOf(Fac.getTabla()));
                        RequestContext.getCurrentInstance().execute("PF('tablaAplicada').show()");
                    } else {
                        mbTodero.setMens("El avalúo fue pactado");
                        mbTodero.info();
                    }

                } else {
                    mbTodero.setMens("Seleccione un registro a ver tabla aplicada");
                    mbTodero.warn();
                }
                break;

            case "OpenPopTableEsp":

                if (FiltrarX.equalsIgnoreCase("Factura")) {
                    ListTablaAplicada = Factu.TablaFacturacion("", numFac);
                    ListaDistanciaTablas = Factu.TablaDistanciaFac("", numFac);
                }

                if (ListTablaAplicada != null && ListTablaAplicada.size() > 0) {
                    RequestContext.getCurrentInstance().execute("PF('tablaAplicada').show()");
                } else {
                    mbTodero.setMens("No se aplico ninguna tabla (Valor pactado).");
                    mbTodero.warn();
                }

                break;

            case "Cons":
                viewPan = false;
                existeFactura = false;
                switch (this.OpcionConsul) {
                    case "Esp":// Para el caso de que sea una preradicacion especifica
                        Cons = new LogConsulta();
                        Cons.ConsultaFac("Esp", (FiltrarX.equalsIgnoreCase("Factura") ? " where Cod_Factura1= " + getNumFac() : "  where cn.avaluos=" + getNumAva() + ""), QueSinAval);
                        if (Cons.getCodAvaluos() != null) {
                            viewPan = true;
                        } else {
                            mbTodero.setMens("No se ha encontrado información referente al avalúo número " + getNumFac() + ".");
                            mbTodero.warn();
                        }
                        if (Cons.getCodFac() != null) {
                            existeFactura = (!(Cons.getCodFac().equals("0")));
                            if (!existeFactura) {
                                mbTodero.setMens("No se ha facturado el avalúo.");
                                mbTodero.warn();
                            } else {
                                mBArchivos.setCodFac(Cons.getCodFac());
                                mBArchivos.setCargArchivos(mBArchivos.getConsulArchivos(14));
                                mBArchivos.setListaArchivosAva(new ArrayList<>());
                                mBArchivos.setListaArchivosAva(mBArchivos.MostrarArchivos(10, File.separator + "DBArchivos" + File.separator + "DBFacturas"));

                            }
                        }

                        break;
                    case "Gen":// Para el caso de que sea una consulta general
                        int Paso = 0;
                        String ItemFecRad = "NO",
                         ItemMatricula = "NO",
                         itemNumAva = "NO",
                         ItemPerito = "NO",
                         itemNumFac = "NO",
                         itemEstado = "NO",
                         itemDireccion = "NO",
                         ItemProEnt = "NO",
                         ItemTipoAva = "NO",
                         ItemRemX = "NO";
                        Query = "";

                        if (OpcFechaFac == true) {
                            //Para la validacion de la fecha de Radicacion
                            if (FechaFacIni == null || FechaFacFin == null) {
                                mbTodero.setMens("Falta llenar información de rangos en fechas de facturación");
                                mbTodero.warn();
                                Paso++;
                            } else if ((this.FechaFacFin.before(this.FechaFacIni))) {
                                mbTodero.setMens("La fecha inicial es mayor a la fecha final");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                ItemFecRad = "OK";
                            }
                        }

                        if (OpcNumAva) {
                            if (numAva <= 0) {
                                mbTodero.setMens("Ingrese un numero de avalúo valido");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                itemNumAva = "OK";
                            }
                        }

                        if (OpcNumFac) {
                            if (numFac <= 0) {
                                mbTodero.setMens("Ingrese un numero de factura valido");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                itemNumFac = "OK";
                            }
                        }
                        if (this.ListEstados.size() > 0) {
                            itemEstado = "OK";
                        }

                        if (OpcDirec) {
                            if (direccion.isEmpty() || direccion.equals("")) {
                                mbTodero.setMens("Ingrese palabras clave en el campo dirección");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                itemDireccion = "OK";
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

                        if (this.ListProEntSeg.size() > 0) {
                            ItemProEnt = "OK";
                        }

                        if (this.ListTipoAvaluo.size() > 0) {
                            ItemTipoAva = "OK";
                        }
                        if (OpcMatricula) {
                            if (matricula.isEmpty() || matricula.equals("")) {
                                mbTodero.setMens("Ingrese palabras clave en el campo matricula");
                                mbTodero.warn();
                                Paso++;
                            } else {
                                ItemMatricula = "OK";
                            }
                        }

                        //Estructura la informacion de los filtros que se encontraran dentro de la consulta
                        if (Paso == 0) {

                            SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
                            //Fechas de Facturacion

                            if ("OK".equals(ItemFecRad)) {
                                Query = "where Fecha_Factura between '" + Format.format(this.FechaFacIni) + "' and  date_add('" + Format.format(this.FechaFacFin) + "',interval 1 day)  ";

                            }
                            if ("OK".equals(itemNumFac)) {
                                if ("".equals(Query)) {
                                    Query = "where Cod_Factura =" + numFac + " ";
                                } else {
                                    Query = Query + " and Cod_Factura =" + numFac + " ";
                                }

                            }

                            if ("OK".equals(itemEstado)) {
                                for (int i = 0; i < ListEstados.size(); i++) {
                                    Val = ListEstados.get(i).toString();
                                    if ("".equals(Query)) {
                                        Query = " where (Estado_Factura = '" + Val + "' ";
                                    } else if (i == 0) {
                                        Query = Query + "and (Estado_Factura = '" + Val + "' ";
                                    } else {
                                        Query = Query + "or Estado_Factura = '" + Val + "' ";
                                    }
                                }
                                Query = Query + ")";
                            }

                            //Facturado por (Cliente or Entidad)
                            Val = "";
                            if (!"".equals(RemitidoX)) {

                                switch (RemitidoX) {
                                    case "Cliente"://Llenado de informacion para el cliente que remite la solicitud
                                        if ("".equals(Query)) {
                                            Query = "where perAFAC like '%" + this.NombreCliente + "%'  ";
                                        } else {
                                            Query = Query + " and  perAFAC  like  '%" + this.NombreCliente + "%'  ";
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
                                            Query = "where CodEnt in (" + Val + ")";
                                        } else {
                                            Query = Query + " and CodEnt in (" + Val + ")";
                                        }
                                        break;
                                }
                            }

                            QueSinAval = Query;

                            if ("OK".equals(itemNumAva)) {
                                if ("".equals(Query)) {
                                    Query = "where avaluos=" + numAva + " ";
                                } else {
                                    Query = Query + " and avaluos=" + numAva + " ";
                                }
                            }

                            if ("OK".equals(itemDireccion)) {
                                if ("".equals(Query)) {
                                    Query = "where direccion like '%" + direccion + "%' ";
                                } else {
                                    Query = Query + "and  direccion like '%" + direccion + "%'  ";
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
                                    Query = "where Cod_ProEnt in (" + Val + ")";
                                } else {
                                    Query = Query + " and Cod_ProEnt in (" + Val + ")";
                                }
                            }

                            //Tipo Avalúo
                            Val = "";
                            if ("OK".equals(ItemTipoAva)) {
                                for (int i = 0; i < ListTipoAvaluo.size(); i++) {

                                    Val = (ListTipoAvaluo.get(i).toString().equalsIgnoreCase("M")) ? "Maquinaria" : (ListTipoAvaluo.get(i).toString().equalsIgnoreCase("E")) ? "Mueble" : "Predio";
                                    if ("".equals(Query)) {
                                        Query = " where (TipAva like '%" + Val + "%' ";
                                    } else if (i == 0) {
                                        Query = Query + "and (TipAva like '%" + Val + "%' ";
                                    } else {
                                        Query = Query + "or TipAva like '%" + Val + "%' ";
                                    }

                                }
                                Query = Query + ")";
                            }

                            if ("OK".equals(ItemMatricula)) {
                                if ("".equals(Query)) {
                                    Query = "where matricula like '%" + matricula + "%' ";
                                } else {
                                    Query = Query + " and matricula like '%" + matricula + "%' ";
                                }
                            }

                            if (!mbAvaluo.getCodDeptoSol().equalsIgnoreCase("0") && !mbAvaluo.getCodDeptoSol().isEmpty() && !mbAvaluo.getCodDeptoSol().equalsIgnoreCase("")) {
                                if (!mbAvaluo.getCodCiudadSol().equalsIgnoreCase("0") && !mbAvaluo.getCodCiudadSol().isEmpty() && !mbAvaluo.getCodCiudadSol().equalsIgnoreCase("")) {
                                    if ("".equals(Query)) {
                                        Query = "where Cod_Ciudad=" + mbAvaluo.getCodCiudadSol() + " ";
                                    } else {
                                        Query = Query + " and Cod_Ciudad=" + mbAvaluo.getCodCiudadSol() + " ";
                                    }
                                } else {
                                    mbTodero.setMens("Debe seleccionar una ciudad");
                                    mbTodero.warn();
                                }
                            }

                            if (!Query.isEmpty()) {
                                Cons = new LogConsulta();
                                ListaConsulFac = new ArrayList<>();
                                ListaConsulFac = Cons.ConsultaFac("Gen", Query, QueSinAval);

                                if (ListaConsulFac == null || ListaConsulFac.isEmpty() || ListaConsulFac.size() <= 0) {

                                    mbTodero.setMens("No se encotraron resultados para la busqueda. Intente con otros filtros");
                                    mbTodero.info();
                                }
                            } else {
                                mbTodero.setMens("No se han ingresado filtros");
                                mbTodero.warn();

                            }

                        }

                        break;
                }
                break;

        }

    }

    public void limpiar() {

        //OpcionConsul = "";
        existeFactura = false;
        numFac = 0;
        numAva = 0;
        OpcDirec = false;
        OpcFechaFac = false;
        OpcMatricula = false;
        OpcNumAva = false;
        OpcNumFac = false;
        ListEntidad = null;
        ListProEntSeg = null;
        ListTipoAvaluo = null;
        SelectListProEntSeg = null;
        SelectListTipoAvaluo = null;
        mbAvaluo.setCodDeptoSol("");
        mbAvaluo.setCodCiudadSol("");
        RemitidoX = "";
        ListaConsulFac = null;
        Nit = "";
        nameEnt = "";
        NombreCliente = "";
        IdCli = "";
        FiltrarX = "";
        FiltrarXCarta = "";
        SelectListProEntSeg = null;
        ListClientes = null;
        genCart = false;
        viewPan = false;
        Cons.setCodFac("");
        Cons.setCodAvaluos("");
        Cons.setClientes("");
        Cons.setTipoFactura("");
        Cons.setEstadoAvaluo("");
        Cons.setTabla("");
        Cons.setValorAvaluo("");
        Cons.setNBien("");
        Cons.setUbicacion("");
        Cons.setProducto_Entidad("");
        Cons.setTipoProductoEntidad("");
        Cons.setTipoAvaluo("");
        Cons.setDirecc("");
        Cons.setMatricula("");
        Cons.setFechaAsigPerito("");
        Cons.setNombrePerito("");
        Cons.setFecha_Solicitud("");
        Cons.setFechaRadicadoSeg("");
        Cons.setFechaEntrega("");
        Cons.setFechaImpresion("");
        Cons.setClientesRemi("");
        Cons.setEmail("");
        Cons.setRegimen("");
        Cons.setValorAvaluo("");
        Cons.setValAdicFac("");
        Cons.setValAdiAva("");
        Cons.setValAdiFacSinIva("");
        Cons.setValAdiAvaSinIva("");
        Cons.setDescuentoFac("");
        Cons.setFacAntF("");
        Cons.setSubtotal("");
        Cons.setIva("");
        Cons.setReteFuente("");
        Cons.setReteIca("");
        Cons.setReteIva("");
        Cons.setValAntic("");
        Cons.setValorApagar("");

    }

    public void consulRemision(String tipo) throws SQLException {
        getNombreCliente();
        switch (tipo) {
            case "consultar":
                genCart = false;
                switch (FiltrarX) {
                    case "Avaluo":

                        ListClientes = Fac.ConsulClieRemi(String.valueOf(numAva), "Avaluo");
                        setSelecListClientes(null);
                        if (ListClientes.isEmpty()) {
                            mbTodero.setMens("No se ha encontrado el numero de avaluo");
                            mbTodero.info();
                        }
                        break;
                    case "Name":
                        ListClientes = Fac.ConsulClieRemi(NombreCliente, "Name");
                        setSelecListClientes(null);
                        if (ListClientes.isEmpty()) {
                            mbTodero.setMens("No se ha encontrado el nombre del filtro");
                            mbTodero.info();
                        }
                        break;
                    case "Id":
                        ListClientes = Fac.ConsulClieRemi(IdCli, "Id");
                        setSelecListClientes(null);

                        if (ListClientes.isEmpty()) {
                            mbTodero.setMens("No se ha encontrado el numero de identificación");
                            mbTodero.info();
                        }
                        break;
                    default:
                        mbTodero.setMens("Seleccione una opción");
                        mbTodero.info();
                        break;
                }
                break;

            case "preVisualizar":

                if (SelecListClientes != null && SelecListClientes.size() >= 1) {
                    panelOtro = false;
                    genCart = false;
                    setNumRemision(Factu.MaxNumRemision());
                    switch (FiltrarXCarta) {
                        case "Cli":
                            setNombreCliente(getSelecListClientes().get(0).getClientesRemi());
                            setEntSoli(getSelecListClientes().get(0).getEntidadSoli());
                            setDireccion((!getSelecListClientes().get(0).getDirecClie().isEmpty()) ? getSelecListClientes().get(0).getDirecClie() : getSelecListClientes().get(0).getDirecOfi());
                            setCodCiudadSol((!getSelecListClientes().get(0).getCiudadCli().isEmpty()) ? getSelecListClientes().get(0).getCiudadCli() : getSelecListClientes().get(0).getCiudadEnt());
                            genCart = true;
                            break;
                        case "Sol":
                            if (getSelecListClientes().get(0).getTip_Ase().equalsIgnoreCase("S")) {
                                setNombreCliente(getSelecListClientes().get(0).getAsignadoASeg());
                                setEntSoli(getSelecListClientes().get(0).getEntidadSoli());
                                setDireccion(getSelecListClientes().get(0).getDirecOfi());
                                setCodCiudadSol(getSelecListClientes().get(0).getCiudadEnt());
                                genCart = true;
                            } else if (getSelecListClientes().get(0).getTip_Clie().equalsIgnoreCase("S")) {
                                setNombreCliente(getSelecListClientes().get(0).getClientesRemi());
                                setEntSoli(getSelecListClientes().get(0).getEntidadSoli());
                                setDireccion(getSelecListClientes().get(0).getDirecClie());
                                setCodCiudadSol(getSelecListClientes().get(0).getCiudadCli());
                                genCart = true;
                            } else {
                                setFiltrarXCarta("Cli");
                                consulRemision("preVisualizar");

                            }

                            break;
                        case "Otro":
                            genCart = true;
                            panelOtro = true;
                            break;
                        default:
                            mbTodero.setMens("Seleccione la persona a la cual desea dirigir la carta");
                            mbTodero.info();
                            break;
                    }
                    String avl = "";
                    for (int i = 0; i < SelecListClientes.size(); i++) {
                        avl = avl + String.valueOf(SelecListClientes.get(i).getNAvaluoRad());
                        if (i + 1 < SelecListClientes.size()) {
                            avl = avl + ",";
                        }
                    }
                    MostrarRemision = Fac.PreviewListRemi(avl);

                } else {
                    mbTodero.setMens("Seleccione un registro de la tabla");
                    mbTodero.info();
                }
                break;
        }
    }

    /**
     * Metodo que permite generar la carta para un avaluador
     *
     * @author Jhojan Stiven Rodriguez
     * @author Maira Alejandra Carvajal
     * @throws java.sql.SQLException
     * @since 01-11-2014
     */
    public void generarRemision() throws SQLException {

        try {
            HashMap parametros = new HashMap<>();
            parametros.put("nombre", (NombreCliente.isEmpty()) ? EntSoli : NombreCliente);
            parametros.put("entSoli", (EntSoli.isEmpty()) ? direccion : (NombreCliente.isEmpty()) ? direccion : EntSoli);
            parametros.put("direccion", (direccion.isEmpty()) ? codCiudadSol : (EntSoli.isEmpty()) ? codCiudadSol : (NombreCliente.isEmpty()) ? codCiudadSol : direccion);
            parametros.put("ciudad", (!NombreCliente.isEmpty() && !EntSoli.isEmpty() && !direccion.isEmpty()) ? codCiudadSol : "");
            parametros.put("numRemi", String.valueOf(numRemision));

            List<String> ListnumAva = new ArrayList<>();

            for (int i = 0; i < SelecListClientes.size(); i++) {
                ListnumAva.add(String.valueOf(SelecListClientes.get(i).getNAvaluoRad()));
            }
            parametros.put("avaList", ListnumAva);

            String path = "/" + mBArchivos.GenerarRemision("Remision", "Remision " + getNumRemision(), parametros);

            Factu.InsertRemision("inserPath", numRemision, 0, path);

            for (int i = 0; i < SelecListClientes.size(); i++) {
                Factu.InsertRemision("detalle", numRemision, SelecListClientes.get(i).getNAvaluoRad(), "");
            }

            mbTodero.setMens("Remision " + getNumRemision() + " generada exitosamente");
            mbTodero.info();

        } catch (SQLException e) {
            System.err.println("ERROR: " + e);
        }
    }

}
