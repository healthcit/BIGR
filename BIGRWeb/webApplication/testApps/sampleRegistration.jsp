
 
 <!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
 
 
 
<html>
<head>
<META HTTP-EQUIV="PRAGMA" CONTENT="NO-CACHE">
<link rel="stylesheet" type="text/css" href="/BIGR/css/bigr.css">
<title>BIGR - Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<script language="JavaScript" src="/BIGR/js/common.js"></script>
<script language="JavaScript" src="/BIGR/js/linkedlist.js"></script>
<script language="JavaScript" src="/BIGR/js/map.js"></script>
<script language="JavaScript"> 
<!--
 
function Privilege() {
  this.setId = setId;
  this.getId = getId;
  this.setDescription = setDescription;
  this.getDescription = getDescription;
  
  var _self = this;
  var _id;
  var _description;
  
  function setId(id) {
    _id = id;
  }
  
  function getId() {
    return _id;
  }
  
  function setDescription(description) {
    _description = description;
  }
  
  function getDescription() {
    return _description;
  }
}
-->
</script>
</head>
<body class="bigr">
<form name="managePrivilegesForm" method="post" action="/BIGR/orm/managePrivileges.do">
 
  <div id="errorDiv" align="center">
    <table width="100%" border="1" cellspacing="1" cellpadding="1" class="foreground-small">
      
      
	</table>
  </div>
  <p>
<input type="hidden" name="objectType" value="User">
<input type="hidden" name="userData.userId" value="PGUPTA">
<input type="hidden" name="userData.accountId" value="PreetiTest">
<input type="hidden" name="userData.firstName" value="Preeti">
<input type="hidden" name="userData.lastName" value="Gupta">
<input type="hidden" name="accountData.id" value="">
<input type="hidden" name="accountData.name" value="">
  <div align="center">
		<b>HCIT<br>Sample Registration Report</b>
<table align="center"></table>
  <br>
     <table class="background" cellpadding="0" cellspacing="0" border="0">
      <tr> 
        <td> 
          <table cellpadding="3" cellspacing="1" border="0" class="foreground">
          <tr class="lightGrey"> 
              <td align="center"> 
                <b>
                 Donor ID</b>
              </td>
              <td align="center"> 
                <b>
                 Donor Alias</b>
              </td>
              <td align="center"> 
                <b>
                 Case ID</b>
              </td>
              <td align="center"> 
                <b>
                 Case Alias</b>
              </td>
              <td align="center"> 
                <b>
                 Sample ID</b>
              </td>
               <td align="center"> 
                <b>
                 Sample Alias</b>
              </td>
               <td align="center"> 
                <b>
                 Sample Type</b>
              </td>
              <td align="center"> 
                <b>
                 Sample Inventory Group</b>
              </td>
              <td align="center"> 
                <b>
                 Tissue</b>
              </td>
              <td align="center"> 
                <b>
                Create Date</b>
              </td>
              <td align="center" title="Collection to Preservation"> 
                <b>
                 Elapsed Time</b>
              </td>
              <td align="center" title="Sample Registered By"> 
                <b>
       					Registered By</b>
              </td>
              <td align="center" title="Sample Prepared By"> 
                <b>
       					Prepared By</b>
              </td>
              <td align="center" title=""> 
                <b>
       					Box ID</b>
              </td>
              <td align="center" title="Room/storage unit/drawer/slot"> 
                <b>
       					Sample Location</b>
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX000029E1 
              </td>
              <td align="center"> 
                 22171-D4
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
               <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="white"> 
               <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002934
              </td>
              <td align="center"> 
                 22134-D4
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left">  
                <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="grey"> 
              <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002944
              </td>
              <td align="center"> 
                 22171-D5
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
               <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="white"> 
               <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002233
              </td>
              <td align="center"> 
                 22171-D7
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
                <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
            <tr class="Grey"> 
              <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002221
              </td>
              <td align="center"> 
                 22171-DS
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
               <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="white"> 
               <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002EE1
              </td>
              <td align="center"> 
                 22177-D4
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
                <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
              <tr class="Grey"> 
                <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00003456
              </td>
              <td align="center"> 
                 22171res 
              </td>
              <td align="center"> 
                Frozen Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
               <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
             <tr class="white"> 
                <td align="center"> 
                 AX0000000438
              </td>
              <td align="center"> 
                 D-TN06-01132
              </td>
              <td align="center"> 
                 CX0000000221
              </td>
              <td align="center"> 
                 TN06-01132
              </td>
              <td align="center"> 
                 SX00002568
              </td>
              <td align="center"> 
                22171-D9
              </td>
              <td align="center"> 
                Paraffin Tissue
              </td>
              <td align="center"> 
                Biobank General
              </td>
              <td align="center"> 
                Heart
              </td>
               <td align="center"> 
                05/07/2007
              </td>
               <td align="center"> 
                5 min 3 sec
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                Jaweed Mohammed
              </td>
               <td align="center"> 
                BX0000012569
              </td>
                <td align="left"> 
              <u>Location Name</u>:Rockville;<u>Room Name</u>:Room1;<u>Storage Unit Name</u>:-80Freezer 01;<br><u>Drawer/Slot</u>:1-A;<u>Box Barcode ID</u>:BX0000000001 
              </td>
            </tr>
            <tr class="white"> 
              <td align="left" class="ln3" colspan="15"> 
              </td>
             </tr>
       
            <tr class="white" > 
              <td align="right" class="grey" colspan="1"> 
                 <b>Total Donors:</b>
              </td>
              <td align="left" class="white" colspan="1"> 
                 <b>1</b>
              </td>
             <td align="right" class="grey" colspan="1"> 
                 <b>Total Cases:</b>
              </td>
               <td align="left" class="white" colspan="1"> 
                 <b>1</b>
              </td>
               <td align="right" class="grey" colspan="1"> 
                 <b>Total Samples:</b>
              </td>
               <td align="left" class="white" colspan="1"> 
                 <b>8</b>
              </td>
               <td align="left" class="white" colspan="1" nowrap="nowrap"> 
                 <b>Frozen Tissue:&nbsp;<a href="sampleRegistration.jsp">7</a><br>
                    Paraffin Tissue:&nbsp;<a href="sampleRegistration.jsp">1</a></b>
              </td>
               <td align="left" class="white" colspan="8"> 
                 <b>Biobank General:&nbsp;<a href="sampleRegistration.jsp">8</a><br>
                  </b>
              </td>
            </tr>
            
             <tr class="white"> 
              <td align="left" class="ln3" colspan="15"> 
              </td>
             </tr>
          </table>
        </td>
      </tr>
    </table>
     <br>
          <table cellpadding="0" cellspacing="0" border="0" class="foreground" align="center">
          <tr class="white" > 
              <td align="center" class="white"> 
                 <a href="sampleRegistration.jsp">Download To Excel</a>
              </td>
            </tr>
          </table>
  </div>
</form>
</body>
</html>

