package hzz2.domain;

import hzz2.constants.WorkTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 编码后产生的任务实例
 * 
 * @author hzz041120 2014年6月8日 下午1:24:12
 */
public class Job {

    /* 生产模式 */
    private WorkType                          workType;
    /* 自产机器工作明细 */
    private LinkedHashMap<Machine, TimeEntry> workDetails = new LinkedHashMap<Machine, TimeEntry>();

    public double getRoi() {
        return workType.getRoi();
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

    public String toString() {
        StringBuilder sb = new StringBuilder("\n" + workType.getJobTypeName()).append("{\n");
        WorkTypeEnum workTypeEnum = workType.getWorkType();
        sb.append("\tworkType:").append(workTypeEnum.name());
        if (workTypeEnum == WorkTypeEnum.selfDo) {
            sb.append(",\n\tmachineDetail:").append("{\n");
            for (Map.Entry<Machine, TimeEntry> entry : workDetails.entrySet()) {
                sb.append("\t\t").append(entry.getKey().getMachineName()).append(":").append(entry.getValue().getStartTime()).append("--->").append(entry.getValue().getEndTime()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\t}\n");
        } else if (workTypeEnum == WorkTypeEnum.outSourcing) {
            sb.append(",\n\toutSourcingCorp:").append(workType.getOurSourcingName()).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

}
