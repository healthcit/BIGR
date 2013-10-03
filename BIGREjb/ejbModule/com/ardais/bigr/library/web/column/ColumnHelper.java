package com.ardais.bigr.library.web.column;

import java.io.IOException;

import com.ardais.bigr.api.Escaper;

public class ColumnHelper {

  public static String generatePvReportHtml(String buttontext, String evaluation,
                             String sample, String linktext, String image1,
                             String image2, String showImageReport) throws IOException {
  
    StringBuffer link = new StringBuffer();
  
    String context = "/BIGR";
  
    if (buttontext != null && !buttontext.equals("")) {
      link.append("<input type=button ");
      link.append("value=\"");
      link.append(buttontext);
      link.append('"');
    } else {
      link.append("<span class=\"fakeLink\"");
    }
    link.append(" onclick=\"showPvRpt('");
  
    if (evaluation != null && !evaluation.equals("")) {
      link.append(evaluation);
    } else if (sample != null && !sample.equals("")) {
      link.append(sample);
    } else {
      throw new IOException("ViewPvReportTag: sample or evaluation must be specified");
    }
  
    link.append("',");
  
    if (showImageReport != null && !showImageReport.equals("")) {
      link.append("true");
    } else {
      link.append("false");
    }
    
    link.append(");\">"); // close showPvRpt call
    
    //Determine what to show for linkable text or images
    if (buttontext == null || buttontext.equals("")) {
      if (linktext != null && !linktext.equals("")) {
        link.append(linktext);
      } else {
        //determine whether to use one image or two
        if (image1 != null && !image1.equals("") && image2 != null && !image2.equals("")) {
          //use both
          link.append("<img src=\"");
          link.append(context + image1);
          link.append("\" height=15 width=15 border=0>");
          link.append("<img src=\"");
          link.append(context + image2);
          link.append("\" border=0>");
    
        } else if (image1 != null && !image1.equals("")) {
          link.append("<img src=\"");
          link.append(context + image1);
          link.append("\" height=15 width=15 border=0>");
    
        } else {
          //throw exception, must have at least something in the hyperlink body
          throw new IOException("ViewPvReportTag: linktext or image1 must be specified");
        }
      }
    }
  
    if (buttontext == null || buttontext.equals("")) {
      link.append("</span>");
    }
    return link.toString();
  
  }

  public static String generatePullInfoHtml(String sampleId, String pullDetails,
                                      String image1, String image2) throws IOException {
                                        
    StringBuffer output = new StringBuffer();
    String context = "/BIGR";
  
    if (sampleId == null || sampleId.trim().equals("")) {
      throw new IOException("ViewPullInfoTag: sampleId must be specified");
    }
    if (pullDetails == null || pullDetails.trim().equals("")) {
      throw new IOException("ViewPullInfoTag: pullDetails must be specified");
    }
    
    StringBuffer info = new StringBuffer(100);
    info.append("This sample was pulled for the following reason: &quot");
    info.append(Escaper.htmlEscape(pullDetails));
    info.append(".&quot");
  
    output.append("<span onmouseout=\"return nd();\" onmouseover=\"return overlib('");
    output.append(Escaper.jsEscapeInXMLAttr(info.toString()));
    output.append("');\" class=\"fakeLink\" onclick=\"showPvRpt('");
    output.append(sampleId);
    output.append("',false");
    output.append(");\">"); // close showPvRpt call
  
    //determine whether to use one image or two
    if (image1 != null && !image1.equals("") && image2 != null && !image2.equals("")) {
      //use both
      output.append("<img src=\"");
      output.append(context + image1);
      output.append("\" height=15 width=15 border=0>");
      output.append("<img src=\"");
      output.append(context + image2);
      output.append("\" border=0>");
  
    } else if (image1 != null && !image1.equals("")) {
      output.append("<img src=\"");
      output.append(context + image1);
      output.append("\" height=15 width=15 border=0>");
  
    } else {
      //throw exception, must have at least something in the hyperlink body
      throw new IOException("ViewPullInfoTag: image1 must be specified");
    }
    output.append("</span>");
  
    return output.toString();
  
  }
}
