#!/bin/sh

# 1. Delete the folder .git/hooks if it exists
if [ -d ".git/hooks" ]; then
    rm -rf .git/hooks
fi

# 2. Create the folder .git/hooks if it is not created
mkdir -p .git/hooks

# 3. Copy all files from git-hooks to .git/hooks except install-git-hooks.sh
find git-hooks -type f ! -name 'install-git-hooks.sh' -exec cp {} .git/hooks/ \;
