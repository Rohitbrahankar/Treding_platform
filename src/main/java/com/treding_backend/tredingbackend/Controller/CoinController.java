package com.treding_backend.tredingbackend.Controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Service.CoinService;
import com.treding_backend.tredingbackend.Service.CoinServiceImpl;
import org.eclipse.angus.mail.handlers.text_html;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coins")

public abstract class CoinController {
    @Autowired
    private CoinService coinService;

    private ObjectMapper objectMapper;
    @GetMapping
    ResponseEntity<List<Coins>>getcoinList(@RequestParam("page")int page) throws Exception {
        List<Coins> coins = coinService.getCoinsList(page);

        return  new ResponseEntity<>(coins, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{CoinId}/chart")
    public ResponseEntity<JsonNode> getMarketChart(
            @PathVariable String CoinId,
            @RequestParam("days") int days
    ) throws Exception {
        String res = coinService.getMarketChart(CoinId, days);
        //res is string so string convert into json  -> to convert this String use JsoneNode and return JsoneNode

        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    ResponseEntity<JsonNode>serchCoin(
            @RequestParam("q")String keyword) throws Exception {
        String coin = coinService.serchCoin(keyword);
        JsonNode jsonNode = objectMapper.readTree(coin);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);

    }
    @GetMapping("/top50")
    ResponseEntity<JsonNode>getTop50CoinsByMarketCapRank() throws Exception {
        String coin = coinService.getTop50CoinsByMarketCapRank();
        JsonNode jsonNode = objectMapper.readTree(coin);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);
    }


    @GetMapping("/trading")
    ResponseEntity<JsonNode> getTradingCoin() throws Exception {
        String  tradingcoin= coinService.getTradingCoin();
        JsonNode jsonNode = objectMapper.readTree(tradingcoin);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);

    }
    @GetMapping("/details/{coindId}")
    ResponseEntity<JsonNode>getCoinDetail(@PathVariable String CoinId) throws Exception {
        String res = coinService.getCoinDetail(CoinId);
        JsonNode jsonNode = objectMapper.readTree(res);
        return new ResponseEntity<>(jsonNode , HttpStatus.ACCEPTED);
    }

}
