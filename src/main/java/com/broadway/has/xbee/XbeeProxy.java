package com.broadway.has.xbee;

import com.broadway.has.messaging.WateringRequest;

public interface XbeeProxy {


    public void handleWateringRequest(WateringRequest request) throws Exception;

}
