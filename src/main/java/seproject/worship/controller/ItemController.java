package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.domain.dto.request.ItemAddListDTO;
import seproject.worship.service.ItemService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public Map itemAdd(@RequestBody ItemAddListDTO itemAddListDTO){


        return itemService.itemAdd(itemAddListDTO);
    }

    @GetMapping("/item")
    public Map itemListLoad(){
        return itemService.itemListLoad();
    }

}
