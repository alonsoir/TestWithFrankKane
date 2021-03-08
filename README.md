Some scala spark stuff from Frank Kane spark course, an scala script to encrypt avro payloads with jpgpj...

// create pair of keys
gpg --gen-key
//export public key 
gpg --armor --output public-key.gpg --export alonsoir@gmail.com
// export private key
gpg --output secret-key.pgp --armor --export-secret-key alonsoir@gmail.com


gpg --export -a "alonsoir@gmail.com" > public.key

gpg --import gooddata-sso.pub

gpg --list-public-keys

gpg --list-secret-keys

gpg --export-secret-keys B0425938F6BE5480A7FB52E8BDD9F7D5486EAE1C > my-private-key.asc


https://www.techrepublic.com/article/how-to-create-and-export-a-gpg-keypair-on-macos/

https://unix.stackexchange.com/questions/481939/how-to-export-a-gpg-private-key-and-public-key-to-a-file
