<?php
/*
This is the main login page that takes care of checking users, passwords, and whether or not to forward the user to the store
*/

session_start();
$option = $_SESSION['option'];
$cookie = $_COOKIE['user'];

// If there is a cookie present and the option is neither login nor cookiepage nor shopping, showCookiePage

if( $cookie && strcmp($option,"login") != 0  && strcmp($option,"cookiePage") != 0 && strcmp($option,"shopping") != 0)
{
	showCookiePage(false);
}
else
{
	// If the session has not yet started
	if(!$option)
	{
		showLoginForm();
	}
	// If the option is login
	elseif( strcmp($option,"login") == 0 )
	{
		// If the email and password have been POSTed
		if( $_POST['email'] && $_POST['password'])
		{
			// If there is a cookie present on the client machine set the session email to the cookie email, otherwise set it to the post email
			if( $cookie ):
				$_SESSION['email'] = $cookie['email'];
			else:
				$_SESSION['email'] = $_POST['email'];
			endif;

			// Set the sessions pass to the POSTed password
			$_SESSION['pass'] = $_POST['password'];

			// If the users.txt file does not exist or the user is new get their information, else perform a normal login
			if( !file_exists("users.txt") ):
				getUserInfo();
			elseif( isNewUser() ):
				getUserInfo();
			else:
				// Get the email address and name of the persion so we can set the cookie
				$email = $_SESSION['email'];
				$name = getName($email);
				setUserCookie($email,$name);

				// If the password enter matches the user's password, show the store, else show the page that displays they have entered an incorrect password
				if( passwordsMatch() ):
					showShoppingPage();
				else:
					showCookiePage(true);
				endif;
			endif;
		}
		else
		{
			// If the option is login and there are no POSTed variables or blank variables, just show the login page
			showLoginForm();
		}
	}
	// If the option is info, the user just ented his or her info into a form and now we are gathering that info to put in users.txt
	elseif( strcmp($option,"info") == 0 )
	{
		// If all of the POSTed variables exist, write the information to the users.txt file and set the cookie again, then show the store
		if( $_SESSION['email'] && $_SESSION['pass'] && $_POST['name'] && $_POST['address'] && $_POST['phone'] && $_POST['card'] ):
			$file = fopen('users.txt','a');
			$line = implode(":", array($_SESSION['email'], $_SESSION['pass'], $_POST['name'], $_POST['address'], $_POST['phone'], $_POST['card']) );
			fwrite($file,$line);
			fwrite($file,"\n");
			fclose($file);
			$email = $_SESSION['email'];
			$name = $_POST['name'];
			setUserCookie($email,$name);
			showShoppingPage();
		else:
			// If one or more of the POSTed variables were blank, then we show the User Information Page again
			getUserInfo();
		endif;
	}
	// If the option is cookie page, aka the previous page was the page where a user with a cookie would enter information
	elseif( strcmp($option,"cookiePage") == 0 )
	{
		// Get the email and the password entered
		$email = $_SESSION['email'] = $cookie['email'];
		$_SESSION['pass'] = $_POST['password'];

		// If the user just wanted to login, check the password and perform a normal login, if they don't match just show the cookie page again
		if( $_POST['Submit'] )
		{
			if( passwordsMatch() )
			{
				showShoppingPage();
			}
			else
			{
				showCookiePage(true);
			}
		}
		// If the user pressed the forgot password button, we send him or her an email with the password and show the cookie page again
		elseif( $_POST['Forgot'] )
		{
			$pass = getPassword($email);
			mail("$email","Shopping Page Password","User: $email , Password: $pass");
			showCookiePage(false,true);
		}
		// If the user wants to login with a new username, unset the session variables and delete the cookie, then show the original login form
		elseif( $_POST['Diff'] )
		{
			unset($_SESSION['email']);
			unset($_SESSION['pass']);
			setcookie("user[email]", "", time() - 3600);
			setcookie("user[name]", "", time() - 3600);
			showLoginForm();
		}
	}
	// If the user presses logout on the store pages, we need to unset the session variables and delete the cookie, then show the login form again
	elseif( $_POST['logout'] )
	{
		unset($_SESSION['email']);
		unset($_SESSION['pass']);
		unset($_SESSION['cart']);
		setcookie("user[email]", "", time() - 3600);
		setcookie("user[name]", "", time() - 3600);
		showLoginForm();
	}
	// If the user is shopping and tries to return to the shop.php manually, he or she will be redirected to the store
	elseif( strcmp($option,"shopping") == 0 )
	{
		showShoppingPage();
	}
	// If it gets here something bad happened fo sho
	else
	{
		echo "somethinhg went wrong <br />";
	}
}

// Sets the cookie with user's email and name
function setUserCookie($email, $name)
{
	setCookie("user[email]" , "$email" , time()+10000);
	setCookie("user[name]" , "$name" , time()+10000);
}

