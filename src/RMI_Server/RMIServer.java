/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RMI_Server;

import RMI_Implement.RMIImpInterface;
import RMI_Interface.StudentInterface;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.*;

/**
 *
 * @author moisessuquila
 */
public class RMIServer {
    public static void main(String[] args) throws RemoteException, AlreadyBoundException {
        StudentInterface skeleton = new RMIImpInterface();
        Registry reg = LocateRegistry.createRegistry(8080);
        reg.bind("GetStudent", skeleton);
        System.out.println("******** Server Online ********");
    }
}
