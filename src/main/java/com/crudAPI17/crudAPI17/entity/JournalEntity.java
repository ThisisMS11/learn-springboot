package com.crudAPI17.crudAPI17.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Date;

@Document(collection = "journal_entries")
@Getter
@Setter
public class JournalEntity {
    @Id
    private ObjectId id;
    private String title;
    private LocalDateTime date;

    public LocalDateTime getDate() {
        return date;
    }
    private String content;
}
