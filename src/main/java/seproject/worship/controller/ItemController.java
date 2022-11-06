package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.request.ItemAddDTO;
import seproject.worship.service.ItemService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public List itemAdd(@RequestBody List<ItemAddDTO> list){

        System.out.println(list.size());
        return itemService.itemAdd(list);
    }

    @GetMapping("/item")
    public List itemListLoad(){
        return itemService.itemListLoad();
    }

}
