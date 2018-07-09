package org.piaohao.blade.cloud.registry.client.endpoint;

import cn.hutool.http.HttpUtil;
import com.blade.kit.UUID;
import com.google.common.collect.Lists;
import lombok.Setter;
import org.piaohao.blade.cloud.registry.client.exception.AppNotExistException;

import java.util.List;

public class AppManager {

    private static AppManager appManager = new AppManager();

    @Setter
    private List<AppInfo> appInfoList = Lists.newLinkedList();

    private AppManager() {

    }

    public static AppManager getInstance() {
        return appManager;
    }

    public String add(AppInfo appInfo) {
        for (AppInfo tmp: appInfoList) {
            if (tmp.getHost().equals(appInfo.getHost()) &&
                    tmp.getPort().equals(appInfo.getPort())) {
                tmp.setUp(true);
                return tmp.getId();
            }
        }
        String id = UUID.UU32();
        appInfo.setId(id);
        this.appInfoList.add(appInfo);
        return id;
    }

    public boolean exist(String appId) {
        for (AppInfo tmp: appInfoList) {
            if (tmp.getId().equals(appId)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUp(String appId) {
        AppInfo appInfo = null;
        for (AppInfo tmp: appInfoList) {
            if (tmp.getId().equals(appId)) {
                appInfo = tmp;
                break;
            }
        }
        if (appInfo == null) {
            throw new AppNotExistException();
        }
        String ret = null;
        try {
            ret = HttpUtil.get(appInfo.getUri() + "/registry/heartbeat");
        } catch (Exception e) {
            appInfo.setUp(false);
            return false;
        }
        if (ret.equals("true")) {
            appInfo.setUp(true);
            return true;
        }
        appInfo.setUp(false);
        return false;
    }

    public List<AppInfo> list() {
        return appInfoList;
    }

    public void remove(AppInfo appInfo) {
        appInfoList.remove(appInfo);
    }

}
