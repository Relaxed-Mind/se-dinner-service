package seproject.worship.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import seproject.worship.dto.request.StaffLoginDTO;
import seproject.worship.entity.Staff;
import seproject.worship.repository.StaffRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public Map staffLogin(StaffLoginDTO staffLoginDTO){

        String dtoSid = staffLoginDTO.getSid();
        String dtoPw = staffLoginDTO.getPw();
        Optional<Staff> staff = staffRepository.findBySidAndpw(dtoSid, dtoPw);

        if(staff.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"login error");
        }
        else{
            Map<String, Object> map = new HashMap<>();
            map.put("staffId",staff.get().getId());
            return map;
        }
    }
}
