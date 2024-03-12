/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author El Buen Pastor
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
public class AlumnosDao {
        Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
     public boolean RegistrarAlumnos(Alumnos al){
        String sql = "INSERT INTO alumnos (nombre, apellido, cedula, sexo, grado) VALUES (?,?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, al.getNombre());
            ps.setString(2, al.getApellido());
            ps.setString(3, al.getCedula());
            ps.setString(4, al.getSexo());
            ps.setString(5, al.getGrado());
            ps.execute();
            return true;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.toString());
            return false;
        }finally{
            try {
                con.close();
            } catch (SQLException e) {
                System.out.println(e.toString());
            }
        }
    }
     public List ListarAlumnos(){
       List<Alumnos> ListaAl = new ArrayList();
       String sql = "SELECT * FROM alumnos";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           rs = ps.executeQuery();
           while (rs.next()) {               
               Alumnos al = new Alumnos();
               al.setId(rs.getInt("id"));
               al.setNombre(rs.getString("nombre"));
               al.setApellido(rs.getString("apellido"));
               al.setCedula(rs.getString("cedula"));
               al.setSexo(rs.getString("sexo"));
               al.setGrado(rs.getString("grado"));
               ListaAl.add(al);
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return ListaAl;
   }
     public boolean EliminarAlumnos(int id){
       String sql = "DELETE FROM alumnos WHERE id = ?";
       try {
           ps = con.prepareStatement(sql);
           ps.setInt(1, id);
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException ex) {
               System.out.println(ex.toString());
           }
       }
   }
   
   public boolean ModificarAlumnos(Alumnos al){
       String sql = "UPDATE alumnos SET id=?, nombre=?, apellido=?, cedula=?, sexo=?, grado=? WHERE id=?";
       try {
           ps = con.prepareStatement(sql);   
           ps.setString(1, al.getNombre());
           ps.setString(2, al.getApellido());
           ps.setString(3, al.getCedula());
           ps.setString(4, al.getSexo());
           ps.setString(6, al.getGrado());
           ps.setInt(5, al.getId());
           ps.execute();
           return true;
       } catch (SQLException e) {
           System.out.println(e.toString());
           return false;
       }finally{
           try {
               con.close();
           } catch (SQLException e) {
               System.out.println(e.toString());
           }
       }
   }
   
   public Alumnos Buscaralumno(String valor){
       Alumnos al = new Alumnos();
       String sql = "SELECT * FROM alumnos WHERE CONCAT(nombre,apellido,cedula)LIKE '%"+valor+"%'";
       try {
           con = cn.getConnection();
           ps = con.prepareStatement(sql);
           ps.setString(1, valor);
           rs = ps.executeQuery();
           if (rs.next()) {
               al.setId(rs.getInt("id"));
               al.setNombre(rs.getString("nombre"));
               al.setApellido(rs.getString("apellido"));
               al.setCedula(rs.getString("cedula"));
               al.setSexo(rs.getString("sexo"));
               al.setGrado(rs.getString("grado"));
           }
       } catch (SQLException e) {
           System.out.println(e.toString());
       }
       return al;
   }
}
