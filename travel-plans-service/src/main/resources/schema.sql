CREATE TABLE IF NOT EXISTS travel_plan (
                                           plan_id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           user_id BIGINT NOT NULL,
                                           attraction_id BIGINT NOT NULL,
                                           visit_date VARCHAR(255) NOT NULL
    );
