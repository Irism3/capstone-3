package org.yearup.data.mysql;

import com.mysql.cj.protocol.Resultset;
import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.yearup.data.CategoryDao;
import org.yearup.models.Category;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlCategoryDao extends MySqlDaoBase implements CategoryDao {


    public MySqlCategoryDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        String sql = "SELECT * FROM Categories;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Category category = mapRow(resultSet);
                categories.add(category);
            }
        } catch (SQLException e) {
           throw new RuntimeException("Error getting categories", e);
        }
        // get all categories
        return categories;
    }

    @Override
    public Category getById(int categoryId) {
        String sql = "SELECT * FROM Categories WHERE category_id = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

           preparedStatement.setInt(1,categoryId);

           try (ResultSet resultSet = preparedStatement.executeQuery()) {
               if (resultSet.next()) {
                   return mapRow(resultSet);
               }
           }
        } catch (SQLException e) {
            throw new RuntimeException("Error getting category Id", e);
        }
        // get category by id
        return null ;
    }

    @Override
    public Category create(Category category) {
        String sql = """
                INSERT TO categories (name, description)
                VALUES (?,?)
                """;

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2,category.getDescription());
            preparedStatement.executeQuery();

            try (ResultSet keys = preparedStatement.getGeneratedKeys()) {
                if (keys.next()) {
                    category.setCategoryId(keys.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error creating category",e);
        }
        // create a new category
        return category;
    }


    @Override
    public void update(int categoryId, Category category) {
        String sql = """
                UPDATE categories 
                SET name = ?, description = ?
                WHERE category_id = ?
                """;
        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, category.getName());
            preparedStatement.setString(2, category.getDescription());
            preparedStatement.setInt(3,categoryId);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // update category
    }

    @Override
    public void delete(int categoryId) {
        String sql = "DELETE FROM categories WHERE category_id = ?";

        try (Connection connection = getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1,categoryId);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting category",e);
        }
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category() {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
