//************************/
//	Author: Douglas Yan
//	Date Created: December 1, 2014
//	Class: CS326
//	Instructor: Dr. Mircea Nicolescu
//*************************/


import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

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
	protected ColorObject[] resetArray;
	protected String[] colors;
	protected int currentColorIndex;
	protected int currentRed = 0;
	protected int currentGreen = 0;
	protected int currentBlue = 0;
	protected Color currentColor;

	public static void main(String argv[]) throws IOException
	{
		new ColorSampler("Color Sampler");
	}

	///////////////////////
	///				    /// 
	///     Methods     ///
	///				 	///
	///////////////////////

	public ColorSampler(String title) throws IOException
	{
		super(title);
		setBounds(100, 100, 375, 325);

		//Instantiate the objects
		drawTest = new ColorWindow();
		redLabel = new ColorLabel("Red:");
		greenLabel = new ColorLabel("Green:");
		blueLabel = new ColorLabel("Blue:");
		saveButton = new JButton("Save");
		resetButton = new JButton("Reset");
		colorList = new JList();
		colors = new String [11];
		colorArray = new ColorObject[11];
		resetArray = new ColorObject[11];
		currentColorIndex = 0;

		//Add listeners
		addWindowListener(new WindowDestroyer());
		saveButton.addActionListener(new ActionHandler());
		resetButton.addActionListener(new ActionHandler());
		colorList.addListSelectionListener(new ListHandler());

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

	/*
		Call readColorsFromFile(); which will load in the objects from the file.
		Call setList(); which will write the names of the colors into an array.
		Finally, set the data inside the colorList data structure.
	*/
	private void setUpList() throws IOException
	{
		readColorsFromFile();
		setList();
		colorList.setListData(colors);
	}

	/*
		Read in from the file.
		After reading in all the data for one color, call the parameterized constructor
			for the ColorObject item.
		The data goes into the colorArray, which is going to be used throughout the program.
		The data also goes into resetArray, which will be used for resetting the values.
	*/
	private void readColorsFromFile() throws IOException
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
			resetArray[counter] = new ColorObject(cName, cRed, cGreen, cBlue);
			counter = counter+1;
		}
		stream.close();
	}

	private void setList()
	{
		for(int i = 0; i < 11; i++)
		{
			colors[i] = colorArray[i].name();
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
		currentColor = new Color(currentRed, currentGreen, currentBlue);
		drawTest.repaint();
	}

	/*
		This method is used to update the values of the red, green, and blue labels
			for the color every time the user clicks something in the list.
		The current values for red, green, and blue are also updated and the color
			panel is also updated.
	*/
	private void updateRGBValues()
	{
		ColorObject c = colorArray[currentColorIndex];
		redLabel.colorTF.setText(String.valueOf(c.red));
		greenLabel.colorTF.setText(String.valueOf(c.green));
		blueLabel.colorTF.setText(String.valueOf(c.blue));
		currentRed = c.red;
		currentGreen = c.green;
		currentBlue = c.blue;
		currentColor = new Color(c.red, c.green, c.blue);
		drawTest.repaint();
	}

	/*
		Using I/O requires that the method throw an IOException, except this method would
			have been called from windowClosing(WindowEvent e);, which cannot throw an
			IOException because it's an overridden method. Therefore, I had to catch an
			IOException instead in the body.
	*/
	private void writeToFile()
	{
		try
		{
			FileOutputStream ostream = new FileOutputStream("colors.txt"); //this makes me nervous
			PrintWriter writer = new PrintWriter(ostream);			//why would we output to the same file?
			String color;
			int red, green, blue;

			for (int i = 0; i < 11; i++) 
			{
				color = colorArray[i].name;
				red = colorArray[i].red;
				green = colorArray[i].green;
				blue = colorArray[i].blue;
				writer.println(color + " " + red + " " + green + " " + blue + "\n");
			}
			writer.flush();
			ostream.close();
		}

		catch (IOException e)
		{
			System.out.println("Error writing to file.");
		}
	}

	///////////////////////
	///				    /// 
	///     Classes     ///
	///				 	///
	///////////////////////

	/*
		This class contains the name of the color component, the text field that
			holds the value of that component, the plus button, and the minus button.
	*/
	private class ColorLabel extends JPanel
	{	
		private JLabel colorName;
		public JTextField colorTF;
		private JButton minusButton;
		private JButton plusButton;

		public ColorLabel(String cName)
		{
			//Create private data members
			colorName = new JLabel(cName);
			colorTF = new JTextField("");
			minusButton = new JButton("-");
			plusButton = new JButton("+");

			//Add listeners
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
			/*
				If a plus button is pressed, it increases the component value.
				If a minus button is pressed, it decreases the componenet value.
				For both buttons, the color is updated and paint panel updated.
			*/
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
					setTitle("Color Sampler*"); //This is kind of lazy. If a plus is pressed
						//when the value is already at 255, nothing is going to be changed,
						//but the title will change anyways. The alternative is to have this 
						//command copied and pasted in 6 different locations.
					updateColor();
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
					setTitle("Color Sampler*");
					updateColor();
				}
			}
		}
	}

	//From notes
	private class ColorWindow extends JComponent
	{
		public void paint(Graphics g)
		{
			Dimension d = getSize();
			g.setColor(currentColor);
			g.fillRect(1, 1, d.width-2, d.height-2);
		}
	}

	/*
		The color object holds the name of the color and the red, green, and blue 
			components that make up the color.
		There are setters and getters written for each data member.
	*/
	private class ColorObject
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

		private void setName(String cName)
		{
			cName = name;
		}

		private void setRed(int cRed)
		{
			red = cRed;
		}

		private void setGreen(int cGreen)
		{
			green = cGreen;
		}

		private void setBlue(int cBlue)
		{
			blue = cBlue;
		}
	}

	/*
		The ActionHandler will detect when the save and reset buttons are pressed.
	*/
	private class ActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == resetButton)
			{
				//Reset all the values of colorArray using resetArray
				for(int i = 0; i < 11; i++)
				{
					colorArray[i].setName(resetArray[i].name);
					colorArray[i].setRed(resetArray[i].red);
					colorArray[i].setGreen(resetArray[i].green);
					colorArray[i].setBlue(resetArray[i].blue);
				}

				//Crude hardcode to reset the value of the color label
				setTitle("Color Sampler");
				currentRed = colorArray[currentColorIndex].red;
				currentGreen = colorArray[currentColorIndex].green;
				currentBlue = colorArray[currentColorIndex].blue;
				redLabel.colorTF.setText(String.valueOf(currentRed));
				greenLabel.colorTF.setText(String.valueOf(currentGreen));
				blueLabel.colorTF.setText(String.valueOf(currentBlue));
				updateColor();
			}

			if(e.getSource() == saveButton )
			{
				//Save the current values into colorArray.
				setTitle("Color Sampler");
				colorArray[currentColorIndex].red = currentRed;
				colorArray[currentColorIndex].green = currentGreen;
				colorArray[currentColorIndex].blue = currentBlue;
			}
		}
	}

	/*
		The ListHandler detects when something in the selection menu is pressed.
	*/
	private class ListHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getSource() == colorList && !e.getValueIsAdjusting())
			{
				currentColorIndex = colorList.getSelectedIndex();
				updateRGBValues();
			}
		}
	}

	/*
		The WindowDestroy class handles the window being closed. First, the method
			to write to the file is called, then the System.exit(0); command is called.
	*/
	private class WindowDestroyer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			writeToFile();
			System.exit(0);
		}
	}
}