create table WEATHER_FORECAST (
    ID binary not null,
    CREATED_ON timestamp,
    DESCRIPTION varchar(255),
    TEMP_ACTUAL float,
    TEMP_MAX float,
    TEMP_MIN float,
    TITLE varchar(100),
    WIND_SPEED float,
    GEOLOCATION_FK binary,
    primary key (ID)
);

alter table WEATHER_FORECAST
add constraint C_WEATHER_FORECAST_TO_GEOLOCATION
foreign key (GEOLOCATION_FK)
references GEOLOCATION;