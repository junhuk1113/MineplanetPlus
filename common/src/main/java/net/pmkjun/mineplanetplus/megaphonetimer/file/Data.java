package net.pmkjun.mineplanetplus.megaphonetimer.file;

import java.io.Serializable;

public class Data implements Serializable {

    public boolean toggleMegaphonetimer = true;
    public boolean toggleAlertSound = true;
    
    public int MegaphonetimerXpos = 1000;
    public int MegaphonetimerYpos = 500;

    public long lastUsedTime = 0;
}