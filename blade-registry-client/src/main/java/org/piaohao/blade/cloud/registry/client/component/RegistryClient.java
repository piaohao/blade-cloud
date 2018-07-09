package org.piaohao.blade.cloud.registry.client.component;

import cn.hutool.core.util.NetUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.blade.Blade;
import com.blade.event.BeanProcessor;
import com.blade.ioc.annotation.Bean;
import com.blade.ioc.annotation.Order;
import com.blade.ioc.annotation.Value;
import org.piaohao.blade.cloud.registry.client.endpoint.AppInfo;
import org.piaohao.blade.cloud.registry.client.endpoint.AppManager;

import java.net.InetAddress;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.blade.mvc.Const.ENV_KEY_SERVER_PORT;

@Order(1)
@Bean
public class RegistryClient implements BeanProcessor {

    @Value(name = "application.name")
    private String applicationName;

    @Override
    public void processor(Blade blade) {
        String port = blade.environment().get(ENV_KEY_SERVER_PORT).get();
        InetAddress localHost = NetUtil.getLocalhost();
        String hostAddress = localHost.getHostAddress();
        String ret = null;
        try {
            ret = HttpUtil.get("http://localhost:8080/registry/add",
                    new JSONObject().put("host", hostAddress)
                            .put("port", port)
                            .put("serivceId", applicationName));
        } catch (Exception e) {

        }
        List<AppInfo> appInfos = JSONUtil.toList(JSONUtil.parseObj(ret).getJSONArray("data"), AppInfo.class);
        AppManager.getInstance().setAppInfoList(appInfos);
        Executors.newSingleThreadScheduledExecutor()
                .scheduleWithFixedDelay(
                        () -> {
                            String result = null;
                            try {
                                result = HttpUtil.get("http://localhost:8080/registry/refresh",
                                        new JSONObject().put("host", hostAddress)
                                                .put("port", port)
                                                .put("serivceId", applicationName));
                            } catch (Exception e) {

                            }
                            List<AppInfo> appInfos1 = JSONUtil.toList(JSONUtil.parseObj(result).getJSONArray("data"), AppInfo.class);
                            AppManager.getInstance().setAppInfoList(appInfos1);
                        },
                        5L,
                        5L,
                        TimeUnit.SECONDS
                );
    }

}