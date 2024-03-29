package com.jdbc.hacaton1.dao.impl;

import com.jdbc.hacaton1.dao.ProductsDao;
import com.jdbc.hacaton1.databaseConfig.DatabaseConfiguration;
import com.jdbc.hacaton1.models.MineProducts;
import com.jdbc.hacaton1.models.ProductWithSeller;
import com.jdbc.hacaton1.models.ProductsFeed;
import com.jdbc.hacaton1.models.ProductsModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor

public class ProductsDaoImpl implements ProductsDao {

    private final DatabaseConfiguration database;

    @Override
    public List<ProductsFeed> getProductsFeed() throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - getProductsFeed()");

        List<ProductsFeed> productsFeed = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, u.login from products p " +
                "join users u on p.users_id = u.id " +
                "where p.delete_time is null ";

        try (Connection connection = database.connection();
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
            if (productsFeed.isEmpty()) {
                throw new NullPointerException();
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getProductsFeed()");
        return productsFeed;
    }

    @Override
    public ProductWithSeller getProductById(Integer id) throws NullPointerException {
        log.info("STARTED : ProductsDaoImpl - getProductById() - {}", id);

        ProductWithSeller product = null;

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description, u.login " +
                "from products p join users u on p.users_id = u.id " +
                "where p.id = ? and p.delete_time is null";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    product = new ProductWithSeller();
                    product.setId(resultSet.getInt(1));
                    product.setCategory(resultSet.getString(2));
                    product.setUrlToPhoto(resultSet.getString(3));
                    product.setFabric(resultSet.getString(4));
                    product.setSize(resultSet.getString(5));
                    product.setDescription(resultSet.getString(6));
                    product.setUsersName(resultSet.getString(7));

                }
                if(product == null){
                    throw new NullPointerException();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getProductById() - {}", id);
        return product;
    }

    @Override
    public Integer createProduct(ProductsModel product) throws RuntimeException{
        log.info("STARTED : ProductsDaoImpl - createProduct() - {}", product);

        int productId = -1;

        String selectSQL = "select * from users where id = ?";
        String insertSQL = "insert into products (category, url_photo, fabric, size, description, users_id)" +
                "values (?, ?, ?, ?, ?, ?) returning id ";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement insertStatement = connection.prepareStatement(insertSQL)) {

            selectStatement.setInt(1, product.getUsersId());
            ResultSet resultSet = selectStatement.executeQuery();

            if (resultSet.next() && resultSet.getDate("delete_time") == null) {

                insertStatement.setString(1, product.getCategory());
                insertStatement.setString(2, product.getUrlPhoto());
                insertStatement.setString(3, product.getFabric());
                insertStatement.setString(4, product.getSize());
                insertStatement.setString(5, product.getDescription());
                insertStatement.setInt(6, product.getUsersId());

                resultSet = insertStatement.executeQuery();
                if (resultSet.next()) {
                    productId = resultSet.getInt(1);
                }
                else throw new RuntimeException("Failed to add product to database");
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - createProduct(), product id = {}", productId);
        return productId;
    }

    @Override
    public List<ProductsModel> getProductsByUserId(Integer id) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - getProductsByUserId() {}", id);


        List<ProductsModel> products = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description " +
                "from products p " +
                "join users u on p.users_id = u.id " +
                "where u.id = ? and p.delete_time is null and u.delete_time is null ";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

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
            if (products.isEmpty()) {
                throw new NullPointerException();
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getProductsByUserId() {}", products);
        return products;
    }

    @Override
    public List<ProductsFeed> getAllProductsWithoutEvaluation(Integer userId) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - getAllProductsWithoutEvaluation() {}", userId);

        List<ProductsFeed> products = new ArrayList<>();

        String select = "select delete_time from users where id = ?";
        String findSQL = "select p.id, p.category, p.url_photo, u.login from products p " +
                "join users u on u.id = p.users_id " +
                "left join evaluate_product ep on ep.product_id = p.id and ep.user_id = ? " +
                "where ep.id is null and p.users_id != ? and p.delete_time is null ";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(select);
             PreparedStatement statement = connection.prepareStatement(findSQL)) {

            selectStatement.setInt(1, userId);
            ResultSet selectResult = selectStatement.executeQuery();
            if(selectResult.next() && selectResult.getDate("delete_time") == null) {

                statement.setInt(1, userId);
                statement.setInt(2, userId);

                ResultSet resultSet = statement.executeQuery();

                while (resultSet.next()) {
                    ProductsFeed product = new ProductsFeed();

                    product.setId(resultSet.getInt(1));
                    product.setCategory(resultSet.getString(2));
                    product.setUrlToPhoto(resultSet.getString(3));
                    product.setUsersName(resultSet.getString(4));

                    products.add(product);
                }
                if (products.isEmpty()) {
                    throw new NullPointerException();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getAllProductsWithoutEvaluation() {}", products);
        return products;
    }

    @Override
    public List<MineProducts> getAllMineProducts(Integer userId) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - getAllMineProducts() {}", userId);

        List<MineProducts> products = new ArrayList<>();

        String SQL = "select p.id, p.category, p.url_photo, p.fabric, p.size, p.description," +
                " avg(ep.evaluate), count(distinct c.id) from products p " +
                "join users u on p.users_id = u.id " +
                "left join evaluate_product ep on p.id = ep.product_id " +
                "left join comments c on p.id = c.product_id " +
                "where p.users_id = ? and u.delete_time is null and " +
                "p.delete_time is null and ep.delete_time is null and c.delete_time is null " +
                "group by p.id";

        try (Connection connection = database.connection();
             PreparedStatement statement = connection.prepareStatement(SQL)) {

            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
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
            if (products.isEmpty()) {
                throw new NullPointerException();
            }

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getAllMineProducts() {}", userId);
        return products;
    }

    @Override
    public ProductsFeed getProductWithoutEvaluationRandomly(Integer userId) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - getProductWithoutEvaluationRandomly() {}", userId);

        ProductsFeed product = null;

        String select = "select delete_time from users where id = ?";
        String findSQL = "select p.id, p.category, p.url_photo, u.login from products p " +
                "join users u on u.id = p.users_id " +
                "left join evaluate_product ep on ep.product_id = p.id and ep.user_id = ? " +
                "where ep.id is null and p.users_id != ? and p.delete_time is null " +
                "order by random() limit 1";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(select);
             PreparedStatement statement = connection.prepareStatement(findSQL)) {

            selectStatement.setInt(1, userId);
            ResultSet selectResult = selectStatement.executeQuery();
            if(selectResult.next() && selectResult.getDate("delete_time") == null) {

                statement.setInt(1, userId);
                statement.setInt(2, userId);

                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        product = new ProductsFeed();
                        product.setId(resultSet.getInt(1));
                        product.setCategory(resultSet.getString(2));
                        product.setUrlToPhoto(resultSet.getString(3));
                        product.setUsersName(resultSet.getString(4));
                    } else throw new NullPointerException();
                }
            }
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - getProductWithoutEvaluationRandomly() {}", product);
        return product;
    }

