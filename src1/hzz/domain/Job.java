package hzz.domain;

import hzz.constants.WorkflowType;

import java.util.LinkedHashMap;

/**
 * 编码后产生的任务实例
 * 
 * @author hzz041120 2014年6月8日 下午1:24:12
 */
public class Job {

    /* 任务类型 */
    private JobType                           jobType;
    /* 生产类型 */
    private WorkflowType                      workflowType;
    /* 外包商 */
    private OutSourcingCorp                   outSourcingCorp;
    /* 自产机器工作明细 */
    private LinkedHashMap<Machine, TimeEntry> workDetails = new LinkedHashMap<Machine, TimeEntry>();

    public WorkflowType getWorkflowType() {
        return workflowType;
    }

    public OutSourcingCorp getOutSourcingCorp() {
        return outSourcingCorp;
    }

    public void setOutSourcingCorp(OutSourcingCorp outSourcingCorp) {
        this.outSourcingCorp = outSourcingCorp;
    }

    public void setWorkflowType(WorkflowType workflowType) {
        this.workflowType = workflowType;
    }

    public JobType getJobType() {
        return jobType;
    }

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
