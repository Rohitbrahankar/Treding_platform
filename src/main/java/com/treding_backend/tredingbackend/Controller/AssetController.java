package com.treding_backend.tredingbackend.Controller;

import com.treding_backend.tredingbackend.Modal.Asset;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Service.AssetService;
import com.treding_backend.tredingbackend.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

public class AssetController {
    private AssetService assetService;
    private UserService userService;


    @GetMapping("/{assetId}")
    public ResponseEntity<Asset> getAssetById(@PathVariable Long assetId) throws Exception {
        Asset asset = assetService.getAssetById(assetId);
        return  ResponseEntity.ok(asset);


    }

    @GetMapping("/coin/{coinId}/user")
    public ResponseEntity<Asset> getAssetByUserIdAndCoinId(
            @PathVariable String coinId,
            @RequestHeader ("Authorization")String jwt
    )throws Exception{
        User user = userService.findUserProfileByJwt(jwt);
        Asset asset = assetService.findAssetByUserIdAndCoinsId(user.getId(), coinId);
        return  ResponseEntity.ok(asset);

    }

    @GetMapping()
    public ResponseEntity<List<Asset>> getAssetsForUser(
        @RequestHeader("Authorization") String jwt ) throws Exception
    {
       User user = userService.findUserProfileByJwt(jwt);
       List<Asset> assets = assetService.getUserAssets(user.getId());
       return  ResponseEntity.ok(assets);
    }
}
