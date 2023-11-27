package com.yrgo.services.customers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;


public class CustomerManagementMockImpl implements CustomerManagementService {
    private HashMap<String, Customer> customerMap;

    public CustomerManagementMockImpl() {
        customerMap = new HashMap<String, Customer>();
        customerMap.put("OB74", new Customer("OB74", "Fargo Ltd", "some notes"));
        customerMap.put("NV10", new Customer("NV10", "North Ltd", "some other notes"));
        customerMap.put("RM210", new Customer("RM210", "River Ltd", "some more notes"));
        customerMap.put("NV11", new Customer("NV11", "North Ltd", "some other notes"));
    }


    @Override
    public void newCustomer(Customer newCustomer) {
        customerMap.put(newCustomer.getCustomerId(), newCustomer);
    }

    @Override
    public void updateCustomer(Customer changedCustomer) {

        customerMap.put(changedCustomer.getCustomerId(), changedCustomer);

    }

    @Override
    public void deleteCustomer(Customer oldCustomer) {
        customerMap.remove(customerMap.get(oldCustomer.getCustomerId()));


    }

    @Override
    public Customer findCustomerById(String customerId) throws CustomerNotFoundException {

        return customerMap.get(customerId);
    }

    @Override
    public List<Customer> findCustomersByName(String name) {
        // TODO Auto-generated method stub

        List<Customer> customerListByName = new ArrayList<Customer>();

        for (Customer customer : customerMap.values()
        ) {
            if (customer.getCompanyName().equals(name)) {
                customerListByName.add(customer);
            }
        }


        return customerListByName;
    }

    @Override
    public List<Customer> getAllCustomers() {
        // TODO Auto-generated method stub
        return new ArrayList<Customer>(customerMap.values());
    }

    @Override
    public Customer getFullCustomerDetail(String customerId) throws CustomerNotFoundException {
        // TODO Auto-generated method stub

        return findCustomerById(customerId);
    }

    @Override
    public void recordCall(String customerId, Call callDetails) throws CustomerNotFoundException {
        //First find the customer
        Customer customer = customerMap.get(customerId);
        //Call the addCall on the customer
        customer.addCall(callDetails);
    }

}
