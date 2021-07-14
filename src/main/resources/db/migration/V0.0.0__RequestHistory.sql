create table REQUEST_HISTORY (
    ID binary not null,
    CREATED_ON timestamp,
    IP_ADDRESS varchar(15),
    COUNTRY varchar(100),
    CITY varchar(255),
    WEATHER clob,
    primary key (ID)
);

create index IDX_REQUEST_HISTORY on REQUEST_HISTORY (IP_ADDRESS, COUNTRY, CITY);