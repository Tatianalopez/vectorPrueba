<%@ page import="java.io.*" %>
<HTML>
<HEAD>
<link href="<%= request.getContextPath()%>/estilo.css" rel="stylesheet" type="text/css">
<%@page import="util.BaseDatos" %>
<jsp:useBean id="u" type="util.BaseDatos" class="util.BaseDatos" scope="request"></jsp:useBean>
<% u.abrirBd(); %>
<%
//   int factura_ini = (int) Double.parseDouble(request.getParameter("factura_ini"));
//   int factura_fin = (int) Double.parseDouble(request.getParameter("factura_fin"));

String fCon = (String)request.getParameter("anora")+"-"+
              u.formatiando("00",Integer.parseInt(request.getParameter("mesra")))+"-"+
              u.formatiando("00",Integer.parseInt(request.getParameter("diara")));

String fFin = (String)request.getParameter("anorf")+"-"+
              u.formatiando("00",Integer.parseInt(request.getParameter("mesrf")))+"-"+
              u.formatiando("00",Integer.parseInt(request.getParameter("diarf")));
%>
<SCRIPT LANGUAGE="JavaScript" SRC="<%= request.getContextPath()%>/jss/fecha.js" ></SCRIPT>

</HEAD>
<BODY>
<FORM name="ListaPredios" method="post" action=''>
<table width="600" border="1">
    <tr>
      <td><div align="center"><h1>Creacion de Archivo Plano de Facturas</h1></div></td>
    </tr>
  </table>
  <br>
  <BR>
  
  <%!

public String remove1(String input) {
    String output = input;
    for (int i=0; i<input.length(); i++) {
        if(!((input.charAt(i) > 64 && input.charAt(i) < 91) || (input.charAt(i) > 96 && input.charAt(i) < 123) || (input.charAt(i) > 31 && input.charAt(i) < 59) )) {
            output = output.replace(input.charAt(i), '?');
        }
    }
    return output;
}//remove1


     
  %>

