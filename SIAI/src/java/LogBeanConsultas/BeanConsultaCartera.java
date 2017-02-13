/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBeanConsultas;

import LogBean.BeanArchivos;
import LogBean.BeanAvaluo;
import LogBean.BeanCartera;
import LogBean.BeanTodero;
import Logic.LogC_Consulta;
import Logic.LogCartera;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Desarrollo
 */
@ManagedBean(name = "MBConsultaCartera")
@ViewScoped
@SessionScoped
public class BeanConsultaCartera {

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

    /**
     * Variable tipo BeanTodero para traer los atributos y metodos de el
     * ManagedBean BeanAvaluo.java
     *
     *
     * @see BeanTodero.java
     */
    @ManagedProperty("#{MBCartera}")
    private BeanCartera mbCartera;

    public BeanCartera getMbCartera() {
        return mbCartera;
    }

    public void setMbCartera(BeanCartera mbCartera) {
        this.mbCartera = mbCartera;
    }

    //Variables de Consulta Cartera
    private String OpcionFiltroEstado;
    private String opcionConsul;
    //Variables para formulario de Consulta Cartera 
    private List<LogCartera> listaEstadoCartera = null;
    private boolean OpcFechaFac;
    private int numAva = 0;
    private Date FechaFacIni;
    //Devolver estado de la factura
    private List<LogCartera> selectCmbioEstado = null;
    private List<LogCartera> TempEstados = new ArrayList<>();
    private String estadoPnlCart = "";
    SimpleDateFormat Format = new SimpleDateFormat("yyyy-MM-dd");
    public String getEstadoPnlCart() {
        return estadoPnlCart;
    }

    public void setEstadoPnlCart(String estadoPnlCart) {
        this.estadoPnlCart = estadoPnlCart;
    }

    public List<LogCartera> getTempEstados() {
        return TempEstados;
    }

    public void setTempEstados(List<LogCartera> TempEstados) {
        this.TempEstados = TempEstados;
    }

    public List<LogCartera> getSelectCmbioEstado() {
        return selectCmbioEstado;
    }

    public void setSelectCmbioEstado(List<LogCartera> selectCmbioEstado) {
        this.selectCmbioEstado = selectCmbioEstado;
    }

    public Date getFechaFacIni() {
        return FechaFacIni;
    }

    public void setFechaFacIni(Date FechaFacIni) {
        this.FechaFacIni = FechaFacIni;
    }

    public boolean isOpcFechaFac() {
        return OpcFechaFac;
    }

    public void setOpcFechaFac(boolean OpcFechaFac) {
        this.OpcFechaFac = OpcFechaFac;
    }

    public int getNumAva() {
        return numAva;
    }

    public void setNumAva(int numAva) {
        this.numAva = numAva;
    }

    public String getOpcionFiltroEstado() {
        return OpcionFiltroEstado;
    }

    public void setOpcionFiltroEstado(String OpcionFiltroEstado) {
        this.OpcionFiltroEstado = OpcionFiltroEstado;
    }

    public String getOpcionConsul() {
        return opcionConsul;
    }

    public void setOpcionConsul(String opcionConsul) {
        this.opcionConsul = opcionConsul;
    }

    public List<LogCartera> getListaEstadoCartera() {
        return listaEstadoCartera;
    }

    public void setListaEstadoCartera(List<LogCartera> listaEstadoCartera) {
        this.listaEstadoCartera = listaEstadoCartera;
    }

    //Tatiana
    //Menu Consulta Cartera
    /**
     * Metodos de validacion de radios y visibilidad de informacion dependiendo
     * del estado seleccionado. sea Cobro Juridico o Castiga Cartera
     *
     * @author Ayda Tatiana Lopez Moreno
     */
    String Var = "";

    public void filtroConsulCarte() {
        try {
            LogCartera EstadoCart = new LogCartera();
            switch (getOpcionConsul()) {
                case "E":
                    break;
                case "Gen":
                    String ItemFecConsig = "NO";
                    int Paso = 0;

                    if (getOpcionFiltroEstado().equalsIgnoreCase("Cast_Carte")) {
                        Var = "C";
                    } else if (getOpcionFiltroEstado().equalsIgnoreCase("Cobro_Jurid")) {
                        Var = "CJ";
                    }

                    if (OpcFechaFac == true) {
                        //Para la validacion de la fecha de Radicacion
                        if (FechaFacIni == null) {
                            mbTodero.setMens("Falta llenar información de rangos en fechas de facturación");
                            mbTodero.warn();
                            Paso++;
                        } else {
                            ItemFecConsig = "OK";
                        }
                    }
                    if (Paso == 0) {
                        if (!"".equals(Var)) {
                            setListaEstadoCartera(EstadoCart.ConslEstadoCartera(Var));
                        }
                        if (ItemFecConsig.equals("OK")) {
                            Logic.LogC_Consulta C_Cartera = new LogC_Consulta();
                            C_Cartera.Porestracto("where  convert(p.Fecha_Consignacion,date)= '"+getFechaFacIni()+"' ");
                        } else {

                        }
                    }

                    mbTodero.setMens("La busqueda se ha realizado con exito");
                    mbTodero.info();
                    break;
            }
        } catch (Exception e) {
            mbTodero.setMens("Error en el metodo '" + this.getClass() + ".filtroConsulCarte()' causado por: " + e.getMessage());
            mbTodero.error();
        }
    }

    public void DevolverCart(String Opc) {
        switch (Opc) {
            case "Devolver_Fact":
                try {
                    //Cambiar el estado de las facturas seleccionadas  , para que lleguen de nuevo a pendientes por Cobrar
                    if (getSelectCmbioEstado().size() > 0 && getSelectCmbioEstado() != null) {
                        mbCartera.cambiarEstados(3, getSelectCmbioEstado().size());
                    } else {
                        mbTodero.setMens("Debe seleccionar un Avaluo");
                        mbTodero.info();
                    }
                } catch (Exception e) {
                    mbTodero.setMens("Error al ejecutar el metodo de Devolver Factura" + e.getMessage());
                    mbTodero.warn();
                }
                break;
        }

    }

}
