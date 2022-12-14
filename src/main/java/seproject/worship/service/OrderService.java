package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.domain.dto.request.OrderDTO;
import seproject.worship.domain.dto.response.ViewSpecificMenuDTO;
import seproject.worship.domain.dto.response.beforeOrderDTO;
import seproject.worship.domain.entity.*;
import seproject.worship.domain.enumpack.OrderStatus;
import seproject.worship.repository.*;

import javax.transaction.Transactional;
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
        if(customer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");
        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(customerId);

        Integer totalPrice = calcTotalPrice(cartMenus);
        Integer discountPrice = 0;

        if(orderRepository.countByCustomerIdAndOrderStatus(customerId, OrderStatus.DONE)>2){
            discountPrice = totalPrice / 10;
        }

        return new beforeOrderDTO(customer.get(), totalPrice, discountPrice);
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
        if(customer.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "user not found");

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
    public Map customerViewSpecificOrder(Long orderId) {
        List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrderId(orderId);
        List<Map> targetList = new ArrayList<>();
        //예외처리
        for (OrderMenu orderMenu : orderMenus) {
            Map<String, Object> map = new HashMap<>();
            List<Map> foodList = new ArrayList<>();

            Menu menu = orderMenu.getMenu();
            List<ModifiedItem> modifiedItems = modifiedItemRepository.findAllByOrderMenuId(orderMenu.getId());
            for (ModifiedItem modifiedItem : modifiedItems) {
                Map<String, Object> foodMap = new HashMap<>();
                Item item = modifiedItem.getItem();
                foodMap.put("name", item.getName());
                foodMap.put("price", item.getPrice() * modifiedItem.getCount());
                foodMap.put("itemCount", modifiedItem.getCount());
                foodList.add(foodMap);
            }
            map.put("menuName", menu.getName());
            map.put("menuURL", menu.getMenuUrl());
            map.put("styleStatus", orderMenu.getStyleStatus().name()); //name빼고 해보자
            map.put("count", orderMenu.getCount());
            map.put("orderMenuPrice", orderMenu.getOrderMenuPrice());
            map.put("foods", foodList);
            targetList.add(map);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results", targetList);
        return responseMap;
    }

    public Map customerLoadOrderList(Long customerId) {
        List<Order> orders = orderRepository.findAllByCustomerId(customerId);
        if(orders.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order is empty");

        List<Map> targetList = new ArrayList<>();

        for (Order order : orders) {
            Map<String, Object> map = new HashMap<>();

            LocalDateTime createdTime = order.getCreatedTime();
            String createdTimeToString = createdTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
            String orderStatusToString = order.getOrderStatus().name();

            List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrderId(order.getId());
            //예외처리
            Menu menu = orderMenus.get(0).getMenu();
            map.put("menuName", menu.getName());
            map.put("menuUrl", menu.getMenuUrl());
            map.put("orderCreatedTime", createdTimeToString);
            map.put("orderStatus", orderStatusToString);
            targetList.add(map);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results", targetList);
        return responseMap;
    }

    @Transactional
    public Map customerCancelOrder(Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not exist");
        Order realOrder = order.get();
        if(realOrder.getOrderStatus().name().equals("RECEIVING")){
            realOrder.customerCancelOrder();
            Map<String, Object> map = new HashMap<>();
            map.put("orderId", realOrder.getId());
            return map;
        } else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order can't be canceled");
        }
    }
}
