package hzz2.service;

import hzz2.domain.ExecuteResult;
import hzz2.domain.WorkType;
import hzz2.service.init.InitJobService;
import hzz2.service.util.ExecuteResultComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Ѱ����������
 * 
 * @author hzz041120 2014��4��20�� ����5:04:51
 */
public class AIServiceForROI {

    /** ��ʼ���� */
    /** �㷨��ϸ������ */
    /** ��ʼ���⼯��С ������ż�������ڽ��� �Ľ�������˵ */
    public static final int            initResSize  = 5 * 2;
    /** �Ŵ����� */
    public static final int            generation   = 10000;
    public static final List<WorkType> workTypeList = new ArrayList<WorkType>();

    /**
     * ��ʼ����������
     */
    public void init() {
        // ��ʼ��������
        LinkedHashMap<String, Integer> j1Machine$time = new LinkedHashMap<String, Integer>();
        j1Machine$time.put("m1", 5);
        j1Machine$time.put("m2", 5);
        j1Machine$time.put("m3", 7);
        workTypeList.add(WorkType.newWorkType("J1", null, 78, -9, 53, (5 + 5 + 7), j1Machine$time));
        workTypeList.add(WorkType.newWorkType("J1", "C1J", (78 - 34), -9, 53, 42, j1Machine$time));
        workTypeList.add(WorkType.newWorkType("J1", "C2J", (78 - 45), -9, 53, 26, j1Machine$time));

        LinkedHashMap<String, Integer> j2Machine$time = new LinkedHashMap<String, Integer>();
        j2Machine$time.put("m1", 4);
        j2Machine$time.put("m2", 6);
        j2Machine$time.put("m3", 8);
        workTypeList.add(WorkType.newWorkType("J2", "C2J", (95 - 25), -10, 61, 43, j2Machine$time));
        workTypeList.add(WorkType.newWorkType("J2", null, 95, -10, 61, (4 + 6 + 8), j2Machine$time));
        workTypeList.add(WorkType.newWorkType("J2", "C1J", (95 - 28), -10, 61, 34, j2Machine$time));

        LinkedHashMap<String, Integer> j3Machine$time = new LinkedHashMap<String, Integer>();
        j3Machine$time.put("m1", 2);
        j3Machine$time.put("m2", 4);
        j3Machine$time.put("m3", 10);
        workTypeList.add(WorkType.newWorkType("J3", null, 102, -7, 43, (2 + 4 + 10), j3Machine$time));
        workTypeList.add(WorkType.newWorkType("J3", "C1J", (102 - 40), -7, 43, 25, j3Machine$time));
        workTypeList.add(WorkType.newWorkType("J3", "C2J", (102 - 51), -7, 43, 19, j3Machine$time));

        LinkedHashMap<String, Integer> j4Machine$time = new LinkedHashMap<String, Integer>();
        j4Machine$time.put("m1", 7);
        j4Machine$time.put("m2", 9);
        j4Machine$time.put("m3", 5);
        workTypeList.add(WorkType.newWorkType("J4", "C1J", (89 - 27), -13, 67, 41, j4Machine$time));
        workTypeList.add(WorkType.newWorkType("J4", "C2J", (89 - 25), -13, 67, 35, j4Machine$time));
        workTypeList.add(WorkType.newWorkType("J4", null, 89, -13, 67, (7 + 9 + 5), j4Machine$time));

        LinkedHashMap<String, Integer> j5Machine$time = new LinkedHashMap<String, Integer>();
        j5Machine$time.put("m1", 9);
        j5Machine$time.put("m2", 3);
        j5Machine$time.put("m3", 5);
        workTypeList.add(WorkType.newWorkType("J5", "C1J", (93 - 37), -5, 55, 36, j5Machine$time));
        workTypeList.add(WorkType.newWorkType("J5", null, 93, -5, 55, (9 + 4 + 5), j5Machine$time));
        workTypeList.add(WorkType.newWorkType("J5", "C2J", (93 - 20), -5, 55, 42, j5Machine$time));
    }

    /**
     * ���������ƻ�
     */
    public void process() {
        // FIXME ʱ���㹻��ԣ��Ҫ���մ���ĸ�ROI������䷨����
        /** ��ʼ���⼯�� */
        List<ExecuteResult> resultList = InitJobService.initOriginalResult();
        ExecuteResult bestResult = getBestResult(resultList);
        System.out.println("SelfDo ProfitAndLoss: " + bestResult.getProfitAndLoss());
        for (int i = 0; i < generation; i++) {
            /** ���� */
            resultList = recombination(resultList);
            bestResult = getBestResult(resultList);
            System.out.println("SelfDo recombination ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            /** ���� */
            mutation(resultList);
            bestResult = getBestResult(resultList);
            System.out.println("SelfDo mutation ProfitAndLoss" + i + ": " + bestResult.getProfitAndLoss());
            // System.out.println("SelfDo jobDetail " + i + ": " + bestResult.getJobResult());
        }
    }

    /**
     * ִ�б��죬��ǰ���õ����������������
     * 
     * @param resultList
     * @return
     */
    private void mutation(List<ExecuteResult> resultList) {
        MutationService.mutation(resultList);
    }

    /**
     * ִ�н������
     * 
     * @param resultList
     * @return
     */
    private List<ExecuteResult> recombination(List<ExecuteResult> resultList) {
        return RecombinationService.doIntermediateRecobination(resultList);
    }

    private ExecuteResult getBestResult(List<ExecuteResult> result) {
        Collections.sort(result, new ExecuteResultComparator());
        return result.get(0);
    }

    public static void main(String[] args) {
        AIServiceForROI service = new AIServiceForROI();
        service.init();
        service.process();
    }

}
