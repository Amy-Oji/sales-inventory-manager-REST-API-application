package com.amyojiakor.sales.inventory.manager.app.services;

import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderDTO;
import com.amyojiakor.sales.inventory.manager.app.models.dto.CustomerOrderResponse;

public interface CustomerService {

    CustomerOrderResponse placeOrder(CustomerOrderDTO customerOrderDTO) throws Exception;
}