<%
    // fecha de ajuste 10/Nov/2011. 
    
    System.out.println("Llego a crearPlano.jsp");

    String cadena = "";
    String cadena1 = "";
    String cadena2 = "";
    String cuentaSubTc = "41559505";   // Cuenta subtotal cuando lleva iva
    String cuentaSubTs = "41559510";   // Cuenta subtotal cuando NO lleba iva
    String cuentaI     = "24080501";   // Cuenta para iva
    String cuentaRFH   = "13551503";   // Cuenta retefuente del 11% Honorarios
    String cuentaRFS   = "13551504";   // Cuenta retefuente del 4% Servicios
    String cuentaRI    = "13551701";   // Cuenta de reteiva del 50% o 75%
    String cuentaRIcd  = "13551805";   // Cuenta de reteica defecto de 9.66 o de 11.04 o de 6.99
    String cuentaRIco  = "13551810";   // Cuenta de reteica otro porcentajes diferentes a los de arriba
    String cuentaCC    = "130505  ";   // Cuenta de Cuenta por cobrar
    String cuentaAnt   = "28050505";   // Cuenta de anticipo
    String cuentaRein  = "42505005";   // Cuenta de reintegro sin iva y no lleva retenciones
    
    String cuentaCreeD = "13551905";
    String cuentaCreeC = "236680  ";
    
    String detalle     = "";
    String detalleCert = "";
    String centroCosto = "210     ";
    String TD = "";
    
    double tReteiva = 0;
    double tRetefuente = 0;
    double tReteica = 0;
    
    String fechaHora = "";
    
    String sFactura = "";
    int nFactura;
    String fecha_factura = "";
    String identificacion = "";
    String estado = "";
    int nTotal_factura;
    
    String avaluos = "";
    String total_factura = "";
    String subtotal_factura = "";
    String iva_factura = "";
    String reteiva = "";
    String retefuente = "";
    String retefuenteT = "";
    String reteica = "";
    String reteicaT = "";
    String cCosto = "";
    String anticipo = "";
    String saldo = "";

    String codigo = "";
    int linea=0;
    double temp=0;
    int tipo_factura = 0;
    double vr_anticipo = 0;
    long temp1=0;
    int navaluos = 0;
    String SQL = ("select f.NUMERO_FACTURA, count(*) nAvaluos, DATE_FORMAT(f.fecha_factura,'%Y%m%d') fecha_factura, " +
            "       CASE WHEN (d.tipo = '2') THEN 'Cobro Faltante' " +
            "            WHEN (d.tipo = '3') THEN 'Copia Avaluo' " +
            "            WHEN (f.tipo = '2' ) THEN 'Anticipo' " +
            "            WHEN (f.tipo = '3' ) THEN 'Otros Conceptos' " +
            "            WHEN (f.tipo = '4' ) THEN 'Tiquetes Aereos' " +
            "            WHEN (f.tipo = '5' ) THEN 'Gastos Avaluos' " +
            "       ELSE 'Normal' END tipo_factura, " +
            "       CASE WHEN (ifnull(f.estado,-1) = 0 and DATE_FORMAT(f.fecha_factura,'%Y%m') = DATE_FORMAT(cafa.FECHA,'%Y%m')) THEN 0 " +
            "            WHEN (ifnull(f.estado,-1) = 0 and DATE_FORMAT(f.fecha_factura,'%Y%m') <> DATE_FORMAT(cafa.FECHA,'%Y%m')) THEN -1 " +
            "       ELSE ifnull(f.estado,-1) END estado, " +
            "       RPAD(concat('Av. ', se.NUMERO," +
            "               CASE WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 1) THEN ' al Inmueble'  " +
            "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 2) THEN ' al Levantamiento Topografico'  " +
            "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 3) THEN ' al Mueble y Enser' " +
            "                    ELSE 'a la maquinaria,' END, " +
            "               CASE WHEN (se.tipo_avaluo = 1) THEN concat(' en la direccion ', se.DIRECCION)  " +
            "                    ELSE '' END, " +
            "               CASE WHEN (se.tipo_avaluo = 3) THEN concat(ma.DETALLE,' ',ifnull(ma.inventario,''),' ',ifnull(ma.serie,'')) " +
            "                    ELSE '' END, " +
            "               ', en ',cise.DESC_CIUDAD,' / ',dese.DESC_DEP " +
            "              ),80,' ') desc_deta, RPAD(obfa.obser_factura,80,' ') obser_factura, " +
            "        RPAD(TRIM(LEADING '0' FROM substring(f.documento,1,CASE WHEN ((LOCATE('-', f.documento)-1)>1) THEN LOCATE('-', f.documento)-1 ELSE 11 END)),13,' ') identificacion, " +
            "        ifnull(d1.valor,0) subtotal,ifnull(d2.valor,0) iva,ifnull(d3.valor,0) reteiva,ifnull(d4.valor,0) retefuente,ifnull(d5.valor,0) reteica, " +
            "        ifnull(d6.valor,0) total,ifnull(d7.valor,0) anticipo,ifnull(d8.valor,0) saldo,RPAD(ifnull(d9.valor,' '),80,' ') detalle " + 
            " from facturas f " +
            "  left outer join detalle_factura         d on d.NUMERO_FACTURA = f.NUMERO_FACTURA" +
            "  left outer join seguimiento            se on d.NUMERO = se.NUMERO AND d.TIPO_AVALUO = se.TIPO_AVALUO AND d.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join predios                pr on pr.NUMERO = se.NUMERO AND pr.TIPO_AVALUO = se.TIPO_AVALUO AND pr.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join maquinaria             ma on ma.NUMERO = se.NUMERO AND ma.TIPO_AVALUO = se.TIPO_AVALUO AND ma.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join ciudades             cise on se.CODIGO_DEP = cise.CODIGO_DEP AND se.CODIGO_CIUDAD = cise.CODIGO_CIUDAD " +
            "  left outer join departamento         dese on se.CODIGO_DEP = dese.CODIGO_DEP " +
            "  left outer join obser_factura_cb     obfa on obfa.numero_factura = f.NUMERO_FACTURA and obfa.consecutivo = 1 " +
            "  left outer join cancelada_factura    cafa on f.NUMERO_FACTURA = cafa.NUMERO_FACTURA " +
            "  left outer join valores_facturas_cb    d1 on d1.numero_factura = f.NUMERO_FACTURA and d1.descripcion = 1 " +
            "  left outer join valores_facturas_cb    d2 on d2.numero_factura = f.NUMERO_FACTURA and d2.descripcion = 2 " +
            "  left outer join valores_facturas_cb    d3 on d3.numero_factura = f.NUMERO_FACTURA and d3.descripcion = 3 " +
            "  left outer join valores_facturas_cb    d4 on d4.numero_factura = f.NUMERO_FACTURA and d4.descripcion = 4 " +
            "  left outer join valores_facturas_cb    d5 on d5.numero_factura = f.NUMERO_FACTURA and d5.descripcion = 5 " +
            "  left outer join valores_facturas_cb    d6 on d6.numero_factura = f.NUMERO_FACTURA and d6.descripcion = 6 " +
            "  left outer join valores_facturas_cb    d7 on d7.numero_factura = f.NUMERO_FACTURA and d7.descripcion = 7 " +
            "  left outer join valores_facturas_cb    d8 on d8.numero_factura = f.NUMERO_FACTURA and d8.descripcion = 8 " +
            "  left outer join valores_facturas_cb    d9 on d9.numero_factura = f.NUMERO_FACTURA and d9.descripcion = 9 " +
            " where f.fecha_factura is not null and f.fecha_factura >= '" + fCon + "' and f.fecha_factura <= '" + fFin + "' " +
            " group by f.NUMERO_FACTURA order by 1 ");
            
