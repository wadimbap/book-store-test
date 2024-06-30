### Проект Bookstore API

1. **О проекте:**
   Проект Bookstore API представляет собой RESTful API для управления книгами и авторами в книжном магазине.

2. **Клонирование проекта с GitHub:**
   ```bash
   git clone https://github.com/your-username/bookstore.git
   cd bookstore
   ```

3. **Запуск проекта с использованием Docker Compose:**
   ```bash
   docker-compose up --build -d
   ```

   Эта команда соберет и запустит контейнеры, описанные в файле `docker-compose.yaml`.

4. **Проверка API:**
   
   - **Postman:**
     - Установите Postman, если он не установлен.
     - Импортируйте коллекцию запросов для API или создайте новые запросы:
       - **GET** запросы:
         - Получить всех авторов: `GET http://localhost:8080/api/author`
         - Получить автора по ID: `GET http://localhost:8080/api/author/{authorId}`
         - Получить все книги автора по ID: `GET http://localhost:8080/api/author/books/{authorId}`
         - Получить все книги: `GET http://localhost:8080/api/book`
         - Получить книгу по ID: `GET http://localhost:8080/api/book/{bookId}`
         - Получить отфильтрованные книги: `GET http://localhost:8080/api/book/filter?title={title}&isbn={isbn}&authorId={authorId}`
       - **POST** запросы:
         - Сохранить автора: `POST http://localhost:8080/api/author`
           - Тело запроса (JSON): `{ "name": "Имя автора" }`
         - Сохранить книгу: `POST http://localhost:8080/api/book`
           - Тело запроса (JSON): `{ "title": "Название книги", "isbn": "ISBN номер" }`
       - **PUT** запросы:
         - Обновить данные автора: `PUT http://localhost:8080/api/author/{authorId}`
           - Тело запроса (JSON): `{ "name": "Новое имя автора" }`
         - Обновить данные книги: `PUT http://localhost:8080/api/book/{bookId}`
           - Тело запроса (JSON): `{ "title": "Новое название книги", "isbn": "Новый ISBN номер" }`
       - **DELETE** запросы:
         - Удалить автора: `DELETE http://localhost:8080/api/author/{authorId}`
         - Удалить книгу: `DELETE http://localhost:8080/api/book/{bookId}`
   
   - **curl:**
     - Используйте команды `curl` для выполнения HTTP-запросов напрямую из командной строки Linux:
       - Пример GET запроса для получения всех авторов:
         ```bash
         curl http://localhost:8080/api/author
         ```
       - Пример POST запроса для сохранения автора:
         ```bash
         curl -X POST -H "Content-Type: application/json" -d '{ "name": "Имя автора" }' http://localhost:8080/api/author
         ```

   После успешного запуска и проверки API вы можете начать использовать и тестировать функциональность вашего проекта Bookstore API.
