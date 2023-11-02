package com.yrgo;

import com.yrgo.services.customers.CustomerManagementService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SimpleClient {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new
                ClassPathXmlApplicationContext("application.xml");
        CustomerManagementService cms = container.getBean(CustomerManagementService.class);
        System.out.println(cms.getAllCustomers());
        container.close();
    }
}
