package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.CustomerLoginDTO;
import seproject.worship.dto.request.CustomerModifyInfoDTO;
import seproject.worship.dto.request.CustomerSaveDTO;
import seproject.worship.dto.response.CustomerLoadInfoDTO;
import seproject.worship.entity.Customer;
import seproject.worship.repository.CustomerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public Customer CustomerSaveDTOtoEntity(CustomerSaveDTO dto){
        return Customer.builder()
                .cid(dto.getId())
                .pw(dto.getPw())
                .name(dto.getName())
                .address(dto.getAddress())
                .cardNum(dto.getCardNum())
                .phoneNum(dto.getPhoneNum())
                .build();
    }

    public Map customerSave(CustomerSaveDTO dto) {
        Customer customer = CustomerSaveDTOtoEntity(dto);
        //예외처리 추가해야함 (중복)

        customerRepository.save(customer);

        Map<String, Object> map = new HashMap<>();
        map.put("customerId", customer.getId());
        return map;
    }

    public Map customerLogin(CustomerLoginDTO dto) {
        String cid = dto.getCid();
        String pw = dto.getPw();
        Optional<Customer> customer = customerRepository.findByCidAndPw(cid, pw);
        if(customer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "login error");
        } else{
            Map<String, Object> map = new HashMap<>();
            map.put("customerId", customer.get().getId());
            map.put("cid", customer.get().getCid());
            return map;
        }
    }

    public CustomerLoadInfoDTO customerLoadInfo(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if(customer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "loadInfo error");
        }else{
            return new CustomerLoadInfoDTO(customer.get());
        }
    }

    @Transactional
    public Map customerModifyInfo(CustomerModifyInfoDTO dto) {
        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        if(customer.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found at modifying error");
        } else{
            customer.get().modifyInfo(dto.getAddress(), dto.getCardNum(), dto.getPhoneNum());
            if(customer.get().getAddress().equals(dto.getAddress())){ //영속성 컨텍스트 실험
                Map<String, Object> map = new HashMap<>();
                map.put("customerId", customer.get().getId());
                return map;
            }else{
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "modify logic error");
            }
        }
    }
}
