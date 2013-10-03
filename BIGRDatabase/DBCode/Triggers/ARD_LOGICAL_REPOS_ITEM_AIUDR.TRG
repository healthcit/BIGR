Create or Replace Trigger ARD_LOGICAL_REPOS_ITEM_AIUDR
 After Insert or Update or Delete on ARD_LOGICAL_REPOS_ITEM
 For Each Row
begin
  if (inserting) then
    sample_triggers.bms_yn_on_repos_item_aiudr(
      'I',
      null, null,
      :new.item_id, :new.item_type);
  elsif (updating) then
    sample_triggers.bms_yn_on_repos_item_aiudr(
      'U',
      :old.item_id, :old.item_type,
      :new.item_id, :new.item_type);
  elsif (deleting) then
    sample_triggers.bms_yn_on_repos_item_aiudr(
      'D',
      :old.item_id, :old.item_type,
      null, null);
  end if;
end;
/

