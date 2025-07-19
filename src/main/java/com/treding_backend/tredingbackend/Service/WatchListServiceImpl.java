package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.WatchList;
import com.treding_backend.tredingbackend.Repository.WatchListRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WatchListServiceImpl implements WatchListService {
    @Autowired
    private WatchListRepo watchListRepo;

    @Override
    public WatchList findUserWatchList(Long UserId) throws Exception {

        WatchList watchList = watchListRepo.findByUserId(UserId);
        if(watchList==null)
        {
            throw  new Exception("WatchList Not found");

        }

        return null;
    }

    @Override
    public WatchList CreateWatchList(User user) {
        WatchList watchList = new WatchList();
        watchList.setUser(user);

        return watchListRepo.save(watchList);
    }

    @Override
    public WatchList findByID(Long id) throws Exception {
        Optional<WatchList> watchListoptional = watchListRepo.findById(id);
        if(watchListoptional.isEmpty())
        {
            throw  new Exception("WatchList not found");
        }
        return watchListoptional.get();
    }

    @Override
    public Coins addItemToWatchList(Coins coins, User user) throws Exception {
        WatchList watchList = findUserWatchList(user.getId());

        if(watchList.getCoins().contains(coins))
        {
             watchList.getCoins().remove(coins);
        }else{
            watchList.getCoins().add(coins);
        }
         watchListRepo.save(watchList);
        return coins;
    }
}
