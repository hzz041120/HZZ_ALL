package hzz.service;

import hzz.domain.ExecuteResult;
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
    //�ӹ���������
    private int worktime = 0;
    //�������ͼ���������
    private Map<JobType,Integer> jobType$count = new HashMap<JobType,Integer>();
    //�����б�
    private List<Machine> machineList = new ArrayList<Machine>();
    
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
        /** ��þܾ��ӹ��Ĳ�Ʒ��ˮ*/
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
        List<ExecuteResult> originalResult = initOriginalResult();
        return null;
    }

    private List<ExecuteResult> initOriginalResult() {
        // TODO Auto-generated method stub
        return null;
    }

}
