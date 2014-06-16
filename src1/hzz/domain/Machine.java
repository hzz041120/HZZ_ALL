package hzz.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * ����
 * 
 * @author hzz041120 2014��4��6�� ����8:12:38
 */
public class Machine {

    public static final Map<String, Machine> machineMap = new HashMap<String, Machine>();

    private Machine(String machineName) {
        this.machineName = machineName;
    }

    // ��������
    private String machineName;
    // ���Խ���������Ŀ�ʼʱ��
    private int    startWorkDelay;

    public String getMachineName() {
        return machineName;
    }

    public int getStartWorkDelay() {
        return startWorkDelay;
    }

    public void setStartWorkDelay(int startWorkDelay) {
        this.startWorkDelay = startWorkDelay;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public static final Machine newMachine(String machineName) {
        if (machineMap.containsKey(machineName)) {
            return machineMap.get(machineName);
        } else {
            Machine m = new Machine(machineName);
            machineMap.put(machineName, m);
            return m;
        }
    }

    public void reset() {
        this.startWorkDelay = 0;
    }

}
