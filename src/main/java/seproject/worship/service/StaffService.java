package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.StaffAcceptOrderDTO;
import seproject.worship.dto.request.StaffChangeOrderStatusDTO;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.dto.request.StaffRefuseOrderDTO;
import seproject.worship.dto.response.StaffLoadAcceptOrderListDTO;
import seproject.worship.dto.response.StaffLoadOrderListDTO;
import seproject.worship.dto.response.StaffViewSpecificOrderDTO;
import seproject.worship.dto.response.StaffViewSpecificOrderOrderMenuDTO;
import seproject.worship.entity.*;
import seproject.worship.enumpack.OrderStatus;
import seproject.worship.enumpack.StyleStatus;
import seproject.worship.repository.ItemRepository;
import seproject.worship.repository.OrderMenuRepository;
import seproject.worship.repository.OrderRepository;
import seproject.worship.repository.StaffRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Map staffLogin(StaffLoginDTO staffLoginDTO){

        String dtoSid = staffLoginDTO.getSid();
        String dtoPw = staffLoginDTO.getPw();
        Optional<Staff> staff = staffRepository.findBySidAndPw(dtoSid, dtoPw);

        if(staff.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"일치하는 직원 정보가 없음");
        }
        else{
            Map<String, Object> map = new HashMap<>();
            map.put("staffId",staff.get().getId());
            return map;
        }
    }

    @Transactional
    public Map staffChangeOrderStatus(StaffChangeOrderStatusDTO staffChangeOrderStatusDTO){
        Long id = staffChangeOrderStatusDTO.getOrderId();
        OrderStatus orderStatus = staffChangeOrderStatusDTO.getOrderStatus();
        Optional<Order> orderFindById = orderRepository.findById(id);
        if(orderFindById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 Order ID를 가진 Order가 DB에 존재하지 않음");
        }
        orderFindById.get().staffChangeOrderStatus(orderStatus);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderFindById.get().getId());
        return map;
    }

    @Transactional
    public Map staffLoadOrderList(){

        List<StaffLoadOrderListDTO> staffLoadOrderListDTOS = new ArrayList<>();
        List<Order> receivingOrders = orderRepository.findByOrderStatus(OrderStatus.RECEIVING);

        if(receivingOrders.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"접수대기 중인 Order가 존재하지 않음");
        }
        for( Order order : receivingOrders){
            StaffLoadOrderListDTO staffLoadOrderListDTO = new StaffLoadOrderListDTO();
            List<Map> staffLoadOrderListDTOOrderMenus = staffLoadOrderListDTO.getOrderMenus();
            List<OrderMenu> orderMenus = order.getOrderMenus();

            for(OrderMenu orderMenu : orderMenus){
                Map<String, Object> map = new HashMap<>();
                map.put("menuName",orderMenu.getMenu().getName());
                map.put("count",orderMenu.getCount());
                staffLoadOrderListDTOOrderMenus.add(map);
            }
            staffLoadOrderListDTO.setOrderID(order.getId());
            staffLoadOrderListDTO.setCid(order.getCustomer().getCid());
            staffLoadOrderListDTOS.add(staffLoadOrderListDTO);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results",staffLoadOrderListDTOS);
        return  responseMap;
    }

    @Transactional
    public Map staffRefuseOrder(StaffRefuseOrderDTO staffRefuseOrderDTO){
        Optional<Order> orderFindById = orderRepository.findById(staffRefuseOrderDTO.getOrderId());

        if(orderFindById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 Order ID를 가진 Order가 DB에 존재하지 않음");
        }

        orderFindById.get().staffChangeOrderStatus(OrderStatus.REFUSED);
        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderFindById.get().getId());
        return map;

    }

    @Transactional
    public Map staffAcceptOrder(StaffAcceptOrderDTO staffAcceptOrderDTO){
        Optional<Order> orderFindById = orderRepository.findById(staffAcceptOrderDTO.getOrderId());

        if(orderFindById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 Order ID를 가진 Order가 DB에 존재하지 않음");
        }

        orderFindById.get().staffChangeOrderStatus(OrderStatus.CONFIRMED);
        List<OrderMenu> OrderMenusOfOrderFindById = orderFindById.get().getOrderMenus();

        for(OrderMenu orderMenu : OrderMenusOfOrderFindById){
            List<MenuItem> menuItems = orderMenu.getMenu().getMenuItems();
            for(MenuItem menuItem : menuItems){
                Optional<Item> itemFindByName = itemRepository.findByName(menuItem.getItem().getName());
                itemFindByName.get().useQuantity(menuItem.getCount()*orderMenu.getCount());
            }
            itemRepository.flush();

            List<ModifiedItem> modifiedItems = orderMenu.getModifiedItems();
            if(!modifiedItems.isEmpty()){
            for(ModifiedItem modifiedItem : modifiedItems){
                Optional<Item> itemFindByName = itemRepository.findByName(modifiedItem.getItem().getName());
                itemFindByName.get().useQuantity(modifiedItem.getCount());
                }
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderFindById.get().getId());
        return map;
    }

    @Transactional
    public StaffViewSpecificOrderDTO staffViewSpecificOrder(Long orderId){

        StaffViewSpecificOrderDTO staffViewSpecificOrderDTO = new StaffViewSpecificOrderDTO();
        staffViewSpecificOrderDTO.setOrderId(orderId);

        Optional<Order> orderFindById = orderRepository.findById(orderId);
        if(orderFindById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 Order ID를 가진 Order가 DB에 존재하지 않음");
        }

        List<OrderMenu> orderMenusFindById = orderFindById.get().getOrderMenus();
        if(orderMenusFindById.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"해당 Order의 Menu가 존재하지 않음.. 불가능..");
        }

        for(OrderMenu orderMenu : orderMenusFindById){
            StaffViewSpecificOrderOrderMenuDTO staffViewSpecificOrderOrderMenuDTO =
                    makeStaffViewSpecificOrderOrderMenuDTO(orderMenu.getOrderMenuPrice(), orderMenu.getCount(), orderMenu.getStyleStatus(),orderMenu.getMenu().getName());

           /* List<MenuItem> menuItems = orderMenu.getMenu().getMenuItems();
            for( MenuItem menuItem : menuItems){
                Map<String, Object> map = new HashMap<>();
                map.put("itemName",menuItem.getItem().getName());
                staffViewSpecificOrderOrderMenuDTO.getMenuItems().add(map);
            }*/

            List<ModifiedItem> modifiedItems = orderMenu.getModifiedItems();
            if(!modifiedItems.isEmpty()){
            for(ModifiedItem modifiedItem : modifiedItems){
                Map<String, Object> map = new HashMap<>();
                map.put("modifiedItemCount",modifiedItem.getCount());
                map.put("modifiedItemName",modifiedItem.getItem().getName());
                staffViewSpecificOrderOrderMenuDTO.getModifiedItems().add(map);
                }
            }
            staffViewSpecificOrderDTO.getOrderMenus().add(staffViewSpecificOrderOrderMenuDTO);
        }

        return staffViewSpecificOrderDTO;
    }

    @Transactional
    public Map staffLoadAcceptOrderList(){
        List<StaffLoadAcceptOrderListDTO> staffLoadAcceptOrderListDTOS = new ArrayList<>();
        List afterConfirmedOrderStatus = getAfterConfirmedOrderStatus();
        List<Order> ordersFindByStatues =
                orderRepository.findAllByOrderStatusIn(afterConfirmedOrderStatus);
        if(ordersFindByStatues.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"주문 수락된 이후의 상태를 가지는 주문이 존재하지 않음");

        for(Order order : ordersFindByStatues){

            StaffLoadAcceptOrderListDTO staffLoadAcceptOrderListDTO = new StaffLoadAcceptOrderListDTO();
            List<Map> staffLoadAcceptOrderListDTOOrderMenus = staffLoadAcceptOrderListDTO.getOrderMenus();
            Map<String, Object> map = new HashMap<>();

            List<OrderMenu> orderMenus = order.getOrderMenus();
            for(OrderMenu orderMenu : orderMenus){
                map.put("menuName",orderMenu.getMenu().getName());
                map.put("count",orderMenu.getCount());
                staffLoadAcceptOrderListDTOOrderMenus.add(map);
            }

            staffLoadAcceptOrderListDTO.setOrderID(order.getId());
            staffLoadAcceptOrderListDTO.setCid(order.getCustomer().getCid());
            staffLoadAcceptOrderListDTOS.add(staffLoadAcceptOrderListDTO);
        }
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("Results",staffLoadAcceptOrderListDTOS);
        return  responseMap;

    }

    public StaffViewSpecificOrderOrderMenuDTO makeStaffViewSpecificOrderOrderMenuDTO(Integer orderMenuPrice, Integer count, StyleStatus styleStatus,String menuName){
        return StaffViewSpecificOrderOrderMenuDTO.builder()
                        .orderMenuPrice(orderMenuPrice)
                        .count(count)
                        .styleStatus(styleStatus)
                        .menuName(menuName)
                        .build();
    }

    public List getAfterConfirmedOrderStatus(){

        List<OrderStatus> orderStatuses = new ArrayList<>();

        orderStatuses.add(OrderStatus.CONFIRMED);
        orderStatuses.add(OrderStatus.COOKING);
        orderStatuses.add(OrderStatus.DELIVERING);

        return orderStatuses;
    }

}


