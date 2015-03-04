typedef struct PlaySpace {
    int x;
    int y;
} PlaySpace;

int area(PlaySpace *playSpace) {
  return playSpace->x * playSpace->y;
}

int fortyTwo() {
  return 42;
}