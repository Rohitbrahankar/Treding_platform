package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Modal.WatchList;
import com.treding_backend.tredingbackend.Service.CoinService;
import com.treding_backend.tredingbackend.Service.UserService;
import com.treding_backend.tredingbackend.Service.WatchListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/watchList")
public class WatchListController

{
    @Autowired
     private   WatchListService watchListService;
    @Autowired
     private   UserService userService;

     @Autowired
     private CoinService coinService;

 @GetMapping("/user")
     public ResponseEntity<WatchList> getUserWatchList(
             @RequestHeader("Authorization") String jwt
 )throws  Exception{

     User user = userService.findUserProfileByJwt(jwt);
     WatchList watchList = watchListService.findUserWatchList(user.getId());
     return  ResponseEntity.ok(watchList);

 }

 @GetMapping("/{watchListId}")
    public ResponseEntity<WatchList> getUserWatchListById(
            @PathVariable Long watchListId ) throws  Exception{
     WatchList watchList = watchListService.findUserWatchList(watchListId);
     return  ResponseEntity.ok(watchList);
 }

 public  ResponseEntity<Coins> addItemToWatchList(
         @RequestHeader("Authorization") String jwt ,
         @PathVariable String coinId ) throws  Exception{
     User user = userService.findUserProfileByJwt(jwt);
     Coins coins= coinService.findById(coinId);
     Coins addcoins= watchListService.addItemToWatchList(coins , user);
     return  ResponseEntity.ok(addcoins);

 }


}
