<?php
$db = mysql_connect("localhost","","loewe.heder");
mysql_select_db("") or die(mysql_error());

echo "<html><head><link rel = 'stylesheet' type = 'text/css' href = 'style3.css' /><title>Tables</title></head><body><center>";
$query = "SELECT * FROM tickets";
show_table($query,"tickets");
$query = "SELECT * FROM admins";
show_table($query,"admins");
$query = "SELECT * FROM assign";
show_table($query,"assign");
echo "</center></body></html>";

function show_table($query,$name)
{
	$result = mysql_query("$query")or die(mysql_error());

	echo "<h4>$name</h4>";
	echo "<table>";
	echo "<tr align = 'center'>";

	// Get the number of rows in the result, as well as the first row
	//  and the number of fields in the rows

	$num_rows = mysql_num_rows($result);  // how many rows in result?
	if ($num_rows == 0):
	    echo "Your query had no matches -- try again";
	    exit;
	endif;
	$row = mysql_fetch_array($result);  // get first row
	$num_fields = sizeof($row);

	// Produce the column labels
	while ($next_element = each($row)){
	    $next_element = each($row);
	    $next_key = $next_element['key'];
	    echo "<th>" . $next_key . "</th>";
	}

	echo "</tr>";

	// Output the values of the fields in the row

	for ($row_num = 0; $row_num < $num_rows; $row_num++) {
	    reset($row);
	    echo "<tr align = 'center'>";
	    for ($field_num = 0; $field_num < $num_fields; $field_num++)
	        echo "<td>" . $row[$field_num] . "</td> ";

	    echo "</tr>";
	    $row = mysql_fetch_array($result);
	}
	echo "</table><br/>";
}
?>