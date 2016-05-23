package techcurb.arpi;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import java.util.concurrent.CountDownLatch;

import techcurb.net.SendMsg;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @SmallTest
    public void testSendMsgTest() throws Throwable
    {
        final CountDownLatch signal = new CountDownLatch(1);

        SendMsg msg = new SendMsg("test", "192.168.29.101", 8888);
        msg.setListener(new SendMsg.GetTaskListener() {
            @Override
            public void onComplete() {
                signal.countDown();
            }
        }).execute();
        signal.await();

        assertEquals("OK", msg.getMsg());


    }
}