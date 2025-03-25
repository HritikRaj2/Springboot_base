package com.illogicalparadox.illogicalParadox.repository;

import com.illogicalparadox.illogicalParadox.entity.JournalEntry;
import com.illogicalparadox.illogicalParadox.entity.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.illogicalparadox.illogicalParadox.entity.JournalEntry;

public interface UserRepository extends MongoRepository<User, ObjectId> {
    User findByUsername(String username);
}
