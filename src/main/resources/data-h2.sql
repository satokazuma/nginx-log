INSERT INTO ranceworks.access_log(
  date_gmt, 
  time_gmt, 
  city, 
  country_code, 
  status_code, 
  uri, 
  remote_addr, 
  http_referer, 
  http_user_agent,
  store_time) VALUES 
('2016-11-23', '20:23:22', 'Tokyo','JPN', '200','/index.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now()),
('2016-11-23', '20:23:22', 'Paris','FRA', '200','/index.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now()),
('2016-11-24', '20:23:22', 'oslo','NOR', '200','/footer.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now()),
('2016-11-25', '20:23:22', 'Paris','FRA', '200','/index.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now()),
('2016-11-26', '20:23:22', 'Paris','FRA', '200','/index.html', '45.24.103.152','-','Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)', now());