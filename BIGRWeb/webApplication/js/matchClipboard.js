// matchClipboard.js

function matchSamplesFromClipboard() {

  // no need to compare if there are no samples in the project...
  if (samplesProj.length == 0) 
  	return;
  	
  // get samples from the clipboard...
  var clipboard = getClipboardText();
  if (clipboard != null) {
    var re = /\s*,\s*|\s+/;
    var samples = clipboard.split(re);
    if (samples.length > 0) {
      re = /(^(FR|PA|SX)(\d|[A-F]){8}$)/;
      // examine each of the string fragments to verify they are sample ids...
      for (i = 0; i < samples.length; i++) {
      	  var sampleId = samples[i];
      	  // verify that it is a valid sample id...
      	  if (sampleId.search(re) != -1) {
    	      // ok, we have a valid sample id 
	      	  // now, let's determine if that matches a sample on the
	      	  // list; if so, let's check off that sample's checkbox
	      	  // which should be indexed in the allsamples array...

	      	  for(j=0; j < samplesProj.length ; j++) {
	      	  	if (sampleId == samplesProj[j]) {
	      	  		//alert("match in compare loop, id=" + sampleId);
	      	  		// ok, there is a match, so let's set the checkbox...
        			document.all.sample[j].checked = true;  
        			selectedCount++;   	  		
	      	  		}
	      	  	}
      	  }
      }
    }
  }
  //alert("after loops, selectedCount=" + selectedCount);
  document.all.numSelected.innerHTML = selectedCount;
}