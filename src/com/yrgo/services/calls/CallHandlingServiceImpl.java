package com.yrgo.services.calls;

import com.yrgo.domain.Action;
import com.yrgo.domain.Call;
import com.yrgo.domain.Customer;
import com.yrgo.services.customers.CustomerManagementMockImpl;
import com.yrgo.services.customers.CustomerManagementService;
import com.yrgo.services.customers.CustomerNotFoundException;
import com.yrgo.services.diary.DiaryManagementService;

import java.util.Collection;
import java.util.List;

public class CallHandlingServiceImpl implements CallHandlingService{
    private CustomerManagementService customerManagementService;
    private DiaryManagementService diaryManagementService;

    public CallHandlingServiceImpl(CustomerManagementMockImpl customerManagementService, DiaryManagementService diaryManagementService) {
        this.customerManagementService = customerManagementService;
        this.diaryManagementService = diaryManagementService;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customerManagementService.recordCall(customerId,newCall);
        for (Action a: actions) {
            diaryManagementService.recordAction(a);
        }




    }


}
