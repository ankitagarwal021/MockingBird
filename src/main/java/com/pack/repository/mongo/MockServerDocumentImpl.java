package com.pack.repository.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created By: Ankit Agarwal
 **/

@Repository
public interface MockServerDocumentImpl extends MongoRepository<com.pack.repository.mongo.entity.MockServerDocument,String> {

}