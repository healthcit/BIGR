Create or Replace Trigger ARD_LOGICAL_REPOS_BMS_YN_AUR
 After Update of BMS_YN on ARD_LOGICAL_REPOS
 For Each Row
 Call sample_triggers.bms_yn_on_repos_bms_yn_aur(
   :old.id, :old.bms_yn, :new.id, :new.bms_yn)
/
