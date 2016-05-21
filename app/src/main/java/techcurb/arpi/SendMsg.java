package techcurb.arpi;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * Created by seanx_000 on 5/21/2016.
 *
 * params
 * String message
 * String IP (IP4)
 * int port
 *
 * To run:
 * call execute() method
 */
public class SendMsg extends AsyncTask<Object, Object, Object>{

    private String msg = "";
    private String ip = "";
    private String recv_msg = "";
    private int p = 80;
    private boolean isHalt = true;

    public SendMsg(String message, String IP, int port)
    {
        super();
        msg = message;
        ip = IP;
        p = port;
    }

    public void waitToComplete() {
        while(isHalt);
    }

    public String getMsg() {
        return recv_msg;
    }

    @Override
    protected Object doInBackground(Object[] params) {

        try {
            Socket s = new Socket(ip, p);
            DataOutputStream dOut = new DataOutputStream(s.getOutputStream());
            BufferedReader dIn  = new BufferedReader(new InputStreamReader(s.getInputStream()));
            dOut.write(msg.getBytes());
            dOut.flush();

            recv_msg = dIn.readLine();
            dIn.close();
            dOut.close();
            isHalt = false;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
