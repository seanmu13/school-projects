<?php
	session_start();
	$userCookie = $_COOKIE['user'];
?>

<html>
<head>
	<title>Title</title>
</head>
<body>

<?php
	if( $userCookie )
	{
		$emailAddress = $_SESSION['email'] = strtok($userCookie,":");
		$userName = $_SESSION['name'] = strtok(":");		
		
		echo "<center><h1>Welcome back $userName with email $emailAddress</h1></center>";
	}
	elseif( !$_POST['tEmail'] && !$_POST['tPass'] && !$_POST['email'] )
	{
		getLoginInfo();
	}
	elseif( $_POST['tEmail'] && $_POST['tPass'] && !$_POST['email'] )
	{
		$email = $_SESSION['email'] = $_POST['tEmail'];
		$password = $_POST['tPass'];
		
		if( !file_exists('users.txt') )
		{
			echo "Users.txt not found<br />";	
			getUserInfo();
		}
		else
		{
			echo "Found users.txt<br />";	
			$file = fopen('users.txt','r');
			$found = false;
			
			while( !feof($file) )
			{
				$line = fgets($file);				
				if( strstr($line,$email) )
				{
					$found = true;
					echo "Email found<br />";
					$match = checkPass($line,$password);
					
					if( $match ):
						$_SESSION['showIntitalCart'] = true;
						echo "Passwords do match <br />";
					else:
						$_SESSION['showIntitalCart'] = false;
						echo "Passwords do NOT match <br />";
					endif;							
				}
			}
			
			if( !$found )
			{
				echo "<br />New User";
				getUserInfo();			
			}
			
			fclose($file);
		}
	}
	elseif( $_POST['email'] )
	{
		$email = $_SESSION['email']; 
		$name = $_SESSION['name'] = $_POST['name'];
		setUserCookie();
		$file = fopen('users.txt','a');
		fwrite($file,"$line");
		fclose($file);
	}
	
	print_r($_SESSION);
	print_r($_POST);
	print_r($_COOKIE);
	
	function setUserCookie()
	{
		setcookie("login", "$_SESSION['email']:$_SESSION['name']", time()+600);	
	}
	
	function getLoginInfo()
	{
		echo "<center>";
		echo "<h1>Login Page</h1>";
		echo "<form action = 'login.php' method = 'POST'>";
		echo "<b>Please enter you email address:</b>";
		echo "<input type = 'text' name = 'tEmail' size = '30' maxlength = '30'>";
		echo "<br /><br />";
		echo "<b>Please enter you password:</b>";
		echo "<input type = 'password' name = 'tPass' size = '30' maxlength = '30'>";
		echo "<br /><br />";
		echo "<input type = 'submit' value = 'Submit'>";
		echo "</form>";
		echo "</center>";
	}

	function checkPass($line, $password)
	{
		$token = strtok($line,":");
		$token = strtok(":");	
		return $token == $password;		
	}

	function getUserInfo()
	{
		echo "<center>";
		echo "<h1>User Information Page</h1>";
		echo "<form action = 'login.php' method = 'POST'>";
		
		echo "<b>Please enter you name:</b>";
		echo "<input type = 'text' name = 'name' size = '30' maxlength = '30'>";
		echo "<br /><br />";
		
		echo "<b>Please enter you address:</b>";
		echo "<input type = 'text' name = 'address' size = '50' maxlength = '50'>";
		echo "<br /><br />";
		
		echo "<b>Please enter you phone:</b>";
		echo "<input type = 'text' name = 'phone' size = '10' maxlength = '10'>";
		echo "<br /><br />";
		
		echo "<b>Please enter you credit card number:</b>";
		echo "<input type = 'text' name = 'card' size = '30' maxlength = '30'>";
		echo "<br /><br />";
		
		echo "<input type = 'submit' value = 'Submit'>";
		echo "</form>";
		echo "</center>";
	}
?>

</body>
</html>