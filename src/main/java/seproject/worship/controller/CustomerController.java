package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.dto.request.CustomerLoginDTO;
import seproject.worship.dto.request.CustomerModifyInfoDTO;
import seproject.worship.dto.request.CustomerSaveDTO;
import seproject.worship.dto.response.CustomerLoadInfoDTO;
import seproject.worship.service.CustomerService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    public Map customerSave(@RequestBody CustomerSaveDTO dto){
        return customerService.customerSave(dto);
    }

    @PostMapping("/login")
    public Map customerLogin(@RequestBody CustomerLoginDTO dto) { return customerService.customerLogin(dto);}

    @GetMapping("/{customerId}")
    public CustomerLoadInfoDTO customerLoadInfo(@PathVariable Long customerId){
        return customerService.customerLoadInfo(customerId);
    }

    @PutMapping()
    public Map customerModifyInfo(@RequestBody CustomerModifyInfoDTO dto){
        return customerService.customerModifyInfo(dto);
    }

    @GetMapping("/{customerId}/orders")
    public Map customerLoadOrderList(@PathVariable Long customerId){
        return customerService.customerLoadOrderList(customerId);
    }


}
