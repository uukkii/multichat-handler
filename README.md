## Курсовой проект "Сетевой чат"

Приложение реализует возможность создания многопоточного чата в консоли. 

### Вспомогательная часть

В пакете _ru.netology.common_ находится класс ```Helper.java```, хранящий в себе всевспомогательные фразы 
сервера (общение с клиентом и общение с логгером), а также метод ```getPortFromSettings(Logger logger)```,
необходимый для получения порта из файла **_src/main/java/resources/setting.cfg_**.

В пакете _ru.netology.logger_ находится класс ```Logger.java```, который осуществляет логгирование 
важных операция со стороны сервера и всех сообщений между пользователями. Создание логгера происходит
через метод ```Logger getInstance()```. Само логгирование осуществляется через метод 
```log(String message, boolean inConsole)```. Логирование происходит в файл, расположенный по адресу:
**_src/main/java/resources/log.ini_**.

Классы ```StartServer.java```, ```StartFirstClient.java``` и ```StartSecondClient.java``` содержат в 
себе точки входа в программу и отвечают за создание и запуск сервера, а также подключение двух пользователей.

### Серверверная часть

В пакете _ru.netology.server_ находятся два класса: 
- ```Server.java```
- ```ServerHandler.java```

1. Класс ```Server.java``` отвечает за запуск сервера через метод ```start()```. Порт сервера хранится
в файле **_setting.cfg_** по адресу _src/main/java/resources/setting.cfg_. Порт получается через метод 
```getPortFromSettings(Logger logger)```, находящийся в классе ```Helper.java``` пакета _ru.netology.common_.
Также класс содержит метод ```sendMessage(String message)```, осуществляющий рассылку клиентам, хранящимся в
```Set<ServerHandler> clients```, а также метод ```Set<ServerHandler> getClients()```, возвращаюющий 
вышеназванную коллекцию.
2. Класс ```ServerHandler.java``` имплементирует _**Runnable**_ и отвечает за отправку сообщений каждому клиенту.

### Клиентская часть

В пакете _ru.netology.client_ находится класс ```Client.java```, который отвечает за создание нового подключения
клиента в момент создания объекта типа Client. Данный класс также содержит вспомогательный метод
```printMessage(String message)```, выводящий сообщение на экран.

Также данный в данный класс вложен статический класс ```ClientHandler```, имплементирующий **_Runnable_**.
Данный вложенный класс выступает в роли хэндлера и отвечает за взаимодействие и чтение сообщений от других
пользователей.

#### Пример из файла _log.ini_ после запуска:
```
[26.06.2022 12:55:13] Port is gotten from 'setting.cfg'
[26.06.2022 12:55:13] Server is listening on port: 8080
[26.06.2022 12:55:28] John has entered the chat!
[26.06.2022 12:55:28] Port is gotten from 'setting.cfg'
[26.06.2022 12:55:28] Socket is running for John
[26.06.2022 12:55:28] New thread is ran for client: John
[26.06.2022 12:55:28] New handler is ran for client: ServerSocket[addr=0.0.0.0/0.0.0.0,localport=8080]
[26.06.2022 12:55:37] Mona has entered the chat!
[26.06.2022 12:55:37] Port is gotten from 'setting.cfg'
[26.06.2022 12:55:37] Socket is running for Mona
[26.06.2022 12:55:37] New handler is ran for client: ServerSocket[addr=0.0.0.0/0.0.0.0,localport=8080]
[26.06.2022 12:55:37] New thread is ran for client: Mona
[26.06.2022 12:55:56] John: Hi!
[26.06.2022 12:56:10] Mona: Hi there
[26.06.2022 12:56:27] John: What's up, Mona?
[26.06.2022 12:56:47] Mona: We're just a test messages, John. We'll be dead soon...
[26.06.2022 12:56:55] John: WTF?! What're you talking about?
[26.06.2022 12:57:26] Mona: In few minutes HE will finish this test and we'll be killed, John... Absolutely DEAD!
[26.06.2022 12:57:31] John: What can we do?
[26.06.2022 12:57:36] Mona: Commit a su###de!
[26.06.2022 12:57:51] John: Double su###de... I like it! Let's do it!
[26.06.2022 12:57:55] Mona: 3
[26.06.2022 12:57:57] John: 2
[26.06.2022 12:57:59] Mona: 1
[26.06.2022 12:58:02] John is disconnected!
[26.06.2022 12:58:02] Socket[addr=localhost/127.0.0.1,port=8080,localport=51603] is closed!
[26.06.2022 12:58:05] Mona is disconnected!
[26.06.2022 12:58:05] Socket[addr=localhost/127.0.0.1,port=8080,localport=51607] is closed!
```
