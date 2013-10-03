CREATE OR REPLACE VIEW RNA_HOLD_AMOUNT_V
(HOLD_AMT, RNAVIALID)
AS 
SELECT sum(hold_amt) as hold_amount, rnavialid
FROM (
	(SELECT (SUM(quantity)) AS hold_amt, barcode_id as rnavialid
	FROM es_shopping_cart_detail
	WHERE product_type = 'RN'
	GROUP BY barcode_id)
UNION ALL
	(SELECT (SUM(proj.volume*rna.concentration)) AS hold_amt, vialtouse as rnavialid
	FROM rna_rna_list proj, rna_batch_detail rna
	WHERE proj.vialtouse = rna.RNAVIALID
	AND vialtosend IS NULL
	GROUP BY vialtouse) )
GROUP BY rnavialid;
/