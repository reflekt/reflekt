*Copied from [here](https://gist.githubusercontent.com/chrisroos/1205934/raw/006e5faa45067c0ac821868dfe9b2a52d617e083/gpg-import-and-export-instructions.md)*

Every so often I have to restore my gpg keys and I'm never sure how best to do it.  So, I've spent some time playing around with the various ways to export/import (backup/restore) keys.

## Method 1

### Backup the public and secret keyrings and trust database

    cp ~/.gnupg/pubring.gpg /path/to/backups/
    cp ~/.gnupg/secring.gpg /path/to/backups/
    cp ~/.gnupg/trustdb.gpg /path/to/backups/
    # or, instead of backing up trustdb...
    gpg --export-ownertrust > chrisroos-ownertrust-gpg.txt

*NOTE* The [GPG manual](http://www.gnupg.org/documentation/manuals/gnupg/GPG-Configuration.html) suggests exporting the ownertrust instead of backing up the trustdb, although it doesn't explain why.

### Restore the public and secret keyrings and trust database

    cp /path/to/backups/*.gpg ~/.gnupg/
    # or, if you exported the ownertrust
    gpg --import-ownertrust chrisroos-ownertrust-gpg.txt

## Method 2

This only really works if you don't mind losing any other keys (than your own).

### Export public and secret key and ownertrust

    gpg -a --export chris@seagul.co.uk > chrisroos-public-gpg.key
    gpg -a --export-secret-keys chris@seagul.co.uk > chrisroos-secret-gpg.key
    gpg --export-ownertrust > chrisroos-ownertrust-gpg.txt

### Import secret key (which contains the public key) and ownertrust

    gpg --import chrisroos-secret-gpg.key
    gpg --import-ownertrust chrisroos-ownertrust-gpg.txt

## Method 3

This is mainly about trusting my key once I've imported it (by either restoring the pubring.gpg and secring.gpg, or by using --import).  This seems to be what I do the most as I either forget to import the trustdb or ownertrust.

### Ultimately trust the imported key

This is so that I can encrypt data using my public key

    gpg --edit-key chris@seagul.co.uk
    gpg> trust
    Your decision? 5 (Ultimate trust)

*NOTE* If I don't trust the public key then I see the following message when trying to encrypt something with it:

    gpg: <key-id>: There is no assurance this key belongs to the named user
