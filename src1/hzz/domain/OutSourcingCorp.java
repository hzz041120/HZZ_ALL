package hzz.domain;

import java.util.Map;

/**
 * 外包商
 * 
 * @author hzz041120 2014年6月16日 下午9:15:02
 */
public class OutSourcingCorp {

    public OutSourcingCorp(String outSourcingName, Map<JobType, OutSourcingDetail> outSourcingMap) {
        this.outSourcingMap = outSourcingMap;
        this.outSourcingName = outSourcingName;
    }

    /** 外包商名称 */
    private String                          outSourcingName;
    /** 外包工件时间收入表 */
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

        // 时间消耗
        private int timeCost;
        // 利润
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
