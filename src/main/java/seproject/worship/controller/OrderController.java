package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.domain.dto.request.OrderDTO;
import seproject.worship.domain.dto.response.ViewSpecificMenuDTO;
import seproject.worship.domain.dto.response.beforeOrderDTO;
import seproject.worship.service.OrderService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/shop")
    public Map loadMenuList(){
        return orderService.loadMenuList();
    }

    @GetMapping("/shop/{menuId}")
    public ViewSpecificMenuDTO viewSpecificMenu(@PathVariable Long menuId){
        return orderService.viewSpecificMenu(menuId);
    }

    @GetMapping("/order/{customerId}")
    public beforeOrderDTO beforeOrder(@PathVariable Long customerId){
        return orderService.beforeOrder(customerId);
    }
    
    @PostMapping("/order")
    public Map order(@RequestBody OrderDTO dto){
        return orderService.order(dto);
    }

    @PostMapping("/orders/cancel/{orderId}")
    public Map customerCancelOrder(@PathVariable Long orderId){
        return orderService.customerCancelOrder(orderId);
    }
}
