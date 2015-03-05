<?php
/*
This is the store page that shows the actualy products in table, you can switch back and forth between the 2 pages via the $_SESSION['store'] variable
*/

	session_start();
	// If the user tries to access this page manually, it will deny them
	if( !isset($_SESSION['option']) || strcmp($_SESSION['option'],"shopping") != 0)
	{
		echo "Access Denied: Please login via <a href='shop.php'>Shopping Login Page</a>";
		unset($_SESSION['option']);
		unset($_SESSION['email']);
		unset($_SESSION['pass']);
		unset($_SESSION['cart']);
		unset($_SESSION['store']);
		session_destroy();
	}
	// If the POSTed variable is addToCart, we add items the carth
	else if( $_POST['addToCart'] )
	{
		$cart = $_SESSION['cart'];
		show_start();
		$added = false;
		
		// For each POST which as the key equal to the item number, we increase the value in the cart by the value entered by the user
		foreach($_POST as $key => $value):
			if($key > 0 && $value > 0)
			{
				$added = true;
				$cart[$key] += $value;
				echo "<i><b>$value</b> of Item <b>$key</b> were added to the cart, thank you.</i>";
			}
		endforeach;
		
		if($added):
			// Set the session cart to the updated cart, then show the table of products and buttons
			$_SESSION['cart'] = $cart;
		endif;
		
		show_table();
		show_buttons();
		show_end();
	}
	// Else, we just show the normal page
	else
	{
		// If the cart has not been made, we make it
		if(!$_SESSION['cart']):
			$_SESSION['cart'] = array();
		endif;

		// If the store is not set, we default it to 1
		if(!$_SESSION['store']):
			$_SESSION['store'] = 1;
		endif;

		// If the POSTed variable is page1, we show the 1st page, if not, we shot the 2nd page
		if($_POST['page1']):
			$_SESSION['store'] = 1;
		elseif($_POST['page2']):
			$_SESSION['store'] = 2;
		endif;

		// Show the HTML of the page
		show_start();
		show_table();
		show_buttons();
		show_end();
	}

	// Shows the outer tags of the document
	function show_start()
	{
		$store = $_SESSION['store'];
		echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><title>Store Page $store</title><link rel='stylesheet' type='text/css' href='style.css' /></head><body><center>";
	}
	
	// Displays the table of the products
	function show_table()
	{
		$store = $_SESSION['store'];
		$headers = array("ID","Name","Description","Photo","Price ($)","Quantity","Order Game");
		$size = sizeof($headers);

		echo "<table border='1' align='center'>";
		echo "<tr><th colspan='$size'>Shopping Table $store</th></tr>";
		echo "<tr>";

		// Display the column names
		foreach($headers as $value):
			echo "<th align='center'>$value</th>";
		endforeach;

		echo "</tr>";

		// Read the file to get the products
		$file = fopen("products$store.txt","r");
		while( !feof($file) )
		{
			// Read a line from the file and split it by the :
			$temp = fgets($file);
			$line = explode(":",$temp);
			
			// If the line is not blank, this is just used in case a blank line is read
			if($line[0]):
				// Start the row
				echo "<tr>";
				// For each value in the products, ex. id, name, desc, etc., write it as table data (td)
				foreach($line as $key => $value):
					// When we get to the pictures, show the actualy pictures and no the filename
					if($key == 3):
						echo "<td align='center'><img src='$value' height='60%' width='60%' /></td>";
					// If the quantity is <=0, just display 0 to the client
					elseif($key == 5 && $line[5] <=0 ):
						echo "<td align='center'>0</td>";
					// Else, just display the data
					else:
						echo "<td align='center'>$value</td>";
					endif;
				endforeach;

				// This is the last column which shows the add to cart button, if the quantity is <=0, show out of stock in stead of the button
				if($line[5] <= 0):
					echo "<td align='center'>Sorry, out of stock</td>";
				else:
					echo "<td><form action='store.php' method='POST'><input type = 'submit' name ='addToCart' value ='Add To Cart'>: <input type='text' name='$line[0]' size = '1' maxlength = '1'></form></td>";
				endif;

				echo "</tr>";
			endif;
		}
		echo "</table><br />";
		fclose($file);
	}

	// Display the buttons at the bottom of the page
	function show_buttons()
	{
		echo "<form action='cart.php' method='POST'><input type = 'submit' name = 'view' value = 'View Cart' ><input type = 'submit' name = 'checkout' value = 'Check Out' ></form>";
		echo "<form action='shop.php' method='POST'><input type = 'submit' name = 'logout' value = 'Logout' ></form>";
	}

	// End the html tags and show the go to other page button
	function show_end()
	{
		$store = $_SESSION['store'];
		if($store == 1):
			echo "<form action='store.php' method='POST'><input type = 'submit' name ='page2' value ='Go To Shopping Page 2'></form>";
		else:
			echo "<form action='store.php' method='POST'><input type = 'submit' name ='page1' value ='Go To Shopping Page 1'></form>";
		endif;
		echo "</center></body></html>";
	}
?>