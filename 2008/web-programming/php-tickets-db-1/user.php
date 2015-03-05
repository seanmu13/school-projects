<?php

#---------------------------------------------------------------------------------------------------------
#	USER PAGE - The user page allows a user to create a ticket with a subject and message
#---------------------------------------------------------------------------------------------------------

session_start();
unset($_SESSION['viewTicket']);

# If there was a POST
if( $_POST )
{
	# Get all of the information from the POST
	$first_name = $_POST['first_name'];
	$last_name = $_POST['last_name'];
	$email = $_POST['email'];
	$subject = $_POST['subject'];
	$description = $_POST['description'];
	$date = date("m/d/Y");

	# Constructs the message that is sent to the user and displayed to the page
	$message = "Name: $first_name $last_name\n";
	$message = $message . "Subject: $subject \n";
	$message = $message . "Description: $description \n";
	$message = $message . "Thank you for your ticket submission.";

	# Replaces all of the \n with break tags to be displayed on the HTML confirmation page
	$page_message = str_replace("\n","<br />",$message);

	# Mails the message to the user
	mail("$email","Ticket Submission Confirmation","$message");

	# Connects to the database and selects MyDatabase
	mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error: </b>" . mysql_error());
	mysql_select_db("MyDatabase") or die("<b>Error: </b>" . mysql_error());

	# Inserts the data into the tickets table
	$query = "INSERT INTO tickets VALUES(0,'$date','$first_name $last_name','$email','$subject','$description','open')" or die("<b>Error: </b>" .  mysql_error());
	mysql_query("$query") or die("<b>Error: </b>" . mysql_error());
	$query = "INSERT INTO assign VALUES(0,DEFAULT)";
	mysql_query("$query") or die("<b>Error: </b>" . mysql_error());

	# Shows the user confirmation page
	show_start();
	show_confirmation($page_message);
	show_end();
}
# There was NO POST - so show information form to submit
else
{
	show_start();
	show_form();
	show_end();
}

# Shows the beginning tags for the HTML pages
function show_start()
{
	echo "<!DOCTYPE html PUBLIC \"-#W3C#DTD XHTML 1.0 Transitional#EN\" \"http:#www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><script language = 'JavaScript' src = 'script3.js' ></script><link rel = 'stylesheet' type = 'text/css' href = 'style3.css' /><title>User</title></head><body><center>";
}

# Shows the form where the user enters information
function show_form()
{
	echo "<h1>Welcome to the User Ticket Page</h1>";
	echo "<h4>Please fill out the form and press the submit ticket button</h4><br />";
	echo "<form action = 'user.php' method = 'post' onsubmit = 'return checkUserInput(this);'>";
	echo "First Name: <input type = 'text' name = 'first_name' /><br /><br />";
	echo "Last Name: <input type = 'text' name = 'last_name' /><br /><br />";
	echo "Email: <input type = 'text' name = 'email' /><br /><br />";
	echo "Subject: <input type = 'text' name = 'subject' /><br /><br />";
	echo "<textarea  rows = '5' cols = '30' name = 'description' >Enter the description of the problem here!</textarea><br /><br />";
	echo "<input type = 'submit' name = 'submit' value = 'Submit Ticket' />";
	echo "<input type = 'reset' name = 'reset' value = 'Reset' />";
	echo "</form>";
	echo "<br /><br /><a href = 'admin.php' >Click here to go to the admin page</a>";
}

# Displays the user confirmation page
function show_confirmation($page_message)
{
	echo "<h1>Ticket Confirmation Page</h1><br />";
	echo "$page_message";
	echo "<br /><br /><a href = 'user.php' >Click here to submit another request</a>";
	echo "<br /><br /><a href = 'admin.php' >Click here to go to the admin page</a>";
}

# Shows the ending tags of the HTML
function show_end()
{
	echo "</center></body></html>";
}

?>