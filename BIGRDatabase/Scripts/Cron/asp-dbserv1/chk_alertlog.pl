#!/usr/bin/perl
# Phil DiPrima
# 7/10/00  Original
# 11/07/06 Modified for GulfStreamBio

$mailer='/bin/mailx'; # Mail program to use
$rcpt='dba\@healthcit.com';  # Who do I email
#$rcpt_cc2='pdiprima\@rational.com';  # Who do I email
#$rcpt_cc2='8664146937\@skytel.com';  # Who do I CC

# First get todays date.
open TEST, "date |";

$dateString = <TEST>;
chomp($dateString);
$TheDate = substr $dateString, 0, 13;

@filearray = ("ASPDB2");

foreach $sid (@filearray) {
  open(AAA,"</u01/app/oracle/admin/$sid/bdump/alert_$sid.log") 
       || die "Cannot open infile on bos-devdb1.ardais.com:$sid\n";

  #print "Database $sid $TheDate\n";
  # Flag to begin looking for Oracle error
    $StartChecking = 0;
  # Flag to stop emailing after first error is found
    $FoundError = 0;

  while (<AAA>) {
    chomp;
    $string = $_;

    if($StartChecking == 1) {   # I can start checking for errors
       if($string =~ /ORA-/) { # Found Oracle error
          if($FoundError == 0) { # Found the first error
             $subj=$sid;
             $message=$string;
             system "echo \"$message\"|$mailer -s \"$subj:Alert Log!\" $rcpt";
             # system "echo \"$message\"|$mailer -s \"$subj\" $rcpt_cc2";
             $FoundError = 1;
          }
       }
    }
    else
      {
       if($string =~ /$TheDate/) { # Found the current date
          $StartChecking = 1; 
       }
      }

   }
  close AAA;
}

1;
