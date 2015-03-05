<?php
	#---------------------------------------------------------------------------------------------------------
	#	USER PAGE - The user page allows a user to create a ticket with a subject and message
	#---------------------------------------------------------------------------------------------------------

	# Start the session
	session_start();

	# If there was a GET
	if($_GET)
	{
		# Get all of the information
		$name = $_SESSION['user'];
		$email = $_GET['email'];
		$subject = $_GET['subject'];
		$description = $_GET['description'];
		$date = date("Y/m/d");

		# If logout, unset some session vars and logout
		if($_GET['logout'])
		{
			unset($_SESSION['userLoggedIn']);
			unset($_SESSION['user']);
			unset($_SESSION['id']);
			header('Location: login.php');
			exit;
		}
		# If the user wants to view his or her tickets
		elseif($_GET['view'])
		{
			show_table();
		}
		# If the user submits a new ticket
		elseif($_GET['submit'])
		{
			echo "<p><em>Your ticket has been submitted</em></p>";
			echo "<p><em>A confirmation has been sent to $email</em></p>";

			# Constructs the message that is sent to the user and displayed to the page
			$message = "Name: $name\n";
			$message = $message . "Subject: $subject \n";
			$message = $message . "Description: $description \n";
			$message = $message . "Thank you for your ticket submission.";
			mail("$email","Ticket Submission Confirmation","$message");

			# Connects to the database and selects MyDatabase
			mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error: </b>" . mysql_error());
			mysql_select_db("MyDatabase") or die("<b>Error: </b>" . mysql_error());

			# Inserts the data into the tickets table
			$query = "INSERT INTO tickets VALUES(0,'$date','$name','$email','$subject','$description','open')" or die("<b>Error: </b>" .  mysql_error());
			mysql_query("$query") or die("<b>Error: </b>" . mysql_error());
			$query = "INSERT INTO assign VALUES(0,DEFAULT)";
			mysql_query("$query") or die("<b>Error: </b>" . mysql_error());
		}
		show_form();
	}
	# If the user is already logged in
	elseif($_SESSION['userLoggedIn'])
	{
		show_form();
	}
	# If the admin is already logged in
	elseif($_SESSION['adminLoggedIn'])
	{
		header('Location: admin.php');
		exit;
	}
	else
	{
		echo "Access Denied";
	}

	# Show the outer HTML tags
	function show_start(){
		echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><script language = 'JavaScript' src = 'script.js' ></script><link rel = 'stylesheet' type = 'text/css' href = 'style.css' /><title>User</title></head><body><center id = 'center' >";
	}

	# Display the table with the users tickets
	function show_table()
	{
		$headers = array('ticket','date','name','email','subject','description','status');

		echo "<h2><em>Your tickets:</em></h2>";
		echo "<table>";

		foreach($headers as $value):
			echo "<th>$value</th>";
		endforeach;

		# Find the user in the database
		$name = $_SESSION['user'];
		mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error:</b> " . mysql_error());
		mysql_select_db("MyDatabase") or die("<b>Error:</b> " . mysql_error());
		$result =  mysql_query("SELECT * FROM tickets WHERE name='$name'") or die("<b>Error:</b> " . mysql_error());

		# Display all of the tickets from the user
		while($row = mysql_fetch_array($result)):
			# Gets all of the information from the row and adds the data to an array
			$ticket = $row['ticket']; $date = $row['date']; $name = $row['name']; $email = $row['email']; $subject = $row['subject']; $description = $row['description']; $status = $row['status'];
			$tableRow = array($ticket,$date,$name,$email,$subject,$description,$status);
			echo "<tr>";
			foreach($tableRow as $value):
				echo "<td>$value</td>";
			endforeach;
			echo "</tr>";
		endwhile;

		echo "</table>";
		echo "<br />";
	}

	# Shows the form where the user enters information
	function show_form(){
		?>
		<h1>Welcome to the User Ticket Page</h1>
		<h4><em>Please fill out the form and press the submit ticket button</em></h4><br />
		<form onsubmit = "return submitInfo(this,'user.php');">
			Email: <input type = 'text' name = 'email' ><br /><br />
			Subject: <input type = 'text' name = 'subject' ><br /><br />
			<textarea  rows = '5' cols = '30' name = 'description' >Enter the description of the problem here!</textarea><br /><br />
			<input type = 'submit' name = 'submit' value = 'SubmitTicket' onclick = "document.pressed='SubmitTicket'" >
			<input type = 'submit' name = 'view' value = 'SeeYourTickets' onclick = "document.pressed='SeeYourTickets'" >
			<input type = 'submit' name = 'logout' value = 'Logout' onclick = "document.pressed='Logout'" >
			<input type = 'reset' name = 'reset' value = 'Reset' >
		</form>
		<?php
	}

	# Shows the ending tags of the HTML
	function show_end(){
		echo "</center></body></html>";
	}
?>