package cn.ouya.gateway.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface {
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return CollUtil.toList("root");
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return CollUtil.toList("root");
    }
}
