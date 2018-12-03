package lettcode.easy;

/**
 *  You are playing the following Nim Game with your friend: There is a heap of stones on the table, each time one of you take turns to remove 1 to 3 
 * stones. The one who removes the last stone will be the winner. You will take the first turn to remove the stones.
 *
 *	Both of you are very clever and have optimal strategies for the game. Write a function to determine whether you can win the game given the 
 * number of stones in the heap.
 *
 *	For example, if there are 4 stones in the heap, then you will never win the game: no matter 1, 2, or 3 stones you remove, the last stone 
 * will always be removed by your friend. 
 *
 *	关键:Both of you are very clever and have optimal strategies for the game
 *	经过推算，4的倍数的时候一定输，不是4的倍数一定赢。
 * @author ChenJingShuai 7 Jul 2016
 *
 */
public class NimGame {
	
	public boolean canWinNim(int n) {
		return n < 4 || n % 4 != 0;
    }
	/**
	 * optimization for the former
	 * @param n
	 * @return
	 */
	public boolean canWinNim2(int n) {
		return n % 4 != 0;
    }
}
