package com.example.jaison.hasura_todo.db.tables.records;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.Date;
import java.sql.Time;
import com.google.gson.JsonElement;

public class TodoRecord {
    @SerializedName("id")
    public Integer id;

    @SerializedName("title")
    public String title;

    @SerializedName("user_id")
    public Integer userId;

    @SerializedName("completed")
    public Boolean completed;

}
