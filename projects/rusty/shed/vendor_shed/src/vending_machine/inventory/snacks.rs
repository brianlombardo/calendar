use crate::vending_machine::inventory::Inventory;

pub struct Snacks;

impl Inventory for Snacks {
  fn restock(&self) -> bool {
    true
  }

  fn items(&self) -> Vec<String> {
    vec![
      "rusty ranchers".to_string(),
      "rxffles".to_string(),
      "cargos".to_string(),
    ]
  }
}
