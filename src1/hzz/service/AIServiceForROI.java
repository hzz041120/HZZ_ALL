package hzz.service;

import hzz.constants.WorkflowType;
import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;
import hzz.domain.OutSourcingCorp;
import hzz.service.init.InitJobService;
import hzz.util.ExecuteResultComparator;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Ѱ����������
 * 
 * @author hzz041120 2014��4��20�� ����5:04:51
 */
public class AIServiceForROI {

    /** ��ʼ���� */
    /** �ӹ��������� */
    public static int                                 worktime                 = 0;
    /** �������ͼ��������� */
    public static Map<JobType, Integer>               jobType$count            = new HashMap<JobType, Integer>();
    /** �������ͼ�������� */
    public static Map<JobType, List<OutSourcingCorp>> jobType$outSourcingCorps = new HashMap<JobType, List<OutSourcingCorp>>();
    /** �㷨��ϸ������ */
    /** ��ʼ���⼯��С ������ż�������ڽ��� �Ľ�������˵ */
    public static final int                           initResSize              = 5 * 2;
    /** �Ŵ����� */
    public static final int                           generation               = 10000;

    /**
     * ��ʼ����������
     */
    public void init() {
        worktime = 50;
        // ��ʼ��������
        jobType$count = new HashMap<JobType, Integer>();
        LinkedHashMap<Machine, Integer> j1Machine$time = new LinkedHashMap<Machine, Integer>();
        j1Machine$time.put(Machine.newMachine("m1"), 5);
        j1Machine$time.put(Machine.newMachine("m2"), 5);
        j1Machine$time.put(Machine.newMachine("m3"), 7);
        jobType$count.put(JobType.newJobType("J1", 78, -9, 53, j1Machine$time), 2);

        LinkedHashMap<Machine, Integer> j2Machine$time = new LinkedHashMap<Machine, Integer>();
        j2Machine$time.put(Machine.newMachine("m1"), 4);
        j2Machine$time.put(Machine.newMachine("m2"), 6);
        j2Machine$time.put(Machine.newMachine("m3"), 8);
        jobType$count.put(JobType.newJobType("J2", 95, -10, 61, j2Machine$time), 2);

        LinkedHashMap<Machine, Integer> j3Machine$time = new LinkedHashMap<Machine, Integer>();
        j3Machine$time.put(Machine.newMachine("m1"), 2);
        j3Machine$time.put(Machine.newMachine("m2"), 4);
        j3Machine$time.put(Machine.newMachine("m3"), 10);
        jobType$count.put(JobType.newJobType("J3", 102, -7, 43, j3Machine$time), 2);

        LinkedHashMap<Machine, Integer> j4Machine$time = new LinkedHashMap<Machine, Integer>();
        j4Machine$time.put(Machine.newMachine("m1"), 7);
        j4Machine$time.put(Machine.newMachine("m2"), 9);
        j4Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J4", 89, -13, 67, j4Machine$time), 2);

        LinkedHashMap<Machine, Integer> j5Machine$time = new LinkedHashMap<Machine, Integer>();
        j5Machine$time.put(Machine.newMachine("m1"), 9);
        j5Machine$time.put(Machine.newMachine("m2"), 3);
        j5Machine$time.put(Machine.newMachine("m3"), 5);
        jobType$count.put(JobType.newJobType("J5", 93, -5, 55, j5Machine$time), 2);
        // ��ʼ������̱�

    }

    /**
     * ���������ƻ�
     */
    public void process() {
        /** FIXME ֻ���������Ҽӹ�����>���>�ܾ��ӹ��ĳ��� */
        /** ������Ҽӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult selfRes = findSelfJobList(jobType$count);
        /** �������ӹ��Ĳ�Ʒ��ˮ */
        Map<JobType, Integer> _jobType$count = new HashMap<JobType, Integer>();
        if (needTodo(selfRes, _jobType$count)) {
            ExecuteResult afterOutSourcingRes = findOutSourcingJobList(selfRes);
            /** ��þܾ��ӹ��Ĳ�Ʒ��ˮ */
            _jobType$count = new HashMap<JobType, Integer>();
            if (needTodo(afterOutSourcingRes, _jobType$count)) {
                ExecuteResult result = findRejectJobList(afterOutSourcingRes);
            }
            /** ��ӡ�����ƻ��� */
            // FIXME
        }

    }

    protected boolean needTodo(ExecuteResult res, Map<JobType, Integer> _jobType$count) {
        Set<Entry<JobType, Integer>> selfDoCount = res.getJobType$count().entrySet();
        boolean needTodo = false;
        for (Entry<JobType, Integer> selfDoTypeCount : selfDoCount) {
            JobType type = selfDoTypeCount.getKey();
            Integer needDo = _jobType$count.get(type) == null ? 0 : _jobType$count.get(type);
//            System.out.println(type.getJobName() + " >>> "  + needDo  + " |||| " + selfDoTypeCount.getValue());
            int todoCount = needDo - selfDoTypeCount.getValue();
            if (todoCount > 0) needTodo = true;
            _jobType$count.put(type, todoCount);
        }
        return needTodo;
    }

    private ExecuteResult findRejectJobList(ExecuteResult afterOutSourcingRes) {
        Map<JobType, Integer> currentJobTypeCount = afterOutSourcingRes.getJobType$count();
        for (Map.Entry<JobType, Integer> entry : jobType$count.entrySet()) {
            JobType jobType = entry.getKey();
            Integer currentjc = currentJobTypeCount.get(jobType);
            Integer needCount = entry.getValue();
            while (currentjc < needCount) {
                Job job = jobType.getInstance(WorkflowType.reject);
                afterOutSourcingRes.addJob(job);
                currentjc++;
            }
        }
        return afterOutSourcingRes;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = InitJobService.initOutSourcingOriginalResult(selfRes);
        ExecuteResult bestResult = getBestResult(resultList);
        System.out.println("ProfitAndLoss: " + bestResult.getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = recombination(resultList, WorkflowType.outSourcing);
            bestResult = getBestResult(resultList);
            System.out.println("A1 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            /** ���� */
            mutation(resultList, WorkflowType.outSourcing);
            bestResult = getBestResult(resultList);
            System.out.println("A2 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
        }
        return bestResult;
    }

    private ExecuteResult findSelfJobList(Map<JobType, Integer> _jobType$count) {
        // FIXME ʱ���㹻��ԣ��Ҫ���մ���ĸ�ROI������䷨����
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = InitJobService.initOriginalResult();
        ExecuteResult bestResult = getBestResult(resultList);
        System.out.println("ProfitAndLoss: " + bestResult.getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = recombination(resultList, WorkflowType.selfDo);
            bestResult = getBestResult(resultList);
            System.out.println("A1 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            /** ���� */
            mutation(resultList, WorkflowType.selfDo);
            bestResult = getBestResult(resultList);
            System.out.println("A2 ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
        }
        return bestResult;
    }

    /**
     * ִ�б��죬��ǰ���õ����������������
     * 
     * @param resultList
     * @return
     */
    private void mutation(List<ExecuteResult> resultList, WorkflowType workflowType) {
        MutationService.mutation(resultList, workflowType);
    }

    /**
     * ִ�н������
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> recombination(List<ExecuteResult> resultList, WorkflowType workflowType) {
        return RecombinationService.doIntermediateRecobination(resultList, workflowType);
    }

    private ExecuteResult getBestResult(List<ExecuteResult> result) {
        Collections.sort(result, new ExecuteResultComparator());
        return result.get(0);
    }

    public static boolean checkCountOver(JobType jobType, Integer currentJobCount) {
        return currentJobCount >= AIServiceForROI.jobType$count.get(jobType);
    }

    public static void main(String[] args) {
        AIServiceForROI service = new AIServiceForROI();
        service.init();
        service.process();
    }

}
