SELECT * FROM EVX_VERTICLE;
SELECT * FROM EVX_ROUTE;
SELECT * FROM EVX_URI;
SELECT * FROM EVX_RULE AS R JOIN EVX_URI AS U ON R.R_URI_ID = U.K_ID;
SELECT * FROM EVX_ADDRESS;