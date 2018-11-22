package gregtech.common;

import gregtech.GT5_Mod;
import gregtech.api.util.GT_Log;

import java.util.ArrayList;

import static gregtech.api.enums.GT_Values.EMPTY_STRING;

public class GT_PlayerActivityLogger
        implements Runnable {
    public void run() {
        try {
            for (; ; ) {
                if (GT_Log.pal == null) {
                    return;
                }
                ArrayList<String> tList = GT5_Mod.gregtechproxy.mBufferedPlayerActivity;
                GT5_Mod.gregtechproxy.mBufferedPlayerActivity = new ArrayList();
                String tLastOutput = EMPTY_STRING;
                int i = 0;
                for (int j = tList.size(); i < j; i++) {
                    if (!tLastOutput.equals(tList.get(i))) {
                        GT_Log.pal.println(tList.get(i));
                    }
                    tLastOutput = tList.get(i);
                }
                Thread.sleep(10000L);
            }
        } catch (Throwable e) {
        }
    }
}
