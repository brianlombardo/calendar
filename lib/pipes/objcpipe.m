///usr/bin/env true; exec blobman objc "$0" $@

#import <Foundation/Foundation.h>

int main (int argc, const char * argv[]) {
  NSAutoreleasePool *pool = [[NSAutoreleasePool alloc] init]; 
  NSMutableString *pipe = [NSMutableString string];
  NSUInteger i;
  char c;

  while ((c = getchar()) != -1) {
    [pipe appendFormat:@"%c", c];
  }

  printf("%s\n", [pipe UTF8String]);
  for (i = 1; i < argc; ++i) { 
    NSString *arg = [NSString stringWithUTF8String:argv[i]]; 
    printf("%s\n", [arg UTF8String]);
  }

  [pool drain];
  return 0;
}
