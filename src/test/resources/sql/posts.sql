INSERT INTO users(id, email, first_name, last_name, identity_provider, identity_reference, added) VALUES
  ('fdbf6800-e6f2-491b-a8a3-8fe57542d8af', 'jd@dead.com', 'John', 'Deaux', 'google', '795971292936161616', '2013-11-21T01:31:00');

INSERT INTO posts(id, article_id, title, source, added, url, imgurl) VALUES
  ('6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'x98hfu912tan', 'US Election Results', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png'),
  ('cce6cd7f-41a6-4fe4-87a5-d2c306e333bb', 'y22h2d412555', 'UK Does Brexit', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png'),
  ('b2febc97-f5e6-481e-a032-7e652004b03f', 'zcvhfqwerqwe', 'What next?', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png');

INSERT INTO post_categories(id, posts_id, text) VALUES
  ('c742059d-f423-4ecf-bb14-33e484cfe527', '6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'POLITICS'),
  ('883d82de-b1c3-4499-a8d0-ea36b149b71a', '6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'US');
