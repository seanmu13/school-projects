public class Test2
{
	public static void main(String[] args)
	{
		MyBag stuff = new MyDB(10);

		Recording R = new CD("Cake",1,0);
		((Audio)R).setArtist("Art");

		System.out.println("Recording1:\n"+R);

		stuff.addElement(R);
		stuff.addElement("Steelers");

		System.out.println("In the Bag:\n" + stuff);

		Recording P = new DVD("Cake",0,0);
		((Video)P).setDirector("Art");
		stuff.addElement(P);

		System.out.println("Recording2:\n"+P);

		System.out.println(stuff.containsElement(P) + "\n");
		System.out.println(stuff.findElement(P) + "\n");
	}


}