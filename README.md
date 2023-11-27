<p align="center">
  <a href="https://github.com/danieljancar/ransomware-encrypter-java" target="blank"><img src="static/lock-red.png" width="200" alt="Red Lock" /></a>
</p>
<p align="center">This Java application simulates the process of ransomware encryption, providing a safe and controlled environment for educational purposes in cybersecurity.</p>

# Java Ransomware Encryption Simulator

## Key Features
- Creates files with random data.
- Secures files using AES-256 encryption.
- Various operational modes: creation, encryption, or both, with an additional help guide.

## Prerequisites
- Java 8 or higher.

## Getting Started
1. Ensure Java is installed on your system.
2. Download the project from [GitHub](https://github.com/danieljancar/ransomware-encrypter-java).

## Usage
The application can be operated through the command line with the following commands:

1. **Compile the Program**:
```bash
javac src/main/java/dev/danieljancar/App.java
```

2. **Run the Program**:
- General Syntax:
  ```bash
  java -cp src/main/java dev.danieljancar.App <mode> <args>
  ```
- To Create Files:
  ```bash
  java -cp src/main/java dev.danieljancar.App create <path> <amount> <file extension>
  ```
- To Encrypt Files:
  ```bash
  java -cp src/main/java dev.danieljancar.App encrypt <path> <key>
  ```
- To Create and Encrypt Files:
  ```bash
  java -cp src/main/java dev.danieljancar.App both <path> <amount> <file extension> <key>
  ```
- For Help:
  ```bash
  java -cp src/main/java dev.danieljancar.App help
  ```

## Technical Details
- **Encryption Algorithm**: AES-256
- Intended for educational insights into file encryption.

## Disclaimer
This is an educational tool. Do not use it on unauthorized systems.

## License
This project is open-source, under the MIT License.
