package cn.wenhaha.data.plugin.mysql;

import cn.hutool.core.io.FileUtil;
import cn.hutool.db.Db;
import cn.hutool.db.ds.simple.SimpleDataSource;
import cn.hutool.system.SystemUtil;
import cn.wenhaha.datasource.EventListen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class EventListenImp  implements EventListen {

    private Logger logger = LoggerFactory.getLogger(EventListenImp.class);

    private final String path = SystemUtil.getUserInfo().getCurrentDir() + File.separator
            + MysqlContext.code + File.separator + "user.db";

    private final String jdbcUrl = "jdbc:sqlite:" + path;


    private final String sql="CREATE TABLE \"user\" (\n" +
            "  \"id\" INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
            "  \"name\" TEXT,\n" +
            "  \"password\" TEXT,\n" +
            "  \"url\" TEXT,\n" +
            "  \"cid\" TEXT,\n" +
            "  \"secret\" TEXT,\n" +
            "  \"token\" TEXT,\n" +
            "  \"loginJson\" TEXT,\n" +
            "  \"last_update\" TEXT,\n" +
            "  \"create_time\" TEXT\n" +
            ");";



    @Override
    public void onLoad(Integer id) {
        if (!FileUtil.exist(path)) {
            FileUtil.touch(path);
        }
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.setAutoCommit(true);
            try (Statement statement = connection.createStatement()){
                boolean hasResults = statement.execute(sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("我被加载了 id为：{}", id);
    }


    @Override
    public void onStart(Integer id) {
        DataSource ds = new SimpleDataSource(jdbcUrl, "", "");
        MysqlContext.db= Db.use(ds);
        logger.info("我被启动了");

    }

    @Override
    public void onStop() {
        MysqlContext.db=null;
        logger.info("我被停止");
    }

    @Override
    public void unLoad() {
        FileUtil.del(path);
        logger.info("我被卸载了");
    }
}
