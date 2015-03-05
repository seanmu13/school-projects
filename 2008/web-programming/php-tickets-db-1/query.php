<!-- access_cars.php
     A PHP script to access the cars database
     through MySQL

     Modified for CS 1520 Summer 2008
     Password form field now needed to access DB.  As with the setup.php
     handout, be sure to change the id field to yours.
     -->
<html><head><link rel = 'stylesheet' type = 'text/css' href = 'style3.css' /><title>Query</title></head><body><center>
<?php
// Connect to MySQL

$userid = rtrim(strip_tags($_POST["id"]));

if( strcmp($userid,"root") == 0 )
{
	echo "Access Denied: ";
	exit;
}

$userpass = rtrim(strip_tags($_POST["pass"]));
$db = mysql_connect("localhost", "$userid", "$userpass");
if (!$db) {
     print "Error - Could not connect to MySQL";
     exit;
}

// Select the user's database

$er = mysql_select_db("MyDatabase");
if (!$er) {
    print "Error - Could not select the cars database";
    exit;
}

// Clean up the given query (delete leading and trailing whitespace)
$query = $_POST["query"];
trim($query);
$query = stripslashes($query);
print "<p> <b> The query is: </b> " . $query . "</p>";

// Execute the query

$result = mysql_query($query);
if (!$result) {
    print "Error - the query could not be executed";
    $error = mysql_error();
    print "<p>" . $error . "</p>";
    exit;
}
else if (gettype($result) == boolean)
{
    echo "Database Update Was Successful <br />";
    exit;
}

// Display the results in a table

print "<table><caption> <h2> Query Results </h2> </caption>";
print "<tr align = 'center'>";

// Get the number of rows in the result, as well as the first row
//  and the number of fields in the rows

$num_rows = mysql_num_rows($result);  // how many rows in result?
if ($num_rows == 0):
    print "Your query had no matches -- try again";
    exit;
endif;
$row = mysql_fetch_array($result);  // get first row
$num_fields = sizeof($row);

// Produce the column labels

while ($next_element = each($row)){
    $next_element = each($row);
    $next_key = $next_element['key'];
    print "<th>" . $next_key . "</th>";
}

print "</tr>";

// Output the values of the fields in the row

for ($row_num = 0; $row_num < $num_rows; $row_num++) {
    reset($row);
    print "<tr align = 'center'>";
    for ($field_num = 0; $field_num < $num_fields; $field_num++)
        print "<td>" . $row[$field_num] . "</td> ";

    print "</tr>";
    $row = mysql_fetch_array($result);
}
print "</table>";
?>
</center>
</body>
</html>

