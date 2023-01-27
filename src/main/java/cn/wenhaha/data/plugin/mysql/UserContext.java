package cn.wenhaha.data.plugin.mysql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.datasource.DataUser;
import cn.wenhaha.datasource.IUserContext;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static cn.wenhaha.data.plugin.mysql.MysqlContext.lruCache;

public class UserContext implements IUserContext<MysqlSource> {




    @Override
    public MysqlSource getUserInfo(Serializable id) {
        if (lruCache.containsKey(id)) {
            return lruCache.get(id);
        }
        return updateUser(id);
    }

    @Override
    public MysqlSource updateUser(Serializable id) {
        MysqlSource mysqlSource = UserService.byId(id);
        lruCache.put(id, mysqlSource);
        return mysqlSource;
    }

    @Override
    public List<DataUser> list() {
        try {
            List<Entity> user = MysqlContext.db.findAll("user");
            return user.stream().map(u -> {
                DataUser dataUser = new DataUser();
                dataUser.setId(u.getStr("id"));
                dataUser.setName(u.getStr("name"));
                dataUser.setIcon("https://cloud.wenhaha.cn/api/v3/file/source/1399/dd.png?sign=-d0miYO2B_Pm3hxhpOa5qf4mn54Trmi-a-8zjfdGjLI%3D%3A0");
                dataUser.setPassword(u.getStr("password"));
                dataUser.setCreateTime(u.getStr("create_time"));
                String updateStr = u.getStr("last_update");
                if (StrUtil.isNotEmpty(updateStr)) {
                    dataUser.setLastUpdateTime(updateStr);
                }
                dataUser.setWebSite("https://www.mysql.com");
                dataUser.setPluginCode(MysqlContext.code);
                dataUser.setPluginName(MysqlContext.name);
                return dataUser;
            }).collect(Collectors.toList());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>(0);
    }

    @Override
    public boolean removeUser(Serializable id) {
        try {
            if (lruCache.containsKey(id)) {
                lruCache.remove(id);
            }
            return MysqlContext.db.del(Entity.create("user").set("id", id)) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
