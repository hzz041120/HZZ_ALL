package hzz.domain;

import java.util.LinkedHashMap;

/**
 * 工件类型
 * 
 * @author hzz041120 2014年4月6日 下午8:12:02
 */
public class JobType {

    public JobType(Integer rev, Integer timeCost, LinkedHashMap<Machine, Integer> machine$time) {
        this.roi = rev / timeCost;
        this.rev = rev;
        this.timeCost = timeCost;
        this.machine$time = machine$time;
    }

    // 自主加工需要的时间
    private Integer                         timeCost;
    // 每个机器加工需要的时间
    private LinkedHashMap<Machine, Integer> machine$time;
    // 自主加工的利润
    private Integer                         rev;
    private double                          roi;

    // 外包加工的时间
    // 外包加工的利润
    // 拒绝的利润

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
