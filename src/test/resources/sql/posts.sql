INSERT INTO users(id, email, first_name, last_name, identity_provider, identity_reference, added) VALUES
  ('fdbf6800-e6f2-491b-a8a3-8fe57542d8af', 'jd@dead.com', 'John', 'Deaux', 'google', '795971292936161616', '2013-11-21T01:31:00');

INSERT INTO posts(id, article_id, title, source, added, url, imgurl) VALUES
  ('6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'x98hfu912tan', 'US Election Results', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png');

INSERT INTO post_categories(posts_id, text) VALUES
  ('6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'POLITICS'),
  ('6ace2a00-5b5b-4628-9cb4-6b2da71a0ffb', 'US');
