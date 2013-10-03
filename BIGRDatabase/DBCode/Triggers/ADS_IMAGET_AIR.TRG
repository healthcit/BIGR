CREATE OR REPLACE TRIGGER ads_imaget_air
after insert on ads_imaget
for each row
begin
  btx.on_ads_imaget_air(:new.slideid, :new.username, :new.magnification, :new.imagenotes, :new.imagefilename);
end ads_imaget_air;
/