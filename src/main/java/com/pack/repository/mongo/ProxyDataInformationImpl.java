package com.pack.repository.mongo;

import com.pack.repository.mongo.entity.ProxyDataInformationDao;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created By: Ankit Agarwal
 **/

public interface ProxyDataInformationImpl extends MongoRepository<ProxyDataInformationDao,String> {



}
