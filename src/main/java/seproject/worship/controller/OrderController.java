package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.response.ViewSpecificMenuDTO;
import seproject.worship.dto.response.beforeOrderDTO;
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
}
