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
    private String url;
    private DataSource dataSource;



}
