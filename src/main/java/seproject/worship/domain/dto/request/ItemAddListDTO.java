package seproject.worship.domain.dto.request;


import lombok.Getter;


import java.util.ArrayList;
import java.util.List;


@Getter

public class ItemAddListDTO {
    List<ItemAddDTO> results = new ArrayList<>();
}
