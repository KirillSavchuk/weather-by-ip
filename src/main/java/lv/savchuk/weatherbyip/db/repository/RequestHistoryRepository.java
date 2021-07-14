package lv.savchuk.weatherbyip.db.repository;

import lv.savchuk.weatherbyip.db.entity.RequestHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RequestHistoryRepository extends CrudRepository<RequestHistory, UUID> {
}