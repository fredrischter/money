package com.fredrischter.minimalist.repository;

import com.fredrischter.minimalist.model.Account;
import com.fredrischter.minimalist.repository.exceptions.RepositoryException;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {

    public AccountRepository() {
        execute("create table test(id int primary key, name varchar(255))");
    }

    public List<Account> query(String sql) {
        try {
            Connection conn = createConnection();
            Statement st = conn.createStatement();
            List<Account> resultList = new LinkedList();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String str = resultSet.getString("username");
                Account user = new Account();
                resultList.add(user);
            }
            st.close();
            conn.close();
            return resultList;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public void execute(String sql) {
        try {
            Connection conn = createConnection();
            Statement st = conn.createStatement();
            st.executeUpdate(sql);
            st.close();
            conn.close();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:~minimalist", "sa", "");
    }

    public Optional<Account> findById(String account) {
        return null;
    }

    public void save(Account originAccount) {
    }
}
