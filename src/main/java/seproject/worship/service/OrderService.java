package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.entity.Menu;
import seproject.worship.enumpack.StyleStatus;
import seproject.worship.repository.MenuRepository;
import seproject.worship.repository.OrderRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;

    public Map loadMenuList() {
        Map<String, Object> map = new HashMap<>();
        return map;
    }
}
