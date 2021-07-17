create table IP_COORDINATES (
    ID binary not null,
    CREATED_ON timestamp,
    IP_ADDRESS varchar(15),
    LATITUDE float,
    LONGITUDE float,
    GEO_LOCATION_FK binary not null,
    primary key (ID)
);

create index IP_COORDINATES_IP_ADDRESS on IP_COORDINATES (IP_ADDRESS);

alter table IP_COORDINATES
add constraint C_IP_COORDINATES_TO_GEOLOCATION
foreign key (GEO_LOCATION_FK)
references GEOLOCATION;