    @Override
    public String updateProductById(Integer id, ProductsModel product) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - updateProductById() {}", id);


        String selectSQL = "select * from products where id = ? ";
        String updateSQL = "update products " +
                "set category = ?, url_photo = ?, fabric = ?, size = ?, description = ? " +
                "where id = ?";

        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            selectStatement.setInt(1, id);
            ResultSet selectResult = selectStatement.executeQuery();

            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                updateStatement.setString(1, product.getCategory());
                updateStatement.setString(2, product.getUrlPhoto());
                updateStatement.setString(3, product.getFabric());
                updateStatement.setString(4, product.getSize());
                updateStatement.setString(5, product.getDescription());
                updateStatement.setInt(6, id);

                updateStatement.executeUpdate();

            } else throw new NullPointerException();

        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - updateProductById() {}", id);
        return "Product with id " + id + " is updated";
    }

    @Override
    public String deleteProductById(Integer id) throws NullPointerException{
        log.info("STARTED : ProductsDaoImpl - deleteProductById() {}", id);


        String selectSQL = "select * from products where id = ?";

        String deleteSQL = "update comments " +
                "set delete_time = ? " +
                "where product_id = ? ; " +

                "update evaluate_product " +
                "set delete_time = ? " +
                "where product_id = ?; " +

                "update products " +
                "set delete_time = ? " +
                "where id = ? ";


        try (Connection connection = database.connection();
             PreparedStatement selectStatement = connection.prepareStatement(selectSQL);
             PreparedStatement deleteStatement = connection.prepareStatement(deleteSQL)) {

            selectStatement.setInt(1, id);
            ResultSet selectResult = selectStatement.executeQuery();

            if (selectResult.next() && selectResult.getDate("delete_time") == null) {

                deleteStatement.setDate(1, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(2, id);
                deleteStatement.setDate(3, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(4, id);
                deleteStatement.setDate(5, new Date(System.currentTimeMillis()));
                deleteStatement.setInt(6, id);

                deleteStatement.executeUpdate();
            } else throw new NullPointerException();
        } catch (SQLException sqlException) {
            System.out.println(sqlException.getMessage());
        }
        log.info("FINISHED : ProductsDaoImpl - deleteProductById() {}", id);
        return "Product with ID " + id + " was deleted!";
    }
}