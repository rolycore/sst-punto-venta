package Modelo;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class VentaDao {

    Connection con;
    Conexion cn = new Conexion();
    PreparedStatement ps;
    ResultSet rs;
    int r;

    public int IdVenta() {
        int id = 0;
        String sql = "SELECT MAX(id) FROM ventas";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return id;
    }

    public int RegistrarVenta(Venta v) {
        String sql = "INSERT INTO ventas (cliente, vendedor, total, fecha, observaciones, estado) VALUES (?,?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, v.getCliente());
            ps.setString(2, v.getVendedor());
            ps.setDouble(3, v.getTotal());
            ps.setString(4, v.getFecha());
            ps.setString(5, v.getObservaciones());
            ps.setString(6, v.getEstado());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    public int RegistrarDetalle(Detalle Dv) {
        String sql = "INSERT INTO detalle (id_pro, cantidad, precio, id_venta) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, Dv.getId_pro());
            ps.setInt(2, Dv.getCantidad());
            ps.setDouble(3, Dv.getPrecio());
            ps.setInt(4, Dv.getId());
            ps.execute();
        } catch (SQLException e) {
            System.out.println(e.toString());
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
        return r;
    }

    public boolean ActualizarEstado(Venta v) {
        String sql = "UPDATE ventas SET estado=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, v.getEstado());
            ps.setInt(2, v.getId());

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
                JOptionPane.showMessageDialog(null, e.toString(),
                        "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean ActualizarStock(int cant, int id) {
        String sql = "UPDATE productos SET stock = ? WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, cant);
            ps.setInt(2, id);
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean ModificarVentaTotal(Venta v) {
        String sql = "UPDATE ventas SET total=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setDouble(1, v.getTotal());

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
                JOptionPane.showMessageDialog(null, e.toString(),
                        "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public boolean ModificarVentaDetalle(Detalle Dv) {
        String sql = "UPDATE detalle SET cantidad=?, precio=? WHERE id=?";
        try {
            ps = con.prepareStatement(sql);

            ps.setInt(1, Dv.getCantidad());
            ps.setDouble(2, Dv.getPrecio());

            ps.execute();
            return true;
        } catch (SQLException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
                JOptionPane.showMessageDialog(null, e.toString(),
                        "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public List Listarventas() {
        List<Venta> ListaVenta = new ArrayList();
        String sql = "SELECT c.id AS id_cli, c.nombre, v.* FROM clientes c INNER JOIN ventas v ON c.id = v.cliente";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Venta vent = new Venta();
                vent.setId(rs.getInt("id"));
                vent.setNombre_cli(rs.getString("nombre"));
                vent.setVendedor(rs.getString("vendedor"));
                vent.setTotal(rs.getDouble("total"));
                vent.setObservaciones(rs.getString("observaciones"));
                vent.setEstado(rs.getString("estado"));
                vent.setFecha(rs.getString("fecha"));
                ListaVenta.add(vent);
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return ListaVenta;
    }

    public Venta BuscarVenta(int id) {
        Venta cl = new Venta();
        String sql = "SELECT * FROM ventas WHERE id = ?";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                cl.setId(rs.getInt("id"));
                cl.setCliente(rs.getInt("cliente"));
                cl.setTotal(rs.getDouble("total"));
                cl.setVendedor(rs.getString("vendedor"));
                cl.setObservaciones(rs.getString("observaciones"));
                cl.setEstado(rs.getString("estado"));
                cl.setFecha(rs.getString("fecha"));
            }
        } catch (SQLException e) {
            System.out.println(e.toString());
        }
        return cl;
    }

    public void pdfV(int idventa, int Cliente, double total, String fechav, String usuario, String observacion) {
        try {

            FileOutputStream archivo;
            String url = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
            File salida = new File(url + "venta.pdf");
            archivo = new FileOutputStream(salida);
            Document doc = new Document();
            PdfWriter.getInstance(doc, archivo);
            doc.open();
            Image img = Image.getInstance(getClass().getResource("/Img/LOGOSHALOM.png"));
            //Factura
            Paragraph fact = new Paragraph();
            Font negrita = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD, BaseColor.BLUE);
            fact.add(Chunk.NEWLINE);
            fact.add("Folio Fac.: " + idventa);
            PdfPTable Encabezado = new PdfPTable(4);
            Encabezado.setWidthPercentage(100);
            Encabezado.getDefaultCell().setBorder(0);
            float[] columnWidthsEncabezado = new float[]{30f, 20f, 70f, 40f};
            Encabezado.setWidths(columnWidthsEncabezado);
            Encabezado.setHorizontalAlignment(Element.ALIGN_CENTER);
            Encabezado.addCell(img);
            Encabezado.addCell("");
            //info empresa
            String config = "SELECT * FROM config";
            String mensaje = "";
            try {
                con = cn.getConnection();
                ps = con.prepareStatement(config);
                rs = ps.executeQuery();
                if (rs.next()) {
                    mensaje = rs.getString("mensaje");
                    Encabezado.addCell(rs.getString("nombre") + "\n" + rs.getString("ruc") + "\n" + rs.getString("telefono") + "\n" + rs.getString("direccion"));
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            //
            Encabezado.addCell(fact);
            doc.add(Encabezado);
            Paragraph gr = new Paragraph();
            gr.add(Chunk.NEWLINE);
            gr.add(mensaje);
            gr.setAlignment(Element.ALIGN_CENTER);
            doc.add(gr);
            //Fecha Facturacion
            Paragraph fecha = new Paragraph();
            fecha.add("Fecha: ");
            String fechv = "select*from ventas where id=?";
            try {
                ps = con.prepareStatement(fechv);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                while (rs.next()) {

                    fecha.add(rs.getString("fecha"));

                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }

            //fecha.add(Chunk.NEWLINE);
            //Date date = new Date();
            doc.add(fecha);

            //cliente
            PdfPTable proveedor = new PdfPTable(5);
            proveedor.setWidthPercentage(100);
            proveedor.getDefaultCell().setBorder(0);
            float[] columnWidthsCliente = new float[]{25f, 25f, 25f, 25f, 25f};
            proveedor.setWidths(columnWidthsCliente);
            proveedor.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cliNom = new PdfPCell(new Phrase("Nombre", negrita));
            PdfPCell cliCedu = new PdfPCell(new Phrase("Cedula", negrita));
            PdfPCell cliDireccion = new PdfPCell(new Phrase("Direccion", negrita));
            PdfPCell cliTelefono = new PdfPCell(new Phrase("Telefono", negrita));
            PdfPCell cliCorreo = new PdfPCell(new Phrase("Correo", negrita));
            cliNom.setBorder(Rectangle.NO_BORDER);
            cliCedu.setBorder(Rectangle.NO_BORDER);
            cliDireccion.setBorder(Rectangle.NO_BORDER);
            cliTelefono.setBorder(Rectangle.NO_BORDER);
            cliCorreo.setBorder(Rectangle.NO_BORDER);
            proveedor.addCell(cliNom);
            proveedor.addCell(cliCedu);
            proveedor.addCell(cliDireccion);
            proveedor.addCell(cliTelefono);
            proveedor.addCell(cliCorreo);
            String prove = "SELECT * FROM clientes WHERE id = ?";
            try {
                ps = con.prepareStatement(prove);
                ps.setInt(1, Cliente);
                rs = ps.executeQuery();
                if (rs.next()) {
                    proveedor.addCell(rs.getString("nombre"));
                    proveedor.addCell(rs.getString("dni"));
                    proveedor.addCell(rs.getString("direccion"));
                    proveedor.addCell(rs.getString("numerotelefono"));
                    proveedor.addCell(rs.getString("correo") + "\n");
                } else {
                    proveedor.addCell("Publico en General");
                    proveedor.addCell("S/N");
                    proveedor.addCell("S/N" + "\n\n");
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(proveedor);

            //detalles    
            PdfPTable tabla = new PdfPTable(5);
            tabla.setWidthPercentage(100);
            tabla.getDefaultCell().setBorder(0);
            float[] columnWidths = new float[]{10f, 20f, 20f, 15f, 15f};
            tabla.setWidths(columnWidths);
            tabla.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell c1 = new PdfPCell(new Phrase("Cant.", negrita));
            PdfPCell c2 = new PdfPCell(new Phrase("Codigo.", negrita));
            PdfPCell c3 = new PdfPCell(new Phrase("Descripción.", negrita));
            PdfPCell c4 = new PdfPCell(new Phrase("P. unt.", negrita));
            PdfPCell c5 = new PdfPCell(new Phrase("P. Total", negrita));
            c1.setBorder(Rectangle.NO_BORDER);
            c2.setBorder(Rectangle.NO_BORDER);
            c3.setBorder(Rectangle.NO_BORDER);
            c4.setBorder(Rectangle.NO_BORDER);
            c5.setBorder(Rectangle.NO_BORDER);
            c1.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c2.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c3.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c4.setBackgroundColor(BaseColor.LIGHT_GRAY);
            c5.setBackgroundColor(BaseColor.LIGHT_GRAY);
            tabla.addCell(c1);
            tabla.addCell(c2);
            tabla.addCell(c3);
            tabla.addCell(c4);
            tabla.addCell(c5);
            String product = "SELECT d.id, d.id_pro,d.id_venta, d.precio, d.cantidad, p.id, p.codigo,p.nombre  FROM detalle d INNER JOIN productos p ON d.id_pro = p.id WHERE d.id_venta = ?";
            try {
                ps = con.prepareStatement(product);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                while (rs.next()) {
                    double subTotal = rs.getInt("cantidad") * rs.getDouble("precio");
                    tabla.addCell(rs.getString("cantidad"));
                    tabla.addCell(rs.getString("codigo"));
                    tabla.addCell(rs.getString("nombre"));
                    tabla.addCell(rs.getString("precio"));
                    tabla.addCell(String.valueOf(subTotal));
                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }
            doc.add(tabla);
            Paragraph info = new Paragraph();
            info.add(Chunk.NEWLINE);
            info.add("Total a pagar: " + total + "\n\n");
            info.setAlignment(Element.ALIGN_RIGHT);
            doc.add(info);
            PdfPTable obser = new PdfPTable(1);

            PdfPCell ob = new PdfPCell(new Phrase("Observaciones", negrita));
            ob.setBorder(Rectangle.PARAGRAPH);
            ob.setBackgroundColor(BaseColor.LIGHT_GRAY);
            obser.addCell(ob);
            String observa = "select*from ventas where observaciones is not null and id=?";
            try {
                ps = con.prepareStatement(observa);
                ps.setInt(1, idventa);
                rs = ps.executeQuery();
                while (rs.next()) {

                    obser.addCell(rs.getString("observaciones"));

                }

            } catch (SQLException e) {
                System.out.println(e.toString());
            }

            doc.add(obser);
            //Vendedor
            
            Paragraph firma = new Paragraph();
            
            firma.add(Chunk.NEWLINE);
            firma.add("Cliente \n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n\n");
            firma.add("Vendedor: " + usuario + "\n\n");
            firma.add("------------------------------------\n");
            firma.add("Firma \n");
            firma.setAlignment(Element.ALIGN_CENTER);
            doc.add(firma);

            doc.close();
            archivo.close();
            Desktop.getDesktop().open(salida);
        } catch (DocumentException | IOException e) {
            System.out.println(e.toString());
            JOptionPane.showMessageDialog(null, e.toString(),
                    "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);

        }
    }
}
