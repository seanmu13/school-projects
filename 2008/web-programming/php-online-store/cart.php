<?php/*
This is the cart page and takes care of everything with the shopping cart
*/
	session_start();
	// If the user tries to access the page manually
	if( !$_SESSION['option'] || strcmp($_SESSION['option'],"shopping") != 0)
	{
		echo "Access Denied: Please login via <a href='shop.php'>Shopping Login Page</a>";		
		unset($_SESSION['option']);
		unset($_SESSION['email']);
		unset($_SESSION['pass']);		
		unset($_SESSION['cart']);
		unset($_SESSION['store']);
		session_destroy();	
	}
	// If the view cart button is pressed
	elseif( $_POST['view'] )
	{
		show_start();
		show_table();
		show_view_buttons();
		show_end();
	}
	// If the checkout button is pressed
	elseif( $_POST['checkout'] )
	{
		show_start();
		show_table();
		show_checkout_buttons();
		show_end();	
	}
	// If the purchase button is pressed
	elseif( $_POST['purchase'])
	{
		updateProducts();
		send_message();	
		unset($_SESSION['cart']);
	}
	
	// Updates the products*.txt files with updated values based on the user's purchase
	function updateProducts()
	{
		$cart = $_SESSION['cart'];
		$cookie = $_COOKIE['user'];
		$name = $cookie['name'];
		$totalPrice = getTotalPrice();
		$contents1 = file('products1.txt');
		$contents2 = file('products2.txt');
		
		// Foreach line the products1.txt file
		foreach($contents1 as $key1 => $value1):
				// Foreach value in the cart
				foreach($cart as $key2 => $value2):
					$line = explode(":",$value1);
					// If the id in the text file matches the number of the item ordered, subtract the number of items the user ordered from the value in the text file
					// Update the line that goes in the text file with the new line
					if($line[0] == $key2):
						$line[5] = ($line[5] - $value2);
						$contents1[$key1] = trim(implode(":",$line)) . "\n";
					endif;
				endforeach;
		endforeach;
		
		// Foreach line the products2.txt file
		foreach($contents2 as $key1 => $value1):
			// Foreach value in the cart
			foreach($cart as $key2 => $value2):
				$line = explode(":",$value1);
				// If the id in the text file matches the number of the item ordered, subtract the number of items the user ordered from the value in the text file
				// Update the line that goes in the text file with the new line
				if($line[0] == $key2):
					$line[5] = ( $line[5] - $value2);
					$contents2[$key1] = trim(implode(":",$line)) . "\n";
				endif;
			endforeach;
		endforeach;
		
		// Write back the contents of the two files because the will have updated values if the user purchased something
		$file = fopen("products1.txt","w");		
		foreach($contents1 as $value):
			fwrite($file,"$value");
		endforeach;
		fclose($file);
		
		$file = fopen("products2.txt","w");
		foreach($contents2 as $value):
			fwrite($file,"$value");
		endforeach;
		fclose($file);
	}
	
	// Sends the email confirmation to the user and displays that same message to the screen
	function send_message()
	{
		$cookie = $_COOKIE['user'];
		$email = $cookie['email'];
		$cart = $_SESSION['cart'];
		$name = $cookie['name'];
		$message[] = "Thank you for your purchase, $name.";
		$total = 0;
		
		// Foreach item in the cart, get its price, then get the subtotal and finally the total, write to the string that will be sent in the email
		foreach($cart as $key => $value):
			$itemPrice = getPrice($key);
			$subPrice = $itemPrice * $value;
			$total += $subPrice;
			$message[] = "Item #$key, Price: $value x $itemPrice = $subPrice";
			$quantity = getQuantity($key);
			if( $quantity < 0 ):
				$back = getBackOrderAmt($quantity,$value);
				$message[] = "Notice: $back of Item #$key are on back order.";
			endif;
		endforeach;
		
		// Append the following messages the message array
		$message[] = "Total: $total";
		$message[] = "Thanks again for your support.";
		$message[] = "This confirmation was sent to: $email , if this is not you disregard this email.";
		
		echo "<html><head><title>Your purchased order</title></head><link rel='stylesheet' type='text/css' href='style.css' /><body><center>";
		echo "<h1>Purchase Confirmation for $email</h1>";
		
		// Output the message to the screen
		foreach($message as $value):
			echo "$value <br />";
		endforeach;
		
		// Show the logout button
		echo "<form action='shop.php' method='POST'><input type = 'submit' name = 'logout' value = 'Logout' ></form>";
		$store = $_SESSION['store'];
		
		// Show the go back to previous page button
		if($store == 2):
			echo "<br /><form action='store.php' method='POST'><input type = 'submit' name ='page2' value ='Go To Shopping Page 2'></form>";
		else:
			echo "<form action='store.php' method='POST'><input type = 'submit' name ='page1' value ='Go To Shopping Page 1'></form>";
		endif;		
		echo "</center></body></html>";
		
		// Ends each line the message with a newline character before emailing this message
		$message = implode("\n",$message);
		$message = $message . "\n\n";
		mail("$email","Thank you for your order $name","$message");
		
		$file = fopen("purchases.txt","a");
		fwrite($file,"$message");
		fclose($file);
	}
	
	//Gets the quantity of the item in stock after the purchase is confirmed
	function getQuantity($id)
	{	
		$contents1 = file('products1.txt');
		$contents2 = file('products2.txt');
		
		foreach($contents1 as $key => $value):
			$line = explode(":",$value);
			if($line[0] == $id):
				return $line[5];
			endif;
		endforeach;
		
		foreach($contents2 as $key => $value):
			$line = explode(":",$value);
			if($line[0] == $id):
				return $line[5];
			endif;
		endforeach;
	}
	
	// Gets the amount of items on backorder
	function getBackOrderAmt($q,$o)
	{
		if( abs($q) >= $o):
			return $o;
		elseif( $q < 0):
			return abs($q);
		else:
			return 0;
		endif;
	}
	
	// Gets the total price of the items in the cart
	function getTotalPrice()
	{
		$cart = $_SESSION['cart'];
		$totalPrice = 0;
		foreach($cart as $key => $value):
			$itemPrice = getPrice($key);
			$subPrice = $itemPrice * $value;
			$totalPrice += $subPrice;
		endforeach;
		return $totalPrice;
	}
	
	// Shows the top level HTML elements of the document
	function show_start()
	{
		$cookie = $_COOKIE['user'];
		$name = $cookie['name'];
		if( $_POST['view'] ):
			echo "<html><head><title>View Your Cart</title><link rel='stylesheet' type='text/css' href='style.css' /></head><body><center>";
			echo "<h1>$name's Cart</h1>";
		elseif( $_POST['checkout'] ):
			echo "<html><head><title>Checkout</title><link rel='stylesheet' type='text/css' href='style.css' /></head><body><center>";
			echo "<h1>$name's Checkout</h1>";
		elseif( $_POST['purchase']):
			echo "<html><head><title>Purchase</title><link rel='stylesheet' type='text/css' href='style.css' /></head><body><center>";
			echo "<h1>$name's Purchase</h1>";
		endif;		
	}

	// Shows the table which displays the contents of the cart
	function show_table()
	{	
		// If the cart is empty, tell the user
		if( !$_SESSION['cart'] ):
			echo "<h4>You have 0 items in your cart</h4>";
		// Make the table
		else:
			$cart = $_SESSION['cart'];
			$cookie = $_COOKIE['user'];
			$name = $cookie['name'];
			$totalPrice = getTotalPrice();
			$headers = array("Item Number","Quantity","Item Price","Sub Price");
			$size = sizeof($headers);

			echo "<table border='1' align='center'>";
			echo "<tr><th colspan='$size'>$name's Cart</th></tr>";

			// Make the column names at the top of the table
			echo "<tr>";
			foreach($headers as $value):
				echo "<th align='center'>$value</th>";
			endforeach;
			echo "</tr>";

			// Write the values of the cart to the table
			foreach($cart as $key => $value):
				$itemPrice = getPrice($key);
				$subPrice = $itemPrice * $value;
				echo "<tr> <td>$key</td> <td>$value</td> <td>$itemPrice</td> <td>$subPrice</td> </tr>";
			endforeach;

			echo "<tr> <td> </td> <td> </td> <th>Total Price:</th> <td>$totalPrice</td> </tr>";
			echo "</table><br />";
		endif;
	}

	// Show the view cart button
	function show_view_buttons()
	{
		if( $_SESSION['cart'] ):
			echo "<form action='cart.php' method='POST'><input type = 'submit' name ='checkout' value ='Check Out'></form>";
		endif;
	}
	
	// Show the buttons when in the checkout page
	function show_checkout_buttons()
	{
		if( $_SESSION['cart'] ):
			echo "<form action='cart.php' method='POST'><input type = 'submit' name ='purchase' value ='Purchase Items'></form>";	
		endif;
	}
	
	// Finish off the HTML tags with the go to previous page button
	function show_end()
	{
		$store = $_SESSION['store'];
		if($store == 2):
			echo "<form action='store.php' method='POST'><input type = 'submit' name ='page2' value ='Go To Previous Page'></form>";
		else:
			echo "<form action='store.php' method='POST'><input type = 'submit' name ='page1' value ='Go To Previous Page'></form>";
		endif;
		echo "</center></body></html>";
	}

	// Gets the price of the item $num by looking in either the products1.txt or products2.txt files
	function getPrice($num)
	{
		$file = fopen("products1.txt","r");
		$found = false;
		$price;
		while( !feof($file) && !$found):
			$temp = fgets($file);
			$line = explode(":",$temp);
			if($num == $line[0]):
				$found = true;
				$price = $line[4];
			endif;
		endwhile;
		fclose($file);

		$file = fopen("products2.txt","r");
		while( !feof($file) && !$found):
			$temp = fgets($file);
			$line = explode(":",$temp);
			if($num == $line[0]):
				$found = true;
				$price = $line[4];
			endif;
		endwhile;

		fclose($file);
		return $price;
	}
?>