package hzz.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ִ���� ������ ������ܾ�ִ�к���������
 * 
 * @author hzz041120 2014��4��6�� ����8:25:13
 */
public class Executer {

    public Executer(String execName) {
        this.execName = execName;
    }

    /**
     * ִ��������
     */
    private String                   execName;
    /**
     * ���������ʱ��
     */
    private Map<JobType, JobExecuteInfo> jobExecMap = new HashMap<JobType, JobExecuteInfo>();

    public Map<JobType, JobExecuteInfo> getJobExecMap() {
        return jobExecMap;
    }

    public void setJobExecMap(Map<JobType, JobExecuteInfo> jobExecMap) {
        this.jobExecMap = jobExecMap;
    }

    public String getExecName() {
        return execName;
    }
    

}
