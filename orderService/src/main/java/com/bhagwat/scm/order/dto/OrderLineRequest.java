package com.bhagwat.scm.order.dto;

public class OrderLineRequest {

    private String productId;
    private int orderedQuantity;

    // Constructors
    public OrderLineRequest() {
    }

    public OrderLineRequest(String skuId, int orderedQuantity) {
        this.productId = skuId;
        this.orderedQuantity = orderedQuantity;
    }

    // Getters and Setters
    public String getSkuId() {
        return productId;
    }

    public void setSkuId(String skuId) {
        this.productId = skuId;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }
}

