public class Test
{
	public static void main(String[] args)
	{
		MyBag stuff = new MyDB(6);

		System.out.print("\n");
		System.out.println("Is empty?  " + stuff.isEmpty());
		System.out.println("Size:  " + stuff.size());

		String item;

		item = new String("Steelers"); stuff.addElement(item);
		item = new String("Eagles"); stuff.addElement(item);
		item = new String("Browns"); stuff.addElement(item);
		item = new String("Bengals"); stuff.addElement(item);
		item = new String("Patriots"); stuff.addElement(item);
		item = new String("Ravens"); stuff.addElement(item);

		System.out.println("Is empty?  " + stuff.isEmpty());
		System.out.println("Size:  " + stuff.size());
		System.out.println("Contains Eagles?:  " + stuff.containsElement("Eagles"));
		System.out.println("Find Patriots?:  " + stuff.findElement("Patriots"));

		System.out.println("\n" + stuff);
		((MyDB)stuff).sortTheData();
		System.out.println(stuff);
		((MyDB)stuff).showThisType("MyBag");

	}
}