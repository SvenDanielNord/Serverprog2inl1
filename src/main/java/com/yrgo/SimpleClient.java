
package com.yrgo;

import com.yrgo.domain.Action;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.calls.CallHandlingService;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

import java.util.GregorianCalendar;

public class SimpleClient {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext container = new ClassPathXmlApplicationContext("production-application.xml");

        CustomerManagementService customerService = container.getBean(CustomerManagementService.class);
        CallHandlingService callService = container.getBean(CallHandlingService.class);
        DiaryManagementService diaryService = container.getBean(DiaryManagementService.class);

         customerService.newCustomer(new Customer("CS03939", "Acme", "Good Customer"));



        Call newCall = new Call("Larry Wall called from Acme Corp");
        Call newCall2 = new Call("Larry is drunk talks jibbrisch");

       Action action1 = new Action("Call back Larry to ask how things are going", new GregorianCalendar(2016, 0, 0), "CS03939");
       Action action2 = new Action("Check our sales dept to make sure Larry is being tracked", new GregorianCalendar(2016, 0, 0), "CS03939");

       diaryService.recordAction(action1);
       diaryService.recordAction(action2);

        try{
            var customers = customerService.getAllCustomers();

            for (Customer c: customers
                 ) {
                System.out.println(c.getCompanyName());
            }


            customerService.recordCall("CS03939", newCall);
            customerService.recordCall("CS03939", newCall2);

            System.out.println(customerService.getFullCustomerDetail("CS03939"));
            System.out.println(diaryService.getAllIncompleteActions("CS03939"));



        }catch (CustomerNotFoundException e){
            System.out.println("That customer doesn't exist");
        }



        container.close();
    }
}
