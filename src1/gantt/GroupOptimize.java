package gantt;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

import de.netronic.common.beanbase.NeColorMap;
import de.netronic.common.beanbase.NeMappedColor;
import de.netronic.common.intface.NeIAppData;
import de.netronic.common.intface.NeICalendar;
import de.netronic.common.intface.NeIEntity;
import de.netronic.common.intface.NeIEntitySet;
import de.netronic.common.intface.NeIProfile;
import de.netronic.common.intface.NeITimeSpan;
import de.netronic.jgantt.JGColorScheme;
import de.netronic.jgantt.JGSymbol;
import de.netronic.jgantt.JGantt;

public class GroupOptimize extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JGantt jGantt1 = new JGantt();
	private JPanel jPanel_jGantt = new JPanel();
	private BorderLayout borderLayout_jGantt = new BorderLayout();
	private JEditorPane jEditorPane_Info = new JEditorPane();
	private JLabel jLabel_Info = new JLabel("Information");
	private long today = getToday();
	private JButton jButton_Close = new JButton();
	private NeColorMap theMap = new NeColorMap();
	private NeMappedColor theMapColor = new NeMappedColor(Color.green, theMap,
			"Color");
	/**
	 * 数据文件路径
	 */
	// private String path =
	// "H:\\Project\\JobShopForLSP\\JGanttExample\\bak.txt";
	private String path = "C:\\workspace\\SGA_2\\src\\input2.txt";

	private static final long ONE_DAY = 86400000L;

	public GroupOptimize() {
		try {
			jbInit();
			createData(jGantt1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initializeCalendar() {
		// define a calendar with saturday and sunday as work-free periods
		NeIProfile profile1;
		NeITimeSpan ts1;
		NeITimeSpan ts2;
		NeICalendar theCalendar = jGantt1.getCalendar();
		ts1 = theCalendar.createTimeSpan("saturday.*.*", 0, 1);
		ts2 = theCalendar.createTimeSpan("sunday.*.*", 0, 2);
		profile1 = theCalendar.createProfile();
		profile1.setName("weekend");
		profile1.addTimeSpan(ts1);
		profile1.addTimeSpan(ts2);
	}

	public void jbInit() throws Exception {
		this.setLayout(null);
		initializeCalendar();

		// Map for dynamic colors
		theMap.addRange("1", new Color(160, 165, 195));
		theMap.addRange("2", new Color(255, 197, 138));
		theMap.addRange("3", new Color(185, 255, 185));
		theMap.addRange("4", new Color(255, 255, 138));
		theMap.addRange("5", new Color(183, 130, 182));
		theMap.addRange("6", new Color(130, 192, 255));
		theMap.addRange("7", new Color(160, 165, 195));
		theMap.addRange("8", new Color(255, 197, 138));
		theMap.addRange("9", new Color(185, 255, 185));
		theMap.addRange("10", new Color(255, 255, 138));
		theMap.addRange("11", new Color(183, 130, 182));
		theMap.addRange("12", new Color(130, 192, 255));
		theMap.addRange("13", new Color(185, 255, 185));
		theMap.addRange("14", new Color(255, 255, 138));
		theMap.addRange("15", new Color(183, 130, 182));
		theMap.addRange("16", new Color(130, 192, 255));
		theMap.addRange("17", new Color(160, 165, 195));
		theMap.addRange("18", new Color(255, 197, 138));
		theMap.addRange("19", new Color(185, 255, 185));
		theMap.addRange("20", new Color(255, 255, 138));

		// initialize JGantt
		// timeScale
		jGantt1.setTimeScaleResolution(35.0); // timeScaleType defaults to
		// "week",
		// so this means 35mm / week
		jGantt1.setTimeScaleStart(today);
		jGantt1.setTimeScaleEnd(today + (ONE_DAY * 2000));

		// display the profile "weekend" in the timescale
		jGantt1.setTimeScaleDisplayProfile("weekend");

		// font and color
		jGantt1.setTimeScaleFont(new java.awt.Font("Dialog", 1, 12));
		jGantt1.setTimeScaleColorScheme(new de.netronic.jgantt.JGColorScheme(
				-8345366, -7623937, -6108968, -4727553, -16777216, -16777216,
				-16777216, -16777216, -16777216));

		// ganttChart
		jGantt1.setGanttColorScheme(new de.netronic.jgantt.JGColorScheme(
				-65536, -2631721, -1, -31, -16777216, -16777216, -16777216,
				-16777216, -16777216));

		// define a monthly line grid and show the profile "weekend" as calendar
		// grid
		jGantt1.setGanttGrid(de.netronic.jgantt.JGantt.GANTT_GRID_MONTHLY);
		jGantt1.setGanttCalendarGrid(true);
		jGantt1.setGanttDisplayProfile("weekend");

		// table
		jGantt1.setTableColorScheme(new de.netronic.jgantt.JGColorScheme(
				-3368449, -4220542, -92, -1, -16777216, -16777216, -16777216,
				-16777216, -16777216));

		// table consist of 2 columns, showing "Name" and "ID"
		jGantt1.setTableColumns(new String[] { "Name", "ID" });
		jGantt1.setTableFont(new java.awt.Font("Dialog", 0, 12));
		jGantt1.setTableColumnWidths(new double[] { 25.0 });

		// don't display tableRowTitles (=rowNumbers)
		jGantt1.setTableRowTitlesVisible(false);

		// Nodes
		// nodes are contained in the entitySet named "nodes" (see createData())
		jGantt1.setNodeSetName("nodes");

		// dates for the bars are contained in the attributes "Start" and "End".
		// we use the nodeDesign "rect" (default), which only needs to dates!
		jGantt1.setNodeDates(new String[] { "Start", "End", "", "" });

		// show a triangle symbol for nodes with zero length(Start == End)
		jGantt1.setNodeZeroLengthVisible(true);
		jGantt1.setNodeZeroLengthSymbol(JGSymbol.getInstance("1"));

		// set the (dynamic, see theMapColor) color for the nodes
		jGantt1.setNodeColorScheme(new JGColorScheme(Color.blue, Color.orange,
				theMapColor, Color.pink));

		// define an annotation for the nodes
		jGantt1.setNodeAnnotations(new String[] { "", "", "Nr" });
		jGantt1.setNodeFont(new java.awt.Font("Dialog", 1, 12));

		// Grouping
		// nodes are grouped by the contents of attribute "ID"
		jGantt1.setGroupBy("ID");

		// All nodes of a group are shown in one row
		jGantt1.setGroupLayout(JGantt.GROUP_ROWLAYOUT_SINGLE_OPTIMIZED);

		// initialize other components
		jLabel_Info.setFont(new Font("Dialog", 1, 12));
		jLabel_Info.setBounds(new Rectangle(13, 6, 71, 10));
		jEditorPane_Info.setBounds(new Rectangle(10, 20, 329, 25));
		jEditorPane_Info
				.setText(" On exceeding a date the line will extend automatically.");
		jEditorPane_Info.setBackground(new Color(212, 208, 200));
		jEditorPane_Info.setEnabled(false);
		jEditorPane_Info.setFont(new Font("Dialog", 0, 12));
		jEditorPane_Info.setBorder(BorderFactory.createEtchedBorder());
		jEditorPane_Info.setDisabledTextColor(Color.black);
		jEditorPane_Info.setSelectedTextColor(Color.black);
		jEditorPane_Info.setSelectionColor(new Color(212, 208, 200));
		jPanel_jGantt.setLayout(borderLayout_jGantt);
		jPanel_jGantt.setBounds(new Rectangle(8, 53, 2048, 1024));
		jButton_Close.setBounds(new Rectangle(356, 22, 85, 24));
		jButton_Close.setText("Close");
		jButton_Close.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		this.add(jEditorPane_Info);
		this.add(jLabel_Info);
		this.add(jPanel_jGantt);
		jPanel_jGantt.add(jGantt1, BorderLayout.CENTER);
		this.add(jButton_Close, null);
	}

	private void createData(JGantt jGantt1) {
		NeIAppData appData = jGantt1.getAppData();

		// create EntitySet for nodes
		NeIEntitySet nodes = appData.createEntitySet("nodes");
		nodes.addEntityAttribute("Name", String.class, "Machine");
		nodes.addEntityAttribute("ID", String.class, "ID");
		nodes.addEntityAttribute("Color", String.class, "Color");
		nodes.addEntityAttribute("Start", java.util.Date.class, "Start");
		nodes.addEntityAttribute("End", java.util.Date.class, "End");
		nodes.addEntityAttribute("Nr", String.class, "Nr");

		// ******************************获得数据******************************
		GanttDataSource ganttDataSource = new GanttDataSource();
		// LinkedList<Squence> Data = ganttDataSource.generateTestData();
		LinkedList<Squence> Data = ganttDataSource.generateDataFromFile(path);

		// 赋值
		NeIEntity template = nodes.getTemplateEntity();
		Iterator<Squence> ItData = Data.iterator();
		int amount = 0;
		while (ItData.hasNext()) {
			amount++;
			Squence item = ItData.next();
			template.setValue("Name", item.MachineNum);
			template.setValue("ID", item.MachineNum);
			template.setValue("Start", new Date(today + item.StartTime
					* ONE_DAY));
			template.setValue("End", new Date(today
					+ (item.StartTime + item.time) * ONE_DAY));
			template.setValue("Color", item.JobNumber);
			template.setValue("Nr", "Job" + item.JobNumber);

			nodes.createEntity("Node" + amount);
		}

	}

	/**
	 * calculate the beginning of the current day
	 */
	private long getToday() {
		long myTimeNow = (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) * 1000 * 60 * 60)
				+ (Calendar.getInstance().get(Calendar.MINUTE) * 1000 * 60)
				+ (Calendar.getInstance().get(Calendar.SECOND) * 1000)
				+ (Calendar.getInstance().get(Calendar.MILLISECOND));
		myTimeNow = new Date().getTime() - myTimeNow;
		return myTimeNow;
	}

	public static void main(String[] args) {
		GroupOptimize groupOptimize1 = new GroupOptimize();
		JFrame frame = new JFrame("NETRONIC Varchart JGantt - Group Optimize");
		frame.getContentPane().add("Center", groupOptimize1);
		frame.setSize(new Dimension(700, 375));

		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation((d.width - frame.getSize().width) / 2,
				(d.height - frame.getSize().height) / 2);
		frame.setVisible(true);
		frame.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				close();
			}
		});
	}

	// Exit Program
	static void close() {
		System.exit(0);
	}

	// Look & Feel
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
