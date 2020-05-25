-- 1호선
insert into STATION (name) values ('수원');
insert into STATION (name) values ('화성');
insert into STATION (name) values ('성균관대');

-- 2호선
insert into STATION (name) values ('교대');
insert into STATION (name) values ('강남');
insert into STATION (name) values ('역삼');
insert into STATION (name) values ('삼성');
insert into STATION (name) values ('잠실');

insert into LINE (name, start_time, end_time, interval_time) values ('1호선', '06:00:00', '18:00:00', '1');
insert into LINE (name, start_time, end_time, interval_time) values ('2호선', '07:00:00', '19:00:00', '2');
insert into LINE (name, start_time, end_time, interval_time) values ('3호선', '08:00:00', '20:00:00', '3');
insert into LINE (name, start_time, end_time, interval_time) values ('4호선', '09:00:00', '21:00:00', '4');
insert into LINE (name, start_time, end_time, interval_time) values ('5호선', '10:00:00', '22:00:00', '5');

insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (1, 1, null, 10, 10);
insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (1, 2, 1, 10, 10);
insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (1, 3, 2, 10, 10);

insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (2, 4, null, 10, 10);
insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (2, 5, 4, 10, 10);
insert into LINE_STATION (line, station_id, pre_station_id, distance, duration) values (2, 6, 5, 10, 10);