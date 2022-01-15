package com.unipi.data.mining.backend.repositories;

import com.unipi.data.mining.backend.entities.mongodb.MongoUser;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface MongoUserRepository extends MongoRepository<MongoUser, ObjectId> {

    MongoUser findMongoUserByEmail(String email);

    List<MongoUser> findMongoUsersByEmail(String email);

    @Query(value="{'cluster': ?0}", fields = "{survey: 1}")
    List<MongoUser> findMongoUsersByCluster(int cluster);

    @Query(value="{}", fields = "{email: 1, _id: 0}")
    List<MongoUser> findEmails();

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);
}