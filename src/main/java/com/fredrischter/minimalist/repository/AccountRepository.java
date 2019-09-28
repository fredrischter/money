package com.fredrischter.minimalist.repository;

import com.fredrischter.minimalist.model.Account;
import com.fredrischter.minimalist.repository.exceptions.RepositoryException;

import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {

    public AccountRepository() {
        execute("create table if not exists account(number varchar(255) primary key, balance decimal);");
    }

    public static void drop() {
        execute("drop table account;");
    }

    public List<Account> query(String sql) {
        try {
            Connection conn = createConnection();
            Statement st = conn.createStatement();
            List<Account> resultList = new LinkedList();
            ResultSet resultSet = st.executeQuery(sql);
            while (resultSet.next()) {
                String number = resultSet.getString("number");
                BigDecimal balance = resultSet.getBigDecimal("balance");
                Account user = new Account(number, balance);
                resultList.add(user);
            }
            st.close();
            conn.close();
            return resultList;
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    public static void execute(String sql) {
        try {
            Connection conn = createConnection();
            Statement st = conn.createStatement();
            st.execute(sql);
            st.close();
            conn.close();
        } catch (Exception e) {
            throw new RepositoryException(e);
        }
    }

    private static Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        return DriverManager.getConnection("jdbc:h2:mem:~minimalist;DB_CLOSE_DELAY=-1", "sa", "");
    }

    public Optional<Account> findByNumber(String accountNumber) {
        List<Account> resultSets = query("select * from account where number = '"+accountNumber+"'");
        if (resultSets.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(resultSets.iterator().next());
    }

    public void updateBalance(String accountNumber, BigDecimal balance) {
        execute("update account set balance = "+balance+" where number = '"+accountNumber+"'");
    }

    public void create(Account accountEntity) {
        execute("insert into account(number, balance) values("+accountEntity.getNumber()+","+accountEntity.getBalance()+")");
    }
}
