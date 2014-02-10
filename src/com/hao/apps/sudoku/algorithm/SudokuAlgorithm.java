package com.hao.apps.sudoku.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


public class SudokuAlgorithm {

	public SudokuAlgorithm() {
		init(9);
	}

	public SudokuAlgorithm(int num) {
		init(num);
	}

	
	private int fieldNum;
	private byte[] layout = null;
	private byte[] outbytes = null;
	private int curPos;
	private Random random = new Random();
	
	private byte[] ansPosArr = null;
	private byte[][] ansArr = null;
	private boolean randomLayout = false;
	
	private String finalSodukuString = null;

	public void setRandomLayout(boolean r) {
		randomLayout = r;
	}
	
	
	public String returnSudokuPuzzleString(){
		return finalSodukuString;
	}

	private void init(int num) {
		
		curPos = 0;
		fieldNum = num;
		if (layout == null)
			layout = new byte[num * num];
		if (outbytes == null)
			outbytes = new byte[(num * num + 1) / 2];
		if (ansPosArr == null)
			ansPosArr = new byte[num * num];
		if (ansArr == null)
			ansArr = new byte[num * num][num];
		for (int i = 0; i < num * num; i++) {
			layout[i] = -1;
			ansPosArr[i] = 0;
			for (int j = 0; j < num; j++)
				ansArr[i][j] = -1;
		}
	}

	private void getAnswer(int pos) {
		for (byte i = 0; i < fieldNum; i++)
			ansArr[pos][i] = i;
		int x = pos / fieldNum, y = pos % fieldNum;
		for (int i = 0; i < fieldNum; i++) {
			if (layout[i * fieldNum + y] != -1)
				ansArr[pos][layout[i * fieldNum + y]] = -1;
			if (layout[x * fieldNum + i] != -1)
				ansArr[pos][layout[x * fieldNum + i]] = -1;
		}
		int subnum = (int) Math.sqrt(fieldNum);
		int x2 = x / subnum, y2 = y / subnum;
		for (int i = x2 * subnum; i < subnum + x2 * subnum; i++) {
			for (int j = y2 * subnum; j < subnum + y2 * subnum; j++) {
				if (layout[i * fieldNum + j] != -1)
					ansArr[pos][layout[i * fieldNum + j]] = -1;
			}
		}
		if (randomLayout == true)
			dealAnswer(pos);
	}

	
	private void dealAnswer(int pos) {
		List<Byte> list = new LinkedList<Byte>();
		for (int i = 0; i < fieldNum; i++)
			list.add(ansArr[pos][i]);
		int rdm = 0, idx = 0;
		while (list.size() != 0) {
			rdm = Math.abs(random.nextInt()) % list.size();
			ansArr[pos][idx] = list.get(rdm);
			list.remove(rdm);
			idx++;
		}
		list = null;
	}

	
	private int getAnswerCount(int pos) {
		int count = 0;
		for (int i = 0; i < fieldNum; i++)
			if (ansArr[pos][i] != -1)
				count++;
		return count;
	}

	private byte getAnswerNum(int fieldPos, int ansPos) {
		int cnt = 0;
		for (int i = 0; i < fieldNum; i++) {
			if (cnt == ansPos && ansArr[fieldPos][i] != -1)
				return ansArr[fieldPos][i];
			if (ansArr[fieldPos][i] != -1)
				cnt++;
		}
		return -1;
	}

	public long generate(long layoutCount) {
		curPos = 0;
		long count = 0;
		while (count < layoutCount || layoutCount == -1) {
			if (ansPosArr[curPos] == 0)
				getAnswer(curPos);
			int ansCount = getAnswerCount(curPos);
			if (ansCount == ansPosArr[curPos] && curPos == 0)
				break;
			if (ansCount == 0) {
				ansPosArr[curPos] = 0;
				curPos--;
				layout[curPos] = -1;
				continue;
			}
			else if (ansPosArr[curPos] == ansCount) {
				ansPosArr[curPos] = 0;
				curPos--;
				layout[curPos] = -1;
				continue;
			} else {
				layout[curPos] = getAnswerNum(curPos, ansPosArr[curPos]);
				ansPosArr[curPos]++;
				curPos++;
			}
			if (fieldNum * fieldNum == curPos) {
				outData();
				System.out.println();
				count++;
				curPos--;
				layout[curPos] = -1;
				ansPosArr[curPos] = 1;
			}
		}
		return count;
	}

	public void generateRandom(int count) {
		setRandomLayout(true);
		for (int i = 0; i < count; i++) {
			init(9);
			generate(1);
		}
	}
	
	public void outData() {
		String temp = "";
		for (int i = 0; i < fieldNum * fieldNum; i++) {
			if (layout[i] != -1){
				temp = temp + (layout[i] + 1);
			}	
		}
		finalSodukuString = temp;
	}

}