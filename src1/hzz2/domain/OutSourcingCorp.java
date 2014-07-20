package hzz2.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * �����
 * 
 * @author hzz041120 2014��6��16�� ����9:15:02
 */
public class OutSourcingCorp {

    private static final Random                 r                   = new Random();
    private static Map<String, OutSourcingCorp> outSourcingCorpMap  = new HashMap<String, OutSourcingCorp>();
    private static List<OutSourcingCorp>        outSourcingCorpList = new ArrayList<OutSourcingCorp>();
    // ���Խ���������Ŀ�ʼʱ��
    private int                                 startWorkDelay      = 0;

    public static Map<String, OutSourcingCorp> getOutSourcingCorpMap() {
        return outSourcingCorpMap;
    }

    public int getStartWorkDelay() {
        return startWorkDelay;
    }

    public static OutSourcingCorp getCorpByRandom() {
        int randomSize = r.nextInt(outSourcingCorpList.size());
        return outSourcingCorpList.get(randomSize);
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
        outSourcingCorpList.add(this);
        outSourcingCorpMap.put(outSourcingName, this);
    }

    /** ��������� */
    private String                          outSourcingName;
    /** �������ʱ������� */
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

    public Integer gettIimeCostByJobType(JobType jobType) {
        return outSourcingMap.get(jobType).getTimeCost();
    }

    public static class OutSourcingDetail {

        // ʱ������
        private int    timeCost;
        // ����
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