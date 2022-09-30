package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.request.CustomerSaveDTO;
import seproject.worship.service.CustomerService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/user")
    public Map customerSave(@RequestBody CustomerSaveDTO dto){
        return customerService.saveCustomer(dto);
    }

}
