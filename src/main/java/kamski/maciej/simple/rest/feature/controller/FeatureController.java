package kamski.maciej.simple.rest.feature.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FeatureController {

//    private ModelMapper modelMapper;
//
//
//    public FeatureController(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }

    @GetMapping("/status/ping")
    public String getPong() {
        return "pong";
    }

//    @PostMapping(value = "/numbers/sort-command")
//    public String getSortedNumberArray(
//            @RequestBody Map<String, Object> dto) {
//        System.out.println(dto);
//        String order = dto.get("order").toString();
//
//        List<Integer> numbers = (ArrayList<Integer>)dto.get("numbers");
//        System.out.println("numbers: " + numbers);
//        return dto.toString();
//    }


    @PostMapping(value = "/numbers/sort-command")
    public ResponseEntity<Map<String, Object>> getSortedNumberArray(
            @RequestBody Map<String, Object> dto) {
//        System.out.println(dto);
        String order = dto.get("order").toString();
        boolean isValidOrder = (Objects.equals(order, "ASC") || Objects.equals(order, "DESC"));

        List<Integer> numbers = (ArrayList<Integer>)dto.get("numbers");
        if (isValidOrder) {
            if (order.equals("ASC")) {
                Collections.sort(numbers); //sorting collection
            } else {
                numbers.sort(Collections.reverseOrder());
            }
        } else {
            return new ResponseEntity<>(new HashMap<>(), HttpStatus.BAD_REQUEST);
        }
        System.out.println("numbers: " + numbers);
        Map<String, Object> validDataMap = new HashMap<>();
        validDataMap.put("numbers", numbers);
        return new ResponseEntity<>(validDataMap, HttpStatus.OK);
    }

}
