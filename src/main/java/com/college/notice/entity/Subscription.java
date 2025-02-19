package com.college.notice.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document(collection = "subscriptions")
public class Subscription {

    @Id
    private String id;
    private String userId;
    private List<String> categories;

    public Subscription() {}

    public Subscription(String userId, List<String> categories) {
        this.userId = userId;
        this.categories = categories;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }
}
