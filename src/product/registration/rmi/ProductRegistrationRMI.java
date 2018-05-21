package product.registration.rmi;

import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.registry.LocateRegistry;
import javax.swing.JOptionPane;
import product.registration.rmi.util.ProjectMap;

/**
 * @author edilson-silva
 */
public class ProductRegistrationRMI {

    ProductRegistrationRMI() {
        try{
            System.setProperty(ProjectMap.RMI_PROPERTY_NAME, ProjectMap.RMI_PROPERTY_IP);
            LocateRegistry.createRegistry(ProjectMap.RMI_PROPERTY_PORT);
            
            ProductImpl productImpl = new ProductImpl();
            Naming.bind(ProjectMap.RMI_SERVICE_NAME, (Remote) productImpl);          
        } catch (Exception e){
            JOptionPane.showMessageDialog(null, "RemoteException > "+e.getMessage());
        }        
    }
    
    public static void main(String[] args) {
        new ProductRegistrationRMI();
    }
    
}
