package hzz2.domain;

import hzz2.constants.WorkTypeEnum;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ��������������ʵ��
 * 
 * @author hzz041120 2014��6��8�� ����1:24:12
 */
public class Job {

    /* ����ģʽ */
    private WorkType                          workType;
    /* �Բ�����������ϸ */
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
