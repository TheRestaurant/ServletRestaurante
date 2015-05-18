/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import auxiliares.Auxiliar;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 *
 * @author AngelakaMogu
 */
@WebServlet(name = "Peticiones", urlPatterns = {"/peticiones"})
public class Peticiones extends HttpServlet {
    
    int pedido;
    int estado;
    ControlDB bd;
    ResultSet r;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        String target, op, action, view;

        target = request.getParameter("target");
        op = request.getParameter("op");

        if (target.equals("login")) {

            bd = new ControlDB();
            bd.cargarDriver();
            bd.conectar();
            String login = request.getParameter("login");
            String pass = request.getParameter("password");
            ResultSet r = bd.ejecutarSelect("SELECT * FROM usuarios WHERE idUsuario='" + login + "' AND passwordUsuario='" + Auxiliar.encriptarPass(pass) + "'");
            JSONObject objetoJSON = new JSONObject();
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            try {
                if (r != null && r.next()) {
                    objetoJSON.put("r", "1");
                    out.print(objetoJSON);
                } else {
                    objetoJSON.put("r", "0");
                    out.print(objetoJSON);
                }
            } catch (SQLException ex) {

            } catch (JSONException ex) {

            }
        } else {
            if (target.equals("pedido")) {
                bd = new ControlDB();
                bd.cargarDriver();
                bd.conectar();
                String s = request.getParameter("datos");
                JSONTokener token = new JSONTokener(s);
                JSONArray ar = null;
                try {
                    ar = new JSONArray(token);
                    for (int i = 0; i < ar.length(); i++) {
                        agregarProducto(ar.getJSONObject(i).getInt("idMesa"), ar.getJSONObject(i).getInt("idProducto"));
                        System.out.println(ar.getJSONObject(i).toString());
                    }
                } catch (JSONException ex) {

                }
                response.setHeader("Content-Type", "application/json");
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                PrintWriter out = response.getWriter();
                JSONObject obj = new JSONObject();
                try {
                    obj.put("r", "recibido");
                } catch (JSONException ex) {

                }
                out.print(obj);
                out.flush();
            } else {
                if (target.equals("mesas")) {
                    bd = new ControlDB();
                    bd.cargarDriver();
                    bd.conectar();

                    ResultSet r = bd.ejecutarSelect("SELECT mesas.idMesa, nombreMesa, nombreZona FROM mesas inner join zona on idzona = Zona_idZona");

                    JSONArray array = new JSONArray();
                    ResultSetMetaData rsMetaData = null;
                    int columns = 0;
                    try {
                        rsMetaData = r.getMetaData();
                        columns = rsMetaData.getColumnCount();
                    } catch (SQLException ex) {

                    }

                    try {
                        while (r.next()) {
                            JSONObject objetoJSON = new JSONObject();
                            for (int i = 1; i <= columns; i++) {
                                objetoJSON.put(rsMetaData.getColumnLabel(i), r.getString(i));
                            }
                            System.out.println(objetoJSON + "\n");
                            array.put(objetoJSON);
                        }
                    } catch (SQLException ex) {

                    } catch (JSONException ex) {

                    }
                    response.setHeader("Content-Type", "application/json");
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    PrintWriter out = response.getWriter();
                    out.print(array);
                    out.flush();
                } else {
                    if (target.equals("familias")) {
                        bd = new ControlDB();
                        bd.cargarDriver();
                        bd.conectar();

                        ResultSet r = bd.ejecutarSelect("SELECT idFamilia, nombreFamilia FROM `familias` order by idFamilia");

                        JSONArray array = new JSONArray();
                        ResultSetMetaData rsMetaData = null;
                        int columns = 0;
                        try {
                            rsMetaData = r.getMetaData();
                            columns = rsMetaData.getColumnCount();
                        } catch (SQLException ex) {

                        }

                        try {
                            while (r.next()) {
                                JSONObject objetoJSON = new JSONObject();
                                for (int i = 1; i <= columns; i++) {
                                    objetoJSON.put(rsMetaData.getColumnLabel(i), r.getString(i));
                                }
                                System.out.println(objetoJSON + "\n");
                                array.put(objetoJSON);
                            }
                        } catch (SQLException ex) {

                        } catch (JSONException ex) {
                            ;
                        }
                        response.setHeader("Content-Type", "application/json");
                        response.setContentType("application/json");
                        response.setCharacterEncoding("UTF-8");
                        PrintWriter out = response.getWriter();
                        out.print(array);
                        out.flush();
                    } else {
                        if (target.equals("productos")) {
                            bd = new ControlDB();
                            bd.cargarDriver();
                            bd.conectar();

                            ResultSet r = bd.ejecutarSelect("SELECT idProducto, nombreProducto, precioProducto, Familias_idFamilias FROM productos order by idProducto");

                            JSONArray array = new JSONArray();
                            ResultSetMetaData rsMetaData = null;
                            int columns = 0;
                            try {
                                rsMetaData = r.getMetaData();
                                columns = rsMetaData.getColumnCount();
                            } catch (SQLException ex) {

                            }

                            try {
                                while (r.next()) {
                                    JSONObject objetoJSON = new JSONObject();
                                    for (int i = 1; i <= columns; i++) {
                                        objetoJSON.put(rsMetaData.getColumnLabel(i), r.getString(i));
                                    }
                                    System.out.println(objetoJSON + "\n");
                                    array.put(objetoJSON);
                                }
                            } catch (SQLException ex) {

                            } catch (JSONException ex) {

                            }
                            response.setHeader("Content-Type", "application/json");
                            response.setContentType("application/json");
                            response.setCharacterEncoding("UTF-8");
                            PrintWriter out = response.getWriter();
                            out.print(array);
                            out.flush();
                        }
                    }
                }
            }
        }

    }

    private void agregarProducto(int idmesa, int idProducto) {
        String usuario = "admin";
        pedido = 0;
        estado = 1;
        r = bd.ejecutarSelect("select * from pedidos where mesas_idmesa="
                + idmesa
                + " order by fechapedido desc");
        try {
            if (r.next()) {
                pedido = r.getInt("idpedido");
                estado = r.getInt("estadopedido");;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
        String consulta = "";
        if (pedido == 0 || estado == 1) {
            String fechaHora = getFecha();
            consulta = "insert into pedidos values(0, '" + fechaHora + "', 0, 0, 1, '" + usuario + "', " + idmesa + ", 1)";
            System.out.println(consulta);
            bd.ejecutarInsert(consulta);
            primerInsert();
        }
        int producto = idProducto;
        consulta = "SELECT count(*) as lineas "
                + "FROM lineapedidos "
                + "WHERE pedidos_idpedido=" + pedido
                + " and productos_idproducto=" + producto
                + " and estadolinea=0";
        System.out.println(consulta);
        r = bd.ejecutarSelect(consulta);
        try {
            r.next();
            System.out.println("!!!Numero de lineas " + r.getInt("lineas"));
            if (r.getInt("lineas") == 0) {
                consulta = "insert into lineapedidos values(0,1,0,0," + pedido + "," + producto + ")";
                bd.ejecutarInsert(consulta);
                System.out.println(consulta);
            } else {
                ResultSet cantidad = bd.ejecutarSelect(
                        "SELECT cantidadlinea "
                        + "FROM lineapedidos "
                        + "WHERE pedidos_idpedido=" + pedido
                        + " and productos_idproducto=" + producto
                        + " and estadolinea=0");
                cantidad.next();
                consulta = "update lineapedidos set cantidadlinea=" + (1 + cantidad.getInt("cantidadlinea"))
                        + " where pedidos_idpedido=" + pedido
                        + " and productos_idproducto=" + producto
                        + " and estadolinea=0";
                bd.ejecutarUpdate(consulta);
                System.out.println(consulta);
            }
        } catch (SQLException ex) {
        }
    }
    
    private void primerInsert(){
        try {
            if (r.next()) {
                pedido = r.getInt("idpedido");
                estado = r.getInt("estadopedido");;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
        }
    }

    private String getFecha() {
        Date now = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return formato.format(now);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
