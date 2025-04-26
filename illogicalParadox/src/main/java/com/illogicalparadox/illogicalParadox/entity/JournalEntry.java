package com.illogicalparadox.illogicalParadox.entity;
import com.illogicalparadox.illogicalParadox.enums.Sentiment;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
@Document(collection ="journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private ObjectId id;
    @NonNull
    private String name;
    private LocalDateTime date;
    private String content;
    private Sentiment sentiment;
}
