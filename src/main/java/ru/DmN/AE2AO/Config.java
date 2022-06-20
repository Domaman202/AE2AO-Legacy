package ru.DmN.AE2AO;

import appeng.me.GridNode;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class Config implements Cloneable {
    public boolean DisableChannels = false;
    public boolean ControllerLimits = false;
    //
    public int Max_X = 7;
    public int Max_Y = 7;
    public int Max_Z = 7;
    // Storage Cell Fire Damage
    public boolean SCFD = false;
    public boolean StorageCellLimits = true;
    //
    public boolean ChatInfo = true;
    //
    public Config clone() {
        try {
            return (Config) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
    //
    public void useConfig() {
        try {
            Field f = GridNode.class.getDeclaredField("CHANNEL_COUNT");
            f.setAccessible(true);
            int[] arr = (int[]) f.get(null);
            arr[1] = this.DisableChannels ? Integer.MAX_VALUE : 8;
            arr[2] = this.DisableChannels ? Integer.MAX_VALUE : 32;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
