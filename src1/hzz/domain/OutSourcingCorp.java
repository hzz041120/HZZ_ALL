package hzz.domain;

import java.util.Map;

/**
 * �����
 * 
 * @author hzz041120 2014��6��16�� ����9:15:02
 */
public class OutSourcingCorp {

    public OutSourcingCorp(String outSourcingName, Map<JobType, OutSourcingDetail> outSourcingMap) {
        this.outSourcingMap = outSourcingMap;
        this.outSourcingName = outSourcingName;
    }

    /** ��������� */
    private String                          outSourcingName;
    /** �������ʱ������� */
    private Map<JobType, OutSourcingDetail> outSourcingMap;

    public String getOutSourcingName() {
        return outSourcingName;
    }

    public Map<JobType, OutSourcingDetail> getOutSourcingMap() {
        return outSourcingMap;
    }

    public void setOutSourcingName(String outSourcingName) {
        this.outSourcingName = outSourcingName;
    }

    public void setOutSourcingMap(Map<JobType, OutSourcingDetail> outSourcingMap) {
        this.outSourcingMap = outSourcingMap;
    }

    public static class OutSourcingDetail {

        // ʱ������
        private int timeCost;
        // ����
        private int rev;

        public int getTimeCost() {
            return timeCost;
        }

        public int getRev() {
            return rev;
        }

        public void setTimeCost(int timeCost) {
            this.timeCost = timeCost;
        }

        public void setRev(int rev) {
            this.rev = rev;
        }

    }
}
