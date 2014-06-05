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
	// 行列(i,j)第i个工件在第j个机器上的加工时间
	private static int process_order[][] = { { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 },
			{ 1, 3, 5, 10, 4, 2, 7, 6, 5, 8, 9 },
			{ 2, 1, 4, 3, 9, 6, 8, 7, 10, 5 },
			{ 2, 3, 1, 5, 7, 9, 8, 4, 10, 6 },
			{ 3, 1, 2, 6, 4, 5, 9, 8, 10, 7 },
			{ 3, 2, 6, 4, 9, 10, 1, 7, 5, 8 },
			{ 2, 1, 4, 3, 7, 6, 10, 9, 8, 5 },
			{ 3, 1, 2, 6, 5, 7, 9, 10, 8, 4 },
			{ 1, 2, 4, 6, 3, 10, 7, 8, 5, 9 },
			{ 2, 1, 3, 7, 9, 10, 6, 4, 5, 8 } }; // 行列(i,j)第i个工件在机器上的的加工顺序
	private static final long serialVersionUID = 1L;
	public static String str = "";
	public Best best = null; // 最佳染色体
	public String[] ipop; // 染色体
	private String[] femaleipop; // 雌性染色体
	private String[] maleipop; // 雌性染色体
	public int gernation = 0; // 染色体代号
	public static final int Job = 10;// 工件数
	public static final int Machine = 10;// 机器数
	public static final int GENE = Job * Machine; // 基因数
	private double runnumber;// 迭代次数
	private int population;// 种群数
	private double Pcmax;// 最大交叉率
	private double Pc[];// 自适应交叉率
	private double FPmmax;// 雌性变异率
	private double MPmmax;// 雄性变异率
	private double FPm[];// 雌性自适应变异率
	private double MPm[];// 雄性自适应变异率
	public static double Favg = 0; // 平均适应值
	public static double Fmax = 0; // 最大适应值
	public static String w = "";

	// public static double[] BestFitness = new double[6000];// 每代的最优值
	// public static double MAX=0;
	/**
	 * Create the frame
	 */
	/**
	 * 初始化一条染色体（用随机自然数表示）
	 * 
	 * @return 一条染色体
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
	 * 初始化一组染色体
	 * 
	 * @return 染色体组
	 */
	public String[] inialPops() {
		String[] ipop = new String[population];
		for (int i = 0; i < population; i++) {
			ipop[i] = inialPop();
		}
		return ipop;
	}

	/**
	 * 将染色体转换成x的值
	 * 
	 * @param str
	 *            染色体
	 * @return 染色体的适应值
	 */
	private double calculatefitnessvalue(String str) {
		int[] data = new int[GENE];
		for (int i = 0; i < GENE; i++) {// 将字符串染色体转换为一维整数数组
			char temp = str.trim().charAt(i);
			data[i] = temp - 48;
		}
		int machineNum;
		int productNum;
		int n = Job, m = Machine;
		int ptemp = 0, mtemp = 0;// n个工件，m个工序
		int[] countProcess = new int[n];// 出现到第几道工序了
		int i;
		int max = 0;
		int[] mEndTime = new int[m];// 机器加工完成时间
		int[] pEndTime = new int[n];// 工件加工完成时间
		for (i = 0; i < m; i++) {
			mEndTime[i] = 0;
			countProcess[i] = 0;
		}
		for (i = 0; i < n; i++) {
			pEndTime[i] = 0;
		}
		for (i = 0; i < data.length; i++) {
			productNum = data[i] + 1;
			machineNum = process_order[productNum - 1][countProcess[productNum - 1]];// 取第一个矩阵的值
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
		// System.out.println("最大适应值" + max);
		return max;
	}

	/**
	 * 解码
	 */
	public int[][] decoding(String str) {
		/**
		 * 一个单元
		 * 
		 * @param JobNumber
		 *            Job编号
		 * @param MachineNum
		 *            机床编号
		 * @param StartTime
		 *            开始时间
		 * @param time
		 *            加工所需时间
		 */
		int[][] unitSet = new int[Job * Machine][4];
		int machineNum;
		int productNum;
		int ptemp = 0, mtemp = 0;// n个工件，m个工序
		int[] countProcess = new int[Job];// 出现到第几道工序了
		int i;
		int[] mEndTime = new int[Machine];// 机器加工完成时间
		int[] pEndTime = new int[Job];// 工件加工完成时间
		for (i = 0; i < Machine; i++) {
			mEndTime[i] = 0;
		}
		for (i = 0; i < Job; i++) {
			countProcess[i] = 0;
			pEndTime[i] = 0;
		}
		int[] data = new int[GENE];
		for (i = 0; i < GENE; i++) {// 将字符串染色体转换为一维整数数组
			char temp = str.trim().charAt(i);
			data[i] = temp - 48;
		}
		for (i = 0; i < str.length(); i++) {
			productNum = data[i] + 1;
			machineNum = process_order[productNum - 1][countProcess[productNum - 1]];// 取第一个矩阵的值
			ptemp = pEndTime[productNum - 1];
			mtemp = mEndTime[machineNum - 1];
			unitSet[i][0] = productNum;// Job编号
			unitSet[i][1] = machineNum;// 机床编号
			unitSet[i][2] = Math.max(ptemp, mtemp);// 开始时间
			unitSet[i][3] = process_time[productNum - 1][countProcess[productNum - 1]];// 加工所需时间
			pEndTime[productNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			mEndTime[machineNum - 1] = Math.max(ptemp, mtemp)
					+ process_time[productNum - 1][countProcess[productNum - 1]];
			countProcess[productNum - 1]++;

		}

		return unitSet;
	}

	/**
	 * 计算群体上每个个体的适应度值; 按由个体适应度值所决定的精英选择将进入下一代的个体;
	 */
	public void select() {
		double evals[] = new double[population]; // 所有染色体适应值
		double p[] = new double[population]; // 各染色体选择概率
		double q[] = new double[population]; // 累计概率
		double F = 0; // 累计适应值总和
		double index = 0;// 指数
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
				if (evals[i] < best.fitness) // 最好的记录下来
				{
					w = "";
					best.fitness = evals[i];

					best.generations = gernation;
					best.str = ipop[i];
					w = ipop[i];
				} else {
					if (evals[i] > Fmax)// 最大适应值
						Fmax = evals[i];
				}
			}
			F = F + evals[i]; // 所有染色体适应值总和
		}

		Favg = F / population;// 平均适应值
		for (int i = 0; i < population; i++) {// 自适应交叉率，利用HD-AGA公式，根据适应值的大小调整交叉率
			if (evals[i] > Favg) {
				index = -0.382 * (evals[i] - Favg) / (Fmax - Favg);
				Pc[i] = Pcmax * Math.exp(index);
			} else
				Pc[i] = Pcmax;
		}
		for (int i = 0; i < population; i++) {// 自适应变异率，利用HD-AGA公式，根据适应值的大小调整变异率
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
				q[i] = q[i - 1] + p[i];// 累计概率有什么用?一种选择规则
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
	 * 交叉操作 ,有性繁殖，交叉概率自适应调整
	 */
	public void cross() {// 两点部分交叉
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
				int pos1 = k1 % GENE;// 随机生成交叉点
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
				str1 = femaleipop[x1].substring(pos1, pos2);// 雌性染色体只能与雄性染色体交配
				str2 = maleipop[x2].substring(pos1, pos2);
				int[] data1 = new int[pos2 - pos1];
				int[] data2 = new int[pos2 - pos1];
				for (i = 0; i < str1.length(); i++) {// 将交叉字符串染色体转换为一维整数数组
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
	 * 基因突变操作
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
					for (int k = 0; k < GENE; k++) {// 将字符串染色体转换为一维整数数组
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
				for (int k = 0; k < GENE; k++) {// 将字符串染色体转换为一维整数数组
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
	 * 执行遗传算法
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
		str = "最小化最大完成时间：" + best.fitness + "\n最佳染色体：第" + best.generations
				+ "个\n" + "最佳工序:" + best.str;
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
	 * //BestFitness[i] = MAX; } str = "最小化最大完成时间：" + best.fitness + "\n最佳染色体：第"
	 * + best.generations + "个\n" + "最佳工序:" + best.str; return str; }
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
			System.out.println("运行时间：" + (t2 - t1) + "毫秒");
			randomToTxt(test.decoding(w));
			// 生成折线图
			/*
			 * LineCharts.setArg(BestFitness); LineCharts.setNum(1000);
			 * LineCharts chart = new LineCharts("折线图"); chart.pack();
			 * RefineryUtilities.centerFrameOnScreen(chart);
			 * chart.setVisible(true);
			 */
			// 生成甘特图
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
	 * 输出结果至文本
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
