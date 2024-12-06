package com.bhagwat.scm.orderService.dto;

public class OrderLineRequest {

    private String skuId;
    private int orderedQuantity;

    // Constructors
    public OrderLineRequest() {
    }

    public OrderLineRequest(String skuId, int orderedQuantity) {
        this.skuId = skuId;
        this.orderedQuantity = orderedQuantity;
    }

    // Getters and Setters
    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }
}