// Returns the name of the person that matches the email paramater
function getName($email)
{
	$file = fopen('users.txt','r');
	while( !feof($file) )
	{
		$temp = fgets($file);
		$line = explode(":",$temp);

		if( strcmp($email,$line[0]) == 0)
		{
			return $line[2];
		}
	}
	fclose($file);
}

// Gets the password from the users.txt file that matches the user with the email parameter
function getPassword($email)
{
	$file = fopen('users.txt','r');
	while( !feof($file) )
	{
		$temp = fgets($file);
		$line = explode(":",$temp);

		if( strcmp($email,$line[0]) == 0)
		{
			return $line[1];
		}
	}
	fclose($file);
}

// Checks to see if the passwords match
function passwordsMatch()
{
	$file = fopen('users.txt','r');
	$match = false;
	$email = $_SESSION['email'];
	$passEntered = $_SESSION['pass'];

	while( !feof($file) )
	{
		$temp = fgets($file);
		$line = explode(":",$temp);
		$e = $line[0];
		$p = $line[1];

		if( strcmp($email,$e) == 0 && strcmp($passEntered,$p) == 0)
		{
			$match = true;
			break;
		}
	}

	fclose($file);
	return $match;
}

// Checks to see if the user is a new user or not
function isNewUser()
{
	$file = fopen('users.txt','r');
	$newUser = true;
	$email = $_SESSION['email'];

	while( !feof($file) )
	{
		$temp = fgets($file);
		$line = explode(":",$temp);

		if( strcmp($email,$line[0]) == 0 )
		{
			$newUser = false;
			break;
		}
	}

	fclose($file);
	return $newUser;
}

// Displays the original login form you would see if there is no cookie present on the client machine, sets the option to login
function showLoginForm()
{
	$_SESSION['option'] = "login";
?>
	<html>
	<head><title>Login Page</title><link rel="stylesheet" type="text/css" href="style.css" /></head>
	<body>
		<center>
			<h1>Login Page</h1>
			<i>Please enter your email address and password</i><br />
			<form action = "shop.php" method = "POST">
				<label for = "email">Email: </label>
				<input type = "text" name = "email" id = "email" size = "28" maxlength = "28">
				<br />
				<label for = "password">Password: </label>
				<input type = "password" name = "password" id = "password" size = "12" maxlength = "12">
				<br />
				<input type = "submit" value = "Submit">
			</form>
		</center>
	</body>
	</html>

<?php
}

// Displays the user information page that is shown to new users, sets the option to info
function getUserInfo()
{
	$_SESSION['option'] = "info";
?>
	<html>
	<head><title>User Information Page</title><link rel="stylesheet" type="text/css" href="style.css" /></head>
	<body>
		<center>
			<h1>User Information Page</h1>
			<h4>Please fill out the following form</h4>
			<form action = "shop.php" method = "POST">

				<label for = "name">Name: </label>
				<input type = 'text' name = 'name' size = '30' maxlength = '30'>
				<br />

				<label for = "address">Address: </label>
				<input type = 'text' name = 'address' size = '50' maxlength = '50'>
				<br />

				<label for = "phone">Phone: </label>
				<input type = 'text' name = 'phone' size = '10' maxlength = '10'>
				<br />

				<label for = "card">Credit Card #: </label>
				<input type = 'text' name = 'card' size = '30' maxlength = '30'>
				<br />

				<input type = "submit" value = "Submit">

			</form>
		</center>
	</body>
	</html>

<?php
}

// Simply redirects the client the store, and sets the option to shopping
function showShoppingPage()
{
	$_SESSION['option'] = "shopping";
	header("Location: store.php");
	exit;
}

// Displays the page if a cookie is found on the client's machine
function showCookiePage($badPass, $mailSent = false)
{
	session_start();
	$_SESSION['option'] = "cookiePage";
	$cookie = $_COOKIE['user'];
	$name = $cookie['name'];
	$email = $cookie['email'];
	?>
	<html>
	<head><title>Welcome</title><link rel="stylesheet" type="text/css" href="style.css" /></head>
	<body>
		<center>
			<h1>Welcome Back!</h1>
				<?php
				//echo "$name $email";
				//echo "<h3>Email: $email</h3>";
				if($badPass)
				{
					echo "<i>The password you entered was incorrect!</i><br />";
				}

				if($mailSent)
				{
					echo "<i>An email was sent to $email with your password, please login again.</i><br />";
				}

				echo "<br />";
				?>
			<form action = "shop.php" method = "POST">
				<label for = "password">Password: </label>
				<input type = 'password' name = 'password' size = '12' maxlength = '12'>
				<br />
				<input type = "submit" name = "Submit" value = "Submit">
				<input type = "submit" name = "Forgot" value = "Forgot Password?">
				<input type = "submit" name = "Diff" value = "Different User?">
			</form>
		</center>
	</body>
	</html>

	<?php
}
?>