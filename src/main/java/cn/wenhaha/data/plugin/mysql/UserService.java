package cn.wenhaha.data.plugin.mysql;

import cn.hutool.db.Entity;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.datasource.exception.DataSourceException;
import cn.wenhaha.datasource.exception.NotFondException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * --------
 *
 * @author ：wyndem
 * @Date ：Created in 2022-07-21 22:21
 */
public class UserService {


    public static MysqlSource byId(Serializable id) {

        try{
            List<Entity> entities = MysqlContext.db.find(Entity.create("user").set("id", id));
            if(entities==null || entities.size()==0){
                throw new NotFondException("末查到该id ->" + id);
            }
            MysqlSource mysqlSource = entities.get(0).toBean(MysqlSource.class);
            DataSourceUtil.initDataSource(mysqlSource);
            return mysqlSource;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e.getMessage());
        }
    }


}
