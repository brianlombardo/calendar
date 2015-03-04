use crate::vending_machine::vendor::Vendor;

mod vending_machine;

fn main() {
  let vendor = Vendor::new();
  for item in vendor.menu() {
    println!("{}", item);
  }
  println!("{}",
    if vendor.service() {
      "vending machine has been serviced."
    } else {
      "service failed."
    }
  )
}
