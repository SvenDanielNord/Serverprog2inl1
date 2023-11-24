package com.yrgo.integrationstest;

import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
@ExtendWith(SpringExtension.class)
@ContextConfiguration( {"/other-tiers.xml", "/datasource-test.xml" } )
@Transactional
public class ManagingUserTest {
    @Autowired
    private CustomerManagementService cm;


    @Test
    public void testCreateCustomer() {
        // arrange

        String customerId = "1234abcd";
        Customer testCustomer = new Customer("1234abcd", "Grabbarna Grus", "Annoying Customer");

        cm.newCustomer(testCustomer);

        // act
        Customer foundCustomer = null;
        try {
          foundCustomer = cm.findCustomerById(customerId);
            assertEquals(testCustomer, foundCustomer, "Wrong customer!");
        } catch (CustomerNotFoundException e) {
            fail("No customer was found when one should have been!");

        }
    }




}

