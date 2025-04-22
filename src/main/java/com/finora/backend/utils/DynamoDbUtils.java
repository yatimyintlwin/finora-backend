package com.finora.backend.utils;

import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.ListTablesRequest;

public class DynamoDbUtils {
    public static boolean tableExists(DynamoDbClient dynamoDbClient, String tableName) {
        return dynamoDbClient.listTables(ListTablesRequest.builder().build())
                .tableNames()
                .contains(tableName);
    }
}