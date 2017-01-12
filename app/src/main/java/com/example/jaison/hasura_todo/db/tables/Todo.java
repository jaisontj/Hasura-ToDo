package com.example.jaison.hasura_todo.db.tables;

import com.example.jaison.hasura_todo.db.tables.records.TodoRecord;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import io.hasura.db.DeleteResult;
import io.hasura.db.InsertResult;
import io.hasura.db.PGField;
import io.hasura.db.Table;
import io.hasura.db.UpdateResult;
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
