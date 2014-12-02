import java.io.*;

public class FileIODemo
{
	public static void main(String argv[]) throws IOException
	{
		FileInputStream stream = new FileInputStream("colors.txt");
		InputStreamReader reader = new InputStreamReader(stream);
		StreamTokenizer tokens = new StreamTokenizer(reader);

		String s;
		int n;
		int f;
		while (tokens.nextToken() != tokens.TT_EOF)
		{
			s = (String) tokens.sval;
			tokens.nextToken();
			n = (int) tokens.nval;
			tokens.nextToken();
			f = (int) tokens.nval;
			tokens.nextToken();

			System.out.println(s + " " + n + " " + f);
		}
		stream.close();
	}
}