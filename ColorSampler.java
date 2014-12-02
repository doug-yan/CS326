import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;

/*
	TODO:
	Add functionality to the color labels
	Add functionality to save and reset buttons
*/
public class ColorSampler extends JFrame
{
	protected ColorWindow drawTest;
	protected ColorLabel redLabel;
	protected ColorLabel greenLabel;
	protected ColorLabel blueLabel;
	protected JButton saveButton;
	protected JButton resetButton;
	protected JList colorList;
	protected ColorObject[] colorArray;
	protected String[] colors;
	protected int currentColorIndex;
	protected int currentRed;
	protected int currentGreen;
	protected int currentBlue;
	protected Color currentColor;

	public static void main(String argv[]) throws IOException
	{
		new ColorSampler("Color Sampler");
	}

	public ColorSampler(String title) throws IOException
	{
		super(title);
		setBounds(100, 100, 375, 325);

		drawTest = new ColorWindow();
		redLabel = new ColorLabel("Red:");
		greenLabel = new ColorLabel("Green:");
		blueLabel = new ColorLabel("Blue:");
		saveButton = new JButton("Save");
		resetButton = new JButton("Reset");
		colorList = new JList();
		colorArray = new ColorObject[11];
		colors = new String [11];
		currentColorIndex = 0;

		//TODO: ADD LISTENERS
		addWindowListener(new WindowDestroyer());

		//Set up file i/o and put it in the list
		setUpList();

		//Add items to the window
		addItemsToWindow();

		//Lay out items (manually)
		manualLayout();

		//Load initial data
		currentRed = colorArray[currentColorIndex].red();
		currentGreen = colorArray[currentColorIndex].green();
		currentBlue = colorArray[currentColorIndex].blue();
		redLabel.colorTF.setText( String.valueOf(currentRed));
		greenLabel.colorTF.setText( String.valueOf(currentGreen));
		blueLabel.colorTF.setText( String.valueOf(currentBlue));
		currentColor = new Color(currentRed, currentGreen, currentBlue);

		setVisible(true);
	}

	private void setUpList() throws IOException
	{
		readColorsFromFile(colorArray);
		setList(colorArray, colors);
		colorList.setListData(colors);
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

	private void addItemsToWindow()
	{
		getContentPane().add(drawTest);
		getContentPane().add(redLabel);
		getContentPane().add(greenLabel);
		getContentPane().add(blueLabel);
		getContentPane().add(saveButton);
		getContentPane().add(resetButton);
		getContentPane().add(colorList);
	}

	private void manualLayout()
	{
		getContentPane().setLayout(null);
		drawTest.setBounds(10, 10, 225, 125);
		redLabel.setBounds(10, 145, 225, 30);
		greenLabel.setBounds(10, 180, 225, 30);
		blueLabel.setBounds(10, 215, 225, 30);
		saveButton.setBounds(57, 250, 60, 30);
		resetButton.setBounds(147, 250, 60, 30);
		colorList.setBounds(250, 10, 115, 275);
	}

	private void updateColor()
	{
		currentColor = new Color(currentRed, currentGreen, currentBlue, 1);
	}

	private void updatePaint()
	{
		drawTest = new ColorWindow();
		getContentPane().add(drawTest);
		drawTest.setBounds(10, 10, 225, 125);
		setVisible(true);
	}

	private class ColorLabel extends JPanel
	{	
		private JLabel colorName;
		public JTextField colorTF;
		private JButton minusButton;
		private JButton plusButton;

		public ColorLabel(String cName)
		{
			//create private data members
			colorName = new JLabel(cName);
			colorTF = new JTextField("");
			minusButton = new JButton("-");
			plusButton = new JButton("+");

			plusButton.addActionListener(new ActionHandler());
			minusButton.addActionListener(new ActionHandler());

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

		private class ActionHandler implements ActionListener
		{
			public void actionPerformed(ActionEvent e)
			{
				if(e.getSource() == plusButton)
				{
					if(colorName.getText() == "Red:" && currentRed < 255)
					{
						currentRed += 5;
						colorTF.setText(String.valueOf(currentRed));
					}

					else if(colorName.getText() == "Green:" && currentGreen < 255)
					{
						currentGreen += 5;
						colorTF.setText(String.valueOf(currentGreen));
					}

					else if(colorName.getText() == "Blue:" && currentBlue < 255)
					{
						currentBlue += 5;
						colorTF.setText(String.valueOf(currentBlue));
					}
					System.out.println(currentColor);
					updateColor();
					//drawTest.repaint(10, 10, 225, 125);
					updatePaint();
				}

				if(e.getSource() == minusButton)
				{
					if(colorName.getText() == "Red:" && currentRed > 0)
					{
						currentRed -= 5;
						colorTF.setText(String.valueOf(currentRed));
					}

					else if(colorName.getText() == "Green:" && currentGreen > 0)
					{
						currentGreen -= 5;
						colorTF.setText(String.valueOf(currentGreen));
					}

					else if(colorName.getText() == "Blue:" && currentBlue > 0)
					{
						currentBlue -= 5;
						colorTF.setText(String.valueOf(currentBlue));
					}
					System.out.println(currentColor);
					updateColor();
					updatePaint();
				}
			}
		}
	}


	private class ColorWindow extends JComponent
	{
		public void paint(Graphics g)
		{
			Dimension d = getSize();
			g.setColor(currentColor);
			g.fillRect(1, 1, d.width-2, d.height-2);
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

		private int red()
		{
			return red;
		}

		private int green()
		{
			return green;
		}

		private int blue()
		{
			return blue;
		}
	}

	private class ActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{

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