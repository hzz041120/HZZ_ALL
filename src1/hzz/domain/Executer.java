package hzz.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 执行者 有三种 外包，拒绝执行和自行生产
 * 
 * @author hzz041120 2014年4月6日 下午8:25:13
 */
public class Executer {

    public Executer(String execName) {
        this.execName = execName;
    }

    /**
     * 执行者名称
     */
    private String                   execName;
    /**
     * 任务收益耗时表
     */
    private Map<JobType, JobExecuteInfo> jobExecMap = new HashMap<JobType, JobExecuteInfo>();

    public Map<JobType, JobExecuteInfo> getJobExecMap() {
        return jobExecMap;
    }

    public void setJobExecMap(Map<JobType, JobExecuteInfo> jobExecMap) {
        this.jobExecMap = jobExecMap;
    }

    public String getExecName() {
        return execName;
    }
    

}
