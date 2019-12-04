/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI_Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import student.StudentDetails;

/**
 *
 * @author moisessuquila
 */
public interface StudentInterface extends Remote{
    public StudentDetails readFromDatabase(int id) throws RemoteException;
}
