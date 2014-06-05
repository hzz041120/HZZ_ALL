package ga;

import gantt.GroupOptimize;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Random;

import javax.swing.JFrame;

public class Sexual_GA {
	private static int process_time[][] = {
			{ 29, 78, 9, 36, 49, 11, 62, 56, 44, 21 },
			{ 43, 90, 75, 11, 69, 28, 46, 46, 72, 30 },
			{ 91, 85, 39, 74, 90, 10, 12, 89, 45, 33 },
			{ 81, 95, 71, 99, 9, 52, 85, 98, 22, 43 },
			{ 14, 1, 22, 61, 26, 69, 21, 49, 72, 53 },
			{ 84, 2, 52, 95, 48, 72, 47, 65, 6, 25 },
			{ 46, 37, 61, 13, 32, 21, 32, 89, 30, 55 },
			{ 21, 86, 46, 74, 32, 88, 19, 48, 36, 79 },
			{ 76, 69, 76, 51, 85, 11, 40, 89, 26, 74 },
			{ 85, 13, 61, 7, 64, 76, 47, 52, 90, 45 } };
	// ����(i,j)��i�������ڵ�j�������ϵļӹ�ʱ��
	private static int process_order[][] = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
			{ 1, 3, 5, 10, 4, 2, 7, 6, 5, 8, 9 },
			{ 2, 1, 4, 3, 9, 6, 8, 7, 10, 5 },
			{ 2, 3, 1, 5, 7, 9, 8, 4, 10, 6 },
			{ 3, 1, 2, 6, 4, 5, 9, 8, 10, 7 },
			{ 3, 2, 6, 4, 9, 10, 1, 7, 5, 8 },
			{ 2, 1, 4, 3, 7, 6, 10, 9, 8, 5 },
			{ 3, 1, 2, 6, 5, 7, 9, 10, 8, 4 },
			{ 1, 2, 4, 6, 3, 10, 7, 8, 5, 9 },
			{ 2, 1, 3, 7, 9, 10, 6, 4, 5, 8 } }; // ����(i,j)��i�������ڻ����ϵĵļӹ�˳��
	private static final long serialVersionUID = 1L;
	public static String str = "";
	public Best best = null; // ���Ⱦɫ��
	public String[] ipop; // Ⱦɫ��
	private String[] femaleipop; // ����Ⱦɫ��
	private String[] maleipop; // ����Ⱦɫ��
	public int gernation = 0; // Ⱦɫ�����
	public static final int Job = 10;// ������
	public static final int Machine = 10;// ������
	public static final int GENE = Job * Machine; // ������
	private double runnumber;// ��������
	private int population;// ��Ⱥ��
	private double Pcmax;// ��󽻲���
	private double Pc[];// ����Ӧ������
	private double FPmmax;// ���Ա�����
	private double MPmmax;// ���Ա�����
	private double FPm[];// ��������Ӧ������
	private double MPm[];// ��������Ӧ������
	public static double Favg = 0; // ƽ����Ӧֵ
	public static double Fmax = 0; // �����Ӧֵ
	public static String w = "";

	// public static double[] BestFitness = new double[6000];// ÿ��������ֵ
	// public static double MAX=0;
	/**
	 * Create the frame
	 */
	/**
	 * ��ʼ��һ��Ⱦɫ�壨�������Ȼ����ʾ��
	 * 
	 * @return һ��Ⱦɫ��
	 */
	public Sexual_GA() {
		population = 0;
		Pcmax = 0;
		FPmmax = 0;
		MPmmax = 0;
	}

	public void initParameters() {
		ipop = new String[population];
		femaleipop = new String[population / 2];
		maleipop = new String[population / 2];
		Pc = new double[population];
		FPm = new double[population];
		MPm = new double[population];
	}
	public double getrunnumber() {
		return runnumber;
	}

	public void setrunnumber(double runnumber) {
		this.runnumber = runnumber;
	}
	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	public double getPcmax() {
		return Pcmax;
	}

	public void setPcmax(double pcmax) {
		Pcmax = pcmax;
	}

	public double getFPmmax() {
		return FPmmax;
	}

	public void setFPmmax(double fPmmax) {
		FPmmax = fPmmax;
	}

	public double getMPmmax() {
		return MPmmax;
	}

	public void setMPmmax(double mPmmax) {
		MPmmax = mPmmax;
	}

	private static String inialPop() {
		Random random = new Random();
		String res = "";
		int Max = Job;
		int TCount = Machine;
		int i;
		int count = 0;
		int temp, j, k = 0;
		int[] data = new int[GENE];
		for (i = 0; i < GENE; i++) {
			data[i] = -1;
		}
		while (k != GENE) {
			count = 0;
			temp = random.nextInt(Max);
			for (j = 0; data[j] != -1; j++) {
				if (temp == data[j])
					count++;
			}

			if (count < TCount)
				data[k++] = temp;
		}
		for (i = 0; i < data.length; i++) {
			res += data[i];
		}
		return res;
	}

	/**
	 * ��ʼ��һ��Ⱦɫ��
	 * 
	 * @return Ⱦɫ����
	 */
	public String[] inialPops() {
		String[] ipop = new String[population];
		for (int i = 0; i < population; i++) {
			ipop[i] = inialPop();
		}
		return ipop;
	}

	/**
	 * ��Ⱦɫ��ת����x��ֵ
	 * 
	 * @param str
	 *            Ⱦɫ��
	 * @return Ⱦɫ�����Ӧֵ
	 */
	private double calculatefitnessvalue(String str) {
		int[] data = new int[GENE];
		for (int i = 0; i < GENE; i++) {// ���ַ���Ⱦɫ��ת��Ϊһά��������
			char temp = str.trim().charAt(i);
			data[i] = temp - 48;
		}
		int machineNum;
		int productNum;
		int n = Job, m = Machine;
		int ptemp = 0, mtemp = 0;// n��������m������
		int[] countProcess = new int[n];// ���ֵ��ڼ���������
		int i;
		int max = 0;
		int[] mEndTime = new int[m];// �����ӹ����ʱ��
		int[] pEndTime = new int[n];// �����ӹ����ʱ��
		for (i = 0; i < m; i++) {
			mEndTime[i] = 0;
			countProcess[i] = 0;
		}
		for (i = 0; i < n; i++) {
			pEndTime[i] = 0;
		}
		for (i = 0; i < data.length; i++) {
			productNum = data[i] + 1;
			machineNum = process_order[productNum - 1][countProcess[productNum - 1]];// ȡ��һ�������ֵ
			ptemp = pEndTime[productNum - 1];
			mtemp = mEndTime[machineNum - 1];
			pEndTime[productNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			mEndTime[machineNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			countProcess[productNum - 1]++;
		}
		for (i = 0; i < n; i++) {
			if (max < pEndTime[i])
				max = pEndTime[i];
		}
		// System.out.println("�����Ӧֵ" + max);
		return max;
	}

	/**
	 * ����
	 */
	public int[][] decoding(String str) {
		/**
		 * һ����Ԫ
		 * 
		 * @param JobNumber
		 *            Job���
		 * @param MachineNum
		 *            �������
		 * @param StartTime
		 *            ��ʼʱ��
		 * @param time
		 *            �ӹ�����ʱ��
		 */
		int[][] unitSet = new int[Job * Machine][4];
		int machineNum;
		int productNum;
		int ptemp = 0, mtemp = 0;// n��������m������
		int[] countProcess = new int[Job];// ���ֵ��ڼ���������
		int i;
		int[] mEndTime = new int[Machine];// �����ӹ����ʱ��
		int[] pEndTime = new int[Job];// �����ӹ����ʱ��
		for (i = 0; i < Machine; i++) {
			mEndTime[i] = 0;
		}
		for (i = 0; i < Job; i++) {
			countProcess[i] = 0;
			pEndTime[i] = 0;
		}
		int[] data = new int[GENE];
		for (i = 0; i < GENE; i++) {// ���ַ���Ⱦɫ��ת��Ϊһά��������
			char temp = str.trim().charAt(i);
			data[i] = temp - 48;
		}
		for (i = 0; i < str.length(); i++) {
			productNum = data[i] + 1;
			machineNum = process_order[productNum - 1][countProcess[productNum - 1]];// ȡ��һ�������ֵ
			ptemp = pEndTime[productNum - 1];
			mtemp = mEndTime[machineNum - 1];
			unitSet[i][0] = productNum;// Job���
			unitSet[i][1] = machineNum;// �������
			unitSet[i][2] = Math.max(ptemp, mtemp);// ��ʼʱ��
			unitSet[i][3] = process_time[productNum - 1][countProcess[productNum - 1]];// �ӹ�����ʱ��
			pEndTime[productNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			mEndTime[machineNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			countProcess[productNum - 1]++;

		}

		return unitSet;
	}

	/**
	 * ����Ⱥ����ÿ���������Ӧ��ֵ; ���ɸ�����Ӧ��ֵ�������ľ�Ӣѡ�񽫽�����һ���ĸ���;
	 */
	public void select() {
		double evals[] = new double[population]; // ����Ⱦɫ����Ӧֵ
		double p[] = new double[population]; // ��Ⱦɫ��ѡ�����
		double q[] = new double[population]; // �ۼƸ���
		double F = 0; // �ۼ���Ӧֵ�ܺ�
		double index = 0;// ָ��
		for (int i = 0; i < population; i++) {
			evals[i] = calculatefitnessvalue(ipop[i]);
			/*
			 * MAX=evals[0]; if(evals[i]<MAX){ MAX=evals[i]; }
			 */
			if (best == null) {
				best = new Best();
				best.fitness = evals[i];
				best.generations = 0;
				best.str = ipop[i];
			} else {
				if (evals[i] < best.fitness) // ��õļ�¼����
				{
					w = "";
					best.fitness = evals[i];

					best.generations = gernation;
					best.str = ipop[i];
					w = ipop[i];
				} else {
					if (evals[i] > Fmax)// �����Ӧֵ
						Fmax = evals[i];
				}
			}
			F = F + evals[i]; // ����Ⱦɫ����Ӧֵ�ܺ�
		}

		Favg = F / population;// ƽ����Ӧֵ
		for (int i = 0; i < population; i++) {// ����Ӧ�����ʣ�����HD-AGA��ʽ��������Ӧֵ�Ĵ�С����������
			if (evals[i] > Favg) {
				index = -0.382 * (evals[i] - Favg) / (Fmax - Favg);
				Pc[i] = Pcmax * Math.exp(index);
			} else
				Pc[i] = Pcmax;
		}
		for (int i = 0; i < population; i++) {// ����Ӧ�����ʣ�����HD-AGA��ʽ��������Ӧֵ�Ĵ�С����������
			if (evals[i] >= Favg) {
				index = -0.618 * (Fmax - evals[i]) / (Fmax - Favg);
				FPm[i] = FPmmax * Math.exp(index);
				MPm[i] = MPmmax * Math.exp(index);
			} else {
				FPm[i] = FPmmax;
				MPm[i] = MPmmax;
			}
		}

		for (int i = 0; i < population; i++) {
			p[i] = evals[i] / F;
			if (i == 0)
				q[i] = p[i];
			else {
				q[i] = q[i - 1] + p[i];// �ۼƸ�����ʲô��?һ��ѡ�����
			}
		}
		for (int i = 0; i < population; i++) {

			double r = Math.random();
			if (r <= q[0]) {
				ipop[i] = ipop[0];

			} else {
				for (int j = 1; j < population; j++) {
					if (r < q[j]) {
						ipop[i] = ipop[j];
						break;
					}
				}
			}
		}
	}

	/**
	 * ������� ,���Է�ֳ�������������Ӧ����
	 */
	public void cross() {// ���㲿�ֽ���
		String trans1, trans2;
		int i, temp, count1 = 0, count2 = 0;
		Random random = new Random();
		for (i = 0; i < population; i++) {
			if (i % 2 == 0) {
				trans1 = ipop[i];
				femaleipop[count1] = trans1;
				trans1 = "";
				count1++;
			} else {
				trans2 = ipop[i];
				maleipop[count2] = trans2;
				trans2 = "";
				count2++;
			}
		}
		for (i = 0; i < population / 2; i++) {
			String str1 = "", str2 = "", convert1 = "", convert2 = "", cross1 = "", cross2 = "";
			int x1 = random.nextInt(population / 2);
			int x2 = random.nextInt(population / 2);
			double evalue1 = calculatefitnessvalue(femaleipop[x1]);
			double evalue2 = calculatefitnessvalue(maleipop[x2]);
			if (evalue1 < Favg || evalue2 < Favg)
				break;
			if (Math.random() < Pc[i]) {
				double r1 = Math.random();
				double r2 = Math.random();
				int k1 = (int) (Math.round(r1 * 1000));
				int k2 = (int) (Math.round(r2 * 1000));
				int pos1 = k1 % GENE;// ������ɽ����
				int pos2 = k2 % GENE;
				if (pos1 > pos2) {
					temp = pos1;
					pos1 = pos2;
					pos2 = temp;
				}
				if (pos2 == 0) {
					pos2 = 1;
				}
				if (pos1 == pos2) {
					pos2++;
				}
				str1 = femaleipop[x1].substring(pos1, pos2);// ����Ⱦɫ��ֻ��������Ⱦɫ�彻��
				str2 = maleipop[x2].substring(pos1, pos2);
				int[] data1 = new int[pos2 - pos1];
				int[] data2 = new int[pos2 - pos1];
				for (i = 0; i < str1.length(); i++) {// �������ַ���Ⱦɫ��ת��Ϊһά��������
					char char1 = str1.trim().charAt(i);
					char char2 = str2.trim().charAt(i);
					data1[i] = char1 - 48;
					data2[i] = char2 - 48;
				}
				int count = str1.length();
				if (count > 1) {
					QuickSort test = new QuickSort();
					test.quickSort(data1, 0, str1.length() - 1);
					test.quickSort(data2, 0, str2.length() - 1);
				}
				for (i = 0; i < data1.length; i++) {
					convert1 += data1[i];
					convert2 += data2[i];
				}
				if (convert1.equals(convert2)) {
					cross1 = femaleipop[x1].substring(0, pos1)
							+ maleipop[x2].substring(pos1, pos2)
							+ femaleipop[x1].substring(pos2);
					cross2 = maleipop[x2].substring(0, pos1)
							+ femaleipop[x1].substring(pos1, pos2)
							+ maleipop[x2].substring(pos2);
					ipop[2 * x1] = cross1;
					ipop[2 * x2 + 1] = cross2;
				}
			}

		}
	}

	/**
	 * ����ͻ�����
	 */
	public void mutation() {
		Random random = new Random();
		for (int i = 0; i < population; i++) {
			double r = Math.random();
			if (i % 2 == 0) {
				if (r < FPm[i]) {
					int k1 = random.nextInt(GENE);
					int k2 = random.nextInt(GENE);
					int k3 = random.nextInt(GENE);
					int k4 = random.nextInt(GENE);
					int k5 = random.nextInt(GENE);
					int k6 = random.nextInt(GENE);
					int k7 = random.nextInt(GENE);
					int k8 = random.nextInt(GENE);
					int k9 = random.nextInt(GENE);
					int k10 = random.nextInt(GENE);
					int k11 = random.nextInt(GENE);
					int k12 = random.nextInt(GENE);
					int[] data = new int[GENE];
					for (int k = 0; k < GENE; k++) {// ���ַ���Ⱦɫ��ת��Ϊһά��������
						char temp = femaleipop[i / 2].trim().charAt(k);
						data[k] = temp - 48;
					}
					int cnum1 = 0, cnum2 = 0, cnum3 = 0, cnum4 = 0, cnum5 = 0, cnum6 = 0;
					if (data[k1] != data[k2] || data[k3] != data[k4]) {
						cnum1 = data[k1];
						data[k1] = data[k2];
						data[k2] = cnum1;
						cnum2 = data[k3];
						data[k3] = data[k4];
						data[k4] = cnum2;
						cnum3 = data[k5];
						data[k5] = data[k6];
						data[k6] = cnum3;
						cnum4 = data[k7];
						data[k7] = data[k8];
						data[k8] = cnum4;
						cnum5 = data[k9];
						data[k9] = data[k10];
						data[k10] = cnum5;
						cnum6 = data[k11];
						data[k11] = data[k12];
						data[k12] = cnum6;
					}
					ipop[i] = "";
					for (int j = 0; j < data.length; j++) {
						ipop[i] += data[j];
					}
				}
			} else if (r < MPm[i]) {
				int k1 = random.nextInt(GENE);
				int k2 = random.nextInt(GENE);
				int k3 = random.nextInt(GENE);
				int k4 = random.nextInt(GENE);
				int k5 = random.nextInt(GENE);
				int k6 = random.nextInt(GENE);
				int k7 = random.nextInt(GENE);
				int k8 = random.nextInt(GENE);
				int k9 = random.nextInt(GENE);
				int k10 = random.nextInt(GENE);
				int k11 = random.nextInt(GENE);
				int k12 = random.nextInt(GENE);
				int[] data = new int[GENE];
				for (int k = 0; k < GENE; k++) {// ���ַ���Ⱦɫ��ת��Ϊһά��������
					char temp = maleipop[i % 2].trim().charAt(k);
					data[k] = temp - 48;
				}
				int cnum1 = 0, cnum2 = 0, cnum3 = 0, cnum4 = 0, cnum5 = 0, cnum6 = 0;
				if (data[k1] != data[k2] || data[k3] != data[k4]) {
					cnum1 = data[k1];
					data[k1] = data[k2];
					data[k2] = cnum1;
					cnum2 = data[k3];
					data[k3] = data[k4];
					data[k4] = cnum2;
					cnum3 = data[k5];
					data[k5] = data[k6];
					data[k6] = cnum3;
					cnum4 = data[k7];
					data[k7] = data[k8];
					data[k8] = cnum4;
					cnum5 = data[k9];
					data[k9] = data[k10];
					data[k10] = cnum5;
					cnum6 = data[k11];
					data[k11] = data[k12];
					data[k12] = cnum6;
				}
				ipop[i] = "";
				for (int j = 0; j < data.length; j++) {
					ipop[i] += data[j];
				}
			}
		}

	}

	/**
	 * ִ���Ŵ��㷨
	 */
	public String process() {
		String str = "";
		for (int i = 0; i < runnumber; i++) {
			this.select();
			this.cross();
			this.mutation();
			gernation = i;
			// BestFitness[i] = MAX;
		}
		str = "��С��������ʱ�䣺" + best.fitness + "\n���Ⱦɫ�壺��" + best.generations
				+ "��\n" + "��ѹ���:" + best.str;
		return str;
	}

	/*
	 * public String process(JProgressBar progress) {
	 * 
	 * progress.setMaximum(100);
	 * 
	 * String str = ""; for (int i = 0; i < 100; i++) {
	 * 
	 * progress.setValue(i);
	 * 
	 * this.select(); this.cross(); this.mutation(); gernation = i;
	 * //BestFitness[i] = MAX; } str = "��С��������ʱ�䣺" + best.fitness + "\n���Ⱦɫ�壺��"
	 * + best.generations + "��\n" + "��ѹ���:" + best.str; return str; }
	 */

	public static void main(String args[]) {

		try {
			double t1 = System.currentTimeMillis();
			Sexual_GA test = new Sexual_GA();
			test.ipop = test.inialPops();
			test.process();
			str = str + test.process() + "\n";
			System.out.print(str);
			double t2 = System.currentTimeMillis();
			System.out.println("����ʱ�䣺" + (t2 - t1) + "����");
			randomToTxt(test.decoding(w));
			// ��������ͼ
			/*
			 * LineCharts.setArg(BestFitness); LineCharts.setNum(1000);
			 * LineCharts chart = new LineCharts("����ͼ"); chart.pack();
			 * RefineryUtilities.centerFrameOnScreen(chart);
			 * chart.setVisible(true);
			 */
			// ���ɸ���ͼ
			GroupOptimize groupOptimize1 = new GroupOptimize();
			JFrame frame = new JFrame(
					"NETRONIC Varchart JGantt - Group Optimize");
			frame.getContentPane().add("Center", groupOptimize1);
			frame.setSize(new Dimension(700, 375));

			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			frame.setLocation((d.width - frame.getSize().width) / 2,
					(d.height - frame.getSize().height) / 2);
			frame.setVisible(true);
			frame.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * ���������ı�
	 */
	public static void randomToTxt(int[][] unitSet) {

		String path = "C:\\workspace\\SGA_2\\src\\input2.txt";
		PrintStream old = System.out;
		PrintStream ps = null;

		try {
			ps = new PrintStream(new FileOutputStream(path));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		System.setOut(ps);

		for (int i = 0; i < unitSet.length; i++) {
			System.out.println(unitSet[i][0] + " " + unitSet[i][1] + " "
					+ unitSet[i][2] + " " + unitSet[i][3]);
		}

		System.setOut(old);
		ps.close();

	}
}
