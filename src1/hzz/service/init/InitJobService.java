package hzz.service.init;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.JobTypeOutSourcingEntry;
import hzz.domain.OutSourcingCorp;
import hzz.service.AIServiceForROI;
import hzz.service.select.JobSelection;
import hzz.service.select.OutSourcingSelection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class InitJobService {

    /*
     * 产生初始化解集合
     * @param initResSize
     * @return
     */
    public static List<ExecuteResult> initOriginalResult() {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < AIServiceForROI.initResSize; i++) {
            JobSelection jobSelection = new JobSelection(new ArrayList<JobType>(AIServiceForROI.jobType$count.keySet()));
            ExecuteResult res = null;
            res = new ExecuteResult(AIServiceForROI.worktime, null);
            System.out.println("Execute " + i + "Start init!");
            while (true) {
                // 如果工件数量已经达到要求则取消掉该类型工件的份额
                JobType jobType = jobSelection.getRandomJobByRoi();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount == null) {
                    currentJobCount = 0;
                }
                if (AIServiceForROI.checkCountOver(jobType, currentJobCount)) {
                    Collection<JobType> jobTypes = jobSelection.getJobs();
                    jobTypes.remove(jobType);
                    if (jobTypes.isEmpty()) {
                        break;
                    }
                    jobSelection = new JobSelection(jobTypes);
                    continue;
                }
                Job job = jobType.getInstance(WorkflowType.selfDo);
                if (!res.addJob(job)) {
                    break;
                }
            }
            System.out.println("Init " + i + " Rev:" + res.getProfitAndLoss());
            resList.add(res);
        }
        return resList;
    }

    /*
     * 产生外包初始化解集合
     * @param initResSize
     * @return
     */
    public static List<ExecuteResult> initOutSourcingOriginalResult(ExecuteResult selfRes) {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < AIServiceForROI.initResSize; i++) {
            OutSourcingSelection outSourcingSelection = new OutSourcingSelection(
                                                                                 null,
                                                                                 new ArrayList<OutSourcingCorp>(OutSourcingCorp.getAllOutSourcingCrops()));
            ExecuteResult res = null;
            res = new ExecuteResult(AIServiceForROI.worktime, selfRes);
            System.out.println("Execute Out Sourcing" + i + "Start init!");
            while (true) {
                // 如果工件数量已经达到要求则取消掉该类型工件的份额
                JobTypeOutSourcingEntry jobTypeOutSourcingEntry = outSourcingSelection.getRandomJobByRoi();
                JobType jobType = jobTypeOutSourcingEntry.getJobType();
                Integer currentJobCount = res.getJobType$count().get(jobType);
                if (currentJobCount == null) {
                    currentJobCount = 0;
                }
                if (AIServiceForROI.checkCountOver(jobType, currentJobCount)) {
                    outSourcingSelection = outSourcingSelection.removeJobTypeAndReInit(jobType);
                    if (outSourcingSelection == null) {
                        break;
                    }
                    continue;
                }
                Job job = jobType.getInstance(WorkflowType.outSourcing);
                job.setOutSourcingCorp(jobTypeOutSourcingEntry.getOutSourcingCorp());
                if (!res.addJob(job)) {
                    break;
                }
            }
            System.out.println("Init " + i + " Rev:" + res.getProfitAndLoss());
            resList.add(res);
        }
        return resList;
    }
}
