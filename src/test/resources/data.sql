insert into author (id, `name`) values (1, 'Kristi');
insert into author (id, `name`) values (2, 'Shakespeare');
insert into author (id, `name`) values (3, 'Prishvin');

insert into genre (id, `name`) values (2, 'detective');
insert into genre (id, `name`) values (3, 'piece');
insert into genre (id, `name`) values (4, 'novella');

insert into book (`name`, authorid, genreid) values
  ( 'Puaro', 1, 2)       , ( 'Puaro. Part1', 1, 2)
, ( 'Puaro. Part2', 1, 2), ( 'Puaro. Part3', 1, 2), ( 'Puaro. Part4', 1, 2)
, ( 'Puaro. Part5', 1, 2), ( 'Puaro. Part6', 1, 2), ( 'Puaro. Part7', 1, 2)
, ( 'Puaro. Part8', 1, 2), ( 'Puaro. Part9', 1, 2), ( 'Puaro. Part10', 1, 2)

, ( 'piece. Part1', 2, 3), ( 'piece. Part2', 2, 3), ( 'piece. Part3', 2, 3)

, ( 'novella. Part1', 3, 4), ( 'novella. Part2', 3, 4), ( 'novella. Part3', 3, 4)
;

insert into bookcomment(comment, bookid) values ('comment11', 2)
,('comment12', 2)
,('comment13', 2)
,('comment14', 2)
,('comment15', 2)
,('comment16', 2)
,('comment17', 2)
,('comment31', 3)
,('comment32', 3)
,('comment33', 3)
,('comment34', 3)
,('comment35', 3)
,('comment36', 3)