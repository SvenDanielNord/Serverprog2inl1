
package com.yrgo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.calls.CallHandlingService;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("application.xml");

        CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
        CallHandlingService callService = container.getBean(CallHandlingService.class);
        DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

        customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));

        Call newCall = new Call("Larry Wall called from Acme Corp");
        Call newCall2 = new Call("Larry is drunk talks jibbrisch");

        try{
            customerService.recordCall("CS03939", newCall);
            customerService.recordCall("CS03939", newCall2);
            System.out.println(customerService.getFullCustomerDetail("CS03939"));


        }catch (CustomerNotFoundException e){
            System.out.println("That customer doesn't exist");
        }



        container.close();
    }
}
