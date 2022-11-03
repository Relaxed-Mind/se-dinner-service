package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.request.CustomerLoginDTO;
import seproject.worship.dto.request.CustomerSaveDTO;
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

}
