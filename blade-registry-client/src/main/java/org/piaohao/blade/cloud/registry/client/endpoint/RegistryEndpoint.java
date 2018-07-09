package org.piaohao.blade.cloud.registry.client.endpoint;

import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Response;

@Path("/registry")
public class RegistryEndpoint {

    @Route("/heartbeat")
    public void heartbeat(Response response) {
        response.text("true");
    }

}
