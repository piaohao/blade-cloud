package org.piaohao.blade.cloud.registry.center.endpoint;

import com.blade.ioc.annotation.Bean;
import com.blade.task.annotation.Schedule;

import java.util.List;

@Bean
public class SimpleTask {

    @Schedule(cron = "0/5 * * * * ?")
    public void synAppInfo() {
        List<AppInfo> list = AppManager.getInstance().list();
        for (AppInfo appInfo: list) {
            boolean up = AppManager.getInstance().isUp(appInfo.getId());
            if (!up) {
                AppManager.getInstance().remove(appInfo);
            }
        }
    }

}