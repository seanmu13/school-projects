var xhr;

function submitInfo(form,url){
	var skipChecking = false;
	for(i=0; i<form.elements.length; i++)
	{
		if( form.elements[i].type == 'submit' && form.elements[i].value == 'Logout' && document.pressed == 'Logout' )
		{
			skipChecking = true;
			break;
		}
		else if( form.elements[i].type == 'submit' && form.elements[i].value == 'SeeYourTickets' && document.pressed == 'SeeYourTickets' )
		{
			skipChecking = true;
			break;
		}
	}

	if(!skipChecking)
	{
		for(i=0; i<form.elements.length; i++)
		{
			if( (form.elements[i].type == 'text' || form.elements[i].type == 'password') && form.elements[i].value == "")
			{
				alert('Please fill in all fields!');
				return false;
			}
		}
	}

	xhr = GetXmlHttpObject();
	if (xhr==null)
	{
		alert ("Browser does not support HTTP Request");
		return false;
	}
	var params = "?";
	for(i=0; i<form.elements.length; i++)
	{
		var name = form.elements[i].name;
		var val = form.elements[i].value;
		var type = form.elements[i].type;
		val = val.replace(/ /g,"%20");

		if(type == "radio")
		{
			if(!form.elements[i].checked)
			{
				continue;
			}
		}

		if(type == "submit")
		{
			if( val != document.pressed)
			{
				continue;
			}
		}

		if( i == form.elements.length-1)
		{
			params += name + "=" + val;
		}
		else
		{
			params += name + "=" + val +  "&";
		}
	}

	url += params;
	xhr.onreadystatechange=stateChanged;
	xhr.open("GET",url,true);
	xhr.send(null);
	return false;
}

function stateChanged(){
	if (xhr.readyState == 4 || xhr.readyState == "complete")
	{
		document.getElementById("center").innerHTML=xhr.responseText;

	}
}

function sortTable(columnNumber){
	currentTable = document.getElementById('table');
	myRows = currentTable.rows;
	sortedColumns = new Array();
    copyRows = new Array();
    copyCols = new Array();
    finalOutput = new Array();

    for(i = 1; i < myRows.length-1; i++){
		sortedColumns[i-1] = myRows[i].childNodes[columnNumber].innerHTML;
        copyRows[i-1] = myRows[i];
    }

	copyCols = sortedColumns.slice();
	sortedColumns.sort();

    for(j = 0; j < sortedColumns.length; j++)
	{
        for(k = 0; k < copyCols.length; k++)
		{
            if(sortedColumns[j] == copyCols[k])
			{
                found = false;
                for(i = 0; i < finalOutput.length; i++)
				{
                    if(finalOutput[i] == k)
					{
                        found = true;
                        break;
                    }
                }

                if( !found )
				{
                    finalOutput[j] = k;
                    break;
                }
            }
        }
    }

	innards = new Array();

	for(n=0;n<finalOutput.length;n++){
		innards[n] = copyRows[finalOutput[n]].innerHTML;
	}

    for (m=0; m<innards.length; m++){
		myRows[m+1].innerHTML = innards[m];
    }
}

function GetXmlHttpObject(){
	xhr = null;
	try
	{
		// Firefox, Opera 8.0+, Safari
		xhr = new XMLHttpRequest();
	}
	catch (e)
	{
		//Internet Explorer
		try
		{
			xhr = new ActiveXObject("Msxml2.xhr");
		}
		catch (e)
		{
			xhr = new ActiveXObject("Microsoft.xhr");
		}
	}
	return xhr;
}