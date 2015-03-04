#[cfg(test)]
mod tests {
  use Ordering::{Equal, Greater, Less};
  use std::cmp::Ordering;

  use rand::Rng;
  use tailcall::tailcall;

  #[test]
  fn shed() {
    rng(1234);
    let test_string = "my string".to_string();
    assert_eq!((test_string.clone(), 9), multiple_return(test_string));
    assert_eq!(4, ref_param(&"test".to_string()));
    let mut mut_string = "hello".to_string();
    change_mut_string(&mut mut_string);
    assert_eq!("hello, world", mut_string);
    assert_eq!(0, ranger(1, 100));
    assert_eq!("string".to_string(), slicer(&"my string".to_string(), 3, 9));
    assert_eq!("asdf", longest_lifetime("asd", "asdf"));
  }

  #[test]
  fn factorial_works() {
    assert_eq!(1, factorial(1));
    assert_eq!(2, factorial(2));
    assert_eq!(6, factorial(3));
    assert_eq!(24, factorial(4));
    assert_eq!(120, factorial(5));
    assert_eq!(87178291200, factorial(14));
    assert_eq!(1, factorial_safe(1));
    assert_eq!(2, factorial_safe(2));
    assert_eq!(6, factorial_safe(3));
    assert_eq!(24, factorial_safe(4));
    assert_eq!(120, factorial_safe(5));
    assert_eq!(87178291200, factorial_safe(14));
  }

  #[test]
  fn factorial_safe_does_not_overflow() { // it does...
    assert!(87178291200 < factorial_safe(12345))
  }

  fn multiple_return(s: String) -> (String, usize) {
    let len = s.len();
    (s, len)
  }

  fn ref_param(s: &String) -> usize {
    s.len()
  }

  fn change_mut_string(s: &mut String) {
    s.push_str(", world");
  }

  fn ranger(start: u64, end: u64) -> u64 {
    let mut total = 0;
    for z in start..end { total += z; }
    for z in (start..end).rev() { total -= z; }
    total
  }

  fn slicer(s: &str, start: usize, end: usize) -> &str {
    &s[start..end]
  }

  fn factorial(n: u64) -> u64 {
    if n > 1 {
      n * factorial(n - 1)
    } else {
      1
    }
  }

  fn factorial_safe(n: u64) -> u64 {
    #[tailcall]
    fn factorial_tail(result: u64, n: u64) -> u64 {
      if n < 2 {
        result
      } else {
        factorial_tail(result * n, n - 1)
      }
    }
    factorial_tail(1, n)
  }

  fn rng(number: u64) {
    match number.cmp(
      &rand::thread_rng()
          .gen_range(1..=100)) {
      Less => println!("Too small!"),
      Greater => println!("Too big!"),
      Equal => println!("You win!"),
    }
  }

  fn longest_lifetime<'lifetime>(x: &'lifetime str, y: &'lifetime str) -> &'lifetime str {
    if x.len() > y.len() {
      x
    } else {
      y
    }
  }
}

