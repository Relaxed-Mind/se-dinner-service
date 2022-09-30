package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.dto.request.CustomerSaveDTO;
import seproject.worship.entity.Customer;
import seproject.worship.repository.CustomerRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer CustomerSaveDTOtoEntity(CustomerSaveDTO dto){
        return Customer.builder()
                .id(dto.getId())
                .pw(dto.getPw())
                .name(dto.getName())
                .address(dto.getAddress())
                .cardNum(dto.getCardNum())
                .build();
    }

    public Map saveCustomer(CustomerSaveDTO dto) {
        Customer customer = CustomerSaveDTOtoEntity(dto);
        //예외처리 추가해야함
        customerRepository.save(customer);

        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customer.getCustomerId());
        return map;
    }
}
