package com.broadway.has.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@EnableScan
public interface CommandRunHistoryRepository extends CrudRepository<CommandHistoryDao, String> {
}
