package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import seproject.worship.dto.request.ItemAddDTO;
import seproject.worship.repository.ItemRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ItemService {

    private  final ItemRepository itemRepository;

    public Map itemAdd(List<ItemAddDTO> list){

    }
}
