package com.example.jaison.hasura_todo.tables;

import com.google.gson.reflect.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import com.google.gson.JsonElement;
import io.hasura.db.*;
import com.example.jaison.hasura_todo.tables.records.*;
public class Todo extends Table<TodoRecord> {
    public static final Todo TODO = new Todo();

    public Todo() {
        super("todo");
    }

    public Type getInsResType() {
        return new TypeToken<InsertResult<TodoRecord>>() {}.getType();
    }

    public Type getSelResType() {
        return new TypeToken<ArrayList<TodoRecord>>() {}.getType();
    }

    public Type getUpdResType() {
        return new TypeToken<UpdateResult<TodoRecord>>() {}.getType();
    }

    public Type getDelResType() {
        return new TypeToken<DeleteResult<TodoRecord>>() {}.getType();
    }

    public final PGField<TodoRecord, Integer> ID = new PGField<>("id");
    public final PGField<TodoRecord, String> TITLE = new PGField<>("title");
    public final PGField<TodoRecord, Integer> USER_ID = new PGField<>("user_id");
    public final PGField<TodoRecord, Boolean> COMPLETED = new PGField<>("completed");

}
