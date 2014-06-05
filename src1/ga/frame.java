package ga;
import gantt.GroupOptimize;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import ga.Sexual_GA;

public class frame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sexual_GA test;

	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_7;
	private JTextField textField_6;
	private JTextField textField_5;

	/**
	 * Launch the application
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame frame = new frame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame
	 */
	public frame() {
		super();

		test = new Sexual_GA();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton button;
		button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
			}
		});

		button.addMouseListener(new MouseAdapter() {
			@SuppressWarnings("static-access")
			public void mouseClicked(final MouseEvent e) {
				test.setPopulation(Integer.parseInt(textField.getText()));
				test.setPcmax(Double.parseDouble(textField_2.getText()));
				test.setFPmmax(Double.parseDouble(textField_1.getText()));
				test.setMPmmax(Double.parseDouble(textField_3.getText()));
				test.setrunnumber(Double.parseDouble(textField_4.getText()));
				try {
					double t1 = System.currentTimeMillis();
					test.initParameters();
					test.ipop = test.inialPops();
					// test.process(progressBar);
					test.str = test.str + test.process() + "\n";
					System.out.print(test.str);
					double t2 = System.currentTimeMillis();
					System.out.println("运行时间：" + (t2 - t1) + "毫秒");
					test.randomToTxt(test.decoding(test.w));
					textField_5.setText(test.best.fitness + " ");
					textField_6.setText(test.best.generations+ " ");
					textField_7.setText(t2 - t1 + " ");
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
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		// System.out.println(test.best.fitness);
		button.setText("计算");

		JLabel label;
		label = new JLabel();
		label.setText("种群规模");

		JLabel label_1;
		label_1 = new JLabel();
		label_1.setText("初始交叉率");

		JLabel label_2;
		label_2 = new JLabel();
		label_2.setText("雌性初始变异率");

		JLabel label_3;
		label_3 = new JLabel();
		label_3.setText("雄性初始变异率");

		JLabel label_4;
		label_4 = new JLabel();
		label_4.setText("迭代次数");

		JLabel label_5;
		label_5 = new JLabel();
		label_5.setText("最优解");

		JLabel label_6;
		label_6 = new JLabel();
		label_6.setText("最优染色体");

		JLabel label_7;
		label_7 = new JLabel();
		label_7.setText("运行时间");

		textField = new JTextField();
		textField.setText("");

		textField_1 = new JTextField();
		textField_1.setText("");

		textField_2 = new JTextField();
		textField_2.setText("");

		textField_3 = new JTextField();
		textField_3.setText("");

		textField_4 = new JTextField();
		// textField_4.setText(new Double(test.getrunnumber()).toString());
		textField_4.setText("");
		textField_5 = new JTextField();
		// textField_5.setText(new Double(test.best.fitness)+ " ");
		textField_5.setText("");
		textField_6 = new JTextField();
		textField_6.setText("");
		textField_7 = new JTextField();
		textField_7.setText("");
		final GroupLayout groupLayout = new GroupLayout(
				(JComponent) getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(GroupLayout.Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																GroupLayout.Alignment.TRAILING)
														.addComponent(button)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addComponent(
																												label)
																										.addGap(51,
																												51,
																												51)
																										.addComponent(
																												textField,
																												GroupLayout.PREFERRED_SIZE,
																												87,
																												GroupLayout.PREFERRED_SIZE))
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGap(103,
																												103,
																												103)
																										.addComponent(
																												textField_2,
																												GroupLayout.PREFERRED_SIZE,
																												87,
																												GroupLayout.PREFERRED_SIZE))
																						.addComponent(
																								label_1)
																						.addGroup(
																								groupLayout
																										.createSequentialGroup()
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																label_3)
																														.addComponent(
																																label_6))
																										.addPreferredGap(
																												LayoutStyle.ComponentPlacement.RELATED)
																										.addGroup(
																												groupLayout
																														.createParallelGroup(
																																GroupLayout.Alignment.LEADING)
																														.addComponent(
																																textField_6,
																																GroupLayout.PREFERRED_SIZE,
																																87,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																textField_3,
																																GroupLayout.PREFERRED_SIZE,
																																87,
																																GroupLayout.PREFERRED_SIZE))))
																		.addGap(43,
																				43,
																				43)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								label_2)
																						.addComponent(
																								label_4)
																						.addComponent(
																								label_5)
																						.addComponent(
																								label_7))
																		.addGap(7,
																				7,
																				7)
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								GroupLayout.Alignment.LEADING)
																						.addComponent(
																								textField_7,
																								GroupLayout.PREFERRED_SIZE,
																								87,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textField_5,
																								GroupLayout.PREFERRED_SIZE,
																								87,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textField_1,
																								GroupLayout.PREFERRED_SIZE,
																								87,
																								GroupLayout.PREFERRED_SIZE)
																						.addComponent(
																								textField_4,
																								GroupLayout.PREFERRED_SIZE,
																								87,
																								GroupLayout.PREFERRED_SIZE))))
										.addContainerGap(54, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				GroupLayout.Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGap(34, 34, 34)
						.addGroup(
								groupLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(label)
										.addComponent(textField,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_4)
										.addComponent(textField_4,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(33, 33, 33)
						.addGroup(
								groupLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(label_1)
										.addComponent(textField_2,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_1,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_2))
						.addGap(31, 31, 31)
						.addGroup(
								groupLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(label_3)
										.addComponent(textField_3,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(textField_5,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_5))
						.addGap(35, 35, 35)
						.addGroup(
								groupLayout
										.createParallelGroup(
												GroupLayout.Alignment.BASELINE)
										.addComponent(textField_6,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_6)
										.addComponent(textField_7,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(label_7))
						.addGap(34, 34, 34).addComponent(button)
						.addContainerGap(56, Short.MAX_VALUE)));
		getContentPane().setLayout(groupLayout);
		pack();
		//
	}

}
