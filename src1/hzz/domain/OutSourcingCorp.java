package hzz.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 外包商
 * 
 * @author hzz041120 2014年6月16日 下午9:15:02
 */
public class OutSourcingCorp {

    private static Map<String, OutSourcingCorp> outSourcingCorpMap = new HashMap<String, OutSourcingCorp>();
    // 可以接收新任务的开始时间
    private int                                 startWorkDelay = 0;

    public static Map<String, OutSourcingCorp> getOutSourcingCorpMap() {
        return outSourcingCorpMap;
    }

    public int getStartWorkDelay() {
        return startWorkDelay;
    }

    public static void setOutSourcingCorpMap(Map<String, OutSourcingCorp> outSourcingCorpMap) {
        OutSourcingCorp.outSourcingCorpMap = outSourcingCorpMap;
    }

    public void setStartWorkDelay(int startWorkDelay) {
        this.startWorkDelay = startWorkDelay;
    }

    public static Collection<OutSourcingCorp> getAllOutSourcingCrops() {
        return outSourcingCorpMap.values();
    }

    public OutSourcingCorp(String outSourcingName, Map<JobType, OutSourcingDetail> outSourcingMap) {
        if (outSourcingMap == null) {
            throw new IllegalArgumentException(outSourcingName + "'s outSourcingMap can't be null");
        }
        this.outSourcingMap = outSourcingMap;
        this.outSourcingName = outSourcingName;
    }

    /** 外包商名称 */
    private String                          outSourcingName;
    /** 外包工件时间收入表 */
    private Map<JobType, OutSourcingDetail> outSourcingMap;

    public static OutSourcingCorp newOutSourcingCorp(String outSourcingName,
                                                     Map<JobType, OutSourcingDetail> outSourcingMap) {
        if (outSourcingCorpMap.containsKey(outSourcingName)) {
            return outSourcingCorpMap.get(outSourcingName);
        }
        return new OutSourcingCorp(outSourcingName, outSourcingMap);
    }

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

    public boolean equals(Object obj) {
        if (obj == null) return false;
        return outSourcingName.equals(((OutSourcingCorp) obj).getOutSourcingName());
    }

    public int hashCode() {
        return outSourcingName.hashCode();
    }

    public double getRoiByJobType(JobType jobType) {
        return outSourcingMap.get(jobType).getRoi();
    }

    public static class OutSourcingDetail {

        // 时间消耗
        private int    timeCost;
        // 利润
        private int    rev;
        private double roi;

        public OutSourcingDetail(int timeCost, int rev) {
            this.timeCost = timeCost;
            this.rev = rev;
            this.roi = (rev * 1d) / timeCost;
        }

        public double getRoi() {
            return roi;
        }

        public int getTimeCost() {
            return timeCost;
        }

        public int getRev() {
            return rev;
        }

    }
}
