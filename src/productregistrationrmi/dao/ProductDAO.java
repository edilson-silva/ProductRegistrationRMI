package productregistrationrmi.dao;

import productregistrationrmi.connection.ConnectionManager;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import productregistrationrmi.ProductService;
import productregistrationrmi.model.Product;

/**
 * @author edilson-silva
 */
public class ProductDAO implements ProductService {

    private static Connection connection;
    private static PreparedStatement statement;

    @Override
    public boolean add(Product product) throws RemoteException {
        try {
            if (product != null) {
                connection = ConnectionManager.getConnection();
                String sql = "INSERT INTO procuct(id, name, description, price)"
                        + "VALUES(?,?,?,?);";

                statement = connection.prepareStatement(sql);
                statement.setInt(1, product.getId());
                statement.setString(2, product.getName());
                statement.setString(3, product.getDescription());
                statement.setDouble(4, product.getPrice());

                return (statement.executeUpdate() > 0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
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

    @Override
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

    @Override
    public Product select(int productId) throws RemoteException {
        Product product = null;

        try {
            connection = ConnectionManager.getConnection();
            String sql = "SELECT id, name, description, price FROM procuct"
                    + "WHERE b.title = ?;";

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

    @Override
    public List<Product> selectAll() throws RemoteException {
        List<Product> products = new ArrayList<>();

        try {
            connection = ConnectionManager.getConnection();
            String sql = "SELECT id, name, description, price FROM procuct;";

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

}
