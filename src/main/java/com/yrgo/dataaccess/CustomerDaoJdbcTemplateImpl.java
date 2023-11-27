package com.yrgo.dataaccess;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import javax.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;


public class CustomerDaoJdbcTemplateImpl implements CustomerDao {
    private static final String DELETE_SQL = "DELETE FROM CUSTOMER WHERE CustomerId=?";
    private static final String UPDATE_SQL = "UPDATE CUSTOMER SET customerId=?, companyName=?, email=?, telephone=?, notes=? WHERE customerId=?";
    private static final String INSERT_SQL = "INSERT INTO CUSTOMER (customerId, companyName, email, telephone, notes) VALUES (?,?,?,?,?)";
    private static final String GET_CustomerById_SQL = "SELECT customerId, companyName, email, telephone, notes FROM CUSTOMER WHERE customerId=?";
    private static final String GET_CustomerByName_SQL = "SELECT customerId, companyName, email, telephone, notes FROM CUSTOMER WHERE companyName=?";
    private static final String GET_AllCustomers_SQL = "SELECT * FROM CUSTOMER";
    private static final String GET_AllCALLS_SQL = "SELECT * FROM CUSTOMERCALL WHERE customerId=?";
    private static final String INSERT_CAll_SQL = "INSERT INTO CUSTOMERCALL (customerId, notes, timeAndDate) VALUES (?,?,?)";

    private JdbcTemplate template;

    public CustomerDaoJdbcTemplateImpl(JdbcTemplate template) {
        this.template = template;
    }
    @PostConstruct
    private void createTables() throws BadSqlGrammarException {
        try{
            this.template.update("CREATE TABLE CUSTOMER (customerId varchar(60), companyName VARCHAR(255), email VARCHAR(255), telephone VARCHAR(20), notes VARCHAR(255))");
            this.template.update("CREATE TABLE CUSTOMERCALL (id int , customerId varchar(60),timeAndDate DATE, notes VARCHAR(255))");
        }catch(Exception e){
            System.err.println("Table already exists");
        }


    }

    @Override
    public void create(Customer customer) {
            this.template.update(INSERT_SQL,customer.getCustomerId(),customer.getCompanyName(),customer.getEmail(),customer.getTelephone(),customer.getNotes());
    }


    @Override
    public Customer getById(String customerId) throws RecordNotFoundException{

        return this.template.queryForObject(GET_CustomerById_SQL, new CustomerRowMapper(), customerId);

    }

    @Override
    public List<Customer> getByName(String name) {

        return this.template.query(GET_CustomerByName_SQL, new CustomerRowMapper(), name);
    }
    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
         this.template.update(UPDATE_SQL, customerToUpdate.getCustomerId(),customerToUpdate.getCompanyName(),customerToUpdate.getEmail(), customerToUpdate.getTelephone(),customerToUpdate.getNotes());
    }

    @Override
    public void delete(Customer oldCustomer) throws RecordNotFoundException {
        this.template.update(DELETE_SQL, oldCustomer.getCustomerId());
    }

    @Override
    public List<Customer> getAllCustomers() {
        return this.template.query(GET_AllCustomers_SQL, new CustomerRowMapper());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws RecordNotFoundException {
        Customer fullCustomer = this.template.queryForObject(GET_CustomerById_SQL, new CustomerRowMapper(), customerId);
        fullCustomer.setCalls(this.template.query(GET_AllCALLS_SQL, new Calls(), customerId));
        return fullCustomer;
    }

    @Override
    public void addCall(Call newCall, String customerId) throws RecordNotFoundException {
            this.template.update(INSERT_CAll_SQL, customerId, newCall.getNotes(), newCall.getTimeAndDate());

    }

}

class CustomerRowMapper implements RowMapper<Customer>{

    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String customerId = rs.getString(1);
        String companyName = rs.getString(2);
        String email = rs.getString(3);
        String telephone = rs.getString(4);
        String notes = rs.getString(5);

        return new Customer(customerId,companyName,email,telephone,notes);

    }

}
class Calls implements RowMapper<Call>{

    @Override
    public Call mapRow(ResultSet rs, int rowNum) throws SQLException {
        int id = rs.getInt(1);
        Date dateAndTime = rs.getDate(3);
        String notes = rs.getString(4);

        return new Call(notes, dateAndTime);

    }
}

