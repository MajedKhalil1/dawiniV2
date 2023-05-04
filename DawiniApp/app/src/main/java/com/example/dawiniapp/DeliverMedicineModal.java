package com.example.dawiniapp;

import java.io.Serializable;

public class DeliverMedicineModal implements Serializable {

    private String clientName;
    private String medicineList;
    private String requestId;

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getMedicineList() {
        return medicineList;
    }

    public void setMedicineList(String medicineList) {
        this.medicineList = medicineList;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }
}
