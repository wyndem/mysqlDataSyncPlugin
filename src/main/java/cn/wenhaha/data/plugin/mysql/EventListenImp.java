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
            "  \"id\" INTEGER NOT NULL,\n" +
            "  \"create_id\" TEXT,\n" +
            "  \"table\" TEXT,\n" +
            "  \"address\" TEXT,\n" +
            "  \"parameter\" TEXT,\n" +
            "  \"port\" INTEGER,\n" +
            "  \"name\" TEXT,\n" +
            "  \"password\" TEXT,\n" +
            "  \"url\" TEXT,\n" +
            "  \"connectionTimeout\" TEXT,\n" +
            "  \"maxLifetime\" TEXT,\n" +
            "  \"validationTimeout\" TEXT,\n" +
            "  \"idleTimeoutMs\" TEXT,\n" +
            "  \"minIdle\" TEXT,\n" +
            "  \"maxPoolSize\" integer,\n" +
            "  \"create_time\" TEXT,\n" +
            "  \"last_update\" TEXT,\n" +
            "  PRIMARY KEY (\"id\")\n" +
            ");";



    @Override
    public void onLoad(String id) {
        if (!FileUtil.exist(path)) {
            FileUtil.touch(path);
        }
        try (Connection connection = DriverManager.getConnection(jdbcUrl)) {
            connection.setAutoCommit(true);
            try (Statement statement = connection.createStatement()){
                statement.execute(sql);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("我被加载了 id为：{}", id);
    }


    @Override
    public void onStart(String id) {
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
