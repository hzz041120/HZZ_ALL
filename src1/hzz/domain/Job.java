package hzz.domain;

import hzz.constants.WorkflowType;

import java.util.LinkedHashMap;

/**
 * ��������������ʵ��
 * 
 * @author hzz041120 2014��6��8�� ����1:24:12
 */
public class Job {

    /* �������� */
    private JobType                           jobType;
    /* �������� */
    private WorkflowType                      workflowType;
    /* ����� */
    private OutSourcingCorp                   outSourcingCorp;
    /* �Բ�����������ϸ */
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
