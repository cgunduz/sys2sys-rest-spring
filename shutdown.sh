#bin/bash!

ps aux | grep "spring" | grep -v grep | tr -s " " | cut -d " " -f 2 | xargs kill -9
