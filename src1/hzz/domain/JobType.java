package hzz.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 工件类型
 * 
 * @author hzz041120 2014年4月6日 下午8:12:02
 */
public class JobType {

    private static Map<String, JobType> jobTypeMap = new HashMap<String, JobType>();

    private JobType(String jobName, Integer rev, Integer rejectRev, Integer timeCost,
                    LinkedHashMap<Machine, Integer> machine$time) {
        this.jobName = jobName;
        this.roi = rev / timeCost;
        this.rev = rev;
        this.timeCost = timeCost;
        this.rejectRev = rejectRev;
        this.machine$time = machine$time;
    }

    private String                          jobName;
    // 自主加工需要的时间
    private Integer                         timeCost;
    // 每个机器加工需要的时间
    private LinkedHashMap<Machine, Integer> machine$time;
    // 自主加工的利润
    private Integer                         rev;
    // 拒绝加工的利润
    private Integer                         rejectRev;
    private double                          roi;

    // 外包加工的时间
    // 外包加工的利润
    // 拒绝的利润

    public double getRoi() {
        return roi;
    }

    public Integer getRev() {
        return rev;
    }

    public Integer getRejectRev() {
        return rejectRev;
    }

    public String getJobName() {
        return jobName;
    }

    public Integer getTimeCost() {
        return timeCost;
    }

    public LinkedHashMap<Machine, Integer> getMachine$time() {
        return machine$time;
    }

    public static JobType newJobType(String jobName, Integer rev, Integer rejectRev, Integer timeCost,
                                     LinkedHashMap<Machine, Integer> machine$time) {
        if (jobTypeMap.containsKey(jobName)) {
            return jobTypeMap.get(jobName);
        }
        return new JobType(jobName, rev, rejectRev, timeCost, machine$time);
    }

    public static Collection<JobType> getAllJobType() {
        return jobTypeMap.values();
    }

    public Job getInstance() {
        Job instance = new Job();
        instance.setJobType(this);
        // instance.setRealWorkTime(timeCost);
        return instance;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        System.out.println(jobName + "<===>" + ((JobType) obj).getJobName());
        return jobName.equals(((JobType) obj).getJobName());
    }

    public int hashCode() {
        return this.getJobName().hashCode();
    }

}
