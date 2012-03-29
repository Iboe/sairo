package de.fhb.sailboat.ufer.prototyp;


import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;

import de.fhb.sailboat.control.Planner;
import de.fhb.sailboat.ufer.prototyp.utility.ConfigMap;
import de.fhb.sailboat.ufer.prototyp.utility.ConfigReader;
import de.fhb.sailboat.ufer.prototyp.utility.ConfigWriter;
import de.fhb.sailboat.worldmodel.WorldModelImpl;


/**
 * This class represents the general user interface for the mission control application.
 * It should be noted that *all* elements native to this class (native as in: declared in it)
 * are positioned not with help of a layout manager, but instead absolutely via constant values.
 * This should enable easy altering of the class in the future, even by persons other than the
 * initial author. 
 * 
 * @author Patrick Rutter
 * 
 */
public class View extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* CONSTANTS */
	final static public int  UPDATE_RATE = 500; // rate in ms at which new values are ordered/ received from controller (and there from the boat)

	final static public String TITLE = "Lorelei"; // title of the application
	final static public String VERSION = "0.69"; // current version of the GUI (for now only subjective gimmick)

	final static public int SCREEN_X = 862; // horizontal size of the window
	final static public int SCREEN_Y = 600; // vertical size of the window
	
	final static public String CONFIG_FILE = "ViewConfig.ini"; // name of the configuration file used for storing settings (created in working directory)

	/* UI-CONSTANTS */

	// default font for the application (style is fixed as plain, this is important for calculating string widths)
	// currently not used anymore.
	/*final static String F_NAME = "Helvetica";
	final static int F_SIZE = 12;*/

	/* Menu:
	 * These solely declare the names of the given entries.
	 * All these constants start with "M_".
	 * Submenu-entries share the name of their superentry with
	 * the addition of "_<Entry name>".
	 */
	final static public String M_FILE = "Datei";
	final static public String M_FILE_CLOSE = "Beenden";

	final static public String M_CONNECTION = "Verbindung";
	final static public String M_CONNECTION_ENABLE = "Aktivieren";
	final static public String M_CONNECTION_DISABLE = "Deaktivieren";
	final static public String M_CONNECTION_SETTINGS = "Einstelllungen";

	final static public String M_MISSION = "Mission";
	final static public String M_MISSION_SEND = "Senden";

	final static public String M_PROTOCOL = "Protokoll";
	
	final static public String M_SMONITOR1 = "Monitor 1";
	final static public String M_SMONITOR2 = "Monitor 2";
	final static public String M_SMONITOR3 = "Monitor 3";
	final static public String M_SMONITOR4 = "Monitor 4";
	final static public String M_SMONITOR_BLANK = "Leer";
	final static public String M_SMONITOR_COMPASS = "Kompass";
	final static public String M_SMONITOR_GPS = "GPS";
	final static public String M_SMONITOR_WIND = "Wind";
	final static public String M_SMONITOR_MISSION = "Mission";

	/* Panels:
	 * All values which can be associated with a certain sensor are grouped
	 * under the same panel to aid usability.
	 * All child elements of a panel calculate their positions relatively to
	 * their parent panel. This means a panel and all its children can easily
	 * moved via its constants.
	 * Panel constants start with "P_".
	 * While creating panels the programmer should aim to first code the panel,
	 * then all its elements, add them to the panel and only then start the next
	 * panel/ other elements to aid overview.
	 */
	final static public int P_SMALLMONITOR1_X = 4;
	final static public int P_SMALLMONITOR1_Y = 4;
	
	final static public int P_SMALLMONITOR2_X = 208;
	final static public int P_SMALLMONITOR2_Y = 4;
	
	final static public int P_SMALLMONITOR3_X = 4;
	final static public int P_SMALLMONITOR3_Y = 128;
	
	final static public int P_SMALLMONITOR4_X = 208;
	final static public int P_SMALLMONITOR4_Y = 128;

	final static public int P_MAP_X = 412;
	final static public int P_MAP_Y = 35;
	final static public int P_MAP_WIDTH = 435;
	final static public int P_MAP_HEIGHT = 505;
	
	final static public int P_MAP_SELECTORS_X = P_MAP_X;
	final static public int P_MAP_SELECTORS_Y = 5;
	final static public int P_MAP_SELECTORS_WIDTH = P_MAP_WIDTH;
	final static public int P_MAP_SELECTORS_HEIGHT = 35;

	final static public int P_CHART_X = 4;
	final static public int P_CHART_Y = 252;
	final static public int P_CHART_WIDTH = 404;
	final static public int P_CHART_HEIGHT = 290;
	final static public String P_CHART_NAME = "Protokoll";

	/* Labels:
	 * All label constants start with "L_".
	 * Normally, two types of labels are used:
	 * Descriptor labels, which are represent the name of a certain value
	 * to be displayed, and value labels which display the value itself.
	 * Because of that only constants for the descruptor label are given,
	 * the location of the value label is calculated in relation to it.
	 */
	final static public int L_OFFSET = 8; // Offset between certain elements (mostly descriptor- and valuelabels)
	final static public int L_DSIZE = 90; // Common width for descriptor labels
	final static public int L_VSIZE = 60; // Common width for value labels (only used while they contain no values)
	final static public int L_LINE = 18; // Common height of a line of text, used as distance between two lines of text (may be removed later)

	// variables
	private boolean debugMode; // if true will emulate updates from boat sensors by random values, for local UI testing
	
	private Planner planner;
	private MapPanel map;
	private Controller controller;

	private JPanel mapArea;
	
	private BoatMonitor smallMonitor1;
	private BoatMonitor smallMonitor2;
	private BoatMonitor smallMonitor3;
	private BoatMonitor smallMonitor4;
	
	//private Coordinate target; // to be used for coordination 

	private View() {
		this.controller = new Controller();
		initUI();
		this.debugMode = false;
		startUpdating();
	}
	
	public View(Planner planner) {
		this();
		this.planner = planner;
		System.out.println("View Constructed");
	}

	/**
	 * Initialize UI-elements
	 */
	private final void initUI() {

		setLayout(null); // disable layout manager

		// create menubar
		JMenuBar menubar = new JMenuBar();

		// Menü->Datei
		JMenu mFile = new JMenu(M_FILE);
		mFile.setMnemonic(KeyEvent.VK_D);

		JMenuItem mFileExit = new JMenuItem(M_FILE_CLOSE);
		mFileExit.setMnemonic(KeyEvent.VK_B);
		mFileExit.setToolTipText(TITLE + " beenden.");

		mFileExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				System.exit(0);
			}

		});

		mFile.add(mFileExit);

		// Menü->Protokoll
		JMenu mProtocol = new JMenu(M_PROTOCOL);
		mProtocol.setMnemonic(KeyEvent.VK_P);

		// Menü->Mission
		//TODO Remove button for exercise from UI
		JMenu mMission = new JMenu(M_MISSION);
		mMission.setMnemonic(KeyEvent.VK_M);
		
		JMenuItem mMissionSend = new JMenuItem(M_MISSION_SEND);
		mMissionSend.setMnemonic(KeyEvent.VK_S);
		mMissionSend.setToolTipText("Koordinaten senden.");

		mMissionSend.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				sendMapMarkers();
			}

		});
		
		mMission.add(mMissionSend);

		// Menü->Verbindung
		JMenu mConnection = new JMenu(M_CONNECTION);
		mConnection.setMnemonic(KeyEvent.VK_V);

		JMenuItem mConnectionEnable = new JMenuItem(M_CONNECTION_ENABLE);
		mConnectionEnable.setMnemonic(KeyEvent.VK_A);
		mConnectionEnable.setToolTipText("Verbindung mit Boot aufbauen.");

		mConnectionEnable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				JOptionPane.showConfirmDialog((Component) event.getSource(),
						"Verbindung zum Boot aufbauen?");
			}

		});

		mConnection.add(mConnectionEnable);

		JMenuItem mConnectionDisable = new JMenuItem(M_CONNECTION_DISABLE);
		mConnectionDisable.setMnemonic(KeyEvent.VK_T);
		mConnectionDisable.setToolTipText("Verbindung mit Boot trennen.");
		mConnectionDisable.setEnabled(false);

		mConnection.add(mConnectionDisable);

		JMenuItem mConnectionSettings = new JMenuItem(M_CONNECTION_SETTINGS);
		mConnectionSettings.setMnemonic(KeyEvent.VK_E);
		mConnectionSettings.setToolTipText("Verbindung einrichten.");

		mConnection.add(mConnectionSettings);
		
		// Menü->Monitor 1
		smallMonitor1 = new BoatMonitor(P_SMALLMONITOR1_X, P_SMALLMONITOR1_Y, controller);
		
		JMenu mSmallMonitor1 = new JMenu(M_SMONITOR1);
		mSmallMonitor1.setMnemonic(KeyEvent.VK_1);
		ButtonGroup mSmallMonitor1Radio = new ButtonGroup();
		
		JRadioButtonMenuItem mSmallMonitor1_Blank = new JRadioButtonMenuItem(M_SMONITOR_BLANK);
		mSmallMonitor1_Blank.setToolTipText("Setzt Monitor 1 auf unbelegt.");
		mSmallMonitor1Radio.add(mSmallMonitor1_Blank);
		mSmallMonitor1_Blank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_BLANK);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor1_Compass = new JRadioButtonMenuItem(M_SMONITOR_COMPASS);
		mSmallMonitor1_Compass.setToolTipText("Setzt Monitor 1 auf Kompassanzeige.");
		mSmallMonitor1Radio.add(mSmallMonitor1_Compass);
		mSmallMonitor1_Compass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_COMPASS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor1_GPS = new JRadioButtonMenuItem(M_SMONITOR_GPS);
		mSmallMonitor1_GPS.setToolTipText("Setzt Monitor 1 auf GPS Anzeige.");
		mSmallMonitor1Radio.add(mSmallMonitor1_GPS);
		mSmallMonitor1_GPS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_GPS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor1_Wind = new JRadioButtonMenuItem(M_SMONITOR_WIND);
		mSmallMonitor1_Wind.setToolTipText("Setzt Monitor 1 auf Windanzeige.");
		mSmallMonitor1Radio.add(mSmallMonitor1_Wind);
		mSmallMonitor1_Wind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_WIND);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor1_Mission = new JRadioButtonMenuItem(M_SMONITOR_MISSION);
		mSmallMonitor1_Mission.setToolTipText("Setzt Monitor 1 auf Missionsanzeige.");
		mSmallMonitor1Radio.add(mSmallMonitor1_Mission);
		mSmallMonitor1_Mission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_MISSION);
			}

		});
		
		mSmallMonitor1.add(mSmallMonitor1_Blank);
		mSmallMonitor1.add(mSmallMonitor1_Compass);
		mSmallMonitor1.add(mSmallMonitor1_GPS);
		mSmallMonitor1.add(mSmallMonitor1_Wind);
		mSmallMonitor1.add(mSmallMonitor1_Mission);
		add(smallMonitor1.getJPanel());		
		
		// Menü->Monitor 2
		smallMonitor2 = new BoatMonitor(P_SMALLMONITOR2_X, P_SMALLMONITOR2_Y, controller);
		
		JMenu mSmallMonitor2 = new JMenu(M_SMONITOR2);
		mSmallMonitor2.setMnemonic(KeyEvent.VK_2);
		ButtonGroup mSmallMonitor2Radio = new ButtonGroup();
		
		JRadioButtonMenuItem mSmallMonitor2_Blank = new JRadioButtonMenuItem(M_SMONITOR_BLANK);
		mSmallMonitor2_Blank.setToolTipText("Setzt Monitor 2 auf unbelegt.");
		mSmallMonitor2Radio.add(mSmallMonitor2_Blank);
		mSmallMonitor2_Blank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_BLANK);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor2_Compass = new JRadioButtonMenuItem(M_SMONITOR_COMPASS);
		mSmallMonitor2_Compass.setToolTipText("Setzt Monitor 2 auf Kompassanzeige.");
		mSmallMonitor2Radio.add(mSmallMonitor2_Compass);
		mSmallMonitor2_Compass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_COMPASS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor2_GPS = new JRadioButtonMenuItem(M_SMONITOR_GPS);
		mSmallMonitor2_GPS.setToolTipText("Setzt Monitor 2 auf GPS Anzeige.");
		mSmallMonitor2Radio.add(mSmallMonitor2_GPS);
		mSmallMonitor2_GPS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_GPS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor2_Wind = new JRadioButtonMenuItem(M_SMONITOR_WIND);
		mSmallMonitor2_Wind.setToolTipText("Setzt Monitor 2 auf Windanzeige.");
		mSmallMonitor2Radio.add(mSmallMonitor2_Wind);
		mSmallMonitor2_Wind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_WIND);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor2_Mission = new JRadioButtonMenuItem(M_SMONITOR_MISSION);
		mSmallMonitor2_Mission.setToolTipText("Setzt Monitor 2 auf Missionsanzeige.");
		mSmallMonitor2Radio.add(mSmallMonitor2_Mission);
		mSmallMonitor2_Mission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_MISSION);
			}

		});
		
		mSmallMonitor2.add(mSmallMonitor2_Blank);
		mSmallMonitor2.add(mSmallMonitor2_Compass);
		mSmallMonitor2.add(mSmallMonitor2_GPS);
		mSmallMonitor2.add(mSmallMonitor2_Wind);
		mSmallMonitor2.add(mSmallMonitor2_Mission);
		add(smallMonitor2.getJPanel());		
		
		// Menü->Monitor 3
		smallMonitor3 = new BoatMonitor(P_SMALLMONITOR3_X, P_SMALLMONITOR3_Y, controller);
		
		JMenu mSmallMonitor3 = new JMenu(M_SMONITOR3);
		mSmallMonitor3.setMnemonic(KeyEvent.VK_3);
		ButtonGroup mSmallMonitor3Radio = new ButtonGroup();
		
		JRadioButtonMenuItem mSmallMonitor3_Blank = new JRadioButtonMenuItem(M_SMONITOR_BLANK);
		mSmallMonitor3_Blank.setToolTipText("Setzt Monitor 3 auf unbelegt.");
		mSmallMonitor3Radio.add(mSmallMonitor3_Blank);
		mSmallMonitor3_Blank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_BLANK);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor3_Compass = new JRadioButtonMenuItem(M_SMONITOR_COMPASS);
		mSmallMonitor3_Compass.setToolTipText("Setzt Monitor 3 auf Kompassanzeige.");
		mSmallMonitor3Radio.add(mSmallMonitor3_Compass);
		mSmallMonitor3_Compass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_COMPASS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor3_GPS = new JRadioButtonMenuItem(M_SMONITOR_GPS);
		mSmallMonitor3_GPS.setToolTipText("Setzt Monitor 3 auf GPS Anzeige.");
		mSmallMonitor3Radio.add(mSmallMonitor3_GPS);
		mSmallMonitor3_GPS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_GPS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor3_Wind = new JRadioButtonMenuItem(M_SMONITOR_WIND);
		mSmallMonitor3_Wind.setToolTipText("Setzt Monitor 3 auf Windanzeige.");
		mSmallMonitor3Radio.add(mSmallMonitor3_Wind);
		mSmallMonitor3_Wind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_WIND);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor3_Mission = new JRadioButtonMenuItem(M_SMONITOR_MISSION);
		mSmallMonitor3_Mission.setToolTipText("Setzt Monitor 3 auf Missionsanzeige.");
		mSmallMonitor3Radio.add(mSmallMonitor3_Mission);
		mSmallMonitor3_Mission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_MISSION);
			}

		});
		
		mSmallMonitor3.add(mSmallMonitor3_Blank);
		mSmallMonitor3.add(mSmallMonitor3_Compass);
		mSmallMonitor3.add(mSmallMonitor3_GPS);
		mSmallMonitor3.add(mSmallMonitor3_Wind);
		mSmallMonitor3.add(mSmallMonitor3_Mission);
		add(smallMonitor3.getJPanel());		
		
		// Menü->Monitor 4
		smallMonitor4 = new BoatMonitor(P_SMALLMONITOR4_X, P_SMALLMONITOR4_Y, controller);
		
		JMenu mSmallMonitor4 = new JMenu(M_SMONITOR4);
		mSmallMonitor4.setMnemonic(KeyEvent.VK_4);
		ButtonGroup mSmallMonitor4Radio = new ButtonGroup();
		
		JRadioButtonMenuItem mSmallMonitor4_Blank = new JRadioButtonMenuItem(M_SMONITOR_BLANK);
		mSmallMonitor4_Blank.setToolTipText("Setzt Monitor 4 auf unbelegt.");
		mSmallMonitor4Radio.add(mSmallMonitor4_Blank);
		mSmallMonitor4_Blank.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_BLANK);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor4_Compass = new JRadioButtonMenuItem(M_SMONITOR_COMPASS);
		mSmallMonitor4_Compass.setToolTipText("Setzt Monitor 4 auf Kompassanzeige.");
		mSmallMonitor4Radio.add(mSmallMonitor4_Compass);
		mSmallMonitor4_Compass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_COMPASS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor4_GPS = new JRadioButtonMenuItem(M_SMONITOR_GPS);
		mSmallMonitor4_GPS.setToolTipText("Setzt Monitor 4 auf GPS Anzeige.");
		mSmallMonitor4Radio.add(mSmallMonitor4_GPS);
		mSmallMonitor4_GPS.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_GPS);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor4_Wind = new JRadioButtonMenuItem(M_SMONITOR_WIND);
		mSmallMonitor4_Wind.setToolTipText("Setzt Monitor 4 auf Windanzeige.");
		mSmallMonitor4Radio.add(mSmallMonitor4_Wind);
		mSmallMonitor4_Wind.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_WIND);
			}

		});
		
		JRadioButtonMenuItem mSmallMonitor4_Mission = new JRadioButtonMenuItem(M_SMONITOR_MISSION);
		mSmallMonitor4_Mission.setToolTipText("Setzt Monitor 4 auf Missionsanzeige.");
		mSmallMonitor4Radio.add(mSmallMonitor4_Mission);
		mSmallMonitor4_Mission.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_MISSION);
			}

		});
		
		mSmallMonitor4.add(mSmallMonitor4_Blank);
		mSmallMonitor4.add(mSmallMonitor4_Compass);
		mSmallMonitor4.add(mSmallMonitor4_GPS);
		mSmallMonitor4.add(mSmallMonitor4_Wind);
		mSmallMonitor4.add(mSmallMonitor4_Mission);
		add(smallMonitor4.getJPanel());

		//add menu-items
		menubar.add(mFile);
		menubar.add(mSmallMonitor1);
		menubar.add(mSmallMonitor2);
		menubar.add(mSmallMonitor3);
		menubar.add(mSmallMonitor4);
		menubar.add(mProtocol);
		menubar.add(mMission);
		menubar.add(mConnection);

		setJMenuBar(menubar);

		setTitle(TITLE + " V" + VERSION);
		setSize(SCREEN_X, SCREEN_Y);

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);

		// create Interface
		
		// load monitor configuration and set the monitors accordingly
		ConfigReader reader = new ConfigReader();
		ConfigMap settings = new ConfigMap();
		if (reader.fileExists(CONFIG_FILE)) {
			settings = reader.readConfigFile(CONFIG_FILE);
			
			int value = settings.getEntryIntegerValue(M_SMONITOR1);
			//System.out.println("Read 1: " + value);
			if (value != -1) {
				setSmallMonitor1(value);
				switch (value) {
					case (BoatMonitor.PERSPECITVE_ID_BLANK) : {
						mSmallMonitor1_Blank.setSelected(true);
						break;
					}
					case (BoatMonitor.PERSPECITVE_ID_COMPASS) : {
						mSmallMonitor1_Compass.setSelected(true);
						break;
					}
					case (BoatMonitor.PERSPECITVE_ID_GPS) : {
						mSmallMonitor1_GPS.setSelected(true);
						break;
					}
					case (BoatMonitor.PERSPECITVE_ID_MISSION) : {
						mSmallMonitor1_Mission.setSelected(true);
						break;
					}
					case (BoatMonitor.PERSPECITVE_ID_WIND) : {
						mSmallMonitor1_Wind.setSelected(true);
						break;
					}
				}
			}
			
			value = settings.getEntryIntegerValue(M_SMONITOR2);
			//System.out.println("Read 2: " + value);
			if (value != -1) {
				setSmallMonitor2(value);
				switch (value) {
				case (BoatMonitor.PERSPECITVE_ID_BLANK) : {
					mSmallMonitor2_Blank.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_COMPASS) : {
					mSmallMonitor2_Compass.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_GPS) : {
					mSmallMonitor2_GPS.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_MISSION) : {
					mSmallMonitor2_Mission.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_WIND) : {
					mSmallMonitor2_Wind.setSelected(true);
					break;
				}
			}
			}
			
			value = settings.getEntryIntegerValue(M_SMONITOR3);
			//System.out.println("Read 3: " + value);
			if (value != -1) {
				setSmallMonitor3(value);
				switch (value) {
				case (BoatMonitor.PERSPECITVE_ID_BLANK) : {
					mSmallMonitor3_Blank.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_COMPASS) : {
					mSmallMonitor3_Compass.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_GPS) : {
					mSmallMonitor3_GPS.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_MISSION) : {
					mSmallMonitor3_Mission.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_WIND) : {
					mSmallMonitor3_Wind.setSelected(true);
					break;
				}
			}
			}
			
			value = settings.getEntryIntegerValue(M_SMONITOR4);
			//System.out.println("Read 4: " + value);
			if (value != -1) {
				setSmallMonitor4(value);
				switch (value) {
				case (BoatMonitor.PERSPECITVE_ID_BLANK) : {
					mSmallMonitor4_Blank.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_COMPASS) : {
					mSmallMonitor4_Compass.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_GPS) : {
					mSmallMonitor4_GPS.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_MISSION) : {
					mSmallMonitor4_Mission.setSelected(true);
					break;
				}
				case (BoatMonitor.PERSPECITVE_ID_WIND) : {
					mSmallMonitor4_Wind.setSelected(true);
					break;
				}
			}
			}
		}
		else {
			// set initial state
			mSmallMonitor1_Blank.setSelected(true);
			setSmallMonitor1(BoatMonitor.PERSPECITVE_ID_BLANK);
			mSmallMonitor2_Blank.setSelected(true);
			setSmallMonitor2(BoatMonitor.PERSPECITVE_ID_BLANK);
			mSmallMonitor3_Blank.setSelected(true);
			setSmallMonitor3(BoatMonitor.PERSPECITVE_ID_BLANK);
			mSmallMonitor4_Blank.setSelected(true);
			setSmallMonitor4(BoatMonitor.PERSPECITVE_ID_BLANK);
		}

		// Map Panel
		map = new MapPanel();
		mapArea = map.mapPanel();
		add(mapArea);

		// chart panel (just temporary)
		JPanel chartArea = new JPanel();
		chartArea
				.setBounds(P_CHART_X, P_CHART_Y, P_CHART_WIDTH, P_CHART_HEIGHT);
		chartArea.setBorder(new javax.swing.border.TitledBorder(P_CHART_NAME));
		chartArea.setLayout(null);

		add(chartArea);
		
		System.out.println("View initialized");
	}
	
	private void sendMapMarkers() {
		System.out.println("Attempting to send markers");
		if (map.getMarkerList().size() > 0) {
			this.controller.setMarkerList(map.getMarkerList());
			this.controller.commitMarkerList(planner);
			System.out.println("Send markers.");
		}
	}

	/**
	 * Updates the values displayed by the various labels used according to the ones received from controller/ model
	 * !!! ONLY FOR GETTING VALUES FROM MODEL, NOT FOR SETTING THESE !!!
	 */
	private void updateView() {
		smallMonitor1.updatePanel();
		smallMonitor2.updatePanel();
		smallMonitor3.updatePanel();
		smallMonitor4.updatePanel();
	}
	
	/**
	 * Creates and starts up a thread which updates the values to be displayed by view from Controller.
	 * See also UPDATE_RATE.
	 */
	private void startUpdating() {
		Runnable updater = new Runnable() { 
			public void run() {
				while(true) {
					
					if (debugMode) {
						controller.updateRandom();
					}
					else {
						controller.updateAll();
						map.followBoat(controller.getGps().getPosition());
					}
					updateView();
					
					try {
						Thread.sleep(UPDATE_RATE);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		new Thread(updater).start();
	}
	
	// Stores the current settings to the configuration file
	private void saveViewConfiguration() {
		// create map of current settings
		ConfigMap settings = new ConfigMap();
		settings.put(M_SMONITOR1, smallMonitor1.getPerspective() + "");
		settings.put(M_SMONITOR2, smallMonitor2.getPerspective() + "");
		settings.put(M_SMONITOR3, smallMonitor3.getPerspective() + "");
		settings.put(M_SMONITOR4, smallMonitor4.getPerspective() + "");
		
		ConfigWriter writer = new ConfigWriter(settings);
		writer.writeConfigFile(CONFIG_FILE);
	}

	// Setter
	private void setSmallMonitor1(int perspectiveID) {
		smallMonitor1.setPerspective(perspectiveID);
		saveViewConfiguration();
	}
	
	private void setSmallMonitor2(int perspectiveID) {
		smallMonitor2.setPerspective(perspectiveID);
		saveViewConfiguration();
	}
	
	private void setSmallMonitor3(int perspectiveID) {
		smallMonitor3.setPerspective(perspectiveID);
		saveViewConfiguration();
	}
	
	private void setSmallMonitor4(int perspectiveID) {
		smallMonitor4.setPerspective(perspectiveID);
		saveViewConfiguration();
	}
	
	// Main für lokales Debuggen der GUI, für Regelbetrieb auskommentieren und debugMode im Constructor false setzen
	/*public static void main(String[] args) {
        System.setProperty("proxyPort","3128");
		System.setProperty("proxyHost","proxy.fh-brandenburg.de");
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				View view = new View();
				view.setVisible(true);
			}
		});
	}*/
}