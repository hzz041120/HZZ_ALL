package hzz.service;

import hzz.domain.ExecuteResult;
import hzz.domain.Job;
import hzz.domain.JobType;
import hzz.domain.Machine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ѱ����������
 * 
 * @author hzz041120 2014��4��20�� ����5:04:51
 */
public class AIServiceForROI {

    /** ��ʼ���� */
    // �ӹ���������
    public static int                   worktime      = 0;
    // �������ͼ���������
    public static Map<JobType, Integer> jobType$count = new HashMap<JobType, Integer>();
    // �����б�
    public static List<Machine>         machineList   = new ArrayList<Machine>();
    /** �㷨��ϸ������ */
    // ��ʼ���⼯��С
    private int                         initResSize   = 10;
    // �Ŵ�����
    private int                         generation    = 10000;

    private JobSelection                jobSelection  = new JobSelection(jobType$count.keySet());

    /**
     * ��ʼ����������
     */
    public void init() {
        // FIXME ��ʼ��
    }

    public void process() {
        /** ������Ҽӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult selfRes = findSelfJobList();
        /** �������ӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult outSourcingRes = findOutSourcingJobList(selfRes);
        /** ��þܾ��ӹ��Ĳ�Ʒ��ˮ */
        ExecuteResult rejectRes = findRejectJobList(selfRes, outSourcingRes);
    }

    private ExecuteResult findRejectJobList(ExecuteResult selfRes, ExecuteResult outSourcingRes) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult findOutSourcingJobList(ExecuteResult selfRes) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult findSelfJobList() {
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = initOriginalResult(initResSize);
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = crossover(resultList);
            /** ���� */
            resultList = mutation(resultList);
        }
        return getBestResult(resultList);
    }

    /**
     * ִ�б��죬��ǰ���õ����������������
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> mutation(List<ExecuteResult> resultList) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * ִ�н�����㣬��ǰ���õķ�����ʵֵ������м�����
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> crossover(List<ExecuteResult> resultList) {
        // TODO Auto-generated method stub
        return null;
    }

    private ExecuteResult getBestResult(List<ExecuteResult> originalResult) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * ������ʼ���⼯��
     * @param initResSize
     * @return
     */
    private List<ExecuteResult> initOriginalResult(int initResSize) {
        List<ExecuteResult> resList = new ArrayList<ExecuteResult>();
        for (int i = 0; i < initResSize; i++) {
            ExecuteResult res = new ExecuteResult(worktime);
            while (true) {
                JobType jobType = jobSelection.getRandomJobByRoi();
                Job job = jobType.getInstance();
                if (!res.addJob(job)) {
                    break;
                }
            }
            resList.add(res);
        }
        return resList;
    }

}
