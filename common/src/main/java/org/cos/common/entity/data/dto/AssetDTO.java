package org.cos.common.entity.data.dto;

import lombok.Data;

@Data
public class AssetDTO {
    private Double purchaseSumAmount;
    private Long purchasePersons;
    private Double withdrawSumAmount;
    private Long withdrawPersons;
}