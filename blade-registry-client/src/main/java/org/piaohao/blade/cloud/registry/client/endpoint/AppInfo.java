package org.piaohao.blade.cloud.registry.client.endpoint;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AppInfo {

    private String id;
    private String serviceId;
    private String host;
    private Integer port;
    private String uri;
    private boolean isUp;
}
