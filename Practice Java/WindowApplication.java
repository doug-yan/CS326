import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.io.*;

public class WindowApplication extends JFrame
{
	protected JButton buttonDone;
	protected JButton buttonCancel;
	protected JTextField tfFirstName;
	protected JTextField tfLastName;
	protected JMenuBar mb;
	protected JMenu m;
	protected JMenuItem mi[];

	public static void main(String argv [])
	{
		new WindowApplication("Doug is Radical");
	}

	public WindowApplication(String title)
	{
		super(title);
		addWindowListener(new WindowDestroyer());

		buttonDone = new JButton("Done");
		buttonCancel = new JButton("Cancel");
		buttonDone.addActionListener(new ActionHandler());
		buttonCancel.addActionListener(new ActionHandler());

		tfFirstName = new JTextField("");
		tfLastName = new JTextField("");

		mb = new JMenuBar();
		m = new JMenu("Action");
		mi = new JMenuItem[2];
		mi[0] = new JMenuItem("Clear");
		mi[0].addActionListener(new ActionHandler());
		mi[1] = new JMenuItem("Exit");
		mi[1].addActionListener(new ActionHandler());
		m.add(mi[0]);
		m.add(new JSeparator());
		m.add(mi[1]);
		mb.add(m);
		setJMenuBar(mb);

		getContentPane().setLayout(new GridLayout(3,2));

		getContentPane().add(new JLabel("First Name:"));
		getContentPane().add(tfFirstName);
		getContentPane().add(new JLabel("Last Name:"));
		getContentPane().add(tfLastName);
		getContentPane().add(buttonDone);
		getContentPane().add(buttonCancel);

		setBounds(100,100, 250, 150);
		setVisible(true);

		tfFirstName.setText("John");
		tfLastName.setText("Doe");
	}

	private class ActionHandler implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(e.getSource() == buttonDone)
			{
				String s1 = tfFirstName.getText();
				String s2 = tfLastName.getText();
				System.out.println("Full name: " + s1 + " " + s2);
			}

			else if(e.getSource() == buttonCancel)
				System.out.println("You pressed the Cancel button.");

			else if(e.getSource() == mi[0])
			{
				tfFirstName.setText("");
				tfLastName.setText("");
			}

			else if(e.getSource() == mi[1])
			{
				System.exit(0);
			}
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