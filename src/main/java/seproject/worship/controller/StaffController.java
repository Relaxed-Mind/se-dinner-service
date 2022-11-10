package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.dto.request.StaffAcceptOrderDTO;
import seproject.worship.dto.request.StaffChangeOrderStatusDTO;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.dto.request.StaffRefuseOrderDTO;
import seproject.worship.dto.response.StaffLoadOrderListDTO;
import seproject.worship.dto.response.StaffViewSpecificOrderDTO;
import seproject.worship.entity.OrderMenu;
import seproject.worship.service.StaffService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/staff")
    public Map staffLogin(@RequestBody StaffLoginDTO staffLoginDTO){
        return staffService.staffLogin(staffLoginDTO);
    }

    @PostMapping("/staff/order/status")
    public Map staffChangeOrderStatus(@RequestBody StaffChangeOrderStatusDTO staffChangeOrderStatusDTO) {
        return staffService.staffChangeOrderStatus(staffChangeOrderStatusDTO);
    }

    @GetMapping("/staff/orders")
    public Map staffLoadOrderList(){
        return staffService.staffLoadOrderList();
    }

    @PostMapping("/staff/order/refusal")
    public Map staffRefuseOrder(@RequestBody StaffRefuseOrderDTO staffRefuseOrderDTO){
        return staffService.staffRefuseOrder(staffRefuseOrderDTO);
    }

    @PostMapping("/staff/order/acceptance")
    public Map staffAcceptOrder(@RequestBody StaffAcceptOrderDTO staffAcceptOrderDTO){
        return staffService.staffAcceptOrder(staffAcceptOrderDTO);
    }

    @GetMapping("/staff/order/{orderId}")
    public StaffViewSpecificOrderDTO staffViewSpecificOrder(@PathVariable Long orderId){

        return staffService.staffViewSpecificOrder(orderId);
    }

    @GetMapping("/staff/orders/acceptace")
    public Map staffLoadAcceptOrderList(){
        return staffService.staffLoadAcceptOrderList();
    }
}
