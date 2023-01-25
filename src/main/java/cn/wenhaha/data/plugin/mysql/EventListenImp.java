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


    /**
     * <p>
     *    onload执行完后，会销毁该对象
     * </p>
     * @Author: Wyndem
     * @DateTime: 2023-01-25 10:21
     *
     */
    @Override
    public void onLoad(String id) {
        logger.info("{} 正在加载插件", id);
        if (FileUtil.exist(path)) {
            logger.info("{} 文件已经创建了，不在进行创建",path);
            return;
        }
        FileUtil.touch(path);
        DataSource ds = new SimpleDataSource(jdbcUrl,"","");

        try {
            Db.use(ds).execute(sql);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }

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
