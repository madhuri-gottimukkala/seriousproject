INSERT INTO artifacts(isbn, type, genre, authors, title, original_title, subtitle, description, publishers, published_on, item_price, quantity, total_quantity, rack_location, created_on, thumbnail_link, total_loans) VALUES
	('9780743269513', 'book', 'Self-Help', 'Stephen R. Covey', 'The 7 Habits of Highly Effective People', 'The 7 Habits of Highly Effective People', 'Powerful Lessons in Personal Change', 'In The 7 Habits of Highly Effective People, author Stephen R. Covey presents a holistic, integrated, principle-centered approach for solving personal and professional problems. With penetrating insights and pointed anecdotes, Covey reveals a step-by-step pathway for living with fairness, integrity, service, and human dignity—principles that give us the security to adapt to change and the wisdom and power to take advantage of the opportunities that change creates.', 'Free Press', '2004-11-09', 10.00, 1, 3, '420', NOW() + 3, '', 2),
	('9780751532715', 'book', 'Personal Finance', 'Robert T. Kiyosaki, Sharon L. Lechter', 'Rich Dad, Poor Dad', 'Rich Dad, Poor Dad', 'What the Rich Teach Their Kids about Money - that the Poor and Middle Class Do Not!', 'Taking to heart the message that the poor and middle class work for money, but the rich have money work for them, the authors lay out a financial philosophy based on the principle that income-generating assets always provide healthier bottom-line results.', 'Time Warner Books UK', '2000-01-01', 10.00, 0, 1, '69', NOW() + 5, 'http://books.google.com/books/content?id=icFfQgAACAAJ&printsec=frontcover&img=1&zoom=1&source=gbs_api', 1),
	('9780671723651', 'book', 'Business & Economics', 'Dale Carnegie', 'How To Win Friends And Influence People', 'How To Win Friends And Influence People', '', 'You can go after the job you want...and get it! You can take the job you have...and improve it! You can take any situation you''re in...and make it work for you! For over 50 years the rock-solid, time-tested advice in this book has carried thousands of now famous people up the ladder of success in their business and personal lives. Now this phenomenal book has been revised and updated to help readers achieve their maximum potential in the complex and competitive 90s! Learn: The six ways to make people like you The twelve ways to win people to your way of thinking The nine ways to change people without arousing resentment and much, much more!', 'Simon and Schuster', '1982-01-01', 10.00, 2, 2, '6954', NOW() + 6, 'http://books.google.com/books/content?id=0KYWs7EdKYMC&printsec=frontcover&img=1&zoom=1&edge=curl&source=gbs_api', 1),
	('9780374275631', 'book', 'Business & Economics', 'Daniel Kahneman', 'Thinking, Fast and Slow', 'Thinking, Fast and Slow', '', 'A psychologist draws on years of research to introduce his "machinery of the mind" model on human decision making to reveal the faults and capabilities of intuitive versus logical thinking.', 'Macmillan', '2011-10-25', 10.00, 2, 2, '7749', NOW() + 4, 'http://books.google.com/books/content?id=SHvzzuCnuv8C&printsec=frontcover&img=1&zoom=1&source=gbs_api', 1),
	('9780061241895', 'book', 'Business & Economics', 'Robert B. Cialdini', 'Influence', 'Influence', 'The Psychology of Persuasion', 'Dr Robert Cialdini explains the six psychological principles that drive the human impulse to comply to the pressures of others and reveals how to defend oneself against manipulation.', 'ICON Group International', '2007-01-01', 10.00, 2, 2, '9981', NOW() + 2, 'http://books.google.com/books/content?id=E5p5qVbkl1IC&printsec=frontcover&img=1&zoom=1&source=gbs_api', 1),
	('9780307353139', 'book', 'Business & Economics', 'Timothy Ferriss', 'The 4-Hour Work Week', 'The 4-Hour Work Week', 'Escape 9-5, Live Anywhere, and Join the New Rich', 'Offers techniques and strategies for increasing income while cutting work time in half, and includes advice for leading a more fulfilling life.', 'Crown Business', '2007-01-01', 10.00, 0, 1, '339', NOW() + 1, 'http://books.google.com/books/content?id=E3HVzZQh88wC&printsec=frontcover&img=1&zoom=1&source=gbs_api', 2);

INSERT INTO login(email, hash) VALUES
	('admin@ucmo.edu', '$2a$10$4i2J.srVSNmYyB6epuepwOe0jfn4fKzkPjbt2ma.15XSngkvpbTpm'),
	('maya@ucmo.edu', '$2a$10$9d4mara/LTmT97YLHGLU3.8phlLxcCTTuO5LX2PwqGn22jDOjKIWa'),
	('priya@ucmo.edu', '$2a$10$9d4mara/LTmT97YLHGLU3.8phlLxcCTTuO5LX2PwqGn22jDOjKIWa'),
	('mayapriya@ucmo.edu', '$2a$10$9d4mara/LTmT97YLHGLU3.8phlLxcCTTuO5LX2PwqGn22jDOjKIWa'),
	('mp@ucmo.edu', '$2a$10$4i2J.srVSNmYyB6epuepwOe0jfn4fKzkPjbt2ma.15XSngkvpbTpm'),
	('madhuri@ucmo.edu', '$2a$10$4i2J.srVSNmYyB6epuepwOe0jfn4fKzkPjbt2ma.15XSngkvpbTpm'),
	('a@a.com', '$2a$10$vEnY95B1hUYQFhz5TXYxCeAmSO/ZxDC792kGmID0p8LnYwnjAwJIG'),
	('u@u.com', '$2a$10$vEnY95B1hUYQFhz5TXYxCeAmSO/ZxDC792kGmID0p8LnYwnjAwJIG');

INSERT INTO members(full_name, email, mobile_number, address, type, born_on, joined_on, last_active_on, website, roles, login_email) VALUES
	('Admin', 'admin@ucmo.edu', '339', 'Somewhere on Earth', 'librarian', '1998-01-01', NOW(), NOW(), '', 'ADMIN', 'admin@ucmo.edu'),
	('Maya', 'maya@ucmo.edu', '007', 'MI6', 'member', '1998-01-01', NOW(), NOW(), '', 'USER', 'maya@ucmo.edu'),
	('Priya', 'priya@ucmo.edu', '69', 'The High Ground', 'member', '1998-01-01', NOW(), NOW(), '', 'USER', 'priya@ucmo.edu'),
	('Maya Priya', 'mayapriya@ucmo.edu', '1234', 'Silicon Valley', 'member', '1998-01-01', NOW(), NOW(), '', 'USER', 'mayapriya@ucmo.edu'),
	('MP', 'mp@ucmo.edu', '6954', 'Somewhere in the Universe', 'librarian', '1998-01-01', NOW(), NOW(), '', 'ADMIN', 'mp@ucmo.edu'),
	('Madhuri', 'madhuri@ucmo.edu', '7749', 'Somewhere in US', 'librarian', '1998-01-01', NOW(), NOW(), '', 'ADMIN', 'madhuri@ucmo.edu'),
	('A A', 'a@a.com', '7749', 'Somewhere in US', 'librarian', '1998-01-01', NOW(), NOW(), '', 'ADMIN', 'a@a.com'),
	('U U', 'u@u.com', '7749', 'Somewhere in US', 'member', '1998-01-01', NOW(), NOW(), '', 'USER', 'u@u.com');

INSERT INTO loan_history(artifact_id, isbn, member_id, return_on, status, fine, issued_on, was_lost) VALUES
	('1', '9780743269513', '1', NOW() + 3, 'issued', 0.0, NOW(), false),
	('2', '9780751532715', '1', NOW() + 3, 'issued', 0.0, NOW(), false),
	('1', '9780743269513', '2', NOW() + 3, 'issued', 0.0, NOW(), false),
	('6', '9780307353139', '2', NOW() + 3, 'issued', 0.0, NOW(), false);

INSERT INTO loan_history(artifact_id, isbn, member_id, returned_on, status, fine, issued_on, was_lost) VALUES
	('3', '9780671723651', '1', NOW() - 1, 'returned', 0.0, NOW() - 3, false),
	('4', '9780374275631', '1', NOW() - 1, 'returned', 0.0, NOW() - 3, false),
	('5', '9780061241895', '1', '2019-03-06', 'returned', 0.0, '2019-03-03', false),
	('6', '9780307353139', '1', '2019-03-06', 'returned', 0.0, '2019-03-03', false);

INSERT INTO reserve_queue(artifact_id, isbn, member_id, expired_on) VALUES
	('1', '9780743269513', '3', NOW() + 3),
	('6', '9780307353139', '1', NOW() + 3),
	('6', '9780307353139', '3', NOW() + 3),
	('2', '9780751532715', '2', NOW() + 3),
	('2', '9780751532715', '3', NOW() + 3);
