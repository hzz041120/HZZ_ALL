package hzz.domain;


/**
 * ��������
 * 
 * @author hzz041120 2014��4��6�� ����8:12:02
 */
public class JobType {

    public JobType(Integer rev, Integer timeCost) {
        roi = rev / timeCost;
    }

    // �����ӹ�������
    private Integer rev;
    // �����ӹ���Ҫ��ʱ��
    private Integer timeCost;
    private double  roi;

    // ����ӹ���ʱ��
    // ����ӹ�������
    // �ܾ�������

    public double getRoi() {
        return roi;
    }

    public Integer getRev() {
        return rev;
    }

    public Integer getTimeCost() {
        return timeCost;
    }
        
}
