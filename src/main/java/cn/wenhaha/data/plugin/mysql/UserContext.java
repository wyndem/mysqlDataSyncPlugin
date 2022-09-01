package cn.wenhaha.data.plugin.mysql;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Entity;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.datasource.DataUser;
import cn.wenhaha.datasource.IUserContext;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class UserContext implements IUserContext<MysqlSource> {


    @Override
    public MysqlSource getUserInfo(Serializable id) {
        return UserService.byId(id);
    }

    @Override
    public MysqlSource updateUser(Serializable id) {

        return getUserInfo(id);
    }

    @Override
    public List<DataUser> list() {
        try {
            List<Entity> user = MysqlContext.db.findAll("user");
            return user.stream().map(u->{
                DataUser dataUser = new DataUser();
                dataUser.setId(u.getStr("id"));
                dataUser.setName(u.getStr("name"));
                dataUser.setPassword(u.getStr("password"));
                dataUser.setCreateTime(u.getStr("create_time"));
                String updateStr = u.getStr("last_update");
                if(StrUtil.isNotEmpty(updateStr)){
                    Date date = new Date(Long.parseLong(updateStr));
                    dataUser.setLastUpdateTime(DateUtil.formatDateTime(date));
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
            return MysqlContext.db.del(Entity.create("user").set("id", id))!=0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
