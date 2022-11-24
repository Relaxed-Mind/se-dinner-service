package seproject.worship.domain.dto.response;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ItemListLoadDTO {

    private Long id;
    private String itemUrl;
    private String name;
    private Integer stockQuantity;
}
