package com.bhagwat.scm.orderService.dto;

public class OrderRequest {

    private String customerId;
    private String consignmentId;

    // Add any other fields you need for order creation

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getConsignmentId() {
        return consignmentId;
    }

    public void setConsignmentId(String consignmentId) {
        this.consignmentId = consignmentId;
    }
}

