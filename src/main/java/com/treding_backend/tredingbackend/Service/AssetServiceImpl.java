package com.treding_backend.tredingbackend.Service;

import com.treding_backend.tredingbackend.Modal.Asset;
import com.treding_backend.tredingbackend.Modal.Coins;
import com.treding_backend.tredingbackend.Modal.User;
import com.treding_backend.tredingbackend.Repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AssetServiceImpl implements AssetService {



    private AssetRepository assetRepository;

    @Override
    public Asset createAsset(User user, Coins coins, double quantity) {

       Asset asset = new Asset();
       asset.setUser(user);
       asset.setCoins(coins);
       asset.setQuantity(quantity);
       asset.setBuyPrice(coins.getCurrentPrice());
        return assetRepository.save(asset);

    }

    @Override
    public Asset getAssetById(Long assetId) throws Exception {
        return assetRepository.findById(assetId)
                .orElseThrow(() -> new Exception("Asset not found with id: " + assetId));
    }


    @Override
    public Asset getAssetByUserIdAndId(Long userId, Long assetId) {


        return null;
    }

    @Override
    public List<Asset> getUserAssets(Long UserId) {
        return assetRepository.findByUserId(UserId);
    }

    @Override
    public Asset updateAsset(Long assetId, double quantity) throws Exception {
        Asset oldAsset= getAssetById(assetId);
        oldAsset.setQuantity(quantity+oldAsset.getQuantity());
        return assetRepository.save(oldAsset);

    }

    @Override
    public Asset findAssetByUserIdAndCoinsId(Long userId, String coinId) {


        return assetRepository.findByUserIdAndCoinsId(userId, coinId);
    }

    @Override
    public void deleteAsset(Long assetId) {
       assetRepository.deleteById(assetId);
    }
}
