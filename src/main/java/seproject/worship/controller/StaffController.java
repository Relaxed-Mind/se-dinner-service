package seproject.worship.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.service.StaffService;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class StaffController {

    private final StaffService staffService;

    @PostMapping("/staff")
    public Map staffLogin(@RequestBody StaffLoginDTO staffLoginDTO){
        return staffService.staffLogin(staffLoginDTO);
    }

}
