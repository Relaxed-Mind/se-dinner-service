package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.StaffChangeOrderStatusDTO;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.dto.response.StaffLoadOrderListDTO;
import seproject.worship.entity.Order;
import seproject.worship.entity.OrderMenu;
import seproject.worship.entity.Staff;
import seproject.worship.enumpack.OrderStatus;
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
    public Long staffChangeOrderStatus(StaffChangeOrderStatusDTO staffChangeOrderStatusDTO){
        Long id = staffChangeOrderStatusDTO.getId();
        OrderStatus orderStatus = staffChangeOrderStatusDTO.getOrderStatus();

        Optional<Order> orderFindById = orderRepository.findById(id);
        orderFindById.get().staffChangeOrderStatus(orderStatus);

        return id;
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

}


