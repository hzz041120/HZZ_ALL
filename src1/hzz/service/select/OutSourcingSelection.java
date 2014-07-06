package hzz.service.select;

import hzz.domain.JobType;
import hzz.domain.JobTypeOutSourcingEntry;
import hzz.domain.OutSourcingCorp;
import hzz.domain.OutSourcingCorp.OutSourcingDetail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class OutSourcingSelection {

    private double                                randomArea;
    private Map<Integer, JobTypeOutSourcingEntry> randomJobMark = new HashMap<Integer, JobTypeOutSourcingEntry>();
    private int                                   scale         = 100;
    private static final Random                   random        = new Random();
    private Collection<JobTypeOutSourcingEntry>   JobTypeOutSourcingEntrys;

    public OutSourcingSelection(Object obj, Collection<OutSourcingCorp> outSourcingCorps) {
        if (outSourcingCorps == null || outSourcingCorps.isEmpty()) throw new IllegalArgumentException(
                                                                                                       "outSourcingCorp can't be null!");
        Collection<JobTypeOutSourcingEntry> _JobTypeOutSourcingEntrys = new ArrayList<JobTypeOutSourcingEntry>();
        for (OutSourcingCorp outSourcingCorp : outSourcingCorps) {
            Map<JobType, OutSourcingDetail> outSourcingMap = outSourcingCorp.getOutSourcingMap();
            if (outSourcingMap == null || outSourcingMap.isEmpty()) {
                throw new IllegalArgumentException("outSourcingMap can't be null! outSourcingCorp:"
                                                   + outSourcingCorp.getOutSourcingName());
            }
            for (Entry<JobType, OutSourcingDetail> jobTypeEntry : outSourcingMap.entrySet()) {
                _JobTypeOutSourcingEntrys.add(new JobTypeOutSourcingEntry(jobTypeEntry.getKey(), outSourcingCorp));
            }
        }
        init(_JobTypeOutSourcingEntrys);
    }

    public OutSourcingSelection(Collection<JobTypeOutSourcingEntry> _JobTypeOutSourcingEntrys) {
        init(_JobTypeOutSourcingEntrys);
    }

    protected void init(Collection<JobTypeOutSourcingEntry> _JobTypeOutSourcingEntrys) {
        for (JobTypeOutSourcingEntry _JobTypeOutSourcingEntry : _JobTypeOutSourcingEntrys) {
            JobType jobType = _JobTypeOutSourcingEntry.getJobType();
            OutSourcingCorp outSourcingCorp = _JobTypeOutSourcingEntry.getOutSourcingCorp();
            randomArea += outSourcingCorp.getRoiByJobType(jobType);
        }
        /* 精度100% 可以适当调高 */
        int percent = 0;
        for (JobTypeOutSourcingEntry _JobTypeOutSourcingEntry : _JobTypeOutSourcingEntrys) {
            JobType jobType = _JobTypeOutSourcingEntry.getJobType();
            OutSourcingCorp outSourcingCorp = _JobTypeOutSourcingEntry.getOutSourcingCorp();
            int newPercent = (int) (scale * (outSourcingCorp.getRoiByJobType(jobType) / randomArea)) + percent;
            System.out.println(jobType.getJobName() + " : " + outSourcingCorp.getOutSourcingName() + " newPercent: "
                               + newPercent);
            for (int i = percent; i < newPercent; i++) {
                System.out.println(i);
                randomJobMark.put(i, _JobTypeOutSourcingEntry);
            }
            percent = newPercent;
        }
        // 由于不能很好的分配精度比例，会有小数所以微调随机精度范围至整数
        scale = percent;
    }

    public Collection<JobTypeOutSourcingEntry> getJobTypeOutSourcingEntrys() {
        return JobTypeOutSourcingEntrys;
    }

    /**
     * 按照ROI权重随机获取一个工件
     * 
     * @return
     */
    public JobTypeOutSourcingEntry getRandomJobByRoi() {
        int nextInt = random.nextInt(scale);
        return randomJobMark.get(nextInt);
    }

    public OutSourcingSelection removeJobTypeAndReInit(JobType jobType) {
        Iterator<JobTypeOutSourcingEntry> iter = JobTypeOutSourcingEntrys.iterator();
        while (iter.hasNext()) {
            JobTypeOutSourcingEntry jtose = iter.next();
            if (jtose.getJobType().equals(jobType)) {
                iter.remove();
            }
        }
        if (JobTypeOutSourcingEntrys.isEmpty()) return null;
        return new OutSourcingSelection(JobTypeOutSourcingEntrys);
    }

    public OutSourcingSelection removeOutSourcingCorpAndReInit(OutSourcingCorp osc) {
        Iterator<JobTypeOutSourcingEntry> iter = JobTypeOutSourcingEntrys.iterator();
        while (iter.hasNext()) {
            JobTypeOutSourcingEntry jtose = iter.next();
            if (jtose.getOutSourcingCorp().equals(osc)) {
                iter.remove();
            }
        }
        if (JobTypeOutSourcingEntrys.isEmpty()) return null;
        return new OutSourcingSelection(JobTypeOutSourcingEntrys);
    }

    public static void main(String[] args) {
        Collection<OutSourcingCorp> outSourcingCorps = new ArrayList<OutSourcingCorp>();
        Map<JobType, OutSourcingDetail> o1 = new HashMap<JobType, OutSourcingCorp.OutSourcingDetail>();
        o1.put(JobType.newJobType("J1", 100, -9, 100, null), new OutSourcingDetail(50, 100));
        o1.put(JobType.newJobType("J2", 100, -9, 50, null), new OutSourcingDetail(100, 100));
        outSourcingCorps.add(OutSourcingCorp.newOutSourcingCorp("o1", o1));
        Map<JobType, OutSourcingDetail> o2 = new HashMap<JobType, OutSourcingCorp.OutSourcingDetail>();
        o2.put(JobType.newJobType("J1", 100, -9, 100, null), new OutSourcingDetail(50, 100));
        o2.put(JobType.newJobType("J2", 100, -9, 50, null), new OutSourcingDetail(100, 100));
        outSourcingCorps.add(OutSourcingCorp.newOutSourcingCorp("o2", o2));
        OutSourcingSelection oss = new OutSourcingSelection(null, outSourcingCorps);
        System.out.println(oss.randomJobMark);
        // while (true) {
        // JobTypeOutSourcingEntry res = oss.getRandomJobByRoi();
        //
        // if (res == null) {
        // System.out.println("===================>");
        // break;
        // }
        // }
    }
}
