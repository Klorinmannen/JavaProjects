package Sorting;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

public class SortingGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	
	private JTextField randTextOne;
	private JTextField randTextTwo;
	private JTextField elementsText;
	private Container contentPane;
	private JList<String> midList;
	private DefaultListModel<String> dm;
	
	private JCheckBox reverseBox;
	private JCheckBox standardBox;
	
	private Quick<Integer> quickSort;
	private Merge<Integer> mergeSort;
	private Heap<Integer> heapSort;
	private Selection<Integer> selectionSort;
	private Insertion<Integer> insertionSort;
	
	private XYSeries series;
	private JFreeChart chart;
	private JScrollPane scrollPane;
	private XYSeriesCollection seriesData;
	
	private long timeTaken = 0;
	private DateFormat dateFormat;
	private Date date;
	private PrintWriter writer;
	
	private final int START_VALUE = 10000;
	private final int INCREMENT = 10000;
	private final int SORT_CAPACITY = 1000000;
	private final int NICE_INCREMENT = 1000;
	private final int NICE_CAPACITY = 10000;
	private final int QUADRATIC_INCREMENT = 10000;
	private final int QUADRATIC_CAPACITY = 100000;
	
	private final double DIVIDER = 1000000000.0;
	
	private final int X_START_TEXT = 50;
	private final int Y_START_TEXT = 80;
	private final int X_WIDTH_TEXT = 150;
	private final int Y_HEIGHT_TEXT = 30;
	private final int X_PANEL = 300;
	private final int Y_PANEL = 500;
	private final int BUTTON_WIDHT = 105;
	private final int BUTTON_HEIGHT = 30;
	private final int BUTTON_START_Y = Y_START_TEXT + 150;
	
	private final int STANDARD_TEST = 00;
	private final int REVERSED_TEST = 11;
	private final int USER_DEFINED_TEST = 22;
	
	public SortingGUI() throws HeadlessException
	{
		super("SortingGui");
		this.initiateVar();
		this.configureFrame();
		this.setVisible(true);
	}

	private void configureFrame()
	{
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(500, 300);
		this.setSize(900, 500);
		this.contentPane.setLayout(new GridLayout(1, 2));
		this.buildLeftSide();
		this.buildMidSide();
	}
	
	private void buildLeftSide()
	{
		
		JPanel panel = new JPanel();
		panel.setBorder(BorderFactory.createTitledBorder(""));
		panel.setSize(X_PANEL, Y_PANEL);
		panel.setLayout(null);
		
		addLabels(panel);
		addTextframes(panel);
		this.addButton(panel);
		this.contentPane.add(panel);
	}
	
	private void buildMidSide()
	{
		this.midList.setBorder(BorderFactory.createTitledBorder("Results"));
		this.scrollPane.setViewportView(midList);
		this.contentPane.add(scrollPane);
	}

	private void addLabels(JPanel panel)
	{
		JLabel label = new JLabel("Lower Bounds");
		label.setSize(100, 50);
		label.setLocation(X_START_TEXT, Y_START_TEXT - 35);
		panel.add(label);
		
		label = new JLabel("Sort Test Program");
		label.setSize(150, 50);
		label.setLocation(150, 0);
		panel.add(label);
		
		label = new JLabel("Upper Bounds");
		label.setSize(100, 50);
		label.setLocation(X_START_TEXT + X_WIDTH_TEXT + 30, Y_START_TEXT - 35);
		panel.add(label);
		
		label = new JLabel("Elements");
		label.setSize(100, 50);
		label.setLocation(X_START_TEXT, Y_START_TEXT + 25);
		panel.add(label);
		
		label = new JLabel("Quicksort");
		label.setSize(100, 50);
		label.setLocation(X_START_TEXT + 25, BUTTON_START_Y - 35);
		panel.add(label);
	}
	
	private void addTextframes(JPanel panel)
	{
		this.randTextOne.setSize(X_WIDTH_TEXT, Y_HEIGHT_TEXT);
		this.randTextOne.setLocation(X_START_TEXT, Y_START_TEXT);
		this.randTextTwo.setSize(X_WIDTH_TEXT, Y_HEIGHT_TEXT);
		this.randTextTwo.setLocation(X_START_TEXT + X_WIDTH_TEXT + 30, Y_START_TEXT);
		this.elementsText.setSize(X_WIDTH_TEXT * 2 + 30, Y_HEIGHT_TEXT);
		this.elementsText.setLocation(X_START_TEXT, Y_START_TEXT + 60);
		panel.add(randTextOne);
		panel.add(randTextTwo);
		panel.add(elementsText);
	}
	
	private void addButton(JPanel panel)
	{
		JButton myButton;
		buttonListener myListener = new buttonListener();
		reverseBox = new JCheckBox("Reverse Order");
		reverseBox.setLocation(X_START_TEXT, Y_START_TEXT * 2 + 10);
		reverseBox.setSize(130, 25);
		reverseBox.setVisible(true);
		reverseBox.addActionListener(myListener);
		reverseBox.setToolTipText("Generates an array in descending order with set ammount of elements");
		panel.add(reverseBox);	
		standardBox = new JCheckBox("Standard Test");
		standardBox.setLocation(X_START_TEXT + 140, Y_START_TEXT * 2 + 10);
		standardBox.setSize(130, 25);
		standardBox.setVisible(true);
		standardBox.addActionListener(myListener);
		standardBox.setToolTipText("Generates test arrays starting at 10 000 elements up to 300 000. Elements range 0 - 10 000. Selecetionsort and Insertionsort generates arrays from 10 000 to 100 000");
		panel.add(standardBox);
		
		myButton = new JButton("Good Pivot");
		myButton.setSize(BUTTON_WIDHT, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT, BUTTON_START_Y);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs a quicksort using ninthers median");
		panel.add(myButton);
		myButton = new JButton("Bad Pivot");
		myButton.setSize(BUTTON_WIDHT, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT, BUTTON_START_Y + BUTTON_HEIGHT + 10);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs quicksort with a bad pivot value. Crashes if elements exceeds 10000");
		panel.add(myButton);
		myButton = new JButton("Merge sort");
		myButton.setSize(BUTTON_WIDHT * 2, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT + BUTTON_WIDHT + 10, BUTTON_START_Y);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs mergesort");
		panel.add(myButton);
		myButton = new JButton("Heap sort");
		myButton.setSize(BUTTON_WIDHT * 2, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT + BUTTON_WIDHT + 10, BUTTON_START_Y + BUTTON_HEIGHT + 10);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs heapsort");
		panel.add(myButton);
		myButton = new JButton("Selection sort");
		myButton.setSize(BUTTON_WIDHT * 2, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT + BUTTON_WIDHT + 10, BUTTON_START_Y + BUTTON_HEIGHT * 2 + 20);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs selectionsort");
		panel.add(myButton);
		myButton = new JButton("Insertion sort");
		myButton.setSize(BUTTON_WIDHT * 2, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT + BUTTON_WIDHT + 10, BUTTON_START_Y + BUTTON_HEIGHT * 3 + 30);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Performs insertionsort");
		panel.add(myButton);	
		myButton = new JButton("Chart");
		myButton.setSize(BUTTON_WIDHT, BUTTON_HEIGHT * 2 + 10);
		myButton.setLocation(X_START_TEXT, BUTTON_START_Y + BUTTON_HEIGHT * 2 + 20);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Creates a chart from the most recent performed sorts");
		panel.add(myButton);	
		myButton = new JButton("Clear list");
		myButton.setSize(BUTTON_WIDHT * 3 + 10, BUTTON_HEIGHT);
		myButton.setLocation(X_START_TEXT, BUTTON_START_Y + BUTTON_HEIGHT * 4 + 40);
		myButton.addActionListener(myListener);
		myButton.setToolTipText("Clears the result list");
		panel.add(myButton);
	}
	
	private void initiateVar()
	{
		this.seriesData = new XYSeriesCollection();
		this.randTextOne = new JTextField();
		this.randTextTwo = new JTextField();
		this.elementsText = new JTextField();
		this.contentPane = this.getContentPane();
		this.midList = new JList<>();
		this.dm = new DefaultListModel<>();
		this.quickSort = new Quick<Integer>();
		this.mergeSort = new Merge<Integer>();
		this.heapSort = new Heap<Integer>();
		this.selectionSort = new Selection<Integer>();
		this.insertionSort = new Insertion<Integer>();
		this.midList.setModel(dm);
		this.scrollPane = new JScrollPane();
		this.dateFormat = new SimpleDateFormat("yyyyMMdd");
		this.date = new Date();
	}
			
	private void fillSeriesData(String name)
	{
		Double myDouble = new Double(this.elementsText.getText());
		double lenght = myDouble.doubleValue();	
		
		double calc = (double)timeTaken / this.DIVIDER;
		BigDecimal bd = new BigDecimal(calc).setScale(5, RoundingMode.DOWN);
		calc = bd.doubleValue();
		
		this.series = new XYSeries(name);
		this.series.add(0, 0);
		this.series.add(lenght, calc);
		this.seriesData.addSeries(series);
	}
	
	private void doStandardTest(String name, String testType, int flag, int caseNr)
	{
		try
		{
			this.writer = new PrintWriter(STANDARD_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
		}
		catch (FileNotFoundException e)
		{
			this.dm.addElement("Failed write to file");
		}
		
		this.series = new XYSeries(testType + " " + name);
		switch (caseNr)
		{
		case 0:
			for (int i = START_VALUE; i <= SORT_CAPACITY; i += INCREMENT)
			{
				timeTaken = quickSort.Sort(STANDARD_TEST(i), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + i + " Tid: " + this.calc());
				this.series.add(i, this.calc());
				this.writer.println(i);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;

		case 1:
			for (int i = START_VALUE; i <= SORT_CAPACITY; i += INCREMENT)
			{
				timeTaken = mergeSort.Sort(STANDARD_TEST(i), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + i + " Tid: " + this.calc());
				this.series.add(i, this.calc());
				this.writer.println(i);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		case 2:
			for (int i = START_VALUE; i <= SORT_CAPACITY; i += INCREMENT)
			{
				timeTaken = heapSort.Sort(STANDARD_TEST(i), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + i + " Tid: " + this.calc());
				this.series.add(i, this.calc());
				this.writer.println(i);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		case 3:
			for (int i = QUADRATIC_INCREMENT; i <= QUADRATIC_CAPACITY; i += QUADRATIC_INCREMENT)
			{
				timeTaken = selectionSort.Sort(STANDARD_TEST(i), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + i + " Tid: " + this.calc());
				this.series.add(i, this.calc());
				this.writer.println(i);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		case 4:
			for (int i = QUADRATIC_INCREMENT; i <= QUADRATIC_CAPACITY; i += QUADRATIC_INCREMENT)
			{
				timeTaken = insertionSort.Sort(STANDARD_TEST(i), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + i + " Tid: " + this.calc());
				this.series.add(i, this.calc());
				this.writer.println(i);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		}
		seriesData.addSeries(series);
	}

	private void doReversedTest(String name, String testType, int flag, int caseNr)
	{
		
		try
		{
			this.writer = new PrintWriter(REVERSED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
		}
		catch (FileNotFoundException e)
		{
			this.dm.addElement("Failed write to file");
		}
		
		int n = 0;
		this.series = new XYSeries(name + " " + testType);
		
		switch (caseNr)
		{
		case 0:
			switch (flag)
			{
			case 0:
				while (n < NICE_CAPACITY)
				{
					n += NICE_INCREMENT;
					timeTaken = this.quickSort.Sort(reverseOrder(n), flag);
					this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
					this.series.add(n, this.calc());
					this.writer.println(n);
					this.writer.println(this.calc());
				}
				this.writer.close();
				break;

			case 1:
				while (n < SORT_CAPACITY)
				{
					n += INCREMENT;
					timeTaken = this.quickSort.Sort(reverseOrder(n), flag);
					this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
					this.series.add(n, this.calc());
					this.writer.println(n);
					this.writer.println(this.calc());
				}
				this.writer.close();
				break;
			}
			break;

		case 1:
			while (n < SORT_CAPACITY)
			{
				n += INCREMENT;
				timeTaken = this.mergeSort.Sort(reverseOrder(n), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
				this.series.add(n, this.calc());
				this.writer.println(n);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		case 2:
			while (n < SORT_CAPACITY)
			{
				n += INCREMENT;
				timeTaken = this.heapSort.Sort(reverseOrder(n), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
				this.series.add(n, this.calc());
				this.writer.println(n);
				this.writer.println(this.calc());
			}
			this.writer.close();
			break;
		case 3:
			while (n < QUADRATIC_CAPACITY)
			{
				n += QUADRATIC_INCREMENT;
				timeTaken = this.selectionSort.Sort(reverseOrder(n), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
				this.series.add(n, this.calc());
				this.writer.println(n);
				this.writer.println(this.calc());
			
			}
			this.writer.close();
			break;
		case 4:
			while (n < QUADRATIC_CAPACITY)
			{
				n += QUADRATIC_INCREMENT;
				timeTaken = this.insertionSort.Sort(reverseOrder(n), flag);
				this.dm.addElement(name + " " + testType + ". Antal element: " + n + " Tid: " + this.calc());
				this.series.add(n, this.calc());
				this.writer.println(n);
				this.writer.println(this.calc());
		
			}
			this.writer.close();
			break;
		}
		this.seriesData.addSeries(series);
	}
	
	private Integer[] reverseOrder(int elements)
	{
		Integer[] arr = new Integer[elements];
		int n = 0;
		for (int i = elements - 1; i >= 0 ; i--)
		{
			arr[n++] = Integer.valueOf(i);
		}
		return arr;	
	}

	private Integer[] STANDARD_TEST(int elements)
	{
		Integer[] arr = new Integer[elements];
		int n = 0;
		int max = 10001;
		for (int i = 0; i < elements; i++)
		{
			n = (int)(Math.random() * max);
			arr[i] = Integer.valueOf(n);
		}
		return arr;		
	}

	private class buttonListener implements ActionListener
	{

		@Override
		public void actionPerformed(ActionEvent e)
		{
			String buttonCommand = e.getActionCommand();
			int buttonFlag = -1;
			
			if (standardBox.isSelected() || reverseBox.isSelected())
			{
				randTextOne.setEnabled(false);
				randTextTwo.setEnabled(false);
				elementsText.setEnabled(false);
			} 
			if(!standardBox.isSelected() && !reverseBox.isSelected())
			{
				randTextOne.setEnabled(true);
				randTextTwo.setEnabled(true);
				elementsText.setEnabled(true);
				buttonFlag	= -1;
			}
			if (standardBox.isSelected())
			{
				reverseBox.setEnabled(false);
				buttonFlag = 0;
			}
			else
			{
				reverseBox.setEnabled(true);
			}
			if(reverseBox.isSelected())
			{
				standardBox.setEnabled(false);
				buttonFlag = 1;
			}
			else
			{
				standardBox.setEnabled(true);
			}
			
			
			switch (buttonCommand)
			{
			case "Good Pivot":
				quickFunc(1, buttonFlag);
				break;
			case "Bad Pivot":
				quickFunc(0, buttonFlag);
				break;
			case "Merge sort":
				mergeFunc(buttonFlag);
				break;
			case "Heap sort":
				heapFunc(buttonFlag);
				break;
			case "Selection sort":
				selectionFunc(buttonFlag);
				break;
			case "Insertion sort":
				insertionFunc(buttonFlag);
				break;
			case "Chart":
				doChart();
				break;
			case "Clear list":
				clearList();
				break;
			}			
		}	
	}
		
	private Integer[] randomizer()
	{		
		Integer myElements = new Integer(elementsText.getText());
		Integer myMax = new Integer(randTextOne.getText());
		Integer myMin = new Integer(randTextTwo.getText());
		int max = myMax.intValue();
		int min = myMin.intValue();
		int elements = myElements.intValue();
		
		Integer[] arr = new Integer[elements];
		int n = 0;
		for (int i = 0; i < elements; i++)
		{
			n = (int)(Math.random() * ((max - min) + 1) + min);
			arr[i] = Integer.valueOf(n);
		}
		
		return arr;	
	}
	
	private void quickFunc(int flag, int buttonFlag)
	{
		
		if (buttonFlag == 1)
		{
			//reverseOrder	
			if (flag == 1)
			{
				//good pivot
				this.doReversedTest("Quicksort", "Reversed order GP", flag, 0);
			}
			else if(flag == 0)
			{
				//bad pivot
				this.doReversedTest("Quicksort", "Reversed order BP", flag, 0);
			}
		}
		else if (buttonFlag == 0)
		{
			//STANDARD_TEST
			if (flag == 1)
			{
				//good pivot
				this.doStandardTest("Quicksort", "Standard Test GP", flag, 0);
			}
			else if(flag == 0)
			{
				//bad pivot
				this.doStandardTest("Quicksort", "Standard Test BP", flag, 0);
			}
		}
		else if(flag == 1)
		{
			//good pivot
			//userdefinedtest
			
			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				try
				{
					this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
				}
				catch (FileNotFoundException e)
				{
					this.dm.addElement("Failed write to file");
				}
				
				timeTaken = quickSort.Sort(randomizer(), flag);
				this.dm.addElement("Bra pivot - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
				this.fillSeriesData("User defined quicksort GP");
				this.writer.println(this.elementsText.getText());
				this.writer.println(this.calc());
				this.writer.close();
			}

		}
		else if(flag == 0)
		{
			//bad pivot
			//userdefinedtest

			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				if (elementsText.getText().compareTo("10000") > 0)
				{
					JOptionPane.showMessageDialog(null, "Invalid input");
				}
				else
				{
					
					try
					{
						this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
					}
					catch (FileNotFoundException e)
					{
						this.dm.addElement("Failed write to file");
					}
					
					timeTaken = quickSort.Sort(randomizer(), flag);
					this.dm.addElement("Dåligt pivot - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
					this.fillSeriesData("User defined quicksort BP");
					this.writer.println(this.elementsText.getText());
					this.writer.println(this.calc());
					this.writer.close();
				}
			}
		}
	
	}
			
	private void mergeFunc(int buttonFlag)
	{
		
		if (buttonFlag == 1)
		{
			//reverseorder
			this.doReversedTest("Mergesort", "Reversed order", buttonFlag, 1);
		}
		else if(buttonFlag == 0)
		{
			//STANDARD_TEST
			this.doStandardTest("Mergesort", "Standard Test", buttonFlag, 1);
		}
		else
		{
			//userdefinedTest

			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				
				try
				{
					this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
				}
				catch (FileNotFoundException e)
				{
					this.dm.addElement("Failed write to file");
				}
				
				timeTaken = mergeSort.Sort(randomizer(), buttonFlag);
				this.dm.addElement("Mergesort - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
				this.fillSeriesData("User Defined mergesort");
				this.writer.println(this.elementsText.getText());
				this.writer.println(this.calc());
				this.writer.close();
			}
		}		
	}
		
	private void heapFunc(int buttonFlag)
	{	
		if (buttonFlag == 1)
		{
			//reverseorder
			this.doReversedTest("Heapsort", "Reversed order", buttonFlag, 2);
		}
		else if(buttonFlag == 0)
		{
			//STANDARD_TEST
			this.doStandardTest("Heapsort", "Standard Test", buttonFlag, 2);
		}
		else
		{
			//userdefinedTest

			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				
				try
				{
					this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
				}
				catch (FileNotFoundException e)
				{
					this.dm.addElement("Failed write to file");
				}
				
				timeTaken = heapSort.Sort(randomizer(), buttonFlag);
				this.dm.addElement("Heapsort - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
				this.fillSeriesData("User defined heapsort");
				this.writer.println(this.elementsText.getText());
				this.writer.println(this.calc());
				this.writer.close();
			}
		}	
	}
			
	private void selectionFunc(int buttonFlag)
	{
		if (buttonFlag == 1)
		{
			//reverseorder
			this.doReversedTest("Selectionsort", "Reversed order", buttonFlag, 3);
		}
		else if(buttonFlag == 0)
		{
			//STANDARD_TEST
			this.doStandardTest("Selectionsort", "Standard Test", buttonFlag, 3);
		}
		else
		{
			//userdefinedTest

			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				
				try
				{
					this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
				}
				catch (FileNotFoundException e)
				{
					this.dm.addElement("Failed write to file");
				}
				
				timeTaken = selectionSort.Sort(randomizer(), buttonFlag);
				this.dm.addElement("Selectionsort - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
				this.fillSeriesData("User defined selectionsort");
				this.writer.println(this.elementsText.getText());
				this.writer.println(this.calc());
				this.writer.close();
			}
		}		
	}
		
	private void insertionFunc(int buttonFlag)
	{
		if (buttonFlag == 1)
		{
			//reverseorder
			this.doReversedTest("Insertionsort", "Reversed order", buttonFlag, 4);
		}
		else if(buttonFlag == 0)
		{
			//STANDARD_TEST
			this.doStandardTest("Insertionsort", "Standard Test", buttonFlag, 4);
		}
		else
		{
			//userdefinedTest

			if (randTextOne.getText().isEmpty() || randTextTwo.getText().isEmpty() || elementsText.getText().isEmpty())
			{
				JOptionPane.showMessageDialog(null, "Invalid input");
			} 
			else
			{
				
				try
				{
					this.writer = new PrintWriter(USER_DEFINED_TEST + dateFormat.format(date) + System.nanoTime() + ".txt");
				}
				catch (FileNotFoundException e)
				{
					this.dm.addElement("Failed write to file");
				}
				
				timeTaken = insertionSort.Sort(randomizer(), buttonFlag);
				this.dm.addElement("Insertionsort - user defined test. Antal element: " + this.elementsText.getText() + " Tid: " + this.calc());
				this.fillSeriesData("User defined insertionsort");
				this.writer.println(this.elementsText.getText());
				this.writer.println(this.calc());
				this.writer.close();
			}
		}
	}
		
	private void doChart()
	{
		WindowEvent myEvent = new WindowEvent();
		chart = ChartFactory.createXYLineChart("Graph", "Elements", "Time", seriesData);	
		ChartFrame frame = new ChartFrame("Line Chart", chart);
		frame.addWindowListener(myEvent);
		frame.setSize(900, 500);
		frame.setVisible(true);
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setVisible(true);
	}
	
	public class WindowEvent extends JFrame implements WindowListener
	{

		@Override
		public void windowActivated(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowClosed(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			clearChart();
			
		}

		@Override
		public void windowClosing(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeactivated(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowDeiconified(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowIconified(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}

		@Override
		public void windowOpened(java.awt.event.WindowEvent e)
		{
			// TODO Auto-generated method stub
			
		}
		
	}
		
	private void clearChart()
	{
		this.seriesData.removeAllSeries();
	}

	public void clearList()
{
	this.dm.clear();
}

	private double calc()
	{
		double calc = (double)timeTaken / this.DIVIDER;
		BigDecimal bd = new BigDecimal(calc).setScale(5, RoundingMode.DOWN);
		calc = bd.doubleValue();
		
		return calc;
	}
	
}
