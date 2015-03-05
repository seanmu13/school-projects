import java.util.*;
import java.io.*;
import java.net.*;

public class ScoreKeeper 
{
	// Choose a port outside of the range 1-1024
	public static final int PORT = 8844;

	private int count;
	private Scanner inFile;
	private PrintWriter outFile;
	private BufferedReader input;
	private PrintWriter output;
	private final String fileName = "topScores.txt";
	private ServerSocket s;
	private Socket socket;

	public ScoreKeeper() throws IOException
	{
		s = new ServerSocket(PORT);
		System.out.println("ScoreKeeper started: " + s);

		count = 0;  // keep track of how many times server is used
		try 
		{
			while(true) 
			{
				// Blocks until a connection occurs:
				socket = s.accept();
				try 
				{
					System.out.println("Connection " + (++count) + " accepted "+ socket);
					input =
						new BufferedReader(
						new InputStreamReader(
						socket.getInputStream()));
					output =
						new PrintWriter(
						new BufferedWriter(
						new OutputStreamWriter(socket.getOutputStream())), true);
					int newScore = Integer.parseInt(input.readLine());
					File theFile = new File(fileName);
					String newData;
					if (theFile.exists())
					{	
						boolean won = false;
						ArrayList<String> scores = new ArrayList<String>();
						inFile = new Scanner(theFile);
						while (inFile.hasNextLine() && scores.size() < 10)
						{
							String currLine = inFile.nextLine();
							String [] currData = currLine.split(" ");
							int currScore = Integer.parseInt(currData[1]);
							if (currScore < newScore && !won)
							{
								output.println("WINNER");
								String inits = input.readLine().substring(0,3);
								newData = inits + " " + newScore;
								scores.add(newData);
								won = true;
								if (scores.size() == 10)
									break;
							}
							scores.add(currLine);
						}
						inFile.close();
						if (!won && scores.size() < 10)
						{
							output.println("WINNER");
							String inits = input.readLine().substring(0,3);
							newData = inits + " " + newScore;
							scores.add(newData);
						}
						else if (!won)
							output.println("LOSER");
					
						outFile = new PrintWriter(new FileOutputStream(fileName),true);
						for (String curr : scores)
						{
							output.println(curr);
							outFile.println(curr);
						}
						outFile.close();
						output.println("DONE");
					}
					else
					{
						output.println("WINNER");
						String inits = input.readLine().substring(0,3);
						newData = inits + " " + newScore;
						output.println(newData);
						output.println("DONE");
						outFile = new PrintWriter(new FileOutputStream(fileName),true);
						outFile.println(newData);
						outFile.close();
					}
				}
				catch (Exception e1)
				{ System.out.println("Wacky problem with client" + e1); }
				finally
				{
					System.out.println("Closing connection");
					socket.close();
				}
			}
		}
		catch (Exception e2)
		{ System.out.println("Wacky problem between clients"); 
		  System.out.println("Shutting server down");
		  s.close();
		}
	}
								
	public static void main(String [] args)
	{
		try
		{
			new ScoreKeeper();
		}
		catch (IOException e)
		{
			System.out.println("Could not start server");
		}
	}
}


