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



    public MySqlCategoryDao(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public List<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        String sql = "SELECT * FROM Categories;";


        try (Connection connection = getConnection()) {
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                Category category = mapRow(resultSet);
                categories.add(category);
//hi
            }
            return getAllCategories();

        } catch (SQLException e) {
            System.err.println("Error pls try again....");;
        }

        // get all categories
        return null;
    }

    @Override
    public Category getById(int categoryId) {
        String sql = "SELECT Category_id FROM Categories;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.err.println("Error"+e);
        }
        // get category by id
        return null;
    }

    @Override
    public Category create(Category category)
    {
        // create a new category
        return null;
    }

    @Override
    public void update(int categoryId, Category category)
    {
        // update category
    }

    @Override
    public void delete(int categoryId)
    {
        // delete category
    }

    private Category mapRow(ResultSet row) throws SQLException
    {
        int categoryId = row.getInt("category_id");
        String name = row.getString("name");
        String description = row.getString("description");

        Category category = new Category()
        {{
            setCategoryId(categoryId);
            setName(name);
            setDescription(description);
        }};

        return category;
    }

}
