package hzz2.domain;

import hzz2.constants.WorkTypeEnum;

/**
 * ����ģʽ
 * 
 * @author zhongzhou.hanzz 2014��7��20�� ����10:54:16
 */
public class WorkType {

    private String       jobTypeName;
    private String       ourSourcingName;
    /** ���� */
    private int          rev;
    /** �޵ȴ�ʱ����� */
    private int          minTimeCost;
    /** ��������� */
    private double       roi;
    private WorkTypeEnum workType;

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

    public WorkTypeEnum getWorkType() {
        return workType;
    }

    public void setWorkType(WorkTypeEnum workType) {
        this.workType = workType;
    }

}
