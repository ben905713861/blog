var oH2 = document.getElementById("mnavh"); 
var oUl = document.getElementById("starlist");  
oH2.onclick = function() {
	var style = oUl.style;
	style.display = style.display == "block" ? "none" : "block";
	oH2.className = style.display == "block" ? "open" : ""
}