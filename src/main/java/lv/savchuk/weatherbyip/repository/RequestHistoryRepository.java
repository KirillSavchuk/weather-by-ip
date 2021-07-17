package lv.savchuk.weatherbyip.repository;

import lv.savchuk.weatherbyip.model.dao.RequestHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestHistoryRepository extends CrudRepository<RequestHistory, UUID> {
}