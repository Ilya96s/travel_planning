package ru.jg.travelplans.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.jg.travelplans.model.TravelPlan;

public interface TravelPlanRepository extends JpaRepository<TravelPlan, Long> {
}
