package org.piaohao.blade.cloud.registry.center.endpoint;

import cn.hutool.json.JSONObject;
import com.blade.mvc.annotation.Param;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Response;

@Path("/registry")
public class RegistryEndpoint {

    @Route("/add")
    public void add(@Param String host, @Param Integer port, @Param String serviceId, Response response) {
        refresh(host, port, serviceId, response);
    }

    @Route("/list")
    public void list(Response response) {
        response.json(AppManager.getInstance().list());
    }

    @Route("/refresh")
    public void refresh(@Param String host, @Param Integer port, @Param String serviceId, Response response) {
        AppManager.getInstance().add(
                new AppInfo.AppInfoBuilder()
                        .serviceId(serviceId)
                        .host(host)
                        .port(port)
                        .uri("http://" + host + ":" + port)
                        .isUp(true)
                        .build()
        );
        response.json(new JSONObject()
                .put("code", 0)
                .put("data", AppManager.getInstance().list()));
    }

}
