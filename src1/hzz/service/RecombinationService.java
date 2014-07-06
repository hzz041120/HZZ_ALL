package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 交叉计算服务
 * 
 * @author hzz041120 2014年6月8日 下午10:46:53
 */
public class RecombinationService {

    // intermediate recombination
    public static List<ExecuteResult> doIntermediateRecobination(List<ExecuteResult> resultList,
                                                                 WorkflowType workflowType) {
        // 进行相邻分组 可改进
        int i = 0;
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>(resultList.size());
        while (i < resultList.size()) {
            ExecuteResult firstRes = resultList.get(i++);
            ExecuteResult secondRes = resultList.get(i++);
            List<ExecuteResult> crossResList = doCross(firstRes, secondRes, workflowType);
            resList.addAll(crossResList);
        }
        return resList;
    }

    private static List<ExecuteResult> doCross(ExecuteResult firstRes, ExecuteResult secondRes,
                                               WorkflowType workflowType) {
        List<ExecuteResult> res = new ArrayList<ExecuteResult>();
        firstRes.setResultName("firstRes");
        res.add(firstRes);
        secondRes.setResultName("secondRes");
        res.add(secondRes);
        int fmp = firstRes.getMiddlePoint();
        List<Job> fJobList = firstRes.getJobList();
        List<Job> f11 = fJobList.subList(0, fmp);
        List<Job> f12 = fJobList.subList(fmp, fJobList.size());

        int smp = secondRes.getMiddlePoint();
        List<Job> sJobList = secondRes.getJobList();
        List<Job> f21 = sJobList.subList(0, smp);
        List<Job> f22 = sJobList.subList(smp, sJobList.size());

        ExecuteResult child1 = getCrossResult(firstRes, f11, f21, workflowType);
        if (child1 != null) {
            child1.setResultName("child1");
            res.add(child1);
        }
        ExecuteResult child2 = getCrossResult(firstRes, f11, f22, workflowType);
        if (child2 != null) {
            child2.setResultName("child2");
            res.add(child2);
        }
        ExecuteResult child3 = getCrossResult(firstRes, f12, f21, workflowType);
        if (child3 != null) {
            child3.setResultName("child3");
            res.add(child3);
        }
        ExecuteResult child4 = getCrossResult(firstRes, f12, f21, workflowType);
        if (child4 != null) {
            child4.setResultName("child4");
            res.add(child4);
        }
        List<ExecuteResult> sortRes = sortByRevAndGetBest2(res);
        return sortRes;
    }

    private static List<ExecuteResult> sortByRevAndGetBest2(List<ExecuteResult> res) {
        Collections.sort(res, new ExecuteResultComparator());
        return res.subList(0, 2);
    }

    private static ExecuteResult getCrossResult(ExecuteResult firstRes, List<Job> x, List<Job> y,
                                                WorkflowType workflowType) {
        ExecuteResult child = new ExecuteResult(firstRes.getWorktime());
        for (Job j : x) {
            if (!child.addJob(j)) {
                return null;
            }
        }
        for (Job j : y) {
            if (!child.addJob(j)) {
                return null;
            }
        }
        while (true) {
            Job job = child.getJobByWorkflow(workflowType, null);
            if (job == null || !child.addJob(job)) {
                break;
            }
        }
        return child;
    }

   

}
