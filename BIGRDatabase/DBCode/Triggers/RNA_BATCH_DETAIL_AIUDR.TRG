Create or Replace Trigger RNA_BATCH_DETAIL_AIUDR
 After Insert or Update or Delete of REP_SAMPLE on RNA_BATCH_DETAIL
 For Each Row
begin
  if (inserting) then
    sample_triggers.on_rna_batch_detail_aiudr(
      'I',
      null, null, null,
      :new.rnavialid, :new.producttype, :new.rep_sample);
  elsif (updating) then
    sample_triggers.on_rna_batch_detail_aiudr(
      'U',
      :old.rnavialid, :old.producttype, :old.rep_sample,
      :new.rnavialid, :new.producttype, :new.rep_sample);
  elsif (deleting) then
    sample_triggers.on_rna_batch_detail_aiudr(
      'D',
      :old.rnavialid, :old.producttype, :old.rep_sample,
      null, null, null);
  end if;
end;
/

