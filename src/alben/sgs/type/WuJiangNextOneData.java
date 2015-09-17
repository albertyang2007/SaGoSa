package alben.sgs.type;

public class WuJiangNextOneData {
	// 0 1 2
	// 3   4
	// 5   6
	// 7
	public static int[][] wjNext_old = { { 3, 5, 7, 6, 4, 2, 1 },
			{ 0, 3, 5, 7, 6, 4, 2 }, { 1, 0, 3, 5, 7, 6, 4 },
			{ 5, 7, 6, 4, 2, 1, 0 }, { 2, 1, 0, 3, 5, 7, 6 },
			{ 7, 6, 4, 2, 1, 0, 3 }, { 4, 2, 1, 0, 3, 5, 7 },
			{ 6, 4, 2, 1, 0, 3, 5 } };

	// 0 1 2 3
	// 4     5
	// 7     6
	public static int[][] wjNext_p = { { 4, 7, 6, 5, 3, 2, 1 },
			{ 0, 4, 7, 6, 5, 3, 2 }, { 1, 0, 4, 7, 6, 5, 3 },
			{ 2, 1, 0, 4, 7, 6, 5 }, { 7, 6, 5, 3, 2, 1, 0 },
			{ 3, 2, 1, 0, 4, 7, 6 }, { 5, 3, 2, 1, 0, 4, 7 },
			{ 6, 5, 3, 2, 1, 0, 4 } };

	// 0 1 2 3 4
	// 5       6
	// 7
	public static int[][] wjNext = { { 5, 7, 6, 4, 3, 2, 1 },
			{ 0, 5, 7, 6, 4, 3, 2 }, { 1, 0, 5, 7, 6, 4, 3 },
			{ 2, 1, 0, 5, 7, 6, 4 }, { 3, 2, 1, 0, 5, 7, 6 },
			{ 7, 6, 4, 3, 2, 1, 0 }, { 4, 3, 2, 1, 0, 5, 7 },
			{ 6, 4, 3, 2, 1, 0, 5 } };
}
