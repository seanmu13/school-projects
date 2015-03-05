function checkAdminInput ( form )
{
	var pressed = document.pressed;

	for (var i=0; i < document.form.select.length; i++)
	{
		if (document.form.select[i].checked)
		{
			var value = document.form.select[i].value;
		}
	}

	if( pressed == 'Sort' && !isNaN(value) )
	{
		alert('Please select a column to sort.');
		return false;
	}
	else if( pressed == 'View Selected Ticket' && isNaN(value) )
	{
		alert('Please select a ticket in the righthand column.');
		return false;
	}

	return true ;
}

function checkForBlanks ( form )
{
  if (form.user.value == "" || form.pass.value == "")
  {
    alert( "Please do not leave any fields blank." );
    return false ;
  }
  return true ;
}

function checkUserInput ( form )
{
  if (form.first_name.value == "" || form.last_name.value == "" || form.email.value == "" || form.subject.value == "" || form.description.value == "") {
    alert( "Please do not leave any fields blank." );
    return false ;
  }
  return true ;
}