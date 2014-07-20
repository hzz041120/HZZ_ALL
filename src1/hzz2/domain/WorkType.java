package hzz2.domain;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ����ģʽ
 * 
 * @author zhongzhou.hanzz 2014��7��20�� ����10:54:16
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

    /* �ù��������ȴ�ʱ�� dj */
    Integer                                maxWait;
    /* ����һ�¾ܾ�����ʧ uj */
    private int                            rejectRev;
    /* Job */
    private String                         jobTypeName;
    /* �����˾���� ���� c1j, c2j�� */
    private String                         ourSourcingName;
    /* ���� ej */
    private int                            rev;
    /* �޵ȴ�ʱ����ģ��������ʱ�䣬���Բ���������Ĺ���ʱ���ܺ� */
    private int                            minTimeCost;
    /* Ͷ������� */
    private double                         roi;
    // ÿ�������ӹ���Ҫ��ʱ�估˳�� pjv, Bjv
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
