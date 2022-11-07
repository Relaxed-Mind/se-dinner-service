package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.response.CartLoadMenuListDTO;
import seproject.worship.entity.CartMenu;
import seproject.worship.entity.Menu;
import seproject.worship.repository.CartMenuRepository;
import seproject.worship.repository.MenuRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartMenuRepository cartMenuRepository;
    private final MenuRepository menuRepository;

    public Map cartLoadMenuList(Long customerId) {
        List<CartMenu> cartMenus = cartMenuRepository.findAllByCustomerId(customerId);
        List<Map> targetList = new ArrayList<>();

        if(cartMenus.isEmpty()){
            Map<String, Object> map = new HashMap<>();
            map.put("Result", "empty");
            return map;
        }else{
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
    }
}
