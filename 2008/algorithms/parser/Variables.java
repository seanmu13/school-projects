import java.util.*;

public class Variables
{
    public ArrayList<Store> list;

	// ------------------------------------------------------------
	// All variables will be stored in an arrayList for easy access
	// ------------------------------------------------------------

    public Variables()
    {
        list = new ArrayList<Store>();
    }

	// ----------------------------------------------------------------------------
	// Sets the associated store value with the given name equal to the integer num
	// ----------------------------------------------------------------------------

    public void setStoreValue(String str, int num)
    {
		int index = list.indexOf(str);

		if( index != -1)
		{
			list.get(index).val = num;
			return;
		}

		Store store = new Store(str, num);
        list.add(store);
    }

	// --------------------------------------------------------------------
	// Gets the associated store value that pertains to that certain string
	// --------------------------------------------------------------------

    public int getStoreValue(String str)
    {
		int index = list.indexOf(str);

		if( index != -1)
		{
			return list.get(index).val;
		}

		return 0;
    }

	// --------------------------------------------------------------------------------
	// Acts like the ++ operator in java that increases the value of the variables by 1
	// --------------------------------------------------------------------------------

    public void increasePlusPlus(String str)
    {
        setStoreValue(str, getStoreValue(str) + 1);
    }

	/*
    public String toString()
    {
		String str = new String("");

		for(Store store : list)
		{
			str += store + "\n";
		}

        return str;
    }
    */
}
