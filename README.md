twitter-feeder
==============

Console tool to call Twitter Stream API.

Interactive command line tool to call statuses/filter method.
- Allows to enter list of key words, through command line input.
- Waits for input while stream is being readed, on input pauses stream processing.
- Alows to reconnect to stream with updated keywords.
- Terminates execution if unsuccesful http status received. 

To compile 
mvn compile

To start execution
mvn exec:java