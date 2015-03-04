use crate::vending_machine::inventory::beverages::Beverages;
use crate::vending_machine::inventory::Inventory;
use crate::vending_machine::inventory::snacks::Snacks;

pub struct Vendor {
  items: Vec<Box<dyn Inventory>>,
}

impl Vendor {
  pub fn new() -> Vendor {
    Vendor {
      items: vec![
        Box::new(Beverages {}),
        Box::new(Snacks {}),
      ]
    }
  }

  pub fn menu(&self) -> Vec<String> {
    self.items.iter()
        .flat_map(|inventory|
            inventory.items().into_iter()
        ).clone()
        .collect()
  }

  pub fn service(&self) -> bool {
    for item in &self.items { if !item.restock() { return false; } }
    true
  }
}