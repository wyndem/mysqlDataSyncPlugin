package cn.wenhaha;

import cn.wenhaha.data.plugin.mysql.DataObjectContext;
import cn.wenhaha.data.plugin.mysql.EventListenImp;
import cn.wenhaha.data.plugin.mysql.FieldTypeFactory;
import cn.wenhaha.datasource.ObjInfo;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{


    @Before
    public void test(){
        EventListenImp eventListenImp = new EventListenImp();
        eventListenImp.onLoad(1);
        eventListenImp.onStart(1);
    }



    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        FieldTypeFactory.getType("bigint");

    }

    @Test
    public void testObjInfo()
    {

        DataObjectContext dataObjectContext = new DataObjectContext();
        ObjInfo account = dataObjectContext.info("Account", 1);
        System.out.println(account.getColumns().size());
    }


}
