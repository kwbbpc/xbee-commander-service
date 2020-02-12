package com.broadway.has.test;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.shared.access.AmazonDynamoDBLocal;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.broadway.has.history.HistoryManager;
import com.broadway.has.messaging.Request;
import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.messaging.XbeeCommand;
import com.broadway.has.repositories.CommandHistoryDao;
import com.broadway.has.repositories.CommandRunHistoryRepository;
import com.broadway.has.sensor.watering.WateringExecutor;
import com.broadway.has.xbee.MessagingConfigProps;
import com.broadway.has.xbee.XbeeProxy;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ITTestGetHistories {



    @Autowired
    WateringExecutor wateringExecutor;

    @Autowired
    HistoryManager historyManager;


    @Autowired
    private AmazonDynamoDB amazonDynamoDB;

    private DynamoDBMapper dynamoDBMapper;

    @Before
    public void setup() throws Exception{

        dynamoDBMapper = new DynamoDBMapper(amazonDynamoDB);

        CreateTableRequest tableRequest = dynamoDBMapper
                .generateCreateTableRequest(CommandHistoryDao.class);
        tableRequest.setProvisionedThroughput(
                new ProvisionedThroughput(1L, 1L));
        amazonDynamoDB.createTable(tableRequest);

    }

    @Test
    public void TestGetHistories(){



        WateringRequest command = new WateringRequest();
        command.setOn(true);
        command.setRequestDetails(new Request(DateTime.now().toDate(), true, "watering1"));
        command.setRunTimeMs(10000);
        command.setValveNumber(1);
        command.setXbeeAddr("1337");

        //execute a command
        wateringExecutor.executeWateringRequest(command);

        //execute another command
        command.setRequestDetails(new Request(DateTime.now().plusDays(50).toDate(), true, "watering2"));
        wateringExecutor.executeWateringRequest(command);

        //execute another command
        command.setRequestDetails(new Request(DateTime.now().plusDays(100).toDate(), true, "watering2"));
        wateringExecutor.executeWateringRequest(command);


        //now get the commands back
        Page<CommandHistoryDao> history = historyManager.getRunHistories(1, 50);



    }

}
