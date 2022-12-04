package cn.wenhaha.data.plugin.mysql;

import cn.wenhaha.data.plugin.mysql.bean.MysqlSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

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
        config.setMaxLifetime(Long.parseLong(source.getMaxLifetime()));
        config.setConnectionTimeout(Long.parseLong(source.getConnectionTimeout()));
        config.setValidationTimeout(Long.parseLong(source.getValidationTimeout()));
        config.setIdleTimeout(Long.parseLong(source.getIdleTimeoutMs()));
        config.setMinimumIdle(Integer.parseInt(source.getMinIdle()));
        config.setMaximumPoolSize(source.getMaxPoolSize());
        config.setConnectionTestQuery("select 1");
        source.setDataSource(new HikariDataSource(config));
    }

}
