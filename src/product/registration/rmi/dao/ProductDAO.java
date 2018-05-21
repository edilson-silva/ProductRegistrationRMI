package product.registration.rmi.dao;

import product.registration.rmi.connection.ConnectionManager;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import product.registration.rmi.model.Product;

/**
 * @author edilson-silva
 */
public class ProductDAO {

    private static Connection connection;
    private static PreparedStatement statement;

    public Product add(Product product) throws RemoteException {
        try {
            connection = ConnectionManager.getConnection();
            String sql = "INSERT INTO product(name, description, price)"
                    + "VALUES(?,?,?);";

            statement = connection.prepareStatement(sql);
            statement.setString(1, product.getName());
            statement.setString(2, product.getDescription());
            statement.setDouble(3, product.getPrice());

            if (statement.executeUpdate() > 0) {
                product.setId(getId());                    
            }
            statement.close();
            connection.close();
            
            return product;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "ADD ERR > "+e.getMessage());
        }
        
        return null;
    }

    public boolean alter(Product product) throws RemoteException {
        try {
            connection = ConnectionManager.getConnection();
            String sql = "UPDATE product"
                    + "SET name = ?, description = ?, price = ? WHERE id = ?";

            statement.setInt(1, product.getId());
            statement.setString(2, product.getName());
            statement.setString(3, product.getDescription());
            statement.setDouble(4, product.getPrice());

            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean remmove(int productId) throws RemoteException {
        try {
            connection = ConnectionManager.getConnection();
            String sql = "DELETE FROM product WHERE id = ?";

            statement.setInt(1, productId);

            return statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Product select(int productId) throws RemoteException {
        Product product = null;

        try {
            connection = ConnectionManager.getConnection();
            String sql = "SELECT name, description, price FROM product"
                    + "WHERE id = ?;";

            statement = connection.prepareStatement(sql);
            statement.setInt(1, productId);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                String name = rs.getString("name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");

                product = new Product(productId, name, description, price);
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return product;
    }

    public List<Product> selectAll() throws RemoteException {
        List<Product> products = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();
            String sql = "SELECT id, name, description, price FROM product;";

            statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String description = rs.getString("description");
                float price = rs.getFloat("price");

                Product product = new Product(id, name, description, price);
                products.add(product);
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return products;
    }
    
    private int getId() throws RemoteException {
        int id = 0;
        try {
            connection = ConnectionManager.getConnection();
            String sql = "SELECT MAX(id) AS id FROM product;";

            statement = connection.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                id = rs.getInt("id");
            }
            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }

}
