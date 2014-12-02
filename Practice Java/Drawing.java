import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

class DrawingTester extends JComponent
{
	public void paint(Graphics g)
	{
		Dimension d = getSize();

		g.setColor(Color.yellow);
		g.fillRect(1, 1, d.width-2, d.height-2);

		g.setColor(Color.black);
		g.drawRect(1, 1, d.width-2, d.height-2);
		g.drawLine(1, d.height-1, d.width-1, 1);
		g.drawOval(d.width/2 - 30, d.height/2 - 30, 60, 60);
	}
}

public class Drawing extends JFrame
{
	protected DrawingTester drawTest;
	protected JLabel labelX;
	protected JLabel labelY;
	protected JTextField tfX;
	protected JTextField tfY;

	public static void main(String argv[])
	{
		new Drawing("Window Application");
	}

	public Drawing(String title)
	{
		super(title);
		setBounds(100, 100, 400, 350);
		addWindowListener(new WindowDestroyer());

		drawTest = new DrawingTester();
		labelX = new JLabel("X");
		labelY = new JLabel("Y");
		tfX = new JTextField("");
		tfY = new JTextField("");

		getContentPane().setLayout(null);

		getContentPane().add(drawTest);
		getContentPane().add(labelX);
		getContentPane().add(labelY);
		getContentPane().add(tfX);
		getContentPane().add(tfY);

		drawTest.setBounds(10, 10, 225, 125);
		labelX.setBounds(40, 220, 20, 30);
		tfX.setBounds(60, 220, 50, 30);
		labelY.setBounds(180, 220, 20, 30);
		tfY.setBounds(200, 220, 50, 30);

		setVisible(true);
	}

	private class WindowDestroyer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}
}