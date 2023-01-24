package cn.wenhaha.data.plugin.mysql;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.db.Db;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.data.plugin.mysql.controller.MysqlController;
import cn.wenhaha.datasource.EventListen;
import cn.wenhaha.datasource.IDataObject;
import cn.wenhaha.datasource.IDataSourcePlugin;
import org.pf4j.Extension;

import java.io.Serializable;
@Extension
public class MysqlContext implements IDataSourcePlugin {

    public static Db db;

    public  static  final String  code  ="mysqlHikariCDataBase";

    public  static  final String  name  ="mysqlHikariC";

    public static Cache<Serializable, MysqlSource> lruCache = CacheUtil.newLRUCache(100);

    @Override
    public Class<?>[] controller() {
        return new Class[]{MysqlController.class};
    }


    @Override
    public UserContext getUserContext() {
        return new UserContext();
    }

    @Override
    public IDataObject getDataObject() {
        return new DataObjectContext();
    }

    @Override
    public EventListen getEventListen() {
        return new EventListenImp();
    }
}
