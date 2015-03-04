# docker build -t devtime .
# docker run -v "~/.devtime:/home/trnqilo/.devtime:rw" -v "$HOME/.ssh:/home/trnqilo/.ssh:ro" -it devtime
FROM ubuntu:noble
RUN apt update && apt upgrade -y && apt install gawk git grep tmux vim watch zsh -y
RUN useradd -m -s /usr/bin/zsh trnqilo
RUN mkdir -p "/home/trnqilo/.devtime" "/home/trnqilo/.ssh"
RUN echo "export PATH=\"$PATH:/home/trnqilo\"" > /home/trnqilo/.zshrc
RUN chown -R trnqilo: "/home/trnqilo/"
ENV TZ="America/Chicago"
COPY devtime /home/trnqilo/devtime
USER trnqilo
WORKDIR /home/trnqilo
CMD ["/usr/bin/zsh", "-c", "/home/trnqilo/devtime"]
