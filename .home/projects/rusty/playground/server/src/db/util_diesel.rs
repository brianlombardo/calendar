use diesel::{Connection, PgConnection};

use crate::db::DB_URL;

pub fn connect() -> PgConnection {
  PgConnection::establish(&DB_URL)
      .unwrap_or_else(|_| panic!("Error connecting to {}", DB_URL))
}