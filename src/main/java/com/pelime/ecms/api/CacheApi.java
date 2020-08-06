package com.pelime.ecms.api;

import com.pelime.ecms.common.R;
import com.pelime.ecms.modules.sys.service.SysMenuService;
import com.pelime.ecms.modules.sys.shiro.UserRealm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cache")
public class CacheApi {

    @Autowired
    UserRealm userRealm;
    @Autowired
    SysMenuService sysMenuService;

    @RequestMapping("clearAll")
    public R clearAll(){
        try {
            userRealm.clearCache();
            sysMenuService.clearCache();
            return R.ok("清除成功");
        }catch (Exception e){
            e.printStackTrace();
            return R.error(e.getMessage());
        }
    }
}
