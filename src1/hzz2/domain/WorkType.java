package hzz2.domain;

import hzz2.constants.WorkTypeEnum;

/**
 * 生产模式
 * 
 * @author zhongzhou.hanzz 2014年7月20日 下午10:54:16
 */
public class WorkType {

    private String       jobTypeName;
    private String       ourSourcingName;
    /** 收益 */
    private int          rev;
    /** 无等待时间损耗 */
    private int          minTimeCost;
    /** 收入产出比 */
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
