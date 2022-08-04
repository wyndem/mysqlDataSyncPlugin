package cn.wenhaha.data.plugin.mysql.controller;


import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.db.sql.SqlExecutor;
import cn.wenhaha.data.plugin.mysql.DataSourceUtil;
import cn.wenhaha.data.plugin.mysql.MysqlContext;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import cn.wenhaha.data.plugin.mysql.bean.TemMysqlInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("mysql")
@CrossOrigin(origins = {"http://localhost:3000", "null"})
@Slf4j
public class MysqlController {


    @Autowired
    private HttpServletRequest request;

    @PostMapping("add")
    public String add(@RequestBody MysqlSource mysql) {


        Long userId=(Long)request.getAttribute("id");
        try{
            int count = MysqlContext.db.count(Entity.create("user")
                    .set("address",mysql.getAddress())
                    .set("port",mysql.getPort())
                    .set("name",mysql.getName())
                    .set("table",mysql.getTable()));
            if(count!=0){
                return "该账号已经被添加了";
            }
            DataSourceUtil.initDataSource(mysql);
            String s = Db.use(mysql.getDataSource()).queryString("select version();");
            log.info("数据库版本{}",s);

            Entity entity = Entity.parse(mysql)
                    .set("create_id", userId)
                    .set("create_time", DateUtil.now())
                    .set("last_update", DateUtil.now());
            entity.remove("dataSource");
            int rows = MysqlContext.db.insert(entity.setTableName("user"));
            if (rows==0){
                return "ERROR_插入失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            return  e.getMessage();
        }
        return "ok";
    }


    @PostMapping("update")
    public String update(@RequestBody MysqlSource mysql) throws SQLException {
        try{
            Long userId=(Long)request.getAttribute("id");
            int count = MysqlContext.db.count(Entity.create("user").set("id", mysql.getId()));
            if(count==0){
                return "该账号末找到";
            }
            DataSourceUtil.initDataSource(mysql);
            String s = Db.use(mysql.getDataSource()).queryString("select version();");
            log.info("数据库版本{}",s);

            Entity entity = Entity.parse(mysql)
                    .set("create_id", userId)
                    .set("last_update", DateUtil.now());
            entity.remove("dataSource");
            int rows = MysqlContext.db.update(entity.setTableName("user"),Entity.create().set("id",mysql.getId()));
            if (rows==0){
                return "ERROR_插入失败";
            }
        }catch (Exception e){
            e.printStackTrace();
            return  e.getMessage();
        }

        return "ok";
    }

    @PostMapping("table")
    public  Object getTableName(@RequestBody TemMysqlInfo mysql) {

        try {
            DataSource ds = new SimpleDataSource(mysql.getUrl(), mysql.getName(), mysql.getPassword());
            ResultSet databases = SqlExecutor.callQuery(ds.getConnection(), "show databases;");
            ArrayList<String> databasesList = new ArrayList<>();
            while (databases.next()){
                String string = databases.getString("Database");
                databasesList.add(string);
            }

            return databasesList;

        } catch (Exception e){
            return ExceptionUtil.getSimpleMessage(e);
        }
    }


    @GetMapping("info/{id}")
    public Object info(@PathVariable("id")String id) throws SQLException {

        List<Entity> entities = MysqlContext.db.find(Entity.create("user").set("id", id));
        if(entities==null || entities.size()==0){
            return "该账号末找到";
        }
        return entities.get(0);
    }

}
