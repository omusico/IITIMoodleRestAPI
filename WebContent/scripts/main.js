var HOME='home';//function to initialize the page	var init = function() {		//showing the home tab on initializing		showTab(HOME);		//adding event listeners to the tabs		$('#menubar a').click(function(event) {			showTab(event.currentTarget.id);		});	};		//function to show the tab	var showTab = function(entity) {		//remove the active class from all the tabs		$('.tab').removeClass("active");		//setting the active class to the selected tab		$('#'+entity).addClass("active");		//hiding all the tabs		$('.g-unit').hide();		//showing the selected tab		$('#' + entity + '-tab').show();		};    	var notSeenClick = function(entity){		var boxId = "val"+ entity.substring(2);			   //alert("box id is : " +boxId);		// $this will contain a reference to the checkbox   		if ($('#'+entity).is(':checked')) {			// the checkbox was checked			//alert("the checkbox was checked");			$('#'+boxId).val('');			$('#'+boxId).prop('disabled', true);					} else {			// the checkbox was unchecked			//alert("the checkbox was UNchecked");			$('#'+boxId).prop('disabled', false);		}    };			var allMoviesMarked = function(){		//loop through all inputs		var index = 0;		var done = 'true';				$('#rating-form :text').each(function(){			if (!($('#ch'+index).is(':checked'))) {				if($(this).val().length<=0){		            done='false';		            				}			}			index++;					});		//if all movies are either rated or marked not seen		if(done=='true'){			//submit the form			$('#movie-rate-form').submit();					}		else{			alert('please mark all movies');		}	};		var validateInput = function(entity){		};    	