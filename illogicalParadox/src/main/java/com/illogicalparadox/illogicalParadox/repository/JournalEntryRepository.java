package com.illogicalparadox.illogicalParadox.repository;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;

public interface JournalEntryRepository extends MongoRepository<JournalEntry, Object> {
}
