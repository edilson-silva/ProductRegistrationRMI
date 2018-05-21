package product.registration.rmi;

import product.registration.rmi.model.Product;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * @author edilson-silva
 */
public interface ProductService extends Remote {
    
    public Product add(Product product) throws RemoteException;
    
    public boolean alter(Product product) throws RemoteException;
    
    public boolean remmove(int productId) throws RemoteException;
    
    public Product select(int productId) throws RemoteException;
    
    public List<Product> selectAll() throws RemoteException;
    
}
