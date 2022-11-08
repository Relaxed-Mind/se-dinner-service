package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.request.StaffChangeOrderStatusDTO;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.dto.response.StaffLoadOrderListDTO;
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

    @PostMapping("/staff/orders/status")
    public Long staffChangeOrderStatus(@RequestBody StaffChangeOrderStatusDTO staffChangeOrderStatusDTO) {
        return staffService.staffChangeOrderStatus(staffChangeOrderStatusDTO);
    }

    @GetMapping("/staff/orders")
    public Map staffLoadOrderList(){
        return staffService.staffLoadOrderList();
    }

}
