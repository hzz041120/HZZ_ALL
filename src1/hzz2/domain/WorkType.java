package hzz2.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 生产模式
 * 
 * @author zhongzhou.hanzz 2014年7月20日 下午10:54:16
 */
public class WorkType {

    private static Map<String, WorkType> workTypeMap = new HashMap<String, WorkType>();
    public static final String           AND         = "____HZZ____";

    private WorkType(String jobTypeName, String ourSourcingName, Integer rev, Integer rejectRev, Integer maxWait,
                     Integer minTimeCost, LinkedHashMap<String, Integer> machine$time) {
        this.jobTypeName = jobTypeName;
        this.ourSourcingName = ourSourcingName;
        this.roi = rev / minTimeCost;
        this.rev = rev;
        this.maxWait = maxWait;
        this.minTimeCost = minTimeCost;
        this.rejectRev = rejectRev;
        this.machine$time = machine$time;
    }

    public static WorkType newWorkType(String jobTypeName, String ourSourcingName, Integer rev, Integer rejectRev,
                                       Integer maxWait, Integer minTimeCost, LinkedHashMap<String, Integer> machine$time) {
        if (workTypeMap.containsKey(jobTypeName + AND + ourSourcingName)) {
            return workTypeMap.get(jobTypeName + AND + ourSourcingName);
        }
        return new WorkType(jobTypeName, ourSourcingName, rev, rejectRev, maxWait, minTimeCost, machine$time);
    }

    public static WorkType getWorkType(String jobTypeName, String ourSourcingName) {
        return workTypeMap.get(jobTypeName + AND + ourSourcingName);
    }

    /* 该工件的最大等待时间 dj */
    Integer                                maxWait;
    /* 冗余一下拒绝的损失 uj */
    private int                            rejectRev;
    /* Job */
    private String                         jobTypeName;
    /* 外包公司名字 比如 c1j, c2j等 */
    private String                         ourSourcingName;
    /* 收益 ej */
    private int                            rev;
    /* 无等待时间损耗，外包工作时间，或自产多个机器的工作时间总和 */
    private int                            minTimeCost;
    /* 投入产出比 */
    private double                         roi;
    // 每个机器加工需要的时间及顺序 pjv, Bjv
    private LinkedHashMap<String, Integer> machine$time;

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public int getRejectRev() {
        return rejectRev;
    }

    public void setRejectRev(int rejectRev) {
        this.rejectRev = rejectRev;
    }

    public LinkedHashMap<String, Integer> getMachine$time() {
        return machine$time;
    }

    public void setMachine$time(LinkedHashMap<String, Integer> machine$time) {
        this.machine$time = machine$time;
    }

    public String getJobTypeName() {
        return jobTypeName;
    }

    public void setJobTypeName(String jobTypeName) {
        this.jobTypeName = jobTypeName;
    }

    public String getOurSourcingName() {
        return ourSourcingName;
    }

    public void setOurSourcingName(String ourSourcingName) {
        this.ourSourcingName = ourSourcingName;
    }

    public int getRev() {
        return rev;
    }

    public void setRev(int rev) {
        this.rev = rev;
    }

    public int getMinTimeCost() {
        return minTimeCost;
    }

    public void setMinTimeCost(int minTimeCost) {
        this.minTimeCost = minTimeCost;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

}
