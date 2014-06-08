package hzz.domain;

import java.util.LinkedHashMap;

/**
 * ��������������ʵ��
 * 
 * @author hzz041120 2014��6��8�� ����1:24:12
 */
public class Job {

//    /* ������Ҫ�Ĺ���ʱ�� */
//    private int                               realWorkTime;
    /* �������� */
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
