<?php

#---------------------------------------------------------------------------------------------------------
#	INIT PAGE - The init page initializes the database with information for the 3 tables
#---------------------------------------------------------------------------------------------------------

	echo "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><html><head></script><link rel = 'stylesheet' type = 'text/css' href = 'style.css' /><title>Init</title></head><body><center>";

	$db = mysql_connect("localhost","MyDatabase","loewe.heder") or die("<b>Error:</b> " . mysql_error());
	$query[] = "DROP DATABASE MyDatabase";
	$query[] = "CREATE DATABASE MyDatabase";
	$query[] = "CREATE TABLE tickets (ticket int NOT NULL AUTO_INCREMENT primary key,date char(10) NOT NULL,name varchar(30) NOT NULL, email varchar(30) NOT NULL, subject varchar(30) NOT NULL, description varchar(300) NOT NULL, status varchar(6) NOT NULL)";
	$query[] = "CREATE TABLE users (id int NOT NULL AUTO_INCREMENT primary key, name varchar(30) NOT NULL, pass varchar(50) NOT NULL, admin int NOT NULL DEFAULT 0)";
	$query[] = "CREATE TABLE assign (ticket int NOT NULL AUTO_INCREMENT primary key, id int NOT NULL DEFAULT 0)";

	$file = file("tickets.txt") or exit("<b>Unable to open tickets.txt!</b>");

	foreach($file as $value):
		$line = explode("|",$value);
		$line[6] = trim($line[6]);
		$query[] = "INSERT INTO tickets VALUES('$line[0]','$line[1]','$line[2]','$line[3]','$line[4]','$line[5]','$line[6]')";
	endforeach;

	$file = file("users.txt") or exit("<b>Unable to open users.txt!</b>");

	foreach($file as $value):
		$line = explode("|",$value);
		$line[2] = trim($line[2]);
		$pass = crypt($line[2],"salt");
		$query[] = "INSERT INTO users VALUES('$line[0]','$line[1]','$pass',$line[3])";
	endforeach;

	for($i = 0; $i < 6; $i++):
		if($i==2 || $i==3)
			$query[] = "INSERT INTO assign VALUES(0,2)";
		elseif($i==0)
			$query[] = "INSERT INTO assign VALUES(0,1)";
		else
			$query[] = "INSERT INTO assign VALUES(0,DEFAULT)";
	endfor;

	foreach($query as $value):
		mysql_query("$value") or die("<b>Error:</b> " . mysql_error());
		if( strcmp($value, "CREATE DATABASE MyDatabase") == 0):
			mysql_select_db("MyDatabase") or die("<b>Error:</b> " . mysql_error());
		endif;
		echo "Query Successful: $value <br /><br />";
	endforeach;
	echo "</center></body></html>";
?>