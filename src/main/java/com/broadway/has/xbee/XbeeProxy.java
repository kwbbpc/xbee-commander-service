package com.broadway.has.xbee;

import com.broadway.has.messaging.WateringRequest;
import com.broadway.has.messaging.XbeeCommand;

public interface XbeeProxy {


    public void proxyCommand(XbeeCommand cmd) throws Exception;

}
