function mnavhClick() {
	var oUl = document.getElementById("starlist");  
	var style = oUl.style;
	style.display = style.display == "block" ? "none" : "block";
	document.getElementById("mnavh").className = style.display == "block" ? "open" : ""
}