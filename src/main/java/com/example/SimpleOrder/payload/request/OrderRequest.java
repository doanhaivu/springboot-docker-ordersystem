package com.example.SimpleOrder.payload.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class OrderRequest {
    @NotNull
    @Size(max = 250)
    private String email;

    @NotNull
    @Size(min = 1, max = 5)
    private List<Long> inventory;

    @NotNull
    @Size(min = 1, max = 5)
    private List<Integer> quantity;

    public List<Long> getInventory() {
        return inventory;
    }

    public void setInventory(List<Long> inventory) {
        this.inventory = inventory;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Integer> getQuantity() {
        return quantity;
    }

    public void setQuantity(List<Integer> quantity) {
        this.quantity = quantity;
    }
}
