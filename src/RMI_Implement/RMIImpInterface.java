/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI_Implement;

import RMI_Interface.StudentInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;
import java.util.logging.*;
import javax.swing.JOptionPane;
import student.StudentDetails;

/**
 *
 * @author moisessuquila
 */
public class RMIImpInterface extends UnicastRemoteObject implements StudentInterface{
    
    public RMIImpInterface() throws RemoteException{}

    @Override
    public StudentDetails readFromDatabase(int id) throws RemoteException {
        StudentDetails stud = null;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/PIHE2019", "root", "")) {
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM details WHERE stud_id = ?");
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                stud = new StudentDetails(rs.getInt("stud_id"), rs.getString("stud_fname"), rs.getString("stud_lname"), rs.getString("stud_contactNo"), rs.getString("stud_address"));
            }
            conn.close();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex.getMessage());
        }
        
        return stud;
    }
    
}
