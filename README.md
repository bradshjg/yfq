# yfq

A Clojure library that returns a stock data (through Yahoo) for a given date,
or over an arbitrary range 

This is in no way affiliated with Yahoo, or something equally legal sounding

## Usage

hist-quote [sym date] returns a stock data map for a given date (not formatted well, yet)
sym is the stock symbol as a string (e.g. "GOOG" for Google)
date is the date as a string in the format mm/dd/yyyy or mm-dd-yyyy (e.g. "12/19/1986")

hist-range [sym date1 date2] returns a data map for a given range (formatted even worse)

## License

Copyright © 2013 James Bradshaw <james.g.bradshaw@gmail.com>

Distributed under the GNU GPL Version 3.
