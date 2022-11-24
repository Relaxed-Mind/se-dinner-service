package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.domain.dto.request.CustomerLoginDTO;
import seproject.worship.domain.dto.request.CustomerModifyInfoDTO;
import seproject.worship.domain.dto.request.CustomerSaveDTO;
import seproject.worship.domain.dto.response.CustomerLoadInfoDTO;
import seproject.worship.domain.entity.CartMenu;
import seproject.worship.domain.entity.Customer;
import seproject.worship.domain.entity.Order;
import seproject.worship.repository.CartMenuRepository;
import seproject.worship.repository.CustomerRepository;
import seproject.worship.repository.OrderRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CartMenuRepository cartMenuRepository;
    private final OrderRepository orderRepository;

    public Customer CustomerSaveDTOtoEntity(CustomerSaveDTO dto){
        return Customer.builder()
                .cid(dto.getCid())
                .pw(dto.getPw())
                .name(dto.getName())
                .address(dto.getAddress())
                .cardNum(dto.getCardNum())
                .phoneNum(dto.getPhoneNum())
                .build();
    }

    public Map customerSave(CustomerSaveDTO dto) {
        Customer customer = CustomerSaveDTOtoEntity(dto);
        Optional<Customer> idCheck = customerRepository.findByCid(customer.getCid());

        //id가 이미 존재
        if(idCheck.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "cid already exist");

        //유저가 이미 가입됨
        Optional<Customer> phoneNumCheck = customerRepository.findByPhoneNum(customer.getPhoneNum());
        if(phoneNumCheck.isPresent())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "customer already saved");

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
            List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(customer.get().getId());
            List<Long> cart = new ArrayList<>();
            for (CartMenu cartMenu : cartMenus) {
                cart.add(cartMenu.getId());
            }
            List<Order> orders = orderRepository.findAllByCustomerId(customer.get().getId());
            List<Long> order = new ArrayList<>();
            for (Order order1 : orders) {
                order.add(order1.getId());
            }

            Map<String, Object> map = new HashMap<>();
            map.put("customerId", customer.get().getId());
            map.put("cid", customer.get().getCid());
            map.put("cart", cart);
            map.put("order", order);
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
