package cn.wenhaha.data.plugin.mysql;

import cn.wenhaha.datasource.DataUser;
import cn.wenhaha.datasource.IUserContext;

import java.io.Serializable;
import java.util.List;

public class UserContext implements IUserContext {


    @Override
    public Object getUserInfo(Serializable id) {
        return null;
    }

    @Override
    public Object updateUser(Serializable id) {
        return null;
    }

    @Override
    public List<DataUser> list() {
        return null;
    }

    @Override
    public boolean removeUser(Serializable id) {
        return false;
    }
}
