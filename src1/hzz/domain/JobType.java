package hzz.domain;

import java.util.LinkedHashMap;

/**
 * ��������
 * 
 * @author hzz041120 2014��4��6�� ����8:12:02
 */
public class JobType {

    public JobType(String jobName, Integer rev, Integer timeCost, LinkedHashMap<Machine, Integer> machine$time) {
        this.jobName = jobName;
        this.roi = rev / timeCost;
        this.rev = rev;
        this.timeCost = timeCost;
        this.machine$time = machine$time;
    }

    private String                          jobName;
    // �����ӹ���Ҫ��ʱ��
    private Integer                         timeCost;
    // ÿ�������ӹ���Ҫ��ʱ��
    private LinkedHashMap<Machine, Integer> machine$time;
    // �����ӹ�������
    private Integer                         rev;
    private double                          roi;

    // ����ӹ���ʱ��
    // ����ӹ�������
    // �ܾ�������

    public double getRoi() {
        return roi;
    }

    public Integer getRev() {
        return rev;
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

    public Job getInstance() {
        Job instance = new Job();
        instance.setJobType(this);
        // instance.setRealWorkTime(timeCost);
        return instance;
    }

    public boolean equals(Object obj) {
        if (obj == null) return false;
        return jobName.equals(((JobType) obj).getJobName());
    }

}
