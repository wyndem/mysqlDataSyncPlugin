package cn.wenhaha.data.plugin.mysql;

import cn.hutool.db.Db;
import cn.wenhaha.data.plugin.mysql.controller.MysqlController;
import cn.wenhaha.datasource.*;

public class MysqlContext implements IDataSourcePlugin {

    public static Db db;
//    public  static  final HTTP http = OkHttpUtil.buildHttp();
    public  static  final String  code  ="mysql";

    public  static  final String  name  ="mysql";


    @Override
    public String addPage() {
        return null;
    }

    @Override
    public String updatePage() {
        return null;
    }

    @Override
    public Class<?>[] controller() {
        return new Class[]{MysqlController.class};
    }

    @Override
    public IPlugin getPlugin() {
        return null;
    }

    @Override
    public IUserContext getUserContext() {
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
