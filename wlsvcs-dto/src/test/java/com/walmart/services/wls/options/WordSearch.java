package com.walmart.services.wls.options;

import static java.util.Objects.nonNull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 *    0 1 2
 *  0 a b c
 *  1 d e f
 *  2 g h i
eight combinations..
(i,j),  (i+1, j), (i + 1, j - 1),  (i + 1, j + 1)
(i, j - 1),(i, j + 1), (i-1, j-1), (i-1, j + 1), (i-1, j)
 * 
 *
 */
public class WordSearch {
	private char[][] chars = null;
	private Map<String, Cell> cellsMap = null;
	private int xl;
	private int yl;
	public WordSearch(char[][] chars) {
		this.chars = chars;
		cellsMap = new HashMap<>();
		int length = chars.length;
		xl = chars[0].length;
		yl = chars[1].length;
		Cell cell = null;
		for(int i = 0; i < xl; i++) {
			for(int j = 0 ; j < yl ; j++) {
				cell = new Cell(i, j, chars[i][j]);
				if(cellsMap.containsKey(cell.key())) {
					cell = 	cellsMap.get(cell.key());	
				} else {
					cellsMap.put(cell.key(), cell);
				}
				add(cell, i, j-1);	
				add(cell, i, j+1);
				add(cell, i+1, j);
				add(cell, i+1, j-1);	
				add(cell, i+1, j+1);
				add(cell, i-1, j);	
				add(cell, i-1, j-1);	
				add(cell, i-1, j+1);
				System.out.println("Cell key: "+cell.key() +" and size of adjacent cells: "+cell.adjacentNodes.size());
			}
		}
		System.out.println("Size of cellsMap is: "+cellsMap.size());
	}
	
	private void add(Cell cell, int i, int j) {
		if(i < 0 || i == xl || j < 0 || j == yl) {
			return;
		}
		Cell newCell = new Cell(i, j, chars[i][j]);
		if(cellsMap.containsKey(newCell.key())) {
			newCell = cellsMap.get(newCell.key());
		} else {
			cellsMap.put(newCell.key(), newCell);
		}
		cell.add(newCell);
	}

	public boolean search(String word) {
		List<String> alreadyVisited = new ArrayList<>();
		System.out.println("recurssion true for string "+word+" search : and actual value is : "+
				find(word, cellsMap.values(), alreadyVisited));
		return find(word.toCharArray());
			
}

	private boolean find(String word, Collection<Cell> cells, List<String> alreadyVisited) {
		char[] chars = word.toCharArray();
		int length = chars.length;
		Collection<Cell> adjacentCells = null;
		boolean retFlag = false;
		for(Cell c : cells) {
			if(retFlag) {
				return retFlag;
			}
			//i = 0;
			//localVisited = new ArrayList<>(); 
			if(c.value == chars[0] 
					//&& !localVisited.contains(c.key())
					&& !alreadyVisited.contains(c.key())) {
				adjacentCells = c.adjacentNodes;
				//localVisited.add(c.key());
				alreadyVisited.add(c.key());
				if(length == 1) {
					retFlag = true;	
				} else{
					retFlag = find(word.substring(1), adjacentCells, alreadyVisited);
				}
			}else {
				continue;
			}
		}
		return retFlag;
	}
	private boolean find(char[] chars) {
		List<String> alreadyVisited = new ArrayList<>();
		Collection<Cell> cells = cellsMap.values();
		Collection<Cell> adjacentCells = null;
		Cell currentCell = null;
		boolean retFlag = true;
		int size = chars.length;
		int i = -1;
		for(Cell c : cells) {
			if(retFlag && i > 0) {
				return retFlag;
			}
			i = 0;
			if(c.value == chars[i]) {
				adjacentCells = c.adjacentNodes;
			}else {
				continue;
			}
			alreadyVisited = new ArrayList<>();
			currentCell = c;
			retFlag = true;
			while(retFlag && ++i < size) {
				currentCell = find(chars[i], adjacentCells, alreadyVisited);
				if(nonNull(currentCell)) {
					adjacentCells = currentCell.adjacentNodes;
				} else {
					retFlag = false;
				}
			}
			
		}
		return retFlag;
	}
		
	private Cell find(char ch, Collection<Cell> adjacentCells, List<String> alreadyVisited) {
		Cell cell = null;
		for(Cell c : adjacentCells) {
			cell = c.value == ch ? c : null;
			if (nonNull(cell) && !alreadyVisited.contains(c.key())) {
				alreadyVisited.add(c.key());
				return cell;
			}
		}
		return null;
	}

	class Cell {
		int xi;
		int yi;
		char value;
		List<Cell> adjacentNodes = null;
		Cell(int xi, int yi, char value) {
			this.xi = xi;
			this.yi = yi;
			this.value = value;
			adjacentNodes = new ArrayList<>();
		}
		void add(Cell adjacent) {
			adjacentNodes.add(adjacent);
		}
		public String key() {
			return ""+xi + ""+yi;
		}
		
	}
	public static void main(String[] args) {
		char[][] chars = { 
				{ 'g', 'o', 'r', 'a', 'k' },
				{ 'a', 'G', 'n', 'K', 'n' }, 
				{ 'h', 'w', 'O', 'A', 'a' },
				{ 'o', 'r', 'n', 'R', 't' }, 
				{ 'a', 'b', 'r', 'i', 'h' } };
		WordSearch ws = new WordSearch(chars);
		boolean expectedTrue = ws.search("GORAK");
		System.out.println("Expected true for string 'GORAK' search : and actual value is : "+expectedTrue);
		expectedTrue = ws.search("gorak");
		System.out.println("Expected true for string 'gorak' search : and actual value is : "+expectedTrue);
		expectedTrue = ws.search("hROGg");
		System.out.println("Expected true for string 'hROGg' search : and actual value is : "+expectedTrue);
		expectedTrue = ws.search("KnAth");
		System.out.println("Expected true for string 'KnAth' search : and actual value is : "+expectedTrue);
		expectedTrue = ws.search("arOKk");
		System.out.println("Expected true for string 'arOKk' search : and actual value is : "+expectedTrue);
		//it requires additional logic in search api to ensure algorithm works for all permutation and combinations..
		expectedTrue = ws.search("KanOA");
		System.out.println("Expected true for string 'KanOA' search : and actual value is : "+expectedTrue);
		expectedTrue = ws.search("Kanath");
		System.out.println("Expected true for string 'KanoA' search : and actual value is : "+expectedTrue);	}

}
