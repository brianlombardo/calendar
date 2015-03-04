source "$HOME/.home/profile"

BASH_PROMPT_COLOR=`ansicode $PROMPT_COLOR`
BASH_PROMPT_ACCENT=`ansicode $PROMPT_ACCENT`
BASH_PROMPT_BODY=`ansicode $PROMPT_BODY`
[[ -r "/usr/local/etc/profile.d/bash_completion.sh" ]] && . "/usr/local/etc/profile.d/bash_completion.sh"
if type __git_ps1 &> /dev/null; then GITPS1CMD=' $(__git_ps1 "(%s)")'; fi
export PS1="\[\e[$BASH_PROMPT_COLOR\]\u\[\e[m\] \[\e[$BASH_PROMPT_ACCENT\]\h\[\e[m\] \[\e[$BASH_PROMPT_ACCENT\]bash\[\e[m\] \[\e[$BASH_PROMPT_ACCENT\]\t\[\e[$BASH_PROMPT_COLOR\] \w\[\e[$BASH_PROMPT_ACCENT\]$GITPS1CMD
\[\e[$BASH_PROMPT_COLOR\]$PROMPT_ICON \[\e[m\]\[\e[$BASH_PROMPT_BODY\]"
export GIT_PS1_SHOWDIRTYSTATE=true
unset HISTCONTROL
