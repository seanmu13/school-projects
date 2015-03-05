<?php
	#---------------------------------------------------------------------------------------------------------
	#	ADMIN PAGE - The admin page allows a admin to view tickets, sort them, and select them
	#---------------------------------------------------------------------------------------------------------

	# Start the session
	session_start();
	unset($_SESSION['viewTicket']);

	# If you just came from the ticket.php page and delete is set, echo the message in the session variable
	if(isset($_SESSION['delete']))
	{
		echo $_SESSION['delete'];
	}
	unset($_SESSION['delete']);

	# If an admin is not logged in
	if(!$_SESSION['adminLoggedIn'])
	{
		echo "Access Denied";
	}
	else
	{
		mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error: </b>" . mysql_error());
		mysql_select_db("MyDatabase") or die("<b>Error: </b>" . mysql_error());
		show_page();
	}

	# Show the table with all of the ticket information
	function show_page()
	{
		# This is the default query which can be overwritten, also gets the id of admin who is logged in
		$query = "SELECT * FROM tickets WHERE status='open'";
		$id = $_SESSION['id'];

		# If the there is a GET with the submit variable being set
		if($submit = $_GET['submit'])
		{
			if( $submit == "ViewAllTickets" ):
				$query = "SELECT * FROM tickets";
				//$_SESSION['all'] = 1;
			elseif( $submit == "ViewOpenTickets" ):
				$query = "SELECT * FROM tickets WHERE status='open'";
			elseif( $submit == "ViewUnassignedTickets" ):
				$query = "SELECT * FROM tickets,assign WHERE tickets.ticket=assign.ticket AND assign.id=0";
			elseif( $submit == "ViewMyTickets" ):
				$query = "SELECT * FROM tickets,assign WHERE tickets.ticket=assign.ticket AND assign.id=$id";
			elseif( $submit == "Logout"):
				unset($_SESSION['adminLoggedIn']);
				unset($_SESSION['user']);
				unset($_SESSION['id']);
				header('Location: login.php');
				exit;
			endif;
		}

		# If the select value is > 0, this means the admin wants to view a ticket
		if( ($select = $_GET['select']) > 0)
		{

			$_SESSION['viewTicket'] = $select;
			header('Location: ticket.php');
			exit;
		}
		show_table($query);
	}

	# Show the table with all of the tickets
	function show_table($query)
	{
		echo "<h1>Technical Support Tickets</h1><form name = 'form' id = 'form' onsubmit = \"return submitInfo(this,'admin.php');\"><table id = 'table' >";
		echo "<tr align = 'center'><th>Ticket #</th><th>Received</th><th>Sender Name</th><th>Sender Email</th><th>Subject</th><th>Tech</th><th>Status</th><th>Select</th></tr>";

		# Gets the result from the query
		$result1 = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$col = 1;

		# Go through each row in the query
		while($row = mysql_fetch_array($result1)):

			# Gets all of the information from the row and adds the data to an array
			$ticket = $row['ticket']; $date = $row['date']; $name = $row['name']; $email = $row['email']; $subject = $row['subject']; $status = $row['status'];
			$tableRow = array($ticket,$date,$name,$email,$subject);

			# Find the name of the admin based on the id
			$result2 = mysql_query("SELECT * FROM users,assign WHERE assign.ticket=$ticket AND users.id=assign.id") or die("<b>Error: </b>" . mysql_error());
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
			echo "<td><input type='radio' name='select' value='$ticket' onclick=\"return submitInfo(document.getElementById('form'),'admin.php');\"></td></tr>";
		endwhile;

		# For the radio buttons at the bottom of the table
		$sorts = array("date","name","email","subject");

		# Places the radio buttons at the bottom of the table
		echo "<tr><td>&nbsp;</td>";
		foreach($sorts as $value):
			echo "<td>Sort By <input type='radio' name='select' onclick=\"return sortTable('$col');\" ></td>";
			$col++;
			//echo "<td>Sort By <input type='radio' name='select' value='$value' checked></td>";
		endforeach;
		echo "<td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>";
		echo "</table>";

		# Defines all of the submit buttons underneath the table
		$submits = array("ViewAllTickets","ViewOpenTickets", "ViewMyTickets", "Logout", "ViewUnassignedTickets",);

		# Foreach of the submit button, place them into the form
		foreach($submits as $value):
			echo "<input type = 'submit' name = 'submit' value = '$value' onClick = 'document.pressed=this.value' >";
			//if( strcmp($value,"ViewSelectedTicket") == 0) echo "<br />";
		endforeach;
		echo "</form>";
	}
?>