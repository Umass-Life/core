package api.fitbit_web_api.fitbit_activity.intraday.heart;

import api.fitbit_web_api.fitbit_activity.intraday.AbstractIntradayActivityRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeartActivityRepository extends AbstractIntradayActivityRepository<HeartActivity> {
}
