# Отчёт о проведённой автоматизации

## 1. Что было запланировано и что реализовано  
Запланировано автоматизировать тестирование формы оплаты тура, включая позитивные и негативные сценарии.  
Реализовано **8 автотестов**, покрывающих основные варианты ввода данных.

## 2. Причины, по которым что-то не было реализовано  
Все запланированные тесты успешно реализованы. Дополнительные сценарии, такие как нагрузочное тестирование, не входили в задачу.

## 3. Сработавшие риски  
### Проблемы с тестовыми данными  
- В задании был указан тестовый номер карты со статусом **"Declined"**, однако в UI **оплата проходила успешно**, хотя в логах отображался корректный статус **"Declined"**.   
- Данный риск привёл к необходимости дополнительного анализа логов и уточнения требований.  

### Человеческий фактор (недостаточная квалификация)  
- Из-за недостаточного опыта в интеграции инструментов, таких как **Docker, Gradle, взаимодействие с базой данных**, потребовалось **значительно больше времени** на изучение документации и устранение возникающих проблем.  
- Основные затруднения возникли при:  
  - Настройке окружения и подключении Docker-контейнеров.  
  - Разборе логики работы Gradle и тестового стенда.  
  - Интеграции различных компонентов (тестов, базы данных, UI).  
- Это увеличило общее время выполнения работы, но позволило получить практический опыт работы с этими инструментами.  

### Временные ограничения и задержки  
- Из-за необходимости изучения дополнительных технологий и устранения ошибок процесс автоматизации занял больше времени, чем планировалось.  
- При планировании не были учтены возможные сложности с подключением сервисов и сред разработки, что привело к увеличению сроков.   

## 4. Временные затраты  
- Запланировано: **2-3 дня** на написание тестов и запуск.  
- Фактически затрачено: **около 3-4 дней**.  
- Расхождение связано со значительной тратой времени на поиск и устранение ошибок в коде и настройках окружения.  
