package com.jdbc.hacaton1.dao;

import com.jdbc.hacaton1.models.MineProducts;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductsDao {
    private Connection connection() {
        Connection connection = null;

        try {
            String URL = "jdbc:postgresql://localhost:5432/Hackaton#1";
            String USER = "postgres";
            String PASSWORD = "postgres";

            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return connection;
    }

    public List<ProductsFeed> getProductsFeed() {

        List<ProductsFeed> productsFeed = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, u.login from products p " +
                     "join users u on p.users_id = u.id;";

        try (Connection connection = connection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SQL)) {

            while (resultSet.next()) {
                ProductsFeed product = new ProductsFeed();

                product.setId(resultSet.getInt(1));
                product.setCategory(resultSet.getString(2));
                product.setUrlToPhoto(resultSet.getString(3));
                product.setUsersName(resultSet.getString(4));

                productsFeed.add(product);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productsFeed;
    }

    public ProductWithSeller getProductById(Integer id) {
        ProductWithSeller product = new ProductWithSeller();

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description, u.login " +
                     "from products p join users u on p.users_id = u.id " +
                     "where p.id = ?";

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product.setId(resultSet.getInt(1));
                    product.setCategory(resultSet.getString(2));
                    product.setUrlToPhoto(resultSet.getString(3));
                    product.setFabric(resultSet.getString(4));
                    product.setSize(resultSet.getString(5));
                    product.setDescription(resultSet.getString(6));
                    product.setUsersName(resultSet.getString(7));
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return product;
    }

    public Integer createProduct(ProductsModel product) {
        int productId = 0;

        String SQL = "Insert into products (category, url_photo, fabric, size, description," +
                     "users_id) " +
                     "values(?,?,?,?,?,?) returning id";

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setString(1, product.getCategory());
            statement.setString(2, product.getUrlPhoto());
            statement.setString(3, product.getFabric());
            statement.setString(4, product.getSize());
            statement.setString(5, product.getDescription());
            statement.setInt(6, product.getUsersId());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                productId = resultSet.getInt(1);
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return productId;
    }
    public List<ProductsModel> getProductByUserId(Integer id) {

        List<ProductsModel> products = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description " +
                     "from products p join users u on p.users_id = u.id " +
                     "where u.id = ?";

        try (Connection connection = connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    ProductsModel product = new ProductsModel();
                    product.setId(resultSet.getInt(1));
                    product.setCategory(resultSet.getString(2));
                    product.setUrlPhoto(resultSet.getString(3));
                    product.setFabric(resultSet.getString(4));
                    product.setSize(resultSet.getString(5));
                    product.setDescription(resultSet.getString(6));
                    product.setUsersId(id);

                    products.add(product);
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        return products;
    }

    public List<ProductsFeed> getAllProductsWithoutEvaluation(Integer userId){
        List<ProductsFeed> products = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, u.login from products p " +
                     "join users u on u.id = p.users_id " +
                     "left join evaluate_product ep on ep.product_id = p.id and ep.user_id = ? " +
                     "where ep.id is null and p.users_id != ?";

        try(Connection connection = connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1, userId);
            statement.setInt(2, userId);

            ResultSet resultSet = statement.executeQuery();

            while(resultSet.next()){
                ProductsFeed product = new ProductsFeed();

                product.setId(resultSet.getInt(1));
                product.setCategory(resultSet.getString(2));
                product.setUrlToPhoto(resultSet.getString(3));
                product.setUsersName(resultSet.getString(4));

                products.add(product);
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return products;
    }

    public List<MineProducts> getAllMineProducts(Integer userID){
        List<MineProducts> products = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description, avg(ep.evaluate), count(distinct c.id) from products p " +
                     "join users u on p.users_id = u.id " +
                     "left join evaluate_product ep on p.id = ep.product_id " +
                     "left join comments c on p.id = c.product_id " +
                     "where p.users_id = ?\n" +
                     "group by p.id";

        try(Connection connection = connection();
            PreparedStatement statement = connection.prepareStatement(SQL)){

            statement.setInt(1,userID);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()){
                MineProducts product = new MineProducts();

                product.setId(resultSet.getInt(1));
                product.setCategory(resultSet.getString(2));
                product.setUrlPhoto(resultSet.getString(3));
                product.setFabric(resultSet.getString(4));
                product.setSize(resultSet.getString(5));
                product.setDescription(resultSet.getString(6));
                product.setAvgEvaluation(resultSet.getDouble(7));
                product.setCountOfComments(resultSet.getInt(8));

                products.add(product);
            }
        }catch (SQLException sqlException){
            System.out.println(sqlException.getMessage());
        }
        return products;
    }
}
