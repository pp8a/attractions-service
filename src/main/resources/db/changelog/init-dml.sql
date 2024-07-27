-- Вставка начальных данных
INSERT INTO city (name, population, has_metro) 
VALUES ('City1', 1000000, true) 
ON CONFLICT DO NOTHING;
INSERT INTO city (name, population, has_metro) 
VALUES ('City2', 500000, false) 
ON CONFLICT DO NOTHING;


INSERT INTO attraction (name, created_date, description, type, city_id) 
VALUES ('Attraction1', '2023-01-01', 'Description1', 'PARK', 1) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction (name, created_date, description, type, city_id) 
VALUES ('Attraction2', '2023-02-01', 'Description2', 'MUSEUM', 2) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction (name, created_date, description, type, city_id) 
VALUES ('Attraction3', '2023-02-01', 'Description3', 'MUSEUM', 1) 
ON CONFLICT DO NOTHING;


INSERT INTO service (name, description) 
VALUES ('Service1', 'Description1') 
ON CONFLICT DO NOTHING;
INSERT INTO service (name, description) 
VALUES ('Service2', 'Description2') 
ON CONFLICT DO NOTHING;
INSERT INTO service (name, description) 
VALUES ('Service3', 'Description3') 
ON CONFLICT DO NOTHING;


INSERT INTO attraction_service (attraction_id, service_id) 
VALUES (1, 1) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction_service (attraction_id, service_id) 
VALUES (2, 2) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction_service (attraction_id, service_id) 
VALUES (3, 2) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction_service (attraction_id, service_id) 
VALUES (2, 1) 
ON CONFLICT DO NOTHING;
INSERT INTO attraction_service (attraction_id, service_id) 
VALUES (2, 3) 
ON CONFLICT DO NOTHING;