package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.WatchList;

public interface WatchListService {

    WatchList  findUserWatchList(Long UserId) throws Exception;

    WatchList CreateWatchList(User user);

    WatchList findByID(Long  id) throws Exception;

    Coins  addItemToWatchList(Coins coins, User user) throws Exception;
}
