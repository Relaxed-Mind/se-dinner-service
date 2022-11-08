package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.dto.response.ViewSpecificMenuDTO;
import seproject.worship.entity.Menu;
import seproject.worship.repository.MenuRepository;
import seproject.worship.repository.OrderRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

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
}
