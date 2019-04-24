package learnDatabase;

import dbProduct.Product;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbMenu extends DbAdapter {

    public void createTables() {
        try {
            statement = connection.createStatement();

            statement.execute("CREATE TABLE IF NOT EXISTS products (idproduct INT primary key unique auto_increment," +
                    "productName text not null," +
                    "productPrice DOUBLE not null)");

            statement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setup() {
        int numberOfRows = -1;

        try {
            PreparedStatement stat = connection.prepareStatement("SELECT count(*) FROM `products`");
            ResultSet resultSet = stat.executeQuery();
            while (resultSet.next()) {
                numberOfRows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (numberOfRows == 0) {
            System.out.println("Number of rows: " + numberOfRows);

            insertProduct("Espresso", 1.30);
            insertProduct("Americano", 1.20);
            insertProduct("Lette", 2.30);
        }
    }

    public void insertProduct(String productName, Double productPrice) {
    
        System.out.println("Insert product " + productName + "with the proce " + productPrice);
        
        try {
            PreparedStatement stat = connection.prepareStatement("INSERT INTO PRODUCTS (productName," +
                    "productPrice) VALUES (?, ?)");
            stat.setString(1, productName);
            stat.setDouble(2, productPrice);
            stat.executeUpdate();
            stat.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List selectAllProducts() {
        List<Product> allProducktsList = new ArrayList();

        try {
            PreparedStatement stat = connection.prepareStatement("SELECT idproduct, productName, productPrice " +
                    "FROM products");
            ResultSet result = stat.executeQuery();
            while (result.next()) {
                int idproduct = result.getInt(1);
                String productName = result.getString(2);
                Double productPrice = result.getDouble(3);

                Product tempProduct = new Product(idproduct, productName, productPrice);
                allProducktsList.add(tempProduct);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allProducktsList;
    }

    public void updateProduct(int idproduct, String productName, double productPrice) {
    
        System.out.println("Updeate item number " + idproduct + " with name = " + productName + " price = " + productPrice);
        
        try {
            if (!(productName.equals(""))) {
                PreparedStatement stat = connection.prepareStatement("UPDATE products SET productName=? WHERE idproduct=?");
                stat.setString(1, productName);
                stat.setInt(2, idproduct);
                stat.executeUpdate();
                stat.close();
            }
            if (productPrice > 0) {
                PreparedStatement stat = connection.prepareStatement("UPDATE products SET productPrice=? WHERE idproduct=?");
                stat.setDouble(1, productPrice);
                stat.setInt(2, idproduct);
                stat.executeUpdate();
                stat.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteProduct(int idproduct) {
        try {
            PreparedStatement st = connection.prepareStatement("DELETE FROM products WHERE idproduct=?");
            st.setInt(1, idproduct);
            st.executeUpdate();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
