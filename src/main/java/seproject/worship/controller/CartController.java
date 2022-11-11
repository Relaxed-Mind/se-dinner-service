package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.dto.request.CartAddMenuDTO;
import seproject.worship.service.CartService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;

    @PostMapping()
    public Map cartAddMenu(@RequestBody CartAddMenuDTO dto){
        return cartService.cartAddMenu(dto);
    }

    @GetMapping("/{customerId}")
    public Map cartLoadMenuList(@PathVariable Long customerId){
        return cartService.cartLoadMenuList(customerId);
    }


}
