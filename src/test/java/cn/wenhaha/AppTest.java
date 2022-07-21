package cn.wenhaha;

import static org.junit.Assert.assertTrue;

import cn.wenhaha.data.plugin.mysql.DataObjectContext;
import cn.wenhaha.data.plugin.mysql.FieldTypeFactory;
import cn.wenhaha.data.plugin.mysql.MysqlContext;
import cn.wenhaha.data.plugin.mysql.UserContext;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        FieldTypeFactory.getType("bigint");

    }
}
