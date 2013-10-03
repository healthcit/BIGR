CREATE OR REPLACE FUNCTION HEXTODEC (
  hexnum IN VARCHAR2)
RETURN PLS_INTEGER
IS
  x      PLS_INTEGER;
  digit  PLS_INTEGER;
  result PLS_INTEGER := 0;
  current_digit CHAR(1);
  current_digit_dec PLS_INTEGER;
BEGIN
  digit := LENGTH(hexnum);
  FOR x IN 1..digit LOOP
      current_digit := SUBSTR(hexnum, x, 1);
      IF current_digit IN ('A','B','C','D','E','F') THEN
         current_digit_dec := ascii(current_digit) - ascii('A') + 10;
      ELSE
         current_digit_dec := to_number(current_digit);
      END IF;

      result := (result * 16) + current_digit_dec;
  END LOOP;
  RETURN result;
END;
/

