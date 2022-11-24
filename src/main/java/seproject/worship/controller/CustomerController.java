package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.domain.dto.request.CustomerLoginDTO;
import seproject.worship.domain.dto.request.CustomerModifyInfoDTO;
import seproject.worship.domain.dto.request.CustomerSaveDTO;
import seproject.worship.domain.dto.response.CustomerLoadInfoDTO;
import seproject.worship.service.CartService;
import seproject.worship.service.CustomerService;
import seproject.worship.service.OrderService;

import java.util.Map;

@RestController
@RequiredArgsConstructor

public class CustomerController {
    private final CustomerService customerService;
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/customer")
    public Map customerSave(@RequestBody CustomerSaveDTO dto){
        return customerService.customerSave(dto);
    }

    @PostMapping("/customer/login")
    public Map customerLogin(@RequestBody CustomerLoginDTO dto) { return customerService.customerLogin(dto);}

    @GetMapping("/customer/{customerId}")
    public CustomerLoadInfoDTO customerLoadInfo(@PathVariable Long customerId){
        return customerService.customerLoadInfo(customerId);
    }

    @PutMapping("/customer")
    public Map customerModifyInfo(@RequestBody CustomerModifyInfoDTO dto){
        return customerService.customerModifyInfo(dto);
    }

    @GetMapping("/customer/{customerId}/orders")
    public Map customerLoadOrderList(@PathVariable Long customerId){
        return orderService.customerLoadOrderList(customerId);
    }

    @GetMapping("/orders/{orderId}")
    public Map customerViewSpecificOrder(@PathVariable Long orderId) {
        return orderService.customerViewSpecificOrder(orderId);
    }

    @PostMapping("/customer/{customerId}/reorder/{orderId}")
    public Map customerReorder(@PathVariable Long customerId
                               ,@PathVariable Long orderId){
        return cartService.customerReorder(customerId, orderId);
    }

}
