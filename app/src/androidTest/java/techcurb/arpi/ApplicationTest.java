package techcurb.arpi;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void testSendMsgTest()
    {
        SendMsg msg = new SendMsg("test", "192.168.29.101", 8888);
        msg.execute();
        msg.waitToComplete();
        assertEquals("OK", msg.getMsg());
    }
}