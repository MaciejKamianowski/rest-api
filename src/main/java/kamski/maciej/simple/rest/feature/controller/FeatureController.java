package kamski.maciej.simple.rest.feature.controller;

import enumtypes.CurrencyCodeTableA;
import http.TableA;
import models.rates.Rate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class FeatureController {

    @GetMapping("/status/ping")
    public String getPong() {
        return "pong";
    }

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

    @PostMapping(value = "/currencies/get-current-currency-value-command")
    public ResponseEntity<Map<String, Object>> getCurrencyRate(
            @RequestBody Map<String, Object> dto) {
        Map<String, Object> map = new HashMap<>();
        try {
            String currencyCode = dto.get("currency").toString();
            List<Rate> rates = new TableA()
                    .currentExchangeRate(CurrencyCodeTableA.valueOf(currencyCode)).getRates();
            Rate lastRate = rates.get(rates.size() - 1);
            double currencyCurrentExchangeRate = lastRate.getMid();
            map.put("value", currencyCurrentExchangeRate);
        } catch (Exception e) {
            map.put("Oops! Something went wrong!", e.fillInStackTrace().toString());
        }
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