//    System.out.println(SQL);
            
    u.consultaSql("select f.NUMERO_FACTURA, count(*) nAvaluos, DATE_FORMAT(f.fecha_factura,'%Y%m%d') fecha_factura, " +
            "       CASE WHEN (d.tipo = '2') THEN 'Cobro Faltante' " +
            "            WHEN (d.tipo = '3') THEN 'Copia Avaluo' " +
            "            WHEN (f.tipo = '2' ) THEN 'Anticipo' " +
            "            WHEN (f.tipo = '3' ) THEN 'Otros Conceptos' " +
            "            WHEN (f.tipo = '4' ) THEN 'Tiquetes Aereos' " +
            "            WHEN (f.tipo = '5' ) THEN 'Gastos Avaluos' " +
            "       ELSE 'Normal' END tipo_factura, " +
            "       CASE WHEN (ifnull(f.estado,-1) = 0 and DATE_FORMAT(f.fecha_factura,'%Y%m') = DATE_FORMAT(cafa.FECHA,'%Y%m')) THEN 0 " +
            "            WHEN (ifnull(f.estado,-1) = 0 and DATE_FORMAT(f.fecha_factura,'%Y%m') <> DATE_FORMAT(cafa.FECHA,'%Y%m')) THEN -1 " +
            "       ELSE ifnull(f.estado,-1) END estado, " +
            "       RPAD(concat('Av. ', se.NUMERO," +
            "               CASE WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 1) THEN ' al Inmueble'  " +
            "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 2) THEN ' al Levantamiento Topografico'  " +
            "                    WHEN (se.tipo_avaluo = 1 and se.tipo_inmueble = 3) THEN ' al Mueble y Enser' " +
            "                    ELSE 'a la maquinaria,' END, " +
            "               CASE WHEN (se.tipo_avaluo = 1) THEN concat(' en la direccion ', se.DIRECCION)  " +
            "                    ELSE '' END, " +
            "               CASE WHEN (se.tipo_avaluo = 3) THEN concat(ma.DETALLE,' ',ifnull(ma.inventario,''),' ',ifnull(ma.serie,'')) " +
            "                    ELSE '' END, " +
            "               ', en ',cise.DESC_CIUDAD,' / ',dese.DESC_DEP " +
            "              ),80,' ') desc_deta, RPAD(obfa.obser_factura,80,' ') obser_factura, " +
            "        RPAD(TRIM(LEADING '0' FROM substring(f.documento,1,CASE WHEN ((LOCATE('-', f.documento)-1)>1) THEN LOCATE('-', f.documento)-1 ELSE 11 END)),13,' ') identificacion, " +
            "        (ifnull(d1.valor,0)-ifnull(cer.VALOR,0)) subtotal,ifnull(d2.valor,0) iva,ifnull(d3.valor,0) reteiva,ifnull(d4.valor,0) retefuente,ifnull(d5.valor,0) reteica, " +
            "        ifnull(d6.valor,0) total,ifnull(d7.valor,0) anticipo,ifnull(d8.valor,0) saldo,RPAD(ifnull(d9.valor,' '),80,' ') detalle " + 
            "        ,replace(ifnull(cer.VALOR,0),'.00','') certi , RPAD(cer.OBSERVACION,80,' ') certi_obs " +
            " from facturas f " +
            "  left outer join detalle_factura         d on d.NUMERO_FACTURA = f.NUMERO_FACTURA" +
            "  left outer join seguimiento            se on d.NUMERO = se.NUMERO AND d.TIPO_AVALUO = se.TIPO_AVALUO AND d.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join predios                pr on pr.NUMERO = se.NUMERO AND pr.TIPO_AVALUO = se.TIPO_AVALUO AND pr.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join maquinaria             ma on ma.NUMERO = se.NUMERO AND ma.TIPO_AVALUO = se.TIPO_AVALUO AND ma.BIEN_NUMERO = se.BIEN_NUMERO" +
            "  left outer join ciudades             cise on se.CODIGO_DEP = cise.CODIGO_DEP AND se.CODIGO_CIUDAD = cise.CODIGO_CIUDAD " +
            "  left outer join departamento         dese on se.CODIGO_DEP = dese.CODIGO_DEP " +
            "  left outer join obser_factura_cb     obfa on obfa.numero_factura = f.NUMERO_FACTURA and obfa.consecutivo = 1 " +
            "  left outer join cancelada_factura    cafa on f.NUMERO_FACTURA = cafa.NUMERO_FACTURA " +
            "  left outer join valores_facturas_cb    d1 on d1.numero_factura = f.NUMERO_FACTURA and d1.descripcion = 1 " +
            "  left outer join valores_facturas_cb    d2 on d2.numero_factura = f.NUMERO_FACTURA and d2.descripcion = 2 " +
            "  left outer join valores_facturas_cb    d3 on d3.numero_factura = f.NUMERO_FACTURA and d3.descripcion = 3 " +
            "  left outer join valores_facturas_cb    d4 on d4.numero_factura = f.NUMERO_FACTURA and d4.descripcion = 4 " +
            "  left outer join valores_facturas_cb    d5 on d5.numero_factura = f.NUMERO_FACTURA and d5.descripcion = 5 " +
            "  left outer join valores_facturas_cb    d6 on d6.numero_factura = f.NUMERO_FACTURA and d6.descripcion = 6 " +
            "  left outer join valores_facturas_cb    d7 on d7.numero_factura = f.NUMERO_FACTURA and d7.descripcion = 7 " +
            "  left outer join valores_facturas_cb    d8 on d8.numero_factura = f.NUMERO_FACTURA and d8.descripcion = 8 " +
            "  left outer join valores_facturas_cb    d9 on d9.numero_factura = f.NUMERO_FACTURA and d9.descripcion = 9 " +
            "  left outer join otros_vrs             cer on cer.numero_factura = f.NUMERO_FACTURA and cer.IVA = 0 " +
            " where f.fecha_factura is not null and f.fecha_factura >= '" + fCon + "' and f.fecha_factura <= '" + fFin + "' " +
            " group by f.NUMERO_FACTURA order by 1 ");
    

    while ( u.getNuevaReg() ) {
        
       if ( Integer.parseInt(u.getCampo("estado")) == 0 ) {
          total_factura = "0";   
       } else {
          total_factura = ( Integer.parseInt(u.getCampo("subtotal")) + Integer.parseInt(u.getCampo("iva")) ) + "";
       }
       identificacion = u.getCampo("identificacion");
       if ( u.getCampo("estado").equals("0") ) {
           detalle = "FACTURA ANULADA";
           detalle = String.format("%1$-80s",detalle);
           identificacion = "830008001    ";
       } else {
           if ( Integer.parseInt(u.getCampo("nAvaluos")) > 1 ) {
                  detalle = u.getAvaluosFactura(Integer.parseInt(u.getCampo("numero_factura")));
                  detalle = String.format("%1$-80s",detalle);
//                  System.out.println("Navaluos ->"+u.getCampo("numero_factura"));
           } else {
               if ( u.getCampo("tipo_factura").equals("Normal") ) {
                   detalle = u.getCampo("desc_deta");
               } else {
                     if ( u.getCampo("tipo_factura").equals("Cobro Faltante") || u.getCampo("tipo_factura").equals("Copia Avaluo") || u.getCampo("tipo_factura").equals("Otros Conceptos") || u.getCampo("tipo_factura").equals("Gastos Avaluos") ) {
                         detalle = u.getCampo("tipo_factura") + ( u.getCampo("desc_deta") != null ? " " + u.getCampo("desc_deta").substring(0, 9) : " ");
                         detalle = String.format("%1$-80s",detalle);
                     } else {
                         detalle = u.getCampo("obser_factura");
                     }
               }
           }
       }
       
       if ( Integer.parseInt(u.getCampo("retefuente")) > 0 ) {
           tRetefuente = Math.rint(  (Double.parseDouble(u.getCampo("retefuente"))/Double.parseDouble(u.getCampo("subtotal")) )*100)/100;

           if ( tRetefuente == 0.11  ) {
               retefuente = cuentaRFH;
               retefuenteT= "011000";
           } else {
               retefuente = cuentaRFS;
               retefuenteT= "004000";
           }
       }
       
       cCosto = centroCosto;
       if ( Integer.parseInt(u.getCampo("reteica")) > 0 ) {
           tReteica    = Math.rint(  (Double.parseDouble(u.getCampo("reteica"))/Double.parseDouble(u.getCampo("subtotal"))) * 100000) /100000;
           reteicaT= String.valueOf(tReteica).substring(2) + "0";
           if ( tReteica == 0.00966 || tReteica == 0.01104 || tReteica == 0.00699 ) {
               reteica = cuentaRIcd;
           } else {
               reteica = cuentaRIco;
               cCosto = "0       ";
           }
       }
       
       TD = (Integer.parseInt(u.getCampo("numero_factura"))) > 60000 ? "AV" : "FV";

//       System.out.println("NUMERO DE FACTURA----->"+u.getCampo("numero_factura"));
       
//       System.out.println("retefuenteT->"+(tRetefuente)+"+++++"+tReteica+"Esto es el campo->"+Integer.parseInt(u.getCampo("retefuente")));
//       System.out.println("RETEFUENTE-->"+(Double.parseDouble(u.getCampo("retefuente"))/Double.parseDouble(u.getCampo("subtotal"))));
       
       
//       System.out.println("reteicaT->"+reteicaT+"----"+String.valueOf(tReteica)+"+++++"+tReteica+"Esto es el campo->"+Integer.parseInt(u.getCampo("reteica")));
//       System.out.println("-->"+(Double.parseDouble(u.getCampo("reteica"))/Double.parseDouble(u.getCampo("subtotal"))));
//       System.out.println("tReteica-->"+tReteica);
       
       
       cadena = "IS001" +TD+ u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+ u.getCampo("fecha_factura");                            // Hasta dato 2, columna 6
       cadena = cadena + identificacion + "00" + ( u.getCampo("estado").equals("0") ? "X" : "0" ) + u.formatiando("000000000000000",Integer.parseInt(total_factura)) + "00+";  // Hasta dato 4, columna 10
       
       // Entonces ingresamos el primer registro
       if ( Integer.parseInt(u.getCampo("iva")) > 0 ) {
           cadena1 = cadena + cuentaSubTc;               // Cuenta para manejo de transacciones con IVA
       } else {
           if ( u.getCampo("tipo_factura").equals("Cobro Faltante") || u.getCampo("tipo_factura").equals("Copia Avaluo") || u.getCampo("tipo_factura").equals("Normal") ) {
               cadena1 = cadena + cuentaSubTs;           // Cuenta para el manejo de transacciones si IVA, Ciudades Leticia y San andres
           } else {
               cadena1 = cadena + cuentaRein;            // Cuenta para el manejo de transacciones de Reintegro, que no deben llevar IVA
           }
       }
//       System.out.println("El detalle ->"+detalle);
//       detalle=u.consultaSql1("select _utf8'"+detalle+"';");
//       System.out.println("El detalle1->"+detalle);
       
       
//       System.out.println("Generando detalle para remove1->"+detalle);
       detalle=remove1(detalle);
//       System.out.println("Esto es detalle despues de remove1->"+detalle);
       
       cadena1 = u.formatiando("000000000",++linea)+ cadena1 + "001" + identificacion + "00" + detalle + "C" + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
       cadena1 = cadena1 + "00000000000000000000000000000+00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";

             // registro Reintegro Certificado *********************************OJO aqui voy  debemos modificar esta pregunta para incluir 
             //                                                                 el valor de Certificado de libertad ya esta el query
       if ( Integer.parseInt(u.getCampo("certi")) > 0 ) {
           detalleCert = remove1(u.getCampo("certi_obs"));
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaRein + "001" + identificacion + "00"+ detalleCert +"C";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("certi"))) + "00+00000000000000000000000000000+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
       }
       
             // registro IVA
       if ( Integer.parseInt(u.getCampo("iva")) > 0 ) {
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaI + "001" + identificacion + "00IVA DE LA FACTURA                                                               C";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("iva"))) + "00+016000000000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
       }
       
       // registro reteIVA
       if ( Integer.parseInt(u.getCampo("reteiva")) > 0 ) {
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaRI + "001" + identificacion + "00RETEIVA DE LA FACTURA                                                           D";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("reteiva"))) + "00+015000016000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("iva"))) + "00+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
       }

       // registro reteFUENTE
       if ( Integer.parseInt(u.getCampo("retefuente")) > 0 ) {
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + retefuente + "001" + identificacion + "00RETEFUENTE DE LA FACTURA                                                        D";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("retefuente"))) + "00+"+retefuenteT+"000000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
       }
                   
       // registro reteica
       if ( Integer.parseInt(u.getCampo("reteica")) > 0 ) {
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + reteica + "001" + identificacion + "00RETEICA DE LA FACTURA                                                           D";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("reteica"))) + "00+"+reteicaT+"000000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
       }
           
       // registro saldo -> Cuenta por Cobrar
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaCC + "001" + identificacion + "00CUENTA POR COBRAR                                                               D";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("total"))) + "00+00000000000000000000000000000+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";

       // registro Anticipo
       if ( Integer.parseInt(u.getCampo("anticipo")) > 0 ) {
/*           cadena = "IS001" +TD+ u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+ u.getCampo("fecha_factura");                            // Hasta dato 2, columna 6
           cadena = cadena + identificacion + "00" + ( u.getCampo("estado").equals("0") ? "X" : "0" ) + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("anticipo"))) + "00+";  // Hasta dato 4, columna 10
           
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaAnt + "001" + identificacion + "00ANTICIPO DE LA FACTURA                                                          D";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("anticipo"))) + "00+00000000000000000000000000000+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
           
           cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaCC + "001" + identificacion + "00CUENTA POR COBRAR                                                               C";
           cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("anticipo"))) + "00+00000000000000000000000000000+";
           cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
*/
       }

