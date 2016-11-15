INSERT INTO users(id, email, first_name, last_name, identity_provider, identity_reference, added) VALUES
  (1, 'jd@dead.com', 'John', 'Deaux', 'google', '795971292936161616', '2013-11-21T01:31:00');

INSERT INTO posts(id, article_id, title, source, added, url, img_url) VALUES
  (1, 'x98hfu912tan', 'US Election Results', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png'),
  (2, 'y22h2d412555', 'UK Does Brexit', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png'),
  (3, 'zcvhfqwerqwe', 'What next?', 'reddit', '2014-11-21T01:31:00', 'http://reddit.com/us-election-results', 'http://reddit.com/us-election-results/thumb.png');

INSERT INTO post_categories(id, posts_id, text) VALUES
  (1, 1, 'POLITICS'),
  (2, 1, 'US');
