# devtime

## setup
+ install packages `bash gawk git grep tmux vim watch coreutils gnu-sed`
+ add `devtime` script to PATH
+ optional: init repo `cd && git clone my_repo_url .devtime`
+ optional: change devtime dir `export DEVTIME="/path/to/.devtime"`

## run
+ start session `devtime`
+ start a new task `> ... task name` or `> ... 12:34 task`
+ enter a note `> - note text` or `> note text`
  - indent note `> --- triple indent note text`
  * add a quest `> ? todo: solve me`
+ take a break `> ... afk` or `> ... 23:45 afk`
+ use vim to open log `> #` or workspace `> ##`
+ open workspace in vscode `> ###`
+ remove last line `> pop`, or n lines `> pop pop pop`
+ tag words `> /tag epic story spike`, edit tags `> /tag`
+ highlight words using `> #these @will +be !highlighted`
+ finish the day `> gg` or `> gg 23:45`
+ leave session `> qq` or `ctrl+c`

## info
+ print hours by task for all days `devtime tasks`
+ print hours by task for current week `devtime week`
+ print total hours for all weeks `devtime weeks`
+ print total hours for all days `devtime forever`
+ print all logs and stats `devtime report`
+ validate all logs `devtime validate`

## questions
+ should devtime be used in production?
  + no
+ are the calculations in devtime accurate?
  + probably, use the validator
+ is the validator accurate?
  + probably
+ does devtime work past midnight?
  + untested. there may be consequences regardless
