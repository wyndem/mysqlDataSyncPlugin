package cn.wenhaha.data.plugin.mysql.bean;


import lombok.Data;

import javax.sql.DataSource;

/**
 * 数据源
 * --------
 *
 * @author ：wyndem
 * @Date ：Created in 2022-07-21 20:12
 */

@Data
public class MysqlSource {

    private Integer id;
    private String table;
    private String name;
    private String password;
    private String address;
    private Integer port;
    private String parameter;
    private String url;
    private String connectionTimeout="60000";
    private String maxLifetime="30000";
    private String validationTimeout="3000";
    private String idleTimeoutMs="500000";
    private String minIdle="10";
    private Integer maxPoolSize=3;
    private DataSource dataSource;



}
