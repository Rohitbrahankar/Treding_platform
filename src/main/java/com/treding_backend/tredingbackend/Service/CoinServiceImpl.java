package com.treding_backend.tredingbackend.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Repository.CoinREpository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;
import java.util.List;
import java.util.Optional;

@Service
public class CoinServiceImpl implements CoinService {
  @Autowired
  private CoinREpository coinREpository;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
    public List<Coins> getCoinsList(int page) throws Exception {

      //url is  give the data from api
      String url="https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&per_page=10&page="+page;

      RestTemplate restTemplate = new RestTemplate(); //rest apis converstion
      try {
          HttpHeaders headers= new HttpHeaders();

          HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

          ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

          List<Coins>coinList = objectMapper.readValue(response.getBody(),
                  new TypeReference<List<Coins>>(){});
          return coinList;
      }catch (HttpClientErrorException | HttpServerErrorException e)
      {
          throw  new Exception(e.getMessage());
      }

    }

    @Override
    public String getMarketChart(String coinId, int days) throws Exception {


        String url = "https://api.coingecko.com/api/v3/coins/" + coinId + "/market_chart?vs_currency=usd&days=" + days;


        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers= new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);

            return response.getBody();
        }catch (HttpClientErrorException | HttpServerErrorException e)
        {
            throw  new Exception(e.getMessage());
        }

    }

    @Override
    public String getCoinDetail(String coinId) throws Exception {


        String url = "https://api.coingecko.com/api/v3/coins/" + coinId ;


        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers= new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);


            JsonNode jsonNode=objectMapper.readTree(response.getBody());
            Coins coin= new Coins();
            coin.setId(jsonNode.get("id").asText());
            coin.setName(jsonNode.get("name").asText());
            coin.setSymbol(jsonNode.get("symbol").asText());
            coin.setImage(jsonNode.get("image").get("large").asText());

            JsonNode marketData=jsonNode.get("market_data");

            coin.setCurrentPrice(marketData.get("current_price").get("usd").asDouble());

            coin.setMarketCap(marketData.get("market_cap").get("usd").asLong() );

            coin.setMarketCapRank(marketData.get("market_cap_rank").asInt());
            coin.setTotalVolume(marketData.get("total_volume").get("usd").asLong());
            coin.setHigh24h(marketData.get("high_24h").get("usd").asDouble());
            coin.setLow24h(marketData.get("low_24h").get("usd").asDouble());
            coin.setPriceChange24h(marketData.get("price_change_24h").get("usd").asDouble());
            coin.setPriceChangePercentage24h(marketData.get("price_change24h").get("usd").asDouble());
            coin.setMarketCapChange24h(marketData.get("market_cap_change24h").get("usd").asLong());

            coin.setMarketCapChangePercentage24h(marketData.get("market_cap_change_percentage_24h").get("usd").asLong());

            coin.setTotalSupply(marketData.get("total_supply").get("usd").asLong());
            coinREpository.save(coin);
            return  response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e)
        {
            throw  new Exception(e.getMessage());
        }

    }

    @Override
    public Coins findById(String CoinId) throws
            Exception {
        Optional<Coins>optionalCoins= coinREpository.findById(CoinId);
        if(optionalCoins.isEmpty())throw new Exception("Coin is not found");


        return optionalCoins.get();

    }

    @Override
    public String serchCoin(String keyword) throws Exception {

        String url = "https://api.coingecko.com/api/v3/seatvh?q" ;

        RestTemplate restTemplate = new RestTemplate();

        try {
            HttpHeaders headers= new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);


            return  response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e)
        {
            throw  new Exception(e.getMessage());
        }
    }

    @Override
    public String getTop50CoinsByMarketCapRank() throws Exception {
        String url = "https://api.coingecko.com/api/v3/coins/markets/vs_currency=usd&per_page=50&page=1";


        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers= new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);


            return  response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e)
        {
            throw  new Exception(e.getMessage());
        }


    }

    @Override
    public String getTradingCoin() throws Exception {
        String url = "https://api.coingecko.com/api/v3/search/trading" ;


        RestTemplate restTemplate = new RestTemplate();


        try {
            HttpHeaders headers= new HttpHeaders();

            HttpEntity<String> entity = new HttpEntity<String>("parameter",headers);

            ResponseEntity<String> response=restTemplate.exchange(url, HttpMethod.GET,entity,String.class);



            return  response.getBody();

        }catch (HttpClientErrorException | HttpServerErrorException e)
        {
            throw  new Exception(e.getMessage());
        }


    }
}
