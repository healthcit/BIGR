// attachment.js

function onDeleteAttachment(obj, form){
    document.getElementById("attachId_"+obj).bgColor="51E8E5";
 
    
    if(confirm('Are you sure that you want to delete this attachment?')) {
    document.getElementById("deleteAttachId").value=obj;
    form.submit();
    return true;
    }
    else {
    document.getElementById("attachId_"+obj).bgColor="white";
    document.getElementById("deleteAttachId").value="";
    return false;
    }

}

function onSubmitAttach(form) {
  
   if(document.getElementById("btnBrowse").value==""){
    alert("You have not selected a file to attach, please select.");
    document.getElementById("btnBrowse").focus();
    return false;
   }
  
   if(document.getElementById("fileComments").value .length > 500){
    alert("Your comments is "+document.getElementById("fileComments").value .length+", please shorten it to 500 or less.");
    document.getElementById("fileComments").focus();
    return false;
   }
   
   form.submit();
   return true;
}

function onKeyInComment(e, form){
var evtobj=window.event? event : e //distinguish between IE's explicit event object (window.event) and Firefox's implicit.
var unicode=evtobj.charCode? evtobj.charCode : evtobj.keyCode
var actualkey=String.fromCharCode(unicode)
   if(unicode == 13) //the user hit the return key
   { return onSubmitAttach(form);
    }
   
   return false;
}