use crate::vending_machine::inventory::Inventory;

pub struct Beverages;

impl Inventory for Beverages {
  fn restock(&self) -> bool {
    true
  }

  fn items(&self) -> Vec<String> {
    vec![
      "rustc cola".to_string(),
      "mr pub".to_string(),
      "vec".to_string(),
    ]
  }
}
