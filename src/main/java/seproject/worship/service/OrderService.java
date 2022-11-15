package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.dto.request.OrderDTO;
import seproject.worship.dto.response.ViewSpecificMenuDTO;
import seproject.worship.dto.response.beforeOrderDTO;
import seproject.worship.entity.*;
import seproject.worship.enumpack.OrderStatus;
import seproject.worship.repository.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final CartMenuRepository cartMenuRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final ModifiedItemRepository modifiedItemRepository;

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

    public Map order(OrderDTO dto){
        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        //예외처리

        Order order = makeOrder(dto, customer.get());
        orderRepository.save(order);

        // cartMenu -> orderMenu && modifiedItem의 cartMenu -> orderMenu로
        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(dto.getCustomerId());
        convertCartMenuToOrderMenu(order, cartMenus);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId", order.getId());
        return map;
    }

    public void convertCartMenuToOrderMenu(Order order, List<CartMenu> cartMenus) {
        for (CartMenu cartMenu : cartMenus) {
            OrderMenu orderMenu = OrderMenu.builder()
                    .order(order)
                    .count(cartMenu.getCount())
                    .orderMenuPrice(cartMenu.getCartMenuPrice())
                    .menu(cartMenu.getMenu())
                    .styleStatus(cartMenu.getStyleStatus())
                    .build();
            orderMenuRepository.save(orderMenu);
            List<ModifiedItem> modifiedItems = modifiedItemRepository.findAllByCartMenuId(cartMenu.getId());
            for (ModifiedItem tempItem : modifiedItems) {
                ModifiedItem modifiedItem = ModifiedItem.builder()
                        .item(tempItem.getItem())
                        .count(tempItem.getCount())
                        .build();
                modifiedItem.setOrderMenu(orderMenu);
                modifiedItemRepository.save(modifiedItem);
            }
            cartMenuRepository.delete(cartMenu);
        }
    }

    private Order makeOrder(OrderDTO dto, Customer customer) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMddHHmm");
        LocalDateTime reservationDate = LocalDateTime.parse(dto.getReservationDate(), format);
        OrderStatus orderStatus = OrderStatus.RECEIVING;

        return Order.builder()
                .customer(customer)
                .destinationAddress(dto.getDestinationAddress())
                .reservationDate(reservationDate)
                .cardNum(dto.getCardNum())
                .phoneNum(dto.getPhoneNum())
                .orderStatus(orderStatus)
                .build();
    }
}
