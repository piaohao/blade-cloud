package org.piaohao.blade.cloud.registry.client.controller;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.blade.Blade;
import com.blade.mvc.annotation.Path;
import com.blade.mvc.annotation.Route;
import com.blade.mvc.http.Request;
import com.blade.mvc.http.Response;
import org.piaohao.blade.cloud.registry.client.endpoint.AppInfo;
import org.piaohao.blade.cloud.registry.client.endpoint.AppManager;

import static com.blade.mvc.Const.ENV_KEY_SERVER_PORT;

@Path
public class IndexController {

    @Route("/info")
    public void welcome(Request request, Response response) {
        String port = Blade.of.getProperty(ENV_KEY_SERVER_PORT);
        response.text(pmeort);
    }

    @Route("/loadbalance")
    public void loadbalance(Request request, Response response) {
        String requestUri = "lb://blade-client/info";
        response.text(restTemplateRequest(requestUri));
    }

    private String restTemplateRequest(String requestUri) {
        if (requestUri.startsWith("lb://")) {
            String serviceId = StrUtil.sub(requestUri, "lb://".length(), StrUtil.indexOf(requestUri, '/', "lb://".length()));
            String contextPath = StrUtil.subSuf(requestUri, StrUtil.indexOf(requestUri, '/', "lb://".length()));
            AppInfo info = AppManager.getInstance().list()
                    .stream()
                    .filter(appInfo -> appInfo.getServiceId().equals(serviceId))
                    .filter(AppInfo::isUp)
                    .findAny()
                    .get();
            return HttpUtil.get(info.getUri() + contextPath);
        } else if (requestUri.startsWith("http://")) {
            return HttpUtil.get(requestUri);
        }
        return null;
    }
}
