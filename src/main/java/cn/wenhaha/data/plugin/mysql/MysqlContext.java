package cn.wenhaha.data.plugin.mysql;

import cn.hutool.db.Db;
import cn.wenhaha.data.plugin.mysql.controller.MysqlController;
import cn.wenhaha.datasource.EventListen;
import cn.wenhaha.datasource.IDataObject;
import cn.wenhaha.datasource.IDataSourcePlugin;

public class MysqlContext implements IDataSourcePlugin {

    public static Db db;

    public  static  final String  code  ="mysqlHikariCDataBase";

    public  static  final String  name  ="mysqlHikariC";



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
