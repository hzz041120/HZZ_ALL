package gantt;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

//���ɸ���ͼ
public class GanttDataSource {

	/**
	 * ����
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		String path = "C:\\workspace\\SGA_2\\src\\input2.txt";
		GanttDataSource gd = new GanttDataSource();
		gd.generateDataFromFile(path);
		System.out.println("------------------");
	}

	/**
	 * ʹ�ò�������
	 * 
	 * @return
	 */
	public LinkedList<Squence> generateTestData() {
		LinkedList<Squence> example = new LinkedList<Squence>();

		example.add(new Squence(1, 1, 0, 7));
		example.add(new Squence(1, 2, 12, 7));

		example.add(new Squence(2, 4, 0, 7));
		example.add(new Squence(2, 5, 7, 5));

		example.add(new Squence(3, 2, 0, 7));
		example.add(new Squence(3, 2, 7, 5));
		example.add(new Squence(3, 3, 12, 3));

		example.add(new Squence(4, 3, 7, 5));
		example.add(new Squence(4, 4, 12, 7));

		example.add(new Squence(5, 3, 0, 7));
		example.add(new Squence(5, 1, 7, 5));
		example.add(new Squence(5, 1, 12, 3));
		example.add(new Squence(5, 5, 15, 4));

		return example;

	}

	/**
	 * ��һ���ļ��ṩ����Դ
	 * 
	 * @param path
	 *            �ļ���λ��
	 * @return
	 */
	public LinkedList<Squence> generateDataFromFile(String path) {
		LinkedList<Squence> data = new LinkedList<Squence>();

		try {
			FileReader fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);

			String Squence = br.readLine();

			while (Squence != null) {
				Scanner sc = new Scanner(Squence);
				Squence sq = new Squence(sc.nextInt(), sc.nextInt(),
						sc.nextInt(), sc.nextInt());
				data.add(sq);
				Squence = br.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�ļ�δ�ҵ���");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

	/**
	 * ��ȡjob������
	 * 
	 * @param DateSource
	 *            ����Դ
	 * @return
	 */
	public int getJobNumber(LinkedList<Squence> DateSource) {
		int JobAmount = 0;

		Iterator<Squence> it1 = DateSource.iterator();
		while (it1.hasNext()) {
			Squence squence = it1.next();
			if (squence.getJobNumber() > JobAmount)
				JobAmount = squence.getJobNumber();
		}

		return JobAmount;

	}

	/**
	 * ��ȡMachine������
	 * 
	 * @param DateSource
	 *            ����Դ
	 * @return
	 */
	public int getMachineNum(LinkedList<Squence> DateSource) {
		int MachineNum = 0;

		Iterator<Squence> it1 = DateSource.iterator();
		while (it1.hasNext()) {
			Squence squence = it1.next();
			if (squence.getMachineNum() > MachineNum)
				MachineNum = squence.getMachineNum();
		}

		return MachineNum;

	}

}
