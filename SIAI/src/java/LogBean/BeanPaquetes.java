/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LogBean;

import Logic.LogAdministracion;
import Logic.LogAvaluo;
import Logic.LogPreRadicacion;
import Logic.LogRadicacion;
import Logic.LogUbicacion;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso y manejo de avaluos por paquetes </b></p>
 *
 * @author Jhojan Stiven Rodriguez
 * @author Maira Alejandra Carvajal
 * @version 2.0.0
 * @since 01-10-2014 1.0.0
 *
 */
@ManagedBean(name = "MBPaquetes")
@ViewScoped
// -- @SessionScoped
public class BeanPaquetes {

    public BeanPaquetes() {
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
    private BeanAvaluo mBAvaluo;

    public BeanAvaluo getmBAvaluo() {
        return mBAvaluo;
    }

    public void setmBAvaluo(BeanAvaluo mBAvaluo) {
        this.mBAvaluo = mBAvaluo;
    }

    /**
     * Variables Implicitas*
     */
    private int Numero;
    List<BeanPaquetes> ListaBienPaqPre = new ArrayList<>();
    List<BeanPaquetes> ListaBienPaqMaq = new ArrayList<>();
    List<BeanPaquetes> ListaBienPaqEns = new ArrayList<>();
    //Variables Logicas
    private LogAvaluo Ava = new LogAvaluo();
    private LogRadicacion Rad = new LogRadicacion();
    private LogPreRadicacion PreRad = new LogPreRadicacion();
    private LogUbicacion Ubic = new LogUbicacion();
    private LogAdministracion Adm = new LogAdministracion();

    /**
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @return
     */
    public int getNumero() {
        return Numero;
    }

    public void setNumero(int Numero) {
        this.Numero = Numero;
    }

    public List<BeanPaquetes> getListaBienPaqPre() {
        return ListaBienPaqPre;
    }

    public void setListaBienPaqPre(List<BeanPaquetes> ListaBienPaqPre) {
        this.ListaBienPaqPre = ListaBienPaqPre;
    }

    public List<BeanPaquetes> getListaBienPaqMaq() {
        return ListaBienPaqMaq;
    }

    public void setListaBienPaqMaq(List<BeanPaquetes> ListaBienPaqMaq) {
        this.ListaBienPaqMaq = ListaBienPaqMaq;
    }

    public List<BeanPaquetes> getListaBienPaqEns() {
        return ListaBienPaqEns;
    }

    public void setListaBienPaqEns(List<BeanPaquetes> ListaBienPaqEns) {
        this.ListaBienPaqEns = ListaBienPaqEns;
    }

    //GET Y SET DE LOGICAS
    public LogAvaluo getAva() {
        return Ava;
    }

    public void setAva(LogAvaluo Ava) {
        this.Ava = Ava;
    }

    public LogRadicacion getRad() {
        return Rad;
    }

    public void setRad(LogRadicacion Rad) {
        this.Rad = Rad;
    }

    public LogPreRadicacion getPreRad() {
        return PreRad;
    }

    public void setPreRad(LogPreRadicacion PreRad) {
        this.PreRad = PreRad;
    }

    public LogUbicacion getUbic() {
        return Ubic;
    }

    public void setUbic(LogUbicacion Ubic) {
        this.Ubic = Ubic;
    }

    public LogAdministracion getAdm() {
        return Adm;
    }

    public void setAdm(LogAdministracion Adm) {
        this.Adm = Adm;
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
     * Metodo para la seleccion de informacion en el tab de informacion
     * solicitante
     *
     * @author Maira Alejandra Carvajal
     * @param Tip int que contiene el tipo de persona de la que se agregara
     * información= 1: cliente 2: entidad
     * @since 01-05-2015
     */
    public void onSeleccion(int Tip) {
        switch (Tip) {
            case 1:
                if ("Entidad".equals(mBPreRadicacion.PreRad.getSolicitante())) {
                    mBEntidad.setOpcionMostrarEntidad("Si1");
                    mBEntidad.setEstPanelEntidad(true);
                    mBEntidad.setOpcionRadiosMostrarEntidad(false);

                    if (mBCliente.isEstadoPanelClienteGeneral() == true) {
                        mBCliente.setOpcionMostrarCliSolicitante("");
                        mBCliente.setEstadoPanelRadiosCli(true);
                    } else {
                        mBCliente.setOpcionMostrarCliSolicitante("");
                        mBCliente.setEstadoPanelRadiosCli(true);
                    }
                }
                if ("Cliente".equals(mBPreRadicacion.PreRad.getSolicitante())) {
                    mBCliente.setOpcionMostrarCliSolicitante("Si");
                    mBCliente.setEstadoPanelClienteGeneral(true);
                    mBCliente.setEstadoPanelRadiosCli(false);

                    if (mBEntidad.isEstPanelEntidad() == true) {
                        mBEntidad.setOpcionMostrarEntidad("");
                        mBEntidad.setOpcionRadiosMostrarEntidad(true);
                    } else {
                        mBEntidad.setOpcionMostrarEntidad("");
                        mBEntidad.setOpcionRadiosMostrarEntidad(true);
                    }
                }
                break;
        }
    }

    /**
     * Metodo para realizar la validacion de la informacion del bien que se esta
     * agregando dentro del paquete
     *
     * @author Maira Alejandra Carvajal
     * @param Tip int que contiene el tipo de avaluo que se va a agregar 1:
     * predio 2: maquinaria 3: enser
     * @since 01-05-2015
     */
    public void validacionTipoBien(int Tip) {
        BeanPaquetes MBPaq;
        String Validar = "";
        switch (Tip) {
            case 1://Para el Tipo de Predio
                if (Ava.getCodTipPredio() <= 0) {
                    mbTodero.setMens("Falta seleccionar un tipo de predio");
                    mbTodero.warn();
                } else if ("".equals(Ava.getMatricula())) {
                    mbTodero.setMens("Falta Ingresar la información de la matricula");
                    mbTodero.warn();
                } else if (("".equals(Ava.getBarrio()) && !"".equals(Ava.getBarrio_predio())) || (!"".equals(Ava.getBarrio()) && "".equals(Ava.getBarrio_predio()))) {
                    mbTodero.setMens("Verifique la información ingresada del Barrio ");
                    mbTodero.warn();
                } else if (!Ava.getBarrio_predio().equals(Ava.getBarrio())) {
                    mbTodero.setMens("La información del barrio no es consistente o igual, favor verifique ");
                    mbTodero.warn();
                } else if (("".equals(Ava.getDireccion()) && !"".equals(Ava.getDireccionTB())) || !"".equals(Ava.getDireccion()) && "".equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("Verifique la información ingresada en la Dirección ");
                    mbTodero.warn();
                } else if (!Ava.getDireccion().equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("La información de la dirección no es consistente o igual, favor verifique ");
                    mbTodero.warn();
                } else if (Ubic.getIdCiu() <= 0) {
                    mbTodero.setMens("Falta Información de la ubicación del Bien por Ingresar");
                    mbTodero.warn();
                } else {
                    Validar = "OK";
                }
                if ("OK".equals(Validar)) {
                    //Llenado de informacion de la lista de predios
                    if (ListaBienPaqPre.size() == 10) {
                        mbTodero.setMens("No se puede ingresar mas de diez (10) bienes dentro del registro");
                        mbTodero.warn();
                    } else {
                        if (ListaBienPaqPre.isEmpty()) {
                            for (int i = 0; i < 1; i++) {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(i);
                                MBPaq.Ava.setCodTipPredio(Ava.getCodTipPredio());
                                MBPaq.Ava.setNombreTipPredio(Adm.ConsulNombreEspecific(Ava.getCodTipPredio(), "Resultado_Parametro", "Parametro", "Cod_Parametro"));
                                MBPaq.Ava.setMatricula(Ava.getMatricula());
                                MBPaq.Ava.setBarrio(Ava.getBarrio());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqPre.add(MBPaq);
                            }
                        } else {
                            int Tamano = ListaBienPaqPre.size(), Paso = 0;
                            for (int i = 0; i < Tamano; i++) {
                                if (ListaBienPaqPre.get(i).Ava.getMatricula().equals(Ava.getMatricula())) {
                                    Paso = 1;
                                    break;
                                }
                            }
                            if (Paso == 1) {
                                mbTodero.setMens("Ya hay una informacion del bien con la misma matricula que se desea ingresar, por favor validar");
                                mbTodero.warn();
                            } else {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(Tamano);
                                MBPaq.Ava.setCodTipPredio(Ava.getCodTipPredio());
                                MBPaq.Ava.setNombreTipPredio(Adm.ConsulNombreEspecific(Ava.getCodTipPredio(), "Resultado_Parametro", "Parametro", "Cod_Parametro"));
                                MBPaq.Ava.setMatricula(Ava.getMatricula());
                                MBPaq.Ava.setBarrio(Ava.getBarrio());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqPre.add(MBPaq);
                            }
                        }
                    }
                    mbTodero.setMens("Bien Agregado Correctamente");
                    mbTodero.info();
                }
                break;
            //Para el Maquinaria
            case 2:
                if (Ava.getCodTipBien() <= 0) {
                    mbTodero.setMens("Seleccione un Tipo de Maquinaria");
                    mbTodero.warn();
                } else if ("".equals(Ava.getObservacionMaquinaria())) {
                    mbTodero.setMens("Falta detalle de la maquinaria");
                } else if (("".equals(Ava.getDireccion()) && !"".equals(Ava.getDireccionTB())) || !"".equals(Ava.getDireccion()) && "".equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("Verifique la información ingresada en la Dirección ");
                    mbTodero.warn();
                } else if (!Ava.getDireccion().equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("La información de la dirección no es consistente o igual, favor verifique ");
                    mbTodero.warn();
                } else if (Ubic.getIdCiu() <= 0) {
                    mbTodero.setMens("Falta Información de la ubicación del Bien por Ingresar");
                    mbTodero.warn();
                } else {
                    Validar = "OK";
                }
                //Para La validacion en ingresar informacion dentro de la lista de maquinaria
                if ("OK".equals(Validar)) {
                    //Llenado de informacion de la lista de Maquinaria
                    if (ListaBienPaqMaq.size() == 10) {
                        mbTodero.setMens("No se puede ingresar mas de diez (10) bienes dentro del registro");
                        mbTodero.warn();
                    } else {
                        if (ListaBienPaqMaq.isEmpty()) {
                            for (int i = 0; i < 1; i++) {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(i);
                                MBPaq.Ava.setCodTipBien(Ava.getCodTipBien());
                                MBPaq.Ava.setNombreTipPredio(Adm.ConsulNombreEspecific(Ava.getCodTipBien(), "Resultado_Parametro", "Parametro", "Cod_Parametro"));
                                MBPaq.Ava.setMarcaMaquinaria(Ava.getMarcaMaquinaria());
                                MBPaq.Ava.setSerieMaquinaria(Ava.getSerieMaquinaria());
                                MBPaq.Ava.setObservacionMaquinaria(Ava.getObservacionMaquinaria());
                                MBPaq.Ava.setModeloMaquinaria(Ava.getModeloMaquinaria());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqMaq.add(MBPaq);
                            }
                        } else {
                            int Tamano = ListaBienPaqMaq.size(), Paso = 0;
                            for (int i = 0; i < Tamano; i++) {
                                if (ListaBienPaqMaq.get(i).Ava.getObservacionMaquinaria().equals(Ava.getObservacionMaquinaria())) {
                                    Paso = 1;
                                    break;
                                }
                            }
                            if (Paso == 1) {
                                mbTodero.setMens("Ya hay una informacion del bien con la misma matricula que se desea ingresar, por favor validar");
                                mbTodero.warn();
                            } else {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(Tamano);
                                MBPaq.Ava.setCodTipPredio(Ava.getCodTipPredio());
                                MBPaq.Ava.setNombreTipPredio(Adm.ConsulNombreEspecific(Ava.getCodTipPredio(), "Resultado_Parametro", "Parametro", "Cod_Parametro"));
                                MBPaq.Ava.setMatricula(Ava.getMatricula());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqMaq.add(MBPaq);
                            }
                        }
                    }
                    mbTodero.setMens("Bien Agregado Correctamente");
                    mbTodero.info();
                }
                break;
            //Para el Tipo de Mueble
            case 3:
                if ("".equals(Ava.getObservacionMueble())) {
                    mbTodero.setMens("Falta ingresar  observacion del mueble");
                    mbTodero.warn();
                } else if (("".equals(Ava.getDireccion()) && !"".equals(Ava.getDireccionTB())) || !"".equals(Ava.getDireccion()) && "".equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("Verifique la información ingresada en la Dirección ");
                    mbTodero.warn();
                } else if (!Ava.getDireccion().equals(Ava.getDireccionTB())) {
                    mbTodero.setMens("La información de la dirección no es consistente o igual, favor verifique ");
                    mbTodero.warn();
                } else if (Ubic.getIdCiu() <= 0) {
                    mbTodero.setMens("Falta Información de la ubicación del Bien por Ingresar");
                    mbTodero.warn();
                } else {
                    Validar = "OK";
                }

                if ("OK".equals(Validar)) {
                    //Llenado de informacion de la lista de Maquinaria
                    if (ListaBienPaqEns.size() == 10) {
                        mbTodero.setMens("No se puede ingresar mas de diez (10) bienes dentro del registro");
                        mbTodero.warn();
                    } else {
                        if (ListaBienPaqEns.isEmpty()) {
                            for (int i = 0; i < 1; i++) {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(i);
                                MBPaq.Ava.setObservacionMueble(Ava.getObservacionMueble());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqEns.add(MBPaq);
                            }
                        } else {
                            int Tamano = ListaBienPaqEns.size(), Paso = 0;
                            for (int i = 0; i < Tamano; i++) {
                                if (ListaBienPaqEns.get(i).Ava.getObservacionMueble().equals(Ava.getObservacionMueble())) {
                                    Paso = 1;
                                    break;
                                }
                            }
                            if (Paso == 1) {
                                mbTodero.setMens("Ya hay una informacion del bien con la misma matricula que se desea ingresar, por favor validar");
                                mbTodero.warn();
                            } else {
                                MBPaq = new BeanPaquetes();
                                MBPaq.setNumero(Tamano);
                                MBPaq.Ava.setObservacionMueble(Ava.getObservacionMueble());
                                MBPaq.Ava.setDireccion(Ava.getDireccion());
                                MBPaq.Ubic.setIdCiu(Ubic.getIdCiu());
                                MBPaq.Ubic.setNomCiu(Ubic.ConsultCampoEspecific(Ubic.getIdCiu()));
                                ListaBienPaqEns.add(MBPaq);
                            }
                        }
                    }
                    mbTodero.setMens("Bien Agregado Correctamente");
                    mbTodero.info();
                }
                break;
        }
    }//FINALIZACION DEL METODO

    /**
     * FIN Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
}
