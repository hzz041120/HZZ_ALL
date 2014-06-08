package hzz.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 机器
 * 
 * @author hzz041120 2014年4月6日 下午8:12:38
 */
public class Machine {

    public static final Map<String, Machine> machineMap = new HashMap<String, Machine>();

    private Machine(String machineName) {
        this.machineName = machineName;
    }

    // 机器名称
    private String                       machineName;
    // 可以接收新任务的开始时间
    private int                          startWorkDelay;

    /**
     * 任务收益耗时表
     */
    private Map<JobType, JobExecuteInfo> jobExecMap = new HashMap<JobType, JobExecuteInfo>();

    public String getMachineName() {
        return machineName;
    }

    public int getStartWorkDelay() {
        return startWorkDelay;
    }

    public void setStartWorkDelay(int startWorkDelay) {
        this.startWorkDelay = startWorkDelay;
    }

    public Map<JobType, JobExecuteInfo> getJobExecMap() {
        return jobExecMap;
    }

    public void setMachineName(String machineName) {
        this.machineName = machineName;
    }

    public void setJobExecMap(Map<JobType, JobExecuteInfo> jobExecMap) {
        this.jobExecMap = jobExecMap;
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

}
