function controlSpinner(behavior) {
	if (behavior.status == 'begin') {
		document.getElementById('spinner').style.display = 'inline';
	}
	else if (behavior.status == 'complete') {
		document.getElementById('spinner').style.display = 'none';
	}
}

/* Roll-over and Click-through row */
$(document).ready(function() {
	$("#patitentSearchForm tbody.ui-datatable-data tr a").click(function(event){
		event.preventDefault();
	})
	$("#patitentSearchForm tbody.ui-datatable-data tr").click(function() {
  		window.location = ($(this).find("td:first").find("a").attr("href"));;
	})
	$("#patitentSearchForm tbody.ui-datatable-data tr").mouseover(function() {
  		$(this).addClass("rowOver");
  	})
  	$("#patitentSearchForm tbody.ui-datatable-data tr").mouseout(function() {
  		$(this).removeClass("rowOver");
  	})

	// Extended from primefaces dialog.js
	if (typeof PrimeFaces != "undefined") {
		if (typeof PrimeFaces.widget != "undefined") {
			if (typeof PrimeFaces.widget.Dialog != "undefined") {
				PrimeFaces.widget.Dialog.prototype.show = function() {
					if(this.visible) {
					   return;
					}

					if(!this.loaded && this.cfg.dynamic) {
						this.loadContents();
					}
					else {
						// re-center the dialog every time it is opened.
						this.cfg.position='center';
						this._show();
						this.initPosition();
					}
				};
			}
		}
	}
});