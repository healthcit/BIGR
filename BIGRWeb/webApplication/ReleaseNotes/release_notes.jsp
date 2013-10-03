<%@ taglib uri="/tld/struts-html" prefix="html" %>
<%
	com.ardais.bigr.orm.helpers.FormLogic.commonPageActions(request, response, this.getServletContext(),"P");
%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="<html:rewrite page="/css/bigr.css"/>">
<title>Terms and Conditions</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>
<body class="bigr">
<table width="75%" border="1">
  <tr>
    <td>
      <div align="center"><b><font size="+1">Release Notes for BIGR&#174; Library 
        Release 2.8</font></b></div>
    </td>
  </tr>
  <tr> 
    <td> 
      <p><b>Major Changes</b>.<br>
        We have adopted tumor staging and grading standards based on the recommendations 
        of the Fifth and Sixth Editions of the American Joint Committee on Cancer 
        (AJCC) in cooperation with the TNM committee of the International Union 
        Against Cancer (UICC).</p>
      <p>We use the pathologic assessment of the TNM values. <br>
        &quot;The TNM system is an expression of the anatomic extent of disease 
        and is based on the assessment of three components:<br>
        T The extent of the primary tumor<br>
        N The absence or presence and extent of regional lymph node metastasis<br>
        M The absence or presence of distant metastasis<br>
        &#133;<br>
        In effect, the system is a shorthand notation for describing the &#133; 
        pathologic anatomic extent of the malignant disease.&quot;<br>
        Minimum Stage Grouping: Stage grouping is based on TNM values (see Appendix 
        A of manual). Each stage group is relatively homogeneous with respect 
        to survival and the survival rates of these groups for each cancer site 
        are distinct.<br>
        The initial query results include many of the values needed for high-level 
        selection decisions and can be viewed prior to using the drill-down method 
        to find additional information. Chief among the values visible here are 
        the Histologic Nuclear Grade and the amount of tumor, lesional, or normal 
        components available in the sample. </p>
      <p><b>Browse/Select Inventory query page</b>. <br>
        New query options for either gross appearance or microscopic appearance 
        are available. The Gross appearance values are for the specimen at gross 
        examination, determined by the pathologist handling the specimen (Gross 
        Diseased, Gross Normal, or Gross Mixed). Alternately, the microscopic 
        appearance values are for the sample upon pathology verification, determined 
        by an Ardais' pathologist (Tumor, Lesional, or Normal).</p>
      <p><b>The Browse/Select Inventory results page has a new dense, tabular 
        format</b>.<br>
        The Browse/Select Inventory results page is entirely new and displays 
        the results of your query in a dense, tabular format.<br>
        The upper frame shows totals for: Samples Selected, Samples (available), 
        ASMs, Cases, and Donors.<br>
        The middle frame contains the bulk of information:<br>
        The lower frame contains buttons for you to: place items on hold, conduct 
        a new search, or view selected items.<br>
      </p>
      <p><b>The middle frame of the Browse/Select Inventory results page contains:</b><br>
        Select: Use this checkbox to record your selection of samples. Remember 
        to &quot;Place Items on Hold&quot; using the button at the bottom of this 
        page. The number of samples on hold (selected) will be shown at the top 
        of this page. The microscope icon indicates that Ardais pathology verification 
        results are available through that hotlink.</p>
      <p>Case ID: Case IDs are surgery identifiers assigned each time a donor 
        comes into a medical institution for a new surgery. Case IDs start with 
        CI for cases where informed consent was obtained and CU for unlinked cases. 
        This hotlink takes you to the pages for Donor Profile and Case Report 
        and the Patient Case Report. </p>
      <p>ASM: Surgical specimens are divided into modules that vary in size, depending 
        on the volume of bankable tissue. Each module is sub-divided into individual 
        samples. Samples are dissected from the module following strict guidelines 
        that preserve positioning within the module. Samples are available in 
        either frozen OCT-embedded format (F) or formalin fixed paraffin embedded 
        format (P). Pathology verification results are available through this 
        hotlink.</p>
      <p>Accession Date: The date when the sample is first recorded into the system.</p>
      <p>Gender: Gender of the donor. Choices are: Male, Female, Other, and Unknown. 
        Other indicates the patient is, for example, a hermaphrodite. Unknown 
        indicates those rare circumstances when gender is not recorded in the 
        medical record.</p>
      <p>Age at Surgery: Age of the donor at the time of the surgery for this 
        case.</p>
      <p>Case Diagnosis from Donor Institution Pathology Report: The diagnosis 
        for the case as recorded by the pathologist of the Donor Institute.</p>
      <p>Sample Pathology from Ardais Pathology Verification: The diagnosis selected 
        by the Ardais pathologist for the sample being verified.</p>
      <p>Tissue of Origin/finding: The tissue in which the disease arises or in 
        which the disease is found. After PV, the name of the tissue represented 
        by the cell types seen under microscopic examination; defined by an Ardais 
        pathologist.</p>
      <p>Appearance: The Gross appearance values are for the specimen at gross 
        examination, determined by the pathologist handling the specimen (Gross 
        Diseased, Gross Normal, or Gross Mixed). Alternately, the microscopic 
        appearance values are for the sample upon pathology verification, determined 
        by an Ardais' pathologist (Tumor, Lesional, or Normal).</p>
      <p>Minimum Stage Grouping: Stage grouping is based on TNM values (see Appendix 
        A of manual). Each stage group is relatively homogeneous with respect 
        to survival and the survival rates of these groups for each cancer site 
        are distinct.</p>
      <p>Histologic Nuclear Grade: Histologic Type; Histologic Nuclear Grade results 
        of pathological grading. See Appendix B of manual.</p>
      <p>NRM: Percent distribution of normal cells in the sample upon microscopic 
        examination.</p>
      <p>LSN: Percent distribution of lesional cells in the sample upon microscopic 
        examination.</p>
      <p>TMR: Percent distribution of tumor cells in the sample upon microscopic 
        examination.</p>
      <p>TCS: Percent distribution of tumor hypercellular stroma cells in the 
        sample upon microscopic examination.</p>
      <p>TAS: Percent distribution of tumor hypo-/acellular stroma cells in the 
        sample upon microscopic examination.</p>
      <p>NEC: Percent distribution of necrotic cells in the sample upon microscopic 
        examination.</p>
      <p>Comments: Comments recorded by Ardais pathologists upon viewing sample 
        slides.</p>
      <p><b>The Donor Profile and Case Report now contains many more fields. </b><br>
        Choosing the Case ID from the new initial results page takes you to the 
        Donor Profile and Case Report. Choosing the Case ID from that page takes 
        you to the Patient Case Report.</p>
      <p> <b>Donor Profile Page</b><br>
        Added Donor Profile Note, which is a free text field to be populated with 
        information abstracted from Clinical Data.</p>
      <p><b>Patient Case Report Page, added or changed the following fields as 
        noted:</b><br>
        Section Diagnosis from Donor Institute Pathology Report<br>
        Section Tissue of Origin of Diagnosis<br>
        Section Site of Finding<br>
        Distant Metastasis<br>
        Minimum Stage Grouping<br>
        Pathology Section notes<br>
        Added notes for Additional Pathology Findings </p>
      <p><b><u>Release Notes for Release 2.7</u></b><br>
        <br>
        Release 2.7 of BIGR(TM) Library was available as of May 18th. Subsequently 
        you will see quicker, smoother access to our clinical information, pathology 
        verification data, and images for samples. As always we are constantly 
        upgrading the number and variety of samples and the amount of associated 
        data in the database.</p>
      <p>One enhancement is in the graphical user interface (GUI). It adds the 
        ability to search for text strings under the diagnosis and the tissue 
        criteria. You will still have the ability to use the pop up list to select 
        your search criteria on diagnosis and tissue. In addition you can use 
        the box labeled: Or Contains, to submit a phrase, keyword, or partial 
        word as part of your criteria. This phrase is added to the chosen items 
        from the list, i.e. it is a logical &quot;OR&quot;. The net result is 
        to display the samples that match the diagnosis or the tissue item from 
        the list (if any) plus the samples that match the keyword or phrase. </p>
      <p>This feature can also be used as a shortcut instead of opening the list. 
        In other words, rather than open the popup diagnosis list to look for 
        &quot;Adenocarcinoma of the prostate&quot; one can simply type in *prostate* 
        and get the prostate cancer cases. This procedure also returns any non-neoplastic 
        cases that contain the word prostate in the diagnosis, but these are fewer 
        in number than the neoplastic cases and easily skipped by using the Quick 
        Info box to examine the diagnosis. </p>
      <p>Another use of this feature is to survey the database for a general diagnosis. 
        Using the word *metastatic* you will see a list of metastatic diagnoses 
        from all body systems. </p>
      <p>Remember to include the asterick before and after the phrase.</p>
      <p>The tissue selections can be used in a like manner.</p>
      <p>We hope that you enjoy the new improvements and find BIGR&#174; Library 
        useful and helpful. We are very happy to have your feedback about how 
        we can better service your needs for clinical genomics resources. Please 
        feel free to contact us at any time.&quot;</p>
      <p><br>
        <u><b>Points of Note</b></u></p>
      <p><br>
        Please do not use the Internet Explorer windows buttons, Back and Close. 
        We prefer that you perform these functions using the buttons we provide 
        within each page for Back, Close, or Cancel (the equivalent of Close). 
        Otherwise errors may result.</p>
      <p><br>
        If you use any of these action buttons, Back, Close, Cancel, Continue, 
        Clear All, etc. please do not use them until after the page has fully 
        completed displaying as you may encounter an error. Example: Hitting the 
        Continue button before the full display of the diagnosis list creates 
        an error. We suggest that you enable and use the IE status bar in order 
        to monitor the completion of page display.</p>
      <p><br>
        If your query is too broad you will receive an error message. Please refine 
        your search and try again. For example, if your desired diagnosis or tissue 
        is gender related, please specify the gender. If your preference is for 
        linked samples only, please specify. If your preference is for samples 
        from donors of a particular age, please specify.<br>
      </p>
      <p><br>
      </p>
      <p><br>
      </p>
      <p>&nbsp; </p>
    </td>
  </tr>
</table>
<p>&nbsp;</p>
<p>&nbsp;</p>
</body>
</html>
