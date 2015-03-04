FROM ubuntu:noble
RUN apt update
RUN apt upgrade -y
RUN apt install gawk git grep tmux vim watch zsh wget curl -y
RUN useradd -m -s /usr/bin/zsh trnqilo
RUN echo source ./.home/zshrc > /home/trnqilo/.zshrc
RUN chown -R trnqilo: /home/trnqilo
ENV TZ="America/Chicago"
USER trnqilo
WORKDIR /home/trnqilo
CMD ["/usr/bin/zsh"]
