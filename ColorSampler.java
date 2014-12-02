import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/*
	TODO:
	Read in to information from the file
	Add functionality to the color labels
	Add functionality to save and reset buttons
*/

class ColorWindow extends JComponent
{
	public void paint(Graphics g)
	{
		Dimension d = getSize();

		//TODO: Set color correctinly
		g.setColor(Color.yellow);
		g.fillRect(1, 1, d.width-2, d.height-2);
	}
}

class ColorLabel extends JPanel
{
	private JLabel colorName;
	private JTextField colorTF;
	private JButton minusButton;
	private JButton plusButton;

	public ColorLabel(String cName)
	{
		//create private data members
		colorName = new JLabel(cName);
		colorTF = new JTextField("");
		minusButton = new JButton("-");
		plusButton = new JButton("+");

		LayoutItems();
		setVisible(true);
	}

	private void LayoutItems()
	{
		setLayout(null);
		add(colorName);
		add(colorTF);
		add(minusButton);
		add(plusButton);

		colorName.setBounds(0, 0, 45, 30);
		colorTF.setBounds(47, 0, 60, 30);
		minusButton.setBounds(109, 0, 60, 30);
		plusButton.setBounds(169, 0, 60, 30);
	}

	//TODO: ADD LISTENERS
}

public class ColorSampler extends JFrame
{
	protected ColorWindow drawTest;
	protected ColorLabel redLabel;
	protected ColorLabel greenLabel;
	protected ColorLabel blueLabel;
	protected JButton saveButton;
	protected JButton resetButton;
	protected JList colorList;

	public static void main(String argv[]) throws IOException
	{
		new ColorSampler("Color Sampler");
	}

	public ColorSampler(String title) throws IOException
	{
		super(title);
		setBounds(100, 100, 375, 325);
		addWindowListener(new WindowDestroyer());

		drawTest = new ColorWindow();
		redLabel = new ColorLabel("Red:");
		greenLabel = new ColorLabel("Green:");
		blueLabel = new ColorLabel("Blue:");
		saveButton = new JButton("Save");
		resetButton = new JButton("Reset");
		ColorObject[] colorArray = new ColorObject[11];
		String colors[] = new String [11];
		colorList = new JList();

		//TODO: ADD LISTENERS

		//Set up file i/o and put it in the list
		readColorsFromFile(colorArray);
		setList(colorArray, colors);
		colorList.setListData(colors);

		//Add items to the window
		getContentPane().add(drawTest);
		getContentPane().add(redLabel);
		getContentPane().add(greenLabel);
		getContentPane().add(blueLabel);
		getContentPane().add(saveButton);
		getContentPane().add(resetButton);
		getContentPane().add(colorList);

		//Lay out items (manually)
		getContentPane().setLayout(null);
		drawTest.setBounds(10, 10, 225, 125);
		redLabel.setBounds(10, 145, 225, 30);
		greenLabel.setBounds(10, 180, 225, 30);
		blueLabel.setBounds(10, 215, 225, 30);
		saveButton.setBounds(57, 250, 60, 30);
		resetButton.setBounds(147, 250, 60, 30);
		colorList.setBounds(250, 10, 115, 275);

		setVisible(true);
	}

	private void readColorsFromFile(ColorObject[] colorArray) throws IOException
	{
		FileInputStream  stream = new FileInputStream("colors.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		StreamTokenizer tokens = new StreamTokenizer(reader);

		String cName;
		int cRed, cGreen, cBlue;
		int counter = 0;

		while(tokens.nextToken() != tokens.TT_EOF)
		{
			cName = (String) tokens.sval;
			tokens.nextToken();
			cRed = (int) tokens.nval;
			tokens.nextToken();
			cGreen = (int) tokens.nval;
			tokens.nextToken();
			cBlue = (int) tokens.nval;
			colorArray[counter] = new ColorObject(cName, cRed, cGreen, cBlue);
			counter = counter+1;
		}
		stream.close();
	}

	private void setList(ColorObject[] srcArray, String[] destArray)
	{
		for(int i = 0; i < 11; i++)
		{
			destArray[i] = srcArray[i].name();
		}
	}

	public class ColorObject
	{
		private String name;
		private int red;
		private int green;
		private int blue;


		public ColorObject(String cName, int cRed, int cGreen, int cBlue)
		{
			name = cName;
			red = cRed;
			green = cGreen;
			blue = cBlue;	
		}

		private String name()
		{
			return name;
		}
	}

	private class WindowDestroyer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
}