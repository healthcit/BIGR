//
// Functions to support diagnosis, procedure and tissue hierarchies.
// Used in conjunction with /DDCHierarchy/HierarchyBuilder.jsp, which 
// calls the functions in response to the appropriate events.

function swapMenu(object) {
	for (i = 0 ; i < ids.length ; i++){
		if(object.toString() == ids[i].toString()){
			if(clicks[i].toString() == "Open"){
				clicks[i] = "Close";
				hideMenu(object);
			} else {
				clicks[i] = "Open";
				showMenu(object);
			}
		}
	}
}

function showMenu(object) {
	for(i = 0;i < ids.length; i++){
		if(object.toString() == ids[i].toString()){
			MenuItems = document.getElementsByName(ids[i].toString());
			MM_swapImage("Image_" + ids[i].toString(),'','/BIGR/images/MenuOpened.gif',1)
			for(j=0; j<MenuItems.length;j++){
				MenuItems[j].style.display = 'inline';
			}
		}
	} 
}

function hideMenu(object) {
  for(i = 0;i < ids.length; i++){
    if(ids[i].toString().length >= object.toString().length) {
      if(object.toString() == ids[i].toString().slice(0,object.length)) {
        MenuItems = document.getElementsByName(ids[i].toString());
        MM_swapImage("Image_" + ids[i].toString(),'','/BIGR/images/MenuClosed.gif',1)
        for(j=0; j<MenuItems.length;j++) {
          MenuItems[j].style.display = 'none';
        }
        clicks[i] = "Close";
      }
    }
  } 
}

function MM_findObj(n, d) { //v4.0
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && document.getElementById) x=document.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
