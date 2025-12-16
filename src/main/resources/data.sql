-- =========================
-- DEMO DATA for Investment Fund ISS
-- Tables: investors, funds, assets, transactions
-- =========================

-- --------
-- INVESTORS
-- --------
insert into investors (id, email, full_name, phone) values
(1, 'ivanov@mail.ru',  'Иванов Иван Иванович',  '89771234567'),
(2, 'petrov@mail.ru',  'Петров Петр Петрович',  '79001234567'),
(3, 'sidorov@mail.ru', 'Сидоров Сергей Сергеевич', '89261230000'),
(4, 'smirnova@mail.ru','Смирнова Анна Олеговна', '79995554433'),
(5, 'kuz@mail.ru',     'Кузнецов Дмитрий Андреевич', '89160001122'),
(6, 'renat@mail.ru',   'Ренат Юнисов', '89770000021'),
(7, 'renat2@mail.ru',  'Renat Iunisov', '79770000022')
on conflict (id) do nothing;

-- -----
-- FUNDS
-- -----
insert into funds (id, created_date, currency, name, nav, risk_level, strategy) values
(1, date '2024-01-10', 'RUB', 'Фонд Рост',        1050.25, 'MEDIUM', 'Акции РФ + рост капитала'),
(2, date '2024-02-05', 'RUB', 'Фонд Облигации',    980.10, 'LOW',    'Консервативные облигации'),
(3, date '2024-03-20', 'USD', 'Фонд Global',      112.55,  'HIGH',   'Акции США и мира'),
(4, date '2024-04-01', 'RUB', 'Фонд Сбалансированный', 1015.00, 'MEDIUM', '50/50 акции и облигации'),
(5, date '2024-05-15', 'EUR', 'Фонд Europe',      98.75,   'MEDIUM', 'Европейский рынок')
on conflict (id) do nothing;

-- ------
-- ASSETS
-- ------
insert into assets (id, currency, name, price, sector, ticker, type) values
(1, 'RUB', 'Сбербанк',           285.40, 'Финансы',      'SBER', 'STOCK'),
(2, 'RUB', 'Газпром',            162.10, 'Энергетика',   'GAZP', 'STOCK'),
(3, 'RUB', 'Яндекс',             4125.0, 'IT',           'YNDX', 'STOCK'),
(4, 'RUB', 'ОФЗ 26238',          101.20, 'Госдолг',      'OFZ26238', 'BOND'),
(5, 'RUB', 'ОФЗ 26233',          99.80,  'Госдолг',      'OFZ26233', 'BOND'),
(6, 'USD', 'Apple',              195.12, 'Technology',   'AAPL', 'STOCK'),
(7, 'USD', 'Microsoft',          412.30, 'Technology',   'MSFT', 'STOCK'),
(8, 'USD', 'US Treasury 10Y',    97.50,  'Gov',          'UST10Y', 'BOND'),
(9, 'EUR', 'Siemens',            168.70, 'Industry',     'SIE',  'STOCK'),
(10,'EUR', 'Bund 10Y',           98.10,  'Gov',          'BUND10Y', 'BOND')
on conflict (id) do nothing;

-- ------------
-- TRANSACTIONS
-- type: DEPOSIT / WITHDRAW
-- ------------
insert into transactions (id, amount, comment, tx_date, type, fund_id, investor_id) values
(1,  150000.00, 'Первичное пополнение', date '2024-06-01', 'DEPOSIT', 1, 1),
(2,   50000.00, 'Пополнение',          date '2024-06-10', 'DEPOSIT', 1, 2),
(3,  200000.00, 'Инвестиция',          date '2024-06-15', 'DEPOSIT', 2, 3),
(4,   25000.00, 'Частичный вывод',     date '2024-06-20', 'WITHDRAW',2, 3),

(5,   100000.00, 'Вход в фонд',        date '2024-07-02', 'DEPOSIT', 3, 4),
(6,    30000.00, 'Докупка',            date '2024-07-05', 'DEPOSIT', 3, 4),
(7,    15000.00, 'Вывод прибыли',      date '2024-07-20', 'WITHDRAW',3, 4),

(8,   120000.00, 'Инвестиция',         date '2024-08-01', 'DEPOSIT', 4, 5),
(9,    40000.00, 'Пополнение',         date '2024-08-10', 'DEPOSIT', 4, 1),
(10,   10000.00, 'Вывод',              date '2024-08-22', 'WITHDRAW',4, 1),

(11,  70000.00,  'Старт',              date '2024-09-01', 'DEPOSIT', 5, 2),
(12,  20000.00,  'Докупка',            date '2024-09-12', 'DEPOSIT', 5, 2),
(13,  15000.00,  'Частичный вывод',    date '2024-09-25', 'WITHDRAW',5, 2),

(14,  100000.00, 'Демо операция',      date '2024-10-01', 'DEPOSIT', 1, 6),
(15,   50000.00, 'Демо вывод',         date '2024-10-10', 'WITHDRAW',1, 7)
on conflict (id) do nothing;