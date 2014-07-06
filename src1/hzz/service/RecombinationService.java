package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.OutSourcingCorp;
import hzz.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            List<ExecuteResult> crossResList = null;
            switch (workflowType) {
                case selfDo:
                    crossResList = doCrossSelfDo(firstRes, secondRes, workflowType);
                    break;
                case outSourcing:
                    crossResList = doCrossOutSourcing(firstRes, secondRes, workflowType);
                default:
                    break;
            }
            resList.addAll(crossResList);
        }
        return resList;
    }

    private static List<ExecuteResult> doCrossOutSourcing(ExecuteResult firstRes, ExecuteResult secondRes,
                                                          WorkflowType workflowType) {
        List<ExecuteResult> res = new ArrayList<ExecuteResult>();
        firstRes.setResultName("firstRes");
        res.add(firstRes);
        secondRes.setResultName("secondRes");
        res.add(secondRes);
        Map<OutSourcingCorp, List<Job>> firstJobMap = firstRes.getOutSourcingJobMap();
        Map<OutSourcingCorp, List<Job>> secondJobMap = secondRes.getOutSourcingJobMap();
        int resCorpSize = firstJobMap.keySet().size();
        int middleSize = resCorpSize / 2;
        if ((resCorpSize & 1) > 0) middleSize++;
        int i = 0;
        Map<OutSourcingCorp, List<Job>> c1 = new HashMap<OutSourcingCorp, List<Job>>();
        Map<OutSourcingCorp, List<Job>> c2 = new HashMap<OutSourcingCorp, List<Job>>();
        for (Map.Entry<OutSourcingCorp, List<Job>> entry : firstJobMap.entrySet()) {
            OutSourcingCorp osc = entry.getKey();
            if (i < middleSize) {
                c1.put(osc, entry.getValue());
                c2.put(osc, secondJobMap.get(osc));
            } else {
                c2.put(osc, entry.getValue());
                c1.put(osc, secondJobMap.get(osc));
            }
            i++;
        }
        boolean firstAllow = true;
        ExecuteResult firstCrossRes = new ExecuteResult(firstRes.getWorktime(), firstRes);
        firstCrossRes.setOutSourcingJobMap(new HashMap<OutSourcingCorp, List<Job>>());
        for (List<Job> jList : c1.values()) {
            for (Job j : jList) {
                if (!firstCrossRes.addJob(j)) {
                    firstAllow = false;
                }
            }
        }
        if (firstAllow) {
            res.add(firstCrossRes);
        }

        boolean secondAllow = true;
        ExecuteResult secondCrossRes = new ExecuteResult(firstRes.getWorktime(), firstRes);
        secondCrossRes.setOutSourcingJobMap(new HashMap<OutSourcingCorp, List<Job>>());
        for (List<Job> jList : c2.values()) {
            for (Job j : jList) {
                if (!secondCrossRes.addJob(j)) {
                    secondAllow = false;
                }
            }
        }
        if (secondAllow) {
            res.add(secondCrossRes);
        }
        List<ExecuteResult> sortRes = sortByRevAndGetBest2(res);
        return sortRes;
    }

    private static List<ExecuteResult> doCrossSelfDo(ExecuteResult firstRes, ExecuteResult secondRes,
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

    public static void main(String[] args) {
        int a = 10;// 0101
        int b = 5;// 0100
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(a >> 1);
        System.out.println(b >> 1);
        System.out.println(a & 1);
        System.out.println(b & 1);
    }

}
