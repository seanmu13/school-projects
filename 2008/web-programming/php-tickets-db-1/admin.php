<?php

#---------------------------------------------------------------------------------------------------------------
#	ADMIN PAGE - The admin page allows an admin to login and see tickets to view them, sort them, or select them
#----------------------------------------------------------------------------------------------------------------

# Starts the session and unsets the viewTicket session variable so that you cannot go to the ticket page manually
session_start();
unset($_SESSION['viewTicket']);

# Connects to the database
mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error: </b>" . mysql_error());
mysql_select_db("MyDatabase") or die("<b>Error: </b>" . mysql_error());

# If there are no sesion variables
if( !$_SESSION )
{
	show_start();

	# If a POST hasn't occured - show the admin login page
	if( !$_POST )
	{
		show_login();
		show_end();
	}
	# A POST has occured
	else
	{
		# Get the user name and password
		$user = $_POST['user'];
		$pass = $_POST['pass'];

		# Checks to see if the user exists
		$userExists = checkUser($user);

		# If the user does exist
		if($userExists):

			# Checks to see if the passwords match
			$passMatch = checkPass($user,$pass);

			# If the passwords match
			if($passMatch):

				# If the login is successful, show the normal admin table page
				$result = mysql_query("SELECT id FROM admins WHERE name='$user'") or die("<b>Error: </b>" . mysql_error());
				$row = mysql_fetch_array($result);
				$_SESSION['techID'] = $row['id'];
				show_table_page();

			# If the passwords do NOT match - show the login page
			else:
				echo "<p>$user, your password was incorrect.</p><br />";
				show_login();
				show_end();
			endif;

		# If the user does NOT exist - show the login page
		else:
			echo "<p>User $user does NOT exist</p><br />";
			show_login();
			show_end();
		endif;
	}
}
# If there is a session variable present, show the normal table page
else
{
	show_table_page();
}

# Show the normal table page with the tickets
function show_table_page()
{
	# This is the default query which can be overwritten, also gets the id of admin who is logged in
	$query = "SELECT * FROM tickets WHERE status='open'";
	$techID = $_SESSION['techID'];

	# If showAll is set and is true, we are going to select all tickets - this is here when the user returns from the ticket page
	if( isset($_SESSION['showAll']) ):
		if( $_SESSION['showAll'] ):
			$query = "SELECT * FROM tickets";
			$_SESSION['showUnassigned'] = false;
			$_SESSION['showMine'] = false;
		endif;
	endif;

	# If there was a POST
	if( $_POST )
	{
		# Get the submit value and select value, showAll tickets is false by default
		$submit = $_POST['submit'];
		$select = $_POST['select'];
		$showAll = false;

		# If showAll is set, get the showAll value
		if( isset($_SESSION['showAll']) ):
			$showAll = $_SESSION['showAll'];
		endif;

		# If the user's submit was 'View All Ticket'
		if( $submit == "View All Tickets" )
		{
			# View all of the tickets
			$query = "SELECT * FROM tickets";
			$_SESSION['showUnassigned'] = false;
			$_SESSION['showAll'] = true;
			$_SESSION['showMine'] = false;
		}

		# If the user's submit was a sort
		elseif( $submit == "Sort" )
		{
			# If we show only the open tickets we order the table by the select value, which will be email, name, subject, or date
			if( !$showAll ):
				$query = "SELECT * FROM tickets WHERE status='open' ORDER BY $select";

			# Else, we select all of the tickets ordered by the sort value
			else:
				$query = "SELECT * FROM tickets ORDER BY $select";
			endif;
		}

		# If the user's submit was 'View Open Tickets'
		elseif( $submit == "View Open Tickets")
		{
			# View only open tickets
			$_SESSION['showUnassigned'] = false;
			$_SESSION['showMine'] = false;
			$_SESSION['showAll'] = false;
			$query = "SELECT * FROM tickets WHERE status='open'";
		}

		# If the user's submit was 'View Unassigned Tickets'
		elseif( $submit == "View Unassigned Tickets" )
		{
			# View only the tickets that are unassigned (assign.id=0)
			$_SESSION['showUnassigned'] = true;
			$query = "SELECT * FROM tickets,assign WHERE tickets.ticket=assign.ticket AND assign.id=0";
		}

		# If the user's submit was 'Logout'
		elseif( $submit == "Logout" )
		{
			# Destroy the session and show the admin login page
			session_destroy();
			header('Location: admin.php');
		}

		# If the user's submit was 'View My Tickets'
		elseif( $submit == "View My Tickets")
		{
			# Select the tickets that have the same id as the currently logged in admin
			$_SESSION['showMine'] = true;
			$query = "SELECT * FROM tickets,assign WHERE tickets.ticket=assign.ticket AND assign.id=$techID";
		}

		# If the user's submit was 'View Selected Ticket'
		elseif( $submit == "View Selected Ticket" )
		{
			# Set the viewTicket variable and show the ticket.php page
			$_SESSION['viewTicket'] = $select;
			header('Location: ticket.php');
		}
	}

	# Show the table with the given query
	show_start();
	show_table($query);
	show_end();
}

# Check to see if the user exists
function checkUser($user)
{
	$result = mysql_query("SELECT * FROM admins WHERE name='$user'") or die("<b>Error: </b>" . mysql_error());
	$row = mysql_fetch_array($result);
	return $row != 0;
}

# Checks to see if the passwords match
function checkPass($user,$pass)
{
	# Encrypt the password and check it with the encrypted password in the table
	$passEnc = crypt($pass,"salt");
	$result = mysql_query("SELECT * FROM admins WHERE name='$user' AND pass='$passEnc'") or die("<b>Error: </b>" . mysql_error());
	$row = mysql_fetch_array($result);
	return $row != 0;
}

# Shows the login page
function show_login()
{
	echo "<h1>Admin Login Page</h1>";
	echo "<form name='form' action='admin.php' method='POST' onsubmit = 'return checkForBlanks(this);'>";
	echo "Admin Name: <input type = 'text' name = 'user' /><br />";
	echo "Password: <input type = 'password' name = 'pass' /><br />";
	echo "<input type = 'submit' name = 'submit' value = 'Submit' />";
	echo "<input type = 'reset' name = 'reset' value = 'Reset' />";
	echo "</form>";
}

# Shows the start of the HTML page
function show_start()
{
	echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head></script><link rel = 'stylesheet' type = 'text/css' href = 'style3.css' /><script language = 'JavaScript' src = 'script3.js' ></script><title>Admin</title></head><body><center>";
}

# Shows the table with the tickets
function show_table($query)
{
	echo "<h1>Technical Support Open Tickets</h1><form name='form' action='admin.php' method='POST' onsubmit = 'return checkAdminInput(this);'><table>";
	echo "<tr align = 'center'><th>Ticket #</th><th>Received</th><th>Sender Name</th><th>Sender Email</th><th>Subject</th><th>Tech</th><th>Status</th><th>Select</th></tr>";

	# Gets the result from the query
	$result1 = mysql_query($query) or die("<b>Error: </b>" . mysql_error());

	# Go through each row in the query
	while($row = mysql_fetch_array($result1)):

		# Gets all of the information from the row and adds the data to an array
		$ticket = $row['ticket']; $date = $row['date']; $name = $row['name']; $email = $row['email']; $subject = $row['subject']; $status = $row['status'];
		$tableRow = array($ticket,$date,$name,$email,$subject);

		# Find the name of the admin based on the id
		$result2 = mysql_query("SELECT * FROM admins,assign WHERE assign.ticket=$ticket AND admins.id=assign.id") or die("<b>Error: </b>" . mysql_error());
		$row2 = mysql_fetch_array($result2);
		$techName = $row2['name'];

		# If the techName is 0, then it is unassigned
		if( !$techName ) $techName = "<i>Unassigned</i>";

		# Append the techName and status to the array
		$tableRow[] = "$techName";
		$tableRow[] = "$status";

		# Iterate through the tableRow array and put the data in the row
		echo "<tr>";
		foreach($tableRow as $value):
			echo "<td>$value</td>";
		endforeach;

		# For each row, put the radio button in the last column and put the value as the ticket number
		echo "<td><input type='radio' name='select' value='$ticket'></td></tr>";
	endwhile;

	# For the radio buttons at the bottom of the table
	$sorts = array("date","name","email","subject");

	# Places the radio buttons at the bottom of the table
	echo "<tr><td>&nbsp;</td>";
	foreach($sorts as $value):
		echo "<td>Sort By <input type='radio' name='select' value='$value' checked></td>";
	endforeach;
	echo "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
	echo "</table>";

	# Defines all of the submit buttons underneath the table
	$submits = array("View All Tickets", "Sort", "View Selected Ticket", "View My Tickets", "Logout", "View Unassigned Tickets",);

	# If showAll is true, change the 0th button
	if( isset($_SESSION['showAll']) ):
		if( $_SESSION['showAll'] ):
			$submits[0] = "View Open Tickets";
		endif;
	endif;

	# If showMine is true, change the 3rd button
	if( isset($_SESSION['showMine']) ):
		if( $_SESSION['showMine'] ):
			$submits[3] = "View Open Tickets";
		endif;
	endif;

	# If showUnassigned is true, change the 5th button
	if( isset($_SESSION['showUnassigned']) ):
		if( $_SESSION['showUnassigned'] ):
			$submits[5] = "View Open Tickets";
		endif;
	endif;

	# Foreach of the submit button, place them into the form
	foreach($submits as $value):
		echo "<input type = 'submit' name = 'submit' value = '$value' onClick = 'document.pressed=this.value' >";
		if( strcmp($value,"View Selected Ticket") == 0) echo "<br />";
	endforeach;
	echo "</form>";
}

# Shows the end HTML
function show_end()
{
	echo "<br /><a href = 'user.php' >Click here to go to user page</a></center></body></html>";
}
?>