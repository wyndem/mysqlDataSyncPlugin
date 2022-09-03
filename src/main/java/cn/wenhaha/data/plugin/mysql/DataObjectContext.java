package cn.wenhaha.data.plugin.mysql;

import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.datasource.Column;
import cn.wenhaha.datasource.IDataObject;
import cn.wenhaha.datasource.Obj;
import cn.wenhaha.datasource.ObjInfo;
import cn.wenhaha.datasource.exception.DataSourceException;
import cn.wenhaha.datasource.exception.NotFondException;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataObjectContext  implements IDataObject {



    @Override
    public List<Obj> list(Serializable id) {
        MysqlSource mysqlSource = UserService.byId(id);

        try {
            List<Entity> table = Db.use(mysqlSource.getDataSource())
                    .find(Arrays.asList("TABLE_NAME","TABLE_COMMENT"),
                            Entity.create("information_schema.TABLES")
                                    .set("TABLE_SCHEMA", mysqlSource.getTable())
                    );
           return table.stream().map(e->{
                Obj obj = new Obj();
                obj.setNameApi(e.getStr("TABLE_NAME"));
                obj.setName(obj.getNameApi());
                obj.setUpdateable(true);
                obj.setCreateable(true);
                obj.setDeletable(true);
                obj.setPluginName(MysqlContext.name);
                obj.setPluginCode(MysqlContext.code);
                return obj;
            }).collect(Collectors.toList());
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e.getMessage());

        }

    }

    @Override
    public ObjInfo info(String nameApi, Serializable id) {

        MysqlSource mysqlSource = UserService.byId(id);

        try {
            List<Entity> table = Db.use(mysqlSource.getDataSource())
                    .find(Arrays.asList("TABLE_NAME","TABLE_COMMENT"),
                            Entity.create("information_schema.TABLES")
                                    .set("TABLE_SCHEMA", mysqlSource.getTable())
                                    .set("TABLE_NAME",nameApi)
                    );
            if(table==null || table.size()==0){
                throw new NotFondException(nameApi+"末找到");
            }
            Entity e = table.get(0);
            ObjInfo objInfo = new ObjInfo();
            objInfo.setUpdateable(true);
            objInfo.setCreateable(true);
            objInfo.setDeletable(true);
            objInfo.setNameApi(e.getStr("TABLE_NAME"));
//            String tableComment = e.getStr("TABLE_COMMENT");
            objInfo.setName(objInfo.getNameApi());
            objInfo.setPluginName(MysqlContext.name);
            objInfo.setPluginCode(MysqlContext.code);

            List<Entity> columns = Db.use(mysqlSource.getDataSource())
                    .find(
                            Entity.create("information_schema.Columns")
                                    .set("TABLE_SCHEMA", mysqlSource.getTable())
                                    .set("TABLE_NAME",nameApi)
                    );


            List<Column> columnList = columns.stream().map(c -> {
                Column column = new Column();
                column.setNameApi(c.getStr("COLUMN_NAME"));
                String columnComment = c.getStr("COLUMN_COMMENT");
                column.setName(StrUtil.isEmpty(columnComment)?column.getNameApi():columnComment);
                String privileges = c.getStr("PRIVILEGES");
                column.setCreateable(StrUtil.contains(privileges, "insert"));
                column.setUpdateable(StrUtil.contains(privileges, "update"));

                column.setCustom(true);
                column.setNullable(StrUtil.equals(c.getStr("IS_NULLABLE"), "YES"));
                column.setPrimaryKey(StrUtil.equals(c.getStr("COLUMN_KEY"), "PRI"));
                column.setRequired(column.getNullable());
                column.setLength(c.getInt("CHARACTER_MAXIMUM_LENGTH"));
                column.setDatatype(FieldTypeFactory.getType(c.getStr("DATA_TYPE")));
                return column;
            }).collect(Collectors.toList());
            objInfo.setColumns(columnList);
            return objInfo;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataSourceException(e.getMessage());
        }

    }
}
