create table REQUEST_HISTORY (
    ID binary not null,
    CREATED_ON timestamp,
    IP_ADDRESS varchar(255),
    IP_COORDINATES_FK binary,
    WEATHER_FORECAST_FK binary,
    primary key (ID)
);