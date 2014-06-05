package hzz.domain;

import java.util.List;

/**
 * 任务收益耗时矩阵列
 */
public class JobExecuteInfo {

    private JobType           job;
    private Integer       timeCost;
    // 机器顺序
    private List<Machine> machineList;
    // 收益 外包则是负
    private Integer       revenue;

    public Integer getRevenue() {
        return revenue;
    }

    public void setRevenue(Integer revenue) {
        this.revenue = revenue;
    }

    public List<Machine> getMachineList() {
        return machineList;
    }

    public void setMachineList(List<Machine> machineList) {
        this.machineList = machineList;
    }

    public JobType getJob() {
        return job;
    }

    public Integer getTimeCost() {
        return timeCost;
    }

    public void setJob(JobType job) {
        this.job = job;
    }

    public void setTimeCost(Integer timeCost) {
        this.timeCost = timeCost;
    }

}
