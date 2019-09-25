package com.broadway.has.repositories;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@EnableDynamoDBRepositories
        (basePackages = "com.broadway.has.repositories")
public class DynamoDbConfig {

    @Value("${aws.dynamodb.endpoint}")
    private String amazonDynamoDBEndpoint;


    @Bean
    public com.amazonaws.services.dynamodbv2.AmazonDynamoDB amazonDynamoDB() {
        com.amazonaws.services.dynamodbv2.AmazonDynamoDB amazonDynamoDB
                = new AmazonDynamoDBClient(new DefaultAWSCredentialsProviderChain().getCredentials());

        if (!StringUtils.isEmpty(amazonDynamoDBEndpoint)) {
            amazonDynamoDB.setEndpoint(amazonDynamoDBEndpoint);
        }

        return amazonDynamoDB;
    }

}
