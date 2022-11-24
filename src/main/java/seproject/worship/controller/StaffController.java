package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.domain.dto.request.StaffAcceptOrderDTO;
import seproject.worship.domain.dto.request.StaffChangeOrderStatusDTO;
import seproject.worship.domain.dto.request.StaffLoginDTO;
import seproject.worship.domain.dto.request.StaffRefuseOrderDTO;
import seproject.worship.domain.dto.response.StaffViewSpecificOrderDTO;
import seproject.worship.service.StaffService;

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

    @GetMapping("/staff/orders/acceptance")
    public Map staffLoadAcceptOrderList(){
        return staffService.staffLoadAcceptOrderList();
    }

}
