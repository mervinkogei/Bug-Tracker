package models.dao;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import models.User;

import java.util.List;

public class Sql2oUserDao implements UserDao{
    private final Sql2o sql2o;

    public Sql2oUserDao(Sql2o sql2o){
        this.sql2o = sql2o;
    }

    @Override
    public void add(User user){
        String sql = "INSERT INTO users (username, email) VALUES (:username, :email)";
        try (Connection con = sql2o.open()){
            int id = (int) con.createQuery(sql, true)
                    .bind(user)
                    .executeUpdate()
                    .getKey();
            user.setId(id);
        } catch (Sql2oException ex) {
            System.out.println(ex);
        }
    }
    @Override
    public List<User> getAll(){
        try (Connection con = sql2o.open()){
            return con.createQuery("SELECT * FROM users").executeAndFetch(User.class);
        }
    }
    @Override
    public void clearAll(){
        String sql = "DELETE from users";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql).executeUpdate();

        } catch (Sql2oException ex) {
            System.out.println(ex);
        }

    }

}
