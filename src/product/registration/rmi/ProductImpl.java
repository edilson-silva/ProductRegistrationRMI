package product.registration.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import product.registration.rmi.dao.ProductDAO;
import product.registration.rmi.model.Product;

/**
 * @author edilson-silva
 */
public class ProductImpl extends UnicastRemoteObject implements ProductService {
    
    private static ProductDAO productDAO;

    public ProductImpl() throws RemoteException {
        super();
        productDAO = new ProductDAO();
    }
    
    @Override
    public Product add(Product product) throws RemoteException {
        return productDAO.add(product);
    }

    @Override
    public boolean alter(Product product) throws RemoteException {
        return productDAO.alter(product);
    }

    @Override
    public boolean remmove(int productId) throws RemoteException {
        return productDAO.remmove(productId);
    }

    @Override
    public Product select(int productId) throws RemoteException {
        return productDAO.select(productId);
    }

    @Override
    public List<Product> selectAll() throws RemoteException {
        return productDAO.selectAll();
    }
    
}
