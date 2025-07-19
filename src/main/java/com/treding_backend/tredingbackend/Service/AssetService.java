package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Asset;
import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.User;

import java.util.List;

public interface AssetService {


    Asset createAsset(User user, Coins coins , double quantity);

    Asset getAssetById(Long assetId) throws Exception;

    Asset getAssetByUserIdAndId(Long userId, Long assetId);

    List<Asset> getUserAssets(Long UserId);

    Asset updateAsset( Long assetId, double quantity) throws Exception;

    Asset findAssetByUserIdAndCoinsId(Long userId, String coinId);

    public void deleteAsset(Long assetId);





}
