import java.util.regex.*;
import java.util.*;

public class regexTesting
{
	public static void main(String[] args)
	{
		String theDate;

		ArrayList<String> months = new ArrayList<String>();
		months.add("jan");
		months.add("feb");
		months.add("mar");
		months.add("apr");
		months.add("may");
		months.add("jun");
		months.add("jul");
		months.add("aug");
		months.add("sep");
		months.add("oct");
		months.add("nov");
		months.add("dec");
		months.add("dasdfsdfec");
		months.add("dsadfsdfec");
		months.add("dasdfsdfec");
		months.add("1");
		months.add(" ");
		months.add("de");


		for(String s: months)
		{
			theDate = "01-" + s + "-00";
			System.out.println(theDate+": " + Pattern.matches("(0[1-9]|1[0-9]|2[0-9]|3[01])[-](jan|feb|mar|apr|may|jun|jul|aug|sep|oct|nov|dec)[-]\\d\\d",theDate));
		}
	}
}

