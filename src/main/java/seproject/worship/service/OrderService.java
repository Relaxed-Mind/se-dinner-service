package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.dto.response.ViewSpecificMenuDTO;
import seproject.worship.dto.response.beforeOrderDTO;
import seproject.worship.entity.CartMenu;
import seproject.worship.entity.Customer;
import seproject.worship.entity.Menu;
import seproject.worship.repository.CartMenuRepository;
import seproject.worship.repository.CustomerRepository;
import seproject.worship.repository.MenuRepository;
import seproject.worship.repository.OrderRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final CartMenuRepository cartMenuRepository;

    public Map loadMenuList() {
        List<Menu> menus = menuRepository.findAll();
        List<Map> targetList = new ArrayList<>();

        for (Menu menu : menus) {
            Map<String, Object> map = new HashMap<>();
            map.put("menuId", menu.getId());
            map.put("menuUrl", menu.getMenuUrl());
            map.put("menuName", menu.getName());
            map.put("menuPrice", menu.getMenuPrice());
            targetList.add(map);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results", targetList);
        return responseMap;
    }

    public ViewSpecificMenuDTO viewSpecificMenu(Long menuId) {
        Optional<Menu> menu = menuRepository.findById(menuId);
        //예외처리
        return new ViewSpecificMenuDTO(menu.get());
    }

    public beforeOrderDTO beforeOrder(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        //예외처리
        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(customerId);

        return new beforeOrderDTO(customer.get(), calcTotalPrice(cartMenus));
    }

    private Integer calcTotalPrice(List<CartMenu> cartMenus) {
        Integer totalPrice = 0;
        for (CartMenu cartMenu : cartMenus) {
            totalPrice += cartMenu.getCartMenuPrice();
        }
        return totalPrice;
    }

}
