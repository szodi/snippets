//http://www.codeproject.com/Articles/569271/A-Poker-hand-analyzer-in-JavaScript-using-bit-math

hands=["4 of a Kind", "Straight Flush", "Straight", "Flush", "High Card",
       "1 Pair", "2 Pair", "Royal Flush", "3 of a Kind", "Full House" ];
var A=14, K=13, Q=12, J=11, _ = { "♠":1, "♣":2, "♥":4, "♦":8 };
 
//Calculates the Rank of a 5 card Poker hand using bit manipulations.
function rankPokerHand(cs,ss) {
  var v, i, o, s = 1<<cs[0]|1<<cs[1]|1<<cs[2]|1<<cs[3]|1<<cs[4];
  for (i=-1, v=o=0; i<5; i++, o=Math.pow(2,cs[i]*4)) {v += o*((v/o&15)+1);}
  v = v % 15 - ((s/(s&-s) == 31) || (s == 0x403c) ? 3 : 1);
  v -= (ss[0] == (ss[1]|ss[2]|ss[3]|ss[4])) * ((s == 0x7c00) ? -5 : 1);
  document.write("Hand: " + hands[v] + (s == 0x403c?" (Ace low)":"")+"<br/>");
}
 
rankPokerHand([10, J, Q, K, A], [ _["♠"], _["♠"], _["♠"], _["♠"], _["♠"] ] ); // Royal Flush
rankPokerHand([ 4, 5, 6, 7, 8], [ _["♠"], _["♠"], _["♠"], _["♠"], _["♠"] ] ); // Straight Flush
rankPokerHand([ 2, 3, 4, 5, A], [ _["♠"], _["♠"], _["♠"], _["♠"], _["♠"] ] ); // Straight Flush
rankPokerHand([ 8, 8, 8, 8, 9], [ _["♠"], _["♣"], _["♥"], _["♦"], _["♠"] ] ); // 4 of a Kind
rankPokerHand([ 7, 7, 7, 9, 9], [ _["♠"], _["♣"], _["♥"], _["♠"], _["♣"] ] ); // Full house
rankPokerHand([10, J, 6, K, 9], [ _["♣"], _["♣"], _["♣"], _["♣"], _["♣"] ] ); // Flush
rankPokerHand([10, J, Q, K, 9], [ _["♠"], _["♣"], _["♥"], _["♣"], _["♦"] ] ); // Straight
rankPokerHand([ 2, 3, 4, 5, A], [ _["♠"], _["♣"], _["♥"], _["♣"], _["♦"] ] ); // Straight
rankPokerHand([ 4, 4, 4, 8, 9], [ _["♠"], _["♣"], _["♥"], _["♠"], _["♣"] ] ); // 3 of a Kind
rankPokerHand([ 8, 8, J, 9, 9], [ _["♠"], _["♣"], _["♥"], _["♠"], _["♣"] ] ); // 2 Pair
rankPokerHand([ 8, 8, 3, 5, 9], [ _["♠"], _["♣"], _["♥"], _["♠"], _["♣"] ] ); // 1 Pair
rankPokerHand([10, 5, 4, 7, 9], [ _["♠"], _["♣"], _["♥"], _["♠"], _["♣"] ] ); // High Card