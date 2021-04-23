package dev.comakeit.potholechallenge.models;

import lombok.Data;

@Data
public class BidRequest {
    public Double bidAmount;
    public String description;
}
