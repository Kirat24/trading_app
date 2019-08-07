package ca.jrvs.apps.trading.controller;

import ca.jrvs.apps.trading.model.domain.MarketOrderDto;
import ca.jrvs.apps.trading.model.domain.SecurityOrder;
import ca.jrvs.apps.trading.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(path = "/order")
public class OrderController {
    private OrderService orderService;
    @Autowired
    public OrderController(OrderService service){
        this.orderService=service;
    }
    @PostMapping(path = "/marketOder")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public SecurityOrder sumbitOrder(@RequestBody MarketOrderDto marketOrderDto){
        try {
            return orderService.executeMarketOrder(marketOrderDto);
        }catch (Exception e){
            throw ResponseExceptionUtil.getResponseStatusException(e);
        }

    }
}
