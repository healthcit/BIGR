-------------------------------------------------------------------------------------
--  File:    SYSERROR_MAIL_NOTIF.TRG
--	Author:  Dean Marsh
--  Date:    08/21/2002
--
--	Purpose: This is a database-level trigger that fires on servererror to email
--			 notification of the error to one or more email addresses.  Due to the
--			 way that email addresses have to be formatted, multiple recipients
--			 have to be specified if needed.  I have tried many different seperaters
--			 to put more than one address on a line and none of them work.
--
--			 This trigger fires only on a specific set of errors, specified in the
--			 first IF statement.  If left open, Oracle will alert you on everything
--			 which can get to be a nuscense really quick.  In order to get the
--			 text of the error, the FOR loop appears to be required.  I read about
--			 it in a note somewhere and it is the only way I can find to get the
--			 text.  SQLERRM by itself always returns "completed successfully".  The
--			 loop has to have an upper bound higher than the largest error number
--			 being watched.
--
--  Comments:  This trigger is installed under the SYS schema.  It can be installed
--			   under another schema, but that schema would need to have "ADMINISTER
--			   DATABASE TRIGGER" privilege and I didn't want to grant that to a user
--			   schema.
--
--			   You need to replace the default values for recpient1, recipient2, and
--			   mailhost with the correct values.
--
--------------------------------------------------------------------------------------


CREATE OR REPLACE TRIGGER syserror_mail_notif
after servererror on database
declare
   sender		  varchar2(10) := database_name;
   db_user		  varchar2(30) := login_user;
   recipient1 	  varchar2(100) := '<email@company.com>';
   recipient2 	  varchar2(100) := 'email@company.com>';
   mailhost 	  VARCHAR2(30) := '<IP address of email server>';
   mail_conn 	  utl_smtp.connection;
   cur_error 	  number := server_error(1);
   v_err		  varchar2(512);
BEGIN
  if cur_error between 449 and 482
     or cur_error between 1500 and 1700
     or cur_error in (12012, 12535)
  then
     v_err := SQLERRM(-cur_error);
     mail_conn := utl_smtp.open_connection(mailhost, 25);
     utl_smtp.helo(mail_conn, mailhost);
     utl_smtp.mail(mail_conn, sender);
  	 utl_smtp.rcpt(mail_conn, recipient1);
     utl_smtp.rcpt(mail_conn, recipient2);
  	 utl_smtp.open_data(mail_conn);
  	 utl_smtp.write_data(mail_conn, utl_tcp.CRLF || 'From:    ' || sender);
  	 utl_smtp.write_data(mail_conn, utl_tcp.CRLF || 'Subject: ' || v_err);
  	 utl_smtp.write_data(mail_conn, utl_tcp.CRLF || 'User: ' || db_user);
  	 utl_smtp.write_data(mail_conn, utl_tcp.CRLF || 'Date: ' || to_char(sysdate, 'DD-MON-RRRR HH24:MI:SS') || '.');
  	 utl_smtp.close_data(mail_conn);
  	 utl_smtp.quit(mail_conn);
  end if;
EXCEPTION
   WHEN OTHERS
   THEN
      dbms_output.put_line (sqlcode || ': ' || sqlerrm);
END;
/