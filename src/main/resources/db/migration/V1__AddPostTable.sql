
CREATE TABLE users (
  id                 SERIAL PRIMARY KEY,
  identity_provider  TEXT      NOT NULL,
  identity_reference TEXT,
  first_name         TEXT      NOT NULL,
  last_name          TEXT,
  email              TEXT      NOT NULL,
  added              TIMESTAMP NOT NULL
);

CREATE TABLE posts (
  id         SERIAL PRIMARY KEY,
  article_id TEXT      NOT NULL,
  title      TEXT      NOT NULL,
  source     TEXT,
  added      TIMESTAMP NOT NULL,
  url        TEXT,
  img_url    TEXT
);

CREATE TABLE post_categories (
  id       SERIAL PRIMARY KEY,
  posts_id SERIAL NOT NULL REFERENCES posts (id),
  text     TEXT   NOT NULL
);
