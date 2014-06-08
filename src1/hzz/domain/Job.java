package hzz.domain;

import java.util.LinkedHashMap;

/**
 * 编码后产生的任务实例
 * 
 * @author hzz041120 2014年6月8日 下午1:24:12
 */
public class Job {

//    /* 单个需要的工作时间 */
//    private int                               realWorkTime;
    /* 任务类型 */
    private JobType                           jobType;

    private LinkedHashMap<Machine, TimeEntry> workDetails = new LinkedHashMap<Machine, TimeEntry>();

//    public int getRealWorkTime() {
//        return realWorkTime;
//    }

    public JobType getJobType() {
        return jobType;
    }

//    public void setRealWorkTime(int realWorkTime) {
//        this.realWorkTime = realWorkTime;
//    }

    public void setJobType(JobType jobType) {
        this.jobType = jobType;
    }

    public LinkedHashMap<Machine, TimeEntry> getWorkDetails() {
        return workDetails;
    }

    public void setWorkDetails(LinkedHashMap<Machine, TimeEntry> workDetails) {
        this.workDetails = workDetails;
    }

    public void addWorkItem(Machine m, TimeEntry timeEntry) {
        workDetails.put(m, timeEntry);
    }

}
