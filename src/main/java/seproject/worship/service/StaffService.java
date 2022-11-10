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
import seproject.worship.dto.response.StaffLoadOrderListDTO;
import seproject.worship.dto.response.StaffViewSpecificOrderDTO;
import seproject.worship.dto.response.StaffViewSpecificOrderOrderMenuDTO;
import seproject.worship.entity.*;
import seproject.worship.enumpack.OrderStatus;
import seproject.worship.enumpack.StyleStatus;
import seproject.worship.repository.OrderMenuRepository;
import seproject.worship.repository.OrderRepository;
import seproject.worship.repository.StaffRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;
    private final OrderRepository orderRepository;
    private final OrderMenuRepository orderMenuRepository;

    @Transactional
    public Map staffLogin(StaffLoginDTO staffLoginDTO){

        String dtoSid = staffLoginDTO.getSid();
        String dtoPw = staffLoginDTO.getPw();
        Optional<Staff> staff = staffRepository.findBySidAndPw(dtoSid, dtoPw);

        if(staff.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"login error");
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
        orderFindById.get().staffChangeOrderStatus(orderStatus);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderFindById.get().getId());
        return map;
    }

    @Transactional
    public Map staffLoadOrderList(){

        List<StaffLoadOrderListDTO> staffLoadOrderListDTOS = new ArrayList<>();
        List<Order> receivingOrders = orderRepository.findByOrderStatus(OrderStatus.RECEIVING);
        for( Order order : receivingOrders){
            StaffLoadOrderListDTO staffLoadOrderListDTO = new StaffLoadOrderListDTO();
            List<Map> staffLoadOrderListDTOOrderMenus = staffLoadOrderListDTO.getOrderMenus();
            Map<String, Object> map = new HashMap<>();

            List<OrderMenu> orderMenus = order.getOrderMenus();
            for(OrderMenu orderMenu : orderMenus){
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
        orderFindById.get().staffChangeOrderStatus(OrderStatus.REFUSED);

        Map<String,Object> map = new HashMap<>();
        map.put("orderId",orderFindById.get().getId());
        return map;

    }

    @Transactional
    public Map staffAcceptOrder(StaffAcceptOrderDTO staffAcceptOrderDTO){
        Optional<Order> orderFingById = orderRepository.findById(staffAcceptOrderDTO.getOrderId());
        orderFingById.get().staffChangeOrderStatus(OrderStatus.CONFIRMED);

        Map<String, Object> map = new HashMap<>();
        map.put("orderId",orderFingById.get().getId());
        return map;
    }

    @Transactional
    public StaffViewSpecificOrderDTO staffViewSpecificOrder(Long orderId){

        StaffViewSpecificOrderDTO staffViewSpecificOrderDTO = new StaffViewSpecificOrderDTO();
        staffViewSpecificOrderDTO.setOrderId(orderId);
        Optional<Order> orderFindById = orderRepository.findById(orderId);
        List<OrderMenu> orderMenusFindById = orderFindById.get().getOrderMenus();
        for(OrderMenu orderMenu : orderMenusFindById){
            StaffViewSpecificOrderOrderMenuDTO staffViewSpecificOrderOrderMenuDTO =
                    makeStaffViewSpecificOrderOrderMenuDTO(orderMenu.getOrderMenuPrice(), orderMenu.getCount(), orderMenu.getStyleStatus());

            List<MenuItem> menuItems = orderMenu.getMenu().getMenuItems();
            for( MenuItem menuItem : menuItems){
                Map<String, Object> map = new HashMap<>();
                map.put("itemName",menuItem.getItem().getName());
                staffViewSpecificOrderOrderMenuDTO.getMenuItems().add(map);
            }
            List<ModifiedItem> modifiedItems = orderMenu.getModifiedItems();
            for(ModifiedItem modifiedItem : modifiedItems){
                Map<String, Object> map = new HashMap<>();
                map.put("modifiedItemName",modifiedItem.getItem().getName());
                map.put("modifiedItemCount",modifiedItem.getCount());
            }
        }

        return staffViewSpecificOrderDTO;
    }

    public StaffViewSpecificOrderOrderMenuDTO makeStaffViewSpecificOrderOrderMenuDTO(Integer orderMenuPrice, Integer count, StyleStatus styleStatus){
        return StaffViewSpecificOrderOrderMenuDTO.builder()
                        .orderMenuPrice(orderMenuPrice)
                        .count(count)
                        .styleStatus(styleStatus)
                        .build();
    }

}


