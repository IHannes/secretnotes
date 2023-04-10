## SecretNotes

### Installation and usage
1. Clone this remote from remote:
    ```bash
    git clone https://github.com/ihannes/secretnotes
    ```
2. First time you launch, do:
    ```bash
    cd ~/secretnotes
    sh launch.sh
    ```
3. To launch again, do:
    ```bash
    cd ~/secretnotes
    java gui
    ```
4. Or else do everything in one step:
 ```bash
    git clone https://github.com/ihannes/secretnotes && cd secretnotes && sh launch.sh
 ```
5. When executing the launch script you're asked to input a filename. Make sure you're saving your file with filename.txt afterwords. Otherwise you'll have issues with the encryption process.
6. For storing a new text file simply run
 ```bash
cd ~/secretnotes && sh launch.sh
```
again and ignore the error codes.
### How it works
This is a basic text editor that allows you to write some text and store it as .txt file. The text you store is encrypted with two encryption modes:
- [AES](https://en.wikipedia.org/wiki/Advanced_Encryption_Standard) (Keysize: 256, ECB padding)
- Custom encryption method based on Letter Substitution

    #### Custom encryption method
    Given an array of characters, the algoritm will determine the index of a letter in the String that is to be encrypted and in the password in that character array. It then returns the Character that is stored at the index of the sum of the String and password index. This is done for every element in the String. Between every two encrypted letters, there are several random letters of whom the number is determined by the index of the previous letter in the defined Character array(This should protect against known-plaintext-attacks, since identical input will produce different output).

#### Please feel free to contact me for questions or contributions regarding this project: hannes.hildner@icloud.com