//     Registros del CREE D
       cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaCreeD + "001" + identificacion + "00AUTORETENCION CREE DEBITO                                                       D";
       cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal")) * 0.8 / 100 ) + "00+"+"000800"+"000000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
       cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
//     Registros del CREE C
       cadena1 = cadena1 + u.formatiando("000000000",++linea) + cadena + cuentaCreeC + "001" + identificacion + "00AUTORETENCION CREE CREDITO                                                      C";
       cadena1 = cadena1 + u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal")) * 0.8 / 100 ) + "00+"+"000800"+"000000" +  u.formatiando("000000000000000",Integer.parseInt(u.getCampo("subtotal"))) + "00+";
       cadena1 = cadena1 + "00000000000000000+00000000000+00000000000+"+cCosto+"                    000000"+TD+u.formatiando("000000",Integer.parseInt(u.getCampo("numero_factura")))+"01"+u.getCampo("fecha_factura")+"                             000000000000000000000+           000000                              00000000                                                                                                                                                                                                                                                                       " + "\n";
           
           
       cadena2 = cadena2 + cadena1;
    }                     

    if ( !cadena.equals("") ) {
        
        fechaHora = u.getFechaHoy("%Y%m%d%H%i%s");
        
       
//        cadena2 = remove1(cadena2);
        
//        System.out.println(cadena2);
        
        byte [] barray = cadena2.getBytes();
        String camino = application.getRealPath("/")+"planos/facturas/"+fechaHora+"_"+fCon+"_"+fFin+".txt";
        FileOutputStream fo = new FileOutputStream(camino);

        for (int i=0; i < barray.length; i++ ) {
            fo.write(barray[i]);
        }
        fo.close();
    }

     %>
<BR>
<% 
   if ( !cadena2.equals("") )  { 
%>
<TABLE border='0' width='400'>
<TR>
   <TD width='100' align='center'><STRONG> Archivo Generado </STRONG></TD>
   <TD width='200'> <A href='<%= request.getContextPath()+"/planos/facturas/"+fechaHora+"_"+ fCon+"_"+fFin+".txt" %>'> <%= fCon+"_"+fFin+".txt" %> </A></TD>
</TR>
</table>
<% 
   } else {
%>
<TABLE border='0' width='400'>
<TR>
   <TD width='100' align='center'></TD>
   <TD width='200'> No existe informacion para generar el archivo plano</TD>
</TR>
</table>
<% }
%>

<BR>
<BR>
<TABLE border="0" width="350">
        <TR>
        <TD width="179"></TD>
        <TD><a href="<%= request.getContextPath()%>/factura/menuFacturacion.jsp"> Volver al Menu </a></TD>
        </TR>
</TABLE>
</form>

<% u.cerrarBd(); %>

</BODY></HTML>