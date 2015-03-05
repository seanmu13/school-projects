<?php
	#--------------------------------------------------------------------------------------------------------------------------
	#	TICKET PAGE - The ticket page shows information on a single ticket, or multiple tickets if certain submits are pressed
	#--------------------------------------------------------------------------------------------------------------------------

	# Start the session
	session_start();

	# If the viewTicket is not set, just show the admin login page
	if( !isset($_SESSION['viewTicket']) )
	{
		echo "Access Denied";
	}

	# If the view POST is set, set the current ticket to the select value
	if( $_GET['view'] )
	{
		$_SESSION['viewTicket'] = $_GET['select'];
	}

	# Connect to the database
	mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error: </b>" . mysql_error());
	mysql_select_db("MyDatabase") or die("<b>Error: </b>" . mysql_error());

	# Get the ticketID and techID
	$ticketID = $_SESSION['viewTicket'];
	$techID = $_SESSION['id'];

	# If the admin closes the ticket
	if( $_GET['close'] )
	{
		# Get the id to see if the admin can close the ticket
		$query = "SELECT id FROM assign WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$id = $row['id'];

		# If the id in the table matches the current admin's id
		if( $id == $techID ):

			# Update the ticket to closed
			echo "<br /><b>Message:</b> The ticket is now closed or is already closed.<br /><br />";
			$query = "UPDATE tickets SET status='closed' WHERE ticket=$ticketID";
			$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());

			# Get the email address to email the user about the closed ticket
			$query = "SELECT email FROM tickets WHERE ticket=$ticketID";
			$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
			$row = mysql_fetch_array($result);
			$email = $row['email'];
			mail("$email","Ticket $ticketID Closed Confirmation","Ticket $ticketID is now closed.  Thank you.");

		# If the ids do NOT match
		else:
			echo "<br /><b>Message:</b> The ticket is NOT assigned to you and cannot be closed.<br /><br />";
		endif;
	}

	# If the admin assigns self to the ticket
	elseif( $_GET['self'] )
	{
		# Gets the id to see if there is already somebody assigned
		$query = "SELECT id FROM assign WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$id = $row['id'];

		# If the ticket is unassigned (id=0)
		if( $id == 0):

			# Update the ticket to be assigned to the current admin
			echo "<br /><b>Message:</b> The ticket is now assigned to you.<br /><br />";
			$query = "UPDATE assign SET id=$techID WHERE ticket=$ticketID";
			$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());

		# If the ticket is already assigned to someone else
		else:
			echo "<br /><b>Message:</b> The ticket is already assigned to a tech.<br /><br />";
		endif;
	}

	# If the admin wants to remove self from ticket
	elseif( $_GET['remove'] )
	{
		# Gets the id to see if there is already somebody assigned
		$query = "SELECT id FROM assign WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$id = $row['id'];

		# Gets the status of the current ticket
		$query = "SELECT status FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$status = $row['status'];

		# If the ID matches and is open
		if( $id == $techID && $status == "open" ):

			# Update the table so that it is unassigned
			echo "<br /><b>Message:</b> You are now removed from this ticket.<br /><br />";
			$query = "UPDATE assign SET id=0 WHERE ticket=$ticketID";
			$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		else:
			echo "<br /><b>Message:</b> The ticket is unassigned, assigned to another tech, or is closed.<br /><br />";
		endif;
	}

	# If the admin wants to query the user
	elseif( $_GET['query'] )
	{
		# Gets the email for the given ticket
		$query = "SELECT email FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$email = $row['email'];

		# Shows the ticket and a box to place a message that will be emailed
		show_ticket();
		show_email_form($email);
		exit;
	}

	# If the POST is email
	elseif( $_GET['email'] )
	{
		# Email the message to the email of the current ticket
		$query = "SELECT email FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$email = $row['email'];
		$message = $_GET['message'];
		echo "<br /><b>Message:</b> Your email was sent to $email .<br /><br />";
		mail("$email","Question About Your Ticket","$message");
	}

	# If the admin wants to delete a ticket
	elseif( $_GET['delete'] )
	{
		# Delete the ticket from table where the ticketID matches
		$query = "DELETE FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$query = "DELETE FROM assign WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$_SESSION['delete'] = "<br /><b>Message:</b> Ticket $ticketID is now deleted.<br />";
		header('Location: admin.php');
		exit;
	}
	elseif( $_GET['leave'] )
	{
		header('Location: admin.php');
		exit;
	}
	# If the admin wants to see tickets with the same email
	elseif( $_GET['same'] )
	{
		# Email the message to the email of the current ticket
		$query = "SELECT * FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$email = $row['email'];

		# Select tickets where the email matches
		$query = "SELECT * FROM tickets WHERE email='$email'";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		echo "<h1>Tickets for $email</h1>";

		show_table($result);
		exit;
	}

	# If the admin wants to see tickets that have similar subjects
	elseif( $_GET['all'] )
	{
		# Gets the subject of the current ticket
		$query = "SELECT subject FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);
		$subject = $row['subject'];

		# Splits the subject on non-word characters
		$split_subject = preg_split("/[\W]+/",$subject);

		# Form the pattern by using each word in the subject, and you can have 1 or more ocurrences for any of the words
		$reg = "";
		foreach($split_subject as $key => $value):
			if($key > 0) $reg = $reg . "|($value)+";
			else $reg = $reg . "($value)+";
		endforeach;

		# Select the results whose subject matches the regular expression
		$query = "SELECT * FROM tickets WHERE subject REGEXP '$reg'";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		echo "<h1>Tickets with similiar subject to '$subject'</h1>";

		# Shows the table with the given result
		show_table($result);
		exit;
	}

	show_ticket();

	# Shows the table with the given result
	function show_table($result)
	{
		echo "<form id = 'form'  ><table><tr align = 'center'><th>Ticket #</th><th>Received</th><th>Sender Name</th><th>Sender Email</th><th>Subject</th><th>Description</th><th>Tech</th><th>Status</th><th>Select</th></tr>";

		# Iterate through each row
		while($row = mysql_fetch_array($result)):

			# Get the date information
			$ticket = $row['ticket']; $date = $row['date']; $name = $row['name']; $email = $row['email']; $subject = $row['subject']; $desc = $row['description']; $status = $row['status'];
			$tableRow = array($ticket,$date,$name,$email,$subject,$desc);

			# Gets the name of the tech
			$result2 = mysql_query("SELECT * FROM users,assign WHERE assign.ticket=$ticket AND users.id=assign.id") or die("<b>Error: </b>" . mysql_error());
			$row2 = mysql_fetch_array($result2);
			$techName = $row2['name'];

			# If there is no tech
			if( !$techName ) $techName = "<i>Unassigned</i>";

			$tableRow[] = "$techName";
			$tableRow[] = "$status";

			# Put the data in the table
			echo "<tr>";
			foreach($tableRow as $value):
				echo "<td>$value</td>";
			endforeach;

			# Add radio buttons to each row
			echo "<td><input type='radio' name='select' value='$ticket' onclick=\"return submitInfo(document.getElementById('form'),'ticket.php');\" ></td></tr>";
		endwhile;
		echo "</table></form>";
	}

	# Shows the table with just the one ticket
	function show_ticket()
	{
		# Gets the result that matches the ticket
		global $ticketID, $techID;
		$query = "SELECT * FROM tickets WHERE ticket=$ticketID";
		$result = mysql_query($query) or die("<b>Error: </b>" . mysql_error());
		$row = mysql_fetch_array($result);

		# Get the information
		$ticket = $row['ticket']; $date = $row['date']; $name = $row['name']; $email = $row['email']; $subject = $row['subject']; $desc = $row['description']; $status = $row['status'];
		$tableRow = array($ticket,$date,$name,$email,$subject,$desc);

		# Gets the name of the tech
		$result2 = mysql_query("SELECT * FROM users,assign WHERE assign.ticket=$ticketID AND users.id=assign.id") or die("<b>Error: </b>" . mysql_error());
		$row2 = mysql_fetch_array($result2);
		$techName = $row2['name'];

		# If there is no tech
		if( !$techName ) $techName = "<i>Unassigned</i>";

		$tableRow[] = "$techName";
		$tableRow[] = "$status";

		echo "<table><tr align = 'center'><th>Ticket #</th><th>Received</th><th>Sender Name</th><th>Sender Email</th><th>Subject</th><th>Description</th><th>Tech</th><th>Status</th></tr>";

		# Put the data in the table
		echo "<tr>";
		foreach($tableRow as $value):
			echo "<td>$value</td>";
		endforeach;
		echo "</tr>";

		# Show the buttons for the ticket
		echo "</table><br /><form id = 'form2' onsubmit=\"return submitInfo(document.getElementById('form2'),'ticket.php');\" ><input type = 'submit' name = 'leave' value = 'GoBackToAdminPage' onclick='document.pressed=this.value' ><input type = 'submit' name = 'close' value = 'CloseTicket' onclick='document.pressed=this.value' ><input type = 'submit' name = 'self' value = 'AssignSelfToTicket' onclick='document.pressed=this.value' ><input type = 'submit' name = 'remove' value = 'RemoveSelfFromTicket' onclick='document.pressed=this.value' ><input type = 'submit' name = 'query' value = 'QueryTheSubmitter' onclick='document.pressed=this.value' ><br /><input type = 'submit' name = 'delete' value = 'DeleteTheTicket' onclick='document.pressed=this.value' ><input type = 'submit' name = 'same' value = 'FindAllTicketsFromSameSubmitter' onclick='document.pressed=this.value' ><input type = 'submit' name = 'all' value = 'FindAllSimilarTickets' onclick='document.pressed=this.value' ></form>";
	}

	# Show the email form to query the user
	function show_email_form($email)
	{
		echo "<br /><form id = 'form3' onsubmit=\"return submitInfo(document.getElementById('form3'),'ticket.php');\" ><textarea name = 'message' cols=40 rows=6>Enter your message here...</textarea><br /><input type = 'submit' name = 'email' value = 'Email:$email' onclick='document.pressed=this.value' ><input type = 'reset' name = 'reset' value = 'Reset' ></form>";
	}
?>