package hzz.domain;

import java.util.LinkedHashMap;

/**
 * ��������
 * 
 * @author hzz041120 2014��4��6�� ����8:12:02
 */
public class JobType {

    public JobType(Integer rev, Integer timeCost, LinkedHashMap<Machine, Integer> machine$time) {
        this.roi = rev / timeCost;
        this.rev = rev;
        this.timeCost = timeCost;
        this.machine$time = machine$time;
    }

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

    public Integer getTimeCost() {
        return timeCost;
    }

    public LinkedHashMap<Machine, Integer> getMachine$time() {
        return machine$time;
    }

    public Job getInstance() {
        Job instance = new Job();
        instance.setJobType(this);
        instance.setRealWorkTime(timeCost);
        return instance;
    }
}
