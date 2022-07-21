package cn.wenhaha.data.plugin.mysql;

import cn.hutool.core.util.StrUtil;
import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

/**
 * 数据源
 * --------
 *
 * @author ：wyndem
 * @Date ：Created in 2022-07-21 20:19
 */
public class DataSourceUtil {

    public static void initDataSource(MysqlSource source){
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(source.getUrl());
        config.setUsername(source.getName());
        config.setPassword(source.getPassword());
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaxLifetime(30000);
        config.setConnectionTimeout(60000);
        config.setValidationTimeout(3000);
        config.setIdleTimeout(500000);
        config.setMinimumIdle(10);
        config.setMaximumPoolSize(30);
        source.setDataSource(new HikariDataSource(config));
    }

}
