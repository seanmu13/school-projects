<?php
	#--------------------------------------------------------------------------------------------------------------------------
	#	LOGIN PAGE - The page where users and admin can login to the site
	#--------------------------------------------------------------------------------------------------------------------------

	# Start the session
	session_start();

	# If the user is already logged in, take them to the user.php page
	if($_SESSION['userLoggedIn'])
	{
		header('Location: user.php');
	}
	# If the admin is already logged in, take them to the admin.php page
	elseif($_SESSION['adminLoggedIn'])
	{
		header('Location: admin.php');
	}
	# If there was a GET from the AJAX, get the user and pass
	elseif($_GET){
		$user =  $_GET['user'];
		$pass =  $_GET['pass'];

		# Connect to the database and try to find the user
		mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error:</b> " . mysql_error());
		mysql_select_db("MyDatabase") or die("<b>Error:</b> " . mysql_error());
		$crypt_pass = crypt($pass,"salt");
		$result =  mysql_query("SELECT * FROM users WHERE name='$user' AND pass='$crypt_pass'") or die("<b>Error:</b> " . mysql_error());
		$row = mysql_fetch_array($result);

		# If the user is not found, show the login again
		if( $row == 0 ){
			echo "<b class='error'>Username or password incorrect</b>";
			show_login();
			exit;
		}

		# If the login is successul
		$_SESSION['onSite'] = 1;
		$_SESSION['user'] = $user;
		$_SESSION['id'] = $row['id'];

		# If the persion is an admin, take them to the admin.php page
		if($row['admin'])
		{
			$_SESSION['adminLoggedIn'] = 1;
			header('Location: admin.php');
		}
		# If the persion is a user, take them to the user.php page
		else
		{
			$_SESSION['userLoggedIn'] = 1;
			header('Location: user.php');
		}
	}
	# If nobody is logged in and there was no GET
	else
	{
		if(!$_SESSION['onSite']) show_start();
		show_login();
		if(!$_SESSION['onSite']) show_end();
	}

	# Show the outer html tags
	function show_start()
	{
		echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head><script language = 'JavaScript' src = 'script.js' ></script><link rel = 'stylesheet' type = 'text/css' href = 'style.css' /><title>Login</title></head><body onload=\"document.getElementById('user').focus()\" ><center id = 'center' >";
	}

	# Shows the form where the user enters information
	function show_login()
	{
		?>
		<h1>Welcome to the Login Page</h1>
		<h4><em>Please enter your login credentials</em></h4><br />
		<form onsubmit = "return submitInfo(this,'login.php');">
			Username: <input type = 'text' name = 'user' id = 'user' ><br />
			Password: <input type = 'password' name = 'pass' ><br /><br />
			<input type = 'submit' name = 'submit' value = 'Login' onclick = "document.pressed='Login'" >
			<input type = 'reset' name = 'reset' value = 'Reset' >
		</form>
		<?php

	}

	# Shows the ending tags of the HTML
	function show_end()
	{
		echo "</center></body></html>";
	}
?>