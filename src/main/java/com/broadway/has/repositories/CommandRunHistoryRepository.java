package com.broadway.has.repositories;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.EnableScanCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;


public interface CommandRunHistoryRepository extends PagingAndSortingRepository<CommandHistoryDao, String> {

    @EnableScan
    @EnableScanCount
    Page<CommandHistoryDao> findAll(Pageable pageable);

}
