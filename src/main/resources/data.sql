-- =========================
-- DEMO DATA for Investment Fund ISS
-- Tables: investors, funds, assets, transactions
-- =========================

TRUNCATE TABLE transactions RESTART IDENTITY CASCADE;
TRUNCATE TABLE funds        RESTART IDENTITY CASCADE;
TRUNCATE TABLE assets       RESTART IDENTITY CASCADE;
TRUNCATE TABLE investors    RESTART IDENTITY CASCADE;

-- --------
-- INVESTORS
-- --------
insert into investors (email, full_name, phone) values
('ivanov@mail.ru',  'Иванов Иван Иванович',  '89771234567'),
('petrov@mail.ru',  'Петров Петр Петрович',  '79001234567'),
('sidorov@mail.ru', 'Сидоров Сергей Сергеевич', '89261230000'),
('smirnova@mail.ru','Смирнова Анна Олеговна', '79995554433'),
('kuz@mail.ru',     'Кузнецов Дмитрий Андреевич', '89160001122'),
('renat@mail.ru',   'Ренат Юнисов', '89770000021'),
('renat2@mail.ru',  'Renat Iunisov', '79770000022');

-- -----
-- FUNDS
-- -----
insert into funds (created_date, currency, name, nav, risk_level, strategy) values
(date '2024-01-10', 'RUB', 'Фонд Рост',        1050.25, 'MEDIUM', 'Акции РФ + рост капитала'),
(date '2024-02-05', 'RUB', 'Фонд Облигации',    980.10, 'LOW',    'Консервативные облигации'),
(date '2024-03-20', 'USD', 'Фонд Global',      112.55,  'HIGH',   'Акции США и мира'),
(date '2024-04-01', 'RUB', 'Фонд Сбалансированный', 1015.00, 'MEDIUM', '50/50 акции и облигации'),
(date '2024-05-15', 'EUR', 'Фонд Europe',      98.75,   'MEDIUM', 'Европейский рынок');

-- ------
-- ASSETS
-- ------
insert into assets (currency, name, price, sector, ticker, type) values
('RUB', 'Сбербанк',           285.40, 'Финансы',      'SBER', 'STOCK'),
('RUB', 'Газпром',            162.10, 'Энергетика',   'GAZP', 'STOCK'),
('RUB', 'Яндекс',             4125.0, 'IT',           'YNDX', 'STOCK'),
('RUB', 'ОФЗ 26238',          101.20, 'Госдолг',      'OFZ26238', 'BOND'),
('RUB', 'ОФЗ 26233',          99.80,  'Госдолг',      'OFZ26233', 'BOND'),
('USD', 'Apple',              195.12, 'Technology',   'AAPL', 'STOCK'),
('USD', 'Microsoft',          412.30, 'Technology',   'MSFT', 'STOCK'),
('USD', 'US Treasury 10Y',    97.50,  'Gov',          'UST10Y', 'BOND'),
('EUR', 'Siemens',            168.70, 'Industry',     'SIE',  'STOCK'),
('EUR', 'Bund 10Y',           98.10,  'Gov',          'BUND10Y', 'BOND');

-- ------------
-- TRANSACTIONS
-- type: DEPOSIT / WITHDRAW
-- ------------
insert into transactions (amount, comment, tx_date, type, fund_id, investor_id) values
(150000.00, 'Первичное пополнение', date '2024-06-01', 'DEPOSIT', 1, 1),
(50000.00, 'Пополнение',          date '2024-06-10', 'DEPOSIT', 1, 2),
(200000.00, 'Инвестиция',          date '2024-06-15', 'DEPOSIT', 2, 3),
(25000.00, 'Частичный вывод',     date '2024-06-20', 'WITHDRAW',2, 3),

(100000.00, 'Вход в фонд',        date '2024-07-02', 'DEPOSIT', 3, 4),
(30000.00, 'Докупка',            date '2024-07-05', 'DEPOSIT', 3, 4),
(15000.00, 'Вывод прибыли',      date '2024-07-20', 'WITHDRAW',3, 4),

(120000.00, 'Инвестиция',         date '2024-08-01', 'DEPOSIT', 4, 5),
(40000.00, 'Пополнение',         date '2024-08-10', 'DEPOSIT', 4, 1),
(10000.00, 'Вывод',              date '2024-08-22', 'WITHDRAW',4, 1),

(70000.00,  'Старт',              date '2024-09-01', 'DEPOSIT', 5, 2),
(20000.00,  'Докупка',            date '2024-09-12', 'DEPOSIT', 5, 2),
(15000.00,  'Частичный вывод',    date '2024-09-25', 'WITHDRAW',5, 2),

(100000.00, 'Демо операция',      date '2024-10-01', 'DEPOSIT', 1, 6),
(50000.00, 'Демо вывод',         date '2024-10-10', 'WITHDRAW',1, 7);
