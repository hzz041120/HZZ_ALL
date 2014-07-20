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
    private WorkType                         workType;
    private boolean                          reject      = false;
    /* ʵ�������Ŀ�ʼʱ�� */
    private Integer                          realStart;
    /* ʵ�������Ľ���ʱ�� */
    private Integer                          realEnd;
    /* �Բ�����������ϸ machine$timeEntry */
    private LinkedHashMap<String, TimeEntry> workDetails = new LinkedHashMap<String, TimeEntry>();

    public LinkedHashMap<String, TimeEntry> getWorkDetails() {
        return workDetails;
    }

    public void setWorkDetails(LinkedHashMap<String, TimeEntry> workDetails) {
        this.workDetails = workDetails;
    }

    public void addWorkItem(String machine, TimeEntry timeEntry) {
        if (workDetails.isEmpty()) {
            realStart = timeEntry.getStartTime();
        }
        workDetails.put(machine, timeEntry);
        realEnd = timeEntry.getEndTime();
    }

    public Integer getRealTimeCost() {
        if (!reject && workType.getOurSourcingName() == null) {
            return realEnd - realStart;
        } else return 0;
    }

    public WorkType getWorkType() {
        return workType;
    }

    public void setWorkType(WorkType workType) {
        this.workType = workType;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\n" + workType.getJobTypeName()).append("{\n");
        WorkTypeEnum workTypeEnum = null;
        if(reject) {
            workTypeEnum = WorkTypeEnum.reject;
        } else if (workType.getOurSourcingName() == null) {
            workTypeEnum = WorkTypeEnum.selfDo;
        } else {
            workTypeEnum = WorkTypeEnum.outSourcing;
        }
        sb.append("\tworkType:").append(workTypeEnum.name());
        if (workTypeEnum == WorkTypeEnum.selfDo) {
            sb.append(",\n\tmachineDetail:").append("{\n");
            for (Map.Entry<String, TimeEntry> entry : workDetails.entrySet()) {
                sb.append("\t\t").append(entry.getKey()).append(":").append(entry.getValue().getStartTime()).append("--->").append(entry.getValue().getEndTime()).append(",\n");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("\t}\n");
        } else if (workTypeEnum == WorkTypeEnum.outSourcing) {
            sb.append(",\n\toutSourcingCorp:").append(workType.getOurSourcingName()).append("\n");
        }
        sb.append("}\n");
        return sb.toString();
    }

    public boolean isReject() {
        return reject;
    }

    public void setReject(boolean reject) {
        this.reject = reject;
    }

}
