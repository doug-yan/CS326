import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.event.*;

public class SelectionList extends JFrame
{
	protected JList listMovies;

	public static void main(String argv[])
	{
		new SelectionList("Window Application");
	}

	public SelectionList(String title)
	{
		super(title);
		setBounds(100, 100, 200, 100);
		addWindowListener(new WindowDestroyer());

		listMovies = new JList();
		listMovies.addListSelectionListener(new ListHandler());

		getContentPane().setLayout(new GridLayout(1,1));
		//getContentPane().add(listMovies); //no scroll
		getContentPane().add(new JScrollPane(listMovies));
		setVisible(true);

		String movies[] = {"12 Angry Men", "Apocalypse Now", 
							"Cape Fear", "Casablanca", "Fargo", "Solaris"};
		listMovies.setListData(movies);
	}

	private class WindowDestroyer extends WindowAdapter
	{
		public void windowClosing(WindowEvent e)
		{
			System.exit(0);
		}
	}

	private class ListHandler implements ListSelectionListener
	{
		public void valueChanged(ListSelectionEvent e)
		{
			if(e.getSource() == listMovies)
			{
				if(!e.getValueIsAdjusting())
				{
					int i = listMovies.getSelectedIndex();
					String s = (String) listMovies.getSelectedValue();
					System.out.println("Position " + i + " selected: " + s);
				}
			}
		}
	}
}