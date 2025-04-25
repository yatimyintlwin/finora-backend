package com.finora.backend.domain.dto;

import lombok.*;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@DynamoDbBean
@NoArgsConstructor
@AllArgsConstructor
public class Expense {
    @Getter(onMethod_ = @DynamoDbPartitionKey)
    private String pk;       // Partition Key (e.g. MONTH#2025-04)
    @Getter(onMethod_ = @DynamoDbSortKey)
    private String sk;       // Sort Key (e.g. DATE#2025-04-02#uuid)
    private String uuid;     // UUID
    private String date;     // YYYY-MM-DD
    private String month;    // YYYY-MM
    private String week;     // YYYY-MM-WW
    private String type;     // e.g. food, transportation
    private String name;     // e.g. "Taxi Ride"
    private Double amount;   // e.g. 12.5
}