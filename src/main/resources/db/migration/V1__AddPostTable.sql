CREATE TABLE users (
  id                 UUID PRIMARY KEY,
  identity_provider  TEXT NOT NULL,
  identity_reference TEXT,
  first_name         TEXT NOT NULL,
  last_name          TEXT,
  email              TEXT NOT NULL,
  added              TIMESTAMP NOT NULL
);

CREATE TABLE posts (
  id         UUID PRIMARY KEY,
  article_id TEXT      NOT NULL,
  title      TEXT      NOT NULL,
  source     TEXT,
  added      TIMESTAMP NOT NULL,
  url        TEXT,
  imgUrl     TEXT
);

CREATE TABLE post_categories (
  id UUID PRIMARY KEY,
  posts_id UUID NOT NULL REFERENCES posts (id),
  text     TEXT NOT NULL
);
