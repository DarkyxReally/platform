package com.platform.auth.common.event;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

import org.springframework.cloud.bus.event.RemoteApplicationEvent;

/**
 * 认证远程事件
 */
@Getter
@Setter
public class AuthRemoteEvent extends RemoteApplicationEvent {

    private static final long serialVersionUID = 7494601286767030018L;
    private List<String> allowedClient;

    public AuthRemoteEvent(Object source, String originService, String destinationService, List<String> allowedClient) {
        // source is the object that is publishing the event
        // originService is the unique context ID of the publisher
        super(source, originService, destinationService);
        this.allowedClient = allowedClient;
    }

}
