package rw.ac.auca.crypto_portfolio.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rw.ac.auca.crypto_portfolio.entity.Asset;
import rw.ac.auca.crypto_portfolio.entity.Tag;
import rw.ac.auca.crypto_portfolio.repository.AssetRepository;
import rw.ac.auca.crypto_portfolio.repository.TagRepository;
import java.util.List;
import java.util.Optional;

@Service
public class AssetService {

    @Autowired
    private AssetRepository assetRepository;

    @Autowired
    private TagRepository tagRepository;

    // Save a new asset
    public Asset save(Asset asset) {
        if (assetRepository.existsBySymbol(asset.getSymbol())) {
            throw new RuntimeException(
                    "Asset with symbol " + asset.getSymbol() + " already exists");
        }
        if (assetRepository.existsByName(asset.getName())) {
            throw new RuntimeException(
                    "Asset with name " + asset.getName() + " already exists");
        }
        return assetRepository.save(asset);
    }

    // Get all assets — no pagination
    public List<Asset> findAll() {
        return assetRepository.findAll();
    }

    // Get all assets WITH pagination and sorting
    // page = which page (0 = first page)
    // size = how many items per page
    // sortBy = which field to sort by
    public Page<Asset> findAllPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return assetRepository.findAll(pageable);
    }

    // Get assets by max risk percentage with pagination
    public Page<Asset> findByMaxRisk(
            Double maxRisk, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,
                Sort.by("riskPercentage"));
        return assetRepository.findByRiskPercentageLessThanEqual(
                maxRisk, pageable);
    }

    // Get asset by id
    public Optional<Asset> findById(Long id) {
        return assetRepository.findById(id);
    }

    // Add tag to asset — Many-to-Many
    public Asset addTag(Long assetId, Long tagId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException(
                        "Asset not found with id: " + assetId));
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException(
                        "Tag not found with id: " + tagId));
        asset.getTags().add(tag);
        return assetRepository.save(asset);
    }

    // Get live price from CoinGecko API
    public String getLivePrice(Long assetId) {
        Asset asset = assetRepository.findById(assetId)
                .orElseThrow(() -> new RuntimeException(
                        "Asset not found with id: " + assetId));

        String url = "https://api.coingecko.com/api/v3/simple/price?ids="
                + asset.getCoinGeckoId()
                + "&vs_currencies=usd";

        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class);
        return response;
    }
}