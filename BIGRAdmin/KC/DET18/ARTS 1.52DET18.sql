set define off;
set echo on;

update pdc_lookup 
set annotation='changed data type to text',
rev='1.52',
datatype='text',
min_value=null,
min_inclusive_yn=null,
drev='18',
other_element_yn='N',
oce_yn='N'
where lookup_type_cd in ('CA11563C', 'CA11564C', 'CA11569C', 'CA11570C', 'CA11583C', 'CA11189C', 'CA11217C');

update pdc_lookup set category_domain = 'SAMPLE_TYPE' where lookup_type_cd in ('CA12504C', 'CA12505C');

insert into pdc_lookup values('SAMPLE_TYPE', 'CA12589C', 'Cervicovaginal Fluid', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12590C', 'Cord Blood', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12591C', 'Cystic Fluid', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12592C', 'Hair Clipping', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12593C', 'Lung Lavage', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12594C', 'miRNA', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12595C', 'Nail Clipping', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
insert into pdc_lookup values('SAMPLE_TYPE', 'CA12596C', 'Pellet', '', '', 'ARD_CAP', 'A', 'P', '', '1.52', '', '', '', '', '', '', 'Value', '', '', '', '', '', '', '', '', '18', '');
commit;
