package com.journalApp.demo.entity;


import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "users")
@NoArgsConstructor
@AllArgsConstructor

public class User {

    @Id
    private ObjectId id;


    @NonNull
    @Indexed(unique=true)
    private String userName;
    private String email;
    private boolean sentimentAnalysis;

    @NonNull
    private String password;
    private List<JournalEntry> journalEntries=new ArrayList<>();
    private List<String> roles;
}
