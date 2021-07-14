package lv.savchuk.weatherbyip.mapper;

public interface ModelMapper<FROM, TO> {

	TO mapFrom(FROM data);

}