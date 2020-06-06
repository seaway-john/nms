package com.oem.nms.common.mongo.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.convert.*;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

/**
 * @author Seaway John
 */
@Slf4j
@Configuration
public class MongoConfig {

    @Bean
    public MappingMongoConverter mappingMongoConverter(MongoDatabaseFactory factory,
                                                       MongoMappingContext context,
                                                       BeanFactory beanFactory) {
        DbRefResolver dbRefResolver = new DefaultDbRefResolver(factory);
        MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, context);

        try {
            converter.setCustomConversions(beanFactory.getBean(MongoCustomConversions.class));
        } catch (Exception e) {
            log.error("Exception in mappingMongoConverter, reason {}", e.getMessage());
        }

        // Don't save column _class to mongo collection
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));

        return converter;
    }

}
