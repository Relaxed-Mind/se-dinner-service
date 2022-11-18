package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.CartAddMenuDTO;
import seproject.worship.dto.request.CartModifyMenuDTO;
import seproject.worship.dto.response.CartLoadMenuListDTO;
import seproject.worship.dto.response.CartViewSpecificMenuDTO;
import seproject.worship.entity.*;
import seproject.worship.enumpack.StyleStatus;
import seproject.worship.repository.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMenuRepository cartMenuRepository;
    private final MenuRepository menuRepository;
    private final CustomerRepository customerRepository;
    private final ItemRepository itemRepository;
    private final ModifiedItemRepository modifiedItemRepository;
    private final OrderMenuRepository orderMenuRepository;
    private final OrderRepository orderRepository;

    public Map cartLoadMenuList(Long customerId) {
        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(customerId);
        List<Map> targetList = new ArrayList<>();
        if(cartMenus.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cart is empty");
        for (CartMenu cartMenu : cartMenus) {
            Optional<Menu> menu = menuRepository.findById(cartMenu.getMenu().getId());
            //예외처리
            CartLoadMenuListDTO dto = new CartLoadMenuListDTO(menu.get(), cartMenu);
            targetList.add(dto.CartLoadMenuListDTOtoMap());
            }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results", targetList);
        return responseMap;
    }

    public Map cartAddMenu(CartAddMenuDTO dto) {
        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        if(customer.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not exist");
        Optional<Menu> menu = menuRepository.findById(dto.getMenuId());
        if(menu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not exist");

        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerIdAndMenuId(dto.getCustomerId(), dto.getMenuId());
        if(!cartMenus.isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "this menu already exist in cart");

        List<Map> foods = dto.getFoods();
        Integer totalMenuPrice = getTotalMenuPrice(menu.get().getMenuPrice(), dto.getCount(), foods);

        CartMenu cartMenu = makeCartMenu(dto, customer, menu, totalMenuPrice);
        cartMenuRepository.save(cartMenu);

        makeModifiedItem(cartMenu, foods);

        Map<String, Object> map = new HashMap<>();
        map.put("cartMenuId", cartMenu.getId());
        return map;
    }

    public void makeModifiedItem(CartMenu cartMenu, List<Map> foods){
        for (Map food : foods) {
            String itemName = (String) food.get("name");
            Optional<Item> item = itemRepository.findByName(itemName);
            if(item.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "item not found");

            ModifiedItem modifiedItem = ModifiedItem.builder()
                    .item(item.get())
                    .count((Integer)food.get("itemCount"))
                    .build();
            modifiedItem.setCartMenu(cartMenu);
            modifiedItemRepository.save(modifiedItem);
        }
    }
    private CartMenu makeCartMenu(CartAddMenuDTO dto, Optional<Customer> customer, Optional<Menu> menu, Integer totalMenuPrice) {
        return CartMenu.builder()
                .menu(menu.get())
                .customer(customer.get())
                .cartMenuPrice(totalMenuPrice)
                .count(dto.getCount())
                .styleStatus(StyleStatus.valueOf(dto.getStyle()))
                .build();
    }

    public Integer getTotalMenuPrice(int price, int count, List<Map> foods){
        Integer totalPrice = price * count;
        for (Map food : foods)
            totalPrice += (Integer) food.get("price");
        return totalPrice;
    }


    public CartViewSpecificMenuDTO cartViewSpecificMenu(Long cartMenuId) {
        Optional<CartMenu> cartMenu = cartMenuRepository.findById(cartMenuId);
        List<ModifiedItem> modifiedItems = modifiedItemRepository.findAllByCartMenuId(cartMenuId);
        //예외처리

        return new CartViewSpecificMenuDTO(cartMenu.get(), modifiedItems);
    }

    public Map cartDeleteMenu(Long cartMenuId) {
        Optional<CartMenu> cartMenu = cartMenuRepository.findById(cartMenuId);
        if(cartMenu.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "cartMenu not exist");

        cartMenuRepository.delete(cartMenu.get());
        Map<String, String> map = new HashMap<>();
        map.put("delete", "ok");
        return map;
    }


    public Map customerReorder(Long customerId, Long orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if(order.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "order not exist");
        if(!(order.get().getOrderStatus().name().equals("DONE"))) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "order must be done");

        List<OrderMenu> orderMenus = orderMenuRepository.findAllByOrderId(orderId);
        //예외처리
        List<Map> targetList = new ArrayList<>();
        for (OrderMenu orderMenu : orderMenus) {
            Map<String, Object> map = new HashMap<>();
            Optional<Customer> customer = customerRepository.findById(customerId); //예외처리
            CartMenu cartMenu = CartMenu.builder()
                    .customer(customer.get())
                    .cartMenuPrice(orderMenu.getOrderMenuPrice())
                    .count(orderMenu.getCount())
                    .styleStatus(orderMenu.getStyleStatus())
                    .menu(orderMenu.getMenu())
                    .build();
            cartMenuRepository.save(cartMenu);
            map.put("cartMenuId", cartMenu.getId());
            targetList.add(map);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results", targetList);
        return responseMap;
    }

    public Map cartModifyMenu(CartModifyMenuDTO dto) {

        cartDeleteMenu(dto.getCartMenuId());

        Long cartMenuId = cartAddMenu(dto);

        Map<String, Object> map = new HashMap<>();
        map.put("cartMenuId", cartMenuId);
        return map;
    }

    public Long cartAddMenu(CartModifyMenuDTO dto) {
        Optional<Customer> customer = customerRepository.findById(dto.getCustomerId());
        if(customer.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not exist");
        Optional<Menu> menu = menuRepository.findById(dto.getMenuId());
        if(menu.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "menu not exist");

        List<Map> foods = dto.getFoods();
        Integer totalMenuPrice = getTotalMenuPrice(menu.get().getMenuPrice(), dto.getCount(), foods);

        CartMenu cartMenu = makeCartMenu(dto, customer, menu, totalMenuPrice);
        cartMenuRepository.save(cartMenu);

        makeModifiedItem(cartMenu, foods);

        return cartMenu.getId();
    }
    private CartMenu makeCartMenu(CartModifyMenuDTO dto, Optional<Customer> customer, Optional<Menu> menu, Integer totalMenuPrice) {
        return CartMenu.builder()
                .menu(menu.get())
                .customer(customer.get())
                .cartMenuPrice(totalMenuPrice)
                .count(dto.getCount())
                .styleStatus(StyleStatus.valueOf(dto.getStyle()))
                .build();
    }
}
