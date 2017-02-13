<%@ page import="java.io.*" %>
<HTML>
    <HEAD>
        <link href="<%= request.getContextPath()%>/estilo.css" rel="stylesheet" type="text/css">
        <%@page import="util.BaseDatos" %>
        <jsp:useBean id="u" type="util.BaseDatos" class="util.BaseDatos" scope="request"></jsp:useBean>
        <% u.abrirBd(); %>
        <%
           int factura = (int) Double.parseDouble(request.getParameter("factura"));
           int tipo = (int) Double.parseDouble(request.getParameter("tipo"));
           double reteICA = Double.parseDouble(request.getParameter("reteICA"));
        %>
        <SCRIPT LANGUAGE="JavaScript" SRC="<%= request.getContextPath()%>/jss/fecha.js" ></SCRIPT>
        <script type="text/javascript" language="JavaScript">
            var warray = new Array();
            // Esto es para el manejo de ventana de datos.
            var newWindow;
            var windowFeatures;
            var winTop = (screen.height / 2) - 125;
            var winLeft = (screen.width / 2) - 125;

            function ventanaContinua()
            {
                windowFeatures = "toolbar=NO,scrollbars=YES,width=400,height=500,location=NO,status=NO,menubar=NO,resizable=NO,";
                windowFeatures = windowFeatures + "left=" + winLeft + ",";
                windowFeatures = windowFeatures + "top=" + winTop;
                newWindow = window.open('correBat.jsp?&factura=<%=factura%>', "Ventana corre", windowFeatures);
                newWindow.focus();
                alert("Factura de Codigo de Barras Generada!");
                window.location = ("<%= request.getContextPath()%>/factura/menuFacturacion.jsp");
            }

            function window_onunload()
            {
                if ((typeof (newWindow) != "undefined") || (typeof (newWindow1) != "undefined"))
                {
                    if ((newWindow.closed == false) || (newWindow1.closed == false))
                    {
                        newWindow.close();
                        newWindow1.close();
                    }
                }
            }

        </script>
    </HEAD>
    <BODY>
        <FORM name="ListaPredios" method="post" action=''>
            <table width="600" border="1">
                <tr>
                    <td><div align="center"><h1>Creacion de Archivo Plano de Facturas  *</h1></div></td>
                </tr>
            </table>
            <br>
            <BR>

            <%!
            public String textoXML(String descripcion, String valor){
                String cadenaXML = "";
                cadenaXML = cadenaXML + "		<Detalle>" + "\n";
                cadenaXML = cadenaXML + "			<Servicio>" + "\n";
                cadenaXML = cadenaXML + "				<Codigo_Servicio/>" + "\n";
                cadenaXML = cadenaXML + "				<Nombre_Servicio/>" + "\n";
                cadenaXML = cadenaXML + "			</Servicio>" + "\n";
                cadenaXML = cadenaXML + "			<Descripcion>" + descripcion + "</Descripcion>" + "\n";
                cadenaXML = cadenaXML + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                cadenaXML = cadenaXML + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                cadenaXML = cadenaXML + "			<Valor>" + valor + "</Valor>" + "\n";
                cadenaXML = cadenaXML + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                cadenaXML = cadenaXML + "			<IVA>0</IVA>" + "\n";
                cadenaXML = cadenaXML + "			<Valor_Total>0</Valor_Total>" + "\n";
                cadenaXML = cadenaXML + "		</Detalle>" + "\n";
                return cadenaXML;
            }
            %>

            <%
                String avaNumero = "";
                String tipoAvaluo = "";
                String bienNumero = "";

                int nAvaluos = 0;
                String tipo_factura = "";
                String det_cobro_faltante = "";
                Double vr_cobro_faltante = 0.0;
                Double vr_temp = 0.0;

                String cadena = "";
                String cadenaPerito = "";
                String cadenaEntidad = "";
                String cadenaCliente = "";
                String cadenaAdicional = "";
                String documento = "";
                String nombre_cliente = "";
                String direccion_cliente = "";
                String ciudad_cliente = "";
                String telefono_cliente = "";
                String perito = "";
                String solicitadopor = "";
                String entidadSolicita = "";
                String entidadQueSolicito = "";
                String solicitante = "";  // Realmente este es el cliente, se coloco en solicitante por base de datos
                String descripFactura = "";
    
                Double valor = 0.0;
                Double valor_n = 0.0;
                Double subtotal = 0.0;
                Double iva = 0.0;
                Double total_factura = 0.0;
                Double reteiva = 0.0;
                Double retefuente = 0.0;
                Double reteica = 0.0;
                Double subtotal_factura = 0.0;
                Double total = 0.0;
                Double anticipo = 0.0;
                Double otro_vr = 0.0;
                String fecha_anticipo = "";
                Double descuento = 0.0;
                int tipo_vrDsto = 0;
                Double saldoapagar = 0.0;
    
                boolean muestraVrAdi = false;
                int vrAdi = 0;
    
                // Este bloque nos ayuda a determinar si existen valores adicionales incluidos en los valores de los avaluos.
                // el campo mostrar='1' nos dice que debe aparecer el valor adicionado al avaluo discriminado 
                // primero se determina si se muestra. en unas lineas mas abajo se guardara en una cadena los valores discriminados a imprimir
                // luego se restara al valor del avaluo los valores adicionados, se imprimira este valor y luego se imprimira la cadena de valores adicionados al avaluo.
                boolean existeAdicionAv=false;
                String cadenaAdicion="";
                Double tAdiAva=0.0;
                String consulta1="";
    
            //    System.out.println("factura->"+factura);
                // Nos ayuda a determinar, si la factura tiene varios avaluos asignados.
                u.consultaSql("select f.numero_factura, count(d.numero_factura) nAvaluos from facturas f, detalle_factura d where d.NUMERO_FACTURA = f.NUMERO_FACTURA and f.numero_factura = "+factura+" group by f.NUMERO_FACTURA;");
                if ( u.getNuevaReg() ) {
                    nAvaluos = (int) Double.parseDouble(u.getCampo("nAvaluos"));
                }
                // Detectamos que tipo de factura es:
                u.consultaSql("select f.NUMERO_FACTURA, " +
                              "       CASE WHEN (d.tipo = '2') THEN 'Cobro Faltante' " +
                              "       WHEN (d.tipo = '3') THEN 'Copia Avaluo' " +
                              "       WHEN (f.tipo = '2' ) THEN 'Anticipo' " +
                              "       WHEN (f.tipo = '3' ) THEN 'Otros Conceptos' " +
                              "       WHEN (f.tipo = '4' ) THEN 'Tiquetes Aereos' " +
                              "       WHEN (f.tipo = '5' ) THEN 'Gastos Avaluos' " +
                              "       ELSE 'Normal' END tipo_factura " +
                              "  from facturas f " +
                              "  left outer join detalle_factura d on d.NUMERO_FACTURA = f.NUMERO_FACTURA " +
                              " where f.NUMERO_FACTURA = " + factura +
                              " group by f.NUMERO_FACTURA; ");
                if ( u.getNuevaReg() ) {
                    tipo_factura = u.getCampo("tipo_factura");
                }
            /*    if ( tipo_factura.equals("Cobro Faltante") ) {
                    u.consultaSql("select f.numero_factura, f.fecha_factura, r.valores_factura " +
                                  "from facturas f, " +
                                  "( select numero_factura, sum(valor_factura) valores_factura " +
                                  "   from detalle_factura " +
                                  "   where numero in ( select numero from detalle_factura where numero_factura = " + factura + " ) " +
                                  "     and numero_factura <> " + factura +
                                  "   group by numero_factura ) r " +
                                  "where f.numero_factura = r.numero_factura; ");
                     if ( u.getNuevaReg() ) {
                        det_cobro_faltante = "MENOS VALOR FACTURADO EL " + u.getCampo("fecha_factura").substring(0,10) + " EN LA FACTURA " + u.getCampo("numero_factura");
                        vr_cobro_faltante = Double.parseDouble(u.getCampo("valores_factura").replace(".00", ""));
                     }
                } */
    
                u.consultaSql("Select CASE WHEN f.codigo_sucursal = 0  THEN c.regimen_clasificacion ELSE e.regimen_clasificacion END regimen " + 
                              "  FROM facturas f " +
                              "  left outer join clientes c on f.documento = c.CC_CLIENTE " +
                              "  left outer join entidad  e on f.DOCUMENTO = e.NIT_ENTIDAD " +
                              " where f.NUMERO_FACTURA = " + factura);
                if ( u.getNuevaReg() ) {
                   if ( u.getCampo("regimen") != null )  {
           
                u.consultaSql("select se.NUMERO,se.TIPO_AVALUO,se.BIEN_NUMERO,fa.NUMERO_FACTURA,fa.DOCUMENTO,convert(fa.FECHA_FACTURA,date) fecha_factura, " + 
                               "       convert(ADDDATE(fa.FECHA_FACTURA, INTERVAL 10 DAY),date) vencimiento, " +
                               "       convert(aa.fecha,date) fecha_anticipo, ifnull(aa.VALOR,0) anticipo, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN cl.nombre_cliente ELSE en.nombre_entidad END nombre_cliente, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN cl.DIRECCION ELSE concat(su.OFICINA,' ',su.direccion) END direccion_cliente, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN cl.TELEFONO  ELSE su.TELEFONO END telefono_cliente, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN concat(cic.DESC_CIUDAD,' / ',decl.DESC_DEP) ELSE concat(cis.DESC_CIUDAD,'/',des.DESC_DEP) END ciudad_cliente, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN cl.regimen_persona else en.regimen_persona END regimen_persona, " +
                               "       CASE WHEN fa.codigo_sucursal = 0  THEN cl.regimen_clasificacion else en.regimen_clasificacion END regimen_clasificacion, " +
                               "       concat('Avaluo ', se.NUMERO, ' realizado ', " +
                               "               CASE WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 1) THEN ' al Inmueble'  " +
                               "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 2) THEN ' al Levantamiento Topografico'  " +
                               "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 3) THEN ' al Mueble y Enser' " +
                               "                    ELSE 'a la maquinaria,' END, " +q
                               "               CASE WHEN (se.tipo_avaluo = 1) THEN concat(' ubicado en la direccion ', se.DIRECCION)  " +
                               "                    ELSE '' END, " +
                               "               CASE WHEN (se.tipo_avaluo = 1 and pr.BARRIO is not null) THEN concat(' ,barrio ', pr.BARRIO)  " +
                               "                    ELSE '' END, " +
                               "               CASE WHEN (se.tipo_avaluo = 3) THEN concat(ma.DETALLE,' ',ifnull(ma.inventario,''),' ',ifnull(ma.serie,'')) ELSE '' END, " +
                               "               CASE WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 3) THEN concat('. ',me.OBSERVACION) ELSE '' END, " +
                               "               ', en ',cise.DESC_CIUDAD,' / ',dese.DESC_DEP " +
                               "              ) desc_deta, concat(cise.DESC_CIUDAD,' / ',dese.DESC_DEP) municipio_predio, " +
                               "       concat('Solicitado por ',  " +
                               "               CASE WHEN fa.codigo_sucursal = 0  THEN cl.nombre_cliente ELSE en.nombre_entidad END, " +
                               "               CASE WHEN fa.codigo_sucursal = 0  THEN '' ELSE concat(' Oficina ',su.OFICINA ) END) solicitado_por_desc, " +
                               "       concat('(', pe.NOMBRE_PERITO, ')') nombre_perito, " +
                               "       ifnull(va.muestra,-1) muestraVrAdi, ifnull(va.valor,0) vrAdi, ifnull(va.observacion,'') descVrAdi, " +
                               "       ifnull(de.VALOR_FACTURA,0)-ifnull(va.VALOR,0) valor_factura_ava, " +
                               "       fa.VALOR_FACTURA valor_factura , fa.IVA_FACTURA iva_factura , fa.VALOR_FACTURA+fa.IVA_FACTURA total_factura, " +
                               "       ifnull(descuentos.TIPO_VALOR,'-1') tipo_dsto, descuentos.VALOR valor_dsto, " +
                               "       ifnull(ena.NOMBRE_ENTIDAD,'-') entidad_solicita, ifnull(sua.oficina,' ') oficina_solicita, ifnull(sua.direccion,' ') direccion_solicita, ifnull(spa.NOMBRE,' ') nombre_solicita, " +
                               "       CASE WHEN av.codigo_sucursal is null THEN  ' ' ELSE concat(cisua.DESC_CIUDAD,' / ',desua.DESC_DEP) END ciudad_solicita, " +
                               "       CASE WHEN sua.telefono is null THEN ' ' ELSE concat('Tels. ',sua.telefono) END telefono_solicita, ifnull(av.solicitante, ' ') solicitante " +
                               "FROM facturas fa " +
                               "left outer join detalle_factura  de on de.NUMERO_FACTURA = fa.NUMERO_FACTURA " +
                               "left outer join otros_vrs_ava_fac va on de.NUMERO_FACTURA = va.NUMERO_FACTURA AND de.NUMERO = va.NUMERO AND de.TIPO_AVALUO = va.TIPO_AVALUO AND de.BIEN_NUMERO = va.BIEN_NUMERO " +
                               "left outer join seguimiento      se on de.NUMERO = se.NUMERO AND de.TIPO_AVALUO = se.TIPO_AVALUO AND de.BIEN_NUMERO = se.BIEN_NUMERO " +
                               "left outer join (select a1.TIPO_AVALUO,a1.NUMERO,a1.BIEN_NUMERO,a1.CUENTA,a1.FECHA,sum(a1.VALOR) valor from abonos_avaluos a1 group by a1.NUMERO,a1.TIPO_AVALUO,a1.BIEN_NUMERO) " +
                               "                aa on aa.NUMERO = se.NUMERO AND aa.TIPO_AVALUO = se.TIPO_AVALUO AND aa.BIEN_NUMERO = se.BIEN_NUMERO " +
                               "left outer join ciudades         cise on se.CODIGO_DEP = cise.CODIGO_DEP AND se.CODIGO_CIUDAD = cise.CODIGO_CIUDAD " +
                               "left outer join departamento     dese on se.CODIGO_DEP = dese.CODIGO_DEP " +
                               "left outer join predios          pr on pr.NUMERO = se.NUMERO AND pr.TIPO_AVALUO = se.TIPO_AVALUO AND pr.BIEN_NUMERO = se.BIEN_NUMERO " +
                               "left outer join maquinaria       ma on ma.NUMERO = se.NUMERO AND ma.TIPO_AVALUO = se.TIPO_AVALUO AND ma.BIEN_NUMERO = se.BIEN_NUMERO " +
                               "left outer join muebles          me on me.NUMERO = se.NUMERO AND me.TIPO_AVALUO = se.TIPO_AVALUO AND me.BIEN_NUMERO = se.BIEN_NUMERO " +
                               "left outer join avaluos          av on se.TIPO_AVALUO = av.TIPO_AVALUO AND se.NUMERO = av.NUMERO " +
                               "left outer join tipo_avaluo      ta on ta.tipo_avaluo = av.tipo_avaluo " +
                               "left outer join sucursales       sua on av.NIT_ENTIDAD = sua.NIT_ENTIDAD AND av.CODIGO_SUCURSAL = sua.CODIGO_SUCURSAL " +
                               "left outer join ciudades         cisua on sua.CODIGO_DEP = cisua.CODIGO_DEP AND sua.CODIGO_CIUDAD = cisua.CODIGO_CIUDAD " +
                               "left outer join departamento     desua on sua.CODIGO_DEP = desua.CODIGO_DEP " +
                               "left outer join supervisores     spa on av.NIT_ENTIDAD = spa.NIT_ENTIDAD AND av.CODIGO_SUCURSAL = spa.CODIGO_SUCURSAL AND av.supervisor = spa.codigo_sup " +
                               "left outer join entidad          ena on sua.NIT_ENTIDAD = ena.NIT_ENTIDAD " +
                               "left outer join entidad          en on en.NIT_ENTIDAD = fa.DOCUMENTO " +
                               "left outer join sucursales       su on su.NIT_ENTIDAD = fa.DOCUMENTO AND su.CODIGO_SUCURSAL = fa.CODIGO_SUCURSAL " +
                               "left outer join ciudades        cis on su.CODIGO_DEP = cis.CODIGO_DEP AND su.CODIGO_CIUDAD = cis.CODIGO_CIUDAD " +
                               "left outer join departamento    des on su.CODIGO_DEP = des.CODIGO_DEP " +
                               "left outer join supervisores     sp on sp.NIT_ENTIDAD = av.NIT_ENTIDAD AND sp.CODIGO_SUCURSAL = av.CODIGO_SUCURSAL AND sp.codigo_sup = av.SUPERVISOR " +
                               "left outer join clientes         cl on cl.CC_CLIENTE = fa.DOCUMENTO " +
                               "left outer join ciudades        cic on cl.CODIGO_DEP = cic.CODIGO_DEP AND cl.CODIGO_CIUDAD = cic.CODIGO_CIUDAD " +
                               "left outer join departamento    decl on cl.CODIGO_DEP = decl.CODIGO_DEP " +
                               "left outer join peritos          pe on se.CC_PERITOS = pe.CC_PERITOS " +
                               "left outer join descuentos       on descuentos.NUMERO_FACTURA = fa.NUMERO_FACTURA " +
                               "where fa.NUMERO_FACTURA = " + factura + " order by pe.NOMBRE_PERITO, se.NUMERO, ena.NIT_ENTIDAD ");

                    if ( u.getNuevaReg() ) {
                        documento = u.getCampo("documento");
                        nombre_cliente = u.getCampo("nombre_cliente");
                        direccion_cliente = u.getCampo("direccion_cliente");
                        telefono_cliente = u.getCampo("telefono_cliente");
                        ciudad_cliente = u.getCampo("ciudad_cliente");
            
            
                        if ( tipo_factura.equals("Cobro Faltante") || tipo_factura.equals("Normal") ) {
                            if ( u.getCampo("regimen_persona").equals("1") ) {
                                if ( u.getCampo("regimen_clasificacion").equals("1") ) {
                                    reteica = 0.0;
                                    retefuente = 0.0;
                                    reteiva = 0.0;
                                } else {
            //                        Es cambio se hace para que persona natural, regimen comun no se le apliquen retenciones
            //                        retefuente = Math.rint(Double.parseDouble(u.getCampo("valor_factura").replace(".00", "")) * 11 / 100) ;
                                    reteica = 0.0;
                                    retefuente = 0.0;
                                    reteiva = 0.0;
                                }
                            } else {
                                if ( u.getCampo("regimen_clasificacion").equals("1") ) {
                                    reteica = 0.0;
                                    retefuente = Math.rint(Double.parseDouble(u.getCampo("valor_factura").replace(".00", "")) * 11 / 100) ;
                                    reteiva = 0.0;
                                } else {
                                    retefuente = Math.rint(Double.parseDouble(u.getCampo("valor_factura").replace(".00", "")) * 11 / 100) ;
                                    if ( u.getCampo("municipio_predio").indexOf("BOGOTA") == 0 ) {
                                        reteica = Math.rint(Double.parseDouble(u.getCampo("valor_factura").replace(".00", "")) * 9.66 / 1000) ;
                                    } else {
                                        reteica = 0.0;
                                    }
            //                        reteiva = Math.rint(Double.parseDouble(u.getCampo("iva_factura").replace(".00", "")) / 2.0) ; cambio por la nueva ley tributaria 2013
                                    reteiva = Math.rint(Double.parseDouble(u.getCampo("iva_factura").replace(".00", "")) * 15 / 100) ;
                                }
                            }
                        }
            
                        if ( !tipo_factura.equals("Cobro Faltante") && !tipo_factura.equals("Copia Avaluo") ) {
                           anticipo = Double.parseDouble(u.getCampo("anticipo").replace(".00", ""));
                           fecha_anticipo = u.getCampo("fecha_anticipo");
                        }
            
                        if ( u.getCampo("entidad_solicita").length() > 2 ) {
                            entidadSolicita = "Entidad Solicitante " + u.getCampo("entidad_solicita") + " " + u.getCampo("oficina_solicita") + " " + u.getCampo("direccion_solicita") + " " + u.getCampo("ciudad_solicita") + " " + u.getCampo("nombre_solicita") + " " + u.getCampo("telefono_solicita");
                        }
            
                        if ( reteICA != 0 ) {
                            reteica = reteICA;
                        }
            
            
                        iva = Double.parseDouble(u.getCampo("iva_factura").replace(".00", ""));
                        total = Double.parseDouble(u.getCampo("total_factura").replace(".00", "")) - reteiva - retefuente - reteica;                       
                        total_factura = Double.parseDouble(u.getCampo("total_factura").replace(".00", ""));
                        subtotal_factura = Double.parseDouble(u.getCampo("valor_factura").replace(".00", ""));
            
                        tipo_vrDsto = Integer.parseInt(u.getCampo("tipo_dsto"));
            //            System.out.println("Tipo Descuento ->"+tipo_vrDsto);
                        if ( tipo_vrDsto != -1) {
                            descuento = Double.parseDouble(u.getCampo("valor_dsto").replace(".00", ""));
            //                System.out.println("Valor Descuento ->"+descuento);
                        }

                        cadena = cadena + "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
                        cadena = cadena + "<!--Sample XML file generated by XMLSpy v2010 (http://www.altova.com)-->" + "\n";
                        cadena = cadena + "<Factura_Servicio xsi:noNamespaceSchemaLocation=\"C:\\Elemental\\ISAInmobiliaria\\xml\\Schema\\Factura_Servicio.xsd\"  xmlns:pd=\"http://skylab/paradocs/sys/paradocs.xsd\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\">" + "\n";
                        cadena = cadena + "	<pd:DocInfo>" + "\n";
                        cadena = cadena + "		<pd:DOCID></pd:DOCID>" + "\n";
                        if ( tipo == 1 ) {
                            cadena = cadena + "		<pd:DocSysState>IsaAvaluo.fo.xslt</pd:DocSysState>" + "\n";
                            cadena = cadena + "		<pd:DocLastView>IsaAvaluo.fo.xslt</pd:DocLastView>" + "\n";
                        } else {
                            cadena = cadena + "		<pd:DocFamily>Avaluo Nuevo</pd:DocFamily>" + "\n";
                            cadena = cadena + "             <pd:DocSysState>NORMAL</pd:DocSysState>" + "\n";
                            cadena = cadena + "             <pd:DocPrintingView>IsaAvaluoNuevo.fo.xslt</pd:DocPrintingView>" + "\n";
                            cadena = cadena + "             <pd:DocLastView>IsaAvaluoNuevo.fo.xslt</pd:DocLastView>" + "\n";
                        }
                        cadena = cadena + "	</pd:DocInfo>" + "\n";
                        cadena = cadena + "	<Encabezado>" + "\n";
                        cadena = cadena + "		<Emite>" + "\n";
                        cadena = cadena + "			<Codigo_Tercero>0</Codigo_Tercero>" + "\n";
                        cadena = cadena + "			<Nombre_Tercero/>" + "\n";
                        cadena = cadena + "			<Telefono/>" + "\n";
                        cadena = cadena + "			<Fax/>" + "\n";
                        cadena = cadena + "			<email/>" + "\n";
                        cadena = cadena + "			<Direccion/>" + "\n";
                        cadena = cadena + "			<Ciudad/>" + "\n";
                        cadena = cadena + "			<Pais/>" + "\n";
                        cadena = cadena + "			<Identificacion/>" + "\n";
                        cadena = cadena + "			<gln/>" + "\n";
                        cadena = cadena + "		</Emite>" + "\n";
                        cadena = cadena + "		<Recibe>" + "\n";
                        cadena = cadena + "			<Codigo_Tercero>" + documento + "</Codigo_Tercero>" + "\n";
                        cadena = cadena + "			<Nombre_Tercero>" + nombre_cliente.replace("&", "?") + "</Nombre_Tercero>" + "\n";
                        cadena = cadena + "			<Telefono>" + telefono_cliente + "</Telefono>" + "\n";
                        cadena = cadena + "			<Fax/>" + "\n";
                        cadena = cadena + "			<email/>" + "\n";
                        cadena = cadena + "			<Direccion>" + direccion_cliente + "</Direccion>" + "\n";
                        cadena = cadena + "			<Ciudad>" + ciudad_cliente + "</Ciudad>" + "\n";
                        cadena = cadena + "			<Pais/>" + "\n";
                        cadena = cadena + "			<Identificacion/>" + "\n";
                        cadena = cadena + "		</Recibe>" + "\n";
                        cadena = cadena + "		<Fecha>" + u.getCampo("fecha_factura") + "</Fecha>" + "\n";
                        cadena = cadena + "		<Numero>" + factura + "</Numero>" + "\n";
                        cadena = cadena + "		<Vencimiento>" + u.getCampo("vencimiento") + "</Vencimiento>" + "\n";
                        cadena = cadena + "		<Comentario/>" + "\n";
                        cadena = cadena + "		<Prefijo>AV</Prefijo>" + "\n";
                        cadena = cadena + "		<Logo>logo2.jpg</Logo>" + "\n";
            //            if ( tipo == 1 )
                             cadena = cadena + "<DetalleObservacion>RECUERDE QUE TAMBIEN PUEDE PAGAR SU FACTURA CON TARJETA DEBITO O CREDITO EN NUESTRA OFICINA EN BOGOTA O EN EL PORTAL DE PAGOS ELECTRONICOS DEL BANCO DE BOGOTA.</DetalleObservacion>" + "\n";
            //            else
            //                 cadena = cadena + "<DetalleObservacion>RECUERDE QUE TAMBIEN PUEDE PAGAR SU FACTURA CON TARJETA DEBITO O CREDITO EN NUESTRA OFICINA EN BOGOTA.</DetalleObservacion>" + "\n";                
                        cadena = cadena + "	</Encabezado>" + "\n";
            
                        avaNumero = u.getCampo("numero");
                        tipoAvaluo = u.getCampo("tipo_avaluo");
                        bienNumero = u.getCampo("bien_numero");
            
                        System.out.println("el Avaluo a Rev->"+avaNumero+"-"+tipoAvaluo+"-"+bienNumero);
            
                        perito = u.getCampo("nombre_perito");
                        entidadQueSolicito = u.getCampo("entidad_solicita");
                        solicitadopor = u.getCampo("solicitado_por_desc");
                        solicitante = u.getCampo("solicitante");
                        cadena = cadena + "	<Detalles>" + "\n";
            
                        if ( tipo_factura.equals("Normal") || tipo_factura.equals("Cobro Faltante") || tipo_factura.equals("Copia Avaluo") ) {
                
                            if ( nAvaluos < 5 ) {
                
                                valor = Double.parseDouble(u.getCampo("valor_factura_ava").replace(".00", ""));

                                do {
                                    if ( !perito.equals(u.getCampo("nombre_perito")) ) {       // Si se cambia de Perito
                                        if ( cadenaPerito != "" ) {
                                            cadena = cadena + cadenaPerito;                    // Imprimiendo Perito
                                        }
                                        perito = u.getCampo("nombre_perito");
                                        cadenaPerito = textoXML(perito, u.formatiando("############", 0+"") );
                                    }
                        
                                    if ( !entidadQueSolicito.equals(u.getCampo("entidad_solicita")) ) {    // Si se cambia de Entidad
                                        if ( cadenaEntidad != "" ) {
                                            cadena = cadena + cadenaEntidad;                   // Imprimiendo Entidad
                                        }
                                        if ( entidadSolicita.length() > 0 ) {
                                            cadenaEntidad = textoXML(entidadSolicita.replace("&", "?"), u.formatiando("############", 0+"") );
                                        }
                                        entidadQueSolicito = u.getCampo("entidad_solicita");
                                        if ( u.getCampo("entidad_solicita").length() > 2 ) {
                                            entidadSolicita = "Entidad Solicitante " + u.getCampo("entidad_solicita") + " " + u.getCampo("oficina_solicita") + " " + u.getCampo("direccion_solicita") + " " + u.getCampo("ciudad_solicita") + " " + u.getCampo("nombre_solicita") + " " + u.getCampo("telefono_solicita");
                                        }
                                    }
                        
                                    if ( !solicitante.equals(u.getCampo("solicitante")) ) {      // Si s cambia de solicitante
                                        if ( cadenaCliente != "" ) {
                                            cadena = cadena + cadenaCliente;                     // Imprime Cliente
                                        }
                                        if ( !u.getCampo("solicitante").equals(u.getCampo("entidad_solicita")) && !u.getCampo("solicitante").equals(u.getCampo("nombre_cliente")) ) {
                                            if ( u.getCampo("solicitante").length() > 0 ) {
                                               cadenaCliente = textoXML(u.getCampo("solicitante").replace("&", "?"), u.formatiando("############", 0+"") );
                                            } else {
                                               cadenaCliente = "";
                                            }
                                        }
                                        solicitante = u.getCampo("solicitante");
                                    }

                                    System.out.println( "LLego a la pregunta 222->" + u.getCampo("numero") );
                        
                                    valor = Double.parseDouble(u.getCampo("valor_factura_ava").replace(".00", ""));
                                    tAdiAva = Double.parseDouble(u.getCampo("vrAdi").replace(".00", "")); 
                                    System.out.println( "Luego ->" + valor + "**" + tAdiAva + "**" + u.getCampo("muestraVrAdi") );
                                    System.out.println("llego a es dif -1");
                                    if ( !(u.getCampo("muestraVrAdi").equals("-1") ) )  {
                                       System.out.println("paso por si es dif -1");
                                       if ( (u.getCampo("muestraVrAdi").equals("0") ) )  {
                                           valor = valor + tAdiAva;
                                           subtotal = subtotal + valor;
                                       } 
                                       if ( (u.getCampo("muestraVrAdi").equals("1") ) )  {
                                           cadenaAdicional = textoXML(u.getCampo("descVrAdi"), u.formatiando("############", tAdiAva+"") );
                                           subtotal = subtotal + valor + tAdiAva;
                                       } 
                                       if ( (u.getCampo("muestraVrAdi").equals("2") ) )  {
                                           System.out.println("llego a muestraAvaAdic->"+u.getCampo("descVrAdi")+ "valor-"+valor+"Adicionar->"+tAdiAva);
                                           cadenaAdicional = textoXML(u.getCampo("descVrAdi"), u.formatiando("############", 0+"") );
                                           valor = valor + tAdiAva;
                                           subtotal = subtotal + valor + tAdiAva;
                                       } 
                                    }
                                    System.out.println("Tiene->"+cadenaAdicional);

                                    descripFactura = u.getCampo("desc_deta");

                                    cadena = cadena + "		<Detalle>" + "\n";
                                    cadena = cadena + "			<Servicio>" + "\n";
                                    cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                    cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                    cadena = cadena + "			</Servicio>" + "\n";
                                    cadena = cadena + "			<Descripcion>" + u.getCampo("desc_deta") + "</Descripcion>" + "\n";
                                    cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                                    cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                                    if ( !tipo_factura.equals("Cobro Faltante") ) {
                                        if ( tipo_factura.equals("Copia Avaluo") ) { 
                                            cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                        } else {
                                            System.out.println("paso con111->"+valor.toString()+"valorAdicional->"+vrAdi);
                                            if ( muestraVrAdi ) {
                                               cadena = cadena + "			<Valor>" + u.formatiando("############", valor.toString()) + "</Valor>" + "\n"; 
                                               valor = valor + vrAdi;
                                            } else {
                                               valor = valor + vrAdi;
                                               cadena = cadena + "			<Valor>" + u.formatiando("############", valor.toString()) + "</Valor>" + "\n"; 
                                            }
                                            subtotal = subtotal + valor;
                                        }
                                    } else {
                                        vr_temp = Double.parseDouble(u.getCampo("valor_factura").replace(".00", "")) + ( tipo_vrDsto == 0 ? ( subtotal * descuento / 100 ) : (descuento) );
            //                            System.out.println("Valor Temporal ->"+vr_temp);
                                        cadena = cadena + "			<Valor>" + u.formatiando("############", vr_temp.toString()) + "</Valor>" + "\n"; 
                                        subtotal = subtotal + Double.parseDouble(u.getCampo("valor_factura").replace(".00", ""));
                                    }
                                    cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                    cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                    cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                    cadena = cadena + "		</Detalle>" + "\n";
                        
                                    if ( !cadenaAdicional.equals("") ) {
                                        cadena = cadena + cadenaAdicional;
                                        tAdiAva = 0.0;
                                        cadenaAdicion = "";
                                    }

                                } while ( u.getNuevaReg() );

                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + perito + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";
                    
                                if ( entidadSolicita.length() > 0 ) {
                                    cadena = cadena + "		<Detalle>" + "\n";
                                    cadena = cadena + "			<Servicio>" + "\n";
                                    cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                    cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                    cadena = cadena + "			</Servicio>" + "\n";
                                    cadena = cadena + "			<Descripcion>( "+ entidadSolicita +" )</Descripcion>" + "\n";
                                    cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                    cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                    cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                    cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                    cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                    cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                    cadena = cadena + "		</Detalle>" + "\n";
                                }

                                if ( !solicitante.equals(entidadQueSolicito) && !solicitante.equals(nombre_cliente) ) {
                                    cadena = cadena + "		<Detalle>" + "\n";
                                    cadena = cadena + "			<Servicio>" + "\n";
                                    cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                    cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                    cadena = cadena + "			</Servicio>" + "\n";
                                    cadena = cadena + "			<Descripcion>" + "Cliente " + solicitante + "</Descripcion>" + "\n";
                                    cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                    cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                    cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                    cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                    cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                    cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                    cadena = cadena + "		</Detalle>" + "\n";
                                }
                            } else {
                    
                                valor_n = Double.parseDouble(u.getCampo("valor_factura").replace(".00", ""));
            //                    System.out.println("este el primer valor_n->"+valor_n);
                                valor_n = ( tipo_vrDsto == 0 ? ( valor_n / (1-descuento/100) ) : (valor_n + descuento) );
            //                    System.out.println("este el segundo valor_n->"+valor_n+"--->"+tipo_vrDsto);
                    
                                descripFactura = "HONORARIOS DE AVALUOS " + nAvaluos;
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + "HONORARIOS POR LA REALIZACION DE " + nAvaluos + " AVALUOS" + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
            //                    vr_temp = valor_n + ( tipo_vrDsto == 0 ? ( valor_n * descuento / 100 ) : (descuento) );
                                cadena = cadena + "			<Valor>" + u.formatiando("############", valor_n.toString()) + "</Valor>" + "\n";
            //                    cadena = cadena + "			<Valor>" + u.formatiando("############",valor.toString()) + "</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";
                                subtotal = subtotal + valor;
                            }
                    
                            if ( tipo_factura.equals("Cobro Faltante") ) {
            /*
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + det_cobro_faltante + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>" + u.formatiando("############", vr_cobro_faltante.toString()) + "</Valor>" + "\n"; 
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n"; 
            */
                                descripFactura = "EXCEDENTE " + descripFactura; 
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + "EXCEDENTE" + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";
                            }
                
                            if ( tipo_factura.equals("Copia Avaluo") ) {
                                descripFactura = "COPIA " + descripFactura; 
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + "(COPIA)" + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>" + u.formatiando("############",subtotal_factura.toString())  + "</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";
                                subtotal = subtotal + subtotal_factura;
                            }
                
                            u.consultaSql("select * from obser_factura_cb where numero_factura = " + factura );
                
                            while ( u.getNuevaReg() ) {
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + u.getCampo("obser_factura") + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";

                                subtotal = subtotal + vr_temp;
                                vr_temp = 0.0;
                            }
            
                        } else { 
                            valor = Double.parseDouble(u.getCampo("valor_factura").replace(".00", ""));
                            vr_temp = valor;
                
                            u.consultaSql("select * from obser_factura_cb where numero_factura = " + factura );
                
                            while ( u.getNuevaReg() ) {
                                if ( descripFactura.equals("") ) {
                                    descripFactura = u.getCampo("obser_factura");
                                }
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>" + u.getCampo("obser_factura") + "</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                                if ( vr_temp > 0.0 ) {
                                    cadena = cadena + "			<Valor>" + u.formatiando("############",vr_temp.toString()) + "</Valor>" + "\n"; 
                                } else {
                                    cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                }
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";

                                subtotal = subtotal + vr_temp;
                                vr_temp = 0.0;
                            }
           
                        }
            
            //            System.out.println("--->"+subtotal);

                        if ( descuento != 0 ) {
                            cadena = cadena + "		<Detalle>" + "\n";
                            cadena = cadena + "			<Servicio>" + "\n";
                            cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                            cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                            cadena = cadena + "			</Servicio>" + "\n";
                            cadena = cadena + "			<Descripcion>" + ( tipo_vrDsto == 0 ? "Descuento del "+descuento+"%" : "Descuento" ) + "</Descripcion>" + "\n";
                            cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                            cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                            if ( nAvaluos < 5 ) {
                               cadena = cadena + "			<Valor>" + ( tipo_vrDsto == 0 ? (u.formatiando("############",( subtotal * descuento / 100 ))) : u.formatiando("############",descuento) ) + "</Valor>" + "\n";
                            } else {
                               vr_temp = ( tipo_vrDsto == 0 ? ( valor_n * descuento / 100 ) : (descuento) );
            //                   cadena = cadena + "			<Valor>" + ( tipo_vrDsto == 0 ? (u.formatiando("############",( vr_temp * descuento / 100 ))) : u.formatiando("############",descuento) ) + "</Valor>" + "\n";                   
                                 cadena = cadena + "			<Valor>" + u.formatiando("############",vr_temp.toString()) + "</Valor>" + "\n";
                            }
                            cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                            cadena = cadena + "			<IVA>0</IVA>" + "\n";
                            cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                            cadena = cadena + "		</Detalle>" + "\n";
            //                subtotal = subtotal - ( tipo_vrDsto == 0 ? ( Math.rint(subtotal * descuento / 100) ) : descuento );
                        }
            
                        u.consultaSql("select * from otros_vrs where numero_factura = " + factura );
                        while ( u.getNuevaReg() ) {
                            otro_vr = Double.parseDouble(u.getCampo("valor").replace(".00", ""));
                            cadena = cadena + "		<Detalle>" + "\n";
                            cadena = cadena + "			<Servicio>" + "\n";
                            cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                            cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                            cadena = cadena + "			</Servicio>" + "\n";
                            cadena = cadena + "			<Descripcion>" + u.getCampo("OBSERVACION") + "</Descripcion>" + "\n";
                            cadena = cadena + "			<Fecha_Desde>2011-01-01</Fecha_Desde>" + "\n";
                            cadena = cadena + "			<Fecha_Hasta>2011-01-01</Fecha_Hasta>" + "\n";
                            if ( otro_vr > 0.0 ) {
                                cadena = cadena + "			<Valor>" + u.formatiando("############",otro_vr.toString()) + "</Valor>" + "\n"; 
                            } else {
                                cadena = cadena + "			<Valor>0</Valor>" + "\n";
                            }
                            cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                            cadena = cadena + "			<IVA>0</IVA>" + "\n";
                            cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                            cadena = cadena + "		</Detalle>" + "\n";
                        }
            

                        saldoapagar = ((total - anticipo) < 0 ? 0 : (total - anticipo));
            
            //            System.out.println("saldoapagar->"+saldoapagar+"total->"+total+"anticipo->"+anticipo+"descuento->"+descuento);
            
                        String letras = "";
            //            System.out.println("Esto es el total ->" + total); 
                        String[] texto = u.letrasNumeros(total);
                        for ( int k = 0 ; k < texto.length; k++ )   { 
                             letras = letras + texto[k];
                        }
            
                        if ( letras.indexOf("MILLON") == 0  && total.toString().indexOf("1") == 0) {
                            letras = "UN " + letras;
                        }

                        if ( anticipo > 0 && !tipo_factura.equals("Cobro Faltante") && !tipo_factura.equals("Copia Avaluo") ) {
                            cadena = cadena + "		<Detalle>" + "\n";
                            cadena = cadena + "			<Servicio>" + "\n";
                            cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                            cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                            cadena = cadena + "			</Servicio>" + "\n";
                            cadena = cadena + "			<Descripcion> </Descripcion>" + "\n";
                            cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                            cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                            cadena = cadena + "			<Valor>0</Valor>" + "\n";
                            cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                            cadena = cadena + "			<IVA>0</IVA>" + "\n";
                            cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                            cadena = cadena + "		</Detalle>" + "\n";
                
                            cadena = cadena + "		<Detalle>" + "\n";
                            cadena = cadena + "			<Servicio>" + "\n";
                            cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                            cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                            cadena = cadena + "			</Servicio>" + "\n";
                            cadena = cadena + "			<Descripcion>( Fecha Anticipo: "+ fecha_anticipo +" )</Descripcion>" + "\n";
                            cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                            cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                            cadena = cadena + "			<Valor>0</Valor>" + "\n";
                            cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                            cadena = cadena + "			<IVA>0</IVA>" + "\n";
                            cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                            cadena = cadena + "		</Detalle>" + "\n";
                
                            if ( (total - anticipo) < 0 ) {
                                cadena = cadena + "		<Detalle>" + "\n";
                                cadena = cadena + "			<Servicio>" + "\n";
                                cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                                cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                                cadena = cadena + "			</Servicio>" + "\n";
                                cadena = cadena + "			<Descripcion>Mayor valor cancelado de "+ u.formatiando("############",(anticipo-total)) +"</Descripcion>" + "\n";
                                cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                                cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                                cadena = cadena + "			<Valor>0</Valor>" + "\n";
                                cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                                cadena = cadena + "			<IVA>0</IVA>" + "\n";
                                cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                                cadena = cadena + "		</Detalle>" + "\n";                    
                            }

                        }
          
             /*                                 
                        cadena = cadena + "		<Detalle>" + "\n";
                        cadena = cadena + "			<Servicio>" + "\n";
                        cadena = cadena + "				<Codigo_Servicio/>" + "\n";
                        cadena = cadena + "				<Nombre_Servicio/>" + "\n";
                        cadena = cadena + "			</Servicio>" + "\n";
                        cadena = cadena + "			<Descripcion>" + solicitadopor + "</Descripcion>" + "\n";
                        cadena = cadena + "			<Fecha_Desde>2010-01-01</Fecha_Desde>" + "\n";
                        cadena = cadena + "			<Fecha_Hasta>2010-01-01</Fecha_Hasta>" + "\n";
                        cadena = cadena + "			<Valor>0</Valor>" + "\n";
                        cadena = cadena + "			<PorcentajeIVA>0</PorcentajeIVA>" + "\n";
                        cadena = cadena + "			<IVA>0</IVA>" + "\n";
                        cadena = cadena + "			<Valor_Total>0</Valor_Total>" + "\n";
                        cadena = cadena + "		</Detalle>" + "\n";     
            */            
            
                        cadena = cadena + "	</Detalles>	" + "\n";

                        cadena = cadena + "	<Mensajes>" + "\n";
                        cadena = cadena + "		<Resolucion>RESOLUCION FACTURA DIAN No. 320001052406 DEL 29/08/2013. PREFIJO AV, DEL No.70001 AL 100000 AUTORIZA</Resolucion>" + "\n";
                        cadena = cadena + "		<Mensaje1>ESTA FACTURA CONSTITUYE TITULO VALOR DE CONFORMIDAD CON LO PREVISTO EN EL ARTICULO 621 DE CODIGO DE COMERCIO Y LA LEY 1231 DEL 17 DE JULIO DE 2008</Mensaje1>" + "\n";
                        cadena = cadena + "		<Mensaje2>SI ES AGENTE RETENEDOR DE ICA Y EL SERVICIO ES PRESTADO EN BOGOTA, APLICAR TARIFA DEL 9.66 X 1.000. ACTIVIDAD ECONOMICA ICA 6810</Mensaje2>" + "\n";
                        cadena = cadena + "		<Mensaje3>TARIFA DE RETENCION EN LA FUENTE DE RENTA POR CONCEPTO DE HONORARIOS 11%</Mensaje3>" + "\n";
                        cadena = cadena + "		<Mensaje4></Mensaje4>" + "\n";
                        cadena = cadena + "	</Mensajes>" + "\n";

                        cadena = cadena + "	<Bancos>" + "\n";
                        cadena = cadena + "		<Banco>" + "\n";
                        cadena = cadena + "			<Nombre>BANCO DE BOGOTA BANCO POPULAR BANCO OCCIDENTE </Nombre>" + "\n";
                        cadena = cadena + "			<cuenta>Cta.Cte.Nro.040-21363-9  Cta.Cte.Nro.062-17319-0 Cta.Cte.Nro.230-05957-8</cuenta>" + "\n";
                        cadena = cadena + "		</Banco>" + "\n";
           
                        cadena = cadena + "	</Bancos>" + "\n";
            
                        cadena = cadena + "	<Totales>" + "\n";
                        cadena = cadena + "		<Subtotal>" +  u.formatiando("############",subtotal_factura.toString()) + "</Subtotal>" + "\n";
                        cadena = cadena + "		<Total_IVA>" +  u.formatiando("############",iva.toString()) + "</Total_IVA>" + "\n";
                        cadena = cadena + "		<Total_Neto>" +  u.formatiando("############",total_factura.toString()) + "</Total_Neto>" + "\n";
                        cadena = cadena + "		<Retencion_Renta>" + u.formatiando("############",retefuente.toString())  + "</Retencion_Renta>" + "\n";
                        cadena = cadena + "		<Retencion_IVA>" +  u.formatiando("############",reteiva.toString()) + "</Retencion_IVA>" + "\n";
                        cadena = cadena + "		<Retencion_ICA>" +  u.formatiando("############",reteica.toString()) + "</Retencion_ICA>" + "\n";
                        cadena = cadena + "		<Total_Pagar>" +  u.formatiando("############",total.toString()) + "</Total_Pagar>" + "\n";
                        cadena = cadena + "		<Total_Letras>" + letras.trim() + ".</Total_Letras>" + "\n";
                        cadena = cadena + "		<Descuentos>" +  u.formatiando("############",descuento.toString()) + "</Descuentos>" + "\n";
                        cadena = cadena + "		<Anticipo>"+  u.formatiando("############",anticipo.toString()) + "</Anticipo>" + "\n";
                        cadena = cadena + "		<Saldo>" +  u.formatiando("############",saldoapagar.toString()) + "</Saldo>" + "\n";
                        cadena = cadena + "	</Totales>" + "\n";
                        cadena = cadena + "</Factura_Servicio>" + "\n";

            //            u.grabaValoresFacCB(factura, subtotal, iva, reteiva, retefuente, reteica, total, anticipo, saldoapagar);
            //            System.out.println(subtotal.toString()+ " -- " + iva.toString()+ " -- " + reteiva.toString()+ " -- " + retefuente.toString()+ " -- " + reteica.toString()+ " -- " + total.toString()+ " -- " + anticipo.toString()+ " -- " + saldoapagar.toString());
                        u.grabaValoresFacCB(factura, subtotal_factura.intValue() , iva.intValue(), reteiva.intValue(), retefuente.intValue(), reteica.intValue(), total.intValue(), anticipo.intValue(), saldoapagar.intValue(), descripFactura );
                    }


            /*   String cadena1 = "";
                String cuentaT = "130505              ";
                String cuentaS = "41559505            ";
                String cuentaI = "24080105            ";
                String cadena2T = "";
                String cadena2S = "";
                String cadena2I = "";
                String cadena2X = "";
                String direccion = "Avaluos ";
                String factura = "";
                String avaluos = "";
                String total_factura = "";
                String subtotal_factura = "";
                String iva_factura = "";
                String documento = "";
                String nombre = "";
                String codigo = "";
                double temp=0;
                int tipo_factura = 0;
                double vr_anticipo = 0;
                long temp1=0;
                int navaluos = 0;


            "select CASE WHEN ( ( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha) ) ) THEN '0' " +
                              "            WHEN (a.tipo is null and b.tipo is null) THEN '1' " +
                              "            WHEN (a.tipo is null and b.tipo = 2 ) THEN '2' " +
                              "            WHEN (a.tipo is null and b.tipo = 3 ) THEN '3' " +
                              "            WHEN (a.tipo = 2    and a.numero_factura >= "+ factura_ini + " and a.numero_factura <=  " + factura_fin + " ) THEN '4' " +
                              "            WHEN (a.tipo = 2    and a.numero_factura < "+ factura_ini + " or a.numero_factura >  " + factura_fin + " ) THEN '5' " +
                              "            WHEN (a.tipo = 3 ) THEN '6' " +
                              "            WHEN (a.tipo = 4 ) THEN '7' " +
                              "            WHEN (a.tipo = 5 ) THEN '8' " +
                              "            ELSE '-1' END tipo_factura, " +
                              "       a.NUMERO_FACTURA, ifnull(g.numero_factura,a.numero_factura) numero_fact,    DATE_FORMAT(a.fecha_factura,'%d/%m/%Y') fecha_factura , " +
                              "       cast(concat(space(15-LENGTH(if(( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha)  ),'0.00',a.VALOR_FACTURA))),if( ( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha) ),'0.00',a.VALOR_FACTURA)) as char) valor_factura, " +
                              "       cast(concat(space(15-LENGTH(if(( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha)  ),'0.00',a.iva_FACTURA))),if(( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha) ),'0.00',a.iva_FACTURA)) as char) IVA_FACTURA, " +
                              "       cast(concat(space(15-LENGTH(if(( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha)  ),'0.00',a.VALOR_FACTURA+a.iva_factura))),if(( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha) ),'0.00',a.VALOR_FACTURA+a.iva_factura)) as char) total_factura, " +
                              "       CASE WHEN (locate('-',a.documento) = 0) THEN if( a.documento = '0','   830008001',LPAD( abs(a.documento),12,' ')) " +
                              "                  ELSE LPAD(  TRIM(LEADING '0' FROM substr(a.documento,1,locate('-',a.documento)-1) )  ,12,' ') END doc, " +
                              "       a.CODIGO_SUCURSAL, ifnull(b.NUMERO,'') numero, b.TIPO_AVALUO, b.BIEN_NUMERO, " +
                              "       CASE WHEN (( a.estado = 0 &&  month(a.fecha_factura) = month(h.fecha) )) THEN RPAD('ANULADO',60,' ') " +
                              "                  ELSE if(b.numero is not null,SUBSTRING(concat('Av.',b.NUMERO,' ',c.DIRECCION,REPEAT(' ',60)),1,60),REPEAT(' ',60)) end direccion, " +
                              "       RPAD(CASE WHEN (a.documento = '0') THEN 'INMOBILIARIA ISA. LTAD' " +
                              "                  ELSE CASE WHEN (a.codigo_sucursal != 0) THEN e.nombre_entidad ELSE d.nombre_cliente END " +
                              "                  END,90,' ') nombre_documento, " +
                              "       RPAD(ifnull(if(a.codigo_sucursal = 0,d.codigo,f.codigo),' '),15,' ') codigo " +
                              "  from facturas a " +
                              "       left join detalle_factura b   ON a.numero_factura = b.numero_factura " +
                              "       left join seguimiento c       ON b.numero = c.numero AND b.tipo_avaluo = c.tipo_avaluo AND b.bien_numero = c.bien_numero " +
                              "       left outer join clientes      d ON a.documento  = d.cc_cliente " +
                              "       left outer join entidad       e ON a.documento  = e.nit_entidad " +
                              "       left outer join sucursales    f ON a.documento  = f.nit_entidad AND a.codigo_sucursal = f.codigo_sucursal " +
                              "       left outer join facturas_asociadas  g ON g.factura_asociada = a.numero_factura " +
                              "       left outer join cancelada_factura   h ON a.numero_factura = h.numero_factura " +
                              " where a.fecha_factura is not null " +
                              "   and ((a.numero_factura >= "+ factura_ini + " and a.numero_factura <= " + factura_fin + " ) or (g.numero_factura >= " + factura_ini + " and g.numero_factura <= " + factura_fin + " )) " +
                              "order by 3 ");

                if ( u.getNuevaReg() ) {
                   factura = u.getCampo("numero_fact");
                   cadena1 = "FV  " + u.formatiando("00000000",Integer.parseInt(u.getCampo("numero_fact"))) + u.getCampo("fecha_factura") ;
                   direccion = u.getCampo("direccion");
                   total_factura = u.getCampo("total_factura");
                   subtotal_factura = u.getCampo("valor_factura");
                   iva_factura = u.getCampo("iva_factura");
                   documento = u.getCampo("doc");
                   nombre = u.getCampo("nombre_documento");
                   codigo = u.getCampo("codigo");
                   tipo_factura = (int) Integer.parseInt(u.getCampo("tipo_factura"));
                   avaluos = "AVALUO " + u.getCampo("numero");
                   if ( tipo_factura == 5 ) 
                      vr_anticipo = (double) Double.parseDouble(u.getCampo("total_factura"));
                   while ( u.getNuevaReg() ) {
                      if ( factura.equals(u.getCampo("numero_fact")) ) {
                         avaluos = avaluos + ((navaluos == 0 && tipo_factura == 5) ? "" : ",") + u.getCampo("numero");
                         navaluos++;
                         if ( tipo_factura == 5 ) {
                            total_factura = u.getCampo("total_factura");
                            subtotal_factura = u.getCampo("valor_factura");
                            iva_factura = u.getCampo("iva_factura");
                         }
                      } else {
                          nombre = nombre + "                                                                                         ";
                          nombre = nombre.substring(0,92);
                          codigo = codigo + "               ";
                          codigo = codigo.substring(0,15);
                          switch (tipo_factura) {
                              case 0: 
                              case 1:
                              case 3: 
                                      if ( tipo_factura == 0 )
                                         direccion = "ANULADA"; 
                                      if ( tipo_factura == 3 )
                                         direccion = "COPIA " + avaluos ; 
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + subtotal_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena2I= "1000               " + iva_factura + "C" + documento + nombre + codigo + "\n";
                                      if ( navaluos > 0 )
                                         direccion = avaluos;
                                      direccion = direccion + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena = cadena + cadena1 + cuentaT + direccion + cadena2T;
                                      cadena = cadena + cadena1 + cuentaS + direccion + cadena2S;
                                      cadena = cadena + cadena1 + cuentaI + direccion + cadena2I;
                                      break;
                              case 2: direccion = "EXEDENTE "+ avaluos + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "41559505            " + direccion + cadena2S;
                                      break;
                              case 4: direccion = "ANTICIPO " + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "280510              " + direccion + cadena2S;
                                      break;
                              case 5: direccion = "LEGALIZACION ANTICIPO " + avaluos + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      temp = (double) Double.parseDouble(subtotal_factura) + vr_anticipo;
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + "    " + u.formatiando("############", (int) temp  ) +".00" + "C" + documento + nombre + codigo + "\n";
                                      cadena2I= "1000               " + iva_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena2X= "1000               " + "    " + u.formatiando("############", (int) vr_anticipo  ) +".00" + "D" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "415595              " + direccion + cadena2S;
                                      cadena = cadena + cadena1 + "240801              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "280510              " + direccion + cadena2X;
                                      break;
                              case 6: direccion = "OTROS CONCEPTOS" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505055            " + direccion + cadena2S;
                                      break;
                              case 7: direccion = "TIQUETES" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505015            " + direccion + cadena2S;
                                      break;
                              case 8: direccion = "GASTOS AVALUOS" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505020            " + direccion + cadena2S;
                                      break;

                          }
                          vr_anticipo = 0;
                          factura = u.getCampo("numero_fact");
                           cadena1 = "FV  " + u.formatiando("00000000",Integer.parseInt(u.getCampo("numero_fact"))) + u.getCampo("fecha_factura") ;
                          direccion = u.getCampo("direccion");
                          total_factura = u.getCampo("total_factura");
                          subtotal_factura = u.getCampo("valor_factura");
                          iva_factura = u.getCampo("iva_factura");
                          documento = u.getCampo("doc");
                          nombre = u.getCampo("nombre_documento");
                          codigo = u.getCampo("codigo");
                          tipo_factura = (int) Integer.parseInt(u.getCampo("tipo_factura"));
                          navaluos = 0;
                          avaluos = "AVALUOS " + u.getCampo("numero");
                          if ( tipo_factura == 5 ) {
                             vr_anticipo = (double)  Double.parseDouble(u.getCampo("total_factura"));
                          }
                          temp=0;
              


                      } 

                   }
                    if ( !cadena.equals("") ) {
                          nombre = nombre + "                                                                                         ";
                          nombre = nombre.substring(0,92);
                          codigo = codigo + "               ";
                          codigo = codigo.substring(0,15);
                          switch (tipo_factura) {
                              case 0: 
                              case 1:
                              case 3: 
                                      if ( tipo_factura == 0 )
                                         direccion = "ANULADA"; 
                                      if ( tipo_factura == 3 )
                                         direccion = "COPIA AVALUO" + avaluos ; 
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + subtotal_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena2I= "1000               " + iva_factura + "C" + documento + nombre + codigo + "\n";
                                      direccion = direccion + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena = cadena + cadena1 + cuentaT + direccion + cadena2T;
                                      cadena = cadena + cadena1 + cuentaS + direccion + cadena2S;
                                      cadena = cadena + cadena1 + cuentaI + direccion + cadena2I;
                                      break;
                              case 2: direccion = "EXEDENTE "+ avaluos + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "415595              " + direccion + cadena2S;
                                      break;
                              case 4: direccion = "ANTICIPO" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "280510              " + direccion + cadena2S;
                                      break;
                              case 5: direccion = "LEGALIZACION ANTICIPO" + avaluos + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      temp = (double) Double.parseDouble(subtotal_factura) + vr_anticipo;
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + "    " + u.formatiando("############", (int) temp  ) +".00" + "C" + documento + nombre + codigo + "\n";
                                      cadena2I= "1000               " + iva_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena2X= "1000               " + "    " + u.formatiando("############", (int) vr_anticipo  ) +".00" + "D" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "415595              " + direccion + cadena2S;
                                      cadena = cadena + cadena1 + "240801              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "280510              " + direccion + cadena2X;
                                      break;
                              case 6: direccion = "OTROS CONCEPTOS" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505055            " + direccion + cadena2S;
                                      break;
                              case 7: direccion = "TIQUETES" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505015            " + direccion + cadena2S;
                                      break;
                              case 8: direccion = "GASTOS AVALUOS" + "                                                            "; 
                                      direccion = direccion.substring(0,60);
                                      cadena2T= "1000               " + total_factura + "D" + documento + nombre + codigo + "\n";
                                      cadena2S= "1000               " + total_factura + "C" + documento + nombre + codigo + "\n";
                                      cadena = cadena + cadena1 + "130505              " + direccion + cadena2T;
                                      cadena = cadena + cadena1 + "42505020            " + direccion + cadena2S;
                                      break;

                          }
            */              
            // Inicio del Plano  ************************************************************************************************
              




                           
            // Fin de Plano  ****************************************************************************************************              
              
             


            // System.out.println(cadena);
             
              

                        byte [] barray = cadena.getBytes();
                        String camino = application.getRealPath("/")+"planos/facturasXML/" +factura+".xml";
            //            System.out.println ("Comienza generacion de plano:");
            //            String caminoL = "c:\\Elemental\\ISAInmobiliaria\\AV\\" +factura+".xml";
                        FileOutputStream fo = new FileOutputStream(camino);
            //            FileOutputStream fL = new FileOutputStream(caminoL);

                        for (int i=0; i < barray.length; i++ ) {
                            fo.write(barray[i]);
            //                fL.write(barray[i]);
                        }
                        fo.close();
            //        }


            //    }





            /* La sentencia SQL, seria
            select a.NUMERO_FACTURA, DATE_FORMAT(a.fecha_factura,'%d/%m/%Y') fecha_factura , a.VALOR_FACTURA, a.IVA_FACTURA, a.DOCUMENTO, a.CODIGO_SUCURSAL, a.ESTADO,
                   a.TIPO, b.NUMERO, b.TIPO_AVALUO, b.BIEN_NUMERO, b.TIPO , SUBSTRING(concat('Av.',b.NUMERO,' ',c.DIRECCION,REPEAT(' ',60)),1,60) direccion,
                   CASE WHEN (a.documento = '0') THEN 'SIN INFORMACION'
                             ELSE CASE WHEN (a.codigo_sucursal != 0) THEN e.nombre_entidad ELSE d.nombre_cliente END
                             END nombre_documento
            from facturas a
                  left join detalle_factura b   ON a.numero_factura = b.numero_factura
                  left join seguimiento c       ON b.numero = c.numero AND b.tipo_avaluo = c.tipo_avaluo AND b.bien_numero = c.bien_numero
                  left outer join clientes      d ON a.documento  = d.cc_cliente
                  left outer join entidad       e ON a.documento  = e.nit_entidad
                  left outer join sucursales    f ON a.documento  = f.nit_entidad AND a.codigo_sucursal = f.codigo_sucursal
            where a.numero_factura > 19739;


            -- Factura normal
               130505 Db Total Fact.
               41559505 Cr Subtotal
               24080105 Cr Iva

            -- Factura copia
               == Factura Normal con detalle "COPIA AVALUO"

            -- Tiquetes Aereos
               42505015 Cr
               130505   Db

            -- Gastos Avaluos
               42505020 Cr
               130505 DB

            -- Anticipos 
               28051005 Cr
               130505 Db
   
               Legalizacion del Anticipo
               130595   Db (+iva)
               280505   Db (SubT)
               415595   Cr (Sub*2)?
               240801   Cr (iva)

            -- Excedente
               2805105 Cr
               130505 Db


               Centro de Costos 1000, 1 posicion a la izquierda
   
               Para las facturas Anuladas, el concepto "ANULADA", y los valores 0 

            */


            %>
            <BR>
            <%


               if ( !cadena.equals("") ) { 
            /*       
                    try{
                        Process pr;
                        String comando = "cmd /c c:\\Elemental\\ISAInmobiliaria\\config\\cargar.bat r:\\"+ factura+".xml"; 
            //            String comando = "cmd /c c:\\Elemental\\ISAInmobiliaria\\config\\cargar.bat " + cadena; 
       
                        System.out.println ("Comienzo de llamada: ->"+comando);
                        pr = Runtime.getRuntime().exec(comando);
                        System.out.println ("Termino la llamada!!!!!");
                    }catch(Exception ex){
            //            System.out.println("Ha ocurrido un error al ejecutar el comando. Error: "+ex);
                    }
              */     
            %>
            <INPUT type="hidden" name="factura" value="<%= factura %>">
            <INPUT type="hidden" name="cliente" value="<%= documento + " - " + nombre_cliente %>">
            <INPUT type="hidden" name="valor" value="<%= total_factura %>">

            <TABLE border='0' width='500'>
                <TR>
                    <TD width='300' align='center'><STRONG> Plano Generado de la Factura : </STRONG></TD>
                    <TD width='100'> <%= factura %> </TD>
                </TR>
            </table>
            <TABLE border='0' width='550'>
                <TR>
                    <TD width='100'>Cliente : </TD>
                    <TD width='400'> <%= documento + " - " + nombre_cliente %> </TD>
                </TR>
                <TR>
                    <TD>total_factura : </TD>
                    <TD><%= u.formatiando("###,###,###,###",total_factura.toString())%> </TD>
                </TR>
            </table>
            <br></br>

            <!--
            
            <TABLE border='0' width='500'>
            <TR>
            < !--   <TD><input type="button" onclick="ventanaContinua();" value="Generar Factura" /></TD>  -- >
               <TD><a href="< %= request.getContextPath()%>/factura/menuFacturacion.jsp"> Volver al Menu </a></TD>
            </TR>
            
            </table>
            -->

            <%  
               } else {
            %>
            <TABLE border='0' width='400'>
                <TR>
                    <TD width='100' align='center'></TD>
                    <TD width='200'> No existe informacion para generar el archivo plano</TD>
                </TR>
            </table>

            <%

                   }
                } else {


            %>

            <TABLE border='0' width='400'>
                <TR>
                    <TD width='100' align='center'></TD>
                    <TD width='200'> No existe informacion del regimen tributario de la persona a facturar</TD>
                </TR>
            </table>
            <%
                }
            %>

            <BR>
            <BR>
            <TABLE border="0" width="350">
                <TR>
                    <TD width="179"></TD>
                    <TD><a href="<%= request.getContextPath()%>/factura/menuFacturacion.jsp"> Volver al Menu </a></TD>
                </TR>
            </TABLE>
            <% } else {
            %>
            <TABLE border='0' width='400'>
                <TR>
                    <TD width='100' align='center'></TD>
                    <TD width='200'> No existe informacion del regimen tributario de la persona a facturar</TD>
                </TR>
            </table>
            <% } %>
        </form>

        <% u.cerrarBd(); %>

    </BODY></HTML>