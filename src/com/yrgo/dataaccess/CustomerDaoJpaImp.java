package com.yrgo.dataaccess;


import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
@Repository
public class CustomerDaoJpaImp implements CustomerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Customer customer) {
        em.persist(customer);
    }

    @Override
    public Customer getById(String customerId) throws RecordNotFoundException, CustomerNotFoundException {
        try {
//            return(Customer)em.createQuery("SELECT customerId, companyName, email, telephone, notes FROM CUSTOMER WHERE customerId=?").setParameter("customerId", customerId).getSingleResult();
            Customer customer = em.find(Customer.class, customerId);
            return customer;

        } catch (javax.persistence.NoResultException e) {
            throw new CustomerNotFoundException();
        }
    }


    @Override
    public List<Customer> getByName(String name) throws RecordNotFoundException {
        return em.createQuery("SELECT customer FROM Customer as customer WHERE customer.companyName= :companyName", Customer.class).setParameter("companyName", name).getResultList();
    }

    @Override
    public void update(Customer customerToUpdate) throws RecordNotFoundException {
//        em.createQuery("UPDATE CUSTOMER SET customerId="+customerToUpdate.getCustomerId()+", companyName="+customerToUpdate.getCompanyName()+", email="+ customerToUpdate.getEmail()+", telephone="+customerToUpdate.getTelephone()+", notes="+customerToUpdate.getNotes()+" WHERE customerId="+customerToUpdate.getCustomerId());

        Customer existingCustomer = em.find(Customer.class, customerToUpdate.getCustomerId());

        if (existingCustomer != null) {
            existingCustomer.setCompanyName(customerToUpdate.getCompanyName());
            existingCustomer.setEmail(customerToUpdate.getEmail());
            existingCustomer.setTelephone(customerToUpdate.getTelephone());
            existingCustomer.setNotes(customerToUpdate.getNotes());

            em.merge(existingCustomer);
        }
    }

        @Override
        public void delete (Customer oldCustomer) throws RecordNotFoundException {
            Customer customer = em.find(Customer.class, oldCustomer.getCustomerId());
            em.remove(customer);
        }

        @Override
        public List<Customer> getAllCustomers () throws RecordNotFoundException {
            return em.createQuery("SELECT customer FROM Customer as customer", Customer.class).getResultList();
        }

        @Override
        public Customer getFullCustomerDetail (String customerId) throws RecordNotFoundException {
           return (Customer) em.createQuery("SELECT customer FROM Customer as customer left join fetch customer.calls WHERE customer.customerId =: customerId").setParameter("customerId", customerId).getSingleResult();


        }

        @Override
        public void addCall (Call newCall, String customerId) throws RecordNotFoundException {
            Customer customer = em.find(Customer.class, customerId);
            customer.addCall(newCall);

        }
    }
