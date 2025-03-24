package com.illogicalparadox.illogicalParadox.entity;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
@Document(collection ="journal_entries")
@Data
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    private LocalDateTime date;
    private String content;
}
