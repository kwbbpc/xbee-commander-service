package com.broadway.has.history;

import com.broadway.has.exceptions.DatabaseError;
import com.broadway.has.repositories.CommandHistoryDao;
import com.broadway.has.repositories.CommandRunHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HistoryManager {

    @Autowired
    private CommandRunHistoryRepository historyRepository;

    public Page<CommandHistoryDao> getRunHistories(int page, int pageSize){

        try {

            return historyRepository.findAll(new PageRequest(page, pageSize));


        }catch (Exception e){
            throw new DatabaseError(e.getMessage());
        }

    }



}
