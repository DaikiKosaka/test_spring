package jp.co.sss.test_spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.sss.test_spring.service.OrderService;

@Controller
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/{id}")
    public String getOrderDetail(@PathVariable Long id, Model model) {
        orderService.getOrderById(id).ifPresent(order -> model.addAttribute("order", order));
        return "purchase";
    }
}
