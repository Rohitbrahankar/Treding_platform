package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Coins;

import java.util.List;

public interface CoinService {



    List<Coins> getCoinsList(int page) throws Exception;

    String getMarketChart(String coinId,int days) throws Exception;

    String getCoinDetail(String coinId) throws Exception;
    Coins findById(String CoinId) throws Exception;// this id is getting from our data base other time id is provided by coingeki API

    String serchCoin(String keyword) throws Exception;

    String getTop50CoinsByMarketCapRank() throws Exception;

    String getTradingCoin() throws Exception;


}
