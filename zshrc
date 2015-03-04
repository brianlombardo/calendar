source "$HOME/.home/profile"

HISTFILE="$HOME/.zsh_history"
HISTSIZE=10000
SAVEHIST=10000

setopt interactive_comments
setopt noautomenu
setopt nomenucomplete
setopt PROMPT_SUBST
setopt SHARE_HISTORY

if type brew &>/dev/null; then
  FPATH="`brew --prefix`/share/zsh-completions:$FPATH"
fi
autoload -Uz compinit
compinit -u
autoload -Uz vcs_info
function precmd { vcs_info; }
zstyle ':vcs_info:git:*' formats '(%b)'

bindkey "^[[1;5C" forward-word
bindkey "^[[1;5D" backward-word
bindkey "^[[H" beginning-of-line
bindkey "^[[F" end-of-line
bindkey "^[[3~" delete-char
bindkey "^U" backward-kill-line
bindkey -e

PROMPT='%F{$PROMPT_COLOR}%n %F{$PROMPT_ACCENT}%m zsh %* %f%F{$PROMPT_COLOR}%~ %F{$PROMPT_ACCENT}${vcs_info_msg_0_}
%F{$PROMPT_COLOR}$PROMPT_ICON %f%F{$PROMPT_BODY}'
