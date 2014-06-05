package hzz.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 机器
 * 
 * @author hzz041120 2014年4月6日 下午8:12:38
 */
public class Machine {

    // 机器名称
    private String                   machineName;
    /**
     * 任务收益耗时表
     */
    private Map<JobType, JobExecuteInfo> jobExecMap = new HashMap<JobType, JobExecuteInfo>();

    public String getMachineName() {
        return machineName;
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

}
