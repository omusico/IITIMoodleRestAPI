var apiUrl='https://localhost:8443/IITIMoodleRestAPI/rest/';
var wstoken;
var currentJson = [];
var init = function() {
	
	// Attach a submit handler to the form
	$( "#login-form" ).submit(function( event ) {
		// Stop form from submitting normally
		event.preventDefault();
		//remove the error message is existing already
		$("#errorMessage").text( "" );
		// Get some values from elements on the page:
		var $form = $( this ),
		resourceUrl = apiUrl +"login";
		// Send the data using post
	
		/*
		$.post( resourceUrl, 
			   $form.serialize(),// 
			   function( data ) {
			          console.log( data.token ); // logging response for dev enironment
			          //save token in local storage rather than in global variable TODO
			          wstoken = data.token; 
		              //Send request to get User data
		              var jsonData = getJson("users/me");
		              },
		     "json");
		     
		  */
		$.ajax({
			url:resourceUrl,
			type: "POST",
			data: $form.serialize(),
			//crossDomain: true,
			dataType: "json",
			error:  function (xhr, ajaxOptions, thrownError) {
		            customAjaxError(xhr);
		            },
			success:function(data){
				    console.log( data.token ); // logging response for dev enironment
		            //save token in local storage rather than in global variable TODO
		            wstoken = data.token; 
	                //Send request to get User data
	                customGetJson("users/me");
	               }
		});
	    
	});
	
/*
	
	$( document ).ajaxError(function( event, jqxhr, settings, exception ) {
		if ( jqxhr.status == 401 ) {
		   $("#errorMessage").text( "Invalid credentials, Please try again" );
		}
		else if ( jqxhr.status == 500 ) {
		   $("#errorMessage").text( "Moodle web or database server is down" );
		}

		else if ( jqxhr.status == 503 ) {
		   $("#errorMessage").text( "High Traffic. Service Unavailable" );
		}
		else{
			$("#errorMessage").text( "API service is down" );
		}
		});
		
*/
};


	var customAjaxError = function(xhr){
		if ( xhr.status == 401 ) {
			   $("#errorMessage").text( "Invalid credentials, Please try again" );
			}
			else if ( xhr.status == 500 ) {
			   $("#errorMessage").text( "Moodle web or database server is down" );
			}

			else if ( xhr.status == 503 ) {
			   $("#errorMessage").text( "High Traffic. Service Unavailable" );
			}
			else{
				$("#errorMessage").text( "API service is down" );
			}
	};

var customGetJson = function(resource){
	
	var resourceUrl = apiUrl + resource;
	console.log("resourceUrl is "+resourceUrl);
	$.ajax({
		url: resourceUrl,
		type: "GET",
		data: {wstoken: wstoken},
		//crossDomain: true,
		dataType: "json",
		error:  function (xhr, ajaxOptions, thrownError) {
	            customAjaxError(xhr);
	            },
		success:function(jsonData){
			     console.log(jsonData[0].city);
			    }
	});
	
};
