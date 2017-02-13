package LogBean;

import Logic.LogConsulta;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

/**
 * <p>
 * <b>Managed bean / clase que se utiliza para crear todos los atributos y
 * metodos necesarios para el proceso y manejo de las consultas estadisticas de
 * cada uno de los modulos </b></p>
 *
 * @author Maira Alejandra Carvalal
 * @version 2.0.0
 * @since 01-03-2015 1.0.0
 *
 *
 */
@ManagedBean(name = "MBConsulta")
@ViewScoped
@SessionScoped
public class BeanConsulta {

    public BeanConsulta() {
    }

    /*Variables de las Logicas (NegociosISA > Logic)
     **/
    //Variables 
    LogConsulta Cons = new LogConsulta();
    /*Variables para PreRadicacion**/
    List<String> LisProEntPr = new ArrayList<>();
    List<String> LisEstadoPr = new ArrayList<>();

    /**
     * Variables para Radicacion*
     */
    /**
     * Variables para Entrega *
     */
    /**
     * Variables para Revision *
     */
    /**
     * GET Y SETS *
     */
    /**
     * VARIABLES DE LOS BEANS *
     */
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
     * Metodos get y set de todas las variables / atributos, listas, etc que se
     * utilizaran para enviar y traer datos a las respectivas variables
     * -----------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
    /**
     *
     * @return
     */
    public List<String> getLisProEntPr() {
        return LisProEntPr;
    }

    public void setLisProEntPr(List<String> LisProEntPr) {
        this.LisProEntPr = LisProEntPr;
    }

    public List<String> getLisEstadoPr() {
        return LisEstadoPr;
    }

    public void setLisEstadoPr(List<String> LisEstadoPr) {
        this.LisEstadoPr = LisEstadoPr;
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
     * METODOS PARA PRERADICACION*
     */
    public void validaInfoPreRadica() {
    }

    /**
     * METODOS PARA RADICACION *
     */
    /**
     * METODOS PARA ENTREGA *
     */
    /**
     * METODOS PARA REVISION *
     */
    /**
     * FIN Metodos de funcionalidad de la clase
     * -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     */
}
