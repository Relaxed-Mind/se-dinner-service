package seproject.worship.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.ItemAddDTO;
import seproject.worship.dto.response.ItemAddResponseDTO;
import seproject.worship.entity.Item;
import seproject.worship.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private  final ItemRepository itemRepository;

    @Transactional
    public List itemAdd(List<ItemAddDTO> list){


        List<ItemAddResponseDTO> modifiedItem = new ArrayList<>();

        for(ItemAddDTO itemAddDTO : list) {

            Long id = itemAddDTO.getId();
            Integer addQuantity = itemAddDTO.getAddQuantity();
            Item itemFindById = itemRepository.findById(id).get();
            itemFindById.addQuantity(addQuantity);

            ItemAddResponseDTO itemAddResponseDTO = new ItemAddResponseDTO();
            itemAddResponseDTO.setId(id);
            itemAddResponseDTO.setStockQuantity(itemFindById.getStockQuantity());
            modifiedItem.add(itemAddResponseDTO);
        }
        return modifiedItem;
    }
}
