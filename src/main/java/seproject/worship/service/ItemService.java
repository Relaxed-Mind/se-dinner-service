package seproject.worship.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.ItemAddDTO;
import seproject.worship.dto.request.ItemAddListDTO;
import seproject.worship.dto.response.ItemAddResponseDTO;
import seproject.worship.dto.response.ItemListLoadDTO;
import seproject.worship.entity.Item;
import seproject.worship.repository.ItemRepository;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ItemService {

    private  final ItemRepository itemRepository;

    @Transactional
    public Map itemAdd(List<ItemAddDTO> itemAddDTOS){


        List<ItemAddResponseDTO> modifiedItem = new ArrayList<>();

        for(ItemAddDTO itemAddDTO : itemAddDTOS) {

            Long id = itemAddDTO.getId();
            Integer addQuantity = itemAddDTO.getAddQuantity();
            Optional<Item> itemFindById = itemRepository.findById(id);
            if(itemFindById.isEmpty()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 ID값을 가지는 item 존재하지 않음");}
            else{
                 itemFindById.get().addQuantity(addQuantity);
            }
            itemRepository.flush();

            ItemAddResponseDTO itemAddResponseDTO = new ItemAddResponseDTO();
            itemAddResponseDTO.setId(id);
            itemAddResponseDTO.setStockQuantity(itemRepository.findById(id).get().getStockQuantity());
            modifiedItem.add(itemAddResponseDTO);
        }
        Map<String, List> map=new HashMap<>();
        map.put("results",modifiedItem);
        return map;
    }

    @Transactional(readOnly = true)
    public Map itemListLoad(){
        List<ItemListLoadDTO> findAllItem = new ArrayList<>();
        List<Item> allItem = itemRepository.findAll();
        if(allItem.isEmpty())
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"item이 DB에 저장되어 있지 않다.");

        for(Item item : allItem){
            ItemListLoadDTO entityToDtoItem = ItemListLoadDTO.builder()
                    .id(item.getId())
                    .itemUrl(item.getItemUrl())
                    .name(item.getName())
                    .stockQuantity(item.getStockQuantity()).build();
            findAllItem.add(entityToDtoItem);
        }
        Map<String,List> map = new HashMap<>();
        map.put("results",findAllItem);
        return map;
    }


}
