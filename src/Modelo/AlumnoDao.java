/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Rolando Salinas
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class AlumnoDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
      public boolean RegistrarAlumno(Alumno al){
      
                String sql = "INSERT INTO alumno (Nombre, Cedula, Sexo,Grado ) VALUES (?,?,?,?)";
        try {
            con = cn.getConnection();
            ps = con.prepareStatement(sql);
           
             ps.setString(1, al.getNombre());
            ps.setString(2, al.getCedula());
             ps.setString(3, String.valueOf(al.getSexo()));
            ps.setString(4, al.getGrado());
           
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
                JOptionPane.showMessageDialog(null, e.toString(),
      "Ocurrio un Error!", JOptionPane.ERROR_MESSAGE);
            }
        }
      }
      
      
}
