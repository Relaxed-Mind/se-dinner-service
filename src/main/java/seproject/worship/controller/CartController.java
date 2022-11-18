package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import seproject.worship.dto.request.CartAddMenuDTO;
import seproject.worship.dto.request.CartModifyMenuDTO;
import seproject.worship.dto.response.CartViewSpecificMenuDTO;
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

    @GetMapping("/{cartMenuId}")
    public CartViewSpecificMenuDTO cartViewSpecificMenu(@PathVariable Long cartMenuId){
        return cartService.cartViewSpecificMenu(cartMenuId);
    }

    @DeleteMapping("/{cartMenuId}")
    public Map cartDeleteMenu(@PathVariable Long cartMenuId){
        return cartService.cartDeleteMenu(cartMenuId);
    }

    @PutMapping()
    public Map cartModifyMenu(@RequestBody CartModifyMenuDTO dto){
        return cartService.cartModifyMenu(dto);
    }

}
