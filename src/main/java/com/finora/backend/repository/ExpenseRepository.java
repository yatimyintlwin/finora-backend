package com.finora.backend.repository;

import com.finora.backend.domain.dto.Expense;
import com.finora.backend.utils.DynamoDbUtils;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ExpenseRepository {

    private final DynamoDbEnhancedClient dynamoDbEnhancedClient;
    private final DynamoDbClient dynamoDbClient;
    private static final String TABLE_NAME = "Expenses";

    public ExpenseRepository(DynamoDbEnhancedClient dynamoDbEnhancedClient, DynamoDbClient dynamoDbClient) {
        this.dynamoDbEnhancedClient = dynamoDbEnhancedClient;
        this.dynamoDbClient = dynamoDbClient;
        ensureTableExists();
    }

    public void save(Expense expense) {
        DynamoDbTable<Expense> table = getTable();
        table.putItem(expense);
    }

    public List<Expense> query(String pk) {
        DynamoDbTable<Expense> table = getTable();
        Iterator<Expense> results = table.query(r -> r.queryConditional(
                QueryConditional.keyEqualTo(k -> k.partitionValue(pk)))).items().iterator();

        List<Expense> expenses = new ArrayList<>();
        results.forEachRemaining(expenses::add);
        return expenses;
    }

    public List<Expense> query(String pk, String skPrefix) {
        DynamoDbTable<Expense> table = getTable();
        Iterator<Expense> results = table.query(r -> r.queryConditional(
                QueryConditional.sortBeginsWith(k -> k.partitionValue(pk).sortValue(skPrefix)))
        ).items().iterator();

        List<Expense> expenses = new ArrayList<>();
        results.forEachRemaining(expenses::add);
        return expenses;
    }

    public void delete(String pk, String sk) {
        DynamoDbTable<Expense> table = getTable();
        Key key = Key.builder()
                     .partitionValue(pk)
                     .sortValue(sk)
                     .build();
        table.deleteItem(key);
    }


    private DynamoDbTable<Expense> getTable() {
        return dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(Expense.class));
    }

    private void ensureTableExists() {
        if (!DynamoDbUtils.tableExists(dynamoDbClient, TABLE_NAME)) {
            DynamoDbTable<Expense> table = getTable();
            table.createTable();
        }
    }
}