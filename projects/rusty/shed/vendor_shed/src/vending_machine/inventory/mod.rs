pub mod snacks;
pub mod beverages;

pub trait Inventory {
  fn restock(&self) -> bool;
  fn items(&self) -> Vec<String>;
}
