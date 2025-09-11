create tablespace arq_hexd datafile '/opt/oracle/oradata/FREE/PDB1/arq_hexd01.dbf' size 100m autoextend on next 1m maxsize 512m;

create tablespace arq_hexi datafile '/opt/oracle/oradata/FREE/PDB1/arq_hexi01.dbf' size 100m autoextend on next 1m maxsize 512m;

create user arq_hex identified by arq_hex default tablespace arq_hexd temporary tablespace temp quota unlimited on arq_hexd;

ALTER USER arq_hex quota unlimited on arq_hexi;
ALTER USER arq_hex quota unlimited on arq_hexd;

CREATE ROLE "RES_SCHEMA_OWNER";

GRANT CREATE TRIGGER TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;
GRANT CREATE VIEW TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;
GRANT CREATE TABLE TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;
GRANT CREATE SYNONYM TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;
GRANT CREATE SEQUENCE TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;
GRANT CREATE PROCEDURE TO "RES_SCHEMA_OWNER" WITH ADMIN OPTION;

GRANT "RES_SCHEMA_OWNER" TO "ARQ_HEX" ;
ALTER USER "ARQ_HEX" DEFAULT ROLE "RES_SCHEMA_OWNER";

GRANT CREATE SESSION TO "ARQ_HEX" WITH ADMIN OPTION;